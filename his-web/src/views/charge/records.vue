<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>收费记录</span>
          <div>
            <el-select v-model="statusFilter" placeholder="状态" clearable style="width: 100px; margin-right: 10px;">
              <el-option label="已支付" :value="1" />
              <el-option label="已退费" :value="2" />
            </el-select>
            <el-button type="primary" @click="loadData">查询</el-button>
          </div>
        </div>
      </template>
      <el-table :data="filteredList" stripe border>
        <el-table-column prop="feeNo" label="收费单号" width="180" />
        <el-table-column prop="patientName" label="患者" width="100" />
        <el-table-column prop="totalAmount" label="金额" width="100">
          <template #default="{ row }">
            <span style="color: #f56c6c; font-weight: bold;">¥{{ row.totalAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="payType" label="支付方式" width="100">
          <template #default="{ row }">
            {{ { CASH: '现金', WECHAT: '微信', ALIPAY: '支付宝', MEDICAL_INSURANCE: '医保' }[row.payType] }}
          </template>
        </el-table-column>
        <el-table-column prop="payStatus" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.payStatus === 1 ? 'success' : 'danger'">
              {{ { 1: '已支付', 2: '已退费' }[row.payStatus] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="payTime" label="支付时间" width="160">
          <template #default="{ row }">{{ formatTime(row.payTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="printReceipt(row)">打印</el-button>
            <el-button v-if="row.payStatus === 1" type="danger" size="small" @click="handleRefund(row)">退费</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 打印预览 -->
    <el-dialog v-model="showPrint" title="收费票据" width="400px">
      <div class="receipt" id="receipt">
        <div class="receipt-header">
          <h2>门诊收费票据</h2>
          <p>医院HIS系统</p>
        </div>
        <div class="receipt-body">
          <div class="receipt-row">
            <span>收费单号:</span>
            <span>{{ printData.feeNo }}</span>
          </div>
          <div class="receipt-row">
            <span>患者姓名:</span>
            <span>{{ printData.patientName }}</span>
          </div>
          <div class="receipt-row">
            <span>支付方式:</span>
            <span>{{ { CASH: '现金', WECHAT: '微信', ALIPAY: '支付宝', MEDICAL_INSURANCE: '医保' }[printData.payType] }}</span>
          </div>
          <div class="receipt-row">
            <span>支付时间:</span>
            <span>{{ formatTime(printData.payTime) }}</span>
          </div>
          <div class="receipt-total">
            <span>应付金额:</span>
            <span class="amount">¥{{ printData.totalAmount }}</span>
          </div>
        </div>
        <div class="receipt-footer">
          <p>收费员: {{ printData.operatorName || '系统' }}</p>
          <p>打印时间: {{ new Date().toLocaleString('zh-CN') }}</p>
        </div>
      </div>
      <template #footer>
        <el-button @click="showPrint = false">关闭</el-button>
        <el-button type="primary" @click="doPrint">打印</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getFees, refund } from '../../api/charge'

const list = ref([])
const statusFilter = ref(null)
const showPrint = ref(false)
const printData = ref({})

const filteredList = computed(() => {
  if (statusFilter.value === null) return list.value
  return list.value.filter(item => item.payStatus === statusFilter.value)
})

const formatTime = (t) => t ? t.replace('T', ' ').substring(0, 16) : ''

onMounted(() => loadData())

const loadData = async () => {
  const res = await getFees()
  list.value = res.data || []
}

const handleRefund = async (row) => {
  await ElMessageBox.confirm('确认退费？', '提示', { type: 'warning' })
  await refund(row.id)
  ElMessage.success('退费成功')
  loadData()
}

const printReceipt = (row) => {
  printData.value = row
  showPrint.value = true
}

const doPrint = () => {
  const printWindow = window.open('', '_blank')
  const content = document.getElementById('receipt').innerHTML
  printWindow.document.write(`
    <html>
      <head><title>收费票据</title>
      <style>
        body { font-family: SimSun, serif; padding: 20px; }
        .receipt-header { text-align: center; border-bottom: 2px solid #000; padding-bottom: 10px; margin-bottom: 10px; }
        .receipt-header h2 { margin: 0; font-size: 18px; }
        .receipt-header p { margin: 5px 0 0; font-size: 12px; }
        .receipt-row { display: flex; justify-content: space-between; padding: 5px 0; font-size: 14px; }
        .receipt-total { display: flex; justify-content: space-between; padding: 10px 0; border-top: 1px dashed #000; margin-top: 10px; font-size: 16px; font-weight: bold; }
        .amount { color: #f00; font-size: 20px; }
        .receipt-footer { border-top: 1px dashed #000; padding-top: 10px; margin-top: 10px; font-size: 12px; }
        .receipt-footer p { margin: 2px 0; }
      </style>
      </head>
      <body>${content}</body>
    </html>
  `)
  printWindow.document.close()
  printWindow.print()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.receipt { padding: 20px; border: 1px solid #ddd; background: #fff; }
.receipt-header { text-align: center; border-bottom: 2px solid #333; padding-bottom: 10px; margin-bottom: 10px; }
.receipt-header h2 { margin: 0; font-size: 20px; }
.receipt-header p { margin: 5px 0 0; color: #666; }
.receipt-row { display: flex; justify-content: space-between; padding: 6px 0; font-size: 14px; }
.receipt-total { display: flex; justify-content: space-between; padding: 12px 0; border-top: 1px dashed #333; margin-top: 12px; font-size: 18px; font-weight: bold; }
.amount { color: #f56c6c; font-size: 22px; }
.receipt-footer { border-top: 1px dashed #333; padding-top: 10px; margin-top: 10px; font-size: 12px; color: #666; }
.receipt-footer p { margin: 2px 0; }
</style>
