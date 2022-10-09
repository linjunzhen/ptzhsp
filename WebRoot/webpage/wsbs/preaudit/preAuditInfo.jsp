<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<style type="text/css">
.select-width{
	width:530px;
}
</style>
<script type="text/javascript">


$(function() {
	AppUtil.initWindowForm("PreAuditInfoForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#PreAuditInfoForm").serialize();
			var url = $("#PreAuditInfoForm").attr("action");
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
						parent.$("#PreAuditGrid").datagrid("reload");
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
	},"T_WSBS_SERIALNUMBER",null, $("input[name='SERIAL_ID']").val());
	
	
});
function addparam(params){
	pos = getCursortPosition(document.getElementsByName('SERIAL_RULE')[0]);		
	s = $("input[name='SERIAL_RULE']").val();		
	$("input[name='SERIAL_RULE']").val(s.substring(0, pos)+params+s.substring(pos));
}

function getCursortPosition (ctrl) {//获取光标位置函数		
	var CaretPos = 0;	// IE Support		
	if (document.selection) {	
		ctrl.focus ();			
		var Sel = document.selection.createRange ();	
		Sel.moveStart ('character', -ctrl.value.length);			
		CaretPos = Sel.text.length;		
	}		// Firefox support		
	else if (ctrl.selectionStart || ctrl.selectionStart == '0')	
		CaretPos = ctrl.selectionStart;		
	return (CaretPos);	
}
/**
*  编号选择器调用代码
*/		
function showSelectRoles(){
		var serialNumberIds = $("input[name='SERIAL_IDS']").val();
		var serialNumberNames = $("input[name='SERIAL_NAMES']").val();
		parent.$.dialog.open("serialNumberController.do?selector&allowCount=1&serialNumberIds="+serialNumberIds+"&serialNumberNames="+serialNumberNames, {
			title : "选择编号配置",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectRoleInfo = art.dialog.data("selectSerialNumberInfo");
    			if(selectRoleInfo){
    				$("input[name='SERIAL_IDS']").val(selectRoleInfo.serialNumberIds);
        			$("input[name='SERIAL_NAMES']").val(selectRoleInfo.serialNumberNames);
    			}
    		}
    	}, false);
		
	}
function selectAuditUser(){
    	var userIds = $("input[name='PREAUDITER_IDS']").val();
    	parent.$.dialog.open("sysUserController.do?selector&allowCount=5&userIds="
				+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectUserInfo = art.dialog.data("selectUserInfo");
    			if(selectUserInfo&&selectUserInfo.userIds){
    				$("input[name='PREAUDITER_IDS']").val(selectUserInfo.userIds);
    				$("textarea[name='PREAUDITER_NAMES']").val(selectUserInfo.userNames);
    				art.dialog.removeData("selectUserInfo");
    			}
    		}
		}, false);
    }
 //增加为常用意见
function addCommonOpinion(){
	var opinionContent = $("textarea[name='opinionContent']").val();
	if(opinionContent.replace(/\s/g, "")==""){
		parent.art.dialog({
			content: "请填写您的意见!",
			icon:"error",
			ok: true
		});
	}else{
		url="commonOpinionController.do?saveCommonOpinion";
		formData="&BUSINESS_NAME=preAudit&BUSINESS_TYPE=preAudit&OPINION_CONTENT="+opinionContent;
		AppUtil.ajaxProgress({
			url : url,
			params : formData,
			callback : function(resultJson) {
				if(resultJson.success){
						$("select[name='OPINION_CONTENT']").find("option:first").remove(); 
						$("select[name='OPINION_CONTENT']").prepend("<option value='"+resultJson.jsonString+"'>"+opinionContent+"</option>"); 
						$("select[name='OPINION_CONTENT']").prepend("<option value=''>==选择常用意见==</option>"); 
						$("select[name='OPINION_CONTENT']").val(resultJson.jsonString); 
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
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
}
//删除选中的常用意见
function  removeCommonOpinion(){
	if($("select[name='OPINION_CONTENT']").children('option:selected').val()==""){
		parent.art.dialog({
			content: "请选择要删除的常用意见!",
			icon:"error",
			ok: true
		});
	}else{
		art.dialog.confirm("您确定要删除选择的常用意见吗?", function() {
			var opinionContent =$("select[name='OPINION_CONTENT']").children('option:selected').text();
			url="commonOpinionController.do?removeCommonOpinion";
			formData="&BUSINESS_NAME=preAudit&BUSINESS_TYPE=preAudit&OPINION_CONTENT="+opinionContent;
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if(resultJson.success){
						$("select[name='OPINION_CONTENT']").children("option:selected").remove(); 
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
					}else{
						parent.art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				}
			});
		});
	}
}

//选中常用意见时更换意见内容
function selectCommonOpinion(){
	var opinionContent = $("select[name='OPINION_CONTENT']").children('option:selected').text();
	if( $("select[name='OPINION_CONTENT']").children('option:selected').val()=="")opinionContent="";
	$("textarea[name='opinionContent']").val(opinionContent);
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="PreAuditInfoForm" method="post" action="serialNumberController.do?saveOrUpdate">
	    <div  id="SerialNumberFormDiv">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="CREATE_TIME" value="${serialNumber.CREATE_TIME}"> 
		    <input type="hidden" name="SSBMBM" value="${serialNumber.SSBMBM}"> 
		    <input type="hidden" name="INITSEQ" value="${serialNumber.INITSEQ}">
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
<tr style="height:29px;"><td colspan="2"></td></tr>
<tr style="height:80px;padding-top:3px;padding-bottom:6px;"><td><span style="width: 120px;float:left;text-align:right;">${title}意见：</span></td>
<td>
	<div style="margin-top:5px;margin-bottom:5px;">
	<eve:eveselect clazz="eve-input select-width" dataParams="preAudit,preAudit"
		 dataInterface="commonOpinionService.findCommonOpinionList" value="" defaultEmptyText="==选择常用意见==" onchange="selectCommonOpinion();" name="OPINION_CONTENT">
	</eve:eveselect>
	<span style="width:30px;display:inline-block;text-align:right;">
	<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="addCommonOpinion();" style="cursor:pointer;vertical-align:middle;" title="添加到常用意见">
	</span>
	<span style="width:30px;display:inline-block;text-align:right;">
	<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeCommonOpinion();" style="cursor:pointer;vertical-align:middle;" title="删除选中的常用意见">
	</span>
	</div>
	<div style="margin-top:5px;margin-bottom:5px;">
<textarea rows="5" cols="5" class="eve-textarea validate[maxSize[500]]"
									style="width: 600px" name="opinionContent"></textarea>
									</div>
</td>
</tr>
<tr style="height:40px;"><td><span style="width: 120px;float:left;text-align:right;">选择转办人员：</span></td>
<td>
	<textarea rows="5" cols="5" class="eve-textarea" readonly="readonly" onclick="selectAuditUser();"
									style="width: 400px" name="PREAUDITER_NAMES"></textarea>
	<input type="hidden" name="PREAUDITER_IDS"/>
</td>
</tr>
<tr style="height:40px;"><td><span style="width: 120px;float:left;text-align:right;">其他操作：</span></td>
<td>
	<div style="margin-top:5px;margin-bottom:5px;">
	<span style="width: 80px;float:left;text-align:right;">意见发布：</span><input type="checkbox" name="showOpinion" style="vertical-align:middle;" value="1" checked="checked"/>向申请人公开预审意见
	<span style="width:80px;display:inline-block;"></span>	<input type="checkbox" name="sendMsg"  style="vertical-align:middle;" value="1" checked="checked"/>向申请人发送短信通知
	</div>
	<div style="margin-bottom:5px;">
	<span style="width: 80px;float:left;text-align:right;">经办人：</span>
	<textarea rows="3" cols="5" class="eve-textarea" readonly="readonly" onclick="selectAuditUser();"
									style="width: 400px" name="PREAUDITER_NAMES1"></textarea>
	<input type="hidden" name="PREAUDITER_IDS1"/>
	</div>
</td>
</tr>
<tr style="height:40px;"><td><span style="width: 120px;float:left;text-align:right;">操作说明：</span></td>
<td>
	<div style="margin-top:5px;color:red;">
	1.操作说明1.。。。。。
	</div>
	<div style="margin-bottom:5px;color:red;">
	2.操作说明2.。。。。。
	</div>
</td>
</tr>
</table>
	</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
