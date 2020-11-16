<template>
  <view style="overflow-x: hidden">
    <cu-custom bgColor="bg-gradual-green1" :isBack="false">
      <block slot="backText">返回</block>
      <block slot="content">
        <ul class="discover-ul">
          <li
            class="discover-li"
            :class="currentSelect == '1' ? 'discover-li-cur' : 'discoverLi'"
            @click="selectHandler('1')"
          >
            <span>推荐</span>
			<text class="lg text-gray"></text>
            <!-- <text class="lg text-gray cuIcon-triangleupfill"></text> -->
            <!-- <text v-if="currentSelect=='2'" class="lg text-gray cuIcon-title" style="color: #f85c0e;"></text> -->
          </li>
          <li
            class="discover-li"
            :class="currentSelect == '2' ? 'discover-li-cur' : ''"
            @tap="selectHandler('2')"
          >
            <span>关注</span>
          </li>
        </ul>
      </block>
    </cu-custom>
    <view class="discover-group" v-if="isSelect">
      <view class="cu-bar bg-white solid-bottom">
        <view class="action">
          <text class="cuIcon-title text-blue"></text>默认分组
        </view>
      </view>
      <view class="padding bg-white solid-bottom">
        <view
          :class="
            item.active
              ? 'cu-tag radius attention active'
              : 'cu-tag radius attention'
          "
          v-for="(item, i) in attentionList"
          :key="i"
          >{{ item.name }}</view
        >
      </view>
    </view>
    <view class="publishData">
      <image
        class="image"
        src="http://cdxyh.stickeronline.cn/FhDX9UB6L_r8YaQ6gqewXMPBCIqG"
        @click="navigatorTo"
      ></image>
    </view>
    <view class="discover-content">
      <moments
        ref="moments"
        :list="momentsList"
        @comment="commentHandler"
      ></moments>
    </view>
    <view class="discover-comment">
      <ygc-comment
        ref="ygcComment"
        :placeholder="'发布评论'"
        @pubComment="pubComment"
      ></ygc-comment>
    </view>
  </view>
</template>

<script>
import ygcComment from "@/components/ygc-comment/ygc-comment.vue";
import moments from "@/components/moments/moments.vue";
import { getDiscoverList, momentLike, momentComment } from "@/api/discover.js";
import { dateUtil } from "@/utils/dateUtil.js";
import comment from "@/components/comment/comment.vue";
var _self = "";
export default {
  components: {
    // moments
    ygcComment,
    comment,
  },
  data() {
    return {
      // commentShow: true,
      cWidth: "",
      cHeight: "",
      pixelRatio: 1,
      canvaMap: null,
      currentfromSelect: "1",
      currentSelect: "2",
      isSelect: false,
      commentParams: {},
      attentionList: [
        {
          name: "道路桥梁与渡河工程",
          active: true,
        },
        {
          name: "车辆工程",
          active: false,
        },
        {
          name: "能源与动力工程",
          active: true,
        },
        {
          name: "物流工程",
          active: false,
        },
        {
          name: "汽车服务工程",
          active: true,
        },
        {
          name: "机械工程",
          active: false,
        },
        {
          name: "工商管理",
          active: true,
        },
        {
          name: "市场营销",
          active: true,
        },
        {
          name: "会计学",
          active: true,
        },
        {
          name: "工程管理",
          active: true,
        },
        {
          name: "信息管理与信息系统",
          active: true,
        },
        {
          name: "经济统计学",
          active: true,
        },
        {
          name: "国际经济与贸易",
          active: true,
        },
        {
          name: "电气工程及其自动化",
          active: true,
        },
        {
          name: "机器人工程",
          active: true,
        },
        {
          name: "电子信息工程",
          active: true,
        },
      ],
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
        order: ""
      },
    };
  },
  onShow() {
    //获取用户ID
    // this.params.userId = uni.getStorageSync("openid");

    // if(!this.params.userId || this.params.userId == ''){
    // 	wx.navigateTo({
    // 		url:'/pages/login/login'
    // 	})
    // }
    //获取朋友圈列表
    this.getDiscoverList();
  },
  bindFormSubmit: function (e) {
    console.log(e.detail.value.textarea);
  },
  onPullDownRefresh() {
    this.getDiscoverList();
  },
  mounted() {
    // that = this;
  },
  methods: {
    //发表评论
    pubComment(value) {
      var that = this;
      this.commentParams.content = value.content;
      this.commentParams.momentId = this.currentMomentId;
      this.$refs.ygcComment.toggleMask("hide");
      // this.fatherCommentMethod(this.commentParams);
      momentComment(this.commentParams).then(data => {
        this.commentText = "";
        //获取当前评论数据
        var fid = this.commentParams.momentId;
        var [error, res] = data;
        if (res && res.data && res.data.result) {
          if (res.data.result.status == 1) {
            this.momentsList.forEach(function (val, index, arr) {
              if (fid == val.id) {
                that.momentsList[index].commentList.push({
                  userName: that.commentParams.userName,
                  content: that.commentParams.content,
                  userId: that.commentParams.userId,
                  replyTime: new Date(),
                  fid: that.commentParams.fid,
                  userPhoto: that.commentParams.userPhoto,
                });
              }
            });
          }
        }
      });
    },
    //评论按钮事件
    commentHandler(id) {
      this.currentMomentId = id;
      //获取用户信息
      this.commentParams.userId = uni.getStorageSync("openid");
      let userInfo = uni.getStorageSync("userInfo");
      if (userInfo) {
        this.commentParams.userName = userInfo.nickName;
        this.commentParams.userPhoto = userInfo.avatarUrl;
        this.$refs.ygcComment.toggleMask("show");
      } else {
        //跳转页面
        wx.navigateTo({
          url: "/pages/login/login",
        });
      }
    },

    //点赞
    // likeClick(params){
    // 	momentLike(params).then(data => {
    // 		console.log(data)
    // 	});
    // },
    // commentMethod(params){
    // 	momentComment(params).then(data => {
    // 		console.log(data);
    // 		this.commentShow = false;
    // 		this.commentText = '';
    // 	});
    // },
    selectHandler(value) {
      this.currentSelect = value;
      if (this.currentSelect == "1") {
        // if (this.currentSelect == this.currentfromSelect) {
        // 	this.isSelect = !this.isSelect;
        // }
        this.currentfromSelect = value;
        this.params.order = "likeCount";
      }
      if (this.currentSelect == "2") {
        // this.isSelect = false;
        this.currentfromSelect = value;
        this.params.order = "";
      }
      this.getDiscoverList();
    },
    //获取朋友圈列表
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
          userId: item.userId,
        };
      });
      return list;
    },
    listToTree(list) {
      var data = list.map(item => {
        return {
          id: item.id,
          url: item.userPhoto,
          name: item.userName,
          content: item.content,
          userId: item.userId,
          commentTime: dateUtil.formatTime(item.createTime),
          parent: item.fid,
          momentId: item.momentId,
        };
      });

      var tree = [];
      var dataMap = data.reduce(function (map, node) {
        map[node.id] = node;
        return map;
      }, {});
      data.forEach(function (node) {
        var parent = dataMap[node.parent];
        if (parent) {
          (parent.replyList || (parent.replyList = [])).push(node);
        } else {
          tree.push(node);
        }
      });
      return tree;
    },
    navigatorTo() {
		let certification=getApp().getIsCertification();
		if(certification){
			uni.navigateTo({
			  url: "/pages/discover/publishData/publishData",
			});
		}else{
			uni.showToast({
				title: "请进行校友认证",
				icon: 'none',
				duration: 2000
			});
			uni.navigateTo({
			  url: "/pages/personal/basicInfo/certification",
			});
		}
		
      
    },
    onConfirm(value) {
      console.log(value);
    },
  },
};
</script>

<style scoped>
.attention {
  margin: 5px 5px 5px 5px;
}

.attention.active {
  color: darkorange;
}

.qiun-charts {
  width: 750upx;
  height: 750upx;
  background-color: #ffffff;
}

.charts {
  width: 750upx;
  height: 750upx;
  background-color: #ffffff;
}

.discover-ul {
  white-space: nowrap;
  list-style: none;
}

.discover-li {
  display: inline-block;
  margin: 0 0.75rem;
}

.discover-li span {
  border-bottom: 2px solid #ff5b3600;
  margin: 0px -5px;
  line-height: 30px;
  padding-left: 5px;
}

.discover-li-cur span {
  border-bottom: 2px solid #ff5b36;
}

/* .discover-li-cur text {
  padding-left: 5px;
}
discoverLi text {
	padding-left: 5px;
} */
.discover-group {
  position: absolute;
  width: 100%;
  z-index: 1;
  background: white;
}
.publishData {
  position: fixed;
  z-index: 99;
  right: 10px;
  bottom: 20rpx;
}
.image {
  height: 40px;
  width: 40px;
}
.discover-comment {
  /* 	position: absolute;
		top: 0rpx;
		bottom: 0;
		width: 100%;
		background: #00008800; */
}
</style>
