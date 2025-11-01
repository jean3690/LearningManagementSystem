import {createRouter,createWebHistory} from "vue-router";
import generatedRoutes from '~pages'
import eventEmitter from "../event/eventEmitter";

const router = createRouter({
    history: createWebHistory(),
    routes: generatedRoutes
})

eventEmitter.on('API:login', () => {
    router.push('/home')
})
eventEmitter.on('API:logout', () => {
    router.push('/login')
})
export default router;