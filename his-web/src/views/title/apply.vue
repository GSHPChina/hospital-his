<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>职称申请</span>
          <el-button type="success" @click="showDialog = true">新建申请</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="appNo" label="申请单号" width="180" />
        <el-table-column prop="empName" label="申请人" width="100" />
        <el-table-column prop="currentTitle" label="当前职称" width="120" />
        <el-table-column prop="applyTitle" label="申请职称" width="120" />
        <el-table-column prop="applyDate" label="申请日期" width="120" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status===6?'success':row.status===7?'danger':'info'">
              {{ {1:'待提交',2:'已提交',3:'科室审核',4:'人事审核',5:'评审委员会',6:'已通过',7:'已驳回'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status===1" type="primary" size="small" @click="submit(row)">提交</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="职称申请" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="当前职称"><el-input v-model="form.currentTitle" /></el-form-item>
        <el-form-item label="申请职称" required>
          <el-select v-model="form.applyTitle">
            <el-option v-for="l in levels" :key="l.id" :label="l.name" :value="l.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="工作年限"><el-input-number v-model="form.workYears" :min="0" /></el-form-item>
        <el-form-item label="学历"><el-select v-model="form.education"><el-option label="博士" value="PhD" /><el-option label="硕士" value="Master" /><el-option label="本科" value="Bachelor" /></el-select></el-form-item>
        <el-form-item label="专业"><el-input v-model="form.major" /></el-form-item>
        <el-form-item label="工作总结"><el-input v-model="form.workSummary" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="主要业绩"><el-input v-model="form.achievements" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="论文著作"><el-input v-model="form.papers" type="textarea" :rows="3" /></el-form-item>
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
const levels = ref([])
const showDialog = ref(false)
const form = reactive({ currentTitle: '', applyTitle: '', workYears: 0, education: '', major: '', workSummary: '', achievements: '', papers: '' })

onMounted(() => { loadData(); loadLevels() })

const loadData = async () => { const res = await request.get('/title/applications'); list.value = res.data }
const loadLevels = async () => { const res = await request.get('/title/levels'); levels.value = res.data }

const handleSubmit = async () => {
  await request.post('/title/applications', form)
  ElMessage.success('申请创建成功')
  showDialog.value = false
  loadData()
}

const submit = async (row) => {
  await request.put(`/title/applications/${row.id}/submit`)
  ElMessage.success('提交成功')
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
