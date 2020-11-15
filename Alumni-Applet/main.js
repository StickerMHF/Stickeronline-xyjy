import Vue from 'vue'

import App from './App'
import '@/mock'

import cuCustom from './colorui/components/cu-custom.vue'
Vue.component('cu-custom',cuCustom)
Vue.prototype.txKey = '5DVBZ-R4D6U-HRIVT-2473O-WXKTE-KFFAE';//使用在腾讯位置服务申请的key
Vue.prototype.referer = 'Alumni-Applet';//调用插件的app的名称
Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()

 



