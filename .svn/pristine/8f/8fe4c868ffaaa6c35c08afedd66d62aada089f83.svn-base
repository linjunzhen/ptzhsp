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
        /* var sensor = $('<pre>'+ text +'</pre>').css({display: 'none'}); 
        $('body').append(sensor); 
        var width = sensor.width();
        sensor.remove(); 
        return width */
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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"GHXZCBYJDJSLD");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("GHXZCBYJDJSLD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("GHXZCBYJDJSLD");
				var url = $("#GHXZCBYJDJSLD").attr("action");
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
	<form id="GHXZCBYJDJSLD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br />规划选址初步意见对接受理单
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" 
						name="VB_GCSLDXMSQR_ST" style="width:150px;"
						value="${SQData.xmsqry}" />：
				</div>
				<div class="eio-word-content">
					你单位于<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:135px"
						readonly="readonly" name="VB_GCSLDXMSQSJ_ST" maxlength="30"
						value="${SQData.xmsqsj}" />提请的<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:200px;"
						name="VB_GCSLDSUBJECT_ST" readonly="readonly"
						value="${SQData.blxmmc}" />项目规划选址初步对接申请材料（
						<input type="checkbox" name="VB_GCDJB_CK" value="1" >  规划选址初步对接表
						<input type="checkbox" name="VB_GCCNS_CK" value="1" >  申请办理行政审批事项承诺书
						<input type="checkbox" name="VB_GCFAGS_CK" value="1" >  项目建设方案概述
						<input type="checkbox" name="VB_GCJCWJ_CK" value="1" >  项目决策文件
						<input type="checkbox" name="VB_GCQT_CK" value="1" >  其他 
						<input type="text" name="VB_GCQTDETAIL_ST" class="eui-ipt" style="width:50px;">）收悉，经审核，要件齐全，符合申请条件。请于<input
						type="text" class="eui-ipt laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GCSLDCN_TIME_ST"
						maxlength="10" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />(
					7个工作日)凭此单到区行政服务中心大厅我局项目投资窗口领取规划选址初步意见。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GCSLDDQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>
				<div class="eui-word-jjr">
					交件人：<input type="text" class="eui-ipt1"
						data-validation-engine="validate[]" style="width:170px"
						name="VB_QSR_ST" maxlength="18" />联系电话：<input type="text"
						class="eui-ipt1" data-validation-engine="validate[]"
						style="width:170px" name="VB_QSRLXDH_ST" maxlength="18" />
				</div>
				<div class="eui-word-notes clearfix">
					<label>备注：</label><span>
						项目决策文件：（1）年度计划内项目提交年度计划表；（2）年度计划外项目提交该项目的区管委会主任办公会或专题会议纪要。</span>
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
