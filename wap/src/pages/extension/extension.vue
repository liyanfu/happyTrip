<template>
  <div class="extension_page">
  	<mt-header class="aheader" fixed title="我要推广"></mt-header>
    <div class="content">
    		<div>我的ID：<span v-text="userInfo.userName"></span></div>
    		<div>我为快乐出行代言</div>
    		<div class="img">
    			<canvas	 ref="webcode"></canvas>
    		</div>
     </div>
  </div>
</template>
<script>
import QRCode from 'qrcode'
import { mapActions } from 'vuex'
export default {
  data () {
    return {
    		imgimg:'',
    		userInfo:{'userName':''}
    }
  },
  mounted(){
  	this.init();
  	this.getMyInfoclick();
  },
  methods:{
  	...mapActions([
		"myPromote",
		"getMyInfo"
	]),
  	init(){
		this.myPromote({}).then(res => {
			if(res.code === 0){
				this.imgimg = res.url
				QRCode.toCanvas(this.$refs.webcode,this.imgimg,{"width":"180px","height":"180px"}, function (error) {
		          if (error) console.error(error)
		            console.log('生成了web二维码讷!');
		          })
				return
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
	getMyInfoclick(){
		this.getMyInfo({}).then(res=>{
			if(res.code === 0){
				this.userInfo = res.userInfo;
				return;
			}
			this.$toast({
			  message: res.msg
			});
		},res =>{
			this.$toast({
			  message: res.msg
			});
		})
	}
  }

}
</script>
<style lang="stylus">
.extension_page{
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
	.content{
		padding-top:8rem
		color:#fff;
		font-size:1.4rem
		line-height:2.4rem;
		.img{
			margin-top:3rem;
		}
	}
}

</style>
