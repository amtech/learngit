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
		//判断用章类型
		addStampOther(); 
	});
	
	function findFullAppName(){
		 //添加申请时，首先添加主表信息
		$.ajax({
			cache:true,
			type:'POST',
			url:'UseStampApp/findFullAppName.do',
			async:false,
			success:function(res){
				$("#fullName").val(res.fullName);
				$("#applicantName").val(res.name);
			}
		}); 
	}

	//申请人信息保存
	function toSaveBaseInfo(){
		//校验其他印章
		if($("#radio5").is(":checked") && $.trim($("#stampTypeOther").val())==""){
			$.messager.alert("提示","请输入其他类型印章","info");
  			return false;
   	 	}
		
		//校验用章数量
		var reg=/^(\+|-)?\d+$/;
  		var us=$("#usQuantity").val();
  		if(!(reg.test($("#usQuantity").val()))){
  			$.messager.alert("提示","请输入大于0的正整数!","info");
  			return false;
  		}else if(parseInt($("#usQuantity").val())<=0 || parseInt($("#usQuantity").val())==0 ){
  			$.messager.alert("提示","请输入大于0的正整数!","info");
  			return false;
  		}
  		//校验时间
		var start=$('#usBeginDate').datebox('getValue');
		var end=$('#usEndDate').datebox('getValue');
		var newDate=new Date();
		var nd=newDate.getFullYear()+"-"+(newDate.getMonth()+1)+"-"+ newDate.getDate();
	    if(Date.parse(start)<Date.parse(nd)){
			$.messager.alert("提示","请重新填选择始时间!","info");
			return false;
		} 
		
		//校验用章事由
		if($("#usReason").val()==0){
			$.messager.alert("提示","请填写用章事由!","info");
			return false;
		}
		
		
	    if(Date.parse(start)!=Date.parse(end)){
	    	if(Date.parse(start)>Date.parse(end)){
				$.messager.alert("提示","请重新选择结束时间!","info");
				return false;
			}
	    } 
		
		$.ajax({
			cache:true,
			type:'POST',
			url:"UseStampApp/saveUseStamp.do",
			data:$('#useStampForm').serialize(),
			async:false,
			dataType:'JSON',
			success:function(res){
				 if(res.status){
					$("#useStampForm").form('clear');//清空表单
					//添加时申请人
					findFullAppName();
					//判断用章类型
					addStampOther(); 
					
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
    
     //添加其他类型印章
     function addStampOther(){
    	 $("#stampTypeOther").hide();
    	 if($("#radio5").is(":checked")){
    		 $("#stampTypeOther").show();
    	 }
     }
     
    //比较起始时间
    $('#usBeginDate').datebox({
	    onSelect: function(date){
	        var newDate=new Date();
	        var start=$('#usBeginDate').datebox('getValue');
	        //当前系统时间
	        var nd=newDate.getFullYear()+"-"+(newDate.getMonth()+1)+"-"+ newDate.getDate();
	       	
	       	if(Date.parse(start)<Date.parse(nd)){
		        	$.messager.alert("提示","请重新填选择始时间!","info");
	       	}
	    }
	});

  //比较结束时间
    $('#usEndDate').datebox({
	    onSelect: function(date){
	    	var start=$('#usBeginDate').datebox('getValue');
			var end=$('#usEndDate').datebox('getValue');
	        
	        if(Date.parse(start)!=Date.parse(end)){
		    	if(Date.parse(start)>Date.parse(end)){
					$.messager.alert("提示","请重新选择结束时间!","info");
				}
		    } 
	    }
	}); 
  
  	function regNumber(){
  		var reg=/^(\+|-)?\d+$/;
  		var us=$("#usQuantity").val();
  		if(!(reg.test($("#usQuantity").val()))){
  			$.messager.alert("提示","请输入大于0的正整数!","info");
  			return false;
  		}else if(parseInt($("#usQuantity").val())<=0 || parseInt($("#usQuantity").val())==0 ){
  			$.messager.alert("提示","请输入大于0的正整数!","info");
  			return false;
  		}
  		
		  		
  	}
  	
  	//重置
  	function resetUseStamp(){
  		$('#useStampForm').form('reset');
  		findFullAppName();
  	}
     
</script>
<div id="tt">
	<div title="用章申请详情">
	  <div class="well well-small" style="margin:5px;">
	     	<form id="useStampForm">
	          <input id="appNo" name="appNo" type="hidden"/><!-- 申请编号 -->
	      	  <input id="usaId" name="usaId" type="hidden"/><!-- 申请id -->
	          <input id="appStatus" name="appStatus" type="hidden"/><!-- 申请状态 -->
	          <input id="procStatus" name="procStatus" type="hidden"/><!-- 流程状态 -->
	          <input id="applicantNo" name="applicantNo" type="hidden"/><!-- 申请人id -->
	          <input id="deptNo" name="deptNo" type="hidden"/><!-- 部门id -->
	          <input id="appDate" name="appDate" type="hidden" /><!--申请时间 -->
	          
	         <table class="table">
				<tr>
						<th>部门:</th>
						<td><input name="fullName" id="fullName" type="text" readonly="readonly" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>申请人:</th>
						<td><input name="applicantName" id="applicantName" type="text" readonly="readonly" style="width: 170px"/>&nbsp;</td>
				</tr>
				
				<tr>
					<th>印章类型:</th>
					<td>
						<input type="radio" id="radio1" name="stampType" value="A" onclick="addStampOther();" checked="checked"/>公章
						<input type="radio" id="radio2" name="stampType" value="B" onclick="addStampOther();"/>合同章
						<input type="radio" id="radio3" name="stampType" value="C" onclick="addStampOther();"/>财务章
						<input type="radio" id="radio4" name="stampType" value="D" onclick="addStampOther();"/>法人章
						<input type="radio" id="radio5" name="stampType" value="E" onclick="addStampOther();">其他</input>
						<input type="text" id="stampTypeOther" name="stampTypeOther" style="width: 170px"/>
					</td>
				</tr>
				
				<tr>
					<th>用章开始时间:</th>
					<td><input id="usBeginDate" name="usBeginDate" placeholder="请选择起始日期" class="easyui-textbox easyui-datebox" data-options="editable:false"/></td>
					<th>用章结束时间:</th>
					<td><input id="usEndDate" name="usEndDate" placeholder="请选择结束日期" class="easyui-textbox easyui-datebox" data-options="editable:false" /></td>
				</tr>
				
				<tr>
					<th>用章数量:</th>
					<td><input id="usQuantity" name="usQuantity"  type="text" style="width: 50px;text-align: center;" value="0" onchange="regNumber();"/>个</td>
				</tr>
				
				<tr>
					<th>用章事由:</th>
					<td colspan="3">
						<textarea id="usReason" name="usReason" class="easyui-textbox easyui-validatebox" style="width: 739px;height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
					</td>
				</tr>
				
				 <tr>
					<th>备注:</th>
					<td colspan="3">
					  <textarea id="remark" name="remark" class="easyui-textbox easyui-validatebox" style="width: 739px; height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
					</td>
				</tr>
				<tr>
				   <td colspan="6" style="text-align: right;">
				   	  <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="toSaveBaseInfo();">保存</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetUseStamp();">重置</a>
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
