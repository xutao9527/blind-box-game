import { createApp } from 'vue'
import App from './App.vue'
import '@/config/style/global.less'
import 'element-plus/theme-chalk/dark/css-vars.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import "@/core/common/browser_patch.js"
import {bbgConf} from "@/config/index.js";
import router from '@/router'
import store from "@/store/index";

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
    bbgConf.iconList.push(key);
}
app.use(store)
app.use(router)
app.mount('#app')

