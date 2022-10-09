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
		$("#EXE_ID").val(flowVars.busRecord.EXE_ID);
		$("#CUR_STEPNAMES").val(flowVars.busRecord.CUR_STEPNAMES);
		$("#BUS_TABLENAME").val(flowVars.busRecord.BUS_TABLENAME);
		changemblx();
	});
        
	function changemblx(){
		var selectValue = $("input[name='mblx']:checked ").val();
		var ITEM_CODE = $("#ITEM_CODE").val();
		var EXE_ID = $("#EXE_ID").val();
		var CUR_STEPNAMES = $("#CUR_STEPNAMES").val();
		var BUS_TABLENAME = $("#BUS_TABLENAME").val();
		if(selectValue==1){
			getTicketsTemplate(ITEM_CODE);
		}else if(selectValue==2){
			getDocumentTemplate(ITEM_CODE);
        }else if(selectValue==3){
            getReadTemplate(ITEM_CODE,EXE_ID,CUR_STEPNAMES,BUS_TABLENAME);
        }
	}
	function getReadTemplate(itemId,EXE_ID,CUR_STEPNAMES,BUS_TABLENAME){
		var result = {};
		var username = "${sessionScope.curLoginUser.username}";
		result["ITEM_CODE"] = itemId;
		result["EXE_ID"] = EXE_ID;
		result["username"] = username;
		result["CUR_STEPNAMES"] = CUR_STEPNAMES;
		result["BUS_TABLENAME"] = BUS_TABLENAME;
		$.post("readTemplateController.do?selectedPrint",result, function(resultJson) {
                $("#mblist").html("");
                   var itemList = resultJson.rows;
                   var newhtml = "";
                   for(var i=0; i<itemList.length; i++){
                	   if(itemList[i].READ_DOC=='20160112103103.doc'
                			   ||itemList[i].READ_DOC=='20160112111058.doc'){
                           newhtml += "<tr><td style=\"text-align: center;\">";
                              newhtml +="<a style='text-decoration:none;' href=\"javascript:void(0);\" onclick=\"showTemplate(3,'"+itemList[i].READ_DOC+"','"+itemList[i].READ_NAME+
                           		   "','"+itemList[i].IS_SIGN_BUTTON+"','"+itemList[i].isOverLimit+"')\" ><font color='#4F4F4F'>"+
                                        itemList[i].READ_NAME+"</font></a>";    
                              newhtml +="</td></tr>";
                	   }else{
                           newhtml += "<tr><td style=\"text-align: center;\">";
                              newhtml +="<a href=\"javascript:void(0);\" onclick=\"showTemplate(3,'"+itemList[i].READ_DOC+"','"+itemList[i].READ_NAME+
                           		   "','"+itemList[i].IS_SIGN_BUTTON+"','"+itemList[i].isOverLimit+"')\" >"+
                                        itemList[i].READ_NAME+"</a>";    
                              newhtml +="</td></tr>";
                	   }
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
                       newhtml +="<a href=\"javascript:void(0);\" onclick=\"showTemplate(2,'"+itemList[i].DOCUMENT_DOC+"','"+itemList[i].DOCUMENT_NAME+"','"+itemList[i].IS_SIGN_BUTTON+"','"+itemList[i].isOverLimit+"')\" >"+
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
                       newhtml +="<a href=\"javascript:void(0);\" onclick=\"showTemplate(1,'"+itemList[i].TICKETS_DOC+"','"+itemList[i].TICKETS_NAME+"','"+itemList[i].IS_SIGN_BUTTON+"','"+itemList[i].isOverLimit+"')\" >"+
                                 itemList[i].TICKETS_NAME+"</a>";    
                       newhtml +="</td></tr>";
                   }
                   $("#mblist").html(newhtml);
        });
    }
	function showTemplate(typeId,TemplatePath,TemplateName, isSignButton,isOverLimit){
        if (isOverLimit && isOverLimit == 'true') {
            art.dialog({
                content: "超过每天打印次数限制，无法继续打印",
                icon: "warning",
                ok: true
            });
            return;
        }
		var flowVars = JSON2.parse($("#flowSubmitInfoJson").val());
		var dateStr = "";
		dateStr +="&EFLOW_EXEID="+flowVars.EFLOW_EXEID;
		dateStr +="&typeId="+typeId;
		dateStr +="&TemplatePath="+TemplatePath;
		dateStr +="&TemplateName="+TemplateName;
		dateStr +="&isSignButton="+isSignButton;
		var url=encodeURI("printAttachController.do?printTemplate"+dateStr);
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
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height:30px;background-color: #f7f7f7;" >
       <input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson" value="" />
       <input type="hidden" id="ITEM_CODE" name="ITEM_CODE" value="" />
       <input type="hidden" id="EXE_ID" name="EXE_ID" value="" />
       <input type="hidden" id="CUR_STEPNAMES" name="CUR_STEPNAMES" value="" />
       <input type="hidden" id="BUS_TABLENAME" name="BUS_TABLENAME" value="" />
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

