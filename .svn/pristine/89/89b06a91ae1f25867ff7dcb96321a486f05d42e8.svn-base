<%@page import="net.evecom.platform.hflow.service.FlowMappedService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<%
   String defId = request.getParameter("defId");
   String nodeName = request.getParameter("nodeName");
   FlowMappedService flowMappedService = (FlowMappedService) AppUtil.getBean("flowMappedService");
   Map<String,Object> mapped = flowMappedService.getByDefidAndNodeName(defId, nodeName);
   request.setAttribute("mapped", mapped);
%>

<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>

<div class="bsbox clearfix" id="bllc">
	<div class="bsboxT">
    	<ul>
        	<li class="on" style="background:none"><span>办理流程</span></li>
        </ul>
    </div>
   	<div class="flow_container" style="height: 65px;">
           <ul>
               <c:forEach var="m" items="${mapped.mappedList}">
               <li class="${m.nodeClass}" style="width:${m.nodeWidth}"><div class="pro_base"></div><a href="javascript:void(0);">${m.YS_NAME}</a></li>
               </c:forEach>
           </ul>
           <div class="flow_progress" style="margin:0 ${mapped.flow_progress};">
               <div class="flow_progress_bar">
                   <span class="flow_progress_highlight" style="width:${mapped.allHighLight};"></span>
               </div>
           </div>
     </div>
     
            <div class="bsboxC">
            	<div>
                	<table cellpadding="0" cellspacing="0" class="bstable2">
                    	<tr>
                        	<th width="95px">环节</th>
                            <th width="135px">办理人</th>
<!--                             <th width="135px">办理时限</th> -->
                            <th width="325px">审查标准</th>
                            <th width="235px">审查意见（结果）</th>
                        </tr>
						<c:forEach items="${serviceItem.HJMXS}" var="hjmx" varStatus="s">
                        <tr>
                        	<td>${hjmx.NODE_NAME}</td>
                        	<td>${hjmx.USER_NAME}</td>
<!--                             <td style="text-align:centen"> -->
<!--                             <c:if test="${hjmx.TIME_LIMIT==null||hjmx.TIME_LIMIT==''}"> </c:if> -->
<!-- 							<c:if test="${hjmx.TIME_LIMIT==0}">${hjmx.TIME_LIMIT}</c:if> -->
<!-- 							<c:if test="${hjmx.TIME_LIMIT!=0}"> -->
<!-- 								${hjmx.TIME_LIMIT} -->
<!-- 								<c:if test="${hjmx.TIME_TYPE=='gzr'}">工作日</c:if> -->
<!-- 								<c:if test="${hjmx.TIME_TYPE=='zrr'}">自然日</c:if> -->
<!-- 								<c:if test="${hjmx.TIME_TYPE=='y'}">月</c:if> -->
<!-- 							</c:if> -->
<!-- 							</td> -->
                            <td style="text-align:left">${hjmx.REVIEW_STANDARD}</td>
                            <td style="text-align:left">${hjmx.HANDLE_OPINIONS}</td>
                        </tr>
						</c:forEach>
                    </table>
                </div>
            </div>
            
</div>