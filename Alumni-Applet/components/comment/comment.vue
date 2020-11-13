<template>
	<view class="whole_comment">
		<view class="whole_comment_bottom">
			<view class="cu-form-group margin-top wc_input">
				<textarea class="wc_textarea" maxlength="-1" @input="textareaAInput" placeholder="多行文本输入框"></textarea>
			</view>
			<view class="wc_btnmenu">
				<span class="wcb_location">
					<text class="cuIcon-location">点击获取位置</text>
				</span>
				<span class="wcb_btn">发布</span>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			textareaAInput(data,value){
				
			}
		}
	}
</script>

<style lang="scss" scoped>
	.whole_comment{
		position: absolute;
		top: 0;
		bottom: 0;
		width: 100%;
		background: #cecece;
	}
.whole_comment_bottom{
	height: 200rpx;
	width: 100%;
}
.wc_input{
	padding: 10rpx 20rpx;
}
.wc_textarea{
	background: #e8e8e8;
}
</style>
