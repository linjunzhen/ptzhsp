<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<style>
	.inputWidth{
		width:350px;
	}
</style>
<%-- 不动产权注销登记  受理信息 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx">
	<tr>
		<th colspan="4">受理信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" 
			name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ZDQLLX"
			dataInterface="dictionaryService.findDatasForSelect" value=""
			defaultEmptyText="选择权利类型" name="QLLX" id="accept_QLLX">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color"></font>类型：</td>
		<td colspan="3">
			<input type="radio" name="ZX_TYPE" value="1" <c:if test="${busRecord.ZX_TYPE!='2'}">checked="checked"</c:if> >不动产注销登记
			<input type="radio" name="ZX_TYPE" style="margin-left:50px" value="2" <c:if test="${busRecord.ZX_TYPE=='2'}">checked="checked"</c:if> >依职权不动产注销登记
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*不动产单元号：</font></td>
		<td><input type="text" class="tab_text"  name="ESTATE_NUM" value="${busRecord.ESTATE_NUM}"></td>
		<td class="tab_width"><font class="tab_color">*</font>定着物类型</td>
		<td><eve:eveselect clazz="tab_text validate[required]"  dataParams="dzwlx"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.DZWLX}"
			defaultEmptyText="选择定着物类型" name="accept_DZWLX" id="accept_DZWLX">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
		<td><input type="text" class="tab_text validate[required]"
				   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
				   <td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
		    name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">坐落：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="LOCATED" style="width:945px" value="${busRecord.LOCATED}" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">通知人姓名：</td>
		<td><input type="text" class="tab_text "
				   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
		<td class="tab_width">通知人电话：</td>
		<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="16"
			name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
	</tr>
	<tr>
		<td class="tab_width">受理人员：</td>
		<td><input type="text" class="tab_text " maxlength="32"
				   name="SLRY" id="SLRY" value="${busRecord.SLRY}"/></td>
		<td colspan="2">
			<a href="javascript:void(0);" class="eflowbutton" onclick="errorAction()" name="dyslhzd" id="dyslhzd">打印受理回执单</a>
			<a href="javascript:void(0);" class="eflowbutton" onclick="errorAction()" name="dysqb" id="dysqb">打印申请表</a>
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td><input type="text" class="tab_text validate[]" maxlength="60"
			name="REMARK" value="${busRecord.REMARK}" style="width: 95%;"  />
		</td>
		<td colspan="2">
			<a href="javascript:void(0);" class="eflowbutton" onclick="errorAction()" name="dybysl" id="dybysl">不予受理</a>
			<a href="javascript:void(0);" class="eflowbutton" onclick="errorAction()" name="dybjcl" id="dybjcl">补交材料</a>
		</td>
	</tr>
	
</table>
	
	
	
	
	
