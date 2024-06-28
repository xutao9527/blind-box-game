export const hasPermission = {
    install(Vue) {
        //自定义指令v-has：
        Vue.directive('has', {
            mounted(el, binding, vnode) {
                console.log(el)
                console.log(binding)
                console.log(vnode)
            },
        });
    }
};
export default hasPermission;