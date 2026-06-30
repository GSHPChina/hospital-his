<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>薪资管理</span>
          <div>
            <el-date-picker v-model="month" type="month" placeholder="选择月份" value-format="YYYY-MM" style="margin-right: 10px;" />
            <el-button type="primary" @click="loadData">查询</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="empName" label="员工" width="100" />
        <el-table-column prop="ym" label="月份" width="100" />
        <el-table-column prop="baseSalary" label="基本工资" width="100" />
        <el-table-column prop="positionSalary" label="岗位工资" width="100" />
        <el-table-column prop="bonus" label="奖金" width="80" />
        <el-table-column prop="allowance" label="津贴" width="80" />
        <el-table-column prop="deduction" label="扣款" width="80" />
        <el-table-column prop="socialInsurance" label="社保" width="80" />
        <el-table-column prop="tax" label="个税" width="80" />
        <el-table-column prop="netSalary" label="实发工资" width="120">
          <template #default="{ row }"><span style="color: #67c23a; font-weight: bold;">¥{{ row.netSalary }}</span></template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'warning':'success'">{{ row.status===1?'待发放':'已发放' }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const list = ref([])
const month = ref('')

onMounted(() => {
  const now = new Date()
  month.value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`
  loadData()
})

const loadData = async () => {
  const res = await request.get('/hr/salary', { params: { ym: month.value } })
  list.value = res.data
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
