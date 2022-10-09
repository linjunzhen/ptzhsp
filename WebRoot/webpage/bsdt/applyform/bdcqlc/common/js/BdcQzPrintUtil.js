var BdcQzPrintUtil = new Object({	
	
	/**
	 * 发起后台保存逻辑
	 */
	submitBdcJffzInfo:function(flowSubmitObj){
	   var postParam = $.param(flowSubmitObj);
       AppUtil.ajaxProgress({
            url : "bdcQlcSffzInfoController.do?saveBdcJffzinfo",
            params : postParam,
            callback : function(resultJson) {
                if(resultJson.OPER_SUCCESS){
                    var ispass = flowSubmitObj["EFLOW_ISPASSAUDIT"];
                    if(ispass != null && ispass =="1"){
                    	BdcQzPrintUtil.BDC_showQzPrintAuditFun(flowSubmitObj);
                    }else{
                        art.dialog({
                         content : resultJson.OPER_MSG,
                         icon : "succeed",
                         //time : 3,
                         ok: function(){
                             /* opener.$("#MyApplyExecGrid").datagrid('reload');
                             if(opener.$("#callingGrid").length>0){
                                 opener.$("#callingGrid").datagrid('reload');
                                 opener.parent.reloadTabGrid("已受理记录","accept");
                             }
                             if(top.opener.$("#callingNewGrid").length>0){
                                 top.opener.afterRefresh();
                             }
                             if(top.opener.$("#QueueItemDetailGrid").length>0){
                                 top.opener.afterDeal();
                             } */
                             //window.opener = null;   
                             //window.open('','_self'); 
                             //window.close(); 
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
	 * 提交
	 */
	BDC_jffzSubmitFun:function(){
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
        //将其转换成JSON对象
        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
        //定义流程提交对象信息
        var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
        //调用动态表单的函数
        flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_SubmitFun(flowSubmitObj);
        flowSubmitObj["EFLOW_ISPASSAUDIT"]="1";
        if(flowSubmitObj != null){
            var BDC_OPTYPE = flowSubmitObj.BDC_OPTYPE;
            if (BDC_OPTYPE && BDC_OPTYPE == '1') {
                art.dialog.confirm("请确认填写权证标识码并进行保存后再提交流程", function() {
                    BdcQzPrintUtil.submitBdcJffzInfo(flowSubmitObj);
                });
            } else {
                BdcQzPrintUtil.submitBdcJffzInfo(flowSubmitObj);
            }



        }
	},
	/**
	 * 暂存
	 */
	BDC_jffzTempSaveFun:function(){
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
        //将其转换成JSON对象
        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
        //定义流程提交对象信息
        var flowSubmitObj = FlowUtil.initFlowSubmitObj(eflowObj);
        
        //调用动态表单的函数
        flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_TempSaveFun(flowSubmitObj); 
        flowSubmitObj["EFLOW_ISTEMPSAVE"] = "1";       
        if(flowSubmitObj != null){
        	BdcQzPrintUtil.submitBdcJffzInfo(flowSubmitObj);
        }
	},
	/**
	 * 提交并弹窗
	 */
	BDC_showQzPrintAuditFun:function(flowSubmitObj){
		var eveId = flowSubmitObj.EFLOW_EXEID;
        $.dialog.open("bdcQlcSffzInfoController.do?qzprintaudit&eveId=" + eveId, {
            title : "提交页面",
            width : "600px",
            height : "400px",
            lock : true,
            resize : false
        }, false);
	},
	/**
	 * 业务表单页面上的暂存操作
	 */
	BDC_jffzTempSavePageFun:function(){
		//使用jsAPI中获取流程相关数据
		var flowSubmitObj = FlowUtil.getFlowObj();    
        //调用动态表单的函数
        //flowSubmitObj = $("#EFLOWFORM_IFRAME")[0].contentWindow.FLOW_TempSaveFun(flowSubmitObj); 
        flowSubmitObj = FLOW_TempSaveFun(flowSubmitObj); 
        flowSubmitObj["EFLOW_ISTEMPSAVE"] = "1";       
        if(flowSubmitObj != null){
        	BdcQzPrintUtil.submitBdcJffzInfo(flowSubmitObj);
        }
	}
});