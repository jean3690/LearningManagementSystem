import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import Pages from 'vite-plugin-pages'
import path from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        Pages({
            passDir: 'src/pages',
            importPath: '@/pages',
            extensions: ['vue', 'jsx']
        }),
        AutoImport({
            resolvers: [ElementPlusResolver()],
            imports: ['vue', 'vue-router']
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        })
    ],
    alias: {
        '@': path.resolve(__dirname, 'src')
    }
})
