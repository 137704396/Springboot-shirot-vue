import request from '@/utils/request'

export function getPermissionList(routerId) {
  return request.get(
    `permission/${routerId}`
  )
}

export function insertPermission(data) {
  return request.post(
    'permission',
    data
  )
}
export function updatePermission(data) {
  return request.put(
    'permission',
    data
  )
}


export function delPermission(id) {
  return request.delete(
    `permission/${id}`
  )
}
