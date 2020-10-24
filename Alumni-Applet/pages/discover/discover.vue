<template>
	<view style="overflow-x: hidden;">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">
				<ul class="discover-ul">
					<li class="discover-li discover-li-cur" @click="selectHandler('1')"><span>关注</span>
						<text v-if="currentSelect=='1'" class="lg text-gray " :class="isSelect?'cuIcon-triangleupfill':'cuIcon-triangledownfill'"></text>
						<text v-if="currentSelect=='2'" class="lg text-gray cuIcon-title" style="color: #f85c0e;"></text>
					</li>
					<li class="discover-li" @tap="selectHandler('2')"><span>推荐</span></li>
				</ul>
			</block>
		</cu-custom>
		<view class="discover-group " v-if="isSelect">
			<view class="cu-bar bg-white solid-bottom">
				<view class='action'>
					<text class='cuIcon-title text-blue'></text>默认分组
				</view>
			</view>
			<view class="padding bg-white solid-bottom">
				<view :class="item.active?'cu-tag radius attention active':'cu-tag radius attention'" v-for="(item, i) in attentionList">{{item.name}}</view>
			</view>
		</view>
		<view class="discover-content">
			<moments :list="momentsList"></moments>
		</view>
	</view>
</template>

<script>	
	import moments from '@/components/moments/moments.vue';
	import {getDiscoverList} from '@/api/discover.js'
	import {dateUtil } from "@/utils/dateUtil.js"
	var _self = '';
	export default {
		data() {
			return {
				cWidth: '',
				cHeight: '',
				pixelRatio: 1,
				canvaMap: null,
				currentfromSelect: '1',
				currentSelect: '1',
				isSelect: false,
				attentionList: [{
						name: "道路桥梁与渡河工程",
						active: true
					},
					{
						name: "车辆工程",
						active: false
					}, {
						name: "能源与动力工程",
						active: true
					}, {
						name: "物流工程",
						active: false
					}, {
						name: "汽车服务工程",
						active: true
					}, {
						name: "机械工程",
						active: false
					}, {
						name: "工商管理",
						active: true
					},  {
						name: "市场营销",
						active: true
					}, {
						name: "会计学",
						active: true
					},{
						name: "工程管理",
						active: true
					},{
						name: "信息管理与信息系统",
						active: true
					}, {
						name: "经济统计学",
						active: true
					}, {
						name: "国际经济与贸易",
						active: true
					}, {
						name: "电气工程及其自动化",
						active: true
					}, {
						name: "机器人工程",
						active: true
					}, {
						name: "电子信息工程",
						active: true
					}
				],
				momentsList: [{
					username: "凯尔",
					publishDate: "2019年12月3日",
					photo: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
					content: "折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来终结！",
					images: [{
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg"
					}],
					viewCount: 20,
					likeCount: 10,
					commentCount: 5,
					commentList: [{
						url: "https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png",
						name: "莫甘娜",
						content: "凯尔，你被自己的光芒变的盲目。",
						userId: "1",
						commentTime: "2018年12月4日",
						replyList: [{
							name: "凯尔",
							content: "妹妹，你在帮他们给黑暗找借口吗?",
							userId: "",
							replyTime: ""
						}]
					}, {
						url: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
						name: "凯尔",
						content: "妹妹，如果不是为了飞翔，我们要这翅膀有什么用?",
						userId: "2",
						commentTime: "2018年12月4日",
						replyList: []
					}]
				}],
				params: {
					pageNo:1,
					pageSize:5
				}
			}
		},
		onLoad() {
	
			//获取朋友圈列表
			this.getDiscoverList();

		},
		methods: {
			selectHandler(value) {
				this.currentSelect = value;
				if (this.currentSelect == '1') {
					if (this.currentSelect == this.currentfromSelect) {
						this.isSelect = !this.isSelect;
					}
					this.currentfromSelect = value;
				}
				if (this.currentSelect == '2') {
					this.isSelect = false;
					this.currentfromSelect = value;
				}
			},			
			//获取朋友圈列表
			getDiscoverList(){
				getDiscoverList(this.params).then(data =>{
					let [error, res] = data;
					if(res&&res.data&&res.data.result){
						let content = res.data.result.content;
						this.momentsList = this.transformData(content);
					}
				});
			},
			transformData(list){
				list = list.map(item =>{
					return {
						username: item.userName,
						publishDate: dateUtil.formatDate(item.createTime),
						photo: item.userPhoto,
						content: item.content,
						images: JSON.parse(item.photos)
					}					
				});
				return list;
			}			
		}
	}
</script>

<style>
	.attention {
		margin: 5px 5px 5px 5px;
	}

	.attention.active {
		color: darkorange;
	}

	.qiun-charts {
		width: 750upx;
		height: 750upx;
		background-color: #FFFFFF;
	}

	.charts {
		width: 750upx;
		height: 750upx;
		background-color: #FFFFFF;
	}

	.discover-ul {
		white-space: nowrap;
		list-style: none;
	}

	.discover-li {
		display: inline-block;
		margin: 0 .75rem;
	}

	.discover-li span {
		border-bottom: 2px solid #ff5b3600;
		margin: 0px -5px;
		line-height: 30px;
	}

	.discover-li-cur span {
		border-bottom: 2px solid #ff5b36;
	}

	.discover-li-cur text {
		padding-left: 10px;

	}

	.discover-group {
		position: absolute;
		width: 100%;
		z-index: 1;
		background: white;

	}
</style>
