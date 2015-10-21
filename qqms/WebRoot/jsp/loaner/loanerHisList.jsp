<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var $dg;
	$(function() {
		 $dg = $("#dg");
		 $grid=$dg.datagrid({
			url : "loaner/loanerAction!showLoanerHisList.action?loanerId='${loanerId}'",
			width : 'auto',
			height : 700,
			rownumbers:true,
			pagination:true,
			singleSelect:true,
			fitColumns:false,
			columns : [ [ {field : 'name',title : '贷款人姓名',width :100,align : 'center'},
			              {field : 'genderType',title : '贷款人性别',width :100,align : 'center'},
			              {field : 'idNo',title : '身份证号',width :150,align : 'center'},
			              {field : 'age',title : '年龄',width :100,align : 'center'},
			              {field : 'hukouAddr',title : '户籍地址',width :150,align : 'center'},
			              {field : 'curAddr',title : '现住地址',width :150,align : 'center'},
			              {field : 'mobileTel',title : '手机号码',width :100,align : 'center'},
			              {field : 'fixedTel',title : '住址电话',width :100,align : 'center'},
			              {field : 'marriageType',title : '婚姻状况',width :100,align : 'center'},
			              {field : 'hasChild',title : '有无子女',width :100,align : 'center'},
			              {field : 'email',title : '邮箱',width :100,align : 'center'},
			              {field : 'qqNo',title : 'QQ号',width :100,align : 'center'},
			              {field : 'annualSalary',title : '年收入',width :100,align : 'center'},
			              {field : 'incomeSrc',title : '收入来源',width :100,align : 'center'},
			              {field : 'mortgageStatus',title : '房屋居住状况',width :150,align : 'center'},
			              {field : 'houseInstallPay',title : '月供',width :100,align : 'center'},
			              {field : 'rent',title : '房租',width :100,align : 'center'},
			              {field : 'changeContents',title : '变更内容',width :200,align : 'center'},
			              {field : 'changeTiem',title : '变更时间',width :100,align : 'center'},
			              {field : 'changePeople',title : '变更人',width :100,align : 'center'}
			              ] ]
		});
		
		
	})
</script>
<div id="tt" style="margin-top:5px">
<table id="dg"></table>
</div>

