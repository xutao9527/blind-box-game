import axios from "axios";
import {bbgConf} from "@/config";
import JSONbig from "json-bigint";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export class AppAxiosHttp {
    constructor() {
        this.axiosRequestConfig = {};
        this.requestConfig()
        this.axiosInstance = axios.create(this.axiosRequestConfig);
        this.interceptorsRequest()
        this.interceptorsResponse()
    }

    get(url, config) {
        return this.axiosInstance.get(url, config)
    }

    post(url, data, config) {
        return this.axiosInstance.post(url, data, config)
    }

    request(config) {
        return this.axiosInstance.request(config);
    }

    // 请求配置
    requestConfig() {
        this.axiosRequestConfig = {
            baseURL: bbgConf.env.appBaseUrl,
            timeout: 5000,
            //  这是核心
            transformResponse: [function (data) {
                try {
                    // 如果转换成功则返回转换的数据结果
                    return JSONbig.parse(data)
                } catch (err) {
                    // 如果转换失败，则包装为统一数据格式并返回
                    return {
                        data
                    }
                }
            }]
        }
    }

    // 请求拦截
    interceptorsRequest() {
        this.axiosInstance.interceptors.request.use((config) => {
            // if(!this.userStore){
            //     this.userStore = useUserStore()
            // }
            if (mockGlobal.bizToken != null) {
                config.headers['token'] = mockGlobal.bizToken
            }
            config.data = config.data ? config.data : {}
            config.params = config.params ? config.params : {}
            return config;
        })
    }

    // 响应拦截
    interceptorsResponse() {
        this.axiosInstance.interceptors.response.use((res) => {
            return res.data;
        })
    }
}

export const appHttp = new AppAxiosHttp()