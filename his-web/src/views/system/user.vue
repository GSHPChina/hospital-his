<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>用户管理</span>
          <div>
            <el-input v-model="keyword" placeholder="搜索用户" style="width: 200px; margin-right: 10px;" clearable @keyup.enter="loadData" />
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增用户</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="realName" label="真实姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60">
          <template #default="{ row }">{{ { 1: '男', 2: '女' }[row.gender] || '-' }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="deptName" label="所属科室" width="150" />
        <el-table-column prop="post" label="职务" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editUser(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑用户' : '新增用户'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名" required>
          <el-input v-model="form.username" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="密码" :required="!form.id">
          <el-input v-model="form.password" type="password" :placeholder="form.id ? '留空不修改' : '请输入密码'" />
        </el-form-item>
        <el-form-item label="真实姓名" required>
          <el-input v-model="form.realName" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="科室">
          <el-tree-select
            v-model="form.deptId"
            :data="deptTree"
            :props="{ label: 'name', value: 'id', children: 'children' }"
            placeholder="选择科室"
            check-strictly
            clearable
            filterable
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="职务">
          <el-input v-model="form.post" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleIds" multiple placeholder="选择角色" style="width: 100%">
            <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id" />
          </el-select>
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
import { getUsers, addUser, updateUser, deleteUser } from '../../api/user'
import { getDeptTree, getRoles } from '../../api/system'

const list = ref([])
const deptTree = ref([])
const roleList = ref([])
const keyword = ref('')
const showDialog = ref(false)

const form = reactive({
  id: null,
  username: '',
  password: '',
  realName: '',
  gender: 1,
  phone: '',
  deptId: null,
  post: '',
  roleIds: []
})

onMounted(() => {
  loadData()
  loadDeptTree()
  loadRoles()
})

const loadData = async () => {
  const res = await getUsers({ keyword: keyword.value })
  list.value = res.data
}

const loadDeptTree = async () => {
  const res = await getDeptTree()
  deptTree.value = res.data
}

const loadRoles = async () => {
  const res = await getRoles()
  roleList.value = res.data
}

const editUser = (row) => {
  Object.assign(form, { ...row, password: '', roleIds: [] })
  showDialog.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确认删除该用户？', '提示', { type: 'warning' })
  await deleteUser(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSubmit = async () => {
  if (!form.username || !form.realName) {
    ElMessage.warning('请填写必填项')
    return
  }
  if (form.id) {
    await updateUser(form.id, form)
  } else {
    if (!form.password) form.password = '123456'
    await addUser(form)
  }
  ElMessage.success('操作成功')
  showDialog.value = false
  Object.assign(form, { id: null, username: '', password: '', realName: '', gender: 1, phone: '', deptId: null, post: '', roleIds: [] })
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
