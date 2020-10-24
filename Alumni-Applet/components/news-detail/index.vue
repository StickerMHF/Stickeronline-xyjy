<template>
  <div class="hm-news-detail">
    <div class="hd">
      <img class="yunshu" :src="options.yunshu?options.yunshu:yunshu" />
      <div class="container">
        <div class="outer">
          <span class="author">{{ options.createBy }}</span>
          <div class="wrap">
            <img class="like" :src="like" />
            <img class="share" :src="share" />
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
          yunshu:
            'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_0.png',
          createBy: '天邦科技',
          like:
            'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_1.png',
          share:
            'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_2.png',
          createTime: 1603468800000,
          title: '如何用 Python 在笔记本上分析100GB 数据',
          thumb:
            "['http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_3.png']",
          contents:
            '第 1 种是对数据进行子抽样，但它有一个明显缺点：可能因忽略部分数据而错失关键信息，甚至误解数据表达的含义。第 2 种是使用分布式计算。虽然在某些情况下这是一种有效的方法，但是管理和维护集群会带来巨大开销。想象一下，要为一个刚超出内存大小、大概 30-50GB 的数据集就建立一套集群，对我来说，这似乎有点“用力过猛”。'
        };
      }
    }
  },
  data() {
    return {
		yunshu:'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_0.png',
		like:'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_1.png',
		share:'http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_2.png',
		thumb:
		  "['http://www.imapway.cn/Alumni/static/hm-news-detail/images/img_22946_0_3.png']"
	};
  },
  methods: {
   formatDate(data){
	   return getApp().formatDate(data);
   }
  }
};
</script>
<style>
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
</style>
