const baseurl='http://localhost:8084'
const api = {
	test:baseurl+'/stickeronline/rich/text/list',
	getUserOpenid:baseurl+'/tb/wechat/getAppletUserInfo',
	/**
	 * 新闻接口
	 */
    getNewsList: baseurl+'/stickeronline/news/list',
	getNewsById: baseurl+'/stickeronline/news/queryById',
    addNews: baseurl+'/stickeronline/news/add',
    updateNews: baseurl+'/stickeronline/news/edit',
    deleteNews: baseurl+'/stickeronline/news/delete',
	/**
	 * 师资力量
	 */
	getTeachersList: baseurl+'/stickeronline/teachers/list',
	getTeachersById: baseurl+'/stickeronline/teachers/queryById',
	addTeachers: baseurl+'/stickeronline/teachers/add',
	updateTeachers: baseurl+'/stickeronline/teachers/edit',
	deleteTeachers: baseurl+'/stickeronline/teachers/delete',
	/**
	 * 优秀校友
	 */
	getMemberList: baseurl+'/stickeronline/member/list',
	getMemberById: baseurl+'/stickeronline/member/queryById',
	addMember: baseurl+'/stickeronline/member/add',
	updateMember: baseurl+'/stickeronline/member/edit',
	deleteMember: baseurl+'/stickeronline/member/delete',
	/**
	 * 校友合作
	 */
	getCooperationList:baseurl+'/stickeronline/cooperation/list',
	getCooperationById: baseurl+'/stickeronline/cooperation/queryById',
	addCooperation: baseurl+'/stickeronline/cooperation/add',
	updateCooperation: baseurl+'/stickeronline/cooperation/edit',
	deleteCooperation: baseurl+'/stickeronline/cooperation/delete',
	
	/**
	 * 组织
	 */
	getAlumnusList: baseurl+'/stickeronline/organization/list',
}
export default api