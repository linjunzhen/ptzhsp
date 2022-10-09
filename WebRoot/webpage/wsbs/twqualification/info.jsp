<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine,swfupload"></eve:resources>
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("TwQualificationForm", function(form, valid) {
			if (valid) {
			    /* //照片必上传验证
			    var photoPath = $("input[name='PHOTO_PATH']").val();
			    if(photoPath == null || photoPath == 'undefined' || photoPath == '' ){
			    	parent.art.dialog({
							content: "请上传照片！",
							icon:"succeed",
							time:3,
						    ok: true
						});
					return ;
			    } */
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#TwQualificationForm").serialize();
				var url = $("#TwQualificationForm").attr("action");
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
							parent.$("#TwQualifyInfoGrid").datagrid("reload");
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
		}, "T_TW_QUALIFICATION");
		
		<%-- AppUtil.initUploadControl({
            file_types : "*.png;*.jpg;*.bmp;*.jpeg;",
            file_upload_limit : 0,
            file_queue_limit : 1,
            uploadPath : "TWQUALIFICATION",
            busTableName : "T_WSBS_TWQUALIFICATION",
            uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
            uploadButtonId : "PHOTO_PATH_BTN",
            singleFileDivId : "PHOTO_PATH_DIV",
            singleFileId : $("input[name='PHOTO_ID']").val(),
            singleFileFieldName : "PHOTO_ID",
            limit_size : "500KB",
            uploadSuccess : function(resultJson) {
                $("input[name='PHOTO_PATH']").val(resultJson.filePath);
                $("#iconLogo").attr("src","<%=basePath%>/"+resultJson.filePath);
            }
        }); --%>

	});
	function openImageUploadDialog(){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传(图片)",
			width: "480px",
			height: "240px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#iconLogo").attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='PHOTO_PATH']").val(uploadedFileInfo.filePath);
					}else{
						$("#iconLogo").attr("src",'<%=path%>/webpage/cms/module/images/twQualificationPhoto.jpg');
						$("input[name='PHOTO_PATH']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function delTwQualificatLogo(){		
		$("input[name='PHOTO_PATH']").val('');
		$("#iconLogo").attr("src","<%=path%>/webpage/cms/module/images/twQualificationPhoto.jpg");
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="TwQualificationForm" method="post"
		action="twQualificationController.do?saveOrUpdate">
		<div id="TwQualificationFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="QUALIFICATION_ID" value="${twQualification.QUALIFICATION_ID}"/>
		<input type="hidden" name="PHOTO_PATH" value="${twQualification.PHOTO_PATH}"/>
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>采信证书基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 180px;float:left;text-align:right;">姓名：</span>
					<input type="text" style="width:150px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${twQualification.USERNAME}"
					name="USERNAME" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				
				<td rowspan="4"><span style="width: 180px;float:left;text-align:right;">照片：</span>
				<c:if test="${twQualification.PHOTO_PATH!=null}">                   
                        <img id="iconLogo" width="130" height="125" border="0" src="${_file_Server }${twQualification.PHOTO_PATH}" style="border:0;"/>
                    </c:if>
                    <c:if test="${twQualification.PHOTO_PATH==null}">
                        <img id="iconLogo" width="130" height="125" border="0" src="<%=path%>/webpage/cms/module/images/twQualificationPhoto.jpg" style="border:0;"/>
                    </c:if>
                    </div>
					<input type="button" value="上传图片..." onclick="openImageUploadDialog();">
					<input style="width: 42px; height: 22px; color: #336699;" type="button" onclick="delTwQualificatLogo();" class="z-dlg-button z-dialog-cancelbutton" value="删除">
                    <div style="width:100%;display:none;" id="PHOTO_PATH_DIV"></div>
                 </td>
            </tr>
            <tr>
				<td><span style="width: 180px;float:left;text-align:right;">性别：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="sex"
						dataInterface="dictionaryService.findDatasForSelect" value="${twQualification.SEX}" 
						defaultEmptyText="==选择性别==" name="SEX"></eve:eveselect><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 180px;float:left;text-align:right;">出生日期：</span>
					 <input type="text" style="width:150px;float:left; height: 24px; line-height: 24px;margin-left: 4px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
					 name="BIRTHDAY" value="${twQualification.BIRTHDAY}"><font 
					 class="dddl_platform_html_requiredFlag">*</font></td>
			
			</tr>
			<tr>
				<td><span style="width: 180px;float:left;text-align:right;">台胞证件号：</span>
					<input type="text" style="width:150px;float:left;"
					 value="${twQualification.TB_ZJHM}" class="eve-input validate[required]"
					 name="TB_ZJHM"><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td ><span style="width: 180px;float:left;text-align:right;">台湾地区职业资格及等级：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.ZYZG_LEVEL}" name="ZYZG_LEVEL" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>	
				<td ><span style="width: 180px;float:left;text-align:right;">职业（工种）及等级：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.ZY_LEVEL}" name="ZY_LEVEL" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>	
			</tr>
			<tr>
				<td ><span style="width: 180px;float:left;text-align:right;">证书号码：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.ZSHM}" name="ZSHM" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
				<td ><span style="width: 180px;float:left;text-align:right;">证书编号：</span>
				    <c:if test="${twQualification.ZS_NUMBER==null}">
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
					value="${twQualification.ZS_NUMBER}" name="ZS_NUMBER" id="ZS_NUMBER"/><font
					class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
					 <c:if test="${twQualification.ZS_NUMBER!=null}">
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]" readonly='readonly'
					value="${twQualification.ZS_NUMBER}" name="ZS_NUMBER" id="ZS_NUMBER"/><font
					class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
					</td>	
			</tr>
			<tr>	
				<td colspan='2'><span style="width: 180px;float:left;text-align:right;">有效范围：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.YXFW}" name="YXFW" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
						
				<td colspan='2'><span style="width: 180px;float:left;text-align:right;">证书有效期：</span>
					<input type="text" style="width:187px;float:left; height: 24px; line-height: 24px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZS_VALIDITY\')}'})" 
					 name="CXZS_TIME" id="CXZS_TIME" readonly="readonly" value="${twQualification.CXZS_TIME}">
				<span style="width: 20px;float:left;text-align:center;">至</span>
					<input type="text" style="width:187px;float:left; height: 24px; line-height: 24px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'CXZS_TIME\')}'})" 
					 name="ZS_VALIDITY" id="ZS_VALIDITY" readonly="readonly" value="${twQualification.ZS_VALIDITY}">	<font
					class="dddl_platform_html_requiredFlag">*</font></td>	
			</tr>
			<tr>
				<td colspan='2'><span style="width: 180px;float:left;text-align:right;">备注：</span>
					<input type="text" style="width:550px;float:left;" maxlength="126"
					class="eve-input validate[]"
					value="${twQualification.BZ}" name="BZ" /></td>
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

