import {appHttp} from "./mockHttp.js";
import {http} from "@/core/axios";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";


export const loginMock = reactive({
    loginReq: {
        mobile: "18612344321",
        password: "xutao",
    },
    loginRes: {},
    getInfo: async () => {
        // 后端拿token
        const getTokenRet = await http.get('boxGameMock/getToken')
        if(getTokenRet.ok){
            mockGlobal.bizToken = getTokenRet.data
        }
        // 后端拿token
        const apiRet = await appHttp.get('bizUser/getInfo')
        if(apiRet.ok){
            mockGlobal.bizUser = apiRet.data.bizUser
            mockGlobal.bizToken = apiRet.data.token
        }
    },
    login: async () => {
        const apiRet = await appHttp.post('bizUser/login', loginMock.loginReq)
        if(apiRet.ok){
            mockGlobal.bizUser = apiRet.data.bizUser
            mockGlobal.bizToken = apiRet.data.token
            // 保存token到后端，防止刷新页面后token丢失
            await http.get('boxGameMock/setToken?token=' + apiRet.data.token)
        }

    },
    logout: async () => {
        const apiRet = await appHttp.get('bizUser/logout')
        if(apiRet.ok){
            mockGlobal.bizUser = {}
            mockGlobal.bizToken = null
            await http.get('boxGameMock/setToken?token=' )
        }
    },

})
