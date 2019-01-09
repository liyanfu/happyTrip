$(function () {
	
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'matchId', width:80, title: 'ID'},
        {field:'matchTime', minWidth:160, title: '比赛时间',templet:function(d){
        	return formatterDateHour(d.matchTime);
        }},
        {field:'hostTeamName', 	   minWidth:100,  title: '主队名称'},
        {field:'guestTeamName',    minWidth:100,  title: '客队名称'},
        {field:'matchStatus',      minWidth:120,  title: '比赛状态', align:'center',templet:formatterMatchStatus},
        {field:'settleFlag',       minWidth:80,   title: '结算状态',  align:'center',templet:formatterSettleFlag},
        {field:'status',       minWidth:80,   title: '上架/下架',  	align:'center',templet:'#statusTpl'},
        {field:'importantFlag',    minWidth:60,   title: '重要赛',   align:'center',templet: '<div>{{d.importantFlag==0?"否":"是"}}</div>'},
        {field:'promotionFlag',    minWidth:60,   title: '淘汰赛',   align:'center',templet: '<div>{{d.promotionFlag==0?"否":"是"}}</div>'},
        {title: '操作', width:80, fixed:'right',align:'center',templet:'#barTpl'}
    ]];
	tableOption.url = baseURL + 'vsball/match/list';
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
        value:new Date(),
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
    
    
    layui.laydate.render({
        elem: '#matchTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function(value, date, endDate){
            vm.match.matchTime = value;
        }
    });
    
    //状态
    layui.form.on('switch(status)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false});
        var status = 0;
        if(data.elem.checked){
            status = 1;
        }
        vm.updateStatus(data.value, status);

        layer.close(index);
    });
    
    
	layui.use('form', function(){
   	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
   	  
   	  form.on('select(matchStatus)', function(data){
		  vm.q.matchStatus = data.value;
		   return false;
	  });
   	  
   	  form.on('select(settleFlag)', function(data){
   		  vm.q.settleFlag = data.value;
   		  return false;
   	  });
   	  form.on('select(importantFlag)', function(data){
   		  vm.q.importantFlag = data.value;
   		   return false;
   	  });
   	  form.on('select(promotionFlag)', function(data){
   		  vm.q.promotionFlag = data.value;
   		   return false;
   	  });
   	  form.on('select(status)', function(data){
   		  vm.q.status = data.value;
   		   return false;
   	  });
   	  
   	  
   	
   	form.on('select(select_type)', function(data){
   		var name = $(data.elem).attr("name");
   		if(name=='matchStatus'){
   			vm.match.matchStatus = data.value;
       }else if(name=='settleFlag'){
 		  vm.match.settleFlag = data.value;
       }
 		  return false;
 	  });
   	  
   	form.on('switch(status_type)', function(data){
   		var name = $(data.elem).attr("name");
        var status = 0;
        if(data.elem.checked){
            status = 1;
        }
        if(name=='status'){
        	 vm.match.status = status;
        }else if(name=='promotionFlag'){
        	vm.match.promotionFlag = status;
        }else if(name=='importantFlag'){
        	vm.match.importantFlag = status;
        }else if(name=='autoSettleFlag'){
        	vm.match.autoSettleFlag = status;
        }else if(name=='promotedTeam'){
        	vm.match.promotedTeam = status;
        }
        return false;
    });
   	  
   	 form.render();
   });
    
    
    
    layui.form.on('submit(saveOrUpdate)', function(){
        vm.saveOrUpdate();
        return false;
    });

    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        vm.copyBean(obj.data);//为了解决get加载慢先赋值
        if(layEvent === 'edit'){
            vm.update(data.matchId);
        } else if(layEvent === 'del'){
            var ids = [data.matchId];
            vm.del(ids);
        }
    });
    
});

//格式化比赛状态
var formatterMatchStatus = function(d){
	var text = '<span class="label label-success">其他</span>';
	if(d.matchStatus==0){
		text = '<span class="label label-danger">异常</span>';
	}else if(d.matchStatus==1){
		text = '<span class="label label-normal">未开赛</span>';
	}else if(d.matchStatus==2){
		text = '<span class="label label-success">比赛中</span>';
	}else if(d.matchStatus==3){
		text = '<span class="label label-disabled">完赛</span>';
	}else if(d.matchStatus==4){
		text = '<span class="label label-warm">取消</span>';
	}
	return text;
	
};

//格式化结算状态
var formatterSettleFlag = function(d){
	var text = '<span class="label label-success">其他</span>';
	if(d.settleFlag==0){
		text = '<span class="label label-danger">未结算</span>';
	}else if(d.settleFlag==1){
		text = '<span class="label label-normal">竞猜退款</span>';
	}else if(d.settleFlag==2){
		text = '<span class="label label-success">普通结算</span>';
	}else if(d.settleFlag==100){
		text = '<span class="label label-success">已结算</span>';
	}
	return text;
	
};

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{						//界面查询条件	
			queryName: null,
            beginTime: null,
            endTime: null,
            matchStatus:null,
            status:null,
            importantFlag:null,
            promotionFlag:null,
            settleFlag:null
		},
        showForm: false,		//编辑时form表单
		match: {},				//查看与修改时赋值的表单对象
		showSelectForm:false,	//查看form表单
		flag:false				//初始化Date控件时show的控制
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
                ids.push(item.matchId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var matchId =null;
            $.each(list, function(index, item) {
            	matchId =  item.matchId;
            	vm.copyBean(item);
            });
            
            return matchId;
        },
        query: function () {
            vm.reload();
        },
        select: function () { //查看赛事详情,竞猜
        	 var matchId = vm.selectedRow();
        	  if(matchId == null){
                  return ;
              }
        	  
        	  var index = layer.open({
                  title: "查看",
                  type: 1,
                  content: $("#selectForm"),
                  end: function(){
                      vm.showSelectForm = false;
                      layer.closeAll();
                  }
              });
        	  
              $.get(baseURL + "vsball/match/select/"+matchId, function(r){
            	  vm.copyBean(r.match);//赛事
                  layui.use('form', function(){//刷新form
                  	var form = layui.form;
                  	form.render();
                  });
                  vm.selectGuessTable(r.guessList);//竞猜
              });
              vm.showSelectForm = true;
              layer.full(index);
        },
        selectGuessTable: function(data){
        	if(data.length == 0 ){
                return ;
            }
        	var selectTableOption = tableOption;
        	//表格参数
        	selectTableOption.cols = [[
                {field:'guessingCode',      minWidth:120,  title: '竞猜单号', align:'center'},
                {field:'userName',      minWidth:120,  title: '竞猜用户', align:'center'},
                {field:'guessingMultiple', minWidth:80,   title: '赔率',  align:'center'},
                {field:'coinType',    minWidth:120,   title: '货币类型',   align:'center',templet: '<div>{{d.coinType==0?"金币":"银币"}}</div>'},
                {field:'initiatorCount',    minWidth:120,   title: '竞猜金额',   align:'center'},
                {field:'completeCount',    minWidth:120,   title: '完成金额',   align:'center'},
                {field:'finalCount',    minWidth:120,   title: '赔付金额',   align:'center'},
                {field:'joinPersonNum',    minWidth:120,   title: '竞猜人数',   align:'center'},
                {field:'guessingType',    minWidth:120,   title: '竞猜类型',   align:'center'},
                {field:'winCoinCount',    minWidth:120,   title: '盈利结果',   align:'center'}
            ]];
        	selectTableOption.id = 'selectGridTable';
        	selectTableOption.elem = '#selectGridTable';
        	selectTableOption.height =  315; //设置高度
        	selectTableOption.page = false;
        	selectTableOption.data = data;
        	selectTableOption.url = null;
        	gridTable = layui.table.render(selectTableOption);
        	
        },
		update: function (matchId) {
			 
			var index = layer.open({
                title: "修改",
                type: 1,
                content: $("#editForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });
			
			$.get(baseURL + "vsball/match/info/"+matchId, function(r){
                vm.copyBean(r.match);
                layui.use('form', function(){
                	var form = layui.form;
                	form.render();
                });
            });
           
            vm.showForm = true;
            layer.full(index);
		},
		del: function (ids) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "vsball/match/delete",
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
		updateStatus: function (matchId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "vsball/match/status",
                data: {matchId: matchId, status: status},
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
		saveOrUpdate: function (event) {
			var url = vm.match.matchId == null ? "vsball/match/save" : "vsball/match/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    contentType: "application/json",
			    data: JSON.stringify({
				    	matchId:vm.match.matchId,
				    	matchTime:new Date(vm.match.matchTime),
				    	currentMatchTime:vm.match.currentMatchTime,
				    	hostTeamScore:vm.match.hostTeamScore,
				    	guestTeamScore:vm.match.guestTeamScore,
				    	matchStatus:vm.match.matchStatus,
				    	status:vm.match.status,
				    	matchResult:vm.match.matchResult,
				    	remark:vm.match.remark,
				    	promotionFlag:vm.match.promotionFlag,
				    	importantFlag:vm.match.importantFlag,
				    	settleFlag:vm.match.settleFlag,
				    	promotedTeam:vm.match.promotedTeam,
				    	autoSettleFlag:vm.match.autoSettleFlag
			    }),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
                            layer.closeAll();
                            vm.match ={};
                            vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		copyBean: function(match){
			vm.match = match;
			vm.match.matchTime = formatterTime(match.matchTime);
			vm.match.createTime = formatterTime(match.createTime);
			vm.match.settleTime = formatterTime(match.settleTime);
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