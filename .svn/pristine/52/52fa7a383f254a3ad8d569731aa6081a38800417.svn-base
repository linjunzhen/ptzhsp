<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="bstable1" style="margin-top: -1px;">
		<tr>
			<th style="width: 135px;"><span class="bscolor1">*</span>义务人姓名：</th>
			<td ><input type="text" class="tab_text validate[required]" 
				name="${currentTime}YWR_MC" maxlength="32" value="${busRecord.YWR_MC}"/></td>
			<th style="width: 135px;"><span class="bscolor1">*</span>义务人证件种类：</th>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}YWR_ZJNO');"
				defaultEmptyText="选择证件类型" name="${currentTime}YWR_ZJLX" id="${currentTime}YWR_ZJLX"  value="${busRecord.YWR_ZJLX}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><span class="bscolor1">*</span>义务人证件号码：</th>
			<td>
			  <input type="text" class="tab_text validate[required]" maxlength="64" id="${currentTime}YWR_ZJNO" style="float: left;"
				name="${currentTime}YWR_ZJNO" value="${busRecord.YWR_ZJNO}" />
			</td>
			<th><span class="bscolor1">*</span>义务人手机号码：</th>
			<td>
			  <input type="text" class="tab_text validate[required,custom[equalsYwrSjhm]],custom[mobilePhoneLoose]" maxlength="11" id="${currentTime}YWR_SJHM" style="float: left;"
				name="${currentTime}YWR_SJHM" value="${busRecord.YWR_SJHM}" />
			</td>
		</tr>
	</table>
	<a href="javascript:void(0);" onclick="javascript:delQlrDiv(this);" style="float: right;top: auto;margin-top:-42px;" class="syj-closebtn"></a>
</div>