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
				<span>权利人信息</span> 
				<a id="bdcdcz_btn" href="javascript:void(0);" style="float:right; margin: 2px 0;display:none;" class="eflowbutton"
					onclick="BDCQLC_bdcdbcz();" name="doBooking">确认登簿</a>
				<a href="javascript:void(0);" class="eflowbutton titleButton" style="float:right; "  onclick="addTable('PowerPeopleInfo');"
					name="addQlrxx">新增 </a>
				<a href="javascript:void(0);" class="eflowbutton titleButton" style="float:right; " onclick="updateTable('PowerPeopleInfo');"
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
						<td class="tab_width">
							<font class="tab_color">*</font>
							权利人姓名：
						</td>
						<td>
							<input type="text" class="tab_text validate[required]" id="POWERPEOPLENAME" name="POWERPEOPLENAME"
								maxlength="62" />
						</td>
						<td colspan="2">
							<!--<a href="javascript:void(0);" style="float:left; margin: 2px 4px;margin-right: 8px;"
								class="eflowbutton" onclick="provePreview();" name="provePreview">预登簿</a>-->
						</td>
					</tr>
					<tr>
						<td class="tab_width">权利比例：</td>
						<td>
							<input type="text" class="tab_text" id="POWERPEOPLEPRO" name="POWERPEOPLEPRO"
								maxlength="62" />
						</td>
						<td class="tab_width">权利人性质：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="QLRXZ"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE">
							</eve:eveselect>
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
						<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[required]" maxlength="30" id="POWERPEOPLEIDNUM"
								style="float: left;" name="POWERPEOPLEIDNUM" />
							<a href="javascript:void(0);" onclick="PowerPeopleRead(this);" class="eflowbutton">读 卡</a>
						</td>
					</tr>
					<tr>
						<td class="tab_width">性别</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="sex"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择性别"
								name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
						</td>
						<td class="tab_width">移动电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="POWERPEOPLEMOBILE" maxlength="11" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">地址：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="POWERPEOPLEADDR" maxlength="62" />
						</td>
						<td class="tab_width">邮政编码：</td>
						<td>
							<input type="text" class="tab_text validate[,custom[chinaZip]]" name="POWERPEOPLEPOSTCODE"
								maxlength="6" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">电子邮件：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="POWERPEOPLEEMAIL" maxlength="62" />
						</td>
						<td class="tab_width"></td>
						<td></td>
					</tr>
					<tr>
						<td class="tab_width">法定代表人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="POWLEGALNAME" maxlength="62" />
						</td>
						<td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType" value="SF"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM"
								style="float: left;" name="POWLEGALIDNUM" />
						</td>
						<td>
							<a href="javascript:void(0);" onclick="PowLegalRead(this);" class="eflowbutton">读 卡</a>
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="tab_width">代理人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="POWAGENTNAME" maxlength="62" />
						</td>
						<td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType" value="SF"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								defaultEmptyText="选择证件类型" name="POWAGENTIDTYPE" id="POWAGENTIDTYPE">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="30" id="POWAGENTIDNUM"
								style="float: left;" name="POWAGENTIDNUM" />
						</td>
						<td>
							<a href="javascript:void(0);" onclick="PowAgentRead(this);" class="eflowbutton">读 卡</a>
						</td>
						<td></td>
					</tr>
					<tr class="bdcdbc_tr" style="display:none;">
						<td class="tab_width">缮证证号：</td>
						<td colspan="3">
							<input type="text" class="tab_text" maxlength="500" id="BDC_SZZH"
								style="float: left; width: 74%;" name="BDC_SZZH" value="${busRecord.BDC_SZZH}" />
								<input type="button" id="BDC_QZVIEW" class="eflowbutton" style="" value="证书预览" onclick="bdcCQZSprint(1)">
								<input type="button" id="BDC_QZPRINT" class="eflowbutton" style="" value="证书打印" onclick="bdcCQZSprint(2)">
						</td>
					</tr>
					<tr class="bdcdbc_tr" style="display:none;">
						<td class="tab_width">权证标识码：</td>
						<td>
							<input type="text" class="tab_text" maxlength="128" id="BDC_QZBSM"
								style="float: left;" name="BDC_QZBSM" value="${busRecord.BDC_QZBSM}" />
							<input id="qzbsmsavebtn" style="display:none;" type="button" class="eflowbutton" value="保存" onclick="saveQzbsm();" />
						</td>
						<td class="tab_width"></td>
						<td></td>
					</tr>
					<tr class="bdcdbc_tr" style="display:none;">
						<td class="tab_width">操作人：</td>
						<td>
							<input type="text" class="tab_text" maxlength="128" id="BDC_CZR"
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
								value="${busRecord.POWREMARK}" style="width: 72%;" />
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>
				<!-- 权力人表格开始 -->
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="PowerPeopleTable">
					<tr>
						<!-- <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>  -->
						<td class="tab_width1" style="width: 30%;color: #000; font-weight: bold;text-align: center;">权力人名称</td>
						<td class="tab_width1" style="width: 35%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
						<td class="tab_width1" style="width: 20%;color: #000; font-weight: bold;text-align: center;">性别</td>
						<td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
				<!-- 权力人表格结束 -->
			</td>
		</tr>
	</table>
	<!-- 权力人信息结束 -->
</div>