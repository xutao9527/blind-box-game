import {useUserStore} from "@/store/userStore.js";

const TenantUtil = {
    currentUser: useUserStore().user,
    isSuperTenant: () => {
        if (TenantUtil.currentUser != null) {
            return TenantUtil.currentUser.superTenant
        }
        return false;
    }
}

export default TenantUtil
