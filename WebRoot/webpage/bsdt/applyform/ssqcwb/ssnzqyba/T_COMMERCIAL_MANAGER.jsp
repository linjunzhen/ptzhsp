<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="manager">
	<tr>
		<th colspan="2"  style="background-color: #FFD39B;">新经理信息</th>
	</tr>
	<tr id="manager_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="MANAGER_NAME" maxlength="8"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjzw:03" id="jlzw"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="MANAGER_JOB">
						</eve:eveselect>
					</td>
				</tr>
				<tr >
					<td class="tab_width"><font class="tab_color">*</font>国别：</td>
					<td >
						<eve:eveselect clazz="tab_text " dataParams="ssdjgb"
									   dataInterface="dictionaryService.findDatasForCountrySelect"
						defaultEmptyText="请选择国别" name="MANAGER_COUNTRY">
						</eve:eveselect>
					</td>
					<td class="tab_width">手机号码：</td>
					<td >
						<input type="text" class="tab_text "
							   name="MANAGER_PHONE" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="MANAGER_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="MANAGER_IDNO" /></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="MANAGER_GENERATION_MODE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>任命方：</td>
					<td><input type="text" class="tab_text " disabled="disabled" id="appointor"
						name="MANAGER_APPOINTOR" /></td>
				</tr>
				<tr>
					<td class="tab_width"> 任期（年）：</td>
					<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
						name="MANAGER_OFFICETERM" value="三" />年</td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deleteCurrentManager" value="删除行" onclick="deleteManager('1');">
			<br>
			<br>
			<input type="button" name="addOneManager" value="增加一行" onclick="addManager();">
		</td>
	</tr>
</table>
