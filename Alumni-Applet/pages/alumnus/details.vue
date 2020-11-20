<template>
  <view class="whole_alumnus_details">
    <cu-custom bgColor="bg-gradual-green1" :isBack="true">
      <block slot="content">{{ title }}</block>
    </cu-custom>
    <!-- banner -->
    <view class="al-desc" style="padding-bottom: 0px">
      <swiper
        class="ph-banner screen-swiper square-dot"
        :indicator-dots="true"
        :circular="true"
        :autoplay="true"
        interval="5000"
        duration="500"
      >
        <swiper-item
          class="al-desc-image ph-banner-item"
          v-for="(item, index) in swiperList"
          :key="index"
        >
          <image :src="item.url" v-if="item.type == 'image'"></image>
        </swiper-item>
      </swiper>
      <view class="al-menu cu-list grid col-5 shadow-warp radius">
        <view v-for="(item, index) in menus" :key="index">
          <view class="al-menu-image" :data-cur="item.menu" @click="switchMenu">
            <image :src="item.icon" class="image"></image>
          </view>
          <text>{{ item.txt }}</text>
        </view>
      </view>
    </view>
    <view class="al-desc-title" v-show="showNews">
      <view class="cu-bar bg-white solid-bottom">
        <view class="action">
          <text class="cuIcon-titles text-green1"></text> 公告
        </view>
      </view>
      <listMessage v-bind:lists="newsList"></listMessage>
      <!-- <view class="view_more" @click="switchMenu" data-cur="news">
				<uni-load-more :status="more" :contentText="contentText"></uni-load-more>
			</view> -->
    </view>
    <view class="al-desc-title" v-show="showActivity">
      <view class="cu-bar bg-white solid-bottom">
        <view class="action">
          <text class="cuIcon-titles text-green1"></text> 活动
        </view>
      </view>
      <listActivity v-bind:lists="activityList"></listActivity>
      <!-- <view class="view_more" @click="switchMenu" data-cur="activity">
				<uni-load-more :status="more" :contentText="contentText"></uni-load-more>
			</view> -->
    </view>
    <view class="al-desc-title" v-show="showPhoto">
      <view class="cu-bar bg-white solid-bottom">
        <view class="action">
          <text class="cuIcon-titles text-green1"></text> 动态
        </view>
      </view>
      <view class="discover-content">
        <moments v-bind:list="photoList" :isComment="false"></moments>
      </view>
    </view>
    	<view class="al-desc-title" v-show="showMember">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action action1 peoBox">
					<view class="">
						<text class="cuIcon-titles text-green1"></text>
						<text>成员</text>
					</view>
					<view class="">
						<span v-if="checkState==1"  class="al-desc-title-btn acbtn  round bg-gradual-green1" type="default">待审核</span>
						<span v-else-if="checkState==-1"  class="al-desc-title-btn acbtn  round bg-gradual-green1" type="default">拒绝申请</span>
						<span v-else-if="checkState==0" @click="applyHandler" class="al-desc-title-btn acbtn  round bg-gradual-green1" type="default">申请会长</span>
						
					</view>
				</view>
			</view>
			<list-member v-bind:list="memberList"></list-member>
		</view>
   
    <!-- <view class="view_more" @click="loadMore" data-cur="photo">
      <uni-load-more status="more" :contentText="contentText"></uni-load-more>
    </view> -->
	<suspendMenu :menusList="menusList" :isJoin="isJoin" :showMenu="showMenu" :fid="params.fid"></suspendMenu>
  </view>
</template>

<script>
import listMessage from "@/components/list-message/list-message.vue";
import listActivity from "@/components/list-activity/list-activity.vue";
import listMember from "@/components/list-member/list-member.vue";
import { dateUtil } from "@/utils/dateUtil.js";
import suspendMenu from '@/components/suspend-menu/suspend-menu.vue';
import {
  getAlumnusNewsList,
  getAlumnusActivityList,
  getAlumnusPhotoList,
  gitAlumnusMemberList,
  queryPresidentByUserId,
  applyByUserId
} from "@/api/alumnus.js";
import { getDiscoverListByAlumnusId } from "@/api/discover.js";

export default {
  components: {
    listMessage,
    listActivity,
    listMember,
	suspendMenu
  },
  data() {
    return {
		showMenu:false,
		checkState:0,//申请会长状态默认未申请
		isJoin:0,//是否加入本会
		menusList:[
			{
				id:"gg",
				iconPath:'http://www.imapway.cn/Alumni/static/alumnus/lxr2x.png',
				name:'发公告',
				url:'/pages/alumnus/sendNotice'
			},
			{
				id:"tw",
				iconPath:'http://www.imapway.cn/Alumni/static/alumnus/jj2x.png',
				name:'发图文',
				url:'/pages/discover/publishData/publishData'
			},
			{
				id:"hd",
				iconPath:'http://www.imapway.cn/Alumni/static/alumnus/ly2x.png',
				name:'发活动',
				url:'/pages/alumnus/sendActivity'
			},
			
		],
		  title: "校友会",
		  contentText: {
			contentdown: "显示更多",
			contentrefresh: "正在加载...",
			contentnomore: "没有更多数据了",
		  },
		  newsList: [],
		  activityList: [],
		  photoList: [],
		  memberList: [],
		  meanu: "news", //当前菜单
		  showNews: true,
		  showActivity: true,
		  showPhoto: true,
		  showMember: false,
		  swiperList: [
			{
			  id: 0,
			  type: "image",
			  url: "http://www.imapway.cn/Alumni/static/home/banner12x.png",
			},
			{
			  id: 1,
			  type: "image",
			  url: "http://www.imapway.cn/Alumni/static/home/banner22x.png",
			},
			{
			  id: 2,
			  type: "image",
			  url: "http://www.imapway.cn/Alumni/static/home/banner32x.png",
			},
		  ],
		  menus: [
			// {
			//   icon: "http://www.imapway.cn/Alumni/static/alumnus/jj2x.png",
			//   txt: "简介",
			//   page: "/pages/home/news/news",
			//   menu: "intro",
			// },
			{
			  icon: "http://www.imapway.cn/Alumni/static/alumnus/lxr2x.png",
			  txt: "公告",
			  page: "/pages/home/news/news",
			  menu: "news",
			},
			{
			  icon: "http://www.imapway.cn/Alumni/static/alumnus/cy2x.png",
			  txt: "成员",
			  page: "/pages/home/news/news",
			  menu: "member",
			},
			{
			  icon: "http://www.imapway.cn/Alumni/static/alumnus/xc2x.png",
			  txt: "动态",
			  page: "/pages/schoolHistory/schoolHistory",
			  menu: "photo",
			},
			{
			  icon: "http://www.imapway.cn/Alumni/static/alumnus/ly2x.png",
			  txt: "活动",
			  page: "/pages/teachers/teachers",
			  menu: "activity",
			},
		  ],
		  params: {
			pageNo: 1, // 当前页数
			pageSize: 2, // 每页显示的数据条数
			fid: null, //所属分会ID
		  },
		  pageable: {},
    };
  },
  onShow() {
  	this.initData(this.meanu);
  },
  onLoad(options) {
    // 初始化页面数据
    this.title = options.name;
    this.params.fid = options.id;
    this.initData(this.meanu);
	this.getApplyInfo();
  },
  methods: {
    initData(menu) {
      switch (menu) {
        case "intro":
          this.params.pageSize = 2;
          this.getAlumnusNewsList();
          this.getAlumnusActivityList();
          this.getAlumnusPhotoList();
          this.showNews = true;
          this.showActivity = true;
          this.showPhoto = true;
          this.showMember = false;
          break;
        case "news":
          this.params.pageSize = 10;
          this.getAlumnusNewsList();
          this.showNews = true;
          this.showActivity = false;
          this.showPhoto = false;
          this.showMember = false;
          break;
        case "activity":
          this.params.pageSize = 10;
          this.getAlumnusActivityList();
          this.showNews = false;
          this.showActivity = true;
          this.showPhoto = false;
          this.showMember = false;
          break;
        case "photo":
          this.params.pageSize = 10;
          this.getAlumnusPhotoList();
          this.showNews = false;
          this.showActivity = false;
          this.showPhoto = true;
          this.showMember = false;
          break;
        case "member":
          this.params.pageSize = 10;
          this.gitAlumnusMemberList();
          this.showNews = false;
          this.showActivity = false;
          this.showPhoto = false;
          this.showMember = true;
          break;
        default:
          break;
      }
    },
	applyHandler(){
		let that=this;
		that.params.userId=getApp().getOpenId();
		that.params.alumnusId = this.params.fid;
		that.params.checkState="1";
		applyByUserId(that.params).then(data => {
		  let [error, res] = data;
		  if (res && res.data && res.data.result) {
		    let item = res.data.result;
		    that.checkState=1;
		  }else{
			if(res.data.msg){
				uni.showToast({
				    title: res.data.msg,
					icon:'none',
				    duration: 2000
				});
			}
			
		  }
		});
	},
	//获取会长身份信息
	getApplyInfo(){
		let that=this;
		this.params.userId=getApp().getOpenId();
		this.params.alumnusId = this.params.fid;
		queryPresidentByUserId(this.params).then(data => {
		  let [error, res] = data;
		  if (res && res.data && res.data.success) {
		    let item = res.data.result;
		    that.checkState=item.checkState;
			that.isJoin=item.president;
		  }else{
			  that.isJoin=-1;
		  }
		});
	},
	//加载更多
	loadMore(){
		this.initData(this.currentMenu);
	},
    //切换菜单
    switchMenu(e) {
      let menu = e.currentTarget.dataset.cur;
	  this.currentMenu=menu;
      this.initData(menu);
    },
    getAlumnusNewsList() {
      getAlumnusNewsList(this.params).then(data => {
        let [error, res] = data;
        if (res && res.data && res.data.result) {
          let list = res.data.result.content;
          this.newsList = this.convertData(list);
          if (this.newsList.length == 0) {
            this.showNews = false;
          }
        }
      });
    },
    getAlumnusActivityList() {
      getAlumnusActivityList(this.params).then(data => {
        let [error, res] = data;
        if (res && res.data && res.data.result) {
          let list = res.data.result.content;
          this.activityList = this.convertData(list);
          if (this.activityList.length == 0) {
            this.showActivity = false;
          }
        }
      });
    },
    getAlumnusPhotoList() {
      getDiscoverListByAlumnusId(this.params).then(data => {
        let [error, res] = data;
        if (res && res.data && res.data.result) {
          let list = res.data.result.content;
          this.photoList = this.converMomentsData(list);
          if (this.photoList.length == 0) {
            this.showPhoto = false;
          }
        }
      });
    },
    gitAlumnusMemberList() {
      let openid = uni.getStorageSync("openid");
      this.params.userId = openid;
      this.params.alumnusId = this.params.fid;
      gitAlumnusMemberList(this.params).then(data => {
        let [error, res] = data;
        if (res && res.data && res.data.result) {
          this.memberList = res.data.result.content;
          if (this.memberList.length == 0) {
            this.showMember = false;
          }
        }
      });
    },
    // getAlumnusPhotoById(){
    // 	let params = {
    // 		id: 1
    // 	}
    // 	getAlumnusPhotoById(params).then(data =>{
    // 		let [error, res] = data;
    // 		if(res&&res.data&&res.data.result){
    // 			let obj = res.data.result;
    // 			this.photoList = this.converMomentsData(obj);
    // 		}
    // 	});
    // },
    //构造数据格式
    convertData(list) {
      return list.map((val, index, arr) => {
        let obj = {
          id: val.id,
          avatar: val.img,
          title: val.title,
          excerpt: val.context,
          user_name: val.createBy,
          last_modify_date: dateUtil.formatDate(val.createTime),
        };
        return obj;
      });
    },
    //构造相册数据
    converMomentsData(list) {
      list = list.map(item => {
        var res = {
          id: item.id,
          username: item.userName,
          publishDate: dateUtil.formatDate(item.createTime),
          photo: item.userPhoto,
          content: item.content,
          viewCount: item.viewCount,
          likeCount: item.likeCount,
          commentCount: item.commentCount,
          commentList: this.listToTree(item.commentList),
        };
        if (item.photos) {
          let imgArray = JSON.parse(item.photos);
          res.images = imgArray.map(item => {
            if (item&&item != "") {
              return item;
            }
          });
        }
        return res;
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
  },
};
</script>

<style lang="scss">
.whole_alumnus_details {
  background: #ffffff;
}
.al-desc {
  padding: 10px;
  background: #ffffff;
}

.al-desc-image {
  height: 150px;
}

.screen-swiper {
  min-height: 150px;
}

.al-menu {
  padding: 10px 10px;
  margin: 13px 10px;
  margin-top: -15px;
  box-shadow: 0 0 10rpx rgba(0, 0, 0, 0.5);
  justify-content: center;
}

.al-menu-image > image {
  width: 25px;
  height: 30px;
  text-align: center;
  margin: 0 auto;
  margin-top: 10px;
}

.cu-item {
  padding: 5px 0 5px;
}

.view_more {
  text-align: center;
}
.al-desc-title .peoBox{
	display: flex;
	justify-content: space-between;
	align-items: center;
	width: 100%;
	.acbtn{
		line-height: 50rpx;
		width: 140rpx;
		height: 50rpx;
		font-size: 14px;
		text-align: center;
		display: inline-block;
	}
}
</style>
