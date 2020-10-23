<template>
	<view class="anniversary">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="anniversary_content" style="background-image:url(../../static/anniversary/background.png);">
			<view class="anniversary_menu">
				<view class="grid col-3 grid-square">
					<view class="anniversary_item" v-for="(item,index) in menu" :key="index" >
						<view class="anniversary_item_img" :style="'background-image:url('+item.url+');'"></view>
						<view class="anniversary_item_text">
							{{item.title}}
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: "",
				menu:[{
					url:"/static/anniversary/xqzx2x.png",
					title:"校庆资讯"
				},
				{
					url:"/static/anniversary/xqhd2x.png",
					title:"校庆活动"
				},
				{
					url:"/static/anniversary/xqzf2x.png",
					title:"校庆祝福"
				},
				{
					url:"/static/anniversary/xqtx2x.png",
					title:"校庆头像"
				},
				{
					url:"/static/anniversary/dlqq2x.png",
					title:"点亮全球"
				},
				{
					url:"/static/anniversary/zpzj2x.png",
					title:"照片征集"
				}]
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title = options.title;
		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.anniversary {
		width: 100%;
		height: 100%;
	}

	.anniversary_content {
		position: absolute;
		top: 45px;
		bottom: 0px;
		left: 0px;
		right: 0px;
		background-size: 100% 100%;
		background-color: white;
	}

	.anniversary_menu {
		top: 200px;
		position: relative;
		padding: 30px;
	}
	.anniversary_item{
		text-align: center;
	}
	.anniversary_item_img{
		width: 150rpx;
		height: 150rpx;
		margin: 0 auto;
		background-size: 100% 100%;
	}
</style>
