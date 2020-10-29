<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content">
			<wechatAvatar :imgList="sources"></wechatAvatar>
		</view>
	</view>
</template>

<script>
	import wechatAvatar from "@/components/wechat-avatar/wechat-avatar.vue";
	export default {
		components:{
			wechatAvatar
		},
		data() {
			return {
				sources:["../../static/anniversary/head0.png","../../static/anniversary/head1.png","../../static/anniversary/head2.png","../../static/anniversary/head3.png","../../static/anniversary/head0.png","../../static/anniversary/head1.png"]
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
