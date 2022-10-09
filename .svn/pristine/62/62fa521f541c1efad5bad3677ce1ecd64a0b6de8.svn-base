<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">			
		
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}DIRECTOR_NAME"  placeholder="请输入姓名"  maxlength="32"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjzw:01,04"
				dataInterface="dictionaryService.findTwoDatasForSelect"
				defaultEmptyText="请选择职务" name="${currentTime}DIRECTOR_JOB" value="${directorJob}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别：</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown validate[required] " dataParams="ssdjgb"
				dataInterface="dictionaryService.findDatasForCountrySelect"
				defaultEmptyText="请选择国别" name="${currentTime}DIRECTOR_COUNTRY">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required] " dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}DIRECTOR_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${currentTime}DIRECTOR_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${currentTime}DIRECTOR_GENERATION_MODE" value="${directorGenerationMode}">
				</eve:eveselect>
			</td>
			<th> 任期（年）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
				name="${currentTime}DIRECTOR_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td colspan="3">
				<select name="${currentTime}DIRECTOR_APPOINTOR" class="input-dropdown validate[required]" style="width: auto;">
					<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
						<option value="">请选择任命方</option>
					</c:if>
					<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">							
						<option value="股东会">股东会</option>
					</c:if>
					<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">		
						<option value="投资者">投资者</option>
					</c:if>
				</select>
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delDsxxDiv(this);" class="syj-closebtn"></a>
</div>