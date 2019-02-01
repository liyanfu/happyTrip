<template>
<div>
	<aheader :title="name"></aheader>
	<div class="defaultform">
		<mt-cell title="团队人数" :value="teamNum+'人'"></mt-cell>
		
		<mt-cell title="累计团队金额" :value="teamTotalMoney+'元'"></mt-cell>
		
	</div>
	
	<div class="defaulttable">
		<table>
			<tr class="firsttr">
				<th>会员名称</th>
				<th>会员级别</th>
				<th>创建时间</th>
				<th>是否消费</th>
				<th>团队业绩</th>
			</tr>
			<tr v-if="list.length>0" v-for="(o,index) in list" :key="index">
				<td v-text="o.userName"></td>
				<td v-text="o.userLevel"></td>
				<td v-text="o.createTime"></td>
				<td v-text="o.isConsume"></td>
				<td v-text="o.teamsMoney"></td>
			</tr>
			<tr v-if="list.length === 0">
				<td colspan="5">暂无数据</td>
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
    		name:'我的团队',
    		teamNum:'',
    		teamTotalMoney:'',
    		list:[]
    }
  },
  created () {

  },
  mounted () {
	this.init()
  },
  methods:{
  	...mapActions([
		"getMyTeams"
	]),
	init(){
		this.getMyTeams({}).then(res => {
			if(res.code === 0){
				this.teamNum = res.teamNum;
				this.teamTotalMoney = res.teamTotalMoney;
				this.list = res.list;
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