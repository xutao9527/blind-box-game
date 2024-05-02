import {createPinia} from 'pinia'
import {createPersistedState} from "pinia-plugin-persistedstate";

const store = createPinia()
store.use(createPersistedState({
    auto: true,	//	该配置将会使所有 Store 持久化存储，且必须配置 persist: false 显式禁用持久化。
}))

export default store

