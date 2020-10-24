<template>
	<view class="teachers-detail">
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{content.name}}</block>
		</cu-custom>
		<scroll-view scroll-x class="bg-white nav  text-center" scroll-with-animation>
			<view class="cu-item" :class="index==tabCur?'text-green cur':''" v-for="(item,index) in tabList" :key="index" @tap="tabSelect"
			 :data-id="item.id">
				{{item.name}}
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
					<text>{{content.name}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">性别：</view>
					<text>{{content.sex}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">职称：</view>
					<text>{{content.rank}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">学历：</view>
					<text>{{content.education}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">毕业院校：</view>
					<text>{{content.byyx}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">电子邮箱：</view>
					<text>{{content.email}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">办公地址：</view>
					<text>{{content.bgdd}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">所在学院：</view>
					<text>{{content.college}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">联系电话：</view>
					<text>{{content.contact}}</text>
				</view>
				<view class="cu-form-group">
					<view class="title">出生年月：</view>
					<text>{{content.test}}</text>
				</view>
			</view>

			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 个人简介
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;">
				<p>特殊土主要涉及：季节性冻土、黄土、膨胀土与盐渍土等，工程应用主要涉及：输水渠道渠基稳定、新疆坎儿井保护、市政工程海绵城市建设、黄土湿陷性评价、黄土增湿变形本构模型等方面。</p>
				<p> 当前以第一作者在《Soils and
					Foundations》、《岩土力学》、《四川大学学报（工程科学版）》、《农业工程学报》、《灌溉排水学报》、《水资源与水工程学报》等期刊发表论文10篇，其中SCI收录1篇，EI收录6篇。申请专利2项，软件著作权2项。参编国家标准1部。参编专著2部。
					目前指导硕士研究生6名。</p>
				<p>欢迎具备地质工程、岩土工程、土木工程、水利工程等学科相关背景的同学报考！</p>
			</view>
		</view>

		<view v-if="tabCur==='1'" class="td-item td-yjly">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 研究领域
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;">
				<p>主要从事黄土、季节性冻土、膨胀土、盐渍土等特殊土的工程性质及其应用研究，当前主要研究方向如下：</p>
				<p> 1 特殊土渠道渠基稳定及工程灾变机理研究</p>
				<p>	2 吐鲁番黄土的工程性质及坎儿井保护研究</p>
				<p>	3 新疆伊犁大厚度湿陷性黄土工程性质及湿陷性评价研究</p>
				<p>	4 特殊土地区海绵城市建设市政工程结构优化设计研究</p>
			</view>

			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 科研项目
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;">
				<p>“冻土-衬砌结构”作用机制研究及渠道结构优化，中国博士后基金，主持，在研。</p>
				<p> “冻融-长期渗透”作用下黄土渠基性质劣化与结构异化带研究，陕西省自然科学基金（青年），主持，在研。</p>
				<p>	市政工程湿陷性黄土地基处理关键技术与示范（湿陷性黄土海绵城市综合管廊与生态滤沟关键技术），陕西省科技厅，纵向，60万，主要参与，2019.01-2021.12，在研。</p>
				<p>	安康机场膨胀土高填方地基变形规律及机理研究，安康市科技局，22万，主要参与，2019.12-2021.12，在研。</p>
			
			</view>
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 论文
				</view>
			</view>
			<view class="td-item-content" style="padding: 0 20px;background: #ffffff;">
				<p>以第一作者发表论文：</p>
				<p>安鹏; 张爱军; 邢义川; 倪万魁; 张博. 伊犁深厚湿陷性黄土浸水入渗及沉降变形特征分析,岩土力学,2017,38(02):557-564 (EI)</p>
				<p>安鹏; 邢义川;王松鹤;程大伟. 坎儿井隧洞剥落破坏原因与防渗加固，农业工程学报, 2018, 34(9): 138-144 (EI)</p>
			
			</view>
		</view>

		<view v-if="tabCur==='2'" class="td-item td-kjcg">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 科技成果
				</view>
			</view>
			<view style="width: 80%;margin: 0 auto;">

			</view>

			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 荣誉奖励
				</view>
			</view>
			<view style="width: 80%;margin: 0 auto;">

			</view>

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

<style lang="scss">
	.td-item-content {
		p {
			text-indent: 2em;
			margin: 0 auto;
			line-height: 30px;
		}
	}
</style>
