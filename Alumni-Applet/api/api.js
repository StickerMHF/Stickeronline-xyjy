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
	getAlumnusList: baseurl+'/stickeronline/organization/list',
}
export default api