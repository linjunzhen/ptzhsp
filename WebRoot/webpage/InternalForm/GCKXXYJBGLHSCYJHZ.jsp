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
        return width;
    };
	$(function(){
		//input宽度自适应输入内容
		$("input").unbind('keydown').bind('keydown', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
		//input宽度自适应输入内容
		$("input").unbind('blur').bind('blur', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
		
	    
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"GCKXXYJBGLHSCYJHZ");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("GCKXXYJBGLHSCYJHZ", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("GCKXXYJBGLHSCYJHZ");
				var url = $("#GCKXXYJBGLHSCYJHZ").attr("action");
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
	<form id="GCKXXYJBGLHSCYJHZ" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:150px;"
						name="VB_KXXYJBGXMMC_ST" readonly="readonly"
						value="${SQData.blxmmc}" />项目<br />《工程可行性研究报告+》联合审查<br />情况汇总表
				</div>
				<br>
				<div class="eui-word-xmbh">
					项目编号：岚综实项目〔<input type="text"  data-validation-engine="validate[required]" name="VB_KXXYJBGBHN_ST" class="eui-ipt1" style="width:60px"/>〕
					<input type="text" name="VB_KXXYJBGBHH_ST"  data-validation-engine="validate[required]" class="eui-ipt1" style="width:40px"/>号
				</div>
				<br>
				<div class="eio-word-content">
				<br>
					&nbsp;&nbsp;区经发局（发改处）项目可行性和节能审查意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_FGC_ST" ></textarea><br>
					&nbsp;&nbsp;区规划局设计方案审查意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_GHJ_ST" ></textarea><br>
					&nbsp;&nbsp;区环土局（环评处）环境影响评价审查意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_HPC_ST" ></textarea><br>
					&nbsp;&nbsp;区经发局（水利处）水土保持审查意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_SLC_ST" ></textarea><br>
					&nbsp;&nbsp;区财金局资金来源审查意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_CJJ_ST" ></textarea><br>
					&nbsp;&nbsp;区重点办意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_ZDB_ST" ></textarea><br>
					&nbsp;&nbsp;片区开发管理局关于社会稳定风险的意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_KFGLJ_ST" ></textarea><br>
					&nbsp;&nbsp;其他事项：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_QTSX_ST" ></textarea><br>
					&nbsp;&nbsp;公示情况说明：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_GSQK_ST" ></textarea><br>
					&nbsp;&nbsp;协调情况说明：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_KXXBGXMYJ_XTQK_ST" ></textarea><br>			
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_KXXBGDQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>
				<br><br><br>
			</div>
			<div class="eui-word-bottom"></div>
		</div>
		<div class="eve_buttons">
		    <input value="保存" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
