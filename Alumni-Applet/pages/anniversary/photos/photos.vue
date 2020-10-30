<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content" style="background-image: url(http://www.imapway.cn/Alumni/static/anniversary/zpzj.png);">
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title:'照片征集'
			}
		},
		methods: {
			
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
</style>
