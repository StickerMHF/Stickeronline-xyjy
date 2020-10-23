
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取新闻
export const getTeachersList = (params) => {
    return getAction(api.getTeachersList, params)
}
//根据ID查询新闻
export const getTeachersById = (params) => {
    return getAction(api.getTeachersById, params)
}
// 添加新闻
export const addTeachers = (params) => {
    return postAction(api.addTeachers, params)
}
// 更新新闻
export const updateTeachers = (params) => {
    return putAction(api.updateTeachers, params)
}
//删除新闻
export const deleteTeachers = (params) => {
    return deleteAction(api.deleteTeachers, params)
}




