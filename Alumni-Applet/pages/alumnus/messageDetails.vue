<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<newsDetail :options="detail" :photos="detail.thumb" @likeHandler="likeHandler" @shareHandler="shareHandler"></newsDetail>
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
	import { getAlumnusNewsById } from '@/api/alumnus.js'
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
			this.getAlumnusNewsById(this.id);
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
			getAlumnusNewsById(id) {
				let param = {
					id: id
				};
				getAlumnusNewsById(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let result = res.data.result;
						this.detail = {
							contents: result.context,
							title: result.title,
							createTime: result.createTime,
							createBy: result.author,
							yunshu: result.img
						};
					}
				});
			},
		}
	}
</script>

<style>

</style>
