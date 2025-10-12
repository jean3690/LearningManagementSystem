<template>
  <el-header class="flex justify-around items-center border-b-2 border-black-200">
    <h1 class="text-2xl">Header</h1>
    <span class="time">{{ time }}</span>
  </el-header>
</template>
<script setup>
import dayjs from 'dayjs';
import { ref } from 'vue';
const time = ref(dayjs().format('YYYY年MM月DD天 HH时mm分ss秒'));
setInterval(() => {
  time.value = dayjs().format('YYYY年MM月DD天 HH时mm分ss秒');
}, 1000);
</script>