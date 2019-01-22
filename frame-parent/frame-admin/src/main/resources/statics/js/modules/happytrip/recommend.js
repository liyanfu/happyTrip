$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'recommendId', 			width:100, 	title: '推荐ID'},
        {field:'createTime', 		minWidth:100,   title: '创建时间',templet:function(d){
        	return formatterDate(d.createTime);
        }},
        {field:'userName', 			width:120, 	title: '用户名称' },
        {field:'recommendNumber', 			width:120, 	title: '今日推荐人数' },
        {field:'teamAchievement', 			width:120, 	title: '今日团队业绩' },
        {field:'updateTime', 		minWidth:100,   title: '修改时间',templet:function(d){
        	return formatterTime(d.updateTime);
        }},
        {field:'updateUser', 		minWidth:100,   title: '修改者'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/recommend/list';
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
            vm.update(data.recommendId);
        }else if(layEvent === 'del'){//删除
            vm.del(data.recommendId);
        }
    	
    });
    
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	userName: null,
        	beginTime: null,
            endTime: null
        },
        addForm:false,			//新增form
        showForm: false,		//编辑form表单
		showSelectForm:false,	//查看form表单
        recommend:{}			//用户对象
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
                ids.push(item.recommendId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var recommendId =null;
            $.each(list, function(index, item) {
            	recommendId =  item.recommendId;
            });
            return recommendId;
        },
        query: function () {
            vm.reload();
        },
        select: function(recommendId){
        	if(recommendId == null || isNaN(recommendId)){
        		recommendId = vm.selectedRow();
        	}
        	
        	if(recommendId == null){
        		return ;
        	}
        	vm.getrecommend(recommendId);
    	
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
        	vm.recommend ={};
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
        update: function (recommendId) {
            vm.getrecommend(recommendId);
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
        del: function (recommendId) {
        	if(recommendId == null || isNaN(recommendId)){
        		recommendId = vm.selectedRow();
        	}
        	if(recommendId == null){
        		return ;
        	}
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ht/recommend/delete",
                    data: {recommendId:recommendId},
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
        saveOrUpdate: function () {
            var url = vm.recommend.recommendId == null ? "ht/recommend/save" : "ht/recommend/update";
            //编辑时间格式报错.
            vm.recommend.createTime = null;
        	vm.recommend.updateTime = null;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.recommend),
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
        getrecommend: function(recommendId){
            $.get(baseURL + "ht/recommend/info/"+recommendId, function(r){
                vm.copyBean(r.recommend);
            });
        },
        copyBean:  function(recommend){
        	vm.recommend = recommend;
        	vm.recommend.createTime = formatterTime(recommend.createTime);
        	vm.recommend.updateTime = formatterTime(recommend.updateTime);
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

