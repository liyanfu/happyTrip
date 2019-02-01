<template>
<div>
	<aheader :title="name"></aheader>
	<div class="defaultform purchase_page">
		<mt-cell title="支付金额" :value="saleMoney"></mt-cell>
		
		<mt-radio
		  align="right"
  		  title="支付方式"
		  v-model="value"
		  :options="paymentList">
		</mt-radio>
		<mt-button class="defaultbtn" @click="purchaseclick" :disabled="btndisable">去付款</mt-button>
		
		<div class="img" v-show="flag">
			<div class="tips">请扫描二维码进行支付<br>并填写您的随机码：<span v-text="randomCode"></span></div>
    			<canvas	 ref="webcode"></canvas>
    		</div>
	</div>
</div>
	
</template>

<script>
import QRCode from 'qrcode'
import { mapActions } from 'vuex'
import aheader from '@/components/base/aheader'
export default {
  name:'purchase',
  components: {
    aheader
  },
  data () {
    return {
    		name:'付款',
    		money:100,
    		paymentList:[],
    		value:'',
    		saleMoney:0,
    		productId:'',
    		flag : false,
			oneflag:true,
    		randomCode:'',
    		btndisable:false,
			orderId:null
    }
  },
  created () {

  },
  mounted () {
  	this.saleMoney = this.$route.query.saleMoney
  	this.productId = this.$route.query.productId
	this.init()
  },
  methods:{
  	...mapActions([
		"getPaymentList",
		"payOrder"
	]),
	init(){
		this.getPaymentList({}).then(res => {
			if(res.code === 0){
				for(var i=0;i<res.paymentList.length;i++){
					var obj = {
						'label':res.paymentList[i].paymentName,
						'value':res.paymentList[i].paymentKey,
						'paymentId':res.paymentList[i].paymentId
					}
					this.paymentList.push(obj)
				}
				this.value = this.paymentList[0].value
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
	purchaseclick(){
		var obj = {
			'productId':this.productId,
			'paymentKey':this.value,
			'orderId':this.orderId
		}
		this.btndisable = true
		this.flag = false
		this.payOrder(obj).then(res =>{
			this.btndisable = false
			if(res.code === 0 && res.qrCode != undefined){
				this.flag = true
				this.randomCode = res.randomCode
				this.orderId = res.orderId
				//var _url = 'http://'+window.location.host+'/api/readImg?path='+res.qrCode
				var _url = 'http://120.78.138.125:38888/api/readImg?path='+res.qrCode
				QRCode.toCanvas(this.$refs.webcode,_url,{"width":"180px","height":"180px"}, function (error) {
		          if (error) console.error(error)
		            console.log('生成了web二维码讷!');
		        })
			}else{
				if(res.code === 0){
					this.$toast({
					  message: "支付成功"
					});	
					this.$router.push({'path':'/index/home'})
				}else{
					this.$toast({
					  message: res.msg
					});
				}
			}
		},res => {
			this.btndisable = false
			this.$toast({
			  message: res.msg
			});
		})
	}
  },
  watch: {
    value(newvalue,oldvalue) {
      if(newvalue === 'PAYMENT_ALIPAY_KEY' && this.oneflag === false){
      	  this.flag = true
      }else{
		  this.flag = false
	  }
	  this.oneflag = true
    }
  }
  
}
</script>

<style lang="stylus">
.purchase_page{
	.img{
		margin-top:3rem;
		padding-bottom:4rem
		.tips{
			width:80%;
			margin:0 auto;
			line-height:2rem
			font-size:1.2rem;
			color:#fff;
			span{
				color:red
			}
		}
	}
}
</style>