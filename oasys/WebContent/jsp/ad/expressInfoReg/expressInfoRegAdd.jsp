<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">



//初始化构建列表
$(function(){
		//初始化组织机构
		$("#receiverOrgName1").combotree({
			width:171,
			url:"Organization/organizationList.do",
			idFiled:'id',
		 	textFiled:'name',
		 	valueFiled:'id',
		 	parentField:'pid',
		 	onLoadSuccess:function(data){
		 		//加一个全部
		 	},
		 	onChange:function(){
		 			$("#deptNo").val($(this).combotree('getValue'));
		 			 RenderName($(this).combotree('getValue'));
		 	}
		}); 
		
})

function closeWindow(){
	$('#saveOrUpdateExpressDialog').dialog('close');
	$("#dg").datagrid("reload");
}

//用户名的下拉
function RenderName(organizeId){
	//选中部门后下拉框
	$("#receiverUserName1").combobox({
		width:171,
		multiple:false,
		separator:",", 
		url:"BadgeApp/findOrgUserList.do?organizeId="+organizeId,
		valueField:'code',
	 	textFiled:'text',
	 	onLoadSuccess:function(data){
	 		//加一个全部
	 	},
	 	onChange:function(){
	 		$("#addresseeNo").val($(this).combotree('getValue'));
	 	}
	}); 
}




function saveExpressInfoReg(){
	var isValid = $("#expressInfoRegForm").form('validate');
   	if(!isValid){
   		return false;
   	}  	
	$.ajax({
	   url: "expressInfoRegController/addExpressInfoReg.do",
	   type: "POST",
	   data:$('#expressInfoRegForm').serialize(),
         dataType:'JSON',
	   success: function(msg){
	    	if(msg.status){
	    		$("#dg").datagrid("load",{});//刷新表格   
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
    <form id="expressInfoRegForm"  method="post" style="width: 875px;margin-left:5px;">
    <div style="margin-left: 5px;margin-top: 5px;">
	   <input type="hidden" id="eirId" name="eirId" value="${expressInfoReg.eirId}"/>
	   <input type="hidden" id="registrantNo" name="registrantNo" value="${expressInfoReg.registrantNo}"/>
	   <input type="hidden" id="addresseeNo" name="addresseeNo" value="${expressInfoReg.addresseeNo}"/>
	   <input type="hidden" id="deptNo" name="deptNo" value="${expressInfoReg.deptNo}"/>
			<table class="table"  width="95%">
				<tr>
					<th>登记人:</th>
					<td>
						<input id="userName" name="userName" value="${expressInfoReg.userName}"  type="text" class="easyui-textbox"  readonly="readonly" data-options="validType:'length[0,50]'"/>
					</td>
					
					<th>登记日期:</th>						
					<td colspan="3">
						<input id="regDatetime" name="regDatetime" value="${expressInfoReg.regDatetime}" type="text"  class="easyui-textbox"  readonly="readonly"  data-options="validType:'length[0,50]'"/>
					</td>
				</tr>
				<tr>
					<th>快递单号:</th>
					<td>
						<input name="expNo" id="expNo" value="${expressInfoReg.expNo}"  type="text"   class="easyui-textbox easyui-validatebox easyui-numberbox"   editable="false"   data-options="validType:'int',required:true"/>
					</td>
					<th>金额:</th>
					<td colspan="3">
						<input id="expAmt" name="expAmt" value="${expressInfoReg.expAmt}"  class="easyui-textbox easyui-validatebox easyui-numberbox" min="0.01"   max="999"  precision="2"   type="text"  data-options="validType:'intOrFloat',required:true"/>
					</td>
				</tr>
				<tr>
					<th>收件部门:</th>
					<td><input name="receiverOrgName1" id="receiverOrgName1" type="text"  style="width: 170px" data-options="required:true" value="${expressInfoReg.addOrgName }"/></td>
					<th>收件人:</th>
					<td><input name="receiverUserName1" id="receiverUserName1" type="text" class="easyui-combobox" style="width: 170px" data-options="required:true" value="${expressInfoReg.addUserName }"/></td>				
					<th>收件日期:</th>
					<td><input name="nhwrDate" id="nhwrDate"  class="easyui-datebox"  data-options="validType:'Date',required:true" value="${expressInfoReg.nhwrDate }"/></td>										
				</tr>					
				<tr>
					<th colspan="1">事由:</th>
					<td colspan="5"><textarea name="nhwrReson"  id="nhwrReson"  class="easyui-textbox" style="width: 600px; height: 90px;" data-options="validType:'length[0,200]'">${expressInfoReg.nhwrReson }</textarea></td>
				</tr>
				<tr>
					<th colspan="1">备注:</th>
					<td colspan="5"><textarea name="remark"  id="remark"  class="easyui-textbox" style="width: 600px; height: 90px;" data-options="validType:'length[0,200]'">${expressInfoReg.remark }</textarea></td>
				</tr>			
				<tr>
					<td colspan="4"></td>
				   <td colspan="2" align="right">
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveExpressInfoReg();">保存</a>
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton"  onclick="closeWindow();">关闭</a>
				   </td>
				</tr>					
			</table>
	</div>			
    </form>
