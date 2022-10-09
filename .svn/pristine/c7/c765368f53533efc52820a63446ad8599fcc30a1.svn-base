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
		
		//如果其它选项选中，其它后面的内容为必填
		$("input[name='VB_KXXBG_QT_CK']").click("checked",function(){
	    	if (document.getElementsByName("VB_KXXBG_QT_CK")[0].checked){
				$("input[name='VB_KXXBG_YJS_ST']").attr("data-validation-engine","validate[required]");
			}else{
				$("input[name='VB_KXXBG_YJS_ST']").removeAttr("data-validation-engine");
			}
    	});
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"GCKXXYJBGXBBSCYJJSD");
    			if (document.getElementsByName("VB_KXXBG_QT_CK")[0].checked){
    				$("input[name='VB_KXXBG_YJS_ST']").attr("data-validation-engine","validate[required]");
    			}
    		}
    	}
    	
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("GCKXXYJBGXBBSCYJJSD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("GCKXXYJBGXBBSCYJJSD");
				var url = $("#GCKXXYJBGXBBSCYJJSD").attr("action");
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
	<form id="GCKXXYJBGXBBSCYJJSD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br />《工程可行性研究报告+》（修编版）<br />审查意见接收单
				</div>
				<br>
				<div class="eui-word-xmbh">
					项目编号：岚综实项目〔<input type="text" name="VB_KXXBGXMBHN_ST"  data-validation-engine="validate[required]" class="eui-ipt1" style="width:60px"/>〕
					<input type="text" name="VB_KXXBGXMBHH_ST"  data-validation-engine="validate[required]" class="eui-ipt1" style="width:40px"/>号
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:200px" name="VB_KXXBGLXDW_ST" maxlength="50"  value="${SQData.gwjsbm}"  />：
				</div>
				<div class="eio-word-content">
					你单位于<input	type="text" class="eui-ipt laydate-icon" data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_FKSJ_ST"	maxlength="10" readonly="readonly"  
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />提交的<input type="text" 
						class="eui-ipt"	data-validation-engine="validate[required]" readonly="readonly"
						style="width:200px" name="VB_XMMC_ST" maxlength="200" value="${SQData.blxmmc}" />
						项目《工程可行性研究报告+》（修编版）审查意见（
						<input type="checkbox" name="VB_KXXBG_GKYJ_CK" value="1" >  工可审查意见
						<input type="checkbox" name="VB_KXXBG_HPYJ_CK" value="1" >  环评审查意见
						<input type="checkbox" name="VB_KXXBG_SBYJ_CK" value="1" >  水保方案审查意见
						<input type="checkbox" name="VB_KXXBG_JNYJ_CK" value="1" >  节能评估报告审查意见 
						<input type="checkbox" name="VB_KXXBG_QT_CK" value="1" > 其他 
						<input type="text" name="VB_KXXBG_YJS_ST" class="eui-ipt" style="width:80px;">）
						，我局于<input	type="text" class="eui-ipt laydate-icon" data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_KXXBGXMJS_TIME_ST"	maxlength="10" readonly="readonly"  
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />收悉。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]"
					 style="width:150px;height: 36px;" name="VB_KXXBGXMDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}"
					 readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
				</div>
				<div class="eui-word-jjr">
					交件人：<input type="text" class="eui-ipt1" data-validation-engine="validate[]" style="width:170px"  name="VB_QSR_ST" maxlength="10" />联系电话：<input
						type="text" class="eui-ipt1" data-validation-engine="validate[]" style="width:170px" name="VB_QSRLXDH_ST" maxlength="15"/>
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
