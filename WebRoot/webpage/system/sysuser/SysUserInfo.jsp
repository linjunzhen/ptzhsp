<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">

	$(function () {
		var SYSUSER_GROUP = '${sysUser.SYSUSER_GROUP}';
		$("#SYSUSER_GROUP").combobox("setValue", SYSUSER_GROUP);
	})
   
    function showSelectDeparts(){
    	var departId = $("input[name='DEPART_ID']").val();
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				$("input[name='DEPART_ID']").val(selectDepInfo.departIds);
        			$("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
    			}
    		}
    	}, false);
    }
	
	function showSelectRoles(){
		var roleIds = $("input[name='ROLE_IDS']").val();
		var roleNames = $("input[name='ROLE_NAMES']").val();
		parent.$.dialog.open(encodeURI("sysRoleController.do?selector&allowCount=10&roleIds="+roleIds+"&roleNames="+roleNames), {
			title : "角色选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectRoleInfo = art.dialog.data("selectRoleInfo");
    			if(selectRoleInfo){
    				$("input[name='ROLE_IDS']").val(selectRoleInfo.roleIds);
        			$("input[name='ROLE_NAMES']").val(selectRoleInfo.roleNames);
    			}
    		}
    	}, false);
		
	}

	function openSignFileUploadDialog() {
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_MSJW_SYSTEM_SYSUSER', {
			title: "上传(附件)",
			width: "650px",
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
						$("input[name='SIGN_FILE_ID']").val(uploadedFileInfo.fileId);
						var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
								+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
								+"&attachType="+attachType+"\" ";
						spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"');\" ><font color=\"red\">删除</font></a></p>"
						$("#signFile").html(spanHtml);
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}

	function delUploadFile(fileId) {
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			$("#"+fileId).remove();
			$("input[name='SIGN_FILE_ID']").val('');
		});
	}


	$(function() {
		AppUtil.initWindowForm("SysUserForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#SysUserForm").serialize();
				var url = $("#SysUserForm").attr("action");
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
							parent.$("#SysUserGrid").datagrid("reload");
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
		}, "T_MSJW_SYSTEM_SYSUSER","","${sysUser.USER_ID}");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="SysUserForm" method="post"
		action="sysUserController.do?saveOrUpdate">		
		<div id="SysUserFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="USER_ID" value="${sysUser.USER_ID}">
		<input type="hidden" name="DEPART_ID" value="${sysUser.DEPART_ID}">
		<input type="hidden" name="ROLE_IDS" value="${sysUser.ROLE_IDS}">
		<input type="hidden" name="SIGN_FILE_ID" value="${sysUser.SIGN_FILE_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">帐号：</span>
					<c:if test="${sysUser.USER_ID!=null}">
					 ${sysUser.USERNAME}
					 </c:if> <c:if test="${sysUser.USER_ID==null}">
						<input type="text" style="width:180px;float:left;" maxlength="15"
							class="eve-input validate[required],custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]"
							value="${sysUser.USERNAME}" id="USERNAME" name="USERNAME" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if></td>
				<td><span style="width: 100px;float:left;text-align:right;">用户姓名：</span>
					<input type="text" style="width:180px;float:left;" maxlength="32"
					class="eve-input validate[required]" value="${sysUser.FULLNAME}"
					name="FULLNAME" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">所在机构：</span>
					<input type="text" style="width:180px;float:left;"
					readonly="readonly" value="${sysUser.DEPART_NAME}"
					class="eve-input validate[required]" name="DEPART_NAME" onclick="showSelectDeparts();"><font
					class="dddl_platform_html_requiredFlag">*</font></td>
				<td><span style="width: 100px;float:left;text-align:right;">身份证号：</span>
					<input style="width:180px;float:left;"
					maxlength="18" class="eve-input validate[[],custom[vidcard],ajax[ajaxVerifyValueExist]]"
					id="USERCARD" name="USERCARD" value="${sysUser.USERCARD}"/>
				</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
					<input type="text" style="width:180px;float:left;" maxlength="28"
					class="eve-input validate[],custom[mobilePhoneLoose],ajax[ajaxVerifyValueExist]"
					value="${sysUser.MOBILE}" name="MOBILE" id="MOBILE"/></td>
				<td><span style="width: 100px;float:left;text-align:right;">邮箱地址：</span>
					<input type="text" style="width:180px;float:left;" maxlength="40"
					class="eve-input validate[],custom[email]" value="${sysUser.EMAIL}"
					name="EMAIL" /></td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">性别：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="sex"
						dataInterface="dictionaryService.findDatasForSelect" value="${sysUser.SEX}" 
						defaultEmptyText="==选择性别==" name="SEX"></eve:eveselect><font
					class="dddl_platform_html_requiredFlag">*</font></td>
				<td><span style="width: 100px;float:left;text-align:right;">签名：</span>
					<div style="margin-top: 4px;">
						<a href="javascript:void(0);" onclick="openSignFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
						</a>
						<span id="signFile">
							<c:if test="${sysUser.SIGN_FILE_ID != null}">
								<p id="${sysUser.SIGN_FILE_ID}">
									<a href="javascript:void(0);" style="color: blue;margin-right: 5px;" onclick="AppUtil.downLoadFile('${sysUser.SIGN_FILE_ID}')">
										<font style="color: blue;">${sysUser.signFileName}</font>
									</a>
									<a href="javascript:void(0);" onclick="delUploadFile('${sysUser.SIGN_FILE_ID}');"><font color="red">删除</font></a>
								</p>
							</c:if>
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">角色：</span>
					<input type="text" style="width:180px;float:left;"
					readonly="readonly" value="${sysUser.ROLE_NAMES}"
					onclick="showSelectRoles();" class="eve-input validate[required]"
					name="ROLE_NAMES"><font
					class="dddl_platform_html_requiredFlag">*</font></td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">分组：</span>
					<input class="easyui-combobox tab_text_1" style="width: 200px;" name="SYSUSER_GROUP" id="SYSUSER_GROUP"
						   data-options="
								    prompt : '请输入或者选择分组',
									url: 'dictionaryController.do?load&typeCode=RYFZ',
									method: 'get',
									valueField : 'DIC_CODE',
									textField : 'DIC_NAME',
									filter : function(q, row) {
										var opts = $(this).combobox('options');
										return row[opts.textField].indexOf(q) > -1;
									},
									onSelect:function(){

									}
								"/><span style="width:25px;display:inline-block;text-align:right;">
										<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('RYFZ','SYSUSER_GROUP');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">
								  </span>
					<span style="width:25px;display:inline-block;text-align:right;">
										<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('SYSUSER_GROUP');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">
								  </span>
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
	<div class="treeContent eve-combotree"
		style="display:none; position: absolute;" id="DEPART_IDTreeContent">
		<ul class="ztree" style="margin-top:0; width:160px;height: 150px"
			id="DEPART_IDTree"></ul>
	</div>

	<script>
		function newDicInfoWin(typeCode,combId){
			$.dialog.open("sysUserController.do?addUserGroupView&typeCode="+typeCode, {
				title : "新增",
				width:"600px",
				lock: true,
				resize:false,
				height:"200px",
				close: function(){
					$("#"+combId).combobox("reload");
				}
			}, false);
		}
		function removeDicInfo(combId) {
			var datas = $("#"+combId).combobox("getData");
			var val = $("#"+combId).combobox("getValue");
			var id = "";
			for(var i=0;i<datas.length;i++){
				if(datas[i].DIC_CODE==val){
					id = datas[i].DIC_ID;
					break;
				}
			}
			art.dialog.confirm("您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');
				$.post("dictionaryController.do?multiDel",{
					selectColNames:id
				}, function(responseText, status, xhr) {
					layer.close(layload);
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success == true){
						art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
						$("#"+combId).combobox("reload");
						$("#"+combId).combobox("setValue","");
					}else{
						$("#"+combId).combobox("reload");
						art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				});
			});
		}
	</script>

</body>

