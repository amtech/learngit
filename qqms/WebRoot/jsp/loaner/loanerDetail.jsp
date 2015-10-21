<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<div class="well well-small" data-options="region:'center',border:false" title="">
				<div title="个人基本信息" data-options="iconCls:'icon-cstbase'" style="padding:10px">
						<fieldset>
							<legend>个人基本信息</legend>
							<c:if test="${empty baseObj }">
								客户未填写个人基本信息!
							</c:if>
							<c:if test="${!empty baseObj }">
							 <table class="table" width="100%" border="0">
								 <tr>
								    <th width="10%" style="text-align: right;">贷款人姓名</th>
									<td>${baseObj[0] }</td>
									<th style="text-align: right;">性别</th>
									<td>${baseObj[1] }</td>
									<th style="text-align: right;">身份证号</th>
									<td>${baseObj[2] }</td>
									<th style="text-align: right;">年龄</th>
									<td>${baseObj[3] }</td>
								 </tr>
								 <tr>
								    <th style="text-align: right;">户籍地址</th>
									<td>${baseObj[4] }</td>
								    <th style="text-align: right;">现住地址</th>
									<td>${baseObj[5] }</td>
									<th style="text-align: right;">手机</th>
									<td>${baseObj[6] }</td>
									<th style="text-align: right;">住址电话</th>
									<td>${baseObj[7] }</td>
								 </tr>
								  <tr>
									<th style="text-align: right;">QQ号</th>
									<td>${baseObj[9] }</td>
									<th style="text-align: right;">邮箱</th>
									<td>${baseObj[10] }</td>
									<th style="text-align: right;">婚姻状况</th>
									<td>${baseObj[11] }</td>
									 <th style="text-align: right;">有无子女</th>
									<td>${baseObj[12] }</td>
								 </tr>
								<tr>
								    <th width="10%" style="text-align: right;">年收入</th>
									<td>${baseObj[13] }</td>
									<th style="text-align: right;">收入来源</th>
									<td>${baseObj[14] }</td>
									<th style="text-align: right;">居住状况</th>
									<td>${baseObj[15] }</td>
								 </tr>
							   </table>
							   </c:if>
						</fieldset>
				</div>
				<div title="单位信息" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>单位信息</legend>
							<c:if test="${empty companyList }">
								客户未填写单位信息!
							</c:if>
						<c:if test="${!empty companyList }">
						 <table class="table" width="100%" border="1">
							   <%-- <tr>
								    <th style="text-align: right;" width="10%">工作单位</th>
									<td width="15%">${companyObj[0] }</td>
									<th style="text-align: right;" width="10%">注册资本</th>
									<td width="10%">${companyObj[3] }</td>
									<th style="text-align: right;" width="10%">单位电话</th>
									<td width="10%">${companyObj[1] }</td>
									<th style="text-align: right;" width="10%">单位地址</th>
									<td>${companyObj[2] }</td>
								 </tr>
								 <tr>
								    <th style="text-align: right;">成立时间</th>
									<td>${companyObj[4] }</td>
									<th style="text-align: right;" width="10%">员工数量</th>
									<td>${companyObj[5] }</td>
								    <th style="text-align: right;" width="10%">营业面积</th>
									<td>${companyObj[6] }</td>
									<th style="text-align: right;" width="10%">经营范围</th>
									<td>${companyObj[7] }</td>
								 </tr> --%>
								 <tr>
								 <th width="15%" style="text-align: center;">工作单位</th>
								 <th width="12%" style="text-align: center;">注册资本</th>
								 <th width="10%" style="text-align: center;">单位电话</th>
								 <th width="23%" style="text-align: center;">单位地址</th>
								 <th width="10%" style="text-align: center;">成立时间</th>
								 <th width="10%" style="text-align: center;">员工数量</th>
								 <th width="10%" style="text-align: center;">营业面积</th>
								 <th width="10%" style="text-align: center;">经营范围</th>
								 </tr>
								 <c:forEach items="${companyList }" var="companyObj">
								 <tr>
								 <td style="text-align: center;">${companyObj[0] }</td>
								 <td style="text-align: center;">${companyObj[3] }</td>
								 <td style="text-align: center;">${companyObj[1] }</td>
								 <td>${companyObj[2] }</td>
								 <td style="text-align: center;">${companyObj[4] }</td>
								 <td style="text-align: center;">${companyObj[5] }</td>
								 <td style="text-align: center;">${companyObj[6] }</td>
								 <td style="text-align: center;">${companyObj[7] }</td>
								 </tr>
								 </c:forEach>
						</table>
						</c:if>
						</fieldset>
				</div>
				<div title="开户行信息" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>开户行信息</legend>
							<c:if test="${empty assetsList }">
								客户未填写开户行信息!
							</c:if>
						<c:if test="${!empty assetsList }">
						 <table class="table" width="100%" border="1">
								 <tr>
									<th width="20%" style="text-align: center;">开户名称</th>
									<th width="20%" style="text-align: center;">开户行</th>
								    <th width="20%" style="text-align: center;">银行账号</th>
								    <th width="20%" style="text-align: center;">账户介质</th>
								    <th width="20%" style="text-align: center;">账户性质</th>
								</tr>
								<c:forEach items="${assetsList }" var="assets">
								<tr>
								<td style="text-align: center;">${assets[0] }</td>
								<td style="text-align: center;">${assets[1] }</td>
								<td style="text-align: center;">${assets[2] }</td>
								<td style="text-align: center;">${assets[3] }</td>
								<td style="text-align: center;">${assets[4] }</td>
								</tr> 
								</c:forEach>
						</table>
						</c:if>
						</fieldset>
				</div>
				<div title="配偶信息" data-options="iconCls:'icon-help'" style="padding:10px">
							<fieldset>
							<legend>配偶信息</legend>
							<c:if test="${empty spouseObj }">
								客户未填写配偶信息!
							</c:if>
							<c:if test="${!empty spouseObj }">
							  <table class="table" width="100%" border="0">
								<tr>
								    <th width="10%" style="text-align: right;">配偶姓名</th>
									<td>${spouseObj[1] }</td>
									<th style="text-align: right;">性别</th>
									<td>${spouseObj[2] }</td>
									<th style="text-align: right;">身份证号</th>
									<td>${spouseObj[3] }</td>
								 </tr>
								 <tr>
								    <th style="text-align: right;">年龄</th>
									<td>${spouseObj[4] }</td>
									<th style="text-align: right;">现住地址</th>
									<td>${spouseObj[5] }</td>
									<th style="text-align: right;">手机</th>
									<td>${spouseObj[6] }</td>
								 </tr>
								 <tr>
								    <th style="text-align: right;">家庭电话</th>
									<td>${spouseObj[7] }</td>
									<th style="text-align: right;">工作单位</th>
									<td>${spouseObj[8] }</td>
									<th style="text-align: right;">单位电话</th>
									<td>${spouseObj[9] }</td>
								 </tr>
								 <tr>
								    <th style="text-align: right;">职务</th>
									<td>${spouseObj[10] }</td>
									<th style="text-align: right;">单位地址</th>
									<td>${spouseObj[11] }</td>
									<th style="text-align: right;">年收入</th>
									<td>${spouseObj[12] }</td>
								 </tr>
								 <tr>
								    <th style="text-align: right;">工作年限</th>
									<td>${spouseObj[13] }</td>
									</tr>
							</table>
							</c:if>
						</fieldset>
				</div>
							
				<div title="紧急联系人信息" data-options="iconCls:'icon-help'" style="padding:10px">
					<fieldset>
							<legend>紧急联系人信息</legend>
							 <c:if test="${empty emccontactList }">
							 	客户未填写紧急联系人信息!
							 </c:if>
							 <c:if test="${!empty emccontactList }">
							  <table class="table" width="100%" border="1">
							 <tr>
								    <th style="text-align: center;" width="15%">姓名</th>
								    <th style="text-align: center;" width="15%">与本人关系</th>
								    <th style="text-align: center;" width="25%">工作单位</th>
								    <th style="text-align: center;" width="15%">单位地址</th>
								    <th style="text-align: center;" width="15%">现住地址</th>
								    <th style="text-align: center;" width="15%">联系电话</th>
							 </tr>
							<c:forEach items="${emccontactList }" var="emc">
							 <tr>
								    <td style="text-align: center;">${emc[0] }</td>
								    <td style="text-align: center;">${emc[1] }</td>
								    <td style="text-align: center;">${emc[2] }</td>
								    <td>${emc[3] }</td>
								    <td>${emc[4] }</td>
								    <td style="text-align: center;">${emc[5] }</td>
							 </tr>
							</c:forEach>
						</table>
						</c:if>
						</fieldset>
				</div>
		  </div>
</div>
