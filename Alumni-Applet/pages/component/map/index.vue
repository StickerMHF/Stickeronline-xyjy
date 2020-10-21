<template>
  <view>
    <uni-ec-canvas
      class="uni-ec-canvas"
      id="line-chart"
      canvas-id="multi-charts-line"
      :ec="ec"
    ></uni-ec-canvas>
  </view>
</template>

<script>
import * as echarts from '@/components/uni-ec-canvas/echarts'
import uniEcCanvas from "@/components/uni-ec-canvas/uni-ec-canvas";
import china from './china.json' // 引入中国地图数据
echarts.registerMap('china',china);
export default {
  data() {
    return {
      ec: {
        option: { // 进行相关配置
          backgroundColor: "#ffffff",
          tooltip: {
			  trigger: "item",
			  formatter: p =>{
				  let val = p.value;
				  if (window.isNaN(val)) {
					  val = 0;
				  }
				  let txtCon =
					  "<div style='text-align:center'>" + p.name + ":<br />台量：" + val.toFixed(2) + '</div>';
				  return txtCon;
			  }
		  }, // 鼠标移到图里面的浮动提示框
          dataRange: {
            show: false,
            min: 0,
            max: 1000,
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
			  emphasis: {
				  //对应的鼠标悬浮效果
				  show: true,
				  textStyle: {
					  color: "#000"
				  }
			  }
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
              }
            }
          },
          series: [
			 {
              type: 'scatter',
              coordinateSystem: 'geo' // 对应上方配置
            },
            {
              name: '数量', // 浮动框的标题
              type: 'map',
              geoIndex: 0,
              data: [{
                "name": "北京",
                "value": 999
              }, {
                "name": "上海",
                "value": 142
              }, {
                "name": "黑龙江",
                "value": 44
              }, {
                "name": "深圳",
                "value": 92
              }, {
                "name": "湖北",
                "value": 810
              }, {
                "name": "四川",
                "value": 453
              }]
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
  width: 750upx;
  height: 450upx;
  display: block;
}
</style>
