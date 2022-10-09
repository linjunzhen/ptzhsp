<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/wordOnline-1.0/js/WordUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/InternalForm/css/word.css" />
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function(){
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"TYYSQSD");
    		}
    	}
		
		AppUtil.initWindowForm("TYYSQSD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("TYYSQSD");
				var url = $("#TYYSQSD").attr("action");
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
							  art.dialog.data("saveTemplateInfo", {
									templatePath:resultJson.templatePath,
									xnb_id:resultJson.xnb_id
							    });
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
				
		},null,"bottomLeft");
	});
</script>
</head>

<body>
	<form id="TYYSQSD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br />统一验收签收单（一）
				</div>
				<div class="eui-word-xmbh">项目编号：岚综实项目〔<input type="text" name="VB_XMNH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:60px"/>〕<input type="text" name="VB_XMBH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:30px"/>号</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:250px" name="VB_CGLXMSQR_ST" maxlength="50"  value="${SQData.xmsqry}"  />：
				</div>
				<div class="eio-word-content">	
						你单位于
						<input type="text" value="${SQData.xmsqsj}"
						class="eui-ipt" data-validation-engine="validate[required]" style="width:135px;"
						name="VB_CGLXMSQSJ_ST" maxlength="10" readonly="readonly" />
						提请的
						<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:273px"  name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />
						项目统一验收受理已完成，现将项目《联合竣工验收汇总表》交给你单位。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;"
					 name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
				</div>
				
				<div class="eui-word-jjr">
					签收人：<input type="text" class="eui-ipt1" style="width:170px"  name="VB_QSR_ST" maxlength="10" />
					联系电话：<input
						type="text" class="eui-ipt1" style="width:170px" name="VB_QSRLXDH_ST" maxlength="15"/>
				</div>
			</div>
			<div class="eui-word-bottom"></div>
		</div>
		<div class="eve_buttons">
		    <input value="保存" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
