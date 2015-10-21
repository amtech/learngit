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
<title>工牌申请</title>
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

		/**
		 * 根据索引获取对象名称
		 * @param target Grid对象
		 * @param index 索引
		 * @returns 索引的对应行的信息
		 */
		function getRowData (index) {
			if (!$.isNumeric(index) || index < 0) { return undefined; }
			var rows = $("#purchaseAppGrid").datagrid("getRows");
		    return rows[index];
		}

	  $(function() {
		$("#procStatus").combobox({
			valueField: 'value',   
	        textField: 'label',   
	        data: [{
				label: '全部',
				value: ''
			},{
				label: '初始化',
				value: '1'
			},{
				label: '审批中',
				value: '2'
			},{
				label: '已完成',
				value: '3'
			},{
				label: '已失效',
				value: '4'
			},{
				label: '已撤销',
				value: '5'
			}],
			editable:false ,
			onLoadSuccess : function(){
			userData = $(this).combobox("getData");
			for (var item in userData[0]) {
	                if (item == "value") {
	                    $(this).combobox("select", userData[0][item]);
	                }
	            }
			}
		});	
	  }); 

  	//工牌申请列表
  	var $grid;
	$(function() {
		$grid =$("#purchaseAppGrid").datagrid({
					url : 'BadgeApp/findBadgeAppAttList.do',
					width: 'auto',
					height : 730,
					pagination : true,
					rownumbers : true,
					border : true,
					singleSelect : true,
					pageList:[10,20,30,40],
					nowrap : true,
					multiSort : false,
					fitColumns : true,
					columns : [ [
					        {field : 'appNo',title : '申请编号',width:110,align : 'center'},
					        {field : 'fullName',title : '部门',width:110,align : 'center'},
							{field : 'name',title : '申请人',width:100,align : 'center',
								formatter : function(value, row, index) {
									return "<a href=\"javascript:void(0)\" onclick=\"showView("+ index + ");\">" + value + "</a>";
								}
							},
							{field : 'namePinyin',title : '英文姓名',width : 110,sortable : true,align : 'center'},
							{field : 'positionName',title : '职位',width:100,align : 'center'},
							{field : 'remark',title : '备注',width : 200,align : 'center'},
							{field : 'procStatus',title : '流程状态',width : 80,align : 'center',formatter:function(value,row,index){
			                	if(row.procStatus == "1"){
			                		return "初始状态";
			                	}else if(row.procStatus == "2"){
			                		return "审批中";
			                	}else if(row.procStatus == "3"){
			                		return "已完成";
			                	}else if(row.procStatus == "4"){
			                		return "已失效";
			                	}else{
			                		return "已撤销";
			                	}
			                }},
							{field : 'appDate',title : '申请日期',width:100,align : 'center'},   
							{field : 'contractSignDate',title : '附件',width : 100,align : 'center',
								formatter : function(value,row,index){
									return "<a href=\"javascript:void(0)\" onclick=\"lookBadgeAttment("+ index + ");\">查看附件</a>";
								}	
							},
							{field : 'id',title : '操作',width:150,align : 'center',
								formatter : function(value, row, index) {
									//未提交和申请调整能看到修改申请
									if (row.procStatus == 1 ) {
										var result = "<a href='javascript:void(0);' onclick='sumitBadgeApp("+ index + ");'>提交申请</a>　　";
										result += "<a href='javascript:void(0);' onclick='delRows("+ index + ");'>删除申请</a>　　";
										result += "<a href='javascript:void(0);' onclick='updRowsOpenDlg("+ index + ");'>修改申请</a>";
										return result;
									} else {
										var result = "<a href='javascript:void(0);' onclick='lookBadgeAppCommentDialog("+index+");'>查看审批意见</a>　　"
										if(row.procStatus==2){
											result += "<a href='javascript:void(0);' onclick='showImage("+ index + ");'>查看当前流程图</a>"
										}
										
										return result;
									}
								}
							} 
							] ],
							 onLoadSuccess:function(data){
								
								   var rows = data.rows;
								   
								   delBadgeNotDate(rows);
								   
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
						        	   //$(this).datagrid("autoMergeCells",['fullName']);
						               $(this).datagrid('mergeCells',{
						                   index: mergeMap[i].index,
						                   field: 'appDate',
						                   rowspan: mergeMap[i].rowspan
						               });
						               $(this).datagrid('mergeCells',{
						                   index: mergeMap[i].index,
						                   field: 'procStatus',
						                   rowspan: mergeMap[i].rowspan
						               }); 
						              // $(this).datagrid("autoMergeCells",['appStateName']);
						               $(this).datagrid('mergeCells',{
						                   index: mergeMap[i].index,
						                   field: 'contractSignDate',
						                   rowspan: mergeMap[i].rowspan
						               });
						               $(this).datagrid('mergeCells',{
						                   index: mergeMap[i].index,
						                   field: 'id',
						                   rowspan: mergeMap[i].rowspan
						               });
						              
						           }
						           $(this).datagrid("doCellTip",{'max-width':'100px'});
							  }, 
					toolbar : '#tb'
				});
	});
	
	//查看附件
	function lookBadgeAttment(index){
		//主页面查看时，明细为申请id
		var row = this.getRowData(index);
		checkAttachementDetail(row.appNo,row.registrantNo,'',row.pnrId);
	};
	
	//新增
	function addRowsOpen() {
 		$("#applyView").dialog({
			title:"增加工牌申请",
			iconCls:'icon-add',
			resizable:true,
			modal:true,
			width: 900,
			height:660,
			href : "jsp/ad/badgeApply/badgeAppAddForm.jsp", 
			onClose:function(){
				$grid.datagrid('reload');
			} 
		}); 
	}
  
	//执行查询
	function subCustomerRepaymentForm(){
		$("#purchaseAppGrid").datagrid("load",{
			procStatus:$("#procStatus").combobox("getValue"),
			appNo:$("#appNoMain").val(),
			appDateS:$('#appDateS').datebox('getValue'),
			appDateE:$('#appDateE').datebox('getValue')
		});  
	}
	
	//弹窗修改
	function updRowsOpenDlg(index) {
		//var row = $dg.datagrid('getSelected');
			var row = this.getRowData(index);
			$("#editView").dialog({
				title : '修改工牌申请',
				iconCls:'icon-edit',
				width : 940,
				height : 710,
				modal:true,
				href : "jsp/ad/badgeApply/badgeAppEditForm.jsp",
				 onLoad:function(){
					 badgePeopleGrid(row.appNo);
				}, 
				onClose:function(){
					$grid.datagrid('reload');
				} 
			}); 
	}
	 // 查看流程批注
	 var $$row = "";
	function lookBadgeAppCommentDialog(index) {
		var rows = $("#purchaseAppGrid").datagrid("getRows");
		 $$row = rows[index];//获取本条数据
		$("#optionsDialog").dialog('open').dialog('refresh');
	} 
  
	// 提交申请
	function sumitBadgeApp(index){
		var row = this.getRowData(index);
		$.messager.confirm('确定','是否确定提交所选的数据吗？',	function(flag) {
			if (flag) {
				$.ajax({
						url : "BadgeTask/startBadgeApp.do",
						data : {"pnrId" : row.pnrId},
						success : function(rsp) {
							if(rsp.status){
								parent.$.messager.show({
									title : rsp.title,
									msg : rsp.message,
									timeout : 1000 * 2
								});
								$grid.datagrid('reload');
							}else{
								parent.$.messager.alert(rsp.title,rsp.message,'error');
							}
						}
					});
				}
			});
	}
	
	
	
	//删除请求
	function delRows(index) {
		var row = getRowData(index);
		if (row) {
			$.messager.confirm('提示','是否确定删除所选的数据吗？',function(flag) {
				if (flag) {
					$.ajax({
						url : "BadgeApp/deleteBadgeApp.do",
						data : {"appNo":row.appNo} ,
						dataType : 'JSON',
						success : function(rsp) {
							if (rsp.status) {
								//删除成功后刷新列表
								$grid.datagrid('reload');
								parent.$.messager.show({
									title : rsp.title,
									msg : rsp.message,
									timeout : 3000 * 2
								});
							}else{
								parent.$.messager.alert(rsp.title,rsp.message,'error');
							}
						}
					});
				}
			});
		}
	}
	
	// 查看流程图片
	function showImage(index) {
		var row = this.getRowData(index);
		var src = "BadgeTask/showBadgeProcess.do?pnrId="+ row.pnrId;
		$('#imageDialog').dialog("open");
		$("#image").attr("src", src);
	}
	
	//根据申请编号删除空数据
	function delBadgeNotDate(rows){
		var pnrIds = new Array();
		for(var i=0;i<rows.length;i++){
			if(rows[i].applicantNo==0){
				pnrIds.push(rows[i].pnrId);
			}
		}
		pnrIds = pnrIds.join(",");
		if(pnrIds!=''){
			$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否删除空数据?', function(d) {
	    		if (d) {
	    			$.ajax( {
	    				type : "POST",
	    				url : 'BadgeApp/delBadgeNotDate.do',
	    				data : {
	    					"pnrIds":pnrIds
	    				},
	    				dataType:'JSON',
	    				success : function(rsp) {
	    					if (rsp.status) {
								//删除成功后刷新列表
								$grid.datagrid('reload');
								parent.$.messager.show({
									title : rsp.title,
									msg : rsp.message,
									timeout : 3000 * 2
								});
							}else{
								parent.$.messager.alert(rsp.title,rsp.message,'error');
							}
	    				}
	    			});
	    		}
	    	});
		}
		
	}
	
	
</script>
</head>
<body>
			<div style="margin-left: 5px;margin-top: 5px">
				<div class="position" style="margin-top: 5px;">您当前所在位置：业务管理-->行政办公-->工牌申请</div>
			</div>
			<div  class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<form id="customerRepaymentForm"  method="post">
					<table cellpadding="10px;">
						<tr>
							 <th>申请编号:</th>
					      <td><input name="appNo" id="appNoMain" class="easyui-textbox"/></td>
					      <th>申请状态:</th>
					      <td>
					      	<input id="procStatus" name="procStatus" class="easyui-textbox easyui-validatebox" />
					      </td>
					      <th>申请日期:</th>
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
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpen();">添加</a></td>
				</tr>
			</table>
		</div>
		
		<table id="purchaseAppGrid" title="工牌申请管理"></table>
		<!-- 展示客户信息详情 -->
		<div id="applyView"></div>
		<div id="attachmentList">
			<table id="lookBadgeAttmentList" title="申请附件的信息"></table>
		</div>
		<div id="saveOrUpdateInvestProductDialog"></div>
		<div id="imageDialog" class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
			<img id="image" src="">
		</div>
		<div id ="editView"></div>
		<div id="optionsDialog" class="easyui-dialog" title="历史审批意见" style="width:900px;height:500px;" closed="true" data-options="href:'jsp/ad/optionsList.jsp',resizable:true,modal:true">
</body>
</html>
