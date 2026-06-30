<template>
  <div class="dashboard-container">
    <div class="dashboard-header">
      <h1>🏥 医院运营数据驾驶舱</h1>
      <div class="header-time">{{ currentTime }}</div>
    </div>

    <div class="dashboard-body">
      <!-- 顶部核心指标 -->
      <div class="top-metrics">
        <div class="metric-card" v-for="m in topMetrics" :key="m.label">
          <div class="metric-icon" :style="{background: m.color}"><el-icon :size="28"><component :is="m.icon" /></el-icon></div>
          <div class="metric-info">
            <div class="metric-value">{{ m.value }}</div>
            <div class="metric-label">{{ m.label }}</div>
          </div>
        </div>
      </div>

      <div class="main-content">
        <!-- 左侧 -->
        <div class="left-panel">
          <el-card class="chart-card">
            <template #header><span>本周门诊量趋势</span></template>
            <div class="chart-placeholder">
              <div v-for="(v, i) in weeklyData" :key="i" class="bar-item">
                <div class="bar" :style="{height: v.value * 3 + 'px'}"></div>
                <span>{{ v.day }}</span>
              </div>
            </div>
          </el-card>
          <el-card class="chart-card">
            <template #header><span>科室门诊量排名</span></template>
            <div class="rank-list">
              <div v-for="(d, i) in deptRank" :key="i" class="rank-item">
                <span class="rank-no" :class="i < 3 ? 'top' : ''">{{ i + 1 }}</span>
                <span class="rank-name">{{ d.name }}</span>
                <el-progress :percentage="d.percent" :stroke-width="14" :text-inside="true" />
              </div>
            </div>
          </el-card>
        </div>

        <!-- 中间 -->
        <div class="center-panel">
          <el-card class="chart-card">
            <template #header><span>实时挂号动态</span></template>
            <div class="live-feed">
              <div v-for="item in liveFeed" :key="item.id" class="feed-item">
                <el-tag :type="item.type === 'EXPERT' ? 'warning' : 'info'" size="small">{{ {NORMAL:'普通',EXPERT:'专家',EMERGENCY:'急诊'}[item.type] }}</el-tag>
                <span class="feed-patient">{{ item.patient }}</span>
                <span class="feed-dept">{{ item.dept }}</span>
                <span class="feed-time">{{ item.time }}</span>
              </div>
            </div>
          </el-card>
          <el-card class="chart-card">
            <template #header><span>收入构成</span></template>
            <div class="pie-chart">
              <div v-for="item in incomeComposition" :key="item.label" class="pie-item">
                <div class="pie-bar" :style="{width: item.percent + '%', background: item.color}"></div>
                <span>{{ item.label }}: ¥{{ item.amount }}</span>
              </div>
            </div>
          </el-card>
        </div>

        <!-- 右侧 -->
        <div class="right-panel">
          <el-card class="chart-card">
            <template #header><span>床位使用率</span></template>
            <div class="gauge-chart">
              <div class="gauge-value">{{ bedUsage }}%</div>
              <el-progress :percentage="bedUsage" :stroke-width="20" :color="bedUsage > 90 ? '#f56c6c' : '#67c23a'" />
              <div class="gauge-label">总床位: 500 | 使用: {{ Math.round(500 * bedUsage / 100) }}</div>
            </div>
          </el-card>
          <el-card class="chart-card">
            <template #header><span>今日手术</span></template>
            <div class="surgery-stats">
              <div class="stat-row"><span>计划手术</span><span class="stat-val">28</span></div>
              <div class="stat-row"><span>已完成</span><span class="stat-val success">22</span></div>
              <div class="stat-row"><span>进行中</span><span class="stat-val warning">4</span></div>
              <div class="stat-row"><span>取消</span><span class="stat-val danger">2</span></div>
            </div>
          </el-card>
          <el-card class="chart-card">
            <template #header><span>药品库存预警</span></template>
            <div class="warning-list">
              <div v-for="item in stockWarnings" :key="item.name" class="warning-item">
                <el-icon color="#e6a23c"><Warning /></el-icon>
                <span>{{ item.name }}: 仅剩{{ item.qty }}{{ item.unit }}</span>
              </div>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import request from '../../utils/request'

const currentTime = ref('')
const bedUsage = ref(86)

const topMetrics = ref([
  { label: '今日门诊量', value: '1,256', icon: 'User', color: '#409eff' },
  { label: '今日收入', value: '¥186,520', icon: 'Money', color: '#67c23a' },
  { label: '住院人数', value: '432', icon: 'House', color: '#e6a23c' },
  { label: '手术台次', value: '28', icon: 'Knife', color: '#f56c6c' },
  { label: '药品库存', value: '10,280', icon: 'Box', color: '#909399' }
])

const weeklyData = ref([
  { day: '周一', value: 85 },
  { day: '周二', value: 92 },
  { day: '周三', value: 78 },
  { day: '周四', value: 95 },
  { day: '周五', value: 88 },
  { day: '周六', value: 65 },
  { day: '周日', value: 45 }
])

const deptRank = ref([
  { name: '呼吸内科', percent: 95 },
  { name: '心血管内科', percent: 88 },
  { name: '普通外科', percent: 82 },
  { name: '骨科', percent: 75 },
  { name: '儿科', percent: 68 },
  { name: '眼科', percent: 62 },
  { name: '妇产科', percent: 58 },
  { name: '皮肤科', percent: 52 }
])

const liveFeed = ref([
  { id: 1, patient: '王**', dept: '呼吸内科', type: 'EXPERT', time: '14:32' },
  { id: 2, patient: '李**', dept: '心血管内科', type: 'NORMAL', time: '14:28' },
  { id: 3, patient: '张**', dept: '骨科', type: 'EXPERT', time: '14:25' },
  { id: 4, patient: '刘**', dept: '儿科', type: 'NORMAL', time: '14:20' },
  { id: 5, patient: '陈**', dept: '急诊科', type: 'EMERGENCY', time: '14:15' }
])

const incomeComposition = ref([
  { label: '药品收入', amount: '68,500', percent: 37, color: '#409eff' },
  { label: '检查收入', amount: '52,300', percent: 28, color: '#67c23a' },
  { label: '治疗收入', amount: '38,200', percent: 20, color: '#e6a23c' },
  { label: '挂号收入', amount: '18,520', percent: 10, color: '#f56c6c' },
  { label: '其他', amount: '9,000', percent: 5, color: '#909399' }
])

const stockWarnings = ref([
  { name: '阿莫西林胶囊', qty: 45, unit: '盒' },
  { name: '布洛芬缓释胶囊', qty: 38, unit: '盒' },
  { name: '头孢克洛胶囊', qty: 42, unit: '盒' }
])

let timer = null

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
  loadData()
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' })
}

const loadData = async () => {
  try {
    const regRes = await request.get('/outpatient/registers')
    if (regRes.data) {
      topMetrics.value[0].value = regRes.data.length.toLocaleString()
    }
    const incRes = await request.get('/finance/incomes')
    if (incRes.data) {
      const total = incRes.data.reduce((s, i) => s + Number(i.amount), 0)
      topMetrics.value[1].value = '¥' + total.toLocaleString()
    }
  } catch (e) {
    console.error(e)
  }
}
</script>

<style scoped>
.dashboard-container { min-height: 100vh; background: linear-gradient(135deg, #0c1426 0%, #1a2a4a 100%); color: #fff; padding: 16px; }
.dashboard-header { display: flex; justify-content: space-between; align-items: center; padding: 10px 20px; background: rgba(255,255,255,0.05); border-radius: 8px; margin-bottom: 16px; }
.dashboard-header h1 { font-size: 24px; background: linear-gradient(90deg, #409eff, #67c23a); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }
.header-time { font-size: 16px; color: #8cc8ff; }
.top-metrics { display: flex; gap: 16px; margin-bottom: 16px; }
.metric-card { flex: 1; display: flex; align-items: center; gap: 12px; padding: 16px; background: rgba(255,255,255,0.08); border-radius: 8px; }
.metric-icon { width: 48px; height: 48px; border-radius: 10px; display: flex; align-items: center; justify-content: center; color: #fff; }
.metric-value { font-size: 22px; font-weight: bold; }
.metric-label { font-size: 12px; color: #8cc8ff; }
.main-content { display: flex; gap: 16px; }
.left-panel, .right-panel { width: 28%; display: flex; flex-direction: column; gap: 16px; }
.center-panel { flex: 1; display: flex; flex-direction: column; gap: 16px; }
.chart-card { background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.1); }
.chart-card :deep(.el-card__header) { border-bottom: 1px solid rgba(255,255,255,0.1); padding: 10px 16px; }
.chart-card :deep(.el-card__body) { padding: 12px; }
.chart-placeholder { display: flex; align-items: flex-end; justify-content: space-around; height: 150px; padding-top: 20px; }
.bar-item { display: flex; flex-direction: column; align-items: center; gap: 4px; }
.bar { width: 30px; background: linear-gradient(180deg, #409eff, #1a6fdb); border-radius: 4px 4px 0 0; transition: height 0.3s; }
.bar-item span { font-size: 12px; color: #8cc8ff; }
.rank-list { display: flex; flex-direction: column; gap: 8px; }
.rank-item { display: flex; align-items: center; gap: 8px; }
.rank-no { width: 20px; height: 20px; border-radius: 50%; background: rgba(255,255,255,0.1); display: flex; align-items: center; justify-content: center; font-size: 12px; }
.rank-no.top { background: #e6a23c; color: #fff; }
.rank-name { width: 80px; font-size: 13px; }
.live-feed { max-height: 200px; overflow-y: auto; }
.feed-item { display: flex; align-items: center; gap: 10px; padding: 8px 0; border-bottom: 1px solid rgba(255,255,255,0.05); }
.feed-patient { flex: 1; font-size: 14px; }
.feed-dept { font-size: 12px; color: #8cc8ff; }
.feed-time { font-size: 12px; color: #666; }
.pie-chart { display: flex; flex-direction: column; gap: 10px; }
.pie-item { display: flex; align-items: center; gap: 10px; }
.pie-bar { height: 20px; border-radius: 4px; min-width: 20px; }
.pie-item span { font-size: 12px; white-space: nowrap; }
.gauge-chart { text-align: center; }
.gauge-value { font-size: 36px; font-weight: bold; color: #67c23a; margin-bottom: 10px; }
.gauge-label { font-size: 12px; color: #8cc8ff; margin-top: 8px; }
.surgery-stats { display: flex; flex-direction: column; gap: 10px; }
.stat-row { display: flex; justify-content: space-between; padding: 6px 0; border-bottom: 1px solid rgba(255,255,255,0.05); font-size: 14px; }
.stat-val { font-weight: bold; }
.stat-val.success { color: #67c23a; }
.stat-val.warning { color: #e6a23c; }
.stat-val.danger { color: #f56c6c; }
.warning-list { display: flex; flex-direction: column; gap: 8px; }
.warning-item { display: flex; align-items: center; gap: 8px; font-size: 13px; }
</style>
