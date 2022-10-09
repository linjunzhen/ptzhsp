<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.core.util.JsonUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.ApplyMaterService"%>
<%@ page language="java" import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ page language="java" import="com.alibaba.fastjson.JSON"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ApplyMaterService applyMaterService = (ApplyMaterService)AppUtil.getBean("applyMaterService");
String EFLOW_VARS_JSON = request.getParameter("EFLOW_VARS_JSON");
EFLOW_VARS_JSON = StringEscapeUtils.unescapeHtml3(EFLOW_VARS_JSON);
List<Map<String,Object>> applyMaterList = applyMaterService.findByflowVarJson(EFLOW_VARS_JSON);
String applyMatersJson = JsonUtil.jsonStringFilter(applyMaterList,new String[]{"uploadFiles"},true);
request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
request.setAttribute("applyMaterList",applyMaterList);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
$(function(){
	//初始化材料列表
	AppUtil.initNetUploadMaters({
		busTableName:'${EFLOW_VARS.EFLOW_BUSTABLENAME}'
	});
	$("select[id^='RECEIVE_METHOD_']").change(function(){ 
		 var id = $(this).attr("id");
		 var selValue = $(this).val();
		 var materCode = id.replace("RECEIVE_METHOD_","");
		 var UploadId = "Upload_"+materCode;
		 var UploadedFiles = "UploadedFiles_"+materCode;
		 if(selValue!="1"){
			 $("#"+UploadId).attr("style","display:none;");
			 $("#"+UploadedFiles).attr("style","display:none;");
		 }else{
			 $("#"+UploadId).attr("style","");
			 $("#"+UploadedFiles).attr("style","");
		 }
	  });
	$("select[id='APPLY_ALL_SELECT']").change(function(){ 
		var allSelectVal = $(this).val();
		$("select[id^='RECEIVE_METHOD_']").each(function(){
			$(this).val(allSelectVal);
			 var id = $(this).attr("id");
			 var selValue = $(this).val();
			 var materCode = id.replace("RECEIVE_METHOD_","");
			 var UploadId = "Upload_"+materCode;
			 var UploadedFiles = "UploadedFiles_"+materCode;
			 if(selValue!="1"){
				 $("#"+UploadId).attr("style","display:none;");
				 $("#"+UploadedFiles).attr("style","display:none;");
			 }else{
				 $("#"+UploadId).attr("style","");
				 $("#"+UploadedFiles).attr("style","");
			 }
		});
	});
});
</script>

</head>

<body>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}"/>
    <div class="syj-title1"><span>材料列表</span></div>
	<table cellpadding="0" cellspacing="0" class="eui-table1">
		
		<tr>
			<td class="eui-bg5" style="width: 30px;">序号</td>
			<td class="eui-bg5" style="width: 300px;">材料名称</td>
			<td class="eui-bg5" style="width: 50px;">材料说明</td>
			<td class="eui-bg5" style="width: 200px;">附件</td>
			<td class="eui-bg5" style="width: 30px;">补件状态</td>
			<td class="eui-bg5" style="width: 50px;">补件说明</td>
			<td class="eui-bg5" style="width: 80px;">
			收取方式
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
			<fda:fda-exselect id="APPLY_ALL_SELECT" name="APPLY_ALL_SELECT" staticlables="未收取,纸质收取,上传"
					staticvalues="3,2,1" style="width:100px;" allowblank="false" 
					></fda:fda-exselect>
			</c:if>
			</td>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
			<td class="eui-bg5" style="width: 80px;">文件操作</td>
			</c:if>
			
			
		</tr>
		<c:forEach items="${applyMaterList}" var="applyMater" varStatus="varStatus">
		<c:if test="${!empty applyMater.MATER_TYPENAME_TOP&&(EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true')}">
		<tr>
			<td colspan="8" style="text-align: left;font-weight:bold">${applyMater.MATER_TYPENAME_TOP}</td>
		</tr>
		</c:if>
		<c:if test="${!empty applyMater.MATER_TYPENAME_TOP&&!(EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true')}">
		<tr>
			<td colspan="7" style="text-align: left;font-weight:bold">${applyMater.MATER_TYPENAME_TOP}</td>
		</tr>
		</c:if>
		<tr>
			<td>${applyMater.MATER_INDEX}</td>
			<td>
			<%--判断是否必填 --%> 
			<c:if test="${applyMater.MATER_ISNEED=='1'}">
					<font color="#ff0101">*</font>
			</c:if>
			${applyMater.MATER_NAME}
			<%--判断是否有样本 --%> 
			<c:if test="${applyMater.MATER_PATH!=null}">
				<a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');" style="color:#F00;">[样本]</a>
			</c:if>
			</td>
			<td>${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份</td>
			<td>
			   <%-- 
				<p>
					<a href="javascript:void(0);" style="cursor: pointer;"> <font
						color="blue"> 范德萨飞机迪斯科范德萨健康范德萨健康.docx </font>
					</a> <a href="javascript:void(0);"> <font color="red">删除</font>
					</a>
				</p>
				<p>
					<a href="javascript:void(0);" style="cursor: pointer;"> <font
						color="blue"> 身份证2.jpg </font>
					</a> <a href="javascript:void(0);"> <font color="red">删除</font>
					</a>
				</p>
				--%>
				<div id="UploadedFiles_${applyMater.MATER_CODE}" <c:if test="${!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=1}">style="display: none;"</c:if>
				<c:if test="${empty applyMater.RECEIVE_METHOD}">style="display: none;"</c:if>
				>
				    <c:forEach var="uploadFile" items="${applyMater.uploadFiles}">
						<c:if test="${applyMater.UPLOADED_SIZE!=0&&applyMater.UPLOADED_SIZE!=-1}">	
							<p id="${uploadFile.FILE_ID}" >
					             <a href="javascript:void(0);"
                                onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');"
                                 style="cursor: pointer;">
					              <font color="blue">
					                           ${uploadFile.FILE_NAME}
					              </font>
					             </a>
					             <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE!='false'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
					             <a href="javascript:void(0);"
                                onclick="AppUtil.delUploadMater('${uploadFile.FILE_ID}','${uploadFile.ATTACH_KEY}');">
                                <font color="red">删除</font></a>
                                </c:if>
					        </p>
						</c:if>
					</c:forEach>
				</div>
				<div id="UploadedFiles_DZZZ_${applyMater.MATER_CODE}" <c:if test="${!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=4}">style="display: none;"</c:if>>
					<c:forEach var="uploadFile" items="${applyMater.uploadFiles}">
						<c:if test="${applyMater.UPLOADED_SIZE==-1}">	
							<p id="${uploadFile.FILE_ID}" filepath="${uploadFile.FILE_PATH}" filename="${uploadFile.FILE_NAME}">
					             <a href="javascript:void(0);"
                                 onclick="AppUtil.downLoadFileByFilePath('${uploadFile.FILE_NAME}','${uploadFile.FILE_PATH}');"
                                 style="cursor: pointer;">
					              <font color="blue">
			                           ${uploadFile.FILE_NAME}
					              </font>
					             </a>
					             <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE!='false'}">
					             <a href="javascript:void(0);"
                                onclick="delDZZZFile('${uploadFile.FILE_ID}');">
                                <font color="red">删除</font></a>
                                </c:if>
					        </p>
						</c:if>
					</c:forEach>
				</div>
			</td>
			<td width="100px" <c:if test="${applyMater.BJZT=='补件'}">style="color:red;"</c:if>>${applyMater.BJZT}</td>
			<td width="100px">${applyMater.BJYJ}</td>
			<td>
				<c:if test="${(!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=4)||empty applyMater.RECEIVE_METHOD}">
				<fda:fda-exselect id="RECEIVE_METHOD_${applyMater.MATER_CODE}" name="RECEIVE_METHOD_SELECT" staticlables="未收取,纸质收取,上传"
					staticvalues="3,2,1" style="width:100px;" allowblank="false" value="${applyMater.RECEIVE_METHOD}" 
					disabled="${(EFLOW_VARS.EFLOW_IS_START_NODE=='false'&&EFLOW_VARS.EFLOW_FROMHISTROY!='true')?'disabled':''}"></fda:fda-exselect>
				</c:if>
				<c:if test="${!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD==4}">
				<fda:fda-exselect id="RECEIVE_METHOD_${applyMater.MATER_CODE}" name="RECEIVE_METHOD_SELECT" staticlables="未收取,纸质收取,上传,电子证照验证"
					staticvalues="3,2,1,4" style="width:100px;" allowblank="false" value="${applyMater.RECEIVE_METHOD}" 
					disabled="${(EFLOW_VARS.EFLOW_IS_START_NODE=='false'&&EFLOW_VARS.EFLOW_FROMHISTROY!='true')?'disabled':''}"></fda:fda-exselect>
				</c:if>
			</td>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
			<td>
				<div id="Upload_${applyMater.MATER_CODE}" <c:if test="${!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=1}">style="display: none;"</c:if>
				<c:if test="${empty applyMater.RECEIVE_METHOD}">style="display: none;"</c:if>
				>
			    	 <img id="${applyMater.MATER_CODE}" src="webpage/common/images/tab_btn_sc.png" />
			     </div>
			</td>
			</c:if>
				
		</tr>
		</c:forEach>
		
	</table>
</body>
</html>
