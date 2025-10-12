<template>
    <div class="w-screen h-screen">
        <el-container class="h-full w-full flex flex-1">
            <Side class="h-full w-1/5 float-left bg-[#545c64]">
                <template #title>
                    <h2 class="text-2xl mb-2 mt-2">在线学习管理平台</h2>
                </template>
            </Side>
            <el-main class="h-full float-right w-0.8">
                <Header class="-mt-9"/>
                <RouterView/>
            </el-main>
        </el-container>
    </div>
</template>
<script setup>
import Side from '@/components/side/index.vue'
import Header from '@/components/hearder/index.vue'
</script>
<style scoped>
</style>