<template>
  <view>
    <view class="header">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true" :isCallBack="false">
			<!-- <block slot="backText">返回</block> -->
			<block slot="content">我的消息</block>
		</cu-custom>
    </view>
    <view
      v-if="lists.length === 0"
      style="margin: 20px auto; color: #00beb7; text-align: center"
      >暂无消息~
    </view>
	<view class="contentBox">
		<view
		  class="phm-card cu-card case no-card"
		  v-for="(item, index) in lists"
		  :key="index"
		>
			<view
			  :class="'itemList indexItem-' + index"
			  :id="'indexes-' + index"
			  :data-index="index"
			>
			  <view class="cu-list menu-avatar no-padding">
				<view class="cu-item">
				  <view class="content">
					<view class="text-grey">{{ item.content }} </view>
					<view class="text-grey">{{ item.createTime.slice(0, 11) }} </view>
				  </view>
				</view>
			  </view>
			</view>
		</view>
	</view>
	<uni-load-more v-if="lists.length > 0" :status="status" />
  </view>
</template>

<script>
import { getMyNews } from "@/api/user.js";

export default {
  data() {
    return {
      lists: [
        { content: "xx关注了你", createTime: "2020-02-11" },
        { content: "你的校友审核已通过", createTime: "2020-02-11" },
        { content: "xx关注了你", createTime: "2020-02-11" },
        { content: "有人赞了你的朋友圈", createTime: "2020-02-11" },
      ],
      CustomBar: this.CustomBar,
      listCurID: "",
      current: 1,
      pageSize: 10,
	  status: 'more', // 加载状态
    };
  },
  onLoad() {
    this.getMemberList(true);
  },
  methods: {
	  /**
	   * 下拉刷新回调函数
	   */
	  onPullDownRefresh() {
	  	this.current = 1
	  	this.getMemberList(true);
	  },
	  /**
	   * 上拉加载回调函数
	   */
	  onReachBottom() {
	  	this.getMemberList();
	  },
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
            pageNo: this.current,
            pageSize: this.pageSize,
        };
        getMyNews(param).then(data => {
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

<style lang="scss" scoped>
.itemList .cu-list.menu-avatar .cu-item {
  height: 65px;
  .content {
    left: 20px;
  }
}
</style>
