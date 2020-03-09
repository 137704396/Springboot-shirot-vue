import request from '@/utils/request'
import md5 from 'js-md5'
export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request.post(
    '/auth/logout'
  )
}

export function getInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}

// 修改密码
export function updateUserPassword(data) {
  return request.patch(
    '/auth/password',
    {
      password:data.password,
      oldPassword:md5(data.oldPassword)
    }
  )
}

