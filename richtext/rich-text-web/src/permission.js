import Vue from 'vue'
import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import notification from 'ant-design-vue/es/notification'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { generateIndexRouter,existsRouter } from "@/utils/util"
NProgress.configure({ showSpinner: false }) // NProgress Configuration
import { asyncRouterMap} from "@/config/router.config"
const whiteList = ['/user/login','/componentIntroduce'] // no redirect whitelist

router.beforeEach((to, from, next) => {
  console.log(Vue.ls.get(ACCESS_TOKEN))
  NProgress.start()
  if (Vue.ls.get(ACCESS_TOKEN)) {
    if (to.path === '/user/login') {
      next({ path: '/index' })
      NProgress.done()
    } else {
      if(to.path === '/index'){
        next()
      }else{
        console.log(store.getters.permissionList)
        if(!store.getters.permissionList&&!Vue.ls.get('SYSTEMID')){
          next({ path: '/index'})
        }else if(Vue.ls.get('SYSTEMID')&&store.getters.permissionList.length===0){
          store.dispatch('GetPermissionList').then(res => {
                const menuData = res.result.menu;
                let whitelist = asyncRouterMap;
                for (let item in whitelist) {
                  menuData.push(whitelist[item]);
                };
                console.log(res.message)
                if (menuData === null || menuData === "" || menuData === undefined) {
                  return;
                }
                let constRoutes = [];
                constRoutes = generateIndexRouter(menuData);
                store.dispatch('UpdateAppRouter', { constRoutes }).then(() => {
                  router.addRoutes(store.getters.addRouters)
                  const redirect = decodeURIComponent(from.query.redirect || to.path)
                  if (to.path === redirect) {
                    next({ ...to, replace: true })
                  } else {
                    next({ path: redirect })
                  }
                })
              }).catch((e) => {
                      console.log(e);
                       notification.error({
                         message: '系统提示',
                         description: '请求用户信息失败，请重试！'
                       })
                      store.dispatch('Logout').then(() => {
                        next({ path: '/user/login', query: { redirect: to.fullPath } })
                      })
                    })
        }else if(Vue.ls.get('SYSTEMID')&&store.getters.permissionList.length>0){
          next()
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next({ path: '/user/login', query: { redirect: to.fullPath } })
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
