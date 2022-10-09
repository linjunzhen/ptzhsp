<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>

<script type="text/javascript">
var i=1;
$(function() {
    var projectCode="${projectCode}";
    var itemName="${itemName}";
    var itemCode="${itemCode}";
    var exeId="${exeId}";
    var url=encodeURI("creditController.do?electDocumentDatagrid&projectCode="+
    		projectCode+"&itemName="+itemName+"&itemCode="+itemCode+"&exeId="+exeId);
	girdDatagrid(url);
});

function girdDatagrid(url) {
	$('#ElectDocumentInfoGrid').datagrid({  //初始化datagrid
		url:url,
		idField:"fileIdentifier",
		rownumbers: false,
		fit:true,
		border:false,
		checkOnSelect:true,
		selectOnCheck:true,
		singleSelect:true,
		fitColumns:true,
		pagination:true,
		pageSize:15,
		pageList:[15,20,30],
		columns:[[
			{field:'fileIdentifier',title:'电子文件标识',hidden:true, width:30,align:'center'},
			{field:'fileSignatureStatus',title:'签章状态',hidden:true, width:30,align:'center'},
			{field:'serialNumber',title:'序号',width:30,align:'center'},
			{field:'fileNumber',title:'文件编号',width:120,align:'left'},
			{field:'fileName',title:'电子文件名称',width:120,align:'left'},
			{field:'fileIssuingAuthorityName',title:'文件颁发机构',width:120,align:'left'},
			{field:'fileRetentionPeriod',title:'文件保管期限',width:80,align:'left'},
			{field:'FileID',title:'操作',width:160,align:'left',formatter: function(value,row,index){
					return formatOperator(value,row);
				}}
		]]
	});
}

function formatOperator(val,row){
    var fileIdentifier = row.fileIdentifier;
    var fileSignatureStatus = row.fileSignatureStatus;
    href = "<a  onclick=uploadFile('" + fileIdentifier + "','" + fileSignatureStatus ;
    href += "')><span style='color:green;font-size: 14px;margin-left:5px;cursor: pointer;'>" + "上传至审批平台" + "</span></a>";
    return href;
}

function uploadFile(fileIdentifier, fileSignatureStatus){
	var itemName = $("#itemName").val();
	var itemCode = $("#itemCode").val();
	var exeId = $("#exeId").val();
    $.post("creditController.do?uploadElectDocumentFile",{
    	fileIdentifier:fileIdentifier,
    	itemName:itemName,
    	itemCode:itemCode,
    	exeId,exeId,
    	fileSignatureStatus,fileSignatureStatus,
        attachKey:"${attachKey}",
        busRecordId:"${busRecordId}",
        busTableName:"${busTableName}"
    },
    function(responseText1, status, xhr) {
        var resultJson1 = $.parseJSON(responseText1);
        art.dialog.data("resultJsonInfo", resultJson1);
        AppUtil.closeLayer();
    });
}

function openCreditFeedBackView(val,fileId){
	var title = "";
	if (val == 1) {
		title = "缺失证照反馈";
	} else if (val == 2) {
		title = "错误证照反馈";
	}
	var itemId="${itemId}";
	var aheadUserId="${aheadUserId}";
	var backUserId="${backUserId}";
	var names="${names}";
	var codes="${codes}";
	var creditMark="${creditMark}";
	var url = "creditController.do?creditFeedBackView&creditFeedBackType=" + val +
			"&itemId=" + itemId + "&aheadUserId=" + aheadUserId + "&backUserId=" + backUserId
			+ "&codes=" + codes + "&names=" + names + "&creditMark=" + creditMark;
	if (val == 2) {
		url += "&fileId=" + fileId;
	}
	$.dialog.open(encodeURI(url), {
		title: title,
		width: "800px",
		height: "400px",
		lock: true,
		resize: false
	}, false);
}

function searchCredit(){
	//新云电子证照需要额外传的新数据
    var projectCode="${projectCode}";
    var itemName="${itemName}";
    var itemCode="${itemCode}";
    var exeId="${exeId}";
	var url = "creditController.do?electDocumentDatagrid&projectCode="+
						projectCode+"&itemName="+itemName+"&itemCode="+itemCode+"&exeId="+exeId;
	var creditCode = $("input[name='CREDIT_CODE']").val();
	var creditName = $("input[name='CREDIT_NAME']").val();
	if (creditCode && creditCode != "") {
		url += "&creditCode=" + creditCode;
	}
	if (creditName && creditName != "") {
		url += "&creditName=" + creditName;
	}
	girdDatagrid(encodeURI(url));
}

function searchReset() {
	$("input[name='CREDIT_CODE']").val('');
	$("input[name='CREDIT_NAME']").val('');
}

function closePage(){
	art.dialog.data("creditInfo",null);
	AppUtil.closeLayer();
}
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
<div class="easyui-layout" fit="true">
	<div region="center">
		<input type="hidden" name="itemName" id="itemName" value="${itemName}">
		<input type="hidden" name="itemCode" id="itemCode" value="${itemCode}">
		<input type="hidden" name="exeId" id="exeId" value="${exeId}">
		<div id="FormFieldToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<span>文件编号：</span><input type="text" class="eve-input" name="CREDIT_CODE">
							<span>电子文件名称：</span><input type="text" class="eve-input" name="CREDIT_NAME">
							<a href="#" class="easyui-linkbutton"
							   plain="true"
							   onclick="searchCredit();">搜索</a>
							<a href="#" class="easyui-linkbutton"
							   plain="true"
							   onclick="searchReset();">重置</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<table id="ElectDocumentInfoGrid"></table>
	</div>
</div>
</body>
