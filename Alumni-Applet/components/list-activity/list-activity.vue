<template>
	<view>
		<uni-list>
			<uni-list-item :to="'/pages/anniversary/activity/activityDetail?id='+item.id" direction="column" v-for="item in lists" :key="item.id">
				<template v-slot:body>
					<view class="uni-list-box">
						<view class="uni-thumb">
							<image :src="item.avatar" mode="aspectFill"></image>
						</view>
						<view class="uni-content">
							<view class="uni-title-sub uni-ellipsis-2">{{item.excerpt}}</view>
							<view class="uni-note">{{ item.user_name }} {{ item.last_modify_date }}</view>
						</view>
					</view>
				</template>
			</uni-list-item>
		</uni-list>
	</view>
</template>

<script>
	export default {
		data() {
			return {	
			};
		},
		props: {
		    lists: {
		      type: Array,
		      default: function() {
		        return [{
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
				}];
		      }
		    }
		  },
		methods: {
			
		}
	};
</script>

<style lang="scss">
	@import '@/common/uni-ui.scss';

	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}

	.tips {
		color: #67c23a;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
		background-color: #f0f9eb;
		height: 0;
		opacity: 0;
		transform: translateY(-100%);
		transition: all 0.3s;
	}

	.tips-ani {
		transform: translateY(0);
		height: 40px;
		opacity: 1;
	}

	.content {
		width: 100%;
		display: flex;
	}

	.list-picture {
		width: 100%;
		height: 145px;
	}

	.thumb-image {
		width: 100%;
		height: 100%;
	}

	.ellipsis {
		display: flex;
		overflow: hidden;
	}

	.uni-ellipsis-1 {
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.uni-ellipsis-2 {
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
	}
</style>
