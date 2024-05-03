import {http} from "@/core/axios/index.js";
import {mockGlobal} from "@/views/mock/mockGlobal.js";


export const loginMock = reactive({
    loginReq: {
        mobile: "18612344321",
        password: "xutao",
    },
    loginRes: {},
    login: async () => {
        const apiRet = await http.post('boxGameMock/login', loginMock.loginReq)
        if(apiRet.ok){
            console.log(apiRet.data.token)
            mockGlobal.bizUser = apiRet.data.bizUser
            mockGlobal.bizToken = apiRet.data.token
        }

    },
    logout: async () => {
        const apiRet = await http.post('boxGameMock/logout')
        if(apiRet.ok){
            console.log(apiRet.data.token)
            mockGlobal.bizUser = {}
            mockGlobal.bizToken = null
        }
    }
})
