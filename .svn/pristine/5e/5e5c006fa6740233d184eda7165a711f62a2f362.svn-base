<%@page import="net.evecom.platform.wsbs.service.CommonProblemService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>

<%
CommonProblemService commonProblemService = (CommonProblemService)AppUtil.getBean("commonProblemService");
Map<String,Object> cjwtmap = commonProblemService.findCjwtList("1","5","","");
request.setAttribute("cjwtmap", cjwtmap);
%>

<div class="mainL2T" style="height: 178px;">
	<div class="title2">最新常见问题</div>
	<div class="list1 tmargin12" >
		<ul>
			<c:forEach items="${cjwtmap.list}" var="cjwt">
            <li >
                <a 
                href="commonProblemController/cjwtDetail.do?entityId=${cjwt.PROBLEM_ID}" target="_blank" title="${cjwt.PROBLEM_TITLE}【${cjwt.ITEM_NAME}】">
                <e:sub str="${cjwt.PROBLEM_TITLE}【${cjwt.ITEM_NAME}】" endindex="24" ></e:sub></a>
            </li>
            </c:forEach>
		</ul>
	</div>
</div>
