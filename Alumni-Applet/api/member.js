
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取优秀校友
export const getMemberList = (params) => {
    return getAction(api.getMemberList, params)
}
//根据ID查询优秀校友
export const getMemberById = (params) => {
    return getAction(api.getMemberById, params)
}
// 添加优秀校友
export const addMember = (params) => {
    return postAction(api.addMember, params)
}
// 更新优秀校友
export const updateMember = (params) => {
    return putAction(api.updateMember, params)
}
//删除优秀校友
export const deleteMember = (params) => {
    return deleteAction(api.deleteMember, params)
}




