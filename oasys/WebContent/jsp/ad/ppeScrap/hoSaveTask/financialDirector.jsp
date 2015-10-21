<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">
$(function(){
	//上传附件
	$("#upploadAttachment").click(function(){
		fileUploadsDlg($selRow.ppeApp.appNo);
	});
	//查看附件
	$("#checkAttachment").click(function(){
		checkAttachementDetail($selRow.ppeApp.appNo);
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
    		url:"ppeScrapAppController/saveTaskPPEScrapApp.do",
    		data:$('#investProductInputOrSaveForm').serialize(),
    		async: false,//默认true设置下，所有请求均为异步请求
    		cache: true,
    	    success: function(iJson) {    	    	
     	    	if(iJson.status){
    	    		$("#saveOrUpdateInvestProductDialog").dialog("close");//关闭弹窗
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
	
	function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	} 

	
	
</script>

	
	<div style="margin-left: 5px;margin-top: 5px;">
	   <form id="investProductInputOrSaveForm" method="post">
	   <input type="hidden" id="psaId" name="psaId"/>
	   <input type="hidden" id="taskID" name="taskID"/>
	   <input type="hidden" id="result" name="result" value="" />
	   <input type="hidden" id="isSuccess" name="isSuccess" value="" />
			<table class="table"  width="95%">
				<tr>
					<th>申请编号:</th>
					<td>
						<input id="appNo" name="appNo" class="easyui-textbox" readonly="readonly"/>
					</td>
					<th>申请人:</th>
					<td>
						<input id="userName" name="userName" type="text"  class="easyui-textbox"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>申请部门:</th>						
					<td>
						<input id="orgName" name="orgName" type="text"   class="easyui-textbox"  readonly="readonly"  />
					</td>
					<th>申请时间:</th>
					<td>
						<input name="appDate" id="appDate" class="easyui-datebox"  editable="false" style="width:174px;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th colspan="1">*审批意见</th>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td colspan="3"><textarea name="taskModel.remark" id="taskModel.remark" class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" data-options="required:true,validType:'length[0,200]'"></textarea></td>
				</tr>
				
				<tr>
					<td colspan="2"></td>
				   <td colspan="2" align="right">
						<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
						<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>							   
				        <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveTaskFunc('FinancialDirectorSubmit',1);">审批通过</a>
				   </td>
				</tr>					
			</table>
		</form>
	</div>				
