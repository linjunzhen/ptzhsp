<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("AuditerForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#AuditerForm").serialize();
				var url = $("#AuditerForm").attr("action");
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
							//parent.$("#defGrid").datagrid("reload");
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

	function selectUser() {
		var userIds = $("input[name='USER_ID']").val();
		parent.$.dialog.open("sysUserController.do?selector&allowCount=100&noAuth=true&userIds="
			+ userIds, {
				title : "人员选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectUserInfo = art.dialog.data("selectUserInfo");
					if (selectUserInfo && selectUserInfo.userIds) {
						$("input[name='USER_ID']").val(selectUserInfo.userIds);
						$("input[name='USER_NAME']").val(selectUserInfo.userNames);
						$("input[name='USER_ACCOUNT']").val(selectUserInfo.userAccounts);
					}
				}
			}, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="AuditerForm" method="post"
		action="departServiceItemController.do?saveOrUpdateAuditer">
		<div id="AuditerFormDiv"
			data-options="region:'center',split:true,border:false">
			<%--==========隐藏域部分开始 ===========--%>
			<input type="hidden" name="RECORD_ID"
				value="${auditerInfo.RECORD_ID}"> <input type="hidden"
				name="USER_ACCOUNT" value="${auditerInfo.USER_ACCOUNT}"> <input
				type="hidden" name="USER_ID" value="${auditerInfo.USER_ID}">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>

				<tr>
					<td colspan="2"><span
						style="width: 120px;float:left;text-align:right;">环节名称：</span> <input
						type="text" style="width:250px;float:left;"
						value="${auditerInfo.NODE_NAME}" class="eve-input "
						readonly="readonly" name="NODE_NAME" /></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">办理人员：</span>
						<input type="text" style="width:250px;float:left;" maxlength="1998"
						class="eve-input validate[required]" readonly="readonly"
						value="${auditerInfo.USER_NAME}" name="USER_NAME"
						onclick="selectUser()" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><font class="dddl_platform_html_requiredFlag" style="margin-left: 120px;">注：受理环节人员，应为实际服务窗口人员</font></td>
				</tr>
				
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">办理时限：</span>
						<input type="text" style="width:150px;float:left;" maxlength="3"
						class="eve-input validate[[],custom[integer],min[0]]" 
						value="${auditerInfo.TIME_LIMIT}" name="TIME_LIMIT" />
						<eve:radiogroup typecode="FDSXLX" width="150px" defaultvalue="gzr"
							value="${auditerInfo.TIME_TYPE}" fieldname="TIME_TYPE">
						</eve:radiogroup>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">适用情形：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="SUIT_SITUATION">${auditerInfo.SUIT_SITUATION}</textarea>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">(法定)审查内容：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="REVIEW_CONTENT">${auditerInfo.REVIEW_CONTENT}</textarea>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">审查标准：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[maxSize[1998]]"
							style="width: 400px" name="REVIEW_STANDARD">${auditerInfo.REVIEW_STANDARD}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">审查要点：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="KEYPOINTS">${auditerInfo.KEYPOINTS}</textarea>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td><span -->
<!-- 						style="width: 120px;float:left;text-align:right;">审查程序及审查人：</span> -->
<!-- 						<textarea rows="4" cols="5" class="eve-textarea validate[[required],maxSize[1998]]" -->
<!-- 							style="width: 400px" name="EXAMINANT">${auditerInfo.EXAMINANT}</textarea><font -->
<!-- 								class="dddl_platform_html_requiredFlag">*</font> -->
<!-- 						</td> -->
<!-- 				</tr> -->
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">办理意见（结果）：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[maxSize[1998]]"
							style="width: 400px" name="HANDLE_OPINIONS">${auditerInfo.HANDLE_OPINIONS}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">办理责任：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="RESPONSIBILITY">${auditerInfo.RESPONSIBILITY}</textarea>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">注意事项：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="ATTENTION_MATTER">${auditerInfo.ATTENTION_MATTER}</textarea>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">备注：</span>
						<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="REMARK">${auditerInfo.REMARK}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>
