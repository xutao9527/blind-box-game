import {useUserStore} from "@/store/userStore.js";
import {http} from "@/core/axios/index.js";

const TenantUtil = {
    currentUser: await useUserStore().getUser,
    isSuperTenant: () => {
        console.log("isSuperTenant",TenantUtil.currentUser)
        if (TenantUtil.currentUser) {
            return TenantUtil.currentUser.superTenant
        }
        return false;
    },
    getTenantName: (tenantId) => {
        if (tenantId && TenantUtil.currentUser != null && TenantUtil.currentUser.superTenant) {
            const sysTenant = TenantUtil.currentUser.tenantMap[tenantId.toString()]
            return sysTenant == null ? undefined : sysTenant.tenantName
        }
        return undefined
    },
    getTenants: (includeTopTenant) => {
        if (TenantUtil.currentUser != null && TenantUtil.currentUser.superTenant) {
            if(includeTopTenant){
                return Object.values(TenantUtil.currentUser.tenantMap).sort((a, b) => a.id - b.id)
            }
            return Object.values(TenantUtil.currentUser.tenantMap).filter(tenant => tenant.parentId !== null).sort((a, b) => a.id - b.id)
        }
        return []
    }
}

export default TenantUtil
