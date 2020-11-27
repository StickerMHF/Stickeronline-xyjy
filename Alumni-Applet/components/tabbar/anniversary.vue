<template>
	<view class="anniversary">
		<cu-custom bgColor="bg-gradual-green1" :isBack="false">
			<block slot="content">校庆</block>
		</cu-custom>
		<view class="anniversary_content" style="background-image:url(http://www.imapway.cn/Alumni/static/anniversary/background.png);">
			<view class="anniversary_menu">
				<view class="grid col-3 grid-square">
					<view class="anniversary_item" v-for="(item,index) in menu" :key="index" >
						<navigator :url="item.page+'?title='+item.title">
						<view class="anniversary_item_img" :style="'background-image:url('+item.url+');'"></view>
						<view class="anniversary_item_text">
							{{item.title}}
						</view>
						</navigator>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return{
				menu:[{
					url:"http://www.imapway.cn/Alumni/static/anniversary/xqzx2x.png",
					title:"校庆资讯",
					page:"/pages/anniversary/news/news"
				},
				{
					url:"http://www.imapway.cn/Alumni/static/anniversary/xqhd2x.png",
					title:"校庆活动",
					page:"/pages/anniversary/activity/activity"
				},
				{
					url:"http://www.imapway.cn/Alumni/static/anniversary/xqzf2x.png",
					title:"校庆祝福",
					page:"/pages/anniversary/wish/wish"
				},
				{
					url:"http://www.imapway.cn/Alumni/static/anniversary/xqtx2x.png",
					title:"校庆头像",
					page:"/pages/anniversary/headLogo/headLogo"
				},
				{
					url:"http://www.imapway.cn/Alumni/static/anniversary/dlqq2x.png",
					title:"点亮全球",
					page:"/pages/anniversary/footprint/footprint"
				},
				{
					url:"http://www.imapway.cn/Alumni/static/anniversary/zpzj2x.png",
					title:"照片征集",
					page:"/pages/anniversary/photos/photos"
				}]
			}
		},
		methods: {
			ontrueGetList(){
				// uni.showToast({
				// 	title:'第3个页面'
				// })
				// console.log("加载了第三个页面，可以把网络请求放这里")
			},
			lower(){
				uni.showToast({
					title:'scroll-view的加载更多'
				})
				console.log("分页数据可以放这里~~~~~~嘿嘿")
			},
			callBack(){
				uni.switchTab({
					url: '/pages/home/home'
				});
			},
		}
	}
</script>

<style lang="scss">
	// .main{background-image: linear-gradient(45deg, #f43f3b, #ec008c);}
	// // padding-bottom:120rpx;box-sizing: border-box;重要*************************保证页面底部内容不被隐藏也不会出现多余的滚动条
	// .main_box{width:100vw;height: 100vh;padding-bottom:120rpx;box-sizing: border-box;}
	// .main_centent{width: 100%;height: 100vh;color: #fff;letter-spacing: 4rpx;display: flex;align-items: center;justify-content: center;}
	// image{width:750rpx;height: 750rpx;}
	.anniversary {
		width: 100%;
		height: 100%;
	}
	
	.anniversary_content {
		position: absolute;
		top: 45px;
		bottom: 0px;
		left: 0px;
		right: 0px;
		background-size: 100% 100%;
		background-color: white;
	}
	
	.anniversary_menu {
		top: 200px;
		position: relative;
		padding: 30px;
	}
	.anniversary_item{
		text-align: center;
	}
	.anniversary_item_img{
		width: 150rpx;
		height: 150rpx;
		margin: 0 auto;
		background-size: 100% 100%;
	}
</style>
