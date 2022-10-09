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
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
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
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_CZWSPRPSGWXKZ_FORM");
    		}
		}
	});
	
	function submitFlow() {
		AppUtil.submitWebSiteFlowForm('T_BSFW_CZWSPRPSGWXKZ_FORM');
	}

	function tempSaveFlow() {
		AppUtil.tempSaveWebSiteFlowForm('T_BSFW_CZWSPRPSGWXKZ_FORM');
	}
	
</script>
</head>
<style type="text/css">
	.tab_width{
		width: 160px;
		font-size: 16px;
		font-size: 16px;
		color: #333333;
		vertical-align: middle;
		line-height: 20px;
		color: #434343;
		font-weight: 400;
		text-align: right;
		padding: 10px 10px 10px 0;
	}
	.tab_text{
		width: 280px;
		height: 40px;
		line-height: 20px;
		font-size: 16px!important;
		color: #000000!important;
		padding: 0 10px!important;
		box-sizing: border-box!important;
		border: 1px solid #c9deef!important;
	}
</style>

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
        
        <form id="T_BSFW_CZWSPRPSGWXKZ_FORM" method="post" >
        
        <%--===================重要的隐藏域内容=========== --%>
		    <%--开始引入公共隐藏域部分 --%>
			<jsp:include page="./commonhidden.jsp" />
		    <%--结束引入公共隐藏域部分 --%>
	    <%--===================重要的隐藏域内容=========== --%>
    	
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./tzjbxx.jsp" />
		<%--结束引入基本信息部分 --%>
		
		<%--开始编写证照信息 --%>
        <div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>证照信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
				<table cellpadding="0" cellspacing="0" class="bstable1">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>排水户名称:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="PSHMC"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>排水户代码:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="PSHDM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width" class="tab_text"><font class="tab_color">*</font>有效期自：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="YXQ_BEGIN" />
						</td>
						<td class="tab_width" class="tab_text"><font class="tab_color">*</font>有效期至：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="YXQ_END" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>许可证编号:<br>
							<span style="color: red">(格式：XXXXXXXXXX字第XXXX号</span>
						</td>
						<td>
						  <input type="text" name="XKZBH" value="${busRecord.XKZBH}" class="tab_text validate[required,custom[czpwZzbm]]"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>发证时间：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="FZSJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>排水户名称或排水项目名称:</td>
						<td colspan="3">
						  <input type="text" class="tab_text validate[required]" name="PSHMCHPSXMMC"/>
						</td>
					</tr>
					
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>法定代表人:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="FDDBR"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>营业执照注册号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="YYZZZCH"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>详细地址:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="XXDZ"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>排水户类型:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="PSHLX"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>列入重点排污单位名录（是/否）：</td>
							<td> <select id="projectType" name="LRZDPWDWML" class="tab_text" dataparams="PROJECTTYPE" >
							<option value="是">是</option>
							<option value="否">否</option>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>主要污染物项目及排放标准:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="ZYWRWXMJPFBZ"   />（mg/L）
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>排污水口编号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="PWSKBH"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>连接管位置:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LJGWZ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>排水去向（路名）:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="PSQX"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>排水量:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="PSL"/>（m3/日）
						</td>
					</tr>
					<tr>
						<td class="tab_width" ><font class="tab_color">*</font>污水最终去向:</td>
						<td colspan="3">
						  <input type="text" class="tab_text validate[required]" name="WSZZQX"/>
						</td>
					</tr>
				</table>
            </div>
        </div>
		
		<div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            <jsp:include page="./matterListZF.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_CZWSPRPSGWXKZ_FORM" name="formName"/>
            </jsp:include>
        </div>
        
        <jsp:include page="./applyuserinfo.jsp" />
        </form>
        
        <%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="tempSaveFlow();" name="tempSaveFn"/>
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