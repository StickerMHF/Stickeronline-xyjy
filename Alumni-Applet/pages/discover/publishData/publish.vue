<template>
	<view class="page" @touchstart="touchStart" @touchend="touchEnd">
		<form>
			<view class="uni-textarea">
				<textarea placeholder="这一刻的想法..." v-model="publishData.content" />
				</view>
			<view class="uni-list list-pd">
				<view class="uni-list-cell cell-pd">
					<view class="uni-uploader">
						<view class="uni-uploader-head">
							<view class="uni-uploader-title"></view>
							<view class="uni-uploader-info">{{imageList.length}}/9</view>
						</view>
						<view class="uni-uploader-body">
							<view class="uni-uploader__files">
								<block v-for="(image,index) in imageList" :key="index">
									<view class="uni-uploader__file" style="position: relative;">
										<image class="uni-uploader__img" mode="aspectFill" :src="image" :data-src="image" @tap="previewImage"></image>
										<view class="close-view" @click="close(index)">×</view>
									</view>
								</block>
								<view class="uni-uploader__input-box" v-show="imageList.length < 9">
									<view class="uni-uploader__input" @tap="chooseImage"></view>
								</view>
							</view>
						</view>
						<view class="listBox" @click="selectAddress">
							<i class="icon cuIcon-location"></i>
							<text>{{address}}</text>
						</view>
					</view>
				</view>
			</view>
			
			<view class="footer">
				<button type="default" class="feedback-submit" @click="publish">提交</button>
			</view>
		</form>
	</view>
</template>

<script>
	import image from '@/common/image.js';
	import {uploadFile, publishMoment} from '@/api/discover.js';
	const chooseLocation = requirePlugin('chooseLocation');
	var sourceType = [
		['camera'],
		['album'],
		['camera', 'album']
	]
	var sizeType = [
		['compressed'],
		['original'],
		['compressed', 'original']
	]
	export default {
		data() {
			return {
				// title: 'choose/previewImage',
				// input_content:'',
				address:'所在位置',
				location:'',
				imageList: [],				
				sourceTypeIndex: 2,
				sourceType: ['拍照', '相册', '拍照或相册'],
				sizeTypeIndex: 2,
				sizeType: ['压缩', '原图', '压缩或原图'],
				countIndex: 8,
				count: [1, 2, 3, 4, 5, 6, 7, 8, 9],
				
				//侧滑返回start
				startX: 0, //点击屏幕起始位置
				movedX: 0, //横向移动的距离
				endX: 0, //接触屏幕后移开时的位置
				//end
				photosArray:[],
				//发布参数
				publishData:{
					content: '',					
					photos: '',
					userId: '',
					userName: '',
					userPhoto: '',
					viewCount: 0,
					likeCount: 0,
					commentCount: 0
				}
			}
		},
		onLoad(options) {
			
		},
		onUnload() {
			this.imageList = [],
			this.sourceTypeIndex = 2,
			this.sourceType = ['拍照', '相册', '拍照或相册'],
			this.sizeTypeIndex = 2,
			this.sizeType = ['压缩', '原图', '压缩或原图'],
			this.countIndex = 8;			
		},
		
		methods: {
			getLocation(){
				const location = chooseLocation.getLocation(); // 如果点击确认选点按钮，则返回选点结果对象，否则返回null
				if(location !== null){
					this.address = location.name;
					this.location = JSON.stringify({
					  latitude: location.latitude,
					  longitude: location.longitude
					});
				}
			},
			setLocation(){
				// 页面卸载时设置插件选点数据为null，防止再次进入页面，geLocation返回的是上次选点结果
				chooseLocation.setLocation(null);
			},
			async publish(){
				if (!this.publishData.content) {
					uni.showModal({ content: '内容不能为空', showCancel: false, });
					return;
				}
				
				// uni.showLoading({title:'发布中'});
				
				// var location = await this.getLocation();//位置信息,可删除,主要想记录一下异步转同步处理
				// var images = [];
				// for(var i = 0,len = this.imageList.length; i < len; i++){
				// 	var image_obj = {name:'image-'+i,uri:this.imageList[i]};
				// 	images.push(image_obj);
				// }
				
				//获取用户信息
				this.publishData.userId = uni.getStorageSync('openid');
				let userInfo = uni.getStorageSync('userInfo');
				console.log(userInfo)
				if(userInfo){
					this.publishData.userName = userInfo.nickName;
					this.publishData.userPhoto = userInfo.avatarUrl;
				} else {
					//跳转页面 
					 wx.navigateTo({
					 	url:'pages/login/login'
					 })
				}
				
				this.publishData.photos = JSON.stringify(this.photosArray);
				this.publishData.address = this.address === "所在位置" ? '' :this.address;
				publishMoment(this.publishData).then(data =>{
					// console.log(data);
					 // let pages = getCurrentPages() // 获取加载的页面
					 // console.log(pages,'UUUUUUUU')
					 //  let currentPage = pages[pages.length - 2] // 获取当前页面的对象
					 //  let url = currentPage.route // 当前页面url
					uni.showLoading({title:'审核中！'});
					setTimeout(function () {
					    uni.hideLoading();
						uni.navigateBack()
						// uni.navigateBack({
						// 	url:"/pages/discover/discover"
						// });
					}, 1000);

					
					// uni.navigateTo({
					// 	url:"/pages/discover/discover"
					// });
					
					// uni.hideLoading();
					
				});
			},
			selectAddress(){
				let location = ""
				if(this.location === ""){
					let that = this;
					uni.getLocation({
					    type: 'wgs84',
					    success: function (res) {
							location = JSON.stringify({
								latitude: res.latitude,
								longitude: res.longitude
							});
							that.chooseLocationByTx(location);
					    }
					});
				}else{
					location = this.location;
					this.chooseLocationByTx(location);
				}
			},
			
			chooseLocationByTx(location){
				console.log(this.txKey);
				console.log(this.referer)
				uni.navigateTo({
				  url: `plugin://chooseLocation/index?key=${this.txKey}&referer=${this.referer}&location=${location}`
				});
			},
			// getLocation(){//h5中可能不支持,自己选择
			// 	return new Promise((resolve, reject) => {
			// 		uni.getLocation({
			// 			type: 'wgs84',
			// 			success: function (res) {
			// 				resolve(res);
			// 			},
			// 			fail: (e) => {  
			// 				reject(e);
			// 			}
			// 		});
			// 	} )
			// },			
			close(e){
			    this.imageList.splice(e,1);
				this.photosArray.splice(e,1);
			},
			chooseImage: async function() {
				if (this.imageList.length === 9) {
					let isContinue = await this.isFullImg();
					console.log("是否继续?", isContinue);
					if (!isContinue) {
						return;
					}
				}
				uni.chooseImage({
					sourceType: sourceType[this.sourceTypeIndex],
					sizeType: sizeType[this.sizeTypeIndex],
					count: this.imageList.length + this.count[this.countIndex] > 9 ? 9 - this.imageList.length : this.count[this.countIndex],
					success: (res) => {

						// #ifdef APP-PLUS
						//提交压缩,因为使用了H5+ Api,所以自定义压缩目前仅支持APP平台
						var compressd = cp_images=> {
							this.imageList = this.imageList.concat(cp_images)//压缩后的图片路径
						}
						image.compress(res.tempFilePaths,compressd);
						// #endif
						
						// #ifndef APP-PLUS
						this.imageList = this.imageList.concat(res.tempFilePaths)//非APP平台不支持自定义压缩,暂时没有处理,可通过uni-app上传组件的sizeType属性压缩
						// #endif
						//上传图片
						this.uploadFile(res.tempFiles);
					}
				})
			},
			isFullImg: function() {
				return new Promise((res) => {
					uni.showModal({
						content: "已经有9张图片了,是否清空现有图片？",
						success: (e) => {
							if (e.confirm) {
								this.imageList = [];
								res(true);
							} else {
								res(false)
							}
						},
						fail: () => {
							res(false)
						}
					})
				})
			},
			previewImage: function(e) {
				var current = e.target.dataset.src
				uni.previewImage({
					current: current,
					urls: this.imageList
				})
			},
			touchStart: function(e) {
				this.startX = e.mp.changedTouches[0].pageX;
			},
			
			touchEnd: function(e) {
				this.endX = e.mp.changedTouches[0].pageX;
				if (this.endX - this.startX > 200) {
					uni.navigateBack();
				}
			},
			uploadFile: function (files){
				files.map(file =>{
					uni.uploadFile({
					            url: 'https://www.imapway.cn/alumniapi/file/upload', //仅为示例，非真实的接口地址
					            // files:files,
					            name: 'file',
								fileType:'image',
								filePath: file.path,
								// header: {"Content-Type": "multipart/form-data; boundary=<calculated when request is sent>"},
					            // formData: {
					            //     'file': files[0]
					            // },
					            success: (uploadFileRes) => {
					                console.log(uploadFileRes.data);
									let res = JSON.parse(uploadFileRes.data);
									this.photosArray.push({
										url: res.result[0].url,
										fileName:  res.result[0].fileName
									});
					            },
								fail:()=>{
									uni.showToast({  
										title: '请求错误',  
										duration: 2000  
									});  
								}
					        });
				});
				
			
			}
		}
	}
</script>

<style scoped>
	body{
		background: #fff;
	}
	.footer {
		/* margin-top: 80upx; */
		    position: fixed;
		    width: 100%;
		    bottom: 0;
	}
	.footer .feedback-submit{
		    color: #fff;
		    background-color: #00beb7;
	}
	
	.cell-pd {
		padding: 20upx 30upx;
	}

	.uni-textarea {
		width: auto;
		padding: 20upx 25upx;
		line-height: 1.6;
		height: 150upx;
	}
	.uni-list::before {
		height: 0;
	}
	.uni-list:after {
		height: 0;
	}
	.list-pd {
		margin-top: 0;
	}
	.close-view{
	    text-align: center;
		line-height:30upx;
		height: 35upx;
		width: 35upx;
		background: #ef5350;
		color: #FFFFFF;
		position: absolute;
		top: 1upx;
		right: 1upx;
		font-size: 35upx;
		border-radius: 8upx;
	}
	.page {
		width: 750upx;
		height: 100%;
	}
	.listBox{
		display: flex;
		align-items: center;
		
	}
	.listBox .icon{
			color: #00beb7;
			font-size: 20px;
			margin-right: 20px;
		}
</style>
