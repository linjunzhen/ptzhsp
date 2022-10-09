<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-用户中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<!--[if lte IE 6]> 
	<script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="artdialog"></eve:resources>
	<script type="text/javascript"> 
	$(function(){
		var username = '${sessionScope.curLoginMember.YHMC}';
		if(null==username||""==username){
			window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login";
		}else{
			window.location.href = __newUserCenter;
		}
	})
	
	function thsm(bjid,backinfo,applyStatus){
		if(bjid!=''&&backinfo!=''&&applyStatus=='4'){
			$.dialog.open("webSiteController/thbjxx.do?&BJID="+bjid, {
                title : "退回补件信息",
                width : "900px",
                height : "400px",
                lock : true,
                resize : false
            }, false);
		}else if(bjid==''&&backinfo!=''&&applyStatus=='1'){
			art.dialog({
	            title: '退回意见',
	            content: backinfo,
	            lock : true,
	            width : "400px",
	            ok: true
	        });
		}else if(bjid!=''&&backinfo!=''&&applyStatus=='1'){
            art.dialog({
                title: '退回意见',
                content: backinfo,
                lock : true,
                width : "400px",
                ok: true
            });
        }else if(backinfo!=''){
            art.dialog({
                title: '退回意见',
                content: backinfo,
                lock : true,
                width : "400px",
                ok: true
            });
        }
	}
	
	function ckhz(exeId){
		$.dialog.open("webSiteController/hzxx.do?&exeId="+exeId, {
            title : "查看回执信息",
            width : "800px",
            height : "400px",
            lock : true,
            resize : false
        }, false);
	}
	</script> 
</head>

<body class="userbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../yhzx/head.jsp?currentNav=yhzx" />
    <%--结束编写头部文件 --%>
    <div class="container lbpadding20">
    	<div class="main_t"></div>
    	<div class="nmain clearfix">
        	<%--开始引入用户中心的左侧菜单 --%>
        	<jsp:include page="./yhmenu.jsp" >
        	    <jsp:param value="grzx" name="menuKey"/>
        	</jsp:include>
        	<%--结束引入用户中心的左侧菜单 --%>
            <div class="nmainR">
            	<div class="userIp">上次登录时间：${sessionScope.curLoginMember.SCDLSJ} 登录IP：<span class="bscolor1">${sessionScope.curLoginMember.SCDLIP}</span> 如果登录信息有异常，请您及时
            	<a href="webSiteController/view.do?navTarget=website/yhzx/zhsz">修改密码</a>，以防您的信息泄露！</div>
                <div class="nmainRtitle"><h3>我的待办事项</h3></div>
                <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
                    <tr>
                        <th width="50px">序号</th>
                        <th width="150px">申报号</th>
                        <th>事项名称</th>
                        <th width="260px" style="background-image:none;">操作</th>
                    </tr>
                    <c:forEach items="${userTaskList}" var="userTask" varStatus="varStatus">
                    <tr>
                        <td valign="top">${varStatus.index+1}</td>
                        <td valign="top">${userTask.EXE_ID}</td>
                        <td valign="top" style="text-align:left;">${userTask.ITEM_NAME}</td>
                        <td valign="top">
                        <c:if test="${userTask.BJXX_ID!=''||userTask.BACKOPINION!=''}">
                        <a href="javascript:void(0);" class="userbtn3" onclick="thsm('${userTask.BJXX_ID}','${userTask.BACKOPINION}','${userTask.APPLY_STATUS}')">退回说明</a>
                        </c:if>
                        <a href="javascript:void(0);" onclick="ckhz('${userTask.EXE_ID}');" class="userbtn3" >查看回执</a>
                        <a href="webSiteController.do?applyItem&itemCode=${userTask.ITEM_CODE}&exeId=${userTask.EXE_ID}&taskId=${userTask.TASK_ID}" class="userbtn3">办理</a>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                <div class="nmainRtitle"><span>总办件： ${mapCount.ZBJCOUNT}  件      办理中：${mapCount.BLZCOUNT}  件       已办结： ${mapCount.YBJCOUNT}  件</span></div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
