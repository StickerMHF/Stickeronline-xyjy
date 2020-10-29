<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<!-- <view class="lightUp" v-if="lightUpState">
			<view class="lightUpText">
				共有 <text class="alumnus">{{alumnus}}</text> 位校友参与
			</view>
			<text class="lightUpBtn" @click="lightUpStart">我要点亮</text>
		</view> -->
		<view class="ann_news_content">
			<map class="map" :longitude="longitude" :latitude="latitude" :markers="markers" :covers="covers" scale="5" show-location></map>
			<view class="numsBox">
				<view class="">点亮人数<text class="textNums">{{starNums}}</text></view>
				<view class="">点亮城市<text class="textNums">{{starCitys}}</text></view>
				<view class="">点亮国家<text class="textNums">{{starCountrys}}</text></view>
			</view>
			<view class="btnBox">
				<view class="btn" @click="lightUpOrUpdate">{{textBtn}}</view>
				<view class="btn">邀请好友点亮</view>
			</view>
			<view :class="!lightUpState && (!showState) ? 'tipBox showTip' : 'tipBox hideTip'">
				<view class="tip">
					<view class="circle"></view>
					<view class="starBox">
						<view class="">您是第<text class="textNums">{{totalStars}}</text>位点亮者</view>
						<view class="">您所在的城市已有<text class="textNums">{{currentCityStars}}</text>位点亮哦</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	// var QQMapWX = require('../../../js_sdk/qqmap-wx-jssdk.js')
	import {
		getPositionList,
		addLightUpPersonnel,
		updatePersonnelPosition
	} from '@/api/cooperation.js'
	export default {
		data() {
			return {
				title:'校庆专题-点亮全球',
				userId:'',
				lightUpState: true,
				showState: true,
				alumnus:4308,
				starNums:4308,
				totalStars:4309,
				currentCityStars:45,
				starCitys:29,
				starCountrys:21,
				location:'',
				longitude:'',
				latitude:'',
				textBtn:'我要点亮',
				markers:[],
				covers:[]
			}
		},
		onLoad() {
			let that = this;
			uni.getLocation({
				type: 'gcj02',
				altitude: true,
				//定位成功，更新定位结果      
				success: function(res) {
					console.log(res)
					that.latitude = res.latitude;
					that.longitude = res.longitude;
					that.speed = res.speed;
					that.accuracy = res.accuracy;
				}, //定位失败回调      
				fail: function() {
					uni.hideLoading();
					console.log("getLocationFail")
				},
				complete: function() {
					//隐藏定位中信息进度       
					uni.hideLoading()
				}
			})
			this.getPositionListData()
			// this.getMarkerDatas();
		},
		methods: {
			getPositionListData(){
				getPositionList("").then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let datas = res.data.result;
						datas.forEach(v => {
							this.location = v.location;
							let location = v.location.split(",");
							this.markers.push({ //赋值数组
								id: v.userId,
								latitude: location[1],
								longitude: location[0],
								iconPath: '../../../static/images/marker.png', //图标路径
								width: 30,
								height: 30
							})
						})
					}
				});
			},
			lightUpStart(){
				this.lightUpState = false;
			},
			lightUpOrUpdate(){
				let that = this;
				if(this.lightUpState){
					this.userId = uni.getStorageSync('openid');
					let userInfo = uni.getStorageSync('userInfo');
					if(userInfo){
						let param = {
							userId: this.userId,
							userName: userInfo.nickName,
							userPhoto: userInfo.avatarUrl,
							location: that.location
						}
						addLightUpPersonnel(param).then(data => {
							var [error, res] = data;
							if (res && res.data.success) {
								let datas = res.data.result;
								console.log('data',JSON.stringify(datas));
								debugger
								that.starNums = that.starNums+1;
								that.textBtn = '更新位置';
								that.lightUpState = false;
								that.showState = false;
								setTimeout(() => {
									that.showState = true;
								},2000)
							}
						});
					} else {
						//跳转页面 
						 wx.navigateTo({
						 	url:'pages/login/login'
						 })
					}
				}else{
					uni.showToast({
					  title: '位置更新成功'
					});
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
.ann_news{
	width: 100%;
	height: 100%;
}
.ann_news_content{
	position: absolute;
	top: 45px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background-size: 100% 100%;
	background-color: white;
}
.map{
	width:100%;
	height:500px;
}
.lightUp{
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
}
.lightUpText{
	margin-bottom:40rpx;
}
.alumnus{
	color:#fa9a25;
}
.lightUpBtn{
	padding:8rpx 70rpx;
	background: #fa9a25;
	color:#fff;
}
.numsBox{
	display: flex;
	justify-content: space-around;
	align-items: center;
	background: #f2f2f2;
	height: 40px;
}
.textNums{
	color:#F37B1D;
	margin-left:10rpx;
}
.btnBox{
	display: flex;
	justify-content: space-around;
	margin-top:40rpx;
	.btn{
		background: #FF8901;
		color: #fff;
		padding: 0px 8px;
		width: 200rpx;
		text-align: center;
		border-radius: 4px;
	}
}
.tipBox{
	position: fixed;
	height:100rpx;
	border-radius: 30px;
	top: 180rpx;
	left: 100rpx;
	background: #bebebeb5;
}
.tip{
	// width:540rpx;
	display: flex;
	align-items: center;
	.circle{
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		background: #fff;
		margin: 0 12rpx;
	}
	.starBox{
		margin-right: 40rpx;
		.textNums{
			margin-right:10rpx;
		}
	}
}
.showTip{
	display: block;
	transition: all 2s;
}
.hideTip{
	display: none;
	transition: all 2s;
}
</style>
