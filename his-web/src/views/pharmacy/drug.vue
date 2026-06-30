<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>药品管理</span>
          <div>
            <el-input v-model="keyword" placeholder="搜索药品" style="width: 200px; margin-right: 10px;" clearable prefix-icon="Search" @keyup.enter="loadData" />
            <el-button type="primary" @click="loadData">查询</el-button>
            <el-button type="success" @click="showDialog = true">新增药品</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="drugCode" label="编码" width="80" />
        <el-table-column prop="drugName" label="药品名称" width="150" />
        <el-table-column prop="genericName" label="通用名" width="120" />
        <el-table-column prop="spec" label="规格" width="120" />
        <el-table-column prop="unit" label="单位" width="50" />
        <el-table-column prop="manufacturer" label="生产厂家" width="150" />
        <el-table-column prop="category" label="分类" width="80">
          <template #default="{ row }">
            <el-tag size="small">{{ { WESTERN:'西药', CHINESE_PATENT:'中成药', CHINESE_HERBAL:'中草药', INJECTION:'注射剂' }[row.category] || row.category }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="unitPrice" label="单价" width="80">
          <template #default="{ row }">¥{{ row.unitPrice }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="70">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" link @click="editDrug(row)">编辑</el-button>
            <el-button type="danger" size="small" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑药品' : '新增药品'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="编码" required><el-input v-model="form.drugCode" /></el-form-item>
        <el-form-item label="药品名称" required><el-input v-model="form.drugName" /></el-form-item>
        <el-form-item label="通用名"><el-input v-model="form.genericName" /></el-form-item>
        <el-form-item label="规格"><el-input v-model="form.spec" /></el-form-item>
        <el-form-item label="单位"><el-input v-model="form.unit" /></el-form-item>
        <el-form-item label="生产厂家"><el-input v-model="form.manufacturer" /></el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.category">
            <el-option label="西药" value="WESTERN" />
            <el-option label="中成药" value="CHINESE_PATENT" />
            <el-option label="中草药" value="CHINESE_HERBAL" />
            <el-option label="注射剂" value="INJECTION" />
          </el-select>
        </el-form-item>
        <el-form-item label="单价"><el-input-number v-model="form.unitPrice" :min="0" :precision="2" /></el-form-item>
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
import { getDrugs, addDrug, updateDrug, deleteDrug } from '../../api/pharmacy'

const list = ref([])
const keyword = ref('')
const showDialog = ref(false)
const form = reactive({ id: null, drugCode: '', drugName: '', genericName: '', spec: '', unit: '', manufacturer: '', category: 'WESTERN', unitPrice: 0 })

onMounted(() => loadData())

const loadData = async () => { const res = await getDrugs({ keyword: keyword.value }); list.value = res.data || [] }

const editDrug = (row) => { Object.assign(form, row); showDialog.value = true }

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' })
  await deleteDrug(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSubmit = async () => {
  if (!form.drugCode || !form.drugName) { ElMessage.warning('请填写必填项'); return }
  if (form.id) { await updateDrug(form.id, form) }
  else { await addDrug(form) }
  ElMessage.success('操作成功')
  showDialog.value = false
  Object.assign(form, { id: null, drugCode: '', drugName: '', genericName: '', spec: '', unit: '', manufacturer: '', category: 'WESTERN', unitPrice: 0 })
  loadData()
}
</script>

<style scoped>.page-container { display: flex; flex-direction: column; gap: 16px; }</style>
