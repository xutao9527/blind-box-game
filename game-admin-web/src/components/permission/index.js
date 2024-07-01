import {useUserStore} from "@/store/userStore.js";

let userStore = null;
export const hasPermission = {
    install(Vue) {
        //自定义指令v-has：
        Vue.directive('has', {
            mounted(el, binding, vnode) {
                if (userStore == null) {
                    userStore = useUserStore();
                }
                if (!userStore.hasPermission(binding.value)) {
                    el.style.display = 'none';
                }
            },
        });
        Vue.config.globalProperties.has = function (permissions) {
            if (userStore == null) {
                userStore = useUserStore();
            }
            let hasPermission = false;
            if (permissions instanceof Array) {
                for (let value of permissions) {
                    if (userStore.hasPermission(value)) {
                        hasPermission = true
                        break;
                    }
                }
            }
            return hasPermission
        };
    }
};
export default hasPermission;