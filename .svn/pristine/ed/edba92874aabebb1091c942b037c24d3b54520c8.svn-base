<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.ShtzService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<%
   String type = request.getParameter("TZLX");
   String dqjd = request.getParameter("DQJD");
   String dqlc = request.getParameter("DQLC");
   ShtzService shtzService = (ShtzService)AppUtil.getBean("shtzService");
   List<List<Map<String,Object>>> list = shtzService.findShtzDatas(type);
   request.setAttribute("list", list);
   request.setAttribute("dqjd",Integer.parseInt(dqjd));
   request.setAttribute("dqlc",Integer.parseInt(dqlc));
%>

<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>

<div class="bsbox clearfix">
	<div class="bsboxT">
    	<ul>
        	<li class="on" style="background:none"><span>当前节点信息</span></li>
        </ul>
    </div>
    <div class="bsboxClc">
    	<div class="BiglcTitle">
        	<ul>
        	  
            	<li <c:if test="${dqjd==1}">class="on"</c:if> ><a href="javascript:void(0)" class="BlcT">阶段 1</a></li>
                <li <c:if test="${dqjd==2}">class="on"</c:if> ><a href="javascript:void(0)" class="BlcT1">阶段 2</a></li>
                <li <c:if test="${dqjd==3}">class="on"</c:if> ><a href="javascript:void(0)" class="BlcT1">阶段 3</a></li>
                <li <c:if test="${dqjd==4}">class="on"</c:if> ><a href="javascript:void(0)" class="BlcT2">阶段 4</a></li>
            </ul>
        </div>
        <div class="BiglcCon">
           
           <c:forEach items="${list}" var="dataList" varStatus="jdStatus">
	        	<div class="tmargin12">
	                <div class="smallcTitle">
	                	<ul>
	                	    
	                	   <c:forEach items="${dataList}" var="data" varStatus="varStatus">
	                	   
	                    	<li 
	                    	<c:if test="${dqjd>(jdStatus.index+1)}">class="pastnum"</c:if>
	        	            <c:if test="${dqjd==(jdStatus.index+1)&&dqlc==(varStatus.index+1)}">class="nownum"</c:if>
	        	            <c:if test="${dqjd==(jdStatus.index+1)&&dqlc>(varStatus.index+1)}">class="pastnum"</c:if>
	                    	
	                    	><span>${varStatus.index+1}
	                    	</span>
	                    	</li>
	                       </c:forEach>
	                    </ul>
	                </div>
	                <div class="smallccon">
	                    <c:forEach items="${dataList}" var="data" varStatus="varStatus" >
	                    	<div class="smallcBox">
		                    	<h2>${data.DIC_NAME}
		                    	</h2>
		                    </div>
	                    </c:forEach>
	                </div>
	            </div>
            </c:forEach>
            
        </div>
        <script type="text/javascript">jQuery(".bsboxClc").slide({titCell:".BiglcTitle li",mainCell:".BiglcCon",trigger:"click"})</script>
    </div>
</div>