<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<!-- 受理信息 -->
<div id="SLXX">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th colspan="4">受理信息</th>
		</tr>
		<tr>
			<td class="tab_width">
				<font class="tab_color">*</font>登记类型：
			</td>
			<td><input type="text" class="tab_text " disabled="disabled" name="CATALOG_NAME"
					value="${serviceItem.CATALOG_NAME }" /></td>
			<td class="tab_width">
					权利类型：
			</td>
			<td>
				<eve:eveselect clazz="tab_text" dataParams="ZDQLLX" value="${serviceItem.HF_QLLX }"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择权利类型" name="HF_QLLX" id="HF_QLLX">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<td class="tab_width">
				<font class="tab_color ">*</font>申请人(单位)：
			</td>
			<td><input type="text" class="tab_text validate[required]" name="APPLICANT_UNIT"
					value="${busRecord.APPLICANT_UNIT }" /></td>
			<td class="tab_width">
				<font><font class="tab_color">*</font>持证类型</font>
			</td>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
				    name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<td class="tab_width">
				<font class="tab_color ">*</font>不动产单元号：
			</td>
			<td colspan="3"><input type="text" class="tab_text validate[required]" style="width: 450px"
				name="ESTATE_NUM" id="ESTATE_NUM"	value="${busRecord.ESTATE_NUM }" />&nbsp;&nbsp;
				<input type="button" value="不动产档案信息查询" class="eve-button" onclick="showPageBdcInfoSelector();" /> </td>
		</tr>
		<tr>
			<td class="tab_width">
				<font class="tab_color ">*</font>坐落：
			</td>
			<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60" name="LOCATED"
					value="${busRecord.LOCATED}" style="width: 72%;" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">通知人姓名：</td>
			<td colspan="3"><input type="text" class="tab_text " name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }" /></td>
		</tr>
		<tr>
			<td>受理人员</td>
			<td><input type="text" class="tab_text" name="SLRY" value="${busRecord.SLRY}" /></td>
			<td class="tab_width">备注：</td>
			<td><input type="text" class="tab_text validate[]" maxlength="60" name="REMARK"
					value="${busRecord.REMARK}" style="width: 72%;" />
			</td>
		</tr>
	</table>
</div>