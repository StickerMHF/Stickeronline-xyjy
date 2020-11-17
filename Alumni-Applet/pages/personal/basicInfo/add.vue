<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<bjx-form labelType="inline" labelWidth="150" :form="form" ref="form">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 基本信息
				</view>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="姓名" prop="name">
					<input class="basicinfo_name input" v-model="form.name" @blur="formValidator('name')" name="input" placeholder="请输入真实姓名" />
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="性别" label-right="right" prop='sex'>
					<picker @change="sexChange" :value="form.sex" :range="sex">
						<view class="picker">
							{{form.sex}}
						</view>
					</picker>
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="身份证" label-right="left" prop='identityCard'>
					<input v-model="form.identityCard" class="input" @blur="formValidator('identityCard')" name="input" placeholder="请输入身份证号" />
				</bjx-form-item>
			</view>
			<view class="cu-bar bg-white solid-bottom margin-top">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 学院信息
				</view>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="所属学院" label-right="left" prop='college'>
					<input v-model="form.college" class="input"  @blur="formValidator('college')" name="input" placeholder="所属学院" disabled="true" />
				</bjx-form-item>
			</view>
			<!-- 不是教师 -->
			<view v-if="type!='3'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="所在专业" label-right="right" prop='profession'>
					<picker @change="professionChange" :value="form.profession" :range="profession">
						<view class="picker">
							{{form.profession}}
						</view>
					</picker>
				</bjx-form-item>
			</view>
			<view v-if="type!='3'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="班级" label-right="left" prop='classGrade'>
					<input v-model="form.classGrade" class="input" @blur="formValidator('classGrade')" name="input" placeholder="请输入所在班级" />
				</bjx-form-item>
			</view>
			<view v-if="type!='3'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="学号" label-right="left" prop='studentNumber'>
					<input v-model="form.studentNumber" class="input" @blur="formValidator('studentNumber')" name="input" placeholder="请输入学号" />
				</bjx-form-item>
			</view>

			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="学历" label-right="right" prop='education'>
					<picker @change="eduChange" :value="form.education" :range="eduPicker">
						<view class="picker">
							{{form.education}}
						</view>
					</picker>
				</bjx-form-item>
			</view>
			<!-- 曾经在校 -->
			<view v-if="type=='1'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="入校时间" label-right="right" prop='startDate'>
					<picker mode="date" :value="form.startDate" start="1970-09-01" end="2030-09-01" @change="startDateChange">
						<view class="picker">
							{{form.startDate}}
						</view>
					</picker>
				</bjx-form-item>
			</view>
			<!-- 在校学生 -->
			<view v-else-if="type=='2'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="入学时间" label-right="right" prop='startDate'>
					<picker mode="date" :value="form.startDate" start="1970-09-01" end="2030-09-01" @change="startDateChange">
						<view class="picker">
							{{form.startDate}}
						</view>
					</picker>
				</bjx-form-item>
			</view>
			<!-- 在职教师 -->
			<view v-else class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="入职时间" label-right="right" prop='startDate'>
					<picker mode="date" :value="form.startDate" start="1970-09-01" end="2030-09-01" @change="startDateChange">
						<view class="picker">
							{{form.startDate}}
						</view>
					</picker>
				</bjx-form-item>
			</view>

			<!-- 曾经在校 -->
			<view v-if="type=='1'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="离校时间" label-right="right" prop='endDate'>
					<picker mode="date" :value="form.endDate" start="1970-09-01" end="2030-09-01" @change="endDateChange">
						<view class="picker">
							{{form.endDate}}
						</view>
					</picker>
				</bjx-form-item>
			</view>
			<view v-if="type=='1'" class="cu-bar bg-white solid-bottom margin-top">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 工作信息
				</view>
			</view>
			<view v-if="type=='1'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="工作单位" label-right="left" prop='company'>
					<input v-model="form.company" @blur="formValidator('company')" class="input" name="input" placeholder="请输入单位名称" />
				</bjx-form-item>
			</view>

			<view v-if="type=='1'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="职位/职称" label-right="left" prop='jobTitle'>
					<input v-model="form.jobTitle" @blur="formValidator('jobTitle')" class="input" name="input" placeholder="请输入职务/职称" />
				</bjx-form-item>
			</view>

			<view class="cu-bar bg-white solid-bottom margin-top">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 通讯信息
				</view>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="电话" label-right="left" prop='phone'>
					<input v-model="form.phone" @blur="formValidator('phone')" class="input" name="input" placeholder="请输入联系方式" />
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="微信" label-right="left" prop='wechat'>
					<input v-model="form.wechat" @blur="formValidator('wechat')" class="input" name="input" placeholder="请输入微信号(选填)" />
				</bjx-form-item>
			</view>
			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="QQ" label-right="left" prop='qq'>
					<input v-model="form.qq" @blur="formValidator('qq')" class="input" name="input" placeholder="请输入QQ号码(选填)" />
				</bjx-form-item>
			</view>

			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="Email" label-right="left" prop='email'>
					<input v-model="form.email" @blur="formValidator('email')" class="input" name="input" placeholder="请输入邮箱地址(选填)" />
				</bjx-form-item>
			</view>

			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="住址" label-right="left" prop='address'>
					<input v-model="form.address" @blur="formValidator('address')" class="input" name="input" placeholder="请输入住址(选填)" />
				</bjx-form-item>
			</view>

			<view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="备注" label-right="left" prop='remark'>
					<input v-model="form.remark" class="input" name="input" placeholder="(选填)" />
				</bjx-form-item>
			</view>

			<button class="bg-gradual-green1 margin" type="primary" @tap="submitForm">
				提交
			</button>
		</bjx-form>
	</view>
</template>

<script>
	import bjxForm from '@/components/bjx-form/bjx-form.vue'
	import bjxFormItem from '@/components/bjx-form/bjx-form-item.vue'
	import {
		addWechatUser,
		updateWechatUser,
		getWechatUserById
	} from '@/api/user.js'
	import WxValidate from '@/utils/WxValidate.js'
	export default {
		components: {
			bjxForm,
			bjxFormItem
		},
		data() {
			return {
				type: "",
				isEdit: false,
				form: {
					openid: '',
					name: '',
					sex: '男',
					identityCard: '',
					college: '地测学院',
					profession: '勘查技术与工程',
					education: '本科',
					startDate: '2018-09-01',
					endDate: '2020-06-20',
					classGrade: '',
					studentNumber: '',
					email: '',
					qq: '',
					address: '',
					company: '',
					jobTitle: '',
					phone: '',
					wechat: '',
					remark: ''
				},
				index: -1,
				eduIndex: -1,
				sexIndex: -1,
				professionIndex: -1,
				// picker: ['喵喵喵', '汪汪汪', '哼唧哼唧'],
				sex: ['男', '女'],
				eduPicker: ['大专', '本科', '硕士', '博士'],
				profession: ['勘查技术与工程', '地球物理学', '测绘工程', '地理信息科学', '遥感科学与技术', '安全工程', '地质工程'],
				rules: {
					name: {
						required: true,
						minlength: 2,
						
					},
					identityCard: {
						required: true,
						idcard: true
					},
					college: {
						required: true
					},
					education: {
						required: true
					},
					startDate: {
						required: true
					},
					endDate: {
						required: true
					},
					classGrade: {
						required: true,
						minlength: 3
					},
					studentNumber: {
						required: true,
						digits: true
					},
					company: {
						required: true
					},
					jobTitle: {
						required: true
					},
					phone: {
						required: true,
						tel: true
					},
					email: {
						required: false,
						email: true
					},
					qq: {
						required: false,
						number: true
					}					
				},
				messages: {
					name: {
						required: '请输入姓名',
						minlength: '请输入真实姓名'
					},
					identityCard: {
						required: '请输入身份证号码',
						idcard: '请正确填写身份证号码'
					},
					college: {
						required: '请选择学院'
					},
					education: {
						required: '请选择学历'
					},
					startDate: {
						required: '请选择时间'
					},
					endDate: {
						required: '请选择离校时间'
					},
					classGrade: {
						required: '请输入班级',
						minlength: '请正确填写班级'
					},
					studentNumber: {
						required: '请输入学号',
						digits: '请输入有效学号'
					},
					company: {
						required: '请输入工作单位'
					},
					jobTitle: {
						required: '请输入职务'
					},
					phone: {
						required: '请输入手机号码',
						tel: '请正确填写手机号码'
					},
					email: {
						required: '请输入邮箱地址',
						email: '请正确填写邮箱地址'
					},
					qq: {
						required: '请输入QQ号码',
						number: '请正确填写QQ号码'
					}
				}
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title = options.title;
			this.type = options.type;
			this.isEdit = options.isEdit;
			if (!this.type) {}
			if (this.isEdit || this.isEdit == "true") {
				this.isEdit = true;
				this.getWechatUserInfo();
			}
			this.initValidate()
		},
		methods: {

			//验证函数
			initValidate() {
				const rules = this.rules;
				const messages = this.messages;
				this.WxValidate = new WxValidate(rules, messages)
			},
			formValidator (param){
				// let rules = this.rules[param];
				//校验表单
				// if(!this.WxValidate.checkParam(param, rules, this.form)){
				// 	debugger
				// 	const error = this.WxValidate.errorList[0];
				// 	this.showModal(error);
				// 	return false;
				// }
				let params = this.form;
				if (!this.WxValidate.checkForm(params)) {
					const error = this.WxValidate.errorList[0]
					if(error.param == param){
						this.showModal(error)
						return false
					}					
				}
			},
			//调用验证函数
			submitForm () {
				let params = this.form;
				//校验表单
				if (!this.WxValidate.checkForm(params)) {
					const error = this.WxValidate.errorList[0]
					this.showModal(error)
					return false
				}
				// this.showModal({
				// 	msg: '提交成功'
				// })
				this.save(this.form);
				
			},
			showModal(error) {
				wx.showModal({
					content: error.msg,
					showCancel: false,
				})
			},			
			// submit(e) {
			// 	debugger
			// 	let that = this;
			// 	this.$refs.form.validate(val => {
			// 		if (val) {
			// 			this.save(that.form);
			// 		}
			// 	})
			// },
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
								that.form = ss;
								that.type = that.form.type;
							} else {}
						}
					});
				} else {
					getApp().getUserInfo();
				}
			},
			formatTime(date) {
				return getApp().formatTime(date);
			},
			textareaInput(e) {
				this.contents = e.detail.value
			},
			pickerChange(e) {
				this.index = e.detail.value
			},
			eduChange(e) {
				this.eduIndex = e.detail.value;
				this.form.education = this.eduPicker[this.eduIndex];
			},
			sexChange(e) {
				this.sexIndex = e.detail.value;
				this.form.sex = this.sex[this.sexIndex];
			},
			professionChange(e) {
				this.professionIndex = e.detail.value;
				this.form.profession = this.profession[this.professionIndex];
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
			save(formData) {
				let that = this;
				let userInfo = uni.getStorageSync('userInfo');
				let params = Object.assign(userInfo, formData);
				let openid = uni.getStorageSync('openid');
				if (openid && openid != "") {
					params.openid = openid;
					params.type = that.type;
					params.updateTime = that.formatTime(params.updateTime);
					params.createTime = that.formatTime(params.createTime);
					if (!this.isEdit) {
						this.addWechatUser(params);
					} else {
						this.updateWechatUser(params);
					}
				} else {
					getApp().getUserInfo();
				}
			},
			addWechatUser(params) {
				addWechatUser(params).then(data => {
					var [error, res] = data;
					if (res && res.data && res.data.success) {
						uni.navigateBack({
							delta: 2
						});
					} else {
						uni.showModal({
							content: '保存失败，请稍后再试：' + JSON.stringify(res.data),
							showCancel: false
						})
					}
				})
			},
			updateWechatUser(params) {
				updateWechatUser(params).then(data => {
					var [error, res] = data;
					if (res && res.data && res.data.success) {
						uni.navigateBack({
							delta: 2
						});
					} else {
						uni.showModal({
							content: '保存失败，请稍后再试：' + JSON.stringify(res.data),
							showCancel: false
						})
					}
				})
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
