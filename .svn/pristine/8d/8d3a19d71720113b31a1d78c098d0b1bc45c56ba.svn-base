<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
Properties properties = FileUtil.readProperties("project.properties");
String fileBasePath = properties.getProperty("uploadFileUrl");
%>
<c:set var="webRoot" value="<%=basePath%>" />
<!-- load the extjs libary -->
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/syj-style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/main/css/style1.css"/>

