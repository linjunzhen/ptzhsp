<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="supervisor">
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">监事信息</th>
	</tr>
	<tr id="supervisor_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="SUPERVISOR_NAME" maxlength="8"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjzw:02" id="jszw"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="SUPERVISOR_JOB">
						</eve:eveselect>
					</td>
				</tr>
				<tr >
					<td class="tab_width"><font class="tab_color">*</font>国别：</td>
					<td colspan="3">
						<eve:eveselect clazz="tab_text " dataParams="ssdjgb"
									   dataInterface="dictionaryService.findDatasForCountrySelect"
									   defaultEmptyText="请选择国别" name="SUPERVISOR_COUNTRY">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="SUPERVISOR_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="SUPERVISOR_IDNO" /></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="SUPERVISOR_GENERATION_MODE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>任命方：</td>
					<td><input type="text" class="tab_text " disabled="disabled" id="sappointor"
						name="SUPERVISOR_APPOINTOR" /></td>
				</tr>
				<tr>
					<td class="tab_width"> 任期（年）：</td>
					<td><input type="text" class="tab_text" disabled="disabled"
						name="SUPERVISOR_OFFICETERM" value="三" />年</td>
					<td class="tab_width"> 是否职工代表：</td>
					<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择是否为职工代表" name="SUPERVISOR_REPRESENTATIVE">
						</eve:eveselect>
					</td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deleteCurrentSupervisor" value="删除行" onclick="deleteSupervisor('1');">
			<br>
			<br>
			<input type="button" name="addOneSupervisor" value="增加一行" onclick="addSupervisor();">
		</td>
	</tr>
</table>
