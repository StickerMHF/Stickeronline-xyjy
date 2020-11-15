import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

//获取校友组织列表
export const getAlumnusList = (params) => {
	return getAction(api.getAlumnusList, params)
}

//获取资讯列表
export const getAlumnusNewsList = (params) => {
	return getAction(api.getAlumnusNewsList, params);
}
//根据ID获取资讯详情
export const getAlumnusNewsById = (params) =>{
	return getAction(api.getAlumnusNewsById, params);
}

//获取活动列表
export const getAlumnusActivityList = (params) => {
	return getAction(api.getAlumnusActivityList, params);
}

//获取活动详情
export const getAlumnusActivityById = (params) => {
	return getAction(api.getAlumnusActivityById, params);
}

//获取相册列表
export const getAlumnusPhotoList = (params) => {
	return getAction(api.getAlumnusPhotoList, params);
}

//获取相册详情
export const getAlumnusPhotoById = (params) => {
	return getAction(api.getAlumnusPhotoById, params);
}

//获取成员列表
export const gitAlumnusMemberList = (params) => {
	return getAction(api.gitAlumnusMemberList, params);
}
//获取用户会长身份接口
export const queryPresidentByUserId = (params) => {
	return getAction(api.queryPresidentByUserId, params);
}
//申请会长接口
export const applyByUserId = (params) => {
	return postAction(api.applyByUserId, params);
}
//获取成员信息
export const getAlumnusMemberById = (params) => {
	return getAction(api.getAlumnusMemberById, params);
}

//加入组织
export const addAlumnusJoin = (params) => {
	return postAction(api.addAlumnusJoin, params);
}

//退出组织
export const delAlumnusJoin = (params) => {
	return postAction(api.delAlumnusJoin, params);
}

//获取加入组织列表
export const getAlumnusByuserId = (params) => {
	return getAction(api.getAlumnusByuserId, params);
}

// 获取
export const getActivityApplyList = (params) => {
    return getAction(api.getActivityApplyList, params)
}
//根据ID查询
export const getActivityApplyById = (params) => {
    return getAction(api.getActivityApplyById, params)
}
// 添加
export const addActivityApply = (params) => {
    return postAction(api.addActivityApply, params)
}
// 更新
export const updateActivityApply = (params) => {
    return putAction(api.updateActivityApply, params)
}
//删除
export const deleteActivityApply = (params) => {
    return deleteAction(api.deleteActivityApply, params)
}

//获取班级列表
export const getClassGrade = (params) => {
	return getAction(api.getClassGrade, params)
}

//发布公告
export const sendNotice = (params) => {
	return postAction(api.sendNotice, params)
}
//发布图文
export const sendWeChat = (params) => {
	return postAction(api.sendWeChat, params)
}
//发布活动
export const sendActivity = (params) => {
	return postAction(api.sendActivity, params)
}
