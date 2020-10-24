<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">个人信息</block>
		</cu-custom>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			onLoad(options) {
				// 初始化页面数据
				this.title="";
				this.getTeachersById(options.id);
			},
		}
	}
</script>

<style>

</style>
