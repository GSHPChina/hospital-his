<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>考核模板</span>
          <el-button type="success" @click="showDialog = true">新增模板</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="name" label="模板名称" width="200" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">{{ {DOCTOR:'医生',NURSE:'护士',TECH:'医技',ADMIN:'行政'}[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="year" label="年度" width="80" />
        <el-table-column prop="totalScore" label="总分" width="80" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewIndicators(row)">查看指标</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增模板" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type"><el-option label="医生" value="DOCTOR" /><el-option label="护士" value="NURSE" /><el-option label="医技" value="TECH" /><el-option label="行政" value="ADMIN" /></el-select>
        </el-form-item>
        <el-form-item label="年度" required><el-input-number v-model="form.year" :min="2020" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showIndicators" title="考核指标" width="600px">
      <el-table :data="indicators" border>
        <el-table-column prop="name" label="指标名称" />
        <el-table-column prop="category" label="类别" width="100" />
        <el-table-column prop="score" label="分值" width="80" />
        <el-table-column prop="description" label="评分标准" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const indicators = ref([])
const showDialog = ref(false)
const showIndicators = ref(false)
const form = reactive({ name: '', type: 'DOCTOR', year: 2026 })

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/performance/templates'); list.value = res.data }

const viewIndicators = async (row) => {
  const res = await request.get(`/performance/templates/${row.id}/indicators`)
  indicators.value = res.data
  showIndicators.value = true
}

const handleSubmit = async () => {
  await request.post('/performance/templates', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
