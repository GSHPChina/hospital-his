import request from '../utils/request'

export function getDrugs(params) {
  return request.get('/pharmacy/drugs', { params })
}

export function addDrug(data) {
  return request.post('/pharmacy/drugs', data)
}

export function updateDrug(id, data) {
  return request.put(`/pharmacy/drugs/${id}`, data)
}

export function deleteDrug(id) {
  return request.delete(`/pharmacy/drugs/${id}`)
}

export function getStocks() {
  return request.get('/pharmacy/stocks')
}

export function getStockWarnings() {
  return request.get('/pharmacy/stocks/warnings')
}

export function inboundStock(params) {
  return request.post('/pharmacy/stocks/inbound', null, { params })
}

export function getPendingDispensings() {
  return request.get('/pharmacy/dispensings/pending')
}

export function dispense(id) {
  return request.post(`/pharmacy/dispensings/${id}/dispense`)
}
