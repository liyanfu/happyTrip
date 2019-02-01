<template>
<div>
	<aheader :title="name"></aheader>
	<div class="withdown_page">
		<mt-navbar v-model="selected">
		  <mt-tab-item id="1">提现</mt-tab-item>
		  <mt-tab-item id="2">提现明细</mt-tab-item>
		</mt-navbar>
		
		<!-- tab-container -->
		<mt-tab-container v-model="selected">
		  <mt-tab-container-item id="1">
		    <div class="wd_page_info">
		    		<mt-cell title="账号" :value="user.userMobile"></mt-cell>
		    		<mt-cell title="姓名" :value="user.userName"></mt-cell>
		    		<mt-cell title="可提现数量" :value="user.balance"></mt-cell>
		    		<mt-field label="提现数量" placeholder="请输入提现数量" v-model="money_num" @keyup.native="checkmoney"></mt-field>
		    		<mt-cell title="手续费" :value="user.withdrawFee"></mt-cell>
		    </div>
		    <mt-button class="defaultbtn" @click="confirm">申请提现</mt-button>
		  </mt-tab-container-item>
		  <mt-tab-container-item id="2">
		  	<div class="wd_page_list" v-for="(o,index) in withdrawDetailList" :key="index">
		  		<div><span class="left" v-text="'金额：'+o.withdrawMoney"></span><span class="right" v-text="o.status"></span></div>
		  		<div><span class="left" v-text="'手续费：'+o.withdrawFee"></span><span class="left" v-text="o.createTime"></span></div>
		  	</div>
		  </mt-tab-container-item>
		</mt-tab-container>
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
    		name:'我要提现',
    		user:{
    			'userMobile':'',
    			'userMobile':'',
    			'balance':'',
    			'alipayName':'',
    			'withdrawFee':'',
    		},
    		withdrawDetailList:null,
    		money_num:'',
    		selected:'1'
    }
  },
  created () {

  },
  mounted () {
	this.init();
	this.withdrawDetail();
  },
  methods:{
  	...mapActions([
		"getWithdrawInfo",
		"getWithdrawDetails",
		"withdrawSubmit"
	]),
	init(){
		this.getWithdrawInfo({}).then(res => {
			if(res.code === 0){
				this.user.userName = res.userName;
				this.user.balance = res.balance;
				this.user.userMobile = res.userMobile;
				this.user.withdrawFee = res.withdrawFee;
				this.user.alipayName = res.alipayName;
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
	},
	withdrawDetail(){
		this.getWithdrawDetails({}).then(res => {
			if(res.code === 0){
				this.withdrawDetailList = res.withdrawDetailList;
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
	},
	confirm(){
		var obj = {
			"money":this.money_num
		}
		if(obj.money === ''){
			this.$toast({
			  message: "提现金额不能为空"
			});
			return
		}
		this.withdrawSubmit(obj).then(res => {
			if(res.code === 0){
				this.$toast({
				  message: '申请成功'
				});
				this.money_num = '';
				this.selected = '2';
				this.withdrawDetail();
				this.init();
				//this.$router.push({'path':'/index/custom'})
				return;
			}
			this.$toast({
			  message: res.msg
			});
		},res => {
			this.$toast({
			  message: res.msg
			});
		})
	},
	checkmoney(){
		this.money_num = this.layerglobal.checkNum(this.money_num);
			if(this.money_num.length == 0) {
				this.money_num = '';
			}
	}
  }
}
</script>

<style lang="stylus">
.withdown_page{
	width:100%
	padding-top:3.5rem
	.mint-navbar {
		background: transparent;
		.mint-tab-item{
			&.is-selected{
				background url("../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
				border:none
				margin-bottom:0
				color:#000;
				.mint-tab-item-label{
					color:#000;
				}
			}
			color:#fff;
			font-size:1rem
			padding:0.7rem 0
			border:0.1rem solid #fff;
			border-radius:1rem
			.mint-tab-item-label{
				color:#fff;
				font-size:1rem
			}
		}
	}
	.mint-tab-container{
		margin-top:1rem
		.mint-tab-container-wrap{
			.mint-tab-container-item{
				display:flex;
				flex-wrap: wrap;
				justify-content: space-between;
				.wd_page_info{
					width:100%;
					.mint-cell{
						height:3.4rem;
						line-height:3.4rem;
						background: transparent;
						padding: 0 0.5rem;
						border-bottom:1px solid #fff;
						.mint-cell-wrapper{
							background: none;
							.mint-cell-title{
								text-align:left
								span{
									color:#fff
								}
							}
							.mint-cell-value{
								span{
									color:#fff
								}
							}
						}
					}
					.mint-field{
						.mint-cell-wrapper{
							.mint-cell-value{
								input{
									background: transparent;
									color:#fff;
									text-align: right;
								}
								.mint-field-clear{
									display:none
								}
							}
						}
					}
				}
				.defaultbtn{
						width:90%
						margin:1rem auto
						background: url("../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
					}
				.wd_page_list{
					width:90%;
					height:auto
					margin:0 auto
					background:rgba(219,196,107,0.3)
					border: 0.1rem solid #79abab;
    				border-radius: 1rem;
					margin-top:0.5rem
					>div{
						display: block;
						min-height:2rem
						&:after{
							content:'';
							width:0;height:0;
							clear:both;
							display:block;
							overflow:hidden;
						}
						span{
							color:#fff
							line-height:2rem;
							font-size:1.0rem
							padding:0 1rem
						}
					}
				}
			}
		}
	}
}
</style>