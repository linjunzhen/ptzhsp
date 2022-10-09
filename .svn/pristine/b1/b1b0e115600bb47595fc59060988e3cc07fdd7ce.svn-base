<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript">
	
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		if(EFLOW_FLOWOBJ){
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			//初始化表单字段值
    		if(eflowObj.busRecord){
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_GCJSSPFYSXKZ_FORM");
    		}
		}
	});

	function submitFlow() {
		AppUtil.submitWebSiteFlowForm('T_BSFW_GCJSSPFYSXKZ_FORM');
	}

	function tempSaveFlow() {
		AppUtil.tempSaveWebSiteFlowForm('T_BSFW_GCJSSPFYSXKZ_FORM');
	}
	
</script>
</head>
<style type="text/css">
	.tab_width{
	    width: 160px;
	    font-size: 16px;
	    font-size: 16px;
	    color: #333333;
	    vertical-align: middle;
	    line-height: 20px;
	    color: #434343;
	    font-weight: 400;
	    text-align: right;
	    padding: 10px 10px 10px 0;
	}
</style>

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
        
        <form id="T_BSFW_GCJSSPFYSXKZ_FORM" method="post" >
        
        <%--===================重要的隐藏域内容=========== --%>
		    <%--开始引入公共隐藏域部分 --%>
			<jsp:include page="./commonhidden.jsp" />
		    <%--结束引入公共隐藏域部分 --%>
	    <%--===================重要的隐藏域内容=========== --%>
    	
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./tzjbxx.jsp" />
		<%--结束引入基本信息部分 --%>
		
		<%--开始编写证照信息 --%>
        <div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>证照信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
				<table cellpadding="0" cellspacing="0" class="bstable1">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>证照编号:<br>
						<span style="color: red">(格式：（XXXX）岚综实房许字第XX号)</span>
						<td>
						  <input type="text" class="tab_text validate[required,custom[spfZzbm]]" name="CERT_NUM" value="${busRecord.CERT_NUM}"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>批准时间：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="PZSJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>开发单位:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32" name="KFDW" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>开发单位编码:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32"  name="KFDWBM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>项目名称:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32" name="XMMC" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>项目座落:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32" name="XMZL" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>批准预售楼号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32" name="PZYSLH" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>建筑面积:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32"  name="JZMJ" />
						</td>
					</tr>
					
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>用途:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="300"  name="YT" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>预计竣工时间：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="YJJGSJ" />
						</td>
					</tr>
					<tr>	
						<td class="tab_width"><font class="tab_color">*</font>监管银行:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32"  name="JGYH" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>账号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="32"  name="ZH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width" >备注:</td>
						<td colspan="3">
						  <input type="text" class="tab_text w838" maxlength="300" name="BZ"/>
						</td>
					</tr>
				</table>
            </div>
        </div>
		
		<div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            <jsp:include page="./matterListZF.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_GCJSSPFYSXKZ_FORM" name="formName"/>
            </jsp:include>
        </div>
        
        <jsp:include page="./applyuserinfo.jsp" />
        </form>
        
        <%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="AppUtil.tempSaveWebSiteFlowForm('T_BSFW_GCJSSPFYSXKZ_FORM');" name="tempSaveFn"/>
		</jsp:include>
		<%--结束引入提交DIV界面 --%>
            
        <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
        <c:if test="${EFLOWOBJ.HJMC!=null}">
        <jsp:include page="xmlct.jsp" >
           <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
           <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
        </jsp:include>
        </c:if>
        <%-- 结束引入阶段流程图 并且当前节点不在开始或结束节点--%>
        
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