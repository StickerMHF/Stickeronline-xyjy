<template>
	<view class="newsContent">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<newsDetail :options="detail" :photos="JSON.parse(detail.thumb)" @likeHandler="likeHandler" @shareHandler="shareHandler"></newsDetail>
	<!-- 分享弹窗 -->
	<uni-popup ref="sharepopup" type="bottom">
		<share-btn :sharedataTemp="sharedata"></share-btn>
	</uni-popup>
	</view>
</template>

<script>
	import newsDetail from '@/components/news-detail/index.vue';
	import uniPopup from '@/components/uni-popup/uni-popup.vue';
	import shareBtn from '@/components/share-btn/share-btn.vue';
	import {
		getNewsById
	} from '@/api/news.js'
	import {dateUtil} from '@/utils/dateUtil.js'
	export default {
		components: {
			newsDetail,
			uniPopup,
			shareBtn
		},
		data() {
			return {
				detail: {
					yunshu:
					  "http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_0.png",
					createBy: "天邦科技",
					like:
					  "http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_1.png",
					share:
					  "http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_2.png",
					createTime: 1603468800000,
					title: "",
					thumb:
					  "",
					contents:
					  ""
				},
				sharedata: {
					type: 1,
					strShareUrl: "http://www.baidu.com",
					strShareTitle: "分享标题",
					strShareSummary: "分享总结",
					strShareImageUrl: "http://www.xuelejia.com/xljapp/h5/static/aboutUsLogo.png"
				},
				id: ''
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.id = options.id;
			this.getNewsById(this.id);
		},
		methods: {
			formatDate(date){
				return dateUtil.formatDate(date);
			},
			likeHandler(){
			},
			shareHandler(){
				this.$refs.sharepopup.open();
			},
			getNewsById(id) {
				let param = {
					id: id
				};
				getNewsById(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let ss = res.data.result;
						this.detail = res.data.result;
					}
				});
			},
		}
	}
</script>

<style lang="scss">
	// .newsContent{
	// 	.hm-news-detail{
	// 		height:100%;
	// 	}
	// }
</style>
