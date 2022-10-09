<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,ztree,swfupload,artdialog"></eve:resources>
<script type="text/javascript">
	var editIndex = undefined;
	/**
	 * 结束编辑模式
	 */
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($("#searchConfigGrid").datagrid("validateRow", editIndex)) {
			var ed = $("#searchConfigGrid").datagrid("getEditor", {
				index : editIndex,
				field : 'queryCondition'
			});
			var DIC_NAME = $(ed.target).combobox("getText");
			$("#searchConfigGrid").datagrid("getRows")[editIndex]["DIC_NAME"] = DIC_NAME;
			$("#searchConfigGrid").datagrid("endEdit", editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 添加表信息
	 */
	function addQueryInfo() {
		if (endEditing()) {
			$("#searchConfigGrid").datagrid("appendRow", {});
			editIndex = $("#searchConfigGrid").datagrid("getRows").length - 1;
			$("#searchConfigGrid").datagrid("selectRow", editIndex).datagrid(
					"beginEdit", editIndex);
		}
	}

	function removeQueryInfo() {
		if (editIndex == undefined) {
			return
		}
		$("#searchConfigGrid").datagrid("cancelEdit", editIndex).datagrid(
				"deleteRow", editIndex);
		editIndex = undefined;
	}

	function confirmQueryInfo() {
		if (endEditing()) {
			$("#searchConfigGrid").datagrid("acceptChanges");
		}
	}

	function onClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$("#searchConfigGrid").datagrid("selectRow", index).datagrid(
						"beginEdit", index);
				editIndex = index;
			} else {
				$("#searchConfigGrid").datagrid("selectRow", editIndex);
			}
		}
	}

	$(function() {
		AppUtil.initWindowForm("ExcelConfigForm", function(form, valid) {
			if (valid) {
				var rows = $("#searchConfigGrid").datagrid("getRows");
				if (rows.length > 0) {
					var datas = [];
					for (var i = 0; i < rows.length; i++) {
						datas.push({
							"paramName" : rows[i].paramName,
							"queryCondition" : rows[i].queryCondition
						});
					}
					$("input[type='hidden'][name='SEARCH_CONFIG']").val(
							JSON.stringify(datas));
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#ExcelConfigForm").serialize();
				var url = $("#ExcelConfigForm").attr("action");
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
							parent.$("#ExcelConfigGrid").datagrid("reload");
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
		}, "T_MSJW_SYSTEM_EXCELCONFIG");

		/* AppUtil.initUploadControl({
			file_types : "*.xls;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "excelconfig",
			busTableName : "T_MSJW_SYSTEM_EXCELCONFIG",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "TPL_PATH_BTN",
			singleFileDivId : "TPL_PATH_DIV",
			singleFileId : $("input[name='TPL_PATH']").val(),
			singleFileFieldName : "TPL_PATH",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='TPL_PATH']").val(resultJson.fileId);
			}
		}); */

		var TPL_PATH= $("input[name='TPL_PATH']").val();
		$.post("executionController.do?getResultFiles&fileIds="+TPL_PATH,{}, function(resultJson) {
			 if(resultJson!="websessiontimeout"){
				$("#TPL_PATH_DIV").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\'TPL_PATH\');"><font color="red">删除</font></a>';
					newhtml+='</p>';
				} 
				$("#TPL_PATH_DIV").html(newhtml);
			 }
		});
	});
	
	function openExcelTplUploadDialog(){
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attach&busTableName=T_MSJW_SYSTEM_EXCELCONFIG', {
			title: "上传(附件)",
			width: "620px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						var fileType = 'xls';
						var attachType = 'attach';							
						$("input[name='TPL_PATH']").val(uploadedFileInfo.fileId);									
						var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
						+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
						+"&attachType="+attachType+"\" ";
						spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','TPL_PATH');\" ><font color=\"red\">删除</font></a></p>"
						$("#TPL_PATH_DIV").html(spanHtml); 
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function delUploadFile(fileId,id){
		//AppUtil.delUploadMater(fileId,attacheKey);
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			//移除目标span
			$("#"+fileId).remove();
			$("input[name='"+id+"']").val("");
		});
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ExcelConfigForm" method="post"
		action="excelConfigController.do?saveOrUpdate">
		<div id="ExcelConfigFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="CONFIG_ID"
				value="${excelConfig.CONFIG_ID}">
		     <input type="hidden" name="SEARCH_CONFIG"> 
		     <input
				type="hidden" name="CURLOGIN_USERID"
				value="${sessionScope.curLoginUser.userId}"> <input
				type="hidden" name="TPL_PATH" value="${excelConfig.TPL_PATH}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本配置</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">模版名称：</span>
						<input type="text" style="width:150px;float:left;" maxlength="60"
						class="eve-input validate[required]"
						value="${excelConfig.TPL_NAME}" name="TPL_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">模版KEY：</span>
						<c:if test="${excelConfig.CONFIG_ID!=null}">
				   ${excelConfig.TPL_KEY}
				</c:if> <c:if test="${excelConfig.CONFIG_ID==null}">
							<input type="text" style="width:150px;float:left;" maxlength="28"
								class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
								value="${excelConfig.TPL_KEY}" id="TPL_KEY" name="TPL_KEY" />
							<font class="dddl_platform_html_requiredFlag">*</font>
						</c:if></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">写入的起始行：</span>
						<input type="text" style="width:150px;float:left;" maxlength="18"
						class="easyui-numberbox validate[required]"
						value="${excelConfig.START_ROW}" precision="0" name="START_ROW" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">写入的起始列：</span>
						<input type="text" style="width:150px;float:left;" maxlength="18"
						class="easyui-numberbox validate[required]"
						value="${excelConfig.START_COL}" precision="0" name="START_COL" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>其他配置</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">模版路径：</span>
						<!-- <a id="TPL_PATH_BTN"></a> -->						
						<a href="javascript:void(0);" onclick="openExcelTplUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="TPL_PATH_DIV"></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">排序条件：</span>
						<input type="text" style="width:250px;float:left;" maxlength="252"
						class="eve-input" value="${excelConfig.ORDER_CONDITION}"
						name="ORDER_CONDITION" /></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">配置的SQL语句：</span>
						<textarea rows="5" cols="5"
							class="eve-textarea validate[required]" style="width: 400px"
							name="SQL_CONTENT">${excelConfig.SQL_CONTENT}</textarea><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>

			<div id="searchConfigToolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-note-add"
					plain="true" onclick="addQueryInfo();">新建</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-note-delete" plain="true"
					onclick="removeQueryInfo();">删除</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-save" plain="true"
					onclick="confirmQueryInfo();">确定</a>
			</div>
			<table toolbar="#searchConfigToolbar" id="searchConfigGrid"
				class="easyui-datagrid" title="查询条件配置"
				style="width:100%;height:350px;"
				data-options="singleSelect:true,url:'excelConfigController.do?querys&configId=${excelConfig.CONFIG_ID}',method:'post',onClickRow: onClickRow">
				<thead>
					<tr>
						<th
							data-options="field:'paramName',width:'70%',align:'left',editor:'textbox'">参数名称</th>
						<th
							data-options="field:'queryCondition',width:'30%',align:'left',
			    editor:{
					type:'combobox',
					options:{
					    editable:false,
						valueField:'DIC_CODE',
						textField:'DIC_NAME',
						url:'dictionaryController.do?load&typeCode=QueryRule',
						required:true
					}
				}
			">查询条件</th>
					</tr>
				</thead>
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

