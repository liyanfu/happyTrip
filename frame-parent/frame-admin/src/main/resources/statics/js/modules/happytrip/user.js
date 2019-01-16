$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'userId', 			width:100, 	title: '用户ID'},
        {field:'userName', 			width:120, 	title: '用户名' ,templet:function(d){
        	return  '<a class="layui-btn layui-btn-xs layui-bg-blue" lay-event="userAccount" title="账户信息" >'+d.userName+'</a>'; ;
        }},
        {field:'userMobile', 			width:120, 		title: '登录账号'},
        {field:'balance', 		minWidth:100,   title: '账户余额',	 templet:function(d){
        	return d.map.balance;
        }},
        {field: 'status',  			width:100, 		title: '状态',	align:'center',  templet: '#statusTpl'},
        {field:'recomendNum', 	minWidth:100, 	title: '今日推荐人数', templet:function(d){
        	return d.map.recomendNum;
        }},
        {field:'teamAchievement', 	minWidth:100, 	title: '今日团队业绩', templet:function(d){
        	return d.map.teamAchievement;
        }},
        {field:'createTime', 		minWidth:160, 	title: '创建时间',	templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {field:'lastLoginTime', minWidth:160, title: '上次登录时间',templet:function(d){
        	return formatterTime(d.lastLoginTime);
        }},
        {field:'lastLoginIp', minWidth:150, title: '上次登录Ip'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'ht/user/list';
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
    });

    layui.form.on('submit(saveOrUpdate)', function(data){
    	
        if(data.field.status == 'on'){
            vm.user.status = 1;
        }else{
            vm.user.status = 0;
        }
        vm.saveOrUpdate();
        return false;
    });
    
    layui.form.on('submit(rechargeOrSubtract)', function(data){
    	if(data.field.coinType==""){
    		alert("请选择货币类型");
    		return false;
    	}
        vm.rechargeOrSubtract();
        return false;
    });
    
    
    
   
    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        	vm.copyBean(obj.data);
        
    	if(layEvent === 'add'){//新增
             vm.add(data.userId);
            // $("#userHeadImg").attr('src',data.headImgUrl);
        }else if(layEvent === 'edit'){//编辑
            vm.update(data.userId);
           // $("#userHeadImg").attr('src',data.headImgUrl);
        }else if(layEvent === 'del'){//删除
            var userIds = [data.userId];
            vm.del(userIds);
        }else if(layEvent === 'userWallet'){//账户
            vm.select(data.userId);
        }else if(layEvent === 'recharge'){//充值
            vm.recharge(data.userId);
        }else if(layEvent === 'subtract'){//扣款
            vm.subtract(data.userId);
        }
    });

    layui.use('form', function(){
     	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
     	  form.on('select(selectStatus)', function(data){
	  		  vm.q.status = data.value;
	  		   return false;
     	  });
     	  
     	 form.on('select(selectCoinType)', function(data){
      		  vm.accountChange.coinType = data.value;
      		   return false;
      	  });
     	  
     	  form.render();
    });
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{						//查询条件
        	userName: null,
        	status:null
        },
        addForm:false,			//新增form
        showForm: false,		//编辑form表单
		showSelectForm:false,	//查看form表单
		walletForm:false,		//充值扣款form表达
        user:{},				//用户对象
        wallet:{},				//账户对象
        walletChange:{},		 //帐变对象
        rechargeOrSubtractFlag:null //充值还是扣款
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
                ids.push(item.userId);
            });
            return ids;
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请选择一条记录");
                return ;
            }
            var userId =null;
            $.each(list, function(index, item) {
            	userId =  item.userId;
            });
            return userId;
        },
        query: function () {
            vm.reload();
        },
        select: function(userId){
        	if(userId == null || isNaN(userId)){
        		userId = vm.selectedRow();
        	}
        	
        	if(userId == null){
        		return ;
        	}
        	//账号信息
        	vm.getUser(userId);
        	//账户信息
        	vm.getWallet(userId);
        	//帐变信息
        	vm.getUserWalletChange(userId);
        	
        },
        add: function () {
        	vm.user ={};
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
        update: function (userId) {
            vm.getUser(userId);
            var index = layer.open({
                title: "修改",
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
        resetpass: function (userId) { //重置登录密码
            vm.getUser(userId);
            var index = layer.open({
                title: "修改",
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
        updateStatus: function (userId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "ht/user/status",
                data: {userId: userId, status: status},
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
            var url = vm.user.userId == null ? "ht/user/save" : "ht/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
        rechargeOrSubtract: function () {
        	
        	if(vm.rechargeOrSubtractFlag==null){
        		return;
        	}
            var url = vm.rechargeOrSubtractFlag ? "ht/wallte/recharge" : "ht/wallte/subtract";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.accountChange),
                success: function(r){
                    if(r.code === 0){
                        layer.alert('操作成功', function(){
                            layer.closeAll();
                            vm.accountChange = {};
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        getUser: function(userId){
            $.get(baseURL + "ht/user/info/"+userId, function(r){
                vm.copyBean(r.user);
            });
        },
        getUserWallet: function(userId){
            $.get(baseURL + "ht/wallet/info/"+userId, function(r){
                vm.wallet = r.wallet;
            });
        },
        getUserWalletChange: function(userId){
            var index = layer.open({
                title: "查看",
                type: 1,
                content: $("#selectForm"),
                end: function(){
                    vm.showSelectForm = false;
                    layer.closeAll();
                }
            });
           
        	var selectTableOption = tableOption;
        	//表格参数
        	selectTableOption.cols = [[
                {field:'changeId',      minWidth:120,  title: '帐变ID',   align:'center'},
                {field:'userName',      minWidth:120,  title: '用户名称',  align:'center',templet:function(d){
                	return d.map.userName;
                }},
                {field:'createTime', 	minWidth:160,  title: '帐变时间',  align:'center',templet:function(d){
                	return formatterTime(d.createTime);
                }},
                {field:'operatorMoney',   minWidth:120,   title: '帐变金额',   align:'center'},
                {field:'balance',minWidth:120,   title: '剩余金额',   align:'center'},
                {field:'operatorName',    minWidth:120,   title: '帐变类型',   align:'center'},
                {field:'relationId',    minWidth:120,   title: '关联Id',   align:'center'}
            ]];
        	
        	selectTableOption.id = 'walletChangeGrid';
        	selectTableOption.elem = '#awalletChangeGrid';
        	selectTableOption.height =  315; //设置高度
        	selectTableOption.url = baseURL + 'ht/walletChange/list';
        	selectTableOption.where ={userId:userId} ;
        	gridTable = layui.table.render(selectTableOption);
       
        	vm.showSelectForm = true;
        	layer.full(index);
            
            
        },
        recharge:function(userId){
        	if(userId == null || isNaN(userId)){
        		userId = vm.selectedRow();
        	}
        	
        	if(userId == null){
        		return ;
        	}
        	
        	vm.rechargeOrSubtractFlag = true;
        	vm.accountChange.userId = userId;
        	
        	 var index = layer.open({
                 title: "人工充值",
                 type: 1,
                 content: $("#walletForm"),
                 end: function(){
                     vm.walletForm = false;
                     vm.rechargeOrSubtractFlag==null;
                     layer.closeAll();
                 }
             });
        	
             vm.walletForm = true;
             layer.full(index);
        },
        subtract: function(userId){
        	if(userId == null || isNaN(userId)){
        		userId = vm.selectedRow();
        	}
        	
        	if(userId == null){
        		return ;
        	}
        	
        	vm.rechargeOrSubtractFlag = false;
        	vm.walletChange.userId = userId;
        	
        	 var index = layer.open({
                 title: "人工扣款",
                 type: 1,
                 content: $("#walletForm"),
                 end: function(){
                     vm.walletForm = false;
                     vm.rechargeOrSubtractFlag==null;
                     layer.closeAll();
                 }
             });
        	
             vm.walletForm = true;
             layer.full(index);
        },
        copyBean:  function(user){
        	vm.user = user;
        	vm.user.createTime = formatterTime(user.createTime);
            vm.user.lastLoginTime = formatterTime(user.lastLoginTime);
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

