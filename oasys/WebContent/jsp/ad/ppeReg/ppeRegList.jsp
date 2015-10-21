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
		 	$(function() {
				//加载固定资产报废申请的数据
				 $dg = $("#dg");
				 $grid=$dg.datagrid({
					url : "ppeRegController/findAllPpeReg.do",
					title: "固定资产登记信息",
					width : 'auto',
					height : $(this).height()-83,
					pagination:true,
					rownumbers:true,
					border:true,
					singleSelect:true,
					nowrap:true,
					multiSort:false,
					border:false,
					fitColumns:true,
					pageSize:30,
					pageList:[10,20,30,40],
					columns : [ [
									{field : 'prId',        title : '编号',    width : $(this).width * 0.1, align:'center'},
									{field : 'userName',        title : '登记人',    width : 20, align:'center'},
									{field : 'regDatetime',        title : '登记时间',    width : 60, align:'center'},
									{field : 'ppeUserName',        title : '使用人',    width : 60, align:'center' },
									{field : 'ppeOrgName',        title : '使用部门',    width : 70, align:'center'},
									{field : 'ppeName',    title : '固定资产名称',    width : 70, align:'center' },
									{field : 'ppeModel',    title : '固定资产规格',    width : 70, align:'center' },
									{field : 'qty',    title : '数量',    width : 30, align:'center' },
									{field : 'price',    title : '金额',    width : 30, align:'center' },
									{field : 'remark',    title : '备注',    width : 30, align:'center' },
									{field : 'caozuo',    title : '操作',    width : $(this).width * 0.2, align:'center',
						        	  formatter: function(value,row,index){
						        		  	var result = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; 
											result += "<a href='javascript:void(0);' onclick='toSaveOrUpdatePpeReg("+ row.prId + ");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
											result += "<a href='javascript:void(0);' onclick='deletePpeReg("+ index + ");'>删除</a>　　";
											return result;
					      				}
						          }
					              ] ],
		              toolbar:'#tb',
		              onClickCell:function(rowIndex, field, value){
		            	  $(this).datagrid("selectRow","rowIndex");
		              }
				});
				 $('#dg').datagrid('hideColumn','prId');
				 $(this).datagrid("doCellTip",{'max-width':'100px'});
			});  
		 	
		 	
		 	/** 删除固定资产登记信息  **/
			function deletePpeReg(index){
				var selectedRow = getRowData(index);
		    	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
		    		if (d) {
		    			$.ajax( {
		    				type : "POST",
		    				url : 'ppeRegController/delPpeReg.do',
		    				data : "ids="+selectedRow.prId,
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
		 	
		//执行高级查询
		function doSearch(){			
			$("#dg").datagrid("load",{
				regDatetimeBegin:$('#regDatetimeBegin').datebox('getValue'),
				regDatetimeEnd:$('#regDatetimeEnd').datebox('getValue'),
				ppeModel:$('#ppeModel').val(),
				ppeName:$('#ppeName').val()
			}); 
		}

		//重置条件
		function clearAdvancedQueryConditions(){
			//1、清空高级查询各组件内容
			$("#searchForm").form("clear");
			//2、datagrid重新加载
			$("#dg").datagrid("load",{});
		}
		
		/* 动态显示弹出的Dialog的标题,显示"添加"或者"修改"报废申请*/
		function changeMyTitle(index){
			if(null == index || '' == index){
				return '添加固定资产登记信息';					
			}else{
				return '修改固定资产登记信息';
			}
		}
		
		// 根据索引获取每一行的基本信息
		function getRowData (index) {
	        if (!$.isNumeric(index) || index < 0) { return undefined; }
	        var rows = $grid.datagrid("getRows");
	        return rows[index];
	    }

		
		/** 点击按钮，新增或者修改报废申请 **/			
		function toSaveOrUpdatePpeReg(prId){
			$("#saveOrUpdateExpressDialog").dialog({
				/* 动态显示Dialog的标题	*/
				width : 900,
				height : 650,					
				title : changeMyTitle(prId),
				href : "ppeRegController/toAddPpeReg.do?prId="+ prId,
				modal:true,
				resizable:true,
				iconCls:'icon-add',
				closed: false
			});
		}
	</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
      	<div class="position" style="margin-top: 5px;">您当前所在位置： 业务管理 -> 行政办公 -> 固定资产登记</div>
		<!-- 高级查询栏区域 -->
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
			<form id="searchForm" action="ppeRegController/findAllppeScrap.do" method="post">
			<input type="hidden"  name="appNoShow" id="appNoShow" />
				<table cellpadding="0" cellspacing="1" border="0">
					<tr>
						<td>登记日期：&nbsp;&nbsp;</td>
						<td><input name="regDatetimeBegin" id="regDatetimeBegin" class="easyui-datebox" editable="true" style="width:174px;" value=""  title="开始日期" /></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
						<td><input name="regDatetimeEnd" id="regDatetimeEnd" class="easyui-datebox" editable="true" style="width:174px;" value="" title="结束日期"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						
						<td>固定资产名称：&nbsp;&nbsp;</td>
						<td><input name="ppeName" id="ppeName" class="easyui-textbox"  style="width:174px;" value=""  />&nbsp;&nbsp;&nbsp;&nbsp;</td>
						
						<td>固定资产规格：&nbsp;&nbsp;</td>
						<td><input name="ppeModel" id="ppeModel" class="easyui-textbox"  style="width:174px;" value=""  /></td>
						
						<td width="70px"></td>
						<td colspan="6" align="right">
						    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="doSearch();">执行查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
						    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="clearAdvancedQueryConditions()">条件重置</a>
						</td>	
					</tr>	
				</table>
			</form>			  			
		</div>
		<div id="tb" style="padding:2px 0">
			<a id="id4ExportReports" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toSaveOrUpdatePpeReg('');">新增</a>
		</div>
		<!-- 表格区域 -->
		<table id="dg"  width="100%"></table>	
		<!-- 增加或修改对话框区域 -->
		<div id="saveOrUpdateExpressDialog"></div>
  	</div>	
  </body>
</html>
