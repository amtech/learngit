<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">	
	function loadUser(){
		$("#user").combogrid({    
		    panelWidth:250,  
		    mode: 'remote',   
		    idField:'userId',    
		    textField:'name',    
		    url:'callingCard/getUserInfo.do?q='+$("#user").val(),
		    columns:[[    
		        {field:'name',title:'用户名',width:120},    
		        {field:'fullName',title:'所在部门',width:90}
		    ]],
		    onChange:function(n,o){
		    	$.ajax({
		    		type: "POST",
		    		url:"callingCard/loadUserInfo.do?q="+n,
		    		async: false,//默认true设置下，所有请求均为异步请求
		    		cache: true,
		    	    success: function(iJson) {
		     	    	$("#dept").val(iJson.fullName);
		    	    }
		    	});		
		    }
		}); 
	}
	
	$(function(){
// 		$("#investProductInputOrSaveForm").form('load',rowdata);
// 		alert(rowdata.userId);
		loadUser();
//     	$("#user").combogrid('setValue',rowdata.userId);
	});
	/** 保存增加或者修改名片申请的数据**/
	function saveInvestProductInfo(){
		//校验输入的信息
		var isValid = $("#investProductInputOrSaveForm").form('validate');
		if(!isValid){
			return false;
		} 
		//保存用车数据
		$.ajax({
			type: "POST",
			url:"vehicleUsesReg/addCarInfo.do",
			data:$('#investProductInputOrSaveForm').serialize(),
			async: false,//默认true设置下，所有请求均为异步请求
			cache: true,
		    success: function(iJson) {    	    	
	 	    	if(iJson.status){
	 	    		alert("成功！");
// 	 	    		$("#addWindow").dialog("close");
		    		$("#dg").datagrid("reload");//刷新表格    	    		
		    		/* disableForm();//禁用form */
		    	}
		    	parent.$.messager.show({
					title : '提示',
					msg : iJson.message,
					timeout : 4000 * 2
				}); 
		    }
		});		
	};
	/** 清空新添加的的的数据**/
	function clearForm(){
		$("#investProductInputOrSaveForm").form("clear");		
	}	
	
	function sumAcount(){
		$("#totalAMT").val(function(){
			return Number($("#fuelCharge").val())+Number($("#roadToll").val())+Number($("#parkingFee").val());
		});
	}
</script>
	<div style="margin-left: 5px;margin-top: 5px;" data-options="iconCls:'icon-cstbase'">
	   <form id="investProductInputOrSaveForm"  method="post">
 	   		<input id="vurId" name="vurId" type="hidden"/>
<!--  	   		<input id="appNo" name=appNo type="hidden"/>理财产品的ID -->
<!--  	   		<input id="unit" name=unit type="hidden"/>理财产品的ID -->
			<table class="table" width="100%">
				<tr>
					<th>使用人:</th>
					<td id="name">
						<input name="user" id="user" class="easyui-textbox easyui-combogrid"/>
					</td>
					<th>部门:</th>
					<td>
						<input name="dept" id="dept" class="easyui-textbox" readonly="true"/>
					</td>
				</tr>
				<tr>
					<th>车牌号码:</th>
					<td>
						<input name="carNo" id="carNo" class="easyui-textbox"/>
					</td>
					<th>使用事由:</th>
					<td>
						<input name="usesReson" id="usesReson" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<th>用车时间:</th>
					<td>
						<input name="usesDateTime" id="usesDateTime" class="easyui-datebox"/>
					</td>
					<th>始发地:</th>
					<td>
						<input name="origin" id="origin" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<th>目的地:</th>
					<td>
						<input id="destination" name="destination" class="easyui-textbox"/>
					</td><th>启程公里数:</th>
					<td>
						<input id="bgKilometer" name="bgKilometer" type="text" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<th>结束公里数:</th>
					<td>
						<input id="edKilometer" name="edKilometer" class="easyui-textbox"/>
					</td><th>归还时间:</th>
					<td>
						<input id="gvDateTime" name="gvDateTime" type="text" class="easyui-datebox"/>
					</td>
				</tr>
				<tr>
					<th>加油费:</th>
					<td>
						<input id="fuelCharge" name="fuelCharge" class="easyui-textbox" value="0" onblur="sumAcount()"/>
					</td><th>路桥费:</th>
					<td>
						<input id="roadToll" name="roadToll" type="text" class="easyui-textbox" value="0" onblur="sumAcount()"/>
					</td>
				</tr>
				<tr>
					<th>停车费:</th>
					<td>
						<input id="parkingFee" name="parkingFee" class="easyui-textbox" value="0" onblur="sumAcount()"/>
					</td><th>合计金额:</th>
					<td>
						<input id="totalAMT" name="totalAMT" type="text" class="easyui-textbox " value="0" readonly="true"/>
					</td>
				</tr>
				<tr>
					<th>备注信息:</th>
					<td colspan="2">
						<input id="remark" name="remark" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
				   <td colspan="4">
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" iconCls="icon-reload" onclick="clearForm();">重置</a>
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveInvestProductInfo();">保存</a>
				   </td>
				</tr>					
			</table>
		</form>
	</div>	