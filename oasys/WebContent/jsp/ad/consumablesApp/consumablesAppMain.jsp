<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../../layout/script.jsp"></jsp:include>
<title>低值易耗品申请主页面</title>
<style type="text/css">
.easyui-textbox {
	height: 18px;
	width: 170px;
	line-height: 16px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
}

textarea:focus, input[type="text"]:focus {
	border-color: rgba(82, 168, 236, 0.8);
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
	outline: 0 none;
}
.table {
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
	max-width: 100%;
}
fieldset {
	    border-width: 1px;
	    margin-top: 5px;
	    border-color:#F5F5F5;
	    -moz-border-radius:8px;
}
input, textarea {
	font-weight: normal;
}

.table td {
	padding: 6px;
}
.table th{
    text-align: right;
	padding: 6px;
}
.textStyle{
  text-align: right;
}
</style>
</head>
<script type="text/javascript">
var $dg;
var $grid;
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
	
$(function(){
	createconsumablesAppGrid();
})
/**
 * 创建低值易耗品申请列表
 */
function createconsumablesAppGrid(){
	$("#dg").datagrid({
		url:'consumablesApp/findConsumablesAppList.do',
		title:'低值易耗品申请',
		width: 'auto',
		height : $(this).height()-65,
		pagination:true,
		rownumbers:true,
		border:false,
		singleSelect:true,
		nowrap:true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
		pageSize:30,
		pageList:[10,20,30,40],
		remoteSort:false,//定义是否从服务器对数据进行排序。
		striped:true,//是否显示斑马线
		columns:[[	{field : 'caId',        title : '编号',    width : $(this).width * 0.1, align:'center',
				       	 formatter: function(value,row,index){
				    		 return row.consumablesApp.caId;
								}},
			        {field : 'appNo',title : '申请编号',width :120,align : 'center',
			        	 formatter: function(value,row,index){
			        		 return row.consumablesApp.appNo;
		      				}},
	                {field : 'account',title : '申请人',width : 100,align : 'center',
					        	 formatter: function(value,row,index){
					        		 return row.consumablesApp.account;
				      				}},
		            {field : 'appDeptName',title : '申请部门',width : 100,align : 'center',
							        	 formatter: function(value,row,index){
							        		 return row.consumablesApp.appDeptName;
						      				}},
	                {field : 'appDate',title : '申请日期',width : 80,align : 'center',
									        	 formatter: function(value,row,index){
									        		 return row.consumablesApp.appDate;
								      				}},
	                /* {field : 'appStatus',title : '申请状态',width : 80,align : 'center'}, */
	                {field : 'procStatus',title : '流程状态',width : 80,align : 'center',formatter:function(value,row,index){
	                	if(row.consumablesApp.procStatus == "1"){
	                		return "初始状态";
	                	}else if(row.consumablesApp.procStatus == "2"){
	                		return "审批中";
	                	}else if(row.consumablesApp.procStatus == "3"){
	                		return "已完成";
	                	}else{
	                		return "已撤销";
	                	}
	                }},
	                {field : 'remark',title : '备注',width : 110,align : 'center',
			        	 formatter: function(value,row,index){
			        		 return row.consumablesApp.remark;
		      				}},
	                {field : 'goodsName',title : '用品名称',width : 80,align : 'center'},
	                {field : 'model',title : '规格型号',width : 80,align : 'center'},
	                {field : 'unit',title : '单位',width : 80,align : 'center'},
	                {field : 'stock',title : '库存',width : 80,align : 'center'},
	                {field : 'price',title : '单价',width : 80,align : 'center'},
	                {field : 'qty',title : '数量',width : 80,align : 'center'},
	                {field : 'totalAmt',title : '合计金额',width : 80,align : 'center'},
	                {field : 'allAmt',title : '总金额',width : 80,align : 'center',
			        	 formatter: function(value,row,index){
			        		 return row.consumablesApp.allAmt;
		      				}},
	                {field : 'caozuo',title : '操作',width :180,align : 'center',formatter:function(value,row,index){
	                	if(row.consumablesApp.procStatus == "1"){
	             	    	return "<a href='javascript:void(0);' onclick='deleteConsumablesApp("+ index + ");'>删除</a>&nbsp;&nbsp;"+"<a href='javascript:void(0);' onclick='toSaveOrUpdatePpeScrap("+row.consumablesApp.caId+");'>编辑</a>&nbsp;&nbsp;"+"<a href='javascript:void(0);' onclick='sumitLoanOrder("+index+");'>提交申请</a>";
	             	    }else if(row.consumablesApp.procStatus == "2"){
	             	    	return "<a href='javascript:void(0);' onclick='checkInvestOrderOpinions("+ index + ");'>查看审批意见</a>&nbsp;&nbsp;"+"<a href='javascript:void(0);' onclick='showImage("+ index + ");'>查看流程图</a>";
	             	    }else{
	             	    	return "<a href='javascript:void(0);' onclick='checkInvestOrderOpinions("+ index + ");'>查看审批意见</a>";
	             	    }
	                }}
	                
		   ]],
		   onClickCell:function(rowIndex, field, value){
         	  $(this).datagrid("selectRow","rowIndex");
           },
		   onLoadSuccess:function(data){
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
	                   field: 'account',
	                   rowspan: mergeMap[i].rowspan
	               });
	               $(this).datagrid('mergeCells',{
	                   index: mergeMap[i].index,
	                   field: 'appDeptName',
	                   rowspan: mergeMap[i].rowspan
	               });
	               $(this).datagrid('mergeCells',{
	                   index: mergeMap[i].index,
	                   field: 'allAmt',
	                   rowspan: mergeMap[i].rowspan
	               });
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
	               $(this).datagrid('mergeCells',{
	                   index: mergeMap[i].index,
	                   field: 'remark',
	                   rowspan: mergeMap[i].rowspan
	               });
	               $(this).datagrid('mergeCells',{
	                   index: mergeMap[i].index,
	                   field: 'caozuo',
	                   rowspan: mergeMap[i].rowspan
	               });
	           }
	           $(this).datagrid("doCellTip",{'max-width':'100px'});
		  }, 
	   /* toolbar:[{
		   iconCls: 'icon-add',
		   text:'新增',
		   handler:toAdd
	   }] */
	   	  toolbar:'#tb',
	});
	$('#dg').datagrid('hideColumn','caId'); 
	$('#procStatus').combobox({
		data:procStateJson,
		valueField:'id',
		textField:'text'
	});
}
/**
 * 新增
 */
function toAdd(){
	$('#addWindow').dialog({    
	    title: '添加申请',    
	    width: 920,    
	    height: 600,    
	    closed: false,    
	    cache: false, 
	    resizable:true,
	    href: 'consumablesApp/toAddWindow.do',    
	    modal: true,
	    onClose : function(){
	    	$("#dg").datagrid("reload");
	    },
	    buttons : [ {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addWindow').dialog('close');
			}
		}]
	});
}
//删除
function toDelete(index){
	var row = getRowData(index);
	$.ajax({
		   type: "POST",
		   url: "consumablesAppController/delconsumablesApp.do?paId="+row.paId,
	       async: false,
	       dataType:'JSON',
		   success: function(msg){
			   if(msg.status){
				   $("#dg").datagrid("reload");
			   }
			   parent.$.messager.show({
	    			title : msg.title,
	    			msg : msg.message,
	    			timeout : 4000 * 2
	    	   });
		   }
	});
}
//根据index获取该行
function getRowData(index){
	if (!$.isNumeric(index) || index < 0) {
		return undefined;
	}
	var rows = $("#dg").datagrid("getRows");
	return rows[index];
}
//高级搜索
function toSearch(){
	var appNo = $("#searchForm input[name='appNo']").val();
 	var appType = $("#appType").combobox('getValue');
	var totalAmtMini = $("#totalAmtMini").numberbox('getValue');
	var totalAmtMax = $("#totalAmtMax").numberbox('getValue');
	var appDateMini = $("#appDateMini").datebox('getValue');
	var appDateMax = $("#appDateMax").datebox('getValue'); 
	$("#dg").datagrid("reload",{"appNo":appNo,"appType":appType,"totalAmtMini":totalAmtMini,"totalAmtMax":totalAmtMax,"appDateMini":appDateMini,"appDateMax":appDateMax});
};
//查看详情
function showconsumablesAppAttachView(index){
	var row = getRowData(index);
	$('#addWindow').dialog({    
	    title: '低值易耗品详情',    
	    width: 920,    
	    height: 600,    
	    closed: false,    
	    cache: false, 
	    resizable:true,
	    href: 'consumablesAppAttachController/findconsumablesAppAttachList.do?appNo='+row.appNo,    
	    modal: true,
	    buttons : [ {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addWindow').dialog('close');
			}
		}]
	});
};
//编辑
function toEdit(index){
	var row = getRowData(index);
	$('#addWindow').dialog({    
	    title: '修改',    
	    width: 920,    
	    height: 600,    
	    closed: false,    
	    cache: false, 
	    resizable:true,
	    href: 'consumablesAppController/findconsumablesAppByPaId.do?appNo='+row.appNo,    
	    modal: true,
	    onClose : function(){
	    	$("#dg").datagrid("reload");
	    },
	    buttons : [ {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addWindow').dialog('close');
			}
		}]
	});
}
/**
 * 提交申请
 */
function sumitConsumablesApp(index){
	var row = this.getRowData(index);
	$.messager.confirm('确定','是否确定提交所选的数据吗？',function(flag) {
		if (flag) {
			$.ajax({
					url : "consumablesAppController/startWorkflow.do",
					data : {"paId" : row.paId},
					success : function(rsp) {
						if(rsp.status){
							parent.$.messager.show({
								title : rsp.title,
								msg : rsp.message,
								timeout : 2000 * 2
							});
							$("#dg").datagrid('reload');
						}else{
							parent.$.messager.alert(rsp.title,rsp.message,'error');
						}
					}
				});
			}
		});
}

/** 点击按钮，新增或者修改申请 **/			
function toSaveOrUpdatePpeScrap(caId){
	$("#addWindow").dialog({
		/* 动态显示Dialog的标题	*/
		width : 900,
		height : 650,					
		title : changeMyTitle(caId),
		href : "consumablesApp/toAddConsumablesApp.do?caId="+ caId,
		modal:true,
		resizable:true,
		iconCls:'icon-add',
		closed: false,
	    buttons : [ {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addWindow').dialog('close');
				$("#dg").datagrid("reload");
			}
		}]
	});
}

/* 动态显示弹出的Dialog的标题,显示"添加"或者"修改"报废申请*/
function changeMyTitle(index){
	if(null == index || '' == index){
		return '添加低值易耗品申请单';					
	}else{
		return '修改低值易耗品申请单';
	}
}

/** 删除申请  **/
function deleteConsumablesApp(index){
	var selectedRow = getRowData(index);
	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
		if (d) {
			$.ajax( {
				type : "POST",
				url : 'consumablesApp/delConsumablesApp.do',
				data : "appNo="+selectedRow.appNo,
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

//提交申请
function sumitLoanOrder(index){
	var selectedRow = getRowData(index);
	$.messager.confirm('提交', '是否提交所选择的申请?', function(d) {
		if (d) {
			$.ajax( {
				type : "POST",
				url : 'consumablesApp/startProcessConsumablesApp.do',
				data : {"caId":selectedRow.consumablesApp.caId,"appDept":selectedRow.consumablesApp.appDept},
			//	dataType:'JSON',
				success : function(rsp) {		    					    				
					if(rsp.status){
						parent.$.messager.show({
							title : rsp.title,
							msg : rsp.message,
							timeout : 2000 * 2
						});
						doSearch();
 						$("#dg").datagrid("load",{}); 
					}else{
						parent.$.messager.alert(rsp.title,rsp.message,'error');
					}    					
				}
    			});
		}
	});		
}

//查看流程图
function showImage(index){
	var row = getRowData(index);
	var src = "consumablesApp/showProcessImg.do?caId="+row.consumablesApp.caId;
	$('#imageDialog').dialog("open");
	$("#image").attr("src", src);
}
//查看审批意见
function checkInvestOrderOpinions(index){
	$$row = getRowData(index).consumablesApp;
	$("#optionsDialog").dialog({
		/* 动态显示Dialog的标题	*/
		width : 850,
		height : 450,					
		title : "查看审批意见",
		href : "jsp/ad/optionsList.jsp",
		onClose:function(){
			$$row=null;
		},
		modal:true,
		resizable:true,
		iconCls:'icon-add',
		closed: false
	});
}
</script>
<body>
<div class="position" style="margin-top: 5px;">您当前所在位置： 行政办公  &gt; 低值易耗品申请</div>
<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
	<form id="searchForm" action="consumablesApp/findConsumablesAppList">
	  <table>
	    <tr>
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
<div id="tb" style="padding:2px 0">
			<a id="id4ExportReports" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toSaveOrUpdatePpeScrap('');">新增</a>
		</div>
<table id="dg"></table>
<!-- 增加或修改对话框区域 -->
<div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
			<img id="image" src="" >
		</div>
<div id="optionsDialog"></div>
<div id="addWindow"></div>
</body>
</html>