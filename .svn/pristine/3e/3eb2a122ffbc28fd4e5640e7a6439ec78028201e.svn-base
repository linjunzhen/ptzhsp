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
			$('#T_COMMERCIAL_BRANCH').find('input,textarea').prop("readonly", true);
			$('#T_COMMERCIAL_BRANCH').find('select').prop("disabled", "disabled");
			$('#T_COMMERCIAL_BRANCH').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#T_COMMERCIAL_BRANCH').find(".laydate-icon").attr('disabled',"disabled");
			$('#T_COMMERCIAL_BRANCH').find(":button").attr('style',"display:none");
			$("input[name='SCOPE_REMARK']").prop("readonly", false);

			var checkoperNodes = ['工商审批','质监审批','国税审批','地税审批','社保审批','统计审批','公安审批','办结'];
			if(checkoperNodes.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
				$("#unicode").attr('style',"");
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商审批'||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='办结'){
					$("input[name='SOCIAL_CREDITUNICODE']").prop("readonly", false);
				}
			}
			
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='银行预审'){
				$("#jbxx").attr("style","display:none;");
				$("#subordinateInfo").attr("style","display:none;");
				$("#ryxx").attr("style","display:none;");
				$("#lxyxx").attr("style","display:none;");
				$("#fzrxx").attr("style","display:none;");
				$("#yzxx").attr("style","display:none;");
				$("#sbxx").attr("style","display:none;");
				$("#qtxx").attr("style","display:none;");
				$("#clxx").attr("style","display:none;");
			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!=undefined&&(flowSubmitObj.EFLOW_CURUSEROPERNODENAME.indexOf('预审')!=-1
					||flowSubmitObj.EFLOW_CURUSEROPERNODENAME.indexOf('审批')!=-1)){
				$("#bank").attr("style","display:none;");
				$("#bankMater").attr("style","display:none;");
			}
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商审批'||flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='工商预审'){
				$("#sbxx").attr("style","display:none;");			
				$("#yzxx").attr("style","display:none;");	
			}
		}
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
    		if(flowSubmitObj.busRecord.IS_ACCOUNT_OPEN!=1){
				$("#bank").attr("style","display:none;");
				$("#bankMater").attr("style","display:none;");
    		}
    		if(flowSubmitObj.busRecord.SUBORDINATE_LOCATION==0){
    			$("#outprovince").attr("style","");
    		}
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
						$("#jbxx").attr("style","display:none;");
						$("#subordinateInfo").attr("style","display:none;");
						$("#ryxx").attr("style","display:none;");
						$("#lxyxx").attr("style","display:none;");
						$("#fzrxx").attr("style","display:none;");
						$("#yzxx").attr("style","display:none;");
						$("#sbxx").attr("style","display:none;");
						$("#qtxx").attr("style","display:none;");
						$("#clxx").attr("style","display:none;");		
						$("#bank").attr("style","display:none;");
						$("#bankMater").attr("style","display:none;");
					}
				});
			}
		}else{
			$("input[name='FILL_DATE']").val(laydate.now());
		}
		
		var notFiledCheck = ['工商预审','国税预审'];
		if(notFiledCheck.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0
			&&!(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='undefined')
			&&flowSubmitObj.EFLOW_ISQUERYDETAIL=='false'){
			var tabs = $('#T_COMMERCIAL_BRANCH').find(".tab_width");
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
		//FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_COMMERCIAL_DOMESTIC_FORM");
	}
	//----------------主页面结束------------------------
	
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

/**==========页面初始化js开始=======================================*/

function initAutoTable(flowSubmitObj){
	
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
	var gczjzxqyslJson = flowSubmitObj.busRecord.GCZJZXQYSL_JSON;
	if(null != gczjzxqyslJson && '' != gczjzxqyslJson){
		var gczjzxqysls = eval(gczjzxqyslJson);
		for(var i=0;i<gczjzxqysls.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(gczjzxqysls[i],"gczjzxqysl_1");
			}else{
				addGczjzxqysl();
				FlowUtil.initFormFieldValue(gczjzxqysls[i],"gczjzxqysl_"+(i+1));
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

/**==========工程造价咨询企业设立分支机构备案开始================================*/
var gczjzxqyslSn = 1;
function addGczjzxqysl(){
	gczjzxqyslSn = gczjzxqyslSn+1;
	var table = document.getElementById("gczjzxqysl");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteGczjzxqysl('");
	if(gczjzxqyslSn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteGczjzxqysl('"+gczjzxqyslSn+"')");
	trHtml = "<tr id=\"gczjzxqysl_"+gczjzxqyslSn+"\">"+trHtml+"</tr>";
	$("#gczjzxqysl").append(trHtml);
}

function deleteGczjzxqysl(idSn){
	var table = document.getElementById("gczjzxqysl");
	if(table.rows.length==4){
		parent.art.dialog({
			content: "最少三个造价工程师！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#gczjzxqysl_"+idSn).remove();
}

function getGczjzxqyslJson(){
	var datas = [];
	var table = document.getElementById("gczjzxqysl");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#gczjzxqysl_"+i).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='GCZJZXQYSL_JSON']").val(JSON.stringify(datas));
}
/**==========工程造价咨询企业设立分支机构备案结束================================*/


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
	 if(validateResult){
		setGrBsjbr();//个人不显示经办人设置经办人的值
		 getFdcjjjgJson();//房地产经纪专业人员
		 getGgfbdwJson();//广告从业、审查人员
		 getGczjzxqyslJson();//造价工程师
		var forms = $("form[id]");
		forms.each(function() {
			var formId = $(this).attr("id");
			var formData = FlowUtil.getFormEleData(formId);
			for ( var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}
		});
		flowSubmitObj.shzdshIsOk = true;
		$(".shzdsh").each(function(i){		
			if(!$(this).is(":checked")){
				flowSubmitObj.shzdshIsOk = $(this).is(":checked");
			}
		});
		var checkNodeName = ['工商预审','国税预审','交建预审','食药监预审','商务处预审','银行预审'];
		var checkNodeName2 = ['工商审批','交建审批','食药监审批'];
		if(checkNodeName.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
			flowSubmitObj.EFLOW_JOINPRENODENAMES="工商预审,国税预审,交建预审,食药监预审,商务处预审,银行预审";
		}else if(checkNodeName2.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
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
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 getFdcjjjgJson();//房地产经纪专业人员
		 getGgfbdwJson();//广告从业、审查人员
		 getGczjzxqyslJson();//造价工程师
		 // 先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_COMMERCIAL_DOMESTIC_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
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
				$("textarea[name='BUSSINESS_SCOPE']").val(selectClassify.chooseClassify+"(依法须经批准的项目，经相关部门批准后方可展开经营活动)");
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
/**==========企业基本信息页面js结束=======================================*/

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
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
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