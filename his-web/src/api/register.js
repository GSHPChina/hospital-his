import request from '../utils/request'

export function getPatients(params) {
  return request.get('/outpatient/patients', { params })
}

export function addPatient(data) {
  return request.post('/outpatient/patients', data)
}

export function getRegisters() {
  return request.get('/outpatient/registers')
}

export function register(data) {
  return request.post('/outpatient/registers', data)
}

export function cancelRegister(id) {
  return request.put(`/outpatient/registers/${id}/cancel`)
}

export function getSchedules(params) {
  return request.get('/outpatient/schedules', { params })
}
