<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String value = request.getParameter("gyjssd");
			request.setAttribute("gyjssd", value);
%>

<script>
	function bgTypeclick() {
		var bgValue = $("input[name='BG_TYPE']:checked").val();
		// 单证
		if ("1" == bgValue) {
			$("#fwjbxx").hide();
		} else if ("2" == bgValue) {
			$("#fwjbxx").attr("style", "display:block");
		}
	}
</script>


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">受理信息</th>
	</tr>
	<tr>
		<td class="tab_width">
			<font class="tab_color">*</font>
			登记类型：
		</td>
		<td>
			<input type="text" class="tab_text " disabled="disabled" name="CATALOG_NAME"
				value="${serviceItem.CATALOG_NAME }" />
		</td>
		<td class="tab_width">
			<font class="tab_color">*</font>
			权利类型：
		</td>
		<td>
			<%-- <input type="text" class="tab_text " disabled="disabled"
				   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/> --%>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ZDQLLX"
				dataInterface="dictionaryService.findDatasForSelect" value="" defaultEmptyText="选择权利类型"
				name="HF_QLLX" id="HF_QLLX">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">
			<font class="tab_color ">*</font>
			事项类型：
		</td>
		<td colspan="3">
			<input type="radio" name="BG_TYPE" class="validate[required]" onclick="bgTypeclick();" value="1"
				<c:if test="${busRecord.BG_TYPE=='1'}">checked="checked"</c:if>>
			国有建设用地使用权及房屋所有权变更登记（单证）
			<input type="radio" name="BG_TYPE" class="validate[required]" onclick="bgTypeclick();" value="2"
				<c:if test="${busRecord.BG_TYPE=='2'}">checked="checked"</c:if>>
			国有建设用地使用权及房屋所有权变更登记（双证）
		</td>
	</tr>
	<tr>
		<td class="tab_width">
			<span id="bdcdyh_font">
				<font class="tab_color ">*</font>
			</span>
			不动产单元号：
		</td>
		<td>
			<input type="text" class="tab_text validate[required,custom[estateNum]]" name="ESTATE_NUM"
				value="${busRecord.ESTATE_NUM }" />
			<c:if test="${gyjssd==1}">
				<input type="checkbox" name="IS_DXZPGXM" value="1"
					<c:if test="${busRecord.IS_DXZPGXM=='1'}">checked="checked"</c:if> onclick="checkBdcdyh();" />大型招拍挂项目
		    </c:if>
		</td>
		<td class="tab_width">
			<font class="tab_color">*</font>
			持证类型：
		</td>
		<td style="width:430px">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
				defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE"
				value="${busRecord.TAKECARD_TYPE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">
			<font class="tab_color ">*</font>
			申请人(单位)：
		</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="APPLICANT_UNIT"
				value="${busRecord.APPLICANT_UNIT }" />
		</td>
		<td class="tab_width">
		<font class="tab_color">*</font>
			定着物类型：
		</td>
		<td style="width:430px">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="dzwlx"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
				defaultEmptyText="选择定着物类型" name="DZWLX_TYPE" id="DZWLX_TYPE"
				value="${busRecord.DZWLX_TYPE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">
			<font class="tab_color ">*</font>
			坐落：
		</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" maxlength="60" name="LOCATED"
				value="${busRecord.LOCATED}" style="width: 72%;" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">通知人姓名：</td>
		<td>
			<input type="text" class="tab_text " name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }" />
		</td>
		<td class="tab_width">通知人电话：</td>
		<td>
			<input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11"
				name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="3">
			<input type="text" class="tab_text " maxlength="60" name="REMARK" value="${busRecord.REMARK}"
				style="width: 72%;" />
		</td>
	</tr>
</table>
