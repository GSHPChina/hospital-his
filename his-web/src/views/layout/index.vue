<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '220px'" class="layout-aside">
      <div class="logo" @click="$router.push('/dashboard')">
        <span class="logo-icon">🏥</span>
        <span v-if="!isCollapse" class="logo-text">HIS系统</span>
      </div>
      <el-scrollbar>
        <el-menu
          :default-active="currentPath"
          :collapse="isCollapse"
          router
          background-color="#1e293b"
          text-color="#94a3b8"
          active-text-color="#fff"
          :collapse-transition="false"
        >
          <el-menu-item index="/dashboard">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
          </el-menu-item>

          <el-sub-menu index="outpatient">
            <template #title>
              <el-icon><FirstAidKit /></el-icon>
              <span>门诊管理</span>
            </template>
            <el-menu-item index="/outpatient/register">门诊挂号</el-menu-item>
            <el-menu-item index="/outpatient/appointment">预约挂号</el-menu-item>
            <el-menu-item index="/outpatient/queue">排队叫号</el-menu-item>
            <el-menu-item index="/outpatient/records">挂号记录</el-menu-item>
            <el-menu-item index="/outpatient/patient">患者管理</el-menu-item>
            <el-menu-item index="/outpatient/schedule">医生排班</el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/doctor/workstation">
            <el-icon><Monitor /></el-icon>
            <span>医生工作站</span>
          </el-menu-item>

          <el-sub-menu index="charge">
            <template #title>
              <el-icon><Wallet /></el-icon>
              <span>门诊收费</span>
            </template>
            <el-menu-item index="/charge/list">门诊收费</el-menu-item>
            <el-menu-item index="/charge/records">收费记录</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="pharmacy">
            <template #title>
              <el-icon><Box /></el-icon>
              <span>药房管理</span>
            </template>
            <el-menu-item index="/pharmacy/dispensing">药房发药</el-menu-item>
            <el-menu-item index="/pharmacy/drug">药品管理</el-menu-item>
            <el-menu-item index="/pharmacy/stock">库存管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="emr">
            <template #title>
              <el-icon><Notebook /></el-icon>
              <span>电子病历</span>
            </template>
            <el-menu-item index="/emr/record">病历管理</el-menu-item>
            <el-menu-item index="/emr/template">病历模板</el-menu-item>
            <el-menu-item index="/emr/quality">病历质控</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="finance">
            <template #title>
              <el-icon><Money /></el-icon>
              <span>财务系统</span>
            </template>
            <el-menu-item index="/finance/income">收入管理</el-menu-item>
            <el-menu-item index="/finance/expense">支出管理</el-menu-item>
            <el-menu-item index="/finance/report">财务报表</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="hr">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>人事系统</span>
            </template>
            <el-menu-item index="/hr/employee">员工档案</el-menu-item>
            <el-menu-item index="/hr/attendance">考勤管理</el-menu-item>
            <el-menu-item index="/hr/salary">薪资管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="equipment">
            <template #title>
              <el-icon><Cpu /></el-icon>
              <span>设备管理</span>
            </template>
            <el-menu-item index="/equipment/list">设备台账</el-menu-item>
            <el-menu-item index="/equipment/maintenance">维修保养</el-menu-item>
            <el-menu-item index="/equipment/depreciation">设备折旧</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="performance">
            <template #title>
              <el-icon><Trophy /></el-icon>
              <span>绩效考核</span>
            </template>
            <el-menu-item index="/performance/template">考核模板</el-menu-item>
            <el-menu-item index="/performance/evaluate">绩效考核</el-menu-item>
            <el-menu-item index="/performance/result">考核结果</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="title">
            <template #title>
              <el-icon><Medal /></el-icon>
              <span>职称评价</span>
            </template>
            <el-menu-item index="/title/apply">职称申请</el-menu-item>
            <el-menu-item index="/title/review">职称评审</el-menu-item>
            <el-menu-item index="/title/manage">职称管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="research">
            <template #title>
              <el-icon><DataBoard /></el-icon>
              <span>科研管理</span>
            </template>
            <el-menu-item index="/research/project">课题管理</el-menu-item>
            <el-menu-item index="/research/paper">论文管理</el-menu-item>
            <el-menu-item index="/research/achievement">成果管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="adverse">
            <template #title>
              <el-icon><Warning /></el-icon>
              <span>不良事件</span>
            </template>
            <el-menu-item index="/adverse/report">事件上报</el-menu-item>
            <el-menu-item index="/adverse/handle">事件处理</el-menu-item>
            <el-menu-item index="/adverse/statistics">事件统计</el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/bigscreen/dashboard">
            <el-icon><DataBoard /></el-icon>
            <span>院长驾驶舱</span>
          </el-menu-item>

          <el-sub-menu index="system">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/system/user">用户管理</el-menu-item>
            <el-menu-item index="/system/role">角色管理</el-menu-item>
            <el-menu-item index="/system/dept">科室管理</el-menu-item>
            <el-menu-item index="/system/dict">字典管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <!-- 顶栏 -->
      <el-header class="layout-header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" style="background: #409eff;">
                {{ userStore.userInfo?.realName?.charAt(0) || 'U' }}
              </el-avatar>
              <span class="user-name">{{ userStore.userInfo?.realName || '用户' }}</span>
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容 -->
      <el-main class="layout-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)

const currentPath = computed(() => route.path)
const currentTitle = computed(() => route.meta.title || '首页')

onMounted(async () => {
  try {
    await userStore.fetchUserInfo()
  } catch (e) {
    console.error('获取用户信息失败:', e)
  }
})

const handleCommand = async (command) => {
  if (command === 'logout') {
    await userStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.layout-aside {
  background-color: #1e293b;
  transition: width 0.3s;
  overflow: hidden;
}
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background: #0f172a;
  cursor: pointer;
}
.logo-icon {
  font-size: 24px;
}
.logo-text {
  color: #fff;
  font-size: 18px;
  font-weight: bold;
}
.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  padding: 0 20px;
}
.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}
.collapse-btn {
  font-size: 20px;
  cursor: pointer;
  color: #666;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}
.user-name {
  font-size: 14px;
  color: #333;
}
.layout-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
