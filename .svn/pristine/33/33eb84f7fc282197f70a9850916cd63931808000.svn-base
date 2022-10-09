var FlowUtil = new Object({
	/**
	 * 追回流程实例任务
	 * @param {} val
	 * @param {} row
	 */
	revokeFlowExe:function(gridId){
		var exeId = AppUtil.getEditDataGridRecord(gridId);
		if (exeId) {
			art.dialog.confirm("您确定要撤回该流程吗?", function() {
				var layload = layer.load("正在提交请求中…");
				$.post("executionController.do?revokeFlow",{
					    exeId:exeId
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
							FlowUtil.reloadFlowPublicGrid();
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
	 * 格式化经我办理标题字段
	 * @param {} val
	 * @param {} row
	 */
	formatHandleByMeSubject:function(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var subject = row.SUBJECT;
		var href = "<a href=\"javascript:void(0)\"";
		var EFLOW_ISQUERYDETAIL = "true";
		href+=" onclick=\"FlowUtil.showExecutionWindow('','"+exeId+"','"+EFLOW_ISQUERYDETAIL+"','"+subject+"','');\" ";
		href += " ><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	},
	/**
	 * 重新加载流程的公共列表部分
	 */
	reloadFlowPublicGrid:function(){
	    if(parent){
	    	parent.$("#MyApplyExecGrid").datagrid("reload");
			parent.$("#NeedMeHandleGrid").datagrid("reload");
			parent.$("#SjglGrid").datagrid("reload");
			parent.$("#YsglGrid").datagrid("reload");
			parent.$("#FoodNeedMeHandleGrid").datagrid("reload");
			parent.$("#CaseSourceGrid").datagrid("reload");
			parent.$("#CaseSourceNeedByHandleGrid").datagrid("reload");
			parent.$("#CaseSourceNeedByCheckGrid").datagrid("reload");
			parent.$("#GeneralCaseGrid").datagrid("reload");
			parent.$("#CaseNeedByCaseHandleGrid").datagrid("reload");
			parent.$("#CaseTransferGrid").datagrid("reload");
			parent.$("#NeedFillDissentGrid").datagrid("reload");
			parent.$("#NeedAuditDissentGrid").datagrid("reload");
			parent.$("#clueClaimGrid").datagrid("reload");
			parent.loadByMeHandledTaskDatas();
	    }
	},
	/**
	 * 重启流程任务
	 * @param {} gridId
	 */
	restartFlowTask:function(gridId){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var isAllowStart = true;
			var selectTaskIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				var TASK_STATUS = rowsData[i].TASK_STATUS;
				var TASK_ID  = rowsData[i].TASK_ID;
				if(TASK_STATUS!="-1"){
					isAllowStart = false;
					break;
				}
				if(i>0){
					selectTaskIds+=",";
				}
				selectTaskIds+=TASK_ID;
			}
			if(!isAllowStart){
				art.dialog({
					content: "只能选择被挂起的流程任务进行重启!",
					icon:"warning",
				    ok: true
				});
			}else{
				$.dialog.open("flowTaskController.do?goReStart&selectTaskIds="+selectTaskIds, {
					title : "重启流程",
					width : "700px",
					height : "250px",
					lock : true,
					resize : false,
					close: function () {
						$("#"+gridId).datagrid('reload');
						$("#"+gridId).datagrid('clearSelections').datagrid('clearChecked');
		    		}
				}, false);
				//AppUtil.operateDataGridRecord("flowTaskController.do?reStart",{selectTaskIds:selectTaskIds},gridId);
			}
		}
	},
	/**
	 * 格式化剩余工作日
	 * @param {} val
	 * @param {} row
	 */
	formatLeftDay:function(val,row){
		if(val=="0"){
			return "<font color='red'><b>今天截止</b></font>";
		}else if(val=="-1"){
			return "<font color='red'><b>已超期</b></font>";
		}else if(val!="-2"){
			return "<font color='red'><b>剩余"+val+"个工作日</font>";
		}else{
			return "无限制";
		}
	},
	/**
	 * 格式化流程标题
	 * @param {} val
	 * @param {} row
	 */
	formatFlowSubject:function(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//获取流程任务ID
		var taskId = row.TASK_ID;
		//获取流程任务状态
		var taskStatus = row.TASK_STATUS;
		var href = "<a href=\"javascript:void(0)\"";
		var EFLOW_ISQUERYDETAIL = "true";
		if(taskStatus!="1"){
			EFLOW_ISQUERYDETAIL = "true";
		}else{
			EFLOW_ISQUERYDETAIL = "false";
		}
		href+=" onclick=\"FlowUtil.showExecutionWindow('','"+exeId+"','"+EFLOW_ISQUERYDETAIL+"','"+val+"','"+taskId+"');\" ";
		href += " ><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	},
	/**
	 * 格式化流程标题,面向编辑办件的
	 */
	formatFlowSubjectForEdit:function(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var subject = row.SUBJECT;
		var href = "<a href=\"javascript:void(0)\"";	
		var username = "${sessionScope.curLoginUser.username}";
		if(username=="admin"){
			href+=" onclick=\"FlowUtil.showExecutionWindow('','"+exeId+"','true','"+val+"','','true');\" ";
		}else{
			var reskey = "${sessionScope.curLoginUser.resKeys}";
			if(reskey.indexOf("SPXZSJWHR")==-1||runStatus!="1"){
				href+=" onclick=\"FlowUtil.showExecutionWindow('','"+exeId+"','true','"+val+"','','false');\" ";
			}else if(runStatus=="1"&&reskey.indexOf("SPXZSJWHR")!=-1){
				href+=" onclick=\"FlowUtil.showExecutionWindow('','"+exeId+"','true','"+val+"','','true');\" ";
			}
		}
		href += " ><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	},
	/**
	 * 格式化流程任务状态
	 * @param {} val
	 * @param {} row
	 * @return {String}
	 */
	formatTaskStatus:function(val,row){
	  if(val=="1"){
	      return "<font color='green'><b>正在审核</b></font>";
	  }else if(val=="2"){
		  return "<font color='blue'><b>已审核</b></font>";
	  }else if(val=="3"){
		  return "<font color='red'><b>退回</b></font>";
	  }else if(val=="4"){
		  return "<font color='purple'><b>转发</b></font>";
	  }else if(val=="5"){
		  return "<font color='purple'><b>委托</b></font>";
	  }else if(val=="6"){
		  return "<font color='black'><b>结束流程</b></font>";
	  }else if(val=="-1"){
		  return "<font color='purple'><b>挂起</b></font>";
	  }
	},
	/**
	 * 重绘流程任务面板界面
	 */
	resizeFlowTaskList:function(){
		$("#FlowTaskGrid").datagrid("resize");   
	},
	/**
	 * 显示流程明细信息弹出框
	 * @param {} EFLOW_DEFKEY
	 * @param {} EFLOW_EXEID
	 * @param {} EFLOW_ISQUERYDETAIL
	 * @param {} SUBJECT
	 */
	showExecutionWindow:function(EFLOW_DEFKEY,EFLOW_EXEID,EFLOW_ISQUERYDETAIL,SUBJECT,EFLOW_CURRENTTASK_ID,EFLOW_FROMHISTROY,CALLBACK){
		var url = "executionController.do?redirectForm&EFLOW_DEFKEY="+EFLOW_DEFKEY+"&EFLOW_EXEID="+EFLOW_EXEID+
				"&EFLOW_ISQUERYDETAIL="+EFLOW_ISQUERYDETAIL+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&EFLOW_FROMHISTROY="+EFLOW_FROMHISTROY;
		$.dialog.open(url, {
			title : SUBJECT,
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
			resize : false,
			close:function(){
				if(CALLBACK){
					CALLBACK.call(this);
				}
			}
		}, false);
	},
	/**
	 * 获取流程变量对象
	 */
	getFlowVarsObj:function(){
		var EFLOW_VARS_JSON = $("input[name='EFLOW_VARS_JSON']").val();
		//将其转换成JSON对象
		var EFLOW_VARS = $.parseJSON(EFLOW_VARS_JSON);
		//var json = JSON.stringify(EFLOW_VARS);
		return EFLOW_VARS;
	},
	/**
	 * 提交表单申请数据
	 * @param {} flowSubmitObj
	 */
	submitFlowInfo:function(flowSubmitObj,EFLOW_CALLBACKFN){
		flowSubmitObj.flashvars = "";
		flowSubmitObj.movie = "";
		flowSubmitObj.applyMatersJson = "";
		flowSubmitObj.EFLOW_CURFIELDRIGHTS = null;
		flowSubmitObj.EFLOW_CURBUTTONRIGHTS = null;
		flowSubmitObj.EFLOW_CURAUDITRIGHTS = null;
		if(flowSubmitObj.EFLOW_IS_START_NODE=="true"){
			flowSubmitObj.EFLOW_APPLY_STATUS = "1";
		}
		if(AppUtil.isExitsFunction("getFlowPermisDocJson")){
	    	var EFLOW_PERMIS_DOCJSON = getFlowPermisDocJson();
	    	if(EFLOW_PERMIS_DOCJSON&&EFLOW_PERMIS_DOCJSON!=""){
	    		flowSubmitObj.EFLOW_PERMIS_DOCJSON = EFLOW_PERMIS_DOCJSON;
	    	}
    	}
		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "executionController.do?exeFlow",
			params : postParam,
			callback : function(resultJson) {
				if(resultJson.OPER_SUCCESS){
					if(resultJson.ITEM_CODE&&resultJson.ITEM_CODE!=""
						&&resultJson.EFLOW_ISTEMPSAVE!="1"&&resultJson.EFLOW_IS_START_NODE=='true'){
							
							parent.$.dialog.open("executionController.do?showFlowResultInfo&EFLOW_EXEID="+resultJson.EFLOW_EXEID, {
						        title : "提交成功！",
						        width:"800px",
						        lock: true,
						        resize:false,
						        height:"300px",
						        cancel:false,
						        close:function(){
						        		FlowUtil.reloadFlowPublicGrid();
							        	if(EFLOW_CALLBACKFN){
											EFLOW_CALLBACKFN.call(this,resultJson);
										}
						        }
						    }, false);
					}else{
						var msgContent = "";
						if(resultJson.EFLOW_ISTEMPSAVE&&resultJson.EFLOW_ISTEMPSAVE=='1'){
							msgContent = "暂存成功！";
						}else{
							msgContent = resultJson.OPER_MSG;
						}
						parent.art.dialog({
							content :msgContent,
							icon : "succeed",
							cancel:false,
							lock: true,
							ok: function(){
								//重新加载流程表格
								try{
									if(resultJson.EFLOW_ISTEMPSAVE=="1"&&resultJson.EFLOW_FROMHISTROY!='true'&&resultJson.EFLOW_IS_START_NODE=='true'){
										parent.FlowUtil.showExecutionWindow("",resultJson.EFLOW_EXEID,false,resultJson.EFLOW_DEFNAME,"");
									}
									if(resultJson.EFLOW_ISTEMPSAVE=="1"&&resultJson.EFLOW_FROMHISTROY!='true'&&resultJson.EFLOW_IS_START_NODE!='true'){
										parent.FlowUtil.showExecutionWindow("",resultJson.EFLOW_EXEID,false,resultJson.EFLOW_DEFNAME,resultJson.EFLOW_CURRENTTASK_ID);
									}
									if(resultJson.EFLOW_ISTEMPSAVE=="1"&&resultJson.EFLOW_FROMHISTROY=='true'){
										parent.FlowUtil.showExecutionWindow("",resultJson.EFLOW_EXEID,true,resultJson.EFLOW_DEFNAME,"","true");
									}
									FlowUtil.reloadFlowPublicGrid();
									if(EFLOW_CALLBACKFN){
										EFLOW_CALLBACKFN.call(this,resultJson);
									}
								}catch(e){
									
								}
							}
						});
					}
				}else{
					parent.art.dialog({
						content : resultJson.OPER_MSG,
						icon : "error",
						ok : true
					});
				}
			}
		});
	},
	/**
	 * 流程提交操作函数
	 */
	FLOW_SubmitFun:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
		var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		$.post("executionController.do?storeFlowSubmitInfoJson",{
			EFLOW_VARS_JSON:EFLOW_VARS_JSON
		}, function(responseText, status, xhr) {
			var EFLOW_NODECONFIG  = EFLOW_VARS.EFLOW_NODECONFIG;
			// 存储
			parent.$.dialog.open("executionController.do?goSubmitFlow", {
				title : "提交表单",
				width :EFLOW_NODECONFIG.WINDOW_WIDTH,
				height:EFLOW_NODECONFIG.WINDOW_HEIGHT,
				lock : true,
				resize : false,
				close: function () {
					if(EFLOW_CALLBACKFN){
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
						}
					}
	    		}
			}, false);
		});
	},
	/**
	 * 流程的退回操作
	 */
	FLOW_BackFun:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
		EFLOW_VARS.EFLOW_ISBACK = "true";
		var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		$.post("executionController.do?storeFlowSubmitInfoJson",{
			EFLOW_VARS_JSON:EFLOW_VARS_JSON
		}, function(responseText, status, xhr) {
			// 存储
			parent.$.dialog.open("executionController.do?goBackFlow&noSendMsg=true", {
				title : "退回流程",
				width : "1000px",
				height : "500px",
				lock : true,
				resize : false,
				close: function () {
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
					/*if(EFLOW_CALLBACKFN){
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
						}
					}*/
	    		}
			}, false);
		});
	},
	/**
	 * 流程预审补件操作
	 */
	FLOW_PreAuditPatchFun:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		parent.$.dialog.open("executionController.do?preAuditPatchFlow&EFLOW_EXEID="+EFLOW_EXEID
		+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID, {
			title : "预审补件",
			width : "1000px",
			height : "500px",
			lock : true,
			resize : false,
			close: function () {
				if(EFLOW_CALLBACKFN){
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
					}
				}else{
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
				}
    		}
		}, false);
	},
	/**
	 * 转办按钮
	 */
	FLOW_TransferFun:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		parent.$.dialog.open("executionController.do?transferFlow&USER_RANGE=1&EFLOW_EXEID="+EFLOW_EXEID
		+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID, {
			title : "流程转派",
			width : "1000px",
			height : "500px",
			lock : true,
			resize : false,
			close: function () {
				if(EFLOW_CALLBACKFN){
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
					}
				}else{
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
				}
    		}
		}, false);
	},
	/**
	 * 移送按钮
	 */
	FLOW_ChangePathFlow:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		parent.$.dialog.open("executionController.do?changePathFlow&EFLOW_EXEID="+EFLOW_EXEID
		+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID, {
			title : "流程移送",
			width : "1000px",
			height : "500px",
			lock : true,
			resize : false,
			close: function () {
				if(EFLOW_CALLBACKFN){
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
					}
				}else{
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
				}
    		}
		}, false);
	},
	/**
	 * 延期按钮
	 */
	FLOW_PunishDelay:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		parent.$.dialog.open("punishDelayController.do?info&EFLOW_EXEID="+EFLOW_EXEID
		+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID, {
			title : "申请延期",
			width : "1000px",
			height : "500px",
			lock : true,
			resize : false,
			close: function () {
				if(EFLOW_CALLBACKFN){
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
					}
				}else{
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
					}
					AppUtil.closeLayer();
				}
    		}
		}, false);
	},
	/**
	 * 转办同级
	 */
	FLOW_TransSameLevel:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var EFLOW_DEFKEY = EFLOW_VARS.EFLOW_DEFKEY;
		if(EFLOW_DEFKEY&&EFLOW_DEFKEY=="029"){
			parent.$.dialog.open("executionController.do?transferClueFlow&EFLOW_EXEID="+EFLOW_EXEID
					+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&USER_RANGE=1&noSendMsg=true&ROLE_CODE_RANG=AJGL_006", {
						title : "转办同级",
						width : "1000px",
						height : "350px",
						lock : true,
						resize : false,
						close: function () {
							var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
							if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
								art.dialog.removeData("EFLOW_VARS");
								AppUtil.closeLayer();
							}
			    		}
				 }, false);
		}else{
			parent.$.dialog.open("executionController.do?transferFlow&EFLOW_EXEID="+EFLOW_EXEID
				+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&USER_RANGE=1&noSendMsg=true", {
					title : "转办同级",
					width : "1000px",
					height : "350px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
			 }, false);
		}
	},
	/**
	 * 交办下级
	 */
	FLOW_TransSubLevel:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var EFLOW_DEFKEY = EFLOW_VARS.EFLOW_DEFKEY;
		if(EFLOW_DEFKEY&&EFLOW_DEFKEY=="029"){
			parent.$.dialog.open("executionController.do?transferClueFlow&EFLOW_EXEID="+EFLOW_EXEID
					+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&USER_RANGE=2&noSendMsg=true&ROLE_CODE_RANG=AJGL_006", {
						title : "交办下级",
						width : "1000px",
						height : "350px",
						lock : true,
						resize : false,
						close: function () {
							var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
							if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
								art.dialog.removeData("EFLOW_VARS");
								AppUtil.closeLayer();
							}
			    		}
				 }, false);
		}else{
			parent.$.dialog.open("executionController.do?transferFlow&EFLOW_EXEID="+EFLOW_EXEID
				+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&USER_RANGE=2&noSendMsg=true", {
					title : "交办下级",
					width : "1000px",
					height : "350px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
			 }, false);
		}
	},
	/**
	 * 报送上级
	 */
	FLOW_TransSuperLevel:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var EFLOW_DEFKEY = EFLOW_VARS.EFLOW_DEFKEY;
		if(EFLOW_DEFKEY&&EFLOW_DEFKEY=="029"){
			parent.$.dialog.open("executionController.do?transferClueFlow&EFLOW_EXEID="+EFLOW_EXEID
					+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&USER_RANGE=3&noSendMsg=true&ROLE_CODE_RANG=AJGL_006", {
						title : "报送上级",
						width : "1000px",
						height : "350px",
						lock : true,
						resize : false,
						close: function () {
							var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
							if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
								art.dialog.removeData("EFLOW_VARS");
								AppUtil.closeLayer();
							}
			    		}
				 }, false);
		}else{
			parent.$.dialog.open("executionController.do?transferFlow&EFLOW_EXEID="+EFLOW_EXEID
					+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID+"&USER_RANGE=3&noSendMsg=true", {
						title : "报送上级",
						width : "1000px",
						height : "350px",
						lock : true,
						resize : false,
						close: function () {
							var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
							if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
								art.dialog.removeData("EFLOW_VARS");
								AppUtil.closeLayer();
							}
			    		}
				 }, false);
		}
	},
	/**
	 * 打印文书操作
	 */
	FLOW_PrintDocument:function(EFLOW_VARS){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_DEFID = EFLOW_VARS.EFLOW_DEFID;
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		if(!EFLOW_EXEID){
			alert("请先进行暂存或提交操作!");
			return;
		}
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "permissionBindController.do?listView&EFLOW_DEFID="+EFLOW_DEFID+"&EFLOW_EXEID="+EFLOW_EXEID
		+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID;
		parent.$.dialog.open(url, {
			title : "打印文书",
			width : "800px",
			height : "500px",
			lock : true,
			resize : false,
			close: function () {
				 var savewordpath = art.dialog.data("savewordpath");
		         if(savewordpath!=undefined){
		             //alert("docpath:"+savewordpath.docpath);
		         }
    		}
		}, false);
	},
	/**
	 * 打印食品经营证照操作
	 */
	FLOW_PrintLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "jyLicensePrintController.do?printJyxkJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印证照",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	
	
	
	/**
	 * 打印食品生产证照操作
	 */
	FLOW_PrintSCLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var checkUrl = "scLicensePrintController.do?checkOtherLicense&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		var mergeJumpUrl = "scLicensePrintController.do?confimMergePage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		var jumpUrl = "scLicensePrintController.do?printScxkJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		AppUtil.ajaxProgress({
			url : checkUrl,
			params : '',
			callback : function(resultJson) {
				if(resultJson.INVALID){
					art.dialog('已办理过新证，不能打印', function(){});
				}else if(resultJson.OPER_SUCCESS){
					$.dialog.open(mergeJumpUrl, {
			    		title : "合并证照打印",
						width: "100%",
					    height: "100%",
					    fixed: true,
						lock : true,
			            resize:false,
						close: function () {
			    		}
			    	}, false); 
				}else{
					$.dialog.open(jumpUrl, {
			    		title : "打印证照",
						width: "100%",
					    height: "100%",
					    fixed: true,
						lock : true,
			            resize:false,
						close: function () {
							 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
			    		}
			    	}, false); 
				}
			}
		});
		

	},
	
	/**
	 * 打印化妆品许可证照操作
	 */
	FLOW_PrintHZPLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "hzpLicensePrintController.do?printHzpxkJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印证照",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	
	/**
	 * 打印第二类医疗器械产品注册证书操作
	 */
	FLOW_PrintYLQXProductLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "ylqxLicensePrintController.do?printYlqxxkJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印证照",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	
	/**
	 * 打印药品经营批发证书操作
	 */
	FLOW_PrintYpjypfLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "ypjyLicensePrintController.do?printYpjyPfJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印证照",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	
	/**
	 * 打印药品经营零售证书操作
	 */
	FLOW_PrintYpjylsLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "ypjyLicensePrintController.do?printYpjyLsJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印证照",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	
	/**
	 * 打印药品经营质量管理规范认证证书GSP操作
	 */
	FLOW_PrintYpjyGSPLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "ypjyLicensePrintController.do?printYpjyGspJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印证照",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	/**
	 * 打印体外诊断试剂注册证书操作
	 */
	FLOW_PrintTWSJProductLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "twsjLicensePrintController.do?printTwsjxkJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID;
		$.dialog.open(url, {
    		title : "打印批件",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	/**
	 * 打印第二类医疗器械产品注册证书操作
	 */
	FLOW_PrintSecYLQXLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_DEFKEY = EFLOW_VARS.EFLOW_DEFKEY;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "secYLQXLicensePrintController.do?printSecYLQXxkJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID+"&EFLOW_DEFKEY="+EFLOW_DEFKEY;
		$.dialog.open(url, {
    		title : "打印批件",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	/**
	 * 医疗机构制剂处理
	 */
	FLOW_PrintYLJGLicense:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_DEFKEY = EFLOW_VARS.EFLOW_DEFKEY;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		var url = "yljgLicensePrintController.do?printYljgJumpPage&taskId="+EFLOW_CURRENTTASK_ID+"&exeId="+EFLOW_EXEID+"&EFLOW_DEFKEY="+EFLOW_DEFKEY;
		$.dialog.open(url, {
    		title : "打印批件",
			width: "100%",
		    height: "100%",
		    fixed: true,
			lock : true,
            resize:false,
			close: function () {
				 $.post("lodopTemplateConfigController.do?removeDocFromSession",null, null);
    		}
    	}, false); 
	},
	/**
	 * 预审通过按钮的实现
	 */
	FLOW_PreAuditPass:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		//var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		// 存储
		parent.$.dialog.open("executionController.do?preAuditPass&EFLOW_EXEID="+EFLOW_EXEID+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID, {
			title : "预审通过",
			width :"1000px",
			height:"500px",
			lock : true,
			resize : false,
			close: function () {
				if(EFLOW_CALLBACKFN){
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
					}
				}else{
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
				}
    		}
		}, false);
		
		
	},
	/**
	 * 挂起流程任务
	 */
	FLOW_HandUp:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		$.post("executionController.do?storeFlowSubmitInfoJson",{
			EFLOW_VARS_JSON:EFLOW_VARS_JSON
		}, function(responseText, status, xhr) {
			// 存储
			parent.$.dialog.open("flowTaskController.do?goHandUp", {
				title : "挂起流程",
				width : "700px",
				height : "250px",
				lock : true,
				resize : false,
				close: function () {
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
	    		}
			}, false);
		});
	},
	/**
	 * 办结流程
	 */
	FLOW_HandleOver:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
		var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		$.post("executionController.do?storeFlowSubmitInfoJson",{
			EFLOW_VARS_JSON:EFLOW_VARS_JSON
		}, function(responseText, status, xhr) {
			var EFLOW_NODECONFIG  = EFLOW_VARS.EFLOW_NODECONFIG;
			// 存储
			parent.$.dialog.open("executionController.do?handleOver", {
				title : "审核不通过",
				width :"1000px",
				height:"500px",
				lock : true,
				resize : false,
				close: function () {
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
					/*if(EFLOW_CALLBACKFN){
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
						}
					}*/
	    		}
			}, false);
		});
		
	},
	/**
	 * 
	 * 预审不通过按钮实现
	 */
	FLOW_PreAuditNoPass:function(EFLOW_VARS,EFLOW_CALLBACKFN){
		if(!EFLOW_VARS){
			EFLOW_VARS = FlowUtil.getFlowVarsObj();
		}
		var EFLOW_EXEID = EFLOW_VARS.EFLOW_EXEID;
		var EFLOW_CURRENTTASK_ID = EFLOW_VARS.EFLOW_CURRENTTASK_ID;
		//var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		// 存储
		parent.$.dialog.open("executionController.do?preAuditNoPass&EFLOW_EXEID="+EFLOW_EXEID+"&EFLOW_CURRENTTASK_ID="+EFLOW_CURRENTTASK_ID, {
			title : "预审不通过",
			width :"1000px",
			height:"500px",
			lock : true,
			resize : false,
			close: function () {
				if(EFLOW_CALLBACKFN){
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						EFLOW_CALLBACKFN.call(this,EFLOW_VARS);
					}
				}else{
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
				}
    		}
		}, false);
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
					.attr("selected", "selected");
				}else{
					targetField.val(fieldValueObj[fieldName]);
				}
		    });
		}
	},
	/**
	 * 初始化表单字段值
	 * @param {} fieldValueObj
	 * @param {} elementObj
	 */
	initFormObjValue:function(fieldValueObj,elementObj){
		for(var fieldName in fieldValueObj){
			//获取目标控件对象
			var targetFields = elementObj.find("[name$='"+fieldName+"']");
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
					.attr("selected", "selected");
				}else{
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
			var targetFields = $("#"+elementId).find("[name='"+fieldName+"']");
			targetFields.each(function(){
				var targetField = $(this);
				var type = targetField.attr("type");
				var tagName = targetField.get(0).tagName;
				var classValue = targetField?targetField.attr("class"):"";
				if(rightFlag==2){
					if(tagName=="SELECT"){
						targetField.attr("disabled","disabled");
					}else if(tagName=="INPUT"&&type=="text"){
						if(classValue){
							var index = classValue.indexOf("laydate-icon");
							if(index!=-1){
								targetField.attr("disabled","disabled");
							}else{
								targetField.attr("readonly","readonly");
							}
						}else{
							targetField.attr("readonly","readonly");
						}
					}else if(type=="radio"){
						$(this).attr("disabled","disabled");
					}else if(type=="checkbox"){
						$(this).attr("disabled","disabled");
					}else if(tagName=="TEXTAREA"){
						targetField.attr("readonly","readonly");
					}
				}else if(rightFlag==3){
					if(type=="radio"||type=="checkbox"){
						$(this).attr("style","display:none;");
					}else{
						targetField.attr("style","display:none;");
					}
				}
				
		    });
		}
	},
	/**
	 * 初始化表单字段权限控制(name$="")
	 * @param {} currentNodeFieldRights
	 * @param {} elementId
	 */
	initFormFieldRightPrefixControl:function(currentNodeFieldRights,elementId){
		for(var index in currentNodeFieldRights){
			var rightConfig = currentNodeFieldRights[index];
			var fieldName = rightConfig.FIELD_NAME;
			var rightFlag = rightConfig.RIGHTFLAG;
			//获取目标控件对象
			var targetFields = $("#"+elementId).find("[name$='"+fieldName+"']");
			targetFields.each(function(){
				var targetField = $(this);
				var type = targetField.attr("type");
				var tagName = targetField.get(0).tagName;
				var classValue = targetField?targetField.attr("class"):"";
				if(rightFlag==2){
					if(tagName=="SELECT"){
						targetField.attr("disabled","disabled");
					}else if(tagName=="INPUT"&&type=="text"){
						if(classValue){
							var index = classValue.indexOf("laydate-icon");
							if(index!=-1){
								targetField.attr("disabled","disabled");
							}else{
								targetField.attr("readonly","readonly");
							}
						}else{
							targetField.attr("readonly","readonly");
						}
					}else if(type=="radio"){
						$(this).attr("disabled","disabled");
					}else if(type=="checkbox"){
						$(this).attr("disabled","disabled");
					}else if(tagName=="TEXTAREA"){
						targetField.attr("readonly","readonly");
					}
				}else if(rightFlag==3){
					if(type=="radio"||type=="checkbox"){
						$(this).attr("style","display:none;");
					}else{
						targetField.attr("style","display:none;");
					}
				}
				
		    });
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
				var reg = /[\(]/g,reg2 = /[\)]/g;
				if(inputValue!=null){
					inputValue = inputValue.replace(reg,"（").replace(reg2,"）");
				}
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
			return "<font color='red'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='green'><b>正在办理</b></font>";
		}else if(val=="2"){
			return "<font color='blue'><b>已办结(正常结束)</b></font>";
		}else if(val=="3"){
			return "<font color='blue'><b>已办结(审核通过)</b></font>";
		}else if(val=="4"){
			return "<font color='blue'><b>已办结(审核不通过)</b></font>";
		}else if(val=="5"){
			return "<font color='red'><b>已办结(退回)</b></font>";
		}else if(val=="6"){
			return "<font color='black'><b>业务退回</b></font>";
		}
	},
	/**
	 * 提交网站上申报的表单
	 * @param {} flowSubmitObj
	 * @param {} EFLOW_CALLBACKFN
	 */
	submitWebSiteApplyForm:function(flowSubmitObj,EFLOW_CALLBACKFN){
		flowSubmitObj.flashvars = "";
		flowSubmitObj.movie = "";
		flowSubmitObj.applyMatersJson = "";
		flowSubmitObj.EFLOW_CURFIELDRIGHTS = null;
		flowSubmitObj.EFLOW_CURBUTTONRIGHTS = null;
		flowSubmitObj.EFLOW_CURAUDITRIGHTS = null;
		if(flowSubmitObj.EFLOW_IS_START_NODE=="true"){
			flowSubmitObj.EFLOW_APPLY_STATUS = "1";
		}
		var EFLOW_ISTEMPSAVE = flowSubmitObj.EFLOW_ISTEMPSAVE;
		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "webSiteController.do?submitApply",
			params : postParam,
			callback : function(resultJson) {
				if(resultJson.OPER_SUCCESS){
					if(EFLOW_ISTEMPSAVE=="1"){
						parent.art.dialog({
							content : "保存成功!草稿信息可在个人中心查看!",
							icon : "succeed",
							cancel:false,
							lock: true,
							ok: function(){
								var reloadHref = __ctxPath+"/executionController.do?redirectForm&EFLOW_EXEID=";
								reloadHref+=(resultJson.EFLOW_EXEID+"&EFLOW_ISQUERYDETAIL=false&EFLOW_ISFORWEBSITE=true");
							    //window.top.location.href=__ctxPath+"/userInfoController.do?wdbj";
								window.top.location.href=reloadHref;
							}
						});		
					}else{
						art.dialog.data("submitApplyFormResult",resultJson);// 存储数据
						$.dialog.open("webSiteController/view.do?navTarget=website/zxsb/sbcg", {
				    		title : "提交成功",
				            width:"1000px",
				            lock: true,
				            fixed: true,
				            cancel:false,
				            resize:false,
				            esc:false,
				            height:"440px",
				            dblclick_hide:false
				    	}, false);
					}
				}else{
					parent.art.dialog({
						content : resultJson.OPER_MSG,
						icon : "error",
						ok : true
					});
				}
			}
		});
	},
	/**
	 * 实现不予受理按钮函数
	 */
	FLOW_NotAccept:function(){
		//获取流程变量对象
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		EFLOW_VARS.handleResult = "2";
		FlowUtil.FLOW_SubmitFun(EFLOW_VARS, function(EFLOW_VARS) {
			AppUtil.closeLayer();
		}); 
	},
	/**
	 * 转案件管理
	 */
	FLOW_TurnCaseManage:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.handleResult = "3";
			FlowUtil.FLOW_SubmitFun(EFLOW_VARS, function(EFLOW_VARS) {
				AppUtil.closeLayer();
			}); 
		}
	},
	/**
	 * 实现核查按钮函数
	 */
	FLOW_Check:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.handleResult = "1";
			FlowUtil.FLOW_SubmitFun(EFLOW_VARS, function(EFLOW_VARS) {
				AppUtil.closeLayer();
			}); 
		}
	},
	/**
	 * 执行与结案
	 */
	FLOW_exeAndClose:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
			var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				EFLOW_VARS_JSON:EFLOW_VARS_JSON
			}, function(responseText, status, xhr) {
				// 存储
				parent.$.dialog.open("generalCaseController.do?goZxyja", {
					title : "执行与结案",
					width :"800px",
					height:"500px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
				}, false);
			});
		}
	},
	/**
	 * 归档按钮函数
	 */
	FLOW_fileAway:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
			var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				EFLOW_VARS_JSON:EFLOW_VARS_JSON
			}, function(responseText, status, xhr) {
				// 存储
				parent.$.dialog.open("generalCaseController.do?goguidan", {
					title : "归档",
					width :"800px",
					height:"300px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
				}, false);
			});
		}
	},
	/**
	 * 处罚决定按钮函数
	 */
	FLOW_PunishDesicion:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
			var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				EFLOW_VARS_JSON:EFLOW_VARS_JSON
			}, function(responseText, status, xhr) {
				// 存储
				parent.$.dialog.open("generalCaseController.do?goCfjd", {
					title : "处罚决定",
					width :"800px",
					height:"500px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
				}, false);
			});
			
		}
	},
	/**
	 * 撤案按钮实现函数
	 */
	FLOW_RevokeCase:function(){
		var EFLOW_VARS = FlowUtil.getFlowVarsObj();
		EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
		var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
		$.post("executionController.do?storeFlowSubmitInfoJson",{
			EFLOW_VARS_JSON:EFLOW_VARS_JSON
		}, function(responseText, status, xhr) {
			var EFLOW_NODECONFIG  = EFLOW_VARS.EFLOW_NODECONFIG;
			// 存储
			parent.$.dialog.open("generalCaseController.do?revokeCase", {
				title : "撤案",
				width :"800px",
				height:"320px",
				lock : true,
				resize : false,
				close: function () {
					var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
					if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
						art.dialog.removeData("EFLOW_VARS");
						AppUtil.closeLayer();
					}
	    		}
			}, false);
		});
	},
	/**
	 * 格式线索登记流程状态
	 */
	formatClueStatus:function(val,row){
		if(val=="0"){
			return "<font color='red'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='green'><b>待受理</b></font>";
		}else if(val=="2"){
			return "<font color='green'><b>核查中</b></font>";
		}else if(val=="3"){
			return "<font color='blue'><b>已核查</b></font>";
		}else if(val=="4"){
			return "<font color='blue'><b>已立案</b></font>";
		}else if(val=="5"){
			return "<font color='red'><b>结束(未立案)</b></font>";
		}else if(val=="6"){
			return "<font color='blue'><b>结束(已归档)</b></font>";
		}else if(val=="7"){
			return "<font color='green'><b>案源登记审核</b></font>";
		}
	},
	/**
	 * 调查取证按钮实现函数
	 */
	FLOW_ObtainEvidence:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
			var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				EFLOW_VARS_JSON:EFLOW_VARS_JSON
			}, function(responseText, status, xhr) {
				var EFLOW_NODECONFIG  = EFLOW_VARS.EFLOW_NODECONFIG;
				// 存储
				parent.$.dialog.open("generalCaseController.do?obtainEvidence", {
					title : "调查取证提交",
					width :"1000px",
					height:"500px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
				}, false);
			});
		}
		
	},
	/**
	 * 立案申请按钮实现函数
	 */
	FLOW_FilingApply:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			/*var EFLOW_FILEATTACHJSON = getEflowFileAttachJSON();
			if(EFLOW_FILEATTACHJSON){
				EFLOW_VARS.EFLOW_FILEATTACHJSON = EFLOW_FILEATTACHJSON;
			}*/
			EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
			var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				EFLOW_VARS_JSON:EFLOW_VARS_JSON
			}, function(responseText, status, xhr) {
				var EFLOW_NODECONFIG  = EFLOW_VARS.EFLOW_NODECONFIG;
				// 存储
				parent.$.dialog.open("generalCaseController.do?filingApply", {
					title : "立案提交",
					width :"1000px",
					height:"500px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
				}, false);
			});
		}
		
	},
	
	/**
	 * 延期保存申请
	 */
	FLOW_delayKeepTime:function(){
		//验证表单是否合法
		var valiateTabForm = AppUtil.validateTabForm();
		if (valiateTabForm) {
			//获取流程变量对象
			var EFLOW_VARS = FlowUtil.getFlowVarsObj();
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			EFLOW_VARS.EFLOW_ISTEMPSAVE = "-1";
			var EFLOW_VARS_JSON = JSON.stringify(EFLOW_VARS);
			$.post("executionController.do?storeFlowSubmitInfoJson",{
				EFLOW_VARS_JSON:EFLOW_VARS_JSON
			}, function(responseText, status, xhr) {
				// 存储
				parent.$.dialog.open("propertyController.do?delayWindow", {
					title : "延长保存申请",
					width :"800px",
					height:"500px",
					lock : true,
					resize : false,
					close: function () {
						var EFLOW_VARS = art.dialog.data("EFLOW_VARS");
						if(EFLOW_VARS&&EFLOW_VARS.OPER_SUCCESS){
							art.dialog.removeData("EFLOW_VARS");
							AppUtil.closeLayer();
						}
		    		}
				}, false);
			});
		}
	}
});