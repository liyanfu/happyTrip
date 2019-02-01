<template>
<div>
	<aheader :title="name"></aheader>
	
	<div class="orderrecode_page">
		<mt-navbar v-model="selected">
		  	<mt-tab-item v-for="(o,index) in productTypeList" :key="index" :id="o.productTypeId" v-text="o.productTypeName"></mt-tab-item>
		</mt-navbar>
		<!-- tab-container v-model="selected"-->
		<mt-tab-container>
		  <mt-tab-container-item>
	    		<!-- <div class="defaulttable">
				<table>
					<tr class="firsttr">
						<th>商品名称</th>
						<th>投资金额</th>
						<th>订单状态</th>
						<th>创建时间</th>
					</tr>
					<tr v-if="orderList.length>0" v-for="(o,index) in orderList" :key="index">
						<td v-text="o.productName"></td>
						<td v-text="o.buyMoney"></td>
						<td v-text="o.status"></td>
						<td v-text="o.createTime"></td>
					</tr>
					<tr v-if="orderList.length === 0">
						<td colspan="4">暂无数据</td>
					</tr>
				</table>
			</div> -->
			<div class="wd_page_list" v-for="(o,index) in orderList" :key="index">
				<div>
					<span class="left" v-text="'商品名称：'+o.productName"></span>
					<span class="right" v-text="o.status"></span>
				</div>
				<div>
					<span class="left" v-text="'投资金额：'+o.buyMoney"></span>
					<span class="right file" v-if="o.status === '未支付'"><input class="upload" @change="add_img(o.orderId)" ref="avatarInput"  type="file"><i>上传凭证</i></span>
					<!-- <span class="right" v-if="o.submitStatus === 1">已上传</span> -->
				</div>
				<div><span class="left" v-text="o.createTime"></span></div>
			</div>
		  </mt-tab-container-item>
		  <!--<mt-tab-container-item v-if="productTypeList[1]!= null" :id="productTypeList[1].productTypeId">
		    <mt-cell v-for="(n,index) in 10" :key="index" :title="'测试 ' + n" ></mt-cell>
		  </mt-tab-container-item>-->
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
    		name:'购买记录',
    		orderList:[],
    		productTypeList:[],
    		selected:'',
			imgs: [],
			imgData: {
				accept: 'image/gif, image/jpeg, image/png, image/jpg',
			}
    }
  },
  created () {

  },
  mounted () {
	// this.init()
	this.getab();
  },
  methods:{
  	...mapActions([
		"getMyBuyOrderList",
		"getTopTabList",
		"submitOrderCredential"
	]),
	init(){
		this.getMyBuyOrderList({'typeId':this.selected}).then(res => {
			if(res.code === 0){
				this.orderList = res.orderList;
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
	getab(){
			this.getTopTabList({}).then(res => {
				if(res.code === 0){
					this.productTypeList=res.productTypeList;
					this.selected = this.productTypeList[0].productTypeId*1;
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
		add_img(orderId){
			let img1=event.target.files[0];
			//console.log(event)
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
			form.append("orderId", orderId);
			this.submitOrderCredential(form).then(res =>{
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
  },
  watch: {
      selected(newvalue,oldvalue) {
        if(newvalue !== oldvalue){
        		this.init()
        }
      }
    }
}
</script>

<style lang="stylus">
.orderrecode_page{
	padding-top:4rem;
	.mint-navbar {
		background: transparent;
		.mint-tab-item{
			&.is-selected{
				background url("../../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
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
			.gold{
				color:gold;
			}
			.gray{
				color:gray;
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
}
</style>