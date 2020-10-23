import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取发现列表
export const getDiscoverList = (params) => {
    return getAction(api.getDiscoverList, params)
}