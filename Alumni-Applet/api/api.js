// const baseurl='https://www.imapway.cn/alumniapi'
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
	getMemberList: baseurl+'/stickeronline/member/queryAllByUserId',
	getMemberById: baseurl+'/stickeronline/member/queryById',
	addMember: baseurl+'/stickeronline/member/add',
	updateMember: baseurl+'/stickeronline/member/edit',
	deleteMember: baseurl+'/stickeronline/member/delete',
	/**
	 * 关注校友
	 */
	getMemberAttentionList: baseurl+'/stickeronline/member/attention/list',
	getMemberAttentionById: baseurl+'/stickeronline/member/attention/queryById',
	addMemberAttention: baseurl+'/stickeronline/member/attention/add',
	updateMemberAttention: baseurl+'/stickeronline/member/attention/edit',
	deleteMemberAttention: baseurl+'/stickeronline/member/attention/delete',
	
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
	
	//获取组织成员列表
	gitAlumnusMemberList: baseurl+'/stickeronline/alumnusMember/list',
	//获取组织成员信息
	getAlumnusMemberById: baseurl+'/stickeronline/alumnusMember/queryById',
	
	/**
	 * 发现
	 */
	//获取发现列表
	getDiscoverList: baseurl+'/stickeronline/moments/list',
	//发布消息
	publishMoment: baseurl+'/stickeronline/moments/add',
	//点赞操作
	momentLike: baseurl+'/stickeronline/momentsLike/add',
	
	uploadFile: baseurl+'/file/upload'
	
}
export default api