<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>
<c:forEach items="${holderList}" var="holder" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
	
		<tr>
			<th><font class="syj-color2">*</font>股东姓名/名称：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32" value="${holder.SHAREHOLDER_NAME}"/></td>
			<th><font class="syj-color2">*</font>股东类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgdlx"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setGdlxValidate(this.value,'${s.index}LEGAL_PERSON');setGdlxValidate(this.value,'${s.index}LEGAL_IDNO_PERSON');setGdlxValidateToType(this.value,'${s.index}LICENCE_TYPE','${s.index}LICENCE_NO');"
				defaultEmptyText="请选择股东类型" name="${s.index}SHAREHOLDER_TYPE" value="${holder.SHAREHOLDER_TYPE}">
				</eve:eveselect>
				<script type="text/javascript">
					$(function() {
						setGdlxValidate('${holder.SHAREHOLDER_TYPE}','${s.index}LEGAL_PERSON');			
						setGdlxValidate('${holder.SHAREHOLDER_TYPE}','${s.index}LEGAL_IDNO_PERSON');		
						setGdlxValidateToType('${holder.SHAREHOLDER_TYPE}','${s.index}LICENCE_TYPE','${s.index}LICENCE_NO');					
					});
				</script>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top: -1px;">
		<tr>
			<th><font class="syj-color2">*</font>证照类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${s.index}LICENCE_NO');" 
				defaultEmptyText="请选择证照类型" name="${s.index}LICENCE_TYPE" value="${holder.LICENCE_TYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="${s.index}LICENCE_NO"  placeholder="请输入证件号码" maxlength="32" value="${holder.LICENCE_NO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>身份证件地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required]"
				name="${s.index}ID_ADDRESS" placeholder="请输入身份证件地址" maxlength="64" value="${holder.ID_ADDRESS}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>联系方式：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="${s.index}CONTACT_WAY"  placeholder="请输入联系方式" maxlength="16" value="${holder.CONTACT_WAY}"/></td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
							   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
							   name="${s.index}SHAREHOLDER_COMPANYCOUNTRY" value="${holder.SHAREHOLDER_COMPANYCOUNTRY}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 投资企业或其他组织机构法定代表人姓名：</th>
			<td ><input type="text" class="syj-input1"
				name="${s.index}LEGAL_PERSON"  placeholder="请输入法定代表人姓名" maxlength="32" value="${holder.LEGAL_PERSON}"/></td>
			<th> 法定代表人身份证号码：</th>
			<td ><input type="text" class="syj-input1  validate[]"
						name="${s.index}LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码" value="${holder.LEGAL_IDNO_PERSON}" maxlength="32"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0 && isClose!='1'}">
	<a href="javascript:void(0);"
			onclick="delGdxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>
	

