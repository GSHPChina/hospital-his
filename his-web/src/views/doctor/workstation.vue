<template>
  <div class="workstation">
    <!-- 左侧患者列表 -->
    <div class="patient-panel">
      <el-card class="full-height">
        <template #header>
          <div class="panel-header">
            <span>今日患者</span>
            <el-tag>{{ patients.length }}人</el-tag>
          </div>
        </template>
        <el-input v-model="searchKey" placeholder="搜索患者" prefix-icon="Search" clearable size="small" style="margin-bottom: 12px;" />
        <div class="patient-list">
          <div v-for="p in filteredPatients" :key="p.id"
               class="patient-card" :class="{ active: selectedRegister?.id === p.id }"
               @click="selectPatient(p)">
            <div class="patient-avatar">{{ p.patientName?.charAt(0) || '?' }}</div>
            <div class="patient-info">
              <div class="patient-name">{{ p.patientName }}</div>
              <div class="patient-meta">
                <el-tag size="small" :type="p.registerType === 'EXPERT' ? 'warning' : 'info'">
                  {{ { NORMAL:'普通', EXPERT:'专家', EMERGENCY:'急诊' }[p.registerType] }}
                </el-tag>
                <span>{{ p.deptName }}</span>
              </div>
            </div>
          </div>
          <el-empty v-if="filteredPatients.length === 0" description="暂无患者" :image-size="60" />
        </div>
      </el-card>
    </div>

    <!-- 右侧工作区 -->
    <div class="work-panel">
      <el-card v-if="selectedRegister" class="full-height">
        <template #header>
          <div class="panel-header">
            <div class="patient-header">
              <el-avatar :size="40" style="background: #409eff;">{{ selectedRegister.patientName?.charAt(0) }}</el-avatar>
              <div>
                <div class="header-name">{{ selectedRegister.patientName }}</div>
                <div class="header-info">{{ selectedRegister.deptName }} | {{ selectedRegister.registerNo }}</div>
              </div>
            </div>
          </div>
        </template>

        <!-- AI诊断区域 -->
        <el-card class="ai-card" shadow="never">
          <div class="ai-header">
            <el-icon :size="20" color="#409eff"><MagicStick /></el-icon>
            <span style="font-weight: bold;">AI辅助诊断</span>
            <el-tag v-if="aiResult" :type="aiResult.confidence > 0.7 ? 'success' : 'warning'" size="small">
              置信度: {{ Math.round(aiResult.confidence * 100) }}%
            </el-tag>
          </div>

          <el-input
            v-model="chiefComplaint"
            type="textarea"
            :rows="2"
            placeholder="请输入患者主诉，如：咳嗽、咳痰、发热 / 腹痛、腹泻、恶心 / 头晕、心悸、血压高..."
            style="margin-bottom: 12px;"
          />

          <div class="ai-actions">
            <el-button type="primary" @click="doAiDiagnose" :loading="aiLoading">
              <el-icon><MagicStick /></el-icon> AI智能诊断
            </el-button>
            <el-button v-if="aiResult" type="success" @click="applyAiResult">
              <el-icon><Check /></el-icon> 应用诊断 (按空格键)
            </el-button>
          </div>

          <!-- AI诊断结果 -->
          <div v-if="aiResult" class="ai-result">
            <el-divider content-position="left">诊断结果</el-divider>

            <el-descriptions :column="2" border size="small">
              <el-descriptions-item label="建议诊断">
                <el-tag type="danger" size="large">{{ aiResult.diagnosisName }}</el-tag>
                <span style="margin-left: 8px; color: #999;">ICD: {{ aiResult.icdCode }}</span>
              </el-descriptions-item>
              <el-descriptions-item label="诊断标题">{{ aiResult.title }}</el-descriptions-item>
            </el-descriptions>

            <!-- 临床指南推荐 -->
            <el-card v-if="aiResult.guideline" class="guideline-card" shadow="never">
              <template #header>
                <span><el-icon><Document /></el-icon> 临床指南推荐意见</span>
              </template>
              <div class="guideline-content" v-html="formatGuideline(aiResult.guideline)"></div>
            </el-card>

            <!-- 推荐药品 -->
            <el-card v-if="aiResult.drugs && aiResult.drugs.length > 0" class="drugs-card" shadow="never">
              <template #header>
                <span><el-icon><FirstAidKit /></el-icon> 推荐药品 ({{ aiResult.drugs.length }}种)</span>
              </template>
              <el-table :data="aiResult.drugs" border size="small">
                <el-table-column prop="drugName" label="药品名称" width="160" />
                <el-table-column prop="drugSpec" label="规格" width="120" />
                <el-table-column prop="dosage" label="用量" width="80" />
                <el-table-column prop="frequency" label="频次" width="60">
                  <template #default="{ row }">{{ {QD:'每日一次',BID:'每日两次',TID:'每日三次',PRN:'需要时'}[row.frequency] || row.frequency }}</template>
                </el-table-column>
                <el-table-column prop="usageMethod" label="用法" width="60" />
                <el-table-column prop="days" label="天数" width="50" />
                <el-table-column prop="description" label="说明" />
              </el-table>
            </el-card>
          </div>
        </el-card>

        <el-tabs v-model="activeTab" type="border-card" style="margin-top: 16px;">
          <!-- 病历 -->
          <el-tab-pane label="门诊病历" name="record">
            <el-form :model="medicalRecord" label-width="80px">
              <el-row :gutter="20">
                <el-col :span="24">
                  <el-form-item label="主诉">
                    <el-input v-model="medicalRecord.chiefComplaint" type="textarea" :rows="2" />
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="现病史">
                    <el-input v-model="medicalRecord.presentIllness" type="textarea" :rows="3" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="既往史">
                    <el-input v-model="medicalRecord.pastHistory" type="textarea" :rows="2" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="体格检查">
                    <el-input v-model="medicalRecord.physicalExam" type="textarea" :rows="2" />
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="治疗方案">
                    <el-input v-model="medicalRecord.treatmentPlan" type="textarea" :rows="2" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-form-item>
                <el-button type="primary" @click="saveRecord">保存病历</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>

          <!-- 诊断 -->
          <el-tab-pane label="诊断" name="diagnosis">
            <div style="margin-bottom: 16px;">
              <el-input v-model="newDiagnosis.diagnosisName" placeholder="诊断名称" style="width: 200px; margin-right: 10px;" />
              <el-input v-model="newDiagnosis.icdCode" placeholder="ICD编码" style="width: 120px; margin-right: 10px;" />
              <el-radio-group v-model="newDiagnosis.isPrimary" style="margin-right: 10px;">
                <el-radio-button :value="1">主诊断</el-radio-button>
                <el-radio-button :value="0">其他</el-radio-button>
              </el-radio-group>
              <el-button type="primary" @click="addDiagnosisItem">添加诊断</el-button>
            </div>
            <el-table :data="diagnosisList" border>
              <el-table-column prop="diagnosisName" label="诊断名称" />
              <el-table-column prop="icdCode" label="ICD编码" width="120" />
              <el-table-column prop="isPrimary" label="类型" width="80">
                <template #default="{ row }">
                  <el-tag :type="row.isPrimary === 1 ? 'danger' : 'info'" size="small">{{ row.isPrimary === 1 ? '主诊断' : '其他' }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="80">
                <template #default="{ row }">
                  <el-button type="danger" size="small" link @click="removeDiagnosis(row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>

          <!-- 处方 -->
          <el-tab-pane label="开处方" name="prescription">
            <div style="margin-bottom: 16px;">
              <el-button type="primary" @click="addDrugRow">添加药品</el-button>
              <el-button type="success" @click="submitRx" :disabled="prescriptionItems.length === 0">提交处方</el-button>
            </div>
            <el-table :data="prescriptionItems" border>
              <el-table-column label="药品" width="220">
                <template #default="{ row }">
                  <el-select v-model="row.drugId" filterable placeholder="选择药品" @change="(val) => onDrugSelect(row, val)" style="width: 100%">
                    <el-option v-for="drug in drugList" :key="drug.id" :label="`${drug.drugName} (${drug.spec})`" :value="drug.id" />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="单价" width="80">
                <template #default="{ row }">¥{{ row.unitPrice }}</template>
              </el-table-column>
              <el-table-column label="数量" width="100">
                <template #default="{ row }">
                  <el-input-number v-model="row.quantity" :min="1" size="small" style="width: 100%" />
                </template>
              </el-table-column>
              <el-table-column label="用量" width="100">
                <template #default="{ row }">
                  <el-input v-model="row.dosage" size="small" />
                </template>
              </el-table-column>
              <el-table-column label="频次" width="100">
                <template #default="{ row }">
                  <el-select v-model="row.frequency" size="small">
                    <el-option label="每日一次" value="QD" />
                    <el-option label="每日两次" value="BID" />
                    <el-option label="每日三次" value="TID" />
                    <el-option label="需要时" value="PRN" />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="用法" width="100">
                <template #default="{ row }">
                  <el-select v-model="row.usageMethod" size="small">
                    <el-option label="口服" value="口服" />
                    <el-option label="外用" value="外用" />
                    <el-option label="注射" value="注射" />
                    <el-option label="滴眼" value="滴眼" />
                    <el-option label="喷喉" value="喷喉" />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="金额" width="80">
                <template #default="{ row }">¥{{ (row.unitPrice * row.quantity).toFixed(2) }}</template>
              </el-table-column>
              <el-table-column label="操作" width="60">
                <template #default="{ $index }">
                  <el-button type="danger" size="small" link @click="prescriptionItems.splice($index, 1)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="total-amount">
              合计: <span>¥{{ totalAmount }}</span>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-card>

      <el-empty v-if="!selectedRegister" description="请从左侧选择患者开始诊疗" :image-size="120">
        <template #image><div style="font-size: 64px;">👨‍⚕️</div></template>
      </el-empty>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getTodayPatients, getMedicalRecord, saveMedicalRecord, getDiagnoses, addDiagnosis, deleteDiagnosis, getPrescriptions, createPrescription } from '../../api/doctor'
import { getDrugs } from '../../api/pharmacy'
import request from '../../utils/request'

const patients = ref([])
const selectedRegister = ref(null)
const activeTab = ref('record')
const searchKey = ref('')
const chiefComplaint = ref('')
const aiResult = ref(null)
const aiLoading = ref(false)

const medicalRecord = reactive({ chiefComplaint: '', presentIllness: '', pastHistory: '', physicalExam: '', treatmentPlan: '' })
const diagnosisList = ref([])
const newDiagnosis = reactive({ diagnosisName: '', icdCode: '', isPrimary: 1 })
const prescriptionItems = ref([])
const drugList = ref([])

const filteredPatients = computed(() => searchKey.value ? patients.value.filter(p => p.patientName?.includes(searchKey.value)) : patients.value)
const totalAmount = computed(() => prescriptionItems.value.reduce((sum, item) => sum + (item.unitPrice * item.quantity), 0).toFixed(2))

const formatGuideline = (text) => text ? text.replace(/\n/g, '<br>').replace(/•/g, '&bull;') : ''

const handleKeydown = (e) => {
  if (e.code === 'Space' && aiResult.value && !e.target.closest('input, textarea, .el-select')) {
    e.preventDefault()
    applyAiResult()
  }
}

onMounted(() => { loadPatients(); loadDrugs(); document.addEventListener('keydown', handleKeydown) })
onUnmounted(() => { document.removeEventListener('keydown', handleKeydown) })

const loadPatients = async () => { const res = await getTodayPatients(); patients.value = res.data || [] }
const loadDrugs = async () => { const res = await getDrugs(); drugList.value = res.data || [] }

const selectPatient = async (patient) => {
  selectedRegister.value = patient
  activeTab.value = 'record'
  aiResult.value = null
  chiefComplaint.value = ''
  await loadRecord(patient.id)
  await loadDiagnoses(patient.id)
  await loadPrescriptions(patient.id)
}

const loadRecord = async (registerId) => {
  const res = await getMedicalRecord(registerId)
  if (res.data) { Object.assign(medicalRecord, res.data) }
  else { Object.assign(medicalRecord, { chiefComplaint: '', presentIllness: '', pastHistory: '', physicalExam: '', treatmentPlan: '' }) }
}

const loadDiagnoses = async (registerId) => { const res = await getDiagnoses(registerId); diagnosisList.value = res.data || [] }

const loadPrescriptions = async (registerId) => {
  const res = await getPrescriptions({ registerId })
  if (res.data && res.data.length > 0 && res.data[0].items) { prescriptionItems.value = res.data[0].items }
  else { prescriptionItems.value = [] }
}

// AI诊断
const doAiDiagnose = async () => {
  if (!chiefComplaint.value.trim()) { ElMessage.warning('请输入患者主诉'); return }
  aiLoading.value = true
  try {
    const res = await request.post('/doctor/ai/diagnose', { chiefComplaint: chiefComplaint.value })
    aiResult.value = res.data
    ElMessage.success('AI诊断完成，按空格键应用结果')
  } catch (e) { ElMessage.error('AI诊断失败') }
  finally { aiLoading.value = false }
}

// 应用AI诊断结果
const applyAiResult = () => {
  if (!aiResult.value) return
  // 填充病历
  medicalRecord.chiefComplaint = aiResult.value.chiefComplaint || chiefComplaint.value
  medicalRecord.presentIllness = aiResult.value.presentIllness || ''
  medicalRecord.pastHistory = aiResult.value.pastHistory || ''
  medicalRecord.physicalExam = aiResult.value.physicalExam || ''
  medicalRecord.treatmentPlan = aiResult.value.treatmentPlan || ''
  // 添加诊断
  newDiagnosis.diagnosisName = aiResult.value.diagnosisName
  newDiagnosis.icdCode = aiResult.value.icdCode
  newDiagnosis.isPrimary = 1
  // 填充处方
  if (aiResult.value.drugs && aiResult.value.drugs.length > 0) {
    prescriptionItems.value = aiResult.value.drugs.map(drug => {
      const matchedDrug = drugList.value.find(d => d.drugName === drug.drugName)
      return {
        drugId: matchedDrug?.id || null,
        drugName: drug.drugName,
        drugSpec: drug.drugSpec,
        unit: drug.unit,
        unitPrice: parseFloat(drug.unitPrice),
        quantity: parseInt(drug.days) || 1,
        dosage: drug.dosage,
        frequency: drug.frequency,
        usageMethod: drug.usageMethod
      }
    })
  }
  activeTab.value = 'record'
  ElMessage.success('AI诊断结果已应用')
}

const saveRecord = async () => {
  await saveMedicalRecord({ registerId: selectedRegister.value.id, patientId: selectedRegister.value.patientId, doctorId: selectedRegister.value.doctorId, ...medicalRecord })
  ElMessage.success('病历保存成功')
}

const addDiagnosisItem = async () => {
  if (!newDiagnosis.diagnosisName) { ElMessage.warning('请输入诊断名称'); return }
  await addDiagnosis({ registerId: selectedRegister.value.id, patientId: selectedRegister.value.patientId, doctorId: selectedRegister.value.doctorId, ...newDiagnosis })
  ElMessage.success('诊断添加成功')
  newDiagnosis.diagnosisName = ''; newDiagnosis.icdCode = ''
  loadDiagnoses(selectedRegister.value.id)
}

const removeDiagnosis = async (id) => { await deleteDiagnosis(id); ElMessage.success('删除成功'); loadDiagnoses(selectedRegister.value.id) }

const addDrugRow = () => { prescriptionItems.value.push({ drugId: null, drugName: '', drugSpec: '', unit: '', unitPrice: 0, quantity: 1, dosage: '', frequency: 'TID', usageMethod: '口服' }) }

const onDrugSelect = (row, drugId) => {
  const drug = drugList.value.find(d => d.id === drugId)
  if (drug) { row.drugName = drug.drugName; row.drugSpec = drug.spec; row.unit = drug.unit; row.unitPrice = drug.unitPrice }
}

const submitRx = async () => {
  if (prescriptionItems.value.length === 0) { ElMessage.warning('请添加药品'); return }
  await createPrescription({ registerId: selectedRegister.value.id, patientId: selectedRegister.value.patientId, doctorId: selectedRegister.value.doctorId, type: 'DRUG', items: prescriptionItems.value })
  ElMessage.success('处方开立成功')
  loadPrescriptions(selectedRegister.value.id)
}
</script>

<style scoped>
.workstation { display: flex; gap: 16px; height: calc(100vh - 140px); }
.patient-panel { width: 280px; flex-shrink: 0; }
.work-panel { flex: 1; overflow: auto; }
.full-height { height: 100%; }
.panel-header { display: flex; justify-content: space-between; align-items: center; }
.patient-list { max-height: calc(100vh - 280px); overflow-y: auto; }
.patient-card { display: flex; align-items: center; gap: 10px; padding: 10px; margin-bottom: 6px; border-radius: 8px; cursor: pointer; transition: all 0.2s; border: 1px solid #f0f0f0; }
.patient-card:hover { background: #f5f7fa; }
.patient-card.active { background: #ecf5ff; border-color: #409eff; }
.patient-avatar { width: 36px; height: 36px; border-radius: 50%; background: #e6f7ff; color: #1890ff; display: flex; align-items: center; justify-content: center; font-weight: bold; }
.patient-info { flex: 1; }
.patient-name { font-weight: 500; font-size: 14px; }
.patient-meta { display: flex; align-items: center; gap: 6px; font-size: 12px; color: #999; }
.patient-header { display: flex; align-items: center; gap: 12px; }
.header-name { font-size: 16px; font-weight: 500; }
.header-info { font-size: 12px; color: #999; }
.total-amount { text-align: right; margin-top: 16px; font-size: 16px; }
.total-amount span { color: #f56c6c; font-weight: bold; font-size: 20px; }
.ai-card { background: linear-gradient(135deg, #f5f7fa 0%, #e6f7ff 100%); border: 1px solid #b3d8ff; }
.ai-header { display: flex; align-items: center; gap: 8px; margin-bottom: 12px; }
.ai-actions { display: flex; gap: 10px; margin-bottom: 12px; }
.ai-result { margin-top: 12px; }
.guideline-card { margin-top: 12px; background: #fffbe6; border: 1px solid #ffe58f; }
.guideline-card :deep(.el-card__header) { background: #fff7e6; border-bottom: 1px solid #ffe58f; padding: 8px 16px; font-weight: 500; }
.guideline-content { font-size: 13px; line-height: 1.8; color: #333; }
.drugs-card { margin-top: 12px; }
.drugs-card :deep(.el-card__header) { background: #f6ffed; border-bottom: 1px solid #b7eb8f; padding: 8px 16px; font-weight: 500; }
</style>
