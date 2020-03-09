import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人信息', icon: 'user', noCache: true }
      }
    ]
  }
]

export const asyncRoutes = [
  {
  //   path: '/system',
  //   component: Layout,
  //   redirect: 'noRedirect',
  //   name: 'System',
  //   alwaysShow: true,
  //   meta: { title: '系统管理', icon: 'system' },
  //   children: [
  //     {
  //       path: 'router',
  //       name: 'Router',
  //       component: () => import('@/views/system/router'),
  //       meta: { title: '路由管理' }
  //     },
  //     {
  //       path: 'button',
  //       name: 'Button',
  //       component: () => import('@/views/system/button'),
  //       meta: { title: '按钮管理' }
  //     },
  //     {
  //       path: '/system/user-manage',
  //       component: () => import('@/views/system/user-manage'),
  //       redirect: 'noRedirect',
  //       name: 'UserManage',
  //       alwaysShow: true,
  //       meta: { title: '用户管理' },
  //       children: [
  //         {
  //           path: 'adminuser',
  //           name: 'AdminUser',
  //           component: () => import('@/views/system/user-manage/adminuser'),
  //           meta: { title: '管理员' }
  //         },
  //         {
  //           path: 'adminuser/add',
  //           name: 'AdminUserAdd',
  //           component: () => import('@/views/system//user-manage/adminuser/add'),
  //           hidden: true,
  //           meta: { title: '管理员--新增' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'auth-manage',
  //       name: 'AuthManage',
  //       component: () => import('@/views/system/auth-manage'),
  //       alwaysShow: true,
  //       redirect: 'noRedirect',
  //       meta: { title: '权限管理' },
  //       children: [
  //         {
  //           path: 'role',
  //           name: 'Role',
  //           component: () => import('@/views/system/auth-manage/role'),
  //           meta: { title: '角色管理' }
  //         },
  //         {
  //           path: 'role/add',
  //           name: 'RoleAdd',
  //           component: () => import('@/views/system/auth-manage/role/add'),
  //           hidden: true,
  //           meta: { title: '角色管理--新增' }
  //         },
  //         {
  //           path: 'role/edit',
  //           name: 'RoleEdit',
  //           component: () => import('@/views/system/auth-manage/role/edit'),
  //           hidden: true,
  //           meta: { title: '角色管理-修改' }
  //         },
  //         {
  //           path: 'auth',
  //           name: 'Auth',
  //           component: () => import('@/views/system/auth-manage/auth'),
  //           meta: { title: '权限列表' }
  //         }
  //       ]
  //     }
  //   ]
  }
]
export const errorRoutes = [
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
