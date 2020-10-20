<template>
  <a-locale-provider :locale="locale">
    <div id="app">
      <router-view/>
    </div>
  </a-locale-provider>
</template>
<script>
  import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
  // import enquireScreen from '@/utils/device'

  export default {
    data () {
      return {
        locale: zhCN,
      }
    },
    created () {
    }
  }
</script>
<style>
  #app {
    height: 100%;
  }
::-webkit-scrollbar {
    width: 8px;
    height: 12px;
    background-color: #fff;
}

::-webkit-scrollbar-thumb {
    display: block;
    min-height: 12px;
    min-width: 8px;
    border-radius: 6px;
    background-color: rgb(217, 217, 217);
}

::-webkit-scrollbar-thumb:hover {
    display: block;
    min-height: 12px;
    min-width: 8px;
    border-radius: 6px;
    background-color: rgb(159, 159, 159);
}
</style>
