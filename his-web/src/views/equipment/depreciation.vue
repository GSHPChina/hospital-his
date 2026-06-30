<template>
  <div class="page-container">
    <el-card>
      <template #header><span>设备折旧</span></template>
      <el-table :data="equipments" stripe border>
        <el-table-column prop="equipNo" label="设备编号" width="100" />
        <el-table-column prop="name" label="设备名称" width="150" />
        <el-table-column prop="purchasePrice" label="原值" width="120">
          <template #default="{ row }">¥{{ row.purchasePrice }}</template>
        </el-table-column>
        <el-table-column prop="depreciationYears" label="折旧年限" width="80" />
        <el-table-column label="年折旧额" width="120">
          <template #default="{ row }">¥{{ (row.purchasePrice / row.depreciationYears).toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="currentValue" label="净值" width="120">
          <template #default="{ row }">¥{{ row.currentValue }}</template>
        </el-table-column>
        <el-table-column label="折旧率" width="150">
          <template #default="{ row }">
            <el-progress :percentage="Math.round((1 - row.currentValue / row.purchasePrice) * 100)" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const equipments = ref([])

onMounted(async () => {
  const res = await request.get('/equipment/equipments')
  equipments.value = (res.data || []).filter(e => e.status !== 3)
})
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
