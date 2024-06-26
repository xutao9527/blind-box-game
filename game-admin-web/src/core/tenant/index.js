import {useUserStore} from "@/store/userStore.js";
import {http} from "@/core/axios/index.js";

const TenantUtil = {
    currentUser: await useUserStore().getUser,
    isSuperTenant: () => {
        if (TenantUtil.currentUser) {
            return TenantUtil.currentUser.superTenant
        }
        return false;
    },
    getTenantName: (tenantId) => {
        if (tenantId && TenantUtil.currentUser != null && TenantUtil.currentUser.superTenant) {
            const sysTenant = TenantUtil.currentUser.tenantMap[tenantId.toString()]
            return sysTenant == null ? "无" : sysTenant.tenantName
        }
        return "无"
    }
}

export default TenantUtil
