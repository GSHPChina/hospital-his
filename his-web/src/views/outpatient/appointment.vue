<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>预约挂号</span>
          <el-button type="success" @click="showDialog = true">新建预约</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="appointmentNo" label="预约单号" width="180" />
        <el-table-column prop="patientName" label="患者" width="100" />
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="doctorName" label="医生" width="100" />
        <el-table-column prop="appointmentDate" label="预约日期" width="120" />
        <el-table-column prop="timeSlot" label="时段" width="80">
          <template #default="{ row }">{{ row.timeSlot === 'AM' ? '上午' : '下午' }}</template>
        </el-table-column>
        <el-table-column prop="queueNo" label="排队号" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'':row.status===2?'success':row.status===3?'info':'danger'">
              {{ {1:'已预约',2:'已签到',3:'已就诊',4:'已取消',5:'爽约'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status===1" type="success" size="small" @click="checkIn(row)">签到</el-button>
            <el-button v-if="row.status===1" type="danger" size="small" @click="cancel(row)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新建预约" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="患者" required>
          <el-select v-model="form.patientId" filterable placeholder="选择患者">
            <el-option v-for="p in patients" :key="p.id" :label="`${p.name} (${p.phone})`" :value="p.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="科室" required>
          <el-tree-select v-model="form.deptId" :data="deptTree" :props="{label:'name',value:'id',children:'children'}" check-strictly filterable @change="onDeptChange" />
        </el-form-item>
        <el-form-item label="医生" required>
          <el-select v-model="form.doctorId" filterable>
            <el-option v-for="d in doctors" :key="d.id" :label="`${d.realName} (${d.post})`" :value="d.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约日期" required>
          <el-date-picker v-model="form.appointmentDate" type="date" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="时段" required>
          <el-radio-group v-model="form.timeSlot">
            <el-radio value="AM">上午</el-radio>
            <el-radio value="PM">下午</el-radio>
          </el-radio-group>
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
import request from '../../utils/request'
import { getDeptTree } from '../../api/system'
import { getUsers } from '../../api/user'
import { getPatients } from '../../api/register'

const list = ref([])
const patients = ref([])
const deptTree = ref([])
const doctors = ref([])
const showDialog = ref(false)
const form = reactive({ patientId: null, deptId: null, doctorId: null, appointmentDate: '', timeSlot: 'AM' })

onMounted(() => { loadData(); loadPatients(); loadDepts() })

const loadData = async () => { const res = await request.get('/outpatient/appointments'); list.value = res.data || [] }
const loadPatients = async () => { const res = await getPatients({}); patients.value = res.data || [] }
const loadDepts = async () => { const res = await getDeptTree(); deptTree.value = (res.data || []).filter(d => d.type === 1) }
const onDeptChange = async (deptId) => { form.doctorId = null; const res = await getUsers({ deptId }); doctors.value = (res.data || []).filter(u => u.post?.includes('医师')) }

const handleSubmit = async () => {
  await request.post('/outpatient/appointments', form)
  ElMessage.success('预约成功')
  showDialog.value = false
  loadData()
}

const checkIn = async (row) => {
  await request.put(`/outpatient/appointments/${row.id}/checkin`)
  ElMessage.success('签到成功')
  loadData()
}

const cancel = async (row) => {
  await ElMessageBox.confirm('确认取消预约？', '提示', { type: 'warning' })
  await request.put(`/outpatient/appointments/${row.id}/cancel`)
  ElMessage.success('已取消')
  loadData()
}
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
