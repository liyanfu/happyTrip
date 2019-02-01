<template>
<div class="home-page">
	<mt-header class="aheader" fixed title="首页"></mt-header>
	<div class="abody">
		<div class="abanner"><img src="../../../static/image/homepage/banner.jpeg" alt=""></div>
		
		<div class="anav">
			<mt-navbar v-model="selected">
			  	<mt-tab-item v-for="(o,index) in productTypeList" :key="index" :id="o.productTypeId" v-text="o.productTypeName"></mt-tab-item>
			</mt-navbar>
			<!-- tab-container v-model="selected"-->
			<mt-tab-container>
			  <mt-tab-container-item>
			    <div v-for="(o,index) in productList" :key="index" class="item">
			    		<div class="ceng1">
			    			<div class="img"><img :src="localhost+o.productImgurl" alt="" /></div>
			    			<div class="introd">
				    			<div><span v-text="o.productName"></span><span class="red price" v-text="'¥'+o.saleMoney+'元'"></span></div>
				    			<div v-if="selected == '1'">收益期：<span v-text="o.rebatePeriods+'天'"></span></div>
				    			<div v-if="selected == '2'">收益期：<span v-text="o.rebatePeriods+'小时'"></span></div>
				    			<div>每天收益：<span v-text="o.rebateMoney+'元'"></span></div>
			    			</div>
			    		</div>
			    		<div class="ceng2">
			    			<div class="line_all">		    				
			    				<div class="line_procee" :style="{'width':o.rest+'%'}"></div>
			    			</div>
			    			<div class="line_num"><span v-text="'剩余'+o.rest+'%'"></span></div>
			    		</div>
			    		<div class="ceng3">
			    			<div class="btn" @click="purchase(o.saleMoney,o.productId)">购买</div>
			    		</div>
			    </div>
			  </mt-tab-container-item>
			  <!--<mt-tab-container-item v-if="productTypeList[1]!= null" :id="productTypeList[1].productTypeId">
			    <mt-cell v-for="(n,index) in 10" :key="index" :title="'测试 ' + n" ></mt-cell>
			  </mt-tab-container-item>-->
			</mt-tab-container>
		</div>
	</div> 
</div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
	data(){
		return {
			selected:'',
			productTypeList:[],
			productList:[],
			localhost:''
		}
	},
	mounted(){
		this.getab();
		//this.localhost = 'http://'+window.location.host+'/api/readImg?path='
		this.localhost = 'http://120.78.138.125:38888/api/readImg?path='
	},
	methods:{
		...mapActions([
			"getTopTabList",
			"getProductList"
		]),
		purchase(saleMoney,productId){
			this.$router.push({'path':'/index/purchase','query':{'saleMoney':saleMoney,'productId':productId}})	
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
		getproduct(){
			this.getProductList({"typeId":this.selected}).then(res => {
				if(res.code === 0){
					this.productList = res.productList;
					for(var i=0;i<this.productList.length;i++){
						var temp = this.productList[i]
						var sale = (temp.saleVolumes/temp.saleQuantity)
						//this.productList[i].precent = sale.toFixed(2)
						this.productList[i].rest = ((1 - sale)*100).toFixed(2)
					}
					return;
				}		
				this.$toast({
				  message: res.msg
				});
				
			}, res => {
				this.$toast({
				  message: res.msg
				});
			})
		}
	},
	watch: {
      selected(newvalue,oldvalue) {
        if(newvalue !== oldvalue){
        		this.getproduct()
        }
      }
    }
}
</script>
<style lang="stylus">
.home-page{
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
	.abody{
		padding:0.5rem;
		margin:3.2rem 0 5rem 0;
		.abanner{
// 			width:100%
// 			height:10rem;
// 			margin-bottom:0.7rem
// 			background: url("../../../static/image/homepage/banner.jpeg") no-repeat center center;
// 			border-radius 0.5rem;
			img{
				width:100%
				height:10rem;
				margin-bottom:0.7rem
				border-radius 0.5rem;
			}
		}
		.anav{
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
			.mint-tab-container{
				margin-top:1rem
				.mint-tab-container-wrap{
					.mint-tab-container-item{
						display:flex;
						flex-wrap: wrap;
						justify-content: space-between;
						.item{
							width:48%
							
							min-height:18rem
							height:auto
							border-radius:1rem
							display:inline-block
							background transparent url("../../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
							border:0.1rem solid #fff;
							margin-top:0.5rem;
							float:left;
							opacity: 0.8
							.ceng1{
								-width:100%;
								min-height:13rem;
								height: auto
								padding:0 1rem;
								background: rgba(0,0,0,0.5);
								border-radius 1rem 1rem 0 0;
								.img{
									width:100%;
									height:8rem
									img{
										width:100%;
										height:100%;
									}
								}
								.introd{
									text-align: left;
									div{
										font-size:0.9rem;
										color:#000;
										line-height:1.4rem
										overflow:hidden
										.price{
											word-break: break-all;
											-text-align: right;
											-float: right;
											display: inline-block;
											width: 100%;
										}
									}
								}
							}
							.ceng2{
								width:100%;
								height:1.6rem;
								line-height:1.6rem
								position: relative;	
								.line_all{
									width:100%;
									height:100%;
									background:#fff;
									border-radius:0.8rem;						
									text-align: center;
									.line_procee{
										-width:70%;
										height:100%;
										border-radius:0.8rem;
										background:url("../../../static/image/homepage/line_procee.jpg") repeat center center
									}
								}
								.line_num{
									position: absolute;
									left:0;
									top:0;
									width:100%
									
									span{
										color:#c7c7c7;								
										font-size:1rem
										z-index:10;						
									}
								}	
								
							}
							.ceng3{
								.btn{
									width:4.5rem;
									height:2.4rem;
									background: #000;;
									color:#fff;
									margin:0.5rem auto
									line-height:2.4rem
									font-size:0.9rem;
									border-radius:1rem
								}
							}
						}
					}
				}
			}
		}
	}
}
</style>
