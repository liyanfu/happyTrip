$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'paymentId', 			width:100, 	title: '支付ID'},
        {field:'paymentName', 			width:120, 	title: '支付名称' },
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet: '#statusTpl'},
        {field:'sort', 			width:120, 	title: '排序' },
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {field:'createUser', 		minWidth:100,   title: '创建者'},
        {field:'updateTime', 		minWidth:100,   title: '修改时间',templet:function(d){
        	return formatterTime(d.updateTime);
        }},
        {field:'updateUser', 		minWidth:100,   title: '修改者'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/payment/list';
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
            vm.payment.status = 1;
        }else{
            vm.payment.status = 0;
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
            vm.update(data.paymentId);
        }else if(layEvent === 'del'){//删除
            vm.del(data.paymentId);
        }
    	
    });
    
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	paymentName: null,
        	status:null
        },
        addForm:false,			//新增form
        showForm: false,		//编辑form表单
		showSelectForm:false,	//查看form表单
        payment:{}			//用户对象
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
                ids.push(item.paymentId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var paymentId =null;
            $.each(list, function(index, item) {
            	paymentId =  item.paymentId;
            });
            return paymentId;
        },
        query: function () {
            vm.reload();
        },
        select: function(paymentId){
        	if(paymentId == null || isNaN(paymentId)){
        		paymentId = vm.selectedRow();
        	}
        	
        	if(paymentId == null){
        		return ;
        	}
        	vm.getpayment(paymentId);
    	
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
        	vm.payment ={};
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
        update: function (paymentId) {
            vm.getpayment(paymentId);
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
        del: function (paymentId) {
        	if(paymentId == null || isNaN(paymentId)){
        		paymentId = vm.selectedRow();
        	}
        	if(paymentId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/payment/delete",
                    data: {paymentId:paymentId},
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
        updateStatus: function (paymentId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/payment/status",
                data: {paymentId: paymentId, status: status},
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
            var url = vm.payment.paymentId == null ? "ht/payment/save" : "ht/payment/update";
            //编辑时间格式报错.
            vm.payment.createTime = null;
        	vm.payment.updateTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.payment),
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
        getpayment: function(paymentId){
            $.get(baseURL + "ht/payment/info/"+paymentId, function(r){
                vm.copyBean(r.payment);
            });
        },
        copyBean:  function(payment){
        	vm.payment = payment;
        	vm.payment.createTime = formatterTime(payment.createTime);
        	vm.payment.updateTime = formatterTime(payment.updateTime);
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

