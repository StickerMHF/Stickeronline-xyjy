<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="content">校友统计</block>
		</cu-custom>
		<!-- banner -->
		<view class="al-desc" style="padding-bottom: 0px;">
			<swiper class="ph-banner screen-swiper square-dot" :indicator-dots="true" :circular="true" :autoplay="true" interval="5000"
			 duration="500">
				<swiper-item class="al-desc-image ph-banner-item" v-for="(item,index) in swiperList" :key="index">
					<image :src="item.url" v-if="item.type=='image'"></image>
				</swiper-item>
			</swiper>
			<view class="al-menu cu-list grid col-5">
				<view v-for="(item,index) in menus" :key="index">
					<view class="al-menu-image" :data-cur="item.menu" @click="switchMenu">
						<image :src="item.icon" class="image"></image>
					</view>
					<text>{{item.txt}}</text>
				</view>
			</view>
		</view>
		<view class="al-desc-title" v-show="showNews">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织资讯
				</view>
			</view>
			<listNews v-bind:lists="newsList"></listNews>
			<view class="view_more" @click="switchMenu" data-cur="news">
				<uni-load-more :status="more" :contentText="contentText"></uni-load-more>
			</view>
		</view>
		<view class="al-desc-title" v-show="showActivity">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织活动
				</view>
			</view>
			<listActivity v-bind:lists="activityList"></listActivity>
			<view class="view_more" @click="switchMenu" data-cur="activity">
				<uni-load-more :status="more" :contentText="contentText"></uni-load-more>
			</view>
		</view>
		<view class="al-desc-title" v-show="showPhoto">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织相册
				</view>
			</view>
			<view class="discover-content">
				<moments v-bind:list="photoList"></moments>
			</view>
			<view class="view_more" @click="switchMenu" data-cur="photo">
				<uni-load-more :status="more" :contentText="contentText"></uni-load-more>
			</view>
		</view>
	</view>
</template>

<script>
	import listNews from "@/components/list-news/list-news.vue"
	import listActivity from "@/components/list-activity/list-activity.vue"
	import {
		dateUtil
	} from "@/utils/dateUtil.js"
	import {
		getAlumnusNewsList,
		getAlumnusActivityList,
		getAlumnusPhotoList
	} from '@/api/alumnus.js'
	export default {
		components: {
			listNews,
			listActivity
		},
		data() {
			return {
				contentText: {
					contentdown: "显示更多",
					contentrefresh: "正在加载...",
					contentnomore: "没有更多数据了"
				},
				newsList: [],
				activityList: [],
				photoList: [],
				meanu: 'intro', //当前菜单
				showNews: true,
				showActivity: true,
				showPhoto: true,
				swiperList: [{
					id: 0,
					type: 'image',
					url: '/static/home/banner12x.png'
				}, {
					id: 1,
					type: 'image',
					url: '/static/home/banner22x.png',
				}, {
					id: 2,
					type: 'image',
					url: '/static/home/banner32x.png'
				}],
				menus: [{
						icon: '/static/home/dcxw2x.png',
						txt: '简介',
						page: '/pages/home/news/news',
						menu: 'intro'
					},
					{
						icon: '/static/home/xqzt2x.png',
						txt: '资讯',
						page: '/pages/home/news/news',
						menu: 'news'
					},
					{
						icon: '/static/home/xqzt2x.png',
						txt: '成员',
						page: '/pages/home/news/news',
						menu: 'member'
					},
					{
						icon: '/static/home/ysjs2x.png',
						txt: '相册',
						page: '/pages/schoolHistory/schoolHistory',
						menu: 'photo'
					},
					{
						icon: '/static/home/szll2x.png',
						txt: '活动',
						page: '/pages/teachers/teachers',
						menu: 'activity'
					}
				],
				params: {
					pageNo: 1, // 当前页数
					pageSize: 2, // 每页显示的数据条数
					fid: null //所属分会ID
				},
				pageable: {}
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.params.fid = options.id;
			this.initData(this.meanu);
		},
		methods: {
			initData(menu) {
				switch (menu) {
					case 'intro':
						this.params.pageSize = 2;
						this.getAlumnusNewsList();
						this.getAlumnusActivityList();
						this.getAlumnusPhotoList();
						this.showNews = true;
						this.showActivity = true;
						this.showPhoto = true;
						break;
					case 'news':
						this.params.pageSize = 10;
						this.getAlumnusNewsList();
						this.showNews = true;
						this.showActivity = false;
						this.showPhoto = false;
						break;
					case 'activity':
						this.params.pageSize = 10;
						this.getAlumnusActivityList();
						this.showNews = false;
						this.showActivity = true;
						this.showPhoto = false;
						break;
					case 'photo':
						this.params.pageSize = 10;
						this.getAlumnusPhotoList();
						this.showNews = false;
						this.showActivity = false;
						this.showPhoto = true;
						break;
					case 'member':
						// this.showNews = false;
						// this.showActivity = false;
						// this.showPhoto = false;
						break;
					default:
						break;
				}
			},
			//切换菜单
			switchMenu(e) {
				let menu = e.currentTarget.dataset.cur;
				this.initData(menu);
			},
			getAlumnusNewsList() {
				getAlumnusNewsList(this.params).then(data => {
					let [error, res] = data;
					if (res && res.data && res.data.result) {
						let list = res.data.result.content;
						this.newsList = this.convertData(list);
					}
				})
			},
			getAlumnusActivityList() {
				getAlumnusActivityList(this.params).then(data => {
					let [error, res] = data;
					if (res && res.data && res.data.result) {
						let list = res.data.result.content;
						this.activityList = this.convertData(list);
					}
				});
			},
			getAlumnusPhotoList() {
				getAlumnusPhotoList(this.params).then(data => {
					let [error, res] = data;
					if (res && res.data && res.data.result) {
						let list = res.data.result.content;
						this.photoList = this.converMomentsData(list);
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
						"id": val.id,
						"avatar": val.img,
						"title": val.title,
						"excerpt": val.context,
						"user_name": val.createBy,
						"last_modify_date": dateUtil.formatDate(val.createTime)
					};
					return obj;
				});
			},
			//构造相册数据
			converMomentsData(list) {
				list = list.map(item => {
					var res = {
						username: item.author,
						publishDate: dateUtil.formatDate(item.createTime),
						photo: item.photo,
						content: item.context,
						viewCount: item.viewCount,
						likeCount: item.likeCount,
						commentCount: item.commentCount
					}
					if (item.imgs) {
						let imgArray = item.imgs.split(";");
						res.images = imgArray.map(item => {
							if (item != "") {
								return {
									url: item
								}
							}
						});
					}
					return res;
				});
				return list;
			}
		}
	}
</script>

<style lang="scss">
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
		padding: 10px 30px;
	}

	.al-menu-image>image {
		width: 30px;
		height: 30px;
	}

	.cu-item {
		padding: 5px 0 5px;
	}

	.view_more {
		text-align: center;
	}
</style>
