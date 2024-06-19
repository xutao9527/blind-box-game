import {appHttp} from "./mockHttp.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export const rollMock = reactive({
    GetRollListReq: {
        pageNumber: 1,
        pageSize: "10"
    },
    rollList: [],
    getRollList: async () => {
        const apiRet = await appHttp.post('csgoRoll/getRollList', rollMock.GetRollListReq)
        if (apiRet.ok) {
            rollMock.rollList = apiRet.data.records
        }
    },
})