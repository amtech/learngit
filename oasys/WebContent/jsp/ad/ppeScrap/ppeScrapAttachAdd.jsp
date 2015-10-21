<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">		
	/** 清空新添加的理财产品的的数据**/
	
	$(function(){
		//上传附件
		$("#upploadAttachment").click(function(){
			fileUploadsDlg($('#ppeScrapAttachForm #appNo').val(),$('#ppeScrapAttachForm #psaId').val());
		});
		//查看附件
		$("#checkAttachment").click(function(){
			checkAttachementDetail($('#ppeScrapAttachForm #appNo').val(),'','',$('#ppeScrapAttachForm #psaId').val());
		});
		
	});
	
	function clearForm(){
		$("#ppeScrapAttachForm").form("clear");		
	}	
	/** 增加或者修改理财产品的数据**/
	function savePpeScrapAttach(){
		//校验理财产品输入的信息
   		var isValid = $("#ppeScrapAttachForm").form('validate');
		var objData = $('#ppeScrapAttachForm').serialize();
		objData = objData.replace("&appNo=&","&appNo="+$('#appNo').val()+"&");
    	if(!isValid){
    		return false;
    	}  	
    	$.ajax({
    		type: "POST",
    		url:"ppeScrapAppAttachController/addPPEScrapAppAttach.do",
    		data:objData,
    		async: false,//默认true设置下，所有请求均为异步请求
    		cache: true,
    	    success: function(iJson) {    	    	
     	    	if(iJson.status){
    	    		$("#ppeScrapAttachGrid").datagrid("load",{appNo:$('#appNo').val()});//刷新表格  
    	    	}
    	    	parent.$.messager.show({
    				title : '提示',
    				msg : iJson.message,
    				timeout : 4000 * 2
    			}); 
    	    }
    	});		
	}
	
	function closeWindow(){
		$('#formWindow').dialog("close"); 
	}
	
	function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	} 

	
	
</script>

	
	<div style="margin-left: 5px;margin-top: 5px;">
	   <form id="ppeScrapAttachForm" method="post">
	   <input type="hidden" id="psaId" name="psaId" value="${ppeAttach.psaId}"/>
	   <input type="hidden" id="appNo" name="appNo" value="${ppeAttach.appNo}"/>
	   
			<table class="table"  width="95%">
				<tr>
					<th>资产编号:</th>
					<td colspan="5">
						<input id="ppeNo" name="ppeNo" value="${ppeAttach.ppeNo}" class="easyui-textbox easyui-validatebox" data-options="validType:'intOrFloat', required:true"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						(*请正确输入您的资产编号)
					</td>
				</tr>
				<tr>
					<th>资产名称:</th>
					<td>
						<input id="ppeName" name="ppeName" value="${ppeAttach.ppeName}"  type="text" class="easyui-textbox easyui-validatebox"   data-options="validType:'length[0,50]'"/>
					</td>
					
					<th>品牌规格:</th>						
					<td colspan="3">
						<input id="ppeModel" name="ppeModel" value="${ppeAttach.ppeModel}" type="text" class="easyui-textbox easyui-validatebox" data-options="validType:'length[0,50]'"/>
					</td>
				</tr>
				<tr>
					<th>购买时间:</th>
					<td>
						<input name="buyDateStr" id="buyDateStr" value="${ppeAttach.buyDate}" class="easyui-datebox"  editable="false" style="width:174px;" data-options="validType:'Date',required:true"/>
					</td>
					<th>使用年限:</th>
					<td colspan="3">
						<input id="usedYear" name="usedYear" value="${ppeAttach.usedYear}" class="input easyui-numberbox" min="0.01"   max="100000000"  precision="1"  type="text" />
					</td>
				</tr>
				<tr>
					<th>资产原值:</th>
					<td><input id="ppeGross" name="ppeGross" value="${ppeAttach.ppeGross}"  class="input easyui-numberbox" min="0.01"   max="100000000"  precision="2"  type="text"  />&nbsp;￥</td>
					<th>资产净值:</th>
					<td><input id="ppeNet" name="ppeNet" value="${ppeAttach.ppeNet}"  class="input easyui-numberbox" min="0.01"   max="100000000"  precision="2"  type="text" />&nbsp;￥</td>				
					<th>资产残值:</th>
					<td><input id="ppeSalvageVal" name="ppeSalvageVal"  value="${ppeAttach.ppeSalvageVal}"  class="input easyui-numberbox" min="0.01"   max="100000000"  precision="2"  type="text" />&nbsp;￥</td>										
				</tr>
				<tr>
					<th colspan="2">*资产报废原因(资产现状及解决方案):</th>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td colspan="5"><textarea name="scrapReson"  id="scrapReson"  class="easyui-textbox easyui-validatebox" style="width: 600px; height: 90px;" data-options="required:true,validType:'length[0,200]'">${ppeAttach.scrapReson }</textarea></td>
				</tr>
				<tr>
					<th colspan="2">*资产报废图片上传:</th>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td colspan="5">
								<a id="upploadAttachment" href="javascript:void(0);" class="easyui-linkbutton" >上传附件</a>&nbsp;&nbsp;&nbsp;&nbsp;	
								<a id="checkAttachment" href="javascript:void(0);" class="easyui-linkbutton">查看附件</a>	
					</td>
				</tr>
				<tr>
					<td colspan="4"></td>
				   <td colspan="2" align="right">
				      <a href="javascript:void(0)" id="save" class="easyui-linkbutton" iconCls="icon-save" onclick="savePpeScrapAttach();">保存</a>
				      <a href="javascript:void(0)" id="rset" class="easyui-linkbutton" onclick="closeWindow();">关闭</a>
				   </td>
				</tr>					
			</table>
		</form>
	</div>				
