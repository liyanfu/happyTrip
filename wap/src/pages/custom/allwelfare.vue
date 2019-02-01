<template>
<div>
	<aheader :title="name"></aheader>
	<div class="defaultform">
		
		<mt-cell title="累计收益" :value="totalsProfitMoney+'元'"></mt-cell>
		
	</div>
	
	<div class="defaulttable">
		<table>
			<tr class="firsttr">
				<th>福利名称</th>
				<th>收益金额</th>
				<th>收益时间</th>
			</tr>
			<tr v-if="bonusList.length>0" v-for="(o,index) in bonusList" :key="index">
				<td v-text="o.operatorName"></td>
				<td v-text="o.operatorMoney"></td>
				<td v-text="o.createTime"></td>
			</tr>
			<tr v-if="bonusList.length === 0">
				<td colspan="3">暂无数据</td>
			</tr>
		</table>
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
    		name:'全民福利',
    		totalsProfitMoney:'',
    		bonusList:[]
    }
  },
  created () {

  },
  mounted () {
	this.init()
  },
  methods:{
  	...mapActions([
		"getAllWelfare"
	]),
	init(){
		this.getAllWelfare({}).then(res => {
			if(res.code === 0){
				this.totalsProfitMoney = res.totalsProfitMoney;
				this.bonusList = res.bonusList;
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