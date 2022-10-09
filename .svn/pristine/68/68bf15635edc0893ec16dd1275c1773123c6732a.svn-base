<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
    //var progressBarUnit = 14.3;
    //var progressBarUnit = 11.1;
    var progressBarUnit = 10;
    function saveAuditUsers(currentStep) {
    	//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
    	var userIds = $("input[name='PREAUDITER_IDS']").val();
    	var nextStep = parseInt(currentStep)+1;
    	if(userIds){
    		AppUtil.ajaxProgress({
    			url : "serviceItemController.do?bindUsers",
    			params : {
    				"itemId" : itemId,
    				"userIds" : userIds
    			},
    			callback : function(resultJson) {
    				if (resultJson.success) {
    					changeNextStepPro(currentStep,nextStep);
    				} else {
    					parent.art.dialog({
    						content : resultJson.msg,
    						icon : "error",
    						ok : true
    					});
    				}
    			}
    		});
    	}else{
    		changeNextStepPro(currentStep,nextStep);
    	}
	}
    
    function selectFlowDef(){
    	var defIds = $("input[name='BDLCDYID']").val();
    	//获取事项ID
    	var itemId = $("input[name='ITEM_ID']").val();
    	var currentStep = $("input[name='CurrentStep']").val();
    	parent.$.dialog.open("flowDefController.do?selector&allowCount=1&defIds="+defIds, {
    		title : "流程定义选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDefInfo = art.dialog.data("selectDefInfo");
    			if(selectDefInfo){
    				$("input[name='BDLCDYID']").val(selectDefInfo.defIds);
    				AppUtil.ajaxProgress({
    					url:"serviceItemController.do?saveOrUpdate",
    					params:{
    						"ITEM_ID":itemId,
    						"BDLCDYID":selectDefInfo.defIds,
    						"currentStep":currentStep
    					},
    					callback:function(resultJson){
    	    				addtoTD("defname",selectDefInfo.defNames);
    					}
    				})
    				art.dialog.removeData("selectDefInfo");
    			}
    		}
    	}, false);
    }
    
    function selectAuditUser(){
    	var userIds = $("input[name='PREAUDITER_IDS']").val();
    	parent.$.dialog.open("sysUserController.do?selector&allowCount=5&userIds="
				+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectUserInfo = art.dialog.data("selectUserInfo");
    			if(selectUserInfo&&selectUserInfo.userIds){
    				$("input[name='PREAUDITER_IDS']").val(selectUserInfo.userIds);
    				//$("input[name='PREAUDITER_NAMES']").val(selectUserInfo.userNames);
    				addtoTD("auditusernames",selectUserInfo.userNames);
    				art.dialog.removeData("selectUserInfo");
    			}
    		}
		}, false);
    }
    
    function jumpToTargetDiv(targetDiv){
    	//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
		//获取当前的环节值
		var currentStep = $("input[name='CurrentStep']").val();
    	if(itemId&&currentStep!=targetDiv){
    		if(currentStep>targetDiv){
    			for(var i=targetDiv+1;i<=currentStep;i++){
    				$("#flow_node_"+i).attr("class","flow_nodone");
    			}
    		}else if(currentStep<targetDiv){
    			$("#flow_node_"+currentStep).attr("class","flow_done");
    		}
        	changeNextStepPro(currentStep,targetDiv);
    	}else{
    		return;
    	}
    }
    /**
	 * 删除常见问题列表记录
	 */
	function deleteCommonProblemGridRecord() {
		AppUtil.deleteDataGridRecord("commonProblemController.do?multiDel",
				"CommonProblemGrid");
	};
	/**
	 * 编辑常见问题列表记录
	 */
	function editCommonProblemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("CommonProblemGrid");
		if (entityId) {
			showCommonProblemWindow(entityId);
		}
	}

	/**
	 * 显示常见问题信息对话框
	 */
	function showCommonProblemWindow(entityId) {
		//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
		$.dialog.open("commonProblemController.do?info&entityId=" + entityId+"&itemId="+itemId, {
			title : "常见问题信息",
			width : "800px",
			height : "500px",
			lock : true,
			resize : false,
			close: function () {
				$("#CommonProblemGrid").datagrid("reload");
			}
		}, false);
	};
	
	/*function showSelectBusTypes(){
		var needCheckIds= $("input[name='BUS_TYPEIDS']").val();
		//定义数据源
		art.dialog.data("EveTreeDataConfig", {
			allowCount:0,
			checkCascadeY:"ps",
			checkCascadeN:"s",
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_WSBS_BUSTYPE",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "PARENT_ID,PATH,TYPE_CODE",
				"rootParentId" : "0",
				"needCheckIds":needCheckIds,
				"isShowRoot" : "false",
				"rootName" : "服务类别树"
			}
		});
		parent.$.dialog.open("eveControlController.do?treeSelector", {
			title : "服务类别选择器",
	        width:"500px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
				var selectTreeInfo = art.dialog.data("selectTreeInfo");
				if(selectTreeInfo){
					$("input[name='BUS_TYPEIDS']").val(selectTreeInfo.checkIds);
					$("input[name='BUS_TYPENAMES']").val(selectTreeInfo.checkNames);
					art.dialog.removeData("selectTreeInfo");
				}
			}
		}, false);
	}*/
    
    function updateDicSn(){
    	//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
    	var rows = $("#ApplyMaterGrid").datagrid("getRows"); 
		var materIds = [];
		for(var i=0;i<rows.length;i++){
			materIds.push(rows[i].MATER_ID);
		}
		if(materIds.length>0){
			AppUtil.ajaxProgress({
				url:"applyMaterController.do?updateSn",
				params:{
					itemId:itemId,
					materIds:materIds
				},
				callback:function(resultJson){
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
					$("#ApplyMaterGrid").datagrid("reload");
				}
			})
		}
    	
    }
    
	function deleteBindApplyMater() {
		//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
		AppUtil.deleteDataGridRecord("serviceItemController.do?delmaters&itemId="+itemId,
				"ApplyMaterGrid");
	};
    
	function saveSelectMaters(materIds) {
		//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
		AppUtil.ajaxProgress({
			url : "serviceItemController.do?bindmaters",
			params : {
				"materIds" : materIds,
				"itemId" : itemId
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
			    $("input[name='Q_SM.ITEM_ID_EQ']").val(itemId);
				AppUtil.gridDoSearch("ApplyMaterToolbar", "ApplyMaterGrid");
			}
		});
	}
    
    function bindMater(){
    	var rows = $("#ApplyMaterGrid").datagrid("getRows");
    	var materIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				materIds+=",";
			}
			materIds+=rows[i].MATER_ID;
		}
    	parent.$.dialog.open("applyMaterController.do?selector&materIds="+materIds, {
			title : "申请材料选择器",
	        width:"1000px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
	        	var selectMaterInfo = art.dialog.data("selectMaterInfo");
    			if(selectMaterInfo){
    				saveSelectMaters(selectMaterInfo.materIds);
    				art.dialog.removeData("selectMaterInfo");
    			}
			}
		}, false);
    }
    
    function doPre(){
    	var currentStep = $("input[name='CurrentStep']").val();
    	$("#StepDiv_"+currentStep).attr("style","display:none;");
    	//获取上一步数字
    	var preStep = parseInt(currentStep)-1;
    	if(preStep==1){
    		$("#preStepButton").attr("disabled","disabled");
    	}
    	$("#StepDiv_"+preStep).attr("style","");
    	$("input[name='CurrentStep']").val(preStep);
    	$("#flow_node_"+preStep).attr("class","flow_active");
    	for(var index=preStep+1;index<=11;index++){
    		$("#flow_node_"+index).attr("class","flow_nodone");
    	}
    	$("#nextStepButton").val("保存并下一步");
    	$("#nextStepButton").removeAttr("disabled");
    	//计算百分比
    	var progressBarPer = (preStep-1)*progressBarUnit;
    	$("#progress_bar").attr("style","width:"+progressBarPer+"%;");
    }
    
    //更改保存并下一步的样式
    function changeNextStepPro(currentStep,nextStep){
    	$("#StepDiv_"+currentStep).attr("style","display:none;");
    	if(nextStep==1){
    		$("#preStepButton").attr("disabled","disabled");
    	}else{
    		$("#preStepButton").removeAttr("disabled");
    	}
    	if(nextStep==6){
    		$("#StepDiv_"+nextStep).css("display","block");
    		$("#ApplyMaterGrid").datagrid("resize");          
    	}else if(nextStep==7){
    		$("#StepDiv_"+nextStep).css("display","block");
    		//获取事项ID
    		var itemId = $("input[name='ITEM_ID']").val();
    		$("#CommonProblemGrid").datagrid("resize"); 
    		$("#CommonProblemGrid").datagrid({
    			url:"commonProblemController.do?datagrid&Q_T.ITEM_ID_EQ="+itemId
    		});
    	}else{
    		$("#StepDiv_"+nextStep).attr("style","");
    	}
    	$("input[name='CurrentStep']").val(nextStep);
    	$("#flow_node_"+nextStep).attr("class","flow_active");
    	for(var index=nextStep-1;index>=1;index--){
    		$("#flow_node_"+index).attr("class","flow_done");
    	}
    	if(nextStep==10){
            if("${shan}"!=null&&"${shan}"!=""){
            	$("#nextStepButton").val("下一步");
            	$("#nextStepButton").attr("disabled","disabled");
            }else{
            	$("#nextStepButton").val("保存");
            }
        }
        if(nextStep==11){
        	$("#preStepButton").removeAttr("disabled");
        	$("#nextStepButton").attr("disabled","disabled");
        }else{
        	$("#nextStepButton").val("保存并下一步");
    		$("#nextStepButton").removeAttr("disabled");
        }
    	//计算百分比
    	var progressBarPer = (nextStep-1)*progressBarUnit;
    	$("#progress_bar").attr("style","width:"+progressBarPer+"%;");
    }
    
    function submitInfo(currentStep){
    	var url = $("#StepForm_"+currentStep).attr("action");
    	//获取保存并下一步数字
    	var nextStep = parseInt(currentStep)+1;
    	//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("StepForm_"+currentStep);
    	if(currentStep!="1"){
    		formData.ITEM_ID = $("input[name='ITEM_ID']").val();
    	}
    	if(currentStep=="3"){
    		var newsbtext=$("input[name='RZBSDTFS']:checked")[0].nextSibling.nodeValue;
    		formData.RZBSDTFS_TEXT=newsbtext;
    	}
    	formData.currentStep=currentStep;
    	AppUtil.ajaxProgress({
			url : url,
			params : formData,
			callback : function(resultJson) {
				if (resultJson.success) {
				    if(currentStep=="1"){
				    	$("input[name='ITEM_ID']").val(resultJson.itemId);
				    	$("input[name='ITEM_CODE']").attr("readonly","readonly");
				    	var sxlx=resultJson.SXLXflag;
						if(sxlx==1){//其他转即办件,可读变只读
							$("input[name='CNQXGZR']").val("0");
							$("input[name='CNQXGZR']").attr("readonly","readonly");
						}else if(sxlx==2){//即办件转其他，只读变可读
							 $("input[name='CNQXGZR']").removeAttr("readonly"); 
						}
				    	
				    }/**
				    if(currentStep==10){
				            parent.art.dialog({
				            content : "保存成功",
				            icon : "succeed",
				            time : 3,
				            ok : true
				        });
				        AppUtil.closeLayer();
			        }else{
			        	   changeNextStepPro(currentStep,nextStep);
			        }  **/
			         changeNextStepPro(currentStep,nextStep);
				} else {
					parent.art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			}
		});
    }

    function doNext(){
    	var currentStep = $("input[name='CurrentStep']").val();
    	//获取保存并下一步数字
    	var nextStep = parseInt(currentStep)+1;
    	if(currentStep==8){
    		saveAuditUsers(currentStep);
    		return;
    	}else if(currentStep==1||currentStep==2||currentStep==3||currentStep==4||currentStep==5||currentStep==9||currentStep==10){
    		if(currentStep==1&&!checkBusTypeTree()){
    			return;
    		}
    		var validateResult =$("#StepForm_"+currentStep).validationEngine("validate");
    		if(!validateResult){
    			return;
    		}else{
    			submitInfo(currentStep);
    		}
    	}else{
    		changeNextStepPro(currentStep,nextStep);
    	}
    	
    }
    
    function formatReceiveTypes(val,row){
		if(val=="1"){
			return "纸质收取";
		}else if(val=="2"){
			return "上传";
		}
	};
	
	function formatIsNeed(val,row){
		if(val=="1"){
			return "是";
		}else if(val=="0"){
			return "否";
		}
	};
	

	$(function() {
		$("input[name='FWDX']").click(function(){
			//获取值
			var fwdxValue = $(this).val();
			//获取目前选中的服务对象
			var selectedFwdx = $("input[name='SELECTEDFWDX']").val();
			if(selectedFwdx!=fwdxValue){
				var checkboxGroup = $("div[name='BUS_TYPEIDS']");
				AppUtil.reloadCheckbox(checkboxGroup,{
					dataParams:fwdxValue
				});
				$("input[name='SELECTEDFWDX']").val(fwdxValue);
			}
		});
		
		$("input[name='SFSF']").click(function(){
			//获取值
			var sfsfValue = $(this).val();
			if(sfsfValue=="1"){
				$("#SFFS_TR").attr("style","");
				$("#SFBZJYJ_TR").attr("style","");
			}else{
				$("#SFFS_TR").attr("style","display:none;");
				$("#SFBZJYJ_TR").attr("style","display:none;");
			}
		});
		
	      $("input[name='RZBSDTFS']").click(function(){
	            //获取值
	            var rzbsdtfsValue = $(this).val();
	            var text=$(this)[0].nextSibling.nodeValue;
	            if(rzbsdtfsValue=="in02"){
	                $("#APPLY_URL_TR").attr("style","");
	            }else{
	                $("#APPLY_URL_TR").attr("style","display:none;");
	            }
	        });
		var oldsbtext=$("input[name='RZBSDTFS']:checked")[0].nextSibling.nodeValue;
		$("input[name='OLDRZBSDTFS_TEXT']").val(oldsbtext);
		for(var i=1;i<=5;i++){
			AppUtil.bindValidateEngine({
				selector:"#StepForm_"+i
			});
		}
		
		if("${serviceItem.DOCUMENT_NAMES}"!=""){
        	addtoTD("documentnames","${serviceItem.DOCUMENT_NAMES}");
        }
        if("${serviceItem.READ_NAMES}"!=""){
        	addtoTD("readnames","${serviceItem.READ_NAMES}");
        }
        if("${serviceItem.TICKETS_NAMES}"!=""){
            addtoTD("ticketsnames","${serviceItem.TICKETS_NAMES}");
        }
        if("${serviceItem.BDLCDYNAME}"!=""){
            addtoTD("defname","${serviceItem.BDLCDYNAME}");
        }
        if("${serviceItem.PREAUDITER_NAMES}"!=""){
            addtoTD("auditusernames","${serviceItem.PREAUDITER_NAMES}");
        }
        if("${serviceItem.BINDFORM_NAMES}"!=""){
            addtoTD("bdname","${serviceItem.BINDFORM_NAMES}");
        }
        var needCheckIds= $("input[name='BUS_TYPEIDS']").val();
        var busTypeTreeSetting = {
        		check:{
        			enable: true,
                    chkboxType :{
                        "Y":"ps",
                        "N":"s"
                        }
        		},
        		edit : {
                    enable : false,
                    showRemoveBtn : false,
                    showRenameBtn : false
                },
                view : {
                    selectedMulti : false
                },
                async : {
                    enable : true,
                    url : "baseController.do?tree",
                    otherParam : {
                        "tableName" : "T_WSBS_BUSTYPE",
                        "idAndNameCol" : "TYPE_ID,TYPE_NAME",
                        "targetCols" : "PARENT_ID,PATH,TYPE_CODE",
                        "rootParentId" : "0",
                        "needCheckIds":needCheckIds,
                        "isShowRoot" : "false",
                        "rootName" : "服务类别树"
                    }
                }
            };
            $.fn.zTree.init($("#busTypeTree"), busTypeTreeSetting);
        
	});
	/**
     * 编辑申请材料列表记录
     */
    function editApplyMaterGridRecord() {
        var MATER_ID = AppUtil.getEditDataGridRecord("ApplyMaterGrid");
        if (MATER_ID) {
            showApplyMaterWindow(MATER_ID);
        }
    }

    /**
     * 显示申请材料信息对话框
     */
    function showApplyMaterWindow(entityId) {
        //获取事项ID
        var itemId = $("input[name='ITEM_ID']").val();
        $.dialog.open("applyMaterController.do?info&entityId=" + entityId + "&itemId=" + itemId,
                {
                    title : "申请材料信息",
                    width : "600px",
                    height : "400px",
                    lock : true,
                    resize : false,
                    close: function () {
                    	 $("input[name='Q_SM.ITEM_ID_EQ']").val(itemId);
                         AppUtil.gridDoSearch("ApplyMaterToolbar", "ApplyMaterGrid");
                    }
                }, false);
    };
    
    function selectAuditTickets(){
        var ticketsIds = $("input[name='TICKETS_IDS']").val();
        parent.$.dialog.open("ticketsController.do?selector&ticketsIds="
                + ticketsIds, {
            title : "票单选择器",
            width : "800px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
            	var selectTicketsInfo = art.dialog.data("selectTicketsInfo");
            	//获取事项ID
                var itemId = $("input[name='ITEM_ID']").val();
                
                if(selectTicketsInfo&&selectTicketsInfo.ticketsIds){
                    AppUtil.ajaxProgress({
                    	url : "serviceItemController.do?bindTickets",
                        params : {
                            "itemId" : itemId,
                            "ticketsIds" : selectTicketsInfo.ticketsIds
                        },
                        callback : function(resultJson) {
                            if (resultJson.success) {
                            	parent.art.dialog({
                                    content: resultJson.msg,
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                            	$("input[name='TICKETS_IDS']").val(selectTicketsInfo.ticketsIds);
                                //$("textarea[name='TICKETS_NAMES']").val(selectTicketsInfo.ticketsNames);
                                addtoTD("ticketsnames",selectTicketsInfo.ticketsNames);
                                art.dialog.removeData("selectTicketsInfo");
                            	
                            } else {
                                parent.art.dialog({
                                    content : resultJson.msg,
                                    icon : "error",
                                    ok : true
                                });
                            }
                        }
                    });
                    
                }
            }
        }, false);
        
    };
    function selectAuditDocument(){
        var documentIds = $("input[name='DOCUMENT_IDS']").val();
        parent.$.dialog.open("documentTemplateController.do?selector&documentIds="
                + documentIds, {
            title : "公文选择器",
            width : "800px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectDocumentInfo = art.dialog.data("selectDocumentInfo");
                //获取事项ID
                var itemId = $("input[name='ITEM_ID']").val();
                
                if(selectDocumentInfo&&selectDocumentInfo.documentIds){
                    AppUtil.ajaxProgress({
                        url : "serviceItemController.do?bindDocument",
                        params : {
                            "itemId" : itemId,
                            "documentIds" : selectDocumentInfo.documentIds
                        },
                        callback : function(resultJson) {
                            if (resultJson.success) {
                                parent.art.dialog({
                                    content: resultJson.msg,
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                                $("input[name='DOCUMENT_IDS']").val(selectDocumentInfo.documentIds);
                                //$("textarea[name='DOCUMENT_NAMES']").val(selectDocumentInfo.documentNames);
                                addtoTD("documentnames",selectDocumentInfo.documentNames);
                                art.dialog.removeData("selectDocumentInfo");
                                
                            } else {
                                parent.art.dialog({
                                    content : resultJson.msg,
                                    icon : "error",
                                    ok : true
                                });
                            }
                        }
                    });
                    
                }
            }
        }, false);
        
    };
    function selectAuditRead(){
    	var readIds = $("input[name='READ_IDS']").val();
        parent.$.dialog.open("readTemplateController.do?selector&readIds="
                + readIds, {
            title : "阅办模板选择器",
            width : "800px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectReadInfo = art.dialog.data("selectReadInfo");
                //获取事项ID
                var itemId = $("input[name='ITEM_ID']").val();
                
                if(selectReadInfo&&selectReadInfo.readIds){
                    AppUtil.ajaxProgress({
                        url : "serviceItemController.do?bindRead",
                        params : {
                            "itemId" : itemId,
                            "readIds" : selectReadInfo.readIds
                        },
                        callback : function(resultJson) {
                            if (resultJson.success) {
                                parent.art.dialog({
                                    content: resultJson.msg,
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                                $("input[name='READ_IDS']").val(selectReadInfo.readIds);
                               // $("textarea[name='READ_NAMES']").val(selectReadInfo.readNames);
                               addtoTD("readnames",selectReadInfo.readNames);
                                art.dialog.removeData("selectReadInfo");
                                
                            } else {
                                parent.art.dialog({
                                    content : resultJson.msg,
                                    icon : "error",
                                    ok : true
                                });
                            }
                        }
                    });
                    
                }
            }
        }, false);
        
    }
    //通过或者退回
    function doPassOrBack(){
        var itemId = $("input[name='ITEM_ID']").val();
        $.dialog.open("serviceItemController.do?auditingBackInfo&itemIds=" + itemId+"&state=${serviceItem.FWSXZT}", {
            title : "审核意见",
            width: "600px",
            height: "400px",
            fixed: true,
            lock : true,
            resize : false,
            close: function () {
                var backinfo = art.dialog.data("backinfo");
                if(backinfo&&backinfo.back){
                    parent.art.dialog({
                        content: "提交成功",
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                    art.dialog.removeData("backinfo");
                    AppUtil.closeLayer();
                }
            }
        }, false);
    }
    
    function addtoTD(id,values){
    	var v = values.split(",");
    	var showtext="";
    	for(var i=0;i<v.length;i++){
    		showtext +="<p>"+v[i]+"</P>";
    	}
    	$("#"+id).html(showtext);
    }
    //选择所属目录
    function selectCatalog(){
        var catalogCode = $("input[name='CATALOG_CODE']").val();
        var catalogName = $("input[name='CATALOG_NAME']").val();
        parent.$.dialog.open("catalogController.do?selector&allowCount=1&catalogCode="
                + catalogCode+"&catalogName="+catalogName, {
            title : "目录选择器",
            width : "1000px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
                if(selectCatalogInfo&&selectCatalogInfo.catalogCode){
                	if($("input[name='CATALOG_CODE']").val()==selectCatalogInfo.catalogCode){
                		
                	}else{
                		$("input[name='CATALOG_CODE']").val(selectCatalogInfo.catalogCode);
                		$("input[name='CATALOG_NAME']").val(selectCatalogInfo.catalogName);
                		$.post("serviceItemController.do?getMaxNumCode",{
                			catalogCode:selectCatalogInfo.catalogCode
                        }, function(responseText, status, xhr) {
                            var resultJson = $.parseJSON(responseText);
                            if(resultJson.success == true){
                            	$("input[name='NUM_CODE']").val(resultJson.jsonString);
                            	$("input[name='ITEM_CODE']").val(selectCatalogInfo.catalogCode+resultJson.jsonString);
                            }else{
                                art.dialog({
                                    content: resultJson.msg,
                                    icon:"error",
                                    ok: true
                                });
                            }
                    });
                		
                	}
                }
       
            }
        }, false);
        
    }
    //检测类别树是否有被选择
    function checkBusTypeTree(){
        var treeObj = $.fn.zTree.getZTreeObj("busTypeTree");
        var nodes = treeObj.getCheckedNodes(true);
        var checkIds = "";
        var checkNames = "";
        var checkRecords = [];
        if(nodes!=null&&nodes.length>0){
            for(var i =0;i<nodes.length;i++){
                if(nodes[i].id!="0"){
                    checkIds+=nodes[i].id+",";
                    checkNames+=nodes[i].name+",";
                    checkRecords.push(nodes[i].id);
                }
            }
            if(checkRecords.length>=1){
                checkIds = checkIds.substring(0,checkIds.length-1);
                checkNames = checkNames.substring(0,checkNames.length-1);
                $("input[name='BUS_TYPEIDS']").val(checkIds);
                $("input[name='BUS_TYPENAMES']").val(checkNames);
                return true;
            }
        }else{
            art.dialog({
                content: "请至少选择一条服务类别记录!",
                icon:"warning",
                ok: true
            });
            return false;
        }
    }
    
    function isneedApplyMaterGridRecord(){
    	//获取事项ID
        var itemId = $("input[name='ITEM_ID']").val();
        updateIsneedApplyMater("applyMaterController.do?updateIsneed&isneed=1&itemId="+itemId,"ApplyMaterGrid");
    }
    function noneedApplyMaterGridRecord(){
        //获取事项ID
        var itemId = $("input[name='ITEM_ID']").val();
        updateIsneedApplyMater("applyMaterController.do?updateIsneed&isneed=0&itemId="+itemId,"ApplyMaterGrid");
    }
    //更新材料是否为必须提供
    function updateIsneedApplyMater(url,gridId){
        var $dataGrid = $("#"+gridId);
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要修改的记录!",
                icon:"warning",
                ok: true
            });
        }else{
            art.dialog.confirm("您确定要修改该记录吗?", function() {
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
                                $dataGrid.datagrid('reload');
                                $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
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
    
    function selectBindForm(){
        var bindFormIds = $("input[name='BINDFORM_IDS']").val();
        //获取事项ID
        var itemId = $("input[name='ITEM_ID']").val();
        parent.$.dialog.open("serviceItemFormController.do?selector&allowCount=3&bindFormIds="+bindFormIds, {
            title : "绑定表单选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectBindFormInfo = art.dialog.data("selectBindFormInfo");
                if(selectBindFormInfo){
                    $("input[name='BINDFORM_IDS']").val(selectBindFormInfo.bindFormIds);
                    AppUtil.ajaxProgress({
                        url:"serviceItemController.do?bindForm",
                        params:{
                            "ITEM_ID":itemId,
                            "bindFormIds":selectBindFormInfo.bindFormIds
                        },
                        callback:function(resultJson){
                            addtoTD("bdname",selectBindFormInfo.bindFormNames);
                        }
                    })
                    art.dialog.removeData("selectBindFormInfo");
                }
            }
        }, false);
    }
    function filterApplyMaterGridRecord(){
    	var rowsData = $("#ApplyMaterGrid").datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要修改的记录!",
                icon:"warning",
                ok: true
            });
        }else{
    	parent.$.dialog.open("applyMaterController.do?filterParameter", {
            title : "设置过滤参数",
            width:"400px",
            lock: true,
            resize:false,
            height:"100px",
            close: function () {
                var filterMaterInfo = art.dialog.data("filterMaterInfo");
                if(filterMaterInfo){
                    var colName = $("#ApplyMaterGrid").datagrid('options').idField;  
                    var materIds = "";
                    for ( var i = 0; i < rowsData.length; i++) {
                        if(i>0){
                        	materIds+=",";
                        }
                        materIds+=eval('rowsData[i].'+colName);
                    }
                    var itemId = $("input[name='ITEM_ID']").val();
                    AppUtil.ajaxProgress({
                        url:"applyMaterController.do?updateFilterPara",
                        params:{
                        	"materIds" : materIds,
                        	"itemId" : itemId,
                            "fpara":filterMaterInfo.fpara
                        },
                        callback:function(resultJson){
                        	if(resultJson.success == true){
                                art.dialog({
                                    content: resultJson.msg,
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                                    $("#ApplyMaterGrid").datagrid('reload');
                                    $("#ApplyMaterGrid").datagrid('clearSelections').datagrid('clearChecked');
                            }else{
                                art.dialog({
                                    content: resultJson.msg,
                                    icon:"error",
                                    ok: true
                                });
                            }
                        }
                    })
                    art.dialog.removeData("filterMaterInfo");
                }
            }
        }, false);
      }
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div id="SysRoleGrantFormDiv" class="easyui-layout" fit="true" >
	    <input type="hidden" name="CurrentStep" value="1">
	    <div data-options="region:'north',split:true,border:false" style="height: 100px;"  >
	        <div class="flow_container">
		        <ul>
		            <li class="flow_active" id="flow_node_1"><div class="pro_base">1</div><span onclick="jumpToTargetDiv(1);">基本信息</span></li>
		            <li class="flow_nodone" id="flow_node_2"><div class="pro_base">2</div><span onclick="jumpToTargetDiv(2);">法定依据及申请条件</span></li>
		            <li class="flow_nodone" id="flow_node_3"><div class="pro_base">3</div><span onclick="jumpToTargetDiv(3);">申报方式</span></li>
		            <li class="flow_nodone" id="flow_node_4"><div class="pro_base">4</div><span onclick="jumpToTargetDiv(4);">时限配置</span></li>
		            <li class="flow_nodone" id="flow_node_5"><div class="pro_base">5</div><span onclick="jumpToTargetDiv(5);">办公指引</span></li>
		            <li class="flow_nodone" id="flow_node_6"><div class="pro_base">6</div><span onclick="jumpToTargetDiv(6);">申请材料</span></li>
		            <li class="flow_nodone" id="flow_node_7"><div class="pro_base">7</div><span onclick="jumpToTargetDiv(7);">常见问题</span></li>
		            <li class="flow_nodone" id="flow_node_8"><div class="pro_base">8</div><span onclick="jumpToTargetDiv(8);">预审人员</span></li>
		            <li class="flow_nodone" id="flow_node_9"><div class="pro_base">9</div><span onclick="jumpToTargetDiv(9);">办理结果 </span></li>
		            <li class="flow_nodone" id="flow_node_10"><div class="pro_base">10</div><span onclick="jumpToTargetDiv(10);">其他配置</span></li>
		            <li class="flow_nodone" id="flow_node_11"><div class="pro_base">11</div><span onclick="jumpToTargetDiv(11);">操作日志</span></li>
		        </ul>
		        <div class="flow_progress">
		            <div class="flow_progress_bar">
		                <span class="flow_progress_highlight" id="progress_bar" style="width:0%;"></span>
		            </div>
		        </div>
		    </div>
		</div>
	
		<div data-options="region:'center'" style="background-color: #f7f7f7;">
				<div title="基本信息" id="StepDiv_1" style="width:100%;height:100%;"  >
				    <div class="easyui-layout" fit="true" >
				     <div data-options="region:'west',split:false"
				        style="width:250px;">
				        <div class="right-con">
				            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				                <div class="e-toolbar-ct">
				                    <div class="e-toolbar-overflow">
				                        <div style="color:#005588;">
				                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
				                                style="position: relative; top:7px; float:left;" />&nbsp;服务类别树
				                        </div>
				                    </div>
				                </div>
				            </div>
				        </div>
				        <ul id="busTypeTree" class="ztree"></ul>
				    </div>
				    <div data-options="region:'center'" style="background-color: #f7f7f7;">
				     <form action="serviceItemController.do?saveOrUpdate" id="StepForm_1">
				    <!--==========隐藏域部分开始 ===========-->
					<input type="hidden" name="ITEM_ID" value="${serviceItem.ITEM_ID}">
					<input type="hidden" name="SELECTEDFWDX" value="${serviceItem.FWDX}" >
		            <input type="hidden" name="BUS_TYPEIDS" value="${serviceItem.BUS_TYPEIDS}" >
		            <input type="hidden" name="BUS_TYPENAMES" value="${serviceItem.BUS_TYPENAMES}" >
		            <input type="hidden" name="CATALOG_CODE" value="${serviceItem.CATALOG_CODE}" >
		            <input type="hidden" name="NUM_CODE" value="${serviceItem.NUM_CODE}" >
		            <input type="hidden" name="SXXZ" value="${serviceItem.SXXZ}" >
		            <!--==========隐藏域部分结束 ===========-->
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<tr style="height:29px;">
							<td colspan="1" class="dddl-legend" style="font-weight:bold;">主要信息</td>
						</tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">所属目录：</span>
                                <input type="text" style="width:400px;float:left;" maxlength="62"
                                class="eve-input validate[required]"
                                value="${serviceItem.CATALOG_NAME}" name="CATALOG_NAME" readonly="readonly"/><font
                                class="dddl_platform_html_requiredFlag">*</font>
                                <c:if test="${serviceItem.FWSXZT!=1}">
                                <input value="选择目录" type="button" class="eve_inpbtn" onclick="selectCatalog()"/>
                                </c:if>
                                </td>
                        </tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">事项名称：</span>
								<input type="text" style="width:400px;float:left;" maxlength="62"
								class="eve-input validate[required]"
								value="${serviceItem.ITEM_NAME}" name="ITEM_NAME" /><font
								class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">事项编码：</span>
								<input type="text" style="width:150px;float:left;" maxlength="30"
								class="eve-input validate[required,custom[onlyLetterNumber]]"
								readonly="readonly"
								value="${serviceItem.ITEM_CODE}" name="ITEM_CODE" /><font
								class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">办件类型：</span>
							    <eve:eveselect clazz="eve-input validate[required]" dataParams="ServiceItemType"
							          value="${serviceItem.SXLX}"
							         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择办件类型"
							         name="SXLX">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">数量限制说明：</span>
								<input type="text" style="width:400px;float:left;" maxlength="126"
								class="eve-input" value="${serviceItem.SLXZSM}" name="SLXZSM" /></td>
						</tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">主办处室：</span>
                                <input type="text" style="width:400px;float:left;" maxlength="200"
                                class="eve-input " value="${serviceItem.ZBCS}" name="ZBCS" /></td>
                        </tr>
                        <tr>
                            <td><span style="width: 100px;float:left;text-align:right;">联系人：</span>
                                <input type="text" style="width:400px;float:left;" maxlength="200"
                                class="eve-input " value="${serviceItem.LXR}" name="LXR" /></td>
                        </tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">联系电话：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[120]]"
                                    style="width: 400px" name="LXDH">${serviceItem.LXDH}</textarea></td>
                        </tr>
                        <tr>
                            <td><span style="width: 100px;float:left;text-align:right;">监督电话：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[120]]"
                                    style="width: 400px" name="JDDH">${serviceItem.JDDH}</textarea></td>
                        </tr>
                        <tr>
                            <td><span style="width: 100px;float:left;text-align:right;">事项星级：</span>
                                <eve:eveselect clazz="eve-input" 
                                    dataParams="starType"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="===选择事项星级==" name="ITEMSTAR" value="${serviceItem.ITEMSTAR}"></eve:eveselect></td>
                        </tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">备注说明：</span>
								<textarea class="eve-textarea"
									style="width: 400px;height:100px;" name="BZSM">${serviceItem.BZSM}</textarea></td>
						</tr>
						<tr>
							<td><span
								style="width: 100px;float:left;text-align:right;">关键字：</span>
								<input type="text" style="width:400px;float:left;"
								maxlength="126" class="eve-input"
								value="${serviceItem.KEYWORD}" name="KEYWORD" /><font class="dddl_platform_html_requiredFlag">(多个关键字以“|”分割)</font></td>
						</tr>
					</table>
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<tr style="height:29px;">
							<td colspan="1" class="dddl-legend" style="font-weight:bold;">收费信息</td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">是否收费：</span>
							    <eve:radiogroup typecode="YesOrNo" width="130px" value="${serviceItem.SFSF}" 
							       fieldname="SFSF">
							    </eve:radiogroup>
								<font class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr id="SFFS_TR" <c:if test="${serviceItem.SFSF=='0'}">style="display: none;"</c:if> >
							<td><span style="width: 100px;float:left;text-align:right;">收费方式：</span>
							    <eve:excheckbox dataInterface="dictionaryService.findDatasForSelect" name="SFFS" width="350px;"
							       clazz="checkbox" dataParams="ChargeType"
							      value="${serviceItem.SFFS}">
							     </eve:excheckbox>
							</td>
						</tr>
						<tr id="SFBZJYJ_TR"
						   <c:if test="${serviceItem.SFSF=='0'}">style="display: none;"</c:if>
						>
							<td><span style="width: 100px;float:left;text-align:right;">收费标准及依据：</span>
								<textarea style="width: 400px;height: 100px;" class="eve-textarea" name="SFBZJYJ">${serviceItem.SFBZJYJ}</textarea>
							</td>
						</tr>
					</table>
					</form>
					</div>
					</div>
				</div>
				
				<div title="法定依据及申请条件" style="display: none;" id="StepDiv_2">
				    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_2">
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<tr style="height:29px;">
							<td colspan="1" class="dddl-legend" style="font-weight:bold;">依据条件</td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">行使依据：</span>
								<textarea rows="5" cols="5" class="eve-textarea"
									style="width: 600px" name="XSYJ">${serviceItem.XSYJ}</textarea></td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">申请条件及事项说明：</span>
								<textarea rows="5" cols="5" class="eve-textarea validate[maxSize[2000]]"
									style="width: 600px;height:180px;" name="SQTJ">${serviceItem.SQTJ}</textarea></td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">办理流程：</span>
								<textarea rows="5" cols="5" class="eve-textarea"
									style="width: 600px;height:180px;" name="BLLC">${serviceItem.BLLC}</textarea></td>
						</tr>
					</table>
					</form>
				</div>
				<div title="申报方式" style="display: none;" id="StepDiv_3">
				    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_3">
				    	<input type="hidden" name="OLDRZBSDTFS_TEXT" value="">
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<tr style="height:29px;">
							<td colspan="1" class="dddl-legend" style="font-weight:bold;">申请方式</td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">入驻办事大厅方式：</span>
							    <eve:radiogroup typecode="RZBSDTFS" width="150px" value="${serviceItem.RZBSDTFS}"
							       fieldname="RZBSDTFS">
							    </eve:radiogroup>
							</td>
						</tr>
						<tr id="APPLY_URL_TR"
                           <c:if test="${serviceItem.RZBSDTFS!='in02'}">style="display: none;"</c:if>>
							<td><span style="width: 150px;float:left;text-align:right;">申报网址：</span>
								<input type="text" style="width:400px;float:left;" maxlength="254" class="eve-input"
								value="${serviceItem.APPLY_URL}" name="APPLY_URL" />
								</td>
						</tr>
					</table>
					<p>
					1、指南式：仅在平潭办事大厅发布详细的办事指南信息，必须到物理窗口申请；<br>
					2、链接式：在平潭办事大厅发布详细的办事指南信息且提供网上申请链接；（选择该方式，必须填写有效的互联网申请链接用于申报人申请跳转）<br>
                    3、收办分离式：审批人员在平潭办事大厅进行网上预审，预审通过后分发办件到部门自建的业务系统或者各级行政服务中心业务办理系统进行办理；
                                                                            （选择该方式，必须选择与省网上办事大厅已实现对接的业务办理系统）<br>
                    4、全流程：事项入驻办事大厅，并在平潭办事大厅的行政审批和公共服务事项业务办理系统进行网上申报预审和业务办理。
					</p>
					</form>
				</div>
				<div title="时限配置" style="display: none;" id="StepDiv_4">
				    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_4">
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<tr style="height:29px;">
							<td colspan="1" class="dddl-legend" style="font-weight:bold;">时限配置</td>
						</tr>
						<tr>
							<td id="step4WeekLimit"><span style="width: 100px;float:left;text-align:right;">承诺时限工作日：</span>
							
								<input type="text" style="width:150px;float:left;" maxlength="3"
								class="eve-input validate[required,custom[integer],min[0]]" 
								<c:if test="${serviceItem.SXLX==1}"> readonly="readonly" value="0" </c:if>
								value="${serviceItem.CNQXGZR}" name="CNQXGZR" />
								<font class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr>
							<td ><span style="width: 100px;float:left;text-align:right;">法定时限工作日：</span>
							
								<input type="text" style="width:150px;float:left;" maxlength="3"
								class="eve-input validate[required,custom[integer],min[0]]" 
								<c:if test="${serviceItem.SXLX==1}"> readonly="readonly" value="0" </c:if>
								value="${serviceItem.FDSXGZR}" name="FDSXGZR" />
								<font class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">承诺时限说明：</span>
								<textarea rows="5" cols="5" class="eve-textarea validate[maxSize[500]]"
									style="width: 400px" name="CNQXSM">${serviceItem.CNQXSM}</textarea></td>
						</tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">法定时限说明：</span>
								<textarea rows="5" cols="5" class="eve-textarea validate[maxSize[500]]"
									style="width: 400px" name="FDQX">${serviceItem.FDQX}</textarea></td>
						</tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">外网预审时限：</span>
                                <input type="text" style="width:150px;float:left;" maxlength="3"
                                class="eve-input validate[custom[integer],min[0]]"
                                value="${serviceItem.PREDAY}" name="PREDAY" />工作日</td>
                        </tr>
                        <tr>
                            <td><span style="width: 100px;float:left;text-align:right;">收件时限：</span>
                                <input type="text" style="width:150px;float:left;" maxlength="3"
                                class="eve-input validate[custom[integer],min[0]]"
                                value="${serviceItem.RECEIVEDAY}" name="RECEIVEDAY" />工作日</td>
                        </tr>
					</table>
					</form>
				</div>
				<div title="办公指引" style="display: none;" id="StepDiv_5" >
				    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_5">
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<tr style="height:29px;">
							<td colspan="1" class="dddl-legend" style="font-weight:bold;">
								窗口办理方式</td>
						</tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">办理窗口：</span>
                                <input type="text" style="width:400px;float:left;"
                                maxlength="62" class="eve-input" value="${serviceItem.SSZTMC}"
                                name="SSZTMC" /></td>
                        </tr>
                        <tr>
                            <td><span style="width: 100px;float:left;text-align:right;">对外办公时间：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[120]]"
                                    style="width: 400px" name="BGSJ">${serviceItem.BGSJ}</textarea></td>
                        </tr>
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">办理地点：</span>
								<textarea rows="5" cols="5" class="eve-textarea validate[maxSize[120]]"
									style="width: 400px" name="BLDD">${serviceItem.BLDD}</textarea></td>
						</tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">交通指引：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[250]]"
                                    style="width: 400px" name="TRAFFIC_GUIDE">${serviceItem.TRAFFIC_GUIDE}</textarea></td>
                        </tr>
						<tr style="height:29px;">
                            <td colspan="1" class="dddl-legend" style="font-weight:bold;">
                                                                                                 网上办理方式</td>
                        </tr>
						<tr>
                            <td><span style="width: 100px;float:left;text-align:right;">网上办理方式：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[400]]"
                                    style="width: 400px" name="WSSQFS">${serviceItem.WSSQFS}</textarea></td>
                        </tr>
					</table>
					</form>
				</div>
				<div title="申请材料" style="display: none;height: 400px;" id="StepDiv_6" >
					    <div id="ApplyMaterToolbar">
					        <input type="hidden" name="Q_SM.ITEM_ID_EQ" value="${serviceItem.ITEM_ID}">
							<div class="right-con">
								<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
									<div class="e-toolbar-ct">
										<div class="e-toolbar-overflow">
											<a href="#" class="easyui-linkbutton" 
												iconCls="icon-add" plain="true"
												onclick="bindMater();">绑定材料</a> 
											<a href="#" class="easyui-linkbutton" 
												iconCls="icon-remove" plain="true"
												onclick="deleteBindApplyMater();">移除材料</a> 
											<a href="#" class="easyui-linkbutton" iconCls="icon-tick" plain="true"
								               onclick="updateDicSn();">保存排序</a>
											<a href="#"
												class="easyui-linkbutton" resKey="EDIT_FlowDef"
												iconCls="icon-note-add" plain="true"
												onclick="showApplyMaterWindow();">新建材料</a> 
											<a href="#"
												class="easyui-linkbutton" resKey="EDIT_FlowDef"
												iconCls="icon-note-edit" plain="true"
												onclick="editApplyMaterGridRecord();">编辑材料</a>
										    <a href="#"
                                                class="easyui-linkbutton" resKey="EDIT_FlowDef"
                                                iconCls="icon-ok" plain="true"
                                                onclick="isneedApplyMaterGridRecord();">设置必须提供</a>  
                                             <a href="#"
                                                class="easyui-linkbutton" resKey="EDIT_FlowDef"
                                                iconCls="icon-no" plain="true"
                                                onclick="noneedApplyMaterGridRecord();">设置非必须提供</a>
                                             <a href="#"
                                                class="easyui-linkbutton" resKey="EDIT_FlowDef"
                                                iconCls="icon-edit" plain="true"
                                                onclick="filterApplyMaterGridRecord();">设置过滤参数</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<table class="easyui-datagrid" rownumbers="true" pagination="false"
							id="ApplyMaterGrid" fitColumns="true" toolbar="#ApplyMaterToolbar"
							method="post" idField="MATER_ID" checkOnSelect="false" 
							fit="true" selectOnCheck="false" border="false"
							data-options="onLoadSuccess:function(){
							     $(this).datagrid('enableDnd');
							}"
							url="applyMaterController.do?forItemDatas&start=0&limit=100&itemId=${serviceItem.ITEM_ID}">
							<thead>
								<tr>
									<th field="ck" checkbox="true"></th>
									<th data-options="field:'MATER_ID',hidden:true" width="80">MATER_ID</th>
									<th data-options="field:'MATER_SN',align:'left'" width="50">排序值</th>
									<th data-options="field:'MATER_CODE',align:'left'" width="100">材料编码</th>
									<th data-options="field:'MATER_NAME',align:'left'" width="200">材料名称</th>
									<th data-options="field:'MATER_ISNEED',align:'left'" width="50" formatter="formatIsNeed">是否必须提供</th>
									<th data-options="field:'MATER_FPARA',align:'left'" width="200">材料过滤参数</th>
								</tr>
							</thead>
						</table>
				</div>
				<div title="常见问题" style="display: none;height: 400px;" id="StepDiv_7" >
					<div id="CommonProblemToolbar">
						<!--====================开始编写隐藏域============== -->
						<!--====================结束编写隐藏域============== -->
						<div class="right-con">
							<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
								<div class="e-toolbar-ct">
									<div class="e-toolbar-overflow">
										<a href="#"
											class="easyui-linkbutton" resKey="EDIT_FlowDef"
											iconCls="icon-note-add" plain="true"
											onclick="showCommonProblemWindow();">新建问题</a> 
										<a href="#"
											class="easyui-linkbutton" resKey="EDIT_FlowDef"
											iconCls="icon-note-edit" plain="true"
											onclick="editCommonProblemGridRecord();">编辑问题</a> 
										<a href="#"
											class="easyui-linkbutton" resKey="EDIT_FlowDef"
											iconCls="icon-note-edit" plain="true"
											onclick="deleteCommonProblemGridRecord();">删除问题</a> 
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- =========================查询面板结束========================= -->
					<!-- =========================表格开始==========================-->
					<table class="easyui-datagrid" rownumbers="true" pagination="true"
						id="CommonProblemGrid" fitcolumns="true" 
						toolbar="#CommonProblemToolbar" method="post" idfield="PROBLEM_ID"
						checkonselect="false" selectoncheck="false" fit="true" border="false"
						>
						<thead>
							<tr>
								<th field="ck" checkbox="true"></th>
								<th data-options="field:'PROBLEM_ID',hidden:true" width="80">PROBLEM_ID</th>
								<th data-options="field:'PROBLEM_TITLE',align:'left'" width="300">问题标题</th>
								<th data-options="field:'LASTER_UPDATETIME',align:'left'" width="100">最后更新时间</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div title="预审人员" style="display: none;" id="StepDiv_8">
				    <input type="hidden" name="PREAUDITER_IDS" value="${serviceItem.PREAUDITER_IDS}">
					<table style="width:100%;border-collapse:collapse;font-size:14px;"
						class="dddl-contentBorder dddl_table">
						<tr>
                            <td style="text-align: center;" width="15%" >人员配置：</td>
                            <td id="auditusernames" width="65%"></td>
                            <td width="20%">
                                <input value="请选择人员" type="button" class="eve_inpbtn" onclick="selectAuditUser();"/>
                            </td>
                        </tr>
					</table>
				</div>
				
				<div title="办理结果" style="display: none;" id="StepDiv_9">
                    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_9">
                    <table style="width:100%;border-collapse:collapse;"
                        class="dddl-contentBorder dddl_table">
                        <tr style="height:29px;">
                            <td colspan="1" class="dddl-legend" style="font-weight:bold;">办理结果</td>
                        </tr>
                        <tr >
                            <td><span style="width: 100px;float:left;text-align:right;">办理结果：</span>
                                <eve:excheckbox dataInterface="dictionaryService.findDatasForSelect" name="FINISH_TYPE" width="350px;"
                                   clazz="checkbox" dataParams="FinishType"
                                  value="${serviceItem.FINISH_TYPE}">
                                 </eve:excheckbox>
                             </td>
                        </tr>
                        <tr >
                            <td><span style="width: 100px;float:left;text-align:right;">获取方式：</span>
                                <eve:excheckbox dataInterface="dictionaryService.findDatasForSelect" name="FINISH_GETTYPE" width="550px;"
                                   clazz="checkbox" dataParams="FinishGetType"
                                  value="${serviceItem.FINISH_GETTYPE}">
                                 </eve:excheckbox>
                             </td>
                        </tr>
                        <tr>
                            <td><span style="width: 100px;float:left;text-align:right;">结果获取说明：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[250]]" 
                                    style="width: 550px" name="FINISH_INFO">${serviceItem.FINISH_INFO}</textarea></td>
                        </tr>
                    </table>
                    <p>
                                                                                 填写是否可以代领，需要提供什么证明材料,如要邮递材料还应说明邮递地址、邮递接收人、接收人电话、是否有指定的快递等说明; 
                    </p>
                    </form>
                </div>
				
				<div title="其他配置" style="display: none;" id="StepDiv_10">
                    <table style="width:100%;border-collapse:collapse; font-size:14px;"
                        class="dddl-contentBorder dddl_table">
						<tr>
							<th width="15%" style="text-align:center; background:#ECECFF; border:1px solid #e0e0e0;">类型</th>
							<th width="65%" style="text-align:center; background:#ECECFF; border:1px solid #e0e0e0;">已绑定的记录</th>
							<th width="20%" style="text-align:center; background:#ECECFF; border:1px solid #e0e0e0;">操作</th>
						</tr>
						<!-- 
						<tr>
							<td style="text-align: center;">公文配置：<input type="hidden" name="DOCUMENT_IDS" value="${serviceItem.DOCUMENT_IDS}"></td>
							<td id="documentnames"></td>
							<td>
								<input value="请选择公文模板" type="button" class="eve_inpbtn" onclick="selectAuditDocument();"/>
							</td>
						</tr>
						 -->
						<tr>
							<td style="text-align: center;">票单配置：<input type="hidden" name="TICKETS_IDS" value="${serviceItem.TICKETS_IDS}"></td>
							<td id="ticketsnames"></td>
							<td><input value="请选择票单配置" type="button" class="eve_inpbtn" onclick="selectAuditTickets();"/></td>
						</tr>
						<tr>
                            <td style="text-align: center;">阅办配置：<input type="hidden" name="READ_IDS" value="${serviceItem.READ_IDS}"></td>
                            <td id="readnames"></td>
                            <td><input value="请选择阅办模板" type="button" class="eve_inpbtn" onclick="selectAuditRead()"/></td>
                        </tr>
                        <tr>
                            <td style="text-align: center;">流程配置：<input type="hidden" name="BDLCDYID" value="${serviceItem.BDLCDYID}"></td>
                            <td id="defname"></td>
                            <td><input value="请选择流程定义" type="button" class="eve_inpbtn" onclick="selectFlowDef()"/></td>
                        </tr>
                        <tr>
                            <td style="text-align: center;">表单配置：<input type="hidden" name="BINDFORM_IDS" value="${serviceItem.BINDFORM_IDS}"></td>
                            <td id="bdname"></td>
                            <td><input value="请选择配置表单"  type="button" class="eve_inpbtn" onclick="selectBindForm()"/></td>
                        </tr>
                    </table>
                    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_10">
                    <table style="width:100%;border-collapse:collapse;"
                        class="dddl-contentBorder dddl_table">
                        <tr style="height:29px;">
                            <td colspan="1" class="dddl-legend" style="font-weight:bold;">流程相关配置</td>
                        </tr>
                        <tr >
                            <td><span style="width: 100px;float:left;text-align:right;">是否支持挂起：</span>
                                <eve:eveselect clazz="eve-input validate[required]" dataParams="YesOrNo"
                                      value="${serviceItem.SFZCGQ}"
                                     dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择是否挂起" 
                                     name="SFZCGQ" >
                                </eve:eveselect>
                                <font class="dddl_platform_html_requiredFlag">*</font>
                             </td>
                        </tr>
                    </table>
                    </form>
                </div>
                <div title="操作日志" style="display: none;" id="StepDiv_11">
				    <form action="serviceItemController.do?saveOrUpdate" id="StepForm_11">
					<table style="width:100%;border-collapse:collapse;"
						class="dddl-contentBorder dddl_table">
						<c:if test="${!empty serviceItem.uplogList}">
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;">修改日志</td>
						    </tr>
						</c:if>
						<c:forEach items="${serviceItem.uplogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;">流转日志</td>
						    </tr>
						<c:forEach items="${serviceItem.fabulogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach>
						<c:forEach items="${serviceItem.canclogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="1" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach>
					</table>
					</form>
				</div>
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="上一步" type="button" id="preStepButton" disabled="disabled" onclick="doPre();"
					class="z-dlg-button z-dialog-cancelbutton" />
				 <input
					value="保存并下一步" type="button" id="nextStepButton" class="z-dlg-button z-dialog-okbutton aui_state_highlight"
					onclick="doNext();" />
					<c:if test="${shan!=null&&shan!=''}">
					<input
                    value="审核" type="button" id="backButton" class="z-dlg-button z-dialog-okbutton aui_state_highlight"
                    onclick="doPassOrBack();" />
                    </c:if>
			</div>
		</div>
	</div>

	
</body>

