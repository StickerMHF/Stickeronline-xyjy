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

export const getAlumnusPhotoList = (params) => {
	return getAction(api.getAlumnusPhotoList, params);
}