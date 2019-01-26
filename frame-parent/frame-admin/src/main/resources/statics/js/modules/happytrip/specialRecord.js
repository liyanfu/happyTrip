$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'id', 			width:100, 	title: '订单ID'},
        {field:'userName', 			width:120, 	title: '用户名称' },
        {field:'userLevel', 			width:120, 	title: '用户级别'},
        {field:'groupUserIds', 			width:120, 	title: '用户分组'},
        {field:'money', 		minWidth:100,   title: '派发金额', sort: true},
        {field:'totalsNum', 			width:120, 		title: '达标总人数',sort: true},
        {field:'recommendNum', 		minWidth:100,   title: '累计直推人数', sort: true},
        {field:'teamNum', 		minWidth:100,   title: '团队人数', sort: true},
        {field:'teamAchievement', 		minWidth:100,   title: '累计团队业绩', sort: true},
        {field: 'isGrant',  			width:100, 		title: '状态',	align:'center',  templet:formatterStatus},
        {field:'generateTime', 		minWidth:100,   title: '生成日期',templet:function(d){
        	return formatterDate(d.generateTime);
        }},
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {field:'grantTime', 		minWidth:100,   title: '发放时间',templet:function(d){
        	return formatterTime(d.grantTime);
        }},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/specialrecord/list';
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
	  		  vm.q.isGrant = data.value;
	  		   return false;
   	  });
   	  form.render();
  });
    

    layui.form.on('submit(saveOrUpdate)', function(data){
        vm.updateStatus(vm.globalRecord.id,vm.globalRecord.status);
        return false;
    });

   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
    	if(layEvent === 'edit'){//编辑
    		var ids = [data.id];
            vm.grant(ids);
        }else if(layEvent === 'del'){//删除
            vm.del(data.id);
        }
    	
    });
});


//格式化状态
var formatterStatus = function(d){
	var text = '<span class="label label-success">其他</span>';
	if(d.isGrant==0){
		text = '<span class="label label-danger">未发放</span>';
	}else if(d.isGrant==1){
		text = '<span class="label label-normal">已发放</span>';
	}
	return text;
	
};



var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	id:null,
        	beginTime: null,
            endTime: null,
        	userName:null,
        	groupUserIds:null,
        	isGrant:null
        },
		showSelectForm:false,	//查看form表单
        globalRecord:{},				//订单对象
        flag:false				//初始化Date控件时show的控制
    },
    updated: function(){
        layui.form.render();
    },
    methods: {
        selectedRows: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0){
                alert("请至少选择一条记录");
                return ;
            }

            var ids = [];
            $.each(list, function(index, item) {
                ids.push(item.id);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var id =null;
            $.each(list, function(index, item) {
            	id =  item.id;
            });
            return id;
        },
        query: function () {
            vm.reload();
        },
        grant: function (ids) {
        	if(ids == null || isNaN(ids)){
        		ids = vm.selectedRows();
        	}
        	
        	if(ids == null){
        		return ;
        	}
        	  confirm('确定要发放选中的记录？', function(){
                  $.ajax({
                      type: "POST",
                      url: baseURL + "ht/specialrecord/grant",
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
        del: function (id) {
        	if(id == null || isNaN(id)){
        		id = vm.selectedRow();
        	}
        	if(id == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/specialrecord/delete",
                    data: {id:id},
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

