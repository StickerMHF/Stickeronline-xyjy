<template>
	<view>
		<scroll-view scroll-y class="indexes" :scroll-into-view="'indexes-'+ listCurID" :style="[{height:'calc(100vh - '+ CustomBar + 'px - 50px)'}]"
		 :scroll-with-animation="true" :enable-back-to-top="true">
			<block v-for="(item,index) in list" :key="index">
				<view :class="'indexItem-' + item.name" :id="'indexes-' + item.name" :data-index="item.name">
					<view class="cu-list menu-avatar no-padding">
						<view class="cu-item">
							<view v-if="item.photo" class="cu-avatar round lg" :style="'background-image:url('+item.photo+');'"></view>
							<view v-else class="cu-avatar round lg" style="background-image:url('http://www.imapway.cn/Alumni/static/alumnus/default_photo.png')"></view>
							<view class="content">
								<view class="text-grey">{{item.name}}<text class="text-abc"></text>
								</view>
							</view>
							<view class="margin-tb-sm text-center mem-attention">
								<button @click="payHandler(item)" v-if="item.attention&&item.attention==1" class="cu-btn round bg-yellow">已关注</button>
								<button @click="payHandler(item)" v-else class="cu-btn round bg-gradual-green1">关注</button>
							</view>
						</view>
					</view>
				</view>
			</block>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {};
		},
		props: {
			list: {
				type: Array,
				default: function() {
					return [{
							id: "1",
							name: "sticker",
							sex: "男",
							telephone: "15245974561",
							email: "gisfzb001@153.com",
							fid: "001",
							photo: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",
							attention: 1
						},
						{
							id: "2",
							name: "gisfzb",
							sex: "男",
							telephone: "15498743016",
							email: "gisfzb001@154.com",
							fid: "0010",
							photo: "https://ossweb-img.qq.com/images/lol/web201310/skin/big10007.jpg",
							attention: 0
						}
					];
				}
			}
		},
		methods: {

		}
	};
</script>

<style>

</style>
