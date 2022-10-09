<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
	SysUser sysUser = AppUtil.getLoginUser();
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取登录用户部门编码
	String depCode =  sysUser.getDepCode();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否业务咨询受理员
    boolean ywzxsly = roleCodes.contains("ywzxsly");
	//判断是否超级管理员或业务咨询受理员或部门为行政服务中心部门编码88888
	if("__ALL".equals(resKey)||ywzxsly||depCode.equals("88888")){
	    request.setAttribute("isAll",true);
	}else{
	    request.setAttribute("isAll",false);
	}
%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("busConsultInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#busConsultInfoForm").serialize();
				var url = $("#busConsultInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.reloadBusConsultList(resultJson.msg);
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_HD_CONSULT");

		$("input[name='AUDIT_STATUS']").on("click", function(event) {
			if (this.value === "1") {
				$("#tr_dept").show();			
				$("#tr_reply").hide();	
				$("[name='REPLY_CONTENT']").val('');
				$("input[name='CONSULT_DEPT']").attr("disabled",false);
				$("[name='REPLY_CONTENT']").attr("disabled",true);			
			} else {
				$("#tr_dept").hide();
				$("#tr_reply").show();
				$("[name='REPLY_CONTENT']").val('');
				$("input[name='CONSULT_DEPT']").attr("disabled",true);
				$("[name='REPLY_CONTENT']").attr("disabled",false);
			}
		});
	});
	function showSelectDeparts(){
		var departId = $("input[name='CONSULT_DEPTID']").val();
		$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
			title : "组织机构选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					$("input[name='CONSULT_DEPTID']").val(selectDepInfo.departIds);
					$("input[name='CONSULT_DEPT']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="busConsultInfoForm" method="post"
		action="consultController.do?saveOrUpdate">
		<div region="center" style="min-height:456px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="CONSULT_ID" value="${busConsult.CONSULT_ID}">
			<input type="hidden" name="CONSULT_DEPTID" value="${busConsult.CONSULT_DEPTID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询标题：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${busConsult.CONSULT_TITLE}</p>
					</td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询类型：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;"><c:if test="${busConsult.CONSULT_TYPE == 0}">办事咨询</c:if><c:if test="${busConsult.CONSULT_TYPE == 1}">其他咨询</c:if></p>
					</td>
				</tr>
				<c:if test="${busConsult.CONSULT_TYPE == 0}">
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询事项：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${busConsult.CONSULT_ITEMS}</p>
					</td>
				</tr>
				</c:if>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询部门：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${busConsult.CONSULT_DEPT}</p>
					</td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询内容：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${busConsult.CONSULT_CONTENT}</p>
					</td>
				</tr>				
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询者：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;">${busConsult.CREATE_USERNAME}</p>
					</td>
				</tr>
				<tr>					
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">手机号码：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;">${userInfo.SJHM}</p>
					</td>
				</tr>
				</table>
				<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>回复信息</a></div></td>
				</tr>
				
				<tr <c:if test="${isAll == false}">style="display:none;"</c:if>>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">状态：</span>
					</td>
					<td  colspan="3">
						直接回复<input type="radio" value="0" name="AUDIT_STATUS" checked="checked"/>
						转发部门<input type="radio" value="1" name="AUDIT_STATUS"/>
					</td>
				</tr>	
				<tr style="height:29px;display:none;" id="tr_dept">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">部门：</span>
					</td>
					<td  colspan="3">
						<input class="eve-input validate[required]" type="text" name="CONSULT_DEPT" placeholder="请选择部门" style="width:172px"  readonly="readonly" onclick="showSelectDeparts();" disabled="disabled"/>
					</td>
				</tr>
				
				<tr style="height:100px;" id="tr_reply">
					<td>
						<span style="width: 80px;float:left;text-align:right;height:100px;">回复内容：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:420px;height:100px;" 
						  name="REPLY_CONTENT">${busConsult.REPLY_CONTENT}</textarea>
					</td>
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

