import {http} from "@/core/axios/index.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export const dreamMock = reactive({
    dreamListReq: {
        pageNumber: 1,
        pageSize: 10,
        name: null,
        type: null,
        expandProps: {}
    },
    dreamListRes: {
        dreamGoodsPage: {}
    },
    dreamList: async () => {
        const apiRet = await http.post('/boxGameMock/dreamList', dreamMock.dreamListReq)
        if (apiRet.ok) {
            dreamMock.dreamListRes.dreamGoodsPage = apiRet.data.dreamGoodsPage
        }
    },
    dreamGoodReq: {
        boxGoodId: null,
        probability: null,
    },
    dreamGoodRes: {
        bizUser: null,
        csgoBoxGood: null,
    },
    dreamGoodRecord: [],
    dreamGood: async () => {
        const apiRet = await http.post('/boxGameMock/dreamGood', dreamMock.dreamGoodReq)
        if (apiRet.ok) {
            dreamMock.dreamGoodRes = apiRet.data
            if (dreamMock.dreamGoodRes.csgoBoxGood != null) {
                ElMessage({type: 'success', message: `中奖:${dreamMock.dreamGoodRes.csgoBoxGood.name}`})
                dreamMock.dreamGoodRecord.push(dreamMock.dreamGoodRes.csgoBoxGood)
            }else{
                ElMessage({type: 'error', message: '没有中奖'})
            }
            console.log(dreamMock.dreamGoodRes)
            mockGlobal.bizUser.money = dreamMock.dreamGoodRes.bizUser.money
        }
    },

})