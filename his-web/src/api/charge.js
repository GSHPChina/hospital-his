import request from '../utils/request'

export function getPendingFees() {
  return request.get('/charge/fees/pending')
}

export function getFees() {
  return request.get('/charge/fees')
}

export function charge(data) {
  return request.post('/charge/fees', data)
}

export function refund(id) {
  return request.post(`/charge/fees/${id}/refund`)
}
