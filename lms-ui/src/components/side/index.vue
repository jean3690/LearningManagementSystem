<template>
  <el-aside style="text-align: center;">
    <!-- Logo 和标题 -->
    <div class="flex justify-center items-center mt-2">
      <el-icon style="font-size: 30px;">
        <i-ep-eleme />
      </el-icon>
      <slot name="title">
        <h2 class="text-2xl text-white mb-4 ml-2">后台管理系统</h2>
      </slot>
    </div>

    <!-- 菜单 -->
    <el-menu
      :default-active="$route.path"
      :collapse="isCollapse"
      router
      background-color="gray"
      text-color="#fff"
      active-text-color="#ffd04b"
      class="border-none"
    >
      <el-menu-item
        v-for="(item, index) in array"
        :key="index"
        :index="`/home/${item.path}`"
      >
        <el-icon>
          <!-- 可以根据 item.name 动态设置图标，这里先用一个通用图标 -->
          <component :is="getIconComponent(item.path)" />
        </el-icon>
        <template #title>
          <h3 class="text-xl">{{ item.name }}</h3>
        </template>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import {
  User,
  Tickets,
  Setting,
  Document,
  Menu,
  Checked,
  ChatLineSquare,
  Clock,
  Money,
  Ticket,
  ShoppingCart,
  Plus
} from '@element-plus/icons-vue'

// 定义图标映射
const iconMap = {
  user: User,
  course: Tickets,
  module: Setting,
  section: Document,
  category: Menu,
  courseEnroll: Checked,
  courseComment: ChatLineSquare,
  courseStudy: Clock,
  invoice: Money,
  coupon: Ticket,
  order: ShoppingCart,
  default: Plus
}

const getIconComponent = (path) => {
  return iconMap[path] || iconMap.default
}

// 当前路由
const route = useRoute()

// 是否折叠菜单
const isCollapse = ref(false)

// 菜单项数据
const array = ref([
  { name: '用户管理接口', path: 'user' },
  { name: '课程管理接口', path: 'course' },
  { name: '课程模块管理接口', path: 'courseModule' },
  { name: '课程课时内容管理接口', path: 'lessonProgress' },
  { name: '分类管理接口', path: 'category' },
  { name: '课程报名管理接口', path: 'enrollment' },
  { name: '课程评论管理接口', path: 'courseReviews' },
  { name: '课程学习进度管理接口', path: 'lesson' },
  { name: '发票管理接口', path: 'invoice' },
  { name: '优惠券管理接口', path: 'coupon' },
  { name: '订单管理接口', path: 'order' }
])
</script>

<style scoped>
/* 去除 el-menu 默认边框 */
.el-menu {
  border-right: none;
}
</style>