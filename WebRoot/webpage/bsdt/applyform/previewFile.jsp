<%@page import="java.io.Console"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
    
 
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    String filePath =(String) request.getAttribute("fileAllPath");
    String fileType =(String) request.getAttribute("fileType");
    poCtrl1.setCustomToolbar(false); //false的话则不出现pageoffice自己的工具栏
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);
    poCtrl1.setOfficeToolbars(false);
    poCtrl1.setAllowCopy(false);
    poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    if(filePath!=null&&!filePath.equals("")){
        if("doc".equals(fileType) || "docx".equals(fileType)){
           poCtrl1.webOpen(filePath, OpenModeType.docReadOnly, "evecom");
        }else if("xls".equals(fileType) || "xlsx".equals(fileType)){
           poCtrl1.webOpen(filePath, OpenModeType.xlsReadOnly, "evecom");
        }else if("ppt".equals(fileType) || "pptx".equals(fileType)){
           poCtrl1.webOpen(filePath, OpenModeType.pptReadOnly, "evecom");
        }else{
           poCtrl1.webCreateNew("evecom",DocumentVersion.Word2003);
        }
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
