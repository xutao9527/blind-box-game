import {appHttp} from "./mockHttp.js";

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