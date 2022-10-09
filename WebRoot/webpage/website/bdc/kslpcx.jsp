<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<title>可售楼盘查询-平潭综合实验区行政服务中心</title>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/aos.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.selectlist.js"></script>
	<script type="text/javascript">
		function page(num){
			if(null!=num && ''!=num){
				if(!isNaN(num)){
					$("input[name='PageNumber']").val(num);
					$('#kslpcxForm').submit();	
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
					$('#kslpcxForm').submit();	
				}else{
				   alert("请输入有效的参数");
				$("input[name='pageNum']").focus();
				}
			}else{				
				 alert("请输入有效的参数");
				$("input[name='pageNum']").focus();
			}
		}
		$(function(){
			$("#Purpose").val('${variables.Purpose}');
		});
	</script>
</head>

<body>
	
	<!--头部-->
	<div class="eui-header"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/head.jsp"></iframe></div>
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > <span>可售楼盘查询</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
		<div class="lfloat in-l">			
			<jsp:include page="/webpage/website/bdc/leftList.jsp?currentNav=6" />
		</div>
		<div class="rfloat in-r">						
			<form id="kslpcxForm" method="post" action="bdcQueryController/kslpcx.do">
			<div class="display-table kslp-search">
				<input class="kslp-input num" type="hidden" name="PageSize" value="${variables.PageSize}" >
				<input class="kslp-input num" type="hidden" name="PageNumber" value="${variables.PageNumber}" >
				<p style="text-align: right;">项目名称：</p>
				<p><input  class="kslp-input info" type="text" placeholder="请输入项目名称" name="ProjectName" maxlength="32" value="${variables.ProjectName}"></p>
				<p style="text-align: right;">项目坐标点：</p>
				<p><input class="kslp-input info" type="text" placeholder="请输入项目坐标点"  name="Location" maxlength="32" value="${variables.Location}"></p>
				<p style="text-align: right;">项目用途：</p>
				<p>
					<select class="kslp-select" id="Purpose" name="Purpose">
						<option value="">所有用途</option>
						<option value="住宅">住宅</option>
						<option value="商业用房">商业用房</option>
						<option value="办公用房">办公用房</option>
						<option value="车库车位">车库车位</option>
					</select>
				</p>
			</div><div class="display-table  kslp-search">				
				<p style="text-align: right;padding-left: 10px;">
					面积：</p>			
				<p>
					<input class="kslp-input num" type="text" name="AreaForm"  maxlength="16"   value="${variables.AreaForm}">
					-
					<input class="kslp-input num" type="text" name="AreaTO"  maxlength="16" value="${variables.AreaTO}" >	
				</p>			
				<p style="text-align: right;width: 98px;">
					参考均价：</p>			
				<p>
					<input class="kslp-input num" type="text" name="PriceForm"  maxlength="16" value="${variables.PriceForm}">
					-
					<input class="kslp-input num" type="text" name="PriceTo"  maxlength="16" value="${variables.PriceTo}">
				</p>
				<p class="btn"><a class="index-btn" href="javascript:void(0);" onclick="$('#kslpcxForm').submit();">查 询</a></p>
			</div>
			</form>
			<div class="in-table-box">
				<table class="index-table" border="0" cellspacing="0" cellpadding="0">
					<tr>						
						<th>序号</th>
						<th>开发商</th>
						<th>项目名称</th>
						<th>项目坐落</th>
						<th>幢号</th>
						<th>房号</th>
						<th>所在层</th>
						<th>建筑面积</th>
						<th>参考均价</th>
					</tr>
					<c:forEach items="${kslpcxMap.Items}" var="kslpcx" varStatus="s">
					<tr>
						<td>${(kslpcxMap.PagedData.PageNumber-1)*kslpcxMap.PagedData.PageSize+s.index+1}</td>
						<td>${kslpcx.DeveloperName}</td>
						<td>${kslpcx.ProjectName}</td>
						<td>${kslpcx.Location}</td>
						<td>${kslpcx.BuildingID}</td>
						<td>${kslpcx.HouseID}</td>
						<td>${kslpcx.OnFloor}</td>
						<td>${kslpcx.BuildArea}</td>
						<td>${kslpcx.ConsultUnitPrice}</td>
					</tr>
					</c:forEach>
				</table>
				
			    <div align="center" class="eui-page">
			        <ul>
			            <li>共 <b>${kslpcxMap.PagedData.ItemCount}</b> 项</li>
			            <li>第 <b>${kslpcxMap.PagedData.PageNumber}/${kslpcxMap.PagedData.PageCount}</b> 页</li>
						<c:if test="${kslpcxMap.PagedData.PageNumber==1}">
			            <li><a href="javascript:void(0)" class="eui-disabled">首页</a></li>
			            <li><a href="javascript:void(0)" class="eui-disabled">上一页</a></li>
						</c:if>
						<c:if test="${kslpcxMap.PagedData.PageNumber>1}">
			            <li><a href="javascript:void(0)" onclick="page(1)">首页</a></li>
			            <li><a href="javascript:void(0)" onclick="page(${kslpcxMap.PagedData.PageNumber-1})">上一页</a></li>
						</c:if>
						<c:if test="${kslpcxMap.PagedData.PageCount<=5}">
						<c:forEach var="s"  begin="1" end="${kslpcxMap.PagedData.PageCount}">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${kslpcxMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						
						<c:if test="${kslpcxMap.PagedData.PageCount>5 && kslpcxMap.PagedData.PageNumber<=3}">
						<c:forEach var="s"  begin="1" end="5">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${kslpcxMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						
						
						<c:if test="${kslpcxMap.PagedData.PageCount>5 && kslpcxMap.PagedData.PageNumber>3 && kslpcxMap.PagedData.PageNumber!=kslpcxMap.PagedData.PageCount}">
						<c:forEach var="s"  begin="${kslpcxMap.PagedData.PageNumber-2}" end="${kslpcxMap.PagedData.PageNumber+2}">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${kslpcxMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						<c:if test="${kslpcxMap.PagedData.PageCount>5 && kslpcxMap.PagedData.PageNumber==kslpcxMap.PagedData.PageCount}">
						<c:forEach var="s"  begin="${kslpcxMap.PagedData.PageCount-4}" end="${kslpcxMap.PagedData.PageCount}">						
							<li><a href="javascript:void(0)" onclick="page(${s})" <c:if test="${kslpcxMap.PagedData.PageNumber==s}">class="eui-active"</c:if>>${s}</a></li>
						</c:forEach>
						</c:if>
						
						<c:if test="${kslpcxMap.PagedData.PageNumber<kslpcxMap.PagedData.PageCount}">
			            <li><a href="javascript:void(0)" onclick="page(${kslpcxMap.PagedData.PageNumber+1})">下一页</a></li>
			            <li><a href="javascript:void(0)" onclick="page(${kslpcxMap.PagedData.PageCount})">尾页</a></li>
						</c:if>
						<c:if test="${kslpcxMap.PagedData.PageNumber==kslpcxMap.PagedData.PageCount}">
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
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>