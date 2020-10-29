<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true" >
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
			<block slot="right" @rightClick="editHandler">
				<view style="padding: 0 10px;" @click="editHandler">编辑</view>
			</block>
		</cu-custom>
		<bjx-form labelType="inline" :rules="rules" labelWidth="150" :form="form" ref="form">
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="姓名" prop="name">
					<input class="basicinfo_name input" v-model="form.name" disabled name="input" placeholder="姓名" />
					<view class="basicinfo_name renzheng">已认证</view>
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="性别" prop="sex">
					<input v-model="form.sex" disabled class="input" name="input" placeholder="性别" />
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="身份证" label-right="left" prop='identityCard'>
					<input v-model="form.identityCard" disabled class="input" name="input" placeholder="身份证号" />
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="所属学院" label-right="left" prop='college'>
					<input v-model="form.college" disabled class="input" name="input" placeholder="所属学院" />
				</bjx-form-item>
			</view>
			<!-- 不是教师 -->
			<view v-if="form.type!='3'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="所在专业" label-right="right" prop='profession'>
					<input v-model="form.profession" disabled class="input" name="input" placeholder="所在专业" />
				</bjx-form-item>
			</view>

			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="学历" label-right="right" prop='education'>
					<input v-model="form.education" disabled class="input" name="input" placeholder="学历" />
				</bjx-form-item>
			</view>
			<!-- 曾经在校 -->
			<view v-if="form.type=='1'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="入校时间" label-right="right" prop='startDate'>
					<input v-model="form.startDate" disabled class="input" name="input" placeholder="入校时间" />
				</bjx-form-item>
			</view>
			<!-- 在校学生 -->
			<view v-else-if="form.type=='2'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="入学时间" label-right="right" prop='startDate'>
					<input v-model="form.startDate" disabled class="input" name="input" placeholder="入学时间" />
				</bjx-form-item>
			</view>
			<!-- 在职教师 -->
			<view v-else class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="入职时间" label-right="right" prop='startDate'>
					<input v-model="form.startDate" disabled class="input" name="input" placeholder="入职时间" />
				</bjx-form-item>
			</view>

			<!-- 曾经在校 -->
			<view v-if="form.type=='1'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="离校时间" label-right="right" prop='endtDate'>
					<input v-model="form.endDate" disabled class="input" name="input" placeholder="离校时间" />
				</bjx-form-item>
			</view>
		</bjx-form>
	</view>
</template>

<script>
	import bjxForm from '@/components/bjx-form/bjx-form.vue'
	import bjxFormItem from '@/components/bjx-form/bjx-form-item.vue'
	import {
		getWechatUserById
	} from '@/api/user.js'
	export default {
		components: {
			bjxForm,
			bjxFormItem
		},
		data() {
			return {
				form: {
					openid: '',
					name: '',
					sex: '',
					identityCard: "",
					college: '',
					profession: '',
					education: '',
					startDate: '2018-12-25',
					endDate: '2018-12-25',
					type: "1"
				},
				index: -1,
				eduIndex: -1,
				eduPicker: ['大专', '本科', '硕士', '博士']

			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title = options.title;
			let userInfo = uni.getStorageSync('userInfo');
			if (userInfo && userInfo != "") {
				this.userInfo = userInfo;
				this.getWechatUserInfo();
			} else {
				uni.navigateTo({
					url: "/pages/login/login"
				});
			}
		},
		methods: {
			getWechatUserInfo() {
				let that = this;
				let openid = uni.getStorageSync('openid');
				if (openid && openid != "") {
					let param = {
						openid: openid
					};
					getWechatUserById(param).then(data => {
						var [error, res] = data;
						if (res && res.data.success) {
							let ss = res.data.result;
							if (ss != null) {
								that.form=ss;
							} else {
								debugger
							}
						}
					});
				} else {
					getApp().getUserInfo();
				}


			},
			editHandler(){
				uni.navigateTo({
					url: "/pages/personal/basicInfo/add?isEdit=true"
				});
			},
			textareaInput(e) {
				this.contents = e.detail.value
			},
			eduChange(e) {
				this.eduIndex = e.detail.value;
				this.form.education = this.eduPicker[this.eduIndex];
			},
			startDateChange(e) {
				this.form.startDate = e.detail.value
			},
			endDateChange(e) {
				this.form.endDate = e.detail.value
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
					debugger
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
				debugger


			}
		}
	}
</script>

<style lang="scss" scoped>
	.basicinfo_item {
		width: 100% !important;
	}

	.basicinfo_name {
		display: inline-block;

		.renzheng {
			float: right;
		}
	}
</style>
