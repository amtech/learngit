<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">	
	/** 保存增加或者修改名片申请的数据**/
	function saveInvestProductInfo(){
		//校验输入的信息
			var isValid = $("#investProductInputOrSaveForm").form('validate');
		if(!isValid){
			return false;
		} 
		     	    	
		var lowLendEdu = $("#lowLendEdu").val();
		if(lowLendEdu == "" ){
			$("#lowLendEdu").val(null);
		}
	
		var higLendEdu = $("#higLendEdu").val();
		if(higLendEdu == "" ){
			$("#higLendEdu").val(null);
		}
		//保存主表数据
		$.ajax({
			type: "POST",
			url:"callingCard/addCard.do",
			data:$('#investProductInputOrSaveForm').serialize(),
			async: false,//默认true设置下，所有请求均为异步请求
			cache: true,
		    success: function(iJson) {    	    	
	 	    	if(iJson.status){
	 	    		alert("成功！");
		    		$("#prodId").val(iJson.data);//成功保存理财产品ID    	    		
		    		$("#lookLoanOrderdgAdd").datagrid("reload");//刷新表格    	    		
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
	//展示附表数据
	$(function(){
		var uri = "";
		if($row==null){
			if($("#caID").val()==null){
				uri = "callingCard/indexCardAppAttach.do";
			}else{
				uri = "callingCard/indexCardAppAttach.do?caID="+$("#caID").val()
			}
		}else{
			uri = "callingCard/indexCardAppAttach.do?caID="+$row.caID;
		}
		$grid = $("#lookLoanOrderdgAdd").datagrid({
			url : uri,
			width : 'auto',
			height : 400,
			pagination:true,
			rownumbers:true,
			border:true,
			singleSelect:true,
			nowrap:true,
			multiSort:false,
			columns : [ [ 
			              {field : 'applicantNo',title : '申请人',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'position',title : '申请人职位',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'deptNo',title : '所属部门',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'personalTel',title : '个人电话',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'officeTel',title : '办公电话',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'email',title : '邮箱',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'branchAddr',title : '分公司地址',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'comUrl',title : '公司网址',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'appQty',title : '申请数量',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'unit',title : '单位',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'remark',title : '备注信息',width : parseInt($(this).width()*0.1),align : 'center'},
			              {field : 'manager',title : '操作',width :parseInt($(this).width()*0.1),align : 'center',
			            	  formatter: function(value,row,index){
		            			  var result="<a href='javascript:void(0);' onclick='toSaveOrUpdateInvestProductOpenDlg2("+index+");'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";				            			  
		            			  	  result+="<a href='javascript:void(0);' onclick='deleteInvestProduct("+row.caId+","+1+");'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;"
	      						  return result;					            		  					           		  
			      				}}
			              ] ]
			});
	});
	
	// 根据索引获取每一行的基本信息
	function getRowData2 (index) {
        if (!$.isNumeric(index) || index < 0) { return undefined; }
        var rows = $grid.datagrid("getRows");
        return rows[index];
    }

	/** 点击按钮，新增或者修改,加载编辑的值**/			
	function toSaveOrUpdateInvestProductOpenDlg2(index){		
		var selectedRow = getRowData2(index);
    	var saveOrUpdateForm = $("#investProductInputOrSaveForm");
    	saveOrUpdateForm.form("load",selectedRow);
    	$("#applicantNo").combogrid('setValue',selectedRow.userId);
	}

	/** 清空新添加的的的数据**/
	function clearForm(){
		$("#investProductInputOrSaveForm").form("clear");		
	}	
	
	//添加上传
	function addAccessory(){
		$(".table tr:eq(5)").after('<tr><th>上传附件:</th><td colspan="4"><input id="prodName" name="prodName" class="easyui-textbox easyui-validatebox" data-options="validType:"length[0,100]", required:true"/><input type="file"/><a class="add" style="font-size: 20px;" onclick="addAccessory();">+</a><a class="del" style="font-size: 20px;margin-left:10px;" onclick="delAccessory(this);">x</a></td></tr>');
	}
	
	//删除上传
	function delAccessory(id){
		$(id).parent().parent().remove();
	}
	
	//加载姓名下拉框
	$(function(){
		$("#applicantNo").combogrid({    
		    panelWidth:250,  
		    mode: 'remote',   
		    idField:'userId',    
		    textField:'name',    
		    url:'callingCard/getUserInfo.do?q='+$("#applicantNo").val(),
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
		     	    	$("#deptNo").val(iJson.fullName);
		     	    	$("#position").val(iJson.name);
		     	    	$("#personalTel").val(iJson.mobile);
		     	    	$("#officeTel").val(iJson.tel);
		     	    	$("#email").val(iJson.email);
		    	    }
		    	});		
		    }
		}); 
	});
	
	
	/** 删除名片申请  **/
	function deleteInvestProduct(psaId,index){
    	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
    		if (d) {
    			$.ajax( {
    				type : "POST",
    				url : urlChange(index),
    				data : "id="+psaId,
    				dataType:'JSON',
    				success : function(flag) {
    					if(flag.status==true){
    						alert("删除成功！");
// 		    					//刷新列表		    						
	    					$("#lookLoanOrderdgAdd").datagrid("load",{});
	    					parent.$.messager.show({
	    						title : iJson.title,
	    						msg : iJson.message,
	    						timeout : 4000 * 2
	    					});
    					}else{
    						alert("删除失败！");
    					}
    				},error:function(){
    					alert("失败了");
    				}
	    			});
    		}
    	});		
	}	
	
	//1为删除名片附件，2为删除名片
	function urlChange(index){
		if(index==1){
			return "callingCard/removeCardAttach.do";
		}else if(index==2){
			return "callingCard/removeCard.do";
		}
	}
	
	//保存到附件表
	$(function(){
		$("#saveCardApp").click(function(){
			$.ajax({
				type : "POST",
				url : "callingCard/addCardTable.do",
				data:$('#investProductInputOrSaveForm').serialize(),
				dataType:'JSON',
				success : function(flag) {
					if(flag.status==true){
						alert("添加成功！");
		    			//刷新列表		    						
						$("#lookLoanOrderdgAdd").datagrid("load",{});
						parent.$.messager.show({
							title : iJson.title,
							msg : iJson.message,
							timeout : 4000 * 2
						});
					}else{
						alert("添加失败！");
					}
				},error:function(){
					alert("失败了");
				}
			});
		});
	});
	
	 //上传附件
    $("#upploadAttachment").click(function(){
		fileUploadsDlg($("#appNo").val(),$("#caId").val());
	});
	 
  //查看附件
	$("#checkAttachment").click(function(){
		checkAttachementDetail($("#appNo").val(),$("#registrantNO").val(),'',$("#caId").val());
	});
</script>
	<div style="margin-left: 5px;margin-top: 5px;" data-options="iconCls:'icon-cstbase'">
	   <form id="investProductInputOrSaveForm"  method="post">
 	   		<input id="caId" name="caId" type="hidden"/><!-- 理财产品的ID -->
 	   		<input id="appNo" name=appNo type="hidden"/><!-- 理财产品的ID -->
 	   		<input id="unit" name=unit type="hidden"/><!-- 理财产品的ID -->
 	   		<input id="registrantNO" name=registrantNO type="hidden"/><!-- 理财产品的ID -->
			<table class="table" width="100%">
				<tr>
					<th>姓名:</th>
					<td id="name">
						<input name="applicantNo" id="applicantNo" class="easyui-textbox easyui-combogrid" data-options="validType:'length[0,100]', required:true"/>
					</td>
					<th>部门:</th>
					<td>
						<input name="deptNo" id="deptNo" class="easyui-textbox" readonly="true"/>
					</td>
				</tr>
				<tr>
					<th>职务:</th>
					<td>
						<input name="position" id="position" class="easyui-textbox" readonly="true"/>
					</td>
					<th>个人电话:</th>
					<td>
						<input name="personalTel" id="personalTel" class="easyui-textbox" readonly="true"/>
					</td>
				</tr>
				<tr>
					<th>办公电话:</th>
					<td>
						<input name="officeTel" id="officeTel" class="easyui-textbox" readonly="true"/>
					</td>
					<th>邮箱:</th>
					<td>
						<input name="email" id="email" class="easyui-textbox" readonly="true"/>
					</td>
				</tr>
				<tr>
					<th>地址:</th>
					<td>
						<input id="branchAddr" name="branchAddr" class="easyui-textbox easyui-validatebox" data-options="validType:'length[0,100]', required:true"/>
					</td><th>数量:</th>
					<td>
						<input id="appQty" name="appQty" type="text" class="easyui-textbox easyui-validatebox" data-options="validType:'integer',required:true" onblur="decideMsfByLendingCycle()" onchange="decideMsfByLendingCycle()"/>
					</td>
				</tr>
				<tr>
					<th>公司网址:</th>
					<td colspan="2">
						<input id="comUrl" name="comUrl" class="easyui-textbox easyui-validatebox" data-options="validType:'length[0,100]', required:true"/>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<th>上传附件:</th> -->
<!-- 					<td colspan="3"> -->
<!-- 						<input id="prodName"  class="easyui-textbox" data-options="validType:'length[0,100]', required:true"/><input type="file"/><a class="add" style="font-size: 20px;margin-right: 10px;" onclick="addAccessory();">+</a> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>
					<th>附件类型:</th>
					<td>
						<input id="attType" class="easyui-textbox easyui-combobox" />
					</td>
					<td colspan="2">
						<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
						<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>		
					</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td colspan="3">
						<textarea id="remark" name="remark" class="easyui-textbox" data-options="validType:'length[0,400]', required:true" style="width:560px;"></textarea>
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
		<!--添加名片列表 -->
		<table id="lookLoanOrderdgAdd" title="添加名片" style="margin-top: 60px;"></table>
		<div style="float:right;">
			 <a href="javascript:void(0)" id="saveCardApp" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		</div>
		<!--查看附件-->
		<div id="saveOrUpdateInvestProductDialog"></div>		