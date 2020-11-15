<template>
	<view class="list">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="content">审核</block>
		</cu-custom>
		<view>
			<scroll-view scroll-x class="bg-white nav text-center" scroll-with-animation>
				<view class="cu-item" :class="item.id == tabCur ? 'text-green cur' : ''" v-for="item in tabList" :key="item.id"
				 @tap="tabSelect" :data-id="item.id">{{ item.name }}</view>
			</scroll-view>
		</view>
		<view>
			<view v-if="tabCur==1">
				<uni-list :class="{ 'uni-list--waterfall': waterfall }">
					<!-- 通过 uni-list--waterfall 类决定页面布局方向 -->
					<uni-list-item :border="!waterfall" class="uni-list-item--waterfall" title="自定义商品列表" v-for="item in lists" :key="item._id">
						<!-- 通过header插槽定义列表左侧图片 -->
						<template v-slot:header>
							<view class="uni-thumb shop-picture" :class="{ 'shop-picture-column': waterfall }">
								<image :src="item.user_phopt"></image>
							</view>
						</template>
						<!-- 通过body插槽定义商品布局 -->
						<view slot="body" class="shop">
							<view>
								<view class="uni-title">
									<text class="uni-ellipsis-2">{{ item.user_name }}</text>
								</view>
								<view class="alumunus-button">
									<button class="alumnus-btn cu-btn round sm bg-orange" @click="agreeBtn({item})">
										同意
									</button>
									<button class="alumnus-btn cu-btn round sm bg-red" @click="refuseBtn({item})">
										拒绝
									</button>
								</view>
							</view>
						</view>
					</uni-list-item>
				</uni-list>
			</view>
			<view v-else>
				<uni-list :class="{ 'uni-list--waterfall': waterfall }">
					<!-- 通过 uni-list--waterfall 类决定页面布局方向 -->
					<uni-list-item :border="!waterfall" class="uni-list-item--waterfall" title="自定义商品列表" v-for="item in lists" :key="item._id">
						<!-- 通过header插槽定义列表左侧图片 -->
						<template v-slot:header>
							<view class="uni-thumb shop-picture" :class="{ 'shop-picture-column': waterfall }">
								<image :src="item.user_phopt"></image>
							</view>
						</template>
						<!-- 通过body插槽定义商品布局 -->
						<view slot="body" class="shop">
							<view>
								<view class="uni-title">
									<text class="uni-ellipsis-2">{{ item.user_name }}</text>
								</view>
								<view class="alumunus-button">
									<button class="alumnus-btn cu-btn round sm bg-orange" @click="alBtn({item})">
										同意
									</button>
									<button class="alumnus-btn cu-btn round sm bg-red" @click="reBtn({item})">
										拒绝
									</button>
								</view>
							</view>
						</view>
					</uni-list-item>
				</uni-list>
			</view>
			<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
			<!-- <uni-load-more v-if="lists.length > 0" :status="status" /> -->
		</view>
	</view>
</template>

<script>
	import uCharts from "../../../js_sdk/u-charts/u-charts.js";
	import {
		getExamineList,
		getExamineStatus,
		getPresidentList,
		getPresidentStatus,
	} from "../../../api/cooperation.js"
	// import alumnusDistribution from "../../alumnus/alumnusDistribution.vue";
	import uniRate from '@/components/uni-rate/uni-rate.vue';
	var _self;
	var canvaColumn = null;
	var canvaTrack = null;
	var canvaEmployment = null;

	export default {
		data() {
			return {
				cWidth: "",
				cHeight: "",
				pixelRatio: 1,
				serverData: "",
				peopleNums: 0,
				groupNums: 21,
				tabCur: "all",
				current: 0,
				tabList: [{
						id: 1,
						name: "校友认证",
					},
					{
						id: 2,
						name: "会长认证",
					},
				],

				lists: [], // 列表数据
				waterfall: false, // 布局方向切换
				status: "more", // 加载状态
				tipShow: false, // 是否显示顶部提示框
				// pageSize: 10, // 每页显示的数据条数
				// current: 1, // 当前页数
				totalPages: null, //总页数
				params: {
					pageNo: 1, // 当前页数
					pageSize: 10, // 每页显示的数据条数
					type: "all",
					userId: "",
				},
			};
		},
		onLoad(options) {
			// 初始化页面数据
			this.params.userId = uni.getStorageSync("openid");
			this.getExamineList()
		},
		onHide() {
			// this.tabCur = 1;
		},
		onShow() {
			// this.tabCur = "all";
			_self = this;
			this.cWidth = uni.upx2px(750);
			this.cHeight = uni.upx2px(500);
		},
		methods: {
			//校友
			getExamineList() {
				getExamineList().then(data => {
					this.lists = []
					let res = data[1].data.result
					res.content.forEach(item => {
						let obj = {
							user_name: item.nickName,
							user_phopt: item.avatarUrl,
							openid:item.openid,
							auditStatus:item.auditStatus,
							
						}
						this.lists.push(obj)
					})
				})
			},
			// 会长
			getPresidentList(){
				getPresidentList().then(data=>{
					this.lists = []
					let res = data[1].data.result
					console.log(8888,res)
					res.content.forEach(item => {
						let opt = {
							user_name: item.userName,
							user_phopt: item.userPhoto,
							id:item.id,
							checkState:item.checkState
						}
						this.lists.push(opt)
					})
				})
			},
			agreeBtn(item){
				let params={
					openid:item.item.openid,
					auditStatus:1
				}
				console.log(9999,params)
				getExamineStatus(params).then(res=>{
					this.getExamineList()
				})
			},
			refuseBtn(item){
				let params={
					id:item.item.openid,
					auditStatus:-1
				}
				getExamineStatus(params).then(res=>{
					this.getExamineList()
				})
			}, 
			//会长
			alBtn(item){
				let params={
					id:item.item.id,
					checkState:"2"
				}
				getPresidentStatus(params).then(res=>{
					this.getPresidentList()
				})
			},
			reBtn(item){
				let params={
					id:item.item.id,
					checkState:"-1"
				}
				getPresidentStatus(params).then(res=>{
					this.getPresidentList()
				})
			},
			
			tabSelect(e) {
				this.tabCur = e.currentTarget.dataset.id;
				this.params.type = e.currentTarget.dataset.id;
				this.params.pageNo = 1;
				// this.lists = [];
				//判断数据类型
				if (this.tabCur == 1) {
					this.getExamineList()
				} else {
					this.getPresidentList()
				}
				if (e.currentTarget.dataset.id == 5) {
					_self = this;
					this.cWidth = uni.upx2px(750);
					this.cHeight = uni.upx2px(500);
				}
			},
			/**
			 * 切换商品列表布局方向
			 */
			select() {
				this.waterfall = !this.waterfall;
			},
			/**
			 * 上拉加载回调函数
			 */
			onReachBottom() {
				console.log("上拉刷新");
				// this.getNewsList();
			},
		},
	};
</script>

<style lang="scss">
	@import "@/common/uni-ui.scss";

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
		width: 50px;
		height: 50px;
		margin-top: 20rpx;
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
		background: #007aff;
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
		// overflow: hidden;
		// text-overflow: ellipsis;
		// display: -webkit-box;
		// -webkit-line-clamp: 2;
		// -webkit-box-orient: vertical;
		font-size: 32rpx;
	}

	.starBox {
		display: flex;
		align-items: center;

		.liveness {
			margin-right: 10rpx;
			color: #888888;
			font-size: 14px;
		}
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
					display: flex;
					align-items: center;

					.uni-list-item__container {
						padding: 5px;
						flex-direction: column;
						align-items: center;
					}
				}

				/* #ifndef H5 || APP-VUE */
			}

			/* #endif */

		}
	}

	/deep/ .uni-title {
		padding: 0;
		margin: 0;
	}

	.nav .cu-item {
		height: 45px;
		display: inline-block;
		line-height: 45px;
		margin: 0 5px;
		padding: 0 5px;
	}

	.alumunus-button {
		position: absolute;
		right: 10px;
		top: 40px;

		.alumnus-btn {
			width: 140rpx;
			height: 60rpx;
		}
	}


	.star-pos {
		margin: 10rpx;
		display: flex;
		flex-direction: row;
	}

	.stars {
		width: 40rpx;
		height: 40rpx;
		margin-left: 30rpx;
	}

	.webviewStyles {
		position: absolute;
		left: 0xp;
		right: 0px;
		bottom: 0px;
		top: 90px;
	}

	.statisticsView {
		background: #fff;
	}

	.flex-box {
		display: flex;
		justify-content: space-around;
		padding: 20rpx 0;
	}

	.alumnusNums {
		width: 200rpx;
		height: 200rpx;
		// border: 5px solid #d83838;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
		padding: 5px;
		border-radius: 50%;
		background-image: -webkit-linear-gradient(left, #00aaff 40%, #5500ff 100%);
		background-image: -moz-linear-gradient(left, #00aaff 40%, #5500ff 100%);
		background-image: linear-gradient(left, #00aaff 40%, #5500ff 100%);

		.myIcon {
			font-size: 30px;
		}
	}

	.groupNums {
		background-image: -webkit-linear-gradient(left, #ff0000 30%, #aa007f 100%);
		background-image: -moz-linear-gradient(left, #ff0000 30%, #aa007f 100%);
		background-image: linear-gradient(left, #ff0000 30%, #aa007f 100%);
	}

	.numsBox {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 100%;
		height: 100%;
		background: #f2f2f2;
		border-radius: 50%;
		justify-content: center;
	}

	.peopleNumsText {
		font-size: 36rpx;
		color: #00ffff;
	}

	.groupNumsText {
		font-size: 36rpx;
		color: #ff0000;
	}

	page {
		background: #f2f2f2;
		width: 750upx;
		overflow-x: hidden;
	}

	.qiun-padding {
		padding: 2%;
		width: 96%;
	}

	.qiun-wrap {
		display: flex;
		flex-wrap: wrap;
	}

	.qiun-rows {
		display: flex;
		flex-direction: row !important;
	}

	.qiun-columns {
		display: flex;
		flex-direction: column !important;
	}

	.qiun-common-mt {
		margin-top: 10upx;
	}

	.qiun-bg-white {
		background: #ffffff;
	}

	.qiun-title-bar {
		width: 96%;
		padding: 10upx 2%;
		flex-wrap: nowrap;
	}

	.qiun-title-dot-light {
		padding-left: 10upx;
		font-size: 32upx;
		color: #000000;
	}

	.qiun-charts {
		width: 750upx;
		height: 500upx;
		background-color: #ffffff;
	}

	.charts {
		width: 750upx;
		height: 500upx;
		background-color: #ffffff;
	}

	.view-left {
		width: 200px;
	}

	.cu-tag {
		font-size: 22rpx;
		height: 45rpx;
	}
	.btnSty{
		width: 100rpx;
	}
	.uni-title text{
		line-height: 5;
	}
</style>
