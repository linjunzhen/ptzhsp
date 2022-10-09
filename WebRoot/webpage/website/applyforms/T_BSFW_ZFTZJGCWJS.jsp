<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
<eve:resources
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer,laydate"></eve:resources>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<eve:resources
	loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;

		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName : "T_BSFW_ZFTZJGCWJS"
		//});
		
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		if(EFLOW_FLOWOBJ){
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			//初始化表单字段值
    		if(eflowObj.busRecord){
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_ZFTZJGCWJS_FORM");
    		}
		}
	});

	function submitFlow(flowSubmitObj){
		 //先判断表单是否验证通过
	//	 var validateResult =$("#T_BSFW_ZFTZJGCWJS_FORM").validationEngine("validate");
		 if(eWebEditor.getHTML()==null||eWebEditor.getHTML()==""){
			 layer.alert("请填写项目投资概况");
			 return null;
		 }
		 $("#NTZXMGK").val(eWebEditor.getHTML());
		 AppUtil.submitWebSiteFlowForm('T_BSFW_ZFTZJGCWJS_FORM');
	}
</script>
</head>

<body style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>
	<%--结束编写头部文件 --%>
	<div class="eui-main">
		<jsp:include page="./formtitle.jsp" />
		<form id="T_BSFW_ZFTZJGCWJS_FORM" method="post">
			<%--===================重要的隐藏域内容=========== --%>
			<%--开始引入公共隐藏域部分 --%>
			<jsp:include page="./commonhidden.jsp" />
		    <%--结束引入公共隐藏域部分 --%>
			<%--===================重要的隐藏域内容=========== --%>
			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>基本信息</span></li>
					</ul>
				</div>
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="0" class="bstable1">
		           	    <tr>
		                   	<th><span class="bscolor1">*</span> 拟投资项目编号</th>
	                        <td>
	                           <input type="text" class="tab_text validate[required]" value="${busRecord.TZXMBH}" name="TZXMBH" />
	                        </td>
		                </tr>
						<tr>
							<th><span class="bscolor1">*</span> 拟投资项目名称</th>
							<td><input type="text" maxlength="62"
								class="tab_text validate[required]" name="NTZXMC" /><a href="javascript:AppUtil.showProjectSelector('NTZXMC');" class="projectBtnA">选 择</a></td>
							<th><span class="bscolor1">*</span> 投资人</th>
							<td><input type="text" class="tab_text validate[required]"
								name="TZR" maxlength="14" /></td>
						</tr>
						<tr>
							<th><span class="bscolor1">*</span> 项目负责人姓名</th>
							<td><input type="text" maxlength="14"
								class="tab_text validate[required]" name="XMFZRXM" /></td>
							<th><span class="bscolor1">*</span> 项目负责人电话</th>
							<td><input type="text" maxlength="14"
								class="tab_text validate[required,custom[mobilePhoneLoose]]"
								name="XMFZRDH" /></td>
						</tr>
						<tr>
							<th><span class="bscolor1">*</span> 项目联系人姓名</th>
							<td><input type="text" maxlength="14"
								class="tab_text validate[required]" name="XMLXRXM" /></td>
							<th><span class="bscolor1">*</span> 项目联系人电话</th>
							<td><input type="text" maxlength="14"
								class="tab_text validate[required,custom[mobilePhoneLoose]]"
								name="XMLXRDH" /></td>
						</tr>
					</table>
				</div>
			</div>
			<%--结束编写基本信息 --%>

			<%--开始编写拟投资项目概况 --%>
			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>拟投资项目概况</span></li>
					</ul>
				</div>
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="1" class="bstable1">
						<tr>
							<td colspan="4" align="center"><IFRAME ID="eWebEditor"
									NAME="eWebEditor"
									SRC="plug-in/ewebeditor/ewebeditor.htm?id=NTZXMGK&style=mini500"
									FRAMEBORDER="0" SCROLLING="no" WIDTH="95%" HEIGHT="400"></IFRAME>
								<input type="hidden" id="NTZXMGK" name="NTZXMGK"></td>
						</tr>
					</table>
				</div>
			</div>
			<%--结束编写拟投资项目概况 --%>
			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>所需材料</span></li>
					</ul>
				</div>
				<jsp:include page="../../bsdt/applyform/applyMaterList.jsp">
					<jsp:param value="1" name="applyType" />
				</jsp:include>
			</div>

			<jsp:include page="./applyuserinfo.jsp" />
		</form>
		
		<%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="AppUtil.tempSaveWebSiteFlowForm('T_BSFW_ZFTZJGCWJS_FORM');" name="tempSaveFn"/>
		</jsp:include>
		<%--结束引入提交DIV界面 --%>


		<%--引入阶段流程图 --%>
		<jsp:include page="tzxmjddetail.jsp">
			<jsp:param value="ZFTZ" name="TZLX" />
			<jsp:param value="4" name="DQJD" />
			<jsp:param value="2" name="DQLC" />
		</jsp:include>
		
		<%--开始引入回执界面 --%>
		<jsp:include page="./hzxx.jsp">
			<jsp:param value="${EFLOWOBJ.EFLOW_EXEID}" name="exeId" />
		</jsp:include>
		<%--结束引入回执界面 --%>
	</div>


	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
