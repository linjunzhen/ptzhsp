<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
	String applyType = request.getParameter("applyType");
  	request.setAttribute("applyType", applyType);
  	String isWebsite = request.getParameter("isWebsite");
  	request.setAttribute("isWebsite", isWebsite);
%>
<script type="text/javascript">
    var  onlineBusTableName = "${serviceItem.FORM_KEY}";
    var uploadUserId = $("input[name='uploadUserId']").val();

	function chagediv(selectid){
		var selectValue = $("#SQFS_"+selectid).val();
		if(selectValue=="1"){
	        $("#div1_"+selectid).attr("style","");
	        $("#div2_"+selectid).attr("style","display:none;");
	        $("#UploadedFiles_"+selectid).attr("style","");
	    }else{
	    	$("#div1_"+selectid).attr("style","display:none;");
	        $("#div2_"+selectid).attr("style","");
	        $("#UploadedFiles_"+selectid).attr("style","display:none;");
	    }
	}
	
	function showMater(code){
		if($("#"+code+"_a").html() == "展开"){
			$("."+code).show();		
			$("#"+code+"_a").html("收起");			
		}else{			
			$("."+code).hide();	
			$("#"+code+"_a").html("展开");
		}
	}
	
	function setUploadHidden(attachKey){
        $("#"+attachKey+"__SC").attr("style","display: none;");
        $("#"+attachKey+"__SCAN").attr("style","display: none;");
    }
	
	function bindScanCtrl(attachKey, matreClsm){
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/scan.jsp?uploadPath=oldAgeCard&attachKey="+attachKey+
	              "&uploadUserId="+uploadUserId+"&uploadUserName="+uploadUserName+"&busTableName="+onlineBusTableName+"&scanLimit=1", {
		    title: "良田高拍仪XF660R-文档拍摄页",
			width: "810px",
			lock: true,
			resize: true,
			height: "97%",
			close: function(){
			    var resultJsonInfo = art.dialog.data("docScannerResult");
				if(resultJsonInfo && resultJsonInfo.length > 0){
					initScanUploadMaters(resultJsonInfo, matreClsm);
					art.dialog.removeData("docScannerResult");
				}
			}
		});
	}
	
	function initScanUploadMaters(resultJson,matreClsm){
		for(var i = 0; i < resultJson.length; i++){	
			//获取文件ID		
			var fileId = resultJson[i].fileId;
			//获取文件名称
			var fileName = resultJson[i].fileName;
			//获取材料KEY
			var attachKey = resultJson[i].attachKey;
			//获取文件路径
			var filePath = resultJson[i].filePath;
			//获取文件的类型
			var fileType = resultJson[i].fileType;
			//获取是否是图片类型
			var isImg = resultJson[i].isImg;
			var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
			spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
			spanHtml +="<font color=\"blue\">"+fileName+"</font></a>";
			spanHtml += "<a href=\""+__ctxPath+"/"+filePath+"\" data-lightbox=\""+fileId+"\" data-title=\""+fileName+"\"><font color=\"blue\">预览</font></a>";
			spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>";
			$("#UploadedFiles_"+attachKey).append(spanHtml);
		}
	}
	
	function setWordHidden(attachKey){
		
	}
	
	function setWordShow(attachKey){
		
	}
</script>
<style>
.materBtnA {
    background: #62a1cf none repeat scroll 0 0;
    color: #fff;
    display: inline-block;
    height: 26px;
    left: -1px;
    line-height: 26px;
    margin-left: 5px;
    position: relative;
    text-align: center;
    top: 1px;
    width: 59px;
}
</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro1">
	<tr>
	    <c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
			<th colspan="6">申请人材料信息</th>
		</c:if>
	    <c:if test="${empty nodeConfig || nodeConfig.UPLOAD_FILES!='1'}">
            <th colspan="5">代理人材料信息</th>
        </c:if>
	</tr>
	<tr>
		<td class="tab_width1" width="50px">序号</td>
		<td class="tab_width1" width="400px">材料名称</td>
		<td class="tab_width1" width="80px">材料说明</td>
		<td class="tab_width1">附件</td>
		<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
			<td class="tab_width1" width="200px">文件操作</td>
		</c:if>
	</tr>
	<c:forEach items="${applyMaters}" var="applyMater"
		varStatus="varStatus">
		<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if> >
			<td>${varStatus.index+1}</td>
			<td>
				<%--判断是否必填 --%> <c:if test="${applyMater.MATER_ISNEED=='1'}">
					<font class="tab_color">*</font>
				</c:if> ${applyMater.MATER_NAME} <%--判断是否有样本 --%> <c:if
					test="${applyMater.MATER_PATH!=null}">
					<a href="javascript:void(0);"
						onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');"
						style="color:#F00;">[样本]</a>
				</c:if>
			</td>
			<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX=='综合件'}">
				<td colspan="4">
					<a class="materBtnA" style="color:#fff; float: right; margin-right: 15px;" href="javascript:showMater('${applyMater.MATER_CODE}')" id="${applyMater.MATER_CODE}_a">收起</a>
				</td>
			</c:if>
			<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!='综合件'}">
			<td>			 
			    ${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份
			</td>
			<td>
				<div id="UploadedFiles_${applyMater.MATER_CODE}"
					<c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
					<c:forEach var="uploadFile" items="${applyMater.uploadFiles}">
						<c:if test="${applyMater.UPLOADED_SIZE!=0}">	
							<p id="${uploadFile.FILE_ID}" >
					             <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');" style="cursor: pointer;">
					              	<font color="blue">${uploadFile.FILE_NAME}</font>
					             </a>
					             <c:if test="${uploadFile.IS_IMG == 1}">
					                 <a href="${_file_Server }${uploadFile.FILE_PATH}" data-lightbox="${uploadFile.FILE_ID }" data-title="${uploadFile.FILE_NAME }">
					                     <font color="blue">预览</font>
					                 </a>
					             </c:if>
					              <c:if test="${EFLOW_IS_START_NODE!='false'&&isQueryDetail!='true'}">
					             	<a href="javascript:void(0);" onclick="AppUtil.delUploadMater('${uploadFile.FILE_ID}','${uploadFile.ATTACH_KEY}');">
                                		<font color="red">删除</font>
                                	</a>
                               	</c:if>
					        </p>
						</c:if>
					</c:forEach>
				</div>
			</td>
			<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
			<input type="hidden" id="SQFS_${applyMater.MATER_CODE }" value="1"/>
			<td>
				<div id="div1_${applyMater.MATER_CODE}" 
					<c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
	                <div id="${applyMater.MATER_CODE}__SC" style="width: 80px;float: left;" >
						<a href="javascript:void(0);" onclick="openTitleFileUploadDialog('${applyMater.MATER_TYPE}','${applyMater.MATER_CODE}')">
							<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
					   </a>
				    </div>
					<div id="${applyMater.MATER_CODE}__SCAN" 
						<c:if test="${isWebsite=='2'}">style="float: left;"</c:if>
						<c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>
						<a href="javascript:bindScanCtrl('${applyMater.MATER_CODE}','${applyMater.MATER_CLSM}')"><img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/></a>
					</div>
				</div>
				<div id="div2_${applyMater.MATER_CODE}"
					<c:if test="${applyMater.SQFS!=2}">style="display: none;"</c:if>>
					<select type="select" class="tab_text" style="width: 90px;"
						id="SFSQ_${applyMater.MATER_CODE}">
						<option value="1"
							<c:if test="${applyMater.SFSQ==1}">selected="true"</c:if>>
							已收取</option>
						<option value="-1"
							<c:if test="${applyMater.SFSQ==-1}">selected="true"</c:if>>
							未收取</option>
					</select>
				</div>
			</td>
			</c:if>
			</c:if>
		</tr>
	</c:forEach>
	<!-- 共性材料信息 -->
	<c:if test="${materListValue == true }">
		<tr>
			<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
			<td colspan="5">共性材料信息</th>
			</c:if>
			<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES!='1'}">
			<td colspan="4">共性材料信息</th>
			</c:if>
		</tr>
		<tr>
			<td class="tab_width1" width="50px">序号</td>
			<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
				<td class="tab_width1" colspan="3">材料名称</td>
			</c:if>
			<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES!='1'}">
				<td class="tab_width1" colspan="2">材料名称</td>
			</c:if>
	        <td class="tab_width1">上传时间</td>
		</tr>
		<c:forEach items="${materList}" var="materListInfo" varStatus="materst">
			<tr>
				<td>${materst.index+1}</td>
				<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
					<td colspan="3" title="${materListInfo.FILE_NAME}">
						<a style="color: blue;" title="${materListInfo.FILE_NAME}"
							href="${fileServer }download/fileDownload?attachId=${materListInfo.FILE_ID}&attachType=${materListInfo.ATTACH_TYPE}" >
							${materListInfo.FILE_NAME}
						</a>
					</td>
				</c:if>
				<c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES!='1'}">
					<td colspan="2" title="${materListInfo.FILE_NAME}">
						<a style="color: blue;" title="${materListInfo.FILE_NAME}"
							href="${fileServer }download/fileDownload?attachId=${materListInfo.FILE_ID}&attachType=${materListInfo.ATTACH_TYPE}" >
							${materListInfo.FILE_NAME}
						</a>
					</td>
				</c:if>
				<td>${materListInfo.CREATE_TIME}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
