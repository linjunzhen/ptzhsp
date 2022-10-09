<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="PERSONNEL_FORM"  >
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addZxswhhrDiv();" class="syj-addbtn" id="addZxswhhr">添 加</a>
		<span>执行事务合伙人<font style="color:red;width:90%;">（友情提示：合伙人出资情况中执行事务合伙人选择“是”才可进行选择）</font></span>
	</div>
	<div id="zxswhhrDiv">	
		<c:if test="${empty pratnerList}">
		<div style="position:relative;">			
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
				
					<th><font class="syj-color2">*</font>执行事务合伙人：</th>
					<td colspan="3">
						<select name="PARTNER_PARTNERSHIP" class="input-dropdown w280 validate[required]">
							<option value="">请选择执行事务合伙人</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="PARTNER_NAME"  placeholder="请输入姓名" maxlength="8" value="${busRecord.PARTNER_NAME}"/></td>
					<th> 固定电话：</th>
					<td><input type="text" class="syj-input1 validate[]"
						name="PARTNER_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.PARTNER_FIXEDLINE}"  maxlength="16"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>移动电话：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="PARTNER_MOBILE"  placeholder="请输入移动电话" value="${busRecord.PARTNER_MOBILE}"  maxlength="16"/></td>
					<th> 电子邮箱：</th>
					<td><input type="text" class="syj-input1 validate[],custom[email]"
						name="PARTNER_EMAIL" placeholder="请输入电子邮箱" maxlength="32"  value="${busRecord.PARTNER_EMAIL}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'PARTNER_IDNO');" 
						defaultEmptyText="请选择证件类型" name="PARTNER_IDTYPE" value="${busRecord.PARTNER_IDTYPE}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="PARTNER_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.PARTNER_IDNO}"/></td>
				</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
		</c:if>
		<jsp:include page="./initZxswhhrDiv.jsp"></jsp:include>
	</div>
	
</form>
