import {http} from "@/core/axios/index.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export const dreamMock = reactive({
    dreamListReq: {
        pageNumber: 1,
        pageSize: 10,
        name:null,
        type:null,
        expandProps:{}
    },
    dreamListRes: {
        dreamGoodsPage:{}
    },
    dreamList: async () => {
        const apiRet = await http.post('/boxGameMock/dreamList', dreamMock.dreamListReq)
        if (apiRet.ok) {
            dreamMock.dreamListRes.dreamGoodsPage = apiRet.data.dreamGoodsPage
        }
    }
})