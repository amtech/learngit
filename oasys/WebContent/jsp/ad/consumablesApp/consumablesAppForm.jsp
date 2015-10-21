<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
function saveConsumablesApp(){
	$.ajax({
		   url: "consumablesApp/addOrUpdateConsumablesApp.do",
		   type: "POST",
		   data:$('#consumablesAppForm').serialize(),
          dataType:'JSON',
		   success: function(msg){
			   if(msg.status){
				   $("#consumablesAppForm input[name='caId'][type='hidden']").val(msg.data[0]);
				   $("#consumablesAppForm input[name='appNo'][type='hidden']").val(msg.data[1]);
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
    <form id="consumablesAppForm"  method="post" style="width: 875px;margin-left:5px;">
    <fieldset>
    <legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/>基本信息</legend>
	<input type="hidden" id="appNo" name="appNo" value="${consumablesApp.appNo }" /><!-- 外键appNo -->
	<input type="hidden" id="caId" name="caId" value="${consumablesApp.caId }" /> <!-- id标识 判断是否为新增 -->
	<input type="hidden" id="appDept" name="appDept" value="${consumablesApp.appDept }" /><!-- 申请部门 -->
	<input type="hidden" id="applicantNo" name="applicantNo" value="${consumablesApp.applicantNo }" /> <!-- 申请人 -->
	<input type="hidden" id="appDate" name="appDate" value="${consumablesApp.appDate }" /><!-- 申请时间 -->
	<input type="hidden" id="appStatus" name="appStatus" value="${consumablesApp.appStatus }" /><!-- 申请状态 -->
	<input type="hidden" id="procStatus" name="procStatus" value="${consumablesApp.procStatus }" /><!-- 流程状态 -->
	
		<table class="table"  width="95%">
			<tr>
				<th align="right">申请人:</th>
				<td>
					<input name="account"  id="userName" class="easyui-textbox"  value="${consumablesApp.account }" disabled="disabled" />
				</td>
				<th align="right">申请时间:</th>						
				<td colspan="3">
					<input name="appDate" id="appDate"  class="easyui-textbox"  value="${consumablesApp.appDate }" disabled="disabled" />
				</td>
			</tr>
			<tr>
				<th align="right">申请部门:</th>
				<td>
					<input name="appDeptName"  id="orgName" class="easyui-textbox" value="${consumablesApp.appDeptName }" disabled="disabled" />
				</td>
				<th align="right">申请状态:</th>
				<td>
					<input name="sqzt" id="sqzt"   class="easyui-textbox" value="${consumablesApp.procStatus }" disabled="disabled"  />
				</td>
			</tr>
			<tr>
				<th align="right" colspan="1">备注:</th>
			   <td colspan="5" align="left">
			      	<textarea name="remark" id="remark" class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" value="${consumablesApp.remark }" />
			   </td>
			</tr>	
			<tr>
				<td colspan="3"></td>
			   <td colspan="1" align="right" >
			      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveConsumablesApp();">保存</a>
			   </td>
			</tr>	
		</table>
    </fieldset>
    </form>
  <div style="width: 875px;margin-left:5px;margin-top: 2px;">
       <table id="consumablesAppAttachGrid"></table>
       <div id="formWindow" class="easyui-dialog" title="新增低值易耗品" closed="true" ></div>
    </div>
<script type="text/javascript">
//初始化构建列表
$(function(){
		createConsumablesAppAttachGrid();
})
/**
 * 编辑固定资产项
 */
function editConsumablesAppAttach(){
	var row = $("#consumablesAppAttachGrid").datagrid("getSelected");
	var rows = $("#consumablesAppAttachGrid").datagrid("getSelections");
	if(row == null){
		$.messager.alert("提示","请选择一条记录进行修改!","warning");
		return false;
	}
	if(rows!=null && rows.length>1){
		$.messager.alert("提示","您只能选择一条记录进行修改!","warning");
		return false;
	}
	$('#formWindow').dialog({
        title: '编辑固定资产',
        inline:false,
        closed:true,
        width: 850,    
        height: 550,                
        cache: false,
        resizable:true,
        shadow:false,
        modal: true ,
        href:'consumablesAppAttach/toAddConsumablesAppAttach.do?caaId='+row.caaId
	});
	$('#formWindow').dialog("open");
}
//构建列表方法
function createConsumablesAppAttachGrid(){
	$("#consumablesAppAttachGrid").datagrid({
		url:'consumablesAppAttach/findAllConsumablesAppAttach.do?appNo='+$('#appNo').val(),
		width: 'auto',
		height : $(this).height()-480,
		pagination:false,
		rownumbers:true,
		border:true,
		singleSelect:false,
		nowrap:true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
		remoteSort:false,//定义是否从服务器对数据进行排序。
		striped:true,//是否显示斑马线
		columns:[[
			{field : 'caaId',        title : 'CAA_ID',    width : 60, align:'center' },
			{field : 'goodsName',        title : '用品名称',    width : 60, align:'center' },
			{field : 'model',      title : '规格型号', width : 875*0.1, align:'center'},
			{field : 'unit',    title : '资产规格',    width : 875*0.1, align:'center' },
			{field : 'stock',    title : '库存',    width : 60, align:'center' },
			{field : 'price',    title : '单价',    width : 60, align:'center' },
			{field : 'qty',    title : '数量',    width : 875*0.1, align:'center' },
			{field : 'totalAmt',    title : '合计金额',    width : 875*0.1, align:'center' },
	   ]],
	   toolbar:[{
		   iconCls: 'icon-add',
		   text:'新增',
		   handler:toFormWindow
	   },"-",{
		   iconCls: 'icon-edit',
		   text:'编辑',
		   handler:editConsumablesAppAttach
	   },"-",{
		   iconCls: 'icon-cut',
		   text:'删除',
		   handler:delConsumablesAppAttach
	   }]
	});
	$('#consumablesAppAttachGrid').datagrid('hideColumn','caaId');
}
//跳转新增
function toFormWindow(){
	if($("#consumablesAppForm input[name='appNo'][type='hidden']").val() == ""){
		alert('请先保存基本信息;');
	}else{
		$('#formWindow').dialog({
	        title: '新增固定资产',
	        inline:false,
	        closed:true,
	        width: 600,    
	        height: 400,                
	        cache: false,
	        resizable:true,
	        shadow:false,
	        modal: true ,
	        href:'jsp/ad/consumablesApp/consumablesAppAttachAddForm.jsp',
	        onOpen:function(){
	        	var appNo =$("#consumablesAppForm input[name='appNo'][type='hidden']").val();
				$('#appNo').val(appNo); 
			}
		});
		$('#formWindow').dialog("open");
	}
}



function delConsumablesAppAttach(){
	var rows = $("#consumablesAppAttachGrid").datagrid("getSelections");
	var ids = new Array();
	if(rows==null || rows.length<=0){
		$.messager.alert("提示","请至少选择一条记录!","warning");
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].caaId);
	}
	ids = ids.join(",");
	var appNo = $("#consumablesAppForm input[name='appNo']").val();
	$.messager.confirm('删除', '是否删除该'+rows.length+'条记录', function(d) {
	if (d) {
		$.ajax({
		   type: "POST",
		   url: "consumablesAppAttach/delConsumablesAppAttach.do",
		   data:{"ids":ids},
	       async: false,
		   success: function(msg){
			   if(msg.status){
				   createConsumablesAppAttachGrid();
			   }
			   parent.$.messager.show({
	   			title : msg.title,
	   			msg : msg.message,
	   			timeout : 4000 * 2
	   	       });
		   }
		});
	}});
}
</script>