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
        return width;
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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"QHXZCBYJDJLXD");
    		}
    	}
    	
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
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
					平潭综合实验区投资项目<br />规划选址初步意见对接联系单
				</div>
				<!-- <div class="eui-word-cg">（存根联）</div>
				<div class="eui-word-xmbh">项目编号：<input type="text" class="eui-ipt1" style="width:150px"/></div> -->
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:250px" name="VB_CGLDEPART_NAME_ST" readonly="readonly" maxlength="50"  value="${SQData.gwjsbm}"  />：
				</div>
				<div class="eio-word-content">
					<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" readonly="readonly" style="width:200px"  name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />项目的《项目建设方案概述》已完成，现转给你单位。请于<input type="text"
						class="eui-ipt laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGLCN_TIME_ST" maxlength="10" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
						前（5个工作日）将该项目的规划选址初步意见提交我局。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
				</div>
				<div class="eui-word-notes clearfix">
					<label>附件：</label><span>
					1.规划选址初步对接表；<br />2.项目建设方案概述；<br />3.区管委会主任办公会议（专题会议）纪要或区年度计划表。
					</span>
				</div>
				<div class="eui-word-jjr">
					签收人：<input type="text" class="eui-ipt1" data-validation-engine="validate[]" style="width:170px"  name="VB_QSR_ST" maxlength="10" />联系电话：<input
						type="text" class="eui-ipt1" data-validation-engine="validate[]" style="width:170px" name="VB_QSRLXDH_ST" maxlength="15"/>
				</div>
				<div class="eui-word-notes clearfix">
					<label>备注：</label><span>
					请区规划局对选址征求意见环节涉以下项目审查事项给予明确：□涉海项目审查 □涉地质灾害易发区域内的项目审查□涉水土保持重点预防区内的项目审查□涉国家级旅游风景名胜区域内项目的审查□涉气象台站探测环境审查 □涉危害地震观测环境审查  □涉国家安全事项建设项目审查</span>
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
