<template>
  <view class="statisticsView">
    <view class="flex-box">
      <view class="alumnusNums"> 校友总数 </view>
      <view class="alumnusNums"> 校友会 </view>
    </view>
    <view class="qiun-columns">
      <view class="qiun-bg-white qiun-title-bar qiun-common-mt">
        <view class="qiun-title-dot-light">基本柱状图</view>
      </view>
      <view class="qiun-charts">
        <canvas
          canvas-id="canvasColumn"
          id="canvasColumn"
          class="charts"
          @touchstart="touchColumn"
        ></canvas>
      </view>
    </view>
  </view>
</template>

<script>
import uCharts from "../../js_sdk/u-charts/u-charts.js";
var _self;
var canvaColumn = null;
export default {
  components: {},
  data() {
    return {
      cWidth: "",
      cHeight: "",
      pixelRatio: 1,
      serverData: "",
      contentText: {
        contentdown: "显示更多",
        contentrefresh: "正在加载...",
        contentnomore: "没有更多数据了",
      },
      newsList: [],
      activityList: [],
      photoList: [],
      meanu: "intro", //当前菜单
      showNews: true,
      showActivity: true,
      showPhoto: true,
      swiperList: [
        {
          id: 0,
          type: "image",
          url: "http://www.imapway.cn/Alumni/static/home/banner12x.png",
        },
        {
          id: 1,
          type: "image",
          url: "http://www.imapway.cn/Alumni/static/home/banner22x.png",
        },
        {
          id: 2,
          type: "image",
          url: "http://www.imapway.cn/Alumni/static/home/banner32x.png",
        },
      ],
      menus: [
        {
          icon: "http://www.imapway.cn/Alumni/static/home/dcxw2x.png",
          txt: "简介",
          page: "/pages/home/news/news",
          menu: "intro",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/xqzt2x.png",
          txt: "资讯",
          page: "/pages/home/news/news",
          menu: "news",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/xqzt2x.png",
          txt: "成员",
          page: "/pages/home/news/news",
          menu: "member",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/ysjs2x.png",
          txt: "相册",
          page: "/pages/schoolHistory/schoolHistory",
          menu: "photo",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/szll2x.png",
          txt: "活动",
          page: "/pages/teachers/teachers",
          menu: "activity",
        },
      ],
      params: {
        pageNo: 1, // 当前页数
        pageSize: 2, // 每页显示的数据条数
        fid: null, //所属分会ID
      },
      pageable: {},
    };
  },
  onShow() {
    debugger;
  },
  onLoad(options) {
    _self = this;
    this.cWidth = uni.upx2px(750);
    this.cHeight = uni.upx2px(500);
    this.getServerData();
  },
  methods: {
    getServerData() {
      debugger;
      uni.request({
        url: "https://www.ucharts.cn/data.json",
        data: {},
        success: function (res) {
          console.log(res.data.data);
          //下面这个根据需要保存后台数据，我是为了模拟更新柱状图，所以存下来了
          _self.serverData = res.data.data;
          let Column = { categories: [], series: [] };
          //这里我后台返回的是数组，所以用等于，如果您后台返回的是单条数据，需要push进去
          Column.categories = res.data.data.Column.categories;
          Column.series = res.data.data.Column.series;
          _self.showColumn("canvasColumn", Column);
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
        type: "column",
        legend: { show: true },
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
          //disabled:true
        },
        dataLabel: true,
        width: _self.cWidth * _self.pixelRatio,
        height: _self.cHeight * _self.pixelRatio,
        extra: {
          column: {
            type: "group",
            width:
              (_self.cWidth * _self.pixelRatio * 0.45) /
              chartData.categories.length,
          },
        },
      });
    },
    touchColumn(e) {
      canvaColumn.showToolTip(e, {
        format: function (item, category) {
          if (typeof item.data === "object") {
            return category + " " + item.name + ":" + item.data.value;
          } else {
            return category + " " + item.name + ":" + item.data;
          }
        },
      });
    },
  },
};
</script>

<style lang="scss">
.statisticsView {
  background: #fff;
}
.flex-box {
  display: flex;
  justify-content: space-around;
  padding: 20rpx 0;
}
.alumnusNums {
  width: 300rpx;
  height: 150rpx;
  border: 1px solid #e9e9e9;
  border-radius: 4px;
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
  border-left: 10upx solid #0ea391;
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
