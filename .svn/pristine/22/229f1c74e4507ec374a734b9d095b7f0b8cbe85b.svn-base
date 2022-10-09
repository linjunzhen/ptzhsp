<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String exeId = (String)request.getAttribute("exeId");
    String busTableName = (String)request.getAttribute("busTableName");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
</head>

<body>
	<!-- =========================表格开始==========================-->
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro1">
		<thead>
			<tr>				
				<td class="tab_width1" width="50px">序号</td>
				<td class="tab_width1" width="350px">签章流程号</td>
				<td class="tab_width1" width="350px">签章材料名称</td>
				<td class="tab_width1" width="350px">签章状态</td>				
				<td class="tab_width1" width="250px">操作</td>
			</tr>
		</thead>

		<c:forEach var="mater" items="${materList}" varStatus="varStatus">
			<tr>
				<td>${varStatus.index+1}</td>
				<td style="text-align: center;">${mater.SIGN_FLOWID}</td>
				<td style="text-align: center;">${mater.MATER_NAME}</td>
				<td style="text-align: center;">${mater.IS_SIGN}</td>				
				<td style="text-align: center;">
					<p>
						<a href="javascript:void(0);"
							onclick="showSignFlowInfo('${mater.SIGN_FLOWID}');"
							style="cursor: pointer;"> <font color="blue"> 查看签署明细 </font>
						</a> <a href="javascript:void(0);"
							onclick="downloadDoc('${mater.YW_ID}','${mater.DOWNLOAD_DOCURL}');" style="cursor: pointer;">
							<font color="blue"> 下载签署文档 </font>
						</a>
					</p>
				</td>
			</tr>
		</c:forEach>

	</table>
	<!-- =========================表格结束==========================-->
</body>
<script type="text/javascript">

	//查看签署过程明细
	function showSignFlowInfo(signFlowId){
	    $.dialog.open("bdcQlcMaterGenAndSignController.do?goSignFlowDetail&SIGN_FLOWID="+signFlowId, {
	        title : "签章过程明细",
	        width:"100%",
	        lock: true,
	        resize:false,
	        height:"100%",
	        close: function () {
	        }   
	    }, false);
	}

	//下载签署文档
	function downloadDoc(ywId, downloadDocUrl) {
		if (downloadDocUrl) {
			window.location.href = __ctxPath + "/bdcQlcMaterGenAndSignController/downLoadSignMater.do?YW_ID=" + ywId;
		} else {
			layer.alert("签署过程未完成，无法下载签署文档！");
		}
	}
	
</script>
</html>
