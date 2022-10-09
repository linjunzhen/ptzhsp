<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>不动产权证收费领证登记信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css" href="plug-in/easyui-1.4/themes/bootstrap/easyui.css"
	rel="stylesheet">
<link type="text/css" href="plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
<link type="text/css" href="plug-in/eveflowdesign-1.0/css/design.css"
	rel="stylesheet">
<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript" src="plug-in/easyui-1.4/jquery.easyui.min.js"></script>
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
<script type="text/javascript" src="plug-in/layer-1.8.5/layer.min.js"></script>
<script type="text/javascript" src="plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="plug-in/gwq/js/gwq.js"></script>
<script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script>
</head>
<!-- 添加柜外清设备终端 -->
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0"
	height="0"></object>
 <script language="javascript" for="GWQ"
	event="OnAfterGWQ_StartFormInfo(ErrorCode, selectList)" type="text/javascript">
	var params = "&EXE_ID=" + '${EFLOW_FLOWEXE.EXE_ID}';
	var url = encodeURI("printAttachController.do?createSubmitFormFile&selectList=" + selectList + params);
	// 将用户填写内容发送后台做与对应模板文件做数据绑定，传至printReadTemplate.jsp页面，生成word预览文档
	$.dialog.open(url, {
		title : "打印模版",
		width : "100%",
		height : "100%",
		left : "0%",
		top : "0%",
		close: function () {
			// 因为GWQ回调脚本重复调用，导致父页面的回调脚本失效，刷新此页面重新加载脚本
			window.location.reload();
		}
	}, false);
</script> 
<script type="text/javascript">	
	$(function() {
        var url=encodeURI('${IFRAME_URL}');
		$("#EFLOWFORM_IFRAME").attr('src',url);//设置src属性
	});

</script>
<body class="easyui-layout bodySelectNone" fit="true">
	<input type="hidden" name="EFLOW_FLOWOBJ" id="EFLOW_FLOWOBJ"
		value="${EFLOW_FLOWOBJ}" />
	<!-- 开始编写头部工具栏 -->
	<c:if test="${bdc_optype eq '1' || bdc_optype eq '2'}">
		<div data-options="region:'north',collapsible:false" style="height: 42px;"
			split="true" border="false">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="text-align:center;">			
	                           <button class="eflowbutton" id="BDC_JFFZSUBMIT_BTN"
	                                    onclick="BdcQzPrintUtil.BDC_jffzSubmitFun();">
	                                                                            提交</button>
	                           <button class="eflowbutton" id="BDC_JFFZSUBMIT_BTN"
	                                    onclick="BdcQzPrintUtil.BDC_jffzTempSaveFun();">
	                                                                           暂存</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<!-- 结束编写头部工具栏 -->

	<div data-options="region:'center'">
		<div class="easyui-tabs eve-tabs" id="EVEFLOW_TAB" fit="true">
			<div title="申请表单">
				<iframe id="EFLOWFORM_IFRAME" scrolling="auto" width="100%" height="95%" frameborder="0"> </iframe>
			</div>			
			<div title="流程图"
				href="flowDefController.do?flowimg&defId=${EFLOW_FLOWDEF.DEF_ID}&exeId=${EFLOWOBJ.EFLOW_EXEID}">
			</div>
		</div>
	</div>
</body>
</html>
