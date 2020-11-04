<template>
  <view class="ann_news">
    <cu-custom bgColor="bg-gradual-green1" :isBack="true">
      <block slot="backText">返回</block>
      <block slot="content">{{ title }}</block>
    </cu-custom>
    <view class="ann_news_content">
      <view class="bulletChatArea">
        <lff-barrage ref="lffBarrage" :list="dataList"></lff-barrage>
      </view>
      <view class="sendBox">
        <textarea
          class="sendText"
          :value="textContent"
          placeholder="请输入祝福语"
          @input="changeText"
        />
        <button type="default" @click="colrdo" class="sendBtn">发送祝福</button>
      </view>
    </view>
  </view>
</template>

<script>
import lffBarrage from "@/components/lff-barrage/lff-barrage.vue";
import { getBulletChatList, sendBulletChat } from "@/api/cooperation.js";
export default {
  data() {
    return {
      title: "校庆祝福",
      textContent: "",
      dataList: [],
    };
  },
  onLoad() {
    this.getBulletChatList();
  },
  components: { lffBarrage },
  methods: {
    getBulletChatList() {
      let param = {
        pageNo: 1,
        pageSize: 10,
      };
      getBulletChatList(param).then(data => {
        var [error, res] = data;
        if (res && res.data.success) {
          let dataList = res.data.result.content;
          this.dataList = dataList;
        }
      });
    },
    changeText(e) {
      this.textContent = e.target.value;
    },
    colrdo() {
      //插入一条弹幕
      // 获取用户信息
      let userInfo = uni.getStorageSync("userInfo");
      let userId = uni.getStorageSync("openid");
      if (userInfo) {
        if (this.textContent === "") {
          uni.showToast({
            icon: "none",
            title: "请输入祝福语",
          });
          return;
        }
        let param = {
          context: this.textContent,
          userId: userId,
          userName: userInfo.nickName === null ? "校友" : userInfo.nickName,
          userPhoto: userInfo.avatarUrl,
        };
        sendBulletChat(param).then(data => {
          var [error, res] = data;
          if (res && res.data.success) {
            let datas = res.data.result;

            this.$refs.lffBarrage.add({
              item: this.textContent,
              name: userInfo.nickName,
              avatarUrl: userInfo.avatarUrl,
            });
            uni.showToast({
              title: "发送成功",
            });
            this.textContent = "";
          }
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.ann_news {
  width: 100%;
  height: 100%;
}
.ann_news_content {
  position: absolute;
  top: 120rpx;
  bottom: 0px;
  left: 0px;
  right: 0px;
  background-size: 100% 100%;
  background-color: white;
}
.sendBox {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 200rpx;
  .sendBtn {
    background: #f37b1d;
    color: #fff;
    font-size: 16px;
    margin: 16rpx auto;
    width: 96%;
    height: 100rpx;
    line-height: 100rpx;
  }
  .sendText {
    width: 100%;
    height: 70rpx;
    line-height: 60rpx;
    padding: 10rpx 0;
    text-indent: 10px;
    background: #fff;
    border: 1px solid #f2f2f2;
  }
}
</style>
