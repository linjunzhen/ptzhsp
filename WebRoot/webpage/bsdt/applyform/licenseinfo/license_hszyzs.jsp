<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00041XK01701'&&serviceItem.ITEM_CODE!='00041XK01703'&&serviceItem.ITEM_CODE!='00041XK01704'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>性别:</td>
	<td><eve:eveselect clazz="tab_text" dataParams="sex"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择性别" name="GR_XB" id="GR_XB"></eve:eveselect></td>
	<td class="tab_width"><font class="tab_color">*</font>国籍:</td>
	<td><input type="text" class="tab_text validate[required]"
		name="GJ" id="GJ" maxlength="128"></td>
</tr>