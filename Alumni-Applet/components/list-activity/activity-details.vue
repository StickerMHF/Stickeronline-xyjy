<template>
	<view class="activity_details">
		<view class="actd_heade">
			<image :src="item.url" mode="aspectFill"></image>
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
		<view class="actd_content" v-html="item.content"></view>
		<view class="actd_foot">
			<view class="actd_foot_btn bg-white">
				已报
			</view>
			<view class="actd_foot_btn bg-gradual-green1">
				报名已截止
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item: {
					url:"http://cdxyh.stickeronline.cn/banner12x.png",
					title:"长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行",
					startTime:"2020年11月11日",
					endTime:"2020年12月12日",
					address:"长安大学渭水校区",
					deadline:"2020年10月10日",
					content:"<h1>长安大学70周年校庆公告（第二号）发布仪式暨校友代表座谈会举行</h1>"
				}
			}
		},
		mounted() {
			
		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.activity_details{
		width: 100%;
		height: 100%;
	}
	.actd_heade{
		width: 100%;
		border-radius: 0;
		image{
			width: 100%;
			border-radius: 0;
		}
	}
	.actd_addr{
		padding: 5px 15px;
		border-bottom: 1px solid #eaeaea;
		.actd_addr_title{
			color: #000000;
			font-weight: bold;
		}
		.actd_addr_item{
			line-height: 35px;
			.aiicon{
				padding-right: 10px;
			}
		}
	}
	.actd_content_title{
		border-bottom: 1px solid #eaeaea;
	}
	.actd_content{
		padding: 10px 20px;
	}
	.actd_foot{
		height: 70px;
		position: absolute;
		bottom: 0px;
		left: 0px;
		right: 0px;
		display: flex;
		.actd_foot_btn{
			display: flex;
			width: 50%;
		}
	}
</style>
