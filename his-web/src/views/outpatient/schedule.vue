<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>医生排班</span>
          <div>
            <el-tree-select
              v-model="deptId"
              :data="deptTree"
              :props="{ label: 'name', value: 'id', children: 'children' }"
              placeholder="选择科室"
              check-strictly
              clearable
              filterable
              style="width: 200px; margin-right: 10px;"
            />
            <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" style="margin-right: 10px;" />
            <el-button type="primary" @click="loadData">查询</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="doctorName" label="医生" width="100" />
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="scheduleDate" label="排班日期" width="120" />
        <el-table-column prop="timeSlot" label="时段" width="80">
          <template #default="{ row }">{{ row.timeSlot === 'AM' ? '上午' : '下午' }}</template>
        </el-table-column>
        <el-table-column prop="feeType" label="号源类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.feeType === 'EXPERT' ? 'warning' : row.feeType === 'EMERGENCY' ? 'danger' : 'info'">
              {{ { NORMAL: '普通号', EXPERT: '专家号', EMERGENCY: '急诊号' }[row.feeType] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="号源情况" width="150">
          <template #default="{ row }">
            <el-progress
              :percentage="row.totalNum > 0 ? Math.round(row.usedNum / row.totalNum * 100) : 0"
              :format="() => `${row.usedNum}/${row.totalNum}`"
              :status="row.usedNum >= row.totalNum ? 'exception' : ''"
            />
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '正常' : '停诊' }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getSchedules } from '../../api/register'
import { getDeptTree } from '../../api/system'

const list = ref([])
const deptTree = ref([])
const deptId = ref(null)
const date = ref(null)

onMounted(() => {
  loadData()
  loadDeptTree()
})

const loadData = async () => {
  const res = await getSchedules({ deptId: deptId.value, date: date.value })
  list.value = res.data
}

const loadDeptTree = async () => {
  const res = await getDeptTree()
  deptTree.value = res.data.filter(d => d.type === 1)
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
