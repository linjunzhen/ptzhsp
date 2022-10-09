<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
    BusTypeService busTypeService = (BusTypeService)AppUtil.getBean("busTypeService");
    List<Map<String,Object>> grbsList = busTypeService.findByTypeCodeForWebSite("GRZTFL");
    List<Map<String,Object>> qybsList = busTypeService.findByTypeCodeForWebSite("FRZTFL");
    List<Map<String,Object>> bmfwList = busTypeService.findByTypeCodeForWebSite("BMFW");
    request.setAttribute("grbsList", grbsList);
    request.setAttribute("qybsList", qybsList);
    request.setAttribute("bmfwList", bmfwList);
%>
<div class="eui-bsl">
	<div class="hd eui-bstitle">
		<ul>
			<li><a href="javascript:void(0);"><img src="webpage/website/common/images/eui-icon2.png" />个人办事</a></li>
			<li><a href="javascript:void(0);"><img src="webpage/website/common/images/eui-icon3.png" />法人办事</a></li>
			<li><a href="javascript:void(0);" style="border-bottom:none;"><img src="webpage/website/common/images/eui-icon4.png" />部门办事</a></li>
		</ul>
	</div>
	<div class="bd eui-bscon clearfix">
		<div class="grcon">
			<ul>
				<c:forEach items="${grbsList}" var="grbsInfo">
					<li><a href="webSiteController/view.do?navTarget=website/grbs/grbs&sybusTypeId=${grbsInfo.TYPE_ID}" 
					target="_blank">
						<c:if test="${grbsInfo.ICON_PATH!=null}">
						   <img src="${grbsInfo.ICON_PATH}" />
						</c:if>
						<c:if test="${grbsInfo.ICON_PATH==null}">
						   <img src="webpage/website/common/images/sicon17.png" />
						</c:if>
						<p>${grbsInfo.TYPE_NAME}</p>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="tabcont1">
			<ul>
				<c:forEach items="${qybsList}" var="qybsInfo">
				<li><a href="webSiteController/view.do?navTarget=website/frbs/frbs&sybusTypeId=${qybsInfo.TYPE_ID}" 
				target="_blank">${qybsInfo.TYPE_NAME}</a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="tabcont1">
			<ul>				
				<c:forEach items="${bmfwList}" var="bmfwInfo">
				<li><a href="webSiteController/view.do?navTarget=website/bmfw/bmfw&sybusTypeId=${bmfwInfo.TYPE_ID}" 
				target="_blank" title="${bmfwInfo.TYPE_NAME}"><e:sub  str="${bmfwInfo.TYPE_NAME}" endindex="10" ></e:sub></a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<script type="text/javascript">jQuery(".eui-bsl").slide({})
		$('.eui-bscon').slimscroll({
			height: '238px'
		});
	</script>
</div>