// const baseurl = 'https://www.imapway.cn/alumniapi'
const baseurl = 'http://192.168.0.160:8084'
const api = {
	test: baseurl + '/stickeronline/rich/text/list',
	getUserOpenid: baseurl + '/tb/wechat/getAppletUserInfo',
	/**
	 * 微信用户接口
	 */
	getWechatUserList: baseurl + '/stickeronline/wechat/users/list',
	getWechatUserById: baseurl + '/stickeronline/wechat/users/queryById',
	addWechatUser: baseurl + '/stickeronline/wechat/users/add',
	updateWechatUser: baseurl + '/stickeronline/wechat/users/edit',
	deletetWechatUser: baseurl + '/stickeronline/wechat/users/delete',
	queryAllByUserId: baseurl + '/stickeronline/wechat/users/queryAllByUserId',
	queryFansListByUserId: baseurl + '/stickeronline/wechat/users/queryFansListByUserId',
	queryAttentionListByUserId: baseurl + '/stickeronline/wechat/users/queryAttentionListByUserId',
	/**
	 * 关注校友
	 */
	getWechatUserAttentionList: baseurl + '/stickeronline/wechat/users/attention/list',
	getWechatUserAttentionById: baseurl + '/stickeronline/wechat/users/attention/queryById',
	addWechatUserAttention: baseurl + '/stickeronline/wechat/users/attention/add',
	updateWechatUserAttention: baseurl + '/stickeronline/wechat/users/attention/edit',
	deleteWechatUserAttention: baseurl + '/stickeronline/wechat/users/attention/delete',

	/**
	 * 新闻接口
	 */
	getNewsList: baseurl + '/stickeronline/news/list',
	getNewsById: baseurl + '/stickeronline/news/queryById',
	addNews: baseurl + '/stickeronline/news/add',
	updateNews: baseurl + '/stickeronline/news/edit',
	deleteNews: baseurl + '/stickeronline/news/delete',
	/**
	 * 师资力量
	 */
	getTeachersList: baseurl + '/stickeronline/teachers/list',
	getTeachersById: baseurl + '/stickeronline/teachers/queryById',
	addTeachers: baseurl + '/stickeronline/teachers/add',
	updateTeachers: baseurl + '/stickeronline/teachers/edit',
	deleteTeachers: baseurl + '/stickeronline/teachers/delete',
	/**
	 * 优秀校友
	 */
	getMemberList: baseurl + '/stickeronline/member/list',
	getMemberById: baseurl + '/stickeronline/member/queryById',
	addMember: baseurl + '/stickeronline/member/add',
	updateMember: baseurl + '/stickeronline/member/edit',
	deleteMember: baseurl + '/stickeronline/member/delete',


	/**
	 * 校友合作
	 */
	getCooperationList: baseurl + '/stickeronline/cooperation/list',
	getCooperationById: baseurl + '/stickeronline/cooperation/queryById',
	addCooperation: baseurl + '/stickeronline/cooperation/add',
	updateCooperation: baseurl + '/stickeronline/cooperation/edit',
	deleteCooperation: baseurl + '/stickeronline/cooperation/delete',
	/**
	 * 点亮全球
	 */
	addLightUpPersonnel: baseurl + '/stickeronline/signin/add',
	getPositionList: baseurl + '/stickeronline/signin/queryAll',
	updatePersonnelPosition: baseurl + '/stickeronline/signin/edit',
	/**
	 * 校庆祝福
	 */
	getBulletChatList: baseurl + '/stickeronline/barrage/list',
	sendBulletChat: baseurl + '/stickeronline/barrage/add',
	/**
	 * 照片征集
	 */
	getPhotoList: baseurl + '/stickeronline/photo/list',
	getUserList: baseurl + '/stickeronline/photo/getUserList',
	addPhoto: baseurl + '/stickeronline/photo/add',
	/**
	 * 组织
	 */
	getAlumnusList: baseurl + '/stickeronline/alumnus/list',
	//加入组织
	addAlumnusJoin: baseurl + '/stickeronline/alumnusJoin/add',
	//获取我加入的组织列表
	getAlumnusByuserId: baseurl + '/stickeronline/alumnus/getListByUserId',
	//资讯
	getAlumnusNewsList: baseurl + '/stickeronline/alumnusNews/list',
	//资讯详情
	getAlumnusNewsById: baseurl + '/stickeronline/alumnusNews/queryById',
	//获取活动列表
	getAlumnusActivityList: baseurl + '/stickeronline/alumnusActivity/list',
	//获取活动详情
	getAlumnusActivityById: baseurl + '/stickeronline/alumnusActivity/queryById',

	//获取相册列表
	getAlumnusPhotoList: baseurl + '/stickeronline/alumnusPhoto/list',
	//获取相册详情
	getAlumnusPhotoById: baseurl + '/stickeronline/alumnusPhoto/queryById',

	//获取组织成员列表
	gitAlumnusMemberList: baseurl + '/stickeronline/alumnusMember/list',
	//获取组织成员信息
	getAlumnusMemberById: baseurl + '/stickeronline/alumnusMember/queryById',

	/**
	 * 发现
	 */
	//获取发现列表
	getDiscoverList: baseurl + '/stickeronline/moments/list',
	//发布消息
	publishMoment: baseurl + '/stickeronline/moments/add',
	//点赞操作
	momentLike: baseurl + '/stickeronline/momentsLike/add',
	//发现评论
	momentComment: baseurl + '/stickeronline/momentsComment/add',

	uploadFile: baseurl + '/file/upload'

}
export default api