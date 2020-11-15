<template>
	<view class="mask" :class="maskState===0 ? 'none' : maskState===1 ? 'show' : ''" @click="toggleMask">
		<view class="mask-content" @click.stop.prevent="stopPrevent">

			<view class="mask-content-input">
				<textarea class="textarea" v-model="item.content" :placeholder="placeholder" :cursor-spacing="100"
				 :show-confirm-bar="false" :focus="focus" maxlength="140"></textarea>
			</view>
			<view class="mask-content-topbar">
				<view class="left cuIcon-location" @click="getLocation">点击获取位置</view>
				<view class="right" @click="pubComment">发布</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "ygcComment",
		//属性
		props: {
			placeholder: {
				type: String
			}
		},
		data() {
			return {
				maskState: 0,
				item: {
					content: "",
					position: ""
				},
				focus: false
			};
		},
		created() {},
		methods: {
			stopPrevent() {
				this.item = {
					content: "",
					position: ""
				}
			},
			toggleMask(type) {
				this.item = {
					content: "",
					position: ""
				}
				let timer = type === 'show' ? 10 : 300;
				let state = type === 'show' ? 1 : 0;
				// this.maskState = 2;
				setTimeout(() => {
					this.maskState = state;
					// #ifdef APP-PLUS
					// 安卓app软键盘自动弹出有点问题，暂时还没有很好的解决方案，所以就禁止安卓app软键盘自动弹出，如果哪位朋友有好的解决方案可以在评论里告诉大家参考一下
					if (uni.getSystemInfoSync().platform == "ios") {
						this.focus = this.maskState ? true : false;
					}
					// #endif
					// #ifndef APP-PLUS
					this.focus = this.maskState ? true : false;
					// #endif
				}, timer)
			},
			getLocation(location) {

			},
			pubComment() {
				this.$emit('pubComment', this.item);
			}
		}
	}
</script>

<style lang="scss" scoped>
	$font-color-base: #606266;
	$base-color: #5A9BEC;

	.mask {
		display: flex;
		align-items: flex-end;
		position: fixed;
		left: 0;
		top: var(--window-top);
		bottom: 0rpx;
		width: 100%;
		background: rgba(0, 0, 0, 0);
		z-index: 9995;
		transition: .3s;
		-webkit-transition: .3s;

		.mask-content {
			width: 100%;
			background: #FFFFFF;
			transform: translateY(100%);
			transition: .3s; //底部弹出的持续时间
			-webkit-transition: .3s; //底部弹出的持续时间 
			// overflow-y:scroll;
			display: flex;
			flex-direction: column;

			.mask-content-topbar {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				padding: 20upx 30upx 10upx;
				font-size: 32upx;
				line-height: 1.2;
				border-top: 1px solid #eaeaea;

				.left {
					padding: 10rpx 20rpx;
					background-color: #f4f4f4;
					border-radius: 40rpx;
					color: $font-color-base;
					height: 60rpx;
					line-height: 40rpx;
					font-size: 24rpx;
				}

				.right {
					height: 60rpx;
					line-height: 40rpx;
					padding: 10upx 50upx;
					border-radius: 50upx;
					color: #FFFFFF;
					font-weight: 500;
					background-color: $base-color;
					font-size: 24rpx;
				}
			}

			.mask-content-input {
				width: 718upx; //如果textarea的宽带有问题可以把width改为100%试试
				// width: 100%;
				padding: 10upx 16upx 20upx;

				.textarea {
					height: 70px;
					background-color: #f4f4f4;
					width: 720upx; //如果textarea的宽带有问题可以把width改为100%试试
					// width: 100%;
					padding: 16upx;
					// border:2upx solid #d5d5d6;
					border-radius: 8upx;
				}
			}
		}

		&.none {
			display: none;
		}

		&.show {
			background: rgba(0, 0, 0, .4);

			.mask-content {
				transform: translateY(0);
			}
		}
	}
</style>
