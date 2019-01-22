$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'welfareId', 			width:100, 	title: '福利ID'},
        {field:'welfareName', 			width:100, 	title: '福利名称'},
        {field:'welfareValue', 			width:120, 	title: '参数值' },
        {field:'bonusPool', 			width:120, 	title: '奖金池'},
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet: '#statusTpl'},
        {field:'remark', 		minWidth:160,   title: '备注',templet:function(d){
        	return "<div title='"+d.remark+"'><span class='label'>"+d.remark+"</span></div>";
        }},
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/welfare/list';
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
    
    
    //状态
    layui.form.on('switch(formStatus)', function(data){
        var status = 0;
        if(data.elem.checked){
            status = 1;
        }
        vm.welfare.status = status;
        return false;
    });
 
    layui.use('form', function(){
   	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
   	  form.on('select(selectStatus)', function(data){
	  		  vm.q.status = data.value;
	  		   return false;
   	  });
   	  
   	 form.on('select(selectWelfareStatus)', function(data){
		  vm.q.welfareKey = data.value;
		   return false;
   	 });
   	
   	 
   	 form.on('select(formSelectWelfareStatus)', function(data){
	  		  vm.welfare.welfareKey = data.value;
	  		  if(data.value=='GLOBAL_BONUS_KEY'){
	  			vm.welfare.remark = "每日直推X人";
	  		  }
	  		  if(data.value=='TEAM_LEADERSHIP_AWARD_KEY'){
	  			vm.welfare.remark = "当日直推X人,当日业绩达X";
	  		  }
	  		  if(data.value=='SPECIAL_CONTRIBUTION_AWARD_KEY'){
	  			vm.welfare.remark = "累计直推有效会员X人,团队人数X,累计团队业绩X";
	  		  }
	  		  $("#remark").val(vm.welfare.remark);
	  		 return false;
  	  });
   	
   	  form.render();
  });
  

    layui.form.on('submit(saveOrUpdate)', function(data){
        vm.saveOrUpdate();
        return false;
    });

   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        	vm.copyBean(obj.data);
    	if(layEvent === 'edit'){//编辑
            vm.update(data.welfareId);
        }else if(layEvent === 'del'){//删除
            vm.del(data.welfareId);
        }
    	
    });
 	  
 	 vm.getWelfareList();
});




var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	welfareId:null,
        	welfareKey: null,
        	welfareName:null,
        	status:null
        },
        remark :null,
        addForm:false,
		showSelectForm:false,	//查看form表单
        welfare:{},				//订单对象
        welfareList:{}		//产品类型集合
        
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
                ids.push(item.welfareId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var welfareId =null;
            $.each(list, function(index, item) {
            	welfareId =  item.welfareId;
            });
            return welfareId;
        },
        query: function () {
            vm.reload();
        },
        select: function(welfareId){
        	if(welfareId == null || isNaN(welfareId)){
        		welfareId = vm.selectedRow();
        	}
        	
        	if(welfareId == null){
        		return ;
        	}
        	vm.getwelfare(welfareId);
    	
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
        	vm.welfare ={};
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
            $('#welfareListSelect').removeAttr('disabled');
            layer.full(index);
        },
        update: function (welfareId) {
            vm.getwelfare(welfareId);
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
            $('#welfareListSelect').attr('disabled','true');
            layer.full(index);
        },
        del: function (welfareId) {
        	if(welfareId == null || isNaN(welfareId)){
        		welfareId = vm.selectedRow();
        	}
        	if(welfareId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/welfare/delete",
                    data: {welfareId:welfareId},
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
        updateStatus: function (welfareId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/welfare/status",
                data: {welfareId: welfareId, status: status},
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
            var url = vm.welfare.welfareId == null ? "ht/welfare/save" : "ht/welfare/update";
            //编辑时间格式报错.
           vm.welfare.createTime = null;
           vm.welfare.updateTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.welfare),
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
        getwelfare: function(welfareId){
            $.get(baseURL + "ht/welfare/info/"+welfareId, function(r){
                vm.copyBean(r.welfare);
            });
        },
        getWelfareList: function(){
            $.get(baseURL + "ht/welfare/getWelfareList/", function(r){
            	if(r.code!=0){
            		alert(r.msg);
            		return false;
            	}
                vm.welfareList = r.list;
            });
        },
        copyBean:  function(welfare){
        	vm.welfare = welfare;
        	vm.welfare.createTime = formatterTime(welfare.createTime);
        	vm.welfare.updateTime = formatterTime(welfare.updateTime);
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

