<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">

	$(function () {
		//测绘公司下拉框初始化()
		$("#SURVEY_USERID").combobox({
			url: 'bdcSurveyController.do?getDrawOrgList',
			method: 'post',
			valueField: 'VALUE',
			textField: 'TEXT',
			panelHeight: '200',
			editable: false,
			required: true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onLoadSuccess: function (row) {

			},
			onSelect: function (row) {
				$("input[name='SURVEY_USERNAME']").val(row.TEXT);
			}
		});
		$("#SURVEY_USERID").combobox("setValue", '${bdcSurvey.SURVEY_USERID}');
	});

	$(function() {
		AppUtil.initWindowForm("BdcSurveyForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BdcSurveyForm").serialize();
				var url = $("#BdcSurveyForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:5,
							    ok: true
							});
							parent.$("#BdcSurveyGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
							$("input[type='submit']").attr("disabled", false);
						}
					}
				});
			}
		}, "T_BDC_SURVEY");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BdcSurveyForm" method="post"
		action="bdcSurveyController.do?saveOrUpdate">		
		<div id="BdcSurveyFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="ID" value="${bdcSurvey.YW_ID}">
		<input type="hidden" name="SURVEY_USERNAME" value="${bdcSurvey.SURVEY_USERNAME}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>坐落：</span>
					 <c:if test="${bdcSurvey.ID!=null}">
						 ${bdcSurvey.LOCATED}
					 </c:if> 
					 <c:if test="${bdcSurvey.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="128"
							class="eve-input validate[required]"
							value="${bdcSurvey.LOCATED}" id="LOCATED" name="LOCATED" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>测绘公司：</span>
					<input class="easyui-combobox"  style="width:250px;float:left;height:30px;margin-left:90px;"  name="SURVEY_USERID" id="SURVEY_USERID"
						   data-options="prompt:'请选择测绘公司'">
				</td>
			</tr>
<%--			<tr>--%>
<%--				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>申请人：</span>--%>
<%--					 <c:if test="${bdcSurvey.ID!=null}">--%>
<%--						 ${bdcSurvey.SQR}--%>
<%--					 </c:if> --%>
<%--					 <c:if test="${bdcSurvey.ID==null}">--%>
<%--						<input type="text" style="width:300px;float:left;" maxlength="128"--%>
<%--							class="eve-input validate[required]"--%>
<%--							value="${bdcSurvey.SQR}" id="SQR" name="SQR" />--%>
<%--					</c:if>--%>
<%--				</td>--%>
<%--			</tr>--%>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;">申报号：</span>
					 <c:if test="${bdcSurvey.ID!=null}">
						 ${bdcSurvey.EXE_ID}
					 </c:if> 
					 <c:if test="${bdcSurvey.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[]"
							value="${bdcSurvey.EXE_ID}" id="EXE_ID" name="EXE_ID" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td ><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>测绘状态：</span>
						<eve:eveselect clazz="eve-input validate[required]" dataParams="CHZT"
						dataInterface="dictionaryService.findDatasForSelect" value="${bdcSurvey.SURVEY_STATUS}"
						defaultEmptyText="===请选择===" defaultEmptyValue="1" name="SURVEY_STATUS"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font
					class="dddl_platform_html_requiredFlag">*</font>是否默认：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="YESNO"
						dataInterface="dictionaryService.findDatasForSelect" value="${bdcSurvey.IS_MR}"
						defaultEmptyText="===请选择===" defaultEmptyValue="1" name="IS_MR"></eve:eveselect></td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

