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
			<listNews></listNews>
		</view>
		
		<view class="al-desc-title">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织活动
				</view>
			</view>
			<listNews></listNews>
		</view>
		<view class="al-desc-title">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 组织相册
				</view>
			</view>
			<view class="discover-content">
				<moments ></moments>
			</view>
		</view>
	</view>
</template>

<script>
	import listNews from "@/components/list-news/list-news.vue"
	export default {
		components: {
			listNews
		},
		data() {
			return {
				lists: [{
					"id": '123',
					"avatar": "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
					"title": "创业路上的“格桑”姐妹",
					"excerpt": "白朗县位于西藏日喀则市，是西藏主要蔬菜生产基地之一。今年26岁的藏族女孩格桑旺姆是该县巴扎乡彭仓村人，2018年从西藏职业技术学院毕业后返乡创业，和姐姐德吉卓玛一起进行果蔬种植。2019年，在政府援藏资金的帮扶下，姐妹俩通过土地置换在彭仓村建成13个温室大棚，经过引进果蔬新品种和技术积累，实现大棚种植收入大幅提升。同年8月，格桑旺姆申领了大学生创业补贴资助，两人又在白朗县城开了一家蔬菜销售店。姐姐管理大棚、培训雇工，妹妹负责果蔬销售和店铺运营，两人密切合作，事业蒸蒸日上。",
					"user_name": "sticker",
					"last_modify_date": "2020-10-19 12:12:11"
				},{
					"id": '12',
					"avatar": "http://www.sznews.com/photo/pic/2020-08/14/7111edd5-c869-4ba1-a413-0fae37bbbf19.jpg",
					"title": "奇观天下|走进非洲最高山峰下的野生大象家园",
					"excerpt": "肯尼亚东南部，与坦桑尼亚接壤的安博赛利国家公园，距内罗毕240公里，因水草丰美而成为野生非洲大象的乐园。公园面积392平方公里，邻近世界上距赤道最近的、终年积雪的乞力马扎罗雪山。",
					"user_name": "sticker",
					"last_modify_date": "2020-10-19 12:12:11"
				}], 
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
			}
		},
		onLoad(options) {
			// 初始化页面数据
			console.log(options);
		},
		methods: {

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
