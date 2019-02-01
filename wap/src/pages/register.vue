<template>
	<div class="register_page">
		<div class="register_box">
			<div class="box">
				<mt-field label="推荐人ID" v-model="recommendMobile" disabled></mt-field>
				<mt-field placeholder="请输入手机号" v-model="userMobile"></mt-field>
				<mt-field placeholder="请输入注册名" v-model="userName"></mt-field>
				<mt-field placeholder="请输入密码" v-model="userPass1" type="password"></mt-field>
				<mt-field placeholder="请再次输入密码" v-model="userPass2" type="password"></mt-field>
				<mt-field placeholder="请输入支付宝姓名" v-model="alipayName"></mt-field>
				<mt-field placeholder="请输入支付宝账号"  v-model="alipayMobile"></mt-field>
				<mt-field placeholder="请输入验证码" v-model="code">
				  	<span class="codeimg" v-text="codeimg" @click="changecode"></span>
				</mt-field>
				<mt-button class="logincase" @click="regiclick">下一步</mt-button>
			</div>
		</div>
	</div>
</template>

<script>
import { mapActions } from 'vuex'
export default{
	data(){
		return{
			recommendMobile:'',
			userMobile:'',
			userName:'',
			userPass1:'',
			userPass2:'',
			alipayName:'',
			alipayMobile:'',
			code:'',
			codeimg:''
		}
	},
	mounted(){
		this.changecode();
		this.recommendMobile = this.$route.query.mobile;
	},
	methods:{
		...mapActions([
			"getcode",
			"registerConfirm"
		]),
		changecode(){
			this.getcode({}).then(res => {
				this.codeimg = res.captcha
			},res => {
				console.log(res)
			})
		},
		regiclick(){
			if(this.recommendMobile === ''){
				this.$toast({
				  message: '请输入推荐人ID！'
				});
				return;
			}
			if(this.userMobile === ''){
				this.$toast({
				  message: '请输入手机号！'
				});
				return;
			}
			if(this.userName === ''){
				this.$toast({
				  message: '请输入注册名！'
				});
				return;
			}
			if(this.userPass1 === ''){
				this.$toast({
				  message: '请输入密码！'
				});
				return;
			}
			if(this.userPass2 === ''){
				this.$toast({
				  message: '请再次输入密码！'
				});
				return;
			}
			if(this.alipayName === ''){
				this.$toast({
				  message: '请输入支付宝姓名！'
				});
				return;
			}
			if(this.alipayMobile === ''){
				this.$toast({
				  message: '请输入支付宝账号！'
				});
				return;
			}
			if(this.code === ''){
				this.$toast({
				  message: '请输入验证码！'
				});
				return;
			}
			var obj = {
			  "recommendMobile": this.recommendMobile,
			  "userMobile": this.userMobile,
			  "userName": this.userName,
			  "userPass1": this.userPass1,
			  "userPass2": this.userPass2,
			  "alipayName": this.alipayName,
			  "alipayMobile": this.alipayMobile,
			  "code": this.code
			}
			this.registerConfirm(obj).then(res => {
				if(res.code === 0){
					this.$toast({
					  message: "注册成功",
					});
					this.$router.push({'path':'/login'})
					return;
				}
				this.$toast({
				  message: res.msg,
				});
			},res => {
				this.resetData()
			})
		},
		resetData(){
			this.userMobile = ''
			this.userName = ''
			this.userPass1 = ''
			this.userPass2 = ''
			this.alipayName = ''
			this.alipayMobile = ''
			this.code = ''
			this.changecode();
		}
	}
}
</script>

<style lang="stylus">
.register_page{
	width:100%;
	height:100%;
	-background:tan;
	position:relative
	.register_box{
		width:80%;
		height:28rem;
		padding:1rem 0
		background:rgba(255,255,255,0.3);
		position:absolute
		left:50%;
		top:50%;
		margin-left:-40%;
		margin-top:-14rem;
		border-radius :1rem;
		.box{
			padding:0 0.5rem
			.mint-cell{
				background: transparent;
				border-bottom:1px solid #999
				input{
					background: transparent;
					color:#fff
				}
				.mint-cell-text{
					float:left;
					color:#000
				}
			}
			.logincase{
				width:100%;
				margin-top:0.5rem;
				border-radius:0.4rem
				background: url("../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
			}
			.codeimg{
				width:5rem;
				height:2.4rem;
				text-align:center;
				line-height:2.4rem;
				font-size:1.4rem
				background:#fff;
				display:inline-block
			}
		}
	}
}
</style>