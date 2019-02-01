import axios from 'axios'
import layerglobal from '@/js/global'

axios.defaults.withCredentials = true
axios.defaults.timeout = 5000 // 响应时间
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8' // 配置请求头
axios.defaults.baseURL = 'http://120.78.138.125:38888';   //配置接口地址
//axios.defaults.contentType = false;
//axios.defaults.processData = false;

if (process.env.NODE_ENV === 'production') {
  console.log('正式生产环境')
} else if (process.env.NODE_ENV === 'development') {
  console.log('本地调试环境')
}

// POST传参序列化(添加请求拦截器)
axios.interceptors.request.use((config) => {
  // 在发送请求之前做某件事
  // console.log(config)
	
  if (config.method === 'post') {
		if(config.url !== '/api/submitRechargeCredential' && config.url !== '/api/submitOrderCredential'){
			config.data = JSON.stringify(config.data)
		}
  }
  var token = layerglobal.myGotCookie('token')
  if (token != null) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
	 config.headers.token = token;
  }
  return config
}, (error) => {
  console.log('错误的传参')
  return Promise.reject(error)
})
// 返回状态判断(添加响应拦截器)
axios.interceptors.response.use((res) => {
  // 对响应数据做些事
  // console.log(res.data)
  if (res) {
  	if(res.data.code === 530){
  		layerglobal.removeCookie('token')
  		alert(res.data.msg)
  		location.href="/login"
  	}
    return Promise.resolve(res)
  }
  // if (res) {
  //   if (res.data.code === 1) {
  //     return Promise.resolve(res)
  //   } else {
  //     return Promise.reject(res)
  //   }
  // }
  return Promise.reject(res)
}, (error) => {
  console.log(error)
  return Promise.reject(error.data)
})

export default {
  // 返回一个Promise(发送get请求)
  myGotFetch (_url, paramArr) {
    return new Promise((resolve, reject) => {
      axios.get(_url, {
        params: paramArr
      }).then(response => {
        // console.log(response.data)
        resolve(response.data)
      }, err => {
        reject(err.data)
      }).catch((error) => {
        reject(error.data)
      })
    })
  },

  // 返回一个Promise(发送post请求)
  myPostFetch (_url, paramArr) {
		if(_url === '/api/submitRechargeCredential' || _url === '/api/submitOrderCredential'){
			return new Promise((resolve, reject) => {
			  axios.post(_url, paramArr,{
				  headers:{'Content-Type':'multipart/form-data'}
        }).then(response => {
			    // console.log(response.data);
			    resolve(response.data)
			  }, err => {
			    reject(err.data)
			  }).catch((error) => {
			    reject(error.data)
			  })
			})
		}else{
			
		
			return new Promise((resolve, reject) => {
				axios.post(_url, paramArr).then(response => {
					// console.log(response.data);
					resolve(response.data)
				}, err => {
					reject(err.data)
				}).catch((error) => {
					reject(error.data)
				})
			})
			
			
		}
  }
}
