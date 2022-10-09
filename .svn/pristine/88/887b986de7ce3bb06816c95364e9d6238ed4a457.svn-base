<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >法定代表人基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LEGAL_NAME" maxlength="8" value="${busRecord.LEGAL_NAME }"/></td>
		<td class="tab_width"> 职务：</td>
		<td>
			<c:if test="${requestParams.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='02'
			||requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'
							||requestParams.COMPANY_SETNATURE=='04'||busRecord.COMPANY_SETNATURE=='04'}">
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:02,03,10,11,30"
					dataInterface="dictionaryService.findDatasForSelect" id="legalJob"
					defaultEmptyText="请选择职务" name="LEGAL_JOB" onchange="setLegalValue(this.value);"   value="${busRecord.LEGAL_JOB}">
					</eve:eveselect>
				</c:if>
				
				<c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'
						||requestParams.COMPANY_SETNATURE=='03'||busRecord.COMPANY_SETNATURE=='03'}">
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,02,03,10,11"
					dataInterface="dictionaryService.findDatasForSelect" id="legalJob"
					defaultEmptyText="请选择职务" name="LEGAL_JOB" onchange="setLegalValue(this.value);"   value="${busRecord.LEGAL_JOB}">
					</eve:eveselect>
				</c:if>
				<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:02,03,10,11,20,30"
						dataInterface="dictionaryService.findDatasForSelect" id="legalJob"
						defaultEmptyText="请选择职务" name="LEGAL_JOB" onchange="setLegalValue(this.value);"   value="${busRecord.LEGAL_JOB}">
						</eve:eveselect>
					</c:if>
					
					<c:if test="${requestParams.COMPANY_SETTYPE!='zwhz'&&busRecord.COMPANY_SETTYPE!='zwhz'
						&&requestParams.COMPANY_SETNATURE!='01'&&busRecord.COMPANY_SETNATURE!='01'
						&&requestParams.COMPANY_SETNATURE!='02'&&busRecord.COMPANY_SETNATURE!='02'
						&&requestParams.COMPANY_SETNATURE!='07'&&busRecord.COMPANY_SETNATURE!='07'
						&&requestParams.COMPANY_SETNATURE!='03'&&busRecord.COMPANY_SETNATURE!='03'
					&&requestParams.COMPANY_SETNATURE!='04'&&busRecord.COMPANY_SETNATURE!='04'}">
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,02,03,10,11,20"
						dataInterface="dictionaryService.findDatasForSelect" id="legalJob"
						defaultEmptyText="请选择职务" name="LEGAL_JOB" onchange="setLegalValue(this.value);"   value="${busRecord.LEGAL_JOB}">
						</eve:eveselect>
					</c:if>
		</td>
	</tr>
	<tr style="display: none;" id="legalCountry">
		<td class="tab_width"><font class="tab_color">*</font>国别：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text " dataParams="ssdjgb"
			dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
			defaultEmptyText="请选择国别" name="LEGAL_COUNTRY" value="${busRecord.LEGAL_COUNTRY }">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LEGAL_MOBILE" value="${busRecord.LEGAL_MOBILE }"/></td>
		<td class="tab_width"> 单位电话：</td>
		<td><input type="text" class="tab_text "
			name="LEGAL_FIXEDLINE" value="${busRecord.LEGAL_FIXEDLINE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 邮箱：</td>
		<td colspan="3"><input type="text" class="tab_text validate[[],custom[email]]"
			name="LEGAL_EMAIL" maxlength="32" value="${busRecord.LEGAL_EMAIL }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]" onblur="checkDifferent(this.name);"
			name="LEGAL_IDNO" maxlength="32" value="${busRecord.LEGAL_IDNO }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国别（地区）：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
						   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
						   name="LEGAL_COMPANYCOUNTRY" value="${busRecord.LEGAL_COMPANYCOUNTRY}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择产生方式" name="LEGAL_PRODUCEMODE"  value="${busRecord.LEGAL_PRODUCEMODE}" >
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>住所</td>
		<td colspan="3"><input type="text" class="tab_text"
							   name="LEGAL_ADDRESS" placeholder="请输入住所" maxlength="256" value="${busRecord.LEGAL_ADDRESS}" /></td>
	</tr>
</table>
