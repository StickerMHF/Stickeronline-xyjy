const baseurl='http://192.168.0.118:8084';
const api = {
	test:baseurl+'/rich/text/list',
	/**
	 * 新闻接口
	 */
    getNewsList: baseurl+'/sys/login',
	getNewsById: baseurl+'/sys/login',
    addNews: baseurl+'/sys/logout',
    updateNews: baseurl+'/auth/forge-password',
    deleteNews: baseurl+'/auth/register',
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