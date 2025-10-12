import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import Pages from 'vite-plugin-pages'
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";
import Components from 'unplugin-vue-components/vite'
import AutoImport from 'unplugin-auto-import/vite';
import Icon from 'unplugin-icons/vite';
import IconsResolver from 'unplugin-icons/resolver';
import tailwindcss from '@tailwindcss/vite';
import RemoteAssets from 'vite-plugin-remote-assets';
import eslintPlungin from 'vite-plugin-eslint';

export default defineConfig({
    plugins: [
        vue(),
        tailwindcss(),
        RemoteAssets(),
        eslintPlungin({
            cache: false,
            include: ['src/**/*.js', 'src/**/*.vue', 'src/*.js', 'src/*.vue'],
            exclude: ['node_modules'],
            fix: true,
            formatter: 'stylish',
            failOnError: false,
            failOnWarning: false,
        }),
        Pages(
            {
                dirs: ['src/pages'],
                extensions: ['vue'],
                exclude: ['**/components/*.vue'],
            }
        ),
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
    ],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src')
        }
    },
    server: {
        open: true,
        port: 3000
    }
})
