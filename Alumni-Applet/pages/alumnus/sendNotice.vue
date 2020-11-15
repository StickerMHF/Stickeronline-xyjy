<template>
	<view style="overflow-x: hidden;background: #fff;">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="content">发布公告</block>
		</cu-custom>
		<view class="album-content">
			<input class="noticeTitle" type="text" v-model="titleValue" placeholder="标题(5~20个字)" />
			<textarea class="noticeContent" v-model="noticeContent" placeholder="请输入内容" />
			<view class="dateBox">
				<!-- <i class="cuIcon-location"></i> -->
				<picker mode="date" :value="date" @change="bindDateChange">
					<view class="uni-input">{{date}}</view>
				</picker>
			</view>
		</view>
		<view class="footer">
			<button type="default" class="feedback-submit" @click="send">发布</button>
		</view>
	</view>
</template>

<script>
	import {sendNotice} from '@/api/alumnus.js'
	export default {
		data() {
			const currentDate = this.getDate({
				format: true
			});
			return {
				titleValue:"",
				noticeContent:"",
				date: currentDate,
				userName:'',
				openId:'',
				fid:''
			}
		},
		onLoad(options) {
			this.fid = options.id;
			let userInfo = uni.getStorageSync("userInfo");
			if(userInfo === undefined){
				uni.reLaunch({
				    url: '/pages/login/login'
				});
			}
			this.userName = userInfo.nickName;
			this.openId = uni.getStorageSync("openid");
		},
		methods: {
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
	
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			bindDateChange: function(e) {
				this.date = e.target.value
			},
			send(){
				let param = {
					createBy:this.userName,
					title:this.titleValue,
					context:this.noticeContent,
					author:this.userName,
					openId:this.openId,
					fid:this.fid,
					img:'http://cdxyh.stickeronline.cn/banner12x.png',
					date:this.date
				}
				if(this.titleValue === "" || this.noticeContent === ""){
					uni.showToast({
						icon:'none',
						title:'请完善信息'
					})
					return;
				}
				sendNotice(param).then(data => {
					let [error, res] = data;
					if (res && res.data && res.data.result) {
						if(res.data.success){
							uni.showToast({
								title:'发布成功'
							})
							setTimeout(v => {
								uni.navigateBack();
							},500)
						}
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	body{
		background: #fff;
	}
	.album-content{
		width: 100%;
		font-size: 14px;
		background: #fff;
		padding: 10px;
		.noticeTitle{
			margin-bottom: 20rpx;
			height: 30px;
		}
		.noticeContent{
			width: 100%;
			background: #f2f2f2;
			border-radius: 6px;
			padding: 5px;
			text-indent: 10px;
		}
	}
	.footer{
		position: fixed;
		width: 100%;
		bottom: 0;
		.feedback-submit{
		    color: #fff;
		    background-color: #00beb7;
		}
	}
</style>
