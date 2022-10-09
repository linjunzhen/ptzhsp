<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
    Map<String,Object> datamap = new HashMap<String,Object>();
    datamap.put("nian", "2015");
    datamap.put("xm", "lij");
    datamap.put("dh","18059026449");
    datamap.put("year","189");
    datamap.put("yea231231r","189");
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
    poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);
    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    String filepath =null;
    Map map = (Map) request.getAttribute("tickets");
    if(map.get("TICKETS_DOC")!=null&& !map.get("TICKETS_DOC").equals("")){
        filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/" + "attachFiles/tickets/files/"+map.get("TICKETS_DOC").toString(); 
    }
    poCtrl1.setSaveFilePage(request.getContextPath() + "/printAttachController/savefile.do");
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);
    poCtrl1.setOfficeToolbars(true);
    poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    if(filepath!=null&&!filepath.equals("")){
        poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "evecom");
        //poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "lij");
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
	 drlist.Refresh();
	    var bkName = "";
	    <%
	    for(Object entry : datamap.keySet()){
	    %>
	    for (var i = 0; i < drlist.Count; i++) {
	        bkName = drlist.Item(i).Name;
	        if('<%=entry.toString()%>'==bkName.substring(bkName.indexOf('_')+1,bkName.lastIndexOf('_'))){
	        	drlist.Item(i).Value="<%=datamap.get(entry.toString())%>";
	        }
	    }
	    <%}%>
} 
 
//页面打印
 function ShowDialog_2() {
	 document.getElementById("PageOfficeCtrl1").WebSave();
     document.getElementById("PageOfficeCtrl1").ShowDialog(4);
 }
 function insertPrintAttach(){
	 var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
	 var result = {};
	 result["FILE_PATH"] = strCustomSaveResult;
	 result["BUS_TABLENAME"] = "T_WSBS_TICKETS";
	 result["FLOW_EXEID"] = "${TemplateData.xmsqbh}";
     result["FILE_NAME"] = "${TemplateData.TemplateName}";
	 $.post("printAttachController.do?saveOrUpdate",result, function(resultJson) {});
 }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
      <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
