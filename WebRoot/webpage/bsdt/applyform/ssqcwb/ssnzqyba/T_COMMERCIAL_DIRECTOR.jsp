<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="director">
	<tr>
		<th colspan="2"  style="background-color: #FFD39B;">新董事信息</th>
	</tr>
	<tr id="director_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="DIRECTOR_NAME" maxlength="30"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjzw:01,04" id="dszw"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别：</td>
					<td >
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
						defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
						</eve:eveselect>
					</td>
					<td class="tab_width">手机号码：</td>
					<td >
						<input type="text" class="tab_text "
							   name="DIRECTOR_PHONE" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="DIRECTOR_IDNO" /></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="DIRECTOR_GENERATION_MODE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>任命方：</td>
					<td><input type="text" class="tab_text" disabled="disabled" id="appointor"
						name="DIRECTOR_APPOINTOR" /></td>
				</tr>
				<tr>
					<td class="tab_width"> 任期（年）：</td>
					<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
						name="DIRECTOR_OFFICETERM" value="三" />年</td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deleteCurrentDirector" value="删除行" onclick="deleteDirector('1');">
			<br>
			<br>
			<input type="button" name="addOneDirector" value="增加一行" onclick="addDirector();">
		</td>
	</tr>
</table>
