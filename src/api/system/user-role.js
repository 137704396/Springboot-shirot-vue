import request from '@/utils/request'

export function getRelationRoleByUser(id) {
  return request.get(
    `relation/user/role/${id}`
  )
}

export function insertUserRole(userId,roleId) {
  return request.post(
    `relation/user/role/${userId}/${roleId}`
  )
}

export function deleteUserRole(userId,roleId) {
  return request.delete(
    `relation/user/role/${userId}/${roleId}`
  )
}
