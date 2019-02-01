<template>
	<div class="login_page">
		<div class="login_box">
			<div class="logo">
				<img src="../../static/image/homepage/car.png" alt="" />
			</div>
			<div class="title">快乐出行</div>
			<div class="box">
				<mt-field placeholder="请输入用户名" v-model="username"></mt-field>
				<mt-field placeholder="请输入密码" type="password" v-model="pwd"></mt-field>
				<mt-field placeholder="请输入验证码" v-model="code">
				  	<span class="codeimg" v-text="codeimg" @click="changecode"></span>
				</mt-field>
				<mt-button class="logincase" @click="loginclick" :disabled="btndisable">立即登陆</mt-button>
			</div>
		</div>
	</div>
</template>

<script>
import { mapActions } from 'vuex'
export default{
	data(){
		return{
			username:'',
			pwd:'',
			code:'',
			codeimg:'',
			btndisable:false
		}
	},
	mounted(){
		this.changecode();
		var username = localStorage.getItem('username')
		if(username != null){
			this.username = username;
		}
	},
	methods:{
		...mapActions([
			"getcode",
			"loginConfirm"
		]),
		changecode(){
			this.getcode({}).then(res => {
				this.codeimg = res.captcha
			},res => {
				console.log(res)
			})
		},
		loginclick(){
			if(this.username === ''){
				this.$toast({
				  message: '请输入用户名！'
				});
				return;
			}
			if(this.pwd === ''){
				this.$toast({
				  message: '请输入密码！'
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
			  "userMobile": this.username,
			  "userPass": this.pwd,
			  "captcha": this.code
			}
			this.btndisable = true
			this.loginConfirm(obj).then(res => {
				this.btndisable = false
				if(res.code === 0){
					localStorage.setItem('username',this.username)
					this.layerglobal.mySetCookie('token',res.token)
					this.$router.push({'path':'/index/home'})
					return;
				}
				
				this.$toast({
				  message: res.msg
				});
				if(res.code === 500){
					this.resetData()
				}
			},res => {
				this.btndisable = false
				this.resetData()
				console.log(res)
			})
		},
		resetData(){
			this.username = '';
			this.pwd = ''
			this.code = ''
			this.changecode();
		}
	}
}
</script>

<style lang="stylus">
.login_page{
	width:100%;
	height:100%;
	-background:tan;
	position:relative
	.login_box{
		width:80%;
		height:20rem;
		padding:0rem 0 0.5rem 0
		background:rgba(255,255,255,0.3);
		position:absolute
		left:50%;
		top:50%;
		margin-left:-40%;
		margin-top:-10rem;
		border-radius :1rem;
		.logo{
			width:100%;
			height:5rem;
			text-align center
			img{
				height:100%;
			}
		}
		.title{
			width:100%;
			height:2.4rem;
			line-height:2.4rem;
			text-align:center;
			color:#000;
			font-weight:bold;
			font-size:1.4rem
		}
		.box{
			padding:0 0.5rem
			.mint-cell{
				background: transparent;
				border-bottom:1px solid #999
				input{
					background: transparent;
					color:#fff
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