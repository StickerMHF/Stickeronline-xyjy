<template>
  <div class="index">
    <div class="sticker-title">
      <span class="sticker-title-name">校友会</span>
    </div>
    <div class="sticker-menu">
      <ul class="sticker-menu-all">
        <li
          v-for="(item, index) in systemList"
          :key="index"
          @click="clickHandler(item)"
           class="sticker-menu-item"
        >
          <img :src="imageList[index]" alt="" />
          <span>{{ item.name }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import { getAction } from '@/api/manage'
import { mapActions } from 'vuex'
import Vue from 'vue'
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      targetTime: new Date().getTime() + 3900000,
      systemList: [],
      imageList: ['/img/cyrygl_jg.png', '/img/cyrygl_yw.png', '/img/cyrygl_zj.png'],
      item:null
    }
  },
  created() {},
  mounted() {
    this.init()
    // Vue.ls.remove('SYS_LIST')
  },
  methods: {
    ...mapActions(['LoginSubSyatem']),
    init() {
      getAction('stickeronline/sys/permission/queryListBySystemType', {}).then(res => {
        if (res.success) {
          this.systemList = res.result
        } else {
          this.$notification['error']({
            message: '请求失败',
            description: ((err.response || {}).data || {}).message || err.message || '请求出现错误，请稍后再试',
            duration: 4
          })
        }
      })
    },
    clickHandler(item) {
      let that = this
      that.LoginSubSyatem(item.id).then(()=>{//赋值systemid
        let that=this;
        this.item = item
        if (that.item!=undefined&&that.item != null && that.item != '') {
          if (that.item.url != null && that.item.url != '') {
            that.$router.push({ path: that.item.url})
          } else if (that.item.redirect != null && that.item.redirect != '') {
            that.$router.push({ path: that.item.redirect })
          } else {
            that.$router.push({ name: 'index' })
          }
        } else {
          that
            .LogoutSubSyatem()
            .then(() => {
              this.$router.push({ name: 'index' })
            })
            .catch(err => {
              that.$notification['error']({
                message: '子系统有误',
                description: ((err.response || {}).data || {}).message || err.message || '请求出现错误，请稍后再试',
                duration: 4
              })
            })
        }
      })
    }
  }
}
</script>

<style scoped>
.index {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: url(../../public/img/cyrygl_back.jpg);
}
.sticker-background {
  width: 100%;
  height: 100%;
  position: absolute;
}
.sticker-title {
  color: #2ab1e8;
  text-align: center;
}
.sticker-title-name {
  font-size: 36px;
  background-image: -webkit-linear-gradient(bottom, #41cfd1, #98ebec, #cefcfd);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.sticker-menu {
    height: 80%;
    width: 100%;
    overflow: auto;
    padding: 5% 0 2% 0;
}
.sticker-menu-all {
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
}
.sticker-menu-item {
  width: 300px;
  height: 200px;
  background: url(../../public/img/cyrygl_sys.png) center;
  background-repeat: no-repeat;
  background-size: 100%;
  margin: 30px;
  display: flex;
  flex-wrap: wrap;
  -webkit-box-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  align-items: center;
  cursor: pointer;
  padding: 30px 0;
  border-radius: 10px;
}
.sticker-menu-item:hover {
  transform: scale(1.1);
  box-shadow:0px 0px 10px #41cfd1;
}
.sticker-menu-item img {
  width: 91px;
  height: 67px;
}
.sticker-menu-item span {
  width: 100%;
  text-align: center;
  color: #fff;
  font-size: 24px;
}
.sticker-menu::-webkit-scrollbar {
  
    display: none;
}
</style>