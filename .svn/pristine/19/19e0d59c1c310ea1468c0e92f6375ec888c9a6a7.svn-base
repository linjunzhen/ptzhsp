<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">		
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]"  onchange="setSelectOldDsName(this.value)"
				name="DIRECTOR_NAME_OLD"  placeholder="请输入姓名，须填写自然人姓名"  maxlength="32"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>	
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:01,04"
				dataInterface="dictionaryService.findTwoDatasForSelect"
				defaultEmptyText="请选择职务" name="DIRECTOR_JOB_OLD" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'DIRECTOR_IDNO_OLD');" 
				defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE_OLD" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="DIRECTOR_IDNO_OLD"  placeholder="请输入证件号码"  maxlength="32" /></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]"
				name="DIRECTOR_PHONE_OLD"  placeholder="请输入手机号码"  maxlength="32"/>
			</td>
		</tr>
	</table>
	<a href="javascript:void(0);"
			onclick="delYdsxxDiv(this);" class="syj-closebtn"></a>
</div>