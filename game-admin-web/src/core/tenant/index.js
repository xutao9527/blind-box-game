import {http} from "@/core/axios/index.js";
import {bbgConf} from "@/config/index.js";


export class TenantObject{
    async constructor() {
        await this.fetchData()
    }
    async fetchData() {
        this.data = await http.get("sysTenant/getTenantList")
    }
}

export const tenantObject = new TenantObject();

