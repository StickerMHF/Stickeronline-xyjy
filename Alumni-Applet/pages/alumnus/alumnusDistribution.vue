<template>
  <view class="qiun-columns">
    <view class="qiun-bg-white qiun-title-bar qiun-common-mt">
      <view class="qiun-title-dot-light">
        <!-- cuIcon-titles text-green1 -->
        <text
          class="xyfb_icon"
          style="
            background: url(https://www.imapway.cn/Alumni/static/home/xyfb.png);
            background-size: 100%;
          "
        ></text
        >2020毕业校友分布</view
      >
    </view>
    <view class="qiun-charts">
      <canvas
        canvas-id="canvasDistribution"
        id="canvasDistribution"
        class="charts"
        @touchstart="touchDistribution"
      ></canvas>
	 <!-- <view class="xyfb_legend">
	  	dd
	  </view> -->
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
  created() {},
  mounted() {
    // _selfs = this;
    this.getData();
  },
  methods: {
    getData() {
      _selfs = this;
      this.cWidth = uni.upx2px(700);
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
                      province.color = "#F45937";
                    } else if (datas[i].data >= 50) {
                      province.color = "#F4871E";
                    } else if (datas[i].data >= 20) {
                      province.color = "#FFBA08";
                    } else {
                      province.color = "#3FC1C0";
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
          show: true,
        },
        background: "#f2fbff",
        pixelRatio: _selfs.pixelRatio,
        series: chartData.series,
        width: _selfs.cWidth * _selfs.pixelRatio,
        height: _selfs.cHeight * 1.5,
        extra: {
          map: {
            border: true,
            borderWidth: 0.1,
            borderColor: "#ffffff",
            fillOpacity: 1,
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
  },
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
  width: 730rpx;
  // height: 600rpx;
  background-color: #ffffff;
  padding: 10upx;
  overflow: hidden;
  z-index: 9;
}
.charts {
  width: 730upx;
  height: 480upx;
  background-color: #ffffff;
  z-index: 9;
}
.xyfb_icon {
  width: 15px;
  height: 15px;
  display: inline-block;
  background-size: 100% 100%;
  margin-right: 5px;
}
.xyfb_legend{
	position: absolute;
}
</style>
