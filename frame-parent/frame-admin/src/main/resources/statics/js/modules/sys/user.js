$(function () {
	//表格参数
	tableOption.cols = [[
        {type:'checkbox'},
        {field:'userId', width:100, title: '用户ID'},
        {field:'userName', width:120, title: '用户名'},
        {field:'deptName', minWidth:120, title: '所属部门',templet: function(d){
        	return d.map.deptName;
        }},
        {field:'email', minWidth:120, title: '邮箱'},
        {field:'mobile', minWidth: 100, title: '手机号'},
        {field: 'status', title: '状态', width:100, align:'center', templet: '#statusTpl'},
        {field:'createTime', minWidth:170, title: '创建时间',templet:function(d){
        	return formatterTime(d.createTime);
        }},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'sys/user/list';
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
        vm.user.map.roleIdList = [];
        $("input:checkbox[name='roleIdList']:checked").each(function() {
            vm.user.map.roleIdList.push($(this).val());
        });

        if(data.field.status == 'on'){
            vm.user.status = 1;
        }else{
            vm.user.status = 0;
        }

        vm.saveOrUpdate();
        return false;
    });


    //批量删除
    $(".delBatch").click(function(){
        var ids = vm.selectedRows();
        if(ids == null){
            return;
        }

        vm.del(ids);
    });

    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){
            vm.update(data.userId);
        } else if(layEvent === 'del'){
            var userIds = [data.userId];
            vm.del(userIds);
        }
    });

});


var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
        	userName: null
        },
        showForm: false,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            map:{
            	deptName:null,
            	roleIdList:[]
            }
        }
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
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.roleList = {};
            vm.user = {deptId:null, status:1, map:{roleIdList:[],deptName:null}};
            //获取角色信息
            this.getRoleList();

            vm.getDept();

            var index = layer.open({
                title: "新增",
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
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.user.map.deptName = node.name;
                }
            })
        },
        update: function (userId) {
            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();

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
                url: baseURL + "sys/user/status",
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
        del: function (userIds) {
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
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
            });
        },
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
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
        getUser: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.userPass = null;

                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: $("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.map.deptName = node[0].name;
                    layer.close(index);
                },
                end: function(){
                    $("#deptLayer").hide();
                }
            });
        },
        exports: function () {
            var url = baseURL + 'sys/user/export';
            if(vm.q.userName != null){
                url += '?userName='+vm.q.userName;
            }
            window.location.href = url;
        },
        reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                	userName: vm.q.userName
                }
            });
        }
    }
});

