
	$(function(){
		
		 //默认企业法人
		 //qyclick();
		 //$("input:radio[name='BSYHLX'][value='2']").attr("checked",true); 
		 $("input[name='JBR_NAME']").removeAttr('readonly');
		 $("input[name='JBR_ZJHM']").removeAttr('readonly');
	
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
    	var flowSubmitObj = FlowUtil.getFlowObj();
    	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
    	dealItems = "," + dealItems + ",";
    	if(flowSubmitObj){
    		//获取表单字段权限控制信息
    		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
    		var exeid = flowSubmitObj.EFLOW_EXEID;
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
    		 //初始化表单字段权限控制
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_GYJSSCDJ_FORM");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			initAutoTable(flowSubmitObj);
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_GYJSSCDJ_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
					if($("input[name='SBMC']").val()){
					}else{
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
				}
				var  isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
				//设置文件是否合规
				if(isAuditPass == "-1"){
					$("input:radio[name='isAuditPass'][value='-1']").attr("checked",true); 
				}
    		}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}

			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems&&dealItems!=""){
    			$("#ptcyjb").attr("style","");
    			if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
    				$("input[name='JBR_NAME']").removeAttr('readonly');
				}
    		}
    	}
    	
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BDC_GYJSSCDJ"
		//});
		
	});
	

	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BDC_GYJSSCDJ_FORM").validationEngine("validate");
		 if(validateResult){
// 		     flowSubmitObj.CyjbJson = getCyjbJson();
			 getPowerInfoJson();
			 getPowerPeopleInfoJson();
			 getPowerSourceInfoJson();
		 	 var isAuditPass = $('input[name="isAuditPass"]:checked').val();
		     if(isAuditPass == "-1"){
		     	 parent.art.dialog({
					content : "检查上传的审批表扫描件不合规，请先退回补件。",
					icon : "warning",
					ok : true
				 });
				 return null;
		     }else{
		     	 setGrBsjbr();//个人不显示经办人设置经办人的值
				 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
				 if(submitMaterFileJson||submitMaterFileJson==""){
					 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
					 //先获取表单上的所有值
					 var formData = FlowUtil.getFormEleData("T_BDC_GYJSSCDJ_FORM");
					 for(var index in formData){
						 flowSubmitObj[eval("index")] = formData[index];
					 }
					 //console.dir(flowSubmitObj);	
					return flowSubmitObj;
				 }else{
					 return null;
				 }
		     }			 
		 }else{
			 return null;
		 }
	}
	
	function FLOW_TempSaveFun(flowSubmitObj){
		//先判断表单是否验证通过
// 		flowSubmitObj.CyjbJson = getCyjbJson();
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("T_BDC_GYJSSCDJ_FORM");
		for(var index in formData){
			flowSubmitObj[eval("index")] = formData[index];
		}				 
		return flowSubmitObj;
			 
	}
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
	
	function setFileNumber(serialNum){
		/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
		var enterprise = '${execution.SQJG_CODE}';
		var idCard = '${execution.SQRSFZ}';
		alert(idCard + "," + enterprise);
		var fileNum;
		if (enterprise != ""){
			fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
		} else {
			fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
		}
		$("#fileNumber").val(fileNum);
	}
	

	function initAutoTable(flowSubmitObj){
		var powerinfoJson = flowSubmitObj.busRecord.POWERINFO_JSON;
		if(null != powerinfoJson && '' != powerinfoJson){
			var powerinfos = eval(powerinfoJson);
			for(var i=0;i<powerinfos.length;i++){
				if(i==0){
					FlowUtil.initFormFieldValue(powerinfos[i],"powerInfo_1");
				}else{
					addPowerInfo();
					FlowUtil.initFormFieldValue(powerinfos[i],"powerInfo_"+(i+1));
				}
			}
		}
		var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
		if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson){
			var powerpeopleinfos = eval(powerpeopleinfoJson);
			for(var i=0;i<powerpeopleinfos.length;i++){
				if(i==0){
					FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_1");
				}else{
					addPowerPeopleInfo();
					FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_"+(i+1));
				}
			}
		}
		var powersourceinfoJson = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
		if(null != powersourceinfoJson && '' != powersourceinfoJson){
			var powersourceinfos = eval(powersourceinfoJson);
			for(var i=0;i<powersourceinfos.length;i++){
				if(i==0){
					FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_1");
				}else{
					addPowerSourceInfo();
					FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_"+(i+1));
				}
			}
		}
	}

	/**=================权利信息开始================================*/
	var powerInfoSn = 1;
	function addPowerInfo(){
		powerInfoSn = powerInfoSn+1;
		var table = document.getElementById("powerInfo");
		var trContent = table.getElementsByTagName("tr")[1];
		var trHtml = trContent.innerHTML;
		var findex = trHtml.indexOf("deletePowerInfo('");
		if(powerInfoSn>10){
			var replacestr = trHtml.substring(findex,findex+21);
		}else{
			var replacestr = trHtml.substring(findex,findex+20);
		}
		trHtml = trHtml.replace(replacestr,"deletePowerInfo('"+powerInfoSn+"')");
		trHtml = "<tr id=\"powerInfo_"+powerInfoSn+"\">"+trHtml+"</tr>";
		$("#powerInfo").append(trHtml);
	}

	function deletePowerInfo(idSn){
		var table = document.getElementById("powerInfo");
		if(table.rows.length==2){
			parent.art.dialog({
				content: "最少一个权利人信息！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		$("#powerInfo_"+idSn).remove();
	}

	function getPowerInfoJson(){
		var datas = [];
		var table = document.getElementById("powerInfo");
		for ( var i = 1; i <= table.rows.length-1; i++) {
			var trData = {};
			$("#powerInfo_"+i).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }          
		    });
			datas.push(trData);
		}
		$("input[type='hidden'][name='POWERINFO_JSON']").val(JSON.stringify(datas));
	}
	/**=================权利信息结束================================*/
	/**=================权利人信息开始================================*/
	var powerPeopleInfoSn = 1;
	function addPowerPeopleInfo(){
		powerPeopleInfoSn = powerPeopleInfoSn+1;
		var table = document.getElementById("powerPeopleInfo");
		var trContent = table.getElementsByTagName("tr")[1];
		var trHtml = trContent.innerHTML;
		var findex = trHtml.indexOf("deletePowerPeopleInfo('");
		if(powerPeopleInfoSn>10){
			var replacestr = trHtml.substring(findex,findex+27);
		}else{
			var replacestr = trHtml.substring(findex,findex+26);
		}
		trHtml = trHtml.replace(replacestr,"deletePowerPeopleInfo('"+powerPeopleInfoSn+"')");
		trHtml = "<tr id=\"powerPeopleInfo_"+powerPeopleInfoSn+"\">"+trHtml+"</tr>";
		$("#powerPeopleInfo").append(trHtml);
	}

	function deletePowerPeopleInfo(idSn){
		var table = document.getElementById("powerPeopleInfo");
		if(table.rows.length==2){
			parent.art.dialog({
				content: "最少一个权利人信息！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		$("#powerPeopleInfo_"+idSn).remove();
	}

	function getPowerPeopleInfoJson(){
		var datas = [];
		var table = document.getElementById("powerPeopleInfo");
		for ( var i = 1; i <= table.rows.length-1; i++) {
			var trData = {};
			$("#powerPeopleInfo_"+i).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }          
		    });
			datas.push(trData);
		}
		$("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(JSON.stringify(datas));
	}
	/**=================权利人信息开始================================*/
	/**=================权属来源信息开始================================*/
	var powerSourceInfoSn = 1;
	function addPowerSourceInfo(){
		powerSourceInfoSn = powerSourceInfoSn+1;
		var table = document.getElementById("powerSourceInfo");
		var trContent = table.getElementsByTagName("tr")[1];
		var trHtml = trContent.innerHTML;
		var findex = trHtml.indexOf("deletePowerSourceInfo('");
		if(powerSourceInfoSn>10){
			var replacestr = trHtml.substring(findex,findex+27);
		}else{
			var replacestr = trHtml.substring(findex,findex+26);
		}
		trHtml = trHtml.replace(replacestr,"deletePowerSourceInfo('"+powerSourceInfoSn+"')");
		trHtml = "<tr id=\"powerSourceInfo_"+powerSourceInfoSn+"\">"+trHtml+"</tr>";
		$("#powerSourceInfo").append(trHtml);
	}

	function deletePowerSourceInfo(idSn){
		var table = document.getElementById("powerSourceInfo");
		if(table.rows.length==2){
			parent.art.dialog({
				content: "最少一个权属来源信息！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		$("#powerSourceInfo_"+idSn).remove();
	}

	function getPowerSourceInfoJson(){
		var datas = [];
		var table = document.getElementById("powerSourceInfo");
		for ( var i = 1; i <= table.rows.length-1; i++) {
			var trData = {};
			$("#powerSourceInfo_"+i).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }          
		    });
			datas.push(trData);
		}
		$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
	}
	/**=================权属来源信息开始================================*/