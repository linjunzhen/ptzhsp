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
    			WordUtil.initFormFieldValue(dataObj.variableMap,"TYYSYJS");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("TYYSYJS", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("TYYSYJS");
				var url = $("#TYYSYJS").attr("action");
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
	<form id="TYYSYJS" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					<br/>
					平潭综合实验区关于
					<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" style="min-width: 200px;" readonly="readonly" maxlength="200" value="${SQData.blxmmc}" />
					投资项目<br />联合竣工验收意见书
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="min-width: 200px;" name="VB_CGLXMSQR_ST" maxlength="50"  value="${SQData.xmsqry}"  />：
				</div>
				<div class="eio-word-content">
					我局于
					<input type="text"
						class="eui-ipt"  style="width:135px;" name="VB_CGLXMSQSJ_ST" maxlength="10" readonly="readonly" value="${SQData.xmsqsj}" />
					受理了你公司提出的
					<input
						type="text" class="eui-ipt" data-validation-engine="validate[required]" style="width:273px" readonly="readonly" name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />
					项目统一验收申请，按照《行政许可法》第二十六条的规定，经征求相关职能部门意见及区领导审批，决定如下：
					
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">一、关于环保设施竣工验收意见: </span><br/>
							<textarea rows="6"  data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_HBYSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">二、关于人防工程验收意见:</span><br/>
							<textarea rows="6"  data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_RFYSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">三、关于建设工程消防验收意见:</span><br/>
							<textarea rows="6" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_XFYSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">四、关于建设工程规划验收意见:</span><br/>
							<textarea rows="6" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_GHYSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">五、关于档案验收意见:</span><br/>
							<textarea rows="6" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_DAYSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">六、关于建设工程验收备案意见:</span><br/>
							<textarea rows="6" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_YSBA_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">七、工程结算审核意见:</span><br/>
							<textarea rows="6" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_GCJSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">八、竣工财务决算审批意见:</span><br/>
							<textarea rows="6" data-validation-engine="validate[required]" 
							style="width: 99%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;text-indent:42px;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_CWJSYJ_ST" maxlength="200" ></textarea>
							</div>
							<div style="text-indent: 0px;">
							<span style="font-weight: bold;">九、其他事项:</span><br/>
							&nbsp;&nbsp;&nbsp;本《联合竣工验收意见书》载明的事项法律效力等同于相关职能部门出具的验收意见、工程结算审核、竣工财务决算审批等文书。
							若因工作衔接需要，项目业主可以依据本《联合竣工验收意见书》，直接到区财政金融局办理资金拨付手续，
							到区行政服务中心窗口办理房地产权证等，不需再提交其他申请材料或证明文件。
							</div>
				</div>
				<div class="eui-word-bname">平潭综合实验区行政审批局</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;"
					 name="VB_CGLDQ_TIME_ST" maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>
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
