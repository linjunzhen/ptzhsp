var FlowUtil = new Object({	
	
	/**
	 * 初始化流程提交对象
	 * @param {} eflowObj
	 */
	initFlowSubmitObj:function(eflowObj){
		return eflowObj;
	},
	/**
	 * 提交流程申请数据
	 * @param {} flowSubmitObj
	 */
	submitFlowInfo:function(flowSubmitObj){
		flowSubmitObj.flashvars = "";
		flowSubmitObj.movie = "";
		flowSubmitObj.applyMatersJson = "";
		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "executionController.do?exeFlow",
			params : postParam,
			callback : function(resultJson) {
				if(resultJson.OPER_SUCCESS){
					if(resultJson.EFLOW_CALLBACKFN){
						var flowVarsJson = JSON2.stringify(resultJson);
						var fnName = resultJson.EFLOW_CALLBACKFN;
						$("#EFLOWFORM_IFRAME")[0].contentWindow.eval(fnName+"('"+flowVarsJson+"')");
					}else{
						art.dialog({
							content : resultJson.OPER_MSG,
							icon : "succeed",
							//time : 3,
							ok: function(){
								opener.$("#MyApplyExecGrid").datagrid('reload');
								if(opener.$("#callingGrid").length>0){
									opener.$("#callingGrid").datagrid('reload');
									opener.parent.reloadTabGrid("已受理记录","accept");
								}
								if(top.opener.$("#callingNewGrid").length>0){
									top.opener.afterRefresh();
								}
								if(top.opener.$("#QueueItemDetailGrid").length>0){
									top.opener.afterDeal();
								}
								window.opener = null;   
								window.open('','_self'); 
								window.close(); 
							}
						});
					}
				}else{
					art.dialog({
						content : resultJson.OPER_MSG,
						icon : "error",
						ok : true
					});
				}
			}
		});
	},
	/**
	 * 流程的退回操作
	 */
	FLOW_BackFun:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		flowSubmitObj.EFLOW_ISBACK = "true";
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goBackFlow", {
					title : "退回流程",
					width : "600px",
					height : "300px",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},
	/**
	 * 查看汇总意见操作函数
	 */
	FLOW_ViewSumOpinionFun:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_ViewSumOpinionFun(flowSubmitObj);
		if(flowSubmitObj!=null){
//			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.dialog.open("executionController.do?viewSumOpinion&exeId="+flowSubmitObj.EFLOW_EXEID+"&currentTaskId="+flowSubmitObj.EFLOW_CURRENTTASK_ID, {
				title : "汇总意见查看",
				width : "900px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		}
	},
	/**
	 * 流程的退回补件操作
	 */
	FLOW_BackBjFun:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		flowSubmitObj.EFLOW_ISBACK = "true";
		flowSubmitObj.EFLOW_ISBACK_PATCH = "true";
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goBackBjFlow", {
					title : "退回补件流程",
					width : "1000px",
					height : "80%",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},
	/**
	 * 流程的打印操作
	 */
	FLOW_PrintTemplate:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			art.dialog.data("flowSubmitInfo", {
				flowSubmitInfoJson:flowSubmitInfoJson
			});// 存储
			$.dialog.open("executionController.do?goPrintTemplateFlow", {
				title : "打印模版",
				width : "600px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		}
	},
	
	/**
	 * 打印窗口一次性告知单
	 */
	FLOW_PrintYcxgzd:function(){
		var SQRMC = $(window.frames["EFLOWFORM_IFRAME"].document).find("input[name='SQRMC']").val();//申请人
		if(SQRMC == null || SQRMC ==undefined || SQRMC ==""){
			art.dialog({
				content : "申请人不能为空！",
				icon : "warning",
				ok : true
			});
			return;
		}else{
			//获取流程信息对象JSON
			var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
			var itemCode = $("#ITEM_CODE").val();//事项编码
			var lineId = $("#LineId").val();//叫号记录ID
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			//定义流程提交对象信息
			var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
			//调用动态表单的函数
			flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
			if(flowSubmitObj!=null){
				var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
				$.post("executionController.do?storeFlowSubmitInfoJson",{
					flowSubmitInfoJson:flowSubmitInfoJson
				}, function(responseText, status, xhr) {
					// 存储
					$.dialog.open(encodeURI("executionController.do?goPrintYcxgzd&itemCode="+itemCode+"&lineId="+lineId+"&SQRMC="+SQRMC), {
						title : "打印一次性告知单",
						width : "1000px",
						height : "80%",
						lock : true,
						resize : false
					}, false);
				});
			}
		}
	},
	
	
	/**
	*审批事项办理情况
	*/
	listApprovalItemInfo:function (){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		$.dialog.open("shtzController.do?info&projectCode="+eflowObj.busRecord.PROJECTCODE+"&exeid="+eflowObj.EFLOW_EXEID, {
			title : "审批事项办理情况",
			width : "100%",
			height : "540px",
			lock : true,
			resize : false
		}, false); 
	},
	/**
	 * 流程提交操作函数
	 */
	
	FLOW_SubmitFun:function(isPass){
		/*$album1 = $("#EFLOWFORM_IFRAME")[0].contentWindow.document;	
		var creditlevel = $album1.getElementsByName("creditlevel")[0].value;*/
		var creditlevel = $(window.frames["EFLOWFORM_IFRAME"].document).find("input[name='creditlevel']").val();
		if(creditlevel=='D'){
			var obj = art.dialog.confirm("申请人员信用评级为D级（黑名单），是否继续办理业务？", function() {
				//获取流程信息对象JSON
				var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
				//将其转换成JSON对象
				var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
				//定义流程提交对象信息
				var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
				if(isPass){
					flowSubmitObj.EFLOW_IS_PASS = isPass;
				}
				
				flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
				$("#EVEFLOW_TAB").tabs("select",0);
				//调用动态表单的函数
				flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
				
				if(flowSubmitObj!=null){
					flowSubmitObj.flashvars = "";
					flowSubmitObj.movie = "";
					flowSubmitObj.applyMatersJson = "";
					var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
					$.post("executionController.do?storeFlowSubmitInfoJson",{
						flowSubmitInfoJson:flowSubmitInfoJson
					}, function(responseText, status, xhr) {
						// 存储
						$.dialog.open("executionController.do?goSubmitFlow", {
							title : "提交流程",
							width : "1000px",
							height:"80%",
							//height : "480px",
							lock : true,
							resize : false
						}, false);
					});
					
				}
		    }, function() {
		    });
	    }else{
	    	//获取流程信息对象JSON
			var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			//定义流程提交对象信息
			var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
			if(isPass){
				flowSubmitObj.EFLOW_IS_PASS = isPass;
			}
			
			flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
			$("#EVEFLOW_TAB").tabs("select",0);
			//调用动态表单的函数
			flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
			
			if(flowSubmitObj!=null){
				flowSubmitObj.flashvars = "";
				flowSubmitObj.movie = "";
				flowSubmitObj.applyMatersJson = "";
				var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
				$.post("executionController.do?storeFlowSubmitInfoJson",{
					flowSubmitInfoJson:flowSubmitInfoJson
				}, function(responseText, status, xhr) {
					// 存储
					$.dialog.open("executionController.do?goSubmitFlow", {
						title : "提交流程",
						width : "1000px",
						height:"80%",
						//height : "480px",
						lock : true,
						resize : false
					}, false);
				});
				
			}
	    }
		
	},
	/**
	 * 预审通过按钮的实现
	 */
	FLOW_PreAuditPass:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_APPLY_STATUS = "2";
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		
		if(flowSubmitObj!=null){
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			flowSubmitObj.applyMatersJson = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goSubmitFlow", {
					title : "预审通过",
					width : "1000px",
					height:"80%",
					//height : "480px",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},
	/**
	 * 挂起流程任务
	 */
	FLOW_HandUp:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj['IS_HANDUP'] = '1';
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			var exeId=eflowObj.EFLOW_EXEID;
			var flag=false;
			flowSubmitObj.applyMatersJson = "";
			var postParam = $.param(flowSubmitObj);
				parent.$.dialog.open("flowTaskController.do?specialLinkExplain&exeId="+exeId,{
					title : "挂起环节信息",
		            width:"600px",
		            lock: true,
		            resize:false,
		            height:"300px",
		            close: function () {
		    			var linkInfo = art.dialog.data("linkInfo");
		    			if(linkInfo){
		    				var LinkId=linkInfo.linkId;
			    			var link_man_tel=linkInfo.linkmantel;
			    	    	var link_man=linkInfo.linkman;
			    	    	var URL= "flowTaskController.do?handUp&LinkId="+LinkId
			    	    	+"&link_man_tel="+link_man_tel+"&link_man="+link_man;
		    				AppUtil.ajaxProgress({
		    					url :encodeURI(URL),
		    					params : postParam,
		    					callback : function(resultJson) {
		    						if(resultJson.OPER_SUCCESS){
		    							parent.art.dialog({
		    								content : resultJson.OPER_MSG,
		    								icon : "succeed",
		    								cancel:false,
		    								lock: true,
		    								ok: function(){
		    									top.opener.$("#MyApplyExecGrid").datagrid('reload');
		    									top.opener.$("#NeedMeHandleGrid").datagrid('reload');
		    									if(top.opener.$("#callingUndoGrid").length>0){
		    										top.opener.$("#callingUndoGrid").datagrid('reload');
		    										top.opener.parent.reloadTabGrid("已受理记录","accept");
		    									}
												if(top.opener.$("#QueueItemDetailGrid").length>0){
													top.opener.afterDeal();
												}
		    									top.opener.loadByMeHandledTaskDatas();
		    									top.window.opener = null;   
		    									top.window.open('','_self'); 
		    									top.window.close(); 
		    								}
		    							});
		    						} else {
		    							parent.art.dialog({
		    								content : resultJson.OPER_MSG,
		    								icon : "error",
		    								ok : true
		    							});
		    						}
		    					}
		    				});
		    				art.dialog.removeData("linkInfo");
		    			}
		    		}
				},false);
		}
	},
	/**
	 * 办结流程
	 */
	FLOW_HandleOver:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			flowSubmitObj.applyMatersJson = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goHandleOver", {
					title : "办结流程",
					width : "1000px",
					height:"35%",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},
	/**
	 * 
	 * 预审不通过按钮实现
	 */
	FLOW_PreAuditNoPass:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			flowSubmitObj.applyMatersJson = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goPreAuditNoPass", {
					title : "预审不通过",
					width : "1000px",
					height:"80%",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},
	/**
	 * 流程暂存操作函数
	 */
	FLOW_TempSaveFun:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_TempSaveFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			FlowUtil.submitFlowInfo(flowSubmitObj);
		}
	},
	/**
	 * 初始化表单字段值
	 * @param {} fieldValueObj
	 * @param {} elementId
	 */
	initFormFieldValue:function(fieldValueObj,elementId){
		for(var fieldName in fieldValueObj){
			//获取目标控件对象
			var targetFields = $("#"+elementId).find("[name='"+fieldName+"']");
			targetFields.each(function(){
				var targetField = $(this);
				var type = targetField.attr("type");
				var tagName = targetField.get(0).tagName;
				var fieldValue = fieldValueObj[fieldName];
				if(type=="radio"){
					var radioValue = targetField.val();
					if(radioValue==fieldValue){
						$(this).attr("checked","checked");
					}
				}else if(type=="checkbox"){
					var checkBoxValue = targetField.val();
					var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
					if(isChecked){
						$(this).attr("checked","checked");
					}
				}else if(tagName=="SELECT"){
					targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
					.prop("selected", "selected");
				}else{
					targetField.val(fieldValueObj[fieldName]);
				}
		   });
		}
	},
	// 初始化表格
	initTableValue: function(fieldValueObj, elementId) {
		for (var fieldName in fieldValueObj) {
			//获取目标控件对象
			var targetFields = $("#" + elementId).find("[name='" + fieldName + "']");
			targetFields.each(function() {
				var targetField = $(this);
				var type = targetField.attr("type");
				var tagName = targetField.get(0).tagName;
				var fieldValue = fieldValueObj[fieldName];
				if (type == "radio") {
					var radioValue = targetField.val();
					if (radioValue == fieldValue) {
						$(this).attr("checked", "checked");
					}
				} else if (type == "checkbox") {
					var checkBoxValue = targetField.val();
					var isChecked = AppUtil.isContain(fieldValue.split(","), checkBoxValue);
					if (isChecked) {
						$(this).attr("checked", "checked");
					}
				} else {
					targetField.val(fieldValueObj[fieldName]);
				}
			});
		}
	},
	/**
	 * 初始化表单字段权限控制
	 * @param {} currentNodeFieldRights
	 * @param {} elementId
	 */
	initFormFieldRightControl:function(currentNodeFieldRights,elementId){
		for(var index in currentNodeFieldRights){
			var rightConfig = currentNodeFieldRights[index];
			var fieldName = rightConfig.FIELD_NAME;
			var rightFlag = rightConfig.RIGHTFLAG;
			//获取目标控件对象
			var targetField = $("#"+elementId).find("[name='"+fieldName+"']")[0];
			//获取字段的标签值
			var tagName = "";
			if(targetField&&targetField.tagName){
				tagName = targetField.tagName;
			}
			var type = targetField?targetField.type:"";
			var classValue = targetField?targetField.getAttribute("class"):"";
			//如果是只读权限
			if(rightFlag==2){
				if(tagName=="SELECT"){
					targetField.setAttribute("disabled","disabled");
					/*var selectField = $("#"+elementId).find("[name='"+fieldName+"']")[0];
					selectField.onbeforeactivate = function(){return false;};  
					selectField.onfocus = function(){selectField.blur();};  
			        selectField.onmouseover = function(){selectField.setCapture();};  
			        selectField.onmouseout = function(){selectField.releaseCapture();};*/
				}else if(tagName=="INPUT"&&type=="text"){
					targetField.setAttribute("disabled","disabled");
				}else if(type=="radio"){
					var radios = $("#"+elementId).find("[name='"+fieldName+"']");
					radios.each(function(){
						$(this).attr("disabled","disabled");
				    });
				}else if(type=="checkbox"){
					var checkboxes = $("#"+elementId).find("[name='"+fieldName+"']");
					checkboxes.each(function(){
						$(this).attr("disabled","disabled");
				    });
				}else if(type=="button"){
					var buttons = $("#"+elementId).find("[name='"+fieldName+"']");
					buttons.each(function(){
						$(this).attr("disabled","disabled");
				    });
				}else if(tagName=="TEXTAREA"){
					targetField.setAttribute("readonly","readonly");
				}
			}else if(rightFlag==3){
				if(type=="radio"||type=="checkbox"||type=="button"){
					var controls = $("#"+elementId).find("[name='"+fieldName+"']");
					controls.each(function(){
						$(this).attr("style","display:none;");
				    });
				}else{
					targetField.setAttribute("style","display:none;");
				}
			}
			
		}
	},
	/**
	 * 获取流程信息对象
	 */
	getFlowObj:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = parent.$("#EFLOW_FLOWOBJ").val();
		if(EFLOW_FLOWOBJ){
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			return eflowObj;
		}else{
			return null;
		}
	},
	/**
	 * 获取checkBox勾选的自定义标签值
	 * @param {} checkBoxName
	 * @param {} tagName
	 * @return {}
	 */
	getCheckBoxTagValues:function(checkBoxName,tagName){
		var checkValues = "";
    	$("input[name='"+checkBoxName+"'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  checkValues+=($(this).attr(tagName)+",");
	          }
	    });
	    if(checkValues!=""){
	    	checkValues = checkValues.substring(0,checkValues.lastIndexOf(","));
	    }
	    return checkValues;
	},
	 /**
     * 根据名称获取checkbox勾选的值 
     */
    getCheckBoxValues:function(checkBoxName){
    	var checkValues = "";
    	$("input[name='"+checkBoxName+"'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  checkValues+=($(this).val()+",");
	          }
	    });
	    if(checkValues!=""){
	    	checkValues = checkValues.substring(0,checkValues.lastIndexOf(","));
	    }
	    return checkValues;
    },
	/**
	 * 根据传入的元素id获取子孙节点元素值
	 * @param {} elementId
	 */
	getFormEleData:function(elementId){
		var formData = {};
	    $("#"+elementId).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  	  	 formData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  formData[inputName] = inputValues;
			  }else{
			  	  formData[inputName] = inputValue;
			  }
	          
	    });
	    return formData;
	},
	/**
	 * 格式化流程状态
	 * @param {} val
	 * @param {} row
	 */
	formatRunStatus:function(val,row){
		if(val=="0"){
			return "<font color='#ff4b4b'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='#0368ff'><b>正在办理</b></font>";
		}else if(val=="2"){
			return "<font ><b>已办结(正常结束)</b></font>";
		}else if(val=="3"){
			return "<font ><b>已办结(审核通过)</b></font>";
		}else if(val=="4"){
			return "<font ><b>已办结(审核不通过)</b></font>";
		}else if(val=="5"){
			return "<font color='#ff4b4b'><b>已办结(退回)</b></font>";
		}else if(val=="6"){
			return "<font color='black'><b>强制结束</b></font>";
		}else if(val=="7"){
			return "<font color='#ff4b4b'><b>预审不通过</b></font>";
		}
	},
	/**
	 * 
	 * 退件按钮实现
	 */
	FLOW_NotAccept:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		if (!(flowSubmitObj.EFLOW_BUSTABLENAME == "T_BDC_DYQZXDJ" || flowSubmitObj.EFLOW_BUSTABLENAME == "T_BDC_DYQSCDJ")) {
			flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
		}
		// flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			flowSubmitObj.applyMatersJson = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goNotAccept", {
					title : "退件",
					width : "1000px",
					height:"80%",
					lock : true,
					resize : false
				}, false);
			});

		}
	},
	/**
	 * 受理按钮的实现
	 */
	FLOW_AcceptPass:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_APPLY_STATUS = "8";
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		$("#EVEFLOW_TAB").tabs("select",0);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
		
		if(flowSubmitObj!=null){
			flowSubmitObj.flashvars = "";
			flowSubmitObj.movie = "";
			flowSubmitObj.applyMatersJson = "";
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goSubmitFlow", {
					title : "受理",
					width : "1000px",
					height:"80%",
					//height : "480px",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},
	/**
	 * 流程的预审补件操作
	 */
	FLOW_PreAudtiBjFun:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_APPLY_STATUS = "4";
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		flowSubmitObj.EFLOW_ISBACK = "true";
		flowSubmitObj.EFLOW_ISBACK_PATCH = "true";
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				flowSubmitInfoJson:flowSubmitInfoJson
			}, function(responseText, status, xhr) {
				// 存储
				$.dialog.open("executionController.do?goBackBjFlow", {
					title : "预审补件流程",
					width : "900px",
					height : "400px",
					lock : true,
					resize : false
				}, false);
			});
			
		}
	},

	/**
	 * 商事流程的二维码打印操作
	 */
	FLOW_PrintQRcode:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		//调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_BackFun(flowSubmitObj);
		if(flowSubmitObj!=null){
			var flowSubmitInfoJson = JSON2.stringify(flowSubmitObj);
			art.dialog.data("flowSubmitInfo", {
				flowSubmitInfoJson:flowSubmitInfoJson
			});// 存储
			$.dialog.open("executionController.do?goPrintQRcodeFlow", {
				title : "二维码打印",
				width : "600px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		}
	},
	
	FLOW_ViewFieldAudit:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		$.dialog.open("commercialController.do?filedProblemView&exeId="+eflowObj.EFLOW_EXEID, {
			title : "表单问题",
			width : "100%",
			height : "540px",
			lock : true,
			resize : false
		}, false); 
	},
	
	/**
	 * 老年人优待证打印按钮
	 */
	FLOW_PrintOldAgeCard: function(){
		$("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_PrintOldAgeCard();
	},
	/**
	 * 产业奖补打印审批表
	 */
	FLOW_PrintCyjbFun:function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		$("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_PrintCyjbFun(flowSubmitObj);
	},
	/**
	 * 申请单打印
	 */
	FLOW_PrintSubForm : function() {
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		// 换发不动产权证书和不动产登记证明流程的子项类型：换发抵押权登记证明、换发抵押权预告登记证明 采用抵押型问卷模板
		// 获取页面实时变化的子类型数据
		var hfType = eflowObj.BDC_DYLX;
		if (eflowObj.EFLOW_EXEID == undefined || eflowObj.EFLOW_EXEID == '') {
			//alert("打印前请先暂存！");
			art.dialog({
				content : "打印前请先暂存",
				icon : "warning",
				ok : true
			});
			return;
		}
		// 打印前将做数据暂存操作，实现数据与签字文件数据同步
		// 获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		// 将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		// 定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
		$("#EVEFLOW_TAB").tabs("select", 0);
		// 调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_TempSaveFun(flowSubmitObj);
		if (flowSubmitObj != null) {
			FlowUtil.submitPrintBeforeFlowInfo(flowSubmitObj, hfType, eflowObj);
		}
	},
	/**
	 * 申请单打印前数据暂存操作
	 */
	submitPrintBeforeFlowInfo : function(flowSubmitObj, hfType, eflowObj) {
		flowSubmitObj.flashvars = "";
		flowSubmitObj.movie = "";
		flowSubmitObj.applyMatersJson = "";
		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "executionController.do?exeFlow",
			params : postParam,
			callback : function(resultJson) {
				if (resultJson.OPER_SUCCESS) {
					AppUtil.ajaxProgress({
						url : "printAttachController.do?getPrintTemplate&deftId=" + eflowObj.EFLOW_DEFID + "&BUS_RECORDID=" + eflowObj.busRecord.YW_ID,
						params : {},
						callback : function(resultJson) {
							if(resultJson.jsonString == "") {
								art.dialog({
									content : "未配置事项对应的申请书模板，请联系管理员！",
									icon : "error",
									ok : true
								});
								return ;
							}
							// 默认通用型模板
							if ( (resultJson != undefined && "20190402155521.doc" == resultJson.jsonString) ) {
								// 启动表单交互，发送问卷到终端设备
								DoGWQ_StartFormInfo("bdc_ebuy_form.html", 60);
							} else {
								DoGWQ_StartFormInfo("bdc_normal_form.html", 60);
							}
						}
					});
				} else {
					art.dialog({
						content : resultJson.OPER_MSG,
						icon : "error",
						ok : true
					});
				}
			}
		});
	},
	/**
	 * 纸质申请单打印，不通过pad
	 */
	FLOW_PrintSubFormRe : function() {
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		// 换发不动产权证书和不动产登记证明流程的子项类型：换发抵押权登记证明、换发抵押权预告登记证明 采用抵押型问卷模板
		// 获取页面实时变化的子类型数据
		var hfType = eflowObj.BDC_DYLX;
		if (eflowObj.EFLOW_EXEID == undefined || eflowObj.EFLOW_EXEID == '') {
			//alert("打印前请先暂存！");
			art.dialog({
				content : "打印前请先暂存",
				icon : "warning",
				ok : true
			});
			return;
		}
		// 打印前将做数据暂存操作，实现数据与签字文件数据同步
		// 获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		// 将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		// 定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
		$("#EVEFLOW_TAB").tabs("select", 0);
		// 调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_TempSaveFun(flowSubmitObj);
		if (flowSubmitObj != null) {
			FlowUtil.submitPrintReBeforeFlowInfo(flowSubmitObj, hfType, eflowObj);
		}
	},
	/**
	 * 纸质申请单打印前数据暂存操作
	 */
	submitPrintReBeforeFlowInfo : function(flowSubmitObj, hfType, eflowObj) {
		flowSubmitObj.flashvars = "";
		flowSubmitObj.movie = "";
		flowSubmitObj.applyMatersJson = "";
		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "executionController.do?exeFlow",
			params : postParam,
			callback : function(resultJson) {
				if (resultJson.OPER_SUCCESS) {
					
					AppUtil.ajaxProgress({
						url : "printAttachController.do?getPrintTemplate&deftId=" + eflowObj.EFLOW_DEFID + "&BUS_RECORDID=" + eflowObj.busRecord.YW_ID,
						params : {},
						callback : function(resultJson) {
							if(resultJson.jsonString == "") {
								art.dialog({
									content : "未配置事项对应的申请书模板，请联系管理员！",
									icon : "error",
									ok : true
								});
								return ;
							}
							$.dialog.open("printAttachController.do?createSubmitFormFile&&EXE_ID=" + eflowObj.EFLOW_EXEID, {
								title : "打印模版",
								width : "100%",
								height : "100%",
								left : "0%",
								top : "0%"
							}, false);
						}
					});
				} else {
					art.dialog({
						content : resultJson.OPER_MSG,
						icon : "error",
						ok : true
					});
				}
			}
		});
	},

	/*
	* 不动产全流程申请书打印
	* */
	EFLOW_PrintSubFormBdcqlc : function(){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		if (eflowObj.EFLOW_EXEID == undefined || eflowObj.EFLOW_EXEID == '') {
			//alert("打印前请先暂存！");
			art.dialog({
				content : "打印前请先暂存",
				icon : "warning",
				ok : true
			});
			return;
		}
		// 打印前将做数据暂存操作，实现数据与签字文件数据同步
		// 获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		// 将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		// 定义流程提交对象信息
		var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
		flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
		$("#EVEFLOW_TAB").tabs("select", 0);
		// 调用动态表单的函数
		flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_TempSaveFun(flowSubmitObj);
		if (flowSubmitObj != null) {
			FlowUtil.submitPrintBdcqlcBeforeFlowInfo(flowSubmitObj, eflowObj);
		}
	},

	/*
	* 不动产全流程申请书打印前数据暂存操作
	* */
	submitPrintBdcqlcBeforeFlowInfo: function (flowSubmitObj, eflowObj) {
		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "executionController.do?exeFlow",
			params : postParam,
			callback: function (resultJson) {
				if (resultJson.OPER_SUCCESS) {
					AppUtil.ajaxProgress({
						url : "bdcSpbPrintConfigController/getTemplateName.do",
						params : {
							itemCode:flowSubmitObj.ITEM_CODE,
							tableName:flowSubmitObj.EFLOW_BUSTABLENAME
						},
						callback : function (resultJson) {
							if (resultJson.success) {
								var templateName = resultJson.templateName;
								var dataStr = "&EFLOW_EXEID="+flowSubmitObj.EFLOW_EXEID;
								/*tableType不为null或为1，代表是申请表*/
								dataStr += "&typeId=3&tableType=1&TemplateName=" + templateName;
								var url = encodeURI("printAttachController.do?printTemplate"+dataStr);
								$.dialog.open(url, {
									title : "打印模版",
									width: "100%",
									height: "100%",
									left: "0%",
									top: "0%",
									fixed: true,
									lock : true,
									resize : false
								}, false);
							}
						}
					});

				}
			}
		});
	},

	/**
	 *	工程建设项目流程撤回
	 */
	FLOW_Recall:function (){
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		$.dialog.open("executionController.do?recall&projectCode="+eflowObj.busRecord.PROJECTCODE
				+"&exeid="+eflowObj.EFLOW_EXEID+"&itemCode="+eflowObj.busRecord.ITEM_CODE, {
			title : "流程撤回",
			width : "700px",
			height : "350px",
			lock : true,
			resize : false
		}, false); 
	}
});