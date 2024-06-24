import {defineStore} from "pinia";


export const useDebugDataStore = defineStore('debugDataStore', {
    state: () => (
        {
            debugData: {},
        }
    ),
    actions:{
        setData(key, data){
            this.debugData[key] = data;
        },
        getData(key)   {
            return this.debugData[key]
           // console.log('1',this.debugData)
        }
    },
    persist: {
        key: 'debug-data',
        paths: ['debugData'],
        storage: localStorage,
    },
})