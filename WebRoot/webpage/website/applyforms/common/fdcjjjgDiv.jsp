<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
		<tr>
			<th><font class="syj-color2">*</font>房地产经纪专业人员姓名：</th>
			<td>
				<input type="text" class="syj-input1" 
				name="${currentTime}FDCJJJG_NAME"  placeholder="请输入姓名"  maxlength="8"/>
			</td>
			<th><font class="syj-color2">*</font>身份证号码：</th>
			<td>
				<input type="text" class="syj-input1" 
				name="${currentTime}FDCJJJG_IDCARD"  placeholder="请输入身份证号码"  maxlength="18"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>职业资格证书管理号：</th>
			<td>
				<input type="text" class="syj-input1 validate[groupRequired[${currentTime}FDCJJJG_MANAGE_NUMBER,${currentTime}FDCJJJG_REGISTRATION_NUMBER]]"
				name="${currentTime}FDCJJJG_MANAGE_NUMBER"  placeholder="请输入管理号"  maxlength="32"/>
			</td>
			<th><font class="syj-color2">*</font>登记证书登记号：</th>
			<td>
				<input type="text" class="syj-input1 validate[groupRequired[${currentTime}FDCJJJG_MANAGE_NUMBER,${currentTime}FDCJJJG_REGISTRATION_NUMBER]]"
				name="${currentTime}FDCJJJG_REGISTRATION_NUMBER"  placeholder="请输入登记号"  maxlength="32"/>
			</td>
		</tr>	
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delFdcjjjgDiv(this);" class="syj-closebtn"></a>
</div>