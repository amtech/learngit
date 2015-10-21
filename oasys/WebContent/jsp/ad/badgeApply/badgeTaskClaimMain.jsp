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
    <title>代办任务</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
	
		/**
		 * 根据索引获取对象名称
		 * @param target Grid对象
		 * @param index 索引
		 * @returns 索引的对应行的信息
		 */
		//根据index获取该行
		function getRowData(index){
			if (!$.isNumeric(index) || index < 0) {
				return undefined;
			}
			var rows = $("#dg").datagrid("getRows");
			return rows[index];
		}	
	
			var $grid;
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
					
				
				$grid =$("#dg").datagrid({
							url : 'BadgeTask/findNotTaskClaimList.do',
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
													return "<a href=\"javascript:void(0)\" target=\"view_window\" onclick=\"lookAttachment("+ index + ");\">查看附件</a>";
												}	
											},
											{field : 'id',    title : '操作',    width : $(this).width * 0.1, align:'center',
								        	 	formatter: function(value,row,index){
								        		   	var result = ""; 
													if (row.assistant == null || row.assistant == "") {
														result += "<a href='javascript:void(0);' onclick='claimTask("+row.taskId+");'>签收任务</a>　　";
													}else{
														result += "<a href='javascript:void(0);' onclick='handleTaskDialog("+index+");'>受理任务</a>　　";
													}
													result += "<a href='javascript:void(0);' onclick='lookBadgeAppCommentDialog("+index+");'>查看审批意见</a>　　"
													result += "<a href='javascript:void(0);'  onclick='showImage("+ index + ");'>查看当前流程图</a>"
													
													return result; 
							      				}
								            } 
											] ],
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
			function lookAttachment(index){
				//主页面查看时，明细为申请id
				var row = this.getRowData(index);
				checkAttachementDetail(row.appNo,row.registrantNo,2,row.pnrId);
			};
		
			//办理任务
		function handleTaskDialog(index){
			$row  = getRowData(index);
			$.ajax({
				type:"POST",
				url:"workflowAction/findTaskFormKeyByTaskId.do",
				data:{"taskId":$row.taskId},
				success:function(jspName){
					 $("#mgrTaskView").dialog({
							title:'办理任务',
							width:1000,
							height:650,
							modal:true,
							href:"jsp/ad/badgeApply/userTask/"+jspName,
							onLoad:function(){
								var badgeTaskFrom=$("#badgeTaskForm");
								badgeTaskFrom.form("load",$row);
							},
							onClose:function(){
								$("#dg").datagrid('reload')
							} 
					 }); 
				}
			});
		}
			
		// 查看流程图片
		function  showImage(index) {
			var row = getRowData(index);
			var src = "BadgeTask/showProcessImg.do?taskId="+ row.taskId;
			$('#imageDialog').dialog("open");
			$("#image").attr("src", src);
		}
	    
		// 领取任务
		function claimTask(taskId) {
			//var row = getRowData($grid,index);
			$.ajax({
				url : "BadgeTask/taskUserClaim.do",
				data : {"taskId" : taskId},
				success : function(rsp) {
					if(rsp.status){
						parent.$.messager.show({
							title : rsp.title,
							msg : rsp.message,
							timeout : 1000 * 2
						});
					}else{
						parent.$.messager.alert(rsp.title,rsp.message,'warning');
					}
					$grid.datagrid('reload');
				}
			});
		}
		
		 // 查看流程批注
		 var $$row = "";
		function lookBadgeAppCommentDialog(index) {
			var rows = $("#dg").datagrid("getRows");
			 $$row = rows[index];//获取本条数据
			$("#optionsDialog").dialog('open').dialog('refresh');
		} 
		
		//执行查询
		function subCustomerRepaymentForm(){
			$("#dg").datagrid("load",{
				procStatus:$("#procStatus").combobox("getValue"),
				appNo:$("#appNoMain").val(),
				appDateS:$('#appDateS').datebox('getValue'),
				appDateE:$('#appDateE').datebox('getValue')
			});  
		} 
		
</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<div class="position" style="margin-top: 5px;">您当前所在位置：业务管理-->工牌申请-->代办任务</div>
		</div>
		<!-- 高级查询栏区域 -->
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
		<table id="dg"></table>
	    <div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
			<img id="image" src="" >
		</div>
		 <div id="imageDialog"  class="easyui-dialog" title="流程图片" data-options="border:false,closed:true,fit:true">
			<img id="image" src="" >
		</div>
		<!-- 跳转办理任务 -->
		<div id="mgrTaskView"></div>
  	</div>	
  	<div id="optionsDialog" class="easyui-dialog" title="历史审批意见" style="width:900px;height:500px;" closed="true" data-options="href:'jsp/ad/optionsList.jsp',resizable:true,modal:true">
  	
  </body>
</html>
