<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true" :isCallBack="true" @callBack="callback">
			<!-- <block slot="backText">返回</block> -->
			<block slot="content">校友合作信息登记</block>
		</cu-custom>
		<form>
			<view class="cu-form-group margin-top">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 合作事项
				</view>
				<!-- <view class="title">合作事项</view> -->
				<view calss="cu-form-group">
					<!-- <input placeholder="请输入合作事项" name="input" @input="inputTitleChange"></input> -->
					<textarea maxlength="-1" @input="inputTitleChange" placeholder="请输入合作事项"></textarea>
				</view>
			</view>

			<view class="cu-form-group">
				<view class="action">
					<text class="title cuIcon-titles text-green1"></text>联系方式
				</view>
				<input placeholder="输入联系方式" name="input" @input="inputContactChange"></input>
			</view>


			<!-- !!!!! placeholder 在ios表现有偏移 建议使用 第一种样式 -->

			<view class="cu-form-group align-start">
				<view class="action">
					<text class="title cuIcon-titles text-green1"></text>描述
				</view>
				<textarea maxlength="-1" @input="textareaInput" placeholder="多行文本输入框"></textarea>
			</view>
			<view class="padding flex flex-direction">
				<button @click="saveClickHandler" class="cu-btn bg-gradual-green1 lg">提交</button>
			</view>
		</form>
	</view>
</template>

<script>
	import {
		addCooperation
	} from '@/api/cooperation.js'
	export default {
		data() {
			return {
				title: '',
				name: '',
				contents: '',
				contact: '',
				rules:{
					phone: {
						required: true, 
						validator: this.validatePhone,
					}
				}
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title = options.title;

		},
		methods: {
			validatePhone(phone){
				let phoneCodeVerification = /^[1][3,4,5,7,8][0-9]{9}$/;
				return phoneCodeVerification.test(phone);
			},
			callback(){
				uni.redirectTo({
					url:"../cooperation"
				})
			},
			textareaInput(e) {
				this.contents = e.detail.value
			},
			inputContactChange(e) {
				this.contact = e.detail.value
			},
			inputTitleChange(e) {
				this.name = e.detail.value
			},
			saveClickHandler() {
				let that = this;
				let params = {
					title: that.name,
					contents: that.contents,
					contact: that.contact
				}
				addCooperation(params).then(data => {
					var [error, res] = data;
					if (res && res.data && res.data.success) {
						uni.redirectTo({
							url: '/pages/cooperation/cooperation'
						})
					} else {
						uni.showModal({
							content: '保存失败，请稍后再试：' + JSON.stringify(res.data),
							showCancel: false
						})
					}
					this.lists = res.data.data;
				})

			}
		}
	}
</script>

<style>
	.action {
		font-size: 30rpx;
		height: 60rpx;
		line-height: 60rpx;
	}

	.cu-form-group {
		display: block;
	}

	.cu-form-group textarea {
		background-color: #eee;
		border-radius: 10rpx;
		padding: 20rpx;
		height: 180rpx;
	}

	.cu-form-group input {
		background-color: #eee;
		height: 80rpx;
		padding: 16rpx;
		border-radius: 10rpx;
		margin: 20rpx 0;
	}
	.cu-form-group+.cu-form-group {
		border: none;
	}
</style>
