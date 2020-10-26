<template>
	<view class="qiun-columns">
		<view class="qiun-charts">
			<!--#ifdef MP-ALIPAY -->
			<canvas canvas-id="canvasMap" id="canvasMap" class="charts" :width="cWidth*pixelRatio" :height="cHeight*pixelRatio"
			 :style="{'width':cWidth+'px','height':cHeight+'px'}" @touchstart="touchMap"></canvas>
			<!--#endif-->
			<!--#ifndef MP-ALIPAY -->
			<canvas canvas-id="canvasMap" id="canvasMap" class="charts" @touchstart="touchMap"></canvas>
			<!--#endif-->
		</view>
	</view>
</template>

<script>
	// import uCharts from '@/components/u-charts/u-charts.js';
	import uCharts from '@/js_sdk/u-charts/u-charts.js';
	var _self;
	var canvaMap = null;

	export default {
		data() {
			return {
				cWidth: '',
				cHeight: '',
				pixelRatio: 1,
				textarea: ''
			}
		},
		mounted() {
			_self = this;
			//#ifdef MP-ALIPAY
			uni.getSystemInfo({
				success: function(res) {
					if (res.pixelRatio > 1) {
						//正常这里给2就行，如果pixelRatio=3性能会降低一点
						//_self.pixelRatio =res.pixelRatio;
						_self.pixelRatio = 2;
					}
				}
			});
			//#endif
			this.cWidth = uni.upx2px(700);
			this.cHeight = uni.upx2px(500);
			this.getServerData();
		},
		methods: {
			getServerData() {
				uni.request({
					// url: 'https://www.ucharts.cn/map.json',
					url: 'https://www.imapway.cn/Alumni/provinces_polygon.json', //provinces_polygon、map、china
					data: {},
					success: function(res) {
						console.log(res.data.features)
						let cMap = {
							series: []
						};
						//这里我后台返回的是数组，所以用等于，如果您后台返回的是单条数据，需要push进去
						cMap.series = res.data.features;
						cMap.series.forEach(feature => {
							if (feature.properties.id > 30) {
								feature.color = "#F0E0C3";
							} else {
								feature.color = "#D7F2F7";
							}

						});
						_self.showMap("canvasMap", cMap);
					},
					fail: () => {
						_self.tips = "网络错误，小程序端请检查合法域名";
					},
				});
			},
			showMap(canvasId, chartData) {
				this.canvaMap = new uCharts({
					$this: _self,
					canvasId: canvasId,
					type: 'map',
					fontSize: 11,
					padding: [0, 0, 0, 0],
					legend: {
						show: false
					},
					background: '#FFFFFF',
					pixelRatio: _self.pixelRatio,
					series: chartData.series,
					dataLabel: true,
					width: _self.cWidth * _self.pixelRatio,
					height: _self.cHeight * _self.pixelRatio,
					extra: {
						map: {
							border: true,
							borderWidth: 0.1,
							borderColor: '#666666',
							fillOpacity: 0.6
						}
					}
				});
			},
			touchMap(e) {
				this.canvaMap.showToolTip(e, {
					format: function(item) {
						return item.properties.name
					}
				});
			}
		}
	}
</script>

<style>
	/*样式的width和height一定要与定义的cWidth和cHeight相对应*/
	.qiun-charts {
		width: 700upx;
		height: 500upx;
		background-color: #FFFFFF;
	}

	.charts {
		width: 700upx;
		height: 500upx;
		background-color: #FFFFFF;
	}
</style>
