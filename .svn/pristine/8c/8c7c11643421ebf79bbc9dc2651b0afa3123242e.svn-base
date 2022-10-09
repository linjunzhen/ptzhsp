function clearTempJudge(){
	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val('');
	$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val('');
	return;
}

function getTempJudge(){
	var $temp_1 = $("#Esuper_TempA").combobox('getValue');
	var $temp_1t = $("#Esuper_TempA").combobox('getText');
	var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
	var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
	// 拼凑([field_1]+[field_2])>[field_3]
		var value = '';	var texts ='';
	if($temp_1==""||$temp_1.size==0){
	   alert("请选择监察字段!");
	   return;
	}
	if($temp_2==""||$temp_2.size==0){
	   alert("请选择判断符!");
	   return;
	}
	//规则判断
	if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
		value = "{["+$temp_1+"]} " +$temp_2;
		texts = "{["+$temp_1t+"]} " +$temp_2t;
		$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val(value);
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val(texts);
		return;
	}
	if($temp_2==">"||$temp_2=="<"||$temp_2=="<>"||$temp_2=="="){
		var $temp_TsA = $("#Esuper_changeTsA").combobox('getValue');	
		var $temp_FixedA = $("#Esuper_FixedA").val();			
		var $temp_VarA = $("#Esuper_VariableA").combobox('getValue');
		var $temp_VarAt = $("#Esuper_VariableA").combobox('getText');			
		if($temp_TsA==""||$temp_TsA.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsA=='1'){
			if($temp_FixedA==""||$temp_FixedA.length==0){
				alert("请输入固定值!");
				return;
			}
			value = '{['+$temp_1+']} '+$temp_2+'{'+$temp_FixedA+'}';
    		texts = '{['+$temp_1t+']} '+$temp_2t+'{'+$temp_FixedA+'}';
		}
		if($temp_TsA=='2'){
			if($temp_VarA==""||$temp_VarA.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '['+$temp_1+'] '+$temp_2+'['+$temp_VarA+']';
    		texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_VarAt+']';
		}
		$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val(value);
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val(texts);
		return;
	}
	if($temp_2=="+"||$temp_2=="-"||$temp_2=="*"||$temp_2=="÷"){
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		if($temp_3==""||$temp_3.size==0){
		   alert("请选择监察字段!");
		   return;
		}
		if($temp_4==""||$temp_4.size==0){
		   alert("请选择判断符!");
		   return;
		}
		var $temp_TsB = $("#Esuper_changeTsB").combobox('getValue');
		if($temp_TsB==""||$temp_TsB.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsB=='1'){
			var $temp_FixedB = $("#Esuper_FixedB").val();
			var $temp_FixedC = $("#Esuper_FixedC").combobox('getValue');
			var $temp_FixedCt = $("#Esuper_FixedC").combobox('getText');
			if($temp_FixedB==""||$temp_FixedB.length==0){
				alert("请输入固定值!");
				return;
			}
			if($temp_FixedC==""||$temp_FixedC.size==0){
			   alert("请选择单位!");
			   return;
			}
			if($temp_FixedC=='1'){
				value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']天';
			}
			if($temp_FixedC=='2'){
				value = '@(<['+$temp_1+']> '+$temp_2+'<['+$temp_3+']>)@'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']工作日';
			}
		}
		if($temp_TsB=='2'){
			var $temp_VarB = $("#Esuper_VariableB").combobox('getValue');
			var $temp_VarBt = $("#Esuper_VariableB").combobox('getText');
			if($temp_VarB==""||$temp_VarB.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_VarB+']';
			texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_VarBt+']';
		}
		$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val(value);
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val(texts);
		return;
	}
}

function toAndTempJudge(){
	var $temp_1 = $("#Esuper_TempA").combobox('getValue');
	var $temp_1t = $("#Esuper_TempA").combobox('getText');
	var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
	var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
	var oldval = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val();
	var oldtext = $("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val();
	// 拼凑([field_1]+[field_2])>[field_3]
		var value = '';	var texts ='';
	if($temp_1==""||$temp_1.size==0){
	   alert("请选择监察字段!");
	   return;
	}
	if($temp_2==""||$temp_2.size==0){
	   alert("请选择判断符!");
	   return;
	}
	//规则判断
	if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
		value = "{["+$temp_1+"]} " +$temp_2;
		texts = "{["+$temp_1t+"]} " +$temp_2t;
		if(oldval.indexOf(value)>=0){
    		alert("暂存规则中已包含"+texts+",无须再添加！");
    		return;
    	}
		$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val(oldval+' and '+value);
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val(oldtext+' 与 '+texts);
		return;
	}
	if($temp_2==">"||$temp_2=="<"||$temp_2=="<>"||$temp_2=="="){
		var $temp_TsA = $("#Esuper_changeTsA").combobox('getValue');	
		var $temp_FixedA = $("#Esuper_FixedA").val();			
		var $temp_VarA = $("#Esuper_VariableA").combobox('getValue');
		var $temp_VarAt = $("#Esuper_VariableA").combobox('getText');			
		if($temp_TsA==""||$temp_TsA.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsA=='1'){
			if($temp_FixedA==""||$temp_FixedA.length==0){
				alert("请输入固定值!");
				return;
			}
			value = '{['+$temp_1+']} '+$temp_2+'{'+$temp_FixedA+'}';
    		texts = '{['+$temp_1t+']} '+$temp_2t+'{'+$temp_FixedA+'}';
		}
		if($temp_TsA=='2'){
			if($temp_VarA==""||$temp_VarA.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '['+$temp_1+'] '+$temp_2+'['+$temp_VarA+']';
    		texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_VarAt+']';
		}
		if(oldval.indexOf(value)>=0){
    		alert("暂存规则中已包含"+texts+",无须再添加！");
    		return;
    	}
		$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val(oldval+' and '+value);
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val(oldtext+' 与 '+texts);
		return;
	}
	if($temp_2=="+"||$temp_2=="-"||$temp_2=="*"||$temp_2=="÷"){
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		if($temp_3==""||$temp_3.size==0){
		   alert("请选择监察字段!");
		   return;
		}
		if($temp_4==""||$temp_4.size==0){
		   alert("请选择判断符!");
		   return;
		}
		var $temp_TsB = $("#Esuper_changeTsB").combobox('getValue');
		if($temp_TsB==""||$temp_TsB.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsB=='1'){
			var $temp_FixedB = $("#Esuper_FixedB").val();
			var $temp_FixedC = $("#Esuper_FixedC").combobox('getValue');
			var $temp_FixedCt = $("#Esuper_FixedC").combobox('getText');
			if($temp_FixedB==""||$temp_FixedB.length==0){
				alert("请输入固定值!");
				return;
			}
			if($temp_FixedC==""||$temp_FixedC.size==0){
			   alert("请选择单位!");
			   return;
			}
			if($temp_FixedC=='1'){
				value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']天';
			}
			if($temp_FixedC=='2'){
				value = '@(<['+$temp_1+']> '+$temp_2+'<['+$temp_3+']>)@'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']工作日';
			}
		}
		if($temp_TsB=='2'){
			var $temp_VarB = $("#Esuper_VariableB").combobox('getValue');
			var $temp_VarBt = $("#Esuper_VariableB").combobox('getText');
			if($temp_VarB==""||$temp_VarB.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_VarB+']';
			texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_VarBt+']';
		}
		if(oldval.indexOf(value)>=0){
    		alert("暂存规则中已包含"+texts+",无须再添加！");
    		return;
    	}
		$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val(oldval+' and '+value);
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val(oldtext+' 与 '+texts);
		return;
	}
}

function toOrTempJudge(){
	var $temp_1 = $("#Esuper_TempA").combobox('getValue');
	var $temp_1t = $("#Esuper_TempA").combobox('getText');
	var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
	var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
	var oldval = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val();
	var oldtext = $("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val();
	// 拼凑([field_1]+[field_2])>[field_3]
		var value = '';	var texts ='';
	if($temp_1==""||$temp_1.size==0){
	   alert("请选择监察字段!");
	   return;
	}
	if($temp_2==""||$temp_2.size==0){
	   alert("请选择判断符!");
	   return;
	}
	//规则判断
	if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
		value = "{["+$temp_1+"]} " +$temp_2;
		texts = "{["+$temp_1t+"]} " +$temp_2t;
		if(oldval.indexOf(value)>=0){
    		alert("暂存规则中已包含"+texts+",无须再添加！");
    		return;
    	}
    	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val('('+oldval+') or ('+value+')');
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val('('+oldtext+') 或 ('+texts+')');
		return;
	}
	if($temp_2==">"||$temp_2=="<"||$temp_2=="<>"||$temp_2=="="){
		var $temp_TsA = $("#Esuper_changeTsA").combobox('getValue');	
		var $temp_FixedA = $("#Esuper_FixedA").val();			
		var $temp_VarA = $("#Esuper_VariableA").combobox('getValue');
		var $temp_VarAt = $("#Esuper_VariableA").combobox('getText');			
		if($temp_TsA==""||$temp_TsA.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsA=='1'){
			if($temp_FixedA==""||$temp_FixedA.length==0){
				alert("请输入固定值!");
				return;
			}
			value = '{['+$temp_1+']} '+$temp_2+'{'+$temp_FixedA+'}';
    		texts = '{['+$temp_1t+']} '+$temp_2t+'{'+$temp_FixedA+'}';
		}
		if($temp_TsA=='2'){
			if($temp_VarA==""||$temp_VarA.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '['+$temp_1+'] '+$temp_2+'['+$temp_VarA+']';
    		texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_VarAt+']';
		}
		if(oldval.indexOf(value)>=0){
    		alert("暂存规则中已包含"+texts+",无须再添加！");
    		return;
    	}
    	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val('('+oldval+') or ('+value+')');
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val('('+oldtext+') 或 ('+texts+')');
		return;
	}
	if($temp_2=="+"||$temp_2=="-"||$temp_2=="*"||$temp_2=="÷"){
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		if($temp_3==""||$temp_3.size==0){
		   alert("请选择监察字段!");
		   return;
		}
		if($temp_4==""||$temp_4.size==0){
		   alert("请选择判断符!");
		   return;
		}
		var $temp_TsB = $("#Esuper_changeTsB").combobox('getValue');
		if($temp_TsB==""||$temp_TsB.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsB=='1'){
			var $temp_FixedB = $("#Esuper_FixedB").val();
			var $temp_FixedC = $("#Esuper_FixedC").combobox('getValue');
			var $temp_FixedCt = $("#Esuper_FixedC").combobox('getText');
			if($temp_FixedB==""||$temp_FixedB.length==0){
				alert("请输入固定值!");
				return;
			}
			if($temp_FixedC==""||$temp_FixedC.size==0){
			   alert("请选择单位!");
			   return;
			}
			if($temp_FixedC=='1'){
				value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']天';
			}
			if($temp_FixedC=='2'){
				value = '@(<['+$temp_1+']> '+$temp_2+'<['+$temp_3+']>)@'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']工作日';
			}
		}
		if($temp_TsB=='2'){
			var $temp_VarB = $("#Esuper_VariableB").combobox('getValue');
			var $temp_VarBt = $("#Esuper_VariableB").combobox('getText');
			if($temp_VarB==""||$temp_VarB.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_VarB+']';
			texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_VarBt+']';
		}
		if(oldval.indexOf(value)>=0){
    		alert("暂存规则中已包含"+texts+",无须再添加！");
    		return;
    	}
    	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val('('+oldval+') or ('+value+')');
		$("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val('('+oldtext+') 或 ('+texts+')');
		return;
	}
}

function clearExp(){
	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('');
	$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('');
	return;
}

function getExp(){
	var $temp_1 = $("#Esuper_TempA").combobox('getValue');
	var $temp_1t = $("#Esuper_TempA").combobox('getText');
	var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
	var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
	var temp_val = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val();
	var temp_text = $("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val();
	// 拼凑([field_1]+[field_2])>[field_3]
		var value = '';	var texts ='';
	if($temp_1==""||$temp_1.size==0){
	   alert("请选择监察字段!");
	   return;
	}
	if($temp_2==""||$temp_2.size==0){
	   alert("请选择判断符!");
	   return;
	}
	//规则判断
	if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
		value = "{["+$temp_1+"]} " +$temp_2;
		texts = "{["+$temp_1t+"]} " +$temp_2t;
		if(temp_val==''&&temp_text==''){
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(value);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(texts);
			return;
		}else{
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(temp_val);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(temp_text);
			return;
		}
	}
	if($temp_2==">"||$temp_2=="<"||$temp_2=="<>"||$temp_2=="="){
		var $temp_TsA = $("#Esuper_changeTsA").combobox('getValue');	
		var $temp_FixedA = $("#Esuper_FixedA").val();			
		var $temp_VarA = $("#Esuper_VariableA").combobox('getValue');
		var $temp_VarAt = $("#Esuper_VariableA").combobox('getText');			
		if($temp_TsA==""||$temp_TsA.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsA=='1'){
			if($temp_FixedA==""||$temp_FixedA.length==0){
				alert("请输入固定值!");
				return;
			}
			value = '{['+$temp_1+']} '+$temp_2+'{'+$temp_FixedA+'}';
    		texts = '{['+$temp_1t+']} '+$temp_2t+'{'+$temp_FixedA+'}';
		}
		if($temp_TsA=='2'){
			if($temp_VarA==""||$temp_VarA.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '['+$temp_1+'] '+$temp_2+'['+$temp_VarA+']';
    		texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_VarAt+']';
		}
		if(temp_val==''&&temp_text==''){
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(value);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(texts);
			return;
		}else{
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(temp_val);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(temp_text);
			return;
		}
	}
	if($temp_2=="+"||$temp_2=="-"||$temp_2=="*"||$temp_2=="÷"){
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		if($temp_3==""||$temp_3.size==0){
		   alert("请选择监察字段!");
		   return;
		}
		if($temp_4==""||$temp_4.size==0){
		   alert("请选择判断符!");
		   return;
		}
		var $temp_TsB = $("#Esuper_changeTsB").combobox('getValue');
		if($temp_TsB==""||$temp_TsB.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsB=='1'){
			var $temp_FixedB = $("#Esuper_FixedB").val();
			var $temp_FixedC = $("#Esuper_FixedC").combobox('getValue');
			var $temp_FixedCt = $("#Esuper_FixedC").combobox('getText');
			if($temp_FixedB==""||$temp_FixedB.length==0){
				alert("请输入固定值!");
				return;
			}
			if($temp_FixedC==""||$temp_FixedC.size==0){
			   alert("请选择单位!");
			   return;
			}
			if($temp_FixedC=='1'){
				value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']天';
			}
			if($temp_FixedC=='2'){
				value = '@(<['+$temp_1+']> '+$temp_2+'<['+$temp_3+']>)@'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']工作日';
			}
		}
		if($temp_TsB=='2'){
			var $temp_VarB = $("#Esuper_VariableB").combobox('getValue');
			var $temp_VarBt = $("#Esuper_VariableB").combobox('getText');
			if($temp_VarB==""||$temp_VarB.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_VarB+']';
			texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_VarBt+']';
		}
		if(temp_val==''&&temp_text==''){
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(value);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(texts);
			return;
		}else{
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(temp_val);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(temp_text);
			return;
		}
	}
}

function toAndExp(){
	var $temp_1 = $("#Esuper_TempA").combobox('getValue');
	var $temp_1t = $("#Esuper_TempA").combobox('getText');
	var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
	var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
	var oldval = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val();
	var oldtext = $("#BusRuleEditForm textarea[name='JUDGE_DESC']").val();
	var temp_val = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val();
	var temp_text = $("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val();
	// 拼凑([field_1]+[field_2])>[field_3]
		var value = '';	var texts ='';
	if($temp_1==""||$temp_1.size==0){
	   alert("请选择监察字段!");
	   return;
	}
	if($temp_2==""||$temp_2.size==0){
	   alert("请选择判断符!");
	   return;
	}
	//规则判断
	if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
		value = "{["+$temp_1+"]} " +$temp_2;
		texts = "{["+$temp_1t+"]} " +$temp_2t;
		if(temp_val==''&&temp_text==''){
			if(oldval.indexOf(value)>=0){
	    		alert("现有规则中已包含"+texts+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
			return;
		}else{
			if(oldval.indexOf(temp_val)>=0){
	    		alert("现有规则中已包含"+temp_text+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+temp_val);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+temp_text);
			return;
		}
	}
	if($temp_2==">"||$temp_2=="<"||$temp_2=="<>"||$temp_2=="="){
		var $temp_TsA = $("#Esuper_changeTsA").combobox('getValue');	
		var $temp_FixedA = $("#Esuper_FixedA").val();			
		var $temp_VarA = $("#Esuper_VariableA").combobox('getValue');
		var $temp_VarAt = $("#Esuper_VariableA").combobox('getText');			
		if($temp_TsA==""||$temp_TsA.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsA=='1'){
			if($temp_FixedA==""||$temp_FixedA.length==0){
				alert("请输入固定值!");
				return;
			}
			value = '{['+$temp_1+']} '+$temp_2+'{'+$temp_FixedA+'}';
    		texts = '{['+$temp_1t+']} '+$temp_2t+'{'+$temp_FixedA+'}';
		}
		if($temp_TsA=='2'){
			if($temp_VarA==""||$temp_VarA.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '['+$temp_1+'] '+$temp_2+'['+$temp_VarA+']';
    		texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_VarAt+']';
		}
		if(temp_val==''&&temp_text==''){
			if(oldval.indexOf(value)>=0){
	    		alert("现有规则中已包含"+texts+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
			return;
		}else{
			if(oldval.indexOf(temp_val)>=0){
	    		alert("现有规则中已包含"+temp_text+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+temp_val);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+temp_text);
			return;
		}
	}
	if($temp_2=="+"||$temp_2=="-"||$temp_2=="*"||$temp_2=="÷"){
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		if($temp_3==""||$temp_3.size==0){
		   alert("请选择监察字段!");
		   return;
		}
		if($temp_4==""||$temp_4.size==0){
		   alert("请选择判断符!");
		   return;
		}
		var $temp_TsB = $("#Esuper_changeTsB").combobox('getValue');
		if($temp_TsB==""||$temp_TsB.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsB=='1'){
			var $temp_FixedB = $("#Esuper_FixedB").val();
			var $temp_FixedC = $("#Esuper_FixedC").combobox('getValue');
			var $temp_FixedCt = $("#Esuper_FixedC").combobox('getText');
			if($temp_FixedB==""||$temp_FixedB.length==0){
				alert("请输入固定值!");
				return;
			}
			if($temp_FixedC==""||$temp_FixedC.size==0){
			   alert("请选择单位!");
			   return;
			}
			if($temp_FixedC=='1'){
				value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']天';
			}
			if($temp_FixedC=='2'){
				value = '@(<['+$temp_1+']> '+$temp_2+'<['+$temp_3+']>)@'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']工作日';
			}
		}
		if($temp_TsB=='2'){
			var $temp_VarB = $("#Esuper_VariableB").combobox('getValue');
			var $temp_VarBt = $("#Esuper_VariableB").combobox('getText');
			if($temp_VarB==""||$temp_VarB.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_VarB+']';
			texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_VarBt+']';
		}
		if(temp_val==''&&temp_text==''){
			if(oldval.indexOf(value)>=0){
	    		alert("现有规则中已包含"+texts+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+value);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+texts);
			return;
		}else{
			if(oldval.indexOf(temp_val)>=0){
	    		alert("现有规则中已包含"+temp_text+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val(oldval+' and '+temp_val);
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val(oldtext+' 与 '+temp_text);
			return;
		}
	}
}

function toOrExp(){
	var $temp_1 = $("#Esuper_TempA").combobox('getValue');
	var $temp_1t = $("#Esuper_TempA").combobox('getText');
	var $temp_2 = $("#Esuper_JudgeA").combobox('getValue');
	var $temp_2t = $("#Esuper_JudgeA").combobox('getText');
	var oldval = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val();
	var oldtext = $("#BusRuleEditForm textarea[name='JUDGE_DESC']").val();
	var temp_val = $("#BusRuleEditForm input[name='JUDGE_CONDITIONS_TEMP']").val();
	var temp_text = $("#BusRuleEditForm textarea[name='JUDGE_DESC_TEMP']").val();
	// 拼凑([field_1]+[field_2])>[field_3]
		var value = '';	var texts ='';
	if($temp_1==""||$temp_1.size==0){
	   alert("请选择监察字段!");
	   return;
	}
	if($temp_2==""||$temp_2.size==0){
	   alert("请选择判断符!");
	   return;
	}
	//规则判断
	if($temp_2 == "is Not NULL" || $temp_2 == "is NULL"){
		value = "{["+$temp_1+"]} " +$temp_2;
		texts = "{["+$temp_1t+"]} " +$temp_2t;
		if(temp_val==''&&temp_text==''){
			if(oldval.indexOf(value)>=0){
	    		alert("现有规则中已包含"+texts+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('('+oldval+') or ('+value+')');
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('('+oldtext+') 或 ('+texts+')');
			return;
		}else{
			if(oldval.indexOf(temp_val)>=0){
	    		alert("现有规则中已包含"+temp_text+",无须再添加！");
	    		return;
	    	}
	    	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('('+oldval+') or ('+temp_val+')');
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('('+oldtext+') 或 ('+temp_text+')');
			return;
		}
	}
	if($temp_2==">"||$temp_2=="<"||$temp_2=="<>"||$temp_2=="="){
		var $temp_TsA = $("#Esuper_changeTsA").combobox('getValue');	
		var $temp_FixedA = $("#Esuper_FixedA").val();			
		var $temp_VarA = $("#Esuper_VariableA").combobox('getValue');
		var $temp_VarAt = $("#Esuper_VariableA").combobox('getText');			
		if($temp_TsA==""||$temp_TsA.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsA=='1'){
			if($temp_FixedA==""||$temp_FixedA.length==0){
				alert("请输入固定值!");
				return;
			}
			value = '{['+$temp_1+']} '+$temp_2+'{'+$temp_FixedA+'}';
    		texts = '{['+$temp_1t+']} '+$temp_2t+'{'+$temp_FixedA+'}';
		}
		if($temp_TsA=='2'){
			if($temp_VarA==""||$temp_VarA.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '['+$temp_1+'] '+$temp_2+'['+$temp_VarA+']';
    		texts = '['+$temp_1t+'] '+$temp_2t+'['+$temp_VarAt+']';
		}
		if(temp_val==''&&temp_text==''){
			if(oldval.indexOf(value)>=0){
	    		alert("现有规则中已包含"+texts+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('('+oldval+') or ('+value+')');
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('('+oldtext+') 或 ('+texts+')');
			return;
		}else{
			if(oldval.indexOf(temp_val)>=0){
	    		alert("现有规则中已包含"+temp_text+",无须再添加！");
	    		return;
	    	}
	    	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('('+oldval+') or ('+temp_val+')');
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('('+oldtext+') 或 ('+temp_text+')');
			return;
		}
	}
	if($temp_2=="+"||$temp_2=="-"||$temp_2=="*"||$temp_2=="÷"){
		var $temp_3 = $("#Esuper_TempB").combobox('getValue');
		var $temp_3t = $("#Esuper_TempB").combobox('getText');
		var $temp_4 = $("#Esuper_JudgeB").combobox('getValue');
		var $temp_4t = $("#Esuper_JudgeB").combobox('getText');
		if($temp_3==""||$temp_3.size==0){
		   alert("请选择监察字段!");
		   return;
		}
		if($temp_4==""||$temp_4.size==0){
		   alert("请选择判断符!");
		   return;
		}
		var $temp_TsB = $("#Esuper_changeTsB").combobox('getValue');
		if($temp_TsB==""||$temp_TsB.size==0){
		   alert("请选择!");
		   return;
		}
		if($temp_TsB=='1'){
			var $temp_FixedB = $("#Esuper_FixedB").val();
			var $temp_FixedC = $("#Esuper_FixedC").combobox('getValue');
			var $temp_FixedCt = $("#Esuper_FixedC").combobox('getText');
			if($temp_FixedB==""||$temp_FixedB.length==0){
				alert("请输入固定值!");
				return;
			}
			if($temp_FixedC==""||$temp_FixedC.size==0){
			   alert("请选择单位!");
			   return;
			}
			if($temp_FixedC=='1'){
				value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']天';
			}
			if($temp_FixedC=='2'){
				value = '@(<['+$temp_1+']> '+$temp_2+'<['+$temp_3+']>)@'+$temp_4+'['+$temp_FixedB+']';
				texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_FixedB+']工作日';
			}
		}
		if($temp_TsB=='2'){
			var $temp_VarB = $("#Esuper_VariableB").combobox('getValue');
			var $temp_VarBt = $("#Esuper_VariableB").combobox('getText');
			if($temp_VarB==""||$temp_VarB.size==0){
			   alert("请选择监察字段!");
			   return;
			}
			value = '(['+$temp_1+'] '+$temp_2+'['+$temp_3+'])'+$temp_4+'['+$temp_VarB+']';
			texts = '(['+$temp_1t+'] '+$temp_2t+'['+$temp_3t+'])'+$temp_4t+'['+$temp_VarBt+']';
		}
		if(temp_val==''&&temp_text==''){
			if(oldval.indexOf(value)>=0){
	    		alert("现有规则中已包含"+texts+",无须再添加！");
	    		return;
	    	}
			$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('('+oldval+') or ('+value+')');
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('('+oldtext+') 或 ('+texts+')');
			return;
		}else{
			if(oldval.indexOf(temp_val)>=0){
	    		alert("现有规则中已包含"+temp_text+",无须再添加！");
	    		return;
	    	}
	    	$("#BusRuleEditForm input[name='JUDGE_CONDITIONS']").val('('+oldval+') or ('+temp_val+')');
			$("#BusRuleEditForm textarea[name='JUDGE_DESC']").val('('+oldtext+') 或 ('+temp_text+')');
			return;
		}
	}
}