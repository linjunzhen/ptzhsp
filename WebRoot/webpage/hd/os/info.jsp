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
		AppUtil.initWindowForm("osTopicForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#osTopicForm").serialize();
				var url = $("#osTopicForm").attr("action");
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
							parent.reloadosTopicList(resultJson.msg);
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
		}, "T_HD_OS_TOPIC");
	});
	
	function onSelectClass(o){
		if(o==1){
			$("#resultcontent_tr").show();
			$("#resultcontent").attr("disabled",false); 
		}else{
			$("#resultcontent_tr").hide();
			$("#resultcontent").attr("disabled",true); 
		}
	}
</script>

</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="osTopicForm" method="post"
		action="osTopicController.do?saveOrUpdate">
		<div region="center" style="min-height:360px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="TOPICID" value="${osTopic.TOPICID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:35px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">主题标题：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="30" type="text" name="TITLE" value="${osTopic.TITLE}" style="width:480px;"/>
					</td>
				</tr>
				
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">开始日期：</span>
					</td>
					<td style="width: 197px;">
						<input type="text"
                                style="width:197px;float:left; height: 24px; line-height: 24px;"  readonly="ture" class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_date\')}'})" id="start_date" name="STARTDATE" value="${osTopic.STARTDATE}"  />
					</td>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">结束日期：</span>
					</td>
					<td>
						<input type="text"
                                style="width:197px;float:left; height: 24px; line-height: 24px;" class="validate[required] Wdate"
                                id="end_date" name="ENDDATE" value="${osTopic.ENDDATE}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_date\')}'})"/>
					</td>
				</tr>
				<tr style="height:100px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;height:100px;">主题内容：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:480px;height:100px;" 
						  name="CONTENT">${osTopic.CONTENT}</textarea>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 80px;float:left;text-align:right;">状态：</span>
					</td>
					<td colspan="3">
					<select defaultemptytext="请选择状态" class="eve-input validate[required]" name="STATE"  onChange="onSelectClass(this.value)">
						<option value="">请选择状态</option>						
						<option value="0" <c:if test="${osTopic.STATE==0}">selected="selected"</c:if>>未发布</option>
						<option value="1" <c:if test="${osTopic.STATE==1}">selected="selected"</c:if>>已发布</option>
					</select>	
					</td>
				</tr>
				<tr id="resultcontent_tr" style="height:100px;<c:if test="${osTopic.STATE!=1||osTopic.STATE==null}">display:none;</c:if>">
					<td>
						<span style="width: 80px;float:left;text-align:right;height:100px;">结果反馈：</span>
					</td>
					<td  colspan="3">
						<textarea id="resultcontent" class="eve-textarea maxSize[500]" rows="3" cols="200" style="width:480px;height:100px;" 
						  name="RESULTCONTENT" <c:if test="${osTopic.STATE!=2||osTopic.STATE==null}">disabled="disabled"</c:if>>${osTopic.RESULTCONTENT}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
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

