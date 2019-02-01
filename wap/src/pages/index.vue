<template>
  <div class="indexbody">
    <router-view></router-view>
    <!--弹出窗口-->
    <a-footer v-show="footerflag"></a-footer>
  </div>
</template>

<script>
// import { mapGetters } from 'vuex'
import aFooter from '@/components/base/afooter'
export default {
  name:'index',
  components: {
    aFooter
  },
  data () {
    return {
    		footerflag:true
    }
  },
  mounted () {

  },
  computed: {
    // ...mapGetters([
    //   ''
    // ])
    // key () {
    //   // 监听路由变化，数据无法更新？处理方法
    //   // return this.$route.path !== undefined ? new Date().getTime() : new Date().getTime()
    //   return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
    // }
  },
  methods: {
  },
  watch: {
      $route(newvalue,oldvalue) {
        switch(newvalue.path){
        		case '/index/home':
        		case '/index/olddriver':
        		case '/index/extension':
        		case '/index/online':
        		case '/index/custom':
        			this.footerflag = true
        		break
        		default:
        			this.footerflag = false
        }
      }
    }
}
</script>

<style lang="stylus">
.indexbody{
	height:100%
	.defaultform{
		width:100%
		padding-top:3rem
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
		.mint-radiolist{
			.mint-radiolist-title{
				text-align:left
			}
		}
	}
	.defaultbtn{
		width:90%
		margin-top:1rem
		background: url("../../static/image/homepage/line_nav.jpg") repeat center center/100% 100%;
	}
	.defaulttable{
		width:100%
		table{
			width:96%;
			margin:2rem auto;
			border-collapse:collapse;
			tr{
				background rgba(255,255,255,.3);
				&.firsttr{
					background:#96552e
				}
				th{
					padding: 10px 0;
				    color: #fff;
				    font-size:0.9rem
				    line-height:1.4rem
				    border: solid 1px #f1f1f1;
				}
				td{
					padding: 5px 0;
					font-size:0.9rem
					line-height:1.4rem
    					border: solid 1px #F0F0F0;
    					color: gold;
				}
			}
		}
	}
}
</style>

