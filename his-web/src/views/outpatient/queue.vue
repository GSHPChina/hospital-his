<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>排队叫号 - 等待中</span>
            <el-tag type="warning" style="margin-left: 8px;">{{ waitingList.length }}人</el-tag>
          </template>
          <div v-for="item in waitingList" :key="item.id" class="queue-item waiting">
            <span class="queue-no">{{ item.queueNo }}</span>
            <div class="queue-info">
              <span class="queue-patient">{{ item.patientName }}</span>
              <span class="queue-doctor">{{ item.doctorName }}</span>
            </div>
            <el-button type="warning" size="small" @click="callPatient(item)">叫号</el-button>
          </div>
          <el-empty v-if="waitingList.length === 0" description="无等待患者" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>当前就诊</span>
            <el-tag type="success" style="margin-left: 8px;">{{ callingList.length }}人</el-tag>
          </template>
          <div v-for="item in callingList" :key="item.id" class="queue-item calling">
            <span class="queue-no">{{ item.queueNo }}</span>
            <div class="queue-info">
              <span class="queue-patient">{{ item.patientName }}</span>
              <span class="queue-doctor">{{ item.doctorName }}</span>
            </div>
            <div>
              <el-button type="success" size="small" @click="completePatient(item)">完成</el-button>
              <el-button type="info" size="small" @click="skipPatient(item)">过号</el-button>
            </div>
          </div>
          <el-empty v-if="callingList.length === 0" description="无就诊患者" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'
import { useUserStore } from '../../stores/user'

const userStore = useUserStore()
const queueList = ref([])

const waitingList = computed(() => queueList.value.filter(q => q.status === 1))
const callingList = computed(() => queueList.value.filter(q => q.status === 2 || q.status === 3))

onMounted(() => loadData())

const loadData = async () => {
  const res = await request.get('/outpatient/queue')
  queueList.value = res.data || []
}

const callPatient = async (item) => {
  await request.put(`/outpatient/queue/${item.id}/call`)
  ElMessage.success(`请 ${item.patientName} 到诊室就诊`)
  loadData()
}

const completePatient = async (item) => {
  await request.put(`/outpatient/queue/${item.id}/complete`)
  ElMessage.success('已完成')
  loadData()
}

const skipPatient = async (item) => {
  await request.put(`/outpatient/queue/${item.id}/skip`)
  ElMessage.warning('已过号')
  loadData()
}
</script>

<style scoped>
.page-container { padding: 0; }
.queue-item { display: flex; align-items: center; gap: 16px; padding: 12px; margin-bottom: 8px; border-radius: 8px; background: #f5f7fa; }
.queue-item.calling { background: #ecf5ff; border-left: 4px solid #409eff; }
.queue-no { font-size: 20px; font-weight: bold; color: #409eff; width: 60px; }
.queue-info { flex: 1; }
.queue-patient { font-size: 16px; font-weight: 500; display: block; }
.queue-doctor { font-size: 12px; color: #999; }
</style>
