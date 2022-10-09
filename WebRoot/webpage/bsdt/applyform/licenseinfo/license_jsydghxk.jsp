<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00010XK02701'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>用地项目名称:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="128" name="YDXMMC"></td>
	<td class="tab_width"><font class="tab_color">*</font>建设规模:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="2000" name="JSGM"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00010XK02701'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>用地位置:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="2000" name="YDWZ"></td>
	<td class="tab_width"><font class="tab_color">*</font>申报号:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="32" name="ZZLSH"></td>
</tr>