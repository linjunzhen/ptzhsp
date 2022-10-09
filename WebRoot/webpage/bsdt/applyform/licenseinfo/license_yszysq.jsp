<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<tr <c:if test="${serviceItem.ITEM_CODE!='00041XK01501'&&serviceItem.ITEM_CODE!='00041XK01502'}">style="display: none;"</c:if>>
		<td class="tab_width">
			<font class="tab_color">*</font>性别:
		</td>
		<td>
			<eve:eveselect clazz="tab_text"
							dataParams="sex"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择性别" name="GR_XB" id="GR_XB"></eve:eveselect>
		</td>
		<td class="tab_width">
			<font class="tab_color">*</font>出生日期:
		</td>
		<td>
			<input type="text" name="CSRQ" class="laydate-icon validate[required]"
				onclick="laydate({istime: false, min:laydate.now(), format: 'YYYY-MM-DD'})" style="width:170px; height:26px;" />
		</td>
	</tr>
	<tr <c:if test="${serviceItem.ITEM_CODE!='00041XK01501'&&serviceItem.ITEM_CODE!='00041XK01502'}">style="display: none;"</c:if>>
		<td class="tab_width">
			<font class="tab_color">*</font>医师资格证编码:
		</td>
		<td>
			<input type="text" name="YSZG" class="tab_text validate[required]" maxlength="512">
		</td>
		<td class="tab_width">
			<font class="tab_color">*</font>执业类别:
		</td>
		<td>
			<input type="text" name="ZYLB" class="tab_text validate[required]" maxlength="128">
		</td>
	</tr>
	<tr <c:if test="${serviceItem.ITEM_CODE!='00041XK01501'&&serviceItem.ITEM_CODE!='00041XK01502'}">style="display: none;"</c:if>>
		<td class="tab_width">
			<font class="tab_color">*</font>执业范围:
		</td>
		<td>
			<input type="text" name="ZYFW" class="tab_text validate[required]" maxlength="256">
		</td>
		<td class="tab_width">
			<font class="tab_color">*</font>签发人:
		</td>
		<td>
			<input type="text" name="QFR" class="tab_text validate[required]" maxlength="32">
		</td>
	</tr>
	<tr <c:if test="${serviceItem.ITEM_CODE!='00041XK01501'&&serviceItem.ITEM_CODE!='00041XK01502'}">style="display: none;"</c:if>>
		<td class="tab_width">
			<font class="tab_color">*</font>执业地点:
		</td>
		<td colspan="3">
			<input type="text" name="ZYDD" class="tab_text validate[required]" maxlength="2000" style="width: 72%;">
		</td>
	</tr>