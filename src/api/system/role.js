import request from '@/utils/request'

export function searchRole(data) {
  return request.get(
    'role',
    { params: data }
  )
}

export function insertRole(data) {
  return request.post(
    'role',
    data
  )
}

export function updateRole(data) {
  return request.put(
    'role',
    data
  )
}

export function deleteRole(id) {
  return request.delete(
    `role/${id}`
  )
}
