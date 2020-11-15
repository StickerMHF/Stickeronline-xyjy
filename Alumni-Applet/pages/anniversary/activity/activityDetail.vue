<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content" style="background-image: url(http://www.imapway.cn/Alumni/static/anniversary/xqhd.png1);">
			<activityDetails ref="activityDesc" :opts="details" @apply="applyHandler"></activityDetails>
		</view>
	</view>
</template>

<script>
	import activityDetails from "@/components/list-activity/activity-details.vue"
	import {
		addActivityApply,getAlumnusActivityById
	} from '@/api/alumnus.js'
	export default {
		components: {
			activityDetails
		},
		data() {
			return {
				title: "活动",
				id:"",
				details: {
					url: "http://cdxyh.stickeronline.cn/banner12x.png",
					title: "长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行",
					startTime: "2020年11月11日",
					endTime: "2020年12月12日",
					address: "长安大学渭水校区",
					deadline: "2020年10月10日",
					context: "<h1>长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行</h1>",
					joinNum: 23
				}
			}
		},
		onLoad(options) {
			this.id = options.id;
		},
		mounted() {
			this.getAlumnusActivityById();
		},
		methods: {
			getAlumnusActivityById(){
				let openid = uni.getStorageSync('openid');
				let p = {
					userId: openid,
					id: this.id
				}
				getAlumnusActivityById(p).then(r=>{
					let [error, res] = r;
					if (res && res.data.success){
						this.details = res.data.result;
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.ann_news {
		width: 100%;
		height: 100%;
		background: white;
	}

	.ann_news_content {
		// position: absolute;
		top: 5px;
		bottom: 0px;
		left: 0px;
		right: 0px;
		background-size: 100% 100%;
		background-color: white;
	}
</style>
