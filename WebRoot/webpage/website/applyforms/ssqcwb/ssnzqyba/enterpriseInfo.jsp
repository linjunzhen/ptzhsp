<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
		var validateResult =$("#enterpriseInfoForm").validationEngine("validate");
		if(!validateResult){
			return false;
		}
    	var rows = $("#enterpriseGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		if(rows.length>0){  
			art.dialog.data("enterpriseInfo", rows);
			AppUtil.closeLayer();  
		}else{
			AppUtil.closeLayer();
		}    	
    	
    }
	
	$(function(){
		AppUtil.initWindowForm("enterpriseInfoForm", function(form, valid) {
			if (valid) {
				var qyname = $("[name='qyname']").val();
				var qycode = $("[name='qycode']").val();
				if(!qyname&& !qycode){					
					parent.art.dialog({
						content: "请输入查询参数！",
						icon:"error",
						ok: true
					});
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#enterpriseInfoForm").serialize();
				var url = $("#enterpriseInfoForm").attr("action");
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
							art.dialog.data("enterpriseInfo", resultJson.data);
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
							$("input[type='submit']").attr("disabled",false)
						}
					}
				});
			}
		}, "enterpriseInfoForm");
	});
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<form id="enterpriseInfoForm"  method="post" action="cancelController.do?getEnterpriseInfo">
		<div data-options="region:'center',split:false" >
				<table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
					<tr style="height:29px;">
						<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>查询参数(精确查询，必须填写一项。)</a></div></td>
					</tr>
						<td style="width:25%;text-align:right;">
							<span>企业名称：</span>
						</td>
						<td style="width:75%;">
							<input id="qyname" name="qyname" class="eve-input" type="text" style="width:80%;margin-top:8px;float:left;"/>
						</td>
					</tr>
					<tr>
						<td style="width:25%;text-align:right;">
							<span>统一社会信用代码：</span>
						</td>
						<td style="width:75%;">
							<input id="qycode" name="qycode" class="eve-input" type="text" style="width:80%;margin-top:8px;float:left;"/>
						</td>
					</tr>
				</table>	
		</div>
		
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
</form>
	
</body>

