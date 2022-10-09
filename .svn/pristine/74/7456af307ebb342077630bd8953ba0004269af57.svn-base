<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}QSZ_NAME"  placeholder="请输入姓名"   maxlength="16" value="${busRecord.QSZ_NAME}"/></td>
			<th> 职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="QSZCYZW"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择职务" name="${currentTime}QSZ_JOB"   value="${busRecord.QSZ_JOB}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}QSZ_IDNO');"
				defaultEmptyText="请选择证件类型" name="${currentTime}QSZ_IDTYPE" id="${currentTime}QSZ_IDTYPE"   value="SF">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[vidcard]" 
				name="${currentTime}QSZ_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.QSZ_IDNO}"/></td>
		</tr>			
		<tr>
			<th><font class="syj-color2">*</font>地址：</th>
			<td colspan="3"><input type="text" class="syj-input1  w878 validate[required]"
				name="${currentTime}QSZ_ADDR"  placeholder="请输入地址"  value="${busRecord.QSZ_ADDR}"  maxlength="64"/></td>
		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>联系方式：</th>
			<td colspan="3"><input type="text" class="syj-input1  validate[required]"
				name="${currentTime}QSZ_MOBILE"  placeholder="请输入联系方式"  value="${busRecord.QSZ_MOBILE}"  maxlength="16"/></td>
		</tr>
	</table>
	<a href="javascript:void(0);"
			onclick="delGdxxDiv(this);" class="syj-closebtn"></a>
</div>
	

