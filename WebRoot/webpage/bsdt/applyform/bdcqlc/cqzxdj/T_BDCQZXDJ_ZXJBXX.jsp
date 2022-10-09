<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%-- 不动产权注销登记：注销基本信息 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="zxjbxx">
	<tr>
		<th colspan="4">注销基本信息<font class="pull-right"  color="red">(单位：平方米)</font></th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注销原因</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" maxlength="60"
				name="ZXYY" id="ZXYY" style="width:1000px" value="${busRecord.ZXYY}" />
		</td>
	</tr>
	<tr id="DBTR">
		<td class="tab_width">登簿人：</td>
		<td><input type="text" class="tab_text" disabled="disabled" name="DBR" id="DBR" value="${busRecord.DBR}">
		</td>
		<td class="tab_width">登记时间</td>
		<td><input type="text" class="tab_text" style="float:left;margin-right:10px" disabled="disabled" name="DJSJ" id="DJSJ" value="${busRecord.DJSJ}">
			<a href="javascript:void(0);" class="eflowbutton" onclick="doBooking()" name="QRDB" id="QRDB">确认无误，登簿</a>
		</td>
	</tr>


</table>
