<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%--抵押权注销登记  注销信息--%>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>注销信息</th>
	</tr>
</table>
<div class="tab_height"></div>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<td class="tab_width">
			<font class="tab_color ">*</font>
			注销原因：
		</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[required]" maxlength="128" name="ZXYY"
				value="${busRecord.ZXYY}" style="width: 72%;" />
			<input type="button" class="eflowbutton" value="意见模板"
				onclick="AppUtil.cyyjmbSelector('dyqzxyy','ZXYY');">
		</td>
	</tr>
	<tr>
		<td class="tab_width">
			<font class="tab_color ">*</font>
			注销附记：
		</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[required]" maxlength="128" name="ZXFJ"
				value="${busRecord.ZXFJ}" style="width: 72%;" />
			<input type="button" class="eflowbutton" value="意见模板"
				onclick="AppUtil.cyyjmbSelector('dyqzxfj','ZXFJ');">
		</td>
	</tr>

	<tr>
		<td class="tab_width">注销人：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="128" name="BDC_ZXR" id="BDC_ZXR"
				value="${busRecord.BDC_ZXR}" disabled="disabled"/>
		</td>

		<td class="tab_width">注销时间：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="32" name="BDC_ZXSJ" id="BDC_ZXSJ"
				value="${busRecord.BDC_ZXSJ}" disabled="disabled"/>
		</td>

		<td class="tab_width">注销宗数：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="32" name="BDC_ZXZS" id="BDC_ZXZS"
				value="${busRecord.BDC_ZXZS}" disabled="disabled"/>
		</td>
	</tr>
</table>
