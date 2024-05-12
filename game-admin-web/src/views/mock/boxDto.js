import {http} from "@/core/axios/index.js";
import {mockGlobal} from "@/views/mock/mockGlobal.js";

export const boxMock = reactive({
    getBoxReq: {
        "type": "1"
    },
    getBoxRes: {},
    boxList: async () => {
        const apiRet = await http.post('boxGameMock/list', boxMock.getBoxReq)
        if (apiRet.ok) {
            boxMock.getBoxRes = apiRet.data
        }
    },
    openBoxReq:{},
    openBoxRes:{},
    openBoxRecord:[],
    openBox: async (boxId) => {
        boxMock.openBoxReq.boxId = boxId;
        const apiRet = await http.post('boxGameMock/open', boxMock.openBoxReq)
        if (apiRet.ok) {
            boxMock.openBoxRes = apiRet.data
            mockGlobal.bizUser.money = boxMock.openBoxRes.bizUser.money
            boxMock.openBoxRecord.push(boxMock.openBoxRes.luckStorehouse)
        }
    }
})