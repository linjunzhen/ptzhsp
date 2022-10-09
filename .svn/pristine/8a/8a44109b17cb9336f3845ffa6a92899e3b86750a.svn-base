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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"TYYSSLD");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("TYYSSLD", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("TYYSSLD");
				var url = $("#TYYSSLD").attr("action");
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
	<form id="TYYSSLD" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区投资项目<br />统一验收受理单（一）
				</div>
				<div class="eui-word-xmbh">项目编号：岚综实项目〔<input type="text" name="VB_XMNH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:60px"/>〕<input type="text" name="VB_XMBH_ST" class="eui-ipt" data-validation-engine="validate[required]" maxlength="4" style="width:30px"/>号</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:200px" name="VB_XMSQR_ST" maxlength="50"  value="${SQData.xmsqry}"  />：
				</div>
				<div class="eio-word-content">
					你单位于<input type="text" class="eui-ipt laydate-icon" data-validation-engine="validate[required]"
					 style="width:150px;height: 36px;" name="VB_XMSQSJ_ST" maxlength="10" readonly="readonly" value="${SQData.xmsqsj}" />
					提请的	
					<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:200px"  name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />
						项目竣工验收申请材料(
						<input type="checkbox" name="VB_YSSQB_CK" value="1" /> 项目统一竣工验收申请表
						<input type="checkbox" name="VB_CNS_CK" value="1" /> 申请办理行政审批事项承诺书
						<input type="checkbox" name="VB_YSBG_CK" value="1" /> 工程竣工验收报告
						<input type="checkbox" name="VB_ZLPGBG_CK" value="1" /> 工程质量评估报告
						<input type="checkbox" name="VB_ZLJCBG_CK" value="1" /> （勘察单位和设计单位）质量检查报告
						<input type="checkbox" name="VB_DAHGLZL_CK" value="1" /> 档案资料和施工管理资料
						<input type="checkbox" name="VB_ZLBXS_CK" value="1" /> 工程质量保修书
						<input type="checkbox" name="VB_SYZL_CK" value="1" /> 相关质量检测和功能性试验资料
						<input type="checkbox" name="VB_ZMCL_CK" value="1" /> 工程质量整改证明材料
						<input type="checkbox" name="VB_JDFFP_CK" value="1" /> 缴交工程质量安全监督费发票
						<input type="checkbox" name="VB_AQPJS_CK" value="1" /> 工程施工安全评价书
						<input type="checkbox" name="VB_QT_CK" value="1" /> 其他<input
						type="text" class="eui-ipt"  style="width:100px"  name="VB_QTDETAIL_ST" maxlength="200" />
						)
						 收悉，经审核，要件齐全，符合申请条件。请于
						<input type="text"
						class="eui-ipt laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;" name="VB_CGGET_TIME_ST" maxlength="10" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
						(40个工作日)凭此单到区行政服务中心大厅我局项目投资窗口领取《项目联合竣工验收意见汇总表》。
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;"
					 name="VB_CGSEND_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
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
