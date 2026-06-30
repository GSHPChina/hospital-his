<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>设备维修保养</span>
          <el-button type="success" @click="showDialog = true">新增记录</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="equipName" label="设备" width="150" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.type==='REPAIR'?'danger':row.type==='MAINTENANCE'?'warning':'info'">
              {{ {REPAIR:'维修',MAINTENANCE:'保养',INSPECTION:'巡检'}[row.type] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column prop="cost" label="费用" width="100">
          <template #default="{ row }">¥{{ row.cost }}</template>
        </el-table-column>
        <el-table-column prop="technician" label="技术员" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'warning':row.status===2?'':'success'">
              {{ {1:'待处理',2:'进行中',3:'已完成'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.status<3" type="success" size="small" @click="complete(row)">完成</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增维修保养记录" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="设备" required>
          <el-select v-model="form.equipId" filterable>
            <el-option v-for="e in equipments" :key="e.id" :label="`${e.equipNo} - ${e.name}`" :value="e.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type">
            <el-option label="维修" value="REPAIR" /><el-option label="保养" value="MAINTENANCE" /><el-option label="巡检" value="INSPECTION" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述" required><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="费用"><el-input-number v-model="form.cost" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="技术员"><el-input v-model="form.technician" /></el-form-item>
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
const equipments = ref([])
const showDialog = ref(false)
const form = reactive({ equipId: null, type: 'REPAIR', description: '', cost: 0, technician: '' })

onMounted(() => { loadData(); loadEquipments() })

const loadData = async () => { const res = await request.get('/equipment/maintenances'); list.value = res.data || [] }
const loadEquipments = async () => { const res = await request.get('/equipment/equipments'); equipments.value = res.data || [] }

const handleSubmit = async () => {
  await request.post('/equipment/maintenances', form)
  ElMessage.success('添加成功')
  showDialog.value = false
  loadData()
}

const complete = async (row) => {
  await request.put(`/equipment/maintenances/${row.id}/complete`)
  ElMessage.success('已完成')
  loadData()
}
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
