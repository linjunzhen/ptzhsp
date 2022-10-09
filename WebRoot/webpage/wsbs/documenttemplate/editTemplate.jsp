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
	Map map = (Map) request.getAttribute("template");
	if(map.get("templatePath")!=null&& !map.get("templatePath").equals("")){
	    filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
	            + "/" + map.get("templatePath").toString(); 
	}
	poCtrl1.addCustomToolButton("打开", "Open", 3);
	poCtrl1.addCustomToolButton("保存", "Save", 1);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("另保存", "OtherSave", 1);
	poCtrl1.addCustomToolButton("关闭", "closeDoc", 10);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("页面设置", "ShowDialog_1()", 0);
	poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);
	poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
	if(map.get("templatePath")!=null&& !map.get("templatePath").equals("")){
	    poCtrl1.setSaveFilePage(request.getContextPath() + "/documentTemplateController/savefile.do?templatePath="+map.get("templatePath")+"&isTemplate="+map.get("isTemplate"));
	}else{
	    poCtrl1.setSaveFilePage(request.getContextPath() + "/documentTemplateController/savefile.do?isTemplate="+map.get("isTemplate"));
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
    poCtrl1.setJsFunction_AfterDocumentSaved("saveSuccess()");

    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
    loadData();
}
function saveSuccess(){
	var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
	art.dialog.data("saveTemplateInfo", {
		templatePath:strCustomSaveResult
    });
	AppUtil.closeLayer();
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
    	/*art.dialog.confirm('当前文档未保存，是否继续关闭？', function () {
        	AppUtil.closeLayer(); 
        }, function () {
           
        });*/
    	 if(confirm("当前文档未保存，是否继续关闭？"))
    	 {
    		 AppUtil.closeLayer(); 
    	 }
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

function loadData(){
    var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;
     drlist.Refresh();
        var bkName = "";
        <%
        for(Object entry : map.keySet()){
        %>
        for (var i = 0; i < drlist.Count; i++) {
            bkName = drlist.Item(i).Name;
            if('<%=entry.toString()%>'==bkName.substring(bkName.indexOf('_')+1,bkName.lastIndexOf('_'))){
                drlist.Item(i).Value="<%=map.get(entry.toString())%>";
            }
        }
        <%}%>
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
      <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
