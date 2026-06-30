<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>财务报表</span>
          <div>
            <el-date-picker v-model="month" type="month" placeholder="选择月份" value-format="YYYY-MM" style="margin-right: 10px;" />
            <el-button type="primary" @click="generateReport">生成报表</el-button>
          </div>
        </div>
      </template>

      <!-- 核心指标 -->
      <el-row :gutter="20" style="margin-bottom: 24px;">
        <el-col :span="6">
          <el-card shadow="hover" class="metric-card income">
            <div class="metric-label">总收入</div>
            <div class="metric-value">¥{{ formatNum(report.totalIncome) }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="metric-card expense">
            <div class="metric-label">总支出</div>
            <div class="metric-value">¥{{ formatNum(report.totalExpense) }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="metric-card" :class="(report.profit || 0) >= 0 ? 'profit' : 'loss'">
            <div class="metric-label">利润</div>
            <div class="metric-value">¥{{ formatNum(report.profit) }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="metric-card rate">
            <div class="metric-label">利润率</div>
            <div class="metric-value">{{ report.totalIncome ? ((report.profit / report.totalIncome) * 100).toFixed(1) : 0 }}%</div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 收入构成 -->
      <el-row :gutter="20" style="margin-bottom: 24px;">
        <el-col :span="12">
          <el-card>
            <template #header><span>收入构成</span></template>
            <div class="breakdown-list">
              <div class="breakdown-item" v-for="item in incomeItems" :key="item.label">
                <div class="item-header">
                  <span>{{ item.label }}</span>
                  <span class="item-value">¥{{ formatNum(item.value) }}</span>
                </div>
                <el-progress :percentage="item.percent" :color="item.color" :stroke-width="10" />
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <template #header><span>支出构成</span></template>
            <div class="breakdown-list">
              <div class="breakdown-item" v-for="item in expenseItems" :key="item.label">
                <div class="item-header">
                  <span>{{ item.label }}</span>
                  <span class="item-value">¥{{ formatNum(item.value) }}</span>
                </div>
                <el-progress :percentage="item.percent" :color="item.color" :stroke-width="10" />
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 详细数据 -->
      <el-card>
        <template #header><span>详细数据</span></template>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="挂号收入">¥{{ formatNum(report.regIncome) }}</el-descriptions-item>
          <el-descriptions-item label="药品收入">¥{{ formatNum(report.drugIncome) }}</el-descriptions-item>
          <el-descriptions-item label="检查收入">¥{{ formatNum(report.examIncome) }}</el-descriptions-item>
          <el-descriptions-item label="治疗收入">¥{{ formatNum(report.treatmentIncome) }}</el-descriptions-item>
          <el-descriptions-item label="工资支出">¥{{ formatNum(report.salaryExpense) }}</el-descriptions-item>
          <el-descriptions-item label="药品采购">¥{{ formatNum(report.drugPurchase) }}</el-descriptions-item>
          <el-descriptions-item label="设备支出">¥{{ formatNum(report.equipmentExpense) }}</el-descriptions-item>
          <el-descriptions-item label="报表月份">{{ report.ym || '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const month = ref('')
const report = ref({})

const formatNum = (n) => (n || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })

const incomeItems = computed(() => {
  const total = report.value.totalIncome || 1
  return [
    { label: '挂号收入', value: report.value.regIncome || 0, percent: Math.round((report.value.regIncome || 0) / total * 100), color: '#409eff' },
    { label: '药品收入', value: report.value.drugIncome || 0, percent: Math.round((report.value.drugIncome || 0) / total * 100), color: '#67c23a' },
    { label: '检查收入', value: report.value.examIncome || 0, percent: Math.round((report.value.examIncome || 0) / total * 100), color: '#e6a23c' },
    { label: '治疗收入', value: report.value.treatmentIncome || 0, percent: Math.round((report.value.treatmentIncome || 0) / total * 100), color: '#f56c6c' }
  ]
})

const expenseItems = computed(() => {
  const total = report.value.totalExpense || 1
  return [
    { label: '工资支出', value: report.value.salaryExpense || 0, percent: Math.round((report.value.salaryExpense || 0) / total * 100), color: '#409eff' },
    { label: '药品采购', value: report.value.drugPurchase || 0, percent: Math.round((report.value.drugPurchase || 0) / total * 100), color: '#67c23a' },
    { label: '设备支出', value: report.value.equipmentExpense || 0, percent: Math.round((report.value.equipmentExpense || 0) / total * 100), color: '#e6a23c' }
  ]
})

onMounted(() => {
  const now = new Date()
  month.value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`
})

const generateReport = async () => {
  const res = await request.post('/finance/report/generate', null, { params: { ym: month.value } })
  report.value = res.data
  ElMessage.success('报表生成成功')
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.metric-card { text-align: center; }
.metric-card .metric-label { font-size: 14px; color: #999; margin-bottom: 8px; }
.metric-card .metric-value { font-size: 24px; font-weight: bold; }
.metric-card.income .metric-value { color: #67c23a; }
.metric-card.expense .metric-value { color: #f56c6c; }
.metric-card.profit .metric-value { color: #67c23a; }
.metric-card.loss .metric-value { color: #f56c6c; }
.metric-card.rate .metric-value { color: #409eff; }
.breakdown-list { display: flex; flex-direction: column; gap: 16px; }
.item-header { display: flex; justify-content: space-between; margin-bottom: 4px; }
.item-value { font-weight: bold; }
</style>
