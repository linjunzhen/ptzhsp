<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
	a{color:bule;}
</style>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerPeopleInfo">
	<tr>
		<th >权利人信息
				<input type="button"  style="float:right" class="eflowbutton"  name="addOnePowerPeopleInfo" value="新增">
		</th>
		<!-- <td> 
			<input type="button" class="eflowbutton"  name="addOnePowerPeopleInfo" value="新增" onclick="addPowerPeopleInfo();"> 
		</td> -->
	</tr>
	<tr id="powerPeopleInfo_1">
		<td >
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权利人姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="POWERPEOPLENAME" maxlength="62"/></td>
					<td class="tab_width"><font class="tab_color">*</font>权利比例：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="POWERPEOPLEPRO" maxlength="62"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权利人性质：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[]" dataParams="QLRXZ"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE" >
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType" value="SF"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWERPEOPLEIDTYPE" id="POWERPEOPLEIDTYPE" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="POWERPEOPLEIDNUM" style="float: left;"
						name="POWERPEOPLEIDNUM"  />
					</td>
					<td><a href="javascript:void(0);" onclick="PowerPeopleRead(this);" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width">性别</td>
					<td>
					<eve:eveselect clazz="tab_text"
										dataParams="sex"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="选择性别" name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
					</td>
					<td class="tab_width">移动电话：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="POWERPEOPLEMOBILE" maxlength="11"/></td>
				</tr>
				<tr>					
					<td class="tab_width">地址：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="POWERPEOPLEADDR" maxlength="62"/></td>
					<td class="tab_width">邮政编码：</td>
					<td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
						name="POWERPEOPLEPOSTCODE" maxlength="6"/></td>
				</tr>
				<tr>					
					<td class="tab_width">法定代表人姓名：</td>
					<td ><input type="text" class="tab_text validate[]" 
						name="POWLEGALNAME" maxlength="62"/></td>
					<td class="tab_width">证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType" value="SF"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM" style="float: left;"
						name="POWLEGALIDNUM"  />
					</td>
					<td><a href="javascript:void(0);" onclick="PowLegalRead(this);" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width">代理人姓名：</td>
					<td ><input type="text" class="tab_text validate[]" 
						name="POWAGENTNAME" maxlength="62"/></td>
					<td class="tab_width">证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType" value="SF"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="POWAGENTIDTYPE" id="POWAGENTIDTYPE" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="POWAGENTIDNUM" style="float: left;"
						name="POWAGENTIDNUM"  />
					</td>
					<td><a href="javascript:void(0);" onclick="PowAgentRead(this);" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="60"
						name="POWREMARK" value="${busRecord.POWREMARK}" style="width: 72%;"  />
					</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<!-- <td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerPeopleInfo" value="删除" onclick="deletePowerPeopleInfo('1');">
		</td> -->
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr align="center">
		<td width="10%">序号</td>
		<td width="35%">权利人名称</td>
		<td width="35%">身份证号码</td>
		<td width="10%">性别</td>
		<td width="10%">操作</td>
	</tr>
	<tr align="center">
		<td>1</td>
		<td></td>
		<td></td>
		<td></td>
		<td><a href="javascript:void(0)">[删除]</a></td>
	</tr>
	<tr align="center">
		<td>2</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerSourceInfo">
	<tr>
		<th >权属来源
			<input type="button" style="float:right" class="eflowbutton"  name="addOnePowerSourceInfo" value="新增" >
		</th>
		<!-- <td> 
			<input type="button" class="eflowbutton"  name="addOnePowerSourceInfo" value="新增" onclick="addPowerSourceInfo();">
		</td> -->
	</tr>
	<tr id="powerSourceInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权属来源类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="QSLYLX"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE" >
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>权属文号：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCEIDNUM" style="float: left;"
						name="POWERSOURCEIDNUM"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">权属状态：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text " dataParams="QSZT"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权属状态" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE" >
						</eve:eveselect>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<!-- <td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerSourceInfo" value="删除" onclick="deletePowerSourceInfo('1');">
		</td> -->
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr align="center">
		<td width="10%">序号</td>
		<td width="35%">权属来源类型</td>
		<td width="35%">权属文号</td>
		<td width="10%">权属状态</td>
		<td width="10%">操作</td>
	</tr>
	<tr align="center">
		<td>1</td>
		<td></td>
		<td></td>
		<td></td>
		<td><a href="javascript:void(0)">[删除]</a></td>
	</tr>
	<tr align="center">
		<td>2</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
