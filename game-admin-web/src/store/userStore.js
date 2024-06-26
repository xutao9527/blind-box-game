import {http} from "@/core/axios/";
import {defineStore} from "pinia";

const viewModules = import.meta.glob('@/views/**/*.vue');

export const useUserStore = defineStore('userStore', {
    state: () => (
        {
            menus: [],
            routes: [],
            user: null,
            token: null
        }
    ),
    getters: {
        // 获取用户的菜单
        getMenuTree: async (state) => {
            if (state.menus.length === 0) {
                await state.asyncRouters()
            }
            let menuTree = convertToTree(state.menus);
            return menuTree
        },
        // 获取用户的路由
        getRoutes: async (state) => {
            let routes = [];
            if (state.menus.length === 0) {
                await state.asyncRouters()
            }
            let result = convertToRoutes(convertToTree(state.menus))
            if (result && result.children && result.children.length > 0) {
                routes.push(result)
            }
            return routes
        },
        // 获取用户信息
        getUser: async (state) => {
            if (!state.user) {
                await state.asyncUserInfo()
            }
            return state.user
        },
    },
    actions: {
        async asyncRouters() {
            let ret = await http.get('/sysUser/currentUserMenu', {token: this.token})
            console.log("/sysUser/currentUserMenu")
            if (ret.ok && ret.data) {
                this.menus = ret.data.filter(m => m.enable)
            } else {
                this.setToken(null)
                this.menus = [];
            }
        },
        async asyncUserInfo() {
            let ret = await http.get('/sysUser/currentUser')
            if (ret.ok) {
                this.user = ret.data;
            } else {
                this.setToken(null)
            }
        },
        setToken(t) {
            this.token = t
            if (!t) {
                this.routes = []
                this.user = null
            }
        }
    },
    persist: {
        key: 'token',
        paths: ['token'],
        storage: localStorage,
    }
})

export const convertToTree = (menuList) => {
    const map = {};
    const tree = [];
    menuList.forEach(item => {
        map[item.id] = {...item, children: []};
    });
    menuList.forEach(item => {
        if (item.parentId !== 0 && item.parentId != null) {
            map[item.parentId].children.push(map[item.id]);
            map[item.parentId].children.sort((a, b) => a.sort - b.sort);
        } else {
            tree.push(map[item.id]);
        }
    });
    tree.sort((a, b) => a.sort - b.sort);
    return tree;
}

const convertToRoutes = (treeData) => {
    let routers
    if (treeData && treeData.length > 0) {
        routers = buildRoutes(treeData[0]);
    }
    return routers
}

const buildRoutes = (data) => {
    let route = {
        path: data.path,
        name: data.name,
        meta: {
            title: data.title,
            id: data.id
        }
    }

    if (data.parentId == null || data.parentId === 0) {
        route.name = data.name
        route.path = "/"
        route.redirect = data.path
    }

    if (typeof data.component === "string" && data.component.trim().length > 0) {
        route.component = viewModules[`/src${data.component}`]
    }

    if (data.children && data.children.length > 0) {
        route.children = []
        for (const dataKey in data.children) {
            route.children.push(buildRoutes(data.children[dataKey]))
        }
    }
    return route
}