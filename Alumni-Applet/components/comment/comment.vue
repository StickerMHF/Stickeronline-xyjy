<template>
	<view class="whole_comment">
		<view class="whole_comment_bottom">
			<view class="cu-form-group wc_input">
				<textarea class="wc_textarea" maxlength="-1" @input="textareaAInput" placeholder="多行文本输入框"></textarea>
			</view>
			<view class="wc_btnmenu">
				<!-- <view class="wc_location"> -->
				<span class="wcb_location">
					<text class="cuIcon-location">点击获取位置</text>
				</span>
				<!-- </view> -->
				<!-- <span class="wcb_btn">发布</span> -->
				<!-- <button class="wc_button bg-gradual-green1">发布</button> -->
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
	.wc_button{
		display: inline-block;
		
	}
	.wcb_location{
		// display: inline-block;
		padding: 10rpx 20rpx;
		height: 60rpx;
		background-color: #D9D9D9;
		border-radius:50px ;
	}
	.whole_comment{
		width: 100%;
		height: 100%;
		background: rgba(0,0,0,0.5);
	}
.whole_comment_bottom{
	height: 235rpx;
	    width: 100%;
	    position: fixed;
	    bottom: 0;
	    z-index: 0;
	    background: white;
		padding: 20rpx;
}
.wc_input{
	padding: 0rpx;
	    border-bottom: 1px solid #e0e0e0;
}
.wc_btnmenu{
	line-height: 80rpx;
	.wcb_btn{
		float: right;
	}
}
.wc_textarea{
	background: #f1efef;
	padding: 20rpx;
	margin: 0;
}
</style>
