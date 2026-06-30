<template>
  <div class="page-container">
    <el-card>
      <template #header><span>职称评审</span></template>
      <el-table :data="list" stripe border>
        <el-table-column prop="appNo" label="申请单号" width="180" />
        <el-table-column prop="empName" label="申请人" width="100" />
        <el-table-column prop="applyTitle" label="申请职称" width="120" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status===6?'success':row.status===7?'danger':'info'">
              {{ {2:'已提交',3:'科室审核',4:'人事审核',5:'评审委员会',6:'已通过',7:'已驳回'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status===3" type="primary" size="small" @click="showReview(row, 'DEPT')">科室审核</el-button>
            <el-button v-if="row.status===4" type="warning" size="small" @click="showReview(row, 'HR')">人事审核</el-button>
            <el-button v-if="row.status===5" type="success" size="small" @click="showReview(row, 'COMMITTEE')">委员会评审</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="职称评审" width="500px">
      <el-form :model="reviewForm" label-width="80px">
        <el-form-item label="评审意见"><el-input v-model="reviewForm.opinion" type="textarea" :rows="3" /></el-form-item>
        <el-form-item v-if="reviewForm.reviewType==='COMMITTEE'" label="评分"><el-input-number v-model="reviewForm.score" :min="0" :max="100" /></el-form-item>
        <el-form-item label="结果">
          <el-radio-group v-model="reviewForm.pass">
            <el-radio :value="true">通过</el-radio>
            <el-radio :value="false">不通过</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const showDialog = ref(false)
const currentId = ref(null)
const reviewForm = reactive({ reviewType: '', opinion: '', pass: true, score: 0 })

onMounted(() => loadData())

const loadData = async () => {
  const res = await request.get('/title/applications')
  list.value = res.data.filter(a => a.status >= 2 && a.status <= 5)
}

const showReview = (row, type) => {
  currentId.value = row.id
  reviewForm.reviewType = type
  reviewForm.opinion = ''
  reviewForm.pass = true
  reviewForm.score = 0
  showDialog.value = true
}

const submitReview = async () => {
  const endpoint = reviewForm.reviewType === 'DEPT' ? 'dept-review' : reviewForm.reviewType === 'HR' ? 'hr-review' : 'committee-review'
  const data = { opinion: reviewForm.opinion, pass: reviewForm.pass }
  if (reviewForm.reviewType === 'COMMITTEE') data.score = reviewForm.score
  await request.put(`/title/applications/${currentId.value}/${endpoint}`, data)
  ElMessage.success('评审完成')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
