<template>
  <div class="index">
    <button @click='prev'>返回上一级</button>
    简介
  </div>
</template>
<script>
export default {
  name: 'Index',
  components: {
  },
  data() {
    return {
    }
  },
  created() {},
  mounted() {

  },
  methods: {
    prev(){
    this.$router.go(-1)
  }
  }
}
</script>

<style scoped>