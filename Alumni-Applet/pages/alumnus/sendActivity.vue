<template>
	<view style="overflow-x: hidden;">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="content">发布活动</block>		
		</cu-custom>
		<view class="album-content">
			<view class="listBox">
				<text class="labelText">活动标题：</text>
				<input class="title" type="text" v-model="title" placeholder="标题(5~20个字)" />
			</view>
			<view class="listBox">
				<text class="labelText">活动内容：</text>
				<textarea class="noticeContent" v-model="content" placeholder="请输入内容" />
			</view>
			<view class="listBox">
				<text class="labelText">活动地址：</text>
				<view class="textIcon" @click="selectAddress">
					<text>{{address}}</text>
					<i class="icon cuIcon-location"></i>
				</view>
			</view>
		</view>
		<view class="placeholder"></view>
		<view class="album-content">
			<view class="listBox">
				<text class="labelText">收费标准：</text>
				<input class="title" type="number" v-model="money" placeholder="100(元/人)" />
			</view>
			<view class="listBox">
				<text class="labelText">报名日期：</text>
				<view class="dateBox">
					<picker mode="date" :value="date" @change="bindDateChange">
						<view class="uni-input">{{date}}</view>
					</picker>
				</view>
			</view>
			<view class="listBox">
				<text class="labelText">开始日期：</text>
				<view class="dateBox">
					<picker mode="date" :value="startDate" @change="bindstartDateChange">
						<view class="uni-input">{{startDate}}</view>
					</picker>
				</view>
			</view>
			<view class="listBox">
				<text class="labelText">结束日期：</text>
				<view class="dateBox">
					<picker mode="date" :value="endDate" @change="bindendDateChange">
						<view class="uni-input">{{endDate}}</view>
					</picker>
				</view>
			</view>
		</view>
		<view class="footer">
			<button type="default" class="feedback-submit" @click="send">发布</button>
		</view>
	</view>
</template>

<script>
	import {sendActivity} from '@/api/alumnus.js';
	const chooseLocation = requirePlugin('chooseLocation');
	export default {
		data() {
			const currentDate = this.getDate({
				format: true
			});
			return {
				title:"",
				content:"",
				address:'陕西省西安市高新区',
				money:"",
				date: currentDate,
				startDate: currentDate,
				endDate: currentDate,
				createBy:'',
				fid:'',
				location:''
			}
		},
		onLoad(options) {
			this.fid = options.id;
			let userInfo = uni.getStorageSync("userInfo");
			this.createBy = userInfo.nickName;
		},
		onShow () {
			const location = chooseLocation.getLocation(); // 如果点击确认选点按钮，则返回选点结果对象，否则返回null
			if(location !== null){
				this.address = location.address;
				this.location = JSON.stringify({
				  latitude: location.latitude,
				  longitude: location.longitude
				});
			}
		},
		onUnload () {
		  // 页面卸载时设置插件选点数据为null，防止再次进入页面，geLocation返回的是上次选点结果
		  chooseLocation.setLocation(null);
	  },
		methods: {
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
				uni.navigateTo({
				  url: `plugin://chooseLocation/index?key=${this.txKey}&referer=${this.referer}&location=${location}`
				});
			},
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
			bindstartDateChange: function(e) {
				this.startDate = e.target.value
			},
			bindendDateChange: function(e) {
				this.endDate = e.target.value
			},
			send(){
				let param = {
					title:this.title,
					context:this.content,
					fid:this.fid,
					startTime:this.startDate+' 00:00:00',
					endTime:this.endDate+' 00:00:00',
					address:this.address,
					deadline:this.date+' 00:00:00',
					createBy:this.createBy,
					money:this.money,
					img:['http://cdxyh.stickeronline.cn/banner12x.png'],
					type:2
				}
				sendActivity(param).then(data => {
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

<style lang="scss" scoped>
	.album-content{
		width: 100%;
		font-size: 16px;
		background: #fff;
		padding: 10px;
		.title{
			background: #f2f2f2;
			width: 84%;
			border-radius: 6px;
			line-height: 70rpx;
			height: 70rpx;
			text-indent: 10px;
			padding: 0 5px;
		}
		.noticeContent{
			background: #f2f2f2;
			border-radius: 6px;
			padding: 5px;
			text-indent: 10px;
		}
	}
	.placeholder{
		width: 100%;
		height: 30rpx;
	}
	.listBox{
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
		.labelText{
			min-width:90px;
		}
		.dateBox{
			margin-top: -15px;
		}
		.textIcon{
			display: flex;
			justify-content: space-between;
			align-items: center;
			width: 75%;
			.icon{
				color: #00beb7;
				font-size: 20px;
			}
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
