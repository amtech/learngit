<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
function submitTask(result,object){
	if($("#comment").val()==""){// 验证备注信息是否已经填写
		$.messager.alert("提示","请填写备注信息后再进行提交!","warning")
		return false;
	}
	$.messager.confirm('提示', '点击按钮之后将进入下一个任务活动环节,此任务将对您不可见!', function(r){
		if (r){
			var data = {
				"remark":$("#comment").val(),
				"result":result,
				"btaId":$row.btaId,
				"appNo":$row.appNo,
				"taskId":$row.taskId,
				"handleResult":result=='bumenfuzerentongguo'?1:0//0驳回 1是通过
			}
			$.ajax({
				type:"POST",
				url:"expensesAppController/submitTask2.do",
				data:data,
				dataType:"JSON",
				success : function(msg) {
					$("#waitTaskGrid").datagrid('reload');
					$banliDialog.dialog('destroy');
				}
			});
		}
	});
}
</script>
<div data-options="region:'north',title:'North Title',split:true">
	<div style="overflow: auto;margin-left: 3px;">
	<form id="acceptTaskForm" method="post">
		 <table class="table" cellpadding="5px;">
			<tr>
			    <td class="textStyle">申请编号:</td>
				<td><input name="appNo" class="easyui-textbox" readonly="readonly" type="text"/></td>
				<td class="textStyle">申请人:</td>
				<td><input name="account" class="easyui-textbox" readonly="readonly" type="text"/></td>
				<td class="textStyle">申请部门:</td>
				<td><input name="fullName" class="easyui-textbox" readonly="readonly"/></td>
			</tr>
			<tr>
				<td class="textStyle">申请金额(元):</td>
				<td><input name="appAmt" class="easyui-textbox" readonly="readonly"/></td>
				<td class="textStyle">申请日期:</td>
				<td><input name="appDate" class="easyui-textbox" readonly="readonly"/></td>
				<td class="textStyle">支付方式:</td>
			    <td><input name="payModeName" class="easyui-textbox" readonly="readonly"/></td>
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('bumenfuzerentongguo',this);">部门负责人通过</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('bumenfuzerenbohui',this);">部门负责人驳回</a>
	</div>
</div>   
