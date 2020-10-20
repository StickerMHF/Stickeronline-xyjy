import { UserLayout, TabLayout, RouteView, BlankLayout, PageView } from '@/components/layouts'

export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: '/user/login',
        name: 'login',
        component: () => import('@/views/user/Login')
      },
    ]
  },{
    path: '/index',
    name:'index',
    component: () => import('@/views/Index')
  },{
    path: '/componentIntroduce',
    name:'componentIntroduce',
    component: () => import('@/components/componentIntroduce/index')
  },

]
