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
    <title>贷款客户管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
			var $dg;
			var $grid;
			var genderTypeArr=jqueryUtil.getTextArr("gender_type");
			$(function() {
				 $dg = $("#dg");
				 $grid=$dg.datagrid({
					url : "loaner/loanerAction!findLoanerList.action",
					width : 'auto',
					height : $(this).height()-125,
					pagination:true,
					rownumbers:true,
					border:true,
					striped:true,
					fitColumns:true,
					singleSelect:true,
					columns : [ [ {field : 'name',title : '客户姓名',width :150,align : 'center',
									formatter:function(value,row){
										 return "<a style=\"color:blue;text-decoration:none;\" onmouseover=\"this.style.color='red'\" onmouseout=\"this.style.color='blue'\" href=\"javascript:void(0)\" onclick=\"showLoanerDetail('"+row.loanerId+"');\">"+value+"</a>";
									}
								  },
					              {field : 'idNo',title : '身份证号',width :200,align : 'center'},
					              {field : 'age',title : '年龄',width : 150,align : 'center'},
					              {field : 'genderType',title : '性别',width :150,align : 'center',
					            	  formatter:function(value,row){
					            		  return jqueryUtil.showText(value,genderTypeArr);
									}
					              },
					              /* {field : 'hukouAddr',title : '户籍地址',width :250,align : 'left'}, */
					              {field : 'mobileTel',title : '手机号码',width :150,align : 'center'},
					              {field : 'a',title : '贷款订单详情',width :300,align : 'center',
					            	  formatter:function(value,row){
					            		  return "<a style=\"color:blue;text-decoration:none;\" onmouseover=\"this.style.color='red'\" onmouseout=\"this.style.color='blue'\" href=\"javascript:void(0)\" onclick=\"showLoanerOrderList('"+row.loanerId+"');\">点击查看客户贷款订单详情</a>";
									}
					              }/* ,
					              {field : 'b',title : '客户履历详情',width :300,align : 'center',
					            	  formatter:function(value,row){
					            		  return "<a style=\"color:blue;text-decoration:none;\" onmouseover=\"this.style.color='red'\" onmouseout=\"this.style.color='blue'\" href=\"javascript:void(0)\" onclick=\"showLoanerHisList('"+row.loanerId+"');\">点击查看客户履历变更详情</a>";
									}  
					              } */
					              ] ]
				});
			});
			
			/**根据贷款人id查看贷款客户详情*/
			function showLoanerDetail(loanerId){
				parent.$.modalDialog({
					title : "贷款客户信息详情",
					width : 1100,
					height : 900,
					href : "loaner/loanerAction!forLastLoanerDetail.action?loanerId="+loanerId,
					buttons : [ {
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler = undefined;
						}
					}
					]
				});
			}
			/**根据客户id查看客户订单列表*/
			function showLoanerOrderList(loanerId){
				parent.$.modalDialog({
					title : "客户贷款订单列表",
					width : 1650,
					height : 800,
					href : "loaner/loanerAction!forLoanerOrderList.action?loanerId="+loanerId,
					buttons : [ {
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler = undefined;
						}
					}
					]
				});
			}
			/**根据客户id查看客户变更履历列表*/
			function showLoanerHisList(loanerId){
				parent.$.modalDialog({
					title : "客户履历变更列表",
					width : 1600,
					height : 800,
					href : "loaner/loanerAction!forLoanerHisList.action?loanerId="+loanerId,
					buttons : [ {
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler = undefined;
						}
					}]		
				});
			}
			/**高级搜索*/
			function doSearch(){
				var name = $("#custName").val();//客户姓名
				var idNo = $("#curIdCardNo").val();//身份证号
				var genderType=$("#gender").combobox('getValue');//性别
				$("#dg").datagrid('load',{name:name,idNo:idNo,genderType:genderType}); 
			}
			
			/** 清空高级查询条件 ，重新加载贷款订单数据**/
			function clearAdvancedQueryConditions(){
				$("#searchForm").form("clear");		
				$("#dg").datagrid('load',{});
			}			
			
		</script>
  </head>
  <style>
  .nkframe_position{padding-left:30px;margin-bottom:10px;border-bottom:1px solid #d2e7f8;height:24px;line-height:24px;background:url(images/nk_position.gif) 5px center no-repeat;font-size:12px;font-weight:normal;}
  </style>
  <body>
      <h1 class="nkframe_position" style="margin-top: 5px;">您当前所在位置： 贷款业务管理  &gt; 贷款客户管理</h1>

<div class="well well-small">
<span class="badge">高级查询</span>
	<form id="searchForm" action="" method="post">
		<table cellpadding="0" cellspacing="1" border="0" width="100%">
			<tr>
			    <td width="5%" style="text-align: right">客户姓名:</td>
				<td width="15%"><input name="custName" id="custName" type="text" class="easyui-textbox easyui-validatebox"/></td>
				<td width="5%" style="text-align: right">身份证号:</td>
				<td width="15%"><input id="curIdCardNo" name="curIdCardNo" type="text" class="easyui-textbox easyui-validatebox"/></td>
				<td width="5%" style="text-align: right">性别:</td>
				<td width="15%">
				<select id="gender" class="easyui-combobox" name="gender" style="width:200px;"> 
					<option value="">全部</option>  
				    <option value="M">男</option>   
				    <option value="F">女</option>    
				</select>  
				</td>
				<td width="50%">&nbsp;&nbsp;
			    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="doSearch();">搜索</a>
			    <!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" plain="false" onclick="javascript:$('#searchForm').form('clear');">清空</a> -->
			    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" plain="false" onclick="clearAdvancedQueryConditions()">清空</a>
			</td>
			</tr>
		</table>
	</form>
	</div>	
	<div>
		<table id="dg" title="贷款客户管理"></table>
  	</div>
  </body>
</html>
