<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >指定或委托代理人信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>指定代表或者委托代理人姓名：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]"
			name="OPERATOR_NAME" maxlength="8" value="${busRecord.OPERATOR_NAME }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 固定电话：</td>
		<td ><input type="text" class="tab_text"
			name="OPERATOR_FIXEDLINE" value="${busRecord.OPERATOR_FIXEDLINE }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
		<td><input type="text" class="tab_text validate[required]"
				   name="OPERATOR_MOBILE" value="${busRecord.OPERATOR_MOBILE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>指定或委托的有效期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="vstarta" readonly="readonly"
			name="VALIDITY_START_DATE"  value="${busRecord.VALIDITY_START_DATE }"/>至
			<input type="text" class="laydate-icon validate[required]" id="venda" readonly="readonly"
			name="VALIDITY_END_DATE"  value="${busRecord.VALIDITY_END_DATE }"/></td>
	</tr>
</table>
