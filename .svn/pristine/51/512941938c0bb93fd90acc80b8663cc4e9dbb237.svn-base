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
    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    String filepath =null;
    filepath = (String)request.getAttribute("templatePath");
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);
    poCtrl1.setOfficeToolbars(false);
    poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);

    if(filepath!=null&&!filepath.equals("")){
        poCtrl1.webOpen(filepath, OpenModeType.docReadOnly, "evecom");
       }else{
        poCtrl1.webCreateNew("evecom",DocumentVersion.Word2003);
       }
    poCtrl1.setJsFunction_AfterDocumentOpened("load()");
    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
}
//页面打印
function ShowDialog_2() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(4);
}
//另存为
function OtherSave(){
	document.getElementById("PageOfficeCtrl1").ShowDialog(3);
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
      <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
