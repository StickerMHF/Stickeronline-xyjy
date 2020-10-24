<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<newsDetail :options="detail" :photos="JSON.parse(detail.thumb)"></newsDetail>
	</view>
</template>

<script>
	import newsDetail from '@/components/news-detail/index.vue';
	import {
		getNewsById
	} from '@/api/news.js'
	import {dateUtil} from '@/utils/dateUtil.js'
	export default {
		components: {
			newsDetail
		},
		data() {
			return {
				detail: {
					yunshu:
					  'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_0.png',
					createBy: '天邦科技',
					like:
					  'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_1.png',
					share:
					  'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_2.png',
					createTime: 1603468800000,
					title: '',
					thumb:
					  "",
					contents:
					  ''
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

<style>

</style>
