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
        {field:'status', width:100, align:'center', title: '状态',templet: function(d){
        	return d.status === 0 ? 
					'<span class="label label-success">正常</span>' :
					'<span class="label label-danger ">暂停</span>';
        }},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url =  baseURL + 'sys/schedule/list';
	//初始化表格
    gridTable = layui.table.render(tableOption);
    
    layui.use('form', function(){
     	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
     	  form.on('select(selectStatus)', function(data){
  	  		  vm.q.status = data.value;
  	  		   return false;
     	  });
     	  form.render();
    });
    
    layui.form.on('submit(saveOrUpdate)', function(data){
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
        vm.copyBean(obj.data);
        if(layEvent === 'edit'){
            vm.update(data.jobId);
        } else if(layEvent === 'del'){
            var ids = [data.jobId];
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
		schedule: {}
	},
	methods: {
		selectedRows: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0){
                alert("请选择一条记录");
                return ;
            }

            var ids = [];
            $.each(list, function(index, item) {
                ids.push(item.jobId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var jobId =null;
            $.each(list, function(index, item) {
            	jobId =  item.jobId;
            });
            return jobId;
        },
		query: function () {
			vm.reload();
		},
		copyBean:  function(schedule){
        	vm.schedule = schedule;
        },
		add: function(){
			vm.showForm = true;
			vm.schedule = {};
		},
		update: function (jobId) {
			if(jobId == null || isNaN(jobId)){
				jobId = vm.selectedRow();
        	}
			if(jobId == null){
				return ;
			}
			vm.getSchedule(jobId);
            var index = layer.open({
                title: "编辑",
                type: 1,
                content: $("#showForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });
            vm.showForm = true;
            layer.full(index);
		},
		getSchedule:function(jobId){
			$.get(baseURL + "sys/schedule/info/"+jobId, function(r){
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
							layer.closeAll();
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
		del: function (jobIds) {
			if(jobIds == null || isNaN(jobIds)){
				jobIds = vm.selectedRows();
        	}
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
			var jobIds = vm.selectedRows();
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
			var jobIds = vm.selectedRows();
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
			var jobIds = vm.selectedRows();
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
			layui.table.reload('gridid', {
	                page: {
	                    curr: 1
	                },
	                where: vm.q
	        });
		}
	}
});