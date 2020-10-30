<template>
	<view class="goodmember-details">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">优秀校友</block>
		</cu-custom>
		<view class="gmember-title cu-bar bg-white solid-bottom">
			<view class="action">
				<text class="cuIcon-titles text-green1"></text> 简介
			</view>
		</view>
		<view class="good_md_content" v-html="item.desc"></view>
	</view>
</template>

<script>
	import {
		getMemberById
	} from '@/api/member.js'
	export default {
		data() {
			return {
				item:{
					desc:"sss"
				}
			}
		},
		onLoad(options) {
			if(options.id){
				this.getMemberById(options.id);
			}
			// this.getMemberById();
			// this.list = list;
		},
		methods: {
			getMemberById(id) {
				let that = this;
				this.status = 'loading'
					let param = {
						id: id
					};
				getMemberById(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						that.item = res.data.result;
						
					}
				})
			
			}
		}
	}
</script>

<style lang="scss" scoped>
page{
	width: 100%;
	height: 100%;
	background-color: #ffffff;
}
.goodmember-details{
	width: 100%;
	height: 100%;
}
.good_md_content{
	padding: 10px 20px;
	background-color: #ffffff;
}
</style>
