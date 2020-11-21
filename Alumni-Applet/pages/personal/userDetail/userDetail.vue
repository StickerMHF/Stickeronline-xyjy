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
          <block slot="content">个人主页</block>
        </cu-custom>
        <view class="avator">
          <view
            class="cu-avatar round lg margin-left"
            :style="[{ backgroundImage: 'url(' + userInfo.avatar_url + ')' }]"
          >
            <!-- <view class="cu-tag badge"></view> -->
            <!-- :class="userInfo.sex==='女'?'cuIcon-female bg-pink':'cuIcon-male bg-blue'" -->
          </view>
        </view>
        <view class="userName">{{ userInfo.nick_name }}</view>
        <view class="fansData">
          <span @click="hrefToPage(1)">组织 {{ alumnusNum }}</span>
          <span @click="hrefToPage(2)">关注 {{ followNum }}</span>
          <span @click="hrefToPage(3)">粉丝 {{ fansNum }}</span>
        </view>
      </view>
      <view class="dongtai">
        <span>动态</span>
      </view>
      <view>
        <moments :list="momentsList"></moments>
      </view>
    </view>
  </view>
</template>

<script>
import moments from "@/components/moments/moments.vue";
import { getDiscoverList } from "@/api/discover.js";
import { dateUtil } from "@/utils/dateUtil.js";
import { getUserDetailsByUserId } from "@/api/user.js";
export default {
  data() {
    return {
      userInfo: {
        nick_name: "暂无",
        gender: 1,
        language: "zh_CN",
        city: "Xian",
        province: "Shannxi",
        country: "China",
        avatar_url: "",
        sex: "",
      },
      momentsList: [
        {
          username: "凯尔",
          publishDate: "2019年12月3日",
          photo:
            "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
          content:
            "折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来终结！",
          images: [
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
            },
          ],
          viewCount: 20,
          likeCount: 10,
          commentCount: 5,
          commentList: [
            {
              url:
                "https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png",
              name: "莫甘娜",
              content: "凯尔，你被自己的光芒变的盲目。",
              userId: "1",
              commentTime: "2018年12月4日",
              replyList: [
                {
                  name: "凯尔",
                  content: "妹妹，你在帮他们给黑暗找借口吗?",
                  userId: "",
                  replyTime: "",
                },
              ],
            },
            {
              url:
                "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
              name: "凯尔",
              content: "妹妹，如果不是为了飞翔，我们要这翅膀有什么用?",
              userId: "2",
              commentTime: "2018年12月4日",
              replyList: [],
            },
          ],
        },
      ],
      params: {
        pageNo: 1,
        pageSize: 5,
        order: "",
        userId: "",
      },
      alumnusNum: 0,
      followNum: 0,
      fansNum: 0,
    };
  },
  onLoad(opt) {
    this.params.userId = opt.userId;
    console.log(opt.userId);

    this.getUserDetailsByUserId();
    this.getDiscoverList();
  },
  methods: {
    getUserDetailsByUserId() {
      let params = {
        userId: this.params.userId,
      };
      getUserDetailsByUserId(this.params).then(data => {
        console.log(111, data[1].data.result);
        let res = data[1].data.result;
        this.fansNum = res.fansNum;
        this.followNum = res.followNum;
        this.alumnusNum = res.alumnusNum;
        this.userInfo = res.userInfo;
        console.log(6666666, this.userInfo);
      });
    },
    hrefToPage(type) {
      let url = "";
      if (type === 1) {
        url = "/pages/personal/alumnus/alumnus?userId=" + this.params.userId;
      } else if (type === 2) {
        url =
          "/pages/personal/attention/attention?userId=" + this.params.userId;
      } else {
        url = "/pages/personal/fans/fans?userId=" + this.params.userId;
      }
      uni.navigateTo({
        url: url,
      });
    },
    getDiscoverList() {
      getDiscoverList(this.params).then(data => {
        uni.stopPullDownRefresh();
        let [error, res] = data;
        if (res && res.data && res.data.result) {
          let content = res.data.result.content;
          this.momentsList = this.transformData(content);
        }
      });
    },
    transformData(list) {
      list = list.map(item => {
        return {
          id: item.id,
          username: item.userName,
          publishDate: dateUtil.formatTime(item.createTime),
          photo: item.userPhoto,
          content: item.content,
          images: JSON.parse(item.photos),
          islike: item.islike,
          commentList: item.commentList, //this.listToTree(item.commentList),
          viewCount: item.viewCount,
          commentCount: item.commentCount,
          likeList: item.likeList,
          likeCount: item.likeList.length,
          status: item.status,
        };
      });
      return list;
    },
  },
};
</script>

<style lang="scss">
.dongtai {
  padding: 20rpx;
  font-size: 36rpx;
  background-color: #fff;
  color: #00beb7;
  padding-left: 40rpx;
  span {
    border-bottom: 1px solid #00beb7;
  }
}
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
    height: 500upx;
    padding-top: 200upx;
    background-color: #4191ea;
    // background:url(../../static/personal/background.png);
    background-size: 100% 100%;
  }
}

.avator {
  .cu-avatar.lg {
    width: 160rpx;
    height: 160rpx;
    font-size: 2em;
    top: 10%;
    margin: -80rpx 0 0 40rpx;
  }
  .cu-tag.badge {
    border-radius: 200rpx;
    position: relative;
    top: -30%;
    right: -30%;
    font-size: 38rpx;
    padding: 0rpx 10rpx;
    height: 50rpx;
    width: 50rpx;
    color: #ffffff;
  }
}

.userName {
  display: inline-block;
  position: absolute;
  top: 20%;
  padding: 20rpx;
  color: #fff;
  font-size: 34rpx;
  left: 30%;
}
.fansData {
  color: #fff;
  font-size: 34rpx;
  text-align: center;
  span {
    margin: 0 20rpx;
  }
}
</style>
