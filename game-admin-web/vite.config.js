import {defineConfig} from 'vite'
import path from "path";
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'

const pathSrc = path.resolve(__dirname, 'src')

export default defineConfig({
    // build:{
    //     assetsDir: "admin-assets",
    //     outDir:"../admin-service/resources/admin-dist"
    // },
    resolve: {
        alias: {
            "@": pathSrc,
        }
    },
    plugins: [
        vue(),
        AutoImport({
            imports: [
                "vue",
                "vue-router"
            ],
            resolvers: [
                // 自动导入 Element Plus 组件
                ElementPlusResolver(),
                // 自动导入图标组件
                IconsResolver(),
            ],
            dts: path.resolve(pathSrc, 'auto-imports.d.ts'),
        }),
        Components({
            resolvers: [
                // 自动导入 Element Plus 组件
                ElementPlusResolver(),
                // 自动注册图标组件
                IconsResolver({
                    enabledCollections: ['ep'],
                }),
            ],
            dts: path.resolve(pathSrc, 'components.d.ts'),
        }),
        Icons({
            autoInstall: true,
        }),
    ],
    css: {
        preprocessorOptions: {
            less: {
                javascriptEnabled: true,
                additionalData: `@import "@/config/style/global.less";`,
            },
        },
    },
    server: {
        host:'0.0.0.0',
        proxy: {
            "/api": {
                target: "http://localhost:5500/",
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ""),
            },
            "/app": {
                target: "http://www.bbg.com/",
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/app/, ""),
            },
        },

    },
})
