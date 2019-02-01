<template>
	<div class="custom_page">
		<mt-header class="aheader" fixed title="个人中心">
			<mt-button slot="right" @click="sheetVisible = true"><span class="icon iconfont">&#xe627;</span></mt-button>
		</mt-header>
		
		<mt-actionsheet
		  :actions="actions"
		  v-model="sheetVisible">
		</mt-actionsheet>
		
		<div class="topheader">
				<div class="databox">
					<div class="ceng1">
						<div class="logo">
							<img src="../../../static/image/homepage/tou.jpeg" alt="" />
						</div>
						<div class="name">
							<div class="user">ID： <span v-text="userInfo.userName"></span>
								<!-- <router-link tag="a" to="/recharge"><span class="recharge">充值</span></router-link> -->
							</div>
							<div class="balance">余额： <span v-text="userInfo.walletBalance"></span>元
								<!-- <router-link tag="a" to="/withdown"><span class="withdown">提现</span></router-link> -->
							</div>
						</div>
					</div>
					<div class="cengzhong">
						<router-link tag="a" to="/recharge"><span class="recharge">充值</span></router-link>
						<router-link tag="a" to="/withdown"><span class="withdown">提现</span></router-link>
					</div>
					<div class="ceng2">
						<table>
							<tr class="topline">
								<td>总收益</td>
								<td>直推人数</td>
								<td>团队人数</td>
							</tr>
							<tr>
								<td v-text="userInfo.totalProfit"></td>
								<td v-text="userInfo.recommendNum"></td>
								<td v-text="userInfo.teamNum"></td>
							</tr>
							<tr class="topline">
								<td>团队总业绩</td>
								<td>今日直推人数</td>
								<td>今日团队业绩</td>
							</tr>
							<tr>
								<td v-text="userInfo.teamTotalMoney"></td>
								<td v-text="userInfo.todayRecomendNum"></td>
								<td v-text="userInfo.todayTeamMoney"></td>
							</tr>
						</table>
					</div>
				</div>
		</div>
		
		<div class="cbody">
			<ul class="content">
				<li>
					<router-link to="/index/mydata" tag="a">
						<span class="icon iconfont">&#xe611;</span>
						<h3>我的资料</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/carprofit" tag="a">
						<span class="icon iconfont">&#xe644;</span>
						<h3>汽车收益</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/allwelfare" tag="a">
						<span class="icon iconfont">&#xe68e;</span>
						<h3>全民福利</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/orderrecode" tag="a">
						<span class="icon iconfont">&#xe63c;</span>
						<h3>购买记录</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/myteam" tag="a">
						<span class="icon iconfont">&#xe654;</span>
						<h3>我的团队</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/globalbonus" tag="a">
						<span class="icon iconfont">&#xe60c;</span>
						<h3>全球分红</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/teamleader" tag="a">
						<span class="icon iconfont">&#xe663;</span>
						<h3>团队领导奖</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/speciallbonus" tag="a">
						<span class="icon iconfont">&#xe6cc;</span>
						<h3>特别贡献奖</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/company" tag="a">
						<span class="icon iconfont">&#xe609;</span>
						<h3>公司介绍</h3>
					</router-link>
				</li>
				<li>
					<router-link to="/index/customservice" tag="a">
						<span class="icon iconfont">&#xe601;</span>
						<h3>客服</h3>
					</router-link>
				</li>
				<li></li><li></li>
			</ul>
		</div>
	</div>
</template>

<script>
import { mapActions } from 'vuex'
export default{
	data(){
		return{
			userInfo:{},
			sheetVisible:false,
			actions:[{
				'name':'确定退出',
				'method':this.logoutclick
			}]
		}
	},
	mounted(){
		this.getMyInfoclick();	
	},
	methods:{
		...mapActions([
			"logout",
			"getMyInfo"
		]),
		logoutclick(){
			this.logout({}).then(res => {
				this.layerglobal.removeCookie('token')
				this.$router.push({'path':'/login'})
				if(res.code === 0){
					return
				}
				this.$toast({
				  message: res.msg
				});
			},res=> {
				this.layerglobal.removeCookie('token')
				this.$router.push({'path':'/login'})
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
.custom_page {
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
		.mint-header-button{
			.mint-button-text{
				span{
					font-size:1.4rem
				}
			}
		}
	}
	.mint-actionsheet{
		.mint-actionsheet-list{
			.mint-actionsheet-listitem{
				color:red;
			}
		}
	}
	.topheader{
			width:100%;
			height:3rem;
			margin:3rem 0 10rem 0
			background: url("../../../static/image/homepage/line.jpeg") repeat center center ;
			.databox{
				width:94%;
				height:13rem;
				background: url("../../../static/image/homepage/line.jpeg") repeat center center ;
				border-radius:1rem;
				margin:0rem auto
				.ceng1{
					width:100%;
					height:5.5rem;
					display:flex;
					align-items: stretch;
					>div{
						height:100%;
					}
					.logo{
						width:30%
						text-align:Center;
						img{
							width:4rem;
							display:inline-block
							border-radius:50%;
							margin: 1rem auto 0;
						}
					}
					.name{
						width:66%
						font-size:1rem;
						color:#fff;line-height:1.4rem
						text-align:left;
						padding-left:0.5rem
						.user{
							width:100%;
							height:1.6rem
							margin-top:2rem
							white-space:nowrap;
							text-overflow:ellipsis;
							overflow:hidden;
// 							.recharge{
// 								width:4rem;
// 								height:1.2rem;
// 								display:inline-block;
// 								float:right;
// 								background: #888820;
// 								border:1px solid gold;
// 								opacity: 0.8;
// 								text-align:Center;
// 								line-height:1.2rem;
// 								color:#fff;
// 								border-radius:1rem
// 								margin-right:1rem;
// 							}
						}
						.balance{
							width:100%;
							height:1.6rem
							white-space:nowrap;
							text-overflow:ellipsis;
							overflow:hidden;
// 							.withdown{
// 								width:4rem;
// 								height:1.2rem;
// 								display:inline-block;
// 								float:right;
// 								background: #888820;
// 								border:1px solid gold;
// 								opacity: 0.8;
// 								text-align:Center;
// 								line-height:1.2rem;
// 								color:#fff;
// 								border-radius:1rem
// 								margin-right:1rem;
// 								margin-top:0.3rem;
// 							}
						}
					}
				}
				.cengzhong{
					width:86%;
					margin:0 auto
					height:1.6rem;
					display:flex;
					justify-content space-around
					a{
						span{
							width:4rem;
							height:1.2rem;
							display:inline-block;
							background: #888820;
							border:1px solid gold;
							opacity: 0.8;
							text-align:Center;
							line-height:1.2rem;
							color:#fff;
							border-radius:1rem
						}
					}
				}
				.ceng2{
					width:86%;
					margin:0 auto
					table{
						width:100%;
						color:#fff;
						td{
							font-size:0.8rem
							line-height:1.4rem
						}
						.topline{
							border-top:1px solid #868686
						}
					}
				}
			}
		}
	.cbody{
		padding:0.5rem;
		margin:5rem 0 5rem 0;
		.content{
			width:100%
			display:flex
			justify-content: space-around;
			flex-wrap:wrap
			li{
				&:nth-child(3n+1){
					&>a{
						float:left
					}			
				}
				&:nth-child(3n-1){
					&>a{
						margin: 0 auto
					}			
				}
				&:nth-child(3n){
					&>a{
						float:right
					}			
				}
				float: left;
				width: 26%;
				height:5rem;
				margin :0 0rem 1.5rem 0;
				&>a{
					display:inline-block;
					max-width:5rem;
					width:100%
					height:100%;
					background: rgba(219,196,107,0.3);
					color:rgb(219,196,107);
					border:0.1rem solid rgb(121,171,171)
					border-radius:1rem
					span{
						font-size:2.6rem
						width:100%;
						line-height:3.5rem
						height:3.5rem;
						display:inline-block
					}
					h3{
						-height:2rem;
						-line-height:2rem;
						letter-spacing: 0.1rem;
					}
				}
			}
		}
	}
}
</style>