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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"SHTZSGTSCSLD");
    		}
    	}
		
		//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	$(this).width(textWidth($(this).val()));
    		}
		});
		
		$("#submitPage").click(function(){
			var otherItem = $("input[name='VB_QTITEMTEXT_ST']").val();
			$("input[name='VB_OTHERITEM_ST']").attr("value",otherItem);
		});

		AppUtil.initWindowForm("SHTZSGTSCSLD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = WordUtil.getFormEleData("SHTZSGTSCSLD");
				var url = $("#SHTZSGTSCSLD").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							art.dialog.data("saveTemplateInfo", {
								templatePath : resultJson.templatePath,
								xnb_id : resultJson.xnb_id
							});
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}

		}, null, "bottomLeft");
	});
</script>
</head>

<body>
	<form id="SHTZSGTSCSLD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br />《施工图》(送审版)审查受理单
				</div>
				<div class="eui-word-xmbh">
					项目编号：岚综实项目【<input type="text" name="VB_XMNH_ST" class="eui-ipt1" data-validation-engine="validate[required]"
						style="width:60px" />】<input type="text" name="VB_XMBH_ST" class="eui-ipt1" data-validation-engine="validate[required]"
						style="width:30px" />号
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:250px" name="VB_XMSQR_ST" maxlength="50"  value="${SQData.xmsqry}"  />：
				</div>
				<div class="eio-word-content">
					你单位于<input type="text" class="eui-ipt" value="${SQData.xmsqsj}"
						style="width:135px;" name="VB_GCSQ_TIME_ST" readonly="readonly"/>
						提请的<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:273px"
						name="VB_GCSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />项目《施工图》（送审版）审查申请材料
					（<input type="checkbox" name="VB_SQB_CK" value="1">  项目《施工图》（送审版）审查申请表 
					  <input type="checkbox" name="VB_CNS_CK" value="1">  申请办理行政审批事项承诺书
					  <input type="checkbox" name="VB_CBSJGS_CK" value="1">  《施工图》（含初步设计及概算）（送审版）
					  <input type="checkbox" name="VB_QT_CK" value="1">  其他
					  <input type="text" name="VB_QTDETAIL_ST" class="eui-ipt" style="width:60px">）
					收悉，经审核，要件齐全，符合申请条件，现予以受理。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
				</div>
				<div class="eui-word-jjr">
					交件人：<input type="text" class="eui-ipt1" data-validation-engine="validate[]" style="width:170px"  name="VB_JJR_ST" maxlength="10" />联系电话：<input
						type="text" class="eui-ipt1" data-validation-engine="validate[]" style="width:170px" name="VB_LXDH_ST" maxlength="15"/>
				</div>
				<div class="eui-word-notes clearfix">
					<label>备注：</label>
					<span>
						1.该《施工图》（送审版）受理在《施工图》送审期间进行，不计入审批时限；
					</span>
					<span>
						2.该审查环节只征求行业主管部门意见。
					</span>
				</div>
			</div>
			<div class="eui-word-bottom"></div>
		</div>
		<div class="eve_buttons">
		    <input value="保存" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" id="submitPage"/>
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
