<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<script type="text/javascript" src="js/validate.js"></script>
<style>
	.easyui-textbox{
		height: 18px;
		width: 170px;
		line-height: 16px;
	    /*border-radius: 3px 3px 3px 3px;*/
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
	}
	
	textarea:focus, input[type="text"]:focus{
	    border-color: rgba(82, 168, 236, 0.8);
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
	    outline: 0 none;
		}
		table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
	}

	fieldset {
	    border: 0 none;
	    margin: 0;
	    padding: 0;
	}
	legend {
	    -moz-border-bottom-colors: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    border-color: #E5E5E5;
	    border-image: none;
	    border-style: none none solid;
	    border-width: 0 0 1px;
	    color: #999999;
	    line-height: 20px;
	    display: block;
	    margin-bottom: 10px;
	    padding: 0;
	    width: 100%;
	}
	input, textarea {
	    font-weight: normal;
	}
	.table ,th,td{
		text-align:left;
		padding: 6px;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div class="well well-small" data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 贷款订单详情</legend>
				 <table class="table">
					 <tr>
					    <th style="text-align: right;">贷款订单编号:</th>
						<td>${loanOrderVO.loanOrderId }</td>
						<th style="text-align: right;">贷款申请日期:</th>
						<td>${loanOrderVO.createDate }</td>
					 </tr>
					 <tr>
					    <th style="text-align: right;">贷款人姓名:</th>
						<td>${loanOrderVO.loanerName }</td>
						<th style="text-align: right;">贷款类型:</th>
						<td>${loanOrderVO.loanType }</td>
					 </tr>
					 <tr>
					    <th style="text-align: right;">贷款申请额度:</th>
					    <td>${loanOrderVO.loanAmount }</td>
					    <th style="text-align: right;">最低接受额度:</th>
						<td>${loanOrderVO.loanMin }</td>
					 </tr>
					  <tr>
						<th style="text-align: right;">申请贷款期限:</th>
						<td>${loanOrderVO.loanPeriod}</td>
					    <th style="text-align: right;">贷款订单状态:</th>
						<td>${loanOrderVO.statusName}</td>
					 </tr>
					 <tr>
						<th style="text-align: right;">还款方式:</th>
						<td>${loanOrderVO.repayMethod}</td>
					    <th style="text-align: right;">订单处理业务员:</th>
						<td>${loanOrderVO.creator}</td>
					 </tr>
					 <tr>
						<th style="text-align: right;">贷款用途:</th>
						<td colspan="3">${loanOrderVO.purpose}</td>
					</tr>
				 </table>
			</fieldset>
	</div>
</div>
