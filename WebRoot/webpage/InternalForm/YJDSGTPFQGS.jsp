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
	$(function(){
	    
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			WordUtil.initFormFieldValue(dataObj.variableMap,"YJDSGTPFQGS");
    		}
    	}
    	
		
		
		AppUtil.initWindowForm("YJDSGTPFQGS", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("YJDSGTPFQGS");
				var url = $("#YJDSGTPFQGS").attr("action");
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
<style type="text/css">
table{ border-collapse:collapse; }
td{ border:1px solid #000;}
</style>
</head>

<body>
	<form id="YJDSGTPFQGS" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-title">
					<input type="text" class="eui-ipt" data-validation-engine="validate[required]" style="min-width: 200px;" 
					 name="VB_CGLSUBJECT_ST" maxlength="200" value="${SQData.blxmmc}" />项目<br />联合竣工验收批复前公示
				</div><br/>
				<div class="eio-word-content" style="text-indent:3px; ">	
					<table style="width: 100%;border:1px solid black;border-collapse:collapse;" cellpadding="0" cellspacing="0">
						<tr>
							<td style="width:126px;"><span>事项名称</span></td>
							<td colspan="3">
								<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:96%;" 
					 				name="VB_CGLSERVICENAME_ST" maxlength="200" />
							</td>
						</tr> 
						<tr>
							<td style="width:126px;"><span>申报单位<span></td>
							<td>
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLSQDEPT_ST" maxlength="200" />
							</td>
							<td style="width:126px;">建设单位</td>
							<td>
								<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLJSDEPT_ST" maxlength="200" />
					 		</td>
						</tr>
						<tr>
							<td>建设地点</td>
							<td>
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLADDRESS_ST" maxlength="200" />
							</td>
							<td>建设期限</td>
							<td>
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLTERM_ST" maxlength="200" />
							</td>
						</tr>
						<tr>
							<td>总投资估算</td>
							<td>
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLMATE_ST" maxlength="200" />
							</td>
							<td>资金来源</td>
							<td>
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLSOURCE_ST" maxlength="200" />
							</td>
						</tr>
						<tr>
							<td>建设规模及主要内容</td>
							<td colspan="3">
							<textarea rows="5" data-validation-engine="validate[required],maxsize[500]"
							 style="width: 96%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_CGLCONTENT_ST" ></textarea>
							</td>
						</tr>
						<tr>
							<td rowspan="2">审批处室</td>
							<td rowspan="2">
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLAUDITDEPT_ST" maxlength="200" />
							</td>
							<td>联系电话</td>
							<td>
								<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLLXDH_ST" maxlength="200" />
					 		</td>
						</tr>
						<tr>
							<td>联系人</td>
							<td>
								<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLLXR_ST" maxlength="200" />
					 		</td>
						</tr>
						<tr>
							<td>电子邮箱</td>
							<td>
							<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLEMAIL_ST" maxlength="200" />
							</td>
							<td>邮政编码</td>
							<td>
								<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:173px" 
					 				name="VB_CGLCODE_ST" maxlength="200" />
					 		</td>
						</tr>
						<tr>
							<td>邮政地址</td>
							<td colspan="3">
							<div>
							<textarea rows="5" class="eui-ipt1" data-validation-engine="validate[required]" 
							style="width: 96%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;border: none;" 
					  		   onpropertychange="this.style.height=this.scrollHeight + 'px'" 
					  		   oninput="this.style.height=this.scrollHeight + 'px'"
					  			name="VB_CGLPOST_ADDRESS_ST" maxlength="200" ></textarea>
							</td>
						</tr>   
					</table>
				</div>
				
				<div class="eui-word-notes clearfix"><br/>
					&nbsp;&nbsp;本公示的期限为
					<input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;"
					 name="VB_BEGIN_TIME_ST" maxlength="10" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>至
					 <input type="text" class="eui-ipt1 laydate-icon" data-validation-engine="validate[required]" style="width:150px;height: 36px;"
					 name="VB_END_TIME_ST" maxlength="10" readonly="readonly" onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})"/>。
					公民、法人和其他经济组织在公示期限内对本行政审批事项直接涉及自身重大利益或者自身与申请人重大利益的，可依法向我局书面陈述、申辩；
					对本行政审批事项内容有其他意见建议的，也可向我局提出
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
