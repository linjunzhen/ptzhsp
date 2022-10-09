<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.ServiceItemService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp" /> 
    <%--结束编写头部文件 --%>
    
    <div class="container">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>投资项目申报</i></div>
<%--        <div style="z-index:177;margin:183px 0px 0px 376px;position:absolute;color:#cc1425;height:45px;line-height:45px;font-size:16px;font-weight:bold;">--%>
<%--            <a  style="color:#cc1425;font-family:"宋体",sans-serif; "   href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" target="_blank"> 省级投资项目申报登记办事指南>></a></div>--%>
        <div class="bsMain1 clearfix">
            <div class="bsboxCon lfloat borderR">
                <div class="newbsboximg"><a href="contentController/view.do?contentId=4321" target="_blank"><img src="webpage/website/common/images/bsboximg4.png" /></a>
                <a target="_blank" href="contentController/view.do?contentId=4321" class="textright">省级投资项目申报登记办事指南>></a></div>
                <div class="newbsboximg"><a href="contentController/view.do?contentId=522" target="_blank"><img src="webpage/website/common/images/bsboximg.png" /></a>
                <a target="_blank" href="contentController/view.do?contentId=522" class="textright">点击查看社会投资项目办事指南>></a></div>
                <div class="bslistL">
                    <div class="bsCone">
                        <div class="bsTone bsxmcolor">土地使用权取得阶段</div>
                        <div class="bsLione">
                            <ul>
                                <li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00102">社会投资项目规划选址初步对接意见</a></li>
                                <li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00103">社会投资项目规划选址及用地事项审批</a></li>
                            </ul>
                        </div>
                        <span class="bsdate"><b>14</b>个工作日，不含初步对接时间</span>
                    </div>
                    <div class="bsCtwo">
                        <div class="bsTtwo bsxmcolor1">项目核准或备案阶段</div>
                        <div class="bsLitwo bsicon5">
                            <ul>
                                <li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00110">社会投资项目核准</a></li>
                                <li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00111">社会投资项目备案</a></li>
                            </ul>
                        </div>
                        <span class="bsdate" style="margin-left:200px;"><b>核准：23</b>个工作日，不含公示时间<br /><b>备案：18</b>个工作日，不含公示时间</span>
                    </div>
                    <div class="bsCthree">
                        <div class="bsTthree bsxmcolor2">施工设计和招标阶段</div>
                        <div class="bsLi1">
                            <a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00107">社会投资项目施工设计和招投标事项审批</a> 
                        </div>
                        <span class="bsdate"><b>17</b>个工作日，不含公示时间</span>
                    </div>
                     <div class="bsCfour">
                        <div class="bsTfour bsxmcolor3">统一验收阶段</div>
                        <div class="bsLi2">
                            <a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00105">社会投资项目统一竣工验收事项审批</a> 
                        </div>
                        <span class="bsdate" style="margin-left:112px;"><b>39</b>个工作日，不含公示时间</span>
                    </div>
                </div>
            </div>
            <div class="bsboxCon rfloat">
                <div class="newbsboximg"><a target="_blank"  href="serviceItemController/bsznDetail.do?itemCode=345071904XK00201"><img src="webpage/website/common/images/bsboximg3.png" /></a>
                	<a href="contentController/view.do?contentId=945" class="textright" target="_blank" >点击查看投资项目招投标核准办事指南>></a></div>
                <div class="newbsboximg"><a target="_blank"  href="contentController/view.do?contentId=521"><img src="webpage/website/common/images/bsboximg1.png" /></a>
                	<a href="contentController/view.do?contentId=521" class="textright" target="_blank" >点击查看政府投资项目办事指南>></a></div>
                <div class="bslistR">
                    <div class="bsCone">
                        <div class="bsTone bsxmcolor4">规划选址用地阶段</div>
                        <div class="bsLione">
                            <ul>
                                <li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00201">政府投资项目规划选址初步对接意见</a></li>
                                <li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00202">政府投资项目规划选址及用地事项审批</a></li>
                            </ul>
                        </div>
                        <span class="bsdate"><b>14</b>个工作日，不含初步对接时间</span>
                    </div>
                    <div class="bsCtwo">
                        <div class="bsTtwo bsxmcolor5">工可报告批复阶段</div>
                        <div class="bsLi1">
                            <a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00209">政府投资项目《工程可行性研究报告+》事项审批</a> 
                        </div>
                        <span class="bsdate"><b>23</b>个工作日，不含公示时间</span>
                    </div>
                    <div class="bsCthree">
                        <div class="bsTthree bsxmcolor6">施工设计和招投标阶段</div>
                        <div class="bsLi1">
                            <a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00204">政府投资项目施工设计和招投标事项审批</a> 
                        </div>
                        <span class="bsdate"><b>33</b>个工作日，不含公示时间</span>
                    </div>
                     <div class="bsCfour">
                        <div class="bsTfour bsxmcolor7">统一竣工验收阶段</div>
                        <div class="bsLi2">
                            <a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=350128XK00210">政府投资项目统一竣工验收事项审批</a> 
                        </div>
                        <span class="bsdate" style="margin-left:112px;"><b>49</b>个工作日，不含公示时间</span>
                    </div>
                </div>
            </div>
<%--            <div class="">--%>
<%--	            <a href="serviceItemController/bsznDetail.do?itemCode=345071904XK00201" target="_blank">--%>
<%--	            	<img src="webpage/website/common/images/bsboximg2.png" />--%>
<%--	            </a>--%>
<%--                <a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=345071904XK00201">投资项目招投标>></a> --%>
<%--	            <a target="_blank" href="contentController/view.do?contentId=945" class="textright">--%>
<%--	            	点击查看投资项目招投标核准办事指南>>--%>
<%--	            </a>--%>
<%--	        </div>--%>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
