<template>
  <div class="page-container">
    <el-card>
      <template #header><span>事件统计</span></template>
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6"><el-statistic title="总事件数" :value="totalEvents" /></el-col>
        <el-col :span="6"><el-statistic title="警告事件" :value="levelCount[1]" value-style="color: #f56c6c" /></el-col>
        <el-col :span="6"><el-statistic title="不良后果" :value="levelCount[2]" value-style="color: #e6a23c" /></el-col>
        <el-col :span="6"><el-statistic title="已处理" :value="statusCount[3]" value-style="color: #67c23a" /></el-col>
      </el-row>

      <el-divider>按类型统计</el-divider>
      <el-table :data="typeStats" stripe border>
        <el-table-column prop="type" label="事件类型" width="150">
          <template #default="{ row }">{{ typeMap[row.type] || row.type }}</template>
        </el-table-column>
        <el-table-column prop="count" label="数量" width="100" />
        <el-table-column label="占比" width="200">
          <template #default="{ row }">
            <el-progress :percentage="totalEvents > 0 ? Math.round(row.count / totalEvents * 100) : 0" />
          </template>
        </el-table-column>
      </el-table>

      <el-divider>按等级统计</el-divider>
      <el-table :data="levelStats" stripe border>
        <el-table-column prop="level" label="事件等级" width="150">
          <template #default="{ row }">{{ {1:'警告事件',2:'不良后果',3:'未造成后果',4:'隐患事件'}[row.level] }}</template>
        </el-table-column>
        <el-table-column prop="count" label="数量" width="100" />
        <el-table-column label="占比" width="200">
          <template #default="{ row }">
            <el-progress :percentage="totalEvents > 0 ? Math.round(row.count / totalEvents * 100) : 0" :status="row.level <= 2 ? 'exception' : ''" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import request from '../../utils/request'

const typeStats = ref([])
const levelStats = ref([])
const typeMap = { MEDICAL:'医疗', NURSING:'护理', DRUG:'药品', INFECTION:'感染', FALL:'跌倒', PRESSURE:'压疮', EQUIPMENT:'设备', OTHER:'其他' }

onMounted(async () => {
  const res = await request.get('/adverse/statistics')
  typeStats.value = res.data.typeStats || []
  levelStats.value = res.data.levelStats || []
})

const totalEvents = computed(() => typeStats.value.reduce((s, t) => s + t.count, 0))
const levelCount = computed(() => {
  const c = { 1: 0, 2: 0, 3: 0, 4: 0 }
  levelStats.value.forEach(l => { if (c[l.level] !== undefined) c[l.level] = l.count })
  return c
})
const statusCount = computed(() => ({ 3: 0 })) // simplified
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
