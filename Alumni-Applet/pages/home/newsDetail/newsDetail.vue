<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<newsDetail :options="detail"></newsDetail>
	</view>
</template>

<script>
	import newsDetail from '@/components/news-detail/index.vue';
	export default {
		components: {
			newsDetail
		},
		data() {
			return {
				detail: {
					yunshu: '/static/hm-news-detail/images/img_22946_0_0.png',
					author: '天邦科技',
					like: '/static/hm-news-detail/images/img_22946_0_1.png',
					share: '/static/hm-news-detail/images/img_22946_0_2.png',
					time: '2 minutes ago',
					title: '如何用 Python 在笔记本上分析100GB 数据.',
					img: '/static/hm-news-detail/images/img_22946_0_3.png',
					content: '第 1 种是对数据进行子抽样，但它有一个明显缺点：可能因忽略部分数据而错失关键信息，甚至误解数据表达的含义。第 2 种是使用分布式计算。虽然在某些情况下这是一种有效的方法，但是管理和维护集群会带来巨大开销。想象一下，要为一个刚超出内存大小、大概 30-50GB 的数据集就建立一套集群，对我来说，这似乎有点“用力过猛”。'
				}
			}
		},
		methods: {

		}
	}
</script>

<style>

</style>
