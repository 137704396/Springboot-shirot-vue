import request from '@/utils/request'

export function searchRouter() {
  return request.get(
    'router'
  )
}

export function insertRouter(data) {
  return request.post(
    'router',
    data
  )
}

export function updateRouter(data) {
  return request.put(
    'router',
    data
  )
}

export function deleteRouter(id) {
  return request.delete(
    `router/${id}`
  )
}
