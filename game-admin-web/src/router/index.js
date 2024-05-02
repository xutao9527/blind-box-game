import {createRouter, createWebHistory} from "vue-router";
import {useUserStore} from "@/store/userStore.js";
import store from "@/store/index.js";

const routes = [
    {
        name: 'Login',
        path: '/login',
        component: () => import('@/views/login.vue')
    },
    {
        name: 'NotFound',
        path: '/NotFound',
        component: () => import('@/views/404.vue')
    },
    {
        name: '404',
        path: '/:catchAll(.*)',
        component: () => import('@/views/404.vue'),
    },
];

const router = createRouter({
    history: createWebHistory('/admin'),
    routes,
});

router.beforeEach(async (to, from) => {
    const store = useUserStore()
    if (router.hasRoute(to.name) && to.name !== "404") {
        return true
    } else {
        if (store.token) {
            if (store.routes.length === 0) {
                let newRoutes = await store.getRoutes
                newRoutes.forEach(newRoute => {
                    router.addRoute(newRoute)
                })
                if (!store.token) {
                    return {path: "/login"}
                } else if (to.name === '404') {
                    return {path: to.path, query: to.query}
                } else {
                    return {...to, replace: true}
                }
            }
        } else {
            return {path: "/login"}
        }
    }
})

export default router