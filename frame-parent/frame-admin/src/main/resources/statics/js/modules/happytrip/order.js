$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'orderId', 			width:100, 	title: '订单ID'},
        {field:'productImgurl', 			width:100, 	title: '商品图片',	 templet:function(d){
        	return  '<div><img width="75px" height="50px" src="'+d.productImgurl+'"></div>' ;
        }},
        {field:'userName', 			width:120, 	title: '用户名称' },
        {field:'productName', 			width:120, 	title: '商品名称'},
        {field:'productTypeName', 			width:120, 	title: '商品类型'},
        {field:'buyMoney', 		minWidth:100,   title: '投资金额', sort: true},
        {field:'buyQuantity', 			width:120, 		title: '投资数量',sort: true},
        {field:'rebateMoney', 		minWidth:100,   title: '每日返利', sort: true},
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet:formatterOrderStatus},
        {field:'totalRebatePeriods', 		minWidth:100,   title: '总返利天数', sort: true},
        {field:'rebatePeriods', 		minWidth:100,   title: '已返利天数', sort: true},
        {field:'profitMoney', 		minWidth:100,   title: '收益总金额', sort: true},
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/order/list';
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
   	  
   	 form.on('select(selectOrderStatus)', function(data){
		  vm.order.status = data.value;
		   return false;
   	 });
   	
   	 form.on('select(selectProductTypeStatus)', function(data){
	  		  vm.q.productTypeId = data.value;
	  		   return false;
  	  });
   	  form.render();
  });
  
    
    
    

    layui.form.on('submit(saveOrUpdate)', function(data){
        vm.updateStatus(vm.order.orderId,vm.order.status);
        return false;
    });

   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        	vm.copyBean(obj.data);
    	if(layEvent === 'edit'){//编辑
            vm.update(data.orderId);
        }else if(layEvent === 'del'){//删除
            vm.del(data.orderId);
        }
    	
    });
 	  
 	 vm.getProductTypeList();
});


//格式化状态
var formatterOrderStatus = function(d){
	var text = '<span class="label label-success">其他</span>';
	if(d.status==0){
		text = '<span class="label label-danger">待支付</span>';
	}else if(d.status==1){
		text = '<span class="label label-normal">收益中</span>';
	}else if(d.status==2){
		text = '<span class="label label-disabled">已完成</span>';
	}else if(d.status==3){
		text = '<span class="label label-warm">已取消</span>';
	}
	return text;
	
};



var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	orderId:null,
        	beginTime: null,
            endTime: null,
        	userName:null,
        	productTypeId:null,
        	productName:null,
        	status:null
        },
		showSelectForm:false,	//查看form表单
        order:{},				//订单对象
        productTypeList:{},		//产品类型集合
        flag:false				//初始化Date控件时show的控制
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
                ids.push(item.orderId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var orderId =null;
            $.each(list, function(index, item) {
            	orderId =  item.orderId;
            });
            return orderId;
        },
        query: function () {
            vm.reload();
        },
        select: function(orderId){
        	if(orderId == null || isNaN(orderId)){
        		orderId = vm.selectedRow();
        	}
        	
        	if(orderId == null){
        		return ;
        	}
        	vm.getOrder(orderId);
    	
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
        	vm.order ={};
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
        update: function (orderId) {
            vm.getOrder(orderId);
            var index = layer.open({
                title: "编辑",
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
        del: function (orderId) {
        	if(orderId == null || isNaN(orderId)){
        		orderId = vm.selectedRow();
        	}
        	if(orderId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/order/delete",
                    data: {orderId:orderId},
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
        updateStatus: function (orderId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/order/status",
                data: {orderId: orderId, status: status},
                success: function(r){
                    if(r.code == 0){
                        layer.alert('操作成功', function(index){
                        	vm.showSelectForm = false;
                        	layer.closeAll();
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function () {
            var url = vm.order.orderId == null ? "ht/order/save" : "ht/order/update";
            //编辑时间格式报错.
           vm.order.createTime = null;
           vm.order.updateTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.order),
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
        getOrder: function(orderId){
            $.get(baseURL + "ht/order/info/"+orderId, function(r){
                vm.copyBean(r.order);
            });
        },
        getProductTypeList: function(){
            $.get(baseURL + "ht/productType/list/", function(r){
            	if(r.code!=0){
            		alert(r.msg);
            		return false;
            	}
                vm.productTypeList = r.list;
            });
        },
        copyBean:  function(order){
        	vm.order = order;
        	vm.order.createTime = formatterTime(order.createTime);
        	vm.order.updateTime = formatterTime(order.updateTime);
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

