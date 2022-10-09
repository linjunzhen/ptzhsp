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
				$('#T_COMMERCIAL_INDIVIDUAL_FORM').find('input,textarea').prop("readonly", true);
				$('#T_COMMERCIAL_INDIVIDUAL_FORM').find('select').prop("disabled", "disabled");
				$('#T_COMMERCIAL_INDIVIDUAL_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
				$('#T_COMMERCIAL_INDIVIDUAL_FORM').find(".laydate-icon").attr('disabled',"disabled");
				$('#T_COMMERCIAL_INDIVIDUAL_FORM').find(":button").attr('style',"display:none");
				$("input[name='SCOPE_REMARK']").prop("readonly", false);
				$("select[name='yjfwcyxdyy']").attr("disabled",false);
	
				var checkoperNodes = ['工商审批','质监审批','国税审批','地税审批','社保审批','统计审批','公安审批','办结'];
				if(checkoperNodes.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
					$("#unicode").attr('style',"");
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商审批'){
						$("input[name='SOCIAL_CREDITUNICODE']").prop("readonly", false);
					}
				}
			}
			//初始化表单字段值
			if(flowSubmitObj.busRecord){
	    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
	    		if(flowSubmitObj.busRecord.MANAGE_FORM==1){
	    			$("#family").attr("style","");
	    		}
				if(flowSubmitObj.busRecord.IS_ACCOUNT_OPEN!=1){
					$("#bank").attr("style","display:none;");
					$("#bankMater").attr("style","display:none;");
				}
				initAutoTable(flowSubmitObj);
				var SSSBLX = flowSubmitObj.busRecord.SSSBLX;	
				if(SSSBLX && SSSBLX==1){	
					$(".oldtr").remove();	
					$("#shbxdjxx").remove();	
					$("#wordNum").show();		
				} else{			
					$(".hymlTr").remove();					
					$("#lyydiv").remove();
					$(".newtr").remove();
				}
			}else{
				$("input[name='COLLECT_TIME']").val(laydate.now()); 
			}
			
			var notFiledCheck = ['工商预审','国税预审'];
			if(notFiledCheck.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0&&!(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='undefined')
			&&flowSubmitObj.EFLOW_ISQUERYDETAIL=='false'){
				var tabs = $('#T_COMMERCIAL_INDIVIDUAL_FORM').find(".tab_width");
				var index = 0;
				tabs.each(function(){
					var fieldid = "field_"+index;
					var tabtext = $(this).text();
					var text = $(this).next("td").find("input,select,textarea").attr("name");
					if(tabtext.indexOf("*")>=0){
						tabtext = tabtext.substr(1,tabtext.length-2);
					}else{
						tabtext = tabtext.substr(0,tabtext.length-1);
					}
					index++;
					$(this).prepend("<input class=\"shzdsh\" type=\"checkbox\" id=\""+fieldid+"\"name=\""+tabtext+"\" value=\""+text+"\" onclick=\"columnCheck(this.id,this.checked,this.value,this.name)\" checked=\"checked\">");
				});
			}
			
			setFieldAudit(flowSubmitObj.EFLOW_EXEID,flowSubmitObj.EFLOW_CURUSEROPERNODENAME,flowSubmitObj.EFLOW_ISQUERYDETAIL);
			
			
		}
		
		$("input[name='MANAGE_FORM']").click(function(){
			//获取值
			var Value = $(this).val();
			if(Value=="1"){
				$("#family").attr("style","");
			}else{
				$("#family").attr("style","display:none;");
			}
		});
		

		$("input[name='MANAGE_FORM']").click(function(){
			//获取值
			var Value = $(this).val();
			if(Value=="1"){
				$("input[name='INVEST_FAMILY']").attr("disabled","disabled");
			}else{
				$("input[name='INVEST_FAMILY']").attr("disabled",false);
			}
		});
});

/**保存经营范围备注描述*/
function saveScopeRemark(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){
			var COMPANY_ID = flowSubmitObj.busRecord.INDIVIDUAL_ID;
			var SCOPE_REMARK = $("[name='SCOPE_REMARK']").val();
			var url = "domesticControllerController.do?saveScopeRemark";
			$.post(url,{
				SCOPE_REMARK:SCOPE_REMARK,
				COMPANY_ID:COMPANY_ID,
				tableName:'T_COMMERCIAL_INDIVIDUAL',
				tableColumn:'INDIVIDUAL_ID'
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
	var BUSSINESS_SCOPE = $("[name='BUSINESS_SCOPE']").val();
	if(null!=BUSSINESS_SCOPE&&''!=BUSSINESS_SCOPE && val!=null && val!=''){
		jyfwAdd = jyfwAdd.replace("（依法须经批准的项目，经相关部门批准后方可开展经营活动）","").replace("（"+val+"）", "").replace("("+val+")", "") + "（"+val+"）"+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
		BUSSINESS_SCOPE = BUSSINESS_SCOPE.replace("（依法须经批准的项目，经相关部门批准后方可开展经营活动）","").replace("（"+val+"）", "").replace("("+val+")", "") + "（"+val+"）"+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
	}else{
		BUSSINESS_SCOPE = BUSSINESS_SCOPE.replace(jyfwAdd, "")+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
		jyfwAdd="";
	} 
	$("[name='BUSINESS_SCOPE']").val(BUSSINESS_SCOPE)
}
function initAutoTable(flowSubmitObj){
	var holderJson = flowSubmitObj.busRecord.FAMILY_JSON;
	var holders = eval(holderJson);
	if(holders!=null ){
		for(var i=0;i<holders.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(holders[i],"family_1");
			}else{
				addFamily();
				FlowUtil.initFormFieldValue(holders[i],"family_"+(i+1));
			}
		}
	}
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


function columnCheck(id,checked,value,name){
	var flowSubmitObj = FlowUtil.getFlowObj();
	var curNode = flowSubmitObj.EFLOW_CURUSEROPERNODENAME;
	var exeId = flowSubmitObj.EFLOW_EXEID;
	if(!checked){
		$.dialog.open(encodeURI("commercialController.do?goFieldAudit&fieldName="+value+"&curNode="+curNode+"&exeId="+exeId+"&fieldId="+id+"&fieldtext="+name), {
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
            url: encodeURI("commercialController.do?deleteFieldOpinion&fieldName="+value+"&curNode="+curNode+"&exeId="+exeId),
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
	}else{
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
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_COMMERCIAL_INDIVIDUAL_FORM").validationEngine("validate");
	 if(validateResult){
		 /**var photo = $("input[name='DEALER_PHOTO']").val();
		 if(photo==null||photo==''||photo=='undefined'){
	 		parent.art.dialog({
				content: "请上传照片",
				icon:"warning",
				ok: true
			});
		 }**/
		flowSubmitObj.shzdshIsOk = true;
		$(".shzdsh").each(function(i){		
			if(!$(this).is(":checked")){
				flowSubmitObj.shzdshIsOk = $(this).is(":checked");
			}
		});
		 getFamilyJson();
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 getFdcjjjgJson();//房地产经纪专业人员
		 getGgfbdwJson();//广告从业、审查人员
		 //先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_COMMERCIAL_INDIVIDUAL_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
		 //console.dir(flowSubmitObj);
		 var checkNodeName = ['工商预审','质监预审','国税预审','地税预审','社保预审','统计预审'];
		 var checkNodeName2 = ['质监审批','国税审批','地税审批','社保审批','统计审批','公安审批'];
		 if(checkNodeName.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
			 flowSubmitObj.EFLOW_JOINPRENODENAMES="工商预审,质监预审,国税预审,地税预审,社保预审,统计预审";
		 }else if(checkNodeName2.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
			 flowSubmitObj.EFLOW_JOINPRENODENAMES="质监审批,国税审批,地税审批,社保审批,统计审批,公安审批";
		 }
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}

function FLOW_TempSaveFun(flowSubmitObj){
	 // 先判断表单是否验证通过
	 var validateResult=$("#T_COMMERCIAL_INDIVIDUAL_FORM").validationEngine("validate");
	 if(validateResult){
		 getFamilyJson();
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 getFdcjjjgJson();//房地产经纪专业人员
		 getGgfbdwJson();//广告从业、审查人员
		 // 先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_COMMERCIAL_INDIVIDUAL_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
		 // console.dir(flowSubmitObj);
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}

/**
* 经营范围选择器
**/
function showSelectJyfw(){
	var induIds = $("input[name='BUSINESS_SCOPE_ID']").val();
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
				$("[name='BUSINESS_SCOPE']").val(selectInduInfo.induNames);
				$("[name='BUSINESS_SCOPE_ID']").val(selectInduInfo.induIds);
				art.dialog.removeData("selectInduInfo");
			}
		}
	}, false);
}

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
					$("[name='BUSSINESS_SCOPE']").val(selectBusScopeInfo.busscopeNames);
					art.dialog.removeData("selectBusScopeInfo");
				}
			}
		}, false);
};
var currentSn = 1;
function addFamily(){
	currentSn = currentSn+1;
	var table = document.getElementById("family");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteFamilys('");
	if(currentSn>10){
		var replacestr = trHtml.substring(findex,findex+19);
	}else{
		var replacestr = trHtml.substring(findex,findex+18);
	}
	trHtml = trHtml.replace(replacestr,"deleteFamilys('"+currentSn+"')");
	trHtml = "<tr id=\"family_"+currentSn+"\">"+trHtml+"</tr>";
	$("#family").append(trHtml);
}

function deleteFamilys(idSn){
	var table = document.getElementById("family");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个家庭成员信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#family_"+idSn).remove();
}

function getFamilyJson(){
	var datas = [];
	var table = document.getElementById("family");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#family_"+i).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='FAMILY_JSON']").val(JSON.stringify(datas));
	return true;
}
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
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
	}
}