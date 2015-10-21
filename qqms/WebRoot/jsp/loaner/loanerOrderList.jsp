<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var $dg;
	$(function() {
		 $dg = $("#dg");
		 $grid=$dg.datagrid({
			url : "loaner/loanerAction!showLoanerOrderList.action?loanerId='${loanerId}'",
			width : 'auto',
			height:700,
			rownumbers:true,
			pagination:true,
			border:false,
			singleSelect:true,
			fitColumns:false,
			columns : [ [ {field : 'loanerName',title : '贷款人姓名',width :100,align : 'center',
							formatter:function(value,row){
								 return "<a style=\"color:blue;text-decoration:none;\" onmouseover=\"this.style.color='red'\" onmouseout=\"this.style.color='blue'\" href=\"javascript:void(0)\" onclick=\"showLoanerDetail('"+row.loanOrderId+"');\">"+value+"</a>";
							} 	
						  },
			              {field : 'loanOrderId',title : '贷款订单编号',width :300,align : 'center',
							  formatter:function(value,row){
			            		  return "<a style=\"color:blue;text-decoration:none;\" onmouseover=\"this.style.color='red'\" onmouseout=\"this.style.color='blue'\" href=\"javascript:void(0)\" onclick=\"showLoanOrderDetail('"+row.loanOrderId+"');\">"+value+"</a>";
							} 
			              },
			              {field : 'idNo',title : '身份证',width :200,align : 'center'},
			              {field : 'loanType',title : '贷款类型',width :100,align : 'center'},
			              {field : 'loanAmount',title : '贷款申请额度',width :150,align : 'center'},
			              {field : 'loanMin',title : '最低接受额度',width :150,align : 'center'},
			              {field : 'createDate',title : '贷款申请日期',width :150,align : 'center'},
			              {field : 'creator',title : '订单处理员工',width :150,align : 'center'},
			              {field : 'statusName',title : '贷款订单状态',width :150,align : 'center'},
			              {field : 'a',title : '订单信审信息',width :150,align : 'center',
			            	  formatter:function(value,row){
			            		  return "<a style=\"color:blue;text-decoration:none;\" onmouseover=\"this.style.color='red'\" onmouseout=\"this.style.color='blue'\" href=\"javascript:void(0)\" onclick=\"showCreditAuditDetail('"+row.loanOrderId+"');\">点击查看订单信审详情</a>";
							}  
			              }
			              ] ],toolbar:'#tb'
		});		
	});
	/**根据贷款人id查看贷款客户详情*/
	function showLoanerDetail(loanOrderId){
		$('#dd').dialog({    
		    title: '贷款客户个人信息',    
		    width: 1100,    
		    height: 900,    
		    closed: false,    
		    cache: false,    
		    href : "loaner/loanerAction!forLoanerDetail.action?loanOrderId="+loanOrderId,   
		    modal: true,
		    buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#dd').dialog('close');
				}
			}
			]
		});    
	}
	
	/*根据贷款定单id查看该条订单详情*/
	function showLoanOrderDetail(loanOrderId){
		$('#dd').dialog({    
		    title: '贷款订单详细信息',    
		    width: 600,    
		    height: 400,    
		    closed: false,    
		    cache: false,    
		    href : "loaner/loanerAction!forLoanOrderDetail.action?loanOrderId="+loanOrderId,   
		    modal: true,
		    buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#dd').dialog('close');
				}
			}
			]
		});    
	}
	
	/*根据贷款订单id查询订单信审情况*/
	function showCreditAuditDetail(loanOrderId){
		$('#dd').dialog({    
		    title: '贷款订单信审报告',    
		    width: 1150,    
		    height: 900,    
		    closed: false,    
		    cache: false,    
		    href : "loaner/loanerAction!forLoanCreditAuditReportDetail.action?loanOrderId="+loanOrderId,   
		    modal: true,
		    buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#dd').dialog('close');
				}
			}
			]
		});    
	}
</script>
<div id="tt" style="margin-top:5px">
<table id="dg"></table>
</div>
<div id="dd"></div>  