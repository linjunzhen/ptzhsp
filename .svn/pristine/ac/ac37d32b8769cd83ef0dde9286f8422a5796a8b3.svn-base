<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	function showSelectBusTypes(){
		var needCheckIds= $("input[name='BUS_TYPEIDS']").val();
		//定义数据源
		art.dialog.data("EveTreeDataConfig", {
			allowCount:0,
			checkCascadeY:"ps",
			checkCascadeN:"s",
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_WSBS_BUSTYPE",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "PARENT_ID,PATH,TYPE_CODE",
				"rootParentId" : "0",
				"needCheckIds":needCheckIds,
				"isShowRoot" : "false",
				"rootName" : "主题服务类别树"
			}
		});
		parent.$.dialog.open("eveControlController.do?treeSelector", {
			title : "主题服务类别选择器",
	        width:"500px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
				var selectTreeInfo = art.dialog.data("selectTreeInfo");
				if(selectTreeInfo){
					$("input[name='BUS_TYPEIDS']").val(selectTreeInfo.checkIds);
					$("input[name='BUS_TYPENAMES']").val(selectTreeInfo.checkNames);
					art.dialog.removeData("selectTreeInfo");
				}
			}
		}, false);
	}

	$(function() {
		AppUtil.initWindowForm("CommonProblemForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				$("#ANSWER_CONTENT").val(document.getElementById("eWebEditor").contentWindow.getHTML());
				if($("#ANSWER_CONTENT").val()==null||$("#ANSWER_CONTENT").val()==''||$("#ANSWER_CONTENT").val()==undefined){
					art.dialog({
						content : "请输入问题答案",
						icon : "warning",
						ok : true
					});
					$("input[type='submit']").attr("disabled", false);
					return;
				}
				var formData = $("#CommonProblemForm").serialize();
				var url = $("#CommonProblemForm").attr("action");
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
							//parent.$("#CommonProblemGrid").datagrid("reload");
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
		}, "T_WSBS_COMMONPROBLEM");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="CommonProblemForm" method="post"
		action="commonProblemController.do?saveOrUpdate">
		<div id="CommonProblemFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="PROBLEM_ID"
				value="${commonProblem.PROBLEM_ID}">
			<input type="hidden" name="ITEM_ID"
				value="${commonProblem.ITEM_ID}">
			<input type="hidden" name="BUS_TYPEIDS" value="${commonProblem.BUS_TYPEIDS}">	
			<input type="hidden" name="DICTYPE_ID"
				value="${commonProblem.DICTYPE_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">问题标题：</span>
						<input type="text" style="width:350px;float:left;" maxlength="254"
						class="eve-input validate[required]"
						value="${commonProblem.PROBLEM_TITLE}" name="PROBLEM_TITLE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<%-- 
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">主题服务类别：</span>
						<input type="text" style="width:350px;float:left;" maxlength="256" readonly="readonly"
					    onclick="showSelectBusTypes();"
						class="eve-input validate[required]" value="${commonProblem.BUS_TYPENAMES}" name="BUS_TYPENAMES" />
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				--%>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">问题答案：</span>
						<input type="hidden" name="ANSWER_CONTENT" id="ANSWER_CONTENT"
						value="${commonProblem.ANSWER_CONTENT}"> <IFRAME
							ID="eWebEditor"
							SRC="plug-in/ewebeditor/ewebeditor.htm?id=ANSWER_CONTENT&style=mini500"
							FRAMEBORDER="0" SCROLLING="no" WIDTH="650" HEIGHT="350"></IFRAME>
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
	<div class="treeContent eve-combotree"
		style="display:none; position: absolute;" id="DICTYPE_IDTreeContent">
		<ul class="ztree" style="margin-top:0; width:160px;height: 150px"
			id="DICTYPE_IDTree"></ul>
	</div>

</body>

