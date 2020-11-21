<template>
  <view>
    <view class="header">
      <view
        class="bg"
        style="
          background-image: url(http://www.imapway.cn/Alumni/static/personal/background.png);
        "
      >
        <view class="box">
          <view class="box-hd">
            <view class="avator" @click="loginHandler">
              <img :src="userInfo.avatarUrl" />
            </view>
            <view class="phone-number">{{ userInfo.nickName }}</view>
          </view>
          <view class="box-bd">
            <navigator url="/pages/personal/myNews/myNews/myNews">
              <view class="item">
                <view class="icon"
                  ><img
                    src="http://www.imapway.cn/Alumni/static/personal/wdpl2x.png"
                /></view>
                <view class="text">我的消息</view>
              </view>
            </navigator>
            <navigator url="/pages/personal/myApply/myApply/myApply">
              <view class="item">
                <view class="icon"
                  ><img
                    src="http://www.imapway.cn/Alumni/static/personal/apply2x.png"
                /></view>
                <view class="text">我的申请</view>
              </view>
            </navigator>
            <navigator url="/pages/personal/myCollect/myCollect/myCollect">
              <view class="item">
                <view class="icon"
                  ><img
                    src="http://www.imapway.cn/Alumni/static/personal/sc2x.png"
                /></view>
                <view class="text">我的收藏</view>
              </view>
            </navigator>
          </view>
        </view>
      </view>
    </view>
    <view class="list-content">
      <view
        class="list"
        v-for="(item, index) in menuList"
        @click="navigateTo(item.id)"
        :key="index"
      >
        <navigator
          :url="isCertification && item.pageInfo ? item.pageInfo : item.page"
        >
          <view class="li noborder">
            <view class="icon">
              <image :src="item.icon"></image>
            </view>
            <view class="text"
              >{{ item.name
              }}{{ isCertification && item.pageInfo ? "(已认证)" : " " }}</view
            >
            <image
              class="to"
              src="http://www.imapway.cn/Alumni/static/user/to.png"
            ></image>
          </view>
        </navigator>
      </view>
    </view>
  </view>
</template>
<script>
import { getWechatUserById } from "@/api/user.js";
export default {
  data() {
    return {
      userInfo: {
        nickName: "点击头像登录",
        gender: 1,
        language: "zh_CN",
        city: "Xian",
        province: "Shannxi",
        country: "China",
        avatarUrl: "http://www.imapway.cn/Alumni/static/user/face.jpg",
      },
      isCertification: false, //是否认证
      auditStatus: "", //认证状态
      menuList: [
        {
          id: "xyrz",
          name: "校友认证",
          icon: "http://www.imapway.cn/Alumni/static/personal/grxx2x.png",
          // page: "/pages/personal/basicInfo/certification",
          // pageInfo: "/pages/personal/basicInfo/basicInfo",
        },
        {
          name: "我的组织",
          icon: "http://www.imapway.cn/Alumni/static/personal/wdzz2x.png",
          page: "/pages/personal/alumnus/alumnus",
        },
        {
          name: "我的关注",
          icon: "http://www.imapway.cn/Alumni/static/personal/wdgz2x.png",
          page: "/pages/personal/attention/attention",
        },
        {
          name: "我的粉丝",
          icon: "http://www.imapway.cn/Alumni/static/personal/fans2x.png",
          page: "/pages/personal/fans/fans",
        },
        {
          id: "xtgl",
          name: "系统管理",
          icon: "http://www.imapway.cn/Alumni/static/personal/sz2x.png",
          // page: "/pages/personal/manager/login"
        },
        //   {
        // 	name: "设置",
        // 	icon: "http://www.imapway.cn/Alumni/static/personal/sz2x.png",
        // 	page: "/pages/personal/setting/setting"
        // }
      ],
    };
  },
  onShow() {
    let userInfo = uni.getStorageSync("userInfo");
    if (userInfo && userInfo != "") {
      this.userInfo = userInfo;
      this.getWechatUserInfo();
    } else {
      uni.navigateTo({
        url: "/pages/login/login",
      });
    }
  },
  methods: {
    getWechatUserInfo() {
      let that = this;
      that.isCertification = uni.getStorageSync("isCertification");
      let openid = uni.getStorageSync("openid");
      if (openid && openid != "") {
        let param = {
          openid: openid,
        };
        getWechatUserById(param).then(data => {
          var [error, res] = data;
          if (res && res.data.success) {
            let result = res.data.result;
            if (result != null) {
              let auditStatus = result.auditStatus;
              that.auditStatus = auditStatus;
              if (auditStatus == "1") {
                that.isCertification = true;
              } else {
                that.isCertification = false;
              }
              uni.setStorageSync("auditStatus", that.auditStatus);
              uni.setStorageSync("isCertification", that.isCertification);
            } else {
              that.isCertification = false;
              uni.setStorageSync("auditStatus", null);
              uni.setStorageSync("isCertification", that.isCertification);
            }
          }
        });
      } else {
        getApp().getUserInfo();
      }
    },

    navigateTo(value) {
      if (value && value == "xyrz") {
        debugger;
        //判断认证状态
        let auditStatus = uni.getStorageSync("auditStatus");
        let isCertification = uni.getStorageSync("isCertification");
        if (isCertification != null && isCertification != "") {
          uni.navigateTo({
            url: "/pages/personal/basicInfo/basicInfo",
          });
        } else {
          if (auditStatus) {
            uni.navigateTo({
              url: "/pages/personal/basicInfo/basicInfo",
            });
          } else {
            uni.navigateTo({
              url: "/pages/personal/basicInfo/certification",
            });
          }
        }
      } else if (value && value == "xtgl") {
        uni.navigateTo({
          // url:'/pages/personal/examine/examine'
          url: "/pages/personal/manager/login",
        });
      }
    },
    changeSkin() {
      uni.navigateTo({
        url: "../skin-change/skin-change",
      });
    },
    loginHandler() {
      let openid = uni.getStorageSync("openid");
      let userInfo = uni.getStorageSync("userInfo");
      let isCertification = uni.getStorageSync("isCertification");
      if (userInfo) {
        this.userInfo = userInfo;
        if (isCertification) {
          uni.navigateTo({
            url: "/pages/personal/userDetail/userDetail?userId=" + openid,
          });
        } else {
          uni.navigateTo({
            url: "/pages/personal/basicInfo/certification",
          });
        }
      } else {
        uni.navigateTo({
          url: "/pages/login/login",
        });
      }
    },
  },
};
</script>

<style lang="scss">
page {
  background-color: #f1f1f1;
  font-size: 30upx;
}

.header {
  background: #fff;
  height: 500upx;
  padding-bottom: 110upx;

  .bg {
    width: 100%;
    height: 300upx;
    padding-top: 200upx;
    background-color: #4191ea;
    // background:url(../../static/personal/background.png);
    background-size: 100% 100%;
  }
}

.box {
  width: 650upx;
  height: 280upx;
  border-radius: 20upx;
  margin: 0 auto;
  background: #fff;
  box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, 0.2);

  .box-hd {
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: center;

    .avator {
      width: 160upx;
      height: 160upx;
      background: #fff;
      border: 5upx solid #fff;
      border-radius: 50%;
      margin-top: -80upx;
      overflow: hidden;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .phone-number {
      width: 100%;
      text-align: center;
    }
  }

  .box-bd {
    display: flex;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: center;

    .item {
      flex: 1 1 auto;
      display: flex;
      flex-wrap: wrap;
      flex-direction: row;
      justify-content: center;
      border-right: 1px solid #f1f1f1;
      margin: 15upx 0;

      &:last-child {
        border: none;
      }

      .icon {
        width: 60upx;
        height: 60upx;

        img {
          width: 100%;
          height: 100%;
        }
      }

      .text {
        width: 100%;
        text-align: center;
        margin-top: 10upx;
      }
    }
  }
}

.list-content {
  background: #fff;
}

.list {
  width: 100%;
  border-bottom: 15upx solid #f1f1f1;
  background: #fff;

  &:last-child {
    border: none;
  }

  .li {
    width: 100%;
    height: 100upx;
    padding: 0 4%;
    border-bottom: 1px solid rgb(243, 243, 243);
    display: flex;
    align-items: center;

    &.noborder {
      border-bottom: 0;
    }

    .icon {
      flex-shrink: 0;
      width: 50upx;
      height: 50upx;

      image {
        width: 50upx;
        height: 50upx;
      }
    }

    .text {
      padding-left: 20upx;
      width: 100%;
      color: #666;
    }

    .to {
      flex-shrink: 0;
      width: 40upx;
      height: 40upx;
    }
  }
}
</style>
