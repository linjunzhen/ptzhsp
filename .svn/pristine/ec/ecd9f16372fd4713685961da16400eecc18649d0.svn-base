<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		$("#OPER_REMARK_SQR").focus();
		AppUtil.initWindowForm("DealForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#DealForm").serialize();
				var url = $("#DealForm").attr("action");
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
		},null);
	});
	function setOperRemarkSqR(){
		$("input[name='OPER_REMARK_SQR']").val("${record.LINE_NAME}");
	}
	function selChange(){
		var pid=$("#pid").val();
        if(pid==3||pid==4){
        	document.getElementById("ckgzd").disabled = false;
        }else{
        	document.getElementById("ckgzd").disabled = true;
        }
	}
	
	function showTempPrint(TemplatePath,TemplateName){
        var  sq=$("#OPER_REMARK_SQR").val();
        if(sq==null||sq==''){
        	$("#OPER_REMARK_SQR").focus();
        	return false;
        }
        var itemCode = '${detail.ITEM_CODE}';
        if(null!=itemCode){
		    	var dateStr = "";
				var OPER_REMARK_SQR=$("#OPER_REMARK_SQR").val();
				dateStr +="&OPER_REMARK_SQR="+OPER_REMARK_SQR;
				dateStr +="&TemplatePath="+TemplatePath;
				dateStr +="&TemplateName="+TemplateName;
				dateStr +="&itemCode="+itemCode;
				 $.dialog.open(encodeURI("printAttachController.do?printBack"+dateStr), {
		                title : "打印模版",
		                width: "100%",
		                height: "100%",
		                left: "0%",
		                top: "0%",
		                fixed: true,
		                lock : true,
		                resize : false
		        }, false);
		   
		}
		
	}
	
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DealForm" method="post" action="newCallController.do?yctbQueuingDeal">
	    <div id="DealFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="DETAIL_ID" value="${detail.DETAIL_ID}">
		    <input type="hidden" name="RECORD_ID" value="${record.RECORD_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<!-- <tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">处理信息</td>
				</tr> -->
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">处理类型：</span>
						<select class="tab_text_wspy1 validate[required]" defaultemptytext="请选择处理类型"  onchange="selChange()" id="pid"  name="DETAIL_STATUS">
							<option selected="selected" value="3">领照(核验办结)</option>
						</select> <font class="dddl_platform_html_requiredFlag">*</font>
						 <input value="打印送达通知书"    onclick="showTempPrint('20160112105638.doc','送达通知书')"  style="margin-left:6px;"
						  type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight"  id="ckgzd"/>
						</td>
					
				</tr>				
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;" >申请人类型：</span>
						<input type="radio" onclick = "setOperRemarkSqR();" name="BSYHLX" value="1" >个人
						<input type="radio" name="BSYHLX" value="2"  checked="checked" >企业
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">申请人/单位：</span>
						<input type="text" class="eve-input validate[required]" max="30" name="OPER_REMARK_SQR" id="OPER_REMARK_SQR" >
						</select> <font class="dddl_platform_html_requiredFlag">*企业咨询请填写企业名称</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">备注：</span>
						<textarea rows="5" cols="50" name="OPER_REMARK" class="validate[[],maxSize[200]]"></textarea>
						
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
