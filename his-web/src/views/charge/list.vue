<template>
  <div class="page-container">
    <el-row :gutter="16">
      <!-- 待收费列表 -->
      <el-col :span="14">
        <el-card>
          <template #header>
            <div class="panel-header">
              <span>待收费处方</span>
              <el-tag type="warning">{{ pendingList.length }}张</el-tag>
            </div>
          </template>
          <el-table :data="pendingList" stripe highlight-current-row @row-click="selectRx" max-height="500">
            <el-table-column prop="prescriptionNo" label="处方号" width="180" />
            <el-table-column prop="patientName" label="患者" width="80" />
            <el-table-column prop="doctorName" label="医生" width="80" />
            <el-table-column prop="totalAmount" label="金额" width="100">
              <template #default="{ row }">
                <span class="amount">¥{{ row.totalAmount }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="开方时间" width="140">
              <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 收费操作 -->
      <el-col :span="10">
        <el-card v-if="selectedRx">
          <template #header><span>收费结算</span></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="处方号">{{ selectedRx.prescriptionNo }}</el-descriptions-item>
            <el-descriptions-item label="患者">{{ selectedRx.patientName }}</el-descriptions-item>
            <el-descriptions-item label="医生">{{ selectedRx.doctorName }}</el-descriptions-item>
            <el-descriptions-item label="金额">
              <span class="amount-lg">¥{{ selectedRx.totalAmount }}</span>
            </el-descriptions-item>
          </el-descriptions>

          <el-divider />

          <div class="pay-methods">
            <div class="pay-label">支付方式</div>
            <el-radio-group v-model="payType" size="large">
              <el-radio-button value="CASH">
                <el-icon><Wallet /></el-icon> 现金
              </el-radio-button>
              <el-radio-button value="WECHAT">
                <el-icon><ChatDotRound /></el-icon> 微信
              </el-radio-button>
              <el-radio-button value="ALIPAY">
                <el-icon><Wallet /></el-icon> 支付宝
              </el-radio-button>
              <el-radio-button value="MEDICAL_INSURANCE">
                <el-icon><FirstAidKit /></el-icon> 医保
              </el-radio-button>
            </el-radio-group>
          </div>

          <el-button type="success" size="large" @click="handleCharge" :loading="loading" style="width: 100%; margin-top: 20px; height: 48px; font-size: 16px;">
            确认收费
          </el-button>
        </el-card>

        <el-card v-if="!selectedRx">
          <el-empty description="请从左侧选择处方">
            <template #image>
              <div style="font-size: 64px;">💰</div>
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
import { getPrescriptions } from '../../api/doctor'
import { charge } from '../../api/charge'

const pendingList = ref([])
const selectedRx = ref(null)
const payType = ref('WECHAT')
const loading = ref(false)

const formatTime = (t) => t ? t.replace('T', ' ').substring(5, 16) : ''

onMounted(() => loadData())

const loadData = async () => {
  const res = await getPrescriptions({ status: 1 })
  pendingList.value = res.data || []
}

const selectRx = (row) => { selectedRx.value = row }

const handleCharge = async () => {
  await ElMessageBox.confirm(`确认收取 ¥${selectedRx.value.totalAmount}？`, '收费确认', { type: 'warning' })
  loading.value = true
  try {
    await charge({
      registerId: selectedRx.value.registerId,
      patientId: selectedRx.value.patientId,
      prescriptionId: selectedRx.value.id,
      totalAmount: selectedRx.value.totalAmount,
      payType: payType.value
    })
    ElMessage.success('收费成功')
    selectedRx.value = null
    loadData()
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.panel-header { display: flex; justify-content: space-between; align-items: center; }
.amount { color: #f56c6c; font-weight: bold; }
.amount-lg { color: #f56c6c; font-weight: bold; font-size: 24px; }
.pay-methods { margin: 16px 0; }
.pay-label { margin-bottom: 8px; font-weight: 500; }
</style>
