$(function () {
	
	//表格参数
	tableOption.cols =  [[
        {field:'logId', width:50, title: 'ID'},
        {field:'userName', width:100, title: '用户名'},
        {field:'operation', width:150, title: '用户操作'},
        {field:'method', minWidth:100, title: '请求方法'},
        {field:'params', minWidth:100, title: '请求参数'},
        {field:'time', width:130, title: '执行时长(毫秒)'},
        {field:'ip', width:120, title: 'IP地址'},
        {field:'sources', width:120, title: '来源',templet:function(d){
        	return d.sources==0 ? "App":"后台";
        }},
        {field:'createTime', width:170, title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }}
    ]];
	tableOption.url = baseURL + 'sys/log/list';
	tableOption.where = {"sources":1};//后台log
	//初始化表格
    gridTable = layui.table.render(tableOption);

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
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
                url += '?userName='+vm.q.userName+'&sources=1';
            }else{
            	url += '?sources=0';
            }
            window.location.href = url;
        },
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                	userName: vm.q.userName,
                	sources: 1
                }
            });
		}
	}
});