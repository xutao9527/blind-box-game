import {useUserStore} from "@/store/userStore.js";


let userStore = null;
export const hasPermission = {
    install(Vue) {
        //自定义指令v-has：
        Vue.directive('has', {
            mounted(el, binding, vnode) {
                if(userStore==null){
                    userStore = useUserStore();
                }
                userStore.hasPermission(binding.value);
            },
        });
    }
};



export default hasPermission;