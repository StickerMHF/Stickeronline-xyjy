<template>
	<view class="teachers-detail">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{content.name}}</block>
		</cu-custom>
		<scroll-view scroll-x class="bg-white nav  text-center" scroll-with-animation>
			<view class="cu-item" :class="index==tabCur?'text-green cur':''" v-for="(item,index) in tabList" :key="index" @tap="tabSelect"
			 :data-id="item.id">
				{{item.name||''}}
			</view>
		</scroll-view>
		<view v-if="tabCur==='0'" class="td-item td-grgk">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 基本信息
				</view>
			</view>
			<view class="td-item-content">
				<view class="cu-form-group">
					<view class="title">姓名：</view>
					<text>{{content.name||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">性别：</view>
					<text>{{content.sex||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">职称：</view>
					<text>{{content.rank||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">学历：</view>
					<text>{{content.education||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">毕业院校：</view>
					<text>{{content.byyx||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">电子邮箱：</view>
					<text>{{content.email||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">办公地址：</view>
					<text>{{content.bgdd||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">所在学院：</view>
					<text>{{content.college||''}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">联系电话：</view>
					<text>{{content.contact||''}}</text>
				</view>
				<!-- <view class="cu-form-group">
					<view class="title">出生年月：</view>
					<text>{{content.test}}</text>
				</view> -->
			</view>

			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 个人简介
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;" v-html="content.grjj">
				
			</view>
		</view>

		<view v-if="tabCur==='1'" class="td-item td-yjly">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 研究领域
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;" v-html="content.yjly">
			
			</view>

			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 科研项目
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;" v-html="content.kyxm">
				
			</view>
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 论文
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;" v-html="content.lw">
				
			</view>
		</view>

		<view v-if="tabCur==='2'" class="td-item td-kjcg">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 科技成果
				</view>
			</view>
			<view style="width: 80%;margin: 0 auto;"  v-html="content.kjcg">

			</view>

			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 荣誉奖励
				</view>
			</view>
			<view style="width: 80%;margin: 0 auto;" v-html="content.ryjl">

			</view>

		</view>
		<view class="teachers-bottom">
			
		</view>
	</view>
</template>

<script>
	import {
		getTeachersById
	} from '@/api/teachers.js'
	export default {
		data() {
			return {
				tabCur: '0',
				tabList: [{
					name: '个人概况',
					id: '0'
				}, {
					name: '研究领域',
					id: '1'
				}, {
					name: '科技成果',
					id: '2'
				}],
				content: {

				}
			}
		},
		onLoad(options) {
			// 初始化页面数据
			this.title = "";
			this.getTeachersById(options.id);
		},
		methods: {
			getTeachersById(id) {
				let param = {
					id: id
				};
				getTeachersById(param).then(data => {
					var [error, res] = data;
					if (res && res.data.success) {
						this.content = res.data.result;
					}
				});
			},
			tabSelect(e) {
				this.tabCur = e.currentTarget.dataset.id;
				// if(this.tabCur==1){
				// 	this.getNewsListData("viewCount");
				// }else{
				// 	this.getNewsListData();
				// }
			},
		}
	}
</script>

<style lang="scss" scoped>
	.td-item-content {
		p {
			text-indent: 2em;
			margin: 0 auto;
			line-height: 30px;
		}
	}
	.teachers-bottom{
		height: 100rpx;
		width: 100%;
		background: #ffffff;
	}
</style>
