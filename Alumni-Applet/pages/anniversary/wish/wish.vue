<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content">
			<view class="bulletChatArea">
				<lff-barrage ref="lffBarrage"></lff-barrage>
			</view>
			<view class="sendBox">
				<textarea class="sendText" :value="textContent" placeholder="请输入祝福语" @input="changeText"/>
				<button type="default" @click="colrdo" class="sendBtn">发送祝福</button>
			</view>
		</view>
	</view>
</template>

<script>
	import lffBarrage from '@/components/lff-barrage/lff-barrage.vue'
	export default {
		data() {
			return {
				title:'校庆祝福',
				textContent:''
			}
		},
		components:{lffBarrage},
		methods: {
			changeText(e){
				this.textContent = e.target.value;
			},
			colrdo(){//插入一条弹幕
			let that = this;
				uni.login({
				  provider: 'weixin',
				  success: function (loginRes) {
				    console.log(loginRes.authResult);
				    // 获取用户信息
				    uni.getUserInfo({
				      provider: 'weixin',
				      success: function (infoRes) {
						if(that.textContent === ""){
							uni.showToast({
								icon:'none',
								title:"请输入祝福语"
							})
							return;
						}
						that.$refs.lffBarrage.add({item:that.textContent,name:infoRes.userInfo.nickName,avatarUrl:infoRes.userInfo.avatarUrl});
						uni.showToast({
							title:"发送成功"
						})
						that.textContent = "";
				      },
					  fail:function (err) {
						  console.log(err);
						  uni.showToast({
						  	icon:'none',
							title:'请登录后重试'
						  })
					  }
				    });
				  }
				});
				
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
	top: 120rpx;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background-size: 100% 100%;
	background-color: white;
}
.sendBox{
	position: fixed;
	bottom: 0;
	width: 100%;
	height: 200rpx;
	.sendBtn{
		width: 240rpx;
		height: 60rpx;
		background: #F37B1D;
		color: #fff;
		font-size: 16px;
		line-height: 60rpx;
		margin-top:40rpx;
	}
	.sendText{
		width:100%;
		height:70rpx;
		line-height:60rpx;
		padding: 10rpx 0;
		text-indent: 10px;
		background: #fff;
		border: 1px solid #f2f2f2;
	}
}
</style>
