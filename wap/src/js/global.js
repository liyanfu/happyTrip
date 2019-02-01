export default global = {
  // 设置cookie
  mySetCookie: function (cname, cvalue, exdays) {
    var d = new Date()
    // if(!exdays){
    // exdays = 1;//默认有效期一天
    // }
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000))
    var expires = 'expires=' + d.toUTCString()
    // console.info(cname + "=" + cvalue + "; " + expires);
    document.cookie = cname + '=' + encodeURIComponent(cvalue) + '; path = /; ' + expires
    // console.info(document.cookie);
  },
  // 获取cookie
  myGotCookie: function (cname) {
    var name = cname + '='
    var ca = document.cookie.split(';')
    for (var i = 0; i < ca.length; i++) {
      var c = ca[i]
      while (c.charAt(0) === ' ') c = c.substring(1)
      if (c.indexOf(name) !== -1) return c.substring(name.length, c.length)
    }
    return ''
  },
  // 清除cookie
  removeCookie: function (cname) {
    var value = this.myGotCookie(cname)
    if (value === null) {
      return false
    }
    this.mySetCookie(cname, value, 0)
    // this.mySetCookie(cname,value,{expires:0});
  },
  checkCookie: function () { // 举个例子
    var user = this.myGotCookie('username')
    if (user !== '') {
      alert('Welcome again ' + user)
    } else {
      user = prompt('Please enter your name:', '')
      if (user !== '' && user != null) {
        this.mySetCookie('username', user, 365)
      }
    }
  },
  // 验证邮箱
  checkMail: function (mail) {
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
    if (filter.test(mail)) {
      return true
    } else {
      return false
    }
  },
  // randomColor:function(){//无用函数
  //   return "#"+(~~(Math.random()*(1<<24))).toString(16)
  // },
  // genUid:function(){//无用函数
  //   return new Date().getTime()+""+Math.floor(Math.random()*899+100);
  // },
  // 输入是否是数字
  checkNum: function (obj) {
    obj = obj + ''
    if (obj === '') {
      return ''
    }
    return obj.replace(/[^0-9]/g, '')
  },
  // 获取地址栏字符串
  getUrlKey: function (name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ''])[1].replace(/\+/g, '%20')) || null
  },
  hasClass(obj, cls) {
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'))
  },
  addClass(obj, cls) {
    if (!this.hasClass(obj, cls)) {
      obj.className += ' ' + cls
    }
  },
  removeClass(obj, cls) {
    if (this.hasClass(obj, cls)) {
      var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)')
      obj.className = obj.className.replace(reg, '')
    }
  },
  toggleClass(obj, cls) {
    if (this.hasClass(obj, cls)) {
      this.removeClass(obj, cls)
    } else {
      this.addClass(obj, cls)
    }
  },
  formatDate: function (str) {
    // yyyy-MM-dd
    var fmt = 'yyyy-MM-dd'
    // var usedDate = str;
    var usedDate = new Date(str)
    var o = {
      'M+': usedDate.getMonth() + 1, // 月份
      'd+': usedDate.getDate(), // 日
      'h+': usedDate.getHours(), // 小时
      'm+': usedDate.getMinutes(), // 分
      's+': usedDate.getSeconds(), // 秒
      'q+': Math.floor((usedDate.getMonth() + 3) / 3), // 季度
      'S': usedDate.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (usedDate.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
      }
    }
    return fmt
  },
  formatAllDate: function (str, fmt) {
    // yyyy-MM-dd hh-mm-ss
    // yyyy-MM-dd
    // var usedDate = str;
    var usedDate = new Date(str)
    var o = {
      'M+': usedDate.getMonth() + 1, // 月份
      'd+': usedDate.getDate(), // 日
      'h+': usedDate.getHours(), // 小时
      'm+': usedDate.getMinutes(), // 分
      's+': usedDate.getSeconds(), // 秒
      'q+': Math.floor((usedDate.getMonth() + 3) / 3), // 季度
      'S': usedDate.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (usedDate.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
      }
    }
    return fmt
  }
}
