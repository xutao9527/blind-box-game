import {http} from "@/core/axios/index.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export const boxMock = reactive({
    getBoxReq: {
        "type": "1"
    },
    boxList: [],
    getBoxList: async () => {
        const apiRet = await http.post('boxGameMock/list', boxMock.getBoxReq)
        if (apiRet.ok) {
            boxMock.boxList = apiRet.data
        }
    },
    openBoxReq:{
        boxId:null,
    },
    openBoxRes:{
        bizUser:null,
        luckStorehouse:null,
    },
    openBoxRecord:[],
    openBox: async (boxId) => {
        boxMock.openBoxReq.boxId = boxId;
        const apiRet = await http.post('boxGameMock/open', boxMock.openBoxReq)
        if (apiRet.ok) {
            boxMock.openBoxRes = apiRet.data
            mockGlobal.bizUser.money = boxMock.openBoxRes.bizUser.money
            boxMock.openBoxRecord.push(boxMock.openBoxRes.luckStorehouse)
            ElMessage({type: 'success', message: `中奖:${boxMock.openBoxRes.luckStorehouse.name}`})
        }
    }
})