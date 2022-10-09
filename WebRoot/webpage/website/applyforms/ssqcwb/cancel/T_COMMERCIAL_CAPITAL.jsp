<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="CAPITAL_FORM"  >	
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addGdxxDiv();" class="syj-addbtn" id="addGdxx">添 加</a><span>股东信息</span>
	</div>
	<div id="gdxxDiv">	
		<c:if test="${empty holderList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			
				<tr>
					<th><font class="syj-color2">*</font>股东姓名/名称：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32"/></td>
					<th><font class="syj-color2">*</font>股东类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgdlx"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE"  onchange="setGdlxValidate(this.value,'LEGAL_PERSON');setGdlxValidate(this.value,'LEGAL_IDNO_PERSON');setGdlxValidateToType(this.value,'LICENCE_TYPE','LICENCE_NO');">
						</eve:eveselect>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					
					<th><font class="syj-color2">*</font>证照类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LICENCE_NO');"
						defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="LICENCE_NO"  placeholder="请输入证件号码" maxlength="32"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>身份证件地址：</th>
					<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
						name="ID_ADDRESS" placeholder="请输入身份证件地址"  maxlength="64"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>联系方式：</th>
					<td ><input type="text" class="syj-input1 validate[required]"
						name="CONTACT_WAY"  placeholder="请输入联系方式"  maxlength="16"/></td>
					<th><font class="syj-color2">*</font>国别（地区）：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb" defaultEmptyValue="中国"
									   dataInterface="dictionaryService.findDatasForCountrySelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> 投资企业或其他组织机构法定代表人姓名：</th>
					<td ><input type="text" class="syj-input1"
						name="LEGAL_PERSON"  placeholder="请输入法定代表人姓名"  maxlength="32"/></td>
					<th> 法定代表人身份证号码：</th>
					<td ><input type="text" class="syj-input1  validate[]"
								name="LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码"  maxlength="32"/></td>
				</tr>
			</table>
			
		</div>
		</c:if>
		<jsp:include page="./initGdxxDiv.jsp"></jsp:include>
	</div>
	
	<div class="syj-title1 ">
		<span>董事信息</span>
	</div>
	<div id="dsxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
				
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" value="${busRecord.DIRECTOR_NAME}"
						name="DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02,03,10,11,12,20,21"
							dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.DIRECTOR_JOB}"
							defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
							</eve:eveselect>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
