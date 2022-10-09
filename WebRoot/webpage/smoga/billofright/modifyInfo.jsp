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
				art.dialog.confirm("确定变更将进入审核库待审，您确定需要进行修改? ", function() {
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
								parent.art.dialog({
									content : "变更保存成功",
									icon : "succeed",
									time : 3,
									ok : true
								});
							if (resultJson.success) {
								AppUtil.closeLayer();
							} else {
								art.dialog({
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
		
		if('${operType}'=='audit'){
			$('#draftInfoForm').find('input,textarea').attr("readonly", "readonly");
			$('#draftInfoForm').find(":radio,:checkbox").attr('disabled',"disabled");
		}
	});
	
	function submit(){
		$("#draftInfoForm").submit();
	}
	
	function qllyCheck(thiz){
		if(thiz.value=='qt'){
			$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled",false);
		}else{
			$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled",true);
			$("input[name='RIGHT_SOURCE_OTHER']").val("");
		}
	}
	
	function selectImpl(){
		var departId = $("input[name='IMPL_DEPART_ID']").val();	
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "实施部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='IMPL_DEPART_ID']").val(selectDepInfo.departIds);
                    $("input[name='IMPL_DEPART_NAME']").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function selectTogetherImpl(){
		var departId = $("input[name='TOGETHER_DEPART_ID']").val();	
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=5&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "共同实施部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='TOGETHER_DEPART_ID']").val(selectDepInfo.departIds);
                    $("input[name='TOGETHER_DEPART_NAME']").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	function audit() {
		var rightIds = $("input[name='RIGHT_ID']").val();
		var status = $("input[name='STATUS']").val();
		$.dialog.open("billOfRightController.do?opinionInfo&status="+status+"&rightIds=" + rightIds, {
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
	
	
	function jumpToTargetDiv(targetDiv){
		var currentStep = $("input[name='CurrentStep']").val();
		if(currentStep==targetDiv){
   		}else{
   			$("#flow_node_"+currentStep).attr("class","flow_nodone");
    		$("#flow_node_"+targetDiv).attr("class","flow_done");
    		$("#StepDiv_"+currentStep).attr("style","width:100%;height:100%;display: none;");
    		$("#StepDiv_"+targetDiv).attr("style","width:100%;height:100%;");
    		$("input[name='CurrentStep']").val(targetDiv);
   		}
	}
	
	function setHis(version){
		var entityId = $("input[name='RIGHT_ID']").val();
		$.post("billOfRightController.do?hisVersionInfo",{
			entityId:entityId,
			version:version
		}, function(responseText, status, xhr) {
			resultJson = $.parseJSON(responseText);
			if(resultJson.success){
				var info = resultJson.jsonString;
				var obj = JSON.parse(info);
				$("#column1").html(obj.RIGHT_NAME);
				$("#column2").html(obj.SUBITEM_NAME);
				$("#column3").html(obj.modifyTypeName);
				$("#column4").html(obj.MODIFY_DESC);
				$("#column5").html(obj.rightTypeName);
				$("#column6").html(obj.levelName);
				$("#column7").html(obj.IMPL_BASIS);
				$("#column8").html(obj.IMPL_DEPART_NAME);
				$("#column9").html(obj.TOGETHER_DEPART_NAME);
				$("#column10").html(obj.IMPL_OBJECT);
				$("#column11").html(obj.REMARK);
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
						<form id="draftInfoForm" method="post" action="billOfRightController.do?modify">
							<div id="draftInfoFormDiv">
								<%--==========隐藏域部分开始 ===========--%>
								<input type="hidden" name="RIGHT_ID" value="${draftInfo.RIGHT_ID}">
								<input type="hidden" name="STATUS" value="${draftInfo.STATUS}">
								<input type="hidden" name="IMPL_DEPART_ID" value="${draftInfo.IMPL_DEPART_ID}">
								<input type="hidden" name="TOGETHER_DEPART_ID" value="${draftInfo.TOGETHER_DEPART_ID}">
								<%--==========隐藏域部分结束 ===========--%>
								<table style="width:100%;border-collapse:collapse;"
									class="dddl-contentBorder dddl_table">
									<tr style="height:29px;">
										<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>变更类别：</span> <eve:radiogroup
												typecode="modifyType" width="130px"
												value="${draftInfo.MODIFY_TYPE}" fieldname="MODIFY_TYPE"
												defaultvalue="03">
											</eve:radiogroup></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>变更说明：</span> <input
											type="text" style="width:600px;float:left;"
											value="${draftInfo.MODIFY_DESC}" maxlength="62"
											class="eve-input validate[required]" name="MODIFY_DESC" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>权利来源：</span> <eve:radiogroup
												onclick="qllyCheck(this)" typecode="qlly" width="130px"
												value="${draftInfo.RIGHT_SOURCE}" fieldname="RIGHT_SOURCE"
												defaultvalue="01">
											</eve:radiogroup> <input type="text" style="width:235px;float:left;"
											disabled="disabled" value="${draftInfo.RIGHT_SOURCE_OTHER}"
											maxlength="16" class="eve-input validate[required]"
											name="RIGHT_SOURCE_OTHER" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>权责清单目录：</span> <input
											type="text" style="width:600px;float:left;"
											value="${draftInfo.RIGHT_NAME}" maxlength="126"
											class="eve-input validate[required]" name="RIGHT_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">权责清单子目录：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.SUBITEM_NAME}" maxlength="126"
											class="eve-input" name="SUBITEM_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">权责类别：</span>
											<eve:radiogroup typecode="qzlb" width="900"
												value="${draftInfo.RIGHT_TYPE}" fieldname="RIGHT_TYPE">
											</eve:radiogroup></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">行使层级：</span>
											<eve:radiogroup typecode="xzcj" width="130px"
												value="${draftInfo.EXERCISE_LEVEL}"
												fieldname="EXERCISE_LEVEL">
											</eve:radiogroup></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>实施依据：</span> <textarea
												class="eve-textarea validate[required]"
												style="width: 600px;height:180px;" name="IMPL_BASIS">${draftInfo.IMPL_BASIS}</textarea>

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;"><font
												class="dddl_platform_html_requiredFlag">*</font>实施部门：</span> <input
											type="text" style="width:600px;float:left;"
											onclick="selectImpl()" value="${draftInfo.IMPL_DEPART_NAME}"
											readonly="readonly" class="eve-input validate[required]"
											name="IMPL_DEPART_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">共同实施部门：</span>
											<input type="text" style="width:600px;float:left;"
											onclick="selectTogetherImpl()"
											value="${draftInfo.TOGETHER_DEPART_NAME}" readonly="readonly"
											class="eve-input" name="TOGETHER_DEPART_NAME" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">追责情形：</span>
											<textarea class="eve-textarea validate[[],maxSize[1512]]"
												style="width: 600px;height:100px;"
												name="IMPUTATION_SITUATION">${draftInfo.IMPUTATION_SITUATION}</textarea>

										</td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">实施对象：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.IMPL_OBJECT}" maxlength="30"
											class="eve-input" name="IMPL_OBJECT" /></td>
									</tr>
									<tr>
										<td><span
											style="width: 120px;float:left;text-align:right;">备注：</span>
											<input type="text" style="width:600px;float:left;"
											value="${draftInfo.REMARK}" maxlength="126" class="eve-input"
											name="REMARK" /></td>
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
							<td colspan="2"class="dddl-legend"><div class="eui-dddltit"><a>暂无历史版本记录</a></div></td>
						</tr>
					</c:if>
					<c:if test="${isHaving==1}">
						<tr style="height:29px;">
							<td colspan="3"><span
								style="width: 10%;float:left;text-align:right;font-weight: bold;">版本选择：</span>
								<input class="easyui-combobox" style="width:182px; height:26px;"
								data-options="			                
						                url:'billOfRightController/loadData.do?entityId=${draftInfo.RIGHT_ID }',
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
							<td style="width: 45%;">${draftInfo.RIGHT_NAME}</td>
							<td style="width: 45%;" id="column1"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">子项名称：</td>
							<td style="width: 45%;">${draftInfo.SUBITEM_NAME}</td>
							<td style="width: 45%;" id="column2"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">变更类别：</td>
							<td style="width: 45%;">${draftInfo.modifyTypeName}</td>
							<td style="width: 45%;" id="column3"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">变更说明：</td>
							<td style="width: 45%;">${draftInfo.MODIFY_DESC}</td>
							<td style="width: 45%;" id="column4"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">权责类别：</td>
							<td style="width: 45%;">${draftInfo.rightTypeName}</td>
							<td style="width: 45%;" id="column5"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">行使层级：</td>
							<td style="width: 45%;">${draftInfo.levelName}</td>
							<td style="width: 45%;" id="column6"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">实施依据：</td>
							<td style="width: 45%;">${draftInfo.IMPL_BASIS}</td>
							<td style="width: 45%;" id="column7"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">实施部门：</td>
							<td style="width: 45%;">${draftInfo.IMPL_DEPART_NAME}</td>
							<td style="width: 45%;" id="column8"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">共同实施部门：</td>
							<td style="width: 45%;">${draftInfo.TOGETHER_DEPART_NAME}</td>
							<td style="width: 45%;" id="column9"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">实施对象：</td>
							<td style="width: 45%;">${draftInfo.IMPL_OBJECT}</td>
							<td style="width: 45%;" id="column10"></td>
						</tr>
						<tr>
							<td
								style="width: 10%;text-align: right;font-size: 14px;font-weight: bold;">备注：</td>
							<td style="width: 45%;">${draftInfo.REMARK}</td>
							<td style="width: 45%;" id="column11"></td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		<div data-options="region:'south'" style="height:46px;">

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
