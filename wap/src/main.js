// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import '../static/css/common.css'
//import ElementUI from 'element-ui'
//import 'element-ui/lib/theme-chalk/index.css'
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css'; 
import App from './App'
import router from './router'
import '../static/font/iconfont.css'
import store from '@/store/store'
import layerglobal from '@/js/global'
import FastClick from 'fastclick'

Vue.config.productionTip = false
Vue.prototype.layerglobal = layerglobal

//Vue.use(ElementUI)
Vue.use(Mint);
FastClick.attach(document.body);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  template: '<App/>'
})
