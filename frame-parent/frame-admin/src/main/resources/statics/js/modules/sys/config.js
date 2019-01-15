$(function () {
	
	//表格参数
	tableOption.cols =  [[
        {type:'checkbox'},
        {field:'configId', width:100, title: 'ID'},
        {field:'configKey', minWidth:100, title: '参数名'},
        {field:'configVal', minWidth:100, title: '参数值'},
        {field:'configStatus', width:100, align:'center', title: '状态',templet: '#statusTpl'},
        {field:'remark', minWidth:100, title: '备注'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'sys/config/list';
	//初始化表格
    gridTable = layui.table.render(tableOption);

    //状态
    layui.form.on('switch(configStatus)', function(data){
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
            configKey: null
		},
        showForm: false,
		config: {}
	},
    updated: function(){
        layui.form.render();
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
                ids.push(item.configId);
            });
            return ids;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.config = {};

            var index = layer.open({
                title: "新增",
                type: 1,
                content: $("#editForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });

            vm.showForm = true;
            layer.full(index);
		},
		update: function (id) {
			$.get(baseURL + "sys/config/info/"+id, function(r){
                vm.config = r.config;
            });

            var index = layer.open({
                title: "修改",
                type: 1,
                content: $("#editForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });

            vm.showForm = true;
            layer.full(index);
		},
		updateStatus: function (configId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/config/status",
                data: {configId: configId, status: status},
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
		del: function (ids) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/config/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		saveOrUpdate: function (event) {
			var url = vm.config.configId == null ? "sys/config/save" : "sys/config/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.config),
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
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                	configKey: vm.q.configKey
                }
            });
		}
	}
});