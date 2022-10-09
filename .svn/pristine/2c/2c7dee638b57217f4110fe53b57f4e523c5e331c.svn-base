<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
    //***************************卓正PageOffice组件的使用********************************

			String webPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String rootPath = request.getContextPath();
			request.setAttribute("webPath", webPath);
			PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
			poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
			poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);
			poCtrl1.addCustomToolButton("页面设置", "ShowDialog_1()", 0);
			poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
			Map datamap = (Map) request.getAttribute("TemplateData");
			String templatePath = (String) datamap.get("TemplatePath");
			poCtrl1.setSaveFilePage("");
			poCtrl1.setTitlebar(false);
			poCtrl1.setMenubar(false);
			poCtrl1.setOfficeToolbars(false);
			poCtrl1.setAllowCopy(false);
			poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
		   if (templatePath != null && !templatePath.equals("")) {
				if(templatePath.endsWith(".doc") || templatePath.endsWith(".docx")){
					poCtrl1.webOpen(templatePath, OpenModeType.docReadOnly, "evecom");
				}else {
					poCtrl1.webOpen(templatePath, OpenModeType.xlsReadOnly, "evecom");
				}
			} else {
				//poCtrl1.webCreateNew("evecom", DocumentVersion.Word2003);
				poCtrl1.webCreateNew("evecom", DocumentVersion.Excel2003);
			}
			poCtrl1.setJsFunction_AfterDocumentOpened("load()");
			poCtrl1.setJsFunction_AfterDocumentSaved("insertPrintAttach()");
			poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
// 电子签证文件路径
var localSignFilePath = "";
var uploadFullPath = "";
// 用于保存到服务器签证后的pdf绝对路径
var uploadSignFilePath = "";
// 用于保存到数据库中的路径
var signPdfPath = "";
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
}
// 关闭当前打印窗口关闭设备显示牌
window.onbeforeunload=onclose;
function onclose() {
	// 关闭设备显示牌
	//GWQ.GWQ_CancelOperate();
}
 
//页面设置
function ShowDialog_1() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(5);
}
//页面打印
function ShowDialog_2() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(4);
    // 保存打印日志
    insertPrintAttach();
}

// 上传打印文件到文件服务器、保存日志
function insertPrintAttach(){
	 var result = {};
	 var templatePath = "${TemplateData.TemplatePath}";
	 var startInx = templatePath.indexOf("attachFiles");
	 result["FILE_PATH"] = templatePath.substr(startInx);
	 result["BUS_TABLENAME"] = "T_WSBS_CERTIFICATE";
	 result["FLOW_EXEID"] = "${TemplateData.xmsqbh}";
	 result["FILE_NAME"] = "${TemplateData.TemplateName}";
	 $.post("printAttachController.do?saveOrUpdate",result, function(resultJson) {});
}


</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
	<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
