<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">		
$(function(){
	//上传附件
	$("#upploadAttachment").click(function(){
		fileUploadsDlg($selRow.stampTurnover.appNo);
	});
	//查看附件
	$("#checkAttachment").click(function(){
		checkAttachementDetail($selRow.stampTurnover.appNo);
	});
	
});

	/** 增加或者修改理财产品的数据**/
	function saveTaskFunc(result,isSuccess){
		//校验理财产品输入的信息
   		var isValid = $("#investProductInputOrSaveForm").form('validate');
    	if(!isValid){
    		return false;
    	}  	
		$("#result").val(result);
		$("#isSuccess").val(isSuccess);
    	 $.ajax({
    		type: "POST",
    		url:"stampTurnoverAppController/saveTaskStampTurnoverApp.do",
    		data:$('#investProductInputOrSaveForm').serialize(),
    		async: false,//默认true设置下，所有请求均为异步请求
    		cache: true,
    	    success: function(iJson) {    	    	
     	    	if(iJson.status){
    	    		$("#saveOrUpdateStampTurnoverDialog").dialog("close");//关闭弹窗
    	    		$("#dg").datagrid("reload");//刷新表格
    	    	}
    	    	parent.$.messager.show({
    				title : '提示',
    				msg : iJson.message,
    				timeout : 4000 * 2
    			}); 
    	    } 
    	});
	}
	
	function closeWindow(){
		$('#saveOrUpdateInvestProductDialog').dialog("close"); 
	}

	
	
</script>

	
	<div style="margin-left: 5px;margin-top: 5px;">
	   <form id="investProductInputOrSaveForm" method="post">
	   <input type="hidden" id="staId" name="staId"/>
	   <input type="hidden" id="taskID" name="taskID"/>
	   <input type="hidden" id="result" name="result" value="" />
	   <input id="appNo" name="appNo" type="hidden"/>
	   <input type="hidden" id="isSuccess" name="isSuccess" value="" />
	   
			<table class="table"  width="95%">
				<tr>
					<th>申请部门:</th>						
					<td>
						<input id="orgName" name="orgName" type="text"   class="easyui-textbox"  readonly="readonly"/>
					</td>
					<th>申请人:</th>
					<td>
						<input id="userName" name="userName" type="text"  class="easyui-textbox"  readonly="readonly"/>
					</td>
					<th>申请时间:</th>
					<td>
						<input name="appDate" id="appDate"  type="text" class="easyui-textbox"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th align="right">接收部门:</th>
					<td>
						<input name="receiverOrgName" id="receiverOrgName" type="text"   readonly="readonly"  />
					</td>				
					<th align="right">接收人:</th>
					<td>
						<input name="receiverUserName" id="receiverUserName" type="text" class="easyui-textbox" readonly="readonly" />
					</td>
					<th align="right">接收时间:</th>	
					<td>
						<input name="receiverDate" id="receiverDate"  type="text" class="easyui-textbox"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th align="right">监交部门:</th>
					<td>
						<input name="superviserOrgName" id="superviserOrgName" type="text"  readonly="readonly" />
					</td>				
					<th align="right">监交人:</th>
					<td>
						<input name="superviserUserName" id="superviserUserName" type="text"  readonly="readonly" />
					</td>
					<th align="right">监交时间:</th>	
					<td>
						<input name="superviserDate" id="superviserDate" type="text"  class="easyui-textbox"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th colspan="1">*审批意见</th>
					<td colspan="5"></td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td colspan="5"><textarea name="taskModel.remark" id="taskModel.remark" class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" data-options="required:true,validType:'length[0,200]'"></textarea></td>
				</tr>
				
				<tr>
					<td colspan="3"></td>
				   <td colspan="3" align="right">
						<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
						<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>						   
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveTaskFunc('ChiefExecutiveOfficerSubmit',1);">审批通过</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" onclick="saveTaskFunc('ChiefExecutiveOfficerBack',0);">审批驳回</a>
				   </td>
				</tr>					
			</table>
		</form>
	</div>				
