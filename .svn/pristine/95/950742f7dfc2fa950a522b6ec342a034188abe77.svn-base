<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>面签信息</title>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css"
          href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <!--[if lte IE 6]>
    <script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
    </script>
    <![endif]-->
    <eve:resources loadres="artdialog,apputil"></eve:resources>

</head>

<body style="background: none; min-width:800px;">
<table cellpadding="0" cellspacing="0" class="zxtable2">
    <tr>
        <th colspan="5"><font style="font-size: 18px;color: #0b6fa2"> 您的面签闽政通申报号为</font>：
            <font  style="font-size: 18px;color: red">${fn:substring(exeId, 8, 20)}</font></th>
    </tr>
    <tr>
        <th width="10%">序号</th>
        <th width="15%">姓名</th>
        <th width="20%">身份证</th>
        <th width="30%">面签审核意见</th>
        <th width="15%">是否已面签</th>

    </tr>
    <c:forEach items="${signInfoList}" var="signInfo" varStatus="signStatus">
        <tr>
            <td>${signStatus.index+1}</td>
            <td>
               ${signInfo.SIGN_NAME}
            </td>
            <td>
                    ${signInfo.SIGN_IDNO}
            </td>
            <td>
                    ${signInfo.SIGN_OPINION}
            </td>
            <td>
                <c:if test="${signInfo.SIGNED_FLAG==0}">
                    <font color="red">需面签</font>
                </c:if>
                <c:if test="${signInfo.SIGNED_FLAG!=0}">
                   <font color="blue"> 已面签</font>
                </c:if>
            </td>

        </tr>
    </c:forEach>
    <tr>
        <td colspan="5">
            <img src="<%=path%>/webpage/website/zzhy/images/signFlow1.png">
        </td>
    </tr>
</table>

</body>
</html>
