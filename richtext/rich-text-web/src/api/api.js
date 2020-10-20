import { getAction,deleteAction,putAction,postAction} from '@/api/manage'

const getUserPermissionByTokenOfSystem = (params)=>getAction("stickeronline/sys/permission/getUserPermissionByTokenOfSystem",params);
const queryTreeList = (params)=>getAction("stickeronline/sys/permission/queryTreeList",params);

export {
getUserPermissionByTokenOfSystem,
queryTreeList
}