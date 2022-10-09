<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/busRuleChange/busRuleChange.js"></script>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	var isAuto = "${busRule.IS_ARTIFICIAL}";
	if(isAuto=='1'){
		$('#superTempCh_conditionS').css('display','none');
		$('#tempCh_trA').css('display','none');
		$('#tempCh_trB').css('display','none');
		$('#tempCh_trC').css('display','none');
		$('#superTempCh_conditionE').css('display','none');
		$('#superTempCh_conditionF').css('display','none');
		$('#superTempCh_judge').removeAttr('readonly');
	}else if (isAuto=='0'){
		$('#superTempCh_conditionS').css('display','');
		$('#superTempCh_conditionE').css('display','');
		$('#superTempCh_conditionF').css('display','');
		$('#superTempCh_judge').attr('readonly','readonly');
	}else {
		$('#superTempCh_conditionS').css('display','');
		$('#superTempCh_conditionE').css('display','');
		$('#superTempCh_conditionF').css('display','');
		$('#superTempCh_judge').attr('readonly','readonly');
	}
	$(function() {
		AppUtil.initWindowForm("BusRuleChangeEditForm", function(form, valid) {
			if (valid) {
				var platCode = $("#ruleChEdit_platCode").combobox('getValue');
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
				var formData = $("#BusRuleChangeEditForm").serialize();
				var url = $("#BusRuleChangeEditForm").attr("action");
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
							parent.$("#BusRuleChangeGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_RULECONFIG_CHANGE");
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
	<form id="BusRuleChangeEditForm" method="post"
		action="busRuleChangeController.do?saveEditRule">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="CHANGE_ID" value="${busRule.CHANGE_ID}">
		<input type="hidden" name="PROCESS_CODE" value="${busRule.PROCESS_CODE}">
		<input type="hidden" name="JUDGE_CONDITIONS" value="${busRule.JUDGE_CONDITIONS}">
		<input type="hidden" name="IS_EXIST" value="1">
		<input type="hidden" name="OBJ_TYPE" value="0">
		<input type="hidden" name="VERSION" value="1">
		<input type="hidden" name="STATUS" value="1">
		<input type="hidden" name="JUDGE_CONDITIONS_TEMP">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="6" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td colspan="3" width="50%"><span style="width: 100px;float:left;text-align:right;">所属单位：</span>
					${busRule.UNIT_NAME}
				</td>
				<td colspan="3" width="*"><span style="width: 100px;float:left;text-align:right;">所属系统：</span>
					<input class="easyui-combobox" style="width:150px;" name="BUSSYS_PLATCODE" id="ruleChEdit_platCode" value="${busRule.BUSSYS_PLATCODE}" 
						data-options="url:'busSysController.do?load&defaultEmpty=true&unitCode=${busRule.BUSSYS_UNITCODE}',editable:false,
						method: 'post',valueField:'SYSTEM_CODE',textField:'SYSTEM_NAME',panelWidth: 250,panelHeight: 'auto' " /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="3"><span style="width: 100px;float:left;text-align:right;">业务专项：</span>
					${busRule.BUS_NAME}
				</td>
				<td colspan="3"><span style="width: 100px;float:left;text-align:right;">业务环节：</span>
					${busRule.TACHE_NAME}
				</td>
			</tr>
			<tr>
				<td colspan="6"><span style="width: 100px;float:left;text-align:right;">监察节点：</span>
					${busRule.PROCESS_NAME}
				</td>
			</tr>			
			<tr>
				<td colspan="3"><span style="width: 100px;float:left;text-align:right;">监察要素：</span>
					${busRule.SUPER_ELEMENTS}
				</td>
				<td colspan="3"><span style="width: 100px;float:left;text-align:right;">监察类型：</span>
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
			<!--
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">对象类型：</span>
					<c:if test="${empty busRule.OBJ_TYPE}">
						<input class="easyui-combobox" style="width:150px;" name="OBJ_TYPE" id="Esuper_ObjType" value="0" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypesKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</c:if>
					<c:if test="${!empty busRule.OBJ_TYPE}">
						<input class="easyui-combobox" style="width:150px;" name="OBJ_TYPE" id="Esuper_ObjType" value="${busRule.OBJ_TYPE}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypesKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</c:if>
				</td>
			</tr>
			-->
			<tr>
				<td colspan="3"><span style="width: 100px;float:left;text-align:right;">监察方式：</span>
					<c:if test="${empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="0" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto', 
						onSelect:function(rec){
							var dicValue = rec.DIC_CODE;
							if(dicValue=='1'){
								$('#superTempCh_conditionS').css('display','none');
								$('#tempCh_trA').css('display','none');
								$('#tempCh_trB').css('display','none');
								$('#tempCh_trC').css('display','none');
								$('#superTempCh_conditionE').css('display','none');
								$('#superTempCh_conditionF').css('display','none');
								$('#superTempCh_judge').removeAttr('readonly');
								$('#EsuperCh_TempA').combobox('setValue','');
								$('#EsuperCh_JudgeA').combobox('setValue','');
								$('#EsuperCh_changeTsA').combobox('setValue','');
								$('#EsuperCh_FixedA').val('');
								$('#EsuperCh_VariableA').combobox('setValue','');
								$('#EsuperCh_TempB').combobox('setValue','');
								$('#EsuperCh_JudgeB').combobox('setValue','');
								$('#EsuperCh_changeTsB').combobox('setValue','');
								$('#EsuperCh_FixedB').numberbox('setValue','');
								$('#EsuperCh_FixedC').combobox('setValue','');
								$('#EsuperCh_VariableB').combobox('setValue','');
							}else if (dicValue=='0'){
								$('#superTempCh_conditionS').css('display','');
								$('#superTempCh_conditionE').css('display','');
								$('#superTempCh_conditionF').css('display','');
								$('#superTempCh_judge').attr('readonly','readonly');
							}else {
								$('#superTempCh_conditionS').css('display','');
								$('#superTempCh_conditionE').css('display','');
								$('#superTempCh_conditionF').css('display','');
								$('#superTempCh_judge').attr('readonly','readonly');
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
								$('#superTempCh_conditionS').css('display','none');
								$('#tempCh_trA').css('display','none');
								$('#tempCh_trB').css('display','none');
								$('#tempCh_trC').css('display','none');
								$('#superTempCh_conditionE').css('display','none');
								$('#superTempCh_conditionF').css('display','none');
								$('#superTempCh_judge').removeAttr('readonly');
								$('#EsuperCh_TempA').combobox('setValue','');
								$('#EsuperCh_JudgeA').combobox('setValue','');
								$('#EsuperCh_changeTsA').combobox('setValue','');
								$('#EsuperCh_FixedA').val('');
								$('#EsuperCh_VariableA').combobox('setValue','');
								$('#EsuperCh_TempB').combobox('setValue','');
								$('#EsuperCh_JudgeB').combobox('setValue','');
								$('#EsuperCh_changeTsB').combobox('setValue','');
								$('#EsuperCh_FixedB').numberbox('setValue','');
								$('#EsuperCh_FixedC').combobox('setValue','');
								$('#EsuperCh_VariableB').combobox('setValue','');
							}else if (dicValue=='0'){
								$('#superTempCh_conditionS').css('display','');
								$('#superTempCh_conditionE').css('display','');
								$('#superTempCh_conditionF').css('display','');
								$('#superTempCh_judge').attr('readonly','readonly');
							}else {
								$('#superTempCh_conditionS').css('display','');
								$('#superTempCh_conditionE').css('display','');
								$('#superTempCh_conditionF').css('display','');
								$('#superTempCh_judge').attr('readonly','readonly');
							}
						}		
						" /> 
					</c:if>
				</td>
				<td colspan="3"><span style="width: 100px;float:left;text-align:right;">预警类型：</span>
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
			<tr id="superTempCh_conditionS">
				<td colspan="2"><span style="width: 80px;float:left;text-align:right;">监察字段：</span>
					<input class="easyui-combobox" style="width:140px;" name="Esuper_TempA" id="EsuperCh_TempA"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 140,panelHeight: 'auto' " />
				</td>
				<td colspan="1"><span style="width: 55px;float:left;text-align:right;">判断符：</span>
					<input class="easyui-combobox" style="width:65px;" name="Esuper_JudgeA" id="EsuperCh_JudgeA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ArithmeticKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 65,panelHeight: 180 ,
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue==''){
									$('#tempCh_trA').css('display','none');
									$('#tempCh_trB').css('display','none');
									$('#tempCh_trC').css('display','none');
									$('#EsuperCh_changeTsA').combobox('setValue','');
									$('#EsuperCh_FixedA').val('');
									$('#EsuperCh_VariableA').combobox('setValue','');
									$('#EsuperCh_TempB').combobox('setValue','');
									$('#EsuperCh_JudgeB').combobox('setValue','');
									$('#EsuperCh_changeTsB').combobox('setValue','');
									$('#EsuperCh_FixedB').numberbox('setValue','');
									$('#EsuperCh_FixedC').combobox('setValue','');
									$('#EsuperCh_VariableB').combobox('setValue','');
								}else if(dicValue=='is NULL'||dicValue=='is Not NULL'){
									$('#tempCh_trA').css('display','none');
									$('#tempCh_trB').css('display','none');
									$('#tempCh_trC').css('display','none');
									$('#EsuperCh_changeTsA').combobox('setValue','');
									$('#EsuperCh_FixedA').val('');
									$('#EsuperCh_VariableA').combobox('setValue','');
									$('#EsuperCh_TempB').combobox('setValue','');
									$('#EsuperCh_JudgeB').combobox('setValue','');
									$('#EsuperCh_changeTsB').combobox('setValue','');
									$('#EsuperCh_FixedB').numberbox('setValue','');
									$('#EsuperCh_FixedC').combobox('setValue','');
									$('#EsuperCh_VariableB').combobox('setValue','');
								}else if(dicValue=='>'||dicValue=='<'||dicValue=='='||dicValue=='<>'){
									$('#tempCh_trA').css('display','');
									$('#tempCh_trB').css('display','none');
									$('#tempCh_trC').css('display','none');
									$('#tempCh_trA1').css('display','none');
									$('#tempCh_trA2').css('display','none');
									$('#EsuperCh_TempB').combobox('setValue','');
									$('#EsuperCh_JudgeB').combobox('setValue','');
									$('#EsuperCh_changeTsB').combobox('setValue','');
									$('#EsuperCh_FixedB').numberbox('setValue','');
									$('#EsuperCh_FixedC').combobox('setValue','');
									$('#EsuperCh_VariableB').combobox('setValue','');
								}else {
									$('#tempCh_trA').css('display','none');
									$('#tempCh_trB').css('display','');
									$('#tempCh_trC').css('display','');
									$('#tempCh_trC1').css('display','none');
									$('#tempCh_trC2').css('display','none');
									$('#EsuperCh_changeTsA').combobox('setValue','');
									$('#EsuperCh_FixedA').val('');
									$('#EsuperCh_VariableA').combobox('setValue','');
								}
							}							
						"/>
				</td>
				<td colspan="3" id="tempCh_trA" style="display:none;"><span style="width: 100px;float:left;text-align:right;">
					<input class="easyui-combobox" style="width:80px;" name="Esuper_changeTsA" id="EsuperCh_changeTsA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=changeRuleKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 80,panelHeight:'auto',
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue=='1'){
									$('#tempCh_trA1').css('display','inline-block');
									$('#tempCh_trA2').css('display','none');		
									$('#EsuperCh_VariableA').combobox('setValue','');							
								}else if(dicValue=='2'){
									$('#tempCh_trA1').css('display','none');
									$('#tempCh_trA2').css('display','inline-block');
									$('#EsuperCh_FixedA').val('');
								}else{
									$('#tempCh_trA1').css('display','none');
									$('#tempCh_trA2').css('display','none');
									$('#EsuperCh_FixedA').val('');
									$('#EsuperCh_VariableA').combobox('setValue','');
								}
							}						
						"/>：
					</span>
					<div id="tempCh_trA1" style="display:none;">
						<input type="text" style="width:145px;" name="Esuper_FixedA" id="EsuperCh_FixedA" maxlength="16" class="eve-input" />
					</div>
					<div id="tempCh_trA2" style="display:none;">
					<input class="easyui-combobox" style="width:150px;" name="Esuper_VariableA" id="EsuperCh_VariableA"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</div>
				</td>
			</tr>
			<tr id="tempCh_trB" style="display:none;">
				<td colspan="2"><span style="width: 80px;float:left;text-align:right;">监察字段：</span>
					<input class="easyui-combobox" style="width:140px;" name="Esuper_TempB" id="EsuperCh_TempB"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 140,panelHeight: 'auto' " />
				</td>
				<td colspan="1"><span style="width: 55px;float:left;text-align:right;">判断符：</span>
					<input class="easyui-combobox" style="width:65px;" name="Esuper_JudgeB" id="EsuperCh_JudgeB"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=JudgeKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 65,panelHeight: 180" />
				</td>
				<td colspan="3" id="tempCh_trC" style="display:none;"><span style="width: 100px;float:left;text-align:right;">
					<input class="easyui-combobox" style="width:80px;" name="Esuper_changeTsB" id="EsuperCh_changeTsB"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=changeRuleKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 80,panelHeight:'auto',
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue=='1'){
									$('#tempCh_trC1').css('display','inline-block');
									$('#tempCh_trC2').css('display','none');		
									$('#EsuperCh_VariableB').combobox('setValue','');
								}else if(dicValue=='2'){
									$('#tempCh_trC1').css('display','none');
									$('#tempCh_trC2').css('display','inline-block');
									$('#EsuperCh_FixedB').numberbox('setValue','');
									$('#EsuperCh_FixedC').combobox('setValue','');
								}else{
									$('#tempCh_trC1').css('display','none');
									$('#tempCh_trC2').css('display','none');
									$('#EsuperCh_FixedB').numberbox('setValue','');
									$('#EsuperCh_FixedC').combobox('setValue','');
									$('#EsuperCh_VariableB').combobox('setValue','');
								}
							}						
						"/>	：
						</span>
						<div id="tempCh_trC1" style="display:none;">
							<input type="text" style="width:80px;" name="Esuper_FixedB" id="EsuperCh_FixedB" maxlength="10" class="easyui-numberbox" />
							单位：
							<input class="easyui-combobox" style="width:65px;" name="Esuper_FixedC" id="EsuperCh_FixedC"  
								data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypeKey',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 65,panelHeight: 'auto' " />
						</div>
						<div id="tempCh_trC2" style="display:none;">	
							<input class="easyui-combobox" style="width:150px;" name="Esuper_VariableB" id="EsuperCh_VariableB"
								data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
								method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
						</div>
				</td>
			</tr>
			<tr id="superTempCh_conditionE" style="height:29px;">
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">暂存表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea"
						style="width:445px;height:40px;resize: none;overflow-y:hidden;" name="JUDGE_DESC_TEMP" id="superTempCh_judgeTemp" readonly="readonly"></textarea>
				</td>
				<td colspan="2">
					<input type="button" name="btnTempA" value="生成暂存" onClick="getTempJudge()">&nbsp;
					<input type="button" name="btnTempB" value="追加(与)" onClick="toAndTempJudge()"><br/>
					<input type="button" name="btnTempD" value="清空暂存" onClick="clearTempJudge()">&nbsp;
					<input type="button" name="btnTempC" value="追加(或)" onClick="toOrTempJudge()">
				</td>
			</tr>
			
			<tr style="height:29px;">
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">最终表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width:445px;height:40px;resize: none;overflow-y:hidden;" name="JUDGE_DESC" id="superTempCh_judge" readonly="readonly">${busRule.JUDGE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td id="superTempCh_conditionF" colspan="2">
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
						style="width:505px;height:35px;resize: none;overflow-y:hidden;" name="RULE_DESC">${busRule.RULE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="6"><span style="width: 100px;float:left;text-align:right;">备注：</span>
					<textarea title="备注最多300字!" rows="1" cols="5" class="eve-textarea"
						onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
						style="width:505px;height:35px;resize: none;overflow-y:hidden;" name="REMARK">${busRule.REMARK}</textarea>
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

