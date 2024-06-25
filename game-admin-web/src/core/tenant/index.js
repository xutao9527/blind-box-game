import {http} from "@/core/axios/index.js";

export class TenantObject{
    async constructor() {
        await this.fetchData()
    }
    async fetchData() {
        this.data = await http.get("sysTenant/getTenantList")
        console.log("TenantObject",this.data)
    }
}

export const tenantObject = new TenantObject();

