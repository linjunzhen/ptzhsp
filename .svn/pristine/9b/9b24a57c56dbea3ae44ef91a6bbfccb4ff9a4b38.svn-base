<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">
   var islogin = 0;
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="USER_ID" value="${sysUser.USER_ID}">
		<input type="hidden" name="DEPART_ID" value="${sysUser.DEPART_ID}">
		<input type="hidden" name="USER_NAME" value="${sysUser.USER_NAME}">
		<input type="hidden" name="SYS_CODE" value="${sysCode}">
		<!--==========隐藏域部分结束 ===========-->
		<div style="display:none">
		    <form action="http://zzhy.ptxzfwzx.gov.cn/xzsp/default.aspx" id="ssFrom">
		    	<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUJMTc5Nzg4NjM2ZGSy4NJN92h1FiFLKLgaEkmJEOU1rg==" />
		    	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWBAKNouPdBQLF1bSzCQLm8eKkDwLSwtXkAha+BMnH26KJ/eSY6vWqAX9/qj7N" />
		    	<input name="TxtUserName" type="text" value="${otherUser.OTHER_USERNAME }"/>
				<input name="TxtUserPwd" type="text" value="${otherUser.OTHER_PASSWORD }"/>
		   	  	<input type="submit" name="ImageButton2" value="登 录" id="ImageButton2"/>
			</form>
		</div>
		<div id="_loginTo"  <c:if test='${hasOtherUser=="0"}'>style="display:none"</c:if>>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			
			<tr style="height:29px;">
				<td colspan="2" style="font-weight:bold;text-align:center;font-size:12pt;">登录跳转</td>
			</tr>
			<tr>
				<td colspan="2">
				  <div style="text-align:center;font-size:13pt;margin-top:25px;">正在为您登录商事系统，请稍等 . . . . . .</div>
				  <div style="text-align:center;font-size:11pt;margin-top:15px;color:green"><span>如果您上次系统自动跳转登录失败，请<a href="javascript:void(0);" 
				  	onclick="reBulid();" style="color:blue;">点击此处</a>重新进行帐号绑定</span>
				  	<span>或<a href="http://zzhy.ptxzfwzx.gov.cn/xzsp/" style="color:blue">直接进入登录页</a>。</span>
				  </div>
				  <div style="text-align:center;font-size:13pt;margin-top:15px;display:online-block;"></div>
				</td>
			</tr>
		</table>
		</div>
		<div id="_loginInput"  <c:if test='${hasOtherUser=="1"}'>style="display:none"</c:if>>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" style="font-weight:bold;text-align:center;font-size:12pt;">输入登录商事系统的用户信息</td>
			</tr>
			<tr >
				<td  colspan="2" style="font-weight:bold;text-align:center;"><span style="width:100px;margin-top:15px;">帐号：</span><input type="text" maxlength="32" class="eve-input validate[required]" style="width:150px;margin: 10px 0 10px 0;" name="OTHER_USERNAME"/></td>
			</tr>
			<tr style="margin-top:15px;">
				<td  colspan="2" style="font-weight:bold;text-align:center;"><span style="width:100px;">密码：</span><input type="password" maxlength="32"  style="width:150px;margin: 10px 0 10px 0;" class="eve-input" name="OTHER_PASSWORD" /></td>
			</tr>
			<tr>
				<td colspan="2"  style="font-weight:bold;text-align:center;"> <input type="button" onclick="buildUser();" value="确认"/> <input type="button" onclick="resetUserInfo();" value="重置"/></td>
			</tr>
		</table>
		</div>
</body>
<script>
	function trunToSSXT(){
		var obj =document.getElementById("ImageButton2")
		if(obj&&islogin==0){obj.click();}
	}
	function reBulid(){
		islogin = 1;
		document.getElementById("_loginInput").style.display="block";
		document.getElementById("_loginTo").style.display="none";
	}
	<c:if test='${hasOtherUser=="1"}'>
		setTimeout("trunToSSXT()",3000);
	</c:if>
	function resetUserInfo(){
		$("input[name='OTHER_USERNAME']").val("");
		$("input[name='OTHER_PASSWORD']").val("");
	}
	function buildUser(){
		if($("input[name='OTHER_USERNAME']").val().replace(/\s/g, "")==""){
			parent.art.dialog({
				content: "帐号不能为空",
				icon:"error",
				ok: true
			});
			return ;
		}

		if($("input[name='OTHER_PASSWORD']").val()==""){
			parent.art.dialog({
				content: "密码不能为空",
				icon:"error",
				ok: true
			});
			return ;
		}
		 var layload = layer.load('正在帮您登录商事系统，请稍候......');
		 $.post("otherSystemController.do?buildUser",{
				OTHER_USERNAME:AppUtil.encodeBase64($("input[name='OTHER_USERNAME']").val()),
				OTHER_PASSWORD:AppUtil.encodeBase64($("input[name='OTHER_PASSWORD']").val()),
				SYS_CODE:$("input[name='SYS_CODE']").val()
			}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success){
				$("input[name='TxtUserName']").val($("input[name='OTHER_USERNAME']").val());
				$("input[name='TxtUserPwd']").val($("input[name='OTHER_PASSWORD']").val());
				islogin = 0;
				trunToSSXT();
			}else{
				layer.close(layload);
				parent.art.dialog({
					content: "操作失败【"+resultJson.msg+"】，请刷新页面重试或联系系统管理员！",
					icon:"error",
					ok: true
				});
			}
		});
	}
</script>
