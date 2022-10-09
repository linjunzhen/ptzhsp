<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.WdAutoFitBehavior"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.WordDocument"%>
<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.Table"%>
<%@page import="com.zhuozhengsoft.pageoffice.wordwriter.DataRegion"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ page import="com.zhuozhengsoft.pageoffice.excelwriter.Workbook" %>
<%@ page import="com.zhuozhengsoft.pageoffice.excelwriter.Sheet" %>
<%@ page import="com.zhuozhengsoft.pageoffice.excelwriter.Cell" %>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
    //***************************卓正PageOffice组件的使用********************************

    String webPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String rootPath = request.getContextPath();
    request.setAttribute("webPath", webPath);
    Map<String, Object> datamap = (Map<String,Object>) request.getAttribute("TemplateData");
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
    if(request.getAttribute("poCtrl") != null) {
        poCtrl1 = (PageOfficeCtrl) request.getAttribute("poCtrl");
    }
    poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
    poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);
    // 是否展示电子签证按钮
    if ("true".equals(request.getAttribute("isSignButton"))) {
        poCtrl1.addCustomToolButton("电子签字", "signNames()", 6);
    }

    //动态表格开始

    Workbook workBook = new Workbook();
    Sheet sheet = workBook.openSheet("Sheet1");

    for( Map.Entry<String,Object> entry : datamap.entrySet()){
        if(entry.getKey().indexOf("_JSON")>0 && entry.getKey().indexOf("_JSON_")<0){
            datamap.replace(entry.getKey(),"");//模板中json值置空，避免pageoffice赋值时前端报错
        }
    }
    for(Object entry: datamap.keySet()) {
        String valueName = entry.toString();
        Cell cellYear =sheet.openCellByDefinedName(valueName);
        if(cellYear !=null){
            cellYear.setValue((String)datamap.get(valueName));
        }
    }
    Calendar calendar = Calendar.getInstance();
    Cell cellYear =sheet.openCellByDefinedName("year");
    if(cellYear!=null){
        cellYear.setValue(String.valueOf(calendar.get(Calendar.YEAR)));
    }
    Cell cellMonth =sheet.openCellByDefinedName("month");
    if(cellMonth!=null){
        cellMonth.setValue(String.valueOf(calendar.get(Calendar.MONTH)+1));
    }
    Cell cellDay =sheet.openCellByDefinedName("day");
    if(cellDay!=null){
        cellDay.setValue(String.valueOf(calendar.get(Calendar.DATE)));
    }
    poCtrl1.setWriter(workBook);
    //动态表格结束

    poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
    String filepath = null;

    String templatePath = (String) datamap.get("TemplatePath");
    String bdcTemplatePath = (String) datamap.get("bdcPath");
    if (bdcTemplatePath != null && !bdcTemplatePath.equals("")) {
        filepath = bdcTemplatePath;
    }else if (datamap.get("TemplatePath") != null && !datamap.get("TemplatePath").equals("")) {
        filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/" + "attachFiles/readtemplate/files/" + templatePath;
    }
    poCtrl1.setSaveFilePage("");
    poCtrl1.setTitlebar(false);
    poCtrl1.setMenubar(false);
    poCtrl1.setOfficeToolbars(false);
    poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    if (filepath != null && !filepath.equals("")) {
        //poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "evecom");
        if(templatePath.endsWith(".doc") || templatePath.endsWith(".docx")){
            poCtrl1.webOpen(filepath, OpenModeType.docReadOnly, "evecom");
        }else {
            poCtrl1.webOpen(filepath, OpenModeType.xlsReadOnly, "evecom");
        }
    } else {
        //poCtrl1.webCreateNew("evecom", DocumentVersion.Word2003);
        poCtrl1.webCreateNew("evecom", DocumentVersion.Excel2003);
    }
    //poCtrl1.setJsFunction_AfterDocumentOpened("load()");
//	poCtrl1.setJsFunction_AfterDocumentSaved("insertPrintAttach()");
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
    loadData();
}

// 关闭当前打印窗口关闭设备显示牌
window.onbeforeunload=onclose;
function onclose() {
	// 关闭设备显示牌
	GWQ.GWQ_CancelOperate();
}

<%--function loadData(){--%>
<%--var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;--%>
 <%--drlist.Refresh();--%>
    <%--var bkName = "";--%>
    <%--<%for (Object entry : datamap.keySet()) {%>--%>
    <%--for (var i = 0; i < drlist.Count; i++) {--%>
        <%--bkName = drlist.Item(i).Name;--%>
        <%--if('<%=entry.toString()%>'==bkName.substring(bkName.indexOf('_')+1,bkName.lastIndexOf('_'))){--%>
        	<%--drlist.Item(i).Value="<%=datamap.get(entry.toString())%>";--%>
        <%--}--%>
    <%--}--%>
    <%--<%}%>--%>
<%--} --%>
 
//页面打印
function ShowDialog_2() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(4);
    // 保存打印日志
    //insertPrintAttach();
}

// 打印日志保存
function insertPrintAttach(){
	 var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
	 var result = {};
	 result["FILE_PATH"] = strCustomSaveResult;
	 result["BUS_TABLENAME"] = "T_WSBS_READTEMPLATE";
	 result["FLOW_EXEID"] = "${TemplateData.xmsqbh}";
	 result["FILE_NAME"] = "${TemplateData.TemplateName}";
	 $.post("printAttachController.do?saveOrUpdate",result, function(resultJson) {});
}

// 电子签证打印日志保存
function insertPrintSignAttach(){
	 var result = {};
	 result["FILE_PATH"] = signPdfPath;
	 result["BUS_TABLENAME"] = "T_WSBS_READTEMPLATE";
	 result["FLOW_EXEID"] = "${TemplateData.xmsqbh}";
	 result["FILE_NAME"] = "${TemplateData.TemplateName}";
	 $.post("printAttachController.do?saveOrUpdate",result, function(resultJson) {});
}

//电子签证
function signNames() {
	document.getElementById("PageOfficeCtrl1").WebSave();
	var signNameKey = "申请人";
	// 申请书打印
	if('<%=templatePath%>' == "20190402155521.doc") {
		signNameKey = "（签章）";
	}
	if('<%=templatePath%>' == "20190402155520.doc") {
		signNameKey = "（签章）";
	}
	// word文件转pdf
	AppUtil.ajaxProgress({
		url : encodeURI("printAttachController.do?toSignNames&signNameKey=" + signNameKey + "&isTwoSign=" + '${isTwoSign}' + "&printTemplateName=" + '<%=templatePath%>'),
		params : {},
		callback : function(resultJson) {
			if(resultJson.jsonString != undefined) {
				var json = JSON.parse(resultJson.jsonString);
				// 保存本地的未签证的pdf文件地址
				localSignFilePath = json.localSignFilePath;
				uploadFullPath = json.uploadFullPath;
				localPdfFilePath = json.localPdfFilePath;
				uploadSignFilePath = json.uploadSignFilePath;
				signPdfPath = json.signPdfPath;
				// 将生成的pdf文件发送设备终端用于打印
				DoGWQ_StartSign("${webPath}" + json.uploadFullPath, '', json.location,'预留字段',60);
			}
		}
	});
}

//启动电子签证
function DoGWQ_StartSign(PDFPath, XmlPath, mylocation, VoiceStr, timeout) {
	if (PDFPath == "" || PDFPath == undefined) {
		alert("未指定PDF文档！");
		return;
	}
	var ret = GWQ.DoGWQ_StartSign(PDFPath, XmlPath, mylocation, VoiceStr, timeout);
	if (ret == 0) {
		alert("电子签字启动成功，请等待客户在签字版签字...");
	} else {
		alert("电子签证启动失败，请尝试重启手写板，若问题依旧存在， 请联系管理员，错误代码为：" + ret);
	}
}
</script>
<script language="javascript" for="GWQ"
	event="OnAfterGWQ_StartSign(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)"
	type="text/javascript">
	if (0 == ErrorCode) {
		top.art.dialog({
			content : "电子签证成功，正在切换到签证预览界面，请稍后...",
			icon : "succeed",
			time: 5,
			ok : true
		});
		AppUtil.ajaxProgress({
			url : "printAttachController.do?saveSignFile&pdfBaseFile=" + SignPdfBase64 + "&uploadSignFilePath=" + uploadSignFilePath,
			params : {},
			callback : function(resultJson) {
				window.location.href = "printAttachController.do?readSignNamesFile&downPdfFilePath=" + uploadSignFilePath;
			}
		});
		// 保存电子签证打印日志
		insertPrintSignAttach();
	} else if (-9 == ErrorCode) {
		alert("客户取消了电子签字！");
	} else if (-2 == ErrorCode) {
		alert("电子签字超时...");
	} else {
		alert("电子签字异常，请联系管理员！错误代码为：" + ErrorCode);
	}
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
	<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0"
		height="0"></object>
	<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
</body>
