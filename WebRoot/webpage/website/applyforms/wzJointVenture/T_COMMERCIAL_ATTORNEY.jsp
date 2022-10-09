<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="ATTORNEY_FORM"  >	
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addAttorneyDiv();" class="syj-addbtn" id="addAttorney">添 加</a>
		<span>法律文件送达接受人基本信息</span>
	</div>
	<div id="attorneyDiv">
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
						name="DELEGATEE" maxlength="62" placeholder="请输入被授权人"/></td>		
				</tr>
				<tr>
					<th ><font class="syj-color2">*</font>被授权人联系人名称/姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="DELEGATEE_CONTRACTNAME" maxlength="16" placeholder="请输入被授权人联系人名称/姓名"/></td>
					<th > 电子邮箱：</th>
					<td><input type="text" class="syj-input1 validate[[],custom[email]]"
						name="DELEGATEE_EMAIL" maxlength="32" placeholder="请输入电子邮箱"/></td>
				</tr>
				<tr>					
					<th ><font class="syj-color2">*</font>移动电话：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="DELEGATEE_MOBILE" maxlength="11" placeholder="请输入移动电话"/></td>
					<th > 固定电话：</th>
					<td><input type="text" class="syj-input1 validate[]" 
						name="DELEGATEE_FIXEDLINE" maxlength="16" placeholder="请输入固定电话"/></td>
				</tr>
				<tr>					
					<th ><font class="syj-color2">*</font>被授权人地址：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="DELEGATEE_ADDR" maxlength="62" placeholder="请输入被授权人地址"/></td>
					<th ><font class="syj-color2">*</font>邮政编码：</th>
					<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]" 
						name="DELEGATEE_POSTCODE" maxlength="6" placeholder="请输入邮政编码"/></td>
				</tr>
			</table>
		</div>
	</div>
</form>
