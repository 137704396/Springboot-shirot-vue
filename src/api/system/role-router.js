import request from '@/utils/request'

export function getRouterIdsByRoleId(id) {
  return request.get(
    `relation/role/router/${id}`
  )
}

export function insertRoleRouter(roleId, routerId) {
  return request.post(
    `relation/role/router/${roleId}/${routerId}`
  )
}

export function deleteRoleRouter(roleId, routerId) {
  return request.delete(
    `relation/role/router/${roleId}/${routerId}`
  )
}
