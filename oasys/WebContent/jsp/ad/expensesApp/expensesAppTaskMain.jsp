<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../../layout/script.jsp"></jsp:include>
<title>待办任务主页面</title>
</head>
<script type="text/javascript">
var $row;
var $$row;
var $banliDialog;
$(function(){
	createWaitTaskGrid();
});
//渲染grid
function createWaitTaskGrid(){
	$("#waitTaskGrid").datagrid({
		url:'expensesAppController/findAllExpensesAppTaskList.do',
		width: 'auto',
		height : $(this).height()-40,
		pagination:true,
		rownumbers:true,
		border:false,
		singleSelect:true,
		nowrap:true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
		pageSize:30,
		pageList:[10,20,30,40],
		remoteSort:false,//定义是否从服务器对数据进行排序。
		striped:true,//是否显示斑马线
		columns:[[
		        {field : 'appNo',title : '申请编号',width :120,align : 'center'},
                {field : 'account',title : '申请人',width : 100,align : 'center'},
	            {field : 'fullName',title : '所属部门',width : 100,align : 'center'},
                {field : 'appDate',title : '申请日期',width : 150,align : 'center'},
                {field : 'appAmt',title : '申请金额(元)',width : 80,align : 'center'},
                {field : 'payModeName',title : '支付方式',width : 80,align : 'center'},
                {field : 'intoAct',title : '转入账号',width : 180,align : 'center'},
                {field : 'bankName',title : '银行名称',width : 80,align : 'center'},
                {field : 'actName',title : '账户名称',width : 80,align : 'center'},
                {field : 'billTypeName',title : '票据类型',width : 80,align : 'center'},
                {field : 'billTypeOther',title : '其他票据类型',width : 80,align : 'center'},
                {field : 'procStatus',title : '流程状态',width : 80,align : 'center',formatter:function(value,row,index){
                	if(row.procStatus == "1"){
                		return "初始状态";
                	}else if(row.procStatus == "2"){
                		return "审批中";
                	}else if(row.procStatus == "3"){
                		return "已完成";
                	}else{
                		return "已撤销";
                	}
                }},
                {field : 'remark',title : '备注信息',width : 240,align : 'center'},
                {field : 'caozuo',title : '操作',width :180,align : 'center',formatter:function(value,row,index){
                	var result = ""; 
				    if (row.taskModel.assistant == null || row.taskModel.assistant == "") {
						result += "<a href='javascript:void(0);' onclick='holdWorkTask("+index+");'>签收任务</a>　　";
					}else{
						result += "<a href='javascript:void(0);' onclick='handleTaskDialog("+index+");'>办理任务</a>　　";
					} 
				    result += "<a href='javascript:void(0);' onclick='checkHistoryOpinions("+ index +");'>查看审批意见</a>　　";
					return result;
                }}
                
	   ]],
	   onLoadSuccess:function(data){
           $(this).datagrid("doCellTip",{'max-width':'100px'});
	  }
	});
}
//根据index获取该行
function getRowData(index){
	if (!$.isNumeric(index) || index < 0) {
		return undefined;
	}
	var rows = $("#waitTaskGrid").datagrid("getRows");
	return rows[index];
}
//签收任务
function holdWorkTask(index){
	var rows = $("#waitTaskGrid").datagrid("getRows");
	var row = rows[index];
	$.ajax({
		type:"POST",
		url:"expensesAppController/holdWorkTask.do",
		data:{"taskId" : row.taskId},
		dataType:"JSON",
		success:function(rsp){
			if(rsp.status){
				parent.$.messager.show({
					title : rsp.title,
					msg : rsp.message,
					timeout : 1000 * 2
				});
			}else{
				parent.$.messager.alert(rsp.title,rsp.message,'warning');
			}
			$("#waitTaskGrid").datagrid('reload');
		}
	});
}
//办理任务
function handleTaskDialog(index){
	var rows = $("#waitTaskGrid").datagrid("getRows");
	$row = rows[index];
	$.ajax({
		type:"POST",
		url:"workflowAction/findTaskFormKeyByTaskId.do",
		data:{"taskId":$row.taskId},
		onClose:function(){
			$("#waitTaskGrid").datagrid('reload');
		},
		success:function(jspName){
			 $banliDialog = $("<div></div>").dialog({
					title:'办理任务',
					width:1000,
					height:650,
					modal:true,
					href:jspName,
					onClose:function(){
						$(this).dialog('destroy');
					}
			 }); 
		}
	});
}
//查看流程图
function showImage(index){
	var rows = $("#waitTaskGrid").datagrid("getRows");
	var row = rows[index];
	var src = "expensesAppController/showProcessImg.do?btaId="+row.btaId;
	$('#imageDialog').dialog("open");
	$("#image").attr("src", src);
}
//查看历史审批意见
function checkHistoryOpinions(index){
	var rows = $("#waitTaskGrid").datagrid("getRows");
	$$row = rows[index];//获取本条数据
	$("<div></div>").dialog({
		title: '历史审批意见',    
	    width: 900,    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    href: 'jsp/ad/optionsList.jsp',    
	    modal: true,
	    onClose : function(){
	    	$(this).dialog('destroy');
	    	$$row = null;
        }
	});
}
</script>
<body>
<div class="position" style="margin-top: 5px;">您当前所在位置： 任务管理  &gt; 待办任务</div>
<table id="waitTaskGrid"></table>
<div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
	<img id="image" src="" >
</div>
</body>
</html>