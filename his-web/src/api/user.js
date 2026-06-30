import request from '../utils/request'

export function login(data) {
  return request.post('/auth/login', data)
}

export function getUserInfo() {
  return request.get('/auth/info')
}

export function logout() {
  return request.post('/auth/logout')
}

export function getUsers(params) {
  return request.get('/system/users', { params })
}

export function addUser(data) {
  return request.post('/system/users', data)
}

export function updateUser(id, data) {
  return request.put(`/system/users/${id}`, data)
}

export function deleteUser(id) {
  return request.delete(`/system/users/${id}`)
}
