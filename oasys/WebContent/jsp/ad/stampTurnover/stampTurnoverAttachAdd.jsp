<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">		
	$(function(){
		var isGivebackHi = $("#isGivebackHi").val();
		$('#isGiveback').combobox({
			data:[{ 
				"id":0, 
				"text":"是"
				},{ 
				"id":1, 
				"text":"否" 
				}],
			valueField:'id',
			textField:'text',
			value:isGivebackHi
		});
	});
	/** 清空新添加的理财产品的的数据**/
	function clearForm(){
		$("#StampTurnoverAttachAppForm").form("clear");		
	}	
	/** 增加或者修改理财产品的数据**/
	function saveStampTurnoverAttach(){
		//校验理财产品输入的信息
   		var isValid = $("#StampTurnoverAttachAppForm").form('validate');
		var objData = $('#StampTurnoverAttachAppForm').serialize();
		objData = objData.replace("&appNo=&","&appNo="+$('#appNo').val()+"&");
    	if(!isValid){
    		return false;
    	}  	
    	$.ajax({
    		type: "POST",
    		url:"StampTurnoverAppAttachController/addStampTurnoverAppAttach.do",
    		data:objData,
    		async: false,//默认true设置下，所有请求均为异步请求
    		cache: true,
    	    success: function(iJson) {    	    	
     	    	if(iJson.status){
    	    		$("#stampTurnoverAttachGrid").datagrid("load",{appNo:$('#appNo').val()});//刷新表格   
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
		$('#formWindow').dialog("close"); 
	}
	
	function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	} 

	
	
</script>

	
	<div style="margin-left: 5px;margin-top: 5px;">
	   <form id="StampTurnoverAttachAppForm" method="post">
	   <input type="hidden" id="taaId" name="taaId" value="${stampTurnoverAttach.taaId}"/>
	   <input type="hidden" id="appNo" name="appNo" value="${stampTurnoverAttach.appNo}"/>
	   <input type="hidden" id="isGivebackHi" name="isGivebackHi" value="${stampTurnoverAttach.isGiveback}"/>
	   
	   
			<table class="table"  width="95%">
				<tr>
					<th>印章名称:</th>
					<td>
						<input id="stampName" name="stampName" value="${stampTurnoverAttach.stampName}"  type="text" class="easyui-textbox easyui-validatebox"   data-options="validType:'length[0,50]' , required:true"/>
					</td>
					<th>是否归还:</th>						
					<td >
						<select id="isGiveback" class="easyui-combobox" name="isGiveback"  style="width: 170px;" data-options="required:true"  value="${stampTurnoverAttach.isGiveback }"></select>
					</td>
					<th>归还时间:</th>
					<td>
						<input name="givebackDatetime" id="givebackDatetime" value="${stampTurnoverAttach.givebackDatetime}" class="easyui-datebox"  editable="false" style="width:174px;" data-options="validType:'Date',required:true"/>
					</td>
				</tr>
				<tr>
				<td colspan="6" height="10"></td>
				</tr>
				<tr>
					<th colspan="1">印章移交原因:</th>
					<td colspan="5">
						<input name="turnoverReson" id="turnoverReson" class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" value="${stampTurnoverAttach.turnoverReson }"  data-options="validType:'length[0,100]' , required:true"/>
					</td>
				</tr>
				<tr>
					<th colspan="1">备注:</th>
				   <td colspan="5" align="left">
				      	<input name="remark" id="remark" class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" value="${stampTurnoverAttach.remark }" />
				   </td>
				</tr>	
				<tr>
					<td colspan="5"></td>
				   <td colspan="1" align="right">
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveStampTurnoverAttach();">保存</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" onclick="closeWindow();">关闭</a>
				   </td>
				</tr>					
			</table>
		</form>
	</div>				
