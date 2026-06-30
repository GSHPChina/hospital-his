<template>
  <div class="page-container">
    <el-card>
      <template #header><span>事件处理</span></template>
      <el-table :data="list" stripe border>
        <el-table-column prop="eventNo" label="事件编号" width="140" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">{{ {MEDICAL:'医疗',NURSING:'护理',DRUG:'药品',FALL:'跌倒'}[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="level" label="等级" width="100">
          <template #default="{ row }">
            <el-tag :type="row.level<=2?'danger':'info'">{{ {1:'警告',2:'不良后果',3:'未造成后果',4:'隐患'}[row.level] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="事件标题" min-width="200" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'warning':row.status===2?'':row.status===3?'success':'info'">
              {{ {1:'已上报',2:'调查中',3:'已处理',4:'已关闭'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status < 3" type="primary" size="small" @click="showHandle(row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="事件处理" width="600px">
      <el-form :model="handleForm" label-width="100px">
        <el-form-item label="处理结果" required><el-input v-model="handleForm.handleResult" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="原因分析"><el-input v-model="handleForm.causeAnalysis" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="改进措施"><el-input v-model="handleForm.improvement" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitHandle">提交处理</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const showDialog = ref(false)
const currentId = ref(null)
const handleForm = reactive({ handleResult: '', causeAnalysis: '', improvement: '' })

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/adverse/events'); list.value = res.data }

const showHandle = (row) => {
  currentId.value = row.id
  handleForm.handleResult = ''
  handleForm.causeAnalysis = ''
  handleForm.improvement = ''
  showDialog.value = true
}

const submitHandle = async () => {
  await request.put(`/adverse/events/${currentId.value}/handle`, handleForm)
  ElMessage.success('处理完成')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
