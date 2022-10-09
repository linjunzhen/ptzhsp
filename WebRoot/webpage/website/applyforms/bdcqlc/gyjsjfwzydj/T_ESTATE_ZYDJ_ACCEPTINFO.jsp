<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>受理信息</span></li>
		</ul>
	</div>
	<div style="padding: 25px 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1" id="slxx_div">
			<tr>
				<th><span class="bscolor1">*</span>登记类型：</th>
				<td><input type="text" class="tab_text " disabled="disabled" name="CATALOG_NAME" value="转移登记" /></td>
				<th><span class="bscolor1">*</span>权利类型：</th>
				<td><input type="text" class="tab_text " disabled="disabled" name="ITEM_NAME"
						value="${serviceItem.ITEM_NAME }" /></td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>类型：</th>
				<td colspan="3">
					<eve:radiogroup fieldname="ZYDJ_TYPE" width="500" onclick="queryTypeFn();" typecode="zydjlx4"
						defaultvalue="1" value="${busRecord.ZYDJ_TYPE}"></eve:radiogroup>
				</td>
			</tr>
			<tr id="hthCheck">
				<th>合同备案号：</th>
				<td colspan="3"><input type="text" class="tab_text validate[]" name="HTBAH"
						value="${busRecord.HTBAH }" />
					<input type="button" style="margin-left: 50px;" class="eflowbutton" value="查询备案信息"
						onclick="showSelectFdchtbacx();" />
				</td>
			</tr>
			<tr>
				<th>不动产单元号：</th>
				<td colspan="3"><input type="text" class="tab_text validate[custom[estateNum]]" name="ESTATE_NUM"
						value="${busRecord.ESTATE_NUM }" />
					<input type="button" style="margin-left: 50px;" class="eflowbutton" value="查询不动产档案信息"
						onclick="showSelectBdcfwzdcx();" />
				</td>
			</tr>
			<tr>
				<th>
					<font class="tab_color ">*</font>申请人(单位)：
				</th>
				<td><input type="text" class="tab_text validate[required]" maxlength="32" name="APPLICANT_UNIT"
						value="${busRecord.APPLICANT_UNIT }" onchange="setTzrxmValue(this.value);" /></td>
				<th><span class="bscolor1">*</span>定着物权利类型：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="dzwlx"
						dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
						defaultEmptyText="选择定着物权利类型" name="DZWQLLX" id="DZWQLLX" value="${busRecord.DZWQLLX}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>坐落：</th>
				<td>
					<input type="text" class="tab_text w280 validate[required]" maxlength="160" name="LOCATED"
						value="${busRecord.LOCATED}" />
				</td>
				<th><span class="bscolor1">*</span>持证类型：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="CZLX"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE"
						value="${busRecord.TAKECARD_TYPE}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th>通知人姓名：</th>
				<td><input type="text" class="tab_text " name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }" /></td>
				<th>通知人电话：</th>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11" name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}" /></td>
			</tr>
			<tr name="lzrTr">
				<th>领证人姓名：</th>
				<td><input type="text" class="tab_text " name="QZR_NAME" value="${busRecord.QZR_NAME }" /></td>
				<th>领证人证件号：</th>
				<td><input type="text" class="tab_text" maxlength="64" name="QZR_ZJH" value="${busRecord.QZR_ZJH}" />
				</td>
			</tr>
			<tr id="isfinishtax_tr" style="display:none;">
				<th><span class="bscolor1">*</span>是否完税：</th>
				<td colspan="3">
					<eve:radiogroup fieldname="IS_FINISHTAX" width="500" typecode="YesOrNo"
						value="${busRecord.IS_FINISHTAX}"></eve:radiogroup>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60" name="REMARK" style="width: 850px;"
						value="${busRecord.REMARK}" />
				</td>
			</tr>
			<tr style="display: none;" id="powTr">
				<th>是否办理电力过户：</th>
				<td>
					<eve:radiogroup fieldname="IS_POWTRANSFER" width="500" typecode="YesOrNo" defaultvalue="0"
						onclick="checkIsPowTransfer(this.value)" value="${busRecord.IS_POWTRANSFER}"></eve:radiogroup>
				</td>
				<th style="display: none;" id="dlhhName">
					<font class="tab_color">*</font>电力户号：
				</th>
				<td style="display: none;" id="dlhhInput"><input type="text" class="tab_text validate[required]"
						maxlength="64" name="POW_NUM" value="${busRecord.POW_NUM}" />
				</td>
			</tr>
			<tr style="display: none;" id="waterTr">
				<th>是否办理水力过户：</th>
				<td>
					<eve:radiogroup fieldname="IS_WATERTRANSFER" width="500" typecode="YesOrNo" defaultvalue="0"
						onclick="checkIsWaterTransfer(this.value)" value="${busRecord.IS_WATERTRANSFER}">
					</eve:radiogroup>
				</td>
				<th style="display: none;" id="slhhName">
					<font class="tab_color">*</font>水力户号：
				</th>
				<td style="display: none;" id="slhhInput"><input type="text" class="tab_text validate[required]"
						maxlength="64" name="WATER_NUM" value="${busRecord.WATER_NUM}" />
				</td>
			</tr>
			<tr style="display: none;" id="gasTr">
				<th>是否办理燃气过户：</th>
				<td>
					<eve:radiogroup fieldname="IS_GASTRANSFER" width="500" typecode="YesOrNo" defaultvalue="0"
						onclick="checkIsGasTransfer(this.value)" value="${busRecord.IS_GASTRANSFER}"></eve:radiogroup>
				</td>
				<th style="display: none;" id="rqhhName">
					<font class="tab_color">*</font>燃气户号：
				</th>
				<td style="display: none;" id="rqhhInput"><input type="text" class="tab_text validate[required]"
						maxlength="64" name="GAS_NUM" value="${busRecord.GAS_NUM}" />
				</td>
			</tr>
		</table>
	</div>
</div>
