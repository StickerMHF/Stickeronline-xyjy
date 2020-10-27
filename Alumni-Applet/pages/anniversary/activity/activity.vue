<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content" style="background-image: url(http://www.imapway.cn/Alumni/static/anniversary/xqhd.png1);">
			<activityDetails :opts="details"></activityDetails>
		</view>
	</view>
</template>

<script>
	import activityDetails from "@/components/list-activity/activity-details.vue"
	export default {
		components:{
			activityDetails
		},
		data() {
			return {
				title:"",
				details:{
						url: "http://cdxyh.stickeronline.cn/banner12x.png",
						title: "长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行",
						startTime: "2020年11月11日",
						endTime: "2020年12月12日",
						address: "长安大学渭水校区",
						deadline: "2020年10月10日",
						content: "<h1>长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行</h1>",
						joinNum: 23
					}
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
