<template>
	<view class="news-item shadow">
		<view class="title-box">
			<image class="imgs" src="http://cdxyh.stickeronline.cn/logo.jpg" mode=""></image>
			<view class="textBox">
				<text class="text">地测学院</text>
				<text class="date">2020-11-12</text>
			</view>
		</view>
		<view class="news-item-title">
			<view class="news-title">{{item.title}}</view>
		</view>
		<view v-if="item.thumb.length>2" class="grid col-3 grid-square">
			<view class="news-item-images" v-for="img in item.thumb">
				<image :src="img" mode="aspectFill"></image><!-- mode="widthFix" -->
			</view>
		</view>
		<view v-else-if="item.thumb.length<1" class="grid col-1">
			<view class="news-item-text" v-html="item.contents">
			</view>
		</view>
		<view v-else-if="item.thumb.length==1" class="grid col-1" style="height: 120px;">
			<view class="news-item-images" v-for="img in item.thumb" style="height: 120px;">
				<image :src="img" mode="aspectFill" style="height: 120px;border-radius: 10px;"></image><!-- mode="widthFix" -->
			</view>
		</view>
		<view v-else class="grid col-2 grid-square">
			<view class="news-item-images" v-for="img in item.thumb">
				<image :src="img" mode="aspectFill"></image><!-- mode="widthFix" -->
			</view>
			<!-- <view class="cu-bar bg-shadeBottom"> <text class="text-cut">{{item.desc}}</text></view> -->
		</view>
		<view class="news-item-foot">
			<view class="phm-zx-content">
				<view class="text-gray text-sm flex justify-between">
					<view class="">
						
					</view>
					<view class="phm-zx-view text-gray text-sm">
						<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.viewCount?item.viewCount:0}}
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		components: {},
		props: {
			opts: {
				type: Object,
				default: function() {
					return {
						thumb: '["http://www.imapway.cn/Alumni/static/home/banner22x.png"]', //"http://www.imapway.cn/Alumni/static/home/banner22x.png"
						title: '学生工作业务能力专题培训举办',
						contents: '我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。',
						createTime: '2020-10-18',
						viewCount: 23
					};
				}
			}
		},
		watch: {
			opts:function(newQuestion, oldQuestion) {
				this.opts.thumb=JSON.parse(this.opts.thumb);
				this.item=this.opts;
			}
		},
		data() {
			return {
				item: {}
			}

		},
		mounted() {
			this.opts.thumb=JSON.parse(this.opts.thumb);
			this.item = this.opts;
		},
		methods: {
			formatDate(date) {
				return getApp().formatDate(date);
			},
		}
	}
</script>


<style lang="scss" scoped>
	.news-item {
		width: 100%;
		padding: 30rpx 20rpx;
		.title-box{
			display: flex;
			justify-content: flex-start;
			align-items: center;
			.imgs{
				width:76rpx;
				height:76rpx;
				margin-right:20rpx;
			}
			.textBox{
				display: flex;
				flex-direction: column;
				align-items: start;
				.text{
					height:44rpx;
				}
				.date{
					font-size: 12px;
					color:#999;
				}
			}
		}
	}

	.news-item-title {
		font-size: 14px;
		font-weight: bold;
		color: #000000;
		.news-title{
			overflow:hidden;
			text-overflow: ellipsis;
			    margin: 20rpx 0;
			    font-size: 16px;
			// white-space: nowrap;
		}
	}

	.news-item-images {
		width: 100%;
		margin-right: 12rpx;
		margin-bottom: 12rpx;
		border-radius: 8rpx;
		image {
			width: 100%;
			background-size: 100% 100%;
		}
	}

	.news-item-text {
		max-height: 85px;
		overflow: hidden;
		text-overflow: ellipsis;
	}
</style>
