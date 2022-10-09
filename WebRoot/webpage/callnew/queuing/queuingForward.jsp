<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	var userSet = false;
	$(function() {
		AppUtil.initWindowForm("ForwardForm", function(form, valid) {
			if (valid&&userSet) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ForwardForm").serialize();
				var url = $("#ForwardForm").attr("action");
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
							parent.$("#callingNewGrid").datagrid("reload");
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
	
    function setUser(winNo){    
    	var curwin = parent.$("input[name='winNo']").val();
    	if(winNo==curwin){    	
			parent.art.dialog({
				content: "此窗口您本人正在使用，请重新选择",
				icon:"error",
				time:3,
				ok: true
			});
			return;
    	}
    	AppUtil.ajaxProgress({
			url : "callSetController.do?userInfoJson",
			params : {winNo:winNo},
			callback : function(resultJson) {
				if(resultJson.success){
					var userInfo = eval("("+resultJson.jsonString+")");
					var userIds = userInfo.OPER_USERIDS.split(",");
					var userNames = userInfo.OPER_USERNAMES.split(",");
					var html = "<td><span style=\"width: 100px;float:left;text-align:right;\">需要转发人员：</span>";
					if(userIds.length<=0){
						parent.art.dialog({
							content: "您选择的窗口当前暂无操作人员，请重新选择",
							icon:"error",
							time:3,
							ok: true
						});
					}
					for(var i=0;i<userIds.length;i++){
						if('${sessionScope.curLoginUser.userId}'!=userIds[i]){
							html += "<input type=\"radio\" name=\"TO_USER\" value=\""+userIds[i]+"\" checked=\"checked\"/> "+userNames[i];
						}
						userSet = true;
					}
					html += "<font class=\"dddl_platform_html_requiredFlag\">*</font></td>";
					$("#users").html(html);
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						time:3,
						ok: true
					});
				}
			}
		});
    }
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ForwardForm" method="post" action="newCallController.do?queuingForward">
	    <div id="ForwardFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${recordId}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend"><div class="eui-dddltit"><a>转发信息</a></div></td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">需要转发窗口：</span>
						<eve:eveselect clazz="tab_text validate[required]"
							dataInterface="newCallService.findWinsForSelect" dataParams="${businessCode }"
							defaultEmptyText="选择窗口" name="TO_WIN" value=""
							onchange="setUser(this.value);">
						</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr id="users">
					<td><span
						style="width: 100px;float:left;text-align:right;">需要转发人员：</span>
						
					</td>
				</tr>

			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
