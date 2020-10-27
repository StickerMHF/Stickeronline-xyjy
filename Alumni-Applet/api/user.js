
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取模型
export const getUserInfo = (params) => {
    return getAction(api.UserInfo, params)
}
// 添加模型
export const getUserInfo1 = (params) => {
    return getAction(api.test, params)
}
// 获取微信用户openid
export const getUserOpenid = (params) => {
    return getAction(api.getUserOpenid, params)
}
// 获取微信用户
export const getWechatUserList = (params) => {
    return getAction(api.getWechatUserList, params)
}
//根据ID查询微信用户
export const getWechatUserById = (params) => {
    return getAction(api.getWechatUserById, params)
}
// 添加微信用户
export const addWechatUser = (params) => {
    return postAction(api.addWechatUser, params)
}
// 更新微信用户
export const updateWechatUser = (params) => {
    return putAction(api.updateWechatUser, params)
}
//删除微信用户
export const deleteWechatUser = (params) => {
    return deleteAction(api.deleteWechatUser, params)
}




