<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>员工档案</span>
          <div>
            <el-input v-model="keyword" placeholder="搜索员工" style="width: 200px; margin-right: 10px;" clearable @keyup.enter="loadData" />
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增员工</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="empNo" label="工号" width="100" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="position" label="岗位" width="120" />
        <el-table-column prop="title" label="职称" width="120" />
        <el-table-column prop="education" label="学历" width="80" />
        <el-table-column prop="workDate" label="入职日期" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'success':row.status===2?'danger':'info'">
              {{ {1:'在职',2:'离职',3:'退休',4:'休假'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="edit(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑员工' : '新增员工'" width="600px">
      <el-form :model="form" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="工号" required><el-input v-model="form.empNo" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="姓名" required><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="性别"><el-radio-group v-model="form.gender"><el-radio :value="1">男</el-radio><el-radio :value="2">女</el-radio></el-radio-group></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="科室"><el-tree-select v-model="form.deptId" :data="deptTree" :props="{label:'name',value:'id',children:'children'}" check-strictly clearable filterable /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="岗位"><el-input v-model="form.position" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="职称"><el-input v-model="form.title" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="学历"><el-select v-model="form.education"><el-option label="博士" value="PhD" /><el-option label="硕士" value="Master" /><el-option label="本科" value="Bachelor" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="入职日期"><el-date-picker v-model="form.workDate" type="date" value-format="YYYY-MM-DD" /></el-form-item></el-col>
        </el-row>
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
import { getDeptTree } from '../../api/system'

const list = ref([])
const deptTree = ref([])
const keyword = ref('')
const showDialog = ref(false)
const form = reactive({ id: null, empNo: '', name: '', gender: 1, phone: '', deptId: null, position: '', title: '', education: '', workDate: '' })

onMounted(() => { loadData(); loadDeptTree() })

const loadData = async () => {
  const res = await request.get('/hr/employees', { params: { keyword: keyword.value } })
  list.value = res.data
}

const loadDeptTree = async () => {
  const res = await getDeptTree()
  deptTree.value = res.data
}

const edit = (row) => { Object.assign(form, row); showDialog.value = true }

const handleSubmit = async () => {
  if (form.id) { await request.put(`/hr/employees/${form.id}`, form) }
  else { await request.post('/hr/employees', form) }
  ElMessage.success('操作成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
