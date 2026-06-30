<template>
  <div class="page-container">
    <!-- 顶部搜索 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="患者查询">
          <el-input v-model="searchForm.keyword" placeholder="姓名/手机号/身份证" clearable prefix-icon="Search" @keyup.enter="searchPatient" style="width: 300px;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchPatient">查询</el-button>
          <el-button type="success" @click="showAddPatient = true">
            <el-icon><Plus /></el-icon> 新建患者
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-row :gutter="16">
      <!-- 左侧患者列表 -->
      <el-col :span="10">
        <el-card>
          <template #header><span>患者列表</span></template>
          <el-table :data="patientList" stripe highlight-current-row @row-click="selectPatient" max-height="500">
            <el-table-column prop="patientNo" label="编号" width="150" />
            <el-table-column prop="name" label="姓名" width="80" />
            <el-table-column prop="gender" label="性别" width="50">
              <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号" width="120" />
            <el-table-column prop="idCard" label="身份证" />
          </el-table>
        </el-card>
      </el-col>

      <!-- 右侧挂号操作 -->
      <el-col :span="14">
        <!-- 患者信息 -->
        <el-card v-if="selectedPatient" style="margin-bottom: 16px;">
          <template #header><span>患者信息</span></template>
          <el-descriptions :column="3" border size="small">
            <el-descriptions-item label="姓名">{{ selectedPatient.name }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ selectedPatient.gender === 1 ? '男' : '女' }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ selectedPatient.phone }}</el-descriptions-item>
            <el-descriptions-item label="身份证">{{ selectedPatient.idCard || '-' }}</el-descriptions-item>
            <el-descriptions-item label="患者编号">{{ selectedPatient.patientNo }}</el-descriptions-item>
            <el-descriptions-item label="年龄">{{ calcAge(selectedPatient.birthDate) }}</el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 挂号表单 -->
        <el-card v-if="selectedPatient">
          <template #header><span>挂号信息</span></template>
          <el-form :model="registerForm" label-width="100px">
            <el-form-item label="挂号科室" required>
              <el-tree-select
                v-model="registerForm.deptId"
                :data="deptTree"
                :props="{ label: 'name', value: 'id', children: 'children' }"
                placeholder="请选择科室"
                check-strictly
                filterable
                style="width: 100%"
                @change="onDeptChange"
              />
            </el-form-item>
            <el-form-item label="挂号医生">
              <el-select v-model="registerForm.doctorId" placeholder="请选择医生" style="width: 100%">
                <el-option v-for="doc in doctorList" :key="doc.id" :label="`${doc.realName} (${doc.post})`" :value="doc.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="挂号类型" required>
              <el-radio-group v-model="registerForm.registerType">
                <el-radio-button value="NORMAL">普通号 ¥15</el-radio-button>
                <el-radio-button value="EXPERT">专家号 ¥50</el-radio-button>
                <el-radio-button value="EMERGENCY">急诊号 ¥30</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="large" @click="handleRegister" :loading="loading" style="width: 200px;">
                确认挂号
              </el-button>
              <el-button size="large" @click="resetForm" style="width: 100px;">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <el-card v-if="!selectedPatient">
          <el-empty description="请从左侧选择患者">
            <template #image>
              <div style="font-size: 64px;">👤</div>
            </template>
          </el-empty>
        </el-card>
      </el-col>
    </el-row>

    <!-- 新建患者对话框 -->
    <el-dialog v-model="showAddPatient" title="新建患者" width="500px">
      <el-form :model="patientForm" label-width="80px">
        <el-form-item label="姓名" required>
          <el-input v-model="patientForm.name" />
        </el-form-item>
        <el-form-item label="性别" required>
          <el-radio-group v-model="patientForm.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" required>
          <el-input v-model="patientForm.phone" />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="patientForm.idCard" />
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker v-model="patientForm.birthDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="住址">
          <el-input v-model="patientForm.address" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddPatient = false">取消</el-button>
        <el-button type="primary" @click="handleAddPatient">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getPatients, addPatient, register } from '../../api/register'
import { getDeptTree } from '../../api/system'
import { getUsers } from '../../api/user'

const loading = ref(false)
const showAddPatient = ref(false)
const patientList = ref([])
const deptTree = ref([])
const doctorList = ref([])
const selectedPatient = ref(null)

const searchForm = reactive({ keyword: '' })
const patientForm = reactive({ name: '', gender: 1, phone: '', idCard: '', birthDate: '', address: '' })
const registerForm = reactive({ deptId: null, doctorId: null, registerType: 'NORMAL' })

onMounted(() => { loadPatients(); loadDeptTree() })

const loadPatients = async () => { const res = await getPatients(searchForm); patientList.value = res.data || [] }

const loadDeptTree = async () => {
  const res = await getDeptTree()
  deptTree.value = (res.data || []).filter(d => d.type === 1)
}

const searchPatient = () => loadPatients()

const selectPatient = (row) => { selectedPatient.value = row }

const calcAge = (birthDate) => {
  if (!birthDate) return '-'
  const birth = new Date(birthDate)
  const now = new Date()
  return Math.floor((now - birth) / (365.25 * 24 * 60 * 60 * 1000))
}

const onDeptChange = async (deptId) => {
  registerForm.doctorId = null
  if (deptId) {
    const res = await getUsers({ deptId })
    doctorList.value = (res.data || []).filter(u => u.post && (u.post.includes('医师') || u.post.includes('主任')))
  } else {
    doctorList.value = []
  }
}

const handleAddPatient = async () => {
  if (!patientForm.name || !patientForm.phone) {
    ElMessage.warning('请填写必填项')
    return
  }
  await addPatient(patientForm)
  ElMessage.success('患者创建成功')
  showAddPatient.value = false
  loadPatients()
  Object.assign(patientForm, { name: '', gender: 1, phone: '', idCard: '', birthDate: '', address: '' })
}

const handleRegister = async () => {
  if (!registerForm.deptId) {
    ElMessage.warning('请选择挂号科室')
    return
  }
  loading.value = true
  try {
    await register({ patientId: selectedPatient.value.id, ...registerForm })
    ElMessage.success('挂号成功')
    resetForm()
    loadPatients()
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  selectedPatient.value = null
  Object.assign(registerForm, { deptId: null, doctorId: null, registerType: 'NORMAL' })
  doctorList.value = []
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
