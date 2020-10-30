
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取校友合作列表
export const getCooperationList = (params) => {
    return getAction(api.getCooperationList, params)
}

//根据ID查询校友合作
export const getCooperationById = (params) => {
    return getAction(api.getCooperationById, params)
}
// 添加校友合作
export const addCooperation = (params) => {
    return postAction(api.addCooperation, params)
}
// 更新校友合作
export const updateCooperation = (params) => {
    return putAction(api.updateCooperation, params)
}
//删除校友合作
export const deleteCooperation = (params) => {
    return deleteAction(api.deleteCooperation, params)
}


//获取人员位置列表
export const getPositionList = (params) => {
    return getAction(api.getPositionList, params)
}
//新增点亮人员
export const addLightUpPersonnel = (params) => {
    return postAction(api.addLightUpPersonnel, params)
}
//更新人员位置
export const updatePersonnelPosition = (params) => {
    return postAction(api.updatePersonnelPosition, params)
}

//获取弹幕列表
export const getBulletChatList = (params) => {
    return getAction(api.getBulletChatList, params)
}
//发送弹幕
export const sendBulletChat = (params) => {
    return postAction(api.sendBulletChat, params)
}

//获取相册列表
export const getPhotoList = (params) => {
    return getAction(api.getPhotoList, params)
}
//新增照片
export const addPhoto = (params) => {
    return postAction(api.addPhoto, params)
}
//获取用户列表
export const getUserList = (params) => {
    return getAction(api.getUserList, params)
}

