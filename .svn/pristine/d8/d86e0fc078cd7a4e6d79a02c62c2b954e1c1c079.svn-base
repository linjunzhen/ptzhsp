<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("CertificateTemplateForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#CertificateTemplateForm").serialize();
			var url = $("#CertificateTemplateForm").attr("action");
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
						parent.$("#CertificateTemplateGrid").datagrid("reload");
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
	},"T_WSBS_Certificate",null, $("input[name='CERTIFICATE_ID']").val());
	
	
	
});

/**
 * 查看配置文件
 */
function showCertificateTemplate() {
    //获取票单ID
    var CertificateTemplateId = $("input[name='CERTIFICATE_ID']").val();
    if(!CertificateTemplateId){
        art.dialog({
            content: '请先保存权证模板信息',
            icon:"error",
            time:3,
            ok: true
        });
    }else{
        $.dialog.open("certificateTemplateController.do?showCertificateTemplate&entityId="+CertificateTemplateId, {
            title : "查看权证模板配置",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
        }, false);
    }
    
};

/**
 * 编辑配置文件
 */
function editCertificateTemplate() {
    //获取票单ID
    var CertificateTemplateId = $("input[name='CERTIFICATE_ID']").val();
    if(!CertificateTemplateId){
        art.dialog({
            content: '请先保存权证模板信息',
            icon:"error",
            time:3,
            ok: true
        });
    }else{
        $.dialog.open("certificateTemplateController.do?editCertificateTemplate&entityId="+CertificateTemplateId, {
        	title : "权证模板配置",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
        }, false);
    }
};
/**
* 限制状态选择器
**/
function showLimitStatusSelect(){
	var url = "certificateTemplateController/limitStatusSelector.do";
	$.dialog.open(url, {
		title : "限制状态选择器",
		width:"760px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectLimitStatusInfo = art.dialog.data("selectLimitStatusInfo");
			if(selectLimitStatusInfo){
				$("[name='LIMIT_STATUS_NAME']").val(selectLimitStatusInfo.readNames);
				$("[name='LIMIT_STATUS']").val(selectLimitStatusInfo.readCodes);
				art.dialog.removeData("selectLimitStatusInfo");
			}
		}
	}, false);
};
/**
* 限制环节选择器
**/
function showLimitNodeNameSelect(){
	var url = "certificateTemplateController/limitNodeNameSelector.do";
	$.dialog.open(url, {
		title : "限制状态选择器",
		width:"760px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectLimitStatusInfo = art.dialog.data("selectLimitNodeNameInfo");
			if(selectLimitStatusInfo){
				$("[name='LIMIT_NODENAME']").val(selectLimitStatusInfo.readNames);
				//$("[name='LIMIT_NODENAME']").val(selectLimitStatusInfo.readCodes);
				art.dialog.removeData("selectLimitNodeNameInfo");
			}
		}
	}, false);
};

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="CertificateTemplateForm" method="post" action="certificateTemplateController.do?saveOrUpdate">
	    <div  id="CertificateTemplateFormDiv" data-options="region:'center',split:true,border:false">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="CERTIFICATE_ID" value="${certificateTemplate.CERTIFICATE_ID}"> 
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
		    <tr style="height:29px;">
		    	<td colspan="1" class="dddl-legend"  style="font-weight:bold;" >基本信息</td>
		    </tr>
			<tr style="height:40px;">
				<td><span style="width: 130px;float:left;text-align:right;">权证模板名称
					：</span>
					<input type="text" style="width:350px;float:left;" maxlength="120"  
					class="eve-input  validate[required] "  value="${certificateTemplate.CERTIFICATE_NAME}" 
					id="CERTIFICATE_NAME"  name="CERTIFICATE_NAME" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr style="height:40px;">
				<td><span style="width: 130px;float:left;text-align:right;">模板编码
				        ：</span>
					<input type="text" style="width:350px;float:left;" maxlength="120" 
					class="eve-input  validate[required] "  value="${certificateTemplate.ALIAS}"
					id="ALIAS"  name=ALIAS /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr style="height:40px;">
				<td><span style="width: 130px;float:left;text-align:right;">环节限制：</span>
					<input type="text" style="width:350px;float:left;" maxlength="120"  
					class="eve-input  validate[] "  
					readonly="readonly" onclick="showLimitNodeNameSelect();" placeholder="打印模板不需出现的环节"
					value="${certificateTemplate.LIMIT_NODENAME}" 
					id="LIMIT_NODENAME"  name="LIMIT_NODENAME" />
					<a href="javascript:showLimitNodeNameSelect();" class="btn1">选 择</a>
				</td>
			</tr>
			<tr style="height:40px;">
				<td><span style="width: 130px;float:left;text-align:right;">状态限制：</span>
					<input type="text" style="width:350px;float:left;" maxlength="120"  
					class="eve-input  validate[] "  
					readonly="readonly" onclick="showLimitStatusSelect();" placeholder="打印模板不需出现的状态"
					value="${certificateTemplate.LIMIT_STATUS_NAME}" 
					id="LIMIT_STATUS_NAME"  name="LIMIT_STATUS_NAME" />
					<input type="hidden" name="LIMIT_STATUS" value="${certificateTemplate.LIMIT_STATUS}">
					<a href="javascript:showLimitStatusSelect();" class="btn1">选 择</a>
				</td>
			</tr>

</table>

</div>

		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="配置模板" type="button" onclick="editCertificateTemplate();" class="z-dlg-button z-dialog-okbutton " />
			    <input value="查看模板" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="showCertificateTemplate();"/>
			    <input value="确定" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
