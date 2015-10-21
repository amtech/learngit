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
<title>固定资产借用登记</title>
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


  	//固定资产借用登记
  	var $grid;
	$(function() {
		$grid =$("#ppeBorrowReggrid").datagrid({
					url : 'PpeBorrowReg/findPpeBorrowRegList.do',
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
							{field : 'model',title : '固定资产型号',width:106,align : 'center'},
							{field : 'borrowerName',title : '借用人',width : 104,sortable : true,align : 'center'},
			                {field : 'fullName',title : '借用部门',width:100,align : 'center'},
							{field : 'regDatetime',title : '借用日期',width:132,align : 'center'},
							{field : 'borReson',title : '借用事由',width:319,align : 'center'},
							{field : 'remark',title : '备注信息',width : 274,align : 'center'},
							{field : 'reverterName',title : '归还人',width : 141,align : 'center'},
							{field : 'revDatetime',title : '归还日期',width:138,align : 'center'},   
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
		$("#ppeBorrowReggrid").datagrid("load",{
			ppeNo:$("#appNoMain").val(),
			appDateS:$('#appDateS').datebox('getValue'),
			appDateE:$('#appDateE').datebox('getValue')
		});  
	}
	
	//添加
	function savePpeborrow(){
		$("#editView").dialog({
			title : '添加固定资产借用登记',
			iconCls: 'icon-add',
			width : 920,
			height : 420,
			modal:true,
			href : "jsp/ad/ppeBorrowReg/ppeBorrowRegAddForm.jsp",
			onLoad:function(){
				
			}, 
			onClose:function(){
				$grid.datagrid('reload');
			} 
		}); 
	}
	
	//弹窗修改
	function saveOrUpdOpenDlg() {
			 $row = $("#ppeBorrowReggrid").datagrid('getSelected');
			$("#editView").dialog({
				title : '修改固定资产借用登记',
				iconCls: 'icon-edit',
				width : 920,
				height : 429,
				modal:true,
				href : "jsp/ad/ppeBorrowReg/ppeBorrowRegAddForm.jsp",
				onLoad:function(){
					
					if($row!=null){
						var f = $("#ppeBorrowRegForm");
						f.form("load", $row); 
						//借用人
						$("#borrower").combobox({
							url:"PpeBorrowReg/findOrgUserList.do?organizeId="+$row.deptNo,
							valueField:'code',
						 	textFiled:'text',
						 	onLoadSuccess:function(date){
						 		$("#borrower").combobox('setValue',$row.borrower)
						 	}
						});
						//归还人
						if($row.reverter!=null){
							$("#reverter").combobox({
								url:"PpeBorrowReg/findOrgUserList.do?organizeId="+$row.reverterDeptNo,
								valueField:'code',
							 	textFiled:'text',
							 	onLoadSuccess:function(date){
							 		$("#reverter").combobox('setValue',$row.reverter)
							 	}
							});
						}
						
						addOrEditval();
					}
				}, 
				onClose:function(){
					$grid.datagrid('reload');
				} 
			}); 
	}
	/**
	 * 根据索引获取对象名称
	 * @param target Grid对象
	 * @param index 索引
	 * @returns 索引的对应行的信息
	 */
	function getRowData (target,index) {
		if (!$.isNumeric(index) || index < 0) { return undefined; }
		if ($.isEmptyObject(target)) { return undefined; }
	    var rows = target.datagrid("getRows");
	    return rows[index];
	}
	
	
	  //批量删除
    function delRows(){
    	var selected = $('#ppeBorrowReggrid').datagrid('getSelections');
    	if (selected.length <= 0) {
    		$.messager.alert("提示", "请至少选择一条记录执行删除!", "warning");
    		return;
    	}
    	var ids = new Array();
    	for(var i=0,len=selected.length; i<len; i++){
    		ids.push(selected[i].pbrId);
    	}
    	ids = ids.join(",");
    	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
    		if (d) {
    			$.ajax( {
    				type : "POST",
    				url : 'PpeBorrowReg/delPpeBorrowRegList.do',
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
			<div class="position" style="margin-top: 5px;">您当前所在位置：业务管理-->行政办公-->固定资产借用登记</div>
		</div>
		
		<div  class="well well-small" style="margin-left: 5px;margin-top: 5px">
			<form id="customerRepaymentForm"  method="post">
				<table >
					<tr>
					      <th>固定资产编号:</th>
					      <td><input name="appNo" id="appNoMain" class="easyui-textbox"/></td>
					      <th>借用日期:</th>
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
		
		<table id="ppeBorrowReggrid" title="固定资产借用登记"></table>
		<!-- 固定资产增加修改页面 -->
		<div id="editView"></div>
		<!-- 展示客户信息详情 -->
		<div id="attachmentList">
			<table id="lookAttachmentList" title="申请附件的信息"></table>
		</div>
</body>
</html>
