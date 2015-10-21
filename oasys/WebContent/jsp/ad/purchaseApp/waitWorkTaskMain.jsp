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
<style type="text/css">
.easyui-textbox {
	height: 18px;
	width: 170px;
	line-height: 16px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
}

textarea:focus, input[type="text"]:focus {
	border-color: rgba(82, 168, 236, 0.8);
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
	outline: 0 none;
}
.table {
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
	max-width: 100%;
}
fieldset {
	    border-width: 1px;
	    margin-top: 5px;
	    border-color:#F5F5F5;
	    -moz-border-radius:8px;
}
input, textarea {
	font-weight: normal;
}

.table td {
	padding: 6px;
}
.table th{
    text-align: right;
	padding: 6px;
}
.textStyle{
  text-align: right;
}
</style>
</head>
<script type="text/javascript">
$(function(){
	createWaitTaskGrid();
});
//渲染grid
function createWaitTaskGrid(){
	$("#waitTaskGrid").datagrid({
		url:'purchaseAppController/findAllPurchaseAppTaskList.do',
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
	            {field : 'fullName',title : '申请部门',width : 100,align : 'center'},
                {field : 'totalAmt',title : '总计金额(元)',width : 80,align : 'center'},
                {field : 'appDate',title : '申请日期',width : 80,align : 'center'},
                {field : 'planRecDate',title : '计划到货日期',width : 80,align : 'center'},
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
                {field : 'remark',title : '备注',width : 110,align : 'center'},
                {field : 'articleName',title : '物品名称',width : 80,align : 'center'},
                {field : 'model',title : '型号规格',width : 80,align : 'center'},
                {field : 'price',title : '单价',width : 80,align : 'center'},
                {field : 'qty',title : '数量',width : 80,align : 'center'},
                {field : 'ztotalAmt',title : '合计价格(元)',width : 80,align : 'center'},
                {field : 'purpose',title : '用途',width : 80,align : 'center'},
                {field : 'user',title : '使用人',width : 80,align : 'center'},
                {field : 'depositary',title : '保管人',width : 80,align : 'center'},
                {field : 'zremark',title : '备注信息',width : 80,align : 'center'},
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
		   var rows = data.rows;
           var mergeMap = {};
           if(rows){
           	for(var i=0;i<rows.length;i++){
           		var appNo = rows[i].appNo
           		if( appNo in mergeMap ){
           			mergeMap[appNo].rowspan++;
           		}else{
           			mergeMap[appNo]={"index":i,"rowspan":1}
           		}
           	}
           }
           for(var i in mergeMap){
           	$(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'appNo',
                   rowspan: mergeMap[i].rowspan
               });
           	$(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'account',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'fullName',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'totalAmt',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'appDate',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'planRecDate',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'procStatus',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'remark',
                   rowspan: mergeMap[i].rowspan
               });
               $(this).datagrid('mergeCells',{
                   index: mergeMap[i].index,
                   field: 'caozuo',
                   rowspan: mergeMap[i].rowspan
               });
           }
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
	var row = getRowData(index);
	$.ajax({
		type:"POST",
		url:"purchaseAppController/holdWorkTask.do",
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
	$row = getRowData(index);
	$.ajax({
		type:"POST",
		url:"workflowAction/findTaskFormKeyByTaskId.do",
		data:{"taskId":$row.taskId},
		onClose:function(){
			$("#waitTaskGrid").datagrid('reload');
		},
		success:function(jspName){
			 $("#addWindow").dialog({
					title:'办理任务',
					width:900,
					height:400,
					modal:true,
					href:jspName
			 }); 
		}
	});
}
//查看流程图
function showImage(index){
	var row = getRowData(index);
	var src = "purchaseAppController/showProcessImg.do?paId="+row.paId;
	$('#imageDialog').dialog("open");
	$("#image").attr("src", src);
}
var $$row;
//查看历史审批意见
function checkHistoryOpinions(index){
	var rows = $("#waitTaskGrid").datagrid("getRows");
	$$row = rows[index];//获取本条数据
	$("#optionsDialog").dialog({
		title: '历史审批意见',    
	    width: 900,    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    href: 'jsp/ad/optionsList.jsp',    
	    modal: true,
	    onClose : function(){
	    	$$row = null;
        }
	});
}
</script>
<body>
<div class="position" style="margin-top: 5px;">您当前所在位置： 任务管理  &gt; 任务办理</div>
<table id="waitTaskGrid"></table>
<!-- 新增弹框 -->
<div id="addWindow"></div>
<div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
	<img id="image" src="" >
</div>
<div id="optionsDialog"></div>
</body>
</html>