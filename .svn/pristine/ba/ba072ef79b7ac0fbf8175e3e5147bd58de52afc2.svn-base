<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
    
	function showSelectBusTypes(){
		var needCheckIds= $("input[name='BUS_TYPEIDS']").val();
		//定义数据源
		art.dialog.data("EveTreeDataConfig", {
			allowCount:0,
			checkCascadeY:"ps",
			checkCascadeN:"s",
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_WSBS_BUSTYPE",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "PARENT_ID,PATH,TYPE_CODE",
				"rootParentId" : "0",
				"needCheckIds":needCheckIds,
				"isShowRoot" : "false",
				"rootName" : "服务类别树"
			}
		});
		parent.$.dialog.open("eveControlController.do?treeSelector", {
			title : "服务类别选择器",
	        width:"500px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
				var selectTreeInfo = art.dialog.data("selectTreeInfo");
				if(selectTreeInfo){
					$("input[name='BUS_TYPEIDS']").val(selectTreeInfo.checkIds);
					$("input[name='BUS_TYPENAMES']").val(selectTreeInfo.checkNames);
					art.dialog.removeData("selectTreeInfo");
				}
			}
		}, false);
	}

	$(function() {
		AppUtil.initWindowForm("ApplyMaterForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#ApplyMaterForm").serialize();
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
		
		/* AppUtil.initUploadControl({
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
		}); */

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
	});
	
  function selectTagName(){
		var tagId = $("input[name='TAG_ID']").val();
        parent.$.dialog.open("tagController.do?selector&tagIds="+tagId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "标签选择器",
            width:"1000px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectTagInfo = art.dialog.data("selectTagInfo");
                if(selectTagInfo){
                    $("input[name='TAG_ID']").val(selectTagInfo.tagIds);
                    $("input[name='TAG_NAME']").val(selectTagInfo.tagNames);
                }
    			art.dialog.removeData("selectTagInfo");
            }
        }, false);
	}	
/**
* 标题附件上传对话框
*/
function openMaterFileUploadDialog(id){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_WSBS_APPLYMATER', {
		title: "上传(附件)",
		width: "620px",
		height: "240px",
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
		action="applyMaterController.do?saveOrUpdate">
		<div id="ApplyMaterFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="itemId" value="${itemId}">
			<input type="hidden" name="MATER_ID" value="${applyMater.MATER_ID}">
			<input type="hidden" name="BUS_TYPEIDS" value="${applyMater.BUS_TYPEIDS}">
			<input type="hidden" name="MATER_PATH" value="${applyMater.MATER_PATH}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">材料编码：</span>
					    <c:if test="${applyMater.MATER_ID!=null}">
					      ${applyMater.MATER_CODE}
					    </c:if> 
					    <c:if test="${applyMater.MATER_ID==null}">
						<input type="text" style="width:180px;float:left;" maxlength="30"
						class="eve-input validate[required,custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]]"
						value="${applyMater.MATER_CODE}" id="MATER_CODE" name="MATER_CODE" />
						</c:if>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">所属材料编码：</span>
						<input type="text" style="width:180px;float:left;" maxlength="30"
						class="eve-input validate[custom[onlyLetterNumberUnderLine]]"
						value="${applyMater.MATER_PARENTCODE}" id="MATER_PARENTCODE" name="MATER_PARENTCODE" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">过滤关键字：</span>
						<input type="text" style="width:180px;float:left;" maxlength="30"
						class="eve-input validate[]"
						value="${applyMater.MATER_FILTER}" id="MATER_FILTER" name="MATER_FILTER" />
					</td>
				</tr>				
	            <tr>
	                <td colspan="2">
		                <span style="width: 120px;float:left;text-align:right;">标签：</span>
		                <input type="hidden" value="${applyMater.TAG_ID}"  name="TAG_ID"/>
		                <input type="text" style="width:350px;float:left;" value="${applyMater.TAG_NAME}" 
		                    class="eve-input" name="TAG_NAME" readonly="readonly" onclick="selectTagName();" /> 
		                &nbsp;<a href="javascript:;" class="easyui-linkbutton" onclick="$('input[name=\'TAG_ID\']').val('');$('input[name=\'TAG_NAME\']').val('');">清除</a>
	                </td>
	            </tr>
				<tr>
                    <td colspan="2"><span style="width: 120px;float:left;text-align:right;">材料名称：</span>
                        <textarea rows="5" cols="5" class="eve-textarea validate[required,maxSize[500]]"
                            style="width: 400px"  name="MATER_NAME">${applyMater.MATER_NAME}</textarea><font
                        class="dddl_platform_html_requiredFlag">*</font></td>
                </tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">上传文件类型：</span>
						<input type="text" style="width:400px;float:left;" maxlength="126"
						class="eve-input validate[required]"
						value="${applyMater.MATER_TYPE}" name="MATER_TYPE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
                    <td colspan="2"><span style="width: 120px;float:left;text-align:right;">上传限制大小：</span>
                       <input type="text" style="width:50px;float:left;" maxlength="2"
						class="eve-input validate[required,custom[integer],min[1]]" 
						value="${applyMater.MATER_SIZE}" name="MATER_SIZE" /><font
						class="dddl_platform_html_requiredFlag">*</font>MB
					</td>
                </tr>
                <tr>
                    <td colspan="2"><span style="width: 120px;float:left;text-align:right;">材料类型：</span>
                       <eve:radiogroup typecode="APPLYMATERTYPE" width="130px" fieldname="MATER_CLSMLX" value="${applyMater.MATER_CLSMLX}" defaultvalue="复印件"></eve:radiogroup>
					</td>
                </tr>
                <tr>
                    <td colspan="2"><span style="width: 120px;float:left;text-align:right;">数字份数：</span>
                       <input type="text" style="width:50px;float:left;" maxlength="2"
						class="eve-input validate[required,custom[integer],min[1]]" 
						value="${applyMater.MATER_CLSM}" name="MATER_CLSM" /><font
						class="dddl_platform_html_requiredFlag">*</font>份
					</td>
                </tr>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>其他信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">服务类别：</span>
					<input type="text" style="width:400px;float:left;" maxlength="256" readonly="readonly"
					    onclick="showSelectBusTypes();"
						class="eve-input validate[required]" value="${applyMater.BUS_TYPENAMES}" name="BUS_TYPENAMES" />
						<font class="dddl_platform_html_requiredFlag">*</font></td>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">材料样本或样图：</span>
						<a href="javascript:void(0);" onclick="openMaterFileUploadDialog('MATER_PATH')">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="MATER_PATH_DIV"></div></td>
				</tr>
				<td>
				   <span style="width: 120px;float:left;text-align:right;">支持WORD编辑：</span>
					<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
				         value="${applyMater.SUPPORT_WORD}" 
				         dataInterface="dictionaryService.findDatasForSelect" 
				         name="SUPPORT_WORD">
				    </eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<tr>
				<td>
					<span style="width: 120px;float:left;text-align:right;">只支持前台上传：</span>
					<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth"
								   value="${applyMater.SUPPORT_FONTUPLOAD}"
								   dataInterface="dictionaryService.findDatasForSelect"
								   name="SUPPORT_FONTUPLOAD">
					</eve:eveselect>
				</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">备注说明：</span>
						<textarea rows="5" cols="5" class="eve-textarea"
							style="width: 400px" name="MATER_DESC">${applyMater.MATER_DESC}</textarea></td>
				</tr>
				<%-- 
				<tr>
				    <td><span style="width: 120px;float:left;text-align:right;">材料类别：</span>
                        <input type="text" style="width:350px;float:left;" maxlength="256" 
                        class="eve-input" value="${applyMater.CLLX}" name="CLLX" />
				    </td>
				</tr>
				--%>
			</table>


		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

