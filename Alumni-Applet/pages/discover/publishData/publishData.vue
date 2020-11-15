<template>
	<view style="overflow-x: hidden;">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>			
		</cu-custom>
		<view class="album-content">
			<publish ref="pub"></publish>
		</view>
	</view>
</template>

<script>
	import publish from './publish.vue';
	export default {
		components: {
			publish
		},
		data() {
			return {
				// id是DB主键，如果id重复会造成在拖拽排序时候，会影响相同ID的元素
				list: [
					{
						id:1,
						src:'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3407686795,968933150&fm=26&gp=0.jpg',
						sortID:1,
					},
					{
						id:2,
						src:'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=137628589,3436980029&fm=26&gp=0.jpg',
						sortID:2,
					},
					{
						id:3,
						src:'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2265591745,134271296&fm=11&gp=0.jpg',
						sortID:3,
					},
					{
						id:4,
						src:'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=473522556,3176124776&fm=11&gp=0.jpg',
						sortID:4,
					},
					{
						id:5,
						src:'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3218418246,4067817241&fm=26&gp=0.jpg',
						sortID:5,
					},
					{
						id:6,
						src:'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2809605157,700908921&fm=26&gp=0.jpg',
						sortID:6,
					},
				],
				commentSwitch:true,
				text:''
			}
		},
		onLoad() {

		},
		onShow() {
			this.$refs.pub.getLocation();
		},
		onUnload () {
		  this.$refs.pub.setLocation();
		},
		methods: {
			onAlbumSort (list) {
				// 返回排序后的数组集合
				// 更新后台排序号
				console.info('排序成功');
				
			},
			onAlbumClick(res){
				// 图片预览
				uni.previewImage(res.index,res.list);
			},
			onAlbumAdd(res){
				// 新增图片，会返回图片的排序号和图片路径
				// 需要保存到后台后，然后add到组件中
				console.info('新增成功');
				this.$refs.rAlbum.add({
					// id必须不能重复！！！！
					id: Math.floor(Math.random() * 1000 + 1),
					sortID: res.sortID,
					src: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3680389605,4248810639&fm=26&gp=0.jpg'
				});
			},
			onAlbumDelete(data){
				// 返回删除的图片实体
				// 到后台删除
				console.info('删除成功');
			}
		}
	}
</script>

<style>
	.album-content{
		width: 100%;
		/* height: calc(100vh - var(--window-top) - var(--window-bottom)); */
		font-size: 14px;
	}
	.paddingLR30{
		padding-left: 30rpx;
		padding-right: 30rpx;
	}
	.paddingT20{
		padding-top: 20rpx;
	}
	.paddingB20{
		padding-bottom: 20rpx;
	}
</style>
