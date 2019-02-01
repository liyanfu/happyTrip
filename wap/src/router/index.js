import Vue from 'vue'
import Router from 'vue-router'
import layerglobal from '@/js/global'
import index from '@/pages/index'

Vue.use(Router)

// 常规路由
export const constantRouterMap = [{
  path: '/',
  component: index,
  redirect: '/index/home'
}, {
  path: '/index',
  component: index,
  redirect: '/index/home',
  children: [{
    path: 'home',
    component: resolve => require(['@/pages/home/home'], resolve)
  },{
    path: 'purchase',
    component: resolve => require(['@/pages/home/purchase'], resolve)
  },{
    path: 'online',
    component: resolve => require(['@/pages/online/online'], resolve)
  },{
    path: 'olddriver',
    component: resolve => require(['@/pages/olddriver/olddriver'], resolve)
  },{
    path: 'extension',
    component: resolve => require(['@/pages/extension/extension'], resolve)
  },{
    path: 'custom',
    component: resolve => require(['@/pages/custom/custom'], resolve)
  },{
    path: 'mydata',
    component: resolve => require(['@/pages/custom/mydata'], resolve)
  },{
    path: 'myteam',
    component: resolve => require(['@/pages/custom/myteam'], resolve)
  },{
    path: 'carprofit',
    component: resolve => require(['@/pages/custom/carprofit'], resolve)
  },{
    path: 'allwelfare',
    component: resolve => require(['@/pages/custom/allwelfare'], resolve)
  },{
    path: 'orderrecode',
    component: resolve => require(['@/pages/custom/orderrecode'], resolve)
  },{
    path: 'globalbonus',
    component: resolve => require(['@/pages/custom/globalbonus'], resolve)
  },{
    path: 'teamleader',
    component: resolve => require(['@/pages/custom/teamleader'], resolve)
  },{
    path: 'speciallbonus',
    component: resolve => require(['@/pages/custom/speciallbonus'], resolve)
  },{
    path: 'company',
    component: resolve => require(['@/pages/custom/company'], resolve)
  },{
    path: 'customservice',
    component: resolve => require(['@/pages/custom/customservice'], resolve)
  }]
}, {
  path: '/login',
  component: resolve => require(['@/pages/login'], resolve)
}, {
  path: '/register',
  component: resolve => require(['@/pages/register'], resolve)
}, {
  path: '/withdown',
  component: resolve => require(['@/pages/withdown'], resolve)
}, {
  path: '/recharge',
  component: resolve => require(['@/pages/recharge'], resolve)
}, {
  path: '*',
  component: resolve => require(['@/pages/error'], resolve)
}]


// 初始化
var router = new Router({
  routes: constantRouterMap
})

router.beforeEach((to, from, next) => {
    var token = layerglobal.myGotCookie('token')
    if(!token && to.path != '/login'){//未登录，强制登录
    		if(to.path !== '/register'){
    			next({
	            'path':"/login"  // 将跳转的路由path作为参数，登录成功后跳转到该路由
	        });	
    		}else{
    			next();
    		}
    }else{
        next();
    }
});

export default router