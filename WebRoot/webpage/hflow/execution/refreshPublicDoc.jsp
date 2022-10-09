<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<c:if test="${publicDocRule=='-1'}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" >
	<tr>
		<th colspan="3">公文流转至：</th>
	</tr>
	<c:forEach items="${publicDocList}" var="publicDoc">
		<tr>
			<td colspan="3">${publicDoc.ASSIGNER_NAME}(${publicDoc.ASSIGNER_DEPNAME})<c:if test="${IS_MARTER==1}"><a class="materBtnA" style="color:#fff;float: right;" href="javascript:bindMater('${publicDoc.ASSIGNER_CODE}')" >指派审核材料</a></c:if></td>
		</tr>
		<c:forEach items="${publicDoc.materList}" var="mater" varStatus="varStatus">
		<tr>
			<td class="tab_widthnum">${varStatus.index+1}</td>
			<td>${mater.DOCUMENT_NAME}</td>
			<td width="260px">
			    <input type="hidden" sfhzd="-1" filename='${mater.FILE_NAME}' value="${mater.FILE_PATH}" filepath="${mater.FILE_PATH}" tplid="${mater.TPL_ID}" xnbid="" authusername="${publicDoc.ASSIGNER_NAME}" authusercode="${publicDoc.ASSIGNER_CODE}" name="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_FILE_PATH">
			    <a href="javascript:void(0);" <c:if test="${!empty mater.FILE_PATH}">style="display: none;" </c:if>
			    id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_ZXBJ" onclick="showDocumentTemplateWindow('${mater.TPL_ID}','${publicDoc.ASSIGNER_CODE}','-1','${mater.DOCUMENT_NAME}','${mater.SFHTML}');" class="tab_btn_tj1 tab_tk_pro2btn">
			           在线编辑
			    </a>
			    <span id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_DIV">
					<a href="javascript:void(0);" onclick="openAuditMatersFileUploadDialog('${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_')">
						<img id="${mater.TPL_ID}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				   </a>
                </span>
                <span id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_DIV2">
                    <c:if test="${!empty mater.FILE_PATH}">
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"
                        onclick="AppUtil.downLoadFile('${mater.FILE_ID}');">下载</a>
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"  onclick="delfile('${mater.FILE_ID}','${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_');" >删除</a>
                    </c:if>
                </span>
                <a id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_YL" onclick="ylDocTempalte('${mater.TPL_ID}','${publicDoc.ASSIGNER_CODE}');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">预览</a>
				<a id="${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_A" onclick="deleteDocTempalte('${mater.TPL_ID}','${publicDoc.ASSIGNER_CODE}','-1');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">删除</a>
			</td>
		</tr>
		</c:forEach>
	</c:forEach>
</table>
</c:if>

<c:if test="${publicDocRule=='1'}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" >
	<tr>
		<th colspan="3">公文流转</th>
	</tr>
	<c:forEach items="${publicDocList}" var="mater" varStatus="varStatus">
		<tr>
			<td class="tab_widthnum">${varStatus.index+1}</td>
			<td>${mater.DOCUMENT_NAME}</td>
			<td width="260px">
			    <input type="hidden" sfhzd="-1" filename='${mater.FILE_NAME}' value="${mater.FILE_PATH}" filepath="${mater.FILE_PATH}" tplid="${mater.TPL_ID}" xnbid="" authusername="${mater.AUTH_USER_NAMES}" authusercode="${mater.AUTH_USER_CODES}" name="${mater.TPL_ID}__FILE_PATH">
			    <a href="javascript:void(0);" <c:if test="${!empty mater.FILE_PATH}">style="display: none;" </c:if>
			    id="${mater.TPL_ID}__ZXBJ" onclick="showDocumentTemplateWindow('${mater.TPL_ID}','','-1','${mater.DOCUMENT_NAME}','${mater.SFHTML}');" class="tab_btn_tj1 tab_tk_pro2btn">
			           在线编辑
			    </a>
			    <span id="${mater.TPL_ID}__DIV">
					<a href="javascript:void(0);" onclick="openAuditMatersFileUploadDialog('${mater.TPL_ID}__')">
						<img id="${mater.TPL_ID}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				   </a>
                </span>
                <span id="${mater.TPL_ID}__DIV2">
                    <c:if test="${!empty mater.FILE_PATH}">
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"
                        onclick="AppUtil.downLoadFile('${mater.FILE_ID}');">下载</a>
                     <a href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn"  onclick="delfile('${mater.FILE_ID}','${mater.TPL_ID}_${publicDoc.ASSIGNER_CODE}_');" >删除</a>
                    </c:if>
                </span>
                <a id="${mater.TPL_ID}__YL" onclick="ylDocTempalte('${mater.TPL_ID}','');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">预览</a>
				<a id="${mater.TPL_ID}__A" onclick="deleteDocTempalte('${mater.TPL_ID}','','-1');" href="javascript:void(0);" class="tab_btn_ct1 tab_tk_pro2btn" style="display: none;">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
</c:if>