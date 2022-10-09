<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
.tab_text_1 {
    width: 350px;
    height: 24px;
    line-height: 24px;
    /* padding: 0 5px; */
    padding: 2px 3px 2px 1px;
    border: 1px solid #bbb;
}

.tab_text1 {
		    width: 50%;
		    height: 25px;
		    line-height: 25px;
		    padding: 0 5px;
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}
</style>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerPeopleInfo">
	<tr>
		<th >权利人信息</th>
		<td> <input type="button" class="eflowbutton"  name="addOnePowerPeopleInfo" value="新增" onclick="addPowerPeopleInfo();"> </td>
	</tr>
	<tr id="powerPeopleInfo_1">
		<td >
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权利人姓名：</td>
					<td ><input type="text" class="tab_text_1 validate[required]" 
						name="POWERPEOPLENAME" maxlength="62" onblur="checkLimitPerson();"/></td>
					<td class="tab_width"><font class="tab_color">*</font>权利比例：</td>
					<td ><input type="text" class="tab_text_1 validate[required]" 
						name="POWERPEOPLEPRO" maxlength="62"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权利人性质：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[]" dataParams="QLRXZ"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE" >
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWERPEOPLEIDTYPE" id="POWERPEOPLEIDTYPE" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[required]" maxlength="30" id="POWERPEOPLEIDNUM" style="float: left;"
						name="POWERPEOPLEIDNUM" onblur="checkLimitPerson();"/>
					</td>
					<td class="tab_width"><a href="javascript:void(0);" onclick="PowerPeopleRead(this);" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<input type="hidden" name="QLR_FILE_URL" >
				<input type="hidden" name="QLR_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
	<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="QLR_FILE_DIV"></div><a id="QLR_FILE_BTN"></a>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:QLRchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=QLR_fileListDiv></div>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">性别</td>
					<td>
					<eve:eveselect clazz="tab_text_1"
										dataParams="sex"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="选择性别" name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
					</td>
					<td class="tab_width">移动电话：</td>
					<td><input type="text" class="tab_text_1 validate[]" 
						name="POWERPEOPLEMOBILE" maxlength="11"/></td>
				</tr>
				<tr>					
					<td class="tab_width">地址：</td>
					<td><input type="text" class="tab_text_1 validate[]" 
						name="POWERPEOPLEADDR" maxlength="62"/></td>
					<td class="tab_width">邮政编码：</td>
					<td><input type="text" class="tab_text_1 validate[,custom[chinaZip]]" 
						name="POWERPEOPLEPOSTCODE" maxlength="6"/></td>
				</tr>
				<tr>					
					<td class="tab_width">法定代表人姓名：</td>
					<td ><input type="text" class="tab_text_1 validate[]" 
						name="POWLEGALNAME" maxlength="62" onblur="checkLimitPerson();"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[]" maxlength="30" id="POWLEGALIDNUM" style="float: left;"
						name="POWLEGALIDNUM"  onblur="checkLimitPerson();"/>
					</td>
					<td class="tab_width"><a href="javascript:void(0);" onclick="PowLegalRead(this);" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<input type="hidden" name="LEGAL_FILE_URL" >
				<input type="hidden" name="LEGAL_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
	<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="LEGAL_FILE_DIV"></div><a id="LEGAL_FILE_BTN"></a>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:LEGALchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=LEGAL_fileListDiv></div>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">代理人姓名：</td>
					<td ><input type="text" class="tab_text_1 validate[]" 
						name="POWAGENTNAME" maxlength="62" onblur="checkLimitPerson();"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWAGENTIDTYPE" id="POWAGENTIDTYPE" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[]" maxlength="30" id="POWAGENTIDNUM" style="float: left;"
						name="POWAGENTIDNUM" onblur="checkLimitPerson();" />
					</td>
					<td class="tab_width"><a href="javascript:void(0);" onclick="PowAgentRead(this);" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<input type="hidden" name="POWAGENT_FILE_URL" >
				<input type="hidden" name="POWAGENT_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
	<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="POWAGENT_FILE_DIV"></div><a id="POWAGENT_FILE_BTN"></a>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:POWAGENTchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=POWAGENT_fileListDiv></div>
					</td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text_1" maxlength="60"
						name="POWREMARK" value="${busRecord.POWREMARK}" style="width: 72%;"  />
					</td>
				</tr>
			</table>			
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerPeopleInfo" value="删除" onclick="deletePowerPeopleInfo('1');">
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerSourceInfo">
	<tr>
		<th >权属来源</th>
		<td> <input type="button" class="eflowbutton"  name="addOnePowerSourceInfo" value="新增" onclick="showSelectBdcdaxxcx('2');"> </td>
	</tr>
	<tr id="powerSourceInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权属来源类型：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[required]" dataParams="QSLYLX"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权属来源类型" name="POWERSOURCE_QSLYLX" id="POWERSOURCE_QSLYLX">
						</eve:eveselect>
					</td>
					<td class="tab_width">权属文号：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[]" maxlength="30" style="float: left;"
						name="POWERSOURCE_QSWH" id="POWERSOURCE_QSWH" readonly='readonly'/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权利人：</td>
					<td>
					    <input type="text" class="tab_text_1 validate[required]" maxlength="30" style="float: left;"
						name="POWERSOURCE_QLRMC" id="POWERSOURCE_QLRMC" onblur="checkLimitPerson();" readonly='readonly'/>
						<%-- <eve:eveselect clazz="tab_text_1 validate[required]" dataParams="QSZT"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权利人" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE" >
						</eve:eveselect> --%>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>产权状态：</td>
					<td>
					     <input type="text" class="tab_text_1 validate[required]" maxlength="30" style="float: left;"
						name="POWERSOURCE_CQZT" id="POWERSOURCE_CQZT" readonly='readonly'/>
						<%-- <eve:eveselect clazz="tab_text_1 validate[required]" dataParams="CQZT"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="选择产权状态" name="POWERSOURCE_CQZT" id="POWERSOURCE_CQZT">
						</eve:eveselect> --%>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<input type="text" class="tab_text_1 validate[required]" maxlength="30" id="POWERSOURCE_ZJLX" style="float: left;"
						name="POWERSOURCE_ZJLX"  readonly="readonly"/> 
						<%-- <eve:eveselect clazz="tab_text_1 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWERSOURCE_ZJLX" id="POWERSOURCE_ZJLX">
						</eve:eveselect> --%>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[]" maxlength="30" id="POWERSOURCE_ZJHM" style="float: left;"
						name="POWERSOURCE_ZJHM" onblur="checkLimitPerson();" readonly='readonly'/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">法定代表人姓名：</td>
					<td ><input type="text" class="tab_text_1 validate[]" 
						name="POWERSOURCE_FRDB"  id="POWERSOURCE_FRDB" maxlength="62" onblur="checkLimitPerson();" readonly='readonly'/></td>
					<td class="tab_width">联系电话：</td>
					<td ><input type="text" class="tab_text_1 validate[]" 
						name="POWERSOURCE_FRDH" id="POWERSOURCE_FRDH" maxlength="62" readonly='readonly'/></td>
				</tr>
				<tr>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[]" dataParams="DocumentType" value="SF"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWERSOURCE_FRZJLX" id="POWERSOURCE_FRZJLX" >
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[]" maxlength="30" id="POWERSOURCE_FRZJHM" style="float: left;"
						name="POWERSOURCE_FRZJHM"  onblur="checkLimitPerson();" readonly='readonly'/>
						<a href="javascript:void(0);" onclick="PowFRDHRead(this);"  class="eflowbutton">读 卡</a>
					</td>
				</tr>
				<input type="hidden" name="FRDB_FILE_URL" >
				<input type="hidden" name="FRDB_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
	<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="FRDB_FILE_DIV"></div><a id="FRDB_FILE_BTN"></a>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:FRDBchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=FRDB_fileListDiv></div>
					</td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text_1" maxlength="60"
						name="POWREMARK" value="${busRecord.POWREMARK}" style="width: 72%;" readonly='readonly' />
					</td>
				</tr>
				<tr>					
					<td class="tab_width">代理人姓名：</td>
					<td ><input type="text" class="tab_text_1 validate[]" 
						name="POWERSOURCE_DLRXM" id="POWERSOURCE_DLRXM" maxlength="62" onblur="checkLimitPerson();"/></td>
					<td class="tab_width">联系电话：</td>
					<td ><input type="text" class="tab_text_1 validate[]" 
						name="POWERSOURCE_DLRDH" id="POWERSOURCE_DLRDH" maxlength="62"/></td>
				</tr>
				<tr>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text_1 validate[]" dataParams="DocumentType" value="SF"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWERSOURCE_DLRZJLX" id="POWERSOURCE_DLRZJLX" >
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[]" maxlength="30" id="POWERSOURCE_DLRZJHM" style="float: left;"
						name="POWERSOURCE_DLRZJHM" onblur="checkLimitPerson();" />
						<a href="javascript:void(0);" onclick="PowDLRRead(this);" class="eflowbutton">读 卡</a>
					</td>
				</tr>
				<input type="hidden" name="DLR_FILE_URL" >
				<input type="hidden" name="DLR_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
	<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="DLR_FILE_DIV"></div><a id="DLR_FILE_BTN"></a>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:DLRchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=DLR_fileListDiv></div>
					</td>
				</tr>
				<tr>
					<td class="tab_width">代理机构名称：</td>
					<td colspan="3"><input type="text" class="tab_text_1" maxlength="60"
						name="POWERSOURCE_DLJG" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text_1" maxlength="60"
						name="POWERSOURCE_BZ" id="POWERSOURCE_BZ" style="width: 72%;"  />
					</td>
				</tr>
			</table>
			<input type="hidden" name="BDCDYH" />
			<input type="hidden" name="ZDDM" />
			<input type="hidden" name="FWBM" />
			<input type="hidden" name="YWH" />
			<input type="hidden" name="ZXYWH" />
			<input type="hidden" name="GLH" />
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerSourceInfo" value="删除" onclick="deletePowerSourceInfo('1');">
		</td>
	</tr>
</table>

<div id="powerDY_div" style="display:none;">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerDY">
	<tr>
		<th>抵押情况</th>
		<td> <input type="button" class="eflowbutton" value="查询" onclick="showSelectBdcdydacx();"> </td>
	</tr>
	<tr id="powerDYInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>抵押权人：</td>
					<td colspan='3'>
						<%-- <eve:eveselect clazz="tab_text_1 validate[required]" dataParams="CYYHHMC"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择抵押权人" name="POWERSOURCE_DYR" id="POWERSOURCE_DYR">
						</eve:eveselect> --%>
						<input class="easyui-combobox tab_text_1" name="POWERSOURCE_DYQR" id="POWERSOURCE_DYQR"
								data-options="
								    prompt : '请输入或者选择抵押权人',
									url: 'dictionaryController.do?load&typeCode=CYYHHMC',
									method: 'get',
									valueField : 'DIC_NAME',
									textField : 'DIC_NAME',
									filter : function(q, row) {
										var opts = $(this).combobox('options');
										return row[opts.textField].indexOf(q) > -1; 
									},	
									onSelect:function(){
										var value = $('#POWERSOURCE_DYR').combobox('getValue');
										setDYQLName(value);
									}								
								"/><span style="width:25px;display:inline-block;text-align:right;">
										<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','POWERSOURCE_DYR');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">
								  </span>
								  <span style="width:25px;display:inline-block;text-align:right;">
										<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('POWERSOURCE_DYR');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">
								  </span>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'POWERSOURCE_DYQRZJHM');"
						defaultEmptyText="选择证件类型" name="POWERSOURCE_DYQRZJLX" id="POWERSOURCE_DYQRZJLX">
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="POWERSOURCE_DYQRZJHM" style="float: left;"
						name="POWERSOURCE_DYQRZJHM" " />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>抵押人：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="POWERSOURCE_DYR" style="float: left;"
						name="POWERSOURCE_DYR"  />
					</td>
					<td></td>
					<td></td>	
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'POWERSOURCE_DYRZJHM');"
						defaultEmptyText="选择证件类型" name="POWERSOURCE_DYRZJLX" id="POWERSOURCE_DYRZJLX">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCE_DYRZJHM" style="float: left;"
						name="POWERSOURCE_DYRZJHM"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">债务起始时间：</td>
					<td>
						<input type="text" id="POWERSOURCE_QLQSSJ" name="POWERSOURCE_QLQSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'POWERSOURCE_QLJSSJ\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="32" style="width:160px;"/>
					</td>
					<td class="tab_width">债务结束时间：</td>
					<td>
						<input type="text" id="POWERSOURCE_QLJSSJ" name="POWERSOURCE_QLJSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'POWERSOURCE_QLQSSJ\')}'})" 
						    class="tab_text Wdate validate[]" maxlength="32" style="width:160px;"/>
					</td>	
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>被担保债权数额：</td>
					<td>
					  <input type="text" class="tab_text_1 validate[required]" maxlength="30" id="POWERSOURCEIDNUM" style="float: left;"
						name="POWERSOURCE_DBSE"  />
					</td>
					<td class="tab_width">在建建筑物抵押范围：</td>
					<td>
					  <input type="text" class="tab_text_1" maxlength="30" id="POWERSOURCEIDNUM" style="float: left;"
						name="POWERSOURCE_DYFW"  />
					</td>
				</tr>
			</table>
			<input type="hidden" name="DY_BDCDYH" />
			<input type="hidden" name="DY_YWH" />
			<input type="hidden" name="DY_GLH"/>
		</td>
		<td></td>
	</tr>
</table>
</div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="emmmmmmmm">
	<tr>
		<th > </th>
	</tr>
</table>

<script type="text/javascript">
	function newDicInfoWin(typeCode,combId){
		$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
			title : "新增",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"180px",
	        close: function(){
				$("#"+combId).combobox("reload");
			}
		}, false);
	}
	
	function removeDicInfo(combId){
		var datas = $("#"+combId).combobox("getData");
		var val = $("#"+combId).combobox("getValue");
		var id = "";
		for(var i=0;i<datas.length;i++){
			//var id = datas[i]
			if(datas[i].DIC_CODE==val){
				id = datas[i].DIC_ID;
				break;
			}
		}
		art.dialog.confirm("您确定要删除该记录吗?", function() {
			var layload = layer.load('正在提交请求中…');
			$.post("dictionaryController.do?multiDel",{
				   selectColNames:id
			    }, function(responseText, status, xhr) {
			    	layer.close(layload);
			    	var resultJson = $.parseJSON(responseText);
					if(resultJson.success == true){
						art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						$("#"+combId).combobox("reload");
						$("#"+combId).combobox("setValue","");
					}else{
						$("#"+combId).combobox("reload");
						art.dialog({
							content: resultJson.msg,
							icon:"error",
						    ok: true
						});
					}
			});
		});
	}
</script>

