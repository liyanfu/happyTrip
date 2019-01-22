var contentEdit;
var layedit = layui.layedit;
$(function () {
	//表格参数
	tableOption.cols =  [[
        {type:'checkbox'},
        {field:'configId', width:100, title: 'ID'},
        {field:'configKey', minWidth:100, title: '参数名'},
        {field:'configVal', minWidth:100, title: '参数值',templet:function(d){
        	//客服二维码，收款二维码
        	if(d.configKey=='SYSTEM_CUSTOMER_SERVICE_IMG_KEY' || d.configKey=='RECHARGE_QRCODE_KEY'){
        		return  '<div><img width="75px" height="50px" src="'+d.configVal+'"></div>' ;
        	}
        	
        	//公司介绍
        	if(d.configKey=='SYSTEM_COMPANY_INTRODUCE_KEY'){
        		return  '<div title="'+d.configVal+'">.......</div>' ;
        	}
        	
        	return d.configVal;
        }},
        {field:'configStatus', width:100, align:'center', title: '状态',templet: '#statusTpl'},
        {field:'remark', minWidth:100, title: '备注'},
        {title: '操作', width:120, templet:'#barTpl',fixed:"right",align:"center"}
    ]];
	tableOption.url = baseURL + 'sys/config/list';
	
	var configType = getUrlParamValue('configType');
	vm.q.configType = configType;
	tableOption.where = {"configType":configType};
	
	//初始化表格
    gridTable = layui.table.render(tableOption);
   
    //状态
    layui.form.on('switch(configStatus)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false});
        var status = 0;
        if(data.elem.checked){
            status = 1;
        }
        vm.updateStatus(data.value, status);

        layer.close(index);
    });
    
    layui.form.on('submit(saveOrUpdate)', function(){
        vm.saveOrUpdate();
        return false;
    });
    
    layui.form.on('submit(saveOrUpdateImg)', function(){
    	if(vm.config.configVal==null || vm.config.configVal==''){
    		alert('请上传相关二维码图片');
    		return false;
    	}
    	vm.config.configVal = $("#imgurl").val();
        vm.saveOrUpdate();
        return false;
    });

    layui.form.on('submit(saveOrUpdateCompany)', function(){
        vm.saveOrUpdateCompany();
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
        vm.copyBean(obj.data);
        if(layEvent === 'edit'){
            vm.update(data.configId,data.configKey);
        } else if(layEvent === 'del'){
            var ids = [data.configId];
            vm.del(ids);
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
	      $("#imgurl").val(res.data.src);
	      vm.config.configVal = res.data.showPath;
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

});

/**
 * 获取URL上指定参数的值
 */
var getUrlParamValue = function(paraName) {
　　　　var url = document.location.toString();
var arrObj = url.split("?");
if (arrObj.length > 1) {
　　var arrPara = arrObj[1].split("&");
　　var arr;
　　for (var i = 0; i < arrPara.length; i++) {
　　　　arr = arrPara[i].split("=");

　　　　if (arr != null && arr[0] == paraName) {
　　　　　　return arr[1];
　　　　}
　　}
　　return "";
}
else {
　　return "";
}
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			configType:null,
            configKey: null
		},
        showForm: false,
        showImgForm:false,
        showCompanyForm:false,
		config: {}
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
                ids.push(item.configId);
            });
            return ids;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.config = {};

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
		update: function (id,key) {
			$.get(baseURL + "sys/config/info/"+id, function(r){
				 vm.copyBean(r.config);
            });

			 var index = null;
			//客服二维码 ,收款二维码
			if(key=='SYSTEM_CUSTOMER_SERVICE_IMG_KEY' || key=='RECHARGE_QRCODE_KEY'){
				index = layer.open({
	                title: "修改",
	                type: 1,
	                content: $("#showImgForm"),
	                end: function(){
	                    vm.showImgForm = false;
	                    layer.closeAll();
	                }
	            });
				 vm.showImgForm = true;
			} else if(key=='SYSTEM_COMPANY_INTRODUCE_KEY'){ //公司介绍
				 //富文本图片上传地址
			    layedit.set({
			        uploadImage: {
			            url: baseURL + 'sys/oss/uploadFull'//接口url
			          , type: 'post' //默认post
			        }
			    });
				
			    layedit.setContent(contentEdit, vm.config.configVal);
				 contentEdit = layedit.build('content');
			    
				index = layer.open({
	                title: "修改",
	                type: 1,
	                content: $("#showCompanyForm"),
	                end: function(){
	                    vm.showCompanyForm = false;
	                    layer.closeAll();
	                }
	            });
				 vm.showCompanyForm = true;
			}else{
				//普通参数
				index = layer.open({
	                title: "修改",
	                type: 1,
	                content: $("#editForm"),
	                end: function(){
	                    vm.showForm = false;
	                    layer.closeAll();
	                }
	            });
				 vm.showForm = true;
			}
             

           
            layer.full(index);
		},
		updateStatus: function (configId, status) {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/config/status",
                data: {configId: configId, status: status},
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
        copyBean:  function(config){
        	vm.config = config;
        },
		del: function (ids) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/config/delete",
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
		saveOrUpdate: function (event) {
			var url = vm.config.configId == null ? "sys/config/save" : "sys/config/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.config),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
                            layer.closeAll();
                            vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		saveOrUpdateCompany: function (event) {
			var content = layedit.getContent(contentEdit);
			vm.config.configVal = content;
			$.ajax({
				type: "POST",
			    url: baseURL + 'sys/config/updateCompany',
			   // contentType: "application/json",
			    data: vm.config,
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
                            layer.closeAll();
                            vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
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