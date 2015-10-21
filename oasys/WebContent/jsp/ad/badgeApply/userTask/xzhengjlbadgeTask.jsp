<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<style type="text/css">
	#acceptTaskForm table input{border: none;}
	.table th{ text-align: right;}
	.table td{ text-align: left;}
</style>
<script type="text/javascript">
	function getTaskRowData (index) {
		if (!$.isNumeric(index) || index < 0) { return undefined; }
	    var rows = $("#lookLoanOrderdg").datagrid("getRows");
	    return rows[index];
	}
	
	var $row;
	$(function(){
		// 查看申请状态
		 $row = $("#dg").datagrid('getSelected');
		$("#taskId").val($row.taskId);
		$("#appNo").val($row.appNo);
		$grid = $("#lookLoanOrderdg").datagrid({
			url : "auditProcHisController/findAllAuditProcHisList.do",
			width : 'auto',
			height : 610,
			pagination:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			nowrap:true,
			queryParams:{"appNo":$row.appNo},
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
			              ] ]
		}); 
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
		var row=getTaskRowData(index)
		checkAttachementDetail($row.appNo,row.handler,'');
	}

	//业务办理
	function microcreditOpinion(result,isSuccess){
		
		if($("#ideaRemark").val()==""){
			
			$.messager.alert("提示","请填写备注信息后再进行提交!","warning")
			return false;
		}
		//处理结果
		$("#isSuccess").val(isSuccess);
		//申请状态
		$("#result").val(result);
		
		$.ajax({
			url :"BadgeTask/saveTaskMgrResult.do",
			data : $("#badgeTaskForm").serialize(),
			async: false,
			type : "post",
			success : function(res) {
					$grid.datagrid('reload');
					 if(res.status){
							parent.$.messager.show({
								title : '提示',
								msg : '任务办理成功!',
								timeout : 4000 * 2
							});
						}else{
							$.messager.alert("提示", '任务办理失败!',"error")
						} 
			},
			error:function(){
				
			}
		});
	}
	
	
		
</script>
<!-- 受理任务 S -->
<div data-options="region:'north',title:'North Title',split:true">
	<div style="width: 986px;height: 190px;overflow: auto;">
			<form id="badgeTaskForm" method="post">
				<input id="pnrId" name="pnrId" type="hidden"/><!-- 工牌申请id -->
				 <input name="taskId" id="taskId"  type="hidden"/><!-- 任务id -->
				 <input name="appNo" id="appNo"  type="hidden"/><!-- 申请编号 -->
				 <input id="result" name="result" type="hidden" /><!-- 申请状态编码 -->
				  <input name="isSuccess" id="isSuccess"  type="hidden"/><!-- 处理结果-->
				 <table class="table" cellpadding="5px;">
					 <tr>
					    <th>申请时间:</th>
						<td><input id="appDate" name="appDate" readonly="readonly" class="easyui-textbox easyui-datebox" data-options="editable:false" type="text"/></td>
					</tr>
					
					<tr>
					 	<th>备注:</th>
						<td colspan="3">
							<textarea id="ideaRemark" name="ideaRemark" class="easyui-validatebox easyui-textbox" style="width:300px;height:70px;"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
							<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>		
						</td>
					</tr>
				 </table>
			</form>
		</div>
	
		<div style="width: 980px;height:30px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="microcreditOpinion('AdminManagerPass','1');">工牌申请通过</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="microcreditOpinion('AdminManagerReject','0');">工牌申请驳回</a>
		</div>
		<div id="lookLoanOrderdg"></div>
</div>  

 </div>
<!-- 决绝决议表E -->	
