<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>WI-FI设备管理系统</title>
	<link rel="stylesheet" type="text/css" href="media/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="media/css/icon.css">
	<link rel="stylesheet" type="text/css" href="media/css/demo.css">
	<script type="text/javascript" src="media/js/jquery.min.js"></script>
	<script type="text/javascript" src="media/js/jquery.easyui.min.js"></script>
	<style type="text/css">
		a:link,a:visited{color:blue;text-decoration:none;}
	</style>
</head>
<body class="easyui-layout">
<div id="screen" style="display: none; z-index: 1000; top: 0px; left: 0px; position: fixed; height: 100%; width: 100%; opacity: 0.8; background-color: rgb(0, 0, 0);">
	<dl class="lightbox" id="idBox" style="position: absolute; left: 40%; top: 30%;width: 280px;height: 180px;   background-color: #fff;">
	  <dt id="idBoxHead" style="text-align: center; font-size: 20px;margin-top: 20px;">添加成功</dt>
	  <dd style="margin-top: 50px;margin-left: 120px;">
	    <input type="button" id="idBoxCancel" value=" 确定 " name="" >
	  </dd>
	</dl>
</div><!-- ;background:#87CEFA -->
	<div data-options="region:'north',border:false" scrolling="no" style="height:80px;padding:10px;font-size: 25px;background-image: url(media/css/icons/yun.PNG);background-repeat: no-repeat;text-align: center;s">龙在天下
		<span style="float: right;margin-top: 35px;margin-right: 10px;background-repeat: inherit;">欢迎登陆:${realname} &nbsp;&nbsp;<a href="#" onclick="showchangepassword()">修改密码</a>　　<a href="index.html">退出</a></span>
	</div>
	<div data-options="region:'west',split:true,title:'菜单栏'" style="width:14%;padding:10px;background-color:#F8F8FF;">
		<iframe id="menus" name="menus" src="menu.jhtml?gradetype=${gradetype }&id=${id}" width="150px" height="100%" frameborder="0" scrolling="no" ></iframe>
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'通知'" style="width:100px;padding:10px;">通知</div>
	<!-- <div data-options="region:'south',border:false" style="height:5px;background:#A9FACD;padding:10px;"></div> -->
	<div data-options="region:'center'" scrolling="no" style="overflow:hidden;">
		<iframe id="countents" name="contents" src="order/add" width="100%" height="100%" frameborder="0" style="overflow:hide;"></iframe>
	</div>
	 <div id="dlg" class="easyui-dialog" modal="true" title="修改密码" style="width:270px;height:170px;padding:10px 20px;text-align: center;"
            closed="true" >
        <%-- <form id="fms" action="user/changepassword" method="post" novalidate>
            <div class="fitem">
                <input name="username" value="${username }" style="display: none;" >
            </div>
            <div class="fitem">
                <label>原密码:</label>
                 <input id="password" name="password"  class="easyui-textbox" required="true" type="password" value=""/>
            </div>
            <div class="fitem">
                <label>修改密码:</label>
               <!--  <input name="newpassword" class="easyui-textbox" required="true" validType="password"> -->
                <input id="password" name="newpassword" class="easyui-textbox" required="true" type="password" value=""/>
            </div>
            <div class="fitem">
                <label>确认密码:</label>
                <!-- <input name="renewpassword" class="easyui-textbox" required="true" validType="password"> -->
                <input type="password" name="repassword" id="repassword" required="true" class="easyui-textbox" />
            </div>
            <button onclick="submit()">提交</button>
        </form> --%>
        	<table>
        		<input name="username" id="username" value="${username }"  style="display: none;" >
        		<tr><td>原密码　</td><td><input id="password" type="password" id="password" name="password" class="easyui-textbox"   required="true" value=""/></td></tr>
        		<tr><td>新密码　</td><td><input name="newpassword" id="newpassword" required="true" class="easyui-textbox"  type="password" value=""/></td></tr>
        		<tr><td>确认密码</td><td><input id="renewpassword" type="password" name="repassword" class="easyui-textbox"  required="true"  /></td></tr>
        		<tr><td></td><td><button onclick="changepassword()">提交</button></td></tr>
        	</table>
                <%-- <input name="username" id="username" value="${username }"  style="display: none;" >
                              原密码　:<input id="password" type="password" id="password" name="password" class="easyui-textbox"   required="true" value=""/><br />
               	 新密码　:<input name="newpassword" id="newpassword" required="true" class="easyui-textbox"  type="password" value=""/><br />
               	 确认密码:<input id="renewpassword" type="password" name="repassword" class="easyui-textbox"  required="true"  /><br />
            	<button onclick="changepassword()">提交</button> --%>
    </div>
</body>
<script type="text/javascript">
	$("#orders").click(function(){
		$("#order").css("display","block");
	});
	$(function(){
		$("#idBoxCancel").click(function(){
			$("#screen").css("display","none");
		});
	});
	function showchangepassword(){
		
		$('#dlg').dialog('open');
	}
	function changepassword(){
		/* var urls = "user/changepassword";
		var username = $("#username").val();
		var password = $("#password").val();
		var newpassword = $("#newpassword").val();
		alert('1111');
		jQuery.ajax({
			url:urls,
			data:{username:username,password:password,newpassword:newpassword},
			type:"POST",
			error:function(){
				alert("修改失败");
				//return false;
			},
			success:function(data){
				alert(data);
				if(data=="1"){
					alert("修改成功");
					$('#dlg').dialog('close');
					//return false;
				}else{
					alert("修改失败");
				}
			}
		});  */
		var username = $("#username").val();
		var password = $("#password").val();
		var newpassword = $("#newpassword").val();
		var renewpassword = $("#renewpassword").val();
		
		if(password=="" || newpassword=="" || renewpassword!=newpassword){
			//alert(password+":"+newpassword+":"+renewpassword);
			alert("填写有误!");
			return false;
		}
		jQuery.ajax({
			cache: true,
	        type: "POST",
	        url:"user/changepassword",
	        data:{username:username,password:password,newpassword:newpassword},
	        async: false,
	        error: function(request) {
	            alert("修改失败");
	        },
	        success: function(data) {
	        	if(data=="1"){
	        		//setTimeout(function(){window.location="index.html";},1000);
	        		$('#dlg').dialog('close');
	        		alert("修改成功,请重新登陆!");
	        		window.location="index.html";
	        	}else{
	        		alert("修改失败!");
	        	}
	            //$("#commonLayout_appcreshi").parent().html(data);
	        }			
		});
		/* var datas = $('#ff').serialize();
		$.post("user/changepassword",datas,function(data,status){
			alert(data);
		}); */
	}
	/* $(function(){
		document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==27){ // 按 Esc 
                //要做的事情
              }
            if(e && e.keyCode==113){ // 按 F2 
                 //要做的事情
               }            
             if(e && e.keyCode==13){ // enter 键
            	 changepassword();
            }
        }; 
		
	}); */
</script>
</html>