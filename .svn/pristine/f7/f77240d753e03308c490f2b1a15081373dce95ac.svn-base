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
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
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
			if (EFLOW_FLOWOBJ) {
				//将其转换成JSON对象
				var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
				//初始化表单字段值
				if (eflowObj.busRecord) {
					FlowUtil.initFormFieldValue(eflowObj.busRecord, "PROJECT_FINISH_MANAGE_FORM");
				} else {
					var projectCode = document.getElementById("PROJECTCODE").value;
					$.post("projectFinishManageController.do?projectFinishItemData", {
						projectCode : projectCode
					}, function(result) {
						if (result) {
							var dataObj = JSON2.parse(result);
							var data = dataObj.data;
							if (dataObj.success) {
							     $("#zljgysBtn").hide();
								FlowUtil.initFormFieldValue(data, "PROJECT_FINISH_MANAGE_FORM");
								$("input[name='PRJNUM']").val(data.PRJNUM);
							} else {
								if (dataObj.code == "-1") {
								    $("#zljgysBtn").hide();
									$("#submitDiv").hide();
									art.dialog({
										content : "请先办结施工许可事项",
										icon : "error",
										ok : true
									});
								} else {
									$("#zljgysBtn").show();
									loadPRJNUM();
									//结构类型可选择
									$("#jglxTd").hide();
									$("#jglxTd2").show();
								}
	
								}
						}
					});
				}
			}
		// document.getElementById("fjbdDiv").style.display="none";
		});
		function submitFlow() {
			var cjgfzrztRows = $("#cjgfzrztGrid").datagrid("getData");
	   		var cjgfzrztJson = JSON.stringify(cjgfzrztRows.rows);
	   		$("input[name='CJGFZRZT_JSON']").val(cjgfzrztJson);
			AppUtil.submitWebSiteFlowForm('PROJECT_FINISH_MANAGE_FORM');
		}
	
	       function tempSaveFn() {
            var cjgfzrztRows = $("#cjgfzrztGrid").datagrid("getData");
            var cjgfzrztJson = JSON.stringify(cjgfzrztRows.rows);
            $("input[name='CJGFZRZT_JSON']").val(cjgfzrztJson);
            AppUtil.tempSaveWebSiteFlowForm('PROJECT_FINISH_MANAGE_FORM');
        }
	
	
		function loadPRJNUM() {
			$.post("projectSgxkController/getPrjCode.do", {
				XZQHCode : '350128',
				codetype : 1
			},
				function(responseText, status, xhr) {
					var resultJson = $.parseJSON(responseText);
					if (resultJson.status) {
						$("[name='PRJNUM']").val(resultJson.code);
					} else {
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			);
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
        <form id="PROJECT_FINISH_MANAGE_FORM" method="post" >
			<c:if test="${projectCode != null }">
				<input type="hidden" id="PROJECTCODE" name="PROJECTCODE"  value="${projectCode}"/>
				<input type="hidden" id="PROJECT_CODE" name="PROJECT_CODE"  value="${projectCode}"/>
			</c:if>
			<c:if test="${projectCode == null }">
				<input type="hidden" id="PROJECTCODE" name="PROJECTCODE"  value="${busRecord.PROJECTCODE}"/>
				<input type="hidden" id="PROJECT_CODE" name="PROJECT_CODE"  value="${busRecord.PROJECTCODE}"/>
			</c:if>
			<input type="hidden" name="CJGFZRZT_JSON" />
			<input type="hidden" name="STRUCTQUALTYPES" />
			<%--===================重要的隐藏域内容=========== --%>
			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>基本信息</span></li>
					</ul>
				</div>
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
						<tr>
							<th> 审批事项</th>
							<td colspan="3">${serviceItem.ITEM_NAME}</td>
						</tr>
					</table>
					<table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;margin-top:-1px;">
						
						<tr>
							<th> 所属部门</th>
							<td style="border-right-style: none;">${serviceItem.SSBMMC}</td>
							<th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
							<td style="border-left-style: none;"></td>
						</tr>
						<tr>
							<th> 审批类型</th>
							<td>${serviceItem.SXLX}</td>
							<th> 承诺时间</th>
							<td>${serviceItem.CNQXGZR}工作日</td>
						</tr>
						<tr>
							<th><span class="bscolor1">*</span> 申报名称</th>
							<td colspan="3"><input type="text" class="tab_text validate[required]"
								style="width: 50%" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}"
								name="SBMC" maxlength="120"/>
								<span class="bscolor1"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
						</tr>
					</table>
				</div>
			</div>
			<%--结束编写基本信息 --%>
	        <%--===================重要的隐藏域内容=========== --%>
			    <%--开始引入公共隐藏域部分 --%>
				<jsp:include page="./commonhidden.jsp" />
			    <%--结束引入公共隐藏域部分 --%>
		    <%--===================重要的隐藏域内容=========== --%>
	    	
	        <%--开始引入消防设计基本信息部分 --%>
			<jsp:include page="./jgxk/jbxx.jsp" />
			<%--结束引入消防设计基本信息部分 --%>

			<%--申报信息--%>
			<jsp:include page="./applyuserinfo.jsp" />
			<%--申报信息--%>
			<%--开始引入申请表信息部分 --%>
			<jsp:include page="/webpage/website/applyforms/xfsj/applyForm.jsp" >
				<jsp:param value="PROJECT_FINISH_MANAGE_FORM" name="formId"/>
				<jsp:param value="21" name="materCode"/>
			</jsp:include>
			<%--结束引入申请表信息部分 --%>
			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>所需材料</span></li>
					</ul>
				</div>
				<jsp:include page="./matterListZF.jsp" >
                	<jsp:param value="1" name="applyType"/>
                	<jsp:param value="PROJECT_FINISH_MANAGE_FORM" name="formName"/>
            	</jsp:include>
			</div>
        </form>
        <%--开始引入提交DIV界面 --%>
        <div  id="submitDiv">
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="tempSaveFn();" name="tempSaveFn"/>
		</jsp:include>
		</div>
		<%--结束引入提交DIV界面 --%>
            
        <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
<%--         <c:if test="${EFLOWOBJ.HJMC!=null}">
	        <jsp:include page="xmlct.jsp" >
	           <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
	           <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
	        </jsp:include>
        </c:if> --%>
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