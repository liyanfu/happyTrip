<template>
<mt-tabbar v-model="selected" class="afooter" fixed>
	<router-link :to="{'path':'/index/home'}" tag="a">
	  <mt-tab-item id="foot1">	
	    <img slot="icon" src="../../../static/image/homepage/foot1.jpg">
	    首页	    
	  </mt-tab-item>
  </router-link>
  <router-link :to="{'path':'/index/olddriver'}" tag="a">
	  <mt-tab-item id="foot2">
	    <img slot="icon" src="../../../static/image/homepage/foot2.jpg">
	    老司机
	  </mt-tab-item>
  </router-link>
   <router-link :to="{'path':'/index/extension'}" tag="a">
  <mt-tab-item id="foot3" class="bigimg">
    <img slot="icon" src="../../../static/image/homepage/foot3.jpg">
  </mt-tab-item>
  </router-link>
  <router-link :to="{'path':'/index/online'}" tag="a">
	  <mt-tab-item id="foot4">
	    <img slot="icon" src="../../../static/image/homepage/foot4.jpg">
	    在线车库
	  </mt-tab-item>
  </router-link>
  <router-link :to="{'path':'/index/custom'}" tag="a">
	  <mt-tab-item id="foot5">	  	
		    <img slot="icon" src="../../../static/image/homepage/foot5.jpg">
		    个人中心
	  </mt-tab-item>
  </router-link>
</mt-tabbar>
</template>

<script>
export default {
  data () {
    return {
    		selected:'foot1'
    }
  },
  mounted(){
  	
  }
}
</script>

<style lang="stylus">
.afooter{
	width:100%;
	height:4rem;
	background: url("../../../static/image/homepage/line.jpeg") repeat center center ;
	&>a{
		display: block;
    		flex: 1;
    		&.router-link-active{
    			.mint-tab-item-label{
				color:#fff
			}
    		}
	}
	.mint-tab-item{
		&.is-selected{
			background:none
			.mint-tab-item-label{
				color:#fff
			}
		}
		&.bigimg{
			.mint-tab-item-icon{
				width:4rem;
				height:3.5rem;
				/*position: relative;
				img{
					position absolute
					width:5.5rem;
					height:4.5rem;
					top:-1rem
					left:-1.2rem
				}*/
			}
		}
		.mint-tab-item-icon{
			width:3rem;
			height:1.8rem;
		}
		.mint-tab-item-label{
			color:rgb(0,111,120)
			font-size:0.8rem
		}
	}
}
</style>