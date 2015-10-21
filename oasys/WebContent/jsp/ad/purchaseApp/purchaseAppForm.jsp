<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <form id="purchaseAppForm" method="post" style="width: 875px;margin-left:5px;">
    <fieldset>
    <legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/>基本信息</legend>
    <input name="totalAmt" type="hidden"/><!-- 合计金额 -->
    <input name="appNo" type="hidden"/><!-- 申请编号-->
    <input name="paId" type="hidden"/><!-- 主键id -->
    <input name="appType" value="1" type="hidden"/><!-- 申请类型 -->
    <input name="appTypeOther" type="hidden"/><!-- 其他类型 -->
    <input name="applicantNo" type="hidden"/><!-- 申请人id -->
    <input name="appDept" type="hidden"/><!-- 申请人部门 -->
    <input name="appStatus" type="hidden"/><!-- 申请状态 -->
    <input name="procStatus" type="hidden"/><!-- 流程状态 -->
    <input name="appDate" type="hidden"/><!-- 申请状态 -->
    <table class="table">
       <tr>
          <th class="textStyle">申请人:</th>
          <td><input name="account" value="${account }" class="easyui-textbox" disabled="disabled"/></td>
          <th class="textStyle">计划到货时间:</th>
          <td><input name="planRecDate" class="easyui-datebox" editable="false"/></td>
       </tr>
       <tr>
          <th class="textStyle">备注:</th>
	      <td colspan="3">
	          <textarea name="remark" rows="5" cols="20" class="easyui-textbox easyui-validatebox" data-options="required:true" maxlength="100" style="width: 720px;height: 50px;"></textarea>
	      </td>
       </tr>
       <tr>
         <td colspan="6" style="text-align: right;">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePurchaseApp('purchaseAppForm')">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" style="display:none;" onclick="editForm(this)">编辑</a>
         </td>
       </tr>
    </table>
    </fieldset>
    </form>
    <div style="width: 871px;margin-left:7px;margin-top: 2px;">
       <table id="purchaseAppAttachGrid"></table>
    </div>
    
   
<script type="text/javascript">
var $sonDialog;
var $rowdata;
var url = "purchaseAppAttachController/findAllPurchaseAppAttachList.do";
$(function(){
	refreshPurchaseAppAttachGrid();
})
function refreshPurchaseAppAttachGrid(){
	if($row!=null){
		$("#purchaseAppForm").form("load",$row);
		url = "purchaseAppAttachController/findAllPurchaseAppAttachList.do?appNo="+$row.appNo;
		createPurchaseAppAttachGrid(url);
	}else{
		createPurchaseAppAttachGrid(url);
	}
};
function createPurchaseAppAttachGrid(url){
	$("#purchaseAppAttachGrid").datagrid({
		url:url,
		width: 'auto',
		height : $(this).height()-480,
		pagination:false,
		rownumbers:true,
		border:true,
		singleSelect:false,
		nowrap:true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
		remoteSort:false,//定义是否从服务器对数据进行排序。
		striped:true,//是否显示斑马线
		columns:[[
                {field : 'articleName',title : '物品名称',width : 110,align : 'center'},
	            {field : 'model',title : '型号规格',width : 90,align : 'center'},
	            {field : 'price',title : '单价',width :80,align : 'center'},
                {field : 'qty',title : '数量',width : 80,align : 'center'},
                {field : 'totalAmt',title : '合计金额(元)',width : 90,align : 'center'},
                {field : 'userName',title : '使用人',width : 100,align : 'center'},
                {field : 'depositaryName',title : '保管人',width : 100,align : 'center'},
                {field : 'purpose',title : '用途',width : 100,align : 'center'},
                {field : 'remark',title : '备注',width : 100,align : 'center'}
	   ]],
	   toolbar:[{
		   iconCls: 'icon-add',
		   text:'新增',
		   handler:toFormWindow
	   },"-",{
		   iconCls: 'icon-edit',
		   text:'编辑',
		   handler:editPurchaseAppAttach
	   },"-",{
		   iconCls: 'icon-cut',
		   text:'删除',
		   handler:delPurchaseAppAttach
	   },"-",{
		   iconCls: 'icon-reload',
		   text:'刷新',
		   handler:function(){
			   $("#purchaseAppAttachGrid").datagrid('reload');
		   }
	   }],
	   onLoadSuccess:function(data){
		   $(this).datagrid("doCellTip",{'max-width':'100px'});
	   }
	});
}
//新增物料
function toFormWindow(){
	 $sonDialog = $("<div></div>").dialog({
			title: '新增物料',    
		    width: 670,    
		    height: 350,    
		    closed: false,    
		    cache: false,    
		    href: 'jsp/ad/purchaseApp/purchaseAppAttachForm.jsp',    
		    modal: true,
		    onClose:function(){
		    	$(this).dialog('destroy');
		    },
		    buttons:[{
		    	text:'保存',
		    	iconCls:'icon-save',
				handler:savePurchaseAppAttach
		    },{
		    	text:'取消',
		    	iconCls:'icon-no',
				handler:function(){
					$sonDialog.dialog('destroy');
				}
		    }]
		});
}
/**
 * 编辑物料
 */
function editPurchaseAppAttach(){
	$rowdata = $("#purchaseAppAttachGrid").datagrid("getSelected");
	var rows = $("#purchaseAppAttachGrid").datagrid("getSelections");
	if($rowdata == null){
		$.messager.alert("提示","请选择一条记录进行修改!","warning");
		return false;
	}
	if(rows!=null && rows.length>1){
		$.messager.alert("提示","您只能选择一条记录进行修改!","warning");
		return false;
	}
	$sonDialog = $("<div></div>").dialog({
		title: '编辑物料',    
	    width: 670,    
	    height: 350,    
	    closed: false,    
	    cache: false,    
	    href: 'jsp/ad/purchaseApp/purchaseAppAttachForm.jsp',    
	    modal: true,
	    onClose:function(){
	    	$(this).dialog('destroy');
	    	$rowdata = null;
	    },
	    buttons:[{
	    	text:'保存',
	    	iconCls:'icon-save',
			handler:savePurchaseAppAttach
	    },{
	    	text:'取消',
	    	iconCls:'icon-no',
			handler:function(){
				$sonDialog.dialog('destroy');
				$rowdata = null;
			}
	    }]
	});
}
//删除物料
function delPurchaseAppAttach(){
	var rows = $("#purchaseAppAttachGrid").datagrid("getSelections");
	var ids = new Array();
	if(rows==null || rows.length<=0){
		$.messager.alert("提示","请至少选择一条记录!","warning");
		return false;
	}
	var count = 0;
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].psaId);
		count += Number(rows[i].totalAmt);
	}
	var count = Math.round(count*100)/100; 
	ids = ids.join(",");
	var appNo = $("#purchaseAppForm input[name='appNo']").val();
	$.ajax({
	   type: "POST",
	   url: "purchaseAppAttachController/delPurchaseAppAttach.do",
	   data:{"ids":ids,"appNo":appNo,"count":count},
       async: false,
	   success: function(msg){
		   if(msg.status){
			   $("#purchaseAppAttachGrid").datagrid("reload",{"appNo":appNo});
		   }
		   parent.$.messager.show({
   			title : msg.title,
   			msg : msg.message,
   			timeout : 4000 * 2
   	       });
	   }
	});
}
/**
 * 保存物料申请
 */
function savePurchaseApp(formId){
	var isValid = $("#purchaseAppForm").form('validate');
	if(!isValid){
		return;
	}
	 $.ajax({
		   url: "purchaseAppController/savePurchaseApp.do",
		   type: "POST",
		   data:$('#purchaseAppForm').serialize(),
           dataType:'JSON',
		   success: function(msg){
			   if(msg.status){
				   $("#purchaseAppForm").form('load',msg.data);
				   disableForm(formId);
			   }
			   //弹出提示信息
			   parent.$.messager.show({
	    			title : msg.title,
	    			msg : msg.message,
	    			timeout : 4000 * 2
	    	   }); 
		   }
		});
}
//禁用form表单
function disableForm(formId){
	$("#"+formId+" a[iconCls = 'icon-edit']").show();
	$("#"+formId+" a[iconCls = 'icon-ok']").hide();
	$("#"+formId+" [class^='easyui-']").each(function(i){
		if($(this).hasClass("easyui-textbox")){
			$(this).attr("disabled",true);
		}else if($(this).hasClass("easyui-datebox")){
			$(this).datebox("disable");
		}else if($(this).hasClass("easyui-combobox")){
			$(this).combobox('disable');
		}else if($(this).hasClass("easyui-numberbox")){
			$(this).numberbox('disable');
		}else if($(this).hasClass("easyui-combogrid")){
			$(this).combogrid("disable");
		}else{
		}
	});
}
//解禁form
function editForm(obj){
	var formId = $(obj).parents("form").attr("id");
	$("#"+formId+" a[iconCls^='icon-edit']").css('display','none');
	$("#"+formId+" a[iconCls^='icon-ok']").css('display','inline-block');
	$("#"+formId+" [class^='easyui-']").each(function(i){
		if($(this).hasClass("easyui-textbox")){
			$(this).attr("disabled",false);
		}else if($(this).hasClass("easyui-datebox")){
			$(this).datebox("enable");
		}else if($(this).hasClass("easyui-combobox")){
			$(this).combobox("enable");
		}else if($(this).hasClass("easyui-numberbox")){
			$(this).numberbox("enable");
		}else if($(this).hasClass("easyui-combogrid")){
			$(this).combogrid("enable");
		}else{
		}
	})
}
/**
 * 保存物料明细
 */
function savePurchaseAppAttach(){
	var isValid = $("#form_0").form('validate');
	if(!isValid){
		return;
	}
	var appNoValue = $("#purchaseAppForm input[name='appNo']").val();
    if(appNoValue == ""){
		$.messager.alert("提示","请先保存基本信息!","warning");
		return false;
	}  
	$("#form_0 input[name='appNo']").val(appNoValue);
	
	/****计算总金额 start****/
	var count = 0;
	var TotalAmt = Number($("#form_0 input[name='totalAmt']").val());
	var rows = $("#purchaseAppAttachGrid").datagrid('getRows'); 
	if(rows==null || rows.length<=0){
		count = TotalAmt;
	}else{
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			count += Number(row.totalAmt);
		}
		var psaId = $("#form_0 input[name='psaId']").val();
		if(psaId==""){
			count += TotalAmt;
		}else{
			var oldTotalAmt = $("#form_0 input[name^='oldTotalAmt']").val();
			count -= oldTotalAmt;
			count += TotalAmt;
		}
	}
	var count = Math.round(count*100)/100; 
	/****计算总金额 end****/
	
	$("#purchaseAppForm input[name='totalAmt']").val(count);
	$.ajax({
		   type: "POST",
		   url: "purchaseAppAttachController/savePurchaseAppAttach.do?count="+count,
		   data:$("#form_0").serialize(),
           async: false,
           dataType:'JSON',
		   success: function(msg){
			   if(msg.status){
				 $sonDialog.dialog("destroy");
				 $("#purchaseAppAttachGrid").datagrid("reload",{"appNo":appNoValue});
				 $rowdata = null;
			   }
			   parent.$.messager.show({
	    			title : msg.title,
	    			msg : msg.message,
	    			timeout : 4000 * 2
	    	   });
		   }
	});
}
/**
 * 计算总价，单价*数量
 */
function sumPrice(val,p,obj){
	var formId = $(obj).parents("form").attr("id");
	var p = parseFloat($("#"+formId+" input[name='"+p+"']").val());
	if(val == ""){
		return false;
	}
	if(p == "" || isNaN(p)){
		return false;
	}
	var sumNum = parseFloat(val) * p;
	sumNum = Math.round(sumNum*100)/100;
	$("#"+formId+" input[name='totalAmt']").val(sumNum);
}
</script>