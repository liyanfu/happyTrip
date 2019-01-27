$(function () {
	//表格参数
	tableOption.cols = [[
		 {type:'checkbox'},
        {field:'reportId',      minWidth:120,  title: '报表ID',   align:'center'},
        {field:'createTime', 	minWidth:160,  title: '创建时间',  align:'center', templet:function(d){
        	return formatterDate(d.createTime);
        }},
        {field:'userName',      minWidth:120,  title: '用户名称',  align:'center'},
        {field:'userMobile',    minWidth:120,   title: '登录账号',   align:'center'},
        {field:'userLevel',   minWidth:120,   title: '用户级别',   align:'center'},
        {field:'orderMoney',  minWidth:120,   title: '订单金额',   align:'center'},
        {field:'artificialRechargeMoney',minWidth:120,   title: '人工充值金额',   align:'center'},
        {field:'artificialWithdrawMoney',minWidth:120,   title: '人工扣款金额',   align:'center'},
        {field:'rechargeMoney',minWidth:120,   title: '充值金额',   align:'center'},
        {field:'rechargeFee',minWidth:120,   title: '充值手续费',   align:'center'},
        {field:'withdrawMoney',minWidth:120,   title: '提现金额',   align:'center'},
        {field:'withdrawFee',minWidth:120,   title: '提现手续费',   align:'center'},
        {field:'carProfitMoney',minWidth:120,   title: '共享汽车收益金额',   align:'center'},
        {field:'peopleWelfareMoney',minWidth:120,   title: '全民福利收益金额',   align:'center'},
        {field:'globalBonusMoney',minWidth:120,   title: '全球分红收益金额',   align:'center'},
        {field:'teamLeaderMoney',minWidth:120,   title: '团队领导奖收益金额',   align:'center'},
        {field:'specialContributionMoney',minWidth:120,   title: '特别贡献奖收益金额',   align:'center'}
        
    ]];
	tableOption.url = baseURL + 'ht/report/list';
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
    
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
        	userId:null,
        	userName: null,
        	userMobile:null,
        	groupUserIds:null,
        	beginTime:null,
        	endTime:null
        },
        flag:false
    },
    updated: function(){
        layui.form.render();
    },
    methods: {
        query: function () {
        	vm.q.userId = null;
        	vm.q.groupUserIds = null;
            vm.reload('ht/report/list');
        },
        selectedRow: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请先选择父级用户");
                return ;
            }
            var userId =null;
            $.each(list, function(index, item) {
            	userId =  item.userId;
            });
            return userId;
        },
        selectedRowGroup: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0 || list.length > 1){
                alert("请先选择父级用户");
                return ;
            }
            var groupUserIds =null;
            $.each(list, function(index, item) {
            	groupUserIds =  item.groupUserIds;
            });
            return groupUserIds;
        },
        lookUnder:function(){//我的下级
        	var	userId = vm.selectedRow();
        	if(userId == null){
        		return ;
        	}
        	vm.q.userId = userId;
        	vm.q.groupUserIds = null;
        	vm.reload('ht/report/lookUnder');
        	
        },
        lookMyTeam:function(){//我的团队
        	var	groupUserIds = vm.selectedRowGroup();
        	if(groupUserIds == null){
        		return ;
        	}
        	vm.q.userId = null;
        	vm.q.groupUserIds = groupUserIds;
        	vm.reload('ht/report/lookMyTeam');
        },
        reload: function (url) {
            layui.table.reload('gridid', {
            	url: baseURL+ url,
                page: {
                    curr: 1
                },
                where: vm.q
            });
        }
    }
});

