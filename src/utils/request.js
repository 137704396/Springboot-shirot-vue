import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import Qs from 'qs'
import md5 from 'js-md5'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000
})
// 设置post请求默认的请求头
// request interceptor
service.interceptors.request.use(
  config => {
    if (config.data) {
      // 遇到密码则将密码加密传输，也就是说，普通的信息传递最好不要有password字段
      if (config.data.password) {
        config.data.password = md5(config.data.password)
      }
      config.data = Qs.stringify(config.data, { arrayFormat: 'repeat' })
    }
    if (store.getters.token) {
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    const res = response.data
    // 刷新token
    if (res.data && res.data.token) {
      store.dispatch('user/saveToken', res.data.token)
    }

    // 1/2代表正常，否则代表失败,todo:登录失败等还没写
    if (res.code === 1 || res.code === 2) {
      if (res.code === 2) {
        Message.success(res.message)
      }
      return res.data
    } else {
      Message({
        message: res.message || 'error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(res.message || 'error')
    }
  },
  error => {
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
