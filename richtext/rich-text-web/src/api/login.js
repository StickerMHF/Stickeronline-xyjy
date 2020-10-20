import api from './index'
import { axios ,getLoginUrl} from '@/utils/request'

export function login(parameter) {
  return axios({
    url: getLoginUrl()+api.Login,
    method: 'post',
    data: parameter
  })
}
export function logout(logoutToken) {
  return axios({
    url: getLoginUrl()+api.Logout,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'X-Access-Token':  logoutToken
    }
  })
}