<template>
  <view class="ann_news">
    <cu-custom
      bgColor="bg-gradual-green1"
      :isBack="true"
      :isCallBack="false"
      @callBack="callBack"
    >
      <block slot="backText">返回</block>
      <block slot="content">{{ title }}</block>
    </cu-custom>
    <!-- <view class="lightUp" v-if="lightUpState">
			<view class="lightUpText">
				共有 <text class="alumnus">{{alumnus}}</text> 位校友参与
			</view>
			<text class="lightUpBtn" @click="lightUpStart">我要点亮</text>
		</view> -->
    <view class="ann_news_content">
      <map
        class="map"
        :longitude="longitude"
        :latitude="latitude"
        :markers="markers"
        :covers="covers"
        scale="5"
        show-location
      ></map>
      <view class="numsBox">
        <view class=""
          >点亮人数<text class="textNums">{{ starNums }}</text></view
        >
        <view class=""
          >点亮城市<text class="textNums">{{ starCitys }}</text></view
        >
        <view class=""
          >点亮国家<text class="textNums">{{ starCountrys }}</text></view
        >
      </view>
      <view class="btnBox">
        <!-- <view class="btn">邀请好友点亮</view> -->
        <button class="btn" @click="lightUpOrUpdate">{{ textBtn }}</button>
        <button class="btn invitationBtn" open-type="share">
          邀请好友点亮
        </button>
      </view>
      <view
        :class="
          !lightUpState && !showState ? 'tipBox showTip' : 'tipBox hideTip'
        "
      >
        <view class="tip">
          <view class="circle"></view>
          <view class="starBox">
            <view class=""
              >您是第<text class="textNums">{{ totalStars }}</text
              >位点亮者</view
            >
            <view class=""
              >您所在的城市已有<text class="textNums">{{
                currentCityStars
              }}</text
              >位点亮哦</view
            >
          </view>
        </view>
      </view>
    </view>
  </view>
</template>
<script>
// var QQMapWX = require('../../../js_sdk/qqmap-wx-jssdk.js')
import {
  getPositionList,
  addLightUpPersonnel,
  updatePersonnelPosition,
} from "@/api/cooperation.js";
export default {
  data() {
    return {
      title: "校庆专题-点亮全球",
      userId: "",
      userLightUpId: "",
      lightUpState: true,
      showState: true,
      alumnus: 0,
      starNums: 0,
      totalStars: 0,
      currentCityStars: 45,
      starCitys: 1,
      starCountrys: 1,
      location: "",
      longitude: "",
      latitude: "",
      textBtn: "我要点亮",
      markers: [],
      covers: [],
    };
  },
  onLoad() {
    let that = this;
    this.userId = uni.getStorageSync("openid");
    this.userLightUpId = uni.getStorageSync("userLightUpId");
    if (this.userId !== this.userLightUpId) {
      //点亮
      this.lightUpState = true;
      this.showState = true;
      this.textBtn = "我要点亮";
    } else {
      //更新
      this.lightUpState = true;
      this.showState = false;
      this.textBtn = "更新位置";
    }
    uni.getLocation({
      type: "gcj02",
      altitude: true,
      //定位成功，更新定位结果
      success: function (res) {
        console.log(res);
        that.latitude = res.latitude;
        that.longitude = res.longitude;
        that.speed = res.speed;
        that.accuracy = res.accuracy;
        that.location = res.longitude + "," + res.latitude;
        that.getPositionListData();
      }, //定位失败回调
      fail: function () {
        uni.hideLoading();
        console.log("getLocationFail");
      },
      complete: function () {
        //隐藏定位中信息进度
        uni.hideLoading();
      },
    });
  },
  onShareAppMessage: function () {
    return {
      title: "快来和我一起点亮全球吧。",
      path: `/pages/anniversary/footprint/footprint`,
    };
  },
  methods: {
    callBack() {
      uni.redirectTo({
        url: "/pages/anniversary/index",
      });
    },
    getPositionListData() {
      getPositionList("").then(data => {
        var [error, res] = data;
        if (res && res.data.success) {
          let datas = res.data.result.list;
          this.starNums = datas.length;
          this.starCountrys = res.data.result.countryNum;
          this.starCitys = res.data.result.cityNum;
          datas.forEach(v => {
            let location = v.location.split(",");
            this.markers.push({
              //赋值数组
              id: v.id,
              latitude: location[1],
              longitude: location[0],
              iconPath: "http://www.imapway.cn/Alumni/static/images/marker.png", //图标路径
              width: 30,
              height: 30,
            });
          });
          console.log("this.markers", this.markers);
        }
      });
    },
    lightUpOrUpdate() {
      if (this.userId !== this.userLightUpId) {
        //点亮
        this.addLightUpPersonnel();
      } else {
        //更新
        this.lightUpState = true;
        this.showState = false;
        this.textBtn = "更新位置";
        this.updatePersonnelPosition();
      }
    },
    addLightUpPersonnel() {
      let that = this;
      let userInfo = uni.getStorageSync("userInfo");
      if (userInfo) {
        let param = {
          userId: that.userId,
          userName: userInfo.nickName,
          userPhoto: userInfo.avatarUrl,
          location: that.location,
        };
        addLightUpPersonnel(param).then(data => {
          var [error, res] = data;
          if (res && res.data.success) {
            let datas = res.data.result;
            console.log("点亮", JSON.stringify(datas));
            let location = datas.location.split(",");
            this.markers.push({
              //赋值数组
              id: datas.id,
              latitude: location[1],
              longitude: location[0],
              iconPath: "../../../static/images/marker.png", //图标路径
              width: 30,
              height: 30,
            });
            uni.setStorageSync("userLightUpId", res.data.result.userId);
            that.starNums = that.starNums + 1;
            that.totalStars = that.starNums;
            that.textBtn = "更新位置";
            that.lightUpState = false;
            that.showState = false;
            setTimeout(() => {
              that.showState = true;
            }, 2000);
          }
        });
      } else {
        //跳转页面
        wx.navigateTo({
          url: "/pages/login/login",
        });
      }
    },
    updatePersonnelPosition() {
      let that = this;
      let param = {
        userId: this.userId,
        location: this.location,
      };
      updatePersonnelPosition(param).then(data => {
        var [error, res] = data;
        if (res && res.data.success) {
          let datas = res.data.result;
          let location = datas.location.split(",");
          that.markers.forEach(v => {
            if (v.id === datas.id) {
              v.latitude = location[1];
              v.longitude = location[0];
            }
          });
          uni.showToast({
            title: "位置更新成功",
          });
        }
      });
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
  top: 45px;
  bottom: 0px;
  left: 0px;
  right: 0px;
  background-size: 100% 100%;
  background-color: white;
}
.map {
  width: 100%;
  height: 500px;
}
.lightUp {
  text-align: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.lightUpText {
  margin-bottom: 40rpx;
}
.alumnus {
  color: #fa9a25;
}
.lightUpBtn {
  padding: 8rpx 70rpx;
  background: #fa9a25;
  color: #fff;
}
.numsBox {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background: #f2f2f2;
  height: 40px;
}
.textNums {
  color: #f37b1d;
  margin-left: 10rpx;
}
.btnBox {
  display: flex;
  justify-content: space-around;
  margin-top: 40rpx;
  .btn {
    background: #ff8901;
    color: #fff;
    padding: 0px 8px;
    width: 260rpx;
    text-align: center;
    border-radius: 20px;
    margin: 0;
    font-size: 14px;
    height: 35px;
  }
  .invitationBtn {
    background: #00beb7;
  }
}
.tipBox {
  position: fixed;
  height: 100rpx;
  border-radius: 30px;
  top: 180rpx;
  left: 100rpx;
  background: #bebebeb5;
}
.tip {
  // width:540rpx;
  display: flex;
  align-items: center;
  .circle {
    width: 80rpx;
    height: 80rpx;
    border-radius: 50%;
    background: #fff;
    margin: 0 12rpx;
  }
  .starBox {
    margin-right: 40rpx;
    .textNums {
      margin-right: 10rpx;
    }
  }
}
.showTip {
  display: block;
  transition: all 2s;
}
.hideTip {
  display: none;
  transition: all 2s;
}
</style>
