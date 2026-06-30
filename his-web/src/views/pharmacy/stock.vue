<template>
  <div class="page-container">
    <!-- 库存预警 -->
    <el-card v-if="warnings.length > 0">
      <template #header>
        <span style="color: #e6a23c;">⚠️ 库存预警</span>
      </template>
      <el-table :data="warnings" stripe border size="small">
        <el-table-column prop="drugName" label="药品名称" />
        <el-table-column prop="drugSpec" label="规格" width="120" />
        <el-table-column prop="quantity" label="当前库存" width="100">
          <template #default="{ row }">
            <span style="color: #f56c6c;">{{ row.quantity }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="safeStock" label="安全库存" width="100" />
      </el-table>
    </el-card>

    <!-- 库存列表 -->
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>库存管理</span>
          <el-button type="success" @click="showInbound = true">入库</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="drugName" label="药品名称" width="150" />
        <el-table-column prop="drugSpec" label="规格" width="120" />
        <el-table-column prop="batchNo" label="批次号" width="120" />
        <el-table-column prop="quantity" label="库存数量" width="100" />
        <el-table-column prop="safeStock" label="安全库存" width="100" />
        <el-table-column prop="expireDate" label="有效期" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'warning' : 'danger'">
              {{ { 1: '正常', 2: '临期', 3: '过期' }[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 入库对话框 -->
    <el-dialog v-model="showInbound" title="药品入库" width="400px">
      <el-form :model="inboundForm" label-width="80px">
        <el-form-item label="药品" required>
          <el-select v-model="inboundForm.drugId" filterable placeholder="选择药品">
            <el-option v-for="drug in drugList" :key="drug.id" :label="`${drug.drugName} (${drug.spec})`" :value="drug.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="批次号" required>
          <el-input v-model="inboundForm.batchNo" />
        </el-form-item>
        <el-form-item label="数量" required>
          <el-input-number v-model="inboundForm.quantity" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showInbound = false">取消</el-button>
        <el-button type="primary" @click="handleInbound">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStocks, getStockWarnings, inboundStock, getDrugs } from '../../api/pharmacy'

const list = ref([])
const warnings = ref([])
const drugList = ref([])
const showInbound = ref(false)

const inboundForm = reactive({
  drugId: null,
  batchNo: '',
  quantity: 100
})

onMounted(() => {
  loadData()
  loadWarnings()
  loadDrugs()
})

const loadData = async () => {
  const res = await getStocks()
  list.value = res.data
}

const loadWarnings = async () => {
  const res = await getStockWarnings()
  warnings.value = res.data
}

const loadDrugs = async () => {
  const res = await getDrugs()
  drugList.value = res.data
}

const handleInbound = async () => {
  if (!inboundForm.drugId || !inboundForm.batchNo) {
    ElMessage.warning('请填写必填项')
    return
  }
  await inboundStock(inboundForm)
  ElMessage.success('入库成功')
  showInbound.value = false
  Object.assign(inboundForm, { drugId: null, batchNo: '', quantity: 100 })
  loadData()
  loadWarnings()
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
