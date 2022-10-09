<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
    
	
	  //获取材料列表信息
	   function getcllbJson(){
	        var cllbList = [];
	        var applyMatersJson = $("input[name='applyMatersJson']").val();
	        var applyMatersObj = JSON2.parse(applyMatersJson);
	        for(var index in applyMatersObj){
	            //获取材料编码
	            var MATER_CODE = applyMatersObj[index].MATER_CODE;
	            var selectValue = $("input[name='SFFH_"+MATER_CODE+"']:checked ").val();
	            if((selectValue&&selectValue=="1") || (selectValue&&selectValue=="-1")){
	                var bjcllb = {};
	                bjcllb.MATER_CODE = $("#mc_"+MATER_CODE).val();
	                //bjcllb.MATER_NAME = $("#mn_"+MATER_CODE).text();
	                bjcllb.SFFH = selectValue;
	               	cllbList.push(bjcllb);
	            }
	         }
	        return JSON2.stringify(cllbList);
	    }
	
	//打印一次性告知单
	function showTempPrint(TemplatePath,TemplateName){
	    var itemCode = $('input[name="itemCode"]').val();
	    var lineId = $('input[name="lineId"]').val();
	    var sqrxm = $('input[name="sqrxm"]').val();
	    var cllbJson = getcllbJson();
        if(null!=itemCode){
	    	var dateStr = "";
			dateStr +="&TemplatePath="+TemplatePath;
			dateStr +="&TemplateName="+TemplateName;
			dateStr +="&itemCode="+itemCode;
			dateStr +="&OPER_REMARK_SQR="+sqrxm;
			dateStr +="&cllbJson="+cllbJson;
			var url=encodeURI("printAttachController.do?printBack"+dateStr);
			$.dialog.open(url, {
	                title : "打印模版",
	                width: "100%",
	                height: "100%",
	                left: "0%",
	                top: "0%",
	                fixed: true,
	                lock : true,
	                resize : false,
	                close : function() {
	                    //保存窗口一次性告知单记录
						$.post("newCallController.do?savePrintRecord", {
							itemCode : itemCode,
							recordId : lineId,
							OPER_REMARK_SQR : sqrxm
						}, function(responseText, status, xhr) {
						});
	                }
	        }, false);
		}
	}
	   
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
	<form id="YcxgzdForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div id="YcxgzdFormDiv" data-options="region:'center',split:true,border:false" >
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson"
				value="${flowSubmitInfoJson}" />
			<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
			
			<input type="hidden" name="itemCode" value="${itemCode}" />
			<input type="hidden" name="lineId" value="${lineId}" />
			<input type="hidden" name="sqrxm" value="${sqrxm}" />
			
			
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">材料信息列表</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr>
								<td style="text-align: center;font-weight:bold;">材料名称</td>
								<td style="text-align: center;font-weight:bold;width: 300px;">是否符合要求</td>
							</tr>
							<c:forEach var="mater" items="${materList}">
								<tr>
									<td style="text-align: center;"><input type="hidden"
										id="mc_${mater.MATER_CODE}" value="${mater.MATER_CODE}" /> <span
										id="mn_${mater.MATER_CODE}">${mater.MATER_NAME}</span></td>
									<td>
									<input type="radio" name="SFFH_${mater.MATER_CODE}" value="1"/>是
									<input type="radio" name="SFFH_${mater.MATER_CODE}" value="-1"  
									 checked="checked"  />否
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons" >
				<!-- <input value="确定" type="button" onclick="showTemplate(3,'20160112110841.doc','窗口一次性告知单','false','false');"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />  -->
				<input value="确定" type="button" onclick="showTempPrint('20210223110841.doc','窗口一次性告知单');"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

