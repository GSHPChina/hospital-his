import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, getUserInfo, logout as logoutApi } from '../api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)
  const menus = ref([])

  async function login(loginForm) {
    const res = await loginApi(loginForm)
    token.value = res.data.token
    userInfo.value = {
      userId: res.data.userId,
      username: res.data.username,
      realName: res.data.realName
    }
    localStorage.setItem('token', res.data.token)
    return res
  }

  async function fetchUserInfo() {
    const res = await getUserInfo()
    userInfo.value = res.data.user
    menus.value = res.data.menus
    return res
  }

  async function logout() {
    try {
      await logoutApi()
    } catch (e) {
      // ignore
    }
    token.value = ''
    userInfo.value = null
    menus.value = []
    localStorage.removeItem('token')
  }

  return {
    token,
    userInfo,
    menus,
    login,
    fetchUserInfo,
    logout
  }
})
