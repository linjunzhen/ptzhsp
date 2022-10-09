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
    		$('#opinionDivs').attr('style','display:none');
    		if(dataObj.variableMap){
    			if(dataObj.variableMap['HAVEOPINION']==2){
    				$('#opinionDivs').attr('style','');
    			}
    			WordUtil.initFormFieldValue(dataObj.variableMap,"SHTZBALHSCHZH");
    		}
    	}
    	
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
	
		AppUtil.initWindowForm("SHTZBALHSCHZH", function(form, valid) {
			if (valid) {
				getOpinion();
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("SHTZBALHSCHZH");
				var url = $("#SHTZBALHSCHZH").attr("action");
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
	
	function getOpinion(){
		var opinionInfo = "";
		if($("input[name='VB_HAVEOPINION_ST']:checked").val()==1){
			opinionInfo="通过审查";
			$("#reason").val("");
		}else  if($("input[name='VB_HAVEOPINION_ST']:checked").val()==2){
			opinionInfo="不通过审查，主要依据和理由：";
				var reasons =$("#reason").val();
				opinionInfo +=reasons;
		}
		$("#gcopinion").val(opinionInfo+"。");
	}
</script>
</head>

<body>
	<form id="SHTZBALHSCHZH" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<input type="hidden" name="VB_OPTION_ST" id="gcopinion">
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-xmbh">
					<input type="text" placeholder="请输入部门函编号" name="VB_HZHBH_ST" class="eui-ipt1" style="width:150px"/>
				</div>
				<div class="eui-word-title">
					关于<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:130px" name="VB_SUBJECT_ST" maxlength="200"
						value="${SQData.blxmmc}" />项目《项目备案请示+》（修编版）联合审查意见回执函
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" style="width:150px"
						data-validation-engine="validate[required]" readonly="readonly"
						value="区行政审批局：" />
				</div>
				<div class="eio-word-content">
					你局关于《平潭综合实验区投资项目〈项目备案请示+〉修编版联合审查联系单》（项目编号：岚综实项目〔<input type="text" class="eui-ipt"
					data-validation-engine="validate[required]" style="width:70px" name="VB_BHN_ST" maxlength="50"
					value="${SQData.KXXBGXMBHN}" />〕<input type="text" class="eui-ipt"
					data-validation-engine="validate[required]" style="width:30px" name="VB_BHH_ST" maxlength="50"
					value="${SQData.KXXBGXMBHH}" />号）收悉。<br>
					 &nbsp;&nbsp;&nbsp;&nbsp; 经对<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:200px" maxlength="200" name="VB_SUBJECT_ST"
						value="${SQData.blxmmc}" /> 项目《项目备案请示+》（修编版）联合审查涉及的<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]"
						style="width:200px" name="VB_SX_ST" maxlength="200" />事项进行认真审查，意见如下:<br>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEOPINION_ST" value="1" onclick="$('#opinionDivs').attr('style','display: none;');"/>通过审查<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEOPINION_ST" value="2" onclick="$('#opinionDivs').attr('style','');"/>不通过审查，主要依据和理由:
					<br>
					<div id="opinionDivs" >
						<div id="opinionDiv" >						
							<textarea rows="6" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_REASON_ST" id="reason"></textarea>
						</div><br>
					</div>
				</div>	
				<br>
				<div class="eui-word-bname">单位盖章</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_DQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>				
				<div class="eui-word-jjr">
					联系人：<input type="text" class="eui-ipt1" style="width:170px"  name="VB_LXR_ST" maxlength="10" />联系电话：<input
						type="text" class="eui-ipt1" style="width:170px" name="VB_LXDH_ST" maxlength="15"/>
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
