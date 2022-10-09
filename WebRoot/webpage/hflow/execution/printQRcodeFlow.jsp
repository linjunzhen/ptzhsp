<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2,swfupload"></eve:resources>
<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<style type="text/css">

a {
    color: blue;
    text-decoration: underline;
}
</style>
<script type="text/javascript">
    
	$(function() {
		var flowSubmitInfo = art.dialog.data("flowSubmitInfo");
		$("#flowSubmitInfoJson").val(flowSubmitInfo.flowSubmitInfoJson);
		var flowVars = JSON2.parse(flowSubmitInfo.flowSubmitInfoJson);
		$("#ITEM_CODE").val(flowVars.busRecord.ITEM_CODE);
		changemblx();
	});
        
	function changemblx(){
		var selectValue = $("input[name='mblx']:checked ").val();
		var ITEM_CODE = $("#ITEM_CODE").val();
		if(selectValue==1){
			getTicketsTemplate(ITEM_CODE);
		}else if(selectValue==2){
			getDocumentTemplate(ITEM_CODE);
        }else if(selectValue==3){
            getReadTemplate(ITEM_CODE);
        }
	}
	function getReadTemplate(itemId){
		var result = {};
		result["ITEM_CODE"] = itemId;
		$.post("readTemplateController.do?selectedPrint",result, function(resultJson) {
                $("#mblist").html("");
                   var itemList = resultJson.rows;
                   var newhtml = "";
                   for(var i=0; i<itemList.length; i++){
                    newhtml += "<tr><td style=\"text-align: center;\">";
                       newhtml +="<a href=\"javascript:void(0);\" onclick=\"showTemplate(3,'"+itemList[i].READ_DOC+"','"+itemList[i].READ_NAME+"')\" >"+
                                 itemList[i].READ_NAME+"</a>";    
                       newhtml +="</td></tr>";
                   }
                   $("#mblist").html(newhtml);
        });
	}
	function getDocumentTemplate(itemId){
        var result = {};
        result["ITEM_CODE"] = itemId;
        $.post("documentTemplateController.do?selectedPrint",result, function(resultJson) {
                $("#mblist").html("");
                   var itemList = resultJson.rows;
                   var newhtml = "";
                   for(var i=0; i<itemList.length; i++){
                    newhtml += "<tr><td style=\"text-align: center;\">";
                       newhtml +="<a href=\"javascript:void(0);\" onclick=\"showTemplate(2,'"+itemList[i].DOCUMENT_DOC+"','"+itemList[i].DOCUMENT_NAME+"')\" >"+
                                 itemList[i].DOCUMENT_NAME+"</a>";    
                       newhtml +="</td></tr>";
                   }
                   $("#mblist").html(newhtml);
        });
    }
	function getTicketsTemplate(itemId){
        var result = {};
        result["ITEM_CODE"] = itemId;
        $.post("ticketsController.do?selectedPrint",result, function(resultJson) {
                $("#mblist").html("");
                   var itemList = resultJson.rows;
                   var newhtml = "";
                   for(var i=0; i<itemList.length; i++){
                    newhtml += "<tr><td style=\"text-align: center;\">";
                       newhtml +="<a href=\"javascript:void(0);\" onclick=\"showTemplate(1,'"+itemList[i].TICKETS_DOC+"','"+itemList[i].TICKETS_NAME+"')\" >"+
                                 itemList[i].TICKETS_NAME+"</a>";    
                       newhtml +="</td></tr>";
                   }
                   $("#mblist").html(newhtml);
        });
    }
	function showTemplate(typeId,TemplatePath,TemplateName){
		var flowVars = JSON2.parse($("#flowSubmitInfoJson").val());
		var dateStr = "";
		dateStr +="&EFLOW_EXEID="+flowVars.EFLOW_EXEID;
		dateStr +="&TemplatePath="+TemplatePath;
		dateStr +="&TemplateName="+TemplateName;
        //打印模版
       $.dialog.open(encodeURI("printAttachController.do?printQRcode"+dateStr), {
                title : "打印二维码",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
		
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height:30px;background-color: #f7f7f7;" >
       <input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson" value="" />
       <input type="hidden" id="ITEM_CODE" name="ITEM_CODE" value="" />
       <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
	     <tr>
	        <td><span style="width: 100px;float:left;text-align:right;">模版类型：</span>
	           <input type="radio" name="mblx" value="1"  onclick="changemblx();" />单据模版
              <!-- <input type="radio" name="mblx" value="2" onclick="changemblx();"/>公文模版  -->
               <input type="radio" name="mblx" value="3" onclick="changemblx();" checked="checked"/>阅办文模版
               </td>
	    </tr>
		</table>
    </div>
	<div data-options="region:'center',border:false" style="background-color: #f7f7f7;">
            <div>
                <table style="width:100%;border-collapse:collapse;"
                class="dddl-contentBorder dddl_table">
                <tr style="height:29px;">
                    <td colspan="1" class="dddl-legend" style="font-weight:bold;">模版列表</td>
                </tr>
                <tbody id="mblist">
                
                </tbody>
                </table>
            </div>
    </div>

</body>

