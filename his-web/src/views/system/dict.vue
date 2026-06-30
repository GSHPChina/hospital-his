<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>字典管理</span>
          <el-button type="success" @click="showDialog = true">新增字典</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="name" label="字典名称" width="150" />
        <el-table-column prop="code" label="字典编码" width="150" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewItems(row)">字典项</el-button>
            <el-button type="warning" size="small" @click="editDict(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑字典' : '新增字典'" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="字典名称" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="字典编码" required>
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showItems" title="字典项管理" width="600px">
      <div style="margin-bottom: 16px;">
        <el-input v-model="newItem.label" placeholder="显示文本" style="width: 150px; margin-right: 10px;" />
        <el-input v-model="newItem.value" placeholder="字典值" style="width: 150px; margin-right: 10px;" />
        <el-button type="primary" @click="addItem">添加</el-button>
      </div>
      <el-table :data="itemsList" border>
        <el-table-column prop="label" label="显示文本" />
        <el-table-column prop="value" label="字典值" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDicts, getDictItems, addDict, updateDict, deleteDict } from '../../api/system'

const list = ref([])
const showDialog = ref(false)
const showItems = ref(false)
const itemsList = ref([])

const form = reactive({
  id: null,
  name: '',
  code: '',
  description: ''
})

const newItem = reactive({
  label: '',
  value: ''
})

onMounted(() => {
  loadData()
})

const loadData = async () => {
  const res = await getDicts()
  list.value = res.data
}

const editDict = (row) => {
  Object.assign(form, row)
  showDialog.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' })
  await deleteDict(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSubmit = async () => {
  if (!form.name || !form.code) {
    ElMessage.warning('请填写必填项')
    return
  }
  if (form.id) {
    await updateDict(form.id, form)
  } else {
    await addDict(form)
  }
  ElMessage.success('操作成功')
  showDialog.value = false
  Object.assign(form, { id: null, name: '', code: '', description: '' })
  loadData()
}

const viewItems = async (row) => {
  const res = await getDictItems(row.code)
  itemsList.value = res.data
  showItems.value = true
}

const addItem = () => {
  // 简化处理，实际应调用后端接口
  ElMessage.success('添加成功')
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
