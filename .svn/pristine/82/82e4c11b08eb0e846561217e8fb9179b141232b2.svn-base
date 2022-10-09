<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("LinkForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#LinkForm").serialize();
				var url = $("#LinkForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							//parent.$("#tshjGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		},null);
	});
	
	function selectOperator(){
	var userIds = $("input[name='OPERATOR_ID']").val();
	parent.$.dialog.open("sysUserController.do?selector&allowCount=3&noAuth=true&userIds="
		+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectUserInfo = art.dialog.data("selectUserInfo");
				if (selectUserInfo && selectUserInfo.userIds) {
					$("input[name='OPERATOR_ID']").val(selectUserInfo.userIds);
					$("input[name='OPERATOR_NAME']").val(selectUserInfo.userNames);
					art.dialog.removeData("selectUserInfo");
				}
			}
		}, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="LinkForm" method="post"
		action="departServiceItemController.do?saveOrUpdateLink">
		<div id="LinkFormDiv"
			data-options="region:'center',split:true,border:false">
			<%--==========隐藏域部分开始 ===========--%>
			<input type="hidden" name="RECORD_ID" value="${linkInfo.RECORD_ID}">
			<input type="hidden" name="ITEM_ID" value="${linkInfo.ITEM_ID}">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td colspan="2"><span
						style="width: 130px;float:left;text-align:right;">环节名称：</span> 
						
						<eve:eveselect clazz="eve-input validate[required]"
						dataParams="SpecialLinkName" value="${linkInfo.LINK_NAME}"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择特殊环节" name="LINK_NAME">
						</eve:eveselect> <font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">办理时限：</span>
						<input type="text" style="width:150px;float:left;" maxlength="3"
								class="eve-input validate[required,custom[integer],min[0]]"
								value="${linkInfo.LINK_LIMITTIME}" name="LINK_LIMITTIME" />工作日
<%--								(<fontclass="dddl_platform_html_requiredFlag">*</font>0代表无时限)--%>
						 <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 130px;float:left;text-align:right;">经办人：</span>
						<input type="text" style="width:150px;float:left;"
						value="${linkInfo.OPERATOR_NAME}" readonly="readonly"
						class="eve-input validate[required]" name="OPERATOR_NAME" />
						<input type="hidden" name="OPERATOR_ID" value="${linkInfo.OPERATOR_ID}" /> <font
								class="dddl_platform_html_requiredFlag">*</font>
						&nbsp;<input type="button" value="选择经办人" class="eve-button"
						style="vertical-align: middle" onclick="selectOperator()" /></td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">时限说明：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[],maxSize[64]]"
							style="width: 400px" name="LIMIT_DESC">${linkInfo.LIMIT_DESC}</textarea>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">环节依据：</span>
						<textarea rows="5" cols="5" class="eve-textarea validate[required,maxSize[1998]]"
							style="width: 400px" name="LINK_BASIS">${linkInfo.LINK_BASIS}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">备注：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="LINK_REMARK">${linkInfo.LINK_REMARK}</textarea>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">适用情形：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[required],maxSize[1998]]"
							style="width: 400px" name="SUIT_SITUATION">${linkInfo.SUIT_SITUATION}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">（法定）审查内容：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[required],maxSize[1998]]"
							style="width: 400px" name="REVIEW_CONTENT">${linkInfo.REVIEW_CONTENT}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">审查标准：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[required],maxSize[1998]]"
							style="width: 400px" name="REVIEW_STANDARD">${linkInfo.REVIEW_STANDARD}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">审查要点：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="KEYPOINTS">${linkInfo.KEYPOINTS}</textarea>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 130px;float:left;text-align:right;">办理人员：</span>
						<input type="text" style="width:150px;float:left;"
						value="${linkInfo.TRANSACTOR}" maxlength="32"
						class="eve-input " name="TRANSACTOR" />
						</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">办理意见（结果）：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[required],maxSize[1998]]"
							style="width: 400px" name="HANDLE_OPINIONS">${linkInfo.HANDLE_OPINIONS}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">办理责任：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[required],maxSize[1998]]"
							style="width: 400px" name="RESPONSIBILITY">${linkInfo.RESPONSIBILITY}</textarea><font
								class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">注意事项：</span>
						<textarea rows="3" cols="5" class="eve-textarea validate[[],maxSize[1998]]"
							style="width: 400px" name="ATTENTION_MATTER">${linkInfo.ATTENTION_MATTER}</textarea>
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
