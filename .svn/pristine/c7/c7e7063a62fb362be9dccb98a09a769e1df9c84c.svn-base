<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="pratner">
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">执行事务合伙人</th>
	</tr>
	<tr id="pratner_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>执行事务合伙人</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" 
						name="PARTNER_PARTNERSHIP"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="PARTNER_NAME" maxlength="8"/></td>
					<td class="tab_width"> 固定电话：</td>
					<td><input type="text" class="tab_text"
						name="PARTNER_FIXEDLINE" maxlength="16"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="PARTNER_MOBILE" maxlength="16"/></td>
					<td class="tab_width"> 电子邮箱：</td>
					<td><input type="text" class="tab_text validate[[],custom[email]]"
						name="PARTNER_EMAIL" maxlength="32" /></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="PARTNER_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="PARTNER_IDNO" /></td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deletePratner" value="删除行" onclick="deletePratner('1');">
			<br>
			<br>
			<input type="button" name="addPratner" value="增加一行" onclick="addPratner();">
		</td>
	</tr>
</table>
