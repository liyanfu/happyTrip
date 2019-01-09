var gridTable;

var baseURL = "../../";

//table默认参数
var tableOption = {
		id: "gridid",
        elem: '#grid',
        url: null ,
        cols: [],
        page: true,
        loading: true,
        limits: [10, 50, 100, 200],
        limit: 10,
        request: {
        	   pageName: 'pageNumber' //页码的参数名称，默认：page
        	  ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        	},
		response: {
			   //statusName: 'status' //数据状态的字段名称，默认：code
			  //,statusCode: 200 //成功的状态码，默认：0
			  //,msgName: 'hint' //状态信息的字段名称，默认：msg
			   countName: 'total' //数据总数的字段名称，默认：count
			  ,dataName: 'list' //数据列表的字段名称，默认：data
			}      
};

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false
});

//重写alert
window.alert = function(msg, callback){
	layer.alert(msg, function(index){
		layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//时间毫秒格式化
Date.prototype.format = function(fmt){ // author: fury
	  var o = {   
	    "M+" : this.getMonth()+1,                 // 月份
	    "d+" : this.getDate(),                    // 日
	    "HH+" : this.getHours(),                   // 小时
	    "m+" : this.getMinutes(),                 // 分
	    "s+" : this.getSeconds(),                 // 秒
	    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
	    "S"  : this.getMilliseconds()             // 毫秒
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
} 

//时间格式化
var formatterTime=function(time){
	if(time){
		var d = new Date();
		d.setTime(time);
		return d.format("yyyy-MM-dd HH:mm:ss");
	}else{
		return "";
	}
}
//日期格式化
var formatterDate=function(time){
	if(time){
		var d=new Date();
		d.setTime(time);
		return d.format("yyyy-MM-dd");
	}else{
		return "";
	}
}
	
//日期格式化
var formatterDateHour=function(time){
	if(time){
		var d=new Date();
		d.setTime(time);
		return d.format("yyyy-MM-dd HH:mm");
	}else{
		return "";
	}	
}
