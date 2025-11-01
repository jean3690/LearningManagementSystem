<template>
  <div class="flex justify-center items-center min-h-screen bg-gradient-to-br from-purple-600 to-blue-500 p-5">
    <el-card class="w-full max-w-md shadow-xl" shadow="hover">
      <template #header>
        <div class="text-center">
          <h2>学习管理系统</h2>
          <p>请登录您的账户</p>
        </div>
      </template>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        label-width="0"
        size="large"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名"
            :prefix-icon="User"
            clearable
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            :prefix-icon="Lock"
            show-password
            clearable
          />
        </el-form-item>

        <div class="flex justify-between items-center mb-5">
          <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
          <el-link type="primary" :underline="false">忘记密码?</el-link>
        </div>

        <el-form-item>
          <el-button
            type="primary"
            class="w-full"
            @click="handleLogin"
            :loading="userStore.isLoading"
            size="large"
          >
            登录
          </el-button>
        </el-form-item>

        <div class="text-center mt-4 text-sm text-gray-600">
          还没有账户?
          <el-link type="primary" :underline="false">立即注册</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/user'
import { MessageUtil } from '@/utils'
import {login} from '@/api/user/user'
const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)

const loginForm = reactive({
  username: '',
  password: '',
  remember: false
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度应在3到20个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 30, message: '密码长度应在6到30个字符之间', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return

  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
          await login(loginForm)
        MessageUtil.success('登录成功')
        // 登录成功后跳转到首页
        router.push('/')
      } catch (error) {
        MessageUtil.error('登录失败，请检查用户名和密码')
      }
    }
  })
}
</script>
