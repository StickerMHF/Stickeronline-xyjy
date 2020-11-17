import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取发现列表
export const getDiscoverList = (params) => {
    return getAction(api.getDiscoverList, params)
}
//获取关注人的朋友圈
export const getDiscoverListByUserId = (params) => {
    return getAction(api.getDiscoverListByUserId, params)
}
//根据校友会ID获取朋友圈
export const getDiscoverListByAlumnusId = (params) => {
    return getAction(api.getDiscoverListByAlumnusId, params)
}
//发布接口
export const publishMoment = (params) => {
	return postAction(api.publishMoment, params);
}

//点赞
export const momentLike = (params) => {
	return postAction(api.momentLike, params);
}

//评论
export const momentComment = (params) => {
	return postAction(api.momentComment, params);
}

//上传文件
export const uploadFile = (params) => {
	return postAction(api.uploadFile, params)
}