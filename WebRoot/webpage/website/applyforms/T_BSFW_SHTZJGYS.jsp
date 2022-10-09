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
<head>
  <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<!--[if lte IE 6]> 
	<script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.bslogo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
<script type="text/javascript">
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		if(EFLOW_FLOWOBJ){
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			//初始化表单字段值
    		if(eflowObj.busRecord){
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_SHTZJGYS_FORM");
    		}
		}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_SHTZJGYS"
		//});
		document.getElementById("fjbdDiv1").style.display="none";
	});
	
	function submitFlow(flowSubmitObj){
		 //先判断表单是否验证通过
		 AppUtil.submitWebSiteFlowForm('T_BSFW_SHTZJGYS_FORM');
	}
</script>
</head>

<body style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>
	<%--结束编写头部文件 --%>
	<div class="eui-main">
    	<jsp:include page="./formtitle.jsp" />
        
        <form id="T_BSFW_SHTZJGYS_FORM" method="post" >
        
        <%--===================重要的隐藏域内容=========== --%>
	    <%--开始引入公共隐藏域部分 --%>
			<jsp:include page="commonhidden.jsp" />
			<%--结束引入公共隐藏域部分 --%>
	    <%--===================重要的隐藏域内容=========== --%>
    	
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./tzjbxx.jsp" />
		<%--结束引入基本信息部分 --%>
            
        <jsp:include page="./annexCompleteAccept.jsp" /> 

        <%--开始编写所需材料情况 --%>    
        <div class="bsbox clearfix" style="margin-bottom:0px;">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            
            <jsp:include page="./matterListZF.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_SHTZJGYS_FORM" name="formName"/>
            </jsp:include>
        </div>
        <%--结束编写所需村夈情况 --%>    
        
        
         <div class="bsbox clearfix">
            <div class="bsboxC">
	            <table cellpadding="0" cellspacing="0" class="bstable1">
	            	<tr>
	                   	<th style="text-align:center;">备注</th>
	                    <td colspan="3">
	                    	<textarea maxlength="500" style="height:85px;width:98%;" name="CLBZ" ></textarea>
	                    </td>
	                </tr>
	            	<tr>
			            <td colspan="4" align="center"> 
             				<div  style="width:98%" align="left">
            				<span style="color:gray;font-size:10pt;">提示：附件内容请参照初步设计方案填写。</span></div>
             			</td>
	                </tr>
	            </table>
            </div>
        </div>
          
        <jsp:include page="./applyuserinfo.jsp" />
        
        </form>
        
        
        <%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="AppUtil.tempSaveWebSiteFlowForm('T_BSFW_SHTZJGYS_FORM');" name="tempSaveFn"/>
		</jsp:include>
		<%--结束引入提交DIV界面 --%>
            
        <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
        <c:if test="${EFLOWOBJ.HJMC!=null}">
        <jsp:include page="xmlct.jsp" >
           <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
           <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
        </jsp:include>
        </c:if>
        <%-- 结束引入阶段流程图 并且当前节点不在开始或结束节点--%>
        
        <%--开始引入回执界面 --%>
		<jsp:include page="./hzxx.jsp">
			<jsp:param value="${EFLOWOBJ.EFLOW_EXEID}" name="exeId" />
		</jsp:include>
		<%--结束引入回执界面 --%> 
        
    </div>
    
	
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
