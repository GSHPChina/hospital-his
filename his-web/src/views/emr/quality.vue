<template>
  <div class="page-container">
    <el-card>
      <template #header><span>病历质控</span></template>
      <el-table :data="list" stripe border>
        <el-table-column prop="recordId" label="病历ID" width="100" />
        <el-table-column prop="checkType" label="检查类型" width="100">
          <template #default="{ row }">{{ row.checkType === 'AUTO' ? '自动' : '手动' }}</template>
        </el-table-column>
        <el-table-column prop="score" label="评分" width="80">
          <template #default="{ row }">
            <span :style="{color: row.score >= 90 ? '#67c23a' : row.score >= 70 ? '#e6a23c' : '#f56c6c'}">{{ row.score }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'success':row.status===2?'danger':'warning'">
              {{ {1:'合格',2:'不合格',3:'整改中'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="checkTime" label="检查时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const list = ref([])

onMounted(async () => {
  const res = await request.get('/emr/quality/list')
  list.value = res.data || []
})
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
