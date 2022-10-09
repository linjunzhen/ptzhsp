<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    poCtrl1.setCustomToolbar(false); //false的话则不出现pageoffice自己的工具栏
    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    String filepath =null;
    Map map = (Map) request.getAttribute("readTemplate");
    if(map.get("READ_DOC")!=null&& !map.get("READ_DOC").equals("")){
        filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/" + "attachFiles/readtemplate/files/"+map.get("READ_DOC").toString(); 
    }
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
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
      <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
