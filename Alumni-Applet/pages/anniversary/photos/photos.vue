<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true" :isCallBack="true" @callBack="callBack">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content">
			<view class="titleBox">
				<!-- <image src="" mode=""></image> -->
				<text class="titleText">长安大学建校70周年照片征集</text>
			</view>
			<view class="photoList">
				<view class="listBox" v-for="(item,index) in listData" :key="index" @click="hrefToPhotoListPage(item.user_id)">
					<view class="imgBox">
						<image :src="item.user_photo" mode="" class="userPhoto"></image>
					</view>
					<view class="userName">
						{{item.user_name}}
					</view>
				</view>
			</view>
			<view class="btnBox">
				<button class="textBtn" open-type="share">分享</button>
				<button class="textBtn" @click="uploadPhoto">上传照片</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getPhotoList,
		addPhoto,
		getUserList
	} from '@/api/cooperation.js'
	export default {
		data() {
			return {
				title:'照片征集',
				listData:[],
				uploadList:[],
				files:[],
				userInfo:{},
				userId:''
			}
		},
		onLoad() {
			this.userId = uni.getStorageSync('openid');
			this.userInfo = uni.getStorageSync('userInfo');
			this.getUserList();
		},
		onShareAppMessage: function () {
			return {
			  title: "照片征集",
			  path:`/pages/anniversary/photos/photos` 
			}
		},
		methods: {
			callBack(){
				uni.redirectTo({
					url: '/pages/anniversary/index'
				});
			},
			hrefToPhotoListPage(id){
				uni.navigateTo({
					url: "./photoList?id="+id
				});
			},
			getUserList(){
				let param = {
					pageNo: 1,
					pageSize: 20,
				}
				getUserList(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let datas = res.data.result;
						this.listData = datas.content;
					}
				});
			},
			addPhoto(){
				let imgs = this.uploadList.join(";");
				let param = {
					userId:this.userId,
					userName:this.userInfo.nickName,
					userPhoto:this.userInfo.avatarUrl,
					imgs:imgs
				}
				addPhoto(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let datas = res.data.result;
						uni.showToast({
							title:'上传成功'
						})
					}
				});
			},
			uploadPhoto(){
				let that = this;
				uni.chooseImage({
				    count: 9, //默认9
				    sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
				    sourceType: ['album'], //从相册选择
				    success: function (res) {
						let files = res.tempFiles;
						that.files = files;
						files.map(file =>{
							uni.uploadFile({
								url: 'https://www.imapway.cn/alumniapi/file/upload',
								name: 'file',
								fileType:'image',
								filePath: file.path,
								success: (uploadFileRes) => {
									let res = JSON.parse(uploadFileRes.data);
									that.uploadList.push(res.result[0].url)
									if(JSON.stringify(file) === JSON.stringify(files[files.length-1])){
										that.addPhoto()
									}
									// macr36059    vh930040
								},
								fail:()=>{
									uni.showToast({  
										title: '请求错误',  
										duration: 2000  
									});  
								}
							});
						});
						// console.log('uploadList---',that.uploadList);
						// that.addPhoto(that.uploadList)
				    }
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
.ann_news{
	width: 100%;
	height: 100%;
}
.ann_news_content{
	position: absolute;
	top: 100rpx;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background-size: 100% 100%;
	background-color: white;
}
.titleBox{
	background-image: url('http://www.imapway.cn/Alumni/static/images/titleBg.png');
	background-size: 100%;
	width: 100%;
	height: 220rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	.titleText{
		color: #fff;
		font-size: 16px;
		margin-top: 50rpx;
	}
}
.photoList{
	display: flex;
	justify-content: center;
	flex-wrap: wrap;
	height: 900rpx;
	overflow-y: auto;
	.listBox{
		width:300rpx;
		height:300rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		.imgBox{
			width:160rpx;
			height:160rpx;
			border: 1px solid #F2F2F2;
			padding:20rpx;
			box-shadow: 0px 0px 10px 0px #e1dada;
			.userPhoto{
				width: 100%;
				height: 100%;
			}
		}
		.userName{
			margin-top:20rpx;
		}
	}
}
.btnBox{
	width: 100%;
	height:120rpx;
	// background-color: #007AFF;
	display: flex;
	justify-content: space-around;
	align-items: center;
	position: fixed;
	bottom: 0px;
	.textBtn{
		width:200rpx;
		height:60rpx;
		line-height:60rpx;
		border-radius: 20px;
		color:#FFFFFF;
		text-align: center;
		background: #ffa261;
		margin: 0;
		font-size: 14px;
	}
}
</style>
