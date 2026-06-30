<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>病历模板</span>
          <el-button type="success" @click="showDialog = true">新增模板</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="name" label="模板名称" width="200" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'启用':'停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewContent(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增模板" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.category" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
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
const form = reactive({ name: '', category: '', content: '' })

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/emr/templates'); list.value = res.data || [] }

const viewContent = (row) => { ElMessage.info('模板内容: ' + (row.content || '无')) }

const handleSubmit = async () => {
  await request.post('/emr/templates', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
