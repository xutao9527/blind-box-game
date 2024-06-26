import {useUserStore} from "@/store/userStore.js";

const TenantUtil = {
    currentUser: await useUserStore().getUser,
    isSuperTenant: () => {
        if (TenantUtil.currentUser ) {
            return TenantUtil.currentUser.superTenant
        }
        return false;
    }
}

export default TenantUtil
