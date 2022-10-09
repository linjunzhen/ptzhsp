<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2 ybzx" id="holder">
	<tr>
		<th colspan="4">股东信息</th>
	</tr>
	<tr id="holder_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>股东姓名/名称：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="SHAREHOLDER_NAME"  maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tab_width"> 股东类型：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjgdlx"
						dataInterface="dictionaryService.findDatasForSelect" id="holderType"
						defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证照类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="LICENCE_NO" maxlength="32"/></td>
					<td class="tab_width"><font class="tab_color">*</font>国家（地区）：</td>
					<td><eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
									   defaultEmptyText="请选择国别"	 dataInterface="dictionaryService.findDatasForCountrySelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY">
					</eve:eveselect></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>身份证件地址：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="ID_ADDRESS" maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>联系方式：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="CONTACT_WAY"  maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"> 投资企业或其他组织机构法定代表人姓名：</td>
					<td ><input type="text" class="tab_text"
						name="LEGAL_PERSON" maxlength="32"/></td>
					<td class="tab_width"> 法定代表人身份证号码：</td>
					<td ><input type="text" class="tab_text validate[]"
								name="LEGAL_IDNO_PERSON" maxlength="32"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!-- <input type="button" name="deleteCurrentHolder" onclick="deleteHolder('1');" value="删除行">
			<br>
			<br>
			<input type="button" name="addOneHolder" value="增加一行" onclick="addHolder();"> -->
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2 ybzx" id="dsxxTable">	
	<tr>
		<th colspan="4">董事信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text validate[required]" readonly="readonly"
			name="DIRECTOR_NAME" value="${busRecord.DIRECTOR_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>职务：</td>
		<td>		
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjzw:02,03,10,11,12,20,21"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.DIRECTOR_JOB}"
			defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
			</eve:eveselect>
		</td>
	</tr>
</table>