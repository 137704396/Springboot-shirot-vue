import request from '@/utils/request'

export function getRelationPermissionIdsByRoleId(roleId) {
  return request.get(
    `relation/role/permission/${roleId}`
  )
}

export function insertRolePermission(roleId, permissionId) {
  return request.post(
    `relation/role/permission/${roleId}/${permissionId}`
  )
}

export function deleteRolePermission(roleId, permissionId) {
  return request.delete(
    `relation/role/permission/${roleId}/${permissionId}`
  )
}
