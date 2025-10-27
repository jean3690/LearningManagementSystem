import {defineConfig} from 'vite';
import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import Pages from 'vite-plugin-pages'
import path from 'path'
import Icon from 'unplugin-icons/vite';
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
import IconsResolver from 'unplugin-icons/resolver';
import eslintPlungin from 'vite-plugin-eslint';

export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        Pages({
            dirs: ['src/pages'],
            extensions: ['vue'],
            exclude: ['**/components/*.vue'],
        }),
        AutoImport({
            imports: ['vue'],
            resolvers: [
                ElementPlusResolver(),
                IconsResolver({
                    prefix: 'Icon',
                }),
            ],
        }),
        Components({
            resolvers: [
                ElementPlusResolver(),
                IconsResolver({
                    enabledCollections: ['ep']
                }),
            ],
        }),
        Icon({
            autoInstall: true,
        }),
        eslintPlungin({
            cache: false,
            include: ['src/**/*.js', 'src/**/*.vue', 'src/*.js', 'src/*.vue'],
            exclude: ['node_modules'],
            fix: true,
            formatter: 'stylish',
            failOnError: false,
            failOnWarning: false,
        }),
    ],
    alias: {
        '@': path.resolve(__dirname, 'src')
    },
    server: {
        open: true,
        port: 3000
    }
})
