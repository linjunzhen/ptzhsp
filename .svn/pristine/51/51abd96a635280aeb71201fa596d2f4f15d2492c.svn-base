<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/wordOnline-1.0/js/WordUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"SHTZSGTLHSCLXD");
    		}
    	}
		//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		AppUtil.initWindowForm("SHTZSGTLHSCLXD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("SHTZSGTLHSCLXD");
				var url = $("#SHTZSGTLHSCLXD").attr("action");
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
	<form id="SHTZSGTLHSCLXD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br> 《施工图》(修编版)联合审查联系单
				</div>
				<div class="eui-word-xmbh">
					项目编号：岚综实项目〔 <input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:60px"
						name="VB_XMNH_ST" maxlength="4" /> 〕 <input type="text"
						class="eui-ipt" data-validation-engine="validate[required]"
						style="width:30px" name="VB_XMBH_ST" maxlength="4" /> 号
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:250px" name="VB_JBR_NAME_ST" maxlength="50"
						value="${SQData.gwjsbm}" />：
				</div>
				<div class="eio-word-content">
					<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:273px"
						name="VB_GCXM_NAME_ST" maxlength="200" value="${SQData.blxmmc}" />项目的《施工图》图审工作已完成，现转给你单位。
					请于<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GCXM_TIME_ST"
						maxlength="10" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />前（10个工作日）将项目相关的审查意见（
						 <input type="checkbox" name="VB_CBSJ_CK" value="1">初步设计（含概算） 
						 <input type="checkbox" name="VB_RFSPYJ_CK" value="1">人防审批意见 
						 <input type="checkbox" name="VB_XFSPYJ_CK" value="1">消防审批意见 
						 <input type="checkbox" name="VB_HYZGYJ_CK" value="1">行业主管意见 
						 <input type="checkbox" name="VB_QT_CK" value="1">其他 
						 <input type="text" class="eui-ipt" style="width:60px" name="VB_QTDETAIL_ST" />）提交我局。
					缺席联合审查或超期不反馈意见的，视为默认同意。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_CGLDQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>
				<div class="eui-word-notes clearfix">
					<label>附件：</label><span> 1. 项目《施工图》（修编版）联合审查申请表；<br />
						2. <input type="text" class="eui-ipt"
						data-validation-engine="validate[required]"
						style="width:100px;font-size: 14px;"
						maxlength="200" value="${SQData.blxmmc}" />项目《施工图》（修编版）；<br />
						3. <input type="checkbox" name="VB_CLQT_CK" value="1"> 其他<input
						type="text" class="eui-ipt" style="width:100px;font-size: 14px;"
						name="VB_CLOTHER_ST" maxlength="200" />
					</span>
				</div>
				<div class="eui-word-jjr">
					签收人：<input type="text" class="eui-ipt1"
						data-validation-engine="validate[]" style="width:170px"
						name="VB_QSR_ST" maxlength="10" />联系电话：<input type="text"
						class="eui-ipt1" data-validation-engine="validate[]"
						style="width:170px" name="VB_LXDH_ST" maxlength="15" />
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
