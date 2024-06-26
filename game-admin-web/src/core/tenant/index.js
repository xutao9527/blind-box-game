import {useUserStore} from "@/store/userStore.js";

const userStore = useUserStore()
const TenantUtil = {
    currentUser: null,
    getCurrentUser: () => {
        console.log("getCurrentUser start")
        if (TenantUtil.currentUser == null) {
            userStore.getUser.then(user => {
                console.log("setCurrentUser11", user)
                TenantUtil.currentUser = user
                console.log("setCurrentUser22", TenantUtil.currentUser)
            })
        } else {
            return
        }
        let sleepTime = 1000;
        let sleepStep = 10;
        const sleep = (ms) => {
            return new Promise(resolve => {
                setTimeout(() => {
                    resolve();
                }, ms);
            });
        }
        const loop = () => {
            sleepTime -= sleepStep
            if (sleepTime <= 0 || TenantUtil.currentUser != null) {
                console.log("loop", sleepTime, TenantUtil.currentUser)
                return
            }
            sleep(sleepStep).then(() => {
                loop(); // 递归调用 loop 函数来实现循环
            });
        }
        loop()
        console.log("getCurrentUser end")
    },
    isSuperTenant: () => {
        TenantUtil.getCurrentUser()
        if (TenantUtil.currentUser == null) {
            const stack = new Error().stack.split('\n').slice(2).filter(line => !line.includes('node_')).join('\n');
            console.log("isSuperTenant", TenantUtil.currentUser, stack)
        }
        if (TenantUtil.currentUser) {
            return TenantUtil.currentUser.superTenant
        }
        return false;
    },
    getTenantName: (tenantId) => {
        TenantUtil.getCurrentUser();
        if (tenantId && TenantUtil.currentUser != null && TenantUtil.currentUser.superTenant) {
            const sysTenant = TenantUtil.currentUser.tenantMap[tenantId.toString()]
            return sysTenant == null ? undefined : sysTenant.tenantName
        }
        return undefined
    },
    getTenants: (includeTopTenant) => {
        TenantUtil.getCurrentUser()
        console.log("getTenants", TenantUtil.currentUser, includeTopTenant)
        if (TenantUtil.currentUser != null && TenantUtil.currentUser.superTenant) {
            if (includeTopTenant) {
                return Object.values(TenantUtil.currentUser.tenantMap).sort((a, b) => a.id - b.id)
            }
            return Object.values(TenantUtil.currentUser.tenantMap).filter(tenant => tenant.parentId !== null).sort((a, b) => a.id - b.id)
        }
        return []
    }
}

export default TenantUtil
