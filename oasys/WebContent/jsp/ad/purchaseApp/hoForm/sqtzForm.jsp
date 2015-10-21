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
/**
 * 营业部经理驳回/通过
 */
function submitTask(result,object){
	if($("#comment").val()==""){
		$.messager.alert("提示","请填写备注信息后再进行提交!","warning")
		return false;
	}
	//0驳回 1是通过
	$.messager.confirm('提示', '点击按钮之后将进入下一个任务活动环节,此任务将对您不可见!', function(r){
		if (r){
			var data = {
				"remark":$("#comment").val(),
				"result":result,
				"paId":$row.paId,
				"appNo":$row.appNo,
				"taskId":$row.taskId,
				"handleResult":result=='shenqingchongti'?1:0
			}
			$.ajax({
				type:"POST",
				url:"purchaseAppController/submitTaskHo.do",
				data:data,
				dataType:"JSON",
				success : function(msg) {
					$("#waitTaskGrid").datagrid('reload');
					$("#addWindow").dialog('close');
				}
			});
		}
	});
}
//申请调整
function tiaoZhengDialog(){
	$("<div></div>").dialog({
		title: '申请调整',    
	    width: 920,    
	    height: 600,    
	    closed: false,    
	    cache: false,    
	    href: 'purchaseAppController/toAddWindow.do',    
	    modal: true,
	    onClose : function(){
	    	$(this).remove();
	    },
	    buttons : [ {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$(this).remove(); 
			}
		}]
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
			    <th class="textStyle">申请编号:</th>
				<td><input name="appNo" class="easyui-textbox" readonly="readonly" type="text"/></td>
				<th class="textStyle">申请人:</th>
				<td><input name="account" class="easyui-textbox" readonly="readonly" type="text"/></td>
				<th class="textStyle">申请部门:</th>
				<td><input name="fullName" class="easyui-textbox" readonly="readonly"/></td>
			</tr>
			<tr>
				<th class="textStyle">合计金额(元):</th>
				<td><input name="totalAmt" class="easyui-textbox" readonly="readonly"/></td>
				<th class="textStyle">申请日期:</th>
				<td><input name="appDate" class="easyui-textbox" readonly="readonly"/></td>
				<th class="textStyle">计划到货日期:</th>
			    <td><input name="planRecDate" class="easyui-textbox" readonly="readonly"/></td>
			</tr>
			<tr>
			 	<th class="textStyle">备注:</th>
				<td colspan="5">
					<textarea id="comment" name="comment" class="easyui-validatebox easyui-textbox" style="width:688px;height:70px;"></textarea>
				</td>
			</tr>
		 </table>
	</form>
	</div>
	
	<div style="margin-left: 25px;margin-bottom: 5px;">
	    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="tiaoZhengDialog();">申请调整</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('shenqingchongti',this);">申请重提</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('shenqingchexiao',this);">申请撤销</a>
	</div>
	
	
</div>   
