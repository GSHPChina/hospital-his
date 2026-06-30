<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>患者管理</span>
          <div>
            <el-input v-model="keyword" placeholder="搜索患者" style="width: 200px; margin-right: 10px;" clearable prefix-icon="Search" @keyup.enter="loadData" />
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新建患者</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border @row-click="selectPatient" highlight-current-row>
        <el-table-column prop="patientNo" label="患者编号" width="180" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="birthDate" label="出生日期" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="idCard" label="身份证号" width="180" />
        <el-table-column prop="address" label="住址" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editPatient(row)">编辑</el-button>
            <el-button type="info" size="small" @click="viewHistory(row)">病历</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 患者病历对话框 -->
    <el-dialog v-model="showHistory" title="患者病历" width="800px">
      <el-descriptions :column="2" border style="margin-bottom: 16px;">
        <el-descriptions-item label="患者">{{ currentPatient.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ currentPatient.gender === 1 ? '男' : '女' }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ calcAge(currentPatient.birthDate) }}岁</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ currentPatient.phone }}</el-descriptions-item>
      </el-descriptions>

      <el-divider>就诊记录</el-divider>

      <el-table :data="patientRecords" border size="small">
        <el-table-column prop="registerNo" label="挂号单号" width="180" />
        <el-table-column prop="deptName" label="科室" width="100" />
        <el-table-column prop="doctorName" label="医生" width="80" />
        <el-table-column prop="registerTime" label="就诊时间" width="140">
          <template #default="{ row }">{{ formatTime(row.registerTime) }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'warning' : row.status === 2 ? 'success' : 'info'" size="small">
              {{ { 1:'待诊', 2:'已诊', 3:'已退' }[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" size="small" link @click="viewRecordDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 病历详情对话框 -->
    <el-dialog v-model="showRecord" title="病历详情" width="700px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="挂号单号">{{ currentRecord.registerNo }}</el-descriptions-item>
        <el-descriptions-item label="科室">{{ currentRecord.deptName }}</el-descriptions-item>
        <el-descriptions-item label="医生">{{ currentRecord.doctorName }}</el-descriptions-item>
        <el-descriptions-item label="时间">{{ formatTime(currentRecord.registerTime) }}</el-descriptions-item>
      </el-descriptions>
      <el-divider />
      <h4>主诉</h4>
      <p>{{ medicalRecord.chiefComplaint || '-' }}</p>
      <h4>现病史</h4>
      <p>{{ medicalRecord.presentIllness || '-' }}</p>
      <h4>既往史</h4>
      <p>{{ medicalRecord.pastHistory || '-' }}</p>
      <h4>体格检查</h4>
      <p>{{ medicalRecord.physicalExam || '-' }}</p>
      <h4>治疗方案</h4>
      <p>{{ medicalRecord.treatmentPlan || '-' }}</p>

      <el-divider>诊断</el-divider>
      <el-table :data="diagnosisList" border size="small">
        <el-table-column prop="diagnosisName" label="诊断名称" />
        <el-table-column prop="icdCode" label="ICD编码" width="120" />
        <el-table-column prop="isPrimary" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.isPrimary === 1 ? 'danger' : 'info'" size="small">{{ row.isPrimary === 1 ? '主诊断' : '其他' }}</el-tag>
          </template>
        </el-table-column>
      </el-table>

      <el-divider>处方</el-divider>
      <el-table :data="prescriptionItems" border size="small">
        <el-table-column prop="drugName" label="药品" />
        <el-table-column prop="quantity" label="数量" width="60" />
        <el-table-column prop="dosage" label="用量" width="80" />
        <el-table-column prop="frequency" label="频次" width="80">
          <template #default="{ row }">{{ {QD:'每日一次',BID:'每日两次',TID:'每日三次',PRN:'需要时'}[row.frequency] || row.frequency }}</template>
        </el-table-column>
        <el-table-column prop="usageMethod" label="用法" width="60" />
      </el-table>
    </el-dialog>

    <!-- 新建/编辑患者对话框 -->
    <el-dialog v-model="showDialog" :title="form.id ? '编辑患者' : '新建患者'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="性别" required>
          <el-radio-group v-model="form.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" required>
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.idCard" />
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker v-model="form.birthDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="住址">
          <el-input v-model="form.address" />
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
import { ElMessage } from 'element-plus'
import { getPatients, addPatient } from '../../api/register'
import { getMedicalRecord, getDiagnoses, getPrescriptions } from '../../api/doctor'
import request from '../../utils/request'

const list = ref([])
const keyword = ref('')
const showDialog = ref(false)
const showHistory = ref(false)
const showRecord = ref(false)
const currentPatient = ref({})
const currentRecord = ref({})
const patientRecords = ref([])
const medicalRecord = ref({})
const diagnosisList = ref([])
const prescriptionItems = ref([])

const form = reactive({ id: null, name: '', gender: 1, phone: '', idCard: '', birthDate: '', address: '' })

const formatTime = (t) => t ? t.replace('T', ' ').substring(0, 16) : ''
const calcAge = (birthDate) => {
  if (!birthDate) return '-'
  return Math.floor((new Date() - new Date(birthDate)) / (365.25 * 24 * 60 * 60 * 1000))
}

onMounted(() => loadData())

const loadData = async () => {
  const res = await getPatients({ keyword: keyword.value })
  list.value = res.data || []
}

const editPatient = (row) => {
  Object.assign(form, row)
  showDialog.value = true
}

const handleSubmit = async () => {
  if (!form.name || !form.phone) { ElMessage.warning('请填写必填项'); return }
  if (form.id) {
    await request.put(`/outpatient/patients/${form.id}`, form)
  } else {
    await addPatient(form)
  }
  ElMessage.success('操作成功')
  showDialog.value = false
  Object.assign(form, { id: null, name: '', gender: 1, phone: '', idCard: '', birthDate: '', address: '' })
  loadData()
}

const viewHistory = async (row) => {
  currentPatient.value = row
  // 获取患者的所有挂号记录
  const res = await request.get('/outpatient/registers')
  patientRecords.value = (res.data || []).filter(r => r.patientId === row.id)
  showHistory.value = true
}

const viewRecordDetail = async (row) => {
  currentRecord.value = row
  // 获取病历
  const recordRes = await getMedicalRecord(row.id)
  medicalRecord.value = recordRes.data || {}
  // 获取诊断
  const diagRes = await getDiagnoses(row.id)
  diagnosisList.value = diagRes.data || []
  // 获取处方
  const rxRes = await getPrescriptions({ registerId: row.id })
  if (rxRes.data && rxRes.data.length > 0 && rxRes.data[0].items) {
    prescriptionItems.value = rxRes.data[0].items
  } else {
    prescriptionItems.value = []
  }
  showRecord.value = true
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
h4 { margin: 12px 0 4px; color: #333; }
p { margin: 0 0 8px; color: #666; line-height: 1.6; }
</style>
