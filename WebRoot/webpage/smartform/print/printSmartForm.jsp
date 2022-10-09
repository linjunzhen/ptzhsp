<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.DataRegionInsertType"%>
<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.DataRegion"%>
<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.WordDocument"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
    poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);	
    poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("另保存", "OtherSave", 1);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("关闭", "windowClose", 1);
    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    String filepath =null;
    Map datamap = (Map) request.getAttribute("TemplateData");
    //System.out.println(datamap);
    if(datamap.get("TemplatePath")!=null&& !datamap.get("TemplatePath").equals("")){
        filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/" + "attachFiles/smartquery/files/"+datamap.get("TemplatePath").toString(); 
    }
    WordDocument doc = new WordDocument();
	for(Object entry : datamap.keySet()){
		Object value=datamap.get(entry.toString());
		if(value!=null&&value.toString()!=""){
			doc.openDataTag("【"+entry.toString()+"】").setValue(value.toString());
		}else{
			doc.openDataTag("【"+entry.toString()+"】").setValue("");
		}
	}
	poCtrl1.setWriter(doc);
	
    poCtrl1.setSaveFilePage(request.getContextPath() + "/printAttachController/savefile.do");
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);
    poCtrl1.setOfficeToolbars(true);
    poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    if(filepath!=null&&!filepath.equals("")){
        poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "evecom");
    }else{
    	poCtrl1.webCreateNew("evecom",DocumentVersion.Word2003);
    }
    poCtrl1.setJsFunction_AfterDocumentOpened("load()");
    poCtrl1.setJsFunction_AfterDocumentSaved("insertPrintAttach()");
    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
    loadData();
    //loadData2();
}
function loadData2(){
	var bkName = "";
    try{
        var bkmkObj = document.all("PageOfficeCtrl1").Document.Bookmarks;
        for (var i = 1; i <= bkmkObj.Count; i++) {
                alert(bkmkObj.Item(1).Name+":"+bkmkObj.Item(1).Range.Text);

        }
    }catch(err){
 
    }
    finally{
    }	
}
 function loadData(){
	var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;
} 
 
//页面打印
 function ShowDialog_2() {
	 document.getElementById("PageOfficeCtrl1").WebSave();
     document.getElementById("PageOfficeCtrl1").ShowDialog(4);
 }
//另存为
 function OtherSave(){
 	document.getElementById("PageOfficeCtrl1").ShowDialog(3);
 }
 function windowClose(){
	 window.opener=null;
	 window.open('','_self');
	 window.close();
 }
 function insertPrintAttach(){
	 var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
	 var result = {};
	 result["FILE_PATH"] = strCustomSaveResult;
	 result["BUS_TABLENAME"] = "T_BDC_DATAQUERY";
	 result["FLOW_EXEID"] = "${TemplateData.xmsqbh}";
	 result["FILE_NAME"] = "${TemplateData.TemplateName}";
	 $.post("printAttachController.do?saveOrUpdate",result, function(resultJson) {});
 }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
      <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
