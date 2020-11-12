<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<!-- <view class="place">
			
		</view> -->
		<view class="ann_news_content" style="background-image: url(http://www.imapway.cn/Alumni/static/anniversary/xqzx.png1);">
			<!-- <newsList :newsList="lists"></newsList> -->
			<newsList2 :list="lists2"></newsList2>
		</view>
	</view>
</template>

<script>
	import newsItem from "@/components/news-list/news-item.vue"
	// import newsList from "@/components/news-list/news-list.vue"
	import newsList2 from '@/components/news-list2/index.vue'; 
	import {
		getNewsList
	} from '@/api/news.js'
	export default {
		components: {
		newsItem,
		newsList2
		},
		
		data() {
			return {
				lists:[],
				lists2:[],
				status: 'more', // 加载状态
				tipShow: false, // 是否显示顶部提示框
				pageSize: 10, // 每页显示的数据条数
				current: 1 // 当前页数		
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title=options.title;
			this.getNewsList(true);
		},
		methods: {
			formatDate(date){
				return getApp().formatDate(date);
			},
			/**
			 * 获取页面数据
			 * @param {Object} reload 参数reload值为true时执行列表初始化逻辑，值为false时执行追加下一页数据的逻辑。默认为false
			 */
			getNewsList(reload) {
				this.status = 'loading'
				let param = {
					pageNo: this.current,
					pageSize: this.pageSize,
					type:0
				};
				getNewsList(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let ss = res.data.result.content;
						const tempList =  res.data.result.content;
						// this.lists = res.data.result.content;
									// 判断是否可翻页
									if (tempList.length === this.pageSize) {
										this.status = 'more'
									} else {
										this.status = 'noMore'
									}
						
									if (reload) {
										// 处理下拉加载提示框
										this.tipShow = true;
										clearTimeout(this.timer);
										this.timer = setTimeout(() => {
											this.tipShow = false;
										}, 2000);
										this.lists = tempList
										this.lists2 = this.lists.map(item=>{
											return {
														id: item.id,
														username: item.createBy,
														publishDate: item.createTime,
														photo: "http://cdxyh.stickeronline.cn/logo.jpg",
														content: item.title,
														images: this.dataTar(item.thumb),
																viewCount: item.viewCount || 0,
																
															}
										})
										// 停止刷新
										uni.stopPullDownRefresh()
									} else {
										// 上拉加载后合并数据
										this.lists = this.lists.concat(tempList)
									}
									if (tempList.length) {
										this.current++
									}
					}
				});
			},
			dataTar(str){
				let arr = JSON.parse(str)
				return arr.map(item=>{
					return {
						url: item
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	// .place{
	// 	width: 100%;
	// 	height:60rpx;
	// }
.ann_news{
	width: 100%;
	height: 100%;
}
.ann_news_content{
	position: absolute;
	top: 60px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background-size: 100% 100%;
	background-color: white;
}
</style>
