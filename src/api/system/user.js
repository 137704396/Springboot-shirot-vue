import request from '@/utils/request'

export function searchUser(data) {
  return request.get(
    'user',
    { params: data }
  )
}

export function insertUser(data) {
  return request.post(
    'user',
    data
  )
}

export function updateUser(data) {
  return request.put(
    'user',
    data
  )
}

export function deleteUser(id) {
  return request.delete(
    `user/${id}`
  )
}

export function resetUserPassword(id) {
  return request.patch(
    `user/${id}`
  )
}

export function enableUser(id) {
  return request.patch(
    `user/enable/${id}`
  )
}

export function disableUser(id) {
  return request.patch(
    `user/disable/${id}`
  )
}
