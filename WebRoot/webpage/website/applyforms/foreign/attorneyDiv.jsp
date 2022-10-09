<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>

<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th ><font class="syj-color2">*</font>授权人/委托方：</th>
			<td>			
				<ul class="AUTHORIZER_UL">
					
				</ul>						
			</td>	
			<th ><font class="syj-color2">*</font>被授权人：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}DELEGATEE" maxlength="62" placeholder="请输入被授权人"/></td>			
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>被授权人联系人名称/姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}DELEGATEE_CONTRACTNAME" maxlength="16" placeholder="请输入被授权人联系人名称/姓名"/></td>
			<th > 电子邮箱：</th>
			<td><input type="text" class="syj-input1 validate[[],custom[email]]"
				name="${currentTime}DELEGATEE_EMAIL" maxlength="32" placeholder="请输入电子邮箱"/></td>
		</tr>
		<tr>					
			<th ><font class="syj-color2">*</font>移动电话：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}DELEGATEE_MOBILE" maxlength="11" placeholder="请输入移动电话"/></td>
			<th > 固定电话：</th>
			<td><input type="text" class="syj-input1 validate[]" 
				name="${currentTime}DELEGATEE_FIXEDLINE" maxlength="16" placeholder="请输入固定电话"/></td>
		</tr>
		<tr>					
			<th ><font class="syj-color2">*</font>被授权人地址：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}DELEGATEE_ADDR" maxlength="62" placeholder="请输入被授权人地址"/></td>
			<th ><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]" 
				name="${currentTime}DELEGATEE_POSTCODE" maxlength="6" placeholder="请输入邮政编码"/></td>
		</tr>
	</table>
	<a href="javascript:void(0);"
			onclick="delAttorneyDiv(this);" class="syj-closebtn"></a>
</div>
	

