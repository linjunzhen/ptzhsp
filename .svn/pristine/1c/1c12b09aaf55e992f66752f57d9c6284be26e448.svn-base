<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">

function creditInquiryByIDNumber(){
	var IDNumber = document.getElementById('IDNumber').value;
	if(IDNumber == "" || IDNumber == null || IDNumber == undefined){
		alert("请填写身份证号码！");
		return;
	}
	$.dialog.open("callController.do?creditInquiry&IDNumber="+IDNumber, {
		title : "信用报告查询 详询:15980100998",
		width:"910px",
		height:"90%",
		lock: true,
		resize:false,
	}, false);
}

function creditInquiryByENumber(){
	var ENumber = document.getElementById('ENumber').value;
	if(ENumber == "" || ENumber == null || ENumber == undefined){
		alert("请填写统一社会信用代码！");
		return;
	}
	$.dialog.open("callController.do?creditInquiry&ENumber="+ENumber, {
		title : "信用报告查询 详询:15980100998",
		width:"910px",
		height:"90%",
		lock: true,
		resize:false,
	}, false);
}

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="CreditInquiryForm" method="post">
		<!--==========隐藏域部分开始 ===========-->
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
			</tr>
			<tr>
				<td><span style="width: 120px;float:left;text-align:right;">身份证号：</span>
					<input type="text" style="width:150px;float:left;" maxlength="18"
					class="eve-input validate[required]" 
					name="IDNumber" id= "IDNumber"/><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
<%--					<a href="javascript:void(0);" onclick="creditInquiryByIDNumber()">查 询</a>--%>
					<input type="button" value="查 询" onclick="creditInquiryByIDNumber()()">
				</td>
			</tr>
			<tr>
				<td><span style="width: 120px;float:left;text-align:right;">统一社会信用代码：</span>
					<input type="text" style="width:150px;float:left;" maxlength="27"
					class="eve-input validate[required]" 
					name="ENumber" id= "ENumber"/><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
<%--					<a href="javascript:void(0);" onclick="creditInquiryByENumber()">查 询</a>--%>
					<input type="button" value="查 询" onclick="creditInquiryByENumber()">
				</td>
			</tr>


		</table>
		<div class="eve_buttons">
			<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
	<div class="treeContent eve-combotree"
		style="display:none; position: absolute;" id="DEPART_IDTreeContent">
		<ul class="ztree" style="margin-top:0; width:160px;height: 150px"
			id="DEPART_IDTree"></ul>
	</div>

</body>

