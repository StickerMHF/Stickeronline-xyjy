<template>
	<view class="qiun-columns">
	  <view class="qiun-bg-white qiun-title-bar qiun-common-mt">
	    <view class="qiun-title-dot-light">
			<text class="cuIcon-titles text-green1"></text>校友分布</view>
	  </view>
	  <view class="qiun-charts">
	    <canvas
	      canvas-id="canvasDistribution"
	      id="canvasDistribution"
	      class="charts"
	      @touchstart="touchDistribution"
	    ></canvas>
	  </view>
	</view>
</template>

<script>
import uCharts from "../../js_sdk/u-charts/u-charts.js";
let _selfs;
let canvaDistribution;
export default {
	components: {},
	data() {
		return {
			cWidth: 0,
			cHeight: 0,
			pixelRatio: 1,
		};
	},
	mounted() {
		// _selfs = this;
		this.getData();
	},
	methods: {
		getData(){
			_selfs = this;
			this.cWidth = uni.upx2px(750);
			this.cHeight = uni.upx2px(500);
			uni.request({
			   //就业地图分布
			   url: "https://www.imapway.cn/Alumni/chinaArea.json",
			   success: function (res) {
					uni.request({
						//就业地图分布
						url: "https://www.imapway.cn/Alumni/mapDistribution.json",
						success: function (mapDistribution) {
							let datas = mapDistribution.data.data.series;
							let provinces = res.data.features;
							let mapData = provinces.map(province => {
							for (var i = 0; i < datas.length; i++) {
								if (datas[i].name === province.properties.name) {
									if (datas[i].data >= 100) {
										province.color = "#ff0000";
									} else if (datas[i].data >= 50) {
										province.color = "#ce8900";
									} else if (datas[i].data >= 20) {
										province.color = "#ffe26b";
									} else if (datas[i].data >= 0) {
										province.color = "#bdd9d8";
									} else {
										province.color = "#d3d9d9";
									}
									return { ...province, ...datas[i] };
								}
							}
						   return province;
						 });
						let mapColumn = {
							series: mapData,
						};
							_selfs.showDistribution("canvasDistribution", mapColumn);
						},
					});
				},
				fail: () => {
					_selfs.tips = "网络错误，小程序端请检查合法域名";
				},
			 });
		},
		showDistribution(canvasId, chartData) {
		  canvaDistribution = new uCharts({
		    $this: _selfs,
		    canvasId: canvasId,
		    type: "map",
		    fontSize: 11,
		    padding: [0, 0, 0, 0],
		    legend: {
		      show: false,
		    },
		    background: "#FFFFFF",
		    pixelRatio: _selfs.pixelRatio,
		    series: chartData.series,
		    width: _selfs.cWidth * _selfs.pixelRatio,
		    height: _selfs.cHeight * _selfs.pixelRatio,
		    extra: {
		      map: {
		        border: true,
		        borderWidth: 1,
		        borderColor: "#666666",
		        fillOpacity: 0.6,
		      },
		    },
		  });
		},
		touchDistribution(e) {
		  canvaDistribution.showToolTip(e, {
		    format: function (item) {
		      return `${item.properties.name}: ${item.data}`;
		    },
		  });
		},
   }
};
</script>

<style lang="scss" scoped>
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
  width: 100%;
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
</style>
