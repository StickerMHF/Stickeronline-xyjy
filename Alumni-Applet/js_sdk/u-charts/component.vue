<template>
	<canvas v-if="canvasId" :id="canvasId" :canvasId="canvasId" :style="{'width':'100%','height':cHeight*pixelRatio+'px'}"
	 @touchstart="touchStart" @touchmove="touchMove" @touchend="touchEnd" @error="error">
	</canvas>
</template>

<script>
	import uCharts from './u-charts.js';
	var canvases = {};

	export default {
		props: {
			chartType: {
				required: true,
				type: String,
				default: 'map'
			},
			opts: {
				required: true,
				type: Object,
				default () {
					return {series:[]};
				}
			},
			canvasId: {
				type: String,
				default: 'u-canvas',
			},
			cWidth: {
				default: uni.upx2px(750),
			},
			cHeight: {
				default: uni.upx2px(750),
			},
			pixelRatio: {
				type: Number,
				default: 2,
			},
		},
		data() {
			return {
				
			}
		},
		mounted() {
			this.init();
		},
		methods: {
			init() {
				switch (this.chartType) {
					case 'column':
						this.initColumnChart();
						break;
					case 'line':
						this.initLineChart();
						break;
					case 'map':
						this.initMapChart();
						break;
					default:
						break;
				}
			},
			initMapChart() {
				canvases[this.canvasId] = new uCharts({
					$this: this,
					canvasId: this.canvasId,
					type: 'map',
					fontSize: 11,
					padding: [0, 0, 0, 0],
					legend: {
						show: false
					},
					background: '#FFFFFF',
					pixelRatio: this.pixelRatio,
					series: this.opts.series,
					dataLabel: true,
					extra: {
						map: {
							border: true,
							borderWidth: 1,
							borderColor: '#666666',
							fillOpacity: 0.6,
						}
					}
				});
			},
			initColumnChart() {
				canvases[this.canvasId] = new uCharts({
					$this: this,
					canvasId: this.canvasId,
					type: 'column',
					legend: true,
					fontSize: 11,
					background: '#FFFFFF',
					pixelRatio: this.pixelRatio,
					animation: true,
					// categories: this.opts.categories,
					series: this.opts.series,
					enableScroll: true,
					xAxis: {
						disableGrid: true,
						itemCount: 4,
						scrollShow: true
					},
					yAxis: {
						//disabled:true
					},
					dataLabel: true,
					width: this.cWidth * this.pixelRatio,
					height: this.cHeight * this.pixelRatio,
					extra: {
						column: {
							type: 'group',
						}
					}
				});
			},
			initLineChart() {
				canvases[this.canvasId] = new uCharts({
					$this: this,
					canvasId: this.canvasId,
					type: 'line',
					fontSize: 11,
					legend: true,
					dataLabel: false,
					dataPointShape: true,
					background: '#FFFFFF',
					pixelRatio: this.pixelRatio,
					// categories: this.opts.categories,
					series: this.opts.series,
					animation: true,
					enableScroll: true,
					xAxis: {
						type: 'grid',
						gridColor: '#CCCCCC',
						gridType: 'dash',
						dashLength: 8,
						itemCount: 4,
						scrollShow: true
					},
					yAxis: {
						gridType: 'dash',
						gridColor: '#CCCCCC',
						dashLength: 8,
						splitNumber: 5,
						min: 10,
						max: 180,
						format: (val) => {
							return val.toFixed(0) + '元'
						}
					},
					width: this.cWidth * this.pixelRatio,
					height: this.cHeight * this.pixelRatio,
					extra: {
						line: {
							type: 'straight'
						}
					}
				});
			},
			// 这里仅作为示例传入两个参数，cid为canvas-id,newdata为更新的数据，需要更多参数请自行修改
			changeData(cid, newdata) {
				canvases[cid].updateData({
					series: newdata.series,
					categories: newdata.categories
				});
			},
			touchStart(e) {
				canvases[this.canvasId].showToolTip(e, {
					format: function(item, category) {
						return category + ' ' + item.name + ':' + item.data
					}
				});
				canvases[this.canvasId].scrollStart(e);
			},
			touchMove(e) {
				canvases[this.canvasId].scroll(e);
			},
			touchEnd(e) {
				canvases[this.canvasId].scrollEnd(e);
			},
			error(e) {
				console.log(e)
			}
		},
	};
</script>

<style scoped>
	.charts {
		width: 100%;
		height: 100%;
		flex: 1;
		background-color: #FFFFFF;
	}
</style>
