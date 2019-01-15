$(function () {
	
	//表格参数
	tableOption.cols =  [[
        {type:'checkbox'},
        {field:'jobId', width:100, title: '任务ID'},
        {field:'beanName', minWidth:100, title: 'bean名称'},
        {field:'methodName', minWidth:100, title: '方法名称'},
        {field:'params', minWidth:100, title: '参数'},
        {field:'cronExpression', minWidth:100, title: 'cron表达式'},
        {field:'remark', minWidth:100, title: '备注'},
        {field:'status', width:100, align:'center', title: '状态',templet: '#statusTpl'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url =  baseURL + 'sys/schedule/list';
	//初始化表格
    gridTable = layui.table.render(tableOption);

    //状态
    layui.form.on('switch(status)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false});
        var status = 0;
        if(data.elem.checked){
            status = 1;
        }
        vm.updateStatus(data.value, status);

        layer.close(index);
    });
    
    layui.form.on('submit(saveOrUpdate)', function(){
        vm.saveOrUpdate();
        return false;
    });


    //批量删除
    $(".delBatch").click(function(){
        var ids = vm.selectedRows();
        if(ids == null){
            return;
        }

        vm.del(ids);
    });

    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){
            vm.update(data.configId);
        } else if(layEvent === 'del'){
            var ids = [data.configId];
            vm.del(ids);
        }
    });
	
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			beanName: null
		},
		showForm: false,
		title: null,
		schedule: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showForm = true;
			vm.title = "新增";
			vm.schedule = {};
		},
		update: function () {
			var jobId = getSelectedRow();
			if(jobId == null){
				return ;
			}
			
			$.get(baseURL + "sys/schedule/info/"+jobId, function(r){
				vm.showForm = true;
                vm.title = "修改";
				vm.schedule = r.schedule;
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.schedule.jobId == null ? "sys/schedule/save" : "sys/schedule/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.schedule),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		updateStatus: function (jobId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/schedule/status",
                data: {jobId: jobId, status: status},
                success: function(r){
                    if(r.code == 0){
                        layer.alert('操作成功', function(index){
                            layer.close(index);
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
		del: function (event) {
			var jobIds = getSelectedRows();
			if(jobIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/schedule/delete",
                    contentType: "application/json",
				    data: JSON.stringify(jobIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		pause: function (event) {
			var jobIds = getSelectedRows();
			if(jobIds == null){
				return ;
			}
			
			confirm('确定要暂停选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/schedule/pause",
                    contentType: "application/json",
				    data: JSON.stringify(jobIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		resume: function (event) {
			var jobIds = getSelectedRows();
			if(jobIds == null){
				return ;
			}
			
			confirm('确定要恢复选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/schedule/resume",
                    contentType: "application/json",
				    data: JSON.stringify(jobIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		runOnce: function (event) {
			var jobIds = getSelectedRows();
			if(jobIds == null){
				return ;
			}
			
			confirm('确定要立即执行选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/schedule/run",
                    contentType: "application/json",
				    data: JSON.stringify(jobIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		reload: function (event) {
			vm.showForm = false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'beanName': vm.q.beanName},
                page:page 
            }).trigger("reloadGrid");
		}
	}
});