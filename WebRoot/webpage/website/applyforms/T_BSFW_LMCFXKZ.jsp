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
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
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
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_LMCFXKZ_FORM");
    		}
		}
	});
	
	function submitFlow() {
		AppUtil.submitWebSiteFlowForm('T_BSFW_LMCFXKZ_FORM');
	}

	function tempSaveFlow() {
		AppUtil.tempSaveWebSiteFlowForm('T_BSFW_LMCFXKZ_FORM');
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
        
        <form id="T_BSFW_LMCFXKZ_FORM" method="post" >
        
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
						<td class="tab_width"><font class="tab_color">*</font>持证者:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CZZ"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>持证主体代码:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CZZTDM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>证照编号:</td>
							<span style="color: red">(格式：XXXX集采字[XXXX]XXX号</span>
						<td>
						  <input type="text" class="tab_text validate[required,custom[lmcfZzbm]]" name="CERT_NUM"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>编号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="BH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>申请根据:</td>
						<td colspan="3">
						  <input type="text" class="tab_text validate[required]" name="SQGJ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>林场（乡镇）:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LC"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>林班（村）:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LB" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>作业区（组）:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="ZYQ"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>小班（地块）:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="XB" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>采伐东至:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFDZ"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>采伐西至：</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFXZ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>采伐南至:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFNZ"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>采伐北至:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFBZ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>GPS定位:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="GPSDW"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>林分起源:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LFQY"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>林种:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LZ"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>树种:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="SZ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>权属:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="QS"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>林权证号（证明）:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LQZH"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>采伐类型:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFLX"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>采伐方式:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFFS"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>采伐强度:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFQD"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>采伐面积:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFMJ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>采伐株数:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFZS"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>采伐蓄积:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CFXJ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>出材量:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CCL"/>
						</td>
			 			<td class="tab_width"><font class="tab_color">*</font>采伐期限起始：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="CFQXQS" />
						</td>
					</tr>
					<tr>
			 			<td class="tab_width"><font class="tab_color">*</font>采伐期限截止：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="CFQXJZ" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>更新面积:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="GXMJ"/>
						</td>
					</tr>
					<tr>
			 			<td class="tab_width"><font class="tab_color">*</font>更新期限：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="GXQX" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>更新株数:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="GXZS"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>是否占限额：</td>
							<td> <select id="projectType" name="SFZXE" class="tab_text" dataparams="PROJECTTYPE" >
							<option value="是">是</option>
							<option value="否">否</option>
						</td>
						<td class="tab_width">备注:</td>
						<td>
						  <input type="text" class="tab_text" name="BZ"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>发证人:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="FZR"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>领证人:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="LZR"/>
						</td>
					</tr>
					<tr>
			 			<td class="tab_width"><font class="tab_color">*</font>发证时间：</td>
						<td>
						<input type="text" class="tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="FZSJ" />
						</td>
						<td class="tab_width"><font class="tab_color">*</font>证书流水号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="ZSLSH"/>
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
                <jsp:param value="T_BSFW_LMCFXKZ_FORM" name="formName"/>
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