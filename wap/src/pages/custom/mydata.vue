<template>
<div>
	<aheader :title="name"></aheader>
	<div class="defaultform">
		<mt-cell title="手机号" :value="user.userMobile"></mt-cell>
		
		<mt-cell title="支付宝姓名" :value="user.alipayName"></mt-cell>
		
		<mt-cell title="支付宝账号" :value="user.alipayMobile"></mt-cell>
		
		<mt-button class="defaultbtn" onclick="window.history.go(-1)">返回</mt-button>
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
    		name:'基本信息',
    		user:{}
    }
  },
  created () {

  },
  mounted () {
	this.init()
  },
  methods:{
  	...mapActions([
		"getMydata"
	]),
	init(){
		this.getMydata({}).then(res => {
			if(res.code === 0){
				this.user = res.user;
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
</style>