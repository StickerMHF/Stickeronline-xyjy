<template>
  <div class="hm-news-detail">
    <div class="hd">
      <img class="yunshu" :src="options.yunshu?options.yunshu:yunshu" />
      <div class="container">
        <div class="outer">
          <span class="author">{{ options.createBy }}</span>
          <div class="wrap">
            <view class="like  hm-btn" :class="islike?'cuIcon-likefill':'cuIcon-like'"  @click="likeHandler"></view>
			<!-- <button open-type="share">s </button> -->
            <view class="share cuIcon-share hm-btn"  @click="shareHandler"></view>
          </div>
        </div>
        <span class="time">{{ formatDate(options.createTime) }}</span>
      </div>
    </div>
    <view class="news-title">{{ options.title }}</view>
  <!--  <div class="main" v-for="photo in photos">
      <div class="entryPicWrap">
		  <image class="img" :src="photo" mode="aspectFill"></image>
		  </div>
    </div> -->
    <div class="ft">
      <view class="new-content" v-html="options.contents"></view>
    </div>
  </div>
</template>
<script>
export default {
  name: 'HmNewsDetail',
  props: {
    dataId: {
      type: String,
      default: 'hm-news-detail'
    },
	photos: {
	  type: Array,
	  default: []
	},
    options: {
      type: Object,
      default: function() {
        return {
          createBy: "",
          createTime: 1603468800000,
          title: "",
          thumb:"[]",
          contents:""
        };
      }
    }
  },
  data() {
    return {
		yunshu:"http://cdxyh.stickeronline.cn/logo.jpg",
		like:"http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_1.png",
		share:"http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_2.png",
		thumb:"",
		islike:false
	};
  },
  methods: {
   formatDate(data){
	   return getApp().formatDate(data);
   },
   likeHandler(){
	   this.islike=!this.islike;
	   this.$emit("likeHandler");
   },
   shareHandler(){
	   this.$emit("shareHandler");
   }
  }
};
</script>
<style scoped>
@import './index.response.css';
.new-content{
	width: 100%;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    line-height: 62rpx;
	    letter-spacing: 0px;
	    white-space: pre-wrap;
	    color: #000000;
	    font-size: 36rpx;
	    font-weight: 400;
		padding: 0 20px;
}
.news-title{
	font-size: 24px;
	    font-weight: bold;
	    text-align: center;
	    padding: 10px 30px;
}
.hm-btn{
	color: #ff8901;
}
.hd{
	min-height: 40px;
}
</style>
