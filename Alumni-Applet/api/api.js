const baseurl='http://localhost:8084'
const api = {
	test:baseurl+'/stickeronline/rich/text/list',
	getUserOpenid:baseurl+'/tb/wechat/getAppletUserInfo',
	/**
	 * 新闻接口
	 */
    getNewsList: baseurl+'/stickeronline/news/list',
	getNewsById: baseurl+'/stickeronline/news/queryById',
    addNews: baseurl+'/stickeronline/news/sdd',
    updateNews: baseurl+'/stickeronline/news/edit',
    deleteNews: baseurl+'/stickeronline/news/delete',
	/**
	 * 校友合作
	 */
	getCooperationList:baseurl+'/cooperation/list',
	getCooperationById: baseurl+'/sys/login',
	addCooperation: baseurl+'/sys/logout',
	updateCooperation: baseurl+'/auth/forge-password',
	deleteCooperation: baseurl+'/auth/register',
	
	/**
	 * 组织
	 */
	getAlumnusList: baseurl+'/stickeronline/alumnus/list',
	//资讯
	getAlumnusNewsList: baseurl+'/stickeronline/alumnusNews/list',
	//资讯详情
	getAlumnusNewsById: baseurl+'/stickeronline/alumnusNews/queryById',
	//获取活动列表
	getAlumnusActivityList: baseurl+'/stickeronline/alumnusActivity/list',
	//获取活动详情
	getAlumnusActivityById: baseurl+'/stickeronline/alumnusActivity/queryById',
	
	//获取相册列表
	getAlumnusPhotoList: baseurl+'/stickeronline/alumnusPhoto/list',
	//获取相册详情
	getAlumnusPhotoById: baseurl+'/stickeronline/alumnusPhoto/queryById',
	
	//获取组织成员信息
	getAlumnusMemberById: baseurl+'/stickeronline/alumnusMember/queryById'
	
}
export default api