<template>
  <div class="page-container">
    <el-card>
      <template #header><span>职称管理</span></template>
      <el-table :data="levels" stripe border>
        <el-table-column prop="name" label="职称名称" width="200" />
        <el-table-column prop="level" label="级别" width="100">
          <template #default="{ row }">{{ {1:'初级',2:'中级',3:'副高',4:'正高'}[row.level] }}</template>
        </el-table-column>
        <el-table-column prop="description" label="说明" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const levels = ref([])

onMounted(async () => {
  const res = await request.get('/title/levels')
  levels.value = res.data
})
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
