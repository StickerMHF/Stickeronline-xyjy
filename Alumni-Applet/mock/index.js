import Mock from 'mockjs'
import api from '@/api/api'
import loginAPI from './login'
import cooperationAPI from './cooperation'
const openMock = true

if (openMock) {
  // Mock.mock(/\/sys\/login/, 'post', loginAPI.loginbyUser)
  // Mock.mock(/\/stickeronline\/sys\/permission\/getUserPermissionByToken/, 'get', permissionAPI.getUserPermissionByToken)
  Mock.mock(api.test, 'get', loginAPI.info)
  Mock.mock(api.getCooperationList, 'get', cooperationAPI.getCooperationList)
}
export default Mock
