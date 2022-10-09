<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		$('#RuleTimeCh_TacheA').combobox('disable');
		$('#RuleTimeCh_ProcessA').combobox('disable');
	
		AppUtil.initWindowForm("BusRuleChangeTimeForm", function(form, valid) {
			if (valid) {
				var platCode = $("#ruleChTime_platCode").combobox('getValue');
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
				var formData = $("#BusRuleChangeTimeForm").serialize();
				var url = $("#BusRuleChangeTimeForm").attr("action");
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
	function getRuleTime(){
		var $temp_1 = $("#RuleTimeCh_ProcessA").combobox('getValue');
		var $temp_2 = $("#RuleTimeCh_ProcessB").combobox('getValue');
		var $temp_3 = $("#RuleTimeCh_JudgeA").combobox('getValue');
		var $temp_4 = $("#RuleTimeCh_JudgeB").combobox('getText');
		var $temp_5 = $("#RuleTimeCh_TimeValue").val();
		var $temp_1t = $("#RuleTimeCh_ProcessA").combobox('getText');
		var $temp_2t = $("#RuleTimeCh_ProcessB").combobox('getText');
		$temp_1t = $temp_1t.replace('\n','');
		$temp_2t = $temp_2t.replace('\n','');
		var $temp_3t = '大于';
		var value = '';	var texts ='';
		if($temp_1==""||$temp_1.length==0){alert("结束时间中的监察点不能为空！");return;}
		if($temp_2==""||$temp_2.length==0){alert("开始时间中的监察点不能为空！");return;}
		if($temp_3==""||$temp_3.length==0){alert("规则判断符不能为空！");return;}
		if($temp_4==""||$temp_4=="请选择"){alert("日期类型不能为空！");return;}
		if($temp_5==""||$temp_5.length==0){alert("时间数不能为空！");return;}
		if($temp_5.length>3){alert("时间数不能超过999！");return;}
		if($temp_3==">"){$temp_3t="大于"}
		else if ($temp_3=="<"){$temp_3t="小于"}
		else if ($temp_3==">="){$temp_3t="大于等于"}
		else if ($temp_3=="<="){$temp_3t="小于等于"}
		else if ($temp_3=="="){$temp_3t="等于"}
		else if ($temp_3=="<>"){$temp_3t="不等于"}
		else{$temp_3t="等于"}		
		value = '{['+$temp_1+']}-{['+$temp_2 +']}'+$temp_3+'{['+$temp_5+']}';
		texts = '流程【'+$temp_2t+'】到【'+$temp_1t+'】办理时间'+$temp_3t+$temp_5+'个'+$temp_4;
		$("#BusRuleChangeTimeForm input[name='JUDGE_CONDITIONS']").val(value);
		$("#BusRuleChangeTimeForm textarea[name='JUDGE_DESC']").val(texts);
	}
	function LimitTextArea(field){ 
    	var maxlimit=300; 
    	if (field.value.length > maxlimit){
    		field.value = field.value.substring(0, maxlimit); 
    	}       
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusRuleChangeTimeForm" method="post"
		action="busRuleChangeController.do?saveEditRule">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="CHANGE_ID" value="${busRule.CHANGE_ID}">
		<input type="hidden" name="RULE_ID" value="${busRule.RULE_ID}">
		<input type="hidden" name="BUSSYS_UNITCODE" value="${busRule.BUSSYS_UNITCODE}">
		<input type="hidden" name="PROCESS_CODE" value="${busRule.PROCESS_CODE}">
		<input type="hidden" name="SUPER_ELEMENTS" value="${busRule.SUPER_ELEMENTS}">
		<input type="hidden" name="JUDGE_CONDITIONS" value="${busRule.JUDGE_CONDITIONS}">
		<input type="hidden" name="IS_ARTIFICIAL" value="0">
		<input type="hidden" name="ANALYSIS_TYPE" value="1"> 
		<input type="hidden" name="OBJ_TYPE" value="2">
		<input type="hidden" name="IS_EXIST" value="1">
		<input type="hidden" name="VERSION" value="1">
		<input type="hidden" name="CREATE_TIME" value="${busRule.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busRule.CREATE_USER}">
		<input type="hidden" name="APPLY_ID" value="${busRule.APPLY_ID}">
		<input type="hidden" name="STATUS" value="1">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">所属单位：</span>
					${busRule.UNIT_NAME}
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">所属系统：</span>
					<input class="easyui-combobox" style="width:150px;" name="BUSSYS_PLATCODE" id="ruleChTime_platCode" value="${busRule.BUSSYS_PLATCODE}" 
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
				<td><span style="width: 90px;float:left;text-align:right;">监察要素：</span>
					${busRule.SUPER_ELEMENTS}
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">监察类型：</span>
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
			<!--<tr>
				<td><span style="width: 90px;float:left;text-align:right;">对象类型：</span>
					<c:if test="${empty busRule.OBJ_TYPE}">
						<input class="easyui-combobox" style="width:150px;" name="OBJ_TYPE" value="0" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypesKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</c:if>
					<c:if test="${!empty busRule.OBJ_TYPE}">
						<input class="easyui-combobox" style="width:150px;" name="OBJ_TYPE" value="${busRule.OBJ_TYPE}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypesKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					</c:if> 
				</td>
			</tr>-->
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">监察方式：</span>
					自动监察
					<!--<c:if test="${empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="0" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' "/> 
					</c:if>
					<c:if test="${!empty busRule.IS_ARTIFICIAL}">
						<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="${busRule.IS_ARTIFICIAL}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' "/> 
					</c:if>-->
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">预警类型：</span>
					<c:if test="${empty busRule.WARN_STATUS}">
						<input class="easyui-combobox" style="width:150px;" name="WARN_STATUS" value="红灯"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=WarnType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' "/>
					</c:if>
					<c:if test="${!empty busRule.WARN_STATUS}">
						<input class="easyui-combobox" style="width:150px;" name="WARN_STATUS" value="${busRule.WARN_STATUS}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=WarnType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' "/>
					</c:if>
				</td>
			</tr>
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">规则表达式</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">开始时间：</span>
					业务环节-
					<input class="easyui-combobox" style="width:150px;" name="RuleTime_TacheB"
						data-options="url:'busRuleChangeController.do?loadTache&defaultEmpty=true&amp;busCode=${busRule.BUS_CODE }&amp;applyId=${busRule.APPLY_ID }',editable:false,
						method: 'post',valueField:'TACHE_CODE',textField:'TACHE_NAME',panelWidth: 150,panelHeight: 'auto', 
						onSelect:function(rec){
							   if(rec.TACHE_CODE){
							   	  $('#RuleTimeCh_ProcessB').combobox('clear');
	        			          var url2 = 'busRuleChangeController.do?loadProcess&defaultEmpty=true&applyId=${busRule.APPLY_ID }&tacheCode='+rec.TACHE_CODE;
	    			    		  $('#RuleTimeCh_ProcessB').combobox('reload',url2); 						   	  
							   }						   
							}" />
					&nbsp;&nbsp;过程编码-
					<input class="easyui-combobox" style="width:230px;" name="RuleTime_ProcessB" id="RuleTimeCh_ProcessB"
						data-options="url:'busRuleChangeController.do?loadProcess&defaultEmpty=true',editable:false,
						method: 'post',valueField:'PROCESS_CODE',textField:'PROCESS_NAME',panelWidth: 230,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">结束时间：</span>
					业务环节-
					<input class="easyui-combobox" style="width:150px;" name="RuleTime_TacheA" id="RuleTimeCh_TacheA" value="${busRule.TACHE_CODE}"
						data-options="url:'busRuleChangeController.do?loadTache&defaultEmpty=true&amp;busCode=${busRule.BUS_CODE }&amp;applyId=${busRule.APPLY_ID }',editable:false,
						method: 'post',valueField:'TACHE_CODE',textField:'TACHE_NAME',panelWidth: 150,panelHeight: 'auto',
						onSelect:function(rec){
						   if(rec.TACHE_CODE){
						   	  $('#RuleTimeCh_ProcessA').combobox('clear');
        			          var url1 = 'busRuleChangeController.do?loadProcess&defaultEmpty=true&applyId=${busRule.APPLY_ID }&tacheCode='+rec.TACHE_CODE;
    			    		  $('#RuleTimeCh_ProcessA').combobox('reload',url1); 						   	  
						   }						   
						}" />
					&nbsp;&nbsp;过程编码-
					<input class="easyui-combobox validate[required]" style="width:230px;" name="RuleTime_ProcessA" id="RuleTimeCh_ProcessA"
						value="${busRule.PROCESS_CODE}" data-options="url:'busRuleChangeController.do?loadProcess&defaultEmpty=true&tacheCode=${busRule.TACHE_CODE}&applyId=${busRule.APPLY_ID }',editable:false,
						method: 'post',valueField:'PROCESS_CODE',textField:'PROCESS_NAME',panelWidth: 230,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">规则判断符：</span>
					<input class="easyui-combobox" style="width:70px;" name="RuleTime_JudgeA" id="RuleTimeCh_JudgeA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=JudgeKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 70,panelHeight: 'auto' " />
					固定值：<input class="easyui-numberbox" style="width:50px;" id="RuleTimeCh_TimeValue" maxlength="3"/>
					单位：<input class="easyui-combobox" style="width:70px;" name="RuleTime_JudgeB" id="RuleTimeCh_JudgeB" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ObjTypeKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 70,panelHeight: 'auto' " />
					<input type="button" name="btnA" value="生成表达式" onClick="getRuleTime()">
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">规则表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width:505px;height:40px;resize: none;" name="JUDGE_DESC" readonly="readonly">${busRule.JUDGE_DESC}</textarea>
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
						style="width:505px;height:40px;resize: none;" name="RULE_DESC">${busRule.RULE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">备注：</span>
					<textarea title="备注最多300字!" rows="1" cols="5" class="eve-textarea" 
						onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
						style="width:505px;height:40px;resize: none;" name="REMARK">${busRule.REMARK}</textarea>
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

