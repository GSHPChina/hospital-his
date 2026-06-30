<template>
  <div class="page-container">
    <el-card>
      <template #header><span>考核结果</span></template>
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6"><el-statistic title="优秀(A)" :value="levelCount.A" /></el-col>
        <el-col :span="6"><el-statistic title="良好(B)" :value="levelCount.B" /></el-col>
        <el-col :span="6"><el-statistic title="合格(C)" :value="levelCount.C" /></el-col>
        <el-col :span="6"><el-statistic title="不合格(D)" :value="levelCount.D" /></el-col>
      </el-row>
      <el-table :data="list" stripe border>
        <el-table-column prop="empName" label="员工" width="120" />
        <el-table-column prop="year" label="年度" width="80" />
        <el-table-column prop="quarter" label="季度" width="80">
          <template #default="{ row }">{{ row.quarter ? `Q${row.quarter}` : '-' }}</template>
        </el-table-column>
        <el-table-column prop="totalScore" label="得分" width="80" />
        <el-table-column prop="level" label="等级" width="80">
          <template #default="{ row }">
            <el-tag :type="row.level==='A'?'success':row.level==='B'?'':row.level==='C'?'warning':'danger'" v-if="row.level">{{ row.level }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import request from '../../utils/request'

const list = ref([])

onMounted(async () => {
  const res = await request.get('/performance/records')
  list.value = res.data
})

const levelCount = computed(() => {
  const c = { A: 0, B: 0, C: 0, D: 0 }
  list.value.forEach(r => { if (r.level && c[r.level] !== undefined) c[r.level]++ })
  return c
})
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
