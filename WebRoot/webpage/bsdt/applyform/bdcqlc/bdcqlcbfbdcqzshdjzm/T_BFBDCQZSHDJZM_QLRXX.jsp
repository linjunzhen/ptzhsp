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
<!-- 权力人信息开始 -->
<div id="qlrxx" name="bdc_qlrxx">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span class="haveButtonTitle">权利人信息</span> 
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="addTable('PowerPeopleInfo');"
					name="addQlrxx">新增 </a>
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="updateTable('PowerPeopleInfo');"
					name="updateQlrxx">保存</a>
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="PowerPeopleInfo">
		<input type="hidden" name="PowerPeopleInfoTrid" id="PowerPeopleInfoTrid" />
		<tr id="PowerPeopleInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>权利人名称：</td>
						<td>
							<input type="text" class="tab_text" id="POWERPEOPLEPRO" name="POWERPEOPLEPRO" 
								maxlength="62" />
						</td>
						<td class="tab_width">权利比例：</td>
						<td>
							<input type="text" class="tab_text" id="POWERPEOPLEPRO" name="POWERPEOPLEPRO"
								maxlength="62" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">权利人性质：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="QLRXZ"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE">
							</eve:eveselect>
						</td>
						<td class="tab_width">性别</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="sex"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择性别"
								name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							证件类型：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType" value="SF"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择证件类型" name="POWERPEOPLEIDTYPE" id="POWERPEOPLEIDTYPE">
							</eve:eveselect>
						</td>
						<td class="tab_width">
							<font class="tab_color">*</font>
							证件号码：
						</td>
						<td>
							<input type="text" class="tab_text validate[required]" maxlength="30" id="POWERPEOPLEIDNUM"
								 name="POWERPEOPLEIDNUM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="POWERPEOPLEMOBILE" maxlength="11" />
						</td>
						<td class="tab_width">邮政编码：</td>
						<td>
							<input type="text" class="tab_text validate[,custom[chinaZip]]" name="POWERPEOPLEPOSTCODE"
								maxlength="6" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">地址：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" name="POWERPEOPLEADDR" maxlength="62" 
								style="width:72%" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">法定代表人姓名：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" name="POWLEGALNAME" maxlength="62" 
								style="width:72%"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="DocumentType" value="SF"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE">
							</eve:eveselect>
							<a href="javascript:void(0);" onclick="PowLegalRead(this);" style="float:right" class="eflowbutton">身份证读卡</a>
						</td>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM"
								style="float: left;" name="POWLEGALIDNUM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理人姓名：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" name="POWAGENTNAME" maxlength="62" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType" value="SF"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE">
							</eve:eveselect>
							<a href="javascript:void(0);" style="float: right" onclick="PowerPeopleRead(this);" 
								class="eflowbutton">身份证读卡</a>
						</td>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM"
								style="float: left;" name="POWLEGALIDNUM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">缮证证号：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="500" id="BDC_SZZH"
								style="float: left; width: 74%;" name="BDC_SZZH" value="${busRecord.BDC_SZZH}" />
						</td>
						<td colspan="2">
							<a href="javascript:void(0);" style="float:left; margin: 2px 4px;"
								class="eflowbutton" onclick="provePreview();" name="provePreview">预登簿</a>
							<a href="javascript:void(0);" style="float:left; margin: 2px 4px;" class="eflowbutton"
								onclick="doBooking();" name="doBooking">确认登簿</a>
							<a href="javascript:void(0);" style="float:left; margin: 2px 4px;" class="eflowbutton"
								onclick="doBooking();" name="doBooking">证书预览</a>
							<a href="javascript:void(0);" style="float:left; margin: 2px 4px;"class="eflowbutton"
							    onclick="provePreview();" name="provePreview">打印权利证书</a>
						</td>
					</tr> 
					<tr>
						<td class="tab_width">权证标识码：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QZBSM"
								style="float: left;" name="BDC_QZBSM" value="${busRecord.BDC_QZBSM}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">操作人：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_CZR"
								style="float: left;" name="BDC_CZR" value="${busRecord.BDC_CZR}" />
						</td>
						<td class="tab_width">操作时间：</td>
						<td>
							<input type="text" class="tab_text" maxlength="32" id="BDC_CZSJ" style="float: left;"
								name="BDC_CZSJ" value="${busRecord.BDC_CZSJ}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">备注：</td>
						<td colspan="3">
							<input type="text" class="tab_text" maxlength="60" name="POWREMARK"
								value="${busRecord.POWREMARK}" style="width: 74%;" />
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>
				<!-- 权力人表格开始 -->
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="PowerPeopleTable">
					<tr>
						<!-- <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>  -->
						<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">权力人名称</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">性别</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
				<!-- 权力人表格结束 -->
			</td>
		</tr>
	</table>
</div>
<!-- 权力人信息结束 -->