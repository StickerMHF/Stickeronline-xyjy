<template>
  <view class="dynamicBox">
    <!-- <view class="publishData">
			<image class="image" src="http://cdxyh.stickeronline.cn/FhDX9UB6L_r8YaQ6gqewXMPBCIqG" @click="navigatorTo"></image>
		</view> -->
    <view
      class="cu-card dynamic"
      :class="isCard ? 'no-card' : ''"
      v-for="(moment, i) in listArray"
      :key="i"
    >
      <navigator :url="'/pages/home/newsDetail/newsDetail?id=' + moment.id">
        <view class="cu-item shadow">
          <view class="cu-list menu-avatar">
            <view class="cu-item">
              <view
                class="cu-avatar round lg"
                :style="'background-image:url(' + moment.photo + ');'"
              ></view>
              <view class="content flex-sub">
                <view>{{ moment.username }}</view>
                <view class="text-gray text-sm flex justify-between">
                  {{ moment.publishDate }}
                </view>
              </view>
            </view>
          </view>
          <view class="text-content details-list">
            {{ moment.content }}
          </view>
          <view
            class="grid flex-sub padding-lr details-list"
            :class="isCard ? 'col-3 grid-square' : 'col-1'"
          >
            <view
              class="bg-img"
              :class="isCard ? '' : 'only-img'"
              :style="'background-image:url(' + item.url + ');'"
              v-for="(item, index) in moment.images"
              :key="index"
            >
            </view>
          </view>
          <view class="text-gray text-sm text-right padding comment_icon">
            <text class="cuIcon-attentionfill margin-lr-xs"></text>
            {{ moment.viewCount }}
          </view>
        </view>
      </navigator>
    </view>
  </view>
</template>

<script>
import { momentLike, momentComment } from "@/api/discover.js";
export default {
  name: "moments",
  data() {
    return {
      commentShow: false,
      ifshow: false,
      commentText: "",
      isCard: true,
      likeParams: {
        momentId: "",
        userId: "",
        userName: "",
        userPhoto: "",
        status: "unlike",
      },
      commentParams: {
        content: "",
        momentId: "",
        userId: "",
        userPhoto: "",
        userName: "",
        fid: "-1",
      },
      listArray: [],
      inputValue: "",
      nowComment: "",
    };
  },
  watch: {
    list() {
      this.listArray = this.list;
    },
  },
  props: {
    // fatherLikeMethod: {
    // 	type: Function,
    // 	default: null
    // },
    // fatherCommentMethod: {
    // 	type: Function,
    // 	dafult: null
    // },
    list: {
      type: Array,
      default: function (e) {
        return [];
      },
    },
  },
  methods: {
    //点击查看大图
    clickPic(imgPreviewPicList, index) {
      uni.removeStorageSync("imgPreviewPicList");
      uni.removeStorageSync("currentImgIndex");
      uni.setStorageSync("currentImgIndex", index);
      uni.setStorageSync("imgPreviewPicList", imgPreviewPicList);
      uni.navigateTo({
        url: "/pages/imgPreview/imgPreview",
      });
    },
    bindBlurEvent() {
      console.log("失去焦点");
      this.commentShow = false;
    },
    bindFocusEvent() {
      console.log("获得焦点");
    },
    //点赞操作
    momentLike(index) {
      if (this.list[index].status == "like") {
        this.list[index].status = "unlike";
        this.list[index].likeCount = this.list[index].likeCount - 1;
        this.likeParams.status = "unlike";
      } else {
        this.list[index].status = "like";
        this.list[index].likeCount = this.list[index].likeCount + 1;
        this.likeParams.status = "like";
      }
      //提交数据
      // if(this.fatherLikeMethod){
      this.likeParams.momentId = this.list[index].id;
      //获取用户信息
      this.likeParams.userId = uni.getStorageSync("openid");
      let userInfo = uni.getStorageSync("userInfo");
      if (userInfo) {
        this.likeParams.userName = userInfo.nickName;
        this.likeParams.userPhoto = userInfo.avatarUrl;
        // this.fatherLikeMethod(this.likeParams);
        momentLike(this.likeParams).then(data => {
          console.log(data);
        });
      } else {
        //跳转页面
        wx.navigateTo({
          url: "/pages/login/login",
        });
      }
      // }
    },
    //相册评论
    commentInput(index) {
      this.commentParams.momentId = this.list[index].id;
      this.nowComment = this.list[index].id;
      //获取用户信息
      this.commentParams.userId = uni.getStorageSync("openid");
      let userInfo = uni.getStorageSync("userInfo");
      if (userInfo) {
        this.commentParams.userName = userInfo.nickName;
        this.commentParams.userPhoto = userInfo.avatarUrl;
        this.commentShow = true;
        // this.$refs.commentdom.focus();
      } else {
        //跳转页面
        wx.navigateTo({
          url: "/pages/login/login",
        });
      }
    },
    //评论的评论
    commentInput2(comment) {
      this.commentParams.momentId = comment.momentId;
      this.commentParams.fid = comment.id;
      //获取用户信息
      this.commentParams.userId = uni.getStorageSync("openid");
      let userInfo = uni.getStorageSync("userInfo");
      if (userInfo) {
        this.commentParams.userName = userInfo.nickName;
        this.commentParams.userPhoto = userInfo.avatarUrl;
        this.commentShow = true;
        // this.$refs.commentdom.focus();
      } else {
        //跳转页面
        wx.navigateTo({
          url: "/pages/login/login",
        });
      }
    },
    // sumbitComment(){
    // 	if(this.fatherCommentMethod){
    // 		this.commentParams.content = this.commentText;
    // 		this.fatherCommentMethod(this.commentParams);
    // 	}
    // },
    //发表评论
    sumbitComment() {
      // if(this.fatherCommentMethod){
      this.commentParams.content = this.commentText;
      // this.fatherCommentMethod(this.commentParams);
      momentComment(this.commentParams).then(data => {
        this.commentShow = false;
        this.commentText = "";
        //获取当前评论数据
        var fid = this.commentParams.momentId;
        var that = this;
        var [error, res] = data;
        if (res && res.data && res.data.result) {
          if (res.data.result.status == 1) {
            debugger;
            this.listArray.forEach(function (val, index, arr) {
              if (fid == val.id) {
                that.listArray[index].commentList.push({
                  name: that.commentParams.userName,
                  content: that.commentParams.content,
                  userId: that.commentParams.userId,
                  replyTime: new Date(),
                  fid: that.commentParams.fid,
                  url: that.commentParams.userPhoto,
                });
              }
            });
          }
        }
      });

      // }
    },
  },
};
</script>

<style lang="scss">
.dynamicBox {
  background: #fff;
}
.details-list {
  margin-top: 10px;
  padding: 0;
  .bg-img {
    margin-right: 12rpx !important;
    margin-bottom: 12rpx !important;
  }
}
.text-title {
  font-weight: bold;
}
.details-list {
  // width: 340px;
  margin-left: 50px;
}
.comment_icon {
  margin-right: 10px;
}
.discover-comment {
  position: fixed;
  z-index: 999;
  bottom: 0;
  background: #f0f3f2;
  width: 100%;
}

.active {
  color: red;
}

.comment-input {
  width: 80%;
  float: left;
  height: 45px;
  size: 10px;
  font-size: 16px;
  margin-left: 10px;
}

.weui-cell_input {
  height: 100%;
}
.comment_icon {
  font-size: 15px;
  padding: 5px;
}
// .publishData{
// 	    position: absolute;
// 	    z-index: 99;
// 		right: 10px;
// 		margin-top: 5px;
// }
// .image{
// 	height: 40px;
// 	width: 40px;
// }
</style>
