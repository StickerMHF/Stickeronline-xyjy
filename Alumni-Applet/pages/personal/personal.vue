<template>
	<view>
		<view class="header">
			<view class="bg" style="background-image:url(../../static/personal/background.png);">
				<view class="box">
					<view class="box-hd">
						<view class="avator">
							<img src="../../static/user/face.jpg">
						</view>
						<view class="phone-number">18909XXXX67</view>
					</view>
					<view class="box-bd">
						<view class="item">
							<view class="icon"><img src="../../static/personal/wdgz2x.png"></view>
							<view class="text">我的关注</view>
						</view>
						<view class="item">
							<view class="icon"><img src="../../static/personal/sc2x.png"></view>
							<view class="text">我的收藏</view>
						</view>
						<view class="item">
							<view class="icon"><img src="../../static/personal/wdzz2x.png"></view>
							<view class="text">我的圈子</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="list-content">
			<view class="list" v-for="item in menuList">
				<view class="li noborder" >
					<view class="icon"><image :src="item.icon"></image></view>
					<view class="text">{{item.name}}</view>
					<image class="to" src="../../static/user/to.png"></image>
				</view>
			</view>
			
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				menuList:[{
					name:"个人信息",
					icon:"../../static/personal/grxx2x.png"
				},{
					name:"我的组织",
					icon:"../../static/personal/wdzz2x.png"
				},{
					name:"我的关注",
					icon:"../../static/personal/wdgz2x.png"
				},{
					name:"我的评论",
					icon:"../../static/personal/wdpl2x.png"
				},{
					name:"收藏",
					icon:"../../static/personal/sc2x.png"
				},{
					name:"设置",
					icon:"../../static/personal/sz2x.png"
				}]
			};
		},
		onLoad() {
		},
		methods: {
			changeSkin(){
				uni.navigateTo({
						url: '../skin-change/skin-change'
				});
			},
		}
	}
</script>

<style lang="scss">
page{
	background-color:#f1f1f1;
	font-size: 30upx;
}
.header{
	background: #fff;
	height: 500upx;
	padding-bottom: 110upx;
	.bg{
		width: 100%;
		height:300upx;
		padding-top:200upx;
		background-color:#4191ea;
		// background:url(../../static/personal/background.png);
		background-size: 100% 100%;
	}
}
.box{
	width: 650upx;
	height: 280upx;
	border-radius: 20upx;
	margin: 0 auto;
	background: #fff;
	box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2); 
	.box-hd{
		display: flex;
		flex-wrap: wrap;
		flex-direction: row;
		justify-content: center;
		.avator{
			width: 160upx;
			height: 160upx;
			background: #fff;
			border: 5upx solid #fff;
			border-radius: 50%;
			margin-top: -80upx;
			overflow: hidden;
			img{
				width: 100%;
				height: 100%;
			}
		}
		.phone-number{
			width: 100%;
			text-align: center;
		}
	}
	.box-bd{
		display: flex;
		flex-wrap: nowrap;
		flex-direction: row;
		justify-content: center;
		.item{
			flex: 1 1 auto;
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;
			border-right: 1px solid #f1f1f1;
			margin: 15upx 0;
			&:last-child{
				border: none;
			}
			.icon{
				width: 60upx;
				height: 60upx;
				img{
					width: 100%;
					height: 100%;
				}
			}
			.text{
				width: 100%;
				text-align: center;
				margin-top: 10upx;
			}
		}
	}
}
.list-content{
	background: #fff;
}
.list{
	width:100%;
	border-bottom:15upx solid  #f1f1f1;
	background: #fff;
	&:last-child{
		border: none;
	}
	.li{
		width:92%;
		height:100upx;
		padding:0 4%;
		border-bottom:1px solid rgb(243,243,243);
		display:flex;
		align-items:center;
	&.noborder{
		border-bottom:0
		}
		.icon{
			flex-shrink:0;
			width:50upx;
			height:50upx;
			image{
				width:50upx;
				height:50upx;
			}
		}
		.text{
			padding-left:20upx;
			width:100%;
			color:#666;
		}
		.to{
			flex-shrink:0;
			width:40upx;
			height:40upx;
		}
	}
}
</style>
