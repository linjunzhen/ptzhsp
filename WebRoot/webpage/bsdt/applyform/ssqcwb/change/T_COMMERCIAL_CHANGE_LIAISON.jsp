<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >联络员信息</th>
	</tr>
	<tr>
		<td style="width: 170px;background: #fbfbfb;"> 变更联络员：</td>
		<td colspan="3"><input type="checkbox" class="checkbox"
			name="IS_LIAISON_CHANGE" value="1" <c:if test="${busRecord.IS_LIAISON_CHANGE==1 }">checked=checked</c:if> /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LIAISON_NAME" maxlength="8" value="${busRecord.LIAISON_NAME }"/></td>
		<td class="tab_width"> 固定电话：</td>
		<td><input type="text" class="tab_text"
			name="LIAISON_FIXEDLINE"  value="${busRecord.LIAISON_FIXEDLINE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LIAISON_MOBILE" value="${busRecord.LIAISON_MOBILE }"/></td>
		<td class="tab_width"> 电子邮箱：</td>
		<td><input type="text" class="tab_text validate[[],custom[email]]"
			name="LIAISON_EMAIL" maxlength="32" value="${busRecord.LIAISON_EMAIL }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="LIAISON_IDTYPE" value="${busRecord.LIAISON_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LIAISON_IDNO"  value="${busRecord.LIAISON_IDNO }"/></td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >邮寄营业执照信息</th>
	</tr>
	<tr>
		<td class="tab_width">是否邮寄：</td>
		<td colspan="3">
			<input type="radio" name="ISEMAIL" value="1" <c:if test="${busRecord.ISEMAIL==1 }">checked="checked"</c:if> />是
			<input type="radio" name="ISEMAIL" value="0" <c:if test="${busRecord.ISEMAIL!=1 }">checked="checked"</c:if> />否
		</td>
	</tr>
	<c:if test="${busRecord.ISEMAIL=='1' }">
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
	</c:if>
</table>