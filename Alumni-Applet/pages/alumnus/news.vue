<template>
	<!-- 校友捐赠 -->
	<view class="list">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<!-- 刷新页面后的顶部提示框 -->
		<!-- 当前弹出内容没有实际逻辑 ，可根据当前业务修改弹出提示 -->
		<view class="tips" :class="{ 'tips-ani': tipShow }">为您更新了10条最新新闻动态</view>
		
		<listNews></listNews>
		<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
		<uni-load-more v-if="lists.length > 0" :status="status" />
	</view>
</template>

<script>
	
	import {getUserInfo1} from '@/api/user.js'
	import listNews from "@/components/list-news/list-news.vue"
	export default {
		components: {
			listNews
		},
		data() {
			return {
				lists: [{
					name:"捐赠桌椅",
					goods_thumb:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603106747111&di=c70b4adee0bef68057ea07caca505d5f&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%3D450%2C600%2Fsign%3De5752296cb95d143da23ec2746c0ae34%2Fdc54564e9258d109140c6727d258ccbf6d814dbc.jpg",
					goods_tip:"捐赠500元",
					tag:[],
					rank:"副教授",
					comment_count:20
				}], // 列表数据
				waterfall: false, // 布局方向切换
				status: 'more', // 加载状态
				tipShow: false, // 是否显示顶部提示框
				pageSize: 20, // 每页显示的数据条数
				current: 1 // 当前页数	
			};
		},
		onLoad(options) {
			// 初始化页面数据
			this.title=options.title;
			// getUserInfo1().then(data=>{
			// 	var [error, res]  = data;
			// })
		},
		methods: {
			/**
			 * 切换商品列表布局方向
			 */
			select() {
				this.waterfall = !this.waterfall;
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
				// this.getNewsList();
			},
			/**
			 * 获取页面数据
			 * @param {Object} reload 参数reload值为true时执行列表初始化逻辑，值为false时执行追加下一页数据的逻辑。默认为false
			 */
			getNewsList(reload) {
				this.status = 'loading'
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

	.shop {
		flex: 1;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.shop-picture {
		width: 110px;
		height: 110px;
	}

	.shop-picture-column {
		width: 100%;
		height: 170px;
		margin-bottom: 10px;
	}

	.shop-price {
		margin-top: 5px;
		font-size: 12px;
		color: #ff5a5f;
	}

	.shop-price-text {
		font-size: 16px;
	}

	.hot-tag {
		background: #ff5a5f;
		border: none;
		color: #fff;
	}

	.button-box {
		height: 30px;
		line-height: 30px;
		font-size: 12px;
		background: #007AFF;
		color: #fff;
	}

	.uni-link {
		flex-shrink: 0;
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


	// 默认加入 scoped ，所以外面加一层提升权重
	.list {
		.uni-list--waterfall {

			/* #ifndef H5 || APP-VUE */
			// 小程序 编译后会多一层标签，而其他平台没有，所以需要特殊处理一下
			/deep/ .uni-list {
				/* #endif */
				display: flex;
				flex-direction: row;
				flex-wrap: wrap;
				padding: 5px;
				box-sizing: border-box;

				/* #ifdef H5 || APP-VUE */
				// h5 和 app-vue 使用深度选择器，因为默认使用了 scoped ，所以样式会无法穿透
				/deep/
				/* #endif */
				.uni-list-item--waterfall {
					width: 50%;
					box-sizing: border-box;

					.uni-list-item__container {
						padding: 5px;
						flex-direction: column;
					}
				}

				/* #ifndef H5 || APP-VUE */
			}

			/* #endif */
		}
	}
</style>
