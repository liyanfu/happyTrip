$(function () {
	
	//表格参数
	tableOption.cols =  [[
        {type:'checkbox'},
        {field:'id', width:100, title: 'ID'},
        {field:'url', minWidth:200, title: 'URL地址'},
        {field:'createDate', width:200, title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }}
    ]];
	tableOption.url = baseURL + 'sys/oss/list';
	//初始化表格
    gridTable = layui.table.render(tableOption);

    layui.form.on('radio(type)', function(data){
        vm.config.type = data.value;
    });

    layui.form.on('submit(saveOrUpdate)', function(){
        vm.saveOrUpdate();
        return false;
    });

    //文件上传
    layui.upload.render({
        elem: '#upload',
        url: baseURL + "sys/oss/upload",
        accept: 'file',
        before: function(){
            layer.load();
        },
        done: function(r){
            layer.closeAll('loading');

            if(r.code == 0){
                alert(r.data.src);
                vm.reload();
            }else{
                alert(r.msg);
            }
        }
    });

    //批量删除
    $(".delBatch").click(function(){
        var ids = vm.selectedRows();
        if(ids == null){
            return;
        }

        vm.del(ids);
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
        showForm: false,
        config: {}
	},
    created: function(){
        this.getConfig();
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
                ids.push(item.id);
            });
            return ids;
        },
		query: function () {
			vm.reload();
		},
		getConfig: function () {
            $.getJSON(baseURL + "sys/oss/config", function(r){
				vm.config = r.config;
            });
        },
		addConfig: function(){
            var index = layer.open({
                title: "云存储配置",
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
		saveOrUpdate: function () {
			var url = baseURL + "sys/oss/saveConfig";
			$.ajax({
				type: "POST",
			    url: url,
                contentType: "application/json",
			    data: JSON.stringify(vm.config),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
                            layer.closeAll();
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
        del: function (ids) {
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/oss/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
		reload: function () {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                }
            });
		}
	}
});