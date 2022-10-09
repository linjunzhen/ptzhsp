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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"YJDSGTLHSCHZB");
    		}
    	}
		//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	$(this).width(textWidth($(this).val()));
    		}
		});
		AppUtil.initWindowForm("YJDSGTLHSCHZB", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("YJDSGTLHSCHZB");
				var url = $("#YJDSGTLHSCHZB").attr("action");
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
	<form id="YJDSGTLHSCHZB" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					《<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:273px"  name="VB_GCSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />项目一阶段施工图》<br>
					联合审查意见汇总表
				</div>
				<div class="eio-word-content">
					区财经局（评审中心）关于招标控制价的审定意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_CJJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区交建局关于行业部门审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_JJJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区交建局（人防办）关于结合民用建筑修建防空地下<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室的审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_RFBYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区消防支队关于建筑工程消防设计的审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_XFYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区气象局关于防雷装置设计的审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_QXJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区地震局关于抗震设防要求的审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_DZJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区安全局关于设计国家安全事项的审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_AQJYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他单位审查意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_QTDWYJ_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公示情况说明：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_GSQK_ST" ></textarea><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="6" 
					style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
					onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'"
					name="VB_BZ_ST" ></textarea><br>
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
				</div>
				<div class="eui-word-notes clearfix">
					<label>备注：</label>
					<span>
						房建项目需征求区交建局（人防办）、区消防支队、区气象局、区地震局意见。
					</span>
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
