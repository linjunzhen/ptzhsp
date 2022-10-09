<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="attorney">
	<tr>
		<th colspan="4">外商投资企业法律文件送达授权委托书基本信息</th>
	</tr>
	<tr id="attorney_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>授权人/委托方：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
						name="AUTHORIZER" maxlength="62"/></td>
					
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>被授权人：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
						name="DELEGATEE" maxlength="62"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>被授权人联系人名称/姓名：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="DELEGATEE_CONTRACTNAME" maxlength="16"/></td>
					<td class="tab_width"> 电子邮箱：</td>
					<td><input type="text" class="tab_text validate[[],custom[email]]"
						name="DELEGATEE_EMAIL" maxlength="32"/></td>
				</tr>
				<tr>					
					<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="DELEGATEE_MOBILE" maxlength="11"/></td>
					<td class="tab_width"> 固定电话：</td>
					<td><input type="text" class="tab_text" 
						name="DELEGATEE_FIXEDLINE" maxlength="16"/></td>
				</tr>
				<tr>					
					<td class="tab_width"><font class="tab_color">*</font>被授权人地址：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="DELEGATEE_ADDR" maxlength="62"/></td>
					<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
					<td><input type="text" class="tab_text validate[required,custom[chinaZip]]" 
						name="DELEGATEE_POSTCODE" maxlength="6"/></td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deleteCurrentAttorney" value="删除行" onclick="deleteAttorney('1');">
			<br>
			<br>
			<input type="button" name="addOneAttorney" value="增加一行" onclick="addAttorney();">
		</td>
	</tr>
</table>
