<template>
	<view>
		<view class="header">
			<view class="bg" style="
		      background-image: url(http://www.imapway.cn/Alumni/static/personal/background.png);
		    ">
				<cu-custom style="color: #fff;" :isBack="true" :isCallBack="false" @callBack="callBack">
					<block slot="content">我的收藏</block>
				</cu-custom>
			</view>
			<view class="apply_content">暂无数据</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="less" scoped>
.apply_content{
	width: 100%;
	height: 100%;
	text-align: center;
	margin: 20rpx;
}
</style>
