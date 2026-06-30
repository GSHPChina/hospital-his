<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>科室管理</span>
          <el-button type="success" @click="showDialog = true">新增科室</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border row-key="id" :tree-props="{ children: 'children' }" default-expand-all>
        <el-table-column prop="name" label="科室名称" width="250" />
        <el-table-column prop="code" label="科室编码" width="100" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.type === 1 ? '' : row.type === 3 ? 'warning' : 'info'">
              {{ { 1: '临床科室', 2: '住院科室', 3: '医技科室', 4: '行政后勤' }[row.type] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editDept(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑科室' : '新增科室'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="上级科室">
          <el-tree-select
            v-model="form.parentId"
            :data="[{ id: 0, name: '顶级科室', children: list }]"
            :props="{ label: 'name', value: 'id', children: 'children' }"
            placeholder="无"
            check-strictly
            clearable
            filterable
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="科室名称" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="科室编码">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="科室类型">
          <el-select v-model="form.type" style="width: 100%">
            <el-option label="临床科室" :value="1" />
            <el-option label="住院科室" :value="2" />
            <el-option label="医技科室" :value="3" />
            <el-option label="行政后勤" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortOrder" :min="0" />
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
import { getDeptTree, addDept, updateDept, deleteDept } from '../../api/system'

const list = ref([])
const showDialog = ref(false)

const form = reactive({
  id: null,
  parentId: 0,
  name: '',
  code: '',
  type: 1,
  sortOrder: 0
})

onMounted(() => {
  loadData()
})

const loadData = async () => {
  const res = await getDeptTree()
  list.value = res.data
}

const editDept = (row) => {
  Object.assign(form, row)
  showDialog.value = true
}

const handleDelete = async (row) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning('该科室下有子科室，不能删除')
    return
  }
  await ElMessageBox.confirm('确认删除该科室？', '提示', { type: 'warning' })
  await deleteDept(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSubmit = async () => {
  if (!form.name) {
    ElMessage.warning('请填写科室名称')
    return
  }
  if (form.id) {
    await updateDept(form.id, form)
  } else {
    await addDept(form)
  }
  ElMessage.success('操作成功')
  showDialog.value = false
  Object.assign(form, { id: null, parentId: 0, name: '', code: '', type: 1, sortOrder: 0 })
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
