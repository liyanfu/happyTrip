<template>
  <div class="olddriver_page">
  	<mt-header class="aheader" fixed title="老司机"></mt-header>
    <div class="onlinecar">
      <div class="carbox">
        <div v-if="orderList.length > 0" v-for="(o,index) in orderList" :key="index">
        		<span v-text="o.productName"></span>：<span v-text="o.buyQuantity+'部'"></span>
        </div>
        <div v-if="orderList.length === 0">
        		暂无库存
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data(){
	return {
		orderList:[]
	}
  },
  mounted(){
  	this.init()
  },
  methods:{
  	...mapActions([
  		"getMyOrderList"
  	]),
  	init(){
  		this.getMyOrderList({}).then(res => {
  			if(res.code === 0){
  				this.orderList = res.orderList
				return;
			}	
			this.$toast({
			  message: res.msg
			});
  		}, res => {
  			this.$toast({
			  message: res.msg
			});
  		})
  	}
  }

}
</script>
<style lang="stylus">
.olddriver_page{
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
    margin 6rem auto
    background: rgba(219,196,107,0.3);
    color: #dbc46b;
    border: 0.1rem solid #79abab;
    border-radius: 1rem;
    padding 3rem 0 3rem 0
    div {
      font-size 1.2rem
      line-height:2rem
    }
  }
}
}

</style>
