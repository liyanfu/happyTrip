$(function () {
	//表格参数
	tableOption.cols = [[
        {field:'historyId',      minWidth:120,  title: '帐变ID',   align:'center'},
        {field:'userName',      minWidth:120,  title: '用户名称',  align:'center'},
        {field:'operatorName',    minWidth:120,   title: '帐变类型',   align:'center'},
        {field:'createTime', 	minWidth:160,  title: '帐变时间',  align:'center', templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {field:'operatorMoney',   minWidth:120,   title: '帐变金额',   align:'center'},
        {field:'balance',minWidth:120,   title: '剩余金额',   align:'center'},
        {field:'remark',    minWidth:120,   title: '备注信息',   align:'center'},
        {field:'createUser',    minWidth:120,   title: '创建者',   align:'center'}
    ]];
	tableOption.url = baseURL + 'ht/walletChange/list';
	//初始化表格
    gridTable = layui.table.render(tableOption);
    
    
    layui.laydate.render({
        elem: '#beginTime',
        type: 'date',
        value:new Date(),
        isInitValue:true,
        show: true,
        done: function(value, date, endDate){
            vm.q.beginTime = new Date(value+" 00:00:00");
        },
        ready: function(beginDate){
        	 vm.q.beginTime = new Date(beginDate.year+"-"+beginDate.month+"-"+beginDate.date+" 00:00:00");
        }
    });
    
    layui.laydate.render({
        elem: '#endTime',
        type: 'date',
        value:new Date((new Date).setDate((new Date()).getDate()+1)),
        isInitValue:true,
        show: true,
        done: function(value, date, endDate){
            vm.q.endTime = new Date(value+" 00:00:00");
        },
        ready: function(endDate){
        	 vm.q.endTime = new Date(endDate.year+"-"+endDate.month+"-"+endDate.date+" 00:00:00");
            if(!vm.flag){
            	$('#layui-laydate2').hide();
            }
            vm.flag= true;
        }
    });
    
    layui.use('form', function(){
   	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
   	  
   	  form.on('select(selectChangeType)', function(data){
		  vm.q.operatorType = data.value;
		  return false;
   	  });
   	  
   	  form.render();
  });
    vm.getTypelist();
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
        	userName: null,
        	operatorType:null,
        	beginTime:null,
        	endTime:null
        },
        flag:false,
        walletChangeTypelist:{}
    },
    updated: function(){
        layui.form.render();
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getTypelist: function(){
            $.get(baseURL + "ht/walletChange/getWalletChangeTypelist/", function(r){
            	if(r.code!=0){
            		alert(r.msg);
            		return false;
            	}
                vm.walletChangeTypelist = r.list;
            });
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

