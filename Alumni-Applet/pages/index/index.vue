<template>
  <view class="content">
    <!-- 首页 -->
    <view :style="{ display: show_index == 0 ? 'block' : 'none' }">
      <tab-home ref="home"></tab-home>
    </view>
    <!-- 组织 -->
    <view :style="{ display: show_index == 1 ? 'block' : 'none' }">
      <tab-organize ref="organize"></tab-organize>
    </view>
    <!-- 校庆 -->
    <view :style="{ display: show_index == 2 ? 'block' : 'none' }">
      <tab-anniversary ref="anniversary"></tab-anniversary>
    </view>
    <!-- 发现 -->
    <view :style="{ display: show_index == 3 ? 'block' : 'none' }">
      <tab-discovery ref="discovery"></tab-discovery>
    </view>
    <!-- 个人中心 -->
    <view :style="{ display: show_index == 4 ? 'block' : 'none' }">
      <tab-mycenter
        :style="{ display: show_index == 4 ? 'block' : 'none' }"
        ref="mycenter"
      ></tab-mycenter>
    </view>
    <!-- is_lhp判断是否为刘海屏在main.js里，好像uniapp有一个css变量获取刘海屏的安全区域 -->
    <view class="tabBar" :style="{ height: is_lhp ? '140rpx' : '98rpx' }">
      <!-- 导航的中间圆圈 -->
      <view
        class="border_box"
        :style="{ paddingBottom: is_lhp ? '40rpx' : '' }"
      >
        <view class="tabBar_miden_border"></view>
      </view>
      <view
        class="tabBar_list"
        :style="{ paddingBottom: is_lhp ? '40rpx' : '' }"
      >
        <view
          v-for="item in tab_nav_list"
          :key="item.id"
          :class="{ tabBar_item: item.id != 2, tabBar_item2: item.id == 2 }"
          @tap="cut_index(item.id)"
        >
          <image
            v-if="show_index == item.id"
            :src="`../../static/tabBar/${item.id + 1}${item.id + 1}.png`"
          ></image>
          <image v-else :src="`../../static/tabBar/${item.id + 1}.png`"></image>
          <view
            :class="{ tabBar_name: true, nav_active: show_index == item.id }"
            >{{ item.name }}</view
          >
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import tabHome from "@/components/tabbar/home.vue";
import tabOrganize from "@/components/tabbar/organize.vue";
import tabAnniversary from "@/components/tabbar/anniversary.vue";
import tabDiscovery from "@/components/tabbar/discovery.vue";
import tabMycenter from "@/components/tabbar/mycenter.vue";
export default {
  components: {
    tabHome, //首页    0
    tabOrganize, //组织    1
    tabAnniversary, //校庆   2
    tabDiscovery, //发现   3
    tabMycenter, //个人中心  4
  },
  data() {
    return {
      show_index: 0, //控制显示那个组件
      tab_nav_list: [
        { id: 0, name: "首页" },
        { id: 1, name: "组织" },
        { id: 2, name: "校庆" },
        { id: 3, name: "发现" },
        { id: 4, name: "我的" },
      ], //菜单列表
      is_lhp: false,
    };
  },
  onLoad() {
    let _this = this;
    this.is_lhp = this.$is_bang;
    this.$nextTick(function () {
      // 一定要等视图更新完再调用方法   -----------++++++++++++++++重要
      setTimeout(function () {
        uni.setNavigationBarColor({
          frontColor: "#ffffff",
          backgroundColor: "#6739b6",
        });
        _this.$refs.home.ontrueGetList(); //初次加载第一个页面的请求数据
      }, 100);
    });

    console.log("是否为刘海屏", this.is_lhp);
  },
  methods: {
    // 切换组件
    cut_index(type) {
      console.log("----------------------------------", type);
      let _this = this;
      _this.show_index = type;
      if (_this.show_index == 0) {
        uni.setNavigationBarColor({
          frontColor: "#ffffff",
          backgroundColor: "#6739b6",
        });
        _this.$refs.home.ontrueGetList();
      } else if (_this.show_index == 1) {
        _this.$refs.organize.ontrueGetList();
      } else if (_this.show_index == 2) {
        _this.$refs.anniversary.ontrueGetList();
      } else if (_this.show_index == 3) {
        uni.setNavigationBarColor({
          frontColor: "#ffffff",
          backgroundColor: "#ec008c",
        });
        _this.$refs.discovery.ontrueGetList();
      } else if (_this.show_index == 4) {
        _this.$refs.mycenter.ontrueGetList();
      }
	  uni.pageScrollTo({
	      scrollTop: 0,
	      duration: 300
	  });
    },
    // onPullDownRefresh() {
    //   uni.showToast({
    //     title: `第${this.show_index + 1}个页面的刷新`,
    //   });
    //   setTimeout(function () {
    //     uni.stopPullDownRefresh();
    //   }, 2000);
    //   console.log(
    //     "下拉刷新四个组件公用的下拉刷新方法,根据在哪个页面下拉执行哪个页面的刷新方方法即可"
    //   );
    //   console.log(
    //     "如果想要自定义刷新的话，插件市场就有一个   非常好用也非常容易入手"
    //   );
    // },
  },
};
</script>

<style lang="scss">
.tabBar {
  width: 100%;
  height: 98rpx;
  background: #fff;
  border-top: 1px solid #e5e5e5;
  position: fixed;
  bottom: 0px;
  left: 0px;
  right: 0px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  .tabBar_list {
    width: 86%;
    display: flex;
    justify-content: space-between;
    image {
      width: 48rpx;
      height: 48rpx;
      margin-bottom: 2rpx;
    }
    .tabBar_item {
      width: 68rpx;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      font-size: 20rpx;
      color: #969ba3;
      margin-top: 4px;
    }
    .tabBar_item2 {
      width: 68rpx;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      font-size: 20rpx;
      color: #969ba3;
      margin-top: -16rpx;
      position: relative;
      z-index: 101;
      image {
        width: 68rpx;
        height: 68rpx;
      }
    }
  }
}
.border_box {
  // pointer-events: none; 事件穿透解决z-index层级问题
  width: 100%;
  height: 110rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0px;
  bottom: 50rpx;
  z-index: 100;
  pointer-events: none;
  .tabBar_miden_border {
    width: 100rpx;
    height: 50rpx;
    border-top: 2rpx solid #e5e5e5;
    border-radius: 50rpx 50rpx 0 0; /* 左上、右上、右下、左下 */
    background: #fff;
  }
}
.nav_active {
  color: #01bfb8;
}
</style>
