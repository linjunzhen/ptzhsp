<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<title>预售公告信息-平潭综合实验区行政服务中心</title>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/aos.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.selectlist.js"></script>	
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<!-- my97 end -->
	<script type="text/javascript">
		function page(num){
			if(null!=num && ''!=num){
				if(!isNaN(num)){
					$("input[name='PageNumber']").val(num);
					$('#spzzddphForm').submit();	
				}else{
				    alert("请输入有效的数字");
					$("input[name='pageNum']").focus();
				}
			}else{				
				 alert("请输入有效的参数");
				$("input[name='pageNum']").focus();
			}
		}
		function goPage(){
			var num = $("input[name='pageNum']").val();
			if(null!=num && ''!=num){
				if(!isNaN(num)){
					$("input[name='PageNumber']").val(num);
					$('#spzzddphForm').submit();	
				}else{
				   alert("请输入有效的参数");
				$("input[name='pageNum']").focus();
				}
			}else{				
				 alert("请输入有效的参数");
				$("input[name='pageNum']").focus();
			}
		}
	</script>
</head>

<body>
	
	<!--头部-->
	<div class="eui-header"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/head.jsp"></iframe></div>
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > <span>预售公示信息</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
		<div class="lfloat in-l">			
			<jsp:include page="/webpage/website/bdc/leftList.jsp?currentNav=7" />
		</div>
		<div class="rfloat in-r">
			<form id="spzzddphForm" method="post" action="bdcQueryController/spzzddph.do">
				<input class="kslp-input num" type="hidden" name="PageSize" value="${variables.PageSize}" >
				<input class="kslp-input num" type="hidden" name="PageNumber" value="${variables.PageNumber}" >
			</form>	
			<div class="in-table-box">
				<table class="index-table" border="0" cellspacing="0" cellpadding="0">
					<tr>						
						<th>序号</th>
						<th>开发商</th>
						<th>项目名称</th>
						<th>幢号</th>
						<th>成交套数(套)</th>
						<th>成交面积(万㎡)</th>
						<th style="width:50px;">查看</th>
					</tr>
					<c:forEach items="${resultMap.Items}" var="item" varStatus="s">
					<tr>
						<td>${(resultMap.PagedData.PageNumber-1)*resultMap.PagedData.PageSize+s.index+1}</td>
						<td>${item.DeveloperName}</td>
						<td><a href="<%=path%>/bdcQueryController/getProject.do?ProjectId=${item.ProjectID}" target="_blank">${item.ProjectName}</a></td>
						<td>${item.BuildingID}</td>
						<td>${item.CaseCount}</td>
						<td>${item.BuildArea}
						</td>
						<td style="width: 40px;"><a href="<%=path%>/bdcQueryController/getProject.do?ProjectId=${item.ProjectID}" target="_blank">查看</a></td>
					</tr>
					</c:forEach>
				</table>
				
			    <div align="center" class="eui-page">
			        <ul>
			            <li>共 <b>${resultMap.PagedData.ItemCount}</b> 项</li>
			            <li>第 <b>${resultMap.PagedData.PageNumber}/${resultMap.PagedData.PageCount}</b> 页</li>
						<c:if test="${resultMap.PagedData.PageNumber==1}">
			            <li><a href="javascript:void(0)" class="eui-disabled">首页</a></li>
			            <li><a href="javascript:void(0)" class="eui-disabled">上一页</a></li>
						</c:if>
						<c:if test="${resultMap.PagedData.PageNumber>1}">
			            <li><a href="javascript:void(0)" onclick="page(1)">首页</a></li>
			            <li><a href="javascript:void(0)" onclick="page(${resultMap.PagedData.PageNumber-1})">上一页</a></li>
						</c:if>
						<c:if test="${resultMap.PagedData.PageCount<=5}">
						<c:forEach var="s"  begin="1" end="${resultMap.PagedData.PageCount}">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${resultMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						
						<c:if test="${resultMap.PagedData.PageCount>5 && resultMap.PagedData.PageNumber<=3}">
						<c:forEach var="s"  begin="1" end="5">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${resultMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						
						<c:if test="${resultMap.PagedData.PageCount>5 && resultMap.PagedData.PageNumber>3 && resultMap.PagedData.PageNumber!=resultMap.PagedData.PageCount}">
						<c:forEach var="s"  begin="${resultMap.PagedData.PageNumber-2}" end="${resultMap.PagedData.PageNumber+2}">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${resultMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						<c:if test="${resultMap.PagedData.PageCount>5 && resultMap.PagedData.PageNumber==resultMap.PagedData.PageCount}">
						<c:forEach var="s"  begin="${resultMap.PagedData.PageCount-4}" end="${resultMap.PagedData.PageCount}">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${resultMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						
						<c:if test="${resultMap.PagedData.PageNumber<resultMap.PagedData.PageCount}">
			            <li><a href="javascript:void(0)" onclick="page(${resultMap.PagedData.PageNumber+1})">下一页</a></li>
			            <li><a href="javascript:void(0)" onclick="page(${resultMap.PagedData.PageCount})">尾页</a></li>
						</c:if>
						<c:if test="${resultMap.PagedData.PageNumber==resultMap.PagedData.PageCount}">
			            <li><a href="javascript:void(0)" class="eui-disabled">下一页</a></li>
			            <li><a href="javascript:void(0)" class="eui-disabled">尾页</a></li>
						</c:if>
			            <li>跳转到<input type="text" class="eui-page-num" name="pageNum" maxlength="8" id="pageNum">页
						<div class="eui-page-confirm"  onclick="goPage($('pageNum').val())">前往</div></li>
			        </ul>
			    </div>				
			</div>
		</div>
	</div>	
	
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" marginHeight="0" 
	marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>