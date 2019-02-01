<template>
  <div class="online_page">
  	<mt-header class="aheader" fixed title="在线车库"></mt-header>
    <div class="onlinecar">
      <div class="carbox">
        <h6>正在内测中</h6>
        <span>即将正式开放！</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    
  },
  // computed: mapGetters([

  // ])

}
</script>
<style lang="stylus">
.online_page{
	.aheader{
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
.onlinecar {
  width 100%
  overflow hidden
  .carbox {
    width 80%
    text-align Center
    height 150px
    margin 175px auto
    background: rgba(219,196,107,0.3);
    color: #dbc46b;
    border: 0.1rem solid #79abab;
    border-radius: 1rem;
    padding-top 60px
    h6 {
      font-size 48px
      line-height 80px
    }
    span {
      font-size 20px
    }
  }
}
}

</style>
