<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<script type="text/javascript" src="<%=path%>/webpage/flow/busRule/busRule.js"></script>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
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
		<input type="hidden" name="IS_EXIST" value="0">
		<input type="hidden" name="STATUS" value="1">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td width="50%"><span style="width: 90px;float:left;text-align:right;">所属单位：</span>
					${busRule.UNIT_NAME}
				</td>
				<td width="*"><span style="width: 90px;float:left;text-align:right;">所属系统：</span>
					<input class="easyui-combobox" style="width:150px;" name="BUSSYS_PLATCODE" id="ruleEdit_platCode" value="${busRule.BUSSYS_PLATCODE}" 
						data-options="url:'busSysController.do?load&defaultEmpty=true&unitCode=${busRule.BUSSYS_UNITCODE}',editable:false,
						method: 'post',valueField:'SYSTEM_CODE',textField:'SYSTEM_NAME',panelWidth: 250,panelHeight: 'auto' " /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">业务专项：</span>
					${busRule.BUS_NAME}
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">业务环节：</span>
					${busRule.TACHE_NAME}
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 90px;float:left;text-align:right;">监察节点：</span>
					${busRule.PROCESS_NAME}
				</td>
			</tr>			
			<tr>
				<td colspan="2"><span style="width: 90px;float:left;text-align:right;">监察要素：</span>
					${busRule.SUPER_ELEMENTS}
				</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">监察类型：</span>
					<input class="easyui-combobox" style="width:150px;" name="ANALYSIS_TYPE" value="${busRule.ANALYSIS_TYPE}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " readonly="readonly"/>
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">对象类型：</span>
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
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">监察方式：</span>
					<c:if test="${empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="0" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /> 
					</c:if>
					<c:if test="${!empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="${busRule.IS_ARTIFICIAL}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /> 
					</c:if>
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">预警类型：</span>
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
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">规则表达式</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 90px;float:left;text-align:right;">监察字段：</span>
					<input class="easyui-combobox" style="width:150px;" name="Esuper_TempA" id="Esuper_TempA"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<div id="div_temps" style="display:inline-block;"></div>
					<div id="div_tempA" style="display:none;">	<!-- 固定值 -->	
						<input type="text" style="width:100px;" name="Esuper_FixedA" id="Esuper_FixedA" maxlength="16" class="eve-input validate[required]" />
					</div>
					<div id="div_tempB" style="display:none;">	<!-- 监察字段 -->
						<input class="easyui-combobox" style="width:150px;" name="Esuper_TempB" id="Esuper_TempB"
							data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
							method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</div>
					<div id="div_tempC" style="display:none;">	<!-- 判断符 -->
						<input class="easyui-combobox" style="width:90px;" name="Esuper_JudgeA" id="Esuper_JudgeA"
							data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ArithmeticKey',editable:false,
							method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 90,panelHeight: 180"/>	
					</div>
				</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">下一步：</span>
					<input class="easyui-combobox" style="width:90px;" name="Esuper_changeTsA" id="Esuper_changeTsA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=changeRuleKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 90,panelHeight:'auto',
						onSelect:function(rec){
								var dicValue = rec.DIC_CODE;
								if(dicValue=='1'){
									$('#div_tempA').css('display','inline-block');
									$('#div_tempB').hide();
									$('#div_tempC').hide();
								}else if(dicValue=='2'){
									$('#div_tempA').hide();
									$('#div_tempB').css('display','inline-block');
									$('#div_tempC').hide();
								}else{
									$('#div_tempA').hide();
									$('#div_tempB').hide();
									$('#div_tempC').css('display','inline-block');
								}
							}						
						"/>	
				</td>
				<td><span style="width: 25px;float:left;text-align:right;">&nbsp;</span>
					<input type="button" name="btnA" value="生成表达式" onClick="getExp()">&nbsp;
					<input type="button" name="btnB" value="追加(与)" onClick="toAndExp()">&nbsp;
					<input type="button" name="btnC" value="追加(或)" onClick="toOrExp()">
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">规则表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width:505px;height:35px;resize: none;" name="JUDGE_DESC" readonly="readonly">${busRule.JUDGE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">描述信息</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">规则描述：</span>
					<textarea title="规则描述最多300字!" rows="2" cols="5" class="eve-textarea validate[required]"
						onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
						style="width:505px;height:35px;resize: none;" name="RULE_DESC">${busRule.RULE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">备注：</span>
					<textarea title="备注最多300字!" rows="1" cols="5" class="eve-textarea"
						onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
						style="width:505px;height:35px;resize: none;" name="REMARK">${busRule.REMARK}</textarea>
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

