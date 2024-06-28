export const hasPermission = {
    install(Vue) {
        //自定义指令v-has：
        Vue.directive('has', {
            mounted(el, binding, vnode) {
                console.log(binding)
            },
        });

    }
};
export default hasPermission;