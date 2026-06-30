<template>
  <div class="page-container">
    <el-row :gutter="16">
      <!-- 待发药列表 -->
      <el-col :span="14">
        <el-card>
          <template #header>
            <div class="panel-header">
              <span>待发药处方</span>
              <el-tag type="warning">{{ pendingList.length }}张</el-tag>
            </div>
          </template>
          <el-table :data="pendingList" stripe highlight-current-row @row-click="selectDispensing" max-height="500">
            <el-table-column prop="dispensingNo" label="发药单号" width="180" />
            <el-table-column prop="patientName" label="患者" width="100" />
            <el-table-column prop="createTime" label="时间" width="140">
              <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="row.status === 1 ? 'warning' : 'success'" size="small">
                  {{ { 1:'待发药', 2:'已发药' }[row.status] }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 发药操作 -->
      <el-col :span="10">
        <el-card v-if="selectedDispensing">
          <template #header><span>发药确认</span></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="发药单号">{{ selectedDispensing.dispensingNo }}</el-descriptions-item>
            <el-descriptions-item label="患者">{{ selectedDispensing.patientName }}</el-descriptions-item>
          </el-descriptions>

          <el-divider />

          <div class="dispense-info">
            <el-icon :size="48" color="#e6a23c"><Box /></el-icon>
            <p>请核对处方信息后确认发药</p>
            <p class="tip">发药后库存将自动扣减</p>
          </div>

          <el-button type="success" size="large" @click="handleDispense" :loading="loading" style="width: 100%; margin-top: 20px; height: 48px; font-size: 16px;">
            确认发药
          </el-button>
        </el-card>

        <el-card v-if="!selectedDispensing">
          <el-empty description="请从左侧选择发药单">
            <template #image>
              <div style="font-size: 64px;">📦</div>
            </template>
          </el-empty>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPendingDispensings, dispense } from '../../api/pharmacy'

const pendingList = ref([])
const selectedDispensing = ref(null)
const loading = ref(false)

const formatTime = (t) => t ? t.replace('T', ' ').substring(5, 16) : ''

onMounted(() => loadData())

const loadData = async () => {
  const res = await getPendingDispensings()
  pendingList.value = res.data || []
}

const selectDispensing = (row) => { selectedDispensing.value = row }

const handleDispense = async () => {
  await ElMessageBox.confirm('确认发药？发药后库存将自动扣减', '发药确认', { type: 'warning' })
  loading.value = true
  try {
    await dispense(selectedDispensing.value.id)
    ElMessage.success('发药成功')
    selectedDispensing.value = null
    loadData()
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.panel-header { display: flex; justify-content: space-between; align-items: center; }
.dispense-info { text-align: center; padding: 20px; }
.dispense-info p { margin: 8px 0; font-size: 14px; color: #666; }
.dispense-info .tip { font-size: 12px; color: #999; }
</style>
