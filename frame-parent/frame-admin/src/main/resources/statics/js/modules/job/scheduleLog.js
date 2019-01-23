$(function () {
	
	//表格参数
	tableOption.cols =  [[
        {field:'logId', width:50, title: '日志ID'},
        {field:'jobId', width:100, title: '任务ID'},
        {field:'beanName', width:150, title: 'bean名称'},
        {field:'methodName', minWidth:150, title: '方法名称'},
        {field:'params', minWidth:150, title: '参数'},
        {field:'times', width:130, title: '执行时长(毫秒)'},
        {field:'status', width:140, title: '状态',templet: function(d){
        	return d.status === 0 ? 
					'<span class="label label-success">成功</span>' :
					'<span class="label label-danger pointer" style="color:blue" onclick="vm.showError('+d.logId+')">失败</span>';
        }},
        {field:'times', width:140, title: '耗时(单位：毫秒)'},
        {field:'createTime', width:160, title: '执行时间',templet:function(d){
        	return formatterTime(d.createTime);
        }}
    ]];
	tableOption.url = baseURL + 'sys/scheduleLog/list';
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
	   	  form.on('select(selectStatus)', function(data){
		  		  vm.q.status = data.value;
		  		   return false;
	   	  });
	   	  form.render();
	  });
  
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			beanName:null,
			methodName: null,
			beginTime:null,
			endTime:null,
			status:null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		showError: function(logId) {
			$.get(baseURL + "sys/scheduleLog/info/"+logId, function(r){
				layer.open({
				  title:'失败信息',
				  closeBtn:0,
				  content: r.log.error
				});
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