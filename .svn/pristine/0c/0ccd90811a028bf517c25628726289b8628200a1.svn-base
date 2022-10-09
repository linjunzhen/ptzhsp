<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">

<div>
	<div class="syj-title1" style="height:30px;">
		<span>许可证-材料信息</span>
	</div>
   <table cellpadding="0" cellspacing="1" class="syj-table2 tmargin2"  style="margin-top: 15px;">
       <tr>
           <th style="width:10%">序号</th>
           <th style="width:40%">施工许可证号</th>
           <th style="width:30%">施工许可电子证照号</th>
           <th style="width:20%">操作</th>
       </tr>
       
       <c:forEach var="certificate" items="${licenceMaterList}">
           <tr>
               <td>${certificate.orderNum}</td>
               <td title="${certificate.CONSTRNUM}">${certificate.CONSTRNUM}</td>
               <td title="${certificate.CERT_NUM}">${certificate.CERT_NUM}</td>
               <td>
                   <a class="xz" href="<%=path%>/DownLoadServlet?fileId=${certificate.CERTIFICATEIDENTIFIERFILEID}">下载施工许可电子证照</a>
               </td>
           </tr>
      </c:forEach>
   </table>
</div>
