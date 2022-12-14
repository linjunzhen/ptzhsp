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

		<!-- ?????????????????? -->
		<div id="apasinfobaseId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>??????????????????</span>
					</th>
				</tr>
			</table>

			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="apasinfobaseInfoId">
				<tr id="apasinfobaseInfoId_1">
					<td>
						<table class="tab_tk_pro2">
							<!-- ?????????????????? -->
							<tr>
								<td class="tab_width">???????????????</td>
								<td>
									<input type="text" class="tab_text" name="PROJECTCODE" value="${apasinfobase.PROJECTCODE}" />
								</td>
								<td class="tab_width">????????????</td>
								<td>
									<input type="text" class="tab_text" name="SN" value="${apasinfobase.SN}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">???????????????</td>
								<td colspan="4">
									<input type="text" style="width: 88%;" class="tab_text" name="PROJECTNAME"
										value="${apasinfobase.PROJECTNAME}" />
								</td>
							</tr>
							<!-- ?????????????????? -->

							<!-- ???????????????????????? -->
							<tr>
								<td class="tab_width">???????????????</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="SBZL"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="??????????????????"
										name="APPLYTYPE" value="${apasinfobase.APPLYTYPE}">
									</eve:eveselect>
								</td>
								<td class="tab_width">???????????????</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="SQLY"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="??????????????????"
										name="APPLYFROM" value="${apasinfobase.APPLYFROM}">
									</eve:eveselect>
								</td>
							</tr>

							<tr>
								<td class="tab_width">?????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="NAME" value="${apasinfobase.NAME}" />
								</td>
								<td class="tab_width">???????????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="ID" value="${apasinfobase.ID}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">???????????????</td>
								<td>
									<input type="text" class="tab_text" name="POSTCODE" value="${apasinfobase.POSTCODE}" />
								</td>
								<td class="tab_width">?????????</td>
								<td>
									<input type="text" class="tab_text" name="EMAIL" value="${apasinfobase.EMAIL}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">?????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="MOBILEPHONE" value="${apasinfobase.MOBILEPHONE}" />
								</td>
								<td class="tab_width">?????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="TEL" value="${apasinfobase.TEL}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">???????????????</td>
								<td colspan="4">
									<input type="text" style="width: 88%;" class="tab_text" name="ADDR"
										value="${apasinfobase.ADDR}" />
								</td>
							</tr>
							<!-- ???????????????????????? -->

							<!-- ?????????????????? -->
							<tr>
								<td class="tab_width">?????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="UNITLEALPERSON"
										value="${apasinfobase.UNITLEALPERSON}" />
								</td>
								<td class="tab_width">?????????</td>
								<td>
									<input type="text" class="tab_text" name="SEX" value="${apasinfobase.SEX}" />
								</td>
							</tr>

							<tr>
								<td class="tab_width">?????????????????????</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="DocumentType"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="??????????????????"
										name="FRIDTYPE" value="${apasinfobase.FRIDTYPE}">
									</eve:eveselect>
								</td>
								<td class="tab_width">?????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="FRID" value="${apasinfobase.FRID}" />
								</td>
							</tr>
							<!-- ?????????????????? -->

							<!-- ????????????????????? -->
							<tr>
								<td class="tab_width">??????????????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORNAME"
										value="${apasinfobase.OPERATORNAME}" />
								</td>
								<td class="tab_width">?????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORSEX" value="${apasinfobase.OPERATORSEX}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">????????????????????????</td>
								<td>
									<eve:eveselect clazz="tab_text" dataParams="DocumentType"
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="??????????????????"
										name="OPERATORCERTIFICATETYPE" value="${apasinfobase.OPERATORCERTIFICATETYPE}">
									</eve:eveselect>
								</td>
								<td class="tab_width">????????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORCERTIFICATENUMBER"
										value="${apasinfobase.OPERATORCERTIFICATENUMBER}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">????????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORMOBILEPHONE"
										value="${apasinfobase.OPERATORMOBILEPHONE}" />
								</td>
								<td class="tab_width">???????????????/???????????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORTEL" value="${apasinfobase.OPERATORTEL}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">????????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORMAIL"
										value="${apasinfobase.OPERATORMAIL}" />
								</td>
								<td class="tab_width">????????????????????????</td>
								<td>
									<input type="text" class="tab_text" name="OPERATORPOSTCODE"
										value="${apasinfobase.OPERATORPOSTCODE}" />
								</td>
							</tr>
							<tr>
								<td class="tab_width">????????????????????????</td>
								<td colspan="4">
									<input type="text" style="width: 88%;" class="tab_text" name="OPERATORADDRESS"
										value="${apasinfobase.OPERATORADDRESS}" />
								</td>
							</tr>
							<!-- ????????????????????? -->
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- ?????????????????? -->

		<!-- ?????????????????? -->
		<div id="nextsId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>??????????????????</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="nextsInfoId">
				<tr id="nextsInfoId_1">
					<td>
						<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
							<tr>
								<td class='tab_width1' width='80px' align='center'>??????</td>
								<td class='tab_width1' width='80px' align='center'>?????????</td>
								<td class='tab_width1' width='80px' align='center'>????????????</td>
								<td class='tab_width1' width='80px' align='center'>??????????????????</td>
								<td class='tab_width1' width='80px' align='center'>????????????</td>
								<td class='tab_width1' width='80px' align='center'>??????????????????</td>
							</tr>
							<c:forEach items="${nexts }" var="nextsItem" varStatus="nextsIndex">
								<tr>
									<td class='tab_width1' width='80px' align='center'>${nextsIndex.index + 1}</td>
									<td class='tab_width1' width='80px' align='center'>${nextsItem.TRANSACTOR}</td>
									<td class='tab_width1' width='80px' align='center'>${nextsItem.STARTTIME}</td>
									<td class='tab_width1' width='80px' align='center'>${nextsItem.DECLARETIME}</td>
									<c:choose>
										<c:when test="${nextsItem.OPINIONTYPE=='1'}">
											<td class='tab_width1' width='80px' align='center'>??????</td>
										</c:when>
										<c:when test="${nextsItem.OPINIONTYPE=='2'}">
											<td class='tab_width1' width='80px' align='center'>??????</td>
										</c:when>
										<c:when test="${nextsItem.OPINIONTYPE=='9'}">
											<td class='tab_width1' width='80px' align='center'>??????</td>
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
		<!-- ?????????????????? -->

		<!-- ?????????????????????????????? -->
		<div id="specialId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>????????????????????????</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="specialIdInfoId">
				<tr id="specialIdInfoId_1">
					<td>
						<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
							<tr>
								<td class='tab_width1' width='80px' align='center'>??????</td>
								<td class='tab_width1' width='80px' align='center'>?????????????????????</td>
								<td class='tab_width1' width='80px' align='center'>?????????????????????</td>
								<td class='tab_width1' width='80px' align='center'>????????????</td>
								<td class='tab_width1' width='80px' align='center'>????????????</td>
								<td class='tab_width1' width='80px' align='center'>????????????</td>
								<td class='tab_width1' width='80px' align='center'>??????</td>
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
		<!-- ?????????????????????????????? -->

		<!-- ?????????????????? -->
		<div id="finishsId">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>??????????????????</span>
					</th>
				</tr>
			</table>
			<div class="tab_height"></div>
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">??????????????????</td>
						<td>
							<input type="text" class="tab_text" name="TRANSACTOR" value="${finishs.TRANSACTOR}" />
						</td>
						<td class="tab_width">???????????????</td>
						<td>
							<input type="text" class="tab_text" name="REASON" value="${finishs.REASON}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">???????????????</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="BLJG"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="??????????????????" name="TYPE"
								value="${finishs.TYPE}">
							</eve:eveselect>
						</td>
						<td class="tab_width">???????????????????????????</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="SFKDSD"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="????????????????????????"
								name="DELIVERYRESULT" value="${finishs.DELIVERYRESULT}">
							</eve:eveselect>
						</td>
					</tr>

					<tr>
						<td class="tab_width">???????????????</td>
						<td>
							<input type="text" class="tab_text" name="DECLARETIME" value="${finishs.DECLARETIME}" />
						</td>
					</tr>
				</table>
			</td>
		</div>
		<!-- ?????????????????? -->

		<!-- ?????????????????? -->
		<div id="attrsId" style="height: 300px;">
			<div class="tab_height"></div>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<span>??????????????????</span>
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
								<th data-options="field:'FILE_NAME',align:'left'" width="250" formatter="formatFileName">????????????</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<!-- ?????????????????? -->
	</form>
</body>
</html>
