<template>
<mt-header fixed :title="title" class="aheaderbox">
  <a onclick="window.history.go(-1)" slot="left">
    <mt-button icon="back">返回</mt-button>
  </a>
</mt-header>
<!--<mt-header title="多个按钮" class="aheaderbox">
  <router-link to="/" slot="left">
    <mt-button icon="back">返回</mt-button>
    <mt-button @click="handleClose">关闭</mt-button>
  </router-link>
  <mt-button icon="more" slot="right"></mt-button>
</mt-header>-->
<!--<mt-header fixed title="固定在顶部"></mt-header>-->
</template>

<script>
export default{
	props:['title']
}
</script>

<style lang="stylus">
.aheaderbox{
	width:100%;
	height:3rem;
	background: url("../../../static/image/homepage/line.jpeg") repeat center center ;
	text-align:center;
	line-height:3rem
	.mint-header-title{
		color:#ffffff;
		font-size:1.4rem
	}
}
</style>