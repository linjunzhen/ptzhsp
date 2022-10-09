<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}SUPERVISOR_NAME" placeholder="请输入姓名"  maxlength="8"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:10"
				dataInterface="dictionaryService.findDatasForSelectIn"
				defaultEmptyText="请选择职务" name="${currentTime}SUPERVISOR_JOB"  value="10" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}SUPERVISOR_IDNO');"
				defaultEmptyText="请选择证件类型" name="${currentTime}SUPERVISOR_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[equalsDirectorOrManagerIdno]"
				name="${currentTime}SUPERVISOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${currentTime}SUPERVISOR_GENERATION_MODE"  value="01" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td>			
				<select name="${currentTime}SUPERVISOR_APPOINTOR" class="input-dropdown w280 validate[required]" 
				defaultemptytext="请选择任命方"  onchange="setRepresentative(this.value,'${currentTime}SUPERVISOR_REPRESENTATIVE')">
				<option value="">请选择任命方</option>
					<option value="监事会" >监事会</option>
				<option value="职工代表大会" >职工代表大会</option>
				<option value="股东大会">股东大会</option>
				</select>
			</td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly" 
				name="${currentTime}SUPERVISOR_OFFICETERM" value="三"  placeholder="请输入任期"/></td>
			<th> 职工代表：</th>
			<td>
				<input disabled="disabled" type="checkbox" name="${currentTime}SUPERVISOR_REPRESENTATIVE" class="checkbox validate[]" value="1" dicdesc="null">
			是否为职工代表
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown w878 validate[required]" dataParams="ssdjgb"
							   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
							   name="${currentTime}SUPERVISOR_COUNTRY" value="${currentTime}SUPERVISOR_IDNO" >
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delJsxxDiv(this);" class="syj-closebtn"></a>
</div>