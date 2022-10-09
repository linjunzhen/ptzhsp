<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${supervisorList}" var="supervisor" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" onblur="setDsbaxx();"
				name="${s.index}SUPERVISOR_NAME" placeholder="请输入姓名"  maxlength="8" value="${supervisor.SUPERVISOR_NAME}"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="ssdjzw:02"
				dataInterface="dictionaryService.findTwoDatasForSelect" onchange="setDsbaxx();" 
				defaultEmptyText="请选择职务" name="${s.index}SUPERVISOR_JOB"  value="10" >
				</eve:eveselect>
			</td>
		</tr>


		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${s.index}SUPERVISOR_IDNO');"
				defaultEmptyText="请选择证件类型" name="${s.index}SUPERVISOR_IDTYPE" value="${supervisor.SUPERVISOR_IDTYPE}">
				</eve:eveselect>

				<script type="text/javascript">
					$(function () {
						setZjValidate('${supervisor.SUPERVISOR_IDTYPE}','${s.index}SUPERVISOR_IDNO');
						
						$("[name='${s.index}SUPERVISOR_JOB']").attr("disabled",true);
						$("[name='${s.index}SUPERVISOR_APPOINTOR']").attr("disabled",true);	
						$("[name='${s.index}SUPERVISOR_APPOINTOR']").val('${supervisor.SUPERVISOR_APPOINTOR}');
					});
				</script>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[equalsDirectorOrManagerIdno],custom[equalsJsIdNo]"
				name="${s.index}SUPERVISOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${supervisor.SUPERVISOR_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${s.index}SUPERVISOR_GENERATION_MODE"  value="${supervisor.SUPERVISOR_GENERATION_MODE}" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td>			
				<select name="${s.index}SUPERVISOR_APPOINTOR" class="input-dropdown  validate[required]" 
				defaultemptytext="请选择任命方" >
				<option value="">请选择任命方</option>
				<option value="职工代表大会" >职工代表大会</option>
				<option value="监事会">监事会</option>
				<option value="股东会">股东会</option>
					<option value="股东">股东</option>
				</select>
			</td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
				name="${s.index}SUPERVISOR_OFFICETERM" value="三"  placeholder="请输入任期" /></td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
							   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
							   name="${s.index}SUPERVISOR_COUNTRY"  value="${supervisor.SUPERVISOR_COUNTRY}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td  colspan="3">
				<input type="text" class="syj-input1 validate[required,custom[mobilePhone]]"  value="${supervisor.SUPERVISOR_PHONE}"
					   name="${s.index}SUPERVISOR_PHONE"  placeholder="请输入手机号码"  maxlength="32"/>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delJsxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>