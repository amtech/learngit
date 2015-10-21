<%@page import="com.oasys.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<jsp:include page="../../../layout/script.jsp"></jsp:include>
	<style type="text/css">
		a{
			text-decoration:none;
		}
	</style>
	<script type="text/javascript">
			//声明一个全局变量row
			//该变量可以在基于此Main页面上的弹出页面中使用，比如optionList.jsp页面。
			var $dg;
			var $grid;
			var $selRow;
			var procStateJson = [{ 
				"id":"", 
				"text":"全部状态", 
				"selected":true 
				},{ 
				"id":1, 
				"text":"初始状态" 
				},{ 
				"id":2, 
				"text":"审批中" 
				},{ 
				"id":3, 
				"text":"已完成"
				},{ 
				"id":4, 
				"text":"已失效" 
				},{ 
				"id":5, 
				"text":"已撤销" 
				}] ;
		 	$(function() {
				//加载固定资产报废申请的数据
				 $dg = $("#dg");
				
				 $grid=$dg.datagrid({
					url : "ppeScrapAppController/findPPEScrapTask.do?definitionKey=<%=Constants.PPESCRAPAPP_HO%>",
					width : 'auto',
					title: "固定资产报废申请",
					height : $(this).height()-83,
					pagination:true,
					rownumbers:true,
					border:true,
					singleSelect:true,
					nowrap:true,
					multiSort:false,
					border:false,
					fitColumns:true,
					columns : [ [
							  {field : 'psaId',        title : '编号',    width : $(this).width * 0.1, align:'center',
								        	 formatter: function(value,row,index){
								        		 return row.ppeApp.psaId;
								        	 }},
							  {field : 'taskID',        title : 'TASK_ID',    width : $(this).width * 0.1, align:'center',
							        	 formatter: function(value,row,index){
							        		 return row.ppeApp.taskID;
						      				}},
						 	 {field : 'formKey',        title : 'FORM_KEY',    width : 60, align:'center' ,
								        	 formatter: function(value,row,index){
								        		 return row.ppeApp.formKey;
							      				}},
							  {field : 'appNo',        title : '申请编号',    width : $(this).width * 0.1, align:'center',
								        	 formatter: function(value,row,index){
								        		 return row.ppeApp.appNo;
							      			 }},
							  {field : 'userName',        title : '申请人',    width : $(this).width * 0.1, align:'center',
								        	 formatter: function(value,row,index){
								        		 return row.ppeApp.userName;
							      			 }},
							  {field : 'orgName',        title : '申请部门',    width : $(this).width * 0.1, align:'center',
								        	 formatter: function(value,row,index){
								        		 return row.ppeApp.orgName;
							      			 }},
							  {field : 'appDate',        title : '申请时间',    width : $(this).width * 0.1, align:'center',
								        	 formatter: function(value,row,index){
								        		 return row.ppeApp.appDate;
							      			 }},
			   				{field : 'ppeNo',        title : '资产编号',    width : 60, align:'center' },
			   				{field : 'ppeName',      title : '资产名称', width : 875*0.1, align:'center'},
			   				{field : 'ppeModel',    title : '资产规格',    width : 875*0.1, align:'center' },
			   				{field : 'buyDate',    title : '购买时间',    width : 60, align:'center' },
			   				{field : 'usedYear',    title : '使用年限',    width : 60, align:'center' },
			   				{field : 'ppeGross',    title : '资产原值',    width : 50, align:'center' },
			   				{field : 'ppeNet',    title : '资产净值',    width : 50, align:'center' },
			   				{field : 'ppeSalvageVal',    title : '资产残值',    width : 50, align:'center' },
			   				{field : 'scrapReson',    title : '资产报废原因',    width : 50, align:'center' },												      				
					         {field : 'sqzt',    title : '申请状态',    width : $(this).width * 0.1, align:'center',
					        	 formatter: function(value,row,index){
					        		 var result = "";
										if (row.ppeApp.procStatus == 1) {
											result = "初始状态";
										} else if (row.ppeApp.procStatus == 3) {
											result = "审批完成";
										}  else if (row.ppeApp.procStatus == 5) {
											result = "审批撤销";
										} else {
											result = "审批中";
										}
										return result;
				      				}
					        	 },
					          {field : 'caozuo',    title : '操作',    width : $(this).width * 0.1, align:'center',
					        	 	formatter: function(value,row,index){
					        		   	var result = ""; 
										if (row.ppeApp.assistant == null || row.ppeApp.assistant == "") {
											result += "<a href='javascript:void(0);' onclick='singForTask("+row.ppeApp.taskID+");'>签收任务</a>　　";
										}else{
											result += "<a href='javascript:void(0);' onclick='saveTask("+index+");'>受理任务</a>　　";
										}
										return result; 
				      				}
					          }
					              ] ],
		              toolbar:'#tb',
		              onClickCell:function(rowIndex, field, value){
		            	  $(this).datagrid("selectRow","rowIndex");
		              },onLoadSuccess:function(data){
		            	  var rows = data.rows;
		                  var mergeMap = {};
		                  if(rows){
		                  	for(var i=0;i<rows.length;i++){
		                  		var appNo = rows[i].appNo
		                  		if( appNo in mergeMap ){
		                  			mergeMap[appNo].rowspan++;
		                  		}else{
		                  			mergeMap[appNo]={"index":i,"rowspan":1}
		                  		}
		                  	}
		                  }
		                  for(var i in mergeMap){
		                  	$(this).datagrid('mergeCells',{
		                          index: mergeMap[i].index,
		                          field: 'appNo',
		                          rowspan: mergeMap[i].rowspan
		                      });
		                  	$(this).datagrid('mergeCells',{
		                          index: mergeMap[i].index,
		                          field: 'userName',
		                          rowspan: mergeMap[i].rowspan
		                      });
		                      $(this).datagrid('mergeCells',{
		                          index: mergeMap[i].index,
		                          field: 'orgName',
		                          rowspan: mergeMap[i].rowspan
		                      });
		                      $(this).datagrid('mergeCells',{
		                          index: mergeMap[i].index,
		                          field: 'appDate',
		                          rowspan: mergeMap[i].rowspan
		                      });
		                      $(this).datagrid('mergeCells',{
		                          index: mergeMap[i].index,
		                          field: 'caozuo',
		                          rowspan: mergeMap[i].rowspan
		                      });
		                      $(this).datagrid('mergeCells',{
		                          index: mergeMap[i].index,
		                          field: 'sqzt',
		                          rowspan: mergeMap[i].rowspan
		                      });
		                  }
		                  $(this).datagrid("doCellTip",{'max-width':'100px'});
		              }
				});
				$('#dg').datagrid('hideColumn', 'psaId');
				$('#dg').datagrid('hideColumn','taskID');
 				$('#dg').datagrid('hideColumn','formKey');
				$('#procStatus').combobox({
					data:procStateJson,
					valueField:'id',
					textField:'text'
				});
			}); 

		 	
		//执行高级查询
		function doSearch(){			
			$("#dg").datagrid("load",{
				appDept:$("#appDept").val(),
				procStatus:$("#procStatus").combobox("getValue"),
				appDateBefore:$('#appDateBefore').datebox('getValue'),
				appDateAfter:$('#appDateAfter').datebox('getValue')
			}); 
		}

		//重置条件
		function clearAdvancedQueryConditions(){
			//1、清空高级查询各组件内容
			$("#searchForm").form("clear");
			//2、datagrid重新加载
			$("#dg").datagrid("load",{});
		}
		
		
		// 根据索引获取每一行的基本信息
		function getRowData (index) {
	        if (!$.isNumeric(index) || index < 0) { return undefined; }
	        var rows = $grid.datagrid("getRows");
	        return rows[index];
	    }

		function saveTask(index){
			var selectedRow = getRowData(index);
			$selRow = selectedRow;
			var formKey = selectedRow.ppeApp.formKey;
			$("#saveOrUpdateInvestProductDialog").dialog({
				/* 动态显示Dialog的标题	*/
				width : 800,
				height : 450,					
				title : '受理任务',
				href : formKey,
			    onLoad:function(){
			    	var saveOrUpdateForm = $("#investProductInputOrSaveForm");
			    	saveOrUpdateForm.form("load",selectedRow.ppeApp);
			    	$("#saveOrUpdateInvestProductDialog #investProductInputOrSaveForm #psaId").val(selectedRow.ppeApp.psaId);
			    	$("#saveOrUpdateInvestProductDialog #investProductInputOrSaveForm #taskID").val(selectedRow.ppeApp.taskID);
			    },
				modal:true,
				resizable:true,
				iconCls:'icon-add',
				closed: false
			});
		}
		
		//签收任务
		function singForTask(taskID){
			$.messager.confirm('签收任务', '是否确认签收任务?', function(d) {
	    		if (d) {
	    			$.ajax( {
	    				type : "POST",
	    				url : 'ppeScrapAppController/signForTask.do',
	    				data : "taskID="+taskID,
	    				dataType:'JSON',
	    				success : function(iJson) {		    					    				
	    					if(iJson.status){
	    						//刷新列表		    						
	    						$("#dg").datagrid("load",{});
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
      <div data-options="region:'center',border : false">
      	<div class="position" style="margin-top: 5px;">您当前所在位置： 任务管理-固定资产报废申请任务列表</div>
		<!-- 高级查询栏区域 -->
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
			<form id="searchForm" action="ppeScrapAppController/findAllppeScrap.do" method="post">
				<table cellpadding="0" cellspacing="1" border="0">
					<tr>
<!-- 						<td>所属部门：&nbsp;&nbsp;</td>
						<td><input name="appDept" id="appDept" type="text" class="easyui-textbox easyui-validatebox"  style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
						<td>申请状态：&nbsp;&nbsp;</td>
						<td><select id="procStatus" class="easyui-combobox" name="procStatus"  style="width: 170px;"></select>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>申请日期：&nbsp;&nbsp;</td>
						<td><input name="appDateBefore" id="appDateBefore" class="easyui-datebox" editable="true" style="width:174px;" value=""  title="开始日期" /></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
						<td><input name="appDateAfter" id="appDateAfter" class="easyui-datebox" editable="true" style="width:174px;" value="" title="结束日期"/></td>
						<td width="70px"></td>
						<td colspan="4" align="right">
						    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="doSearch();">执行查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
						    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="clearAdvancedQueryConditions()">条件重置</a>
						</td>	
					</tr>	
				</table>
			</form>			  			
		</div>
		<!-- 理财业绩数据表格区域 -->
		<table id="dg"  width="100%"></table>	
		<!-- 增加或修改理财对话框区域 -->
		<div id="saveOrUpdateInvestProductDialog"></div>	
  	</div>	
  </body>
</html>
