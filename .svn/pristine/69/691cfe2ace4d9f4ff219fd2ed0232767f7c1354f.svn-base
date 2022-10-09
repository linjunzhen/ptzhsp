<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("osOptionForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#osOptionForm").serialize();
				var url = $("#osOptionForm").attr("action");
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
							//parent.reloadosOptionList(resultJson.msg);
							parent.$("#osOptionGrid").datagrid("reload");
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
		}, "T_HD_OS_OPTION");
	});
	
	function onSelectClass(o){
		if(o!=0){
			$("#appendtickets_tr").show();
			$("#appendtickets").attr("disabled",false); 
		}else{
			$("#appendtickets_tr").hide();
			$("#appendtickets").attr("disabled",true); 
		}
	}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="osOptionForm" method="post"
		action="osOptionController.do?saveOrUpdate">
		<div region="center" style="min-height:160px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="OPTIONID" value="${osOption.OPTIONID}">
			<input type="hidden" name="QUESTIONID" value="${osOption.QUESTIONID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:35px;">
					<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">选项标题：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="10" type="text" name="TITLE" value="${osOption.TITLE}" style="width:80px;"/>
					</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">选项内容：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="50" type="text" name="CONTENT" value="${osOption.CONTENT}" style="width:480px;"/>
					</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">排序编号：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required],custom[onlyNumberSp]" maxLength="3" type="text" name="SEQNUM" value="${osOption.SEQNUM}" style="width:80px;"/>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 80px;float:left;text-align:right;">统计方式：</span>
					</td>
					<td colspan="3">
						<select defaultemptytext="请选择统计方式" class="eve-input validate[required]" name="COUNTTYPE"  onChange="onSelectClass(this.value)">
							<option value="">请选择统计方式</option>						
							<option value="0" <c:if test="${osOption.COUNTTYPE=='0'}">selected="selected"</c:if>>取真实票数</option>
							<option value="1" <c:if test="${osOption.COUNTTYPE=='1'}">selected="selected"</c:if>>取附加票数</option>					
							<option value="2" <c:if test="${osOption.COUNTTYPE=='2'}">selected="selected"</c:if>>取总和</option>
						</select>	
					</td>
				</tr>
				<tr id="appendtickets_tr"  <c:if test="${osOption.COUNTTYPE==0||osOption.COUNTTYPE==null}">style="display:none;"</c:if>>
					<td style="width:80px;height:35px;">
						<span style="width: 80px;float:left;text-align:right;">附加票数：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required],custom[onlyNumberSp]" maxLength="9" type="text" id="appendtickets" name="APPENDTICKETS" value="${osOption.APPENDTICKETS}" style="width:80px;" <c:if test="${osOption.COUNTTYPE==0||osOption.COUNTTYPE==null}">disabled="disabled"</c:if>/>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

