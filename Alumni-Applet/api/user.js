
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




