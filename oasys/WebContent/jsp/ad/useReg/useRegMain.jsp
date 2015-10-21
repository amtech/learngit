<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>固定资产领用登记</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<jsp:include page="../../../layout/script.jsp"></jsp:include>
<style type="text/css">
.textStyle{
	text-align: right;
	color: blue;
}
a{
			text-decoration:none;
		}
</style>
<script type="text/javascript">


  	//固定资产领用登记
  	var $grid;
	$(function() {
		$grid =$("#useReggrid").datagrid({
					url : 'UseReg/findUseRegList.do',
					width: 'auto',
					height : 740,
					pagination : true,
					rownumbers : true,
					border : true,
					singleSelect : false,
					pageList:[10,20,30,40],
					nowrap : true,
					multiSort : false,
					fitColumns : true,
					columns : [ [
					        {field : 'ppeNo',title : '固定资产编号',width:110,align : 'center'},
					        {field : 'ppeName',title : '固定资产名称',width:112,align : 'center'},
							{field : 'userName',title : '领用人',width : 104,sortable : true,align : 'center'},
			                {field : 'deptNoName',title : '领用部门',width:100,align : 'center'},
							{field : 'regDatetime',title : '领用日期',width:100,align : 'center'},
							{field : 'useQty',title : '领用数量',width:100,align : 'center'},
							{field : 'useReson',title : '领用用途',width:319,align : 'center'},
							{field : 'remark',title : '备注信息',width : 300,align : 'center'},
							] ],
							 onLoadSuccess:function(data){
								 	//当鼠标放上该字段时，提示功能
						            $(this).datagrid("doCellTip",{'max-width':'100px'});
						           
							  }, 
					toolbar : '#tb'
				});
	});
	
	//执行查询
	function subCustomerRepaymentForm(){
		$("#useReggrid").datagrid("load",{
			ppeNo:$("#appNoMain").val(),
			appDateS:$('#appDateS').datebox('getValue'),
			appDateE:$('#appDateE').datebox('getValue')
		});  
	}
	
	//添加
	function savePpeborrow(){
		$("#editView").dialog({
			title : '添加固定资产领用表',
			iconCls: 'icon-add',
			width : 920,
			height : 429,
			modal:true,
			href : "jsp/ad/useReg/useRegAddForm.jsp",
			onLoad:function(){
				
			}, 
			onClose:function(){
				$grid.datagrid('reload');
			} 
		}); 
	}
	
	//弹窗修改
	function saveOrUpdOpenDlg() {
			 $row = $("#useReggrid").datagrid('getSelected');
			$("#editView").dialog({
				title : '修改固定资产领用表',
				iconCls: 'icon-edit',
				width : 920,
				height : 429,
				modal:true,
				href : "jsp/ad/useReg/useRegAddForm.jsp",
				onLoad:function(){
					if($row!=null){
						var f = $("#UseRegForm");
						f.form("load", $row); 
						//借用人
						$("#user").combobox({
							url:"UseReg/findOrgUserList.do?organizeId="+$row.deptNo,
							valueField:'code',
						 	textFiled:'text',
						 	onLoadSuccess:function(date){
						 		$("#user").combobox('setValue',$row.user)
						 	}
						});
						
					}
				}, 
				onClose:function(){
					$grid.datagrid('reload');
				} 
			}); 
	}
	
	  //批量删除
    function delRows(){
    	var selected = $('#useReggrid').datagrid('getSelections');
    	if (selected.length <= 0) {
    		$.messager.alert("提示", "请至少选择一条记录执行删除!", "warning");
    		return;
    	}
    	var ids = new Array();
    	for(var i=0,len=selected.length; i<len; i++){
    		ids.push(selected[i].urId);
    	}
    	ids = ids.join(",");
    	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
    		if (d) {
    			$.ajax( {
    				type : "POST",
    				url : 'UseReg/delUseRegList.do',
    				data : {
    					"ids":ids
    				},
    				dataType:'JSON',
    				success : function(iJson) {
    					if(iJson.status){
    						$grid.datagrid('reload');
    					}
    					parent.$.messager.show({
    						title : iJson.title,
    						msg : iJson.message,
    						timeout : 4000 * 2
    					});
    				}
    			});
    		}
    	});
    } 
	
	
</script>
</head>
<body>
		<div style="margin-left: 5px;margin-top: 5px">
			<div class="position" style="margin-top: 5px;">您当前所在位置：业务管理-->行政办公-->固定资产领用登记</div>
		</div>
		
		<div  class="well well-small" style="margin-left: 5px;margin-top: 5px">
			<form id="customerRepaymentForm"  method="post">
				<table >
					<tr>
					      <th>固定资产编号:</th>
					      <td><input name="appNo" id="appNoMain" class="easyui-textbox"/></td>
					      <th>领用日期:</th>
					      <td>
					      	 <input id="appDateS" name="appDateS" placeholder="请选择起始日期" class="easyui-textbox easyui-datebox" data-options="editable:false" />
						　                             　至　　
							 <input id="appDateE" name="appDateE" placeholder="请选择截止日期" class="easyui-textbox easyui-datebox" data-options="editable:false"/>
					      	
					      </td>
					      <td>
					         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="subCustomerRepaymentForm();">搜索</a>
					         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="$('#customerRepaymentForm').form('reset');">重置</a>
					      </td>
				   </tr>
					
					
				</table>
			</form>
		</div>
			
	
			<div id="tb" style="padding: 2px 0">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td style="padding-left: 2px">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="savePpeborrow();">添加</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="saveOrUpdOpenDlg();">编辑</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">批量删除</a>
						</td>
					</tr>
				</table>
			</div>
		
		<table id="useReggrid" title="固定资产借用登记"></table>
		<!-- 固定资产增加修改页面 -->
		<div id="editView"></div>
		<!-- 展示客户信息详情 -->
		<div id="attachmentList">
			<table id="lookAttachmentList" title="申请附件的信息"></table>
		</div>
</body>
</html>
