<template>
	<view>
		<echarts :option="option" style="height: 300px;" @click="echartsClick"></echarts>
		<echarts-el :option="option" style="height: 300px;"></echarts-el>
		<button @click="updateClick">切换数据</button>
	</view>
</template>

<script>
	import Echarts from '@/components/echarts/echarts.vue'
	import EchartsEl from '@/components/echarts/echarts-el.vue'
	export default {
		data() {
			return {
				option: {},
				option2: {
					notMerge: true, // 自定义变量：true代表不合并数据，比如从折线图变为柱形图则需设置为true；false或不写代表合并
					tooltip: {
						trigger: 'axis',
						positionStatus: true,
						formatterStatus: true, // 自定义变量：是否格式化tooltip，设置为false时下面三项均不起作用
						formatterUnit: '元', // 自定义变量：数值后面的单位
						formatFloat2: true, // 自定义变量：是否格式化为两位小数
						formatThousands: true // 自定义变量：是否添加千分位
					},
					legend: {
						data: ['邮件', '手机']
					},
					grid: {
						left: '5%',
						right: '8%',
						bottom: '5%',
						containLabel: true
					},
					xAxis: [{
						type: 'category',
						data: ['周一', '周二', '周三', '周四', '周五']
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [{
							name: '邮件',
							type: 'bar',
							data: [120, 132, 101, 134, 90],
							// 自定义变量，以数组形式传递渐变参数
							linearGradient: [0, 0, 0, 1,
								[{
										offset: 0,
										color: '#2378f7'
									},
									{
										offset: 0.7,
										color: '#2378f7'
									},
									{
										offset: 1,
										color: '#83bff6'
									}
								]
							]
						},
						{
							name: '手机',
							type: 'bar',
							data: [220, 182, 191, 234, 290],
							// 自定义变量，以数组形式传递渐变参数
							linearGradient: [0, 0, 0, 1,
								[{
										offset: 0,
										color: '#0bac00'
									},
									{
										offset: 0.7,
										color: '#0dcb00'
									},
									{
										offset: 1,
										color: '#0fef00'
									}
								]
							]
						}
					]
				},
				option3: {
					notMerge: true, // 自定义变量：true代表不合并数据，比如从折线图变为柱形图则需设置为true；false或不写代表合并
					xAxis: {
						type: 'category',
						data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
					},
					yAxis: {
						type: 'value'
					},
					series: [{
						data: [120, 200, 150, 80, 70, 110, 130],
						type: 'bar',
						showBackground: true,
						backgroundStyle: {
							color: 'rgba(220, 220, 220, 0.8)'
						}
					}]
				}
			};
		},
		components: {
			Echarts,
			EchartsEl
		},
		onLoad() {
			
				var outname = ["南海诸岛", '北京', '天津', '上海', '重庆', '河北', '河南', '云南', '辽宁', '黑龙江', '湖南', '安徽', '山东', '新疆', '江苏', '浙江',
					'江西', '湖北', '广西', '甘肃', '山西', '内蒙古', '陕西', '吉林', '福建', '贵州', '广东', '青海', '西藏', '四川', '宁夏', '海南', '台湾', '香港', '澳门'
				];
			var outvalue = [0, 524, 13, 140, 75, 13, 83, 11, 19, 15, 69, 260, 39, 4, 31, 104, 36, 1052, 33, 347, 9, 157, 22, 4,
				18, 5, 6398, 41, 0, 484, 404, 22, 3, 5, 225
			];
			var outdata = [];
			for (var i = 0; i < outname.length; i++) {
				outdata.push({
					name: outname[i],
					value: outvalue[i]
				})
			}

			this.option  = {
				tooltip: {
					show: true,
					formatter: function(params) {
						return '&nbsp;&nbsp;' + params.name + '&nbsp;&nbsp;&nbsp;' + params.value + '人&nbsp;&nbsp;';
					}
				},
				visualMap: {
					type: 'piecewise',
					left: '15',
					bottom: '15',
					itemWidth: 27,
					itemHeight: 15,
					textStyle: {
						color: '#333333',
						fontSize: 14,
					},
					pieces: [{
						min: 500,
						label: '>500',
					}, {
						max: 500,
						min: 200,
						label: '200-500',
					}, {
						max: 200,
						min: 0,
						label: '<200',
					}, {
						value: 0,
						label: '无数据',
					}, ],
					inRange: {
						color: ['#B2CAE0', '#D2EAFF', '#8AC6FD', '#45A5F8']
					},
					outOfRange: {
						color: ['#999999']
					}
				},
				geo: {
					map: 'china',
					show: true,
					roam: false,
					label: {
						emphasis: {
							show: false
						}
					},
					itemStyle: {
						normal: {
							borderColor: 'rgba(0,63,140,0.2)',
							shadowColor: 'rgba(0,63,140,0.2)',
							shadowOffsetY: 20,
							shadowBlur: 30
						}
					}
				},
				series: [{
					type: 'map',
					map: 'china',
					aspectScale: 0.75,
					//zoom:1.1,
					label: {
						normal: {
							show: false,
						},
						emphasis: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							areaColor: '#B2CAE0',
							borderColor: '#fff',
							borderWidth: 1,
						},
						emphasis: {
							areaColor: '#FFAE00',
						}
					},
					data: outdata
				}]
			};
		},
		methods: {
			/**
			 * 点击事件
			 * @param {Object} params
			 */
			echartsClick(params) {
				console.log('点击数据', params)
			},
			/**
			 * 切换数据
			 */
			updateClick() {
				if (this.option === this.option2) {
					this.option = this.option3
				} else {
					this.option = this.option2
				}
			}
		}
	};
</script>

<style></style>
