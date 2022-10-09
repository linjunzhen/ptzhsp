<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}MANAGER_NAME"  placeholder="请输入姓名，须填写自然人姓名"  maxlength="8"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:03"
				dataInterface="dictionaryService.findTwoDatasForSelect"
				defaultEmptyText="请选择职务" name="${currentTime}MANAGER_JOB" value="20">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}MANAGER_IDNO');"
				defaultEmptyText="请选择证件类型" name="${currentTime}MANAGER_IDTYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}MANAGER_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${currentTime}MANAGER_GENERATION_MODE"  value="01" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
				name="${currentTime}MANAGER_APPOINTOR"  placeholder="请输入任命方"/></td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td ><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
				name="${currentTime}MANAGER_OFFICETERM" value="三"  placeholder="请输入任期"/></td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="state"
							   dataInterface="extraDictionaryService.findDatasForSelect" defaultEmptyValue="156"
							   name="${currentTime}MANAGER_COUNTRY" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td  colspan="3">
				<input type="text" class="syj-input1 validate[required,custom[mobilePhone]]"
					   name="${currentTime}MANAGER_PHONE"  placeholder="请输入手机号码"  maxlength="32"/>
			</td>
		</tr>
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delJlxxDiv(this);" class="syj-closebtn"></a>
</div>