<template>
  <view>
    <view class="header">
      <view
        class="bg"
        style="
          background-image: url(http://www.imapway.cn/Alumni/static/personal/background.png);
        "
      >
        <cu-custom
          style="color: #fff"
          :isBack="true"
          :isCallBack="false"
          @callBack="callBack"
        >
          <block slot="content">我的收藏</block>
        </cu-custom>
      </view>
    </view>
    <view
      v-if="lists.length === 0"
      style="margin: 20px auto; color: #00beb7; text-align: center"
      >暂无收藏记录~
    </view>
    <scroll-view
      scroll-y
      class="indexes"
      :scroll-into-view="'indexes-' + listCurID"
      :style="[{ height: 'calc(100vh - ' + CustomBar + 'px - 50px)' }]"
      :scroll-with-animation="true"
      :enable-back-to-top="true"
    >
      <block v-for="(item, index) in lists" :key="index">
        <view
          :class="'itemList indexItem-' + index"
          :id="'indexes-' + index"
          :data-index="index"
        >
          <view class="cu-list menu-avatar no-padding">
            <view class="cu-item">
              <view class="content">
                <view class="text-grey">{{ item.title }} </view>
                <view class="text-grey"
                  >{{ item.createTime.slice(0, 11) }}
                </view>
              </view>
            </view>
          </view>
        </view>
      </block>
    </scroll-view>
  </view>
</template>

<script>
import { getCollect } from "@/api/user.js";

export default {
  data() {
    return {
      lists: [
        // { title: "有人赞了你的朋友圈", createTime: "2020-02-11" },
      ],
      CustomBar: this.CustomBar,
      listCurID: "",
    };
  },
  onLoad() {
    this.getMemberList(true);
  },
  methods: {
    /**
     * 获取页面数据
     * @param {Object} reload 参数reload值为true时执行列表初始化逻辑，值为false时执行追加下一页数据的逻辑。默认为false
     */
    getMemberList(reload) {
      let that = this;
      this.status = "loading";
      let openid = uni.getStorageSync("openid");
      if (openid && openid != "") {
        let param = {
          userId: openid,
        };
        getCollect(param).then(data => {
          var [error, res] = data;
          if (res && res.data.success) {
            const tempList = res.data.result.content;
            // this.lists = res.data.result.content;
            // 判断是否可翻页
            if (tempList.length === this.pageSize) {
              this.status = "more";
            } else {
              this.status = "noMore";
            }

            if (reload) {
              // 处理下拉加载提示框
              this.tipShow = true;
              clearTimeout(this.timer);
              this.timer = setTimeout(() => {
                this.tipShow = false;
              }, 2000);
              that.lists = tempList;
              // 停止刷新
              uni.stopPullDownRefresh();
            } else {
              // 上拉加载后合并数据
              that.lists = that.lists.concat(tempList);
            }
            if (tempList.length) {
              this.current++;
            }
          }
        });
      } else {
        getApp().getUserInfo();
      }
    },
  },
};
</script>

<style lang="scss">
.itemList .cu-list.menu-avatar .cu-item {
  height: 60px;
  .content {
    left: 20px;
  }
}
</style>
