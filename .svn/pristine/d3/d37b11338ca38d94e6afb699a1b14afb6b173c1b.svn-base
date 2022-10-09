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
                    <td class="tab_width1" width="350px">材料名称</td>
                    <td class="tab_width1">材料附件列表</td>
                    <td class="tab_width1" width="250px">补件要求</td>
                </tr>
            </thead>
            <c:forEach var="bjcl" items="${bjclList}" varStatus="varStatus">
              <tr>
                 <td>${varStatus.index+1}</td>
                 <td style="text-align: center;">${bjcl.MATER_NAME}</td>
                 <td>
                    <c:forEach var="uploadFile" items="${bjcl.filesMap}">
                        <p>
                        <a href="javascript:void(0);"
                                onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');"
                                 style="cursor: pointer;">
                                  <font color="blue">
                                               ${uploadFile.FILE_NAME}
                                  </font>
                        </a>
                        </p>
                    </c:forEach>
                 </td>
                 <td style="text-align: center;">${bjcl.BJYJ}</td>
              </tr>
         </c:forEach>
        </table>
		<!-- =========================表格结束==========================-->
</body>
</html>
