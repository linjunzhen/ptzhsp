<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="RECORD_FORM"  >


	<div class="syj-title1 tmargin20 liaison" style="display: none">
		<span>联络员信息</span>
	</div>
	<div style="position:relative;display: none" class="liaison" id="liaison1" >
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>姓名：</th>
				<td><input type="text" class="syj-input1 "
						   name="LIAISON_NAME"  placeholder="请输入姓名" maxlength="8" value="${busRecord.LIAISON_NAME}"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1"
						   name="LIAISON_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.LIAISON_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 "
						   name="LIAISON_MOBILE"  placeholder="请输入移动电话" value="${busRecord.LIAISON_MOBILE}"  maxlength="16"/></td>
				<th>电子邮箱：</th>
				<td><input type="text" class="syj-input1 custom[email]"
						   name="LIAISON_EMAIL" placeholder="请输入电子邮箱" maxlength="32"  value="${busRecord.LIAISON_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown " dataParams="DocumentType" defaultEmptyValue="SF"
								   dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LIAISON_IDNO');"
								   defaultEmptyText="请选择证件类型" name="LIAISON_IDTYPE" value="${busRecord.LIAISON_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 "
						   name="LIAISON_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.LIAISON_IDNO}"/></td>
			</tr>
		</table>
	</div>


	<div class="syj-title1 tmargin20 clearQue" style="display: none">
		<span>清算组</span>
	</div>
	<div id="jlxxDiv" class="clearQue" style="display: none">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
					<th><font class="syj-color2">*</font>成员：</th>
					<td colspan="3"><input type="text" class="syj-input1 "
							   name="CLEAR_PEOPLE"   maxlength="256" value="${busRecord.CLEAR_PEOPLE }"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>责任人：</th>
					<td><input type="text" class="syj-input1 "
							   name="CLEAR_ZERENREN"  maxlength="32" value="${busRecord.CLEAR_ZERENREN }"/></td>
					<th><font class="syj-color2">*</font>联系电话：</th>
					<td><input type="text" class="syj-input1 "
							   name="CLEAR_PHONENUM"    maxlength="16" value="${busRecord.CLEAR_PHONENUM }"/></td>
				</tr>
			</table>
		</div>
	</div>
</form>
