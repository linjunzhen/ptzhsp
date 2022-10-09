<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
.haveButtonTitle {
	position: absolute;
	left: 47%;
}
.titleButton {
	float: right;
	margin: 2px 0;
	margin-right: 10px;
	padding: 0 20px !important;
}
</style>
<!-- 权属来源信息开始 -->
<div id="qslyxx" name="bdc_qslyxx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>权属来源</span>
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>

	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="PowerSourceInfo">
		<input type="hidden" name="PowerSourceInfoTrid" id="PowerSourceInfoTrid" />
		<tr id="PowerSourceInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							权属来源类型：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="QSLYLX"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE">
							</eve:eveselect>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>证书（文号）：</td>
						<td>
							<input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCEIDNUM"
								style="float: left;" name="POWERSOURCEIDNUM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							不动产单元号：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[required]" maxlength="128" id="BDC_BDCDYH"
								style="float: left;" name="BDC_BDCDYH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">权力人：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="QSZT"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择权属状态" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE">
							</eve:eveselect>
						</td>	
						<td class="tab_width"><font class="tab_color">*</font>权属状态：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="QSZT"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择权属状态" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_QLRZJLX" id="BDC_QLRZJLX">
							</eve:eveselect>
							<!-- 读卡 -->
						</td>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text" maxlength="128" id="BDC_QLRZJHM"
								style="float: left;" name="BDC_QLRZJHM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">宗地代码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_ZDDM"
								style="float: left;" name="BDC_ZDDM" />
						</td>
						<td class="tab_width">权力开始时间：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QLKSSJ"
								style="float: left;" name="BDC_QLKSSJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">权力结束时间1：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QLJSSJ1"
								style="float: left;" name="BDC_QLJSSJ1" />
						</td>
						<td class="tab_width">权力结束时间2：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QLJSSJ2"
								style="float: left;" name="BDC_QLJSSJ2" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">备注：</td>
						<td colspan="3">
							<input type="text" class="tab_text" maxlength="2000" id="BDC_QSLYBZ"
								 style="width:72%" name="BDC_QSLYBZ" />
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>

				<!-- 权属来源明表格开始 -->
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="PowerSourceTable">
					<tr>
						<!-- <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>  -->
						<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">权利人</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
						<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">权属来源类型</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">证书（文号）</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
				<!-- 权属来源明表格结束 -->

			</td>
		</tr>
	</table>
</div>
<!-- 权属来源信息结束 -->
