<template>
	<view>
		<!-- <view class="publishData">
			<image class="image" src="http://cdxyh.stickeronline.cn/FhDX9UB6L_r8YaQ6gqewXMPBCIqG" @click="navigatorTo"></image>
		</view> -->
		<view class="cu-card dynamic" :class="isCard?'no-card':''" v-for="(moment,i) in listArray" :key="i">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg" :style="'background-image:url('+moment.photo+');'"></view>
						<view class="content flex-sub">
							<view>{{moment.username}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{moment.publishDate}}
							</view>
						</view>
					</view>
				</view>
				<view class="text-content">
					{{moment.content}}
				</view>
				<view class="grid flex-sub padding-lr" :class="isCard?'col-3 grid-square':'col-1'">
					<view class="bg-img" :class="isCard?'':'only-img'" :style="'background-image:url('+item.url+');'"
						@tap="clickPic(moment.images, index)"  v-for="(item,index) in moment.images" :key="index">
					</view>
				</view>
				<view class="text-gray text-sm text-right padding">
					<text class="cuIcon-attentionfill margin-lr-xs"></text> {{moment.viewCount}}
					<text class="cuIcon-appreciatefill margin-lr-xs" :class="moment.status=='like'?' active':''" @click="momentLike(i)"></text> {{moment.likeCount}}
					<text class="cuIcon-messagefill margin-lr-xs" v-show="moment.commentList" @click="commentInput(i)"></text> {{moment.commentCount}}
				</view>
				<view class="cu-list menu-avatar comment solids-top">
					<view class="cu-item" v-for="comment in moment.commentList">
						<view class="cu-avatar round" :style="'background-image:url('+comment.url+');'"></view>
						<view class="content">
							<view class="text-grey">{{comment.name}}</view>
							<view class="text-gray text-content text-df">
								{{comment.content}}
							</view>
							<view v-for="reply in comment.replyList" class="bg-grey padding-sm radius margin-top-sm  text-sm">
								<view class="flex" >
									<view>{{reply.name}}:</view>
									<view class="flex-sub">  {{reply.content}}</view>
								</view>
							</view>
							<view class="margin-top-sm flex justify-between">
								<view class="text-gray text-df">{{comment.commentTime}}</view>
								<view>
									<!-- <text class="cuIcon-appreciatefill text-red"></text> -->
									<text class="cuIcon-messagefill text-gray margin-left-sm" @click="commentInput2(comment)"></text>
								</view>
							</view>
						</view>
					</view>					
				</view>
			</view>
		</view>
		<view class="discover-comment" v-show="commentShow">
			<view class="weui-cells weui-cells_after-title">
			  <view class="weui-cell weui-cell_input">
				<input ref="commentdom" class="uni-input comment-input" :focus="commentShow" type="text" placeholder="评论" @blur="bindBlurEvent" @focus="bindFocusEvent" v-model="commentText" confirm-type="send"/>
				<button @click="sumbitComment">发送</button>
			  </view>
			</view>
		</view>	
	</view>
</template>

<script>
	export default {
		name: 'moments',
		data() {
			return {
				commentShow: false,
				ifshow: false,
				commentText: '',
				isCard:true,
				likeParams: {
					momentId: '',
					userId: '',
					userName: '',
					userPhoto: '',
					status: 'unlike'
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
		watch:{
			list(){
				this.listArray = this.list;
			}
		},
		props: {
			fatherLikeMethod: {
				type: Function,
				default: null
			},
			fatherCommentMethod: {
				type: Function,
				dafult: null
			},
			list: {
				type: Array,
				default: function(e) {
					return [{
						username:"凯尔",
						publishDate:"2019年12月3日",
						photo:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
						content:"折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来终结！",
						images:[{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
						}],
						viewCount:20,
						likeCount:10,
						commentCount:5,
						commentList:[{
							url:"https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png",
							name:"莫甘娜",
							content:"凯尔，你被自己的光芒变的盲目。",
							userId:"1",
							commentTime:"2018年12月4日",
							replyList:[{
								name:"凯尔",
								content:"妹妹，你在帮他们给黑暗找借口吗?",
								userId:"",
								replyTime:""
							},{
								name:"凯尔",
								content:"妹妹，你在帮他们给黑暗找借口吗?",
								userId:"",
								replyTime:""
							}]
						},{
							url:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
							name:"凯尔",
							content:"妹妹，如果不是为了飞翔，我们要这翅膀有什么用?",
							userId:"2",
							commentTime:"2018年12月4日",
							replyList:[{
								name:"莫甘娜",
								content:"如果不能立足于大地，要这双脚又有何用?",
								userId:"",
								replyTime:""
							}]
						}]
					}];
				}
			}
		},
		methods:{
			//点击查看大图
			clickPic(imgPreviewPicList, index) {
				uni.removeStorageSync("imgPreviewPicList");
				uni.removeStorageSync("currentImgIndex");
				uni.setStorageSync("currentImgIndex",index);
				uni.setStorageSync("imgPreviewPicList",imgPreviewPicList);
				uni.navigateTo({
					url: '/pages/imgPreview/imgPreview'
				});
			},
			bindBlurEvent(){
				console.log('失去焦点');				
				this.commentShow = false;
			},
			bindFocusEvent(){
				console.log('获得焦点');
			},
			//点赞操作
			momentLike(index){
				if(this.list[index].status == 'like'){
					this.list[index].status = 'unlike';
					this.list[index].likeCount = this.list[index].likeCount-1;
					this.likeParams.status = 'unlike';
				} else {
					this.list[index].status = 'like';
					this.list[index].likeCount = this.list[index].likeCount+1;
					this.likeParams.status = 'like';
				}
				//提交数据
				if(this.fatherLikeMethod){
					this.likeParams.momentId = this.list[index].id;
					//获取用户信息
					this.likeParams.userId = uni.getStorageSync('openid');
					let userInfo = uni.getStorageSync('userInfo');					
					if(userInfo){
						this.likeParams.userName = userInfo.nickName;
						this.likeParams.userPhoto = userInfo.avatarUrl;
						this.fatherLikeMethod(this.likeParams);
					} else {
						//跳转页面 
						 wx.navigateTo({
						 	url:'/pages/login/login'
						 })
					}					
				}		
			},
			//相册评论
			commentInput(index){
				this.commentParams.momentId = this.list[index].id;
				this.nowComment = this.list[index].id;
				//获取用户信息
				this.commentParams.userId = uni.getStorageSync('openid');
				let userInfo = uni.getStorageSync('userInfo');
				if(userInfo){
					this.commentParams.userName = userInfo.nickName;
					this.commentParams.userPhoto = userInfo.avatarUrl;
					this.commentShow = true;
					// this.$refs.commentdom.focus();
				} else {
					//跳转页面 
					 wx.navigateTo({
					 	url:'/pages/login/login'
					 });
				}								
			},
			//评论的评论
			commentInput2(comment){
				this.commentParams.momentId = comment.momentId;
				this.commentParams.fid = comment.id;
				//获取用户信息
				this.commentParams.userId = uni.getStorageSync('openid');
				let userInfo = uni.getStorageSync('userInfo');
				if(userInfo){
					this.commentParams.userName = userInfo.nickName;
					this.commentParams.userPhoto = userInfo.avatarUrl;
					this.commentShow = true;
					// this.$refs.commentdom.focus();
				} else {
					//跳转页面 
					 wx.navigateTo({
					 	url:'/pages/login/login'
					 });
				}	
			},
			sumbitComment(){				
				if(this.fatherCommentMethod){
					this.commentParams.content = this.commentText;
					this.fatherCommentMethod(this.commentParams);
				}
			}
		},
		
	}
</script>

<style  lang="scss">
	.discover-comment{
		position: fixed;
		z-index: 999;
		bottom: 0;
		background: #f0f3f2;
		width: 100%;
	}
	.active{
		    color: red;
	}
	.comment-input{
		    width: 80%;
		    float: left;
		    height: 45px;
		    size: 10px;
		    font-size: 16px;
			margin-left: 10px;
	}
	.weui-cell_input{
		height: 100%;
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
