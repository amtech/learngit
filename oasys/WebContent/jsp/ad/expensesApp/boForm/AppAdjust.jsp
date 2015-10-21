<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var $tiaozhengDialog;
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
				"btaId":$row.btaId,
				"appNo":$row.appNo,
				"taskId":$row.taskId,
				"handleResult":result=='shenqingchongti'?1:0
			}
			$.ajax({
				type:"POST",
				url:"expensesAppController/submitTask.do",
				data:data,
				dataType:"JSON",
				success : function(msg) {
					$("#waitTaskGrid").datagrid('reload');
					$banliDialog.dialog('close');
				}
			});
		}
	});
}
//申请调整
function tiaoZhengDialog(){
	$tiaozhengDialog = $("<div></div>").dialog({
		title: '申请调整',    
	    width: 920,    
	    height: 600,    
	    closed: false,    
	    cache: false,    
	    href: 'jsp/ad/expensesApp/expensesAppForm.jsp',    
	    modal: true,
	    onClose : function(){
	    	$(this).dialog('destroy');
	    },
	    buttons : [ {
	    	text:'保存',
	    	iconCls : 'icon-add',
			handler:saveAddExpensesApp
	    },{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$tiaozhengDialog.dialog('destroy');
			}
		}]
	});
}
//执行新增
function saveAddExpensesApp(){
	$.ajax({
		type:'POST',
		url:'expensesAppController/saveExpensesApp.do',
		data:$("#expensesAppForm").serialize(),
		dataType:'JSON',
		success:function(msg){
		   if(msg.status){
			   $tiaozhengDialog.dialog('destroy');//销毁弹窗
		   }
		   //弹出提示信息
		   parent.$.messager.show({
    			title : msg.title,
    			msg : msg.message,
    			timeout : 4000 * 2
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
	    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="tiaoZhengDialog();">申请调整</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('shenqingchongti',this);">申请重提</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="submitTask('shenqingchexiao',this);">申请撤销</a>
	</div>
