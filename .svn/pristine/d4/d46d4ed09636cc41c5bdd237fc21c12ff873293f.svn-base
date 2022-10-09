<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
	SysUser sysUser = AppUtil.getLoginUser();
	//获取登录用户的角色CODE
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否商事登记受理员
    boolean isGly = roleCodes.contains("88888_GLY");
	//判断是否商事登记受理员
	if("__ALL".equals(resKey)||isGly){
	    request.setAttribute("isShow",true);
	}
%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("auditForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#auditForm").serialize();
				var url = $("#auditForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.auditListSearch();
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_CMS_ARTICLE_AUDIT");
		
	});
	
	function onSelectClass(o){
		if(o==1){
			$("#resultcontent_tr").show();
			$("#resultcontent").attr("disabled",false); 
		}else{
			$("#resultcontent_tr").hide();
			$("#resultcontent").attr("disabled",true); 
		}
	}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		<div region="center" style="height:100%;">
			<div class="easyui-tabs eve-tabs eui-evetabplus" fit="true" >
			<div title="信息采编"  href="moduleController.do?list">		
			</div>
			<div title="信息审核"  href="moduleController.do?auditList">		
			</div>
			<div title="信息发布"  href="moduleController.do?publishList">		
			</div>
			 <c:if test="${isShow=='true'}">  
                   <div title="信息查看"  href="moduleController.do?auditedList">		
					</div>                 
             </c:if>  
		</div>
			
	</form>

</body>

