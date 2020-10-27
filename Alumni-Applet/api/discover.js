import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取发现列表
export const getDiscoverList = (params) => {
    return getAction(api.getDiscoverList, params)
}

//发布接口
export const publishMoment = (params) => {
	return postAction(api.publishMoment, params);
}

//点赞
export const momentLike = (params) => {
	return postAction(api.momentLike, params);
}

//上传文件
export const uploadFile = (params) => {
	return postAction(api.uploadFile, params)
}