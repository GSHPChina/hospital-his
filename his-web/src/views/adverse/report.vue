<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>不良事件上报</span>
          <el-button type="danger" @click="showDialog = true">上报事件</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="eventNo" label="事件编号" width="140" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">{{ typeMap[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="level" label="等级" width="100">
          <template #default="{ row }">
            <el-tag :type="row.level===1?'danger':row.level===2?'warning':row.level===3?'':'info'">
              {{ {1:'警告事件',2:'不良后果',3:'未造成后果',4:'隐患事件'}[row.level] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="事件标题" min-width="200" />
        <el-table-column prop="eventTime" label="发生时间" width="180" />
        <el-table-column prop="reporterName" label="上报人" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'warning':row.status===2?'':row.status===3?'success':'info'">
              {{ {1:'已上报',2:'调查中',3:'已处理',4:'已关闭'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="上报不良事件" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="事件类型" required>
          <el-select v-model="form.type">
            <el-option label="医疗事件" value="MEDICAL" /><el-option label="护理事件" value="NURSING" />
            <el-option label="药品事件" value="DRUG" /><el-option label="感染事件" value="INFECTION" />
            <el-option label="跌倒事件" value="FALL" /><el-option label="压疮事件" value="PRESSURE" />
            <el-option label="设备事件" value="EQUIPMENT" /><el-option label="其他" value="OTHER" />
          </el-select>
        </el-form-item>
        <el-form-item label="事件等级" required>
          <el-select v-model="form.level">
            <el-option label="警告事件" :value="1" /><el-option label="不良后果事件" :value="2" />
            <el-option label="未造成后果事件" :value="3" /><el-option label="隐患事件" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="事件标题" required><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="事件描述" required><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="发生时间" required><el-date-picker v-model="form.eventTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="发生地点"><el-input v-model="form.eventPlace" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="danger" @click="handleSubmit">上报</el-button>
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
const typeMap = { MEDICAL:'医疗', NURSING:'护理', DRUG:'药品', INFECTION:'感染', FALL:'跌倒', PRESSURE:'压疮', EQUIPMENT:'设备', OTHER:'其他' }
const form = reactive({ type: 'MEDICAL', level: 3, title: '', description: '', eventTime: '', eventPlace: '' })

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/adverse/events'); list.value = res.data }

const handleSubmit = async () => {
  await request.post('/adverse/events', form)
  ElMessage.success('事件上报成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
