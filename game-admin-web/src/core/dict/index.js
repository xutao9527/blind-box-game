import {http} from "@/core/axios/index.js";


export class DictObject{
    constructor(bizDict, bizDictDetails) {
        this.bizDict = bizDict;
        this.bizDictDetails = bizDictDetails;
        this.dictMap = this.createDictMap(bizDictDetails);
    }

    static async create(tag) {
        try {
            let apiRet = await http.get(`/bizDict/getDict/${tag}`);
            if (apiRet.ok) {
                const bizDict = apiRet.data;
                const bizDictDetails = bizDict ? bizDict.bizDictDetails : [];
                return new DictObject(bizDict, bizDictDetails);
            }
        } catch (error) {
            console.error("创建 DictObject 实例失败:", error);
            throw error; // 抛出错误以便调用者处理
        }
    }
    createDictMap(bizDictDetails) {
        const dictMap = {};
        bizDictDetails.forEach(detail => {
            dictMap[detail.labelAlias] = detail.value;
        });
        return dictMap;
    }
    getLabel(value){
        const foundElement = this.bizDictDetails.find(obj => obj.value === value)
        if (foundElement) {
            return foundElement.label
        } else {
            return ""
        }
    }
    getValueByLabelAlias(nameAlias){
        const foundElement = this.bizDictDetails.find(obj => obj.labelAlias === labelAlias)
        if (foundElement) {
            return foundElement.labelAlias
        } else {
            return ""
        }
    }
    getValue(label){
        const foundElement = this.bizDictDetails.find(obj => obj.label === label)
        if (foundElement) {
            return foundElement.value
        } else {
            return ""
        }
    }
}
