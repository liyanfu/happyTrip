$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'rechargeId', 		width:100, 	title: '充值ID'},
        {field:'userName', 			width:120, 	title: '用户名称' },
        {field:'userMobile', 		width:120, 	title: '登录账号' },
        {field:'rechargeMoney', 	width:120, 		title: '充值金额',sort: true},
        {field:'rechargeFee', 		minWidth:100,   title: '充值手续费', sort: true},
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet:formatterRechargeStatus},
        {field: 'submitStatus',  	width:100, 		title: '上传凭证状态',	align:'center',  templet:formatterSubmitStatus},
        {field:'alipayName', 		minWidth:100,   title: '支付宝名称'},
        {field:'alipayMobile', 		minWidth:100,   title: '支付宝账号'},
        {field:'rechargeCode', 		minWidth:100,   title: '充值凭证吗'},
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/recharge/list';
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
   	  
   	 form.on('select(selectSubmitStatus)', function(data){
 		  vm.q.submitStatus = data.value;
 		   return false;
	  });
   	  
   	form.on('select(selectRechargeStatus)', function(data){
		  vm.recharge.status = data.value;
		   return false;
	  });
   	  
   	  form.render();
  });
  
    
    
    

    layui.form.on('submit(saveOrUpdate)', function(data){
        vm.updateStatus(vm.recharge.rechargeId,vm.recharge.status);
        return false;
    });

   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        	vm.copyBean(obj.data);
    	if(layEvent === 'edit'){//编辑
            vm.update(data.rechargeId);
        }else if(layEvent === 'del'){//删除
            vm.del(data.rechargeId);
        }
    	
    });
    
});



//格式化状态
var formatterRechargeStatus = function(d){
	var text = '<span class="label label-success">其他</span>';
	if(d.status==0){
		text = '<span class="label label-danger">待支付</span>';
	}else if(d.status==1){
		text = '<span class="label label-disabled">已完成</span>';
	}else if(d.status==2){
		text = '<span class="label label-warm">异常</span>';
	}
	return text;
	
};


//格式化状态
var formatterSubmitStatus = function(d){
	var text = '<span class="label label-success">其他</span>';
	if(d.submitStatus==0){
		text = '<span class="label label-danger">未上传</span>';
	}else if(d.submitStatus==1){
		text = '<span class="label label-disabled">已上传</span>';
	}
	return text;
	
};

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	beginTime: null,
            endTime: null,
            rechargeCode:null,
        	rechargeId:null,
        	userName: null,
        	userMobile: null,
        	alipayMobile:null,
        	status:null,
        	submitStatus:null
        },
		showSelectForm:false,	//查看form表单
        recharge:{}				//对象
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
                ids.push(item.rechargeId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var rechargeId =null;
            $.each(list, function(index, item) {
            	rechargeId =  item.rechargeId;
            });
            return rechargeId;
        },
        query: function () {
            vm.reload();
        },
        select: function(rechargeId){
        	if(rechargeId == null || isNaN(rechargeId)){
        		rechargeId = vm.selectedRow();
        	}
        	
        	if(rechargeId == null){
        		return ;
        	}
        	vm.getRecharge(rechargeId);
    	
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
        	vm.recharge ={};
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
        update: function (rechargeId) {
            vm.getRecharge(rechargeId);
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
        del: function (rechargeId) {
        	if(rechargeId == null || isNaN(rechargeId)){
        		rechargeId = vm.selectedRow();
        	}
        	if(rechargeId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/recharge/delete",
                    data: {rechargeId:rechargeId},
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
        updateStatus: function (rechargeId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/recharge/status",
                data: {rechargeId: rechargeId, status: status},
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
            var url = vm.recharge.rechargeId == null ? "ht/recharge/save" : "ht/recharge/update";
            //编辑时间格式报错.
            vm.recharge.createTime = null;
        	vm.recharge.updateTime = null;
        	vm.recharge.submitTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.recharge),
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
        getRecharge: function(rechargeId){
            $.get(baseURL + "ht/recharge/info/"+rechargeId, function(r){
                vm.copyBean(r.recharge);
            });
        },
        copyBean:  function(recharge){
        	vm.recharge = recharge;
        	vm.recharge.createTime = formatterTime(recharge.createTime);
        	vm.recharge.updateTime = formatterTime(recharge.updateTime);
        	vm.recharge.submitTime = formatterTime(recharge.submitTime);
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

