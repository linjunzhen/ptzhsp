<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
Object itemCodes = request.getAttribute("itemCodes");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.jqprint-0.3.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />

	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	
	<script type="text/javascript">
	
	$(function() {	
		$(".syj-sbmain2").slide({titCell:".syj-tabtitle li",mainCell:".statement_bodySlide",trigger:"click"});
	});
	
	/**
	*  打印界面
	*/
	function printNowPage() {
		$('#statement_bodyDiv').jqprint();
	}
	</script>
</head>
<div class="container tmargin20" style="background: #fff;height: 40px;">
	<a href="javascript:printNowPage();"  class="btn1" style="margin: 6px 0 6px 8px;">打 印</a>
</div>	
<div class="container tmargin20" >
        <div class="syj-sbmain2 tmargin20 "  id="jbxx">
        	<div class="syj-tabtitle">
				<ul id="statement_ul">
					<c:forEach var="itemList" items="${itemList}" >
						<li >
							<a style="color: black">
								<c:if test="${fn:length(itemList.itemName)>8 }">  
			                         ${fn:substring(itemList.itemName, 0, 8)}...  
			                   	</c:if>  
			                 </a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="statement_bodySlide" id="statement_bodyDiv">
				<c:forEach var="itemList" items="${itemList}" >
					<div>						
						<jsp:include page="/webpage/website/applyforms/statement/${itemList.itemCode }.jsp" />
					</div>
				</c:forEach>
			</div>
        </div>
    </div>
</div>
	

