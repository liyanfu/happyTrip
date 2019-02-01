<template>
<div>
	<aheader :title="name"></aheader>
	<div class="companyinfo">
		<p v-html="companyInfo"></p>
	</div>
</div>
	
</template>

<script>
import { mapActions } from 'vuex'
import aheader from '@/components/base/aheader'
export default {
  components: {
    aheader
  },
  data () {
    return {
    		name:'公司介绍',
    		companyInfo:''
    }
  },
  created () {

  },
  mounted () {
	this.init()
  },
  methods:{
  	...mapActions([
		"getCompanyInfo"
	]),
	init(){
		this.getCompanyInfo({}).then(res => {
			if(res.code === 0){
				this.companyInfo = res.companyInfo;
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
.companyinfo{
	padding-top:5rem
	p{
		color:#fff;
		img{
			max-width:100%;
		}
		table{
			max-width:100%;
		}
	}
}
</style>