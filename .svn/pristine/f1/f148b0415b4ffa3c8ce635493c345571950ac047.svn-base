<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.hflow.service.ExecutionService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>

<%
	ExecutionService executionService = (ExecutionService)AppUtil.getBean("executionService");
	Map<String,Object> rdbsmap = executionService.findByRdbs("1","5");
	request.setAttribute("rdbsmap", rdbsmap);
%>

<div class="bsMainR">
    <div class="bstitle">热门办事</div>
    <div class="list3">
        <ul>
            <c:forEach items="${rdbsmap.list}" var="rdbs">
            <li >
                <a title="${rdbs.ITEM_NAME}"
                href="serviceItemController/bsznDetail.do?itemCode=${rdbs.ITEM_CODE}" target="_blank">
                <e:sub str="${rdbs.ITEM_NAME}" endindex="16" ></e:sub></a>
            </li>
            </c:forEach>
        </ul>
    </div>
</div>
