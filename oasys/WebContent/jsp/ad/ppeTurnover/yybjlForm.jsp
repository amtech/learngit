<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.textStyle{
  text-align: right;
}
</style>
<script type="text/javascript">
$(function(){
	 $("#acceptTaskForm").form('load',$row);
	 
	//上传附件
		$("#upploadAttachment").click(function(){
			fileUploadsDlg($row.appNo);
		});
	//查看附件
	$("#checkAttachment").click(function(){
		checkAttachementDetail($row.appNo,'');
	});
})
/**
 * 营业部经理驳回/通过
 */
function submitTask(result,object){
	// 验证备注信息是否已经填写
	if($("#comment").val()==""){
		$.messager.alert("提示","请填写备注信息后再进行提交!","warning")
		return false;
	}
	// 确认是否提交
	//0驳回 1是通过
	$.messager.confirm('提示', '点击按钮之后将进入下一个任务活动环节,此任务将对您不可见!', function(r){
		if (r){
			var data = {
				"remark":$("#comment").val(),
				"result":result,
				"ptaId":$row.ptaId,
				"appNo":$row.appNo,
				"taskID":$row.taskID,
				"handleResult":result=='yingyebujinglitongguo'?1:0
			}
			$.ajax({
				type:"POST",
				url:"ppeTurnoverAppController/submitTask.do",
				data:data,
				dataType:"JSON",
				success : function(rsp) {
					if(rsp.status){
						parent.$.messager.show({
							title : rsp.title,
							msg : rsp.message,
							timeout : 1000 * 2
						});
					}
					
					$("#dg").datagrid('reload');
					$("#addWindow").dialog('close');
				}
			});
		}
	});
}
</script>
     
<div data-options="region:'north',title:'North Title',split:true">
	<div style="overflow: auto;margin-left: 3px;">
	<form id="acceptTaskForm" method="post">
		<!--  <input name="id" id="id"  type="hidden"/>
		 <input name="auditId" type="hidden" value="noauditId"/>  -->
		 <table class="table" cellpadding="5px;">
			<tr>
			    <td class="textStyle">申请编号:</td>
				<td><input name="appNo" class="easyui-textbox" disabled="disabled" type="text"/></td>
				<td class="textStyle">申请人:</td>
				<td><input name="name" class="easyui-textbox" disabled="disabled" type="text"/></td>
				<td class="textStyle">申请部门:</td>
				<td><input name="appDeptName" class="easyui-textbox" disabled="disabled"/></td>
			</tr>
			<tr>
				<td class="textStyle">合计金额(元):</td>
				<td><input name="ppeTotalAmt" class="easyui-textbox easyui-numberbox" disabled="disabled"/></td>
				<td class="textStyle">申请日期:</td>
				<td><input name="appDate" class="easyui-textbox easyui-datebox" disabled="disabled"/></td>
				<!-- <td class="textStyle">计划到货日期:</td>
			    <td><input name="planRecDate" class="easyui-textbox" readonly="readonly"/></td> -->
			</tr>
			<tr>
			 	<td class="textStyle">备注:</td>
				<td colspan="5">
					<textarea id="comment" name="comment" class="easyui-validatebox easyui-textbox" style="width:688px;height:70px;"></textarea>
				</td>
			</tr>
		 </table>
	</form>
	</div>
	
	<div style="margin-left: 25px;margin-bottom: 5px;">
		<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
		<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>	
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('yingyebujinglitongguo',this);">营业部经理通过</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('yingyebujinglibo',this);">营业部经理驳回</a>
	</div>
	
	<div id="lookInfo" class="easyui-accordion" style="height: 300px;width: 980px;overflow: hidden;">
	    <div title="备注信息" data-options="iconCls:'icon-cstbase',selected:true" >   
			<table id="lookLoanOrderdg" title="申请备注的信息"></table>
		</div>
	    <div title="附件信息" data-options="iconCls:'icon-cstbase'" >   
			<table id="lookAttachmentList" title="申请附件的信息"></table>
		</div>
	</div>
</div>   
