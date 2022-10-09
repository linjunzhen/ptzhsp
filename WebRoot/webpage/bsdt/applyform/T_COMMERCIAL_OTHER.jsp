<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >其他信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 登记机关：</td>
		<td colspan="3"><input type="text" class="tab_text"  disabled="disabled"
			name="REGISTER_AUTHORITY" value="平潭综合实验区行政审批局" /></td>
	</tr>
	<tr>
		<td class="tab_width"> 批准机构：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="APPROVAL_AUTHORITY" value="平潭综合实验区行政审批局" /></td>
		<td class="tab_width"> 批准机构代码：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="AUTHORITY_CODE" value="003678642" /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>是否申请电子营业执照：</td>
		<td >
			<input type="radio" class="radio" name="IS_APPLY" value="1" checked="checked"/>是
		</td>
		<td class="tab_width"><font class="tab_color">*</font>申请营业执照副本数量：</td>
		<td>
		<c:choose>
		<c:when test="${busRecord.LICENSE_NUM==null||busRecord.LICENSE_NUM=='' }">
			<input type="text" class="tab_text validate[required,custom[integerplus]]"
				name="LICENSE_NUM" value="1" />本
		</c:when>
		<c:otherwise>
			<input type="text" class="tab_text validate[required,custom[integerplus]]"
				name="LICENSE_NUM" value="${busRecord.LICENSE_NUM }"/>本
		</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<c:if test="${ null != busRecord.IS_SECRET && ''!=busRecord.IS_SECRET}">
	 <tr>
		<td class="tab_width"><font class="tab_color">*</font>是否涉密单位：</td>
		<td colspan="3">
			<input type="radio" class="radio" name="IS_SECRET" value="1" <c:if test="${busRecord.IS_SECRET==1 }">checked="checked"</c:if>  />是
			<input type="radio" class="radio" name="IS_SECRET" value="0" <c:if test="${busRecord.IS_SECRET==0 }">checked="checked"</c:if>  />否
		</td>
	</tr> 
	</c:if> 
	<%-- <tr>
		<td class="tab_width"><font class="tab_color">*</font>申请组织机构代码证电子副本数量：</td>
		<td>
		<c:choose>
		<c:when test="${busRecord.LICENSE_NUM==null||busRecord.LICENSE_NUM=='' }">
			<input type="text" class="tab_text validate[required,custom[integerplus]]"
				name="CERTIFICATE_NUM" value="1" />本
		</c:when>
		<c:otherwise>
			<input type="text" class="tab_text validate[required,custom[integerplus]]"
				name="CERTIFICATE_NUM" value="${busRecord.CERTIFICATE_NUM }"/>本
		</c:otherwise>
		</c:choose>
		</td>
	</tr> --%>
	<%-- <tr id="freeimport" style="display: none;">
		<td class="tab_width"><font class="tab_color">*</font>是否申请进口设备免税：</td>
		<td colspan="3">
			<input type="radio" class="radio" name="IS_FREEIMPORT" value="1" <c:if test="${busRecord.IS_FREEIMPORT==1 }">checked="checked"</c:if> />是
			<input type="radio" class="radio" name="IS_FREEIMPORT" value="0" <c:if test="${busRecord.IS_FREEIMPORT==0 }">checked="checked"</c:if> />否
		</td>
	</tr> --%>
	<tr>
		<td class="tab_width">填表日期：</td>
		<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
			name="FILL_DATE" value="${busRecord.FILL_DATE }"/></td>
	</tr>
</table>
