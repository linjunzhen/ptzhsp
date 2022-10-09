<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,autocomplete"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script>
	$(function() {
		$("#PROVINCEDATA_FORM").find("input[type='text']").attr("disabled", "disabled");
		$("#PROVINCEDATA_FORM").find("[name='APPLYTYPE']").attr("disabled", "disabled");
		$("#PROVINCEDATA_FORM").find("[name='APPLYFROM']").attr("disabled", "disabled");
		$("#PROVINCEDATA_FORM").find("[name='FRIDTYPE']").attr("disabled", "disabled");
		$("#PROVINCEDATA_FORM").find("[name='OPERATORCERTIFICATETYPE']").attr("disabled", "disabled");
		$("#PROVINCEDATA_FORM").find("[name='TYPE']").attr("disabled", "disabled");
		$("#PROVINCEDATA_FORM").find("[name='DELIVERYRESULT']").attr("disabled", "disabled");
	});

	function formatFileName(val, row) {
		var fileId = row.FILE_ID;
		var href = "<a href=\"javascript:void(0)\" ";
		href += ("onclick=\"AppUtil.downLoadFile('" + fileId + "');\" >");
		href += (val + "</a>");
		return href;
	}
</script>
</head>
<style>
.datagrid-btable tr {
	height: 40px;
}

.datagrid-btable td {
	word-break: break-all;
}

.tab_text {
	width: 72%;
}
</style>

<body>
	<form id="PROVINCEDATA_FORM" method="post">

		<!-- 基本信息开始 -->
		<div id="apasinfobaseId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>事项基本信息</span>
					</th>
				</tr>
			</table>

			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="apasinfobaseInfoId">
				<tr id="apasinfobaseInfoId_1">
					<td>
						<table class="tab_tk_pro2">
							<!-- 事项信息开始 -->
							<tr>
								<td class="tab_width">项目编号：</td>
								<td>
									<input type="text" class="tab_text" name="PROJECTCODE" value="${apasinfobase.PROJECTCODE}" />
								</td>
								<td class="tab_width">申报号：</td>
								<td>
									<input type="text" class="tab_text" name="SN" value="${apasinfobase.SN}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">项目名称：</td>
								<td colspan="4">
									<input type="text" style="width: 88%;" class="tab_text" name="PROJECTNAME"
										value="${apasinfobase.PROJECTNAME}" />
								</td>
							</tr>
							<!-- 事项信息结束 -->

							<!-- 申报对象信息开始 -->
							<tr>
								<td class="tab_width">申报种类：</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="SBZL"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择申报种类"
										name="APPLYTYPE" value="${apasinfobase.APPLYTYPE}">
									</eve:eveselect>
								</td>
								<td class="tab_width">申请来源：</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="SQLY"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择申请来源"
										name="APPLYFROM" value="${apasinfobase.APPLYFROM}">
									</eve:eveselect>
								</td>
							</tr>

							<tr>
								<td class="tab_width">申报对象名称：</td>
								<td>
									<input type="text" class="tab_text" name="NAME" value="${apasinfobase.NAME}" />
								</td>
								<td class="tab_width">申报对象证件号码：</td>
								<td>
									<input type="text" class="tab_text" name="ID" value="${apasinfobase.ID}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">邮政编码：</td>
								<td>
									<input type="text" class="tab_text" name="POSTCODE" value="${apasinfobase.POSTCODE}" />
								</td>
								<td class="tab_width">邮箱：</td>
								<td>
									<input type="text" class="tab_text" name="EMAIL" value="${apasinfobase.EMAIL}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">申报对象手机：</td>
								<td>
									<input type="text" class="tab_text" name="MOBILEPHONE" value="${apasinfobase.MOBILEPHONE}" />
								</td>
								<td class="tab_width">申报对象电话：</td>
								<td>
									<input type="text" class="tab_text" name="TEL" value="${apasinfobase.TEL}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">联系地址：</td>
								<td colspan="4">
									<input type="text" style="width: 88%;" class="tab_text" name="ADDR"
										value="${apasinfobase.ADDR}" />
								</td>
							</tr>
							<!-- 申报对象信息结束 -->

							<!-- 法人信息开始 -->
							<tr>
								<td class="tab_width">法人代表姓名：</td>
								<td>
									<input type="text" class="tab_text" name="UNITLEALPERSON"
										value="${apasinfobase.UNITLEALPERSON}" />
								</td>
								<td class="tab_width">性别：</td>
								<td>
									<input type="text" class="tab_text" name="SEX" value="${apasinfobase.SEX}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">法人证件类型：</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="DocumentType"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
										name="FRIDTYPE" value="${apasinfobase.FRIDTYPE}">
									</eve:eveselect>
								</td>
								<td class="tab_width">法人证件号码：</td>
								<td>
									<input type="text" class="tab_text" name="FRID" value="${apasinfobase.FRID}" />
								</td>
							</tr>
							<!-- 法人信息结束 -->

							<!-- 经办人信息开始 -->
							<tr>
								<td class="tab_width">经办人姓名：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORNAME"
										value="${apasinfobase.OPERATORNAME}" />
								</td>
								<td class="tab_width">性别：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORSEX" value="${apasinfobase.OPERATORSEX}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">经办人证件类型：</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="DocumentType"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
										name="OPERATORCERTIFICATETYPE" value="${apasinfobase.OPERATORCERTIFICATETYPE}">
									</eve:eveselect>
								</td>
								<td class="tab_width">经办人证件号码：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORCERTIFICATENUMBER"
										value="${apasinfobase.OPERATORCERTIFICATENUMBER}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">经办人手机号码：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORMOBILEPHONE"
										value="${apasinfobase.OPERATORMOBILEPHONE}" />
								</td>
								<td class="tab_width">经办人办公/家庭电话：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORTEL" value="${apasinfobase.OPERATORTEL}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">经办人电子邮箱：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORMAIL"
										value="${apasinfobase.OPERATORMAIL}" />
								</td>
								<td class="tab_width">经办人邮政编码：</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORPOSTCODE"
										value="${apasinfobase.OPERATORPOSTCODE}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">经办人家庭住址：</td>
								<td colspan="4">
									<input type="text" style="width: 88%;" class="tab_text" name="OPERATORADDRESS"
										value="${apasinfobase.OPERATORADDRESS}" />
								</td>
							</tr>
							<!-- 经办人信息结束 -->
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 基本信息结束 -->

		<!-- 环节流转开始 -->
		<div id="nextsId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>环节流转信息</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="nextsInfoId">
				<tr id="nextsInfoId_1">
					<td>
						<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
							<tr>
								<td class='tab_width1' width='80px' align='center'>序号</td>
								<td class='tab_width1' width='80px' align='center'>办理人</td>
								<td class='tab_width1' width='80px' align='center'>开始时间</td>
								<td class='tab_width1' width='80px' align='center'>环节流转时间</td>
								<td class='tab_width1' width='80px' align='center'>意见类型</td>
								<td class='tab_width1' width='80px' align='center'>办理意见内容</td>
							</tr>
							<c:forEach items="${nexts }" var="nextsItem" varStatus="nextsIndex">
								<tr>
									<td class='tab_width1' width='80px' align='center'>${nextsIndex.index + 1}</td>
									<td class='tab_width1' width='80px' align='center'>${nextsItem.TRANSACTOR}</td>
									<td class='tab_width1' width='80px' align='center'>${nextsItem.STARTTIME}</td>
									<td class='tab_width1' width='80px' align='center'>${nextsItem.DECLARETIME}</td>
									<c:choose>
										<c:when test="${nextsItem.OPINIONTYPE=='1'}">
											<td class='tab_width1' width='80px' align='center'>通过</td>
										</c:when>
										<c:when test="${nextsItem.OPINIONTYPE=='2'}">
											<td class='tab_width1' width='80px' align='center'>退回</td>
										</c:when>
										<c:when test="${nextsItem.OPINIONTYPE=='9'}">
											<td class='tab_width1' width='80px' align='center'>其他</td>
										</c:when>
										<c:otherwise>
											<td class='tab_width1' width='80px' align='center'></td>
										</c:otherwise>
									</c:choose>
									<td class='tab_width1' width='120px' align='center'>${nextsItem.OPINION}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 环节流转结束 -->

		<!-- 统建特殊环节审批开始 -->
		<div id="specialId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>特殊环节审批信息</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="specialIdInfoId">
				<tr id="specialIdInfoId_1">
					<td>
						<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
							<tr>
								<td class='tab_width1' width='80px' align='center'>序号</td>
								<td class='tab_width1' width='80px' align='center'>环节开始办理人</td>
								<td class='tab_width1' width='80px' align='center'>环节结束办理人</td>
								<td class='tab_width1' width='80px' align='center'>开始时间</td>
								<td class='tab_width1' width='80px' align='center'>结束时间</td>
								<td class='tab_width1' width='80px' align='center'>环节类型</td>
								<td class='tab_width1' width='80px' align='center'>备注</td>
							</tr>
							<c:forEach items="${specials }" var="specialsItem" varStatus="specialIndex">
								<tr>
									<td class='tab_width1' width='80px' align='center'>${specialIndex.index + 1}</td>
									<td class='tab_width1' width='80px' align='center'>${specialsItem.SUSER}</td>
									<td class='tab_width1' width='80px' align='center'>${specialsItem.EUSER}</td>
									<td class='tab_width1' width='80px' align='center'>${specialsItem.SDATETIME}</td>
									<td class='tab_width1' width='80px' align='center'>${specialsItem.EDATETIME}</td>
									<td class='tab_width1' width='80px' align='center'>${specialsItem.FTYPE}</td>
									<td class='tab_width1' width='120px' align='center'>${specialsItem.MEMO}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 统建特殊环节审批结束 -->

		<!-- 办结信息开始 -->
		<div id="finishsId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>办结信息信息</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">办理人名称：</td>
						<td>
							<input type="text" class="tab_text" name="TRANSACTOR" value="${finishs.TRANSACTOR}" />
						</td>
						<td class="tab_width">办理描述：</td>
						<td>
							<input type="text" class="tab_text" name="REASON" value="${finishs.REASON}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">办理结果：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="BLJG"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择办理结果" name="TYPE"
								value="${finishs.TYPE}">
							</eve:eveselect>
						</td>
						<td class="tab_width">是否快递送达结果：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="SFKDSD"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择是否快递送达"
								name="DELIVERYRESULT" value="${finishs.DELIVERYRESULT}">
							</eve:eveselect>
						</td>
					</tr>

					<tr>
						<td class="tab_width">办结时间：</td>
						<td>
							<input type="text" class="tab_text" name="DECLARETIME" value="${finishs.DECLARETIME}" />
						</td>
					</tr>
				</table>
			</td>
		</div>
		<!-- 办结信息结束 -->

		<!-- 附件数据开始 -->
		<div id="attrsId" style="height: 300px;">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>附件数据信息</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<div class="easyui-layout" fit="true">
				<div region="center">
					<table class="easyui-datagrid" rownumbers="true" pagination="false" fitcolumns="true"
						nowrap="false" method="post" idfield="FILE_ID" checkonselect="false" selectoncheck="false"
						fit="true" border="false" url="executionController.do?provinceAttrList&exeId=${exeId}">
						<thead>
							<tr>
								<th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th>
								<th data-options="field:'FILE_NAME',align:'left'" width="250" formatter="formatFileName">材料名称</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<!-- 附件数据结束 -->
	</form>
</body>
</html>
