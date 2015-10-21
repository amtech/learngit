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
		 			 RenderName($(this).combotree('getValue'));
		 			 
		 	}
		});
		
		
		
	});
	
	// 用户名的下拉
	function RenderName(organizeId){
		
		$("#user").combobox({
			width:171,
			url:"UseReg/findOrgUserList.do?organizeId="+organizeId,
			valueField:'code',
		 	textFiled:'text',
		 	onLoadSuccess:function(data){
		 		//加一个全部
		 		
		 	},
		}); 
	}
	
	function findFullAppName(){
		 //添加登记人姓名
		$.ajax({
			cache:true,
			type:'POST',
			url:'UseStampApp/findFullAppName.do',
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
		//校验领用数量
		var reg=/^(\+|-)?\d+$/;
  		var us=$("#useQty").val();
  		if(!(reg.test($("#useQty").val()))){
  			$.messager.alert("提示","请输入大于0的正整数!","info");
  			return false;
  		}else if(parseInt($("#useQty").val())<=0 || parseInt($("#useQty").val())==0 ){
  			$.messager.alert("提示","请输入大于0的正整数!","info");
  			return false;
  		}
  		 //领用人验证
	    if($("#user").combobox('getValue')==''){
	    	$.messager.alert("提示","请选择借用人!","info");
	    	return false;
	    }
		//校验用章事由
		if($.trim($("#useReson").val())==''){
			$.messager.alert("提示","请填写借用事由!","info");
			return false;
		}
	   
		$.ajax({
			cache:true,
			type:'POST',
			url:"UseReg/saveUseReg.do",
			data:$('#UseRegForm').serialize(),
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
    
	  function regNumber(){
	  		var reg=/^(\+|-)?\d+$/;
	  		if(!(reg.test($("#useQty").val()))){
	  			$.messager.alert("提示","请输入大于0的正整数!","info");
	  			return false;
	  		}else if(parseInt($("#useQty").val())<=0 || parseInt($("#useQty").val())==0 ){
	  			$.messager.alert("提示","请输入大于0的正整数!","info");
	  			return false;
	  		}
	  		
			  		
	  	}
  		
	  //重置
	  function resetUseReg(){
		  $("#resUrId").val($("#urId").val());
		  $('#UseRegForm').form('reset');
		  $("#urId").val($("#resUrId").val());
		  findFullAppName();
	  }
  	
  
</script>
<div id="tt">
	<div title="固定资产领用详情">
	  <div class="well well-small" style="margin:5px;">
	  			<input id="resUrId" name="resUrId" type="hidden"/><!-- 固定资产借用登记id -->
	     	<form id="UseRegForm">
	      	  <input id="urId" name="urId" type="hidden"/><!-- 固定资产借用登记id  -->
	          <input id="registrantNo" name="registrantNo" type="hidden"/><!-- 登记人id -->
	          <input id="regDatetime" name="regDatetime" type="hidden" /><!-- 借用日期 -->  
	          
	         <table class="table">
	         	<tr>
           			<th>登记人姓名:</th>
					<td >
						<input id="registrantName" name="registrantName"  readonly="readonly" type="text" />
					</td>
	         	</tr>
	         	<tr>
	         		<th>固定资产编号：</th>
	         		<td>
	         			<input id="ppeNo" name="ppeNo" "/>
	         		</td>
	         		<th>固定资产名称:</th>
	         		<td>
	         			<input id="ppeName" name="ppeName"  style="width: 165px"/>
	         		</td>
	         	</tr>
	         	<tr>
	         		<th>领用数量:</th>
	         		<td>
	         			<input id="useQty" name="useQty" style="width: 50px;text-align: center;" value="0"  align="middle" onchange="regNumber();"/>（个）
	         		</td>
	         	</tr>
	         	
				<tr >
						<th>领用部门:</th>
						<td><input name="deptNo" id="deptNo" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>领用人:</th>
						<td><input name="user" id="user" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox easyui-combobox" style="width: 170px"/>&nbsp;</td>
				</tr>
				
				<tr>
					<th>领用用途:</th>
					<td colspan="3">
						<textarea id="useReson" name="useReson" class="easyui-textbox easyui-validatebox" style="width: 744px;height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
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
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetUseReg();">重置</a>
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
