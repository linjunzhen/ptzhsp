<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th>姓 名</th>
			<td><input type="text" maxlength="20"
				value="" placeholder="请输入姓名"
				class="syj-input1 " name="JSRY_XM" /></td>
			<th>性 别</th>
			<td><fda:fda-exradiogroup name="${currentTime}JSRY_XB"
					width="100" value=""
					datainterface="dictionaryService.findList"
					queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}"
					allowblank="true"></fda:fda-exradiogroup></td>
		</tr>
		<tr>
			<th>身份证件号</th>
			<td><input type="text" maxlength="18"
				value="" placeholder="请输入身份证件号"
				class="syj-input1 custom[vidcard]" name="JSRY_SFZHM" /></td>
			<!-- <th>年 龄</th>
			<td><input type="text" maxlength="3"
				value="" placeholder="请输入年龄"
				class="syj-input1 validate[required],custom[onlyNumberSp],min[0],max[100]" name="JSRY_NL" /></td> -->
		<th>文化程度、专业</th>
			<td><input type="text" maxlength="128"
				value="" placeholder="请输入文化程度、专业"
				class="syj-input1" name="JSRY_WHCDZY" /></td>
		</tr>
		
		<tr>
			<th>职 务</th>
			<td><input type="text" maxlength="32"
				value="" placeholder="请输入职务"
				class="syj-input1" name="JSRY_ZW" />
			</td>
			<th>职 称</th>
			<td><input type="text" maxlength="32"
				value="" placeholder="请输入职称"
				class="syj-input1" name="JSRY_ZC" />
			</td>
		</tr>
		<!-- <tr>
			<th>文化程度、专业</th>
			<td colspan="3"><input type="text" maxlength="128"
				value="" placeholder="请输入文化程度、专业"
				class="syj-input1 validate[required]" name="JSRY_WHCDZY" /></td>
		</tr> -->
		<tr>
			<th>食品安全能力适岗培训、考核情况</th>
			<td colspan="3">
				<textarea name="JSRY_PXKHQK" cols="80" rows="5" class="validate[maxSize[500]]"></textarea></td>
		</tr>
	</table>
	<a href="javascript:void(0);"
			onclick="delJsryTechnicalPersonnelDiv(this);" class="syj-closebtn"></a>
</div>