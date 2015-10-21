<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>
<style>
.easyui-textbox {
	height: 18px;
	width: 170px;
	line-height: 16px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
}

textarea:focus, input[type="text"]:focus {
	border-color: rgba(82, 168, 236, 0.8);
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
	outline: 0 none;
}

.table {
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
	max-width: 100%;
}
input, textarea {
	font-weight: normal;
}

td {
	text-align: left;
	padding: 6px;
}
th{
    text-align: right;
	padding: 6px;
}
</style>
<script type="text/javascript">
	
	$(function(){
		//判断添加还是修改
		addOrEditval();
		
		//添加时申请人
		findFullAppName();
		
		//初始化组织机构,借用人
		$("#deptNo").combotree({
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
		 			 RenderName("#borrower",$(this).combotree('getValue'));
		 			 
		 	}
		});
		
		//初始化组织机构,归还人
		$("#reverterDeptNo").combotree({
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
		 			$("#reverterDeptNo").val($(this).combotree('getValue'));
		 			 RenderName("#reverter",$(this).combotree('getValue'));
		 	}
		});
		
		
	});
	
	// 用户名的下拉
	function RenderName(name,organizeId){
		
		$(name).combobox({
			width:171,
			url:"PpeBorrowReg/findOrgUserList.do?organizeId="+organizeId,
			valueField:'code',
		 	textFiled:'text',
		 	onLoadSuccess:function(data){
		 		//加一个全部
		 		
		 	},
		}); 
	}
	
	//判断是添加还是修改
	function addOrEditval(){
		var pbrId=$("#pbrId").val();
		if($.trim(pbrId)!=''){
			$("#rev").show();
		}else{
			$("#rev").hide();
		}
	}
	
	function findFullAppName(){
		 //添加申请时，首先添加主表信息
		$.ajax({
			cache:true,
			type:'POST',
			url:'PpeBorrowReg/findFullAppName.do',
			async:false,
			success:function(res){
				$("#registrantName").val(res.name);
				$("#registrantNo").val(res.userId);
			}
		}); 
	}

	//申请人信息保存
	function toSaveBaseInfo(){
		
		//校验固定资产编号
		if($.trim($("#ppeNo").val())==''){
			$.messager.alert("提示","请填写固定资产编号!","info");
			return false;
		}
		
		//校验借用事由
		if($.trim($("#borReson").val())==''){
			$.messager.alert("提示","请填写借用事由!","info");
			return false;
		}
		
	    //借用人验证
	    if($("#borrower").combobox('getValue')==''){
	    	$.messager.alert("提示","请选择借用人!","info");
	    	return false;
	    }
	    
	    
		$.ajax({
			cache:true,
			type:'POST',
			url:"PpeBorrowReg/savePpeBorrowReg.do",
			data:$('#ppeBorrowRegForm').serialize(),
			async:false,
			dataType:'JSON',
			success:function(res){
				 if(res.status){
					//关闭窗口
					$("#editView").dialog('close');
					parent.$.messager.show({
						title : '提示',
						msg : '恭喜你，保存成功!',
						timeout : 4000 * 2
					});
				}else{
					$.messager.alert("提示", '出错了，保存失败!',"error")
				} 
			}
		});
	} 
    
  
	function resetPpeBorrow(){
		  $("#resPbrId").val($("#pbrId").val());
		  $('#ppeBorrowRegForm').form('reset');
		  $("#pbrId").val($("#resPbrId").val());
		  findFullAppName();
	}
  
</script>
<div id="tt">
	<div title="固定资产借用详情">
	  <div class="well well-small" style="margin:5px;">
	  			 <input id="resPbrId" name="pbrId" type="hidden"/><!-- 固定资产借用登记id -->
	     	<form id="ppeBorrowRegForm">
	      	  <input id="pbrId" name="pbrId" type="hidden"/><!-- 固定资产借用登记id -->
	          <input id="registrantNo" name="registrantNo" type="hidden"/><!-- 登记人id -->
	          <input id="regDatetime1" name="regDatetime" type="hidden" /><!-- 借用日期 -->  
	      	  <input id="revDatetime1" name="revDatetime" type="hidden"/><!-- 归还日期 -->  
	          
	         <table class="table">
	         	<tr>
           			<th>登记人姓名:</th>
					<td >
						<input id="registrantName" name="registrantName" style="width: 165px" readonly="readonly" type="text" />
					</td>
	         	</tr>
	         	<tr>
	         		<th>固定资产编号：</th>
	         		<td>
	         			<input id="ppeNo" name="ppeNo" style="width: 165px" "/>
	         		</td>
	         		<th>固定资产名称:</th>
	         		<td>
	         			<input id="ppeName" name="ppeName" style="width: 165px"/>
	         		</td>
	         		<th>固定资产型号:</th>
	         		<td>
	         			<input id="model" name="model"/>
	         		</td>
	         	</tr>
	         	
				<tr >
						<th>借用部门:</th>
						<td><input name="deptNo" id="deptNo" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>借用人:</th>
						<td><input name="borrower" id="borrower" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox easyui-combobox" style="width: 170px"/>&nbsp;</td>
				</tr>
				
				<tr id="rev">
						<th>归还部门:</th>
						<td><input name="reverterDeptNo" id="reverterDeptNo" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>归还人:</th>
						<td><input name="reverter" id="reverter" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox easyui-combobox" style="width: 170px"/>&nbsp;</td>
				</tr>
				
				<tr>
					<th>借用事由:</th>
					<td colspan="6">
						<textarea id="borReson" name="borReson" class="easyui-textbox easyui-validatebox" style="width: 744px;height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
					</td>
				</tr>
				
				 <tr>
					<th>备注信息:</th>
					<td colspan="6">
					  <textarea id="remark" name="remark" class="easyui-textbox easyui-validatebox" style="width: 744px; height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
					</td>
				</tr>
				<tr>
				   <td colspan="6" style="text-align: right;">
				       <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="toSaveBaseInfo();">保存</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetPpeBorrow();">重置</a>
				   </td>
				</tr>
	         </table>
	       </form>
	  </div>
	   <div style="margin: 5px;">
	        <table id="appUserView"></table>
	   </div>
	   <!--  <div  style="float:right;">
		    <a href="javascript:void(0)" id="saveOrAdd" class="easyui-linkbutton" iconCls="icon-add" onclick="toSaveOrAddInfo();">保存并添加</a>
  		</div> -->
	</div>
	
</div>
