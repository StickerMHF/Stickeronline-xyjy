<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="content">首页</block>
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
					<view class="al-menu-image">
						<image :src="item.icon" class="image"></image>
					</view>
					<text>{{item.txt}}</text>
				</view>
			</view>
		</view>
		<view class="al-desc-title">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织资讯
				</view>
			</view>
			<listNews v-bind:lists="newsList"></listNews>
		</view>
		
		<view class="al-desc-title">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织活动
				</view>
			</view>
			<listActivity v-bind:lists="activityList"></listActivity>
		</view>
		<view class="al-desc-title">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织相册
				</view>
			</view>
			<view class="discover-content">
				<moments v-bind:list=""></moments>
			</view>
		</view>
	</view>
</template>

<script>
	import listNews from "@/components/list-news/list-news.vue"
	import listActivity from "@/components/list-activity/list-activity.vue"
	import {dateUtil} from "@/utils/dateUtil.js"
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
				newsList: [],
				activityList:[],
				photoList:[],
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
						page: '/pages/home/news/news'
					},
					{
						icon: '/static/home/xqzt2x.png',
						txt: '资讯',
						page: '/pages/home/news/news'
					},
					{
						icon: '/static/home/xqzt2x.png',
						txt: '成员',
						page: '/pages/home/news/news'
					},
					{
						icon: '/static/home/ysjs2x.png',
						txt: '相册',
						page: '/pages/schoolHistory/schoolHistory'
					},
					{
						icon: '/static/home/szll2x.png',
						txt: '活动',
						page: '/pages/teachers/teachers'
					}
				],
				params: {
						pageNo: 1,// 当前页数
						pageSize: 3,// 每页显示的数据条数
						fid: null  //所属分会ID
					},
				pageable: {}
			}			
		},
		onLoad(options) {
			// 初始化页面数据
			console.log(options);
			this.params.fid = options.id;
			// this.getAlumnusNewsList();
			this.initData();
		},
		methods: {
			initData(){
				//获取资讯列表
				this.getAlumnusNewsList();
				//获取活动列表
				this.getAlumnusActivityList();
			},
			getAlumnusNewsList(){
				getAlumnusNewsList(this.params).then(data=>{
					console.log(data)
					let [error, res] = data;
					if(res&&res.data&&res.data.result){
						let list = res.data.result.content;
						this.newsList = this.convertData(list);
					}					
				})
			},
			getAlumnusActivityList(){
				getAlumnusActivityList(this.params).then(data => {
					let [error, res] = data;
					if(res&&res.data&&res.data.result){
						let list = res.data.result.content;
						this.activityList = this.convertData(list);
					}
				});
			},
			//构造数据格式
			convertData(list){
				return list.map((val, index, arr)=>{
					let obj = {
						"id": val.id,
						"avatar": val.img,
						"title": val.title,
						"excerpt": val.context,
						"user_name": val.author,
						"last_modify_date": dateUtil.formatDate(val.createTime)
					};							
					return obj;
				});
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
</style>
