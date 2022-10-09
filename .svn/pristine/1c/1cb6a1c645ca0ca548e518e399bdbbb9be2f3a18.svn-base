<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="bstable1" style="margin-top: -1px;">
		<tr>
			<th><span class="bscolor1">*</span>权利人姓名：</th>
			<td ><input type="text" class="tab_text validate[required]" 
				name="${currentTime}MSFXM" maxlength="32" value="${busRecord.MSFXM}"/></td>
			<th><span class="bscolor1">*</span>权利人证件种类：</th>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}MSFZJHM');"
				defaultEmptyText="选择证件类型" name="${currentTime}MSFZJLB" id="${currentTime}MSFZJLB"  value="${busRecord.MSFZJLB}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><span class="bscolor1">*</span>权利人证件号码：</th>
			<td>
			  <input type="text" class="tab_text validate[required]" maxlength="128" id="${currentTime}MSFZJHM" style="float: left;"
				name="${currentTime}MSFZJHM" value="${busRecord.MSFZJHM}" />
			</td>
			<th><span class="bscolor1">*</span>权利人手机号码：</th>
			<td>
			  <input type="text" class="tab_text validate[required,custom[equalsQlrSjhm]],custom[mobilePhoneLoose]" maxlength="11" id="${currentTime}MSFSJHM" style="float: left;"
				name="${currentTime}MSFSJHM" value="${busRecord.MSFSJHM}" />
			</td>
		</tr>
	</table>
	<a href="javascript:void(0);" onclick="javascript:delQlrDiv(this);" style="float: right;top: auto;margin-top:-42px;" class="syj-closebtn"></a>
</div>