<template>
	<view class="wrapper" :style="{height: windowHeight + 'px'}">
		<view class="box">
			<view style="margin-top: 10px;">
				<text class="title">更换头像</text>
			</view>
			<view class="img-wrap">
				<canvas class="canvas" canvas-id="canvas"></canvas>
			</view>
			<view class="select">
				<view class="select-list" :class="{select_active: id == index}" v-for="(source,index) in sources" :key="index" @tap="handle(source.src,index)">
					<image class="avatar" :src="avatarUrl"></image>
					<image class="avatar" :src="source.src"></image>
				</view>
			</view>
		</view>

		<view class="save-btn">
			<!-- <button class="btn" type="default" @tap="save">保存头像</button> -->
			<text class="btn" @click="save">保存头像</text>
			<text class="btn" @click="upload">上传图片</text>

			<!-- <button class="btn" type="default" @tap="upload">上传图片</button> -->
			<!-- <button class="btn" type="default" @tap="share">分享朋友</button> -->
		</view>
		<!-- #ifdef H5 -->
		<view class="preview-wrap" :style="{display: newAvatar ? 'block' : 'none'}" @tap="newAvatar = ''">
			<view class="preview" @tap.stop="(()=>{return false})">
				<view class="new-avatar">
					<image :src="newAvatar"></image>
				</view>
				<view class="hint">长按头像保存图片</view>
			</view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	export default {
		props: {
			imgList: {
				type: Array,
				required: true
			}
		},
		data() {
			return {
				avatarUrl: 'http://www.imapway.cn/Alumni/static/user/face.jpg',
				target: '../../static/anniversary/head0.png',
				windowHeight: 0,
				//画笔
				ctx: null,
				canvasSide: 0,
				id: 0,
				text: '',
				text1: '',
				// #ifdef H5
				newAvatar: ''
				// #endif
			}
		},
		computed: {
			sources() {
				return this.imgList.map((item, index) => {
					return {
						id: index,
						src: item
					}
				})
			}
		},
		methods: {
			handle(src, index) {
				this.target = src;
				this.id = index;
				this.drawImage(src)
			},
			drawImage(img) {
				this.ctx.drawImage(this.avatarUrl, 0, 0, this.canvasSide, this.canvasSide)
				this.ctx.drawImage(img, 0, 0, this.canvasSide, this.canvasSide)
				this.ctx.draw()
			},
			save() {
				uni.canvasToTempFilePath({
					width: this.canvasSide,
					height: this.canvasSide,
					canvasId: 'canvas',
					success: (res) => {
						// #ifndef H5
						uni.saveImageToPhotosAlbum({
							filePath: res.tempFilePath,
							success: function() {
								uni.showToast({
									title: '保存成功'
								});
							},
							fail: (err) => {
								uni.showToast({
									title: '保存失败'
								});
							}
						});
						// #endif
						// #ifdef H5
						this.newAvatar = res.tempFilePath;
						// #endif
					}
				}, this)

			},
			upload() {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						this.avatarUrl = res.tempFilePaths[0];
						this.ctx = uni.createCanvasContext('canvas', this);
						this.drawImage(this.imgList[this.id])
					}
				})
			},
			share() {
				uni.share({
					provider: "weixin",
					scene: "WXSceneSession",
					type: 0,
					success: function(res) {
						console.log("success:" + JSON.stringify(res));
					},
					fail: function(err) {
						console.log("fail:" + JSON.stringify(err));
					}
				})
			}
		},
		mounted() {
			// wx.ready(() => {

			// })
			let that = this;
			uni.createSelectorQuery().in(that).select('.canvas').boundingClientRect(data => {
				that.canvasSide = data.width;
				that.ctx = uni.createCanvasContext('canvas', that);
				// let userInfo = uni.getStorageSync('userInfo');
				// if (userInfo && userInfo != "") {
				// 	debugger

				// } else {
				// 	uni.navigateTo({
				// 		url: "/pages/login/login"
				// 	});
				// }


				uni.getUserInfo({
					provider: 'weixin',
					success: (res) => {
						that.userInfo = res.userInfo;
						debugger
						// that.avatarUrl = userInfo.avatarUrl;
						// that.drawImage(that.imgList[that.id]);
						uni.getImageInfo({
							src: res.userInfo.avatarUrl,
							success: (image) => {
								that.avatarUrl = image.path;
								// this.avatarUrl = userInfo.avatarUrl;
								that.drawImage(that.imgList[that.id])
							},
							fail:(e)=>{
								uni.getImageInfo({
									src: "http://www.imapway.cn/Alumni/static/user/face.jpg",
									success: (image) => {
										that.avatarUrl = image.path;
										// this.avatarUrl = userInfo.avatarUrl;
										that.drawImage(that.imgList[that.id])
									}
								})
								
							}
						})
					}
				})
			}).exec();

		},
		created() {
			uni.getSystemInfo({
				success: (res) => {
					this.windowHeight = res.windowHeight;
				}
			})
		}
	}
</script>

<style>
	.wrapper {
		display: flex;
		justify-content: center;
		align-items: center;
		background-image: linear-gradient(#00BEB7, #00ded3);
	}

	.box {
		width: 80%;
		height: 70%;
		border: 2px solid #000;
		background-color: #FFFFFF;
		border-radius: 20rpx;
		box-shadow: 20rpx 20rpx 10rpx #000000;
		margin-top: -200rpx;
		text-align: center;
	}

	.title {
		font-size: 40rpx;
		color: #4CD964;
	}

	.img-wrap {
		position: relative;
		width: 500rpx;
		height: 500rpx;
		margin: 20rpx auto;
	}

	.canvas {
		position: absolute;
		/* z-index: 10; */
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}

	.avatar {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}

	.select {
		display: flex;
		padding: 0 2rpx;
		overflow-x: auto;
		margin: 2rpx;
	}

	.select-list {
		flex-shrink: 0;
		position: relative;
		width: 140rpx;
		height: 140rpx;
		border: 4rpx solid transparent;
	}

	.select_active {
		border-color: #35d4d0;
	}

	.save-btn {
		position: absolute;
		bottom: 5%;
		display: flex;
		flex-direction: row;
		
	}

	.btn {
		margin: 0 10rpx;
		font-size: 16px;
		border-radius: 20px;
		background: #FF8901;
		color: #fff;
		padding: 0 40rpx;
		line-height: 76rpx;
	}
	/* button::after {
		border: none;
			  background: #FF8901;
			  color: #aa0000;
			  border-radius: 20px;
			} */

	/* #ifdef H5 */
	.preview-wrap {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, .8);
	}

	.preview {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		margin: auto;
		width: 80%;
		height: 60%;
		border-radius: 12px;
		background-color: #fff;
	}

	.new-avatar {
		width: 500rpx;
		height: 500rpx;
		margin: 40rpx auto 0;
		border: 1px solid #b3e6e6;
		border-radius: 4px;
		overflow: hidden;
	}

	.new-avatar image {
		width: 100%;
		height: 100%;
	}

	.hint {
		font-size: 40rpx;
		text-align: center;
		margin: 30rpx auto 0;
	}

	/* #endif */
</style>
