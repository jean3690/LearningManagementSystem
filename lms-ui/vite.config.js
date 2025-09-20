import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Pages from 'vite-plugin-pages'
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";
import Components from 'unplugin-vue-components/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
      vue(),
      Pages(
          {
              dirs: ['src/pages'],
              extensions: ['vue']
          }
      ),
      Components({
          resolvers: [ElementPlusResolver()]
      })
  ],
})
