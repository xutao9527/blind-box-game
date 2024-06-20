import {appHttp} from "./mockHttp.js";
import {http} from "@/core/axios";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";


export const loginMock = reactive({
    LoginPwdReq: {  // 密码登录请求参数
        mobile: "18612344321",
        password: "123456",
    },
    LoginCodeReq: { // 验证码登录请求参数
        mobile: "18612344321",
        code: "",
    },
    RegisterReq: {  //注册登录请求参数
        mobile: "",
        password: "",
        code: "",
        invitationCode:null
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
    loginPwd: async () => {     // 密码登录
        const apiRet = await appHttp.post('bizUser/loginByPwd', loginMock.LoginPwdReq)
        if(apiRet.ok){
            mockGlobal.bizUser = apiRet.data.bizUser
            mockGlobal.bizToken = apiRet.data.token
            // 保存token到后端，防止刷新页面后token丢失
            await http.get('boxGameMock/setToken?token=' + apiRet.data.token)
        }else{
            ElMessage({type: 'success', message: apiRet.msg})
        }
    },
    loginCode: async () => {    // 验证码登录
        const apiRet = await appHttp.post('bizUser/loginByCode', loginMock.LoginCodeReq)

        if(apiRet.ok){
            mockGlobal.bizUser = apiRet.data.bizUser
            mockGlobal.bizToken = apiRet.data.token
            // 保存token到后端，防止刷新页面后token丢失
            await http.get('boxGameMock/setToken?token=' + apiRet.data.token)
        }else{
            ElMessage({type: 'error', message: apiRet.msg})
        }

    },
    sendCode: async (mobile) => {     // 发送验证码
        const apiRet = await appHttp.get('bizUser/sendCode?mobile=' + mobile)
        if(apiRet.ok){
            ElMessage.success('发送成功')
        }
    },
    register: async () => {     // 注册
        const apiRet = await appHttp.post('bizUser/register', loginMock.RegisterReq, {
            headers: {
                promoCode: '1cZVQMDpEB'
            }
        })
        if(apiRet.ok){
            ElMessage.success('注册成功')
        }
    },
    logout: async () => {       // 退出登录
        const apiRet = await appHttp.get('bizUser/logout')
        if(apiRet.ok){
            mockGlobal.bizUser = {}
            mockGlobal.bizToken = null
            await http.get('boxGameMock/setToken?token=' )
        }
    },

})
