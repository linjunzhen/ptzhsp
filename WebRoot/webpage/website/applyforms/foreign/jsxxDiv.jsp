<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}SUPERVISOR_NAME" placeholder="请输入姓名"  maxlength="32"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjzw:02"
				dataInterface="dictionaryService.findTwoDatasForSelect"
				defaultEmptyText="请选择职务" name="${currentTime}SUPERVISOR_JOB" value="10">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别：</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
				dataInterface="dictionaryService.findDatasForCountrySelect"
				defaultEmptyText="请选择国别" name="${currentTime}SUPERVISOR_COUNTRY">
				</eve:eveselect>
			</td>
		</tr>

		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="DocumentType"
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
				<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${currentTime}SUPERVISOR_GENERATION_MODE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${currentTime}SUPERVISOR_APPOINTOR"  placeholder="请输入任命方"   value="股东" readonly="readonly"/></td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly" 
				name="${currentTime}SUPERVISOR_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delJsxxDiv(this);" class="syj-closebtn"></a>
</div>