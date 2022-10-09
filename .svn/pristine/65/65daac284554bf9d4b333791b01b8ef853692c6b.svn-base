<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.platform.system.model.SysUser" %>
<%@ page import="net.evecom.core.util.AppUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx_div">
	<tr>
		<th colspan="4">受理信息
		</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
			name="CATALOG_NAME" value="首次登记"/></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
				   name="ITEM_NAME" value="国有建设用地使用权首次登记"/></td>
	</tr>
	<tr>
		<td class="tab_width">不动产单元号：</td>
		<td><input type="text" class="tab_text validate[custom[estateNum]]" style="width: 72%;"
				   name="ESTATE_NUM" value="${busRecord.ESTATE_NUM }"/>
			<input type="button" class="eflowbutton" value="查询宗地基本信息" onclick="showSelectBdcZdjbxx();"/>
			</td>
		<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
		<td style="width:430px">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX" 
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
		<td><input type="text" class="tab_text validate[required]" style="width: 72%;" maxlength="32"
				   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
		<td class="tab_width">委托期限：</td>
        <td><%-- <input type="text" class="tab_text validate[required]" style="width: 72%;" maxlength="32"
				   name="WTQX" value="${busRecord.WTQX }"/> --%>
		    <input type="text" id="WTQX_BEGIN" name="WTQX_BEGIN"
					value="${busRecord.WTQX_BEGIN}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'WTQX_END\')}'})"
					class="tab_text Wdate" maxlength="32" style="width:130px;"  />至
			<input type="text" id="WTQX_END" name="WTQX_END"
					value="${busRecord.WTQX_END}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'WTQX_BEGIN\')}'})"
					class="tab_text Wdate" maxlength="32" style="width:130px;"  />
		</td>
	</tr>
	<tr>
	    <td class="tab_width"><font class="tab_color">*</font>坐落：</td>
        <td colspan="3">
            <input type="text" class="tab_text validate[required]" maxlength="160" style="width: 72%;"
            name="LOCATED" value="${busRecord.LOCATED}" />
        </td>
	</tr>
	<tr>
		<td class="tab_width">通知人姓名：</td>
		<td><input type="text" class="tab_text " style="width: 72%;"
				   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
		<td class="tab_width">通知人电话：</td>
		<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11" style="width: 80%;"
			name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
	</tr>
	<tr>
        <td class="tab_width">领证人姓名：</td>
        <td><input type="text" class="tab_text " style="width: 72%;"
                   name="QZR_NAME" value="${busRecord.QZR_NAME }"/></td>
        <td class="tab_width">领证人证件号：</td>
        <td><input type="text" class="tab_text" maxlength="64" style="width: 80%;"
            name="QZR_ZJH" value="${busRecord.QZR_ZJH}"  />
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
