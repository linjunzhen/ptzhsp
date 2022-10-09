<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>

	<style type="text/css">
		/*****增加CSS****/
		.btnOneP {
			background: #2da2f2 none repeat scroll 0 0;
			color: #fff;
			display: inline-block;
			font-weight: bold;
			height: 27px;
			line-height: 27px;
			margin-left: 10px;
			text-align: center;
			width: 107px;
		}

	</style>
	<script type="text/javascript">
        /**
         * 电子证照名称选择器
         **/
        function showSelectEvidence(){
            var induIds = $("input[name='META_IDS']").val();
            var url = "domesticControllerController/evidenceSelector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
                +"checkCascadeN=&ISTZINDUSTRY=-1";
            $.dialog.open(url, {
                title : "电子证照名称选择器",
                width:"100%",
                lock: true,
                resize:false,
                height:"100%",
                close: function () {
                    var selectInduInfo = art.dialog.data("selectInduInfo");
                    if(selectInduInfo){
                        $("[name='META_IDS']").val(selectInduInfo.induIds);
                        $("[name='LICENCE_NAME']").val(selectInduInfo.induNames);
                        $("[name='LICENCE_CODE']").val(selectInduInfo.induCodes);
                        art.dialog.removeData("selectInduInfo");
                    }
                }
            }, false);
        };

	</script>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("ApplyMaterForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				/*var formData = $("#ApplyMaterForm").serialize();*/
				var formData = FlowUtil.getFormEleData("ApplyMaterForm");
				var url = $("#ApplyMaterForm").attr("action");
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
							parent.$("#ApplyMaterGrid").datagrid("reload");
							var entityId = $("input[name='MATER_ID']").val();
							if (entityId == null || entityId == "" || entityId == "undefined") {
								art.dialog.opener.showApplyMaterWindow();
							}
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
		}, "T_WSBS_APPLYMATER");

		/*限制材料分类样式*/
		$("select[name='MATER_XZ']").attr("style", "width:150px");

		$("#MATERSSYW").combobox('setValues', '${applyMater.MATER_SSYW }'.split(','));
		
		var PDFFORM_PATH= $("input[name='PDFFORM_PATH']").val();
		$.post("executionController.do?getResultFiles&fileIds="+PDFFORM_PATH,{}, function(resultJson) {
			 if(resultJson!="websessiontimeout"){
				$("#PDFFORM_PATH_DIV").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\'PDFFORM_PATH\');"><font color="red">删除</font></a>';
					newhtml+='</p>';
				} 
				$("#PDFFORM_PATH_DIV").html(newhtml);
			 }
		});	
		var MATER_PATH= $("input[name='MATER_PATH']").val();
		$.post("executionController.do?getResultFiles&fileIds="+MATER_PATH,{}, function(resultJson) {
			 if(resultJson!="websessiontimeout"){
				$("#MATER_PATH_DIV").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\'MATER_PATH\');"><font color="red">删除</font></a>';
					newhtml+='</p>';
				} 
				$("#MATER_PATH_DIV").html(newhtml);
			 }
		});	
		var MATER_PATH2= $("input[name='MATER_PATH2']").val();
		$.post("executionController.do?getResultFiles&fileIds="+MATER_PATH2,{}, function(resultJson) {
			 if(resultJson!="websessiontimeout"){
				$("#MATER_PATH2_DIV").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\'MATER_PATH2\');"><font color="red">删除</font></a>';
					newhtml+='</p>';
				} 
				$("#MATER_PATH2_DIV").html(newhtml);
			 }
		});	
		//pdf电子表单初始化
		/* AppUtil.initUploadControl({
			file_types : "*.pdf;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "resultSample",
			busTableName : "T_WSBS_APPLYMATER",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "PDFFORM_PATH_BTN",
			singleFileDivId : "PDFFORM_PATH_DIV",
			singleFileId : $("input[name='PDFFORM_PATH']").val(),
			singleFileFieldName : "PDFFORM_PATH",
			limit_size : "5 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='PDFFORM_PATH']").val(resultJson.fileId);
			}
		});
		
		AppUtil.initUploadControl({
			file_types : "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.RAR;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "applymater",
			busTableName : "T_WSBS_APPLYMATER",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "MATER_PATH_BTN",
			singleFileDivId : "MATER_PATH_DIV",
			singleFileId : $("input[name='MATER_PATH']").val(),
			singleFileFieldName : "MATER_PATH",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='MATER_PATH']").val(resultJson.fileId);
			}
		});

		AppUtil.initUploadControl({
			file_types : "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.RAR;",
			file_upload_limit : 0,
			file_queue_limit : 1,
			uploadPath : "applymater",
			busTableName : "T_WSBS_APPLYMATER",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			uploadButtonId : "MATER_PATH2_BTN",
			singleFileDivId : "MATER_PATH2_DIV",
			singleFileId : $("input[name='MATER_PATH2']").val(),
			singleFileFieldName : "MATER_PATH2",
			limit_size : "10 MB",
			uploadSuccess : function(resultJson) {
				$("input[name='MATER_PATH2']").val(resultJson.fileId);
			}
		}); */

		$("input[name='MATER_CLSMLX']").click(function() {
			var lx = $(this).val();
			if (lx == '复印件') {
				if ($(this).prop("checked")) {
					$("#PAGECOPY_NUM").attr("style", "");
				} else {
					$("#PAGECOPY_NUM").attr("style", "display:none;");
					$("input[name='PAGECOPY_NUM']").val('');
				}
			}
			if (lx == '原件(收)'||lx == '原件(验)') {
				if ($(this).prop("checked")) {
					/* $("#sqhhy").attr("style", ""); */
					$("#num").attr("style", "");
					//是否需要原件
					$("input[name='ISNEED_RAW']").val(1);
				} else {
					/* $("#sqhhy").attr("style", "display:none;"); */
					$("#num").attr("style", "display:none;");
					$("input[name='ISNEED_RAW']").val('');
				}
			}
		});
		var source = '${applyMater.MATER_SOURCE_TYPE}';
		if (source == '本单位') {
			$("#materSource").attr("style", "display:none;");
		}
		$("input[name='MATER_SOURCE_TYPE']").click(function() {
			var lx = $(this).val();
			if (lx == '非本单位') {
				$("#materSource").attr("style", "");
			} else {
				$("#materSource").attr("style", "display:none;");
			}
		});

		$("input[name='ISNEED_RAW']").click(function() {
			var lx = $(this).val();
			if (lx == '1') {
				/*$("#num").attr("style","");*/
				$("input[name='PAGE_NUM']").val(0);
			} else {
				/*$("#num").attr("style","display:none;");*/
				$("input[name='PAGE_NUM']").val('');
			}
		});

		$("input[name='IS_PREAUDIT']").click(function() {
			var val = $(this).val();
			if (val == 1) {
				$("input[name='PREAUDIT_DEPART_NAME']").attr("disabled", false);
				$("input[name='PREAUDIT_DEPART_NAME']").attr("class", "eve-input validate[required]");
			} else {
				$("input[name='PREAUDIT_DEPART_ID']").val("");
				$("input[name='PREAUDIT_DEPART_NAME']").val("");
				$("input[name='PREAUDIT_DEPART_NAME']").attr("disabled", true);
				$("input[name='PREAUDIT_DEPART_NAME']").attr("class", "eve-input");
			}
		});
		
		var cmethod = '${applyMater.COLLECT_METHOD}';
		if (cmethod.indexOf("02") != -1) {
			$("#uploadType").attr("style", "");
		}
		if (cmethod.indexOf("03") != -1) {
			//$("#license").attr("style", "");
            //$("#licenseName").attr("style", "");
		}
		if (cmethod.indexOf("04") != -1) {
			$("#pdfForm").attr("style", "");
		}
		
		$("input[name='COLLECT_METHOD']").click(function(){
			var str = "";
			$("input[type=checkbox][name='COLLECT_METHOD']:checked").each(function() {
				str += $(this).val()+",";
			});
			str = str.substring(0, str.lastIndexOf(","));
			if (str.indexOf("02") != -1) {
				$("#uploadType").attr("style", "");
			} else {
				$("#uploadType").attr("style", "display:none;");
			}
			if (str.indexOf("03") != -1) {
				//$("#license").attr("style", "");
                //$("#licenseName").attr("style", "");
			} else {
				$("#license").attr("style", "display:none;");
                $("#licenseName").attr("style", "display:none;");
			}
			if (str.indexOf("04") != -1) {
				$("#pdfForm").attr("style", "");
			} else {
				$("#pdfForm").attr("style", "display:none;");
			}
		});
		
		window.onload = function() {
			if ('${applyMater.MATER_CLSMLX}'.indexOf('复印件') == -1) {
				$("#PAGECOPY_NUM").attr("style", "display:none;");
				$("input[name='PAGECOPY_NUM']").val('');
			}
			if ('${applyMater.MATER_CLSMLX}'.indexOf('原件') == -1) {
				$("#sqhhy").attr("style", "display:none;");
				$("#num").attr("style", "display:none;");
				$("input[name='PAGE_NUM']").val('');
			}
		/*if('${applyMater.ISNEED_RAW}'=='1'){
			$("#num").attr("style","");
		}*/
		}
		
		var onlyCode = '${onlyCode}';
// 		if(onlyCode!=null&&onlyCode!='undefined'&&onlyCode!=""){
// 			$("input[name='SWB_MATER_CODE']").attr("class","eve-input validate[required,custom[onlyLetterNumberUnderLine]]");
// 			$("#swbbmneed").attr("style","");
// 		}


		
	});
	/**
	 * 显示业务分类信息对话框
	 */
	function newBusunessClass(selectId) {
		//获取事项ID
		var itemId = $("input[name='itemId']").val();
		$.dialog.open("applyMaterController.do?newBusunessClass&itemId=" + itemId + "&selectId=" + selectId,
			{
				title : "新增",
				width : "400px",
				height : "300px",
				lock : true,
				resize : false,
				close : function(resultJson) {

				}
			}, false);
	}

	function editBusunessClass() {
		var selectId = $("input[name='MATER_SSYW']").val();
		if (selectId.indexOf(",") != -1) {
			parent.art.dialog({
				content : "请选择单条记录进行编辑",
				icon : "error",
				ok : true
			});
			return;
		}
		if (selectId == "") {
			parent.art.dialog({
				content : "请选择一条记录!",
				icon : "error",
				ok : true
			});
			return;
		}
		newBusunessClass(selectId);
	}
	
	function addBusunessClass(val, text) {
		$('#MATERSSYW').combobox('reload');
		$('#MATERSSYW').combobox('select', [ val ]);
		$('#MATERSSYW').combobox('unselect', [ '' ]);
	}
	
	function removeBusunessClassSuccess() {
		$("input[name='MATER_SSYW']").val("");
		$('#MATERSSYW').combobox('reload');
		$('#MATERSSYW').combobox('clear');
	}

	//删除选中的常用
	function removeBusunessClass() {
		var selectId = $("input[name='MATER_SSYW']").val();
		if (selectId == "") {
			parent.art.dialog({
				content : "该记录不可删除!",
				icon : "error",
				ok : true
			});
		} else {
			art.dialog.confirm("您确定要删除选择的记录吗?", function() {
				url = "applyMaterController.do?removeBusunessClass";
				formData = "&selectId=" + selectId;
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							removeBusunessClassSuccess();
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			});
		}
	}
	function selectTagName() {
		var tagId = $("input[name='TAG_ID']").val();
		parent.$.dialog.open("tagController.do?selector&tagIds=" + tagId + "&allowCount=1&checkCascadeY=&"
			+ "checkCascadeN=", {
				title : "标签选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectTagInfo = art.dialog.data("selectTagInfo");
					if (selectTagInfo) {
						$("input[name='TAG_ID']").val(selectTagInfo.tagIds);
						$("input[name='TAG_NAME']").val(selectTagInfo.tagNames);
					}
					art.dialog.removeData("selectTagInfo");
				}
			}, false);
	}
	
	function selectLicenseCatalog(){
		var BINDCATALOG_CODE = $("input[name='BINDCATALOG_CODE']").val();
		var MATER_CODE = $("input[name='MATER_CODE']").val();
		parent.$.dialog.open("licenseCatalogController.do?licenseCatalogSelector&BINDCATALOG_CODE="+BINDCATALOG_CODE, {
				title : "共享材料目录选择器",
				width : "1100px",
				lock : true,
				resize : false,
				height : "500px", 
				close : function() {
					var selectCatalogs = art.dialog.data("selectCatalogs");
					if (selectCatalogs) {
						$("input[name='BINDCATALOG_CODE']").val(selectCatalogs.CatalogCodes);
						$("input[name='BINDCATALOG_NAME']").val(selectCatalogs.CatalogNames);
					}
					art.dialog.removeData("selectCatalogs");
				}
			}, false);
	}

	function selectDepartName() {
		var departId = $("input[name='PREAUDIT_DEPART_ID']").val();
		parent.$.dialog.open("departmentController/selector.do?departIds=" + departId + "&allowCount=1&checkCascadeY=&"
			+ "checkCascadeN=", {
				title : "前置审批部门选择",
				width : "700px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if (selectDepInfo) {
						$("input[name='PREAUDIT_DEPART_ID']").val(selectDepInfo.departIds);
						$("input[name='PREAUDIT_DEPART_NAME']").val(selectDepInfo.departNames);
						art.dialog.removeData("selectDepInfo");
					}
				}
			}, false);
	}
	
	
/**
* 标题附件上传对话框
*/
function openMaterFileUploadDialog(id){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_MSJW_SYSTEM_NOTICE', {
		title: "上传(附件)",
		width: "620px",
		height: "300px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					var fileType = 'gif,jpg,jpeg,bmp,png';
					var attachType = 'attach';
					if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
						attachType = "image";
					}								
					$("input[name='"+id+"']").val(uploadedFileInfo.fileId);									
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','"+id+"');\" ><font color=\"red\">删除</font></a></p>"
					$("#"+id+"_DIV").html(spanHtml); 
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

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="ApplyMaterForm" method="post"
		action="applyMaterController.do?saveOrUpdateDepart">
		<div id="ApplyMaterFormDiv"
			data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="itemId" value="${itemId}"> 
			<input type="hidden" name="MATER_ID" value="${applyMater.MATER_ID}">
			<input type="hidden" name="MATER_PATH" value="${applyMater.MATER_PATH}"> 
			<input type="hidden" name="MATER_PATH2" value="${applyMater.MATER_PATH2}"> 
			<input type="hidden" name="MATER_SIZE" value="150">
			<input type="hidden" name="PDFFORM_PATH" value="${applyMater.PDFFORM_PATH}">
			<input type="hidden" name="META_IDS" value="${applyMater.META_IDS}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 140px;float:left;text-align:right;">材料编码：</span>
						<c:if test="${applyMater.MATER_ID!=null}">
							<input type="text" style="width:150px;float:left;" maxlength="30"
								class="eve-input validate[required]"
								value="${applyMater.MATER_CODE}" id="MATER_CODE"
								name="MATER_CODE" />
						</c:if> <c:if test="${applyMater.MATER_ID==null}">
							<input type="text" style="width:150px;float:left;" maxlength="30"
								class="eve-input validate[required,custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]]"
								value="${applyMater.MATER_CODE}" id="MATER_CODE"
								name="MATER_CODE" />
						</c:if> <font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">省大厅材料编码：</span>
						<input type="text" style="width:150px;float:left;" maxlength="30"
						class="eve-input validate[custom[onlyLetterNumberUnderLine]]"
						value="${applyMater.SWB_MATER_CODE}" name="SWB_MATER_CODE" />
						<span id="swbbmneed" style="display:none;"><font class="dddl_platform_html_requiredFlag">*</font></span>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 140px;float:left;text-align:right;">所属材料编码：</span> <input
						type="text" style="width:150px;float:left;" maxlength="30"
						class="eve-input validate[custom[onlyLetterNumberUnderLine]]"
						value="${applyMater.MATER_PARENTCODE}" id="MATER_PARENTCODE"
						name="MATER_PARENTCODE" />
					</td>					
					<td><span style="width: 120px;float:left;text-align:right;">材料分类：</span>
						<eve:eveselect clazz="eve-input validate[]"
							dataParams="materType"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="===选择材料分类==" name="MATER_XZ"
							value="${applyMater.MATER_XZ}"></eve:eveselect>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">材料名称：</span> <textarea
							rows="5" cols="5"
							class="eve-textarea validate[required,maxSize[500]]"
							style="width: 475px" name="MATER_NAME">${applyMater.MATER_NAME}</textarea><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">注意事项：</span> <textarea
							rows="5" cols="5"
							class="eve-textarea validate[maxSize[500]]"
							style="width: 475px" name="MATER_SQGZ">${applyMater.MATER_SQGZ}</textarea></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">下一级事项：</span> <input
						type="hidden" name="MATER_SSYW" id="noid"
						value="${applyMater.MATER_SSYW }"> <input
						class="easyui-combobox" style="width:250px;" id="MATERSSYW"
						data-options="url:'dictionaryController.do?findMaterBusDatas&amp;defaultEmpty=false&amp;itemId=${itemId}',editable:false,
								method: 'post',valueField:'VALUE',textField:'TEXT',panelWidth: 250,panelHeight: '100',multiple:true,
								onChange: function(rec){
									if(rec!=''){
										$('#MATERSSYW').combobox('reload');
										$('#MATERSSYW').combobox('unselect', ['']);
									}
									var val = $('#MATERSSYW').combobox('getValues').join(',');
									$('#noid').val(val);
								} " />

						<span style="width:25px;display:inline-block;text-align:right;">
							<img src="plug-in/easyui-1.4/themes/icons/edit_add.png"
							onclick="newBusunessClass();"
							style="cursor:pointer;vertical-align:middle;" title="新建事项子项">
						</span>
						<span style="width:25px;display:inline-block;text-align:right;">
							<img src="plug-in/easyui-1.4/themes/icons/pencil.png"
								 onclick="editBusunessClass();"
								 style="cursor:pointer;vertical-align:middle;" title="编辑事项子项"></span>
						<span style="width:25px;display:inline-block;text-align:right;">
							<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png"
							onclick="removeBusunessClass();"
							style="cursor:pointer;vertical-align:middle;" title="删除选中的事项子项">
						</span> <span style="color:red;margin-left：20px;"> <br>
						<spqn style="margin-left:140px;">备注：点击“+”，即可添加下一级事项名称，没有则无需添加。</spqn>
						</span></td>
				</tr>
				<tr>
					<td><span style="width: 140px;float:left;text-align:right;">是否前置审批：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="0"
							value="${applyMater.IS_PREAUDIT}" fieldname="IS_PREAUDIT">
						</eve:radiogroup></td>
					<td><span style="width: 120px;float:left;text-align:right;">前置审批部门：</span>
						<input type="text" style="width:150px;float:left;"
						disabled="disabled" class="eve-input"
						readonly="readonly" onclick="selectDepartName();"
						value="${applyMater.PREAUDIT_DEPART_NAME}"
						name="PREAUDIT_DEPART_NAME" /> <input type="hidden"
						name="PREAUDIT_DEPART_ID" value="${applyMater.PREAUDIT_DEPART_ID}">
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">过滤关键字：</span> <input
						type="text" style="width:475px;float:left;" maxlength="30"
						class="eve-input validate[]" value="${applyMater.MATER_FILTER}"
						id="MATER_FILTER" name="MATER_FILTER" /></td>
				</tr>
				<%-- <tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">标签：</span> <input
						type="hidden" value="${applyMater.TAG_ID}" name="TAG_ID" /> <input
						type="text" style="width:440px;float:left;"
						value="${applyMater.TAG_NAME}" class="eve-input" name="TAG_NAME"
						readonly="readonly" onclick="selectTagName();" /> &nbsp;<a
						href="javascript:;" class="easyui-linkbutton"
						onclick="$('input[name=\'TAG_ID\']').val('');$('input[name=\'TAG_NAME\']').val('');">清除</a>
					</td>
				</tr> --%>
				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">绑定共享材料目录：</span> <input
						type="hidden" value="${applyMater.BINDCATALOG_CODE}" name="BINDCATALOG_CODE" /> <input
						type="text" style="width:440px;float:left;"
						value="${applyMater.BINDCATALOG_NAME}" class="eve-input" name="BINDCATALOG_NAME"
						readonly="readonly" onclick="selectLicenseCatalog();" /> &nbsp;<a
						href="javascript:;" class="easyui-linkbutton"
						onclick="$('input[name=\'BINDCATALOG_CODE\']').val('');$('input[name=\'BINDCATALOG_NAME\']').val('');">清除</a>
					</td>
				</tr>

				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">材料来源/出具单位：</span>
						<eve:radiogroup typecode="CLLYCJDW" width="130px"
							fieldname="MATER_SOURCE_TYPE"
							value="${applyMater.MATER_SOURCE_TYPE}" defaultvalue="非本单位"></eve:radiogroup>
					</td>
				</tr>
				<tr id="materSource">
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">材料来源/出具单位：</span>
						<textarea rows="5" cols="5"
							class="eve-textarea validate[required,maxSize[500]]"
							style="width: 475px" name="MATER_SOURCE">${applyMater.MATER_SOURCE}</textarea><font
						class="dddl_platform_html_requiredFlag">*</font> <span
						style="color:red;margin-left：20px;"> <br> <p
								style="margin-left:140px;">(备注：材料的来源或材料的出局部门名称，
							如：户口所在地派出所，平潭综合实验区市场监督管理局。)</p> </spqn></td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 140px;float:left;text-align:right;">信用评级特权：</span>
						<eve:excheckbox
							dataInterface="dictionaryService.findDatasForSelect"
							name="PERSON_CREDIT_USE" width="450px;" clazz="checkbox"
							dataParams="personCredit" value="${applyMater.PERSON_CREDIT_USE}">
						</eve:excheckbox>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 140px;float:left;text-align:right;">收取方式：</span>
						<eve:excheckbox
							dataInterface="dictionaryService.findDatasForSelect"
							name="COLLECT_METHOD" width="350px;" clazz="checkbox validate[required]"
							dataParams="CollectMethod" value="${applyMater.COLLECT_METHOD}">
						</eve:excheckbox><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr id="uploadType" style="display: none;">
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">上传文件类型限制：</span>
						<input type="text" style="width:350px;float:left;" maxlength="126"
						class="eve-input validate[required]"
						value="${applyMater.MATER_TYPE}" name="MATER_TYPE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr id="license" style="display: none;">
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">可获取电子证照编码：</span>
						<input type="text" style="width:350px;float:left;" maxlength="64"
						class="eve-input validate[]"
						value="${applyMater.LICENCE_CODE}" name="LICENCE_CODE" /></td>
				</tr>
				<tr id="licenseName" style="display: none;">
					<td colspan="2"><span
							style="width: 140px;float:left;text-align:right;">可获取电子证照名称：</span>
						<input type="text" style="width:350px;float:left;" maxlength="512"
							   class="eve-input validate[required]"
							   value="${applyMater.LICENCE_NAME}" name="LICENCE_NAME" /><font class="dddl_platform_html_requiredFlag">*</font>
						<a href="javascript:showSelectEvidence();" class="btnOneP BUS_SCOPE" >电子证照查询</a>
					</td>
				</tr>
				<tr id="pdfForm" style="display: none;">
					<td colspan="2"><span style="width: 140px;float:left;text-align:right;">电子表单：</span>
						<a href="javascript:void(0);" onclick="openMaterFileUploadDialog('PDFFORM_PATH')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="PDFFORM_PATH_DIV"></div></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">介质要求：</span> <eve:excheckbox
							dataInterface="dictionaryService.findDatasForSelect"
							name="MATER_CLSMLX" width="450px;" clazz="checkbox validate[required]"
							dataParams="APPLYMATERTYPE" value="${applyMater.MATER_CLSMLX}">
						</eve:excheckbox><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr id="PAGECOPY_NUM">
					<td colspan="2"><span
						style="width: 140px;float:left;text-align:right;">复印件份数：</span> <input
						type="text" style="width:50px;float:left;" maxlength="2"
						class="eve-input validate[required,custom[integer],min[1]]"
						value="${applyMater.PAGECOPY_NUM}" name="PAGECOPY_NUM" /><font
						class="dddl_platform_html_requiredFlag">*</font>份</td>
				</tr>
				<tr id="isneed">
					<td id="num" colspan="2"><span
						style="width: 140px;float:left;text-align:right;">原件份数：</span> <input
						type="text" style="width:50px;float:left;" maxlength="2"
						class="eve-input validate[required,custom[integer]]"
						value="${applyMater.PAGE_NUM}" name="PAGE_NUM" /><font
						class="dddl_platform_html_requiredFlag">*</font>份</td>
				</tr>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>其他信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 140px;float:left;text-align:right;">提交材料模板：</span>
						<a href="javascript:void(0);" onclick="openMaterFileUploadDialog('MATER_PATH')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="MATER_PATH_DIV"></div></td>
				</tr>
				<tr>
					<td><span style="width: 140px;float:left;text-align:right;">示例模板：</span>
						<a href="javascript:void(0);" onclick="openMaterFileUploadDialog('MATER_PATH2')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="MATER_PATH2_DIV"></div></td>
				</tr>
				<tr>
					<td><span style="width: 140px;float:left;text-align:right;">材料说明：</span>
						<textarea rows="5" cols="5" class="eve-textarea"
							style="width: 475px" name="MATER_DESC">${applyMater.MATER_DESC}</textarea></td>
				</tr>
			</table>


		</div>
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

