<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>角色管理</span>
          <el-button type="success" @click="showDialog = true">新增角色</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="name" label="角色名称" width="150" />
        <el-table-column prop="code" label="角色编码" width="150" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editRole(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑角色' : '新增角色'" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="角色名称" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="角色编码" required>
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRoles, addRole, updateRole, deleteRole } from '../../api/system'

const list = ref([])
const showDialog = ref(false)

const form = reactive({
  id: null,
  name: '',
  code: '',
  description: ''
})

onMounted(() => {
  loadData()
})

const loadData = async () => {
  const res = await getRoles()
  list.value = res.data
}

const editRole = (row) => {
  Object.assign(form, row)
  showDialog.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' })
  await deleteRole(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSubmit = async () => {
  if (!form.name || !form.code) {
    ElMessage.warning('请填写必填项')
    return
  }
  if (form.id) {
    await updateRole(form.id, form)
  } else {
    await addRole(form)
  }
  ElMessage.success('操作成功')
  showDialog.value = false
  Object.assign(form, { id: null, name: '', code: '', description: '' })
  loadData()
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
