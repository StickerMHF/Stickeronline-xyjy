<template>
		<view class="ann_news_list" style="background-image: url(http://www.imapway.cn/Alumni/static/anniversary/xqzx.png1);">
			<view class="ann_news_top shadow-warp" >
				<view class="ann_news_top_item" v-for="(item,index) in top" :key="index">
					<navigator :url="'/pages/home/newsDetail/newsDetail?id='+item.id" class="newsBox">
						<view class="ann_news_div">
							<view class="ann_news_top_icon" style="background-image: url(http://www.imapway.cn/Alumni/static/images/dingzhi2x.png);width: 15px;height: 15px;"></view>
						</view>
						<span class="ann_news_top_title">{{item.title}}</span>
					</navigator>
				</view>
			</view>
			<view class="" v-for="(news,index) in list" :key="index">
				<navigator :url="'/pages/home/newsDetail/newsDetail?id='+news.id">
				<newsItem :opts="news" ></newsItem>
				</navigator>
			</view>
			
		</view>
</template>

<script>
	import newsItem from "@/components/news-list/news-item.vue"
	export default {
		components: {
		newsItem
		},
		props: {
			newsList: {
				type: Array,
				default: function() {
					return [{
					thumb: '["http://www.imapway.cn/Alumni/static/home/banner22x.png"]',
					title: '学生工作业务能力专题培训举办',
					contents: '我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。',
					createTime: '2020-10-18',
					viewCount: 23,
					istop:0
				},{
					thumb: '["http://www.imapway.cn/Alumni/static/home/banner32x.png"]',
					title: '正义天使 凯尔',
					contents: '',
					createTime: '2020-10-18',
					viewCount: 23,
					istop:0
				},{
					thumb: '["http://www.imapway.cn/Alumni/static/home/banner32x.png"]',
					title: '正义天使 凯尔',
					contents: '',
					createTime: '2020-10-18',
					viewCount: 23,
					istop:1
				},{
					thumb: '["http://www.imapway.cn/Alumni/static/home/banner32x.png"]',
					title: '正义天使 凯尔',
					contents: '',
					createTime: '2020-10-18',
					viewCount: 23,
					istop:1
				}];
				}
			}
		},
		data() {
			return {
				list:[],
				top:[{
					thumb: '["http://www.imapway.cn/Alumni/static/home/banner22x.png"]',
					title: '学生工作业务能力专题培训举办',
					contents: '我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。',
					createTime: '2020-10-18',
					viewCount: 23
				}, {
					thumb: '["http://www.imapway.cn/Alumni/static/home/banner32x.png"]',
					title: '正义天使 凯尔',
					contents: '',
					createTime: '2020-10-18',
					viewCount: 23
				}]
			}
		},
		watch:{
			newsList(){
				this.formatData();
			}
		},
		mounted() {
			this.formatData();
		},
		methods: {
			formatData(){
				let that=this;
				that.top=[];
				that.list=[];
				this.newsList.forEach(news =>{
					if(news.istop==1){
						that.top.push(news);
					}else{
						that.list.push(news);
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
.ann_news_list{
	width: 100%;
	height: 100%;
	background: white;
}
.ann_news_top{
	margin: 10px;
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
}
.ann_news_top_item{
	display: flex;
	line-height: 35px;
	padding: 0 10px;
	.newsBox{
		display: flex;
		align-items: center;
	}
	.ann_news_div {
		width: 20px;
		height: 35px;
		line-height: 35px;
		display: flex;
		align-items: center;
		.ann_news_top_icon{
			width: 100%;
			height:100%;
			background-size: 100% 100%;
		}
	}
	
	.ann_news_top_title{
		// display: flex;
		width:660rpx;
		overflow:hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
}
</style>
