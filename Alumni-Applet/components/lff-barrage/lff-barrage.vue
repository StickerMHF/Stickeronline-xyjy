<template>
  <view class="barrageBox">
    <view
      class="danmu-li"
      v-for="(item, index) in listData"
      :class="item.type"
      :style="[item.style]"
      :key="index"
    >
      <view class="danmu-inner">
        <view class="user-box" :style="[item.bg]">
          <view class="user-img">
            <view class="img-box">
              <image :src="item.userPhoto"></image>
            </view>
          </view>
          <view class="userInfo">
            <text class="user-text cl1">
              {{ item.userName === null ? "校友" : item.userName }}
            </text>
            <text class="user-status cl1">
              {{ item.context }}
            </text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>
<script>
export default {
  props: {
    //rightToLeft leftToRight leftBottom
    type: {
      type: String,
      default: "rightToLeft",
    },
    list: {
      type: Array,
      default() {
        return [];
      },
    },
    minTime: {
      type: Number,
      default: 4,
    },
    maxTime: {
      type: Number,
      default: 9,
    },
    minTop: {
      type: Number,
      default: 0,
    },
    maxTop: {
      type: Number,
      default: 240,
    },
    hrackH: {
      //轨道高度
      type: Number,
      default: 40,
    },
  },
  data() {
    return {
      listData: [],
      bg: [
        "#c72f2fcc",
        "#4fd5ffcc",
        "#ff904fcc",
        "#4fa6ffcc",
        "#ff4fb1cc",
        "#4fffa6cc",
      ],
    };
  },
  watch: {
    list() {
      this.list.forEach(v => {
        v["type"] = "rightToLeft";
        v["bg"] = {
          background: `${this.bg[Math.floor(Math.random() * 5)]}`,
        };
        v["style"] = {
          // animationDuration: `9s`,
          animationDuration: `${Math.ceil(
            Math.floor(
              Math.random() * (this.maxTime - this.minTime + 1) + this.minTime
            )
          )}s`,
          top: `${Math.ceil(
            Math.random() * (this.maxTop - this.minTop + 1) + this.minTop
          )}px`,
        };
        this.listData.push(v);
      });
      console.log(this.listData);
    },
  },
  mounted() {
    //leftBottom 使用参数
    // if (this.type === 'leftBottom') {
    // 	this.hrackNum = Math.floor(this.maxTop / this.hrackH);
    // }
  },
  methods: {
    add(obj) {
      let data = {
        item: obj.item,
        name: obj.name,
        avatarUrl: obj.avatarUrl,
        id: Date.parse(new Date()),
        time: Math.ceil(
          Math.floor(
            Math.random() * (this.maxTime - this.minTime + 1) + this.minTime
          )
        ),
        type: this.type,
      };
      let objData = {
        context: data.item,
        userName: data.name,
        userPhoto: data.avatarUrl,
        type: "rightToLeft",
        bg: {
          background: `${this.bg[Math.floor(Math.random() * 5)]}`,
        },
        style: {
          // animationDuration: `${data.time}s`,
          animationDuration: `${Math.ceil(
            Math.floor(
              Math.random() * (this.maxTime - this.minTime + 1) + this.minTime
            )
          )}s`,
          top: `${Math.ceil(
            Math.random() * (this.maxTop - this.minTop + 1) + this.minTop
          )}px`,
        },
        delTime: Date.parse(new Date()) + data.time * 1200,
      };
      this.listData.push(objData);
      // if (this.type === 'leftBottom') {
      // 	let objData = {
      // 		item: data.item,
      // 		type: 'leftBottomEnter',
      // 		style: {
      // 			transition: `all 0.5s`,
      // 			animationDuration: `0.5s`,
      // 			transform: `translateX(0%)`,
      // 			bottom: `${this.minTop}px`
      // 		}
      // 	}
      // 	let listLen = this.listData.length;
      // 	let hrackNum = this.hrackNum;
      // 	for (let i in this.listData) {
      // 		if(this.listData[i].status === 'reuse'){ //重用
      // 			this.$set(this.listData,i,objData);
      // 		}else if(this.listData[i].status === 'reset'){ //重置
      // 			this.listData[i].style.transition = 'none';
      // 			this.listData[i].style.bottom = 0;
      // 			this.listData[i].status = 'reuse';
      // 		}else if(this.listData[i].status === 'recycle'){ //回收
      // 			this.listData[i].type = 'leftBottomExit';
      // 			this.listData[i].status = 'reset';
      // 		}else{
      // 			this.listData[i].style.bottom = parseInt(this.listData[i].style.bottom) + this.hrackH + 'px';
      // 		}
      // 		if(parseInt(this.listData[i].style.bottom) >= (this.maxTop - this.hrackH) && this.listData[i].status !== 'reset'){ //需要回收
      // 			this.listData[i].status = 'recycle';
      // 		}
      // 	}
      // 	if(listLen < hrackNum + 2){
      // 		this.listData.push(objData);
      // 	}
      // } else if (this.type === 'rightToLeft') {
      // let objData = {
      // 	context: data.item,
      // 	userName: data.name,
      // 	userPhoto: data.avatarUrl,
      // 	type: 'rightToLeft',
      // 	style: {
      // 		// animationDuration: `${data.time}s`,
      // 		animationDuration: `9s`,
      // 		top: `${Math.ceil(Math.random() * (this.maxTop - this.minTop + 1) + this.minTop)}px`
      // 	},
      // 	delTime: Date.parse(new Date()) + data.time * 1200
      // }
      // this.listData.push(objData);
      // for (let i in this.listData) {
      // 	if (this.listData[i].delTime <= Date.parse(new Date())) {
      // 		this.repaint(i, objData.type);
      // 		objData.type = '';
      // 		this.$set(this.listData, i, objData);
      // 		return
      // 	}
      // }

      // }
    },
    repaint(index, type) {
      setTimeout(() => {
        this.listData[index].type = type;
      }, 100);
    },
  },
};
</script>
<style></style>
<style lang="scss">
@keyframes leftBottomEnter {
  0% {
    transform: translateY(100%);
    opacity: 0;
  }

  100% {
    transform: translateY(0%);
    opacity: 1;
  }
}

@keyframes leftBottomExit {
  0% {
    transform: translateY(0%);
    opacity: 1;
  }

  100% {
    transform: translateY(-200%);
    opacity: 0;
  }
}

@keyframes leftToRight {
  0% {
    transform: translateX(-100%);
  }

  100% {
    transform: translateX(100%);
  }
}

@keyframes rightToLeft {
  0% {
    transform: translateX(100%);
  }

  100% {
    transform: translateX(-100%);
  }
}
.barrageBox {
  overflow: hidden;
  position: fixed;
  width: 100%;
  height: 1000rpx;
  pointer-events: none;
  top: 120rpx;
}
.danmu-li {
  position: absolute;
  width: 100%;
  transform: translateX(100%);
  // animation-timing-function: linear;
  animation: rightToLeft 5s linear 2s infinite normal;
  /* Firefox: */
  -moz-animation: rightToLeft 5s linear 2s infinite normal;
  /* Safari and Chrome: */
  -webkit-animation: rightToLeft 5s linear 2s infinite normal;
  /* Opera: */
  -o-animation: rightToLeft 5s linear 2s infinite normal;

  &.leftBottomEnter {
    animation-name: leftBottomEnter;
  }
  &.leftBottomExit {
    animation-name: leftBottomExit;
    animation-fill-mode: forwards;
  }

  &.rightToLeft {
    animation-name: rightToLeft;
  }

  &.leftToRight {
    animation-name: rightToLeft;
  }

  .danmu-inner {
    display: inline-block;

    .user-box {
      display: flex;
      padding: 3rpx 40rpx 3rpx 10rpx;
      background: rgba(0, 0, 0, 0.3);
      border-radius: 20px;
      align-items: center;
      //   justify-content: start;
      min-width: 300rpx;
      .userInfo {
        display: flex;
        flex-direction: column;
        justify-content: start;
        // align-items: center;
      }
      .user-img {
        .img-box {
          display: flex;

          image {
            width: 58rpx;
            height: 58rpx;
            background: rgba(55, 55, 55, 1);
            border-radius: 50%;
          }
        }
      }

      .user-status {
        margin-left: 10rpx;
        white-space: nowrap;
        font-size: 28rpx;
        font-weight: 400;
        color: rgba(255, 255, 255, 1);
      }

      .user-text {
        margin-left: 10rpx;
        // white-space: nowrap;
        font-size: 28rpx;
        font-weight: 400;
        // width: 80rpx;
        color: rgba(255, 255, 255, 1);
        height: 18px;
      }
    }
  }
}
</style>
