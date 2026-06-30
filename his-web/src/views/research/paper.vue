<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>论文管理</span>
          <el-button type="success" @click="showDialog = true">新增论文</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="title" label="论文题目" min-width="250" />
        <el-table-column prop="firstAuthorName" label="第一作者" width="100" />
        <el-table-column prop="journal" label="期刊" width="180" />
        <el-table-column prop="level" label="级别" width="80">
          <template #default="{ row }">
            <el-tag :type="row.level==='SCI'?'danger':row.level==='EI'?'warning':''">{{ row.level }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="impactFactor" label="影响因子" width="100" />
        <el-table-column prop="publishDate" label="发表日期" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">{{ {1:'投稿中',2:'已录用',3:'已发表'}[row.status] }}</template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增论文" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="论文题目" required><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="form.authors" /></el-form-item>
        <el-form-item label="期刊"><el-input v-model="form.journal" /></el-form-item>
        <el-form-item label="级别">
          <el-select v-model="form.level"><el-option label="SCI" value="SCI" /><el-option label="EI" value="EI" /><el-option label="核心期刊" value="CORE" /><el-option label="普通" value="GENERAL" /></el-select>
        </el-form-item>
        <el-form-item label="影响因子"><el-input-number v-model="form.impactFactor" :min="0" :precision="3" /></el-form-item>
        <el-form-item label="发表日期"><el-date-picker v-model="form.publishDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
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
const form = reactive({ title: '', authors: '', journal: '', level: 'CORE', impactFactor: 0, publishDate: '' })

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/research/papers'); list.value = res.data }

const handleSubmit = async () => {
  await request.post('/research/papers', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
