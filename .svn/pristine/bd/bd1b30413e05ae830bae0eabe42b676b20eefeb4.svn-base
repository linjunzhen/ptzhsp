<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
	poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
	String filepath =null;
	Map fileMap = (Map) request.getAttribute("fileMap");
	if(fileMap.get("FILE_PATH")!=null&& !fileMap.get("FILE_PATH").equals("")){
	    filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
	            + "/" + fileMap.get("FILE_PATH").toString(); 
	}
	poCtrl1.addCustomToolButton("打开", "Open", 3);
	poCtrl1.addCustomToolButton("保存", "Save", 1);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("另保存", "OtherSave", 1);
	poCtrl1.addCustomToolButton("关闭", "closeDoc", 10);
	poCtrl1.addCustomToolButton("-", "", 0);
	if(fileMap.get("isfrist")==null||fileMap.get("isfrist").equals("0")){
	    poCtrl1.setSaveFilePage(request.getContextPath() + "/applyMaterController/saveOnlineFile.do");
	}else{
	    poCtrl1.setSaveFilePage(request.getContextPath() + "/applyMaterController/saveOnlineFile.do?filePath="+fileMap.get("FILE_PATH"));
	}
	poCtrl1.setTitlebar(false);
	poCtrl1.setOfficeToolbars(true);
	poCtrl1.setMenubar(false);
	poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    
   if(filepath!=null&&!filepath.equals("")){
    poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "evecom");
   }else{
    poCtrl1.webCreateNew("evecom",DocumentVersion.Word2003);
   }
    poCtrl1.setJsFunction_AfterDocumentOpened("load()");
    poCtrl1.setJsFunction_AfterDocumentSaved("saveOnlineWord()");

    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
}
function saveOnlineWord(){
	var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
    AppUtil.ajaxProgress({
        url : "applyMaterController.do?saveFileAttach",
        params : {
        	FILE_NAME:$("input[name='materName']").val()+".doc",
        	UPLOADER_ID:$("input[name='uploadUserId']").val(),
        	UPLOADER_NAME:$("input[name='uploadUserName']").val(),
        	BUS_TABLENAME:$("input[name='onlineBusTableName']").val(),
        	ATTACH_KEY:$("input[name='materCode']").val(),
            IS_IMG:"-1",
            FILE_TYPE:"doc",
            FILE_PATH:strCustomSaveResult
            },
        callback : function(resultJson) {
            if(resultJson.success){
                  parent.art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                art.dialog.data("saveMaterInfo", {
                	templateId:resultJson.fileId
                });
                AppUtil.closeLayer();
            }else{
                parent.art.dialog({
                    content: resultJson.msg,
                    icon:"error",
                    ok: true
                });
            }
        }
    });
}

//保存
function Save(){
	document.getElementById("PageOfficeCtrl1").WebSave();
}
//另存为
function OtherSave(){
	document.getElementById("PageOfficeCtrl1").ShowDialog(3);
}
//打开
function Open(){
    document.getElementById("PageOfficeCtrl1").ShowDialog(1);
}
//关闭
function closeDoc() {
    if(document.getElementById("PageOfficeCtrl1").IsDirty){
        art.dialog.confirm('当前文档未保存，是否继续关闭？', function () {
        	AppUtil.closeLayer(); 
        }, function () {
           
        });
    }else{
    	AppUtil.closeLayer();  
    }
}



</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
       
      
    <div class="easyui-layout" fit="true">
    <div data-options="region:'center',split:false">
        <input type="hidden" name="uploadUserId" value="${fileMap.uploadUserId}">
        <input type="hidden" name="uploadUserName" value="${fileMap.uploadUserName}">
        <input type="hidden" name="materName" value="${fileMap.materName}">
        <input type="hidden" name="materCode" value="${fileMap.materCode}">
        <input type="hidden" name="onlineBusTableName" value="${fileMap.onlineBusTableName}">
        <input type="hidden" name="isfrist" value="${fileMap.isfrist}">
        <div style="height: 96%;width: 100%;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1" ></po:PageOfficeCtrl>
        </div>
    </div>
</div>
       
</body>
