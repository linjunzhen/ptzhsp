<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<% 
//***************************卓正PageOffice组件的使用********************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	
	poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
	poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
	poCtrl1.addCustomToolButton("打开", "Open", 3);
	poCtrl1.addCustomToolButton("保存", "Save", 1);
	poCtrl1.addCustomToolButton("另保存", "OtherSave", 1);
	poCtrl1.addCustomToolButton("关闭", "closeDoc", 10);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("页面设置", "ShowDialog_1()", 0);
	poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);
	poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
	poCtrl1.addCustomToolButton("-", "", 0);
	String filepath =null;
	Map map = (Map) request.getAttribute("certificateTemplate");
	if(map.get("CERTIFICATE_DOC")!=null&& !map.get("CERTIFICATE_DOC").equals("")){
	    filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
	            + "/" + "attachFiles/certificateTemplate/files/"+map.get("CERTIFICATE_DOC").toString(); 
		poCtrl1.setSaveFilePage(request.getContextPath() + "/certificateTemplateController/savefile.do?filename="+map.get("CERTIFICATE_DOC"));
	}else {
		poCtrl1.setSaveFilePage(request.getContextPath() + "/certificateTemplateController/savefile.do?alias="+map.get("ALIAS"));
	}
	poCtrl1.setTitlebar(false);
	poCtrl1.setOfficeToolbars(true);
	poCtrl1.setMenubar(false);
	poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    
    if(map.get("CERTIFICATE_DOC")!=null&& !map.get("CERTIFICATE_DOC").equals("")) {
		if(filepath.endsWith(".doc") || filepath.endsWith(".docx")){
			poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "evecom");
		}else {
			poCtrl1.webOpen(filepath, OpenModeType.xlsNormalEdit, "evecom");
		}
	} else {
		poCtrl1.webCreateNew("evecom", DocumentVersion.Excel2003);
	}
    poCtrl1.setJsFunction_AfterDocumentOpened("load()");
    poCtrl1.setJsFunction_AfterDocumentSaved("updateCertificate()");
    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
}

// 更新certificate表，保存日志
function updateCertificate(){
	var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
    AppUtil.ajaxProgress({
        url : "certificateTemplateController.do?saveOrUpdate",
        params : {
            CERTIFICATE_ID:$("input[name='certificateId']").val(),
            CERTIFICATE_DOC:strCustomSaveResult
            },
        callback : function(resultJson) {
            if(resultJson.success){
                  parent.art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
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
    
    // saveToRemoteServer();
}

function saveToRemoteServer() {
 	 // 保存模板的操作日志并上传模板到文件服务器
	 var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
  	 var result = {};
	 var startInx = templatePath.indexOf("attachFiles");
	 result["FILE_PATH"] = strCustomSaveResult;
	 result["BUS_TABLENAME"] = "T_WSBS_CERTIFICATE";
	 result["FLOW_EXEID"] = "${TemplateData.xmsqbh}";
	 result["FILE_NAME"] = "${TemplateData.TemplateName}";
	 $.post("printAttachController.do?saveOrUpdate",result, function(resultJson) {
	 	 if(resultJson.success){
                  parent.art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                AppUtil.closeLayer();
            }else{
                parent.art.dialog({
                    content: resultJson.msg,
                    icon:"error",
                    ok: true
                });
            }
	 });
}

//全屏/还原
function IsFullScreen() {
    document.getElementById("PageOfficeCtrl1").FullScreen = !document
            .getElementById("PageOfficeCtrl1").FullScreen;
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
//页面设置
function ShowDialog_1() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(5);
}
//页面打印
function ShowDialog_2() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(4);
}
//遍历当前Word中已存在的书签名称
function checkBkNames() {
    var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;
    drlist.Refresh();
    var bkName = "";
    var bkNames = ",";
    for (var i = 0; i < drlist.Count; i++) {
        bkName = drlist.Item(i).Name;
        bkNames += bkName.substr(3) + ",";
    }
    //return bkNames.substr(0, bkNames.length - 1);
    return bkNames;
}

//遍历当前Word中已存在的书签文本
function checkBkConts() {
    var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;
    drlist.Refresh();
    var bkCont = "";
    var bkConts = ",";
    for (var i = 0; i < drlist.Count; i++) {
        bkCont = drlist.Item(i).Value;
        bkConts += bkCont + ",";
    }
   // return bkConts.substr(0, bkConts.length - 1);
    return bkConts;
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',split:false">
        <input type="hidden" name="certificateId" value="${certificateTemplate.CERTIFICATE_ID}">
        <div style="height: 96%;width: 100%;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1" ></po:PageOfficeCtrl>
        </div>
    </div>
</div>
       
</body>
