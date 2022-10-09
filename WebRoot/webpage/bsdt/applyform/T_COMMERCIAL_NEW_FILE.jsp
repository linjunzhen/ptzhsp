<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<c:if test="${execution.ISNEEDSIGN != 1}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
<tr>
	<th colspan="4" >前台用户上传附件</th>
</tr>
<tr>
	<td class="tab_width">法定代表人身份证正面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.LEGAL_SFZZM!=null&&busRecord.LEGAL_SFZZM!=''}">
				<img id="LEGAL_SFZZM_IMG" src="${_file_Server}${busRecord.LEGAL_SFZZM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZZM_IMG','LEGAL_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="LEGAL_SFZZM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZZM_IMG','LEGAL_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="LEGAL_SFZZM" value="${busRecord.LEGAL_SFZZM}">
	</td>
	<td class="tab_width">法定代表人身份证反面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.LEGAL_SFZFM!=null&&busRecord.LEGAL_SFZFM!=''}">
				<img id="LEGAL_SFZFM_IMG" src="${_file_Server}${busRecord.LEGAL_SFZFM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZFM_IMG','LEGAL_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="LEGAL_SFZFM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZFM_IMG','LEGAL_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="LEGAL_SFZFM" value="${busRecord.LEGAL_SFZFM}">
	</td>
</tr>
<tr>
	<td class="tab_width">经办人身份证正面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.OPERATOR_SFZZM!=null&&busRecord.OPERATOR_SFZZM!=''}">
				<img id="OPERATOR_SFZZM_IMG" src="${_file_Server}${busRecord.OPERATOR_SFZZM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZZM_IMG','OPERATOR_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="OPERATOR_SFZZM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZZM_IMG','OPERATOR_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="OPERATOR_SFZZM"  value="${busRecord.OPERATOR_SFZZM}">
	</td>
	<td class="tab_width">经办人身份证反面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.OPERATOR_SFZFM!=null&&busRecord.OPERATOR_SFZFM!=''}">
				<img id="OPERATOR_SFZFM_IMG" src="${_file_Server}${busRecord.OPERATOR_SFZFM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZFM_IMG','OPERATOR_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="OPERATOR_SFZFM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZFM_IMG','OPERATOR_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="OPERATOR_SFZFM"  value="${busRecord.OPERATOR_SFZFM}">
	</td>
</tr>
</table>
</c:if>