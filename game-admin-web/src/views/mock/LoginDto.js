import {http} from "@/core/axios/index.js";


export const LoginMock = reactive({
    loginReq:{
        mobile: "",
        password: ""
    },
    loginRes:{},
    login:()=>{
        const apiRet = http.post('boxGameMock/login',LoginMock.loginReq)
        console.log(apiRet)
    }
})
