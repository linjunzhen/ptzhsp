<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.platform.system.model.SysUser" %>
<%@ page import="net.evecom.core.util.AppUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<input type="hidden" name="isBank" value="${isBank}">
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx_div">
	<tr>
		<th colspan="5">受理信息
		</th>
	</tr>
	<tr>
		<td class="tab_width">登记类型：</td>
		<td><input type="text" class="tab_text " style="width: 72%;"
			name="CATALOG_NAME" value="${busRecord.CATALOG_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td colspan="2"><input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
				   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/></td>
	</tr>
	
	<tr id="hthCheck">
		<td class="tab_width">合同备案号</td>
		<td colspan="2"><input type="text" class="tab_text validate[]" style="width: 72%;"
				   name="HTBAH" value="${busRecord.HTBAH }" maxlength="32"/>
			<!-- <input type="button" class="eflowbutton" value="查询备案信息" onclick="showSelectFdchtbacx();"/> --></td>
	</tr>
	<tr>
		<td class="tab_width">不动产权证号/不动产登记证明号：</td>
		<td><input type="text" class="tab_text validate[]" style="width: 72%;"
				   name="ESTATE_NUM" value="${busRecord.ESTATE_NUM }" maxlength="128"/>
			<!-- <input type="button" class="eflowbutton" value="查询不动产档案信息" onclick="showSelectBdcfwzdcx();"/> -->
			</td>
		<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
		<td style="width:430px" colspan="2">
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
        <td style="width:430px" colspan="2">
            <eve:eveselect clazz="tab_text validate[required]" dataParams="dzwlx" 
            dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
            defaultEmptyText="选择定着物权利类型" name="DZWQLLX" id="DZWQLLX" value="${busRecord.DZWQLLX}">
            </eve:eveselect>
        </td>
	</tr>
	<tr>
	    <td class="tab_width"><font class="tab_color">*</font>坐落：</td>
        <td colspan="4">
            <input type="text" class="tab_text validate[required]" maxlength="128" style="width: 72%;"
            name="LOCATED" value="${busRecord.LOCATED}" />
        </td>
	</tr>
	<tr>
		<td class="tab_width">通知人姓名：</td>
		<td><input type="text" class="tab_text " style="width: 72%;"
				   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }" maxlength="32"/></td>
		<td class="tab_width">通知人电话：</td>
		<td colspan="2"><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11" style="width: 80%;"
			name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
	</tr>
	<tr>
        <td class="tab_width">领证人姓名：</td>
        <td><input type="text" class="tab_text " style="width: 72%;"
                   name="QZR_NAME" value="${busRecord.QZR_NAME }" maxlength="32"/></td>
        <td class="tab_width">领证人证件号：</td>
        <td colspan="2"><input type="text" class="tab_text" maxlength="64" style="width: 80%;"
            name="QZR_ZJH" value="${busRecord.QZR_ZJH}"  />
            <input type="button" class="eflowbutton" value="读 卡" onclick="LZRRead();"/>
         </td>
    </tr>
    <tr id="isfinishtax_tr" style="display:none;">
         <td class="tab_width"><font class="tab_color">*</font>是否完税：</td>
         <td colspan="4">
             <eve:radiogroup fieldname="IS_FINISHTAX" width="500"  typecode="YesOrNo" value="${busRecord.IS_FINISHTAX}"></eve:radiogroup>
         </td>
     </tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="4"><input type="text" class="tab_text validate[]" maxlength="128"
			name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
		</td>
	</tr>
	<%-- <tr style="display: none;" id="powTr">
		<td>是否办理电力过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_POWTRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsPowTransfer(this.value)" value="${busRecord.IS_POWTRANSFER}"></eve:radiogroup>
		</td>
		<td style="display: none;" id="dlhhName"><font class="tab_color">*</font>电力户号：</td>
		<td style="display: none;" id="dlhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="POW_NUM" value="${busRecord.POW_NUM}"  />
		</td>
		<td>
			<a href="executionController.do?goDetail&exeId=${busRecord.POW_EXEID}" target="_blank">${busRecord.POW_EXEID}</a>
		</td>
	</tr>
	<tr style="display: none;" id="waterTr">
		<td>是否办理水力过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_WATERTRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsWaterTransfer(this.value)" value="${busRecord.IS_WATERTRANSFER}"></eve:radiogroup>
		</td>
		<td style="display: none;" id="slhhName"><font class="tab_color">*</font>水力户号：</td>
		<td style="display: none;" id="slhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="WATER_NUM" value="${busRecord.WATER_NUM}"  />
		</td>
		<td>
			<a href="executionController.do?goDetail&exeId=${busRecord.WATER_EXEID}" target="_blank">${busRecord.WATER_EXEID}</a>
		</td>
	</tr>
	<tr style="display: none;" id="gasTr">
		<td>是否办理燃气过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_GASTRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsGasTransfer(this.value)" value="${busRecord.IS_GASTRANSFER}"></eve:radiogroup>
		</td>
		<td style="display: none;" id="rqhhName"><font class="tab_color">*</font>燃气户号：</td>
		<td style="display: none;" id="rqhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="GAS_NUM" value="${busRecord.GAS_NUM}"  />
		</td>
		<td>
			<a href="executionController.do?goDetail&exeId=${busRecord.GAS_EXEID}" target="_blank">${busRecord.GAS_EXEID}</a>
		</td>
	</tr>
	<tr style="display: none;" id="svaTr">
		<td>是否办理广电过户：</td>
		<td>
			<eve:radiogroup fieldname="IS_SVATRANSFER" width="500"  typecode="YesOrNo" defaultvalue="0" onclick="checkIsSVATransfer(this.value)" value="${busRecord.IS_SVATRANSFER}"></eve:radiogroup>
		</td>
		<td style="display: none;" id="gdhhName"><font class="tab_color">*</font>广电户号：</td>
		<td style="display: none;" id="gdhhInput"><input type="text" class="tab_text validate[required]" maxlength="64"
														 name="SVA_NUM" value="${busRecord.SVA_NUM}"  />
		</td>
		<td>
			<a href="executionController.do?goDetail&exeId=${busRecord.SVA_EXEID}" target="_blank">${busRecord.SVA_EXEID}</a>
		</td>
	</tr> --%>
</table>
