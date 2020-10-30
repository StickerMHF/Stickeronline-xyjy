<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<form>
			<view class="cu-form-group margin-top">
				<view class="title">合作事项</view>
				<input placeholder="请输入合作事项" name="input" @input="inputTitleChange"></input>
			</view>

			<view class="cu-form-group">
				<view class="title">联系方式</view>
				<input placeholder="输入联系方式" name="input" @input="inputContactChange"></input>
				<view class="cu-capsule radius">
					<view class='cu-tag bg-blue '>
						+86
					</view>
					<view class="cu-tag line-blue">
						中国大陆
					</view>
				</view>
			</view>


			<!-- !!!!! placeholder 在ios表现有偏移 建议使用 第一种样式 -->

			<view class="cu-form-group align-start">
				<view class="title">描述</view>
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
				contact: ''
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title = options.title;

		},
		methods: {
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

</style>
