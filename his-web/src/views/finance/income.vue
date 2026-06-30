<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>收入管理</span>
          <div>
            <el-select v-model="typeFilter" placeholder="收入类型" clearable style="width: 120px; margin-right: 10px;">
              <el-option label="挂号费" value="REGISTRATION" />
              <el-option label="药品费" value="DRUG" />
              <el-option label="检查费" value="EXAM" />
              <el-option label="治疗费" value="TREATMENT" />
            </el-select>
            <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" value-format="YYYY-MM-DD" style="margin-right: 10px;" />
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增收入</el-button>
          </div>
        </div>
      </template>

      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6">
          <el-statistic title="总收入" :prefix="'¥'" :value="totalIncome" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="挂号收入" :prefix="'¥'" :value="regIncome" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="药品收入" :prefix="'¥'" :value="drugIncome" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="其他收入" :prefix="'¥'" :value="otherIncome" />
        </el-col>
      </el-row>

      <el-table :data="list" stripe border>
        <el-table-column prop="incomeNo" label="收入单号" width="180" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ typeMap[row.type] || row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="120">
          <template #default="{ row }">
            <span style="color: #67c23a; font-weight: bold;">¥{{ row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="incomeDate" label="日期" width="120" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增收入" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="类型" required>
          <el-select v-model="form.type">
            <el-option label="挂号费" value="REGISTRATION" />
            <el-option label="药品费" value="DRUG" />
            <el-option label="检查费" value="EXAM" />
            <el-option label="治疗费" value="TREATMENT" />
          </el-select>
        </el-form-item>
        <el-form-item label="金额" required>
          <el-input-number v-model="form.amount" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="日期" required>
          <el-date-picker v-model="form.incomeDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const typeFilter = ref('')
const dateRange = ref(null)
const showDialog = ref(false)

const typeMap = { REGISTRATION: '挂号费', DRUG: '药品费', EXAM: '检查费', TREATMENT: '治疗费', BED: '床位费' }

const form = reactive({ type: 'REGISTRATION', amount: 0, incomeDate: '', remark: '' })

const totalIncome = computed(() => list.value.reduce((s, i) => s + Number(i.amount), 0))
const regIncome = computed(() => list.value.filter(i => i.type === 'REGISTRATION').reduce((s, i) => s + Number(i.amount), 0))
const drugIncome = computed(() => list.value.filter(i => i.type === 'DRUG').reduce((s, i) => s + Number(i.amount), 0))
const otherIncome = computed(() => totalIncome.value - regIncome.value - drugIncome.value)

onMounted(() => loadData())

const loadData = async () => {
  const params = {}
  if (typeFilter.value) params.type = typeFilter.value
  const res = await request.get('/finance/incomes', { params })
  list.value = res.data
}

const handleSubmit = async () => {
  await request.post('/finance/incomes', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
