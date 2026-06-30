<template>
  <div class="dashboard">
    <!-- 顶部统计卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="4" v-for="card in statCards" :key="card.label">
        <el-card shadow="hover" class="stat-card" :body-style="{ padding: '20px' }">
          <div class="card-content">
            <div class="card-icon" :style="{ background: card.bg }">
              <el-icon :size="24"><component :is="card.icon" /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-value">{{ card.value }}</div>
              <div class="card-label">{{ card.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-bottom: 20px;">
      <!-- 快捷入口 -->
      <el-col :span="16">
        <el-card>
          <template #header><span>快捷入口</span></template>
          <el-row :gutter="16">
            <el-col :span="4" v-for="item in shortcuts" :key="item.path">
              <div class="shortcut-item" @click="$router.push(item.path)">
                <div class="shortcut-icon" :style="{ background: item.bg }">
                  <el-icon :size="20" color="#fff"><component :is="item.icon" /></el-icon>
                </div>
                <span>{{ item.label }}</span>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <!-- 待办事项 -->
      <el-col :span="8">
        <el-card>
          <template #header><span>待办事项</span></template>
          <div class="todo-list">
            <div class="todo-item" v-for="todo in todos" :key="todo.label">
              <el-badge :value="todo.count" :type="todo.type">
                <span>{{ todo.label }}</span>
              </el-badge>
              <el-button type="primary" link size="small" @click="$router.push(todo.path)">处理</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <!-- 最近挂号 -->
      <el-col :span="8">
        <el-card>
          <template #header><span>最近挂号</span></template>
          <el-table :data="recentRegisters" stripe size="small" max-height="300">
            <el-table-column prop="patientName" label="患者" width="80" />
            <el-table-column prop="deptName" label="科室" width="100" />
            <el-table-column prop="registerTime" label="时间" width="140">
              <template #default="{ row }">{{ formatTime(row.registerTime) }}</template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <!-- 库存预警 -->
      <el-col :span="8">
        <el-card>
          <template #header><span style="color: #e6a23c;">⚠️ 库存预警</span></template>
          <div v-for="item in stockWarnings" :key="item.drugName" class="warning-item">
            <span class="warning-name">{{ item.drugName }}</span>
            <el-progress :percentage="Math.round(item.quantity / item.safeStock * 100)" :stroke-width="12"
              :status="item.quantity < item.safeStock ? 'exception' : ''" :format="() => item.quantity" />
          </div>
          <el-empty v-if="stockWarnings.length === 0" description="暂无预警" :image-size="60" />
        </el-card>
      </el-col>
      <!-- 设备状态 -->
      <el-col :span="8">
        <el-card>
          <template #header><span>设备状态</span></template>
          <div class="equip-stats">
            <div class="equip-row" v-for="item in equipStats" :key="item.label">
              <span>{{ item.label }}</span>
              <el-tag :type="item.type" size="large">{{ item.count }}</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const statCards = ref([
  { label: '今日挂号', value: 0, icon: 'User', bg: '#409eff' },
  { label: '今日收入', value: '¥0', icon: 'Money', bg: '#67c23a' },
  { label: '待诊患者', value: 0, icon: 'Clock', bg: '#e6a23c' },
  { label: '待收费', value: 0, icon: 'Wallet', bg: '#f56c6c' },
  { label: '待发药', value: 0, icon: 'Box', bg: '#909399' },
  { label: '不良事件', value: 0, icon: 'Warning', bg: '#b37feb' }
])

const shortcuts = [
  { label: '门诊挂号', path: '/outpatient/register', icon: 'EditPen', bg: '#409eff' },
  { label: '预约挂号', path: '/outpatient/appointment', icon: 'Calendar', bg: '#67c23a' },
  { label: '医生工作站', path: '/doctor/workstation', icon: 'Monitor', bg: '#e6a23c' },
  { label: '门诊收费', path: '/charge/list', icon: 'Wallet', bg: '#f56c6c' },
  { label: '药房发药', path: '/pharmacy/dispensing', icon: 'TakeawayBox', bg: '#909399' },
  { label: '电子病历', path: '/emr/record', icon: 'Notebook', bg: '#b37feb' },
  { label: '排队叫号', path: '/outpatient/queue', icon: 'Bell', bg: '#36cfc9' },
  { label: '院长驾驶舱', path: '/bigscreen/dashboard', icon: 'DataBoard', bg: '#ff85c0' }
]

const todos = ref([
  { label: '待诊患者', count: 0, type: 'primary', path: '/doctor/workstation' },
  { label: '待收费处方', count: 0, type: 'warning', path: '/charge/list' },
  { label: '待发药', count: 0, type: 'danger', path: '/pharmacy/dispensing' },
  { label: '待审批支出', count: 0, type: 'info', path: '/finance/expense' }
])

const recentRegisters = ref([])
const stockWarnings = ref([])
const equipStats = ref([
  { label: '正常设备', count: 0, type: 'success' },
  { label: '维修中', count: 0, type: 'warning' },
  { label: '闲置设备', count: 0, type: 'info' },
  { label: '已报废', count: 0, type: 'danger' }
])

const formatTime = (t) => t ? t.replace('T', ' ').substring(5, 16) : ''

onMounted(async () => {
  try {
    // 挂号数据
    const regRes = await request.get('/outpatient/registers')
    const regs = regRes.data || []
    statCards.value[0].value = regs.length
    recentRegisters.value = regs.slice(0, 8)
    todos.value[0].count = regs.filter(r => r.status === 1).length

    // 收入数据
    const incRes = await request.get('/finance/incomes')
    const income = (incRes.data || []).reduce((s, i) => s + Number(i.amount), 0)
    statCards.value[1].value = '¥' + income.toLocaleString()

    // 处方数据
    const rxRes = await request.get('/doctor/prescriptions')
    statCards.value[2].count = (rxRes.data || []).filter(r => r.status === 1).length

    // 收费数据
    const feeRes = await request.get('/charge/fees/pending')
    statCards.value[3].value = (feeRes.data || []).length
    todos.value[1].count = (feeRes.data || []).length

    // 发药数据
    const dispRes = await request.get('/pharmacy/dispensings/pending')
    statCards.value[4].value = (dispRes.data || []).length
    todos.value[2].count = (dispRes.data || []).length

    // 不良事件
    const aeRes = await request.get('/adverse/events')
    statCards.value[5].value = (aeRes.data || []).length

    // 库存预警
    const stockRes = await request.get('/pharmacy/stocks/warnings')
    stockWarnings.value = stockRes.data || []

    // 设备统计
    const eqRes = await request.get('/equipment/equipments')
    const eqs = eqRes.data || []
    equipStats.value[0].count = eqs.filter(e => e.status === 1).length
    equipStats.value[1].count = eqs.filter(e => e.status === 2).length
    equipStats.value[2].count = eqs.filter(e => e.status === 4).length
    equipStats.value[3].count = eqs.filter(e => e.status === 3).length
  } catch (e) {
    console.error('Dashboard error:', e)
  }
})
</script>

<style scoped>
.dashboard { padding: 0; }
.stat-card { border-radius: 8px; }
.card-content { display: flex; align-items: center; gap: 12px; }
.card-icon { width: 48px; height: 48px; border-radius: 10px; display: flex; align-items: center; justify-content: center; color: #fff; }
.card-value { font-size: 20px; font-weight: bold; color: #333; }
.card-label { font-size: 12px; color: #999; margin-top: 2px; }
.shortcut-item { display: flex; flex-direction: column; align-items: center; padding: 12px 4px; cursor: pointer; border-radius: 8px; transition: all 0.2s; }
.shortcut-item:hover { background: #f5f7fa; transform: translateY(-2px); }
.shortcut-icon { width: 40px; height: 40px; border-radius: 10px; display: flex; align-items: center; justify-content: center; margin-bottom: 6px; }
.shortcut-item span { font-size: 12px; color: #666; }
.todo-list { display: flex; flex-direction: column; gap: 12px; }
.todo-item { display: flex; justify-content: space-between; align-items: center; padding: 8px 0; border-bottom: 1px solid #f0f0f0; }
.warning-item { margin-bottom: 12px; }
.warning-name { font-size: 13px; margin-bottom: 4px; display: block; }
.equip-stats { display: flex; flex-direction: column; gap: 12px; }
.equip-row { display: flex; justify-content: space-between; align-items: center; padding: 6px 0; border-bottom: 1px solid #f0f0f0; }
</style>
