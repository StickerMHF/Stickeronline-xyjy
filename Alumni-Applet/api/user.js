
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'


// 获取模型
export const getUserInfo = (params) => {
    return getAction('/user/info', params)
}
// 添加模型
export const getUserInfo1 = (params) => {
    return getAction('http://192.168.0.50:8084/rich/text/list', params)
}




