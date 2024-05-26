import {appHttp} from "./mockHttp.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export const boxMock = reactive({
    getBoxReq: {
        "type": "1"
    },
    boxList: [],
    getBoxList: async () => {
        const apiRet = await appHttp.post('csgoBox/list', boxMock.getBoxReq)
        if (apiRet.ok) {
            boxMock.boxList = apiRet.data
        }
    },
    openBoxReq:{
        boxId:null,
    },
    openBoxRes:{
        money:null,
        csgoOpenBoxLog:null,
    },
    openBoxRecord:[],
    openBox: async (boxId) => {
        boxMock.openBoxReq.boxId = boxId;
        const apiRet = await appHttp.post('csgoBox/open', boxMock.openBoxReq)
        if (apiRet.ok) {
            boxMock.openBoxRes = apiRet.data
            mockGlobal.bizUser.money = boxMock.openBoxRes.money
            boxMock.openBoxRecord.push(boxMock.openBoxRes.csgoOpenBoxLog)
            ElMessage({type: 'success', message: `中奖:${boxMock.openBoxRes.csgoOpenBoxLog.goodName}`})
        }
    }
})