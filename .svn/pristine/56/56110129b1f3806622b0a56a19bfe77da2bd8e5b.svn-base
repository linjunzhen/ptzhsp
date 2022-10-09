function isButtonAvailable(){
	var result = {};
	result["ITEM_CODE"] = $("input[name='ITEM_CODE']").val();
	result["EXE_ID"] = $("#EXEID").text();
	result["CUR_STEPNAMES"] = $("#CUR_STEPNAMES").val();
	$.post("readTemplateController.do?selectedPrint",result, function(resultJson) {
		  var itemList = resultJson.rows;
          var newhtml = "";
          for(var i=0; i<itemList.length; i++){
        	  if(itemList[i].READ_NAME=='不动产登记审批表一审'){
        		  $("#SPBDF").attr("onclick","goPrintTemplate('BDCDJSPBYS',3)");
        		  $("#SPBSF").attr("onclick","goPrintTemplate('BDCDJSPBYS',3)");
        	  }
          }
	});
}

function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板 3为阅办
	//dataStr +="&TemplatePath="+TemplatePath;	
	dataStr +="&TemplateName="+TemplateName;	//TemplateName为模板名称或别名
	//dataStr +="&isSignButton="+isSignButton;
	var url=encodeURI("printAttachController.do?printTemplate"+dataStr);
    //打印模版
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

function errorAction(){
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}