<template>
	<view class="publishBtn">
		<image class="image" src="http://cdxyh.stickeronline.cn/FhDX9UB6L_r8YaQ6gqewXMPBCIqG" @click="handleMenu"></image>
		<view class="menuUl" v-if="showMenu">
			<view class="menuList" v-for="(item, index) in menusList" :key="index" @click="hrefToPage(item)">
				<image class="img" :src="item.iconPath" mode=""></image>
				<view class="pathName">{{item.name}}</view>
			</view>
			<view class="triangle">

			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			menusList: {
				type: Array,
				default () {
					return [];
				},
			},
			fid: {
				type: String,
				default () {
					return "";
				},
			},
			isJoin: {
				type: Number,
				default () {
					return -1;
				},
			}
		},
		data() {
			return {
				showMenu: false
			}
		},
		methods: {
			handleMenu() {
				let that = this;
				if (that.isJoin==-1) {
					uni.showToast({
						title: "请先加入本会",
						icon: 'none',
						duration: 2000
					});
				}else {
					this.showMenu = !this.showMenu;
				}

			},
			hrefToPage(item) {
				let that = this;
				if(item.id=='gg'||item.id=='hd'){
					if(that.isJoin!=0){
						uni.navigateTo({
							url: item.url + '?id=' + that.fid
						})
						that.showMenu = false;
					}else{
						uni.showToast({
							title: "请申请会长权限",
							icon: 'none',
							duration: 2000
						});
						that.showMenu = false;
					}
				}else{
					let certification=getApp().getIsCertification();
					if(certification){
						uni.navigateTo({
							url: item.url + '?id=' + that.fid
						})
						that.showMenu = false;
					}else{
						uni.showToast({
							title: "请进行校友认证",
							icon: 'none',
							duration: 2000
						});
						that.showMenu = false;
					}
				}
				
			}
		}
	}
</script>

<style lang="scss">
	.publishBtn {
		position: fixed;
		right: 5px;
		bottom: 40rpx;
		width: 40px;
		height: 40px;
		z-index: 100;

		.image {
			width: 100%;
			height: 100%;
		}
	}

	.menuUl {
		width: 90px;
		background: #000000;
		opacity: .7;
		border-radius: 6px;
		position: absolute;
		top: -115px;
		right: 5px;
		z-index: 100;

		.menuList {
			display: flex;
			align-items: center;
			margin: 12px 8px;

			.img {
				width: 15px;
				height: 20px;
				margin-right: 20rpx;
			}

			.pathName {
				color: #fff;
				margin-top: -5px;
			}
		}

		.triangle {
			width: 0;
			height: 0;
			border-right: 5px solid transparent;
			border-left: 5px solid transparent;
			border-top: 5px solid #000;
			position: absolute;
			right: 8px;
		}
	}
</style>
