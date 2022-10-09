<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<c:if test="${busRecord.ISEMAIL=='1' }">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >邮寄营业执照信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 收件人姓名：</td>
		<td >
			<input type="text" class="tab_text"
				   name="EMS_RECEIVER"  value="${busRecord.EMS_RECEIVER }"/>
		</td>
		<td class="tab_width"> 收件人电话：</td>
		<td >
			<input type="text" class="tab_text"
				   name="EMS_PHONE"  value="${busRecord.EMS_PHONE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 寄送地址：</td>
		<td  colspan="3">
			<input type="text" class="tab_text"  style="width: 700px;"
				   name="EMS_ADDRESS"  value="${busRecord.EMS_ADDRESS }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 寄送城市：</td>
		<td  colspan="3">
			<input type="text" class="tab_text"
				   name="EMS_CITY"  value="${busRecord.EMS_CITY }"/>
		</td>
	</tr>
</table>
</c:if>

