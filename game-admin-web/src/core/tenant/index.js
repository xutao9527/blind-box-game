const TenantUtil = {

    currentUser: null,
    isSuperTenant: () => {
        if (TenantUtil.currentUser != null) {
            return TenantUtil.currentUser.superTenant
        }
        return false;
    }
}

export default TenantUtil
