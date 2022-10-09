<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="syj-title1" style="height:30px;">
	<span>事项基本信息(<font color="#ff0101">*</font>为必填)</span>
</div>
<table cellpadding="0" cellspacing="1" class="syj-table2">
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"> 审批事项：</th>
		<td>${serviceItem.ITEM_NAME}</td>
		<th style="border-bottom-width: 1px;width:180px;"> 承诺时间(工作日):</th>
		<td >${serviceItem.CNQXGZR}</td>
	</tr>
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"> 牵头部门：</th>
		<td >${serviceItem.SSBMMC}</td>	
		<th style="border-bottom-width: 1px;width:180px;"> 审批类型：</th>
		<td>${serviceItem.SXLX}</td>
	</tr>
	
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 申报名称：</th>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" style="width: 70%" maxlength="60" name="SBMC" value="${busRecord.SBMC }"/>
			<span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span>
		</td>
	</tr>
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"> 申报号：</th>
		<td id = "EXEID" colspan="3"></td>
	</tr>
</table>