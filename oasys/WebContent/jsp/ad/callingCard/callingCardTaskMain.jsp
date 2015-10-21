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
$(function(){
	createWaitTaskGrid();
});

//上传附件,明细存储taskId
$("#upploadAttachment").click(function(){
	fileUploadsDlg($row.appNo);
});
//查看附件
$("#checkAttachment").click(function(){
	checkAttachementDetail($row.appNo,$row.taskModel.assistant,'');
});

function lookAttachment(index){
	var row=getRowData(index)
	checkAttachementDetail($row.appNo,row.handler,'');
}

//渲染grid
function createWaitTaskGrid(){
	$("#waitTaskGrid").datagrid({
		url:'callingCard/QueryCardTask.do',
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
			{field : 'appNo',        title : '申请编号',    width : $(this).width * 0.1, align:'center' },
			{field : 'userName',        title : '姓名',    width : $(this).width * 0.1, align:'center' },
			{field : 'deptName',    title : '部门',   width : $(this).width * 0.1, align:'center' },
			{field : 'personalTel',    title : '个人电话',    width : $(this).width * 0.1, align:'center' },
			{field : 'officeTel',    title : '办公电话',    width : $(this).width * 0.1, align:'center' },
			{field : 'email',    title : '邮箱',    width : $(this).width * 0.2, align:'center' },
			{field : 'branchAddr',    title : '地址',    width : $(this).width * 0.1, align:'center' },
			{field : 'appQty',    title : '数量',    width : $(this).width * 0.1, align:'center' },
			{field : 'appDate',    title : '申请日期',    width : $(this).width * 0.1, align:'center' },
			{field : 'sumAppQty',    title : '总和',    width : $(this).width * 0.1, align:'center' },
			{field : 'remark',    title : '备注',    width : $(this).width * 0.1, align:'center' },
		    {field : 'caozuo',    title : '操作',    width : $(this).width * 0.1, align:'center',
				formatter: function(value,row,index){
					var result = "";
				    if (row.taskModel.assistant == null || row.taskModel.assistant == "") {
						result += "<a href='javascript:void(0);' onclick='holdWorkTask("+index+");'>签收任务</a>　　";
					}else{
						result += "<a href='javascript:void(0);' onclick='handleTaskDialog("+index+");'>办理任务</a>　　";
					}
				    result += "<a href='javascript:void(0);' onclick='checkHistoryOpinions("+ index +");'>查看审批意见</a>　　";
				    result += "<a href='javascript:void(0);' onclick='lookAttachment("+ index +");'>查看附件</a>　　";
					return result;        		             		  					           		  
				}
			}
	   ]],
	   onLoadSuccess:function(data){
		   var rows = data.rows;
           var mergeMap = {};
           if(rows){
           	for(var i=0;i<rows.length;i++){
           		var appNo = rows[i].appNo;
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
               field: 'sumAppQty',
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
		url:"callingCard/SignTask.do",
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
					width:1000,
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
	var src = "workflowAction/showProcessImg.do?taskId="+row.taskId+"&imgName=OA_AD_purchase_BO";
	$('#imageDialog').dialog("open");
	$("#image").attr("src", src);
}
var $$row = "";
//查看历史审批意见
function checkHistoryOpinions(index){
	var rows = $("#waitTaskGrid").datagrid("getRows");
	$$row = rows[index];//获取本条数据
	$("#optionsDialog").dialog('open').dialog('refresh');
}
</script>
<body>
<div class="position" style="margin-top: 5px;">您当前所在位置： 任务管理  &gt; 待办任务</div>
<table id="waitTaskGrid"></table>
<!-- 新增弹框 -->
<div id="addWindow"></div>
<div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
	<img id="image" src="" >
</div>
<div id="optionsDialog" class="easyui-dialog" title="历史审批意见" style="width:900px;height:400px;" closed="true" data-options="href:'jsp/ad/optionsList.jsp',resizable:true,modal:true"></div>
</body>
</html>