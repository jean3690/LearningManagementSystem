import { createApp } from 'vue'
import App from './App.vue'
import router from "./router/index.js";
import { createPinia } from 'pinia';
import { useUserStore } from './store/user';
import 'element-plus/dist/index.css'
import './css/index.css'

const app = createApp(App)
const pinia = createPinia();

app.use(router);
app.use(pinia);
app.mount('#app');
// // 初始化用户状态
// const userStore = useUserStore();
// userStore.initUserState().then(() => {
//   app.mount('#app');
// }).catch(error => {
//   console.error("初始化用户状态失败:", error);
//   // 即使初始化失败，也挂载应用
//   app.mount('#app');
// });