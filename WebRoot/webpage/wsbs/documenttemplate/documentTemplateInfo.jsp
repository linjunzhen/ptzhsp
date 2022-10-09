<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DocumentTemplateForm", function(form, valid) {
			if (valid) {
				if (endEditing()){
					var rows = $("#paramConfGrid").datagrid("getRows");
					var datas = [];
					for(var i = 0;i<rows.length;i++){
						datas.push({
							"PARAM_NAME":rows[i].PARAM_NAME,
							"PARAM_VALUE":rows[i].PARAM_VALUE
						});
					}
					var PARAM_JSON = JSON.stringify(datas);
					$("input[name='PARAM_JSON']").val(PARAM_JSON);
				}else{
					alert("数据无效,请重新输入!");
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#DocumentTemplateForm").serialize();
				var url = $("#DocumentTemplateForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							parent.$("#DocumentTemplateGrid")
									.datagrid("reload");
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
		}, "T_WSBS_DOCUMENTTEMPLATE", null, $("input[name='DOCUMENT_ID']")
				.val());
		
		AppUtil.initUploadControl({
			file_types : "*.ftl;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "fltMater",
			busTableName : "T_WSBS_DOCUMENTTEMPLATE",
			uploadButtonId : "FTLFILE_ID_BTN",
			singleFileDivId : "FTLFILE_ID_DIV",
			singleFileId : "${documentTemplate.FTLFILE_ID}",
			singleFileFieldName : "FTLFILE_ID",
			limit_size : "20 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='FTLFILE_ID']").val(resultJson.fileId);
			}
		});
		
		$("#paramConfGrid").datagrid({
		    onDblClickRow: function(index, row){
		    	onClickRow(index);
		    }
		});
		

	});

	/**
	 * 查看配置文件
	 */
	function showDocumentTemplate() {
		//获取票单ID
		var DocumentTemplateId = $("input[name='DOCUMENT_ID']").val();
		if (!DocumentTemplateId) {
			art.dialog({
				content : '请先保存模版',
				icon : "error",
				time : 3,
				ok : true
			});
		} else {
			$.dialog.open(
					"documentTemplateController.do?showDocumentTemplate&entityId="
							+ DocumentTemplateId, {
						title : "查看模版配置",
						width : "100%",
						height : "100%",
						left : "0%",
						top : "0%",
						fixed : true,
						lock : true,
						resize : false
					}, false);
		}

	};

	/**
	 * 编辑配置文件
	 */
	function editDocumentTemplate() {
		//获取票单ID
		var DocumentTemplateId = $("input[name='DOCUMENT_ID']").val();
		if (!DocumentTemplateId) {
			art.dialog({
				content : '请先保存模版',
				icon : "error",
				time : 3,
				ok : true
			});
		} else {
			$.dialog.open(
					"documentTemplateController.do?editDocumentTemplate&entityId="
							+ DocumentTemplateId, {
						title : "模版配置",
						width : "100%",
						height : "100%",
						left : "0%",
						top : "0%",
						fixed : true,
						lock : true,
						resize : false
					}, false);
		}

	};
	/**  =======================      ==========================**/
	/**
	 *  编号选择器调用代码
	 */
	function showSelectSerialNumber() {
		var serialNumberIds = $("input[name='SERIAL_ID']").val();
		var serialNumberNames = $("input[name='SERIAL_NAME']").val();
		parent.$.dialog.open(
				"serialNumberController.do?selector&allowCount=1&serialNumberIds="
						+ serialNumberIds + "&serialNumberNames="
						+ serialNumberNames, {
					title : "选择编号配置",
					width : "600px",
					lock : true,
					resize : false,
					height : "500px",
					close : function() {
						var selectRoleInfo = art.dialog
								.data("selectSerialNumberInfo");
						if (selectRoleInfo) {
							$("input[name='SERIAL_ID']").val(
									selectRoleInfo.serialNumberIds);
							$("input[name='SERIAL_NAME']").val(
									selectRoleInfo.serialNumberNames);
						}
					}
				}, false);

	}
	//改变模板展示风格
	function changeWordOrHtml(o) {
		var selectVal = $(o).val();
		if (selectVal&&selectVal == 0) {
				$("#editTemplate").attr("style","");
				$("#showTemplate").attr("style","");
				$("#htmlUrlTr").attr("style","display:none;");
				$("#htmlToWordUrlTr").attr("style","display:none;");
		} else if(selectVal&&selectVal == 1){
			$("#editTemplate").attr("style","display:none;");
			$("#showTemplate").attr("style","display:none;");
			$("#htmlUrlTr").attr("style","");
			$("#htmlToWordUrlTr").attr("style","");
		}else{
			$("#editTemplate").attr("style","display:none;");
			$("#showTemplate").attr("style","display:none;");
			$("#htmlUrlTr").attr("style","display:none;");
			$("#htmlToWordUrlTr").attr("style","display:none;");
		}
	}
	var editIndex = undefined;
	/**
	 * 结束编辑模式
	 */
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($("#paramConfGrid").datagrid("validateRow", editIndex)){
			$("#paramConfGrid").datagrid("endEdit", editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$("#paramConfGrid").datagrid("selectRow", index)
						.datagrid("beginEdit", index);
				editIndex = index;
			} else {
				$("#paramConfGrid").datagrid("selectRow", editIndex);
			}
		}
	}
	
	function addParam(){
		if (endEditing()){
			$("#paramConfGrid").datagrid("appendRow",{});
			editIndex = $("#paramConfGrid").datagrid("getRows").length-1;
			$("#paramConfGrid").datagrid("selectRow", editIndex)
					.datagrid("beginEdit", editIndex);
		}
	}
	
	function delParam() {
		if (editIndex == undefined){
			alert("进入行编辑状态的时候才可以删除!");
			return;
		}
		$("#paramConfGrid").datagrid("cancelEdit", editIndex)
				.datagrid("deleteRow", editIndex);
		editIndex = undefined;
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DocumentTemplateForm" method="post"
		action="documentTemplateController.do?saveOrUpdate">
		<div id="DocumentTemplateFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="DOCUMENT_ID"
				value="${documentTemplate.DOCUMENT_ID}">
			<input type="hidden" name="PARAM_JSON" >
			<input type="hidden" id="fltId" name="FTLFILE_ID" val="${documentTemplate.FTLFILE_ID}" />
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr style="height:40px;">
					<td><span style="width: 130px;float:left;text-align:right;">模版名称：
					</span> <input type="text" style="width:350px;float:left;" maxlength="120"
						class="eve-input  validate[required] "
						value="${documentTemplate.DOCUMENT_NAME}" id="DOCUMENT_NAME"
						name="DOCUMENT_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">办件类型：</span>
						<eve:eveselect clazz="eve-input validate[required]"
							dataParams="documentType"
							value="${documentTemplate.DOCUMENT_TYPE}"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择模版类型" name="DOCUMENT_TYPE">
						</eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">是否为HTML模板：</span>
						<eve:eveselect clazz="eve-input validate[required]"
							dataParams="YesOrNo" onchange="changeWordOrHtml(this)"
							value="${documentTemplate.SFHTML}"
							dataInterface="dictionaryService.findDatasForSelect"
							name="SFHTML" defaultEmptyText="请选择是否HTML模板">
						</eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr id="htmlUrlTr" <c:if test="${documentTemplate.SFHTML!='1'}">style="display: none;"</c:if>>
					<td><span style="width: 130px;float:left;text-align:right;">HTML模板路径：</span>
						<input type="text" style="width:450px;float:left;" maxlength="126"
						class="eve-input validate[required]" value="${documentTemplate.HTML_URL}" name="HTML_URL" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr id="htmlToWordUrlTr" <c:if test="${documentTemplate.SFHTML!='1'}">style="display: none;"</c:if>>
					<td><span style="width: 130px;float:left;text-align:right;">生成WORD模板：</span>
						<a id="FTLFILE_ID_BTN"></a>
						<div id="FTLFILE_ID_DIV"></div>
					</td>
				</tr>
			</table>
			<div id="paramConfToolBar" style="width: 100%;">
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton"
									iconCls="icon-note-add" plain="true"
									onclick="addParam();">新增参数</a> 
								<a href="#"
									class="easyui-linkbutton"
									iconCls="icon-note-delete" plain="true"
									onclick="delParam();">删除参数</a>
									<font class="dddl_platform_html_requiredFlag">(过滤参数为HTML模板路径时进行选择是否配置)</font>
							</div>
						</div>
					</div>
				</div>
			</div>
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="paramConfGrid" fitcolumns="true" toolbar="#paramConfToolBar"
			checkonselect="false" style="width:100%;height:auto;"
			selectoncheck="false" fit="true" border="false" 
			data-options="autoSave:true,method:'post',url:'documentTemplateController.do?paramjson&formId=${documentTemplate.DOCUMENT_ID}'"
			>
				<thead>
					<tr>
						<th data-options="field:'PARAM_NAME',width:'100',align:'left',
						    editor:{
								type:'validatebox',options:{required:true,validType:'length[1,200]'}
							}
						"
						>参数名称</th>
						<th data-options="field:'PARAM_VALUE',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,200]'}}" width="100" >参数缺省值</th>
					</tr>
				</thead>
			</table>
		</div>

		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="配置模版" type="button" onclick="editDocumentTemplate();"
					id="editTemplate" class="z-dlg-button z-dialog-okbutton "
					<c:if test="${documentTemplate.SFHTML!='0'}">style="display: none;"</c:if> />
				<input value="查看模版" type="button"
					class="z-dlg-button z-dialog-cancelbutton" id="showTemplate"
					onclick="showDocumentTemplate();"
					<c:if test="${documentTemplate.SFHTML!='0'}">style="display: none;"</c:if> />
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
	
			</div>
		</div>
	</form>
</body>
