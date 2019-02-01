<template>
  <div id="app">
    <router-view />
    <img src="../static/image/homepage/backg.jpg" class="appbackimg" id="Background">
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height:100%;
}
.appbackimg{
	position: fixed;
	width: 100%;
	height: 100%;
	display: block;
	z-index: -100;	
	top:0;
	right:0;
	left:0;
	bottom:0
}
</style>
