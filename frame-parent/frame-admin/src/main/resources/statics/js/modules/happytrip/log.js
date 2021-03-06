$(function () {
	
	//表格参数
	tableOption.cols =  [[
        {field:'logId', width:50, title: 'ID'},
        {field:'userName', width:100, title: '用户名'},
        {field:'operation', width:150, title: '用户操作'},
        {field:'method', minWidth:150, title: '请求方法'},
        {field:'params', minWidth:150, title: '请求参数'},
        {field:'time', width:130, title: '执行时长(毫秒)'},
        {field:'ip', width:140, title: 'IP地址'},
        {field:'sources', width:120, title: '来源',templet:function(d){
        	return d.sources==0 ? "前端":"后台";
        }},
        {field:'createTime', width:160, title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }}
    ]];
	tableOption.url = baseURL + 'sys/log/list';
	
	tableOption.where = {"sources":0};//前端log
	//初始化表格
    gridTable = layui.table.render(tableOption);
    

	layui.use('form', function(){
	   	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
	   	  form.on('select(selectStatus)', function(data){
		  		  vm.q.sources = data.value;
		  		   return false;
	   	  });
	   	  form.render();
	  });
    
	  
    

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			sources:0,
			userName: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
        exports: function () {
            var url = baseURL + 'sys/log/export';
            if(vm.q.userName != null){
                url += '?userName='+vm.q.userName+'&sources='+vm.q.sources;
            }else{
            	  url += '?sources='+vm.q.sources;
            }
            window.location.href = url;
        },
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: vm.q
            });
		}
	}
});