import Mock from 'mockjs'
import loginAPI from './login'
import cooperationAPI from './cooperation'
const openMock = true

if (openMock) {
  // Mock.mock(/\/sys\/login/, 'post', loginAPI.loginbyUser)
  // Mock.mock(/\/stickeronline\/sys\/permission\/getUserPermissionByToken/, 'get', permissionAPI.getUserPermissionByToken)
  Mock.mock(/\/user\/info/, 'get', loginAPI.info)
  Mock.mock(/\/cooperation\/list/, 'get', cooperationAPI.getCooperationList)
}
export default Mock
