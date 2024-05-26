import {appHttp} from "./mockHttp.js";
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
        const apiRet = await appHttp.post('/csgoBox/dreamList', dreamMock.dreamListReq)
        if (apiRet.ok) {
            dreamMock.dreamListRes.dreamGoodsPage = apiRet.data.dreamGoodsPage
        }
    },
    dreamGoodReq: {
        boxGoodId: null,
        probability: null,
    },
    dreamGoodRes: {
        money: null,
        csgoDreamGoodLog: null,
    },
    dreamGoodRecord: [],
    dreamGood: async () => {
        const apiRet = await appHttp.post('/csgoBox/dreamGood', dreamMock.dreamGoodReq)
        if (apiRet.ok) {
            console.log(apiRet.data)
            dreamMock.dreamGoodRes = apiRet.data
            if (dreamMock.dreamGoodRes.csgoDreamGoodLog.dreamIsWin) {
                ElMessage({type: 'success', message: `中奖:${dreamMock.dreamGoodRes.csgoDreamGoodLog.goodName}`})
                dreamMock.dreamGoodRecord.push(dreamMock.dreamGoodRes.csgoDreamGoodLog)
            }else{
                ElMessage({type: 'error', message: '没有中奖'})
            }
            mockGlobal.bizUser.money = dreamMock.dreamGoodRes.money
        }
    },

})