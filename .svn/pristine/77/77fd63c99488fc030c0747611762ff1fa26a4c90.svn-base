<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">受理信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
			name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
				   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>类型：</td>
		<td colspan="3">
			<eve:radiogroup fieldname="ZYDJ_TYPE" width="500" onclick="queryTypeFn();" typecode="zydjlx" value="1"></eve:radiogroup>
		</td> 
	</tr>
	<tr>
		<td class="tab_width">不动产单元号：</td>
		<td><input type="text" class="tab_text validate[custom[estateNum]]" style="width: 72%;"
				   name="ESTATE_NUM" value="${busRecord.ESTATE_NUM }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
		<td style="width:430px">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX" 
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
		<td><input type="text" class="tab_text validate[required]" style="width: 72%;"
				   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>坐落：</td>
		<td style="width:430px">
			<input type="text" class="tab_text validate[required]" maxlength="160" style="width: 80%;"
			name="LOCATED" value="${busRecord.LOCATED}" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">通知人姓名：</td>
		<td><input type="text" class="tab_text " style="width: 72%;"
				   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
		<td class="tab_width">通知人电话：</td>
		<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11" style="width: 80%;"
			name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
			name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
		</td>
	</tr>
</table>
