<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    poCtrl1.setCustomToolbar(false); //false的话则不出现pageoffice自己的工具栏
    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    Map datamap = (Map) request.getAttribute("TemplateData");
    String templatePath = (String) datamap.get("TemplatePath");
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);
    poCtrl1.setOfficeToolbars(false);
    poCtrl1.setAllowCopy(false);
    poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);

    if(templatePath!=null&&!templatePath.equals("")){
        poCtrl1.webOpen(templatePath, OpenModeType.xlsReadOnly, "evecom");
       }else{
        poCtrl1.webCreateNew("evecom",DocumentVersion.Excel2003);
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
