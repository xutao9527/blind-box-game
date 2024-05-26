import {appHttp} from "@/views/mock/js/mockHttp.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

export const battleMock = reactive({
    getBoxReq: {
        "type": "2"
    },
    boxList: [],
    getBoxList: async () => {
        const apiRet = await appHttp.post('csgoBox/list', battleMock.getBoxReq)
        if (apiRet.ok) {
            battleMock.boxList = apiRet.data
        }
    },
    robotList: [],
    getRobotList: async () => {
        const apiRet = await appHttp.get('csgoRobot/list')
        if (apiRet.ok) {
            battleMock.robotList = apiRet.data
        }
    },
    createRoomReq: {
        battleModel: "1",
        peopleNumber: "2",
        boxesId: [],
        robotsId: [],
    },
    createRoom: async () => {
        if (battleMock.createRoomReq.boxesId.length === 0) {
            ElMessage({type: 'error', message: '请选择箱子'})
            return
        }
        if (battleMock.createRoomReq.robotsId.length + 1 > battleMock.createRoomReq.peopleNumber) {
            ElMessage({type: 'error', message: '人数太多'})
            return
        }
        const apiRet = await appHttp.post('csgoBattleRoom/create', battleMock.createRoomReq)
        if (apiRet.ok) {
            ElMessage({type: 'success', message: '创建成功'})
        }
    },
    roomList: [],
    getRoomList: async () => {
        const apiRet = await appHttp.post('csgoBattleRoom/getRoomList')
        if (apiRet.ok) {
            battleMock.roomList = apiRet.data.records
        }
    },
    joinRoomReq: {
        roomId: ""
    },
    joinRoom: async () => {
        const apiRet = await appHttp.post('csgoBattleRoom/join', battleMock.joinRoomReq)
        if (apiRet.ok) {
            ElMessage({type: 'success', message: '加入成功'})
        }
    },
})