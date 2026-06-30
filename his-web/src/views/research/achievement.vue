<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>成果管理</span>
          <el-button type="success" @click="showDialog = true">新增成果</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="name" label="成果名称" min-width="200" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">{{ {PATENT:'专利',AWARD:'获奖',TECHNOLOGY:'技术转化'}[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="holderName" label="持有人" width="100" />
        <el-table-column prop="obtainDate" label="获得日期" width="120" />
        <el-table-column prop="certificateNo" label="证书编号" width="150" />
        <el-table-column prop="description" label="说明" />
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增成果" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type"><el-option label="专利" value="PATENT" /><el-option label="获奖" value="AWARD" /><el-option label="技术转化" value="TECHNOLOGY" /></el-select>
        </el-form-item>
        <el-form-item label="获得日期"><el-date-picker v-model="form.obtainDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="证书编号"><el-input v-model="form.certificateNo" /></el-form-item>
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
const showDialog = ref(false)
const form = reactive({ name: '', type: 'PATENT', obtainDate: '', certificateNo: '', description: '' })

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/research/achievements'); list.value = res.data }

const handleSubmit = async () => {
  await request.post('/research/achievements', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
