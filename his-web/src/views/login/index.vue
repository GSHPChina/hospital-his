<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="bg-shape shape1"></div>
      <div class="bg-shape shape2"></div>
      <div class="bg-shape shape3"></div>
    </div>
    <div class="login-card">
      <div class="login-header">
        <div class="logo-icon">🏥</div>
        <h1>医院信息管理系统</h1>
        <p>Hospital Information System</p>
      </div>
      <el-form ref="loginFormRef" :model="loginForm" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="Lock" type="password" placeholder="请输入密码" size="large" show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin" size="large" style="width: 100%; height: 44px; font-size: 16px;">
            登 录
          </el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        <div class="test-accounts">
          <p class="title">测试账号</p>
          <div class="accounts-grid">
            <div class="account-item" v-for="acc in testAccounts" :key="acc.user" @click="fillAccount(acc)">
              <span class="acc-role">{{ acc.role }}</span>
              <span class="acc-user">{{ acc.user }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../stores/user'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const testAccounts = [
  { role: '管理员', user: 'admin' },
  { role: '医生', user: 'doctor01' },
  { role: '收费员', user: 'cashier01' },
  { role: '药剂师', user: 'pharmacist01' }
]

const fillAccount = (acc) => {
  loginForm.username = acc.user
  loginForm.password = '123456'
}

const handleLogin = async () => {
  const valid = await loginFormRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await userStore.login(loginForm)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error('登录失败:', error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
}
.login-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.bg-shape {
  position: absolute;
  border-radius: 50%;
  opacity: 0.1;
}
.shape1 { width: 400px; height: 400px; background: #fff; top: -100px; left: -100px; }
.shape2 { width: 300px; height: 300px; background: #fff; bottom: -50px; right: -50px; }
.shape3 { width: 200px; height: 200px; background: #fff; top: 50%; left: 60%; }
.login-card {
  width: 420px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  position: relative;
  z-index: 1;
  backdrop-filter: blur(10px);
}
.login-header {
  text-align: center;
  margin-bottom: 32px;
}
.logo-icon {
  font-size: 48px;
  margin-bottom: 12px;
}
.login-header h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 4px;
}
.login-header p {
  color: #999;
  font-size: 14px;
}
.login-form {
  margin-bottom: 20px;
}
.login-footer {
  border-top: 1px solid #eee;
  padding-top: 16px;
}
.test-accounts {
  text-align: center;
}
.test-accounts .title {
  font-size: 12px;
  color: #999;
  margin-bottom: 10px;
}
.accounts-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
}
.account-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 12px;
  background: #f5f7fa;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
}
.account-item:hover {
  background: #ecf5ff;
  color: #409eff;
}
.acc-role { font-size: 12px; color: #666; }
.acc-user { font-size: 12px; color: #333; font-weight: 500; }
</style>
