<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.core.util.JsonUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.ApplyMaterService"%>
<%@ page language="java" import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ page language="java" import="com.alibaba.fastjson.JSON"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
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
		busTableName:'${EFLOW_VARS.EFLOW_BUSTABLENAME}',
		uploadPath:'applyFormWebsite'
	});
	
	$("select[id^='RECEIVE_METHOD_']").change(function(){ 
		 var id = $(this).attr("id");
		 var selValue = $(this).val();
		 var materCode = id.replace("RECEIVE_METHOD_","");
		 var UploadId = "Upload_"+materCode;
		 var UploadedFiles = "UploadedFiles_"+materCode;
		 var UploadId_DZZZ = "Upload_DZZZ_"+materCode;
		 var UploadedFiles_DZZZ = "UploadedFiles_DZZZ_"+materCode;
		 if(selValue!="1"){
			 $("#"+UploadId).attr("style","display:none;");
			 $("#"+UploadedFiles).attr("style","display:none;");
		 }else{
			 $("#"+UploadId).attr("style","");
			 $("#"+UploadedFiles).attr("style","");
		 } 
		 if(selValue!="4"){
			 $("#"+UploadId_DZZZ).attr("style","display:none;");
			 $("#"+UploadedFiles_DZZZ).attr("style","display:none;");
		 }else{
			 $("#"+UploadId_DZZZ).attr("style","");
			 $("#"+UploadedFiles_DZZZ).attr("style","");
		 } 
		 
	  });
	
});

function delDZZZFile(fileId){
	art.dialog.confirm("您确定要删除该文件吗?", function() {
        var layload = layer.load("正在提交请求中…");
        $.post("fileAttachController.do?multiDel",{
                selectColNames :fileId
            }, function(responseText, status, xhr) {
                layer.close(layload);
                var resultJson = $.parseJSON(responseText);
                if(resultJson.success == true){
		    		$("#"+fileId).remove();
                }else{
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
        });
    });
	
}

function addDZZZ(MATER_CODE,LICENSE_CODE,LICENSE_NAME){
	var uploadedFilesSpans = $("div[id^='UploadedFiles_DZZZ_"+MATER_CODE+"'] > p");
	if(uploadedFilesSpans.length>5){
		art.dialog({
            content: "已经有五个验证照证通过,请勿重复验证",
            icon:"error",
            ok: true
        });
	}else{
		$.dialog.open("applyMaterController/certification.do?LICENSE_CODE="+LICENSE_CODE+"&LICENSE_NAME="+LICENSE_NAME,
				{
					title : "电子证照验证",
					width : "560px",
					height : "460px",
					lock : true,
					resize : false,
					close:function(){
						var certificationInfo = art.dialog.data("certificationInfo");
						if(certificationInfo){
							var UploadedFiles_DZZZ = "UploadedFiles_DZZZ_"+MATER_CODE;
							var newhtml = '';
							newhtml += '<p filepath="'+certificationInfo.filepath+'" filename="'+certificationInfo.filename+'" id="">';
							newhtml += ' <a href="javascript:void(0);" onclick="AppUtil.downLoadFileByFilePath(\''+certificationInfo.filename+'\',\''+certificationInfo.filepath+'\');" style="cursor: pointer;">';
							newhtml += '<font color="blue">'+certificationInfo.filename+'</font></a>';
							newhtml += ' <a href="javascript:void(0);" onclick="delDom(this);" style="cursor: pointer;">';
							newhtml += '<font color="red">删除</font></a>';
							newhtml += '</p>';
							$("#"+UploadedFiles_DZZZ).html(newhtml);
							art.dialog.removeData("certificationInfo");
						}
						
					}
				}, false);
		
	}
}

function delDom(e){
	$(e).closest("p").remove();
}
</script>

</head>

<body>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginMember.USER_ID}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginMember.YHMC}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}"/>
    <table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20 syj-tabtdcenter">
		<tr>
			<th style="width:30px;">序 号</th>
			<th style="width:300px;">材料名称</th>
			<th style="width:50px;">材料说明</th>
			<th style="width:200px;">附 件</th>
			<c:if test="${(EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_CUREXERUNNINGNODENAMES==EFLOW_VARS.EFLOW_STARTNODE_NAME)&&EFLOW_VARS.EFLOW_EXERUNSTATUS=='1'}">
			<th  style="width:40px;">补件状态</td>
			<th  style="width:130px;">补件说明</td>
			</c:if>
			<th style="width:110px;">提交方式</th>
			<th style="width:80px;">文件操作</th>
		</tr>
		<c:forEach items="${applyMaterList}" var="applyMater" varStatus="varStatus">
		<c:if test="${!empty applyMater.MATER_TYPENAME_TOP&&((EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_CUREXERUNNINGNODENAMES==EFLOW_VARS.EFLOW_STARTNODE_NAME)&&EFLOW_VARS.EFLOW_EXERUNSTATUS=='1')}">
		<tr>
			<td colspan="8" style="text-align: left;font-weight:bold">${applyMater.MATER_TYPENAME_TOP}</td>
		</tr>
		</c:if>
		<c:if test="${!empty applyMater.MATER_TYPENAME_TOP&&!((EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_CUREXERUNNINGNODENAMES==EFLOW_VARS.EFLOW_STARTNODE_NAME)&&EFLOW_VARS.EFLOW_EXERUNSTATUS=='1')}">
		<tr>
			<td colspan="6" style="text-align: left;font-weight:bold">${applyMater.MATER_TYPENAME_TOP}</td>
		</tr>
		</c:if>
		<tr>
			<th>${applyMater.MATER_INDEX}</th>
			<td>
			<%--判断是否必填 --%> 
			<c:if test="${applyMater.MATER_ISNEED=='1'}">
					<font color="#ff0101">*</font>
			</c:if>
			${applyMater.MATER_NAME}
			<%--判断是否有样本 --%> 
			<c:if test="${EFLOW_VARS.EFLOW_EXERUNSTATUS == null || EFLOW_VARS.EFLOW_EXERUNSTATUS==0}">
			<c:if test="${applyMater.MATER_PATH!=null}">
				<%-- <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');" style="color:#F00;">[样本]</a> --%>
				<c:set var="count" value="1"></c:set>
				 <e:for paras="${applyMater.MATER_ID}:${applyMater.MATER_ID}" filterid="1" end="5" var="ef" dsid="105">
                                        	<a href="javascript:void(0);" style="color:#F00;" onclick="AppUtil.downLoadFileByFilePath('${ef.FILE_NAME}','${ef.FILE_PATH }');">[样本${count}]</a>
                                        	<c:set var="count" value="${ count+1}"></c:set>
                                        </e:for>
			</c:if>
			</c:if>
			</td>
			<td>${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份</td>
			<td>
			    <div id="UploadedFiles_${applyMater.MATER_CODE}" <c:if test="${!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=1}">style="display: none;"</c:if>>
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
					             <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE!='false'}">
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
			<c:if test="${(EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_CUREXERUNNINGNODENAMES==EFLOW_VARS.EFLOW_STARTNODE_NAME)&&EFLOW_VARS.EFLOW_EXERUNSTATUS=='1'}">
			<td <c:if test="${applyMater.BJZT=='补件'}">style="color:red;"</c:if>>${applyMater.BJZT}</td>
			<td>${applyMater.BJYJ}</td>
			</c:if>
			<td>
				<c:if test="${applyMater.IS_LICENSE!='1'}">
				<fda:fda-exselect id="RECEIVE_METHOD_${applyMater.MATER_CODE}" name="RECEIVE_METHOD_SELECT" staticlables="未提交,电子档上传,窗口纸质提交"
					staticvalues="3,1,2" style="width:100px;" allowblank="false" value="${applyMater.RECEIVE_METHOD}" 
					disabled="${EFLOW_VARS.EFLOW_IS_START_NODE=='false'?'disabled':''}"></fda:fda-exselect>
				</c:if>
				<c:if test="${applyMater.IS_LICENSE=='1'}">
				<fda:fda-exselect id="RECEIVE_METHOD_${applyMater.MATER_CODE}" name="RECEIVE_METHOD_SELECT" staticlables="未提交,电子档上传,窗口纸质提交,电子证照验证"
					staticvalues="3,1,2,4" style="width:100px;" allowblank="false" value="${applyMater.RECEIVE_METHOD}" 
					disabled="${EFLOW_VARS.EFLOW_IS_START_NODE=='false'?'disabled':''}"></fda:fda-exselect>
				</c:if>
			<td>
			  <div id="Upload_${applyMater.MATER_CODE}" 
			     <c:if test="${empty applyMater.RECEIVE_METHOD||(!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=1)||EFLOW_VARS.EFLOW_ISQUERYDETAIL=='true'}">style="display: none;"</c:if>
			   
			   >
			    	 <img id="${applyMater.MATER_CODE}" src="webpage/common/images/tab_btn_sc.png" />
			     </div>
			   <div id="Upload_DZZZ_${applyMater.MATER_CODE}"
			   	 <c:if test="${empty applyMater.RECEIVE_METHOD||(!empty applyMater.RECEIVE_METHOD&&applyMater.RECEIVE_METHOD!=4)||EFLOW_VARS.EFLOW_ISQUERYDETAIL=='true'}">style="display: none;"</c:if>
			   >
			   		<input onclick="addDZZZ('${applyMater.MATER_CODE}','${applyMater.LICENSE_TYPE_CODE}','${applyMater.LICENSE_TYPE_NAME}');" style="padding-left: 0px; padding-right: 0px;" class="extract-button" value="点击验证" type="button">
			   </div>
			</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>
