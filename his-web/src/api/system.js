import request from '../utils/request'

// 科室
export function getDeptTree() {
  return request.get('/system/depts/tree')
}

export function getDepts() {
  return request.get('/system/depts')
}

export function addDept(data) {
  return request.post('/system/depts', data)
}

export function updateDept(id, data) {
  return request.put(`/system/depts/${id}`, data)
}

export function deleteDept(id) {
  return request.delete(`/system/depts/${id}`)
}

// 角色
export function getRoles() {
  return request.get('/system/roles')
}

export function addRole(data) {
  return request.post('/system/roles', data)
}

export function updateRole(id, data) {
  return request.put(`/system/roles/${id}`, data)
}

export function deleteRole(id) {
  return request.delete(`/system/roles/${id}`)
}

// 字典
export function getDicts() {
  return request.get('/system/dicts')
}

export function getDictItems(code) {
  return request.get(`/system/dicts/${code}/items`)
}

export function addDict(data) {
  return request.post('/system/dicts', data)
}

export function updateDict(id, data) {
  return request.put(`/system/dicts/${id}`, data)
}

export function deleteDict(id) {
  return request.delete(`/system/dicts/${id}`)
}

// 菜单
export function getMenuTree() {
  return request.get('/system/menus/tree')
}
