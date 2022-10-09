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
	var textWidth = function(text){
        var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ text + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '21px 宋体, Arial');
        var width = currentObj.width();
        currentObj.remove();
        return width+10;
    };
	$(function(){
		//input宽度自适应输入内容
		$("input").unbind('keydown').bind('keydown', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
	    
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"JGCWJSHZH");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("JGCWJSHZH", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("JGCWJSHZH");
				var url = $("#JGCWJSHZH").attr("action");
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
	<form id="JGCWJSHZH" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					<div class="eui-word-xmbh">
						<input type="text" class="eui-ipt1" style="width:150px;" data-validation-engine="validate[required]" 
						name="VB_CGLDEPART_NUM_ST" placeholder="各部门发函编号"/>
					</div>
					<br/>
					<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />
					项目竣工财务决算<br />审核意见回执函	
				</div>
				<div class="eui-word-name" style="font-size: 18px;"><input
						type="text" class="eui-ipt" readonly="readonly" value="区行政审批局" />：
				</div>
				<div class="eio-word-content">
					你局关于《竣工财务决算审核联系单（二）》（项目编号：岚综实项目
					〔<input type="text" name="VB_XMNH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:60px"/>〕<input type="text" name="VB_XMBH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:30px"/>号）收悉
					。经审核，具体意见如下：
					  <textarea rows="8" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
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
