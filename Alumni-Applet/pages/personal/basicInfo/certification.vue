<template>
	<view class="ann_news">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<view class="certification_content bg-gradual-green1">
			<view class="cer_content_title">
				<text class="title">身份认证</text>
				<text>请选择您的身份</text>
			</view>
			<view class="cer_content_select" v-for="item in list">
				<navigator :url="item.page+'&title='+item.title">
				<view class="select_item" >
						<text class="title">{{item.title}}</text>
						<text>{{item.desc}}</text>
				</view>
				</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		components: {
		},
		data() {
			return {
				title: "",
				list: [{
					title: "曾在校学习或工作",
					page:"/pages/personal/basicInfo/add?type=1",
					desc: "曾经在校学习或工作的校友，请选择该入口认证"
				}, {
					title: "在校生",
					page:"/pages/personal/basicInfo/add?type=2",
					desc: "目前在校就读的学生，请选择该入口认证"
				}, {
					title: "教职工",
					page:"/pages/personal/basicInfo/add?type=3",
					desc: "目前在校就职的教职工，请选择该入口认证"
				}]
			}
		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.certification_content {
		position: absolute;
		top: 45px;
		bottom: 0px;
		left: 0px;
		right: 0px;
		background-size: 100% 100%;

	}

	.cer_content_title {
		padding: 20px;

		.title {
			font-size: 32px;
		}

		text {
			display: block;
		}
	}

	.cer_content_select {
		padding: 0 20px;

		.select_item {
			background: #FFFFFF;
			border-radius: 5px;
			padding: 20px 10px 20px 50px;
			margin: 30px 0px;

			.title {
				color: #000000;
				font-size: 16px;
				font-weight: bold;
			}

			text {
				font-size: 12px;
				display: block;
				color: #4b4b4b;
			}
		}

	}
</style>
