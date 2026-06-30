<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>挂号记录</span>
          <el-select v-model="statusFilter" placeholder="状态筛选" clearable style="width: 120px">
            <el-option label="已挂号" :value="1" />
            <el-option label="已就诊" :value="2" />
            <el-option label="已退号" :value="3" />
          </el-select>
        </div>
      </template>
      <el-table :data="filteredList" stripe border>
        <el-table-column prop="registerNo" label="挂号单号" width="200" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="deptName" label="挂号科室" width="120" />
        <el-table-column prop="doctorName" label="挂号医生" width="100" />
        <el-table-column prop="registerType" label="挂号类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.registerType === 'EXPERT' ? 'warning' : row.registerType === 'EMERGENCY' ? 'danger' : 'info'">
              {{ { NORMAL: '普通号', EXPERT: '专家号', EMERGENCY: '急诊号' }[row.registerType] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fee" label="挂号费" width="80">
          <template #default="{ row }">¥{{ row.fee }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'info' : 'danger'">
              {{ { 1: '已挂号', 2: '已就诊', 3: '已退号' }[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="registerTime" label="挂号时间" width="180" />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 1" type="danger" size="small" @click="handleCancel(row)">退号</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRegisters, cancelRegister } from '../../api/register'

const list = ref([])
const statusFilter = ref(null)

const filteredList = computed(() => {
  if (statusFilter.value === null) return list.value
  return list.value.filter(item => item.status === statusFilter.value)
})

onMounted(() => {
  loadData()
})

const loadData = async () => {
  const res = await getRegisters()
  list.value = res.data
}

const handleCancel = async (row) => {
  await ElMessageBox.confirm('确认退号？', '提示', { type: 'warning' })
  await cancelRegister(row.id)
  ElMessage.success('退号成功')
  loadData()
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
