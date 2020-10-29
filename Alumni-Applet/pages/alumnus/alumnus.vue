<template>
  <view class="list">
    <cu-custom bgColor="bg-gradual-green1" :isBack="false">
      <block slot="content">校友</block>
    </cu-custom>
    <view>
      <scroll-view
        scroll-x
        class="bg-white nav text-center"
        scroll-with-animation
      >
        <view
          class="cu-item"
          :class="item.id == tabCur ? 'text-green cur' : ''"
          v-for="item in tabList"
          :key="item.id"
          @tap="tabSelect"
          :data-id="item.id"
          >{{ item.name }}</view
        >
      </scroll-view>
    </view>
    <view v-if="tabCur == 5">
      <!-- <statistics></statistics> -->
      <view class="statisticsView">
        <view class="flex-box">
          <view class="alumnusNums">
            <text class="cuIcon-people myIcon"></text>
            <view class="numsBox">
              <text>校友总数</text>
              <text>{{ peopleNums }}个</text>
            </view>
          </view>
          <view class="alumnusNums">
            <text class="cuIcon-taoxiaopu myIcon"></text>
            <view class="numsBox">
              <text>校友会</text>
              <text>{{ groupNums }}个</text>
            </view>
          </view>
        </view>
        <view class="qiun-columns">
          <view class="qiun-bg-white qiun-title-bar qiun-common-mt">
            <view class="qiun-title-dot-light"
              ><text class="cuIcon-titles text-green1"></text>各级校友统计</view
            >
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
		<alumnusDistribution></alumnusDistribution>
        <view class="qiun-columns">
          <view class="qiun-bg-white qiun-title-bar qiun-common-mt">
            <view class="qiun-title-dot-light"
              ><text class="cuIcon-titles text-green1"></text>就业率统计</view
            >
          </view>
          <view class="qiun-charts">
            <canvas
              canvas-id="canvasTrack"
              id="canvasTrack"
              class="charts"
              @touchstart="touchTrack"
            ></canvas>
          </view>
        </view>
        <view class="qiun-columns">
          <view class="qiun-bg-white qiun-title-bar qiun-common-mt">
            <view class="qiun-title-dot-light"
              ><text class="cuIcon-titles text-green1"></text>就业人数统计</view
            >
          </view>
          <view class="qiun-charts">
            <canvas
              canvas-id="canvasEmployment"
              id="canvasEmployment"
              class="charts"
              @touchstart="touchEmployment"
            ></canvas>
          </view>
        </view>
      </view>
      <!-- <web-view class="webviewStyles" src="https://www.imapway.cn/Alumni/echarts/test.html"></web-view> -->
    </view>
    <view v-else class="">
      <!-- 刷新页面后的顶部提示框 -->
      <!-- 当前弹出内容没有实际逻辑 ，可根据当前业务修改弹出提示 -->
      <view class="tips" :class="{ 'tips-ani': tipShow }"
        >为您更新了10条最新新闻动态</view
      >
      <!-- 基于 uni-list 的页面布局 -->
      <uni-list :class="{ 'uni-list--waterfall': waterfall }">
        <!-- 通过 uni-list--waterfall 类决定页面布局方向 -->
        <uni-list-item
          :border="!waterfall"
          class="uni-list-item--waterfall"
          title="自定义商品列表"
          v-for="(item, i) in lists"
          :key="i"
        >
          <!-- 通过header插槽定义列表左侧图片 -->
          <template v-slot:header>
            <view
              class="uni-thumb shop-picture"
              :class="{ 'shop-picture-column': waterfall }"
            >
              <image :src="item.thumb"></image>
            </view>
          </template>
          <!-- 通过body插槽定义商品布局 -->
          <view slot="body" class="shop">
            <view class="">
              <navigator
                :url="
                  '/pages/alumnus/details?id=' + item.id + '&name=' + item.name
                "
              >
                <view class="view-left">
                  <view class="uni-title">
                    <text class="uni-ellipsis-2">{{ item.name }}</text>
                  </view>
                  <view>
                    <view class="cu-capsule radius">
                      <view class="cu-tag bg-blue sm"> 活动 </view>
                      <view class="cu-tag line-blue sm">
                        {{ item.activity }}
                      </view>
                    </view>
                    <view class="cu-capsule radius">
                      <view class="cu-tag bg-gradual-green1 sm"> 成员 </view>
                      <view class="cu-tag line-green sm">
                        {{ item.member }}
                      </view>
                    </view>
					<button
					  class="alumnus-btn cu-btn round sm bg-orange"
					  v-if="item.join == true"
					>
					  已加入
					</button>
					<button
					  class="alumnus-btn cu-btn round sm bg-orange"
					  v-else
					  @click="addJoin(item)"
					>
					  加入
					</button>
                  </view>
                </view>
              </navigator>
              
            </view>
          </view>
        </uni-list-item>
      </uni-list>
      <!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
      <uni-load-more v-if="lists.length > 0" :status="status" />
    </view>
  </view>
</template>

<script>
import { getAlumnusList, addAlumnusJoin } from "@/api/alumnus.js";
import uCharts from "../../js_sdk/u-charts/u-charts.js";
import alumnusDistribution from './alumnusDistribution.vue'
var _self;
var canvaColumn = null;
var canvaTrack = null;
var canvaEmployment = null;
// var canvaDistribution = null;

export default {
  components: {
    alumnusDistribution
  },
  data() {
    return {
      cWidth: "",
      cHeight: "",
      pixelRatio: 1,
      serverData: "",
      peopleNums: 7534,
      groupNums: 23,
      tabCur: 5,
      current: 0,
      tabList: [
        {
          id: 5,
          name: "校友统计",
        },
        {
          id: "all",
          name: "全部",
        },
        {
          id: 2,
          name: "校友之窗",
        },
        {
          id: 3,
          name: "同城校友",
        },
        {
          id: 4,
          name: "行业校友",
        },
      ],
      // , {
      // 	id: 5,
      // 	name: '校友统计',
      // 	uri: '/pages/alumnus/statistics'
      // }
      lists: [
        {
          name: "校友总会",
          thumb:
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603106747111&di=c70b4adee0bef68057ea07caca505d5f&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%3D450%2C600%2Fsign%3De5752296cb95d143da23ec2746c0ae34%2Fdc54564e9258d109140c6727d258ccbf6d814dbc.jpg",
          member: 30,
          activity: 2,
        },
      ], // 列表数据
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
    this.title = options.title;
    // let params = {
    // 	pageNo: this.current,
    // 	pageSize: this.pageSize,
    // 	type: ''
    // }
    this.params.userId = uni.getStorageSync("openid");
    this.getAlumnusList(this.params);
    _self = this;
    this.cWidth = uni.upx2px(750);
    this.cHeight = uni.upx2px(500);
    this.getServerData();
  },
  methods: {
    getAlumnusList(params) {
      getAlumnusList(params).then(data => {
        var [error, res] = data;
        if (res && res.data && res.data.result) {
          this.lists = res.data.result.content;
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
      this.getAlumnusList(this.params);

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
     * 下拉刷新回调函数
     */
    onPullDownRefresh() {
      console.log("下拉刷新");
      if (this.params.pageNo > 1) {
        this.status = "loading";
        this.params.pageNo -= 1;
        this.getAlumnusList(this.params);
      }
    },
    /**
     * 上拉加载回调函数
     */
    onReachBottom() {
      console.log("上拉刷新");
      this.getNewsList();
    },
    addJoin(alumnu) {
      debugger;
      let params = {
        alumnusId: alumnu.id,
        userId: "",
        userName: "",
        userPhoto: "",
        status: "1",
      };
      let join = alumnu.join;
      if (!join) {
      }
      //获取用户信息
      params.userId = uni.getStorageSync("openid");
      let userInfo = uni.getStorageSync("userInfo");
      if (userInfo) {
        params.userName = userInfo.nickName;
        params.userPhoto = userInfo.avatarUrl;
        addAlumnusJoin(params).then(data => {
          console.log(data);
        });
      } else {
        //跳转页面
        wx.navigateTo({
          url: "/pages/login/login",
        });
      }
    },
    /**
     * 获取页面数据
     * @param {Object} reload 参数reload值为true时执行列表初始化逻辑，值为false时执行追加下一页数据的逻辑。默认为false
     */
    getNewsList(reload) {
      if (this.totalPages > this.params.pageNo) {
        this.status = "loading";
        this.params.pageNo += 1;
        this.getAlumnusList(this.params);
      } else {
        this.status = "noMore";
      }

      // db.collection('opendb-mall-goods')
      // 	.where({
      // 		// 查询字段是否存在
      // 		_id: dbCmd.exists(true)
      // 	})
      // 	// 跳过对应数量的文档，输出剩下的文档
      // 	.skip(this.pageSize * (this.current - 1))
      // 	// 限制输出到下一阶段的记录数
      // 	.limit(this.pageSize)
      // 	// 获取集合中的记录
      // 	.get()
      // 	.then((res) => {
      // 		const tempList = res.result.data
      // 		// 判断是否可翻页
      // 		if (tempList.length === this.pageSize) {
      // 			this.status = 'more'
      // 		} else {
      // 			this.status = 'noMore'
      // 		}
      // 		if (reload) {
      // 			// 处理下拉加载提示框
      // 			this.tipShow = true;
      // 			clearTimeout(this.timer);
      // 			this.timer = setTimeout(() => {
      // 				this.tipShow = false;
      // 			}, 2000);
      // 			this.lists = tempList
      // 			// 停止刷新
      // 			uni.stopPullDownRefresh()
      // 		} else {
      // 			// 上拉加载后合并数据
      // 			this.lists = this.lists.concat(tempList)
      // 		}
      // 		if (tempList.length) {
      // 			this.current++
      // 		}
      // 	}).catch((err) => {
      // 		uni.showModal({
      // 			content: '请求失败，请稍后再试：' + err,
      // 			showCancel: false
      // 		})
      // 	})s
    },
    getServerData() {
		// _self.$refs.alumnusDistribution.getData();
      uni.request({
        //往期校友统计
        url: "https://www.imapway.cn/Alumni/alumniStatistics.json",
        data: {},
        success: function (res) {
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
        success: function (res) {
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
        success: function (res) {
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
        type: "column",
        legend: { show: false },
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
            width:
              (_self.cWidth * _self.pixelRatio * 0.45) /
              chartData.categories.length,
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
        legend: { show: false },
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
        legend: { show: false },
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
            width: 40,
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
    touchTrack(e) {
      canvaTrack.showToolTip(e, {
        format: function (item) {
          return item.name + ":" + item.data;
        },
      });
    },
    touchEmployment(e) {
      canvaEmployment.showToolTip(e, {
        format: function (item) {
          return item.name + ":" + item.data;
        },
      });
    }
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

.nav .cu-item {
  height: 45px;
  display: inline-block;
  line-height: 45px;
  margin: 0 5px;
  padding: 0 5px;
}

.alumnus-btn {
  position: absolute;
  right: 10px;
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
  width: 300rpx;
  height: 150rpx;
  border: 1px solid #e9e9e9;
  border-radius: 4px;
  display: flex;
  padding: 0 40rpx;
  justify-content: space-between;
  align-items: center;
  .myIcon {
    font-size: 30px;
  }
}
.numsBox {
  display: flex;
  flex-direction: column;
  align-items: center;
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
</style>
