<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>考勤管理</span>
          <div>
            <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" style="margin-right: 10px;" />
            <el-button type="primary" @click="loadData">查询</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="empName" label="员工" width="100" />
        <el-table-column prop="attendanceDate" label="日期" width="120" />
        <el-table-column prop="checkInTime" label="签到时间" width="180" />
        <el-table-column prop="checkOutTime" label="签退时间" width="180" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'success':row.status===2?'warning':row.status===3?'warning':'danger'">
              {{ {1:'正常',2:'迟到',3:'早退',4:'缺勤',5:'请假',6:'出差'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const list = ref([])
const date = ref('')

onMounted(() => loadData())

const loadData = async () => {
  const params = {}
  if (date.value) params.date = date.value
  const res = await request.get('/hr/attendance', { params })
  list.value = res.data
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
