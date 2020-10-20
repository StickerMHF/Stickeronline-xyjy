
import { getAction,deleteAction,putAction,postAction} from '@/api/manage'


// 获取校友合作列表
export const getCooperationList = (params) => {
    return getAction('/cooperation/list', params)
}



