<template>
	<view class="school">
		<cu-custom bgColor="bg-gradual-green1" :isBack="false">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>

		<view class="sch-his">
			<view class="bg-img bg-mask flex align-center" style="background-image: url('http://www.imapway.cn/Alumni/static/home/banner22x.png');height: 300upx;background-size: 100% 100%;"></view>
			<scroll-view scroll-x class="bg-white nav text-center">
				<view class="cu-item" :class="index==tabCur?'text-green1 cur':''" v-for="(item,index) in tabList" :key="item.id" @tap="tabSelect"
				 :data-id="item.id">
					{{item.name}}
				</view>
			</scroll-view>
			<view class="sch-his-content" v-if="tabCur==0">
				<p>长安大学直属国家教育部，是国家首批“211工程”重点建设大学、国家“985优势学科创新平台”建设高校、国家“双一流”建设高校。</p>
				<p>学校创建于新中国百业待兴之时，壮大于改革浪潮迭起之际，兴盛于高等教育强国大势之中，与共和国同向同行近七十载。自1951年起，学校前身西安公路学院、西安地质学院、西北建筑工程学院相继成立。1956年开始招收外国留学生。1978年开始招收硕士研究生。1983年开始招收博士研究生。1997年成为首批“211工程”建设大学。2000年三校合并，组建长安大学，掀开学校跨越式发展新篇章。2005年以来，教育部先后与交通运输部、陕西省人民政府、国土资源部（现自然资源部）、住房和城乡建设部签署共建长安大学协议，“四部一省”共建长安大学的办学格局正式形成。2011年入选国家“985优势学科创新平台”建设高校。2017年入选世界一流学科建设高校，迈向新时代，踏上新征程。</p>
				<p>学校现有9个一级学科博士点，33个一级学科硕士点。有9个博士后科研流动站。工程学、地球科学、材料科学、环境科学与生态学等4个学科进入ESI全球前1%。现有82个本科专业，其中23个专业入选国家“双万计划”一流建设专业，17个专业通过国家工程教育认证。学校稳居中国大学百强，土木与交通学科雄居U.S.News全球大学第66名，被誉为公路交通人才培养的“黄埔军校”、科技创新的“金名片”。</p>
			</view>
			<view class="sch-his-content" v-if="tabCur==1">
				<p>公路学院（公路科学研究院)是教育部直属的长安大学“211工程”、“985工程优势学科创新平台”重点建设的学院，历经西安公路学院公路系、西安公路交通大学公路工程学院、长安大学公路学院。60年以来，学院视富民兴邦为天职，以发展公路交通科技为己任，紧密围绕国家经济建设，积极组织开展公路建设的重大课题和关键技术研究，为我国公路交通科技事业和基础建设作出了突出贡献，得到了党和国家领导人及国务院有关部委的高度评价。</p>
				<p>学院人才济济，名师荟萃，现设有四所两部三办四个中心，教职工283人（专任教师230人，工程实验、管理人员53人），其中博士生导师65人，硕士生导师183人。93%的教师和研究人员具有硕士以上学位（其中，238人具有博士学位）。学院现有教授72人，副教授96人。</p>
			<p>长风破浪会有时，直挂云帆济沧海。学院广大师生员工开拓创新，开放求是，团结协作，力争把公路学院(公路科学研究院)建成特色鲜明、优势突出、国内领先、世界一流的高水平研究型学院。</p>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tabCur: 0,
				tabList: [{
					name: '学校介绍',
					id: 0
				}, {
					name: '院史介绍',
					id: 1
				}],
			};
		},
		onLoad(options) {
			// 初始化页面数据
			this.title=options.title;
		},
		methods: {

			tabSelect(e) {
				this.tabCur = e.currentTarget.dataset.id;
			}
		}
	};
</script>

<style lang="scss">
	p{
		text-indent:2em ;
		margin:10px auto;
		line-height:30px;
	}
	.sch-his-content{
		text-indent:2em ;
	}
	.school {
		background-color: #ffffff;
	}

	.sch-his {
		padding: 10px;
	}
</style>
