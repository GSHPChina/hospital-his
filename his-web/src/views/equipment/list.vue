<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>设备台账</span>
          <div>
            <el-select v-model="statusFilter" placeholder="状态" clearable style="width: 100px; margin-right: 10px;">
              <el-option label="正常" :value="1" /><el-option label="维修中" :value="2" /><el-option label="报废" :value="3" /><el-option label="闲置" :value="4" />
            </el-select>
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增设备</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="equipNo" label="设备编号" width="100" />
        <el-table-column prop="name" label="设备名称" width="150" />
        <el-table-column prop="brand" label="品牌" width="100" />
        <el-table-column prop="model" label="型号" width="120" />
        <el-table-column prop="deptName" label="使用科室" width="100" />
        <el-table-column prop="location" label="存放位置" width="120" />
        <el-table-column prop="purchasePrice" label="购入价格" width="100">
          <template #default="{ row }">¥{{ row.purchasePrice }}</template>
        </el-table-column>
        <el-table-column prop="currentValue" label="现值" width="100">
          <template #default="{ row }">¥{{ row.currentValue }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'success':row.status===2?'warning':row.status===3?'danger':'info'">
              {{ {1:'正常',2:'维修中',3:'报废',4:'闲置'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="responsiblePerson" label="负责人" width="80" />
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增设备" width="600px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="设备名称" required><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="品牌"><el-input v-model="form.brand" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="型号"><el-input v-model="form.model" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="生产厂家"><el-input v-model="form.manufacturer" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="购入价格"><el-input-number v-model="form.purchasePrice" :min="0" :precision="2" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="购入日期"><el-date-picker v-model="form.purchaseDate" type="date" value-format="YYYY-MM-DD" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="使用科室"><el-tree-select v-model="form.deptId" :data="deptTree" :props="{label:'name',value:'id',children:'children'}" check-strictly clearable filterable /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="存放位置"><el-input v-model="form.location" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="负责人"><el-input v-model="form.responsiblePerson" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="折旧年限"><el-input-number v-model="form.depreciationYears" :min="1" /></el-form-item></el-col>
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
const statusFilter = ref(null)
const showDialog = ref(false)
const form = reactive({ name: '', brand: '', model: '', manufacturer: '', purchasePrice: 0, purchaseDate: '', deptId: null, location: '', responsiblePerson: '', depreciationYears: 5 })

onMounted(() => { loadData(); loadDepts() })

const loadData = async () => {
  const params = {}
  if (statusFilter.value) params.status = statusFilter.value
  const res = await request.get('/equipment/equipments', { params })
  list.value = res.data || []
}

const loadDepts = async () => { const res = await getDeptTree(); deptTree.value = res.data || [] }

const handleSubmit = async () => {
  await request.post('/equipment/equipments', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
