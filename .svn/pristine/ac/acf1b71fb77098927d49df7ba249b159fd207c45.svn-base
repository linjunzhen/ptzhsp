<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
 


<head>
<eve:resources
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>

<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<script type="text/javascript">
$(function(){

	$("select[name='linkName']").attr("style","width:150px;");
	$("input[name='link_man']").attr("style","width:150px;");

	AppUtil.initWindowForm("handUpLinkForm", function(form, valid) {
		   if (valid) {
		    //将提交按钮禁用,防止重复提交
		    $("input[name='sub']").attr("disabled", "disabled");
		    var linkId=$("#linkName").val();
    		if(linkId==null||linkId==""){
    			$.messager.alert('警告',"请选择挂起环节，谢谢！");
    			$("input[name='sub']").attr("disabled", false);
				return ; 
    		}
    		 infoLink();
		   }
		  }, "T_CKBS_NUMRECORD");
});
    function infoLink(){
    	var LinkId=$("#linkName").val();
    	var link_man_tel=$("#link_man_tel").val();
    	var link_man=$("#link_man").val();
    	art.dialog.data("linkInfo", {
    		linkmantel:link_man_tel,
    		linkman:link_man,
    		linkId:LinkId
		});// 存储
		AppUtil.closeLayer();
    }
    
    function selectLink(){
    	var linkId=$("#linkName").val();
    	if(linkId==''){
    		$("#LinkDayDiv").empty();
    		return;
    	}
    	url="flowTaskController.do?getLinkDay";
    	param="&linkId="+linkId;
    	AppUtil.ajaxProgress({
			url : url,
			params : param,
			callback : function(resultJson) {
				$("#LinkDayDiv").empty();
				if(resultJson.jsonString=='error'){
				   $("#LinkDayDiv").append("该特殊环节，挂起时限有问题，请联系中心技术处23122315").append("");
				   $("input[type='submit']").attr("disabled", "disabled");
				}else{
					$("#LinkDayDiv").append(resultJson.jsonString).append("");
				}
			}
		});
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="handUpLinkForm" >
		<!--==========隐藏域部分开始 ===========-->
	
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;"></td>
			</tr>
			<tr style="height:70px;">
				<td ><span style="width: 100px;float:left;text-align:right;">挂起节点
				<font class="dddl_platform_html_requiredFlag">*</font>：</span></td>
				<td ><eve:eveselect clazz="eve-input eve-select-width validate[required]" dataParams="${exeId}"
					 dataInterface="flowTaskService.findRestSpecialLink" value="" 
					  defaultEmptyText="==选择挂起节点==" onchange="selectLink();" id="linkName" name="linkName">
				</eve:eveselect>
				</td>
				<td ><span style="width: 60px;margin-left:20px;">该环节可挂起工作日：</span>
				</td>
				<td  style="width:100px;">
					<span style="width:50px;margin-left:8px;color:red;font-size=14px;" id="LinkDayDiv">0</span>
				</td>
			</tr>
			<tr style="height:30px;">
				<td ><span style="width: 100px;float:left;text-align:right;">执行人
				<font class="dddl_platform_html_requiredFlag">*</font>：</span></td>
				<td >
				<input type="text" id="link_man" name="link_man"
							class="eve-input validate[required,maxSize[6]]" maxlength="6" style="width:100px;" />
				</td>
				<td ><span style="width: 100px;float:left;text-align:right;">联系电话
				<font class="dddl_platform_html_requiredFlag">*</font>：</span></td>
				<td >
				<input type="text" id="link_man_tel" name="link_man_tel"
							class="eve-input validate[required,custom[mobilePhoneLoose]]" style="width:180px;" />
				</td>
			</tr>
		</table>
		<div class="eve_buttons">
			<input value="确定" type="submit" name="sub" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>
