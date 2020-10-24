<template>
	<view>
		<view class="header">
			<view class="bg" style="background-image:url(http://www.imapway.cn/Alumni/static/personal/background.png);">
				<view class="box">
					<view class="box-hd">
						<view class="avator" @click="loginHandler">
							<img :src="userInfo.avatarUrl">
						</view>
						<view class="phone-number">{{userInfo.nickName}}</view>
					</view>
					<view class="box-bd">
						<navigator url="/pages/personal/attention/attention">
							<view class="item">
								<view class="icon"><img src="http://www.imapway.cn/Alumni/static/personal/wdgz2x.png"></view>
								<view class="text">我的关注</view>
							</view>
						</navigator>
						<navigator url="/pages/personal/fans/fans">
							<view class="item">
								<view class="icon"><img src="http://www.imapway.cn/Alumni/static/personal/sc2x.png"></view>
								<view class="text">我的粉丝</view>
							</view>
						</navigator>
						<navigator url="/pages/personal/alumnus/alumnus">
							<view class="item">
								<view class="icon"><img src="http://www.imapway.cn/Alumni/static/personal/wdzz2x.png"></view>
								<view class="text">我的圈子</view>
							</view>
						</navigator>
					</view>
				</view>
			</view>
		</view>
		<view class="list-content">
			<view class="list" v-for="item in menuList">
				<navigator :url="item.page">
					<view class="li noborder">
						<view class="icon">
							<image :src="item.icon"></image>
						</view>
						<view class="text">{{item.name}}</view>
						<image class="to" src="http://www.imapway.cn/Alumni/static/user/to.png"></image>
					</view>
				</navigator>
			</view>

		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				userInfo: {
					nickName: "点击头像登录",
					gender: 1,
					language: "zh_CN",
					city: "Xian",
					province: "Shannxi",
					country: "China",
					avatarUrl: "http://www.imapway.cn/Alumni/static/user/face.jpg"
				},
				menuList: [{
					name: "个人信息",
					icon: "http://www.imapway.cn/Alumni/static/personal/grxx2x.png",
					page: "/pages/personal/basicInfo/basicInfo"
				}, {
					name: "我的组织",
					icon: "http://www.imapway.cn/Alumni/static/personal/wdzz2x.png",
					page: "/pages/personal/alumnus/alumnus"
				}, {
					name: "我的关注",
					icon: "http://www.imapway.cn/Alumni/static/personal/wdgz2x.png",
					page: "/pages/personal/attention/attention"
				}, {
					name: "我的粉丝",
					icon: "http://www.imapway.cn/Alumni/static/personal/wdpl2x.png",
					page: "/pages/personal/fans/fans"
				}, {
					name: "收藏",
					icon: "http://www.imapway.cn/Alumni/static/personal/sc2x.png",
					page: "/pages/personal/collect/collect"
				}, {
					name: "设置",
					icon: "http://www.imapway.cn/Alumni/static/personal/sz2x.png",
					page: "/pages/personal/setting/setting"
				}]
			};
		},
		onShow() {//
		debugger
			let userInfo = uni.getStorageSync('userInfo');
			if (userInfo&&userInfo!="") {
				this.userInfo = userInfo;
			}else{
				uni.navigateTo({
				    url: "/pages/login/login"
				});
			}
			// if(userInfo){
			// 	getApp().wxGetUserInfo();
			// }

		},
		methods: {
			changeSkin() {
				uni.navigateTo({
					url: '../skin-change/skin-change'
				});
			},
			loginHandler(){
				let openid = uni.getStorageSync('openid');
				let userInfo = uni.getStorageSync('userInfo');
				if (userInfo) {
					this.userInfo = userInfo;
				}else{
					uni.navigateTo({
					    url: "/pages/login/login"
					});
				}
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f1f1f1;
		font-size: 30upx;
	}

	.header {
		background: #fff;
		height: 500upx;
		padding-bottom: 110upx;

		.bg {
			width: 100%;
			height: 300upx;
			padding-top: 200upx;
			background-color: #4191ea;
			// background:url(../../static/personal/background.png);
			background-size: 100% 100%;
		}
	}

	.box {
		width: 650upx;
		height: 280upx;
		border-radius: 20upx;
		margin: 0 auto;
		background: #fff;
		box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2);

		.box-hd {
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;

			.avator {
				width: 160upx;
				height: 160upx;
				background: #fff;
				border: 5upx solid #fff;
				border-radius: 50%;
				margin-top: -80upx;
				overflow: hidden;

				img {
					width: 100%;
					height: 100%;
				}
			}

			.phone-number {
				width: 100%;
				text-align: center;
			}
		}

		.box-bd {
			display: flex;
			flex-wrap: nowrap;
			flex-direction: row;
			justify-content: center;

			.item {
				flex: 1 1 auto;
				display: flex;
				flex-wrap: wrap;
				flex-direction: row;
				justify-content: center;
				border-right: 1px solid #f1f1f1;
				margin: 15upx 0;

				&:last-child {
					border: none;
				}

				.icon {
					width: 60upx;
					height: 60upx;

					img {
						width: 100%;
						height: 100%;
					}
				}

				.text {
					width: 100%;
					text-align: center;
					margin-top: 10upx;
				}
			}
		}
	}

	.list-content {
		background: #fff;
	}

	.list {
		width: 100%;
		border-bottom: 15upx solid #f1f1f1;
		background: #fff;

		&:last-child {
			border: none;
		}

		.li {
			width: 92%;
			height: 100upx;
			padding: 0 4%;
			border-bottom: 1px solid rgb(243, 243, 243);
			display: flex;
			align-items: center;

			&.noborder {
				border-bottom: 0
			}

			.icon {
				flex-shrink: 0;
				width: 50upx;
				height: 50upx;

				image {
					width: 50upx;
					height: 50upx;
				}
			}

			.text {
				padding-left: 20upx;
				width: 100%;
				color: #666;
			}

			.to {
				flex-shrink: 0;
				width: 40upx;
				height: 40upx;
			}
		}
	}
</style>
