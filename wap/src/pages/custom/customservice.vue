<template>
<div>
	<aheader :title="name"></aheader>
	<div class="customservice">
		<canvas	 ref="webcode"></canvas>
	</div>
</div>
	
</template>

<script>
import { mapActions } from 'vuex'
import QRCode from 'qrcode'
import aheader from '@/components/base/aheader'
export default {
  components: {
    aheader
  },
  data () {
    return {
    		name:'客服',
    		customerInfo:''
    }
  },
  created () {

  },
  mounted () {
	this.init()
  },
  methods:{
  	...mapActions([
		"getCustomerInfo"
	]),
	init(){
		this.getCustomerInfo({}).then(res => {
			if(res.code === 0){
				this.customerInfo = res.customerInfo;
				//var _url = 'http://'+window.location.host+'/api/readImg?path='+this.customerInfo
				var _url = 'http://120.78.138.125:38888/api/readImg?path='+this.customerInfo
				QRCode.toCanvas(this.$refs.webcode,_url,{"width":"180px","height":"180px"}, function (error) {
		          if (error) console.error(error)
		            console.log('生成了web二维码讷!');
		          })
			
				return;
			}
			this.$toast({
			  message: res.msg
			});
		},res=> {
			this.$toast({
			  message: res.msg
			});
		})
	}
  }
}
</script>

<style lang="stylus">
.customservice{
	padding-top:5rem
	div{
		color:#fff;
	}
}
</style>