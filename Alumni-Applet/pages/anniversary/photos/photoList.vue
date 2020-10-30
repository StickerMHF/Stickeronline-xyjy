<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="ann_news_content">
			<view class="photoList">
				<view class="listBox" v-for="(item,index) in imgList" :key="index" @click="previewPhoto(index)">
					<view class="imgBox">
						<image :src="item" mode="" class="userPhoto"></image>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getPhotoList
	} from '@/api/cooperation.js'
	export default {
		data() {
			return {
				title:'照片墙',
				id:'',
				imgList:[],
			}
		},
		onLoad(option) {
			this.id = option.id;
			this.getPhotoList();
		},
		methods: {
			getPhotoList(){
				let param = {
					pageNo:1,
					pageSize:100,
					userId: this.id
				}
				getPhotoList(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						let datas = res.data.result;
						datas.content.forEach(v => {
							if(v.imgs.slice(0,4) === "http"){
								let imgArr = v.imgs.split(";");
								imgArr.forEach(item => {
									if(this.imgList.indexOf(item) === -1 && item !== ""){//判断数组中是否存在该值
										this.imgList.push(item)
									}
								})
							}
						})
						console.log(this.imgList);
						// this.imgList = datas.content;
					}
				});
			},
			previewPhoto(index){
				uni.previewImage({
					current:index,
					urls: this.imgList,
					// longPressActions: {
					// 	itemList: ['发送给朋友', '保存图片', '收藏'],
					// 	success: function(data) {
					// 		console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
					// 	},
					// 	fail: function(err) {
					// 		console.log(err.errMsg);
					// 	}
					// }
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
.ann_news{
	width: 100%;
	height: 100%;
}
.ann_news_content{
	position: absolute;
	top: 100rpx;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background-size: 100% 100%;
	background-color: white;
}
.photoList{
	display: flex;
	justify-content: center;
	align-items: center;
	flex-wrap: wrap;
}
.listBox{
	margin: 20rpx;
	.imgBox{
		width:300rpx;
		height:300rpx;
		.userPhoto{
			width: 100%;
			height: 100%;
		}
	}
}
</style>
