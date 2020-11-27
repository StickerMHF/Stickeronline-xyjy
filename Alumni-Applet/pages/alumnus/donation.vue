<template>
	<view>
		<cu-custom bgColor="bg-gradual-green1" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">校友捐赠</block>
		</cu-custom>
		<view class="contentBox">
			<view class="textBox">
				<view class="textInfo">详细信息</view>
				<view class="btn" @click="ToMiniProgram">校友捐赠</view>
			</view>
			<view class="dataView">
				<image class="image" src="http://www.imapway.cn/Alumni/static/home/banner22x.png" mode=""></image>
				<view class="title">1. 地质工程虚拟实验平台开发计划</view>
				<view class="contentText"> 
				随着互联网、大数据、人工智能和虚拟现实等信息技术的蓬勃发展， 学科专业与信息技术深度融合，虚拟仿真实验也飞速发展。通过虚拟仿真对实体实验进行模拟，展现不可视的结构或原理，完成各类预设的实验， 尤为重要的是可以在短时间内充分展现完整的实验过程，得到闭合的实验数据，拉近实验教学与工程实践、科技前沿的距离。此外，学生在虚拟环境中训练，可以重复实验、随时实验和随地实验，能用合乎逻辑的数据完成预定的实验报告，体会专业的原理。面对突如其来的新冠肺炎疫情，我系虚拟实验扮演了举足轻重的地位。但在虚拟实验制作和推广这场耗资大战中，政府、科技企业、大学、教师都参与了战争，一个关键主体“学生” 却一直被忽略。地质工程系为了调动学生（尤其是一年级新生）学习的积极性，将课程与课外的虚拟实验制作融合，由学生作为主体联合企业开发虚拟实验，这个过程中需要一定的经费支持。
				</view>
			</view>
			<view class="placeholder"></view>
			<view class="dataView">
				<image class="image" src="http://www.imapway.cn/Alumni/static/home/banner12x.png" mode=""></image>
				<view class="title">2. 地球物理系校企共建项目</view>
				<view class="contentText">
					地球物理系的发展和进步与广大校友的关注和支持是分不开的，希望能与各校友单位共建学生实践教学基地或研究生培养基地，充分发挥企业和高校各自的优势，为提升高校人才培养质量和企业的科研创新能力提供帮助。
				</view>
			</view>
			<view class="placeholder"></view>
			<view class="dataView">
				<image class="image" src="http://www.imapway.cn/Alumni/static/home/banner32x.png" mode=""></image>
				<view class="title">3. 测绘类实验室专项资助共建计划</view>
				<view class="contentText">
				测绘专业与先进仪器设备密不可分，新技术层出不穷，海量数据不断涌现，人工智能方兴未艾，为了让学生及时了解学生测绘新技术，拟与测绘企事业单位联合，共建实验室如下：
				</view>
				<view class="tow-level-title">（1）导航与位置服务实验室</view>
				<view class="contentText">
					配备国内外多类型导航定位设备与组件，用于学生参观、实习实验。
				</view>
				<view class="tow-level-title">（2）地信遥感实验室</view>
				<view class="contentText">
					安装多样化国产或国外先进地信、遥感软件，用于学生竞赛、二次开发、课程实习等。
				</view>
				<view class="tow-level-title">（3）地质灾害监测实验室</view>
				<view class="contentText">
					配备各类监测设备、传感器，计算机端口，安装高精度数据处理软件， 用于学生软硬件开发，服务于地质灾害监测。开展相关竞赛、课程实习等。
				</view>
				<view class="tow-level-title">（4）测绘创客工作室</view>
				<view class="contentText">
					配合《测绘创新创业》课程，购买相关图书、配备一定数量的计算机， 历届获奖成果展，用于指导教师创新创业指导。
				</view>
			</view>
			<view class="placeholder"></view>
			<view class="dataView">
				<image class="image" src="http://www.imapway.cn/Alumni/static/home/banner22x.png" mode=""></image>
				<view class="title">4. 安全工程实验室专项资助共建项目</view>
				<view class="contentText">
					安全工程实验室是面向安全工程本科专业和减灾防灾及防护工程博、硕士学科建设的实验室。主要包括安全工程检测实验室、安全工程热工实验室、火灾模拟与爆炸实验室。但随着国家对于安全生产的重视，目前企业对重大突发事故的应急管理越来越迫切，安全工程系为了迅速适应市场的需求，准备进行实验室或设备建设。
				</view>
				<view class="tow-level-title">（1）公共安全与应急虚拟仿真实验室建设</view>
				<view class="contentText">
					购置事故仿真模拟软件和公共安全与应急虚拟仿真软件，它可面向安全工程等多专业开设应急管理、应急救援等课程的实验教学，也可为企业提供相应的应急管理方案设计。
				</view>
				<view class="tow-level-title">（2）综合检测监控实验设备</view>
				<view class="contentText">
					在原有安全检测仪器的基础上进一步充实安全检测监控实验室，购置检验与使用全面罩呼吸器和复苏模拟人实验、安全巡检管理系统巡检、检测监控系统设计、建筑设备性能测试，建筑材料（阻燃材料）燃烧性能测试等，这些实验的开设一方面可以提高学生的实践能力，另一方面也可以更好地开展校企合作。
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				
			};
		},
		onLoad() {
			
		},
		methods: {
			ToMiniProgram(){
				uni.navigateToMiniProgram({
					appId: "wx57ef5751ef32a454",
					success(res) {
						// 打开成功
					},
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.contentBox{
		padding: 20rpx 0;
		background: #fff;
		.textBox{
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding:0 30rpx;
			.textInfo{
				border-left: 4px solid #00beb7;
				padding-left: 5px;
				height: 14px;
				line-height: 14px;
			}
			.btn{
				border-radius: 4px;
				padding: 4px 10px;
				color: #fff;
				background: #00beb7;
				text-align: center;
			}
		}
		.dataView{
			padding:20rpx 30rpx;
			.image{
				width:100%;
				height:300rpx;
			}
		}
		.title{
			font-size: 16px;
		}
		.tow-level-title{
			color: #999;
			text-indent: 40rpx;
		}
		.contentText{
			text-indent: 40rpx;
			color: #999;
		}
		.placeholder{
			width: 100%;
			height:20rpx;
			background: #f2f2f2;
		}
	}
</style>
