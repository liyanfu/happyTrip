<template>
<div>
	<aheader :title="name"></aheader>
	<div class="recharge_page">
		<mt-navbar v-model="selected">
		  	<mt-tab-item id="1" >我要充值</mt-tab-item>
			<mt-tab-item id="2" >充值记录</mt-tab-item>
		</mt-navbar>
		<!-- tab-container v-model="selected"-->
		<mt-tab-container v-model="selected">
		  <mt-tab-container-item id="1">
				<mt-field label="充值数量" placeholder="请输入充值数量" v-model="money_num" @keyup.native="checkmoney"></mt-field>
				
				<mt-button class="defaultbtn" @click="confirm">充值</mt-button>
				
				<div class="img" v-show="flag">
					<div class="tips">请扫描二维码进行支付<br>并填写您的随机码：<span v-text="rechargeCode"></span></div>
					<canvas	 ref="webcode"></canvas>
				</div>
		  </mt-tab-container-item>
		  
		  <mt-tab-container-item id="2">
<!-- 			  <div class="defaulttable">
			  	<table>
			  		<tr class="firsttr">
			  			<th>充值订单ID</th>
			  			<th>充值金额</th>
			  			<th>随机码</th>
			  			<th>状态</th>
						<th>创建时间</th>
						<th>上传转账凭证状态</th>
			  		</tr>
			  		<tr v-if="rechargeList.length>0" v-for="(o,index) in rechargeList" :key="index">
			  			<td v-text="o.rechargeId"></td>
			  			<td v-text="o.rechargeMoney"></td>
			  			<td v-text="o.rechargeCode"></td>
			  			<td v-text="o.status"></td>
						<td v-text="o.createTime"></td>
						<td v-text="o.submitStatus"></td>
			  		</tr>
			  		<tr v-if="rechargeList.length === 0">
			  			<td colspan="4">暂无数据</td>
			  		</tr>
			  	</table>
			  </div> -->
			  <div class="wd_page_list" v-for="(o,index) in rechargeList" :key="index">
			  	<div>
					<span class="left" v-text="'充值订单ID：'+o.rechargeId"></span>
					<span class="right" v-if="o.status === 0">待支付</span>
					<span class="right green" v-if="o.status === 1">已完成</span>
					<span class="right red" v-if="o.status === 2">异常</span>
				</div>
			  	<div><span class="left" v-text="'充值金额：'+o.rechargeMoney"></span>
					<span class="right file" v-if="o.status === 0"><input class="upload" @change="add_img(o.rechargeId)" ref="avatarInput" name="file"  type="file"><i>上传凭证</i></span>
					<span class="right" v-if="o.submitStatus === 1 && o.status === 1">已上传</span>
				</div>
				<div><span class="left" v-text="'随机码：'+o.rechargeCode"></span><span class="right" v-text="o.createTime"></span></div>
			  </div>
		  </mt-tab-container-item>
		</mt-tab-container>

	</div>
</div>
	
</template>

<script>
import QRCode from 'qrcode'
import { mapActions } from 'vuex'
import aheader from '@/components/base/aheader'
export default {
  components: {
    aheader
  },
  data () {
    return {
    		name:'我要充值',
			selected: '1',
    		money_num:'',
    		rechargeCode:'',
    		flag:false,
			rechargeList:[],
			imgs: [],
			imgData: {
				accept: 'image/gif, image/jpeg, image/png, image/jpg',
			}
    }
  },
  created () {

  },
  mounted () {
	this.init()
  },
  methods:{
  	...mapActions([
		"rechargeSubmit",
		"getRechargeList",
		"submitRechargeCredential"
	]),
	add_img(rechargeId){
		let img1=event.target.files[0];
		let type=img1.type;//文件的类型，判断是否是图片
		let size=img1.size;//文件的大小，判断图片的大小
		if(this.imgData.accept.indexOf(type) == -1){
			this.$toast({
			  message: "请选择我们支持的图片格式！"
			});
			return false;
		}
		if(size>3145728){
			this.$toast({
			  message: "请选择3M以内的图片！"
			});
			return false;
		}
		let form = new FormData(); 
		form.append('file',img1,img1.name);
		form.append("rechargeId", rechargeId);
		this.submitRechargeCredential(form).then(res =>{
			this.init()
			if(res.code === 0){
				this.$toast({
				  message: "上传成功"
				});
			}
			
		},res => {
			this.$toast({
			  message: "上传图片出错！"
			});
		})  
	},
	confirm(){
		var obj = {
			"money":this.money_num
		}
		if(obj.money === ''){
			this.$toast({
			  message: "充值金额不能为空"
			});
			return;
		}
		this.flag = false
		this.rechargeSubmit(obj).then(res => {
			if(res.code === 0){
				this.rechargeCode = res.rechargeCode;
				this.flag = true
				var _url = 'http://'+window.location.host+'/api/readImg?path='+res.qrCode
				QRCode.toCanvas(this.$refs.webcode,_url,{"width":"180px","height":"180px"}, function (error) {
		          if (error) console.error(error)
		            console.log('生成了web二维码讷!');
		          })
				this.money_num = '';
				return;
			}
			this.flag = false
			this.$toast({
			  message: res.msg
			});
		},res=> {
			this.flag = false
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
	},		
	init(){
		this.getRechargeList({}).then(res => {
			if(res.code === 0){
				this.rechargeList=res.rechargeList;
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
	}
  }
}
</script>

<style lang="stylus">
.recharge_page{
	padding-top:4rem
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
				font-size:0.8rem
				padding:0 0.5rem
			}
			.green{
				color:green;
			}
			.red{
				color:red;
			}
			.file{
				position: relative;
				.upload{
					width:4rem;
					height: 2rem;
					position: absolute;
					opacity: 0;
					z-idnex:1
					cursor: pointer;
				}
				i{
					width: 4rem;
					height: 1.6rem;
					display:inline-block;
					background: #888820;
					border: 1px solid #ffd700;
					color: #fff;
					text-align: center;
					line-height: 1.6rem;
					border-radius 1rem
					margin-top: 0.1rem;
				}
			}
			
		}
	}
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
	.defaultbtn{
		width:90%
		margin:1rem auto
		background: url("../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
	}
	.img{
		margin-top:3rem;
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