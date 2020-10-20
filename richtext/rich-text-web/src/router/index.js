import Vue from 'vue'
import Router from 'vue-router'
import { constantRouterMap } from '@/config/router.config'

Vue.use(Router)

const VueRouterPush = Router.prototype.push 
Router.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

//replace
const VueRouterReplace = Router.prototype.replace
Router.prototype.replace = function replace (to) {
  return VueRouterReplace.call(this, to).catch(err => err)
}
export default new Router({
  mode: 'history',
  // base: process.env.BASE_URL,
  base: '/',

  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})