<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="<%=path%>/webpage/weixin/agile-lite/agile/css/agile.layout.css">
<link rel="stylesheet" href="<%=path%>/webpage/weixin/agile-lite/agile/css/flat/flat.component.css">
<link rel="stylesheet" href="<%=path%>/webpage/weixin/agile-lite/agile/css/flat/flat.color.css">
<link rel="stylesheet" href="<%=path%>/webpage/weixin/agile-lite/agile/css/flat/iconline.css">
<link rel="stylesheet" href="<%=path%>/webpage/weixin/agile-lite/agile/css/flat/iconform.css">
<link rel="stylesheet" href="<%=path%>/webpage/weixin/agile-lite/component/timepicker/timepicker.css">
<link rel="stylesheet" href="<%=path%>/webpage/weixin/css/weixin.css">