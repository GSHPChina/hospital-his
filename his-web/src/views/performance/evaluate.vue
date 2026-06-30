<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>绩效考核</span>
          <el-button type="success" @click="startEvaluate">开始考核</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border>
        <el-table-column prop="empName" label="被考核人" width="120" />
        <el-table-column prop="templateName" label="考核模板" width="150" />
        <el-table-column prop="year" label="年度" width="80" />
        <el-table-column prop="quarter" label="季度" width="80">
          <template #default="{ row }">{{ row.quarter ? `Q${row.quarter}` : '-' }}</template>
        </el-table-column>
        <el-table-column prop="totalScore" label="得分" width="80">
          <template #default="{ row }">
            <span :style="{ color: row.totalScore >= 90 ? '#67c23a' : row.totalScore >= 70 ? '#e6a23c' : '#f56c6c' }">{{ row.totalScore }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="level" label="等级" width="80">
          <template #default="{ row }">
            <el-tag :type="row.level==='A'?'success':row.level==='B'?'':row.level==='C'?'warning':'danger'" v-if="row.level">{{ row.level }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">{{ {1:'草稿',2:'已提交',3:'已确认'}[row.status] }}</template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="绩效考核" width="700px">
      <el-form :model="evalForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="考核模板" required>
              <el-select v-model="evalForm.templateId" @change="onTemplateChange" style="width: 100%">
                <el-option v-for="t in templates" :key="t.id" :label="t.name" :value="t.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="被考核人" required>
              <el-select v-model="evalForm.empId" filterable style="width: 100%">
                <el-option v-for="e in employees" :key="e.id" :label="e.name" :value="e.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年度" required>
              <el-input-number v-model="evalForm.year" :min="2020" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="季度">
              <el-select v-model="evalForm.quarter" clearable style="width: 100%">
                <el-option label="Q1" :value="1" /><el-option label="Q2" :value="2" />
                <el-option label="Q3" :value="3" /><el-option label="Q4" :value="4" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider>评分</el-divider>

        <div v-for="ind in currentIndicators" :key="ind.id" class="score-item">
          <div class="score-header">
            <span class="score-name">{{ ind.name }}</span>
            <span class="score-max">满分: {{ ind.score }}</span>
          </div>
          <div class="score-desc">{{ ind.description }}</div>
          <el-input-number v-model="scores[ind.id]" :min="0" :max="ind.score" style="width: 200px;" />
        </div>

        <div class="total-score">
          总分: <span>{{ computedTotal }}</span> / {{ totalMax }}
        </div>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitEvaluate">提交考核</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const list = ref([])
const templates = ref([])
const employees = ref([])
const currentIndicators = ref([])
const showDialog = ref(false)
const scores = reactive({})
const evalForm = reactive({ templateId: null, empId: null, year: 2026, quarter: null })

const computedTotal = computed(() => currentIndicators.value.reduce((sum, ind) => sum + (scores[ind.id] || 0), 0))
const totalMax = computed(() => currentIndicators.value.reduce((sum, ind) => sum + ind.score, 0))

onMounted(() => { loadData(); loadTemplates(); loadEmployees() })

const loadData = async () => { const res = await request.get('/performance/records'); list.value = res.data || [] }
const loadTemplates = async () => { const res = await request.get('/performance/templates'); templates.value = res.data || [] }
const loadEmployees = async () => { const res = await request.get('/hr/employees'); employees.value = res.data || [] }

const onTemplateChange = async (tid) => {
  const res = await request.get(`/performance/templates/${tid}/indicators`)
  currentIndicators.value = res.data || []
}

const startEvaluate = () => {
  Object.assign(evalForm, { templateId: null, empId: null, year: 2026, quarter: null })
  currentIndicators.value = []
  showDialog.value = true
}

const submitEvaluate = async () => {
  const details = currentIndicators.value.map(ind => ({ indicatorId: ind.id, score: scores[ind.id] || 0 }))
  await request.post('/performance/evaluate', { ...evalForm, scores: details })
  ElMessage.success('考核提交成功')
  showDialog.value = false
  loadData()
}
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.score-item { padding: 12px; margin-bottom: 12px; background: #f5f7fa; border-radius: 8px; }
.score-header { display: flex; justify-content: space-between; margin-bottom: 4px; }
.score-name { font-weight: 500; }
.score-max { font-size: 12px; color: #999; }
.score-desc { font-size: 12px; color: #666; margin-bottom: 8px; }
.total-score { text-align: right; font-size: 16px; margin-top: 16px; }
.total-score span { font-size: 24px; font-weight: bold; color: #409eff; }
</style>
