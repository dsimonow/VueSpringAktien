<template>
  <div id="app">
    <nav-content aktie='aktien'></nav-content>
    <router-view ></router-view>
  </div>
</template>

<script>
import NavContent from './components/NavContent.vue'

export default {
  name: 'App',
  components: {
    NavContent
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  
  margin-top: 60px;
  margin-right: 22%;
  margin-left: 22%;
}
</style>
