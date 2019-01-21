$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'productTypeId', 			width:100, 	title: '类型ID'},
        {field:'productTypeName', 			width:120, 	title: '类型名称' },
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet: '#statusTpl'},
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {field:'createUser', 		minWidth:100,   title: '创建者'},
        {field:'updateTime', 		minWidth:100,   title: '修改时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {field:'updateUser', 		minWidth:100,   title: '修改者'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/productType/list';
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
        return false;
    });
    
    layui.use('form', function(){
   	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
   	  form.on('select(selectStatus)', function(data){
	  		  vm.q.status = data.value;
	  		   return false;
   	  });
   	  form.render();
  });
  

    layui.form.on('submit(saveOrUpdate)', function(data){
        if(data.field.status == 'on'){
            vm.productType.status = 1;
        }else{
            vm.productType.status = 0;
        }
      
        vm.saveOrUpdate();
        return false;
    });

   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        	vm.copyBean(obj.data);
    	if(layEvent === 'edit'){//编辑
            vm.update(data.productTypeId);
        }else if(layEvent === 'del'){//删除
            vm.del(data.productTypeId);
        }
    	
    });
    
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	productTypeName: null,
        	status:null
        },
        addForm:false,			//新增form
        showForm: false,		//编辑form表单
		showSelectForm:false,	//查看form表单
        productType:{}			//用户对象
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
                ids.push(item.productTypeId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var productTypeId =null;
            $.each(list, function(index, item) {
            	productTypeId =  item.productTypeId;
            });
            return productTypeId;
        },
        query: function () {
            vm.reload();
        },
        select: function(productTypeId){
        	if(productTypeId == null || isNaN(productTypeId)){
        		productTypeId = vm.selectedRow();
        	}
        	
        	if(productTypeId == null){
        		return ;
        	}
        	vm.getproductType(productTypeId);
    	
    	  var index = layer.open({
              title: "查看",
              type: 1,
              content: $("#selectForm"),
              end: function(){
                  vm.showSelectForm = false;
                  layer.closeAll();
              }
          });

      	vm.showSelectForm = true;
      	layer.full(index);
        },
        add: function () {
        	vm.productType ={};
            var index = layer.open({
                title: "新增",
                type: 1,
                content: $("#addForm"),
                end: function(){
                    vm.addForm = false;
                    layer.closeAll();
                }
            });
            vm.addForm = true;
            layer.full(index);
        },
        update: function (productTypeId) {
            vm.getproductType(productTypeId);
            var index = layer.open({
                title: "编辑",
                type: 1,
                content: $("#addForm"),
                end: function(){
                    vm.addForm = false;
                    layer.closeAll();
                }
            });
            vm.addForm = true;
            layer.full(index);
        },
        del: function (productTypeId) {
        	if(productTypeId == null || isNaN(productTypeId)){
        		productTypeId = vm.selectedRow();
        	}
        	if(productTypeId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/productType/delete",
                    data: {productTypeId:productTypeId},
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
        updateStatus: function (productTypeId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/productType/status",
                data: {productTypeId: productTypeId, status: status},
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
        saveOrUpdate: function () {
            var url = vm.productType.productTypeId == null ? "ht/productType/save" : "ht/productType/update";
            //编辑时间格式报错.
           vm.productType.createTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.productType),
                success: function(r){
                    if(r.code === 0){
                        layer.alert('操作成功', function(){
                            layer.closeAll();
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        getproductType: function(productTypeId){
            $.get(baseURL + "ht/productType/info/"+productTypeId, function(r){
                vm.copyBean(r.productType);
            });
        },
        copyBean:  function(productType){
        	vm.productType = productType;
        	vm.productType.createTime = formatterTime(productType.createTime);
        	vm.productType.updateTime = formatterTime(productType.updateTime);
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

