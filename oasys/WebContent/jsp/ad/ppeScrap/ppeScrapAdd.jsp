<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
function savePPEScrapApp(){
	$.ajax({
		   url: "ppeScrapAppController/addOrUpdatePPEScrapApp.do",
		   type: "POST",
		   data:$('#ppeScrapAppForm').serialize(),
          dataType:'JSON',
		   success: function(msg){
			   if(msg.status){
				   $("#ppeScrapAppForm input[name='psaId'][type='hidden']").val(msg.data[0]);
				   $("#ppeScrapAppForm input[name='appNo'][type='hidden']").val(msg.data[1]);
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
    <form id="ppeScrapAppForm"  method="post" style="width: 875px;margin-left:5px;">
    <fieldset>
    <legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/>基本信息</legend>
	<input type="hidden" id="appNo" name="appNo" value="${ppeScrap.appNo }" /><!-- 外键appNo -->
	<input type="hidden" id="psaId" name="psaId" value="${ppeScrap.psaId }" /> <!-- id标识 判断是否为新增 -->
	<input type="hidden" id="appDept" name="appDept" value="${ppeScrap.appDept }" /><!-- 申请部门 -->
	<input type="hidden" id="applicantNo" name="applicantNo" value="${ppeScrap.applicantNo }" /> <!-- 申请人 -->
	<input type="hidden" id="appDate" name="appDate" value="${ppeScrap.appDate }" /><!-- 申请时间 -->
	<input type="hidden" id="appStatus" name="appStatus" value="${ppeScrap.appStatus }" /><!-- 申请状态 -->
	<input type="hidden" id="procStatus" name="procStatus" value="${ppeScrap.procStatus }" /><!-- 流程状态 -->
	
		<table class="table"  width="95%">
			<tr>
				<th align="right">申请人:</th>
				<td>
					<input name="userName"  id="userName" class="easyui-textbox"  value="${ppeScrap.userName }" disabled="disabled" />
				</td>
				<th align="right">申请时间:</th>						
				<td colspan="3">
					<input name="appDate" id="appDate"  class="easyui-textbox"  value="${ppeScrap.appDate }" disabled="disabled" />
				</td>
			</tr>
			<tr>
				<th align="right">申请部门:</th>
				<td>
					<input name="orgName"  id="orgName" class="easyui-textbox" value="${ppeScrap.orgName }" disabled="disabled" />
				</td>
				<th align="right">申请状态:</th>
				<td>
					<input name="sqzt" id="sqzt"   class="easyui-textbox" value="${ppeScrap.procStatus }" disabled="disabled"  />
				</td>
			</tr>
			<tr>
				<th align="right" colspan="1">备注:</th>
			   <td colspan="5" align="left">
			      	<input name="remark" id="remark" class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" value="${ppeScrap.remark }" />
			   </td>
			</tr>	
			<tr>
				<td colspan="3"></td>
			   <td colspan="1" align="right" >
			      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="savePPEScrapApp();">保存</a>
			   </td>
			</tr>	
		</table>
    </fieldset>
    </form>
  <div style="width: 875px;margin-left:5px;margin-top: 2px;">
       <table id="ppeScrapAttachGrid"></table>
       <div id="formWindow" class="easyui-dialog" title="新增固定资产" closed="true" ></div>
    </div>
    
   
<script type="text/javascript">
//初始化构建列表
$(function(){
		createPpeScrapAttachGrid();
})
/**
 * 编辑固定资产项
 */
function editPPEScrapAppAttach(){
	var row = $("#ppeScrapAttachGrid").datagrid("getSelected");
	var rows = $("#ppeScrapAttachGrid").datagrid("getSelections");
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
        href:'ppeScrapAppAttachController/toAddPPEScrapAppAttach.do?psaId='+row.psaId
	});
	$('#formWindow').dialog("open");
}
//构建列表方法
function createPpeScrapAttachGrid(){
	$("#ppeScrapAttachGrid").datagrid({
		url:'ppeScrapAppAttachController/findAllppeScrapAttach.do?appNo='+$('#appNo').val(),
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
			{field : 'psaId',        title : 'PSA_ID',    width : 60, align:'center' },
			{field : 'ppeNo',        title : '资产编号',    width : 60, align:'center' },
			{field : 'ppeName',      title : '资产名称', width : 875*0.1, align:'center'},
			{field : 'ppeModel',    title : '资产规格',    width : 875*0.1, align:'center' },
			{field : 'buyDate',    title : '购买时间',    width : 60, align:'center' },
			{field : 'usedYear',    title : '使用年限',    width : 60, align:'center' },
			{field : 'ppeGross',    title : '资产原值',    width : 875*0.1, align:'center' },
			{field : 'ppeNet',    title : '资产净值',    width : 875*0.1, align:'center' },
			{field : 'ppeSalvageVal',    title : '资产残值',    width : 875*0.1, align:'center' },
			{field : 'scrapReson',    title : '资产报废原因',    width : 875*0.1, align:'center' }
	   ]],
	   toolbar:[{
		   iconCls: 'icon-add',
		   text:'新增',
		   handler:toFormWindow
	   },"-",{
		   iconCls: 'icon-edit',
		   text:'编辑',
		   handler:editPPEScrapAppAttach
	   },"-",{
		   iconCls: 'icon-cut',
		   text:'删除',
		   handler:delPPEScrapAppAttach
	   }]
	});
	$('#ppeScrapAttachGrid').datagrid('hideColumn','psaId');
}
//跳转新增
function toFormWindow(){
	if($("#ppeScrapAppForm input[name='appNo'][type='hidden']").val() == ""){
		alert('请先保存基本信息;');
	}else{
		$('#formWindow').dialog({
	        title: '新增固定资产',
	        inline:false,
	        closed:true,
	        width: 850,    
	        height: 550,                
	        cache: false,
	        resizable:true,
	        shadow:false,
	        modal: true ,
	        href:'jsp/ad/ppeScrap/ppeScrapAttachAdd.jsp',
	        onOpen:function(){
	        	var appNo =$("#ppeScrapAppForm input[name='appNo'][type='hidden']").val();
				$('#appNo').val(appNo); 
			}
		});
		$('#formWindow').dialog("open");
	}
}


//删除物料
function delPPEScrapAppAttach(){
	var rows = $("#ppeScrapAttachGrid").datagrid("getSelections");
	var ids = new Array();
	if(rows==null || rows.length<=0){
		$.messager.alert("提示","请至少选择一条记录!","warning");
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].psaId);
	}
	ids = ids.join(",");
	var appNo = $("#ppeScrapAppForm input[name='appNo']").val();
	$.messager.confirm('删除', '是否删除该'+rows.length+'条记录', function(d) {
	if (d) {
		$.ajax({
		   type: "POST",
		   url: "ppeScrapAppAttachController/delPurchaseAppAttach.do",
		   data:{"ids":ids},
	       async: false,
		   success: function(msg){
			   if(msg.status){
				   createPpeScrapAttachGrid();
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