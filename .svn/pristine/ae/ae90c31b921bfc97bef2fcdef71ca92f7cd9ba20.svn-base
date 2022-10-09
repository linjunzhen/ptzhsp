<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>流程明细-${EFLOW_FLOWEXE.SUBJECT}</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
<link type="text/css" href="plug-in/eveflowdesign-1.0/css/design.css"
	rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript"
	src="plug-in/easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<!-- 开始引入artdialog -->
<link type="text/css" href="plug-in/artdialog-4.1.7/skins/default.css"
	rel="stylesheet">
<script type="text/javascript"
	src="plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript"
	src="plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<!-- 结束引入artdialog -->
<script type="text/javascript"
	src="plug-in/layer-1.8.5/layer.min.js"></script>
<script type="text/javascript"
	src="plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	function goAddSPMatter(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		var curnode=eflowObj.EFLOW_CURUSEROPERNODENAME;
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_APPLY_STATUS = "2";
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		
		if(flowSubmitObj!=null){
			flowSubmitObj.EFLOW_CURUSEROPERNODENAME=curnode;
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			flowSubmitObj.applyMatersJson = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goFlowAddSPMatter", {
					title : "补充绑定材料",
					width : "650px",
					height:"80%",
					//height : "480px",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	}
</script>	
</head>

<body class="easyui-layout bodySelectNone" fit="true" >
    <input type="hidden" name="EFLOW_FLOWOBJ" id="EFLOW_FLOWOBJ" value="${EFLOW_FLOWOBJ}"/>
    
	<div data-options="region:'north',collapsible:false" style="height: 42px;" split="true" border="false" >
	     <div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
					    <div style="text-align:center;">
				           <button class="eflowbutton" id="addSPMatter" onclick="goAddSPMatter();" >
                                	补充审批材料
				           </button>
						</div>
					</div>
				</div>
			</div>
		 </div>
	</div>

    <div data-options="region:'center'" >
       
		<div class="easyui-tabs eve-tabs" fit="true" >
			<div title="申请表单" >
			    <iframe id="EFLOWFORM_IFRAME" scrolling="auto" width="100%" height="90%" frameborder="0" src="${IFRAME_URL}" >
			    </iframe>
			</div>
			<div title="流转公文列表" href="fileAttachController.do?flowFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}">
            </div>
			<c:forEach items="${bindForms}" var="bindForm">
			   <div title="${bindForm.FORM_NAME}" >
				    <iframe scrolling="auto" width="100%" height="90%" frameborder="0" src="${bindForm.FORM_URL}" >
				    </iframe>
			   </div>
			</c:forEach>
			<div title="审核过程明细" href="flowTaskController.do?view&exeId=${EFLOWOBJ.EFLOW_EXEID}">
			</div>
			<div title="补件信息列表" >
			     <iframe scrolling="auto" width="100%" height="90%" frameborder="0" src="bjxxController.do?bjxxFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
                </iframe>
            </div>
			<div title="流程图" href="flowDefController.do?flowimg&defId=${EFLOW_FLOWDEF.DEF_ID}&exeId=${EFLOWOBJ.EFLOW_EXEID}">
			</div>
			<div title="查看打印日志" href="printAttachController.do?printFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}"></div>
		</div>
	</div>
</body>
</html>
