<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>课题管理</span>
          <div>
            <el-select v-model="typeFilter" placeholder="类型" clearable style="width: 100px; margin-right: 10px;">
              <el-option label="国家级" value="NATIONAL" /><el-option label="省级" value="PROVINCIAL" /><el-option label="市级" value="MUNICIPAL" /><el-option label="院级" value="HOSPITAL" />
            </el-select>
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增课题</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="projectNo" label="课题编号" width="140" />
        <el-table-column prop="name" label="课题名称" min-width="200" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.type==='NATIONAL'?'danger':row.type==='PROVINCIAL'?'warning':''">
              {{ {NATIONAL:'国家级',PROVINCIAL:'省级',MUNICIPAL:'市级',HOSPITAL:'院级'}[row.type] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="leaderName" label="负责人" width="100" />
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="budget" label="经费" width="100">
          <template #default="{ row }">¥{{ row.budget }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status===3?'success':row.status===4?'':'info'">
              {{ {1:'申报中',2:'已立项',3:'进行中',4:'已结题',5:'已终止'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增课题" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="课题名称" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type"><el-option label="国家级" value="NATIONAL" /><el-option label="省级" value="PROVINCIAL" /><el-option label="市级" value="MUNICIPAL" /><el-option label="院级" value="HOSPITAL" /></el-select>
        </el-form-item>
        <el-form-item label="开始日期"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="结束日期"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="经费预算"><el-input-number v-model="form.budget" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="经费来源"><el-input v-model="form.source" /></el-form-item>
        <el-form-item label="课题简介"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
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
const typeFilter = ref('')
const showDialog = ref(false)
const form = reactive({ name: '', type: 'HOSPITAL', startDate: '', endDate: '', budget: 0, source: '', description: '' })

onMounted(() => loadData())

const loadData = async () => {
  const params = {}
  if (typeFilter.value) params.type = typeFilter.value
  const res = await request.get('/research/projects', { params })
  list.value = res.data
}

const handleSubmit = async () => {
  await request.post('/research/projects', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
