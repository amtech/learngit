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
	
	   
		//初始化组织机构
		$("#editDeptNo").combotree({
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
		 			$("#editDeptNo").val($(this).combotree('getValue'));
		 			 RenderName($(this).combotree('getValue'))
		 	}
		}); 
		//申请人下拉列表
		RenderName($("#editDeptNo").combotree('getValue'));
	
	});

 	// 用户名的下拉
	function RenderName(organizeId){
		//选中部门后下拉框
		$("#editUserName").combobox({
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
		if($("#editUserName").combogrid("getText")==""){
			$.messager.alert("提示","请填写联系人姓名!","info");
			return false;
		}
		$.ajax({
			cache:true,
			type:'POST',
			url:"BadgeApp/saveBadgeAttache.do",
			data:$('#editPeoAppForm').serialize(),
			async:false,
			dataType:'JSON',
			success:function(res){
				 if(res.status){
					 
					$("#editPeoAppForm").form('clear');//清空表单
					badgePeopleGrid($("#editAppNofo").val());
					
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
 		var deptNo=$("#editDeptNo").val();
 		$("#editAppNofo").val(appNo);
 		//获取当前登记人信息
 		findAppNo();
 		 
 		 //加载申请人
    	$("#editAppUserView").datagrid({
    		url : "BadgeApp/findBadgeAttList.do",
    		queryParams:{
    			"appNo":appNo,
    			"deptNo":deptNo
    		},		
    		width : 896,
    		height : 400,
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
						{field : 'remark',title : '备注',width : 297,align : 'center'}
    		]],
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
		        	  // $(this).datagrid("autoMergeCells",['appNo']);
		        	   $(this).datagrid('mergeCells',{
		                   index: mergeMap[i].index,
		                   field: 'appNo',
		                   rowspan: mergeMap[i].rowspan
		            	 });
		        	   //合并部门
		           		//$(this).datagrid("autoMergeCells",['fullName']);
		           }
		           $(this).datagrid("doCellTip",{'max-width':'100px'});
			  }, 
    		toolbar: [{
    			iconCls: 'icon-cancel',
    			text:'删除',
    			handler:toDelete
    		}] 
    	});
    	
    }
  
   
     //申请人删除
    function toDelete(){
    	var selected = $('#editAppUserView').datagrid('getSelections');
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
    						badgePeopleGrid($("#editAppNofo").val());
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
     
    //上传附件
    $("#upploadAttachment").click(function(){
		fileUploadsDlg($("#editAppNofo").val(),$("#editPnrId").val());
	});
	//查看附件
	$("#checkAttachment").click(function(){
		checkAttachementDetail($("#editAppNofo").val(),$("#editRegistrantNo").val(),'',$("#editPnrId").val());
	});
    
    /*  //保存并继续添加
     function toSaveOrAddInfo(){
    	 //清空表格数据
    	 $("#editPeoAppForm").form('clear');//清空表单
    	 badgePeopleGrid();
    	 $("#editAppPeoNo").val($("#editAppNofo").val());
    	 
     } */
     //重置按钮
     function resetButton(){
    	 $('#editPeoAppForm').form('reset');
    	 findAppNo();
     }
     
     //获取当前登记人信息
     function findAppNo(){
		 //添加申请时，首先添加主表信息
		var appNo=$("#editAppNofo").val();
		$.ajax({
	 			cache:true,
				type:'POST',
				url:"BadgeApp/findBadgeAppList.do",
				data:{"appNo":appNo},
				async:false,
				dataType:'JSON',
				success:function(res){
					//主表信息				
					$("#editPeoAppForm").form("load",res);
					$("#editRegistrantNo").val(res.registrantNo);
					$("#editPnrId").val(res.pnrId);
				}
	 		 });
	}
     
</script>
<div id="tt">
	<div title="申请人信息">
	  <div class="well well-small" style="margin:5px;">
	      <input id="editAppNofo" name="appNo" type="hidden"/><!-- 申请编号 -->
	      <input id="editPnrId" name="pnrId" type="hidden"/><!-- 申请id -->
	       <input id="editRegistrantNo" name="registrantNo" type="hidden"/><!-- 登记人id -->
	     	
	     	<form id="editPeoAppForm">
	         
	         <input id="editAppPeoNo" name="appNo" type="hidden"/><!-- 申请编号 -->
	         
	         <table class="table">
	         	<tr>
           			<th>登记人姓名:</th>
					<td>
						<input id="editRegName"  name="regName" type="text" style="width: 165px" class="easyui-textbox"/>
					</td>
					<th>登记日期:</th>
					<td>
						<input id="editRegDatetime" name="regDatetime"  type="text" class="easyui-textbox easyui-datebox" data-options="editable:false"></input>  
					</td>
           		</tr>
				<tr>
						<th style="width: 81px">部门:</th>
						<td ><input name="deptNo" id="editDeptNo" type="text" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>申请人:</th>
						<td><input name="name" id="editUserName" type="text" class="easyui-textbox easyui-validatebox" style="width: 170px"/>&nbsp;</td>
				</tr>
				 <tr>
					<th>备注:</th>
					<td colspan="3">
					  <textarea id="editRemark" name="remark" class="easyui-textbox easyui-validatebox" style="width: 750px; height: 80px;" data-options="validType:'maxLength[200]'"></textarea>
					</td>
				</tr>
				<tr>
				   <td colspan="6" style="text-align: right;">
				   	  <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="toSaveBaseInfo();">保存</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetButton();">重置</a>
				   </td>
				</tr>
	         </table>
	       </form>
	  </div>
	  <!-- 上传附件 -->
	  <div class="well well-small" style="margin:5px;">
	  	<table class="table" cellpadding="5px;">
			<tr>
				<td colspan="2">
					<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>	
					<a id="checkAttachment" target="view_window" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>		
				</td>
			</tr>
		</table>
	  </div>
	  	<!-- 申请人列表 -->
	   <div style="margin: 5px;">
	        <table id="editAppUserView"></table>
	   </div>
	   <!--  <div  style="float:right;">
		    <a href="javascript:void(0)" id="saveOrAdd" class="easyui-linkbutton" iconCls="icon-add" onclick="toSaveOrAddInfo();">保存并添加</a>
		    <a href="javascript:void(0)" id="saveOrClose" class="easyui-linkbutton" iconCls="icon-cancel" onclick="toSaveOrCloseInfo();">保存并关闭</a>
  		</div> -->

	 	
	</div>
</div>
