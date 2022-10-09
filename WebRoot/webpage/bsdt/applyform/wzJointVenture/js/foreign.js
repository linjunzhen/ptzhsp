/**==========页面初始化js开始=======================================*/
$(function(){
	//----------------主页面开始------------------------
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
				$("#gslx").combobox("disable");
				$("#gslxzl").combobox("disable");
				$('#T_COMMERCIAL_FOREIGN_FORM').find('input,textarea').prop("readonly", true);
				$('#T_COMMERCIAL_FOREIGN_FORM').find('select').prop("disabled", "disabled");
				$('#T_COMMERCIAL_FOREIGN_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
				$('#T_COMMERCIAL_FOREIGN_FORM').find(".laydate-icon").attr('disabled',"disabled");
				$('#T_COMMERCIAL_FOREIGN_FORM').find(":button").attr('style',"display:none");
				$("input[name='SCOPE_REMARK']").prop("readonly", false);
				$("input[name='industryChose']").attr('style',"");
				$("select[name='yjfwcyxdyy']").attr("disabled",false);

				var checkoperNodes = ['工商审批','质监审批','国税审批','地税审批','社保审批','统计审批','公安审批','商务审批','办结'];
				if(checkoperNodes.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
					$("#unicode").attr('style',"");
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商审批'||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='办结'){
						$("input[name='SOCIAL_CREDITUNICODE']").prop("readonly", false);
					}
				}
				
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='公安审批'){
					$("#zjxx").attr("style","display:none;");
					$("#wftzzxx").attr("style","display:none;");
					$("#zftzzxx").attr("style","display:none;");
					$("#jgxx").attr("style","display:none;");
					$("#skrxx").attr("style","display:none;");
					$("#sbxx").attr("style","display:none;");
					$("#qtxx").attr("style","display:none;");
					$("#clxx").attr("style","display:none;");
				}
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='银行预审'){
					$("#gslxtable").attr("style","display:none;");
					$("#jbxx").attr("style","display:none;");
					$("#zjxx").attr("style","display:none;");
					$("#wftzzxx").attr("style","display:none;");
					$("#zftzzxx").attr("style","display:none;");
					$("#jgxx").attr("style","display:none;");
					$("#zxswhhrxx").attr("style","display:none;");
					$("#skrxx").attr("style","display:none;");
					$("#ryxx").attr("style","display:none;");
					$("#lxyxx").attr("style","display:none;");
					$("#wtsxx").attr("style","display:none;");
					$("#yzxx").attr("style","display:none;");
					$("#qtxx").attr("style","display:none;");
					$("#clxx").attr("style","display:none;");
					$("#sbxx").attr("style","display:none;");
					
				}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!=undefined&&(flowSubmitObj.EFLOW_CURUSEROPERNODENAME.indexOf('预审')!=-1
						||flowSubmitObj.EFLOW_CURUSEROPERNODENAME.indexOf('审批')!=-1)){
					$("#bank").attr("style","display:none;");
				}
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商审批'||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商预审'){
					$("#sbxx").attr("style","display:none;");				
					$("#yzxx").attr("style","display:none;");	
				}
				
			}
			//初始化表单字段值
			if(flowSubmitObj.busRecord){
				var url = 'dicTypeController/load.do?parentTypeCode='+flowSubmitObj.busRecord.COMPANY_SETTYPE;
				$('#gslxzl').combobox('reload',url);
	    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
				if(flowSubmitObj.busRecord.COMPANY_SETTYPE=='zwhz'){
		   	   		$('#wpdsrs').attr('style','');
				}else{
		   	   		$('#wpdsrs').attr('style','display:none;');
				}
	    		if(flowSubmitObj.busRecord.IS_ACCOUNT_OPEN!=1){
					$("#bank").attr("style","display:none;");
	    		}
				//FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_COMMERCIAL_FOREIGN_FORM");
				//$("#gslxtable").attr("style","display:none;");
				initAutoTable(flowSubmitObj);
				if(flowSubmitObj.busRecord.ITEMCODES!=null&&(flowSubmitObj.EFLOW_CURUSEROPERNODENAME==undefined
						||(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'&&flowSubmitObj.busRecord.RUN_STATUS!=1)
						||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='窗口办理'
						||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='办结')){
		    		var relatedItem = flowSubmitObj.busRecord.ITEMNAMES.split(",");
		    		var relatedItems = "";
		    		for(var i=0;i<relatedItem.length;i++){
		    			if(i>0){
		    				relatedItems += "<br>";
		    			}
		    			relatedItems += (i+1) + "、" + relatedItem[i];
		    		}
		    		$("#jjgl").attr("style","");
					$("#relatedItem").append(relatedItems);
					initRelatedItemMater(flowSubmitObj.busRecord.ITEMCODES,flowSubmitObj.EFLOW_BUSTABLENAME,flowSubmitObj.busRecord.COMPANY_ID,flowSubmitObj.EFLOW_EXEID);
				}else if(flowSubmitObj.busRecord.ITEMCODES!=null){
					$.post("commercialSetController.do?getAuditerRelatedItem",{
						itemCodes:flowSubmitObj.busRecord.ITEMCODES,
						currNodeName:flowSubmitObj.EFLOW_CURUSEROPERNODENAME
					}, function(responseText, status, xhr) {
						resultJson = $.parseJSON(responseText);
						if(resultJson.success){
							var item = resultJson.jsonString;
							var relatedItemNames = "";
							var relatedItemCodes = "";
							$.each(JSON.parse(item), function(idx, obj) {
				    			if(idx>0){
				    				relatedItemNames += "<br>";
				    				relatedItemCodes += ",";
				    			}
				    			relatedItemNames += (idx+1) + "、" + obj.itemName;
				    			relatedItemCodes += obj.itemCode;
							});
				    		$("#jjgl").attr("style","");
							$("#relatedItem").append(relatedItemNames);
							initRelatedItemMater(relatedItemCodes,flowSubmitObj.EFLOW_BUSTABLENAME,flowSubmitObj.busRecord.COMPANY_ID,flowSubmitObj.EFLOW_EXEID);
							//隐藏商事部分内容
							$("#gslxtable").attr("style","display:none;");
							$("#jbxx").attr("style","display:none;");
							$("#zjxx").attr("style","display:none;");
							$("#wftzzxx").attr("style","display:none;");
							$("#zftzzxx").attr("style","display:none;");
							$("#jgxx").attr("style","display:none;");
							$("#skrxx").attr("style","display:none;");
							$("#frxx").attr("style","display:none;");
							$("#ryxx").attr("style","display:none;");
							$("#lxyxx").attr("style","display:none;");
							$("#wtsxx").attr("style","display:none;");
							$("#yzxx").attr("style","display:none;");
							$("#qtxx").attr("style","display:none;");
							$("#clxx").attr("style","display:none;");
							$("#sbxx").attr("style","display:none;");
						}
					});
				}
				if(flowSubmitObj.busRecord.NEGATIVELIST_NAMES){
					showFmqdTr(1);
				}
				$("[id='currency']").val(flowSubmitObj.busRecord.CURRENCY);
				
				if(flowSubmitObj.busRecord.COMPANY_NATURE=='04'){
					$("#stock").attr('style','');
				}
				if(flowSubmitObj.busRecord.JOINT_STOCK_TYPE=='0'){
					$("#unlist").attr('style','');
				}
				if(flowSubmitObj.busRecord.BUSINESS_TYPE=='02'){
					$("#research").attr('style','');
				}else if(flowSubmitObj.busRecord.BUSINESS_TYPE=='03'){
					$("#function").attr('style','');
				}
				if(flowSubmitObj.busRecord.IS_DUTY_FREE=='1'){
					$("#dutyfree").attr('style','');
				}
			}else{
				$("input[name='IS_PREAPPROVAL_PASS']").eq(1).attr("checked","checked");
				$("input[name='IS_ONLY_CONTACT']").eq(0).attr("checked","checked");
				$("input[name='OPRRATE_TERM_TYPE']").eq(0).attr("checked","checked");
				$("input[name='ACCOUNTING_METHOD']").eq(0).attr("checked","checked");
				$("input[name='FINANCE_TYPE']").eq(1).attr("checked","checked");
				$("input[name='BILL_TYPE']").eq(1).attr("checked","checked");
				$("input[name='CONTRACT_TYPE']").eq(1).attr("checked","checked");
				$("input[name='LEGALSEAL_TYPE']").eq(1).attr("checked","checked");
				$("input[name='FILL_DATE']").val(laydate.now()); 
				$("#selectcurrency").find("option").eq(1).attr("selected","selected");
				//$("select[name='CURRENCY']").find("option:selected").value("人民币");
				$("[id='currency']").val("人民币");
				$('#zxdszw').find('option').eq(1).attr('selected','selected');
				$('#jszw').find('option').eq(1).attr('selected','selected');
				$('#jlzw').find('option').eq(1).attr('selected','selected');
				$("input[name='ESTABLISH_PATTERN']").eq(0).attr("checked","checked");
				$("input[name='IS_ETDZ']").eq(0).attr("checked","checked");
			}
			
			var notFiledCheck = ['工商预审','国税预审','商务处预审'];
			if(notFiledCheck.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0&&!(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='undefined')
			&&flowSubmitObj.EFLOW_ISQUERYDETAIL=='false'){
				var tabs = $('#T_COMMERCIAL_FOREIGN_FORM').find(".tab_width");
				var index = 0;
				tabs.each(function(){
					var fieldid = "field_"+index;
					var tabtext = $(this).text();
					var text = $(this).next("td").find("input,select,textarea").attr("name");
					var belongId = $(this).parent().parent().parent().parent().parent().attr("id");
					if(tabtext.indexOf("*")>=0){
						tabtext = tabtext.substr(1,tabtext.length-2);
					}else{
						tabtext = tabtext.substr(0,tabtext.length-1);
					}
					index++;
					$(this).prepend("<input class=\"shzdsh\" type=\"checkbox\" id=\""+fieldid+"\"name=\""+tabtext+"\" value=\""+text+"\" onclick=\"columnCheck(this.id,this.checked,this.value,this.name,'"+belongId+"')\" checked=\"checked\">");
				});
			}
			setFieldAudit(flowSubmitObj.EFLOW_EXEID,flowSubmitObj.EFLOW_CURUSEROPERNODENAME,flowSubmitObj.EFLOW_ISQUERYDETAIL);
			//获取表单字段权限控制信息
			//var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			 //初始化表单字段权限控制
			//FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_COMMERCIAL_FOREIGN_FORM");
		}
	//----------------主页面结束------------------------
	
	
	//----------------企业资金信息开始-------------------
	$("input[name='INVESTMENT']").attr("disabled",false);
	$("input[name='INVESTMENT']").attr("onblur","checkInvest();");
	$("#foreignCapital").attr("style","");
	//----------------企业资金信息结束-------------------
	
	$("#foreignCountry option[value='中国']").remove();
	
	//----------------中方投资者信息开始-------------------

	
	//----------------中方投资者信息结束-------------------
	
	

	
	//----------------印章信息开始-----------------------
	$("input[name='FINANCE_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=="0"){
			$("input[name='FINANCE_SPEC']").attr("disabled","disabled");
			$("input[name='FINANCE_MATERIAL']").attr("disabled","disabled");
			$("input[name='FINANCE_SPEC']").removeAttr("checked");
			$("input[name='FINANCE_MATERIAL']").removeAttr("checked");
		}else{
			$("input[name='FINANCE_SPEC']").attr("disabled",false);
			$("input[name='FINANCE_MATERIAL']").attr("disabled",false);
		}
	});
	$("input[name='BILL_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=="0"){
			$("input[name='BILL_SPEC']").attr("disabled","disabled");
			$("input[name='BILL_MATERIAL']").attr("disabled","disabled");
			$("input[name='BILL_SPEC']").removeAttr("checked");
			$("input[name='BILL_MATERIAL']").removeAttr("checked");
		}else{
			$("input[name='BILL_SPEC']").attr("disabled",false);
			$("input[name='BILL_MATERIAL']").attr("disabled",false);
		}
	});
	$("input[name='CONTRACT_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=="0"){
			$("input[name='CONTRACT_SPEC']").attr("disabled","disabled");
			$("input[name='CONTRACT_MATERIAL']").attr("disabled","disabled");
			$("input[name='CONTRACT_SPEC']").removeAttr("checked");
			$("input[name='CONTRACT_MATERIAL']").removeAttr("checked");
		}else{
			$("input[name='CONTRACT_SPEC']").attr("disabled",false);
			$("input[name='CONTRACT_MATERIAL']").attr("disabled",false);
		}
	});
	$("input[name='LEGALSEAL_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=="0"){
			$("input[name='LEGALSEAL_SPEC']").attr("disabled","disabled");
			$("input[name='LEGALSEAL_MATERIAL']").attr("disabled","disabled");
			$("input[name='LEGALSEAL_SPEC']").removeAttr("checked");
			$("input[name='LEGALSEAL_MATERIAL']").removeAttr("checked");
		}else{
			$("input[name='LEGALSEAL_SPEC']").attr("disabled",false);
			$("input[name='LEGALSEAL_MATERIAL']").attr("disabled",false);
		}
	});
	//----------------印章信息结束-----------------------	

	//$("#freeimport").attr("style","");
	
	AppUtil.ajaxProgress({
		url : "commercialController.do?getFatAddr",
		callback : function(resultJson) {
			if(resultJson.OPER_SUCCESS){
				var zhqhfdz = resultJson.zhqhfdz;
				$("#zhqhfdz").val(zhqhfdz);
			}
		}
	});
});

//初始化关联事项材料列表
function initRelatedItemMater(itemCodes,busTableName,busRecordId,exeId){
	var url = "commercialSetController.do?getRelatedItemMater";
	$.post(url,{
		itemCodes:itemCodes,
		busTableName:busTableName,
		busRecordId:busRecordId
	}, function(responseText, status, xhr) {
		resultJson = JSON.parse(responseText);
		var materHtml = "<table cellpadding=\"0\" cellspacing=\"1\" class=\"tab_tk_pro1\">";
		materHtml += "<tr><th colspan=\"6\">《"+resultJson[0].ITEM_NAME+"》材料信息</th></tr>";
		materHtml += "<tr><td class=\"tab_width1\" width=\"50px\">序号</td><td class=\"tab_width1\" width=\"400px\">材料名称</td>";
		materHtml += "<td class=\"tab_width1\" width=\"80px\">材料说明</td><td class=\"tab_width1\">附件</td>";
		materHtml += "<td class=\"tab_width1\" width=\"100px\">是否收取</td><td class=\"tab_width1\" width=\"200px\">审核状态</td></tr>";
		var itemCode = resultJson[0].ITEM_CODE;
		var sn = 1;
		for(var i in resultJson){
			var mater = resultJson[i];
			if(mater.ITEM_CODE!=itemCode){
				sn = 1;
				itemCode = mater.ITEM_CODE;
				materHtml += "<tr><th colspan=\"6\">《"+mater.ITEM_NAME+"》材料信息</th></tr>";
				materHtml += "<tr><td class=\"tab_width1\" width=\"50px\">序号</td><td class=\"tab_width1\" width=\"400px\">材料名称</td>";
				materHtml += "<td class=\"tab_width1\" width=\"80px\">材料说明</td><td class=\"tab_width1\">附件</td>";
				materHtml += "<td class=\"tab_width1\" width=\"100px\">是否收取</td><td class=\"tab_width1\" width=\"200px\">审核状态</td></tr>";
			}
			materHtml += "<tr><td>"+sn+"</td>";
			sn++;
			materHtml += "<td>";
			if(mater.MATER_ISNEED=="1"){
				materHtml += "<font class=\"tab_color\">*</font>";
			}
			materHtml += mater.MATER_NAME+"</td>";
			materHtml += "<td>"+mater.MATER_CLSMLX+mater.MATER_CLSM+"份</td>";
			materHtml += "<td><div id=\"UploadedFiles_"+mater.MATER_CODE+"\">";
			if(mater.IS_FORM==1){
				materHtml += "<p>";
				materHtml += "<a href=\"javascript:;\" onclick=\"onlineForm('"+mater.FORM_NAME+"','"+exeId+"');\" style=\"cursor: pointer;\">";
				materHtml += "<font color=\"blue\">"+mater.MATER_NAME+"</font>";
				materHtml += "</a></p>";
			}else if(mater.uploadFiles.length>0){
				for(var i in mater.uploadFiles){
					uploadFile = mater.uploadFiles[i];
					materHtml += "<p id=\""+uploadFile.FILE_ID+"\" >";
					materHtml += "<a href=\"javascript:;\" onclick=\"AppUtil.downLoadFile('"+uploadFile.FILE_ID+"');\" style=\"cursor: pointer;\">";
					materHtml += "<font color=\"blue\">"+uploadFile.FILE_NAME+"</font>";
					materHtml += "</a></p>";
				}
			}
			materHtml += "</div></td>";
			
			if(mater.SFSQ==1||mater.IS_FORM==1){
				materHtml += "<td>已收取</td>";
			}else{
				materHtml += "<td>未收取</td>";
			}
			if(mater.FILE_SHZT==1){
				materHtml += "<td>审核通过</td>";
			}else if(mater.FILE_SHZT==-1){
				materHtml += "<td>审核未通过</td>";
			}else{
				materHtml += "<td>未审核</td>";
			}
			materHtml += "</tr>";
		}
		materHtml += "</table>";
		$("#relatedItemMater").append(materHtml);
	});
}

/**在线表单查看*/
function onlineForm(formName,exeId){
	$.dialog.open("commercialSetController.do?onlineFormView&formName="+formName+"&exeId="+exeId, {
        title : "在线编辑",
        width: "80%",
        height: "100%",
        fixed: true,
        lock : true,
        resize : false
    }, false);
}

/**保存经营范围备注描述*/
function saveScopeRemark(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){
			var COMPANY_ID = flowSubmitObj.busRecord.COMPANY_ID;
			var SCOPE_REMARK = $("[name='SCOPE_REMARK']").val();
			var url = "domesticControllerController.do?saveScopeRemark";
			$.post(url,{
				SCOPE_REMARK:SCOPE_REMARK,
				COMPANY_ID:COMPANY_ID,
				tableName:'T_COMMERCIAL_COMPANY',
				tableColumn:'COMPANY_ID'
			}, function(responseText, status, xhr) {
				var responseJson = JSON2.parse(responseText);
				if(responseJson.success){
					alert("保存成功！")		
				}else{
					alert("保存失败！");
				}
				
			});
		}else{
			alert("保存失败！");
		}		
	}else{
		alert("保存失败！");
	}	
}
var jyfwAdd = "";
function setJyfw(val){	
	var BUSSINESS_SCOPE = $("[name='BUSSINESS_SCOPE']").val();
	if(null!=BUSSINESS_SCOPE&&''!=BUSSINESS_SCOPE && val!=null && val!=''){
		jyfwAdd = jyfwAdd.replace("（依法须经批准的项目，经相关部门批准后方可开展经营活动）","").replace("（"+val+"）", "").replace("("+val+")", "") + "（"+val+"）"+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
		BUSSINESS_SCOPE = BUSSINESS_SCOPE.replace("（依法须经批准的项目，经相关部门批准后方可开展经营活动）","").replace("（"+val+"）", "").replace("("+val+")", "") + "（"+val+"）"+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
	}else{
		BUSSINESS_SCOPE = BUSSINESS_SCOPE.replace(jyfwAdd, "")+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
		jyfwAdd="";
	} 
	$("[name='BUSSINESS_SCOPE']").val(BUSSINESS_SCOPE)
}
function columnCheck(id,checked,value,name,belongId){
	if(belongId=='undefined'){
		belongId='';
	}
	var flowSubmitObj = FlowUtil.getFlowObj();
	var curNode = flowSubmitObj.EFLOW_CURUSEROPERNODENAME;
	var exeId = flowSubmitObj.EFLOW_EXEID;
	if(!checked){
		$.dialog.open("commercialController.do?goFieldAudit&fieldName="+value+"&curNode="+curNode+"&exeId="+exeId+"&fieldId="+id+"&fieldtext="+name+"&belongId="+belongId, {
			title : "字段审核意见",
			width : "450px",
			height : "200px",
			lock : true,
			resize : false,
			cancel : false
		}, false);
	}else{
		$.ajax({
            type: "POST",
            url: "commercialController.do?deleteFieldOpinion&fieldName="+value+"&curNode="+curNode+"&exeId="+exeId,
            async: false, 
            cache: false,
            success: function (responseText) {
            	var resultJson = $.parseJSON(responseText);
            	if(resultJson.success){
					$("[id='opinion_"+id+"']").each(function(index){							
						$(this).remove();
					});
            		parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						ok: true
					});
            	}
            }
        });
	}
}

/**显示字段审核意见*/
function showopinion(id,opinion){
	if($("#"+id).parents('td:first').next().find("input:text,select,textarea").length>0){
		$("#"+id).parents('td:first').next().find("input:text,select,textarea").after("<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见："+opinion+"</span>");
	}else if($("#"+id).parents('td:first').next().find(":radio,:checkbox").length>0){
		var html = $("#"+id).parents('td:first').next().html();
		$("#"+id).parents('td:first').next().html(html+"<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见："+opinion+"</span>");
	}
	
}
/**取消审核*/
function cancelCheck(id){
	$("#"+id).prop("checked",true);
}
/**初始化字段意见*/
function setFieldAudit(exeId,curNode,EFLOW_ISQUERYDETAIL){
	var url = "fieldAuditController/datagrid.do?isShow=1&Q_T.EXE_ID_EQ="+exeId;
	if(EFLOW_ISQUERYDETAIL=='true'){
		url+= "&isShowDelete=1"
	}
	$.post(url,{
	}, function(responseText, status, xhr) {
		var resultJson = responseText.rows;
		for(var i=0;i<resultJson.length;i++){	
			var BELONG_ID =resultJson[i].BELONG_ID;
			var num = 0
			if(BELONG_ID!=null && BELONG_ID!=''){
				num = BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length)-1;
			}
			if(isNaN(num)){
			   num = 0;
			}
			$("[name='"+resultJson[i].FIELD_NAME+"']").each(function(index){				
				if(num==index&&(resultJson[i].AUDIT_NODE==curNode||EFLOW_ISQUERYDETAIL=='true')){
					var targetField = $(this);
					var type = targetField.attr("type");
					var id = targetField.parent().prev().find("input:checkbox").attr("id");
					$("#"+id).prop("checked",false);
					if(type=="checkbox"||type=="radio"){	
						var html = targetField.parent().html();
						targetField.parent().html(html+"<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见（"+resultJson[i].CREATE_TIME+"）："+resultJson[i].AUDIT_OPINION+"</span>");					
						
					}else{						
						targetField.parent().append("<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见（"+resultJson[i].CREATE_TIME+"）："+resultJson[i].AUDIT_OPINION+"</span>");
					}
				}				
			});
		}
	});
}
function showJwInvestorAppoint(obj,id){	
	var  INVESTOR_JOINT_TYPE = obj.INVESTOR_JOINT_TYPE;
	var  IS_PARTNERSHIP = obj.IS_PARTNERSHIP;
	if(INVESTOR_JOINT_TYPE != 6 && IS_PARTNERSHIP == 1){
		$("#" + id + " .foreignAppointTr").show();
	} else {		
		$("#" + id + " .foreignAppointTr").hide();
	}
}
function showZfInvestorAppoint(obj,id){	
	var  INVESTOR_JOINT_TYPE = obj.INVESTOR_JOINT_TYPE;
	var  IS_PARTNERSHIP = obj.IS_PARTNERSHIP;
	if(INVESTOR_JOINT_TYPE != 'zrr' && IS_PARTNERSHIP == 1){
		$("#" + id + " .chineseAppointTr").show();
	} else {		
		$("#" + id + " .chineseAppointTr").hide();
	}
}
function initAutoTable(flowSubmitObj){
	var investForeignJson = flowSubmitObj.busRecord.FOREIGNINVESTOR_JSON;
	var investForeigns = eval(investForeignJson);
	for(var i=0;i<investForeigns.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(investForeigns[i],"foreignInvest_1");
			showJwInvestorAppoint(investForeigns[i],"foreignInvest_1");
		}else{
			addForeignInvest();
			FlowUtil.initFormFieldValue(investForeigns[i],"foreignInvest_"+(i+1));
			showJwInvestorAppoint(investForeigns[i],"foreignInvest_"+(i+1));
		}
	}
	
	var investDomesticJson = flowSubmitObj.busRecord.DOMESTICINVESTOR_JSON;
	if(null != investDomesticJson && investDomesticJson != ''){
		$("#chineseCapital").show();
		var investDomestics = eval(investDomesticJson);
		for(var i=0;i<investDomestics.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(investDomestics[i],"ChineseInvest_1");
				showZfInvestorAppoint(investDomestics[i],"ChineseInvest_1");
			}else{
				addChineseInvest();
				FlowUtil.initFormFieldValue(investDomestics[i],"ChineseInvest_"+(i+1));
				showZfInvestorAppoint(investDomestics[i],"ChineseInvest_"+(i+1));
			}
		}		
	}else{
		$("#chineseCapital").hide();
		$("#ChineseInvest").remove();
	}
	
	
	var attorneyJson = flowSubmitObj.busRecord.ATTORNEY_JSON;
	var attorneys = eval(attorneyJson);
	for(var i=0;i<attorneys.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(attorneys[i],"attorney_1");
		}else{
			addAttorney();
			FlowUtil.initFormFieldValue(attorneys[i],"attorney_"+(i+1));
		}
	}
	/**
	var partnerJson = flowSubmitObj.busRecord.PARTNER_JSON;
	var partners = eval(partnerJson);
	for(var i=0;i<partners.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(partners[i],"pratner_1");
		}else{
			addPratner();
			FlowUtil.initFormFieldValue(partners[i],"pratner_"+(i+1));
		}
	}
	**/
	var fdcjjjgJson = flowSubmitObj.busRecord.FDCJJJG_JSON;
	if(null != fdcjjjgJson && '' != fdcjjjgJson){
		var fdcjjjgs = eval(fdcjjjgJson);
		for(var i=0;i<fdcjjjgs.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(fdcjjjgs[i],"fdcjjjg_1");
			}else{
				addFdcjjjg();
				FlowUtil.initFormFieldValue(fdcjjjgs[i],"fdcjjjg_"+(i+1));
			}
		}
	}
	var ggfbdwJson = flowSubmitObj.busRecord.GGFBDW_JSON;
	if(null != ggfbdwJson && '' != ggfbdwJson){
		var ggfbdws = eval(ggfbdwJson);
		for(var i=0;i<ggfbdws.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(ggfbdws[i],"ggfbdw_1");
			}else{
				addGgfbdw();
				FlowUtil.initFormFieldValue(ggfbdws[i],"ggfbdw_"+(i+1));
			}
		}
	}
}
/**==========页面初始化js开始=======================================*/


/**==========房地产经纪机构及其分支机构备开始================================*/
var fdcjjjgSn = 1;
function addFdcjjjg(){
	fdcjjjgSn = fdcjjjgSn+1;
	var table = document.getElementById("fdcjjjg");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteFdcjjjg('");
	if(fdcjjjgSn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteFdcjjjg('"+fdcjjjgSn+"')");
	trHtml = "<tr id=\"fdcjjjg_"+fdcjjjgSn+"\">"+trHtml+"</tr>";
	$("#fdcjjjg").append(trHtml);
}

function deleteFdcjjjg(idSn){
	var table = document.getElementById("fdcjjjg");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个房地产经纪专业人员！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#fdcjjjg_"+idSn).remove();
}

function getFdcjjjgJson(){
	var datas = [];
	var table = document.getElementById("fdcjjjg");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#fdcjjjg_"+i).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='FDCJJJG_JSON']").val(JSON.stringify(datas));
}
/**==========房地产经纪机构及其分支机构备结束================================*/

/**==========广告发布单位 开始================================*/
var ggfbdwSn = 1;
function addGgfbdw(){
	ggfbdwSn = ggfbdwSn+1;
	var table = document.getElementById("ggfbdw");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteGgfbdw('");
	if(ggfbdwSn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteGgfbdw('"+ggfbdwSn+"')");
	trHtml = "<tr id=\"ggfbdw_"+ggfbdwSn+"\">"+trHtml+"</tr>";
	$("#ggfbdw").append(trHtml);
}

function deleteGgfbdw(idSn){
	var table = document.getElementById("ggfbdw");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个广告从业、审查人员！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#ggfbdw_"+idSn).remove();
}

function getGgfbdwJson(){
	var datas = [];
	var table = document.getElementById("ggfbdw");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#ggfbdw_"+i).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='GGFBDW_JSON']").val(JSON.stringify(datas));
}
/**==========广告发布单位 结束================================*/

/**==========主页面js开始===========================================*/

function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var forms = $("form[id]");
	 var validateResult = true;
	 forms.each(function() {
			var formId = $(this).attr("id");
			if(!$("#"+formId).validationEngine("validate")){
				validateResult = false;
				return false;
			}
		});
		flowSubmitObj.shzdshIsOk = true;
		$(".shzdsh").each(function(i){		
			if(!$(this).is(":checked")){
				flowSubmitObj.shzdshIsOk = $(this).is(":checked");
			}
		});
	 //var validateResult =$("#T_COMMERCIAL_FOREIGN_FORM").validationEngine("validate");
	 if(validateResult){
		 if(!getInvestForeign()){
			 return null;
		 }
		 //getPratnerJson();
		 getAttorneyJson();
		 if(!checkSealSelect()){
			 return null;
		 }
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 getFdcjjjgJson();//房地产经纪专业人员
		 getGgfbdwJson();//广告从业、审查人员
		 //先获取表单上的所有值
		 var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					flowSubmitObj[eval("index")] = formData[index];
				}
			});
		 /*var formData = FlowUtil.getFormEleData("T_COMMERCIAL_FOREIGN_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }*/
		 //console.dir(flowSubmitObj);
		 //var checkNodeName = ['工商预审','质监预审','国税预审','地税预审','社保预审','统计预审','商务处预审','交建预审'];
		 //var checkNodeName2 = ['质监审批','国税审批','地税审批','社保审批','统计审批','公安审批','商务审批','交建审批'];
		 var checkNodeName = ['工商预审','国税预审','交建预审','食药监预审','商务处预审','银行预审'];
		 var checkNodeName2 = ['工商审批','交建审批','食药监审批'];
		 if(checkNodeName.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
			 //flowSubmitObj.EFLOW_JOINPRENODENAMES="工商预审,质监预审,国税预审,地税预审,社保预审,统计预审,商务处预审,交建预审";
			 flowSubmitObj.EFLOW_JOINPRENODENAMES="工商预审,国税预审,交建预审,食药监预审,银行预审";
		 }else if(checkNodeName2.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
			 //flowSubmitObj.EFLOW_JOINPRENODENAMES="质监审批,国税审批,地税审批,社保审批,统计审批,公安审批,商务审批,交建审批";
			 flowSubmitObj.EFLOW_JOINPRENODENAMES="工商审批,交建审批,食药监审批";
		 }
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}

function FLOW_TempSaveFun(flowSubmitObj){
	 // 先判断表单是否验证通过
	 var forms = $("form[id]");
	 var validateResult = true;
	 forms.each(function() {
			var formId = $(this).attr("id");
			if(!$("#"+formId).validationEngine("validate")){
				validateResult = false;
				return false;
			}
		});
	 //var validateResult=$("#T_COMMERCIAL_FOREIGN_FORM").validationEngine("validate");
	 if(validateResult){
		 if(!getInvestForeign()){
			 return null;
		 }
		 //getPratnerJson();
		 getAttorneyJson();
		 if(!checkSealSelect()){
			 return null;
		 }
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 getFdcjjjgJson();//房地产经纪专业人员
		 getGgfbdwJson();//广告从业、审查人员
		 // 先获取表单上的所有值
		 var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					flowSubmitObj[eval("index")] = formData[index];
				}
			});
		/* var formData = FlowUtil.getFormEleData("T_COMMERCIAL_FOREIGN_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }*/
		 // console.dir(flowSubmitObj);
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


/**==========主页面js结束===========================================*/

/**==========企业基本信息页面js开始=======================================*/
function choseScope(){
	var selected = $("textarea[name='BUSSINESS_SCOPE']").val();
	selected = selected.substr(0,selected.indexOf("("));
	$.dialog.open("commercialController.do?scopeSelector&selected="+selected, {
		title : "经营范围筛选",
		width : "550px",
		height : "480px",
		lock : true,
		resize : false,
        close: function () {
			var selectClassify = art.dialog.data("selectClassify");
			if(selectClassify){
				$("textarea[name='BUSSINESS_SCOPE']").val(selectClassify.chooseClassify+"（依法须经批准的项目，经相关部门批准后方可展开经营活动）");
			}
			art.dialog.removeData("selectClassify");
		}
	}, false);
}
/**
* 经营范围选择器
**/
function showSelectJyfw(){
	var induIds = $("input[name='BUSSINESS_SCOPE_ID']").val();
	var url = "domesticControllerController/selector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
	+"checkCascadeN=&ISTZINDUSTRY=-1";
	$.dialog.open(url, {
		title : "经营范围选择器",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var selectInduInfo = art.dialog.data("selectInduInfo");
			if(selectInduInfo){
				$("[name='BUSSINESS_SCOPE']").val(selectInduInfo.induNames);
				$("[name='BUSSINESS_SCOPE_ID']").val(selectInduInfo.induIds);
				$("[name='INVEST_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='INVEST_INDUSTRY_ID']").val(selectInduInfo.induIds);
				$("[name='INDUSTRY_CODE']").val(selectInduInfo.induCodes);
				art.dialog.removeData("selectInduInfo");
			}
		}
	}, false);
};

/**
* 经营范围选择器
**/
function showSelectJyfwNew(){
	var ids = $("input[name='BUSSINESS_SCOPE_ID']").val();
	var defId=$("input[name='FLOW_DEFID']").val();
	parent.$.dialog.open("domesticControllerController.do?selectorNew&allowCount=30&noAuth=true&ids="
		+ ids, {
			title : "经营范围选择器",
			width : "850px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectBusScopeInfo = art.dialog.data("selectBusScopeInfo");
				if (selectBusScopeInfo && selectBusScopeInfo.ids) {
					$("[name='BUSSINESS_SCOPE_ID']").val(selectBusScopeInfo.ids);
					$("[name='INVEST_INDUSTRY_ID']").val(selectBusScopeInfo.ids);
					$("[name='BUSSINESS_SCOPE']").val(selectBusScopeInfo.busscopeNames);
					$("[name='INVEST_INDUSTRY']").val(selectBusScopeInfo.induNames);
					$("[name='INDUSTRY_CODE']").val(selectBusScopeInfo.induCodes);
					art.dialog.removeData("selectBusScopeInfo");
				}
			}
		}, false);
};
function choseIndustry(){
	var selected = $("textarea[name='INVEST_INDUSTRY']").val();
	var industryCode = $("input[name='INDUSTRY_CODE']").val();
	$.dialog.open("commercialController.do?industrySelector&selected="+selected+"&industryCode="+industryCode, {
		title : "投资行业筛选",
		width : "550px",
		height : "300px",
		lock : true,
		resize : false,
        close: function () {
			var selectIndustry = art.dialog.data("selectIndustry");
			if(selectIndustry){
				$("textarea[name='INVEST_INDUSTRY']").val(selectIndustry.chooseIndustry);
				$("input[name='INDUSTRY_CODE']").val(selectIndustry.industryCodes);
			}
			art.dialog.removeData("selectIndustry");
		}
	}, false);
}
/**
* 投资行业选择器
**/
function showSelectTzhy(){
	var induIds = $("input[name='INVEST_INDUSTRY_ID']").val();
	var url = "domesticControllerController/selector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
	+"checkCascadeN=&ISTZINDUSTRY=-1";
	$.dialog.open(url, {
		title : "投资行业选择器",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var selectInduInfo = art.dialog.data("selectInduInfo");
			if(selectInduInfo){
				$("[name='INVEST_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='INVEST_INDUSTRY_ID']").val(selectInduInfo.induIds);
				$("[name='INDUSTRY_CODE']").val(selectInduInfo.induCodes);
				art.dialog.removeData("selectInduInfo");
			}
		}
	}, false);
};
/**
* 主营行业选择器
**/
function showSelectZyhy(){
	var induIds = $("input[name='MAIN_INDUSTRY_ID']").val();
	var url = "domesticControllerController/selector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
	+"checkCascadeN=&ISTZINDUSTRY=1";
	$.dialog.open(url, {
		title : "主营行业选择器",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var selectInduInfo = art.dialog.data("selectInduInfo");
			if(selectInduInfo){
				$("[name='MAIN_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='MAIN_INDUSTRY_ID']").val(selectInduInfo.induIds);
				art.dialog.removeData("selectInduInfo");
			}
		}
	}, false);
};

/**
* 负面清单选择器
**/
function showSelectFmqd(){
	var negativeListCodes = $("input[name='NEGATIVELIST_CODES']").val();
	
	$.dialog.open("negativeListController.do?selector&allowCount=0&negativeListCodes="+negativeListCodes, {
		title : "负面清单选择器",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var negativeListInfo = art.dialog.data("negativeListInfo");
			if(negativeListInfo){
				$("[name='NEGATIVELIST_CODES']").val(negativeListInfo.negativeListCodes);
				$("[name='NEGATIVELIST_NAMES']").val(negativeListInfo.negativeListNames);
				if(negativeListInfo.negativeListCodes!=null&&negativeListInfo.negativeListCodes!=''){					
					showFmqdTr(1);
				}else{					
					showFmqdTr(0);
				}
				art.dialog.removeData("negativeListInfo");
				
			}
		}
	}, false);
};
/**
* 选择负面清单之后显示“批准证书编号”、“进出口企业代码”、“批准文号”
*/
function showFmqdTr(value){
	if (value == "1") {			
		$("#fmqdtr1").attr("style","");	
		$("#fmqdtr2").attr("style","");	
		$("input[name='CERTIFICATE_NO']").attr("disabled",false);
		$("input[name='IMPANDEXP_CODE']").attr("disabled",false);
		$("input[name='APPROVAL_NO']").attr("disabled",false);
	} else {	
		$("#fmqdtr1").attr("style","display: none;");	
		$("#fmqdtr2").attr("style","display: none;");			
		$("input[name='CERTIFICATE_NO']").attr("disabled",true); 
		$("input[name='IMPANDEXP_CODE']").attr("disabled",true); 
		$("input[name='APPROVAL_NO']").attr("disabled",true); 	
	}
}
/**==========企业基本信息页面js结束=======================================*/

/**==========企业资金信息页面js开始=======================================*/
function currencyChange(value){	
	$("[id='currency']").val(value);
	if(value!="人民币"){
		$("input[name='FOLDING_RMB']").attr("disabled",false);
	}
}

function calculation(){
	var allSum = 0;
	var foreignSum = 0;
	var chineseSum = 0;
	var foreigntable = document.getElementById("foreignInvest");
	for ( var i = 1; i <= foreigntable.rows.length-1; i++) {
		var checkHaveName = ['INVESTMENT_ABROAD_EXCHANGE','INVESTMENT_ABROAD_RMB','INVESTMENT_DOMESTIC_RMB','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var investSum = 0;
		$("#foreignInvest_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""){
				  investSum = parseFloat(investSum)+parseFloat(inputValue);
				  allSum = parseFloat(allSum)+parseFloat(inputValue);
				  foreignSum = parseFloat(foreignSum)+parseFloat(inputValue);
			  }
	    });
		$("#foreignInvest_"+i).find("*[name='CONTRIBUTION']").val(investSum);
		if($("select[name='CURRENCY']").val()=='人民币'){
			$("#foreignInvest_"+i).find("*[name='FOLDING_RMB']").val(investSum);
		}
	}
	var chinesetable = document.getElementById("ChineseInvest");
	for ( var i = 1; i <= chinesetable.rows.length-1; i++) {
		var checkHaveName = ['INVESTMENT_DOMESTIC_RMB','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var investSum = 0;
		$("#ChineseInvest_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""){
				  investSum = parseFloat(investSum)+parseFloat(inputValue);
				  allSum = parseFloat(allSum)+parseFloat(inputValue);
				  chineseSum = parseFloat(chineseSum)+parseFloat(inputValue);
			  }
	    });
		$("#ChineseInvest_"+i).find("*[name='CONTRIBUTION']").val(investSum);
		if($("select[name='CURRENCY']").val()=='人民币'){
			$("#ChineseInvest_"+i).find("*[name='FOLDING_RMB']").val(investSum);
		}
	}
	for ( var i = 1; i <= foreigntable.rows.length-1; i++) {
		var investSum = $("#foreignInvest_"+i).find("*[name='CONTRIBUTION']").val();
		var propotion = parseFloat(investSum/allSum)*100;
		if(propotion==100){
			$("#foreignInvest_"+i).find("*[name='PROPORTION']").val(propotion+"%");
		}else{
			$("#foreignInvest_"+i).find("*[name='PROPORTION']").val(propotion.toFixed(2)+"%");
		}
	}
	for ( var i = 1; i <= chinesetable.rows.length-1; i++) {
		var investSum = $("#ChineseInvest_"+i).find("*[name='CONTRIBUTION']").val();
		var propotion = parseFloat(investSum/allSum)*100;
		if(propotion==100){
			$("#ChineseInvest_"+i).find("*[name='PROPORTION']").val(propotion+"%");
		}else{
			$("#ChineseInvest_"+i).find("*[name='PROPORTION']").val(propotion.toFixed(2)+"%");
		}
	}
	$("input[name='REGISTERED_CAPITAL']").val(allSum);
	$("input[name='CHINA_CAPITAL']").val(chineseSum);
	var propotion = parseFloat(chineseSum/allSum)*100;
	if(propotion==100){
		$("input[name='CHINA_RATIO']").val(propotion+"%");
	}else{
		$("input[name='CHINA_RATIO']").val(propotion.toFixed(2)+"%");
	}
	$("input[name='FOREIGN_CAPITAL']").val(foreignSum);
	propotion = parseFloat(foreignSum/allSum)*100;
	if(propotion==100){
		$("input[name='FOREIGN_RATIO']").val(propotion+"%");
	}else{
		$("input[name='FOREIGN_RATIO']").val(propotion.toFixed(2)+"%");
	}
	
	checkInvest();
}
/**==========企业资金信息页面js开始=======================================*/

/**==========外方合伙人信息页面js开始=======================================*/
var foreignSn = 1;
function addForeignInvest(){
	foreignSn = foreignSn+1;
	var table = document.getElementById("foreignInvest");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteForeignInvest('");
	if(foreignSn>10){
		var replacestr = trHtml.substring(findex,findex+25);
	}else{
		var replacestr = trHtml.substring(findex,findex+24);
	}
	trHtml = trHtml.replace(replacestr,"deleteForeignInvest('"+foreignSn+"')");
	trHtml = "<tr id=\"foreignInvest_"+foreignSn+"\">"+trHtml+"</tr>";
	$("#foreignInvest").append(trHtml);
}

function deleteForeignInvest(idSn){
	var table = document.getElementById("foreignInvest");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个外方合伙人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#foreignInvest_"+idSn).remove();
	calculation();
}

function getInvestForeign(){
	var datas = [];
	var table = document.getElementById("foreignInvest");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var haveOne = false;
		var checkHaveName = ['INVESTMENT_ABROAD_EXCHANGE','INVESTMENT_ABROAD_RMB','INVESTMENT_DOMESTIC_RMB','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var trData = {};
		$("#foreignInvest_"+i).find("*[name]").each(function(){
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
				  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""&&inputValue!=null&&inputValue!="undefined"){
					  haveOne = true;
				  }
			  }          
	    });
		if(!haveOne){
			parent.art.dialog({
				content: "境外合伙人出资方式至少包含一项！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		datas.push(trData);
	}
	
	$("input[type='hidden'][name='FOREIGNINVESTOR_JSON']").val(JSON.stringify(datas));
	return true;
}

/**==========外方合伙人信息页面js结束=======================================*/

/**==========中方合伙人信息页面js开始=======================================*/
var chineseSn = 1;
function addChineseInvest(){
	chineseSn = chineseSn+1;
	var table = document.getElementById("ChineseInvest");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteChineseInvest('");
	if(chineseSn>10){
		var replacestr = trHtml.substring(findex,findex+25);
	}else{
		var replacestr = trHtml.substring(findex,findex+24);
	}
	trHtml = trHtml.replace(replacestr,"deleteChineseInvest('"+chineseSn+"')");
	trHtml = "<tr id=\"ChineseInvest_"+chineseSn+"\">"+trHtml+"</tr>";
	$("#ChineseInvest").append(trHtml);
}

function deleteChineseInvest(idSn){
	var table = document.getElementById("ChineseInvest");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个中方合伙人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#ChineseInvest_"+idSn).remove();
	calculation();
}

function getInvestChinese(){
	var datas = [];
	var table = document.getElementById("ChineseInvest");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var haveOne = false;
		var checkHaveName = ['INVESTMENT_DOMESTIC_RMB','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var trData = {};
		$("#ChineseInvest_"+i).find("*[name]").each(function(){
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
				  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""&&inputValue!=null&&inputValue!="undefined"){
					  haveOne = true;
				  }
			  }          
		});
		if(!haveOne){
			parent.art.dialog({
				content: "中方合伙人出资方式至少包含一项！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		datas.push(trData);
	}
	$("input[type='hidden'][name='DOMESTICINVESTOR_JSON']").val(JSON.stringify(datas));

	return true;
}
/**==========中方合伙人信息页面js结束=======================================*/

/**==========执行事务合伙人信息页面js开始=======================================*/
var pratnerSn = 1;
function addPratner(){
	pratnerSn = pratnerSn+1;
	var table = document.getElementById("pratner");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePratner('");
	if(pratnerSn>10){
		var replacestr = trHtml.substring(findex,findex+19);
	}else{
		var replacestr = trHtml.substring(findex,findex+18);
	}
	trHtml = trHtml.replace(replacestr,"deletePratner('"+pratnerSn+"')");
	trHtml = "<tr id=\"pratner_"+pratnerSn+"\">"+trHtml+"</tr>";
	$("#pratner").append(trHtml);
}

function deletePratner(idSn){
	var table = document.getElementById("pratner");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个执行事务合伙人！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#pratner_"+idSn).remove();
}

function getPratnerJson(){
	var datas = [];
	var table = document.getElementById("pratner");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#pratner_"+i).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='PARTNER_JSON']").val(JSON.stringify(datas));
}
/** ==========执行事务合伙人信息页面js结束======================================= */

/**==========外商投资企业法律文件送达授权委托书基本信息开始================================*/
var attorneySn = 1;
function addAttorney(){
	attorneySn = attorneySn+1;
	var table = document.getElementById("attorney");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteAttorney('");
	if(attorneySn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteAttorney('"+attorneySn+"')");
	trHtml = "<tr id=\"attorney_"+attorneySn+"\">"+trHtml+"</tr>";
	$("#attorney").append(trHtml);
}

function deleteAttorney(idSn){
	var table = document.getElementById("attorney");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个授权委托书信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#attorney_"+idSn).remove();
}

function getAttorneyJson(){
	var datas = [];
	var table = document.getElementById("attorney");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#attorney_"+i).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='ATTORNEY_JSON']").val(JSON.stringify(datas));
}
/**==========外商投资企业法律文件送达授权委托书基本信息结束================================*/

/**==========印章信息页面js开始=======================================*/
function checkSealSelect(){
	var msg = "";
	var checkFlag = true;
	if($("input[type=checkbox][name='OFFICIAL_TYPE']:checked").length<=0){
		msg+= "企业公章信息不能为空！<br/>";
		checkFlag =  false;
	}
	if($("input[type=checkbox][name='OFFICIAL_SPEC']:checked").length<=0){
		msg+= "请选择企业公章印章规格！<br/>";
		checkFlag =  false;
	}
	if($("input:radio[name='OFFICIAL_MATERIAL']:checked").val()==null){
		msg+= "请选择企业公章印章材质！<br/>";
		checkFlag =  false;
	}
	if($("input:radio[name='FINANCE_TYPE']:checked").val()==1){
		if($("input[type=checkbox][name='FINANCE_SPEC']:checked").length<=0){
			msg+= "请选择财务专用章印章规格！<br/>";
			checkFlag =  false;
		}
		if($("input:radio[name='FINANCE_MATERIAL']:checked").val()==null){
			msg+= "请选择财务专用章印章材质！<br/>";
			checkFlag =  false;
		}
	}
	if($("input:radio[name='BILL_TYPE']:checked").val()==1){
		if($("input[type=checkbox][name='BILL_SPEC']:checked").length<=0){
			msg+= "请选择发票专用章印章规格！<br/>";
			checkFlag =  false;
		}
		if($("input:radio[name='BILL_MATERIAL']:checked").val()==null){
			msg+= "请选择发票专用章印章材质！<br/>";
			checkFlag =  false;
		}
	}
	if($("input:radio[name='CONTRACT_TYPE']:checked").val()==1){
		if($("input[type=checkbox][name='CONTRACT_SPEC']:checked").length<=0){
			msg+= "请选择合同公章印章规格！<br/>";
			checkFlag =  false;
		}
		if($("input:radio[name='CONTRACT_MATERIAL']:checked").val()==null){
			msg+= "请选择合同公章印章材质！<br/>";
			checkFlag =  false;
		}
	}
	if($("input:radio[name='LEGALSEAL_TYPE']:checked").val()==1){
		if($("input[type=checkbox][name='LEGALSEAL_SPEC']:checked").length<=0){
			msg+= "请选择法人印章印章规格！<br/>";
			checkFlag =  false;
		}
		if($("input:radio[name='LEGALSEAL_MATERIAL']:checked").val()==null){
			msg+= "请选择法人印章印章材质！<br/>";
			checkFlag =  false;
		}
	}
	if(!checkFlag){
		parent.art.dialog({
			content: msg,
			icon:"warning",
			ok: true
		});
	}
	return checkFlag;
}

function specCheck(checkboxId){
	if($('#'+checkboxId).is(':checked')){
		$('#'+checkboxId+'Detail').attr("disabled",false);
	}else{
		$('#'+checkboxId+'Detail').attr("disabled","disabled");
	}
}
/**==========印章信息页面js结束=======================================*/

/**
 * 下载附件
 * @param {} fileId
 */
function downLoadFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){			
			window.location.href=__ctxPath+"/domesticControllerController/downLoad.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE;
		}else{
			art.dialog({
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
	}
}
/**
 * 下载附件
 * @param {} fileId
 */
function downLoadWordFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){			
			window.location.href=__ctxPath+"/domesticControllerController/downLoadWord.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE;
		}else{
			art.dialog({
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
	}
}
/**
 * 预览附件
 * @param {} fileId
 */
function previewFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){	
			window.open(__ctxPath+"/domesticControllerController/pdfPreview.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
		}else{
			art.dialog({
				content : "无法预览",
				icon : "error",
				ok : true
			});
		}
	}
}
function checkDifferent(name){
	var legalNo = $("input[name='LEGAL_IDNO']").val();
	var financeNo = $("input[name='FINANCE_IDNO']").val();
	if(legalNo==financeNo){
		parent.art.dialog({
			content: "法人代表与财务负责人不能为同一人！",
			icon:"warning",
			ok: true
		});
		$("input[name='"+name+"']").val('');
	}
}

function checkInvest(){
	var INVESTMENT = $("input[name='INVESTMENT']").val();
	var REGISTERED_CAPITAL = $("input[name='REGISTERED_CAPITAL']").val();
	if(Number(INVESTMENT)<Number(REGISTERED_CAPITAL)&&INVESTMENT){
		parent.art.dialog({
			content: "投资总额必须大于等于注册资金！",
			icon:"warning",
			ok: true
		});
		$("input[name='INVESTMENT']").val('');
	}
}

function checkDirectorNum(){
	var num = 0;
	var flag = true;
	$("input[name='DIRECTOR_NUM']").each(function(){
		if($(this).val().length==0){
			flag = false;
		}else{
			num = Number(num)+Number($(this).val());
		}
	});
	if(num<3&&flag){
		parent.art.dialog({
			content: "委派董事总人数必须多于3位！",
			icon:"warning",
			ok: true
		});

		$("input[name='DIRECTOR_NUM']").each(function(){
			$(this).val('');
		});
	}
}
function checkPeriod(value){
	var byear = $("input[name='BUSSINESS_YEARS']").val();
	var now = new Date;
    now.setFullYear(now.getFullYear () + Number(byear));
    var year=now.getFullYear();
    var month=now.getMonth();
    var day=now.getDate();
    month=month+1;
    month=checkTime(month);
    day=checkTime(day);
    var time = year+"-"+month+"-"+day;
    var res = dateCompare(value,time);
    if(res==1){
    	parent.art.dialog({
			content: "出资全部缴付到位期限不能超过"+time+"！",
			icon:"warning",
			ok: true
		});
		$("input[name='PAYMENT_PERIOD']").val('');
    }
}
function dateCompare(date1,date2){
	date1 = date1.replace(/\-/gi,"/");
	date2 = date2.replace(/\-/gi,"/");
	var time1 = new Date(date1).getTime();
	var time2 = new Date(date2).getTime();
	if(time1 > time2){
		return 1;
	}else if(time1 == time2){
		return 2;
	}else{
		return 3;
	}
} 
function checkTime(i){
    if (i<10){
    	i="0" + i;
    }
    return i;
}

function changelegaljob(val){
	if(val==1){
		$("#legalJob option[value='30']").remove();
		$("#legalJob option[value='01']").remove();
		$("#legalJob").append("<option value='01'>董事长</option>"); 
	}else{
		$("#legalJob option[value='30']").remove();
		$("#legalJob option[value='01']").remove();
		$("#legalJob").append("<option value='30'>执行董事</option>"); 
	}
}

function addAppointSelect(val,kind){
	var text;
	if(kind=='zf'){
		text = "中方投资者"+val;
	}else{
		text = "外方投资者"+val;
	}
	$("[id='dszappointor']").append("<option value="+text+">"+text+"</option>");
	$("[id='fdszappointor']").append("<option value="+text+">"+text+"</option>");
	$("[id='appointors']").append("<option value="+text+">"+text+"</option>");
}

function checkAppointSelect(id){
	var dsz = $("#dszappointor option:selected").val();
	var fdsz = $("#fdszappointor option:selected").val();
	if(dsz==fdsz){
		$("#"+id+" option:first").prop("selected", 'selected');
		parent.art.dialog({
			content: "董事长与副董事长不能同时是外方或中方委派！",
			icon:"warning",
			ok: true
		});
	}
}

function checkFat(value){
	var zhqhfdz = $("input[name='zhqhfdz']").val();  
	var arrayAddr =zhqhfdz.split(",");
	var isok = false;
	for(var i=0;i<arrayAddr.length;i++){
		if(value.indexOf(arrayAddr[i])>-1){
			isok =true;
		}
	}
	if(isok){
		$("input[name='FAT_TYPE'][value='1']").click();
		$("input[name='FAT_TYPE'][value='0']").prop("disabled",true);
	}else{	
		$("input[name='FAT_TYPE'][value='0']").prop("disabled",false);		
		$("input[name='FAT_TYPE'][value='0']").click();
	}
}