<template>
	<view class="list">
		<cu-custom bgColor="bg-gradual-green1" :isBack="false">
			<block slot="content">校友</block>
		</cu-custom>
		<view>
			<scroll-view scroll-x class="bg-white nav text-center" scroll-with-animation>
				<view class="cu-item" :class="item.id == tabCur ? 'text-green cur' : ''" v-for="item in tabList" :key="item.id"
				 @tap="tabSelect" :data-id="item.id">{{ item.name }}</view>
			</scroll-view>
		</view>
		<view v-if="tabCur == 5">
			<!-- <statistics></statistics> -->
			<view class="statisticsView">
				<view class="flex-box">
					<view class="alumnusNums">
						<!-- <text class="cuIcon-people myIcon"></text> -->
						<view class="numsBox">
							<text class="peopleNumsText">{{ peopleNums }}</text>
							<text>校友总数/人</text>
						</view>
					</view>
					<view class="alumnusNums groupNums">
						<!-- <text class="cuIcon-taoxiaopu myIcon"></text> -->
						<view class="numsBox">
							<text class="groupNumsText">{{ groupNums }}</text>
							<text>校友会/个</text>
						</view>
					</view>
				</view>
				<view class="qiun-columns">
					<view class="qiun-bg-white qiun-title-bar qiun-common-mt">
						<view class="qiun-title-dot-light"><text class="cuIcon-titles text-green1"></text>各级校友统计</view>
					</view>
					<view class="qiun-charts">
						<canvas canvas-id="canvasColumn" id="canvasColumn" class="charts" @touchstart="touchColumn"></canvas>
					</view>
				</view>
				<alumnusDistribution></alumnusDistribution>
				<view class="qiun-columns">
					<view class="qiun-bg-white qiun-title-bar qiun-common-mt">
						<view class="qiun-title-dot-light"><text class="cuIcon-titles text-green1"></text>就业率统计</view>
					</view>
					<view class="qiun-charts">
						<canvas canvas-id="canvasTrack" id="canvasTrack" class="charts" @touchstart="touchTrack"></canvas>
					</view>
				</view>
				<view class="qiun-columns">
					<view class="qiun-bg-white qiun-title-bar qiun-common-mt">
						<view class="qiun-title-dot-light"><text class="cuIcon-titles text-green1"></text>就业人数统计</view>
					</view>
					<view class="qiun-charts">
						<canvas canvas-id="canvasEmployment" id="canvasEmployment" class="charts" @touchstart="touchEmployment"></canvas>
					</view>
				</view>
			</view>
			<!-- <web-view class="webviewStyles" src="https://www.imapway.cn/Alumni/echarts/test.html"></web-view> -->
		</view>
		<view v-else class="">
			<!-- 刷新页面后的顶部提示框 -->
			<!-- 当前弹出内容没有实际逻辑 ，可根据当前业务修改弹出提示 -->
			<view class="tips" :class="{ 'tips-ani': tipShow }">为您更新了10条最新新闻动态</view>
			<scroll-view class="main_box" scroll-y="true"  @scrolltolower="lower">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list :class="{ 'uni-list--waterfall': waterfall }">
				<!-- 通过 uni-list--waterfall 类决定页面布局方向 -->
				<uni-list-item :border="!waterfall" class="uni-list-item--waterfall" title="自定义商品列表" v-for="(item, i) in lists"
				 :key="i">
					<!-- 通过header插槽定义列表左侧图片 -->
					<template v-slot:header>
						<view class="uni-thumb shop-picture" :class="{ 'shop-picture-column': waterfall }">
							<image :src="item.thumb?item.thumb:'http://cdxyh.stickeronline.cn/organizations%402x.png'"></image>
						</view>
					</template>
					<!-- 通过body插槽定义商品布局 -->
					<view slot="body" class="shop">
						<view class="view-left">
							<navigator :url="'/pages/alumnus/details?id=' + item.id + '&name=' + item.name">
								<view class="view-left">
									<view class="uni-title">
										<view class="uni-ellipsis-2">{{ item.name }}</view>
									</view>
									<view>
										<view class="cu-capsule radius" v-if="item.activity">
											<view class="cu-tag bg-blue"> 活动 </view>
											<view class="cu-tag line-blue">
												{{ item.activity }}
											</view>
										</view>
										<view class="cu-capsule radius">
											<view class="cu-tag bg-gradual-green1"> 成员 </view>
											<view class="cu-tag line-green">
												{{ item.member }}
											</view>
										</view>
									</view>
									<view class="starBox" v-if="item.liveness">
										<text class="liveness">活跃度</text>
										<uniRate :readonly="true" :size="14" :value="item.liveness"></uniRate>
									</view>
								</view>
							</navigator>
							<view class="alumunus-button">
								<button class="alumnus-btn cu-btn round sm bg-orange" v-if="item.join == true" @click="deleteAlumnusJoin(item)">
									已加入
								</button>
								<button class="alumnus-btn cu-btn round sm bg-orange" v-else @click="addJoin(item)">
									加入
								</button>
							</view>
						</view>
					</view>
				</uni-list-item>
			</uni-list>
			<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
			<uni-load-more v-if="lists.length > 0" :status="status" />
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import {
		getAlumnusList,
		getClassGrade,
		addAlumnusJoin,
		delAlumnusJoin
	} from "@/api/alumnus.js";
	import uCharts from "@/js_sdk/u-charts/u-charts.js";
	import alumnusDistribution from "@/pages/alumnus/alumnusDistribution.vue";
	import uniRate from '@/components/uni-rate/uni-rate.vue';
	var _self;
	var canvaColumn = null;
	var canvaTrack = null;
	var canvaEmployment = null;
	export default{
		components: {
			alumnusDistribution,
			uniRate
		},
		data() {
			return{
				cWidth: "",
				cHeight: "",
				pixelRatio: 1,
				serverData: "",
				peopleNums: 0,
				groupNums: 21,
				tabCur: 2,
				current: 0,
				tabList: [
					// {
					// 	id: "all",
					// 	name: "全部",
					// },
					{
					  id: 2,
					  name: "各省校友",
					},					
					{
						id: 4,
						name: "行业校友",
					},
					{
						id: 3,
						name: "全部班级",
					},
				// 	, {
				// 	id: 5,
				// 	name: '校友统计',
				// 	uri: '/pages/alumnus/statistics'
				// }
				],
				
				lists: [{
					name: "校友总会",
					thumb: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603106747111&di=c70b4adee0bef68057ea07caca505d5f&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%3D450%2C600%2Fsign%3De5752296cb95d143da23ec2746c0ae34%2Fdc54564e9258d109140c6727d258ccbf6d814dbc.jpg",
					member: 30,
					activity: 2,
				}, ], // 列表数据
				waterfall: false, // 布局方向切换
				status: "more", // 加载状态
				tipShow: false, // 是否显示顶部提示框
				totalPages: null, //总页数
				params: {
					pageNo: 1, // 当前页数
					pageSize: 10, // 每页显示的数据条数
					type: "all",
					userId: "",
				},
			}
		},
		methods: {
			ontrueGetList(){
				this.lists = [];
				this.params.userId = uni.getStorageSync("openid");
				this.tabCur = 2;
				this.params.type = 2;
				this.getAlumnusList(this.params);
				
				// _self = this;
				// this.cWidth = uni.upx2px(750);
				// this.cHeight = uni.upx2px(500);
				// this.getServerData();
			},
			lower(){
				this.getNewsList();
			},
			getAlumnusList(params) {
				getAlumnusList(params).then(data => {
					var [error, res] = data;
					if (res && res.data && res.data.result) {
						this.lists = this.lists.concat(res.data.result.content);
						let pageable = res.data.result.pageable;
						this.params.pageNo = pageable.pageNumber + 1;
						this.totalPages = res.data.result.totalPages;
						if (this.totalPages > this.params.pageNo) {
							this.status = "more";
						} else {
							this.status = "noMore";
						}
					}
				});
			},
			tabSelect(e) {
				this.tabCur = e.currentTarget.dataset.id;
				this.params.type = e.currentTarget.dataset.id;
				this.params.pageNo = 1;
				this.lists = [];
				//判断数据类型
				if(this.tabCur == 2 || this.tabCur == 4){
					this.getAlumnusList(this.params);
				} else {
					this.getClassGradeList(this.params);
				}
				
			
				if (e.currentTarget.dataset.id == 5) {
					_self = this;
					this.cWidth = uni.upx2px(750);
					this.cHeight = uni.upx2px(500);
					this.getServerData();
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
				// console.log("上拉刷新");
				this.getNewsList();
			},
			addJoin(alumnu) {
				let that=this;
				that.isCertification=uni.getStorageSync('isCertification');
				if(that.isCertification==true){
					var params = {
						alumnusId: alumnu.id,
						userId: "",
						userName: "",
						userPhoto: "",
						status: "1",
					};
					let join = alumnu.join;
					if (!join) {}
					//获取用户信息
					params.userId = uni.getStorageSync("openid");
					let userInfo = uni.getStorageSync("userInfo");
					if (userInfo) {
						params.userName = userInfo.nickName;
						params.userPhoto = userInfo.avatarUrl;
						addAlumnusJoin(params).then(data => {
							console.log(data);
							let [error, res] = data
							if(res&&res.data&&res.data.success){
								alumnu.join = true;
								alumnu.member=alumnu.member+1;
								uni.showToast({
								    title: '加入成功',
								    duration: 2000
								});
							} else{
								uni.showToast({
								    title: '服务器忙，请稍后',
								    duration: 2000
								});
							}							
						});
					} else {
						//跳转页面
						wx.navigateTo({
							url: "/pages/login/login",
						});
					}
				}else{
					let auditStatus = uni.getStorageSync('auditStatus');
					let isCertification = uni.getStorageSync('isCertification');
					if(isCertification&&auditStatus != null){
						  uni.navigateTo({
							url: '/pages/personal/basicInfo/basicInfo'
						  });
					} else {
						  uni.navigateTo({
							url: '/pages/personal/basicInfo/certification'
						  });
					}					
					// getApp().getUserInfo();
					// uni.showToast({
					//     title: '请先进行校友认证',
					//     duration: 2000
					// });					
				}				
			},
			//取消关注
			deleteAlumnusJoin(alumnu){
				let formdata = {
					alumnusId: alumnu.id,
					userId: ""
				}
				formdata.userId = uni.getStorageSync("openid");
				delAlumnusJoin(formdata).then(data =>{
					console.log(data);
					let [error,res] = data;
					if(res&&res.data&&res.data.success){
						alumnu.join = false;
						alumnu.member=alumnu.member-1;						
						uni.showToast({
						    title: '退出成功',
						    duration: 2000
						});
					} else{
						uni.showToast({
						    title: '服务器忙，请稍后再试！',
						    duration: 2000
						});
					}
					
				});
			},
			/**
			 * 获取页面数据
			 * @param {Object} reload 参数reload值为true时执行列表初始化逻辑，值为false时执行追加下一页数据的逻辑。默认为false
			 */
			getNewsList(reload) {
				if (this.totalPages > this.params.pageNo) {
					this.status = "loading";
					this.params.pageNo += 1;
					if(this.params.type == 2 || this.params.type == 4){						
						this.getAlumnusList(this.params);
					} else {
						this.getClassGradeList(this.params);
					}
				} else {
					this.status = "noMore";
				}
			},
			getServerData() {
				// _self.$refs.alumnusDistribution.getData();
				uni.request({
					//往期校友统计
					url: "https://www.imapway.cn/Alumni/alumniStatistics.json",
					data: {},
					success: function(res) {
						let Column = res.data.data;
						_self.showColumn("canvasColumn", Column);
					},
					fail: () => {
						_self.tips = "网络错误，小程序端请检查合法域名";
					},
				});
				uni.request({
					//毕业率统计
					url: "https://www.imapway.cn/Alumni/employmentMode.json",
					data: {},
					success: function(res) {
						let pieColumn = res.data.data;
						_self.showTrack("canvasTrack", pieColumn);
					},
					fail: () => {
						_self.tips = "网络错误，小程序端请检查合法域名";
					},
				});
				uni.request({
					//就业人数统计
					url: "https://www.imapway.cn/Alumni/employmentRate.json",
					data: {},
					success: function(res) {
						let employmentColumn = res.data.data;
						_self.showEmployment("canvasEmployment", employmentColumn);
					},
					fail: () => {
						_self.tips = "网络错误，小程序端请检查合法域名";
					},
				});
			},
			showColumn(canvasId, chartData) {
				canvaColumn = new uCharts({
					$this: _self,
					canvasId: canvasId,
					colors: ['#aa00ff'],
					type: "column",
					legend: {
						show: false
					},
					fontSize: 11,
					background: "#FFFFFF",
					pixelRatio: _self.pixelRatio,
					animation: true,
					categories: chartData.categories,
					series: chartData.series,
					xAxis: {
						disableGrid: true,
					},
					yAxis: {
						disabled: true,
						disableGrid: true,
						// data: {
						//   calibration: false,
						//   axisLine: true,
						// },
					},
					dataLabel: true,
					width: _self.cWidth * _self.pixelRatio,
					height: _self.cHeight * _self.pixelRatio,
					extra: {
						column: {
							type: "group",
							// width:
							//   (_self.cWidth * _self.pixelRatio * 0.45) /
							//   chartData.categories.length,
							width: 20,
						},
					},
				});
			},
			showTrack(canvasId, chartData) {
				canvaTrack = new uCharts({
					$this: _self,
					canvasId: canvasId,
					type: "pie",
					fontSize: 11,
					legend: {
						show: false
					},
					background: "#FFFFFF",
					pixelRatio: _self.pixelRatio,
					series: chartData.series,
					animation: true,
					width: _self.cWidth * _self.pixelRatio,
					height: _self.cHeight * _self.pixelRatio,
					dataLabel: true,
					extra: {
						pie: {
							lableWidth: 15,
						},
					},
				});
			},
			showEmployment(canvasId, chartData) {
				canvaEmployment = new uCharts({
					$this: _self,
					canvasId: canvasId,
					type: "column",
					legend: {
						show: false
					},
					fontSize: 11,
					background: "#FFFFFF",
					pixelRatio: _self.pixelRatio,
					animation: true,
					categories: chartData.categories,
					series: chartData.series,
					xAxis: {
						disableGrid: true,
					},
					yAxis: {
						data: {
							calibration: false,
							axisLine: true,
						},
					},
					dataLabel: true,
					width: _self.cWidth * _self.pixelRatio,
					height: _self.cHeight * _self.pixelRatio,
					extra: {
						column: {
							type: "group",
							width: 20,
						},
					},
				});
			},
			touchColumn(e) {
				canvaColumn.showToolTip(e, {
					format: function(item, category) {
						if (typeof item.data === "object") {
							return category + " " + item.name + ":" + item.data.value;
						} else {
							return category + " " + item.name + ":" + item.data;
						}
					},
				});
			},
			touchTrack(e) {
				canvaTrack.showToolTip(e, {
					format: function(item) {
						return item.name + ":" + item.data;
					},
				});
			},
			touchEmployment(e) {
				canvaEmployment.showToolTip(e, {
					format: function(item) {
						return item.name + ":" + item.data;
					},
				});
			},
			getClassGradeList(params){
				getClassGrade(params).then(data =>{
					var [error, res] = data;
					if (res && res.data && res.data.result) {
						this.lists = this.lists.concat(res.data.result.content);
						let pageable = res.data.result.pageable;
						this.params.pageNo = pageable.pageNumber + 1;
						this.totalPages = res.data.result.totalPages;
						if (this.totalPages > this.params.pageNo) {
							this.status = "more";
						} else {
							this.status = "noMore";
						}
					}
				});
			}
		}
	}
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
	.main_box{width:100vw;height: 100vh;padding-bottom:120rpx;box-sizing: border-box;}
	.main_centent{width: 100%;height: 100vh;color: #fff;letter-spacing: 4rpx;display: flex;align-items: center;justify-content: center;}
</style>