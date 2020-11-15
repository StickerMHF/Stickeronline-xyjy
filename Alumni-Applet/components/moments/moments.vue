<template>
	<view>
		<!-- <view class="publishData">
			<image class="image" src="http://cdxyh.stickeronline.cn/FhDX9UB6L_r8YaQ6gqewXMPBCIqG" @click="navigatorTo"></image>
		</view> -->
		<view class="cu-card dynamic" :class="isCard?'no-card':''" v-for="(moment,i) in listArray" :key="i">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg" @click="avatarHandler(moment.userId)" :style="'background-image:url('+moment.photo+');'"></view>
						<view class="content flex-sub">
							<view class="text-title">{{moment.username}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{moment.publishDate}}
							</view>
						</view>
					</view>
				</view>
				<view class="text-content details-list">
					{{moment.content}}
				</view>
				<view class="grid flex-sub padding-lr details-list" :class="isCard?'col-3 grid-square':'col-1'">
					<view class="bg-img" :class="isCard?'':'only-img'" :style="'background-image:url('+item.url+');'" @tap="clickPic(moment.images, index)"
					 v-for="(item,index) in moment.images" :key="index">
					</view>
				</view>
				<view class="text-gray text-sm text-right padding comment_icon" v-if="isComment">
					<!-- <text class="cuIcon-attentionfill margin-lr-xs"></text> {{moment.viewCount}} -->
					<text class="cuIcon-appreciatefill margin-lr-xs" :class="moment.islike=='like'?' active':''" @click="momentLike(i)"></text>
					{{moment.likeCount}}
					<text class="cuIcon-messagefill margin-lr-xs" @click="commentInput(i)"></text> {{moment.commentList.length}}
				</view>
				<view class="cu-list menu-avatar comment solids-top details-list" v-if="isComment">
					<view class="cu-item comment-body" v-for="comment in moment.commentList">
						<view class="cu-avatar round" @click="avatarHandler(comment.userId)" :style="'background-image:url('+comment.userPhoto+');'"></view>
						<view class="content">
							<view class="text-grey">{{comment.userName}}</view>
							<view class="text-gray text-content text-df">
								{{comment.content}}
							</view>
							
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- <view class="discover-comment" v-show="true"> -->
			<!-- <comment></comment> -->
			<!-- <view class="weui-cells weui-cells_after-title">
				<view class="weui-cell weui-cell_input">
					<input ref="commentdom" class="uni-input comment-input" :focus="commentShow" type="text" placeholder="评论" @blur="bindBlurEvent"
					 @focus="bindFocusEvent" v-model="commentText" confirm-type="send" />
					<button @click="sumbitComment">发送</button>
				</view>
			</view> -->
		<!-- </view> -->
	</view>
</template>

<script>
	import {
		momentLike,
		momentComment
	} from '@/api/discover.js'

	export default {
		name: 'moments',
		components:{
			// comment
		},
		data() {
			return {
				commentShow: false,
				ifshow: false,
				commentText: '',
				isCard: true,
				likeParams: {
					momentId: '',
					userId: '',
					userName: '',
					userPhoto: '',
					islike: 'unlike'
				},
				commentParams: {
					content: '',
					momentId: '',
					userId: '',
					userPhoto: '',
					userName: '',
					fid: '-1'

				},
				listArray: [],
				inputValue: '',
				nowComment: ''
			}
		},
		watch: {
			list() {
				this.listArray = this.list;
			}
		},
		props: {
			// fatherLikeMethod: {
			// 	type: Function,
			// 	default: null
			// },
			// fatherCommentMethod: {
			// 	type: Function,
			// 	dafult: null
			// },
			isComment:{
				type: Boolean,
				default: function(e) {
					return true
				}
			},
			list: {
				type: Array,
				default: function(e) {
					return [{
						username: "凯尔",
						publishDate: "2019年12月3日",
						photo: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
						content: "折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来终结！",
						images: [{
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}],
						viewCount: 20,
						likeCount: 10,
						commentCount: 5,
						commentList: [{
							url: "https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png",
							name: "莫甘娜",
							content: "凯尔，你被自己的光芒变的盲目。",
							userId: "1",
							commentTime: "2018年12月4日",
							replyList: [{
								name: "凯尔",
								content: "妹妹，你在帮他们给黑暗找借口吗?",
								userId: "",
								replyTime: ""
							}, {
								name: "凯尔",
								content: "妹妹，你在帮他们给黑暗找借口吗?",
								userId: "",
								replyTime: ""
							}]
						}, {
							url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
							name: "凯尔",
							content: "妹妹，如果不是为了飞翔，我们要这翅膀有什么用?",
							userId: "2",
							commentTime: "2018年12月4日",
							replyList: [{
								name: "莫甘娜",
								content: "如果不能立足于大地，要这双脚又有何用?",
								userId: "",
								replyTime: ""
							}]
						}]
					}];
				}
			}
		},
		methods: {
			//点击查看大图
			clickPic(imgPreviewPicList, index) {
				uni.removeStorageSync("imgPreviewPicList");
				uni.removeStorageSync("currentImgIndex");
				uni.setStorageSync("currentImgIndex", index);
				uni.setStorageSync("imgPreviewPicList", imgPreviewPicList);
				uni.navigateTo({
					url: '/pages/imgPreview/imgPreview'
				});
			},
			bindBlurEvent() {
				console.log('失去焦点');
				this.commentShow = false;
			},
			bindFocusEvent() {
				console.log('获得焦点');
			},
			avatarHandler(openid){debugger
				if(openid!=null){
					uni.navigateTo({
						url:"/pages/personal/userDetail/userDetail?userId="+openid
					})
				}
			},
			//点赞操作
			momentLike(index) {
				if (this.list[index].islike == 'like') {
					this.list[index].islike = 'unlike';
					this.list[index].likeCount = this.list[index].likeCount - 1;
					this.likeParams.islike = 'unlike';
				} else {
					this.list[index].islike = 'like';
					this.list[index].likeCount = this.list[index].likeCount + 1;
					this.likeParams.islike = 'like';
				}
				//提交数据
				// if(this.fatherLikeMethod){
				this.likeParams.momentId = this.list[index].id;
				//获取用户信息
				this.likeParams.userId = uni.getStorageSync('openid');
				let userInfo = uni.getStorageSync('userInfo');
				if (userInfo) {
					this.likeParams.userName = userInfo.nickName;
					this.likeParams.userPhoto = userInfo.avatarUrl;
					// this.fatherLikeMethod(this.likeParams);
					momentLike(this.likeParams).then(data => {
						console.log(data)
					});

				} else {
					//跳转页面 
					wx.navigateTo({
						url: '/pages/login/login'
					})
				}
				// }		
			},
			//相册评论
			commentInput(index) {
				this.commentParams.momentId = this.list[index].id;
				this.nowComment = this.list[index].id;
				this.$emit("comment",this.nowComment)
			}
		},

	}
</script>

<style lang="scss">
	.comment-body{
		    height: 65px;
	}
	.text-title{
		font-weight: bold;
	}
	.details-list{
		    // width: 340px;
		    margin-left: 50px;
	}
	.discover-comment {
		position: fixed;
		z-index: 999;
		bottom: 0;
		background: #f0f3f2;
		width: 100%;
	}

	.active {
		color: red;
	}

	.comment-input {
		width: 80%;
		float: left;
		height: 45px;
		size: 10px;
		font-size: 16px;
		margin-left: 10px;
	}

	.weui-cell_input {
		height: 100%;
	}
	// .discover-comment{
	// 	position: absolute;
	// 	top: 0;
	// 	bottom: 0;
	// 	width: 100%;
	// 	background: #cecece;
	// }
.comment_icon{
	font-size: 15px;
	    padding: 5px;
}
	// .publishData{
	// 	    position: absolute;
	// 	    z-index: 99;
	// 		right: 10px;
	// 		margin-top: 5px;
	// }
	// .image{
	// 	height: 40px;
	// 	width: 40px;
	// }
</style>
