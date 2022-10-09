<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2 ybzx">	
	<tr>
		<th colspan="4">清算组备案信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>清算组备案日期：</td>
		<td>
			<input type="text" class="Wdate validate[required]" id="QSZBARQ" style="height: 28px;  line-height: 28px;"
				readonly="readonly" name="QSZBARQ"  placeholder="请输入清算完结日期" value="${busRecord.QSZBARQ}"  maxlength="16"/>	
		</td>
		<td class="tab_width"><font class="tab_color">*</font>清算组成立日期：</td>
		<td>		
			<input type="text" class="Wdate validate[required]" id="QSZCLRQ" style="height: 28px;  line-height: 28px;"
				readonly="readonly" name="QSZCLRQ"  placeholder="请输入清算完结日期" value="${busRecord.QSZCLRQ}"  maxlength="16"/>	
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2 ybzx" id="qszcyxx">
	<tr>
		<th colspan="4">清算组成员信息</th>
	</tr>
	<tr id="qszcyxx_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="QSZ_NAME"  placeholder="请输入姓名"   maxlength="16" value="${busRecord.QSZ_NAME}"/></td>
					<td class="tab_width"> 职务：</td>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="QSZCYZW"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择职务" name="QSZ_JOB"   value="${busRecord.QSZ_JOB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'QSZ_IDNO');"
						defaultEmptyText="请选择证件类型" name="QSZ_IDTYPE" id="QSZ_IDTYPE"   value="${busRecord.QSZ_IDTYPE}">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="QSZ_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.QSZ_IDNO}"/></td>
				</tr>			
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>地址：</td>
					<td colspan="3"><input type="text" class="tab_text  validate[required]"
						name="QSZ_ADDR"  placeholder="请输入地址"  value="${busRecord.QSZ_ADDR}"  maxlength="64"/></td>
				</tr>	
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>联系方式：</td>
					<td colspan="3"><input type="text" class="tab_text  validate[required]"
						name="QSZ_MOBILE"  placeholder="请输入联系方式"  value="${busRecord.QSZ_MOBILE}"  maxlength="16"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!-- <input type="button" name="deleteCurrentQszcyxx" onclick="deleteQszcyxx('1');" value="删除行">
			<br>
			<br>
			<input type="button" name="addOneQszcyxx" value="增加一行" onclick="addQszcyxx();"> -->
		</td>
	</tr>
</table>