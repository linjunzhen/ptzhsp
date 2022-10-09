<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="eui-card eui-declaration wysb5 ovh">

    <div class="eui-finish">
        <img src="<%=basePath%>/webpage/website/project/images/finish.png" >
        <b>恭喜！提交成功</b>
        <table>
            <tr>
                <th>项目编码：</th>
                <td id="applyCompletedProjectCode"></td>
            </tr>
            <tr>
                <th>项目名称：</th>
                <td id="applyCompletedProjectName"></td>
            </tr>
            <tr>
                <th>申报号：</th>
                <td id="applyCompletedExeId"></td>
            </tr>
        </table>
    </div>

    <div class="eui-flex tc eui-sx-btn">
        <a class="eui-btn light" href="<%=basePath%>projectWebsiteController.do?myProjectView">返回我的办件</a>
        <a class="eui-btn light" href="<%=basePath%>projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b638d343d0024">申报新项目</a>
<%--        <a class="eui-btn" href="javascript:void(0)">继续申报当前项目</a>--%>
    </div>


</div>