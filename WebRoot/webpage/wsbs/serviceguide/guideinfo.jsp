
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,artdialog"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	function formatFileDel(val, row) {
		var FILE_ID = row.FILE_ID;
		if (FILE_ID && FILE_ID != "0") {
			var uploadTableId = "ServiceGuideAttach";
			var uploadButtonId = "ServiceGuideAttach_BTN";
			return "<a href='#' onclick='AppUtil.removeUploadFile(\"" + FILE_ID
					+ "\",null,null,\"" + uploadTableId + "\",\""
					+ uploadButtonId + "\");' >" + "删除</a>";
		}
	}

	function formatFileName(val, row) {
		var FILE_ID = row.FILE_ID;
		if (FILE_ID && FILE_ID != "0") {
			var uploadTableId = "ServiceGuideAttach";
			var uploadButtonId = "ServiceGuideAttach_BTN";
			return "<a href='#' onclick='AppUtil.downLoadFile(\"" + FILE_ID
					+ "\");' ><font color='blue'>" + row.FILE_NAME
					+ "</font></a>";
		}
	}

	function formatFileProgress(val, row) {
		var FILE_ID = row.FILE_ID;
		if (FILE_ID && FILE_ID != "0") {
			return "上传完毕";
		}
	}
	
	function submitInfo(){
		var validateResult =$("#ServiceGuideForm").validationEngine("validate");
		if(validateResult){
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			//先获取表单上的所有值
		    var datas = FlowUtil.getFormEleData("ServiceGuideForm");
			var url = $("#ServiceGuideForm").attr("action");
			var attachFileIds = AppUtil.getAttachFileIds("ServiceGuideAttach");
			datas.attachFileIds = attachFileIds;
			var formData = $.param(datas);
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
						parent.$("#ServiceGuideGrid").datagrid("reload");
						AppUtil.closeLayer();
					} else {
						$("input[type='submit']").removeAttr("disabled");
						parent.art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}else{
			$("#ServiceGuideTab").tabs("select",0);
		}
	}
	
	function showSelectTYPE_IDTree() {
		var treeObj = $("#TYPE_NAME");
		var treeOffset = $("#TYPE_NAME").offset();
		$("#TYPE_NAMETreeContent").css({
					left : (treeOffset.left) + "px",
					top : (treeOffset.top + treeObj.outerHeight()) + "px"
				}).slideDown("fast");
		$("body").bind("mousedown", onTYPE_IDTreeBodyDown);
	}

	function onSelectTYPE_IDTreeClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("TYPE_NAMETree");
		var selectNode = zTree.getSelectedNodes()[0];
		$("#TYPE_NAME").attr("value",selectNode.name);
		hideTYPE_IDSelectTree();
	}

	function onTYPE_IDTreeBodyDown(event) {
		if (!(event.target.id == "TYPE_NAME" || event.target.id == "TYPE_NAMETreeContent" || $(event.target)
				.parents("#TYPE_NAMETreeContent").length > 0)) {
			hideTYPE_IDSelectTree();
		}
	}

	function hideTYPE_IDSelectTree() {
		$("#TYPE_NAMETreeContent").fadeOut("fast");
		$("body").unbind("mousedown", onTYPE_IDTreeBodyDown);
	}
	
	var TYPE_IDSetting = {
		view : {
			dblClickExpand : false,
			selectedMulti: false
		},
		async : {
			enable : true,
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_WSBS_BUSTYPE",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "PARENT_ID,PATH",
				"rootParentId" : "0",
				"dicTypeCode":"",
				"isShowRoot" : "false",
				"rootName":"业务类别"
			}
		},
		callback : {
			onClick : onSelectTYPE_IDTreeClick
		}
	};

	$(function() {
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topLeft";
		$.validationEngine.defaults.autoHideDelay = "3000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		//初始化上传控件
		AppUtil.initUploadControl({
			file_types : "*.doc;*.docx;*.jpg;*.jpeg;*.xls;*.xlsx",
			file_upload_limit : 8,
			file_queue_limit : 8,
			uploadPath : "wsbs",
			busRecordId:$("input[name='GUIDE_ID']").val(),
			busTableName : "T_WSBS_SERVICEGUIDE",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "ServiceGuideAttach_BTN",
			uploadTableId : "ServiceGuideAttach",
			limit_size : "10"
		});
		//初始化下拉树
		$.fn.zTree.init($("#TYPE_NAMETree"),TYPE_IDSetting);

	});
</script>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true" >
		<div data-options="region:'center'" >
			<div class="easyui-tabs eve-tabs" id="ServiceGuideTab" fit="true">
				<div title="办事指南信息">
					<form id="ServiceGuideForm" method="post"
						action="serviceGuideController.do?saveOrUpdate">
						<!--==========隐藏域部分开始 ===========-->
						<%-- <input type="hidden" name="CURLOGIN_USERID" value="${session1111Scope.curLoginUser.userId}">
						--%> 
						<input type="hidden" name="GUIDE_ID" value="${serviceGuide.GUIDE_ID}">
						<input type="hidden" name="TYPE_ID" value="${serviceGuide.TYPE_ID}">

						<!--==========隐藏域部分结束 ===========-->
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr style="height:29px;">
								<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本配置</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">项目名称：</span>
									<input type="text" style="width:250px;float:left;"
									maxlength="62" class="eve-input validate[required]"
									value="${serviceGuide.PROJECT_NAME}" name="PROJECT_NAME" /><font
									class="dddl_platform_html_requiredFlag">*</font></td>
								<td><span style="width: 100px;float:left;text-align:right;">项目编码：</span>
								    <c:if test="${serviceGuide.PROJECT_CODE!=null}">
					   					${serviceGuide.PROJECT_CODE}
									</c:if>
									<c:if test="${serviceGuide.PROJECT_CODE==null}">
									<input type="text" style="width:250px;float:left;"
									maxlength="14" class="eve-input validate[custom[onlyLetterNumber]]"
									value="${serviceGuide.PROJECT_CODE}" id="PROJECT_CODE"
									name="PROJECT_CODE" />
									</c:if>
								</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">服务对象：</span>
								     <eve:excheckbox dataInterface="dictionaryService.findDatasForSelect" name="SERVER_OBJECTS" width="250px;"
								       clazz="validate[minCheckbox[1]] checkbox" dataParams="HandleServerObject"
								      value="${serviceGuide.SERVER_OBJECTS}">
								     </eve:excheckbox>
									<font class="dddl_platform_html_requiredFlag">*</font></td>
								<td><span style="width: 100px;float:left;text-align:right;">办理时限说明：</span>
									<input type="text" style="width:250px;float:left;"
									maxlength="62" class="eve-input"
									value="${serviceGuide.HANDLE_LIMITDESC}"
									name="HANDLE_LIMITDESC" /></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">办理工作日限制：</span>
									<input type="text" style="width:250px;float:left;"
									maxlength="18" class="easyui-numberbox"
									value="${serviceGuide.WORK_LIMIT}" precision="0"
									name="WORK_LIMIT" /></td>
								<td><span style="width: 100px;float:left;text-align:right;">是否有办事指南：</span>
								    <eve:eveselect clazz="eve-input validate[required]" dataParams="WSBSISBSZN"
								         dataInterface="dictionaryService.findDatasForSelect" value="${serviceGuide.IS_BSZN}"
								         name="IS_BSZN">
								    </eve:eveselect>
								   <font class="dddl_platform_html_requiredFlag">*</font></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">业务类别：</span>
									<input type="text" style="width:250px;float:left;" id="TYPE_NAME"
									class="eve-input validate[required]" readonly="readonly"
									onclick="showSelectTYPE_IDTree();"
									value="${serviceGuide.TYPE_NAME}" name="TYPE_NAME" />
									<font class="dddl_platform_html_requiredFlag">*</font></td>
							</tr>
						</table>
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr style="height:29px;">
								<td colspan="1" class="dddl-legend" style="font-weight:bold;">其他配置</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">数量限制：</span>
									<input type="text" style="width:400px;float:left;"
									maxlength="30" class="eve-input"
									value="${serviceGuide.NUM_LIMIT}" name="NUM_LIMIT" /></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">办理机构(科室)：</span>
									<input type="text" style="width:400px;float:left;"
									maxlength="126" class="eve-input" 
									value="${serviceGuide.HANDLE_DEPT}" name="HANDLE_DEPT" /></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">办理地址：</span>
									<input type="text" style="width:400px;float:left;"
									maxlength="126" class="eve-input"
									value="${serviceGuide.HANDLE_ADDRESS}" name="HANDLE_ADDRESS" /></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">办理时间：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="HANDLE_TIMEDESC">${serviceGuide.HANDLE_TIMEDESC}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">在线申报URL：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="HANDLE_URL">${serviceGuide.HANDLE_URL}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">联系电话：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="LXDH">${serviceGuide.LXDH}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">监督电话：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="JDDH">${serviceGuide.JDDH}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">办事依据：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="BSYJ">${serviceGuide.BSYJ}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">收费标准及依据：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="SFBZJYJ">${serviceGuide.SFBZJYJ}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">申请条件：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="SQTJ">${serviceGuide.SQTJ}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">申请材料：</span>
									<textarea rows="10" cols="5" class="eve-textarea"
										style="width: 400px" name="SQCL">${serviceGuide.SQCL}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">经办流程：</span>
									<textarea rows="10" cols="5" class="eve-textarea"
										style="width: 400px" name="JBLC">${serviceGuide.JBLC}</textarea></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">备注：</span>
									<textarea rows="5" cols="5" class="eve-textarea"
										style="width: 400px" name="BZ">${serviceGuide.BZ}</textarea></td>
							</tr>
						</table>
					</form>
				    
				    <div class="treeContent eve-combotree" style="display:none; position: absolute;" id="TYPE_NAMETreeContent" >
					<ul class="ztree" style="margin-top:0; width:160px;height: 150px" id="TYPE_NAMETree" ></ul></div>
				</div>
				<div title="表格上传">
					<div id="uploadTableToolId">
						<span style="float:left;"><a id="ServiceGuideAttach_BTN"></a><font
							color="red">*最多只能上传8个文件</font></span>
					</div>
					<table id="ServiceGuideAttach" class="easyui-datagrid"
						style="width:97%;height:250px;" checkOnSelect="false"
						selectOnCheck="false" idField="SWF_FILEID"
						url="fileAttachController.do?datagrid&BUS_TABLERECORDID=${serviceGuide.GUIDE_ID}&BUS_TABLENAME=T_WSBS_SERVICEGUIDE"
						data-options="rownumbers:true,singleSelect:true">
						<thead>
							<tr>
								<th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th>
								<th data-options="field:'SWF_FILEID',hidden:true" width="80">SWF_FILEID</th>
								<th field="FILE_NAME" width="37%" formatter="formatFileName">文件名</th>
								<th field="FILE_SIZE" width="10%">大小</th>
								<th field="FILE_TYPE" width="10%">类型</th>
								<th field="fileprogress" width="25%"
									formatter="formatFileProgress">状态</th>
								<th field="FILE_DELETE" width="10%" formatter="formatFileDel">删除</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>

		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" >
				<input value="确定" type="submit" onclick="submitInfo();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>
	
</body>

