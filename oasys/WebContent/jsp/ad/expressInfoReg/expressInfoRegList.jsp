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
					url : "expressInfoRegController/findAllExpressInfoReg.do",
					title: "快递信息登记信息",
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
									{field : 'eirId',        title : '编号',    width : $(this).width * 0.1, align:'center'},
									{field : 'userName',        title : '登记人',    width : $(this).width * 0.2, align:'center'},
									{field : 'regDatetime',        title : '登记时间',    width : $(this).width * 0.2, align:'center'},
									{field : 'addUserName',        title : '收件人',    width : $(this).width * 0.2, align:'center' },
									{field : 'addOrgName',        title : '收件部门',    width : $(this).width * 0.2, align:'center'},
									{field : 'nhwrDate',      title : '收件日期', width : $(this).width * 0.2, align:'center'},
									{field : 'expNo',    title : '快递单号',    width : $(this).width * 0.2, align:'center' },
									{field : 'expAmt',    title : '金额',    width : $(this).width * 0.2, align:'center' },
									{field : 'nhwrReson',    title : '事由',    width : $(this).width * 0.2, align:'center' },
									{field : 'remark',    title : '备注',    width : $(this).width * 0.2, align:'center' },
									{field : 'caozuo',    title : '操作',    width : $(this).width * 0.2, align:'center',
						        	  formatter: function(value,row,index){
						        		  	var result = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; 
											result += "<a href='javascript:void(0);' onclick='toSaveOrUpdateExpressInfoReg("+ row.eirId + ");'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
											result += "<a href='javascript:void(0);' onclick='deleteExpressInfoReg("+ index + ");'>删除</a>　　";
											return result;
					      				}
						          }
					              ] ],
		              toolbar:'#tb',
		              onClickCell:function(rowIndex, field, value){
		            	  $(this).datagrid("selectRow","rowIndex");
		              }
				});
				 $('#dg').datagrid('hideColumn','eirId'); 
			});  
		 	
		 	
		 	/** 删除快递信息登记信息  **/
			function deleteExpressInfoReg(index){
				var selectedRow = getRowData(index);
		    	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
		    		if (d) {
		    			$.ajax( {
		    				type : "POST",
		    				url : 'expressInfoRegController/delExpressInfoReg.do',
		    				data : "ids="+selectedRow.eirId,
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
				regDatetimeEnd:$('#regDatetimeEnd').datebox('getValue')
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
				return '添加快递信息登记信息';					
			}else{
				return '修改快递信息登记信息';
			}
		}
		
		// 根据索引获取每一行的基本信息
		function getRowData (index) {
	        if (!$.isNumeric(index) || index < 0) { return undefined; }
	        var rows = $grid.datagrid("getRows");
	        return rows[index];
	    }

		
		/** 点击按钮，新增或者修改报废申请 **/			
		function toSaveOrUpdateExpressInfoReg(eirId){
			$("#saveOrUpdateExpressDialog").dialog({
				/* 动态显示Dialog的标题	*/
				width : 900,
				height : 650,					
				title : changeMyTitle(eirId),
				href : "expressInfoRegController/toAddExpressInfoReg.do?eirId="+ eirId,
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
      	<div class="position" style="margin-top: 5px;">您当前所在位置： 业务管理 -> 行政办公 -> 快递信息登记信息</div>
		<!-- 高级查询栏区域 -->
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
			<form id="searchForm" action="expressInfoRegController/findAllppeScrap.do" method="post">
			<input type="hidden"  name="appNoShow" id="appNoShow" />
				<table cellpadding="0" cellspacing="1" border="0">
					<tr>
						<td>登记日期：&nbsp;&nbsp;</td>
						<td><input name="regDatetimeBegin" id="regDatetimeBegin" class="easyui-datetimebox" editable="true" style="width:174px;" value=""  title="开始日期" /></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
						<td><input name="regDatetimeEnd" id="regDatetimeEnd" class="easyui-datetimebox" editable="true" style="width:174px;" value="" title="结束日期"/></td>
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
			<a id="id4ExportReports" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toSaveOrUpdateExpressInfoReg('');">新增</a>
		</div>
		<!-- 表格区域 -->
		<table id="dg"  width="100%"></table>	
		
		<!-- 增加或修改对话框区域 -->
		<div id="saveOrUpdateExpressDialog"></div>
		
		<!-- 查看历史审批区域 -->
		<div id="PpeScrapHisDialog"></div>
  	</div>	
  	<div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
	<img id="image" src="" >
	</div>
  </body>
</html>
