<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine,laydate"></eve:resources>

<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("draftInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#draftInfoForm").serialize();
				var url = $("#draftInfoForm").attr("action");
				art.dialog.confirm("确定变更将进入审核库待审，您确定需要进行修改? ", function() {
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
				});
			}
		}, null);
		if ('${operType}' == 'audit') {
			$('#draftInfoForm').find('input,textarea').attr("readonly", "readonly");
			$('#draftInfoForm').find(":radio,:checkbox").attr('disabled', "disabled");
		}

		$("input[name='IS_PRE_ENTER']").click(function() {
			var value = $(this).val();
			if (value == "1") {
				$("#enter").attr("style", "");
				$("#noenter").attr("style", "display:none;");
				$("#noenter").find('input,textarea').not(":button").prop("value", "");
			} else {
				$("#noenter").attr("style", "");
				$("#enter").attr("style", "display:none;");
				$("#enter").find('input,textarea').not(":button").prop("value", "");
			}
		});
		if ('${draftInfo.IS_PRE_ENTER}' == '0') {
			$("#noenter").attr("style", "");
			$("#enter").attr("style", "display:none;");
		}
	});

	function submit() {
		$("#draftInfoForm").submit();
	}

	function qllyCheck(thiz) {
		if (thiz.value == 'qt') {
			$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", false);
		} else {
			$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", true);
		}
	}

	function selectItem() {
		$.dialog.open("serviceItemController/selector.do", {
			title : "事项选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectItemInfo = art.dialog.data("selectItemInfo");
				if (selectItemInfo) {
					$("input[name='ITEM_NAME']").val(selectItemInfo.itemNames);
					$("input[name='ITEM_CODE']").val(selectItemInfo.itemCodes);
					art.dialog.removeData("selectItemInfo");
				}
			}
		}, false);
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

	function selectPreItem() {
		var itemCodes = $("input[name='PRE_ITEMCODES']").val();
		$.dialog.open("departServiceItemController.do?selector&allowCount=5&itemCodes="
			+ itemCodes, {
				title : "事项选择器",
				width : "1200px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						$("textarea[name='PRE_ITEMNAMES']").val(selectItemInfo.concatNames);
						$("input[name='PRE_ITEMCODES']").val(selectItemInfo.itemCodes);
						art.dialog.removeData("selectItemInfo");
					}
				}
			}, false);
	}


	function audit() {
		var preIds = $("input[name='PRE_ID']").val();
		var status = $("input[name='STATUS']").val();
		$.dialog.open("preApprovalController.do?opinionInfo&status=" + status + "&preIds=" + preIds, {
			title : "审核意见",
			width : "600px",
			height : "340px",
			fixed : true,
			lock : true,
			resize : false,
			close : function() {
				var backinfo = art.dialog.data("backinfo");
				if (backinfo && backinfo.back) {
					parent.art.dialog({
						content : "提交成功",
						icon : "succeed",
						time : 3,
						ok : true
					});
					art.dialog.removeData("backinfo");
					AppUtil.closeLayer();
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
		var entityId = $("input[name='PRE_ID']").val();
		$.post("preApprovalController.do?hisVersionInfo", {
			entityId : entityId,
			version : version
		}, function(responseText, status, xhr) {
			resultJson = $.parseJSON(responseText);
			if (resultJson.success) {
				var info = resultJson.jsonString;
				var obj = JSON.parse(info);
				$("#column1").html(obj.ITEM_NAME);
				$("#column2").html(obj.IMPL_DEPART_NAME);
				$("#column3").html(obj.TOGETHER_DEPART_NAME);
				if(obj.IS_PRE_ENTER=='0'){
					$("#column4").html(obj.NOTENTER_ITEMNAME);
				}else{
					$("#column4").html(obj.PRE_ITEMNAMES);
				}
				$("#column5").html(obj.PRE_BASIS);
				$("#column6").html(obj.PRE_REMARK);

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
		<div data-options="region:'center'" >
			<div title="基本信息" id="StepDiv_1" style="width:100%;height:100%;">
				<div class="easyui-layout" fit="true">

					<div data-options="region:'center'">
						<form id="draftInfoForm" method="post"
							action="preApprovalController.do?modify">
							<div id="draftInfoFormDiv">
								<%--==========隐藏域部分开始 ===========--%>
								<input type="hidden" name="PRE_ID" value="${draftInfo.PRE_ID}">
								<input type="hidden" name="STATUS" value="${draftInfo.STATUS}">
								<input type="hidden" name="IMPL_DEPART_ID"
									value="${draftInfo.IMPL_DEPART_ID}"> <input
									type="hidden" name="TOGETHER_DEPART_ID"
									value="${draftInfo.TOGETHER_DEPART_ID}">
								<%--==========隐藏域部分结束 ===========--%>
								<table style="width:100%;border-collapse:collapse;"
									class="dddl-contentBorder dddl_table">
									<tr>
										<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>变更类别：</span> <eve:radiogroup
												typecode="modifyType" width="130px"
												value="${draftInfo.MODIFY_TYPE}" fieldname="MODIFY_TYPE"
												defaultvalue="03">
											</eve:radiogroup></td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>变更说明：</span> <input
											type="text" style="width:600px;float:left;"
											value="${draftInfo.MODIFY_DESC}" maxlength="62"
											class="eve-input validate[required]" name="MODIFY_DESC" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>审批事项名称：</span> <input
											type="text" style="width:600px;float:left;"
											value="${draftInfo.ITEM_NAME}" maxlength="126"
											readonly="readonly" class="eve-input validate[required]"
											name="ITEM_NAME" /> <input type="hidden" name="ITEM_CODE"
											value="${draftInfo.ITEM_CODE}" /> &nbsp;
											<c:if test="${operType=='edit' }">
											<input type="button"
											value="选择事项" class="eve-button"
											style="vertical-align: middle" onclick="selectItem()" />
											</c:if>
										</td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>前置事项是否已入驻：</span> <eve:radiogroup
												typecode="YesOrNo" width="130px"
												value="${draftInfo.IS_PRE_ENTER}" fieldname="IS_PRE_ENTER"
												defaultvalue="1">
											</eve:radiogroup></td>
									</tr>
									<tbody id="enter">
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>前置审批事项部门及名称：</span>
												<textarea
													class="eve-textarea validate[required,maxSize[512]]"
													style="width: 600px;height:150px;" name="PRE_ITEMNAMES">${draftInfo.PRE_ITEMNAMES}</textarea>
												<input type="hidden" name="PRE_ITEMCODES"
												value="${draftInfo.PRE_ITEMCODES}" /> &nbsp;
												<c:if test="${operType=='edit' }">
												<input
												type="button" value="选择前置事项" class="eve-button"
												style="vertical-align: middle" onclick="selectPreItem()" />
												</c:if>
											</td>
										</tr>
									</tbody>
									<%-- <tr>
					<td><span style="width: 150px;float:left;text-align:right;">（手动填写）前置审批事项部门及名称：</span>
						<textarea class="eve-textarea validate[[],maxSize[512]]" placeholder="若前置事项与上一栏所选事项不一致，请自行联系局办或处室负责人通过管委会制作的《通讯录》，最终获取对方准确的前置事项名称。
若经对方核实确实未入驻审批平台，请在此处填写您的大名和联系电话，以及已联系过的对方联系人、联系电话及具体联系时间，以便我们汇总上报主管单位处理。"
							style="width: 600px;height:150px;" name="PRE_ITEMNAMES_WRITE">${draftInfo.PRE_ITEMNAMES_WRITE}</textarea>
						
					</td>
				</tr> --%>
									<tbody id="noenter" style="display: none;">
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>未入驻前置审批事项部门：</span>
												<input type="text" style="width:600px;float:left;"
												value="${draftInfo.NOTENTER_DEPART}" maxlength="32"
												class="eve-input validate[required]" name="NOTENTER_DEPART" />

											</td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>未入驻前置审批事项名称：</span>
												<input type="text" style="width:600px;float:left;"
												value="${draftInfo.NOTENTER_ITEMNAME}" maxlength="64"
												class="eve-input validate[required]"
												name="NOTENTER_ITEMNAME" /></td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>未入驻前置审批事项部门联系人：</span>
												<input type="text" style="width:200px;float:left;"
												value="${draftInfo.NOTENTER_CONTACTS}" maxlength="16"
												class="eve-input validate[required]"
												name="NOTENTER_CONTACTS" /></td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>未入驻前置审批事项部门联系电话：</span>
												<input type="text" style="width:200px;float:left;"
												value="${draftInfo.NOTENTER_PHONE}" maxlength="16"
												class="eve-input validate[required,custom[fixOrMobilePhone]]"
												name="NOTENTER_PHONE" /></td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>未入驻前置审批事项部门联系时间：</span>
												<input type="text"
												style="width:180px;height:26px;margin-left: 4px;"
												value="${draftInfo.NOTENTER_CONTACTTIME}"
												name="NOTENTER_CONTACTTIME"
												class="laydate-icon validate[required]" readonly="readonly"
												onclick="laydate({istime: false, format: 'YYYY-MM-DD'})" />
											</td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>填报人姓名：</span> <input
												type="text" style="width:200px;float:left;"
												value="${draftInfo.INFORMANT_NAME}" maxlength="16"
												class="eve-input validate[required]" name="INFORMANT_NAME" />

											</td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;"><font
													class="dddl_platform_html_requiredFlag">*</font>填报人电话：</span> <input
												type="text" style="width:200px;float:left;"
												value="${draftInfo.INFORMANT_PHONE}" maxlength="16"
												class="eve-input validate[required,custom[fixOrMobilePhone]]"
												name="INFORMANT_PHONE" /></td>
										</tr>
										<tr>
											<td><span
												style="width: 220px;float:left;text-align:right;">未入驻前置审批事项备注：</span>
												<input type="text" style="width:600px;float:left;"
												value="${draftInfo.NOTENTER_REMARK}" maxlength="126"
												class="eve-input" name="NOTENTER_REMARK" /></td>
										</tr>
									</tbody>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>前置审批实施部门：</span> <input
											type="text" style="width:600px;float:left;"
											onclick="selectImpl()" value="${draftInfo.IMPL_DEPART_NAME}"
											readonly="readonly" class="eve-input validate[required]"
											name="IMPL_DEPART_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;">前置审批共同实施部门：</span>
											<input type="text" style="width:600px;float:left;"
											onclick="selectTogetherImpl()"
											value="${draftInfo.TOGETHER_DEPART_NAME}" readonly="readonly"
											class="eve-input" name="TOGETHER_DEPART_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>设定前置审批的法定依据：</span>
											<textarea
												class="eve-textarea validate[required,maxSize[512]]"
												style="width: 600px;height:180px;" name="PRE_BASIS">${draftInfo.PRE_BASIS}</textarea>

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 220px;float:left;text-align:right;">备注：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.PRE_REMARK}" maxlength="126"
											class="eve-input" name="PRE_REMARK" /></td>
									</tr>
									<tr/>
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
						                url:'preApprovalController/loadData.do?entityId=${draftInfo.PRE_ID }',
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
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">项目名称：</td>
							<td style="width: 45%;">${draftInfo.ITEM_NAME}</td>
							<td style="width: 45%;" id="column1"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">实施部门：</td>
							<td style="width: 45%;">${draftInfo.IMPL_DEPART_NAME}</td>
							<td style="width: 45%;" id="column2"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">共同实施部门：</td>
							<td style="width: 45%;">${draftInfo.TOGETHER_DEPART_NAME}</td>
							<td style="width: 45%;" id="column3"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">前置审批事项部门及名称：</td>
							<td style="width: 45%;">${draftInfo.PRE_ITEMNAMES}</td>
							<td style="width: 45%;" id="column4"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">设定前置审批法定依据：</td>
							<td style="width: 45%;">${draftInfo.PRE_BASIS}</td>
							<td style="width: 45%;" id="column5"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">备注：</td>
							<td style="width: 45%;">${draftInfo.PRE_REMARK}</td>
							<td style="width: 45%;" id="column6"></td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		<div data-options="region:'south',split:true,border:false">

			<div class="eve_buttons">

				<c:if test="${operType=='edit' }">
					<input value="确定" type="button" onclick="submit()"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
					<input value="取消" type="button"
						class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</c:if>
				<c:if test="${operType=='audit' }">
					<input value="审核意见" type="button" onclick="audit()"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
					<input value="关闭" type="button"
						class="z-dlg-button z-dialog-cancelbutton"
						onclick="AppUtil.closeLayer();" />
				</c:if>
			</div>
		</div>
	</div>
</body>
