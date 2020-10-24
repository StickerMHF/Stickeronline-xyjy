<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<!-- 刷新页面后的顶部提示框 -->
		<view class="tips" :class="{ 'tips-ani': tipShow }">为您更新了10条最新新闻动态</view>
		<!-- 基于 uni-list 的页面布局 -->
		<uni-list>
			
			<uni-list-item :to="'/pages/home/newsDetail/newsDetail?id='+item.id" direction="column" v-for="item in lists" :key="item.id">
				
				<!-- <template v-slot:header>
					<view class="uni-title">{{item.title}}</view>
				</template> -->
				<!-- 通过body插槽定义列表内容显示 -->
				<template v-slot:body>
					<view class="uni-list-box">
						<view class="uni-thumb">
							<image :src="JSON.parse(item.thumb)[0]" mode="aspectFill"></image>
						</view>
						<view class="uni-content">
							<view class="uni-title-sub uni-ellipsis-2">{{item.title}}</view>
							<view class="uni-note">{{ item.createBy }} {{ formatDate(item.createTime) }}</view>
						</view>
					</view>
				</template>
				<!-- 同步footer插槽定义列表底部的显示效果 -->
				<!-- <template v-slot:footer>
					<view class="uni-footer">
						<text class="uni-footer-text">评论</text>
						<text class="uni-footer-text">点赞</text>
						<text class="uni-footer-text">分享</text>
					</view>
				</template> -->
				
			</uni-list-item>
		</uni-list>
		<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
		<uni-load-more v-if="lists.length > 0" :status="status" />
	</view>
</template>

<script>
	import {dateUtil} from '@/utils/dateUtil.js'
	import {
		getNewsList
	} from '@/api/news.js'
	export default {
		data() {
			return {
				lists: [], // 列表数据
				status: 'more', // 加载状态
				tipShow: false, // 是否显示顶部提示框
				pageSize: 10, // 每页显示的数据条数
				current: 1 // 当前页数			
			};
		},
		onLoad(options) {
			// 初始化页面数据
			this.title=options.title;
			this.getNewsList(true);
		},
		methods: {
			formatDate(date){
				return dateUtil.formatDate(date);
			},
			/**
			 * 下拉刷新回调函数
			 */
			onPullDownRefresh() {
				this.current = 1
				this.getNewsList(true);
			},
			/**
			 * 上拉加载回调函数
			 */
			onReachBottom() {
				this.getNewsList();
			},
			/**
			 * 获取页面数据
			 * @param {Object} reload 参数reload值为true时执行列表初始化逻辑，值为false时执行追加下一页数据的逻辑。默认为false
			 */
			getNewsList(reload) {
				this.status = 'loading'
				let param = {
					pageNo: this.current,
					pageSize: this.pageSize
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
				// 通过 clientDB 请求后台数据
				// 	db.collection('opendb-news-articles')
				// 		.where({
				// 			// 查询字段是否存在
				// 			_id: dbCmd.exists(true)
				// 		})
				// 		// 跳过对应数量的文档，输出剩下的文档
				// 		.skip(this.pageSize * (this.current - 1))
				// 		// 限制输出到下一阶段的记录数
				// 		.limit(this.pageSize)
				// 		// 获取集合中的记录
				// 		.get()
				// 		.then((res) => {
				// 			const tempList = res.result.data
				// 			// 判断是否可翻页
				// 			if (tempList.length === this.pageSize) {
				// 				this.status = 'more'
				// 			} else {
				// 				this.status = 'noMore'
				// 			}

				// 			if (reload) {
				// 				// 处理下拉加载提示框
				// 				this.tipShow = true;
				// 				clearTimeout(this.timer);
				// 				this.timer = setTimeout(() => {
				// 					this.tipShow = false;
				// 				}, 2000);
				// 				this.lists = tempList
				// 				// 停止刷新
				// 				uni.stopPullDownRefresh()
				// 			} else {
				// 				// 上拉加载后合并数据
				// 				this.lists = this.lists.concat(tempList)
				// 			}
				// 			if (tempList.length) {
				// 				this.current++
				// 			}
				// 		}).catch((err) => {
				// 			uni.showModal({
				// 				content: '请求失败，请稍后再试：' + err,
				// 				showCancel: false
				// 			})
				// 		})
			}
		}
	};
</script>

<style lang="scss">
	@import '@/common/uni-ui.scss';

	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}

	.tips {
		color: #67c23a;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
		background-color: #f0f9eb;
		height: 0;
		opacity: 0;
		transform: translateY(-100%);
		transition: all 0.3s;
	}

	.tips-ani {
		transform: translateY(0);
		height: 40px;
		opacity: 1;
	}

	.content {
		width: 100%;
		display: flex;
	}

	.list-picture {
		width: 100%;
		height: 145px;
	}

	.thumb-image {
		width: 100%;
		height: 100%;
	}

	.ellipsis {
		display: flex;
		overflow: hidden;
	}

	.uni-ellipsis-1 {
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.uni-ellipsis-2 {
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
	}
</style>
