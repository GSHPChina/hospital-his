import request from '../utils/request'

export function getTodayPatients() {
  return request.get('/doctor/workstation/today-patients')
}

export function getMedicalRecord(registerId) {
  return request.get('/doctor/medical-records', { params: { registerId } })
}

export function saveMedicalRecord(data) {
  return request.post('/doctor/medical-records', data)
}

export function getDiagnoses(registerId) {
  return request.get('/doctor/diagnoses', { params: { registerId } })
}

export function addDiagnosis(data) {
  return request.post('/doctor/diagnoses', data)
}

export function deleteDiagnosis(id) {
  return request.delete(`/doctor/diagnoses/${id}`)
}

export function getPrescriptions(params) {
  return request.get('/doctor/prescriptions', { params })
}

export function getPrescription(id) {
  return request.get(`/doctor/prescriptions/${id}`)
}

export function createPrescription(data) {
  return request.post('/doctor/prescriptions', data)
}

export function submitPrescription(id) {
  return request.put(`/doctor/prescriptions/${id}/submit`)
}
