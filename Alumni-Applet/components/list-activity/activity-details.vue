<template>
	<view class="activity_details">
		<view class="actd_heade">
			<image :src="item.url||'http://cdxyh.stickeronline.cn/banner12x.png'" mode="aspectFill"></image>
		</view>
		<view class="actd_addr">
			<view class="actd_addr_title">
				{{item.title}}
			</view>
			<view class="actd_addr_item">
				<text class="lg text-gray cuIcon-time aiicon"></text>
				<text>{{item.startTime}}至{{item.endTime}}</text>
			</view>
			<view class="actd_addr_item">
				<text class="lg text-gray cuIcon-location aiicon"></text>
				<text>{{item.address}}</text>
			</view>
			<view class="actd_addr_item">
				<text class="lg text-gray cuIcon-time aiicon"></text>
				<text>{{item.deadline}}截至报名</text>
			</view>
		</view>
		<view class="cu-bar bg-white solid-bottom actd_content_title">
			<view class="action">
				<text class="cuIcon-titles text-green1"></text> 活动详情
			</view>
		</view>
		<view class="actd_content" v-html="item.context"></view>
		<view class="actd_foot">
			<view class="actd_foot_btn bg-white">
				已报{{count||0}}人
			</view>
			<view v-if="moment(new Date())>moment(item.deadline)" class="actd_foot_btn bg-gradual-green1">
				报名已截止
			</view>
			<view v-else-if="!state" class="actd_foot_btn bg-gradual-green1" @click="applyHandler(item)">
				点我报名
			</view>
			<view v-else class="actd_foot_btn bg-gradual-green1" >
				已报名
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getAlumnusActivityList,
		addActivityApply,getAlumnusActivityById
	} from '@/api/alumnus.js'
	// import moment from "moment";  
	export default {
		data() {
			return {
				state:false,
				count:0,
				item: {}
			}
		},
		props: {
			opts: {
				type: Object,
				default: function() {
					return {
						url: "http://cdxyh.stickeronline.cn/banner12x.png",
						title: "长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行",
						startTime: "2020-10-26 00:00:00",
						endTime: "2020-10-26 00:00:00",
						address: "长安大学渭水校区",
						deadline: "2020-10-26 00:00:00",
						content: "<h3>长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行</h3>",
						joinNum: 23,
						isApply:false
					}
				}
			}
		},
		watch: {
			opts() {
				this.formatData();
			}
		},
		mounted() {
			this.formatData();
		},
		methods: {
			moment(date){
				var res = new Date(date);
				return res;
			},
			formatData() {
				let that = this;
				that.item = that.opts;
				that.count=that.item.applyList.length;
				that.state=that.item.isApply;
				
			},
			applyHandler(value) {
				let that = this;
				let openid = uni.getStorageSync('openid');
				if (openid) {
					let params = {
						userId: openid,
						activityId: value.id
					}
					addActivityApply(params).then(data => {
						var [error, res] = data;
						if (res && res.data.success) {
							that.state = true;
							that.count=that.count+1;
							uni.showToast({
								title: '报名成功',
								duration: 2000
							});
						}
					})
				} else {
					uni.navigateTo({
						url: "/pages/login/login"
					});
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.activity_details {
		width: 100%;
		height: 100%;
		background: white;
	}

	.actd_heade {
		width: 100%;
		border-radius: 0;
		image {
			width: 100%;
			height: 350rpx;
			border-radius: 0;
		}
	}

	.actd_addr {
		padding: 5px 15px;
		border-bottom: 1px solid #eaeaea;

		.actd_addr_title {
			color: #000000;
			font-weight: bold;
		}

		.actd_addr_item {
			line-height: 70rpx;

			.aiicon {
				padding-right: 10px;
			}
		}
	}

	.actd_content_title {
		border-bottom: 1px solid #eaeaea;
	}

	.actd_content {
		padding: 20rpx 40rpx;
		padding-bottom: 120rpx;
	}

	.actd_foot {
		height: 120rpx;
		position: fixed;
		bottom: 0px;
		left: 0px;
		right: 0px;
		display: inline-block;

		.actd_foot_btn {
			width: 50%;
			text-align: center;
			font-size: 16px;
			line-height: 120rpx;
			display: inline-block;
		}
	}
</style>
