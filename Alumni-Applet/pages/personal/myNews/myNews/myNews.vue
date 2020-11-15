<template>
	<view>
		<view class="header">
			<view class="bg" style="
		      background-image: url(http://www.imapway.cn/Alumni/static/personal/background.png);
		    ">
				<cu-custom style="color: #fff;" :isBack="true" :isCallBack="false" @callBack="callBack">
					<block slot="content">我的消息</block>
				</cu-custom>
			</view>
		</view>
		<view>暂无数据</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style>

</style>
