<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style type="text/css">
	.inputWidth{
		width:650px;
	}

</style>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2 dataquery" >
	<tr>
		<th colspan="4">受理信息</th>
	</tr>
	<tr>
		<td class="tab_width" ><font class="tab_color">*</font>申请人类别：</td>
		<td style="width:630px" colspan="3">
			<eve:eveselect clazz="tab_text inputWidth validate[required]" dataParams="applyType"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择申请人类别" name="APPLYPEOPLE_TYPE" value="${busRecord.APPLYPEOPLE_TYPE }">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">不动产地址：</td>
		<td><input type="text" class="tab_text validate[maxSize[256]]"
			name="ESTATE_ADDR" value="${busRecord.ESTATE_ADDR }"/></td>
		<td class="tab_width">不动产证号：</td>
		<td><input type="text" class="tab_text validate[maxSize[128]]"
				   name="ESTATE_NUM" value="${busRecord.ESTATE_NUM}"/></td>
	</tr>
	<tr>
		<td class="tab_width" ><font class="tab_color">*</font>查询用途：</td>
		<td style="width:630px" colspan="3">
			<eve:eveselect clazz="tab_text inputWidth validate[required]" dataParams="queryOfUse"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择查询用途" name="QUERY_USE" value="${busRecord.QUERY_USE }">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">查询目的：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="QUERY_AIM"
					  class="eve-textarea validate[maxSize[2000]]"
					  style="width:740px;height:100px;" >${busRecord.QUERY_AIM }</textarea>
		</td>
	</tr>

	<tr>
		<td class="tab_width"><font class="tab_color">*</font>查询内容：</td>
		<td colspan="3">
			<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect"
					name="QUERY_CONTENT" width="750px;"
					clazz="checkbox validate[required]" dataParams="qyeryContent"
					value="${busRecord.QUERY_CONTENT}">
			</eve:excheckbox></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>查询结果要求：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text inputWidth validate[required]" dataParams="queryRequire"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择查询结果要求" name="QUERY_REQUIRE" value="${busRecord.QUERY_REQUIRE }">
			</eve:eveselect>
		</td>
	</tr>
	<tr id="QUERY_RESULT_TR" style="display:none;">
		<td class="tab_width"><font class="tab_color">*</font>查询结果：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text inputWidth" dataParams="queryResult"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择查询结果" name="QUERY_RESULT" value="${busRecord.QUERY_RESULT}">
			</eve:eveselect><font style="color: #053892">（请根据查询结果选择。）</font>
		</td>
	</tr>
</table>
