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
		//input宽度自适应输入内容
		$("input").unbind('keydown').bind('keydown', function(){
			changeInputlength(this);
	    });
		
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"QHXZCBYJDJLXD");
    		}
    	}
    	
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	changeInputlength(this);
    		}
		});
		
		AppUtil.initWindowForm("QHXZCBYJDJLXD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("QHXZCBYJDJLXD");
				var url = $("#QHXZCBYJDJLXD").attr("action");
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
function changeInputlength(obj) 
{ 
	var getText=obj;//document.getElementById("text"); 
	obj.size=(getText.value.length)*2;//+8; 
} 
</script>
</head>

<body>
	<form id="QHXZCBYJDJLXD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="min-width:273px;" 
					 name="VB_CGLSUBJECT_ST" maxlength="200" onkeydown="changeInputlength(this);" value="${SQData.blxmmc}" />
						项目统一验收意见回执函
				</div>
				<div class="eui-word-name" style="font-size: 18px;">
					区行政审批局：
				</div>
				<div class="eio-word-content">
					你局关于《统一验收征求意见联系单（一）》（项目编号：岚综实项目〔
					<input type="text" name="VB_CGL_NUM_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="100" style="width:150px"/>
					〕号）收悉。经审核，具体意见如下：<br/>
					<textarea rows="5" cols="6" data-validation-engine="validate[required]" 
					style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:25px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  name="VB_CGL_REVIEW_ST" maxlength="200" ></textarea>
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
