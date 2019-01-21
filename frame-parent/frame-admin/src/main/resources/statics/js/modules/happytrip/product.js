$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'productId', 			width:100, 	title: '商品ID'},
        {field:'productImgurl', 			width:100, 	title: '商品图片',	 templet:function(d){
        	return  '<div><img width="75px" height="50px" src="'+d.productImgurl+'"></div>' ;
        }},
        
        {field:'productName', 			width:120, 	title: '商品名称' },
        {field:'map.productTypeName', 			width:120, 	title: '所属类型' ,	 templet:function(d){
        	return d.map.productTypeName;
        }},
        {field:'saleMoney', 			width:120, 		title: '售卖金额',sort: true},
        {field:'saleQuantity', 		minWidth:100,   title: '售卖数量', sort: true},
        {field:'saleVolumes', 		minWidth:100,   title: '已售数量', sort: true},
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet: '#statusTpl'},
        {field:'rebateMoney', 		minWidth:100,   title: '每期返利', sort: true},
        {field:'rebatePeriods', 		minWidth:100,   title: '返利期数', sort: true},
        {field:'rebateTotals', 		minWidth:100,   title: '返利总金额', sort: true},
        {field:'purchaseRestriction', 		minWidth:100,   title: '每人购买限制'},
        {field:'startRebateTime', 		minWidth:100,   title: '返利开始天数'},
        {field:'sort', 		minWidth:100,   title: '排序'},

        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/product/list';
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
   	  
   	 form.on('select(selectProductTypeStatus)', function(data){
	  		  vm.q.productTypeId = data.value;
	  		  vm.product.productTypeId = data.value;
	  		   return false;
  	  });
   	  form.render();
  });
  
    
    
    

    layui.form.on('submit(saveOrUpdate)', function(data){
        if(data.field.status == 'on'){
            vm.product.status = 1;
        }else{
            vm.product.status = 0;
        }
        
        if(vm.product.productTypeId== null){
    		alert("请选择商品类型");
    		return false;
    	}
        
        if(vm.product.productImgurl == null){
    		alert("请选择商品图片");
    		return false;
    	}
        
        vm.product.productImgurl  = $("#productImgurl").val();
        vm.saveOrUpdate();
        return false;
    });

   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        	vm.copyBean(obj.data);
    	if(layEvent === 'edit'){//编辑
            vm.update(data.productId);
        }else if(layEvent === 'del'){//删除
           // var productIds = [data.productId];
            vm.del(data.productId);
        }
    	
    });
 	  
 	  //普通图片上传
 	  var uploadInst = layui.upload.render({
 	    elem: '#uploadImg'
 	    ,url: baseURL + 'sys/oss/upload'
 	    ,before: function(obj){
 	      //预读本地文件示例，不支持ie8
 	      obj.preview(function(index, file, result){
 	        $('#looklocalImg').attr('src', result); //图片链接（base64）
 	      });
 	    }
 	    ,done: function(res){
 	      //如果上传失败
 	      if(res.code > 0){
 	        return layer.msg('上传失败');
 	      }
 	      //上传成功 赋值
 	      $("#productImgurl").val(res.data.src);
 	      vm.product.productImgurl = res.data.showPath;
 	    }
 	    ,error: function(){
 	      //演示失败状态，并实现重传
 	      var demoText = $('#uploadText');
 	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
 	      demoText.find('.demo-reload').on('click', function(){
 	        uploadInst.upload();
 	      });
 	    }
 	  });
 	  
 	 vm.getProductTypeList();
    
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	productTypeId:null,
        	productName: null,
        	status:null
        },
        addForm:false,			//新增form
        showForm: false,		//编辑form表单
		showSelectForm:false,	//查看form表单
        product:{
        	map:{}
        },				//用户对象
        productTypeList:{}		//产品类型集合
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
                ids.push(item.productId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var productId =null;
            $.each(list, function(index, item) {
            	productId =  item.productId;
            });
            return productId;
        },
        query: function () {
            vm.reload();
        },
        select: function(productId){
        	if(productId == null || isNaN(productId)){
        		productId = vm.selectedRow();
        	}
        	
        	if(productId == null){
        		return ;
        	}
        	vm.getProduct(productId);
    	
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
        	vm.product ={};
        	vm.product.map ={};
        	vm.getProductTypeList();
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
            vm.product.productTypeId = null;
        },
        update: function (productId) {
        	vm.getProductTypeList();
            vm.getProduct(productId);
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
            vm.product.productTypeId = null;
        },
        del: function (productId) {
        	if(productId == null || isNaN(productId)){
        		productId = vm.selectedRow();
        	}
        	if(productId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/product/delete",
                    data: {productId:productId},
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
        updateStatus: function (productId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/product/status",
                data: {productId: productId, status: status},
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
            var url = vm.product.productId == null ? "ht/product/save" : "ht/product/update";
            //编辑时间格式报错.
           vm.product.createTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.product),
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
        getProduct: function(productId){
            $.get(baseURL + "ht/product/info/"+productId, function(r){
                vm.copyBean(r.product);
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
        copyBean:  function(product){
        	vm.product = product;
        	vm.product.createTime = formatterTime(product.createTime);
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

