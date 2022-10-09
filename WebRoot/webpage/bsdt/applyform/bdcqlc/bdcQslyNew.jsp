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
<div class="tab_height"></div>
<!-- 权属来源信息开始 -->
<div id="qslyxx" name="bdc_qslyxx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>权属来源</span>
<%--				<div id="bdcdacxButton" style="display:none; float: right;">--%>
<%--					<a href="javascript:void(0);" class="eflowbutton titleButton" --%>
<%--							onclick="showSelectBdcdaxxcx();">不动产档案查询 </a>--%>
<%--				</div>--%>
				<div id="addOrSaveButton" style="float: right;">
					<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="addTable('PowerSourceInfo');"
						name="addQslyxx">新增 </a>
					<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="updateTable('PowerSourceInfo');"
						name="updateQslyxx">保存</a>
				</div>	
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
								dataInterface="dictionaryService.findDatasForSelect" onchange="setQSLYLX(this.value);"
								defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE">
							</eve:eveselect>
						</td>
						<td class="tab_width">
							<font class="tab_color">*</font>
							权属文号：
						</td>
						<td>
							<input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCEIDNUM"
								style="float: left;" name="POWERSOURCEIDNUM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">不动产单元号：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_BDCDYH"
								style="float: left;" name="BDC_BDCDYH" />
						</td>
						<td class="tab_width">权属状态：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="QSZT"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择权属状态" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">权力人：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" 
								style="float: left;" id="QLR" name="QLR" />
						</td>
						<td class="tab_width">宗地代码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_ZDDM"
								style="float: left;" name="BDC_ZDDM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_QLRZJLX" id="BDC_QLRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QLRZJHM"
								style="float: left;" name="BDC_QLRZJHM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">法定代表人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRXM"
								style="float: left;" name="BDC_FDDBRXM" />
						</td>
						<td class="tab_width">法定代表人电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRDH"
								style="float: left;" name="BDC_FDDBRDH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代表人证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_FDDBRZJLX" id="BDC_FDDBRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">代表人证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRZJHM"
								style="float: left;" name="BDC_FDDBRZJHM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRXM"
								style="float: left;" name="BDC_DLRXM" />
						</td>
						<td class="tab_width">代理人电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRDH"
								style="float: left;" name="BDC_DLRDH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理人证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_DLRZJLX" id="BDC_DLRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">代理人证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRZJHM"
								style="float: left;" name="BDC_DLRZJHM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理机构名称：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLJGMC"
								style="float: left;" name="BDC_DLJGMC" />
						</td>
						<td class="tab_width">权力开始时间：</td>
						<td>
							<input type="text" id="BDC_QLKSSJ" name="BDC_QLKSSJ" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width">权力结束时间1：</td>
						<td>
							<input type="text" id="BDC_QLJSSJ1" name="BDC_QLJSSJ1" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate"/>
						</td>
						<td class="tab_width">权力结束时间2：</td>
						<td>
							<input type="text" id="BDC_QLJSSJ2" name="BDC_QLJSSJ2" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width">备注：</td>
						<td colspan="5">
							<input type="text" class="tab_text validate[]" maxlength="2000" id="BDC_QSLYBZ"
								style="float: left;" style="width: 72%" name="BDC_QSLYBZ" />
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>

				<!-- 权属来源明表格开始 -->
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="PowerSourceTable">
					<tr>
						<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td> 
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">幢号</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">户号</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">不动产权证号</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权属状态</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权力开始时间</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
				<!-- 权属来源明表格结束 -->

			</td>
		</tr>
	</table>
	<!-- 权属来源信息结束 -->
</div>