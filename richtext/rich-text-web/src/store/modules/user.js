import Vue from 'vue'
import { login, logout } from "@/api/login"
import { ACCESS_TOKEN, USER_NAME, USER_INFO, USER_AUTH, SYS_BUTTON_AUTH } from "@/store/mutation-types"
import { welcome } from "@/utils/util"
import { queryPermissionsByUser,getUserPermissionByTokenOfSystem,queryTreeList } from '@/api/api'
const user = {
  state: {
    token: '',
    username: '',
    realname: '',
    welcome: '',
    avatar: '',
    btns:[],
    permissionList: [],
    systemId:'',
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { username, realname, welcome }) => {
      state.username = username
      state.realname = realname
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_PERMISSIONLIST: (state, permissionList) => {
      state.permissionList = permissionList
    },
    SET_SYSTEMID: (state, systemId) => {
      state.systemId = systemId
    },
    SYS_BUTTON_AUTH:(state, btns)=>{
      state.btns = btns
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          if (response.code == '200') {
            const result = response.result
            Vue.ls.set(ACCESS_TOKEN, result.token, 7 * 24 * 60 * 60 * 1000)
            Vue.ls.set(USER_NAME, result.userInfo.username, 7 * 24 * 60 * 60 * 1000)
            Vue.ls.set(USER_INFO, result.userInfo, 7 * 24 * 60 * 60 * 1000)
            resolve(response)
          } else {
            reject(response)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取菜单列表
    GetPermissionList({ commit,state }) {
      return new Promise((resolve, reject) => {
        let v_token = Vue.ls.get(ACCESS_TOKEN);
        let params = { token: v_token ,id:Vue.ls.get('SYSTEMID')};
        getUserPermissionByTokenOfSystem(params).then(response => {
          const menuData = response.result.menu;
          const authData = response.result.auth;
          const allAuthData = response.result.btnAuth;
          const btnAuth=[]
          response.result.btnAuth.map(res=>{
            btnAuth.push(res.btnClass)
          })
          sessionStorage.setItem(USER_AUTH, JSON.stringify(authData));
          sessionStorage.setItem(SYS_BUTTON_AUTH,allAuthData);
          if (menuData && menuData.length > 0) {
            commit('SET_PERMISSIONLIST',menuData)
            // ******************将菜单放到vux里 每次刷新的时候去请求  
            // 系统id 存到store里  每次进入index时 同时清空这两个
            // 进哪个子系统请求那个子系统权限
          } else {
            Vue.ls.set('SYSTEMID', '')
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    

    // 登出
    Logout({ commit, state }) {
      return new Promise((resolve) => {
        let logoutToken = state.token;
        commit('SET_TOKEN', '')
        commit('SET_PERMISSIONLIST', [])
        Vue.ls.remove(ACCESS_TOKEN)
        logout(logoutToken).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        })
      })
    },
    // 退出子系统
    LogoutSubSyatem({ commit, state }) {
      return new Promise((resolve) => {
        // commit('SYSTEMID', '')
        Vue.ls.remove('SYSTEMID')
        // commit('SET_PERMISSIONLIST', [])
        resolve()
      })
    },
     // 进入子系统
     LoginSubSyatem({ commit },systemid) {
      return new Promise((resolve) => {
        Vue.ls.set('SYSTEMID', systemid, 7 * 24 * 60 * 60 * 1000)
        resolve()
      })
    },

  }
}

export default user