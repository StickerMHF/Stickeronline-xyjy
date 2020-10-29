<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="content">通讯录</block>
		</cu-custom>
		<view class="cu-bar bg-white search fixed" :style="[{top:CustomBar + 'px'}]">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input type="text" placeholder="输入搜索的关键词" confirm-type="search"></input>
			</view>
			<view class="action">
				<button class="cu-btn bg-gradual-green shadow-blur round">搜索</button>
			</view>
		</view>
		<scroll-view scroll-y class="indexes" :scroll-into-view="'indexes-'+ listCurID" :style="[{height:'calc(100vh - '+ CustomBar + 'px - 50px)'}]"
		 :scroll-with-animation="true" :enable-back-to-top="true">
			<block v-for="(item,index) in list" :key="index">
				<view :class="'indexItem-' + item.name" :id="'indexes-' + item.name" :data-index="item.name">
					<view class="address-padding">{{item.name}}</view>
					<view class="cu-list menu-avatar no-padding">
						<view class="cu-item" v-for="(items,sub) in item.users" :key="sub">
							<view class="cu-avatar round lg" :style="'background-image: url('+items.photo+');'"></view>
							
							<view class="content">
								<navigator url="">
								<view class="text-grey">{{items.name}}<text class="text-abc">{{items.name}}</text></view>
								<view class="text-gray text-sm">
									{{items.email}}
								</view>
							</navigator>
							</view>
							<view class="address-attention">
								<view v-if="items.isAttention" class="address-attention-btn">已关注</view>
								<view v-else class="address-attention-btn">关注</view>
							</view>
						</view>
					</view>
				</view>
			</block>
		</scroll-view>
		<view class="indexBar" :style="[{height:'calc(100vh - ' + CustomBar + 'px - 50px)'}]">
			<view class="indexBar-box" @touchstart="tStart" @touchend="tEnd" @touchmove.stop="tMove">
				<view class="indexBar-item" v-for="(item,index) in list" :key="index" :id="index" @touchstart="getCur" @touchend="setCur"> {{item.name}}</view>
			</view>
		</view>
		<!--选择显示-->
		<view v-show="!hidden" class="indexToast">
			{{listCur}}
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				StatusBar: this.StatusBar,
				CustomBar: this.CustomBar,
				hidden: true,
				listCurID: '',
				list: [{
				name:"A",
				users:[{
					name:"A君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:true
				},{
					name:"A君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:false
				}]
			},{
				name:"B",
				users:[{
					name:"B君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:false
				},{
					name:"B君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:false
				}]
			}],
				listCur: '',
			};
		},
		onLoad() {
			let list = [{
				name:"A",
				users:[{
					name:"A君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:true
				},{
					name:"A君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:false
				}]
			},{
				name:"B",
				users:[{
					name:"B君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:false
				},{
					name:"B君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"",
					isAttention:true
				}]
			},{
				name:"#",
				users:[{
					name:"其他君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					isAttention:false
				},{
					name:"其他君",
					photo:"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL5CRqudtTN97pM9JdXlQl7zjRNNfiaofSMiaVWyyTaN8elgbiax8v5RO67ozUH6cgVhHkvwOWER0AYw/132",
					email:"111@163.com",
					isAttention:false
				}]
			}];
			this.list = list;
			debugger
			this.listCur = list[0];
		},
		onReady() {
			let that = this;
			uni.createSelectorQuery().select('.indexBar-box').boundingClientRect(function(res) {
				that.boxTop = res.top
			}).exec();
			uni.createSelectorQuery().select('.indexes').boundingClientRect(function(res) {
				that.barTop = res.top
			}).exec()
		},
		methods: {
			//获取文字信息
			getCur(e) {
				this.hidden = false;
				this.listCur = this.list[e.target.id].name;
			},
			setCur(e) {
				this.hidden = true;
				this.listCur = this.listCur
			},
			//滑动选择Item
			tMove(e) {
				let y = e.touches[0].clientY,
					offsettop = this.boxTop,
					that = this;
				//判断选择区域,只有在选择区才会生效
				if (y > offsettop) {
					let num = parseInt((y - offsettop) / 20);
					this.listCur = that.list[num].name
				};
			},

			//触发全部开始选择
			tStart() {
				this.hidden = false
			},

			//触发结束选择
			tEnd() {
				this.hidden = true;
				this.listCurID = this.listCur
			},
			indexSelect(e) {
				let that = this;
				let barHeight = this.barHeight;
				let list = this.list;
				let scrollY = Math.ceil(list.length * e.detail.y / barHeight);
				for (let i = 0; i < list.length; i++) {
					if (scrollY < i + 1) {
						that.listCur = list[i].name;
						that.movableY = i * 20
						return false
					}
				}
			}
		}
	}
</script>

<style >
	page {
		padding-top: 100upx;
	}
.address-padding{
	padding: 0 10px;
}
	.indexes {
		position: relative;
	}

	.indexBar {
		position: fixed;
		right: 0px;
		bottom: 0px;
		padding: 20upx 20upx 20upx 20upx;
		display: flex;
		align-items: center;
	}

	.indexBar .indexBar-box {
		width: 40upx;
		height: auto;
		background: #fff;
		display: flex;
		flex-direction: column;
		box-shadow: 0 0 20upx rgba(0, 0, 0, 0.1);
		border-radius: 10upx;
	}

	.indexBar-item {
		flex: 1;
		width: 40upx;
		height: 40upx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 24upx;
		color: #888;
	}

	movable-view.indexBar-item {
		width: 40upx;
		height: 40upx;
		z-index: 9;
		position: relative;
	}

	movable-view.indexBar-item::before {
		content: "";
		display: block;
		position: absolute;
		left: 0;
		top: 10upx;
		height: 20upx;
		width: 4upx;
		background-color: #f37b1d;
	}

	.indexToast {
		position: fixed;
		top: 0;
		right: 80upx;
		bottom: 0;
		background: rgba(0, 0, 0, 0.5);
		width: 100upx;
		height: 100upx;
		border-radius: 10upx;
		margin: auto;
		color: #fff;
		line-height: 100upx;
		text-align: center;
		font-size: 48upx;
	}
	.address-attention{
		position: absolute;
		    right: 100rpx;
	}
	.address-attention-btn{
		padding: 2px 5px;
		border: 1px solid #00BEB7;
		font-size: 12px;
		    width: 50px;
			text-align: center;
	}
</style>
