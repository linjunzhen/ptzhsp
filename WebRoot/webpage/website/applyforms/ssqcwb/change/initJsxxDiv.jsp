<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${supervisorChangeList}" var="supervisor" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
		
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}SUPERVISOR_NAME"  placeholder="请输入姓名，须填写自然人姓名"  maxlength="32" value="${supervisor.SUPERVISOR_NAME}"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>					
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02"
				dataInterface="dictionaryService.findTwoDatasForSelect"
				defaultEmptyText="请选择职务" name="${s.index}SUPERVISOR_JOB"  value="${supervisor.SUPERVISOR_JOB}" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}SUPERVISOR_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${s.index}SUPERVISOR_IDTYPE" value="${supervisor.SUPERVISOR_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}SUPERVISOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${supervisor.SUPERVISOR_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${s.index}SUPERVISOR_GENERATION_MODE"  value="${supervisor.SUPERVISOR_GENERATION_MODE}" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td>
				<select name="${s.index}SUPERVISOR_APPOINTOR" class="input-dropdown w280 validate[required]" 
					defaultemptytext="请选择任命方" >
					<option value="">请选择任命方</option>
					<option value="职工代表大会" <c:if test="${supervisor.SUPERVISOR_APPOINTOR=='职工代表大会'}">selected=true</c:if> >职工代表大会</option>
					<option value="监事会" <c:if test="${supervisor.SUPERVISOR_APPOINTOR=='监事会'}">selected=true</c:if> >监事会</option>
					<option value="股东会" <c:if test="${supervisor.SUPERVISOR_APPOINTOR=='股东会'}">selected=true</c:if> >股东会</option>
					<option value="股东" <c:if test="${supervisor.SUPERVISOR_APPOINTOR=='股东'}">selected=true</c:if> >股东</option>
				</select>
			</td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td ><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
				name="${s.index}SUPERVISOR_OFFICETERM" value="${supervisor.SUPERVISOR_OFFICETERM}"  placeholder="请输入任期" style="width: 185px;"/></td>
				
			<th><font class="syj-color2">*</font>国别：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="state"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择国别" name="${s.index}SUPERVISOR_COUNTRY" value="${supervisor.SUPERVISOR_COUNTRY}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>		
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required] w280"
				name="${s.index}SUPERVISOR_PHONE"  placeholder="请输入手机号码"  maxlength="32" value="${supervisor.SUPERVISOR_PHONE}"/>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delDsxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>