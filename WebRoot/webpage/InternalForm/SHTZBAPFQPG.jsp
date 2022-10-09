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
		//input宽度自适应输入内容
		$("input").unbind('blur').bind('blur', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
		
	    
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"SHTZBAPFQPG");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("SHTZBAPFQPG", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("SHTZBAPFQPG");
				var url = $("#SHTZBAPFQPG").attr("action");
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
	<form id="SHTZBAPFQPG" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区关于<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:150px;"
						name="VB_SUBJECT_ST" readonly="readonly"
						value="${SQData.blxmmc}" /><br />项目《备案请示+》的批复
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:180px"
						name="VB_SQR_ST" readonly="readonly" maxlength="50"
						value="${SQData.xmsqry}" />：
				</div>
				
				<div class="eio-word-content">
					我局已于
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_SLSJ_ST"	maxlength="16" value=""
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />受理了你公司提出的项目《备案请示+》申请，按照《行政许可法》第二十五条和第二十六条的规定，经审查，集中公示后，决定如下。
				<br>
					&nbsp;&nbsp;一、关于工程可行性的意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_GK_ST" ></textarea><br>
					&nbsp;&nbsp;二、关于节能审查的意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_JN_ST" ></textarea><br>
					&nbsp;&nbsp;三、关于环境影响的审查意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_HJ_ST" ></textarea><br>
					&nbsp;&nbsp;四、关于水土保持的意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_ST_ST" ></textarea><br>
					&nbsp;&nbsp;五、资金来源审查的意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_ZJ_ST" ></textarea><br>
					&nbsp;&nbsp;六、关于社会稳定风险的意见：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_SH_ST" ></textarea><br>
					&nbsp;&nbsp;七、其他事项：<br>
					&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_QT_ST" ></textarea><br>
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_DQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
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
