<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.platform.system.model.SysUser" %>
<%@ page import="net.evecom.core.util.AppUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	SysUser curUser = AppUtil.getLoginUser();
	if (curUser.getRoleCodes().contains("BDCBANKROLE")) {
		request.setAttribute("isBank", true);
	} else {
		request.setAttribute("isBank", false);
	}
%>
<input type="hidden" name="isBank" value="${isBank}">
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx_div">
	<tr>
		<th colspan="4">受理信息
		</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
			name="CATALOG_NAME" value="转移登记"/></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
				   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/></td>
	</tr>
	<tr id="lx">
		<td class="tab_width"><font class="tab_color">*</font>类型：</td>
		<td colspan="3">
			<c:if test="${ param.isKfsywsl == null && busRecord.IS_KFSYWSL == null}">
				<c:if test="${isBank eq 'false'}">
					<eve:radiogroup fieldname="ZYDJ_TYPE" width="500" typecode="zydjlx1" defaultvalue="4" value="${busRecord.ZYDJ_TYPE}"></eve:radiogroup>
				</c:if>
				<c:if test="${isBank eq 'true'}">
					<eve:radiogroup fieldname="ZYDJ_TYPE" width="500" typecode="zydjlx3" defaultvalue="4" value="${busRecord.ZYDJ_TYPE}"></eve:radiogroup>
				</c:if>
			</c:if>
			<c:if test="${ !(param.isKfsywsl == null && busRecord.IS_KFSYWSL == null)}">
				<eve:radiogroup fieldname="ZYDJ_TYPE" width="500" typecode="zydjlx2" defaultvalue="1" value="${busRecord.ZYDJ_TYPE}"></eve:radiogroup>
			</c:if>
		</td> 
	</tr>
	<tr id="hthCheck">
		<td class="tab_width">合同备案号</td>
		<td><input type="text" class="tab_text validate[]" style="width: 72%;"
				   name="HTBAH" value="${busRecord.HTBAH }" />
			<input type="button" class="eflowbutton" value="查询备案信息" onclick="showSelectFdchtbacx();"/></td>
	</tr>
	<tr>
		<td class="tab_width">不动产单元号：</td>
		<td><input type="text" class="tab_text validate[custom[estateNum]]" style="width: 72%;"
				   name="ESTATE_NUM" value="${busRecord.ESTATE_NUM }"/>
			<input type="button" class="eflowbutton" value="查询不动产档案信息" onclick="showSelectBdcfwzdcx();"/>
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
		<td class="tab_width"><font class="tab_color">*</font>定着物权利类型：</td>
        <td style="width:430px">
            <eve:eveselect clazz="tab_text validate[required]" dataParams="dzwlx" 
            dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
            defaultEmptyText="选择定着物权利类型" name="DZWQLLX" id="DZWQLLX" value="${busRecord.DZWQLLX}">
            </eve:eveselect>
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
    <tr id="isfinishtax_tr" style="display:none;">
         <td class="tab_width"><font class="tab_color">*</font>是否完税：</td>
         <td colspan="3">
             <eve:radiogroup fieldname="IS_FINISHTAX" width="500"  typecode="YesOrNo" value="${busRecord.IS_FINISHTAX}"></eve:radiogroup>
         </td>
     </tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
			name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
		</td>
	</tr>
	<tr id="powTr">
		<td>是否办理电力过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_POWTRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsPowTransfer(this.value)" value="${busRecord.IS_POWTRANSFER}"></eve:radiogroup>
		</td>
		<td id="dlhhName"><font class="tab_color">*</font>电力户号：</td>
		<td id="dlhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="POW_NUM" value="${busRecord.POW_NUM}"  />
		</td>
	</tr>
	<tr id="waterTr">
		<td>是否办理水力过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_WATERTRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsWaterTransfer(this.value)" value="${busRecord.IS_WATERTRANSFER}"></eve:radiogroup>
		</td>
		<td id="slhhName"><font class="tab_color">*</font>水力户号：</td>
		<td id="slhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="WATER_NUM" value="${busRecord.WATER_NUM}"  />
		</td>
	</tr>
	<tr id="gasTr">
		<td>是否办理燃气过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_GASTRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsGasTransfer(this.value)" value="${busRecord.IS_GASTRANSFER}"></eve:radiogroup>
		</td>
		<td id="rqhhName"><font class="tab_color">*</font>燃气户号：</td>
		<td id="rqhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="GAS_NUM" value="${busRecord.GAS_NUM}"  />
		</td>
	</tr>
	<tr id="svaTr">
		<td>是否办理广电过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_SVATRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsSvaTransfer(this.value)" value="${busRecord.IS_SVATRANSFER}"></eve:radiogroup>
		</td>
		<td id="gdhhName"><font class="tab_color">*</font>广电户号：</td>
		<td id="gdhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="SVA_NUM" value="${busRecord.SVA_NUM}"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">许可文件编号：</td>
		<td><input type="text" class="tab_text " style="width: 80%;"
				   name="XKFILE_NUM" value="${busRecord.XKFILE_NUM }" disabled="disabled"/></td>
		<td class="tab_width">许可文件名称：</td>
		<td><input type="text" class="tab_text " style="width: 80%;"
			       name="XKFILE_NAME" value="${busRecord.XKFILE_NAME}" disabled="disabled"/></td>
	</tr>
</table>
