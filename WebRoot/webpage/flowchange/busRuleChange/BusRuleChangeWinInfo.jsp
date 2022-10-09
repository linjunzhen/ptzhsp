<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusRuleChangeWinForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusRuleChangeWinForm").serialize();
				var url = $("#BusRuleChangeWinForm").attr("action");
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
							window.parent.$("#BusRuleChangeAddGrid").datagrid("reload");
							window.close();
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
	
	function getExp(){
		var $temp_1 = $("#Esuper_TempA").combobox('getValue');
		var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_5 = $("#Esuper_TempC").combobox('getValue');
		var $temp_1t = $("#Esuper_TempA").combobox('getText');
		var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		var $temp_5t = $("#Esuper_TempC").combobox('getText');
		// 拼凑([field_1]+[field_2])>[field_3]
 		var value = '';	var texts ='';
		if($temp_1==""||$temp_1.size==0){
		   alert("规则变量1不能为空！");
		   return;
		}
		if($temp_2==""||$temp_2.size==0){
		   alert("规则判断符1不能为空！");
		   return;
		}
		//当temp_2选择 >,<,<>时temp_3不能为空,temp_4，temp_5不做为判断条件，
		if($temp_2 == ">"||$temp_2=="<"||$temp_2=="<>"){
			if($temp_3 == ""){
		    	alert("规则变量2不能为空！");
		    	return;
		   	}else{
		    	value = '['+$temp_1+'] '+$temp_2+'['+$temp_3+']';
		    	texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+']';
				$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(value);
				$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(texts);
				return;
			}
		}
		//当temp_2选择"非空"时 且temp_3，temp_4，temp_5为空时，
		if($temp_3==""&&$temp_4==""&&$temp_5==""){
			if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
				value = "{["+$temp_1+"]} " +$temp_2;
				texts = "{["+$temp_1t+"]} " +$temp_2t;
				$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(value);
				$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(texts);
			}else{
				alert("请为规则变量2 ~ 规则变量3选择字段");
			}
		}else{
    		if($temp_3!=""&&$temp_4!=""&&$temp_5!=""){
    		//当temp_2选择"非空"时 且temp_3，temp_4，temp_5不为空时，
				if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
       		 		value = "{["+$temp_1+"]} " +$temp_2+' and ['+$temp_3+']'+$temp_4+'['+$temp_5+']';
       		 		texts = "{["+$temp_1t+"]} " +$temp_2t+' 与 ['+$temp_3t+']'+$temp_4t+'['+$temp_5t+']';
             		$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(value);
					$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(texts);
        		}else{
             		value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_5+']';
             		texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_5t+']';
	         		$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(value);
					$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(texts);
	        	}
			}else{//若temp_3 ~ temp5有一个为空时提示
				alert("请为规则变量2 ~ 规则变量3选择字段");
			}
		}	 
	}
	function toAndExp(){
		var $temp_1 = $("#Esuper_TempA").combobox('getValue');
		var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_5 = $("#Esuper_TempC").combobox('getValue');
		var $temp_1t = $("#Esuper_TempA").combobox('getText');
		var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		var $temp_5t = $("#Esuper_TempC").combobox('getText');
		var oldval = $("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val();
		var oldtext = $("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val();
		// 拼凑([field_1]+[field_2])>[field_3]
 		var value = '';	var texts ='';
		if($temp_1==""||$temp_1.size==0){
		   alert("规则变量1不能为空！");
		   return;
		}
		if($temp_2==""||$temp_2.size==0){
		   alert("规则判断符1不能为空！");
		   return;
		}
		//当temp_2选择 >,<,<>时temp_3不能为空,temp_4，temp_5不做为判断条件，
		if($temp_2 == ">"||$temp_2=="<"||$temp_2=="<>"){
			if($temp_3 == ""){
		    	alert("规则变量2不能为空！");
		    	return;
		   	}else{
		    	value = '['+$temp_1+'] '+$temp_2+'['+$temp_3+']';
		    	texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+']';
				$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
				$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
				return;
			}
		}
		//当temp_2选择"非空"时 且temp_3，temp_4，temp_5为空时，
		if($temp_3==""&&$temp_4==""&&$temp_5==""){
			if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
				value = "{["+$temp_1+"]} " +$temp_2;
				texts = "{["+$temp_1t+"]} " +$temp_2t;
				$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
				$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
			}else{
				alert("请为规则变量2 ~ 规则变量3选择字段");
			}
		}else{
    		if($temp_3!=""&&$temp_4!=""&&$temp_5!=""){
    		//当temp_2选择"非空"时 且temp_3，temp_4，temp_5不为空时，
				if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
       		 		value = "{["+$temp_1+"]} " +$temp_2+' and ['+$temp_3+']'+$temp_4+'['+$temp_5+']';
       		 		texts = "{["+$temp_1t+"]} " +$temp_2t+' 与 ['+$temp_3t+']'+$temp_4t+'['+$temp_5t+']';
             		$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
					$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
        		}else{
             		value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_5+']';
             		texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_5t+']';
	         		$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
					$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
	        	}
			}else{//若temp_3 ~ temp5有一个为空时提示
				alert("请为规则变量2 ~ 规则变量3选择字段");
			}
		}
	}
	function toOrExp(){
		var $temp_1 = $("#Esuper_TempA").combobox('getValue');
		var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_5 = $("#Esuper_TempC").combobox('getValue');
		var $temp_1t = $("#Esuper_TempA").combobox('getText');
		var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		var $temp_5t = $("#Esuper_TempC").combobox('getText');
		var oldval = $("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val();
		var oldtext = $("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val();
		// 拼凑([field_1]+[field_2])>[field_3]
 		var value = '';	var texts ='';
		if($temp_1==""||$temp_1.size==0){
		   alert("规则变量1不能为空！");
		   return;
		}
		if($temp_2==""||$temp_2.size==0){
		   alert("规则判断符1不能为空！");
		   return;
		}
		//当temp_2选择 >,<,<>时temp_3不能为空,temp_4，temp_5不做为判断条件，
		if($temp_2 == ">"||$temp_2=="<"||$temp_2=="<>"){
			if($temp_3 == ""){
		    	alert("规则变量2不能为空！");
		    	return;
		   	}else{
		    	value = '['+$temp_1+'] '+$temp_2+'['+$temp_3+']';
		    	texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+']';
				$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' or '+value);
				$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 或 '+texts);
				return;
			}
		}
		//当temp_2选择"非空"时 且temp_3，temp_4，temp_5为空时，
		if($temp_3==""&&$temp_4==""&&$temp_5==""){
			if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
				value = "{["+$temp_1+"]} " +$temp_2;
				texts = "{["+$temp_1t+"]} " +$temp_2t;
				$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' or '+value);
				$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 或 '+texts);
			}else{
				alert("请为规则变量2 ~ 规则变量3选择字段");
			}
		}else{
    		if($temp_3!=""&&$temp_4!=""&&$temp_5!=""){
    		//当temp_2选择"非空"时 且temp_3，temp_4，temp_5不为空时，
				if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
       		 		value = "{["+$temp_1+"]} " +$temp_2+' and ['+$temp_3+']'+$temp_4+'['+$temp_5+']';
       		 		texts = "{["+$temp_1t+"]} " +$temp_2t+' 与 ['+$temp_3t+']'+$temp_4t+'['+$temp_5t+']';
             		$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' or '+value);
					$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 或 '+texts);
        		}else{
             		value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_5+']';
             		texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_5t+']';
	         		$("#BusRuleChangeWinForm input[name='JUDGE_CONDITIONS']").val(oldval+' or '+value);
					$("#BusRuleChangeWinForm textarea[name='JUDGE_DESC']").val(oldtext+' 或 '+texts);
	        	}
			}else{//若temp_3 ~ temp5有一个为空时提示
				alert("请为规则变量2 ~ 规则变量3选择字段");
			}
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusRuleChangeWinForm" method="post"
		action="busRuleChangeController.do?saveEditRule">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="RULE_ID" value="${busRule.RULE_ID}">
		<input type="hidden" name="BUSSYS_UNITCODE" value="${busRule.BUSSYS_UNITCODE}">
		<input type="hidden" name="BUSSYS_PLATCODE" value="${busRule.BUSSYS_PLATCODE}">
		<input type="hidden" name="PROCESS_CODE" value="${busRule.PROCESS_CODE}">
		<input type="hidden" name="OBJ_TYPE" value="${busRule.OBJ_TYPE}">
		<input type="hidden" name="JUDGE_CONDITIONS" value="${busRule.JUDGE_CONDITIONS}">
		<input type="hidden" name="CREATE_TIME" value="${busRule.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busRule.CREATE_USER}">
		<input type="hidden" name="STATUS" value="0">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">所属单位：</span>
					${busRule.UNIT_NAME}
				</td>
				<td><span style="width: 100px;float:left;text-align:right;">业务专项：</span>
					${busRule.BUS_NAME}
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">业务环节：</span>
					${busRule.TACHE_NAME}
				</td>
				<td><span style="width: 100px;float:left;text-align:right;">监察节点：</span>
					${busRule.PROCESS_NAME}
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">监察要素：</span>
					<input type="text" style="width:150px;float:left;" maxlength="18"
						class="eve-input validate[required]"
						name="SUPER_ELEMENTS" value="${busRule.SUPER_ELEMENTS}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td><span style="width: 100px;float:left;text-align:right;">监察类型：</span>
					<input class="easyui-combobox" style="width:150px;" name="ANALYSIS_TYPE" value="${busRule.ANALYSIS_TYPE}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">监察方式：</span>
					<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="${busRule.IS_ARTIFICIAL}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td><span style="width: 100px;float:left;text-align:right;">预警类型：</span>
					<input class="easyui-combobox" style="width:150px;" name="WARN_STATUS" value="${busRule.WARN_STATUS}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=WarnType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">规则表达式</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">规则变量1：</span>
					<input class="easyui-combobox" style="width:150px;" name="Esuper_TempA" id="Esuper_TempA"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
				<td><span style="width: 100px;float:left;text-align:right;">规则判断符1：</span>
					<input class="easyui-combobox" style="width:150px;" name="Esuper_JudgeA" id="Esuper_JudgeA"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ArithmeticKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">规则变量2：</span>
					<input class="easyui-combobox" style="width:150px;" name="Esuper_TempB" id="Esuper_TempB"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
				<td><span style="width: 100px;float:left;text-align:right;">规则判断符2：</span>
					<input class="easyui-combobox" style="width:150px;" name="Esuper_JudgeB" id="Esuper_JudgeB"
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=ArithmeticKey',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">规则变量3：</span>
					<input class="easyui-combobox" style="width:150px;" name="Esuper_TempC" id="Esuper_TempC"
						data-options="url:'busColumnEsuperController.do?load&defaultEmpty=true&amp;processCode=${busRule.PROCESS_CODE }',editable:false,
						method: 'post',valueField:'COLUMN_CODE',textField:'COLUMN_NAME',panelWidth: 150,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
				<td><span style="width: 25px;float:left;text-align:right;">&nbsp;</span>
					<input type="button" name="btnA" value="生成表达式" onClick="getExp()">&nbsp;
					<input type="button" name="btnB" value="追加(与)" onClick="toAndExp()">&nbsp;
					<input type="button" name="btnC" value="追加(或)" onClick="toOrExp()">
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">规则表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width: 450px" name="JUDGE_DESC">${busRule.JUDGE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">描述信息</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">规则描述：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width: 450px" name="RULE_DESC">${busRule.RULE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">备注：</span>
					<textarea rows="1" cols="5" class="eve-textarea"
						style="width: 450px" name="REMARK">${busRule.REMARK}</textarea>
				</td>
			</tr>
		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="window.close();" />
		</div>
	</form>

</body>

