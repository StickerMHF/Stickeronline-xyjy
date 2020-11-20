<template>
	<view>
		<scroll-view scroll-y class="indexes" :scroll-into-view="'indexes-'+ listCurID" :style="[{height:'calc(100vh - '+ CustomBar + 'px - 50px)'}]"
		 :scroll-with-animation="true" :enable-back-to-top="true">
			<block v-for="(item,index) in dataList" :key="index">
				<view :class="'indexItem-' + item.name" :id="'indexes-' + item.name" :data-index="item.name">
					<view class="cu-list menu-avatar no-padding">
						<view class="cu-item">
							<view v-if="item.avatarurl" @click="avatarHandler(item.id)" class="cu-avatar round lg" :style="'background-image:url('+item.avatarurl+');'"></view>
							<view v-else class="cu-avatar round lg" style="background-image:url('http://www.imapway.cn/Alumni/static/alumnus/default_photo.png')"></view>
							<view class="content">
								<view class="text-grey">{{item.name}}
								<text v-if="item.president==2" class="text-abc">(会长)</text>
								<text v-else-if="item.president==1" class="text-abc">(副会长)</text>
								<text v-else class="text-abc"></text>
								</view>
							</view>
							<view class="margin-tb-sm text-center mem-attention">
								<button  v-if="item.id==userId" class="cu-btn round bg-yellow">我</button>
								<button @click="delAttention(item)" v-else-if="item.attention&&item.attention>=1" class="cu-btn round bg-yellow">已关注</button>
								<button @click="payHandler(item)" v-else class="cu-btn round bg-gradual-green1">关注</button>
							</view>
						</view>
					</view>
				</view>
			</block>
		</scroll-view>
	</view>
</template>

<script>
	import {addWechatUserAttention, deleteWechatUserAttention} from '@/api/user.js';
	export default {
		data() {
			return {
				dataList: this.list,
				userId:""
			};
		},
		watch:{
		    list : {
		　　　　handler(newValue, oldValue) {
		　　　　　　this.dataList = newValue
		　　　　},
		　　　　deep: true
		　　}
		},
		props: {
			list: {
				type: Array,
				default: function() {
					return [];
				}
			}
		},
		mounted() {
			this.userId =  uni.getStorageSync('openid');
		},
		methods: {
			avatarHandler(openid){
				if(openid!=null){
					uni.navigateTo({
						url:"/pages/personal/userDetail/userDetail?userId="+openid
					})
				}
			},
			payHandler(item){
				//获取用户ID
				let userId =  uni.getStorageSync('openid');
				let params = {
					memberId: item.id,
					userId: userId
				}
				addWechatUserAttention(params).then(data =>{
					// debugger
					let [error, res] = data;
					if(res&&res.data&&res.data.success){
						uni.showToast({
						    title: '关注成功',
						    duration: 2000
						});
						item.attention = 1;
					} else{
						uni.showToast({
						    title: '服务器忙',
						    duration: 2000
						});
					}					
				});				
			},
			delAttention(item){
				let userId =  uni.getStorageSync('openid');
				let params = {
					memberId: item.id,
					userId: userId
				}
				deleteWechatUserAttention(params).then(data =>{
					// debugger
					let [error, res] = data;
					if(res&&res.data&&res.data.success){
						uni.showToast({
						    title: '取消关注',
						    duration: 2000
						});
						item.attention = 0;
					} else{
						uni.showToast({
						    title: '服务器忙',
						    duration: 2000
						});
					}					
				});		
			}
		}
	};
</script>

<style lang="less" scoped>
.cu-btn{
	width: 150rpx;
	height: 50rpx;
	font-size: 28rpx;
}
</style>
