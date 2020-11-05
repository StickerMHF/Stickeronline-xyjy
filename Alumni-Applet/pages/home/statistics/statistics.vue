<template>
	<view class="whole_statistics">
		<web-view src="http://39.100.142.33:10082/rest/v1/newdip/share/report/index.html?uuid=78e64602f19f4fdbbeab6f9e0898c8ef"></web-view>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				
			}
		},
		onReady() {
		        // #ifdef APP-PLUS
		        var currentWebview = this.$scope.$getAppWebview() //此对象相当于html5plus里的plus.webview.currentWebview()。在uni-app里vue页面直接使用plus.webview.currentWebview()无效，非v3编译模式使用this.$mp.page.$getAppWebview()
		        setTimeout(function() {
		            let wv = currentWebview.children()[0]
		            wv.setStyle({top:150,height:300})
		        }, 1000); //如果是页面初始化调用时，需要延时一下
		        // #endif
		        },
		methods: {
			
		}
	}
</script>

<style lang="scss" scoped>
.whole_statistics{
	overflow: none;
	overflow-y: auto;
}
</style>
