<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >原变更内容</th>
	</tr>
	<tr class="REG_ADDR" style="display: none">
		<td class="tab_width"><font class="tab_color">*</font>住所：</td>
		<td colspan="3"><input type="text" class="tab_text " style="width:740px;"
			name="OLD_REG_ADDR" maxlength="256" value="${busRecord.OLD_REG_ADDR }"/></td>
	</tr>
	<tr class="BUS_YEARS" style="display: none">
		<td class="tab_width"><font class="tab_color">*</font>营业期限：</td>
		<td COLSPAN="3"><input type="text" class="tab_text " style="width:740px;"
							   name="OLD_BUS_YEARS"  value="${busRecord.OLD_BUS_YEARS}"  maxlength="256"/></td>
	</tr>
	<tr class="BUS_SCOPE" style="display: none">
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td COLSPAN="3"><input type="text" class="tab_text " style="width:740px;"
							   name="OLD_BUS_SCOPE"  value="${busRecord.OLD_BUS_SCOPE}"  maxlength="2000"/></td>
	</tr>
	<tr>
		<th colspan="4" >申请变更登记内容</th>
	</tr>
	<tr class="REG_ADDR" style="display: none">
		<td class="tab_width"><font class="tab_color">*</font>住所：</td>
		<td COLSPAN="3"><input type="text" class="tab_text " style="width:740px;"
							   name="NEW_REG_ADDR" maxlength="256" value="${busRecord.NEW_REG_ADDR }"/></td>
	</tr>
	<tr  class="BUS_YEARS" style="display: none">
		<td class="tab_width"><font class="tab_color">*</font>营业期限：</td>
		<td COLSPAN="3"><input type="text" class="tab_text " style="width:740px;"
							   name="NEW_BUS_YEARS"  value="${busRecord.NEW_BUS_YEARS}"  maxlength="256"/></td>
	</tr>
	<tr class="BUS_SCOPE" style="display: none">
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td COLSPAN="3"><input type="text" class="tab_text " style="width:740px;"
							   name="NEW_BUS_SCOPE"  value="${busRecord.NEW_BUS_SCOPE}"  maxlength="2000"/></td>
	</tr>
</table>
