<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine,swfupload"></eve:resources>
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DjInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#DjInfoForm").serialize();
				var url = $("#DjInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.$("#bgxxGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_YBQLC_GWYWZBBG");
		

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DjInfoForm" method="post"
		action="YbGwybzshController.do?saveOrUpdate">
		<div id="DjInfoFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="BG_YWHs" value="${data.BG_YWH}"/>
	
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="3" class="dddl-legend"><div class="eui-dddltit"><a>公务员危重病登记</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:right;">
					<font class="dddl_platform_html_requiredFlag">*</font>证件号码：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_HM}"
					name="BG_HM" />
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">姓名：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_XM}"
					name="BG_XM" />
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">工作状态：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams=""
						dataInterface="dictionaryService.findDatasForSelect" value="${data.BG_GZZT}" 
						defaultEmptyText="==选择工作状态==" name="BG_GZZT"></eve:eveselect>
				</td>		
            </tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:right;">个人保险号：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_BXH}"
					name="BG_BXH" />
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">单位保险号：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_DWBXH}"
					name="BG_DWBXH" />
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">分中心：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams=""
						dataInterface="dictionaryService.findDatasForSelect" value="${data.BG_FZX}" 
						defaultEmptyText="==选择分中心==" name="BG_FZX"></eve:eveselect>
				</td>		
            </tr>
            <tr>
				<td><span style="width: 130px;float:left;text-align:right;">单位名称：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_DWMC}"
					name="BG_DWMC" />
				</td>
				<td colspan="4"><span style="width: 130px;float:left;text-align:right;">特殊人员类别：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams=""
						dataInterface="dictionaryService.findDatasForSelect" value="${data.BG_RYLB}" 
						defaultEmptyText="==选择特殊人员类别==" name="BG_RYLB"></eve:eveselect>
				</td>		
            </tr>
            <tr>
            	<td><span style="width: 130px;float:left;text-align:right;"><font
					class="dddl_platform_html_requiredFlag">*</font>危病种编码：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams=""
						dataInterface="dictionaryService.findDatasForSelect" value="${data.BG_WBZBM}" 
						defaultEmptyText="==选择分中心==" name="BG_WBZBM"></eve:eveselect>
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">银行户名：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_YHHM}"
					name="BG_YHHM" />
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">银行账户：</span>
					<input type="text" style="width:186px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_YHZH}"
					name="BG_YHZH" />
				</td>			
            </tr>  
            <tr>
				<td><span style="width: 130px;float:left;text-align:right;"><font
					class="dddl_platform_html_requiredFlag">*</font>起始时间：</span>
					<input type="text" style="width:186px;float:left; height: 24px; line-height: 24px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZS_VALIDITY\')}'})" 
					 name="BG_QSNY" id="BG_QSNY"  value="${data.BG_QSNY}">
			   </td>
			   <td colspan="2">
				<span style="width: 130px;float:left;text-align:center;">截止时间</span>
					<input type="text" style="width:186px;float:left; height: 24px; line-height: 24px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'CXZS_TIME\')}'})" 
					 name="BG_ZZNY" id="BG_ZZNY"  value="${data.BG_ZZNY}">	
			   </td>	
			</tr> 
			<tr>
				<td colspan="3"><span style="width: 130px;float:left;text-align:right;">备注：</span>
					<input type="text" style="width:400px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${data.BG_BZ}"
					name="BG_BZ" />
				</td>
			</tr>         
		</table>
		</div>
		
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

