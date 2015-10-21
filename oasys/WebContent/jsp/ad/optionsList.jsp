<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	createOptionsGrid();
}); 
//审批意见列表
 function createOptionsGrid(){
	$("#optionsGrid").datagrid({
		url : "auditProcHisController/findAllAuditProcHisList.do",
		width : 'auto',
		height : 463,
		pagination:false,
		rownumbers:true,
		border:false,
		singleSelect:true,
		nowrap:true,
		queryParams:{"appNo":$$row.appNo},
		multiSort:false,
		columns : [ [ 
		              {field : 'appNO',title : '申请编号',width : 120,sortable:true,align:'center'},
		              {field : 'handler',title : '受理人',width : 120,align:'center'},
		              {field : 'roleName',title : '受理人角色',width :100,align:'center'},
		              {field : 'handleResult',title : '处理结果',width :70,align:'center'},
		              {field : 'handleDate',title : '受理时间',width :140,align:'center'},
		              {field : 'remark',title : '备注信息',width :120,align:'center'},
		              {field : 'id',title : '查看附件',width :90,align:'center',formatter:function(value,row,index){
		            		return "<a href='javascript:void(0);' onclick='lookAttachment("+index+");'>查看附件</a>" ;
		              }}
		] ],
		onLoadSuccess:function(data){
			$(this).datagrid("doCellTip",{'max-width':'100px'});
		}
	});
} 

// 根据行索引获取行信息
function getRowData2 (index) {
    if (!$.isNumeric(index) || index < 0) { return undefined; }
    var rows = $("#optionsGrid").datagrid("getRows");
    return rows[index];
}
//查看附件信息
function lookAttachment(index){
		var row = getRowData2(index);
		$("#attachmentList").dialog("open");
		$("#lookAttachmentList").datagrid({
			url : "attachment/attachmentAction!findAllAttachmentList.action",
			width : 'auto',
			height : 430,
			pagination:false,
			rownumbers:true,
			border:false,
			singleSelect:true,
			nowrap:true,
			queryParams:{"orderId":row.investOrderId,"userId":row.assignee,"orderType":"attachment_type_invest"},
			multiSort:false,
			columns : [ [ 
			              {field : 'attName',title : '附件名称',width : 200,sortable:true,align:'center'},
			              {field : 'attTypeName',title : '附件类型',width : 160,align:'center'},
			              {field : 'creatorName',title : '创建者',width : 170,align:'center'},
			              {field : 'id',title : '查看附件',width :220,align:'center',formatter:function(value,row,index){
			            		var result = "<a target='_blank' href='jsp/openoffice/documentView.jsp?attId="+row.attId+"'>在线预览</a>　　" ;
			            			result += "<a target='_blank' href='javascript:void(0);' onclick=\"downloadAttachment('"+row.attId+"');\">下载</a>　　" ;
			            		return result;
			              }}
		    ] ]
		});
}
</script>
<table id="optionsGrid"></table>
<div id="attachmentList" class="easyui-dialog" title="附件信息" style="width:800px;height:500px;" data-options="resizable:true,modal:true" closed="true">
	<table id="lookAttachmentList"></table>
</div> 
