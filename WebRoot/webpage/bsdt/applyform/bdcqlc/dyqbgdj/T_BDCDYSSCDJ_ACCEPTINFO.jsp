<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">受理信息
			<a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="sfspb" onclick="errorAction();">审批表（双方）</a>
			<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="dfspb" onclick="errorAction();">审批表（单方）</a>
		</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" 
			name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled"
				   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>类型：</td>
		<td colspan="3">
			<input type="radio" name="TAKECARD_TYPE" value="1" <c:if test="${busRecord.TAKECARD_TYPE!='2'}">checked="checked"</c:if> >抵押权变更登记
			<input type="radio" name="TAKECARD_TYPE" value="2" <c:if test="${busRecord.TAKECARD_TYPE=='2'}">checked="checked"</c:if> >在建工程抵押权变更登记
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]"
				   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>坐落：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="LOCATED" value="${busRecord.LOCATED}" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>通知人姓名：</td>
		<td><input type="text" class="tab_text validate[required]"
				   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
		<td class="tab_width"><font class="tab_color ">*</font>通知人电话：</td>
		<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
			name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
	</tr>
	<tr>
        <td class="tab_width">领证人姓名：</td>
        <td><input type="text" class="tab_text " 
                   name="QZR_NAME" value="${busRecord.QZR_NAME }"/></td>
        <td class="tab_width">领证人证件号：</td>
        <td>
          <input type="text" class="tab_text" maxlength="64" name="QZR_ZJH" value="${busRecord.QZR_ZJH}"  />
          <input type="button" class="eflowbutton" value="读 卡" onclick="LZRRead();"/>
        </td>
    </tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
			name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
		</td>
	</tr>
</table>
