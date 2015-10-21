<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>
<style>
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
input, textarea {
	font-weight: normal;
}

td {
	text-align: left;
	padding: 6px;
}
th{
    text-align: right;
	padding: 6px;
	width: 81px;
}
</style>
<script type="text/javascript">
	
	$(function(){
		
		findAppNo();
		//渲染申请人列表
		badgePeopleGrid($("#appNofo").val());
		
	   //选项框
	  $('#tt').tabs({    
		    border:false    
		}); 
	   
		//初始化组织机构
		$("#deptNo1").combotree({
			width:171,
			url:"Organization/organizationList.do",
			idFiled:'id',
		 	textFiled:'name',
		 	valueFiled:'id',
		 	parentField:'pid',
		 	onLoadSuccess:function(data){
		 		//加一个全部
		 		
		 	},
		 	onChange:function(){
		 			$("#deptNo1").val($(this).combotree('getValue'));
		 			 RenderName($(this).combotree('getValue'))
		 	}
		});
		//申请人下拉列表
		//RenderName($("#deptNo1").combotree('getValue'));
	});

	
	function findAppNo(){
		 //添加申请时，首先添加主表信息
		 appNo=$("#appNofo").val();
		$.ajax({
			cache:true,
			type:'POST',
			url:"BadgeApp/findAppNo.do?appNo="+appNo+"",
			dataType:'JSON',
			async:false,
			success:function(res){
				//返回appNo
				$("#appNofo").val(res.appNo);
				//主表信息				
				$("#appPeopleForm").form("load",res);
				
				$("#registrantNo").val(res.registrantNo);
				$("#pnrId").val(res.pnrId);
				
			}
		}); 
	}
	
 	// 用户名的下拉
	function RenderName(organizeId){
		//选中部门后下拉框
		
		$("#userName").combobox({
			width:171,
			multiple:true,
			separator:",", 
			url:"BadgeApp/findOrgUserList.do?organizeId="+organizeId,
			valueField:'code',
		 	textFiled:'text',
		 	onLoadSuccess:function(data){
		 		//加一个全部
		 		
		 	},
		}); 
	}

	//申请人信息保存
	function toSaveBaseInfo(){
		if($("#userName").combogrid("getText")==""){
			$.messager.alert("提示","请填写联系人姓名!","info");
			return false;
		}
		$.ajax({
			cache:true,
			type:'POST',
			url:"BadgeApp/saveBadgeAttache.do",
			data:$('#appPeopleForm').serialize(),
			async:false,
			dataType:'JSON',
			success:function(res){
				 if(res.status){
					badgePeopleGrid($("#appNofo").val());
					$("#appPeopleForm").form('clear');//清空表单
					//$("#appPeoNo").val($("#appNofo").val());
					findAppNo();
					parent.$.messager.show({
						title : '提示',
						msg : '恭喜你，保存成功!',
						timeout : 4000 * 2
					});
				}else{
					$.messager.alert("提示", '出错了，保存失败!',"error")
				} 
			}
		});
	} 

 	 //申请人列表
    function badgePeopleGrid(appNo){
 		 var deptNo=$("#deptNo1").val();
 		
    	$("#appUserView").datagrid({
    		url : "BadgeApp/findBadgeAttList.do",
    		queryParams:{
    			"appNo":appNo,
    			"deptNo":deptNo
    		},		
    		width : 875,
    		height : 300,
    		pagination:true,
    		rownumbers:true,
    		border:true,
    		singleSelect:false,
    		nowrap:true,
    		columns : [ [
						{field : 'appNo',title : '申请编号',width:110,align : 'center'},
    		            {field : 'fullName',title : '部门',width:100,align : 'center'},
						{field : 'name',title : '申请人',width:100,align : 'center',
							formatter : function(value, row, index) {
								return "<a href=\"javascript:void(0)\" onclick=\"showView("+ index + ");\">" + value + "</a>";
							}
						},
						{field : 'namePinyin',title : '英文姓名',width : 118,sortable : true,align : 'center'},
						{field : 'positionName',title : '职位',width:140,align : 'center'},
						{field : 'remark',title : '备注',width : 276,align : 'center'}
    		]],
    		onLoadSuccess:function(data){
				   var rows = data.rows;
		          if(rows.length>0){
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
				        	  // $(this).datagrid("autoMergeCells",['appNo']);
				        	   $(this).datagrid('mergeCells',{
				                   index: mergeMap[i].index,
				                   field: 'appNo',
				                   rowspan: mergeMap[i].rowspan
				            	 });
				        	   //合并部门
				           		$(this).datagrid("autoMergeCells",['fullName']);
				           }
				           $(this).datagrid("doCellTip",{'max-width':'100px'});
		          }
			  }, 
    		toolbar: [{
    			iconCls: 'icon-cancel',
    			text:'删除',
    			handler:toDelete
    		}] 
    	});
    	
    }
 	 
    //上传附件
    $("#upploadAttachment").click(function(){
		fileUploadsDlg($("#appNofo").val(),$("#pnrId").val());
	});
	//查看附件
	$("#checkAttachment").click(function(){
		checkAttachementDetail($("#appNofo").val(),$("#registrantNo").val(),'',$("#pnrId").val());
	});
   
     //申请人删除
    function toDelete(){
    	var selected = $('#appUserView').datagrid('getSelections');
    	if (selected.length <= 0) {
    		$.messager.alert("提示", "请至少选择一条记录执行删除!", "warning");
    		return;
    	}
    	var ids = new Array();
    	for(var i=0,len=selected.length; i<len; i++){
    		ids.push(selected[i].pnrId);
    	}
    	ids = ids.join(",");
    	$.messager.confirm('删除', '执行删除后，数据将不可恢复,是否执行?', function(d) {
    		if (d) {
    			$.ajax( {
    				type : "POST",
    				url : 'BadgeApp/deleteBadgeAttList.do',
    				data : {
    					"ids":ids
    				},
    				dataType:'JSON',
    				success : function(iJson) {
    					if(iJson.status){
    						//刷新列表
    						badgePeopleGrid($("#appNofo").val(),$("#pnrId").val());
    						//$("#linkPeopleGrid").datagrid("reload",{loanerId:investorId});
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
    
    /*  //保存并继续添加
     function toSaveOrAddInfo(){
    	 //清空表格数据
    	 $("#appPeopleForm").form('clear');//清空表单
    	 badgePeopleGrid();
    	 $("#appPeoNo").val($("#appNofo").val());
    	 
     } */
     
     //重置按钮
     function resetButton(){
    	 $('#appPeopleForm').form('reset');
    	 findAppNo();
     }
     
     //放弃功能
     function giveUpApp(){
 			$.messager.confirm('提示','是否确定放弃该申请？',function(flag) {
 				if (flag) {
 					$.ajax({
 						url : "BadgeApp/deleteBadgeApp.do",
 						data : {"appNo":$("#appNofo").val()} ,
 						dataType : 'JSON',
 						success : function(rsp) {
 							if (rsp.status) {
 								//删除成功后刷新列表
 								$("#applyView").dialog('close');
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
     
</script>
<div id="tt">
	<div title="申请人信息">
	  <div class="well well-small" style="margin:5px;">
	      <input id="appNofo" name="appNo" type="hidden"/><!-- 申请编号 -->
	      <input id="pnrId" name="pnrId" type="hidden"/><!-- 申请id -->
	      <input id="registrantNo" name="registrantNo" type="hidden"/><!-- 登记人id -->
	     	<form id="appPeopleForm">
	         <input id="appPeoNo" name="appNo" type="hidden"/><!-- 申请编号 -->
	         
	         <table class="table">
	         	<tr>
           		<th>登记人姓名:</th>
					<td style="width:355px;"><input id="regName" name="regName" style="width: 165px" readonly="readonly" type="text" />
				</td>
				
				<th>登记日期:</th>
				<td>
				  <input id="regDatetime" name="regDatetime" type="text" class="easyui-textbox easyui-datebox" value="2015-10-13" readonly="readonly" data-options="editable:false"/>  
				</td>
           	    </tr>
				<tr>
						<th>部门:</th>
						<td ><input name="deptNo" id="deptNo1" type="text" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>申请人:</th>
						<td><input name="name" id="userName" type="text" class="easyui-textbox easyui-validatebox easyui-combobox" style="width: 170px"/>&nbsp;</td>
				</tr>
				 <tr>
					<th>备注:</th>
					<td colspan="3">
					  <textarea id="remark" name="remark" class="easyui-textbox easyui-validatebox" style="width: 700px; height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
					</td>
				</tr>
				<tr>
				   <td colspan="6" style="text-align: right;">
				   	  <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="toSaveBaseInfo();">保存</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetButton();">重置</a>
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-no" onclick="giveUpApp();">放弃该申请</a>
				      
				   </td>
				</tr>
	         </table>
	       </form>
	  </div>
	  <!-- 附件上传 -->
	  <div class="well well-small" style="margin:5px;">
	  	<table class="table" cellpadding="5px;">
			<tr>
				<td colspan="2">
					<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
					<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>		
				</td>
			</tr>
		</table>
	  </div>
	  
	   <div style="margin: 5px;">
	        <table id="appUserView"></table>
	   </div>
	   <!--  <div  style="float:right;">
		    <a href="javascript:void(0)" id="saveOrAdd" class="easyui-linkbutton" iconCls="icon-add" onclick="toSaveOrAddInfo();">保存并添加</a>
  		</div> -->

	 	
</div>
