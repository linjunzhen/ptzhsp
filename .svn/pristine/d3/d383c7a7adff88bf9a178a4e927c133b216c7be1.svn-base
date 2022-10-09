<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/busRule/busRule.js"></script>
<script type="text/javascript">
$(function () {
	var isAuto = "${busRule.IS_ARTIFICIAL}";
	if(isAuto=='1'){
		$('#superTemp_conditionS').css('display','none');
		$('#temp_trA').css('display','none');
		$('#temp_trB').css('display','none');
		$('#temp_trC').css('display','none');
		$('#superTemp_conditionE').css('display','none');
		$('#superTemp_conditionF').css('display','none');
		$('#superTemp_judge').removeAttr('readonly');
	}else if (isAuto=='0'){
		$('#superTemp_conditionS').css('display','');
		$('#superTemp_conditionE').css('display','');
		$('#superTemp_conditionF').css('display','');
		$('#superTemp_judge').attr('readonly','readonly');
	}else {
		$('#superTemp_conditionS').css('display','');
		$('#superTemp_conditionE').css('display','');
		$('#superTemp_conditionF').css('display','');
		$('#superTemp_judge').attr('readonly','readonly');
	}		
});
</script>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusRuleEditForm", function(form, valid) {
			if (valid) {
				var platCode = $("#ruleEdit_platCode").combobox('getValue');
				if(platCode=='' || platCode=='undefined'){
					parent.art.dialog({
						content: '请选择所属系统',
						icon:"error",
					    ok: true
					});
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusRuleEditForm").serialize();
				var url = $("#BusRuleEditForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.$("#BusRuleGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_LCJC_BUS_RULECONFIG");
	});
	function LimitTextArea(field){ 
    	var maxlimit=300; 
    	if (field.value.length > maxlimit){
    		field.value = field.value.substring(0, maxlimit); 
    	}       
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusRuleEditForm" method="post"
		action="busRuleController.do?saveEditRule">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="RULE_ID" value="${busRule.RULE_ID}">
		<input type="hidden" name="PROCESS_CODE" value="${busRule.PROCESS_CODE}">
		<input type="hidden" name="JUDGE_CONDITIONS" value="${busRule.JUDGE_CONDITIONS}">
		<input type="hidden" name="IS_EXIST" value="1">
		<input type="hidden" name="OBJ_TYPE" value="0">
		<input type="hidden" name="VERSION" value="1">
		<input type="hidden" name="STATUS" value="1">
		<input type="hidden" name="JUDGE_CONDITIONS_TEMP">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table" >
			<tr style="height:29px;">
				<td colspan="6" class="dddl-legend" style="font-weight:bold;" align="left">基本信息</td>
			</tr>
			<tr>
				<td colspan="3" width="50%" ><span style="width: 100px;float:left;text-align:right;">所属单位：</span>
					${busRule.UNIT_NAME}
				</td>
				<td colspan="3" width="50%"><span style="width: 100px;float:left;text-align:right;">所属系统：</span>
					<input class="easyui-combobox" style="width:150px;" name="BUSSYS_PLATCODE" id="ruleEdit_platCode" value="${busRule.BUSSYS_PLATCODE}" 
						data-options="url:'busSysController.do?load&defaultEmpty=true&unitCode=${busRule.BUSSYS_UNITCODE}',editable:false,
						method: 'post',valueField:'SYSTEM_CODE',textField:'SYSTEM_NAME',panelWidth: 250,panelHeight: 'auto' " /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="3"  width="50%"><span style="width: 100px;float:left;text-align:right;">业务专项：</span>
					${busRule.BUS_NAME}
				</td>
				<td colspan="3"  width="50%"><span style="width: 100px;float:left;text-align:right;">业务环节：</span>
					${busRule.TACHE_NAME}
				</td>
			</tr>
			<tr>
				<td colspan="6"><span style="width: 100px;float:left;text-align:right;">监察节点：</span>
					${busRule.PROCESS_NAME}
				</td>
			</tr>			
			<tr>
				<td colspan="3"  width="50%"><span style="width: 100px;float:left;text-align:right;">监察要素：</span>
					${busRule.SUPER_ELEMENTS}
				</td>
				<td colspan="3"  width="50%"><span style="width: 100px;float:left;text-align:right;">监察类型：</span>
					<c:if test="${busRule.ANALYSIS_TYPE==1}">时限监察</c:if>
					<c:if test="${busRule.ANALYSIS_TYPE==2}">内容监察</c:if>
					<c:if test="${busRule.ANALYSIS_TYPE==3}">流程监察</c:if>
					<c:if test="${busRule.ANALYSIS_TYPE==4}">收费监察</c:if>
					<!-- <input class="easyui-combobox" style="width:150px;" name="ANALYSIS_TYPE" value="${busRule.ANALYSIS_TYPE}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " readonly="readonly"/>
					 -->
				</td>
			</tr>
			
			<tr>
				<td colspan="3" width="50%"><span style="width: 100px;float:left;text-align:right;">监察方式：</span>
					<c:if test="${empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="0" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto', 
						onSelect:function(rec){
							var dicValue = rec.DIC_CODE;
							if(dicValue=='1'){
								$('#superTemp_conditionS').css('display','none');
								$('#temp_trA').css('display','none');
								$('#temp_trB').css('display','none');
								$('#temp_trC').css('display','none');
								$('#superTemp_conditionE').css('display','none');
								$('#superTemp_conditionF').css('display','none');
								$('#superTemp_judge').removeAttr('readonly');
								$('#Esuper_TempA').combobox('setValue','');
								$('#Esuper_JudgeA').combobox('setValue','');
								$('#Esuper_changeTsA').combobox('setValue','');
								$('#Esuper_FixedA').val('');
								$('#Esuper_VariableA').combobox('setValue','');
								$('#Esuper_TempB').combobox('setValue','');
								$('#Esuper_JudgeB').combobox('setValue','');
								$('#Esuper_changeTsB').combobox('setValue','');
								$('#Esuper_FixedB').numberbox('setValue','');
								$('#Esuper_FixedC').combobox('setValue','');
								$('#Esuper_VariableB').combobox('setValue','');
							}else if (dicValue=='0'){
								$('#superTemp_conditionS').css('display','');
								$('#superTemp_conditionE').css('display','');
								$('#superTemp_conditionF').css('display','');
								$('#superTemp_judge').attr('readonly','readonly');
							}else {
								$('#superTemp_conditionS').css('display','');
								$('#superTemp_conditionE').css('display','');
								$('#superTemp_conditionF').css('display','');
								$('#superTemp_judge').attr('readonly','readonly');
							}
						}						
						" /> 
					</c:if>
					<c:if test="${!empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="${busRule.IS_ARTIFICIAL}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto',
						onSelect:function(rec){
							var dicValue = rec.DIC_CODE;
							if(dicValue=='1'){
								$('#superTemp_conditionS').css('display','none');
								$('#temp_trA').css('display','none');
								$('#temp_trB').css('display','none');
								$('#temp_trC').css('display','none');
								$('#superTemp_conditionE').css('display','none');
								$('#superTemp_conditionF').css('display','none');
								$('#superTemp_judge').removeAttr('readonly');
								$('#Esuper_TempA').combobox('setValue','');
								$('#Esuper_JudgeA').combobox('setValue','');
								$('#Esuper_changeTsA').combobox('setValue','');
								$('#Esuper_FixedA').val('');
								$('#Esuper_VariableA').combobox('setValue','');
								$('#Esuper_TempB').combobox('setValue','');
								$('#Esuper_JudgeB').combobox('setValue','');
								$('#Esuper_changeTsB').combobox('setValue','');
								$('#Esuper_FixedB').numberbox('setValue','');
								$('#Esuper_FixedC').combobox('setValue','');
								$('#Esuper_VariableB').combobox('setValue','');
							}else if (dicValue=='0'){
								$('#superTemp_conditionS').css('display','');
								$('#superTemp_conditionE').css('display','');
								$('#superTemp_conditionF').css('display','');
								$('#superTemp_judge').attr('readonly','readonly');
							}else {
								$('#superTemp_conditionS').css('display','');
								$('#superTemp_conditionE').css('display','');
								$('#superTemp_conditionF').css('display','');
								$('#superTemp_judge').attr('readonly','readonly');
							}
						}		
						" /> 
					</c:if>
				</td>
				<td colspan="3" width="50%"><span style="width: 100px;float:left;text-align:right;">预警类型：</span>
					<c:if test="${empty busRule.WARN_STATUS}">
						<input class="easyui-combobox" style="width:150px;" name="WARN_STATUS" value="红灯" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=WarnType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</c:if>
					<c:if test="${!empty busRule.WARN_STATUS}">
						<input class="easyui-combobox" style="width:150px;" name="WARN_STATUS" value="${busRule.WARN_STATUS}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=WarnType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</c:if>
				</td>
			</tr>
			<tr style="height:29px;">
				<td colspan="6" class="dddl-legend" style="font-weight:bold;">规则表达式</td>
			</tr>
			<tr id="superTemp_conditionS">
				<td colspan="2" width="33.3333%"><span style="width: 80px;float:left;text-align:right;">监察字段：</span>
					<input class="easyui-combobox" style="width:140px;" name="Esuper_TempA" id="Esuper_TempA"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 140,panelHeight: 'auto' " />
				</td>
				<td colspan="1" width="17.66666%"><span style="width: 55px;float:left;text-align:right;">判断符：</span>
					<input class="easyui-combobox" style="width:65px;" name="Esuper_JudgeA" id="Esuper_JudgeA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ArithmeticKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 65,panelHeight: 180 ,
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue==''){
									$('#temp_trA').css('display','none');
									$('#temp_trB').css('display','none');
									$('#temp_trC').css('display','none');
									$('#Esuper_changeTsA').combobox('setValue','');
									$('#Esuper_FixedA').val('');
									$('#Esuper_VariableA').combobox('setValue','');
									$('#Esuper_TempB').combobox('setValue','');
									$('#Esuper_JudgeB').combobox('setValue','');
									$('#Esuper_changeTsB').combobox('setValue','');
									$('#Esuper_FixedB').numberbox('setValue','');
									$('#Esuper_FixedC').combobox('setValue','');
									$('#Esuper_VariableB').combobox('setValue','');
								}else if(dicValue=='is NULL'||dicValue=='is Not NULL'){
									$('#temp_trA').css('display','none');
									$('#temp_trB').css('display','none');
									$('#temp_trC').css('display','none');
									$('#Esuper_changeTsA').combobox('setValue','');
									$('#Esuper_FixedA').val('');
									$('#Esuper_VariableA').combobox('setValue','');
									$('#Esuper_TempB').combobox('setValue','');
									$('#Esuper_JudgeB').combobox('setValue','');
									$('#Esuper_changeTsB').combobox('setValue','');
									$('#Esuper_FixedB').numberbox('setValue','');
									$('#Esuper_FixedC').combobox('setValue','');
									$('#Esuper_VariableB').combobox('setValue','');
								}else if(dicValue=='>'||dicValue=='<'||dicValue=='='||dicValue=='<>'){
									$('#temp_trA').css('display','');
									$('#temp_trB').css('display','none');
									$('#temp_trC').css('display','none');
									$('#temp_trA1').css('display','none');
									$('#temp_trA2').css('display','none');
									$('#Esuper_TempB').combobox('setValue','');
									$('#Esuper_JudgeB').combobox('setValue','');
									$('#Esuper_changeTsB').combobox('setValue','');
									$('#Esuper_FixedB').numberbox('setValue','');
									$('#Esuper_FixedC').combobox('setValue','');
									$('#Esuper_VariableB').combobox('setValue','');
								}else {
									$('#temp_trA').css('display','none');
									$('#temp_trB').css('display','');
									$('#temp_trC').css('display','');
									$('#temp_trC1').css('display','none');
									$('#temp_trC2').css('display','none');
									$('#Esuper_changeTsA').combobox('setValue','');
									$('#Esuper_FixedA').val('');
									$('#Esuper_VariableA').combobox('setValue','');
								}
							}							
						"/>
				</td>
				<td colspan="3" width="50%" id="temp_trA" style="display:none;"><span style="width: 100px;float:left;text-align:right;">
					<input class="easyui-combobox" style="width:80px;" name="Esuper_changeTsA" id="Esuper_changeTsA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=changeRuleKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 80,panelHeight:'auto',
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue=='1'){
									$('#temp_trA1').css('display','inline-block');
									$('#temp_trA2').css('display','none');		
									$('#Esuper_VariableA').combobox('setValue','');							
								}else if(dicValue=='2'){
									$('#temp_trA1').css('display','none');
									$('#temp_trA2').css('display','inline-block');
									$('#Esuper_FixedA').val('');
								}else{
									$('#temp_trA1').css('display','none');
									$('#temp_trA2').css('display','none');
									$('#Esuper_FixedA').val('');
									$('#Esuper_VariableA').combobox('setValue','');
								}
							}						
						"/>：
					</span>
					<div id="temp_trA1" style="display:none;">
						<input type="text" style="width:145px;" name="Esuper_FixedA" id="Esuper_FixedA" maxlength="16" class="eve-input" />
					</div>
					<div id="temp_trA2" style="display:none;">
					<input class="easyui-combobox" style="width:150px;" name="Esuper_VariableA" id="Esuper_VariableA"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</div>
				</td>
			</tr>
			<tr id="temp_trB" style="display:none;">
				<td colspan="2" width="32.33333%"><span style="width: 80px;float:left;text-align:right;">监察字段：</span>
					<input class="easyui-combobox" style="width:140px;" name="Esuper_TempB" id="Esuper_TempB"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 140,panelHeight: 'auto' " />
				</td>
				<td colspan="1" width="17.66666%"><span style="width: 55px;float:left;text-align:right;">判断符：</span>
					<input class="easyui-combobox" style="width:65px;" name="Esuper_JudgeB" id="Esuper_JudgeB"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=JudgeKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 65,panelHeight: 180" />
				</td>
				<td colspan="3" width="50%" id="temp_trC" style="display:none;"><span style="width: 100px;float:left;text-align:right;">
					<input class="easyui-combobox" style="width:80px;" name="Esuper_changeTsB" id="Esuper_changeTsB"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=changeRuleKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 80,panelHeight:'auto',
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue=='1'){
									$('#temp_trC1').css('display','inline-block');
									$('#temp_trC2').css('display','none');		
									$('#Esuper_VariableB').combobox('setValue','');
								}else if(dicValue=='2'){
									$('#temp_trC1').css('display','none');
									$('#temp_trC2').css('display','inline-block');
									$('#Esuper_FixedB').numberbox('setValue','');
									$('#Esuper_FixedC').combobox('setValue','');
								}else{
									$('#temp_trC1').css('display','none');
									$('#temp_trC2').css('display','none');
									$('#Esuper_FixedB').numberbox('setValue','');
									$('#Esuper_FixedC').combobox('setValue','');
									$('#Esuper_VariableB').combobox('setValue','');
								}
							}						
						"/>	：
						</span>
						<div id="temp_trC1" style="display:none;">
							<input type="text" style="width:80px;" name="Esuper_FixedB" id="Esuper_FixedB" maxlength="10" class="easyui-numberbox" />
							单位：
							<input class="easyui-combobox" style="width:65px;" name="Esuper_FixedC" id="Esuper_FixedC"  
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypeKey',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 65,panelHeight: 'auto' " />
						</div>
						<div id="temp_trC2" style="display:none;">	
							<input class="easyui-combobox" style="width:150px;" name="Esuper_VariableB" id="Esuper_VariableB"
								data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
								method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
						</div>
				</td>
			</tr>
			<tr id="superTemp_conditionE" style="height:29px;">
				<td colspan="4" width="68.16666%"><span style="width: 100px;float:left;text-align:right;">暂存表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea"
						style="width:380px;height:40px;resize: none;overflow-y:hidden;" name="JUDGE_DESC_TEMP" id="superTemp_judgeTemp" readonly="readonly"></textarea>
				</td>
				<td colspan="2" width="31.8333%">
					<input type="button" name="btnTempA" value="生成暂存" onClick="getTempJudge()">&nbsp;
					<input type="button" name="btnTempB" value="追加(与)" onClick="toAndTempJudge()"><br/>
					<input type="button" name="btnTempD" value="清空暂存" onClick="clearTempJudge()">&nbsp;
					<input type="button" name="btnTempC" value="追加(或)" onClick="toOrTempJudge()">
				</td>
			</tr>
			<tr style="height:29px;">
				<td colspan="4" width="68.16666%"><span style="width: 100px;float:left;text-align:right;">最终表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width:380px;height:40px;resize: none;overflow-y:hidden;" name="JUDGE_DESC" id="superTemp_judge" readonly="readonly">${busRule.JUDGE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td colspan="2" width="31.8333%" id="superTemp_conditionF" >
					<input type="button" name="btnA" value="生成最终" onClick="getExp()">&nbsp;
					<input type="button" name="btnB" value="追加(与)" onClick="toAndExp()"><br/>
					<input type="button" name="btnD" value="清空最终" onClick="clearExp()">&nbsp;
					<input type="button" name="btnC" value="追加(或)" onClick="toOrExp()">
				</td>
			</tr>
			<tr style="height:29px;">
				<td colspan="6" class="dddl-legend" style="font-weight:bold;">描述信息</td>
			</tr>
			<tr>
				<td colspan="6"><span style="width: 100px;float:left;text-align:right;">规则描述：</span>
					<textarea title="规则描述最多300字!" rows="2" cols="5" class="eve-textarea validate[required]"
						onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
						style="width:510px;height:35px;resize: none;overflow-y:hidden;" name="RULE_DESC">${busRule.RULE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="6"><span style="width: 100px;float:left;text-align:right;">备注：</span>
					<textarea title="备注最多300字!" rows="1" cols="5" class="eve-textarea"
						onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
						style="width:510px;height:35px;resize: none;overflow-y:hidden;" name="REMARK">${busRule.REMARK}</textarea>
				</td>
			</tr>
			
		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

