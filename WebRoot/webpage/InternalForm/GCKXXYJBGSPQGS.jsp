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
        $(currentObj).css('font', '16px 宋体, Arial');
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
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"GCKXXYJBGSPQGS");
    		}
    	}
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("GCKXXYJBGSPQGS", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("GCKXXYJBGSPQGS");
				var url = $("#GCKXXYJBGSPQGS").attr("action");
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
	<form id="GCKXXYJBGSPQGS" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					平潭综合实验区<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:150px;"
						name="VB_KXXYJBGXMMC_ST" readonly="readonly"
						value="${SQData.blxmmc}" />项目可行性研究报告审批前公示
				</div>
				<div class="eio-word-content">
				<br>
				<table border="1" cellspacing="0" cellpadding="0" width="600" style="font-size:16px;text-indent:0px;">
				  <tr>
				    <td width="127"><p align="center">事项名称 </p></td>
				    <td width="468" colspan="4"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:100%;font-size:14px;"
						name="VB_KXXBGGSSXMC_ST" readonly="readonly"
						value="${SQData.blxmmc}" /></td>
				  </tr>
				  <tr>
				    <td width="127"><p align="center">申报单位 </p></td>
				    <td width="168"><textarea rows="2" style="width: 98%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_SBDW_ST" ></textarea></td>
				    <td width="131" colspan="2"><p align="center">建设单位 </p></td>
				    <td width="169"><textarea rows="2" style="width: 98%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_JSDW_ST" ></textarea></td>
				  </tr>
				  <tr>
				    <td width="127"><p align="center">建设地点 </p></td>
				    <td width="168"><textarea rows="2" style="width: 98%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_JSDD_ST" 
					data-validation-engine="validate[required]"></textarea></td>
				    <td width="131" colspan="2"><p align="center">资金来源</p></td>
				    <td width="169"><textarea rows="2" style="width: 98%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_ZJLY_ST" 
					data-validation-engine="validate[required]"></textarea></td>
				  </tr>
				  <tr>
				    <td width="127"><p align="center">建设期限</p></td>
				    <td width="168"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:100%;font-size:14px;"
						name="VB_JSQX_ST" /></td>
				    <td width="131" colspan="2"><p align="center">总投资估算  </p></td>
				    <td width="169"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width:100%;font-size:14px;"
						name="VB_XTZ_ST" /></td>
				  </tr>
				  <tr style="line-height: 36px;">
				    <td width="127"><p align="center">建设规模及<br/>主要内容 </p></td>
				    <td width="468" colspan="4" >
				       <textarea rows="6" style="width: 99%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_JSGMZYNR_ST" ></textarea>
				     </td>
				  </tr>
				  <tr>
				    <td width="127" rowspan="2"><p align="center">审批处室 </p></td>
				    <td width="198" colspan="2" rowspan="2"><textarea rows="2" style="width: 98%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_SPCS_ST" 
					data-validation-engine="validate[required]"></textarea></td>
				    <td width="100"><p align="center">联系电话 </p></td>
				    <td width="169"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width: 100%;font-size:14px;"
						name="VB_LXDH_ST" /></td>
				  </tr>
				  <tr>
				    <td width="100"><p align="center">联系人 </p></td>
				    <td width="169"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width: 100%;font-size:14px;"
						name="VB_LXR_ST" /></td>
				  </tr>
				  <tr>
				    <td width="127"><p align="center">电子邮箱 </p></td>
				    <td width="198" colspan="2"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width: 100%;font-size:14px;"
						name="VB_EMAIL_ST" /></td>
				    <td width="100"><p align="center">邮政编码 </p></td>
				    <td width="169"><input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" style="width: 100%;font-size:14px;"
						name="VB_YZBM_ST" /></td>
				  </tr>
				  <tr>
				    <td width="127"><p align="center">邮政地址 </p></td>
				    <td width="468" colspan="4"><textarea rows="2" style="width: 99%;font-size:14px; vertical-align:top; 
				    overflow: hidden;line-height: 36px;" onpropertychange="this.style.height=this.scrollHeight + 'px'"
					oninput="this.style.height=this.scrollHeight + 'px'" name="VB_YZDZ_ST" 
					data-validation-engine="validate[required]"></textarea></td>
				  </tr>
				</table>	
				<div style="display:line-block;line-height:10px;height:10px;"></div>
				 公示期: <input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GSKSSJ_ST" maxlength="10" font-size:14px;
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />至
						<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GSJSSJ_ST" maxlength="10" font-size:14px;
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>
				<br><br><br>
			</div>
			<div class="eui-word-bottom"></div>
		</div>
		<div class="eve_buttons">
		    <input value="保存" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
