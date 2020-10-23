<template>
	<view class="echart-map">
		<uni-ec-canvas class="uni-ec-canvas" id="line-chart" canvas-id="multi-charts-line" :ec="ec"></uni-ec-canvas>
	</view>
</template>

<script>
	import * as echarts from '@/components/uni-ec-canvas/echarts'
	import uniEcCanvas from "@/components/uni-ec-canvas/uni-ec-canvas";
	import china from './china.json' // 引入中国地图数据
	echarts.registerMap('china', china);
	export default {
		data() {
			return {
				ec: {
					option: { // 进行相关配置
						backgroundColor: "#ffffff",
						tooltip: {}, // 鼠标移到图里面的浮动提示框
						dataRange: {
							show: false,  //是否显示控制按钮
							min: 0,
							max: 300,
							text: ['High', 'Low'],
							realtime: true,
							calculable: true,
							color: ['orangered', 'yellow', 'lightskyblue']
						},
						geo: { // 这个是重点配置区
							map: 'china', // 表示中国地图
							roam: true,
							label: {
								normal: {
									show: false, // 是否显示对应地名
									textStyle: {
										color: 'rgba(0,0,0,0.4)'
									}
								},
								// emphasis: {
								// 	//对应的鼠标悬浮效果
								// 	show: true,
								// 	textStyle: {
								// 		color: "#000"
								// 	}
								// }
							},
							itemStyle: {
								normal: {
									borderColor: 'rgba(0, 0, 0, 0.2)'
								},
								emphasis: {
									areaColor: null,
									shadowOffsetX: 0,
									shadowOffsetY: 0,
									shadowBlur: 20,
									borderWidth: 0,
									shadowColor: 'rgba(0, 0, 0, 0.5)'
								},
								layoutCenter: ['48%', '50%'],//距离左右，上下距离的百分比
								layoutSize:'15%'//map百分比大小

							}
						},
						series: [{
								type: 'scatter',
								coordinateSystem: 'geo' // 对应上方配置
							},
							{
								name: '签约人数', // 浮动框的标题
								type: 'map',
								geoIndex: 0,
								data: [{name:"陕西",value:211},
										{name:"湖北",value:57},
										{name:"北京",value:37},
										{name:"广东",value:34},
										{name:"四川",value:26},
										{name:"江苏",value:21},
										{name:"山东",value:21},
										{name:"上海",value:17},
										{name:"浙江",value:15},
										{name:"河南",value:15},
										{name:"湖南",value:15},
										{name:"广西",value:12},
										{name:"重庆",value:11},
										{name:"河北",value:10},
										{name:"安徽",value:10},
										{name:"天津",value:9},
										{name:"甘肃",value:9},
										{name:"贵州",value:8},
										{name:"云南",value:7},
										{name:"新疆",value:6},
										{name:"辽宁",value:5},
										{name:"山西",value:4},
										{name:"吉林",value:3},
										{name:"黑龙江",value:3},
										{name:"江西",value:3},
										{name:"西藏",value:3},
										{name:"内蒙古",value:2},
										{name:"福建",value:2},
										{name:"青海",value:1},
										{name:"宁夏",value:1},
										{name:"海南",value:0},
										{name:"台湾",value:0},
										{name:"香港",value:0},
										{name:"澳门",value:0}]
							}
						]
					}
				}
			};
		},
		components: {
			uniEcCanvas
		}
	};
</script>
<style scoped>
	.uni-ec-canvas {
		width: 570upx;
		height: 450upx;
		display: block;
	}
</style>
