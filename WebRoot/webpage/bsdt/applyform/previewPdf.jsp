<%@page import="java.io.Console"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
    String filePath =(String) request.getAttribute("fileAllPath");
    PDFCtrl pdfCtrl1 = new PDFCtrl(request);
    //设置 PDFCtrl 的运行服务页面
 	pdfCtrl1.setServerPage(path + "/poserver.zz"); // 必须
	//关闭菜单栏
	pdfCtrl1.setTheme(ThemeType.CustomStyle);
	pdfCtrl1.setMenubar(false);
	pdfCtrl1.setTitlebar(false);
	//打开PDF文档
	if(filePath!=null&&!filePath.equals("")){
		pdfCtrl1.webOpen(filePath);
	}
	pdfCtrl1.setTagId("PDFCtrl1");// 必须
	
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PDFCtrl1").FullScreen = false;
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
      <po:PDFCtrl id="PDFCtrl1"></po:PDFCtrl>
</body>
