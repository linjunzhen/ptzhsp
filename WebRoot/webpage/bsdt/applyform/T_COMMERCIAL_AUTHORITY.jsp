<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" >
	<tr>
		<th colspan="4">董事、监事、经理信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业权利机构名称：</td>
		<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
			name="AUTHORITY_NAME" maxlength="16" value="${busRecord.AUTHORITY_NAME }"/></td>
	</tr>
</table>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="director_zw">
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">董事会信息</th>
	</tr>
	<tr id="director_1">
		<td colspan="2">
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="DIRECTOR_NAME" maxlength="8"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjzw:01"
						dataInterface="dictionaryService.findTwoDatasForSelect" id="dszzw"
						defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别：</td>
					<td colspan="3">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
						defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
						</eve:eveselect>
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
					<!-- <td><input type="text" class="tab_text"
						name="DIRECTOR_APPOINTOR" id="appointor"/></td> -->
					<td>
						<select name="DIRECTOR_APPOINTOR" id="dszappointor" class="tab_text validate[required]" onchange="checkAppointSelect(this.id);">
							<option value="">请选择任命方</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 任期（年）：</td>
					<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
						name="DIRECTOR_OFFICETERM" value="三" /></td>
				</tr>
			</table>
			<div style="height: 1px;line-height: 1px;background: #0000FF"></div>
		</td>
	</tr>	
	<tr id="director_2">
		<td colspan="2">
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="DIRECTOR_NAME" maxlength="8"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjzw:01" id="fdszzw"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别：</td>
					<td colspan="3">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
						defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
						</eve:eveselect>
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
					<!-- <td><input type="text" class="tab_text"
						name="DIRECTOR_APPOINTOR" id="appointor"/></td> -->
					<td>
						<select name="DIRECTOR_APPOINTOR" id="fdszappointor" class="tab_text validate[required]" onchange="checkAppointSelect(this.id);">
							<option value="">请选择任命方</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 任期（年）：</td>
					<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
						name="DIRECTOR_OFFICETERM" value="三" /></td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">董事信息</th>
	</tr>
	<tr id="director_3">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="DIRECTOR_NAME" maxlength="8"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjzw:01" id="dszw"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别：</td>
					<td colspan="3">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
						defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
						</eve:eveselect>
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
					<!-- <td><input type="text" class="tab_text"
						name="DIRECTOR_APPOINTOR" id="appointor"/></td> -->
					<td>
						<select name="DIRECTOR_APPOINTOR" id="appointors" class="tab_text validate[required]" >
							<option value="">请选择任命方</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 任期（年）：</td>
					<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
						name="DIRECTOR_OFFICETERM" value="三" /></td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deleteCurrentDirector" value="删除行" onclick="deleteDirector('3');">
			<br>
			<br>
			<input type="button" name="addOneDirector" value="增加一行" onclick="addDirector('3');">
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="director_ws" style="display: none;">
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">董事信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="DIRECTOR_NAME" maxlength="8"/></td>
		<td class="tab_width"> 职务：</td>
		<td>
			<eve:eveselect clazz="tab_text " dataParams="ssdjzw:04" id='zxdszw'
			dataInterface="dictionaryService.findTwoDatasForSelect"
			defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国别：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
			dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
			defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
			</eve:eveselect>
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
		<td><input type="text" class="tab_text" id="appointor"
			name="DIRECTOR_APPOINTOR" /></td>
	</tr>
	<tr>
		<td class="tab_width"> 任期（年）：</td>
		<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
			name="DIRECTOR_OFFICETERM" value="三" /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="supervisor">
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">监事信息</th>
	</tr>
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
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国别：</td>
		<td>
			<eve:eveselect clazz="tab_text " dataParams="ssdjgb"
			dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
			defaultEmptyText="请选择国别" name="SUPERVISOR_COUNTRY">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择产生方式" name="SUPERVISOR_GENERATION_MODE">
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
		<td class="tab_width"><font class="tab_color">*</font>任命方：</td>
		<td><input type="text" class="tab_text " id="appointor"
			name="SUPERVISOR_APPOINTOR" /></td>
		<td class="tab_width"> 任期（年）：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="SUPERVISOR_OFFICETERM" value="三" /></td>
	</tr>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="manager">
	<tr>
		<th colspan="4"  style="background-color: #FFD39B;">经理信息</th>
	</tr>
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
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国别：</td>
		<td>
			<eve:eveselect clazz="tab_text " dataParams="ssdjgb"
			dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
			defaultEmptyText="请选择国别" name="MANAGER_COUNTRY">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择产生方式" name="MANAGER_GENERATION_MODE">
			</eve:eveselect>
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
		<td class="tab_width"><font class="tab_color">*</font>任命方：</td>
		<td><input type="text" class="tab_text " id="appointor"
			name="MANAGER_APPOINTOR" /></td>
		<td class="tab_width"> 任期（年）：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="MANAGER_OFFICETERM" value="三" /></td>
	</tr>
</table>
