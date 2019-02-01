<template>
  <div class="indexbody">
  	<mt-header class="aheader404" fixed title="404"></mt-header>
    <div class="error404">
      <div class="errorbox">
        <h6>404</h6>
        <span>抱歉，您访问的页面不存在！</span>
      </div>
    </div>
    <a-footer></a-footer>
  </div>
</template>
<script>
import aFooter from '@/components/base/afooter'
export default {
  components: {
    aFooter
  },
  // computed: mapGetters([

  // ])

}
</script>
<style lang="stylus">
.aheader404{
	width:100%;
		height:3rem;
		background: url("../../static/image/homepage/line.jpeg") repeat center center ;
		text-align:center;
		line-height:3rem
		.mint-header-title{
			color:#ffffff;
			font-size:1.4rem
		}
}
.error404 {
  width 100%
  overflow hidden
  .errorbox {
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
      font-size 60px
      line-height 80px
    }
    span {
      font-size 20px
    }
  }
}
</style>
