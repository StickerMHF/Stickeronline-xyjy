
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取新闻
export const getNewsList = (params) => {
    return getAction(api.getNewsList, params)
}
//根据ID查询新闻
export const getNewsById = (params) => {
    return getAction(api.getNewsById, params)
}
// 添加新闻
export const addNews = (params) => {
    return postAction(api.addNews, params)
}
// 更新新闻
export const updateNews = (params) => {
    return putAction(api.updateNews, params)
}
//删除新闻
export const deleteNews = (params) => {
    return deleteAction(api.deleteNews, params)
}




