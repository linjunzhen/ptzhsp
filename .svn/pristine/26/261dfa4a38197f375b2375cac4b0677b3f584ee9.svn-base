<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine"></eve:resources>

<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("draftInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#draftInfoForm").serialize();
				var url = $("#draftInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, null);

	});
	
	function submit(){
		$("#draftInfoForm").submit();
	}

	function selectItem() {
		var itemCodes = $("input[name='ITEM_CODE']").val();
		$.dialog.open("departServiceItemController.do?selector&allowCount=5&itemCodes="
			+ itemCodes, {
				title : "事项选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						$("textarea[name='ITEM_NAME']").val(selectItemInfo.itemNames);
						$("input[name='ITEM_CODE']").val(selectItemInfo.itemCodes);
						art.dialog.removeData("selectItemInfo");
					}
				}
			}, false);
	}

	function clearItem() {
		$("textarea[name='ITEM_NAME']").val("");
		$("input[name='ITEM_CODE']").val("");
	}

	function selectImpl() {
		var departId = $("input[name='IMPL_DEPART_ID']").val();
		parent.$.dialog.open("departmentController/parentSelector.do?departIds=" + departId + "&allowCount=1&checkCascadeY=&"
			+ "checkCascadeN=", {
				title : "实施部门选择器",
				width : "600px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if (selectDepInfo) {
						$("input[name='IMPL_DEPART_ID']").val(selectDepInfo.departIds);
						$("input[name='IMPL_DEPART_NAME']").val(selectDepInfo.departNames);

						art.dialog.removeData("selectDepInfo");
					}
				}
			}, false);
	}

	function selectTogetherImpl() {
		var departId = $("input[name='TOGETHER_DEPART_ID']").val();
		parent.$.dialog.open("departmentController/parentSelector.do?departIds=" + departId + "&allowCount=5&checkCascadeY=&"
			+ "checkCascadeN=", {
				title : "共同实施部门选择器",
				width : "600px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if (selectDepInfo) {
						$("input[name='TOGETHER_DEPART_ID']").val(selectDepInfo.departIds);
						$("input[name='TOGETHER_DEPART_NAME']").val(selectDepInfo.departNames);

						art.dialog.removeData("selectDepInfo");
					}
				}
			}, false);
	}


	function jumpToTargetDiv(targetDiv) {
		var currentStep = $("input[name='CurrentStep']").val();
		if (currentStep == targetDiv) {
		} else {
			$("#flow_node_" + currentStep).attr("class", "flow_nodone");
			$("#flow_node_" + targetDiv).attr("class", "flow_done");
			$("#StepDiv_" + currentStep).attr("style", "width:100%;height:100%;display: none;");
			$("#StepDiv_" + targetDiv).attr("style", "width:100%;height:100%;");
			$("input[name='CurrentStep']").val(targetDiv);
		}
	}

	function setHis(version) {
		var entityId = $("input[name='SERVICE_ID']").val();
		$.post("agencyServiceController.do?hisVersionInfo", {
			entityId : entityId,
			version : version
		}, function(responseText, status, xhr) {
			resultJson = $.parseJSON(responseText);
			if (resultJson.success) {
				var info = resultJson.jsonString;
				var obj = JSON.parse(info);
				$("#column1").html(obj.ITEM_NAME);
				$("#column2").html(obj.AGENCY_ITEM_NAME);
				$("#column3").html(obj.agencyTypeName);
				$("#column4").html(obj.CHARGE_STANDARD_BASIS);
				$("#column5").html(obj.SET_BASIS);
				$("#column6").html(obj.TIME_LIMIT_BASIS);
				$("#column7").html(obj.IMPL_DEPART_NAME);
				$("#column8").html(obj.TOGETHER_DEPART_NAME);
				$("#column9").html(obj.AGENCY_REMARK);

			}
		});
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div id="draftInfoTabFormDiv" class="easyui-layout" fit="true">
		<input type="hidden" name="CurrentStep" value="1">
		<div data-options="region:'north',split:true,border:false"
			style="height: 100px;">
			<div class="flow_container">
				<ul>
					<li class="flow_active" id="flow_node_1"><div class="pro_base">1</div>
						<span onclick="jumpToTargetDiv(1);">基本信息</span></li>
					<li class="flow_nodone" id="flow_node_2"><div class="pro_base">2</div>
						<span onclick="jumpToTargetDiv(2);">流转日志</span></li>
					<li class="flow_nodone" id="flow_node_3"><div class="pro_base">3</div>
						<span onclick="jumpToTargetDiv(3);">版本对比</span></li>
				</ul>
				<div style="position:relative; top:9px; margin:0 15% 0 52px;">
					<div class="flow_progress_bar">
						<span class="flow_progress_highlight" id="progress_bar"
							style="width:0%;"></span>
					</div>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div title="基本信息" id="StepDiv_1" style="width:100%;height:100%;">
				<div class="easyui-layout" fit="true">

					<div data-options="region:'center'">
						<form id="draftInfoForm" method="post"
							action="agencyServiceController.do?saveOrUpdate">
							<div id="draftInfoFormDiv"
								data-options="region:'center',split:true,border:false">
								<%--==========隐藏域部分开始 ===========--%>
								<input type="hidden" name="SERVICE_ID"
									value="${draftInfo.SERVICE_ID}"> <input type="hidden"
									name="IMPL_DEPART_ID" value="${draftInfo.IMPL_DEPART_ID}">
								<input type="hidden" name="TOGETHER_DEPART_ID"
									value="${draftInfo.TOGETHER_DEPART_ID}">
								<%--==========隐藏域部分结束 ===========--%>
								<table style="width:100%;border-collapse:collapse;"
									class="dddl-contentBorder dddl_table">
									<tr style="height:29px;">
										<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>审批事项名称：</span> <textarea
												class="eve-textarea validate[required,maxSize[512]]"
												readonly="readonly" style="width: 600px;height:100px;"
												name="ITEM_NAME">${draftInfo.ITEM_NAME}</textarea> <input
											type="hidden" name="ITEM_CODE" value="${draftInfo.ITEM_CODE}" />
											&nbsp;<input type="button" value="选择事项" class="eve-button"
											style="vertical-align: middle" onclick="selectItem()" />
											&nbsp;<input type="button" value="清除事项" class="eve-button"
											style="vertical-align: middle" onclick="clearItem()" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>中介服务事项名称：</span> <input
											type="text" style="width:600px;float:left;"
											value="${draftInfo.AGENCY_ITEM_NAME}" maxlength="126"
											class="eve-input validate[required]" name="AGENCY_ITEM_NAME" />

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"> 编码：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.AGENCY_ITEM_CODE}" maxlength="30"
											class="eve-input " name="AGENCY_ITEM_CODE" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;">中介服务机构类型：</span>
											<eve:radiogroup typecode="agencyType" width="130px"
												value="${draftInfo.AGENCY_ORG_TYPE}"
												fieldname="AGENCY_ORG_TYPE">
											</eve:radiogroup></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>中介收费标准和依据：</span> <textarea
												class="eve-textarea validate[required,maxSize[512]]"
												style="width: 600px;height:150px;"
												name="CHARGE_STANDARD_BASIS">${draftInfo.CHARGE_STANDARD_BASIS}</textarea>

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>设定中介服务依据：</span> <textarea
												class="eve-textarea validate[required,maxSize[512]]"
												style="width: 600px;height:150px;" name="SET_BASIS">${draftInfo.SET_BASIS}</textarea>

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>中介服务服务时限及依据：</span>
											<textarea
												class="eve-textarea validate[required,maxSize[512]]"
												style="width: 600px;height:150px;" name="TIME_LIMIT_BASIS">${draftInfo.TIME_LIMIT_BASIS}</textarea>

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;">中介机构名录（或链接）：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.DIRECTORY}" maxlength="126"
											class="eve-input" name="DIRECTORY" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>实施部门：</span> <input
											type="text" style="width:600px;float:left;"
											onclick="selectImpl()" value="${draftInfo.IMPL_DEPART_NAME}"
											readonly="readonly" class="eve-input validate[required]"
											name="IMPL_DEPART_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;">共同实施部门：</span>
											<input type="text" style="width:600px;float:left;"
											onclick="selectTogetherImpl()"
											value="${draftInfo.TOGETHER_DEPART_NAME}" readonly="readonly"
											class="eve-input" name="TOGETHER_DEPART_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 150px;float:left;text-align:right;">备注：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.AGENCY_REMARK}" maxlength="126"
											class="eve-input" name="AGENCY_REMARK" /></td>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div title="流转日志" id="StepDiv_2"
				style="width:100%;height:100%;display: none;">
				<table style="width:100%;border-collapse:collapse;"
					class="dddl-contentBorder dddl_table">
					<c:if test="${fn:length(logList)<=0}">
						<tr style="height:29px;">
							<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>暂无日志记录</a></div></td>
						</tr>
					</c:if>
					<c:if test="${logList!=null&&fn:length(logList)>0}">
						<c:forEach items="${logList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>

								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>

								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre
										style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend"
									style="font-weight:bold;background:#FFFFF0 ;"></td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
			<div title="版本对比" id="StepDiv_3"
				style="width:100%;height:100%;display: none;">
				<table style="width:100%;border-collapse:collapse;"
					class="dddl-contentBorder dddl_table">
					<c:if test="${isHaving==0}">
						<tr style="height:29px;">
							<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>暂无历史版本记录</a></div></td>
						</tr>
					</c:if>
					<c:if test="${isHaving==1}">
						<tr style="height:29px;">
							<td colspan="3"><span
								style="width: 10%;float:left;text-align:right;font-weight: bold;">版本选择：</span>
								<input class="easyui-combobox" style="width:182px; height:26px;"
								data-options="			                
						                url:'agencyServiceController/loadData.do?entityId=${draftInfo.SERVICE_ID}',
						                method:'post',
						                valueField:'DIC_CODE',
						                textField:'DIC_NAME',
						                panelHeight:'auto',
						                required:true,
						                editable:false,
						                onSelect:function(rec){	
										   setHis(rec.DIC_CODE);
										}
				                " />
							</td>
						</tr>
						<tr>
							<td></td>
							<td style="font-weight: bold;text-align: center;">当前版本</td>
							<td style="font-weight: bold;text-align: center;">历史版本</td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">审批事项名称：</td>
							<td style="width: 45%;">${draftInfo.ITEM_NAME}</td>
							<td style="width: 45%;" id="column1"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">中介服务事项名称：</td>
							<td style="width: 45%;">${draftInfo.AGENCY_ITEM_NAME}</td>
							<td style="width: 45%;" id="column2"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">中介服务机构类型：</td>
							<td style="width: 45%;">${draftInfo.agencyTypeName}</td>
							<td style="width: 45%;" id="column3"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">中介收费标准和依据：</td>
							<td style="width: 45%;">${draftInfo.CHARGE_STANDARD_BASIS}</td>
							<td style="width: 45%;" id="column4"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">设定中介服务依据：</td>
							<td style="width: 45%;">${draftInfo.SET_BASIS}</td>
							<td style="width: 45%;" id="column5"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">中介服务服务时限及依据：</td>
							<td style="width: 45%;">${draftInfo.TIME_LIMIT_BASIS}</td>
							<td style="width: 45%;" id="column6"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">实施部门：</td>
							<td style="width: 45%;">${draftInfo.IMPL_DEPART_NAME}</td>
							<td style="width: 45%;" id="column7"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">共同实施部门：</td>
							<td style="width: 45%;">${draftInfo.TOGETHER_DEPART_NAME}</td>
							<td style="width: 45%;" id="column8"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">备注：</td>
							<td style="width: 45%;">${draftInfo.AGENCY_REMARK}</td>
							<td style="width: 45%;" id="column9"></td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons">
				<input value="确定" type="submit" onclick="submit()"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
</body>
