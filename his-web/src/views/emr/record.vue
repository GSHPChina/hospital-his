<template>
  <div class="page-container">
    <el-card>
      <template #header><span>电子病历</span></template>
      <el-table :data="list" stripe border>
        <el-table-column prop="recordNo" label="病历号" width="180" />
        <el-table-column prop="patientName" label="患者" width="100" />
        <el-table-column prop="doctorName" label="医生" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===1?'info':row.status===2?'':row.status===3?'success':'warning'">
              {{ {1:'草稿',2:'已提交',3:'已审核',4:'已归档'}[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewRecord(row)">查看</el-button>
            <el-button v-if="row.status===1" type="success" size="small" @click="submitRecord(row)">提交</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDetail" title="病历详情" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="病历号">{{ currentRecord.recordNo }}</el-descriptions-item>
        <el-descriptions-item label="患者">{{ currentRecord.patientName }}</el-descriptions-item>
        <el-descriptions-item label="医生">{{ currentRecord.doctorName }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ {1:'草稿',2:'已提交',3:'已审核',4:'已归档'}[currentRecord.status] }}</el-descriptions-item>
      </el-descriptions>
      <el-divider />
      <h4>主诉</h4>
      <p>{{ currentRecord.chiefComplaint || '-' }}</p>
      <h4>现病史</h4>
      <p>{{ currentRecord.presentIllness || '-' }}</p>
      <h4>既往史</h4>
      <p>{{ currentRecord.pastHistory || '-' }}</p>
      <h4>体格检查</h4>
      <p>{{ currentRecord.physicalExam || '-' }}</p>
      <h4>诊断</h4>
      <p>{{ currentRecord.diagnosis || '-' }}</p>
      <h4>治疗方案</h4>
      <p>{{ currentRecord.treatmentPlan || '-' }}</p>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const showDetail = ref(false)
const currentRecord = ref({})

onMounted(() => loadData())

const loadData = async () => { const res = await request.get('/emr/records'); list.value = res.data || [] }

const viewRecord = (row) => { currentRecord.value = row; showDetail.value = true }

const submitRecord = async (row) => {
  await request.put(`/emr/records/${row.id}/submit`)
  ElMessage.success('提交成功')
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
h4 { margin: 12px 0 4px; color: #333; }
p { margin: 0 0 8px; color: #666; line-height: 1.6; }
</style>
