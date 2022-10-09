<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${supervisorList}" var="director" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">			
		
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}SUPERVISOR_NAME"  placeholder="请输入姓名"  maxlength="8" value="${director.SUPERVISOR_NAME}"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>	
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02,05"
					dataInterface="dictionaryService.findTwoDatasForSelect"
					defaultEmptyText="请选择职务" name="SUPERVISOR_JOB"
					value="${director.SUPERVISOR_JOB}">
					</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${s.index}SUPERVISOR_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${s.index}SUPERVISOR_IDTYPE" value="${director.SUPERVISOR_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}SUPERVISOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${director.SUPERVISOR_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${s.index}SUPERVISOR_GENERATION_MODE"  value="${director.SUPERVISOR_GENERATION_MODE}" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td>		
				<select name="${s.index}SUPERVISOR_APPOINTOR" class="input-dropdown w280  validate[required]" 
				defaultemptytext="请选择任命方"  onchange="setRepresentative(this.value,'${s.index}SUPERVISOR_REPRESENTATIVE')">
				<option value="">请选择任命方</option>
					<option value="监事会" <c:if test="${director.SUPERVISOR_APPOINTOR=='监事会'}">selected</c:if>>监事会</option>
				<option value="职工代表大会" <c:if test="${director.SUPERVISOR_APPOINTOR=='职工代表大会'}">selected</c:if>>职工代表大会</option>
				<option value="股东大会" <c:if test="${director.SUPERVISOR_APPOINTOR=='股东大会'}">selected</c:if>>股东大会</option>
				</select>
			</td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
				name="${s.index}SUPERVISOR_OFFICETERM" value="${director.SUPERVISOR_OFFICETERM}"  placeholder="请输入任期"/></td>
		<th> 职工代表：</th>
			<td>
			<input disabled="disabled" type="checkbox" name="${s.index}SUPERVISOR_REPRESENTATIVE" class="checkbox validate[]" value="1" dicdesc="null" <c:if test="${director.SUPERVISOR_REPRESENTATIVE==1}">checked</c:if>>
			是否为职工代表</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"  value="${director.SUPERVISOR_COUNTRY}"
							   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
							   name="${s.index}SUPERVISOR_COUNTRY" >
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delJsxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>