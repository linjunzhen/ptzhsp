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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"YJDSGTLHSCYJS");
    		}
    	}
		//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		AppUtil.initWindowForm("YJDSGTLHSCYJS", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("YJDSGTLHSCYJS");
				var url = $("#YJDSGTLHSCYJS").attr("action");
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
	<form id="YJDSGTLHSCYJS" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区关于<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:200px"  name="VB_GCXM_NAME_ST" maxlength="200" value="${SQData.blxmmc}" />项目<br>
					《一阶段施工图》联合审查意见书
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:250px" name="VB_JBR_NAME_ST" maxlength="50"  value="${SQData.xmsqry}"  />：
				</div>
				<div class="eio-word-content">
					我局于<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:135px;" name="VB_GCXMSQSJ_ST" value="${SQData.xmsqsj}" readonly="readonly" />受理了你公司提出的
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:273px"  name="VB_GCXM_NAME_ST" maxlength="200" value="${SQData.blxmmc}" />项目《一阶段施工图》联合审查的申请，按照《行政许可法》第二十五条和二十六条的规定，经联合审查，集中公示后，决定如下：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、区财政投资评审中心审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_CJJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、区交通与建设局审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_JJJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、区交建局（人防办）审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_RFBYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、区消防支队审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_XFYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、区地震局审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_DZJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、区气象局审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_QXJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、区安全局审查意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_AQJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、其他事项<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_QT_ST" ></textarea><br>
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
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
