<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<tr <c:if test="${serviceItem.ITEM_CODE!='00044XK04501'}">style="display: none;"</c:if>>
		<td class="tab_width">
			<font class="tab_color">*</font>工商登记注册日期:
		</td>
		<td colspan="3">
			<input type="text" name="GSDJZCRQ" class="laydate-icon validate[required]"
				onclick="laydate({istime: false, min:laydate.now(), format: 'YYYY-MM-DD'})" style="width:170px; height:26px;" />
		</td>
	</tr>
	<tr <c:if test="${serviceItem.ITEM_CODE!='00044XK04501'}">style="display: none;"</c:if>>
		<td class="tab_width">
			<font class="tab_color">*</font>进出口企业代码:
		</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="16" name="JCKQYDM">
		</td>
		<td class="tab_width">
			<font class="tab_color">*</font>工商登记注册号:
		</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="32" name="GSDJZCH">
		</td>
	</tr>