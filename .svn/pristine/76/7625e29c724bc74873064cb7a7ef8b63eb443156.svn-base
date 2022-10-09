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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"JGCSJSLXD");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("JGCSJSLXD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("JGCSJSLXD");
				var url = $("#JGCSJSLXD").attr("action");
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
	<form id="JGCSJSLXD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br />竣工财务决算审核联系单（二）
				</div>
				<div class="eui-word-xmbh">项目编号：岚综实项目〔<input type="text" name="VB_XMNH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:60px"/>〕<input type="text" name="VB_XMBH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:30px"/>号</div>
				<div class="eui-word-name" style="font-size: 18px;">
				<input type="text" class="eui-ipt" readonly="readonly" style="width:200px"  value="区财政金融局" />：
				</div>
				<div class="eio-word-content">
					<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:273px"  name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />
						项目经联合竣工验收，并已完成竣工财务决算，现转给你单位。请于
						<input type="text"
						class="eui-ipt laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGLCN_TIME_ST" maxlength="10" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
						前(<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:60px"  name="VB_CGLDAYS_ST" maxlength="200" />
						个工作日)将工程结算审核、竣工财务决算审批提交我局，超期视为默认同意。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;"
					 name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
				</div>
				<div class="eui-word-notes clearfix">
					<label>附件：</label><span>
					1.项目统一竣工财务决算申请表；<br />2.竣工财务决算申请材料。
					</span>
				</div>
				<div class="eui-word-jjr">
					交件人：<input type="text" class="eui-ipt1" style="width:170px"  name="VB_QSR_ST" maxlength="10" />
					联系电话：<input
						type="text" class="eui-ipt1" style="width:170px" name="VB_QSRLXDH_ST" maxlength="15"/>
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
