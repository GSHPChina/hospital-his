<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>支出管理</span>
          <div>
            <el-select v-model="statusFilter" placeholder="状态" clearable style="width: 100px; margin-right: 10px;">
              <el-option label="待审批" :value="1" />
              <el-option label="已审批" :value="2" />
              <el-option label="已支付" :value="3" />
            </el-select>
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增支出</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="expenseNo" label="支出单号" width="180" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">{{ typeMap[row.type] || row.type }}</template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="120">
          <template #default="{ row }"><span style="color: #f56c6c;">¥{{ row.amount }}</span></template>
        </el-table-column>
        <el-table-column prop="expenseDate" label="日期" width="120" />
        <el-table-column prop="description" label="说明" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'warning':row.status===2?'':row.status===3?'success':'danger'">
              {{ {1:'待审批',2:'已审批',3:'已支付',4:'已驳回'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status===1" type="success" size="small" @click="approve(row)">审批</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增支出" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="类型" required>
          <el-select v-model="form.type">
            <el-option label="工资" value="SALARY" />
            <el-option label="药品采购" value="DRUG_PURCHASE" />
            <el-option label="设备" value="EQUIPMENT" />
            <el-option label="其他" value="OTHER" />
          </el-select>
        </el-form-item>
        <el-form-item label="金额" required><el-input-number v-model="form.amount" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="日期" required><el-date-picker v-model="form.expenseDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="说明"><el-input v-model="form.description" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const statusFilter = ref(null)
const showDialog = ref(false)
const typeMap = { SALARY: '工资', DRUG_PURCHASE: '药品采购', EQUIPMENT: '设备', OTHER: '其他' }
const form = reactive({ type: 'OTHER', amount: 0, expenseDate: '', description: '' })

onMounted(() => loadData())

const loadData = async () => {
  const params = {}
  if (statusFilter.value) params.status = statusFilter.value
  const res = await request.get('/finance/expenses', { params })
  list.value = res.data
}

const handleSubmit = async () => {
  await request.post('/finance/expenses', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}

const approve = async (row) => {
  await request.put(`/finance/expenses/${row.id}/approve`)
  ElMessage.success('审批成功')
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
