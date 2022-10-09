var AppUtil = new Object({
	/**
	 * 初始化开始时间和结束时间
	 */
	initBeginAndEndTimeLayDate:function(beginTimeId,endTimeId,format){
		var start1 = {
		    elem: "#"+beginTimeId,
		    format: format,
		    istime: true,
		    choose: function(datas){
		    	end1.min = datas; 
		        end1.start = datas;
		    }
		};
		var end1 = {
		    elem: "#"+endTimeId,
		    format: format,
		    istime: true,
		    choose: function(datas){
		    	start1.max = datas;
		    }
		};
		end1.min = $("#"+beginTimeId).val(); 
	    end1.start = $("#"+beginTimeId).val();
	    start1.max = $("#"+endTimeId).val();
		laydate(start1);
		laydate(end1);
	},
	/**
	 * 首字母大写
	 */
	firstLetterUpperCase:function(str){
		return str.substring(0,1).toUpperCase()+str.substring(1);   
	},
	/**
	 * 首字母小写
	 */
	firstLetterLowerCase:function(str){
		return str.substring(0,1).toLowerCase()+str.substring(1);   
	},
	/**
	 * 设置下拉数的值
	 */
	setComboTreeValue:function(comboTreeName,isCheck,treeObj){
		if(!isCheck){
			var nodes = treeObj.getSelectedNodes();
			$("#"+comboTreeName+"_TREETEXT").attr("value",nodes[0].name);
			$("input[name='"+comboTreeName+"']").attr("value",nodes[0].id);
		}else{
			var nodes = treeObj.getCheckedNodes(true);
			var nodeNames = "";
			var nodeIds = "";
			for(var i = 0;i<nodes.length;i++){
				var node = nodes[i];
				if(i>0){
					nodeNames+=",";
					nodeIds+=",";
				}
				nodeNames+=node.name;
				nodeIds+=node.id;
			}
			$("#"+comboTreeName+"_TREETEXT").attr("value",nodeNames);
			$("input[name='"+comboTreeName+"']").attr("value",nodeIds);
		}
		
	},
	/**
	 * 隐藏下拉树
	 */
	hideComboTree:function(comboTreeName){
		$("#"+comboTreeName+"comboTreeMenuContent").fadeOut("fast");
		$("body").unbind("mousedown", function(){
			var contentId = comboTreeName+"comboTreeMenuContent";
			var comboTreeMenuContent = comboTreeName+"comboTreeMenuContent";
			if (!(event.target.id == contentId || $(event.target).parents("#"+comboTreeMenuContent).length>0)) {
				AppUtil.hideComboTree(comboTreeName);
			}
		});
	},
	/**
	 * 显示下拉树
	 */
	showComboTree:function(comboTreeName){
		var treetextObj = $("#"+comboTreeName+"_TREETEXT");
		var treeOffset = $("#"+comboTreeName+"_TREETEXT").offset();
		$("#"+comboTreeName+"comboTreeMenuContent").css({left:treeOffset.left + "px", 
			top:treeOffset.top + treetextObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown",function(event){
			var contentId = comboTreeName+"comboTreeMenuContent";
			var comboTreeMenuContent = comboTreeName+"comboTreeMenuContent";
			if (!(event.target.id == contentId || $(event.target).parents("#"+comboTreeMenuContent).length>0)) {
				AppUtil.hideComboTree(comboTreeName)
			}
		});
	},
	/**
	 * 需要隐藏的控件,其它没有包含全部显示
	 */
	setHideControls:function(hideNames){
		var namesArray = showNames.split(",");
		var targetFields = $("#FormControlForm").find("[name]");
		targetFields.each(function(){
			var fieldName = $(this).attr("name");
			var isContain = AppUtil.isContain(namesArray,fieldName);
			if(!isContain){
				$("[name='"+fieldName+"']").css("display","");
			}else{
				$("[name='"+fieldName+"']").css("display","none");
			}
		});
	},
	/**
	 * 需要展现的控件,其它没有包含在Name全部隐藏
	 */
	setShowControls:function(showNames){
		var namesArray = showNames.split(",");
		var targetFields = $("#FormControlForm").find("[name]");
		targetFields.each(function(){
			var fieldName = $(this).attr("name");
			var isContain = AppUtil.isContain(namesArray,fieldName);
			if(!isContain){
				$("[name='"+fieldName+"']").css("display","none");
			}else{
				$("[name='"+fieldName+"']").css("display","");
			}
		});
	},
	/**
	 * 编辑食品业务办件方法
	 */
	editFoodBusHandleInfo:function(gridId){
		var row = AppUtil.getEditDataGridRow(gridId);
		if (row) {
			//获取流程状态
			var runStatus = row.RUN_STATUS;
			//获取流程申报号
			var exeId = row.EXE_ID;
			var subject = row.SUBJECT;
			AppUtil.ajaxProgress({
				url : "foodManagementController.do?isCanModify",
				params : {
					exeId:exeId
				},
				callback : function(resultJson) {
					if(resultJson.success){
						FlowUtil.showExecutionWindow("",exeId,"true",subject,"","true");
					}else{
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
			
		}
	},
	/**
	 * 清空某个dom下孩子元素的所有值
	 */
	clearDomValue:function(parentDomId){
		var targetFields = $("#"+parentDomId).find("[name]");
		targetFields.each(function(){
			var targetField = $(this);
			targetField.val("");
		});
	},
	/**
	 * 添加全部的用户到可选列表
	 */
	allAllUserToSelected:function(){
		var item = $("#SysUserGrid").datagrid('getRows'); 
    	if (item) { 
	    	for (var i = item.length - 1; i >= 0; i--) { 
	    		var index = $("#SelectedSysUserGrid").datagrid('getRowIndex', item[i].USER_ID); 
		    	if(index==-1){
		    		$("#SelectedSysUserGrid").datagrid('appendRow', item[i]); 
		    	}
	    	} 
    	} 
	},
	/**
	 * 移除datagrid的所有行
	 */
	removeDataGridAllRows:function(gridId){
		var item = $("#"+gridId).datagrid('getRows'); 
    	if (item) { 
	    	for (var i = item.length - 1; i >= 0; i--) { 
		    	var index = $("#"+gridId).datagrid('getRowIndex', item[i]); 
		    	$("#"+gridId).datagrid('deleteRow', index); 
	    	} 
    	} 
	},
	/***
	 * 查询单位树
	 */
	searchDepartTree:function(treeId,searchConditionId){
		var  DEPART_NAME = $("#"+searchConditionId).val();
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        zTree.setting.async.url = "departmentController.do?tree";
        zTree.setting.async.otherParam = {
            "SEARCH_DEP_NAME" : DEPART_NAME
        }
        zTree.reAsyncChildNodes(null, "refresh");
	},
	/**
	 * 验证单个表单
	 * @param {} formId
	 */
	validateSingleForm:function(formId){
		//$("#"+formId).validationEngine("validate");
		//$("#"+formId).validationEngine('updatePromptsPosition');
		AppUtil.validateWebsiteTabForm();
		$("#"+formId).validationEngine("hideAll");
	},
	/**
	 * 初始化缺省验证配置信息
	 */
	initDefaultValidateConfig:function(config){
		//初始化验证引擎的配置
		$.validationEngine.defaults.scroll = false;
		var forms = $("form[id]");
		forms.each(function(){
			var formId = $(this).attr("id");
			$("#"+formId).validationEngine("attach",{ 
			     promptPosition:"topLeft",
			     autoPositionUpdate:true,
			     validateNonVisibleFields:true,
		         autoHidePrompt: false,           
		         autoHideDelay: "10000",       
		         fadeDuration: "0.2",   
		         maxErrorsPerField: "1",  
		         showOneMessage: false
		         //onValidationComplete:config.onValidationComplete
		     });
	    });
		
	},
	/**
	 * 对url地址进行转义
	 * @param {} url
	 */
	unescapeWebUrl:function(url){
		var reg = new RegExp("(^|&)" + url + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null){
			return unescape(r[2]);
		}
		return null;
	},
	/**
	 * 验证多标签FORM
	 * 返回true,代表验证通过
	 */
	validateTabForm:function(isTemplateSave){
		var forms = $("form[id]");
		var validate = true;
		if(isTemplateSave){
			forms.each(function(){
				var formId = $(this).attr("id");
				var targetFields = $("#"+formId).find("[name][class*=validate]");
				targetFields.each(function(){
					var classValue = $(this).attr("class");
					var newClass = classValue.replace("required","");
					$(this).attr("class",newClass);
			    });
		    });
		}
		forms.each(function(){
			var formId = $(this).attr("id");
			var validateResult =$("#"+formId).validationEngine("validate");
			if(!validateResult){
				validate = false;
				$("#"+formId+"_A").attr("class","eui-bg3");
			}else{
				$("#"+formId+"_A").attr("class","eui-bg4");
			}
	    });
	    if(!validate){
	    	/*parent.art.dialog({
				content : "提交失败,表单信息输入格式有误!",
				icon : "error",
				ok : true
			});*/
	    }
	    return validate;
	},
	/**
	 * 验证多标签FORM
	 * 返回true,代表验证通过
	 */
	validateWebsiteTabForm:function(isTemplateSave){
		var forms = $("form[id]");
		var validate = true;
		if(isTemplateSave){
			forms.each(function(){
				var formId = $(this).attr("id");
				var targetFields = $("#"+formId).find("[name][class*=validate]");
				targetFields.each(function(){
					var classValue = $(this).attr("class");
					var newClass = classValue.replace("required","");
					$(this).attr("class",newClass);
			    });
		    });
		}
		forms.each(function(){
			var formId = $(this).attr("id");
			if(formId=="APPLYITEMMATER_FORM"||formId=="SGCX_FILE_FORM"){
				var flag = true;
				var haveOnLine = $("input[name='haveOnline']").val();
				if(null != haveOnLine && haveOnLine.length>0){					
					haveOnLine = haveOnLine.substring(0,haveOnLine.length-1);
					var haveOnLines = haveOnLine.split(",");
					if(haveOnLines.length>0){
						for(var i=0;i<haveOnLines.length;i++){
							var forName = haveOnLines[i];
							if(store[forName]==''||store[forName]==null){
								flag = false;
								break;
							}
						}
					}
				}else{
					flag = false;
				}
				if(flag||formId=="SGCX_FILE_FORM"){
					var applyMatersJson = $("input[name='applyItemMatersJson']").val();
					 var applyMatersObj = JSON2.parse(applyMatersJson);
					 //定义是否上传了必须上传的材料
					 var isUploadMustFile = true;
					 for(var index in applyMatersObj){
						//获取材料编码
						var MATER_CODE = applyMatersObj[index].MATER_CODE;
						//获取是否必须上传
						var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
						//获取收取方式
						var SQFS = $("#SQFS_"+MATER_CODE).val();
						//获取是否收取
						var SFSQ = $("#SFSQ_"+MATER_CODE).val();
						if(MATER_ISNEED=="1"&&SQFS){
							if(SQFS=="1"){
								var leftSpanSize = $("#UploadedFiles_"+MATER_CODE).children("p").length;
								if(leftSpanSize==0){
									flag = false;
									break;
								}else{
									flag = true;
								}
							}
						} else{
							flag = true;
						}
					 }
					 if(applyMatersObj.length<=0){
						flag = true;
					 }
				}
				var text = $("#"+formId+"_A").text(); 
				if(!flag){
					$("#"+formId+"_A").closest("li").addClass("eui-no");
					$("#"+formId+"_A").html("<img src=\"webpage/website/zzhy/images/icon2.png\">"+text);
					//return false;
				}else{
					$("#"+formId+"_A").html("<img src=\"webpage/website/zzhy/images/icon1.png\">"+text);
					$("#"+formId+"_A").closest("li").removeClass("eui-no");
				}
			}else{
				var validateResult =$("#"+formId).validationEngine("validate");
				var text = $("#"+formId+"_A").text(); 
				if(!validateResult){
					validate = false;
					$("#"+formId+"_A").closest("li").addClass("eui-no");
					$("#"+formId+"_A").html("<img src=\"webpage/website/zzhy/images/icon2.png\">"+text);
					//return false;
				}else{
					$("#"+formId+"_A").html("<img src=\"webpage/website/zzhy/images/icon1.png\">"+text);
					$("#"+formId+"_A").closest("li").removeClass("eui-no");
				}
				
			}
	    });
		/*if(!validate){
	    	parent.art.dialog({
				content : "提交失败,表单信息输入格式有误!",
				icon : "error",
				ok : true
			});
	    }*/
	    return validate;
	},
	/**
	 * 获取审批材料的文件IDS
	 */
	getAppMaterFileIds:function(){
		var uploadedFilesDivs = $("#UploadFlowFileTable").find("div[id]");
		var uploadedFileIds = "";
		$.each(uploadedFilesDivs,function(index,div) { 
		 	 if(index>0){
				uploadedFileIds+=",";
			 }
			 uploadedFileIds+=$(this).attr("id");
        });  
        return uploadedFileIds;
	},
	/**
	 * 提交网站的流程表单信息
	 * @param {} formId
	 */
	submitWebSiteFlowForm:function(formId){
		//先判断表单是否验证通过
		 var validateResult =$("#"+formId).validationEngine("validate");
		 if(validateResult){
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
			 	 //获取流程信息对象JSON
				 var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
				 //将其转换成JSON对象
				 var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData(formId);
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
				 var postParam = $.param(flowSubmitObj);
				 AppUtil.ajaxProgress({
						url : "webSiteController.do?submitApply",
						params : postParam,
						callback : function(resultJson) {
							if(resultJson.OPER_SUCCESS){
								art.dialog({
									content : resultJson.OPER_MSG,
									icon : "succeed",
									lock : true,
									ok:function(){
										window.top.location.href=__ctxPath+"/webSiteController.do?usercenter";
									},
									close: function(){
										window.top.location.href=__ctxPath+"/webSiteController.do?usercenter";
									}
								});
							}else{
								art.dialog({
									content : resultJson.OPER_MSG,
									icon : "error",
									ok : true
								});
							}
						}
					});
			 }else{
				 return null;
			 }
		 }
	},
	/**
	 * 合并对象
	 * @param {} sourceObj:源对象
	 * @param {} targetObj:目标对象
	 */
	mergeObject:function(sourceObj,targetObj){
    	for(var index in sourceObj){
    		targetObj[eval("index")] = sourceObj[index];
    	}
    	return targetObj;
	},
    /**
     * 设置为必填非必填（1，-1），disabled和非disabled（-2,2）
     * @param {} sourceObj:源对象
     * @param {} targetObj:目标对象
     */
    changeRequireStatus:function(inputNames,status){
        if(inputNames.length>0){
            var inputArr=inputNames.split(";");
            for(var i=0;i<inputArr.length;i++){
                var name=inputArr[i];
                if(status=="1"){
                    $("input[name='"+name+"']").addClass("  validate[required]");
                    $("select[name='"+name+"']").addClass("  validate[required]");
                }else if(status=="-1"){
                    $("input[name='"+name+"']").val("");
                    $("input[name='"+name+"']").removeClass("  validate[required]");
                    $("select[name='"+name+"']").val("");
                    $("select[name='"+name+"']").removeClass("  validate[required]");
                }else if(status=="2"){
                    $("input[name='"+name+"']").attr("disabled",false);
                    $("input[name='"+name+"']").addClass("  validate[required]");
                    $("select[name='"+name+"']").attr("disabled",false);
                    $("select[name='"+name+"']").addClass("  validate[required]");
                }else if(status=="-2"){
                    $("input[name='"+name+"']").val("");
                    $("input[name='"+name+"']").attr("disabled",true);
                    $("input[name='"+name+"']").removeClass("  validate[required]");
                    $("select[name='"+name+"']").val("");
                    $("select[name='"+name+"']").attr("disabled",true);
                    $("select[name='"+name+"']").removeClass("  validate[required]");
                }
            }
        }
    },
	/**
	 * 获取提交材料的JSON数据
	 */
	getSubmitMaterFileJson:function(EFLOW_ISTEMPSAVE){
		 var applyMatersJson = $("input[name='applyItemMatersJson']").val();
		 var applyMatersObj = JSON2.parse(applyMatersJson);
		 //定义是否上传了必须上传的材料
		 var isUploadMustFile = true;
		 
		 for(var index in applyMatersObj){
			//获取材料编码
			var MATER_CODE = applyMatersObj[index].MATER_CODE;
			//获取材料名称
			var MATER_NAME = applyMatersObj[index].MATER_NAME;
			//获取是否必须上传
			var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
			//获取收取方式
			var SQFS = $("#SQFS_"+MATER_CODE).val();
			//获取是否收取
			var SFSQ = $("#SFSQ_"+MATER_CODE).val();
			if(MATER_ISNEED=="1"&&SQFS){
				if(SQFS=="2"||SQFS=="3"){
					if(SFSQ=="-1"){
						isUploadMustFile = false;
						break;
					}
				}else{
					var leftSpanSize = $("#UploadedFiles_"+MATER_CODE).children("p").length;
					if(leftSpanSize==0){
						isUploadMustFile = false;
						break;
					}
				}
			}
		 }
		 
		 if(!isUploadMustFile && EFLOW_ISTEMPSAVE!=1){			 
			 alert("带*号的必填项目必须上传的材料，请检查!");
			 return null;
		 }
		 var submitMaterList = [];
		 for(var index in applyMatersObj){
			//获取材料编码
			var MATER_CODE = applyMatersObj[index].MATER_CODE;
			//获取是否必须上传
			var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
			//获取收取方式
			var SQFS = $("#SQFS_"+MATER_CODE).val();
			//获取是否收取
			var SFSQ = $("#SFSQ_"+MATER_CODE).val();
			//如果是上传方式
			if(SQFS=="1"){
				 var uploadedFilesSpans = $("div[id^='UploadedFiles_"+MATER_CODE+"'] > p[id]");
				 var uploadedFileIds = "";
				 $.each(uploadedFilesSpans,function(index,span) { 
				 	 var submitMater = {};
				 	 submitMater.ATTACH_KEY = MATER_CODE;
				 	 submitMater.SQFS = "1";
				 	 submitMater.SFSQ = "1";
				 	 submitMater.FILE_ID = $(this).attr("id");
				 	 submitMaterList.push(submitMater);
		         }); 
			}else if(SQFS=="2"){
				var submitMater = {};
		 	    submitMater.ATTACH_KEY = MATER_CODE;
		 	    submitMater.SQFS = "2";
		 	    if(SFSQ=="1"){
		 	    	submitMater.SFSQ = "1";
		 	    }else{
		 	    	submitMater.SFSQ = "2";
		 	    }
		 	    submitMater.FILE_ID = "";
		 	    submitMaterList.push(submitMater);
			}else if(SQFS=="3"){
				var submitMater = {};
		 	    submitMater.ATTACH_KEY = MATER_CODE;
		 	    submitMater.SQFS = "3";
		 	    /*if(SFSQ=="1"){
		 	    	submitMater.SFSQ = "1";
		 	    }else{
		 	    	submitMater.SFSQ = "2";
		 	    }*/
		 	    submitMater.SFSQ = "1";
		 	    submitMater.FILE_ID = "";
		 	    submitMaterList.push(submitMater);
			}
			
		 }
		 return JSON2.stringify(submitMaterList);
	},
	/**
	 * 获取窗口提交材料的JSON数据
	 */
	getWindowSubmitMaterFileJson:function(isTempSave){
		 var result = {};
		 var applyMatersJson = $("input[name='applyItemMatersJson']").val();
		 var applyMatersObj = $.parseJSON(applyMatersJson);
		 //定义是否上传了必须上传的材料
		 var isUploadMustFile = true;
		 for(var index in applyMatersObj){
			//获取材料编码
			var MATER_CODE = applyMatersObj[index].MATER_CODE;
			//获取是否必须上传
			var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
			//获取收取方式
			var RECEIVE_METHOD = $("#SFSQ_"+MATER_CODE).val();
			if(!RECEIVE_METHOD){
				RECEIVE_METHOD = "1";
			}
			if(MATER_ISNEED=="1"){
				if(RECEIVE_METHOD=="3"){
					isUploadMustFile = false;
					break;
				}else if(RECEIVE_METHOD=="1"){
					var leftSpanSize = $("#UploadedFiles_"+MATER_CODE).children("p").length;
					if(leftSpanSize==0){
						isUploadMustFile = false;
						break;
					}
				}
			}
		 }
		 if(!isUploadMustFile&&isTempSave!=true){
			 //alert("请上传必须上传的材料!");
			 alert("带*号的必填项目必须上传的材料，请检查!");
			 result.validate = false;
			 //$("#APPLY_MATER_FORM_A").html("<img src=\"webpage/website/index/images/icon2.png\">"+text);
			 return result;
		 }else{
		 	//$("#APPLY_MATER_FORM_A").html("<img src=\"webpage/website/index/images/icon1.png\">"+text);
		 }
		 var submitMaterList = [];
		 for(var index in applyMatersObj){
			//获取材料编码
			var MATER_CODE = applyMatersObj[index].MATER_CODE;
			//获取是否必须上传
			var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
			//获取收取方式
			var RECEIVE_METHOD = $("#SFSQ_"+MATER_CODE).val();
			if(!RECEIVE_METHOD){
				RECEIVE_METHOD = "1";
			}
			//如果是上传方式
			if(RECEIVE_METHOD=="1"){
				 var uploadedFilesSpans = $("div[id^='UploadedFiles_"+MATER_CODE+"'] > p[id]");
				 var uploadedFileIds = "";
				 if(uploadedFilesSpans.length>0){
					 $.each(uploadedFilesSpans,function(index,span) { 
					 	 var submitMater = {};
					 	 submitMater.ATTACH_KEY = MATER_CODE;
					 	 submitMater.RECEIVE_METHOD = "1";
					 	 submitMater.FILE_ID = $(this).attr("id");
					 	 submitMaterList.push(submitMater);
			         }); 
				 }else{
					 var submitMater = {};
				 	 submitMater.ATTACH_KEY = MATER_CODE;
				 	 submitMater.RECEIVE_METHOD = "3";
				 	 submitMater.FILE_ID = "";
				 	 submitMaterList.push(submitMater);
				 }
				
			}else if(RECEIVE_METHOD=="2"){
				var submitMater = {};
		 	    submitMater.ATTACH_KEY = MATER_CODE;
		 	    submitMater.RECEIVE_METHOD = "2";
		 	    submitMater.FILE_ID = "";
		 	    submitMaterList.push(submitMater);
			}else if(RECEIVE_METHOD=="3"){
				var submitMater = {};
		 	    submitMater.ATTACH_KEY = MATER_CODE;
		 	    submitMater.RECEIVE_METHOD = "3";
		 	    submitMater.FILE_ID = "";
		 	    submitMaterList.push(submitMater);
			}
			
		 }
		 var submitMaterFileJson = JSON.stringify(submitMaterList);
		 result.validate = true;
		 result.submitMaterFileJson =submitMaterFileJson;
		 return result;
	},
	/**
	 * 标题附件上传对话框
	 */
	openTitleFileUploadDialog:function (materType,attachKey){
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType='
		+materType+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName)+'&busTableName='+EFLOW_BUSTABLENAME, {
			title: "上传(附件)",
			width: "650px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						var fileType = 'gif,jpg,jpeg,bmp,png';
						var attachType = 'attach';
						if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
							attachType = "image";
						}		
						
						
						var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a style=\"cursor: pointer;color: blue;margin-right: 5px;\"";
						spanHtml+=(" href=\"" + __file_server 
						+ "download/fileDownload?attachId=" +uploadedFileInfo.fileId
						+ "&attachType="+attachType+"\" target=\"_blank\" ");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>";
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delNewUploadMater('"+uploadedFileInfo.fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>";
						$("#UploadedFiles_"+attachKey).append(spanHtml);
						setWordHidden(attachKey);//隐藏在线编辑按钮
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	},
	/**
	 * 删除已上传的材料文件
	 * @param {} fileId
	 * @param {} attachKey
	 */
	delNewUploadMater:function(fileId,attachKey,uploadFileTdId){
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			//移除目标span
			$("#"+fileId).remove();
			//获取剩余孩子元素
			var leftSpanSize = 0;
			if(uploadFileTdId){
				leftSpanSize = $("#"+uploadFileTdId).children("div").length;
			}else{
				leftSpanSize = $("#UploadedFiles_"+attachKey).children("p").length;
			}
			if(!uploadFileTdId&&leftSpanSize==0){
				setWordShow(attachKey);//展现上传和在线编辑按钮
			} 
		});
	},
	/**
	 * 初始化网上申报上传的材料列表
	 * @param {} config
	 */
	initNetUploadMaters:function(config){
		//遍历需要上传的材料
		var applyMatersJson = $("input[name='applyItemMatersJson']").val();
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		var applyMatersObj = JSON.parse(applyMatersJson);
		for(var index in applyMatersObj){
			//获取材料编码
			var MATER_CODE = applyMatersObj[index].MATER_CODE;
			//获取允许上传文件类型
			var MATER_TYPE = applyMatersObj[index].MATER_TYPE;
			//获取允许上传文件大小
			var MATER_SIZE = applyMatersObj[index].MATER_SIZE;
			var UPLOADED_SIZE = applyMatersObj[index].UPLOADED_SIZE;
			var successful_uploads = null;
			if(UPLOADED_SIZE&&UPLOADED_SIZE!=0){
				successful_uploads = UPLOADED_SIZE;
			}
			var swUploadObj = AppUtil.bindSwfUpload({
				file_types : MATER_TYPE,
				file_size_limit :MATER_SIZE+" MB",
				post_params : {
			           "uploadPath":"applyform",
			           "uploadUserId":uploadUserId,
			           "uploadUserName":encodeURI(uploadUserName),
			           "attachKey":MATER_CODE,
			           "busTableName": config.busTableName 
		        },
		        //指定最多能上传多少个文件
	            file_upload_limit :3,
	            button_placeholder_id:MATER_CODE,
	            successful_uploads:successful_uploads,
	            upload_success_handler:function(resultJson){
	            	if(resultJson.success==true){
	            		//获取文件ID
		            	var fileId = resultJson.fileId;
	            		//获取文件名称
	            		var fileName = resultJson.fileName;
	            		//获取材料KEY
	            		var attachKey =resultJson.attachKey;
	            		//获取文件路径
	            		var filePath = resultJson.filePath;
	            		//获取文件的类型
	            		var fileType = resultJson.fileType;
	            		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
	            		spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
	            		spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
	            		spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            		$("#UploadedFiles_"+attachKey).append(spanHtml);
	            	}
	            }
			});
		}
	},
	/**
	 * 删除已上传的材料文件
	 * @param {} fileId
	 * @param {} attachKey
	 */
	delUploadMater:function(fileId,attachKey,uploadFileTdId){
		art.dialog.confirm("您确定要删除该文件吗?", function() {
    		var layload = layer.load("正在提交请求中…");
			$.post("fileAttachController.do?multiDel",{
					selectColNames :fileId
			    }, function(responseText, status, xhr) {
			    	layer.close(layload);
			    	var resultJson = $.parseJSON(responseText);
					if(resultJson.success == true){
						//移除目标span
			    		$("#"+fileId).remove();
			    		//获取剩余孩子元素
			    		var leftSpanSize = 0;
			    		if(uploadFileTdId){
			    			leftSpanSize = $("#"+uploadFileTdId).children("div").length;
			    		}else{
			    			leftSpanSize = $("#UploadedFiles_"+attachKey).children("p").length;
			    		}
			    		$.each(SWFUpload.instances,function(n,swfObject) {   
				   		 	if(swfObject.settings.button_placeholder_id==attachKey){
				   		 		swfObject.setStats({successful_uploads:leftSpanSize});
				   			}
			            }); 
					}else{
						art.dialog({
							content: resultJson.msg,
							icon:"error",
						    ok: true
						});
					}
			});
		});
	},
	/**
	 * 绑定swfupload上传控件
	 * @param {} config
	 */
	bindSwfUpload:function(config){
		var layload = null;
		var settings_object = {//定义参数配置对象
	            upload_url : config.upload_url?config.upload_url:__ctxPath+"/UploadServlet",
	            flash_url : "plug-in/swfupload-2.2/swfupload.swf",
				flash9_url : "plug-in/swfupload-2.2/swfupload_fp9.swf",
				debug : false,
				use_query_string : true,
				//上传额外提交的参数
	            post_params :config.post_params,
	            //允许上传的文件类型
	            file_types : config.file_types,
	            file_types_description: "All Files",
	            file_size_limit : config.file_size_limit,
	            //指定最多能上传多少个文件
	            file_upload_limit :config.file_upload_limit?config.file_upload_limit:1,
	            //指定文件上传队列里最多能同时存放多少个文件
	            file_queue_limit : config.file_queue_limit?config.file_queue_limit:1,
	            button_placeholder_id :config.button_placeholder_id,
	            button_image_url : config.button_image_url?config.button_image_url:"webpage/common/images/sc.png",
	            button_width : config.button_width?config.button_width:73,
				button_height : config.button_height?config.button_height:27,
	            button_text : '',
				button_text_left_padding : 0,
				button_text_top_padding : 0,
	            button_action : SWFUpload.BUTTON_ACTION.SELECT_FILES,
	            button_disabled : false,
	            button_cursor : SWFUpload.CURSOR.HAND,
	            button_window_mode:SWFUpload.WINDOW_MODE.TRANSPARENT,
	            //swfupload控件装载完毕触发
	            swfupload_loaded_handler : function(){
	            	if(config.successful_uploads){
	            		this.setStats({successful_uploads:config.successful_uploads});
	            	}
				},
				//文件选择窗口,选择或者关闭后触发
				file_dialog_complete_handler : function(numFilesSelected, numFilesQueued){
					if(numFilesQueued>0){
						this.startUpload();
					}
				},
				//加入上传队列失败
				file_queue_error_handler:function(file, errorCode, message){
					try {
						if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
							alert( (message == 0 ? "您已达到上传数量限制" : "您正在上传的文件过多。\n您最多能选择"+(message > 1 ? "上传"+ message+ "个文件。" : "一个文件。")));
							return false;
						}
						switch (errorCode) {
							case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT :
							    alert(file.name + "超出文件大小限制,最大只能上传"+config.file_size_limit);
								break;
							case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE :
								alert("无法上传零字节文件:" + file.name);
								break;
							case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE :
								alert("不支持的文件类型");
								break;
							default :
								if (file !== null) {
									alert("未处理的错误");
								}
								break;
						}
					} catch (ex) {
						alert("未处理的错误");
						return false;
					}
				},
				//在文件往服务端上传之前触发
				upload_start_handler : function(){
					return true;
				},
				//上传进度
				upload_progress_handler :function(){
					layload = layer.load("正在上传中...");
				},
				//上传失败，被终止或没成功触发
				upload_error_handler : function(file, errorCode, message){
					try{
						switch (errorCode) {
						case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
							alert("上传错误: " + message);
							break;
						case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
							alert("上传失败");
							break;
						case SWFUpload.UPLOAD_ERROR.IO_ERROR:
							alert("服务器(IO)出错");
							break;
						case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
							alert("安全错误");
							break;
						case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
							alert("超出上传限制"); 
							break;
						case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
							alert("无法验证，跳过上传 ");
							break;
						case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
							break;
						case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
							alert("停止上传");
							break;
						default:
							alert("未处理过错误");	 
							break;
						}
						
					}catch(ex){
						this.debug(ex);
					}
				},
				//上传成功触发，不管是否成功接收
				upload_success_handler :function(file, serverData, responseReceived){
					layer.close(layload);
					var resultJson = $.parseJSON(serverData);
					config.upload_success_handler.call(this,resultJson);
				},
				//上传完成时触发
				upload_complete_handler : function(file){
					//上传完成后 继续上传下一个
					if (this.getStats().files_queued > 0) {
				        this.startUpload();
				    }
				}
		};
		var swfu = new SWFUpload(settings_object);
		return swfu;
	},
	/**
	 * 绑定验证引擎
	 * @param {} config
	 */
	bindValidateEngine:function(config){
		//获取选择器
		var selector = config.selector;
		$(selector).validationEngine({ 
		     promptPosition:config.promptPosition?config.promptPosition:"topLeft",
		     autoPositionUpdate:true,
	         autoHidePrompt: true,//自动隐藏提示信息
	         autoHideDelay: "3000",//自动隐藏提示信息的延迟时间(单位：ms)   
	         fadeDuration: "0.5",//隐藏提示信息淡出的时间
	         maxErrorsPerField: "1",//单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
	         showOneMessage: false//是否只显示一个提示信息
	     });
	},
	/**
	 * 重新加载自定义复选框组
	 * @param {} selectObj
	 * @param {} config
	 */
	reloadCheckbox:function(selectObj,config){
		var dataInterface = selectObj.attr("dataInterface");
		var dataParams = selectObj.attr("dataParams");
		var value = selectObj.attr("value");
		var name = selectObj.attr("name");
		var clazz = selectObj.attr("clazz");
		AppUtil.ajaxProgress({
			url : "eveControlController/reloadCheckBox.do",
			params : {
				dataInterface:config.dataInterface?config.dataInterface:dataInterface,
				dataParams:config.dataParams?config.dataParams:dataParams,
				value:config.value?config.value:value,
				name:config.name?config.name:name,
				clazz:config.clazz?config.clazz:clazz
			},
			callback : function(resultJson) {
				var CHECKBOXHTML = resultJson.CHECKBOXHTML;
				selectObj.html(CHECKBOXHTML);
			}
		});
	},
	/**
	 * 重新加载自定义下拉框
	 * @param {} selectObj
	 * @param {} config
	 * exp:
	 * var select = $("select[name='LEAVE_TYPE']");
	   AppUtil.reloadSelect(select,{dataParams:"ExpressWay"});
	 */
	reloadSelect:function(selectObj,config,callback){
		var dataInterface = selectObj.attr("dataInterface");
		var dataParams = selectObj.attr("dataParams");
		var defaultEmptyText = selectObj.attr("defaultEmptyText");
		AppUtil.ajaxProgress({
			url : "eveControlController/reloadSelect.do",
			params : {
				dataInterface:config.dataInterface?config.dataInterface:dataInterface,
				dataParams:config.dataParams?config.dataParams:dataParams,
				defaultEmptyText:config.defaultEmptyText?config.defaultEmptyText:defaultEmptyText
			},
			callback : function(resultJson) {
				var SELECTCONTENT = resultJson.SELECTCONTENT;
				selectObj.html(SELECTCONTENT);
				if(callback){
					callback.call(this,resultJson);
				}
			}
		});
	},
	/**
     * 判断一个数组中是否包含某个值 
     */
    isContain:function(array,targetValue){
    	var isExist = false;
    	for(var index in array){
    		var value = array[index];
    		if(value==targetValue){
    			isExist = true;
    			break;
    		}    		
    	}
    	return isExist;
    },
	/**
	 * 获取被授权的栏目ID
	 * @param {} siteId
	 */
    getGrantedChannel:function(siteId){
    	var channelIds = [];
		$.ajax({
            type: "POST",
            url: "channelController.do?loadGranted&siteId="+siteId,
            async: false, 
            cache: false,
            success: function (responseText) {
            	var resultJson = $.parseJSON(responseText);
            	for(var index in resultJson){
            		var obj = resultJson[index];
            		channelIds.push(obj.CHANNEL_ID);
            	}
            }
        });
        return channelIds;
        /*if(channelIds.indexOf("247")!=-1){
        	console.log("存在..");
        }else{
        	console.log("Bu");
        }*/
        
    },
	/**
	 * 获取被授权的文章访问权限
	 * @param {} siteId
	 * @param {} channelId
	 */
	getGrantedArticleRights:function(siteId,channelId){
		var resultJson = null;
		$.ajax({
            type: "POST",
            url: "articleController.do?getRights&siteId="+siteId+"&channelId="+channelId,
            async: false, 
            cache: false,
            success: function (responseText) {
            	resultJson = $.parseJSON(responseText);
            }
        });
        return resultJson;
	},
	/**
	 * 显示收件箱内容
	 * @param {} recordId
	 */
	showMsgRecordWindow:function(recordId){
		$.layer({
			type : 2,
			maxmin : false,
			title : "阅读站内短信",
			area : [ "600px", "400px" ],
			iframe : {
				src : "msgRecordController.do?info&entityId=" + recordId
			},
			close: function(index){
				$("#MsgRecordGrid").datagrid("reload");
			}
		});
	},
	/**
	 * 进入系统
	 * @param {} loginFormId
	 * @param {} isNeedValidCode
	 */
	enterSystem:function(loginFormId,isNeedValidCode){
		//判断浏览器是否为ie6,7,8
	    if (!$.support.leadingWhitespace) {
	       if(event.keyCode == 13){
		    	AppUtil.submitLoginForm(loginFormId,isNeedValidCode);
		    }
	    }else{
	    	event=arguments.callee.caller.arguments[0] || window.event; 
		    if(event.keyCode == 13){
		    	AppUtil.submitLoginForm(loginFormId,isNeedValidCode);
		    }
	    }
	},
	/**
	 * 提交登录表单
	 * @param {} loginFormId
	 * @param {} isNeedValidCode
	 */
	submitLoginForm:function(loginFormId,isNeedValidCode){
		$("#username").val($.trim($("#username").val()));
		var username = $("#username").val();
		var password = $("#password").val();
		if(!(username!=null&&username!="")){
			alert("请输入用户帐号!");
			return;
		}
		if(!(password!=null&&password!="")){
			alert("请输入密码!");
			return;
		}
		if(isNeedValidCode){
			var validateCode = $("#validateCode").val();
			if(!(validateCode!=null&&validateCode!="")){
				alert("请输入验证码!");
				return;
			}
		}
		var remember = AppUtil.getCheckBoxCheckedValue("rememberPassword");
		if(remember&&remember=="1"){
			$.cookie("rememberPassword","1",{ expires:30}); 
			$.cookie("username",username,{ expires:30}); 
			$.cookie("password",password,{ expires:30}); 
		}else{
			$.cookie('rememberPassword', null); 
		}
		$("#"+loginFormId).submit();
	},
	/**
	 * 重置表单
	 * @param {} formName
	 */
	resetForm:function(formName){
		$("form[name='"+formName+"']")[0].reset();
	},
	/**
	 * 检查浏览器版本
	 */
	checkBrowserVersion:function(){
		var userAgent = navigator.userAgent.toLowerCase();
		browser = {
			version : (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [
					0, '0'])[1],
			safari : /webkit/.test(userAgent),
			opera : /opera/.test(userAgent),
			msie : /msie/.test(userAgent) && !/opera/.test(userAgent),
			mozilla : /mozilla/.test(userAgent)
					&& !/(compatible|webkit)/.test(userAgent)
		}
		if (browser.msie && (parseInt(browser.version) <= 7)) {
			layer.alert("您的浏览器正处于ie7或以下级别的文档模式，不适合本系统的运维操作，建议下载并安装<a href='plug-in/browser-1.0/ie8.rar'>ie8</a>或<a href='plug-in/browser-1.0/Firefox-full-latest.exe'>火狐浏览器</a>。");
		}
	},
	/**
	 * 格式化时间
	 * @param {} date
	 * @param {} format
	 */
	formatDate:function(date,format){
		var paddNum = function(num){
          num += "";
          return num.replace(/^(\d)$/,"0$1");
        }
        //指定格式字符
        var cfg = {
           yyyy : date.getFullYear() //年 : 4位
          ,yy : date.getFullYear().toString().substring(2)//年 : 2位
          ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
          ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
          ,d  : date.getDate()   //日 : 如果1位的时候不补0
          ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
          ,hh : paddNum(date.getHours())  //时
          ,mm : paddNum(date.getMinutes()) //分
          ,ss : paddNum(date.getSeconds()) //秒
        }
        format || (format = "yyyy-MM-dd hh:mm:ss");
        return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
	},
	/**
	 * 显示excel导出方式选择窗口
	 * @param {} config
	 */
	showExcelExportWin:function(config){
		var dataGridId = config.dataGridId;
		var page = $("#"+dataGridId).datagrid("options").pageNumber;
		var rows = $("#"+dataGridId).datagrid("options").pageSize;
		var tplKey= config.tplKey;
		var excelName = config.excelName;
		var queryParams = config.queryParams;
		var isportal = config.isportal;
		var src = "excelConfigController.do?selector&tplKey="+tplKey+"&excelName="+excelName
		+"&page="+page+"&rows="+rows;
		if(isportal == "true"){
			src += "&_ISPORTAL=true"
		}
		for(var queryName in queryParams){
			var queryValue = queryParams[queryName];
			if(queryValue){
				src+=("&"+queryName+"="+queryValue);
			}
		}
		$.dialog.open(src, {
    		title : "导出EXCEL",
            width:"500px",
            lock: true,
            resize:false,
            height:"150px"
    	}, false);
	},
	/**
	 * 获取表格的附件IDS
	 * @param {} gridId
	 */
	getAttachFileIds:function(gridId){
		var fileIds = [];
		var rows = $("#"+gridId).datagrid("getRows");
		if(rows.length > 0){
			for(var i = 0;i<rows.length;i++){
				fileIds.push(rows[i].FILE_ID);
			}
		}
		return fileIds;
	},
	/**
	 * 转换文件大小显示
	 * @param {} file
	 */
	convertFileSize:function(file){
		var sizeNames = [' Bytes', ' KB', ' MB', ' GB', ' TB', ' PB', ' EB', ' ZB', ' YB'];
		var i = Math.floor(Math.log(file.size)/Math.log(1024));
		var p = (i > 1) ? 2 : 0;
		var size = (file.size/Math.pow(1024, Math.floor(i))).toFixed(p) + sizeNames[i];
		return size;
	},
	/**
	 * 上传文件排队
	 * @param {} file
	 */
	fileQueued:function(file){
		try{
			var size = AppUtil.convertFileSize(file);
		} catch (ex) {
			this.debug(ex);
		}
	},
	/**
	 * 上传文件选择完毕
	 */
	fileDialogComplete:function(numFilesSelected, numFilesQueued){
		if(numFilesQueued>0){
			this.startUpload();
		}
	},
	/**
	 * 上传失败
	 * @param {} file
	 * @param {} errorCode
	 * @param {} message
	 */
	uploadError:function(file, errorCode, message){
		try{
			switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
				alert("上传错误: " + message);
				this.debug("错误代码: HTTP错误, 文件名: " + file.name + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
				alert("上传失败");
				this.debug("错误代码: 上传失败, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.IO_ERROR:
				alert("服务器(IO)出错");
				this.debug("错误代码: IO 错误, 文件名: " + file.name + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
				alert("安全错误");
				this.debug("错误代码: 安全错误, 文件名: " + file.name + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				alert("超出上传限制"); 
				this.debug("错误代码: 超出上传限制, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
				alert("无法验证，跳过上传 ");
				this.debug("错误代码: 文件验证失败, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
				// If there aren't any files left (they were all cancelled) disable the cancel button
	//			if (this.getStats().files_queued === 0) {
	//				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	//			}
				//alert("取消上传");
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
				alert("停止上传");
				break;
			default:
				alert("未处理过错误");	 
				this.debug("错误代码: " + errorCode + ", 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			}
			
		}catch(ex){
			this.debug(ex);
		}
	},
	/**
	 * 开始上传
	 * @param {} file
	 * @return {Boolean}
	 */
	uploadStart:function(file){
		return true;
	},
	uploadComplete:function(file){
		//上传完成后 继续上传下一个
		if (this.getStats().files_queued > 0) {
	        this.startUpload();
	    }
	},
	/**
	 * 删除已经上传的文件
	 * @param {} fileId
	 */
	removeUploadFile:function(fileId,singleFileDivId,singleFileFieldName,uploadTableId,uploadButtonId){
		parent.layer.confirm("您确定要删除该文件吗?", function(index) {
			parent.layer.close(index);
			$.post("fileAttachController.do?multiDel", {
						selectColNames :fileId
					}, function(responseText, status, xhr) {
						var resultJson = $.parseJSON(responseText);
						if (resultJson.success == true) {
							if(uploadTableId){
								var fileIds = [];
								var rows = $("#"+uploadTableId).datagrid("getRows");
								if(rows.length > 0){
									for(var i = 0;i<rows.length;i++){
										if(rows[i].FILE_ID!=fileId){
											fileIds.push(rows[i].FILE_ID);
										}
									}
								}
								$("#"+uploadTableId).datagrid('load',{
									fileIds:fileIds
								});
								var fileCount = fileIds.length;
								$.each(SWFUpload.instances,function(n,swfObject) {   
								 	 if(swfObject.customSettings.id){
										if(swfObject.customSettings.id==uploadButtonId){
											swfObject.setStats({successful_uploads:fileCount});
										}
									}
						         }); 
							}else{
								$("#"+singleFileDivId).html("");
								$("input[name='"+singleFileFieldName+"']").val("");
							}
						} else {
							layer.msg(resultJson.msg, 3, 1);
						}
					});
		});
	},
	/**
	 * 下载附件
	 * @param {} fileId
	 */
	downLoadFile:function(fileId){
		window.location.href=__ctxPath+"/DownLoadServlet?fileId="+fileId;
	},
	/**
	 * 下载附件
	 * @param {} fileName:文件名称
	 * @param {} filePath:文件路径
	 */
	downLoadFileByFilePath:function(fileName,filePath){
		window.location.href=__ctxPath+"/DownLoadServlet?fileName="+fileName+"&filePath="+filePath;
	},
	/**
	 * 下载附件
	 * @param {} fileId
	 */
	downLoadPrintFile:function(fileId){
		window.location.href=__ctxPath+"/DownLoadPrintServlet?fileId="+fileId;
	},
	/**
	 * 初始化上传控件
	 * @param {} config
	 */
	initUploadControl:function(config){
		//开始回填上传图片的内容
		if(config.uploadImageFieldName&&config.uploadImageId){
			var PHOTO_PATH = $("input[name='"+config.uploadImageFieldName+"']").val();
			if(PHOTO_PATH){
				$("#"+config.uploadImageId).attr("src",PHOTO_PATH);
			}
		}
		//开始回填单个上传的文件内容
		if(config.singleFileId){
			$.post("fileAttachController.do?get", {
				fileId : config.singleFileId
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				var fileName = resultJson.FILE_NAME;
				var fileSize = resultJson.FILE_SIZE;
				var fileId = resultJson.FILE_ID;
				var singleFileFieldName = config.singleFileFieldName;
				if(fileName.length>30){
					fileName = fileName.substr(0,30)+"...";
					$("#"+config.singleFileDivId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+fileName+"&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;&nbsp;</div>");
					$("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
				    $("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.removeUploadFile(\""+fileId+"\",\""+config.singleFileDivId+"\",\""+singleFileFieldName+"\");' >删除</a>");
				}else{
					$("#"+config.singleFileDivId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+fileName+"&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;&nbsp;</div>");
					$("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
				    $("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.removeUploadFile(\""+fileId+"\",\""+config.singleFileDivId+"\",\""+singleFileFieldName+"\");' >删除</a>");
				}
			});
		}
		var uploadUserId = config.uploadUserId?config.uploadUserId:"";
		var busTableName = config.busTableName?config.busTableName:"";
		var busRecordId = config.busRecordId?config.busRecordId:"";
		var uploadPath = config.uploadPath;
		//单个文件大小限制
		var limit_size = config.limit_size?config.limit_size:"10 MB";
		//上传文件类型限制
		var file_types = config.file_types?config.file_types:"*.*;";
	    var file_upload_limit = (config.file_upload_limit!=null)?config.file_upload_limit:1;
	    var file_queue_limit = (config.file_queue_limit!=null)?config.file_queue_limit:1;
	    var button_placeholder_id = config.uploadButtonId;
	    var fileQueued = null;
	    if(config.uploadFileType&&config.uploadFileType=="image"){
	    	fileQueued = AppUtil.fileQueued;
	    }else if(config.singleFileDivId){
	    	fileQueued = function(file) {
				try{
					var size = AppUtil.convertFileSize(file);
					var singleFileDivId = config.singleFileDivId;
					$("#"+singleFileDivId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+file.name+"&nbsp;&nbsp;&nbsp;&nbsp;"+size+"&nbsp;&nbsp;&nbsp;</div>");
					$("#"+singleFileDivId).append("<div style='width:100px; float:left; line-height:28px;'></div>");
					$("#"+singleFileDivId+" div").eq(1).progressbar({
						value:0
					});
				} catch (ex) {
					this.debug(ex);
				}
			}
	    }else if(config.uploadTableId){
	    	fileQueued = function(file) {
				try {
					var fileSize = AppUtil.convertFileSize(file);
					var fileType = file.type;
					fileType = fileType.substring(fileType.lastIndexOf(".")+1,fileType.length)
					$("#"+config.uploadTableId).datagrid('insertRow', {
						row : {
							FILE_ID:"0",
							FILE_NAME : "<a href='#' id='"+file.id+"_download' ><font color='blue'>"+file.name+"</font></a>",
							FILE_SIZE : fileSize,
							FILE_TYPE : fileType,
							SWF_FILEID:file.id,
							fileprogress : '<div id="' + file.id
									+ '"></div>',
							FILE_DELETE : "<a href='#' id='" + file.id
									+ "_delete'></a>"
						}
					});
					$("#" + file.id).progressbar({
								value : 0
							});

				} catch (ex) {
					this.debug(ex);
				}
			}
	    }
	    var uploadProgress = null;
	    if(config.singleFileDivId){
	    	uploadProgress =function(file,bytesLoaded, bytesTotal){
				try {
					var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
					percent = percent == 100 ? 99 : percent;
					$("#"+config.singleFileDivId+" div").eq(1).progressbar({
								value : percent
							});
				} catch (ex) {
					this.debug(ex);
				}
			}
	    }else if(config.uploadTableId){
	    	uploadProgress =function(file,bytesLoaded, bytesTotal){
				try {
					var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
					percent = percent == 100 ? 99 : percent;
					$("#" + file.id).progressbar({
								value : percent
							});
				} catch (ex) {
					this.debug(ex);
				}
			}
	    	
	    }
	    
	    var uploadSuccess = null;
	    if(config.uploadFileType&&config.uploadFileType=="image"){
	    	uploadSuccess = config.uploadSuccess;
	    }else if(config.singleFileDivId){
	    	uploadSuccess = function(file, serverData, responseReceived){
				 $("#"+config.singleFileDivId+" div").eq(1).progressbar({
					value : 100
				});
				var resultJson = $.parseJSON(serverData);
				var fileId = resultJson.fileId;
				$("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
			   $("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.removeUploadFile(\""+fileId+"\",\""+config.singleFileDivId+"\",\""+config.singleFileFieldName+"\");' >删除</a>");
			    if(config.uploadSuccess!=null){
			    	config.uploadSuccess.call(this,resultJson);
			    }
			    
			}
	    }else if(config.uploadTableId){
	    	uploadSuccess = function(file, serverData, responseReceived){
	    		var resultJson = $.parseJSON(serverData);
				var fileId = resultJson.fileId;
				var rowIndex = $("#"+config.uploadTableId).datagrid("getRowIndex",file.id);
				$("#"+config.uploadTableId).datagrid('updateRow',{
					index: rowIndex,
					row: {
						FILE_ID: fileId
					}
				});
				$("#"+file.id+"_download").attr("onclick","AppUtil.downLoadFile(\""+fileId+"\");")
				$("#"+file.id+"_delete").text('删除');
				$("#"+file.id+"_delete").attr("onclick","AppUtil.removeUploadFile(\""+fileId+"\",null,null,\""+config.uploadTableId+"\",\""+config.uploadButtonId+"\");")
			    $("#"+file.id).progressbar({
	                 value: 100
	            });
			}
	    }
	  
		var swfu = new SWFUpload({
			upload_url : __ctxPath+"/UploadServlet?uploadUserId="+uploadUserId+"&busTableName="+busTableName+"&uploadPath="+uploadPath+"&busRecordId="+busRecordId,
			flash_url : __ctxPath+"/plug-in/swfupload-2.2/swfupload.swf",
			flash9_url : __ctxPath+"/plug-in/swfupload-2.2/swfupload_fp9.swf",
			file_size_limit : limit_size,
			file_types : file_types,
			file_types_description : "All Files",
			//指定最多能上传多少个文件
			file_upload_limit : file_upload_limit,
			file_queue_limit : file_queue_limit,
			//加入上传队列成功
			file_queued_handler : fileQueued,
			//加入上传队列失败
			file_queue_error_handler:function(file, errorCode, message){
				try {
					if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
						alert( (message == 0 ? "您已达到上传限制" : "您正在上传的文件过多。\n您最多能选择"+(message > 1 ? "上传"+ message+ "个文件。" : "一个文件。")));
						return false;
					}
					switch (errorCode) {
						case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT :
						    alert(file.name + "超出文件大小限制,最大只能上传"
									+ config.limit_size);
							break;
						case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE :
							alert("无法上传零字节文件:" + file.name);
							this.debug("错误代码: 零字节文件, 文件名: " + file.name + ", 文件尺寸: "
									+ file.size + ", 信息: " + message);
							break;
						case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE :
							alert("不支持的文件类型");
							this.debug("错误代码: 不支持的文件类型, 文件名: " + file.name + ", 文件尺寸: "
									+ file.size + ", 信息: " + message);
							break;
						default :
							if (file !== null) {
								alert("未处理的错误");
							}
							this.debug("错误代码: " + errorCode + ", 文件名: " + file.name
									+ ", 文件尺寸: " + file.size + ", 信息: " + message);
							break;
					}
				} catch (ex) {
					this.debug(ex);
		
				}
			},
			//加入上传队列处理完后触发 
			file_dialog_complete_handler : AppUtil.fileDialogComplete,
			//在文件往服务端上传之前触发
			upload_start_handler : AppUtil.uploadStart,
			//上传进度
			upload_progress_handler : uploadProgress,
			//上传失败，被终止或没成功触发
			upload_error_handler : AppUtil.uploadError,
			//上传成功触发，不管是否成功接收
			upload_success_handler : uploadSuccess,
			//上传完成时触发
			upload_complete_handler : AppUtil.uploadComplete,
			swfupload_loaded_handler:function(){
				if(config.uploadTableId){
					var rows = $("#"+config.uploadTableId).datagrid("getRows");
					swfu.setStats({successful_uploads:rows.length });
				}
				
			},
		    button_image_url : __ctxPath + "/plug-in/easyui-1.4/themes/icons/upload_swfupload1.png",
			button_placeholder_id :button_placeholder_id,
			button_width : 41,
			button_height : 22,
			button_text : '',
			button_text_left_padding : 0,
			button_text_top_padding : 0,
			button_action : config.uploadTableId?SWFUpload.BUTTON_ACTION.SELECT_FILES:SWFUpload.BUTTON_ACTION.SELECT_FILE,
			button_disabled : false,
			button_cursor : SWFUpload.CURSOR.HAND,
			button_window_mode:SWFUpload.WINDOW_MODE.TRANSPARENT,
			custom_settings : {
				limit_size : limit_size,
				file_upload_limit:file_upload_limit,
				id:config.uploadButtonId,
				uploadTableId:config.uploadTableId
			},
			debug : false
		});
		
	},
	/**
	 * 加入菜单资源到中央面板
	 * @param {} menuKey
	 * @param {} menuTitle
	 * @param {} url
	 */
	addMenuToCenterTab:function(menuKey,menuTitle,url,loadType){
		if($("#centerTabpanel").tabs('tabs').length>=20){
			layer.alert("抱歉,最多只能打开20个标签页!");
			return;
		}
		if ($("#centerTabpanel").tabs('exists', menuTitle)) {
			$('#centerTabpanel').tabs('select', menuTitle);
		} else {
			if(loadType&&loadType=='2'){
				var content = '<iframe scrolling="yes" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';  
				$('#centerTabpanel').tabs('add', {
					title:menuTitle,    
					content:content, 
		    		closable:true
				});
			}else{
				$('#centerTabpanel').tabs('add', {
					title:menuTitle,    
					href:url,  
		    		closable:true
				});
			}
			
		}
	},
	/**
	 * 初始化弹出层表单
	 * @param {} formId:表单ID
	 * @param {} onValidationComplete:验证完毕调用函数
	 * @param {} ajaxValidTableName:需要进行ajax验证的表名称
	 */
	initWindowForm:function(formId,onValidationComplete,ajaxValidTableName,validPromptPosition,recordId){
		//设置formDiv的高度
		/*var height = $("body").height()-47;
		var style = "height:"+height+"px;overflow: auto;";
		$("#"+formId+"Div").attr("style",style);*/
	  	//绑定验证引擎
	  	if(onValidationComplete){
	  		if(ajaxValidTableName){
	  			$.validationEngineLanguage.allRules.ajaxVerifyValueExist.extraData =("tableName="+ajaxValidTableName+"&recordId="+recordId);
	  		}
	  		$("#"+formId).validationEngine("attach",{ 
			     promptPosition:validPromptPosition?validPromptPosition:"topLeft",
			     autoPositionUpdate:true,
		         autoHidePrompt: true,            //自动隐藏提示信息
		         autoHideDelay: "3000",           //自动隐藏提示信息的延迟时间(单位：ms)   
		         fadeDuration: "0.2",             //隐藏提示信息淡出的时间
		         maxErrorsPerField: "1",          //单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
		         showOneMessage: false,           //是否只显示一个提示信息
		         onValidationComplete:onValidationComplete
		     });
	  	}
	},
	/**
	 * 关闭弹出层
	 */
	closeLayer:function(){
	   art.dialog.close();
	},
	/**
	 * 关闭全部弹出层
	 */
	closeAllLayer:function(){
		var list = art.dialog.list;
		for (var i in list) {
			list[i].close();
		};
	},
	/**
	 * 重置EasyUI的dagagrid查询条件
	 * @param {} searchToolBarId
	 */
	gridSearchReset:function(searchFormName){
		$("form[name='"+searchFormName+"']").form("reset");
	},
	/**
	 * 去除字符串的前后空格
	 * @param {} str
	 * @return {}
	 */
	trim:function(str){
		str = str.replace(/(^\s*)|(\s*$)/g, "");
		str = str.replace(/^[\s　\t]+|[\s　\t]+$/, ""); 
		return str;
	},
	/**
	 * 根据元素ID获取孩子查询参数
	 */
	getQueryParams:function(elementId){
		var queryParams = {};
		var array = $("#"+elementId+" [name]");
		for(var i =0;i<array.length;i++){
			var value = array.eq(i).val();
			if(value!=null&&value!=""){
				value = AppUtil.trim(value);
				var name = array.eq(i).attr("name");
				var className = array.eq(i).attr("class");
				if(className=="laydate-icon"){
					if(value.indexOf(" ")==-1){
						if(name.indexOf(">=")!=-1||name.indexOf("GE")!=-1){
							queryParams[name] = value+" 00:00:00";
						}else if(name.indexOf("<=")!=-1||name.indexOf("LE")!=-1){
							queryParams[name] = value+" 23:59:59";
						}
					}else{
						queryParams[name] = value;
					}
				}else{
					queryParams[name] = value;
				}
				
			}
		}
		//扩展checkboxgroup的情况
		var checkgroups = $("#"+elementId+" div[class='eve-chcekbox']");
		if(checkgroups){
			for(var j=0;j<checkgroups.length;j++){
				var name = checkgroups.eq(j).attr("name");
				if(name!=null&&name!=""){
					queryParams[name] = AppUtil.getCheckBoxCheckedValue(name);
				}
			}
		}		
		return queryParams;
	},
	/**
	 * easyUI grid做查询API
	 * @param {} searchToolBarId
	 * @param {} gridId
	 */
	gridDoSearch:function(searchToolBarId,gridId){
		var queryParams = AppUtil.getQueryParams(searchToolBarId);
		$("#"+gridId).datagrid('load',queryParams);
	},	
	/**
	 * easyUI grid做查询API
	 * @param {} searchToolBarId
	 * @param {} gridId
	 */
	gridsDoSearch:function(searchToolBarId){
		var queryParams = {};
		var array = $("#"+searchToolBarId+" [name]");
		for(var i =0;i<array.length;i++){
			var value = array.eq(i).val();
			if(value!=null&&value!=""){
				value = AppUtil.trim(value);
				var name = array.eq(i).attr("name");
				var className = array.eq(i).attr("class");
				if(className=="laydate-icon"){
					if(value.indexOf(" ")==-1){
						if(name.indexOf(">=")!=-1){
							queryParams[name] = value+" 00:00:00";
						}else if(name.indexOf("<=")!=-1){
							queryParams[name] = value+" 23:59:59";
						}
					}else{
						queryParams[name] = value;
					}
				}else{
					queryParams[name] = value;
				}
				
			}
		}
		
		//扩展checkboxgroup的情况
		var checkgroups = $("#"+searchToolBarId+" div[class='eve-chcekbox']");
		if(checkgroups){
			for(var j=0;j<checkgroups.length;j++){
				var name = checkgroups.eq(j).attr("name");
				if(name!=null&&name!=""){
					queryParams[name] = AppUtil.getCheckBoxCheckedValue(name);
				}
			}
		}
		
		for(var i=1;i<arguments.length;i++){
			$("#"+arguments[i]).datagrid('load',queryParams);
		}
		//$("#"+gridId).datagrid('load',queryParams);
	},
	/**
	 * 获取toolbar的查询条件的数组
	 */
	getQueryParamsFromSearchToolbar:function(searchToolBarId){
		var queryParams = {};
		var array = $("#"+searchToolBarId+" [name]");
		for(var i =0;i<array.length;i++){
			var value = array.eq(i).val();
			if(value!=null&&value!=""){
				value = AppUtil.trim(value);
				var name = array.eq(i).attr("name");
				var className = array.eq(i).attr("class");
				if(className=="laydate-icon"){
					if(value.indexOf(" ")==-1){
						if(name.indexOf(">=")!=-1||name.indexOf("GE")!=-1){
							queryParams[name] = value+" 00:00:00";
						}else if(name.indexOf("<=")!=-1||name.indexOf("LE")!=-1){
							queryParams[name] = value+" 23:59:59";
						}
					}else{
						queryParams[name] = value;
					}
				}else{
					queryParams[name] = value;
				}
				
			}
		}
		//扩展checkboxgroup的情况
		var checkgroups = $("#"+searchToolBarId+" div[class='eve-chcekbox']");
		if(checkgroups){
			for(var j=0;j<checkgroups.length;j++){
				var name = checkgroups.eq(j).attr("name");
				if(name!=null&&name!=""){
					queryParams[name] = AppUtil.getCheckBoxCheckedValue(name);
				}
			}
		}		
		return queryParams;
	},		
	/**
	 * 待进度条的AJAX请求
	 * @param {} config
	 */
	ajaxProgress:function(config){
		var layload = layer.load('正在提交请求中…');
		 $.post(config.url,config.params, function(responseText, status, xhr) {
			layer.close(layload);
			if(responseText&&responseText!="websessiontimeout"){
				var resultJson = $.parseJSON(responseText);
				if(config.callback!=null){
					config.callback.call(this,resultJson);
				}
			}
		});
	},
	/**
	 * 不带进度条的AJAX请求
	 */
	ajaxPost:function(config){
		 $.post(config.url,config.params, function(responseText, status, xhr) {
			if(responseText&&responseText!="websessiontimeout"){
				var resultJson = $.parseJSON(responseText);
				if(config.callback!=null){
					config.callback.call(this,resultJson);
				}
			}
		});
	},
	/**
	 * 获取被编辑的表格的选择记录
	 */
	getEditDataGridRow:function(gridId){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			var colName = $dataGrid.datagrid("options").idField; 
			return rowsData[0];
		}
	},
	/**
	 * 获取被编辑的表格记录ID值
	 * @param {} gridId
	 */
	getEditDataGridRecord:function(gridId){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				lock : true,
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				lock : true,
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			var colName = $dataGrid.datagrid("options").idField; 
			var idValue = eval("rowsData[0]."+colName);
			return idValue;
		}
	},
	/**
	 * 获取被编辑的表格记录ID值
	 * @param {} gridId
	 */
	getStatusDataGridRecord:function(gridId){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			var i_status = eval("rowsData[0].STATUS");
			if(i_status == 2){
				art.dialog({
					content: "选择的记录正在审核,不可修改!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 3){
				art.dialog({
					content: "选择的记录通过审核,不可修改!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 5){
				art.dialog({
					content: "选择的记录已关闭,不可修改!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				var colName = $dataGrid.datagrid("options").idField; 
				var idValue = eval("rowsData[0]."+colName);
				return idValue;
			}
		}
	},
	/**
	 * 获取被编辑的表格记录ID值
	 * @param {} gridId
	 */
	getEditDataGridRecordValue:function(gridId,colName){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			//var colName = $dataGrid.datagrid("options").idField; 
			var idValue = eval("rowsData[0]."+colName);
			return idValue;
		}
	},
	/**
	 * 删除树形节点
	 * @param {} config
	 */
	deleteTreeNode:function(config){
		if(config.noAllowDeleteIfHasChild){
			if(config.treeNode.children&&config.treeNode.children.length>0){
				art.dialog({
					content: "该节点存在孩子节点!不能进行删除!",
					icon:"warning",
				    ok: true
				});
				return;
			}
		}
		art.dialog.confirm("您确定要删除该记录吗?", function() {
			var layload = layer.load("正在提交请求中…");
			$.post(config.url, {
				selectColNames : config.treeNode.id
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					if(config.callback!=null){
						config.callback.call(this,resultJson);
					}else{
						$.fn.zTree.getZTreeObj(config.treeId)
							.reAsyncChildNodes(null, "refresh");
					}
				} else {
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
			});
		}, function() {
			
		});
	},
	/**
	 * 获取单选框的值
	 * @param {} controlName:控件名称
	 * @param {} tagName:标签名称
	 */
	getCheckRadioTagValue:function(controlName,tagName){
		var radios = $("input[name='"+controlName+"'][type='radio']");
		var tagValue = "";
		$.each(radios,function(index,obj) { 
		      var isChecked = $(this).is(':checked');
	  	      if(isChecked){
	  	      	 tagValue = $(this).attr(tagName);
	  	      }
        }); 
        return tagValue;
	},
	/**
	 * 获取复选框选中的值
	 * @param {} name
	 * @return {}
	 */
	getCheckBoxCheckedValue:function(name){
		var checks = $("input[name='"+name+"'][type='checkbox']");
		var checkBoxValues = "";
		$.each(checks,function(index,checkboxobj) {   
			  if(checkboxobj.checked){
		            checkBoxValues += checkboxobj.value+ ","; //获取被选中的值
			  }
        }); 
        if(checkBoxValues.length>=1){
        	checkBoxValues = checkBoxValues.substr(0,checkBoxValues.length-1);
        }
        return checkBoxValues;
	},
	/**
	 * 获取当前登录用户信息
	 */
	getLoginUserInfo:function(){
		var userJsonInfo=$("#loginUserInfoJson").val();
		var __curUser=JSON2.parse(userJsonInfo);
		return __curUser;
	},
	/**
	 * 判断当前用户是否拥有资源KEY
	 * @param {} resKey
	 * @return {Boolean}
	 */
	isGranted:function(resKey){
		if(!__curUser){
			__curUser = AppUtil.getLoginUserInfo();
		}
		var resKeys = __curUser.resKeys;
		if(resKeys.indexOf('__ALL')!=-1){
			return true;
		}
		if(resKeys.indexOf(resKey)!=-1){
			return true;
		}
		return false;
	},
	/**
	 * 初始化某个父亲节点下操作按钮的权限
	 * @param {} parentDomId
	 */
	initAuthorityRes:function(parentDomId){
		var operateButtons = $("#"+parentDomId+" a[resKey]");
		for(var i =0;i<operateButtons.length;i++){
			var operateBtn = operateButtons.eq(i);
			var resKey = operateBtn.attr("resKey");
			if(!AppUtil.isGranted(resKey)){
				operateBtn.attr("style","display:none;");
			}
		}
	},
	/**
	 * 多选操作表格方法
	 * @param {} url
	 * @param {} gridId
	 * @param {} callback
	 */
	multiOperateDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录",
				icon:"warning",
			    ok: true
			});
		}else{
			var layload = layer.load('正在提交请求中…');
			var colName = $dataGrid.datagrid('options').idField;  
			var selectColNames = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					selectColNames+=",";
				}
				selectColNames+=eval('rowsData[i].'+colName);
			}
			$.post(url,{
				   selectColNames:selectColNames
			    }, function(responseText, status, xhr) {
			    	layer.close(layload);
			    	var resultJson = $.parseJSON(responseText);
					if(resultJson.success == true){
						art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						if(callback!=null){
							callback.call(this,resultJson);
						}else{
							$dataGrid.datagrid('reload');
						}
					}else{
						art.dialog({
							content: resultJson.msg,
							icon:"error",
						    ok: true
						});
					}
			});
		}
	},
	/**
	 * 删除datagrid上的记录
	 * @param {} url:远程URL
	 * @param {} gridId:gridId
	 * @param {} callback:回调函数
	 */
	deleteStatusDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var i_status = eval("rowsData[0].STATUS");
			if(i_status == 2){
				art.dialog({
					content: "选择的记录正在审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 3){
				art.dialog({
					content: "选择的记录通过审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 5){
				art.dialog({
					content: "选择的记录已关闭,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				art.dialog.confirm("您确定要删除该记录吗?", function() {
					var layload = layer.load('正在提交请求中…');
					var colName = $dataGrid.datagrid('options').idField;  
					var selectColNames = "";
					for ( var i = 0; i < rowsData.length; i++) {
						if(i>0){
							selectColNames+=",";
						}
						selectColNames+=eval('rowsData[i].'+colName);
					}
					$.post(url,{
						   selectColNames:selectColNames
					    }, function(responseText, status, xhr) {
					    	layer.close(layload);
					    	var resultJson = $.parseJSON(responseText);
							if(resultJson.success == true){
								art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
								    ok: true
								});
								if(callback!=null){
									callback.call(this,resultJson);
								}else{
									$dataGrid.datagrid('reload');
								}
							}else{
								art.dialog({
									content: resultJson.msg,
									icon:"error",
								    ok: true
								});
							}
					});
				});			
			}
		}
	},
	/**
	 * 操作表格,提交请求
	 * @param {} url
	 * @param {} gridId
	 * @param {} callback
	 */
	operateDataGridRecord:function(url,params,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var layload = layer.load('正在提交请求中…');
		$.post(url,params, function(responseText, status, xhr) {
	    	layer.close(layload);
	    	var resultJson = $.parseJSON(responseText);
			if(resultJson.success == true){
				art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
				    ok: true
				});
				if(callback!=null){
					callback.call(this,resultJson);
				}else{
					$dataGrid.datagrid('reload');
					$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
				}
			}else{
				art.dialog({
					content: resultJson.msg,
					icon:"error",
				    ok: true
				});
			}
		});
	},
	/**
	 * 删除datagrid上的记录
	 * @param {} url:远程URL
	 * @param {} gridId:gridId
	 * @param {} callback:回调函数
	 */
	deleteDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				lock : true,
				icon:"warning",
			    ok: true
			});
		}else{
			art.dialog.confirm("您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $dataGrid.datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				$.post(url,{
					   selectColNames:selectColNames
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							if(callback!=null){
								callback.call(this,resultJson);
							}else{
								$dataGrid.datagrid('reload');
								$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
							}
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
		}
	},
	/**
	 * 删除datagrid上的记录
	 * @param {} url:远程URL
	 * @param {} gridId:gridId
	 * @param {} callback:回调函数
	 */
	deleteColumnStatusDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var i_status = eval("rowsData[0].STATUS");
			if(i_status == 2){
				art.dialog({
					content: "选择的记录正在审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 3){
				art.dialog({
					content: "选择的记录通过审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 5){
				art.dialog({
					content: "选择的记录已关闭,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				art.dialog.confirm("您确定要删除该监察点所有表字段记录吗?", function() {
					var layload = layer.load('正在提交请求中…');
					var colName = $dataGrid.datagrid('options').idField;  
					var selectColNames = "";
					for ( var i = 0; i < rowsData.length; i++) {
						if(i>0){
							selectColNames+=",";
						}
						selectColNames+=eval('rowsData[i].'+colName);
					}
					$.post(url,{
						   selectColNames:selectColNames
					    }, function(responseText, status, xhr) {
					    	layer.close(layload);
					    	var resultJson = $.parseJSON(responseText);
							if(resultJson.success == true){
								art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
								    ok: true
								});
								if(callback!=null){
									callback.call(this,resultJson);
								}else{
									$dataGrid.datagrid('reload');
								}
							}else{
								art.dialog({
									content: resultJson.msg,
									icon:"error",
								    ok: true
								});
							}
					});
				});			
			}
		}
	},
	/**
	 * 禁用表单自动提交
	 */
	disableFormAutoSubmit:function(){
		// 禁用浏览器FORM自动提交
		document.onkeydown = function(event) {  
	        var target, code, tag;  
	        if (!event) {  
	            event = window.event; //针对ie浏览器  
	            target = event.srcElement; 
	            if(target.name){
	            	var controlName = target.name;
	            	if(controlName.indexOf("Q_")!=-1){
	            		 code = event.keyCode;  
			             if (code == 13) {  
			                tag = target.tagName;  
			                if (tag == "TEXTAREA") { return true; }  
			                else { return false; }  
			             }  
	            	}
	            }
	        }  
	        else {  
	            target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	            if(target.name){
	            	var controlName = target.name;
	            	if(controlName.indexOf("Q_")!=-1){
	            		code = event.keyCode;  
			            if (code == 13) {  
			                tag = target.tagName;  
			                if (tag == "INPUT") { return false; }  
			                else { return true; }   
			            }  
	            	}
	            }
	            
	        }  
	    };  
	},
	/**
	 * 设置session过期
	 */
	setSessionTimeOut:function(){
		/**
		 * 覆盖jquery ajax默认请求设置
		 */
		$.ajaxSetup({
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			cache : false,
			type:"POST",
			complete : function(XHR, TS) {
				if(XHR.responseText&&XHR.responseText=="loginrepeated"){					
					alert("账号在异地登录,本地账号被迫下线!");
				    window.top.location.href = __ctxPath+"/loginController.do?login";
				}else if(XHR.responseText&&XHR.responseText=="sessiontimeout"){
					alert("会话过期，请重新登录!");
				    window.top.location.href = __ctxPath+"/loginController.do?login";
				}else if(XHR.responseText&&XHR.responseText=="websessiontimeout"){
					window.top.location.href = __ctxPath+"/webSiteController/view.do?navTarget=website/login/login";
				}
			}
		}); 
	},
	/**
	 * 扩展easyuidatagrid
	 */
	extendsEasyDataGrid:function(){
		$.fn.datagrid.defaults.pageSize = 30;
	    $.fn.datagrid.defaults.pageList = [30,50,100];
		/**
		 * 扩展easyui dagagrid刷新之前去除勾选行
		 */
		$.extend($.fn.datagrid.defaults, {
			onBeforeLoad:function(params){
				$(this).datagrid('clearChecked');
				return true;
			}
		});
		/**
		 * 扩展easyui dagagrid显示被截断文字内容
		 */
		$.extend($.fn.datagrid.methods, {
		    /**
		     * 开打提示功能    
		     * @param {} jq    
		     * @param {} params 提示消息框的样式    
		     * @return {}    
		     */    
		    doCellTip:function (jq, params) {      
		        function showTip(showParams, td, e, dg) {      
		            //无文本，不提示。      
		            if ($(td).text() == "") return;      
		            params = params || {};   
		            var options = dg.data('datagrid');      
		            showParams.content = '<div class="tipcontent">' + showParams.content + '</div>';     
		            $(td).tooltip({      
		                content:showParams.content,      
		                trackMouse:true,      
		                position:params.position,      
		                onHide:function () {      
		                    $(this).tooltip('destroy');      
		                },      
		                onShow:function () {   
		                    var tip = $(this).tooltip('tip');      
		                    if(showParams.tipStyler){      
		                        tip.css(showParams.tipStyler);      
		                    }      
		                    if(showParams.contentStyler){      
		                        tip.find('div.tipcontent').css(showParams.contentStyler);      
		                    }   
		                }      
		            }).tooltip('show');      
		     
		        };      
		        return jq.each(function () {      
		            var grid = $(this);      
		            var options = $(this).data('datagrid');      
		            if (!options.tooltip) {      
		                var panel = grid.datagrid('getPanel').panel('panel');      
		                panel.find('.datagrid-body').each(function () {      
		                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;      
		                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field]', {      
		                        'mouseover':function (e) {   
		                            //if($(this).attr('field')===undefined) return;      
		                            var that = this;   
		                            var setField = null;   
		                            if(params.specialShowFields && params.specialShowFields.sort){   
		                                for(var i=0; i<params.specialShowFields.length; i++){   
		                                    if(params.specialShowFields[i].field == $(this).attr('field')){   
		                                        setField = params.specialShowFields[i];   
		                                    }   
		                                }   
		                            }   
		                            if(setField==null){   
		                                options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');      
		                                var factContentWidth = options.factContent.width();      
		                                params.content = $(this).text();      
		                                if (params.onlyShowInterrupt) {      
		                                    if (factContentWidth > $(this).width()) {
		                                    	var str1 = params.content ;
		                                    	var str2 = "";
		                                    	var n=25;
		                                    	for(var i = 0 ; str1.length>0;i=i+n){
		                                    		s1 = str1.substring(0,n);
		                                    		str1 = str1.substring(n,str1.length);
		                                    		str2+=s1;//+"<br>";
		                                    	}
		                                    	params.content = str2;
		                                    	
		                                        showTip(params, this, e, grid);      
		                                    }      
		                                } else {      
		                                    showTip(params, this, e, grid);      
		                                }    
		                            }else{   
		                                panel.find('.datagrid-body').each(function(){   
		                                    var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');   
		                                    trs.each(function(){   
		                                        var td = $(this).find('> td[field="' + setField.showField + '"]');   
		                                        if(td.length){   
		                                            params.content = td.text();   
		                                        }   
		                                    });   
		                                });   
		                                showTip(params, this, e, grid);   
		                            }   
		                        },      
		                        'mouseout':function (e) {      
		                            if (options.factContent) {      
		                                options.factContent.remove();      
		                                options.factContent = null;      
		                            }      
		                        }      
		                    });      
		                });      
		            }      
		        });      
		    },      
		    /**
		     * 关闭消息提示功能    
		     * @param {} jq    
		     * @return {}    
		     */     
		    cancelCellTip:function (jq) {      
		        return jq.each(function () {      
		            var data = $(this).data('datagrid');      
		            if (data.factContent) {      
		                data.factContent.remove();      
		                data.factContent = null;      
		            }      
		            var panel = $(this).datagrid('getPanel').panel('panel');      
		            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')      
		        });      
		    }
		});
		$.extend($.fn.datagrid.methods, {
			fixRownumber : function (jq) {
				return jq.each(function () {
					var panel = $(this).datagrid("getPanel");
					var clone = $(".datagrid-cell-rownumber", panel).last().clone();
					clone.css({
						"position" : "absolute",
						left : -1000
					}).appendTo("body");
					var width = clone.width("auto").width();
					if (width > 25) {
						//多加5个像素,保持一点边距
						$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
						$(this).datagrid("resize");
						//一些清理工作
						clone.remove();
						clone = null;
					} else {
						//还原成默认状态
						$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
					}
				});
			}
		});
		$.extend($.fn.datagrid.defaults, {
		    onLoadSuccess:function() {
		    	$(this).datagrid('fixRownumber');
				/*$(this).datagrid('doCellTip', {
					onlyShowInterrupt : true, // 是否只有在文字被截断时才显示tip，默认值为false
					position : 'bottom' ,// tip的位置，可以为top,botom,right,left
					tipStyler:{"width":"200px"},
					contentStyler:{"width":"200px"}
					//tipStyler:tip内容的样式，注意要符合jquery css函数的要求。
					//contentStyler:整个tip的样式，注意要符合jquery css函数的要求。
				});*/
			}
		});

	},
	isObjectValueEqual:function(a,b) {   
		  //取对象a和b的属性名
		  var aProps = Object.getOwnPropertyNames(a);
		  var bProps = Object.getOwnPropertyNames(b);
		  //判断属性名的length是否一致
		  if (aProps.length != bProps.length) {
		      return false;
		  }
		  //循环取出属性名，再判断属性值是否一致
		  for (var i = 0; i < aProps.length; i++) {
		    var propName = aProps[i];
		    if (a[propName] !== b[propName]) {
		        return false;
		    }
		  }
		  return true;
	},
	encodeBase64 : function(str) {
		var base64encodechars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		var base64decodechars = new Array(-1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52,
				53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1,
				-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
				14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1,
				-1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
				36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
				50, 51, -1, -1, -1, -1, -1);
		var out, i, len;
		var c1, c2, c3;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			c1 = str.charCodeAt(i++) & 0xff;
			if (i == len) {
				out += base64encodechars.charAt(c1 >> 2);
				out += base64encodechars.charAt((c1 & 0x3) << 4);
				out += "==";
				break;
			}
			c2 = str.charCodeAt(i++);
			if (i == len) {
				out += base64encodechars.charAt(c1 >> 2);
				out += base64encodechars.charAt(((c1 & 0x3) << 4)
						| ((c2 & 0xf0) >> 4));
				out += base64encodechars.charAt((c2 & 0xf) << 2);
				out += "=";
				break;
			}
			c3 = str.charCodeAt(i++);
			out += base64encodechars.charAt(c1 >> 2);
			out += base64encodechars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xf0) >> 4));
			out += base64encodechars.charAt(((c2 & 0xf) << 2)
					| ((c3 & 0xc0) >> 6));
			out += base64encodechars.charAt(c3 & 0x3f);
		}
		return out;
	},
	decodeBase64:function(str){
		var base64encodechars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		var base64decodechars = new Array(-1, -1, -1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52,
					53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1,
					-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
					14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1,
					-1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
					36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
					50, 51, -1, -1, -1, -1, -1);
		var c1, c2, c3, c4;
		var i, len, out;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			do {
				c1 = base64decodechars[str.charCodeAt(i++) & 0xff];
			}while (i < len && c1 == -1);
			if (c1 == -1)
				break;
			do {
				c2 = base64decodechars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c2 == -1);
			if (c2 == -1)
				break;
			out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
			do {
				c3 = str.charCodeAt(i++) & 0xff;
				if (c3 == 61)
					return out;
				c3 = base64decodechars[c3];
			} while (i < len && c3 == -1);
			if (c3 == -1)
				break;
			out += String.fromCharCode(((c2 & 0xf) << 4) | ((c3 & 0x3c) >> 2));
			do {
				c4 = str.charCodeAt(i++) & 0xff;
				if (c4 == 61)
					return out;
				c4 = base64decodechars[c4];
			} while (i < len && c4 == -1);
			if (c4 == -1)
				break;
			out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
		}
		return out;
	},
	isExitsFunction : function (funcName) {
	    try {
	        if (typeof(eval(funcName)) == "function") {
	            return true;
	        }
	    } catch(e) {}
	    return false;
	}

});

$(function(){
   AppUtil.setSessionTimeOut();
});
