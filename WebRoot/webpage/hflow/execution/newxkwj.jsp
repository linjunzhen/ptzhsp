<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>  
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("XKClassForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				var formData = $("#XKClassForm").serialize();
				var url = $("#XKClassForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
<%--							parent.art.dialog({--%>
<%--								content : resultJson.msg,--%>
<%--								icon : "succeed",--%>
<%--								time : 3,--%>
<%--								ok : true--%>
<%--							});--%>
							art.dialog.opener.addXKClass(resultJson.xkId,resultJson.xkfile_name);
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
		}, "T_WSBS_XK");
	});
	
	function selectChildDepartName(){
		var departId = $("input[name='xkdept_id']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=&noAuth=true", {
            title : "部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='xkdept_id']").val(selectDepInfo.departIds);
                    $("input[name='xkdept_name']").val(selectDepInfo.departNames);
                }
            }
        }, false);
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
	<form id="XKClassForm" method="post"
		action="commonXKController.do?saveCommonXK">
		<div id="XKClassFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ITEM_ID" value="${itemId}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
			<th colspan="4">办结结果</th>
		</tr>
		<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件编号：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td>
				<td><input type="hidden" name="RESULT_FILE_URL" >
				<input type="hidden" name="RESULT_FILE_ID">
				<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
					<input type="text" id="xkfile_num" name="xkfile_num"
							class="eve-input validate[required,maxSize[60]]" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件名称：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="xkfile_name" 
							class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
		</tr>
		
		
		<tr height="80px"> 
			<td ><span style="width: 125px;float:left;text-align:right;">（许可）内容：
			<font class="dddl_platform_html_requiredFlag">*</font>
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="5" cols="6"
					class="eve-textarea validate[required,maxSize[1500]]"
					maxlength="1600" style="width: 500px" name="xkcontent"></textarea>
			</td>
		</tr>
		
			</table>
		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

