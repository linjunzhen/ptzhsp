<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		
		
		AppUtil.initWindowForm("commercialSealForm", function(form, valid) {
			if (valid) {
				$('#commercialSealForm').find('input,textarea').prop("disabled", false);
				$('#commercialSealForm').find('select').prop("disabled", false);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#commercialSealForm").serialize();
				var url = $("#commercialSealForm").attr("action");
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
							parent.$("#commercialSealGrid").datagrid("reload");
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
		}, "T_COMMERCIAL_SEAL");
		
		//清除前后空格
		$("input,textarea").on('blur', function(event) { 
			$(this).val(trim($(this).val()));
		});
		var ARMY_REGISTER_HOURSE = '${commercialSeal.ARMY_REGISTER_HOURSE}';
		if(ARMY_REGISTER_HOURSE){			
			setRequired(ARMY_REGISTER_HOURSE,'ARMYHOURSE_REGISTER_REMARKS','03');
		}
	});
	//清除前后空格
	function trim(str){ 
	  return str.replace(/(^\s*)|(\s*$)/g,""); 
	}
	/**
	 *当值为val1时，设置为必填
	 * @param val
	 * @param inputName
	 * @param otherValue
	 */
	function setRequired(val,inputName,val1){
		if (val===val1) {
			$("input[name="+inputName+"]").attr("disabled",false);
			$("input[name="+inputName+"]").addClass(" validate[required]");
		} else {
			$("input[name="+inputName+"]").attr("disabled",true);
			$("input[name="+inputName+"]").removeClass(" validate[required]");
			$("input[name="+inputName+"]").val('');
		}
	}
	//选择证件类型为身份证时添加证件号码验证
	function setZjValidate(zjlx,name){
		if (zjlx == "SF") {
			$("input[name='" + name + "']").addClass(",custom[vidcard]");
		} else {
			$("input[name='" + name + "']").removeClass(",custom[vidcard]");
		}
	}
	
	
	function opencommercialSealImageUploadDialog(name){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传(图片)",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#"+name+"_PATH_IMG").attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='"+name+"']").val(uploadedFileInfo.filePath);
					}else{
						$("#"+name+"_PATH_IMG").attr("src",'<%=path%>/webpage/common/images/nopic.jpg');
						$("input[name='"+name+"']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	$(function(){
		var fileids="${commercialSeal.SQS_FILEID}";
		$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
			if(resultJson!="websessiontimeout"){
				$("#fileListDiv1").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
					if('${isEdit}'==1){
						newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a>"
					}
					
					newhtml+='</p>';
				}
				$("#fileListDiv1").html(newhtml);
			}
		});
		
		$.post("executionController.do?getResultFiles",{fileIds:'${commercialSeal.BAPZ}'}, function(resultJson) {
			if(resultJson!="websessiontimeout"){
				$("#fileListDiv2").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
					newhtml+='</p>';
				}
				$("#fileListDiv2").html(newhtml);
			}
		});
		$.post("executionController.do?getResultFiles",{fileIds:'${commercialSeal.YZKZDJB}'}, function(resultJson) {
			if(resultJson!="websessiontimeout"){
				$("#fileListDiv3").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
					newhtml+='</p>';
				}
				$("#fileListDiv3").html(newhtml);
			}
		});
	});
	
		/**
		* 标题附件上传对话框
		*/
		function openResultFileUploadDialog(name){
			//定义上传的人员的ID
			var uploadUserId = $("input[name='uploadUserId']").val();
			//定义上传的人员的NAME
			var uploadUserName = $("input[name='uploadUserName']").val();
			//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
			art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_SEAL&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
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
							var fileid=$("input[name='"+name+"']").val();
							if(fileid!=""&&fileid!=null){
								$("input[name='"+name+"']").val(fileid+";"+uploadedFileInfo.fileId);
							}else{
								$("input[name='"+name+"']").val(uploadedFileInfo.fileId);
							}
							var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
							spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
							spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
							spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
							$("#fileListDiv1").append(spanHtml);
						}
					}
					art.dialog.removeData("uploadedFileInfo");
				}
			});
		}
		
		function delUploadFile1(fileId,attacheKey){
			//AppUtil.delUploadMater(fileId,attacheKey);
			art.dialog.confirm("您确定要删除该文件吗?", function() {
				//移除目标span
				$("#"+fileId).remove();
				var SQS_FILEID=$("input[name='SQS_FILEID']").val();
				var arrayIds=SQS_FILEID.split(";");
				for(var i=0;i<arrayIds.length;i++){
					if(arrayIds[i]==fileId){
						arrayIds.splice(i,1); 
						break;
					}
				}
				$("input[name='SQS_FILEID']").val(arrayIds.join(";"));
			});
		}
		function pushSealInfo(){
			var statusInput="<input name='STATUS' type='hidden' value='1' />";
			$("#commercialSealForm").append(statusInput);
			$("#commercialSealForm").submit();
		}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	.eflowbutton {
		background: #3a81d0;
		border: none;
		padding: 0 10px;
		line-height: 26px;
		cursor: pointer;
		height: 26px;
		color: #fff;
		border-radius: 5px;
	}
	.whf_input{width:97%!important;;height:25px;float:left;}
	.btn1 {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 27px;
		line-height: 27px;
		margin-left: 10px;
		text-align: center;
		width: 50px;
	}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="commercialSealForm" method="post"
		action="commercialSealController.do?saveOrUpdate">
		<div region="center" style="min-height:460px;">
			<div class="eui-slidebox" style="width:96%">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="SEAL_ID" value="${commercialSeal.SEAL_ID}">
				<!--==========隐藏域部分结束 ===========-->
				
				<div class="syj-title1" style="height:30px;">
					<span>基本信息(<font color="#ff0101">*</font>为必填)</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>企业名称</th>
						<td style="width:378px;">
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" id="COMPANY_NAME"
								class="eve-input validate[required]" placeholder="请输入企业名称"  value="${commercialSeal.COMPANY_NAME}" name="COMPANY_NAME" />
						</td>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>统一社会信用代码</th>
						<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="32" id="SOCIAL_CREDITUNICODE"
								class="eve-input validate[required]" placeholder="请输入统一社会信用代码" value="${commercialSeal.SOCIAL_CREDITUNICODE}" name="SOCIAL_CREDITUNICODE" />
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>法定代表人姓名：</th>
						<td><input type="text" class="eve-input  validate[required]"  style="width:97%;height:25px;float:left;" 
							name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${commercialSeal.LEGAL_NAME}"/></td>
						<th><font class="syj-color2">*</font>法定代表人移动电话：</th>
						<td><input type="text" class="eve-input  validate[required,custom[mobilePhone]]" style="width:97%;height:25px;float:left;" 
							name="LEGAL_MOBILE"  placeholder="请输入法定代表人移动电话"  value="${commercialSeal.LEGAL_MOBILE}"  maxlength="16"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>法定代表人证件类型：</th>
						<td>
							<eve:eveselect clazz="eve-input1  validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LEGAL_IDNO');"
							defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${commercialSeal.LEGAL_IDTYPE}">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>法定代表人证件号码：</th>
						<td><input type="text" class="eve-input inputBackgroundCcc validate[required]"  style="width:97%;height:25px;float:left;" 
							name="LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32"   value="${commercialSeal.LEGAL_IDNO}"/></td>
					</tr>					
					<tr>
						<th><font class="syj-color2">*</font>经办人姓名：</th>
						<td><input type="text" class="eve-input validate[required]"  style="width:97%;height:25px;float:left;" 
							name="OPERATOR_NAME"  placeholder="请输入经办人姓名"  maxlength="8"  value="${commercialSeal.OPERATOR_NAME}"/></td>
						<th><font class="syj-color2">*</font>经办人移动电话：</th>
						<td><input type="text" class="eve-input  validate[required,custom[mobilePhone]]" style="width:97%;height:25px;float:left;" 
							name="OPERATOR_MOBILE"  placeholder="请输入经办人移动电话" value="${commercialSeal.OPERATOR_MOBILE}"  maxlength="16"/></td>
					</tr>
					<tr>
					<tr>
						<th><font class="syj-color2">*</font>经办人证件类型：</th>
						<td>
							<eve:eveselect clazz="eve-input1  validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'OPERATOR_IDNO');"
							defaultEmptyText="请选择证件类型" name="OPERATOR_IDTYPE" value="${commercialSeal.OPERATOR_IDTYPE}">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>经办人证件号码：</th>
						<td><input type="text" class="eve-input validate[required]" style="width:97%;height:25px;float:left;" 
							name="OPERATOR_IDNO" placeholder="请输入经办人证件号码" maxlength="32" value="${commercialSeal.OPERATOR_IDNO}" />
						</td>
					</tr>							
					<tr>
						<th ><font class="syj-color2">*</font>申报号：</th>
						<td colspan="3">
							<input type="text" class="eve-input validate[required]" style="width:47%;height:25px;float:left;" 
							name="EXE_ID" placeholder="请输入申报号" maxlength="32" value="${commercialSeal.EXE_ID}" />
						</td>
					</tr>	
				</table>
				<div class="syj-title1 tmargin20">
					<span>企业公章<font class="syj-color2">*</font>(<font style="color:red;width:105%;">说明：两个套餐区别为法定代表人名章材质不同：套餐一材质为牛角，套餐二材质为橡胶。</font>)</span>
				</div>
				<div  <c:if test="${isEdit == 1}">style="position:relative;"</c:if> <c:if test="${isEdit != 1 && commercialSeal.SEAL_PACKAGE==1}">style="position:relative;display: none;"</c:if>>	
					<div style="margin-top: 10px;margin-bottom: 10px;">
						<input type="radio" name="SEAL_PACKAGE" value="2" class="validate[required]" <c:if test="${commercialSeal.SEAL_PACKAGE==2}"> checked="checked"</c:if>/>套餐一
					</div>
					<table cellpadding="0" cellspacing="1" class="syj-table2">
						<tr>
							<th>采购内容</th>
							<th>型号</th>
							<th>材质</th>
							<th>数量</th>
						</tr>
						<tr>
							<td style="text-align:center;">企业公章</td>
							<td style="text-align:center;">直径40mm圆形</td>
							<td style="text-align:center;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<td style="text-align:center;">财务专用章</td>
							<td style="text-align:center;">直径38mm圆形</td>
							<td style="text-align:center;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<td style="text-align:center;">发票专用章</td>
							<td style="text-align:center;">40mm*30mm椭圆形</td>
							<td style="text-align:center;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<td style="text-align:center;">法定代表人名章</td>
							<td style="text-align:center;">18mm*18mm正方形</td>
							<td style="text-align:center;color:red;">牛角</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<th>供应商</th>
							<td colspan="3"  style="text-align:center;">
								平潭综合实验区鑫楚印章制作有限公司
							</td>
						</tr>
					</table>
				</div>
				
				<div <c:if test="${isEdit == 1}">style="position:relative;"</c:if> <c:if test="${isEdit != 1 && commercialSeal.SEAL_PACKAGE==2}">style="position:relative;display: none;"</c:if>>
					<div style="margin-top: 10px;margin-bottom: 10px;">
						<input type="radio" name="SEAL_PACKAGE" value="1" class="validate[required]" <c:if test="${commercialSeal.SEAL_PACKAGE==1}"> checked="checked"</c:if>/>套餐二
					</div>
					<table cellpadding="0" cellspacing="1" class="syj-table2">
						<tr>
							<th>采购内容</th>
							<th>型号</th>
							<th>材质</th>
							<th>数量</th>
						</tr>
						<tr>
							<td style="text-align:center;">企业公章</td>
							<td style="text-align:center;">直径40mm圆形</td>
							<td style="text-align:center;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<td style="text-align:center;">财务专用章</td>
							<td style="text-align:center;">直径38mm圆形</td>
							<td style="text-align:center;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<td style="text-align:center;">发票专用章</td>
							<td style="text-align:center;">40mm*30mm椭圆形</td>
							<td style="text-align:center;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<td style="text-align:center;">法定代表人名章</td>
							<td style="text-align:center;">18mm*18mm正方形</td>
							<td style="text-align:center;color:red;">橡胶</td>
							<td style="text-align:center;">1枚</td>
						</tr>
						<tr>
							<th>供应商</th>
							<td colspan="3"  style="text-align:center;">
							漳州市盾安印章制作服务有限公司
							</td>
						</tr>
					</table>
				</div>
				<div class="syj-title1" style="height:30px;">
					<span>附件信息</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th>法定代表人<br/>身份证正面：</th>
						<td>							
							<c:choose>
								<c:when test="${commercialSeal.LEGAL_SFZZM!=null&&commercialSeal.LEGAL_SFZZM!=''}">
									<img id="LEGAL_SFZZM_PATH_IMG" src="${_file_Server}${commercialSeal.LEGAL_SFZZM}"
										height="140px" width="220px">				
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('LEGAL_SFZZM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<img id="LEGAL_SFZZM_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">				
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('LEGAL_SFZZM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[]" name="LEGAL_SFZZM" value="${commercialSeal.LEGAL_SFZZM}">
						</td>
						<th>法定代表人<br/>身份证反面：</th>
						<td>							
							<c:choose>
								<c:when test="${commercialSeal.LEGAL_SFZFM!=null&&commercialSeal.LEGAL_SFZFM!=''}">
									<img id="LEGAL_SFZFM_PATH_IMG" src="${_file_Server}${commercialSeal.LEGAL_SFZFM}"
										height="140px" width="220px">					
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('LEGAL_SFZFM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<img id="LEGAL_SFZFM_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">					
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('LEGAL_SFZFM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[]" name="LEGAL_SFZFM" value="${commercialSeal.LEGAL_SFZFM}">
						</td>
					</tr>
					<tr>
						<th>经办人身份证正面：</th>
						<td>							
							<c:choose>
								<c:when test="${commercialSeal.OPERATOR_SFZZM!=null&&commercialSeal.OPERATOR_SFZZM!=''}">
									<img id="OPERATOR_SFZZM_PATH_IMG" src="${_file_Server}${commercialSeal.OPERATOR_SFZZM}"
										height="140px" width="220px">					
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('OPERATOR_SFZZM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<img id="OPERATOR_SFZZM_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">					
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('OPERATOR_SFZZM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[]" name="OPERATOR_SFZZM" value="${commercialSeal.OPERATOR_SFZZM}">
						</td>
						<th>经办人身份证反面：</th>
						<td>							
							<c:choose>
								<c:when test="${commercialSeal.OPERATOR_SFZFM!=null&&commercialSeal.OPERATOR_SFZFM!=''}">
									<img id="OPERATOR_SFZFM_PATH_IMG" src="${_file_Server}${commercialSeal.OPERATOR_SFZFM}"
										height="140px" width="220px">						
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('OPERATOR_SFZFM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<img id="OPERATOR_SFZFM_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">						
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('OPERATOR_SFZFM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[]" name="OPERATOR_SFZFM" value="${commercialSeal.OPERATOR_SFZFM}">
						</td>
					</tr>
					<tr>
						<th>营业执照信息：</th>
						<td>							
							<c:choose>
								<c:when test="${commercialSeal.YYZZ_PATH!=null&&commercialSeal.YYZZ_PATH!=''}">
									<img id="YYZZ_PATH_PATH_IMG" src="${_file_Server}${commercialSeal.YYZZ_PATH}"
										height="140px" width="220px">							
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('YYZZ_PATH')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<img id="YYZZ_PATH_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">								
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('YYZZ_PATH')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[]" name="YYZZ_PATH" value="${commercialSeal.YYZZ_PATH}">
						</td>
						<th>经办人人脸照片：</th>
						<td>							
							<c:choose>
								<c:when test="${commercialSeal.OPERATOR_PHOTO!=null&&commercialSeal.OPERATOR_PHOTO!=''}">
									<img id="OPERATOR_PHOTO_PATH_IMG" src="${_file_Server}${commercialSeal.OPERATOR_PHOTO}"
										height="140px" width="220px">									
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('OPERATOR_PHOTO')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:when>
								<c:otherwise>
									<img id="OPERATOR_PHOTO_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">									
									<c:if test="${isEdit == 1}">	
									<a href="javascript:void(0);" onclick="opencommercialSealImageUploadDialog('OPERATOR_PHOTO')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
									</c:if>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[]" name="OPERATOR_PHOTO" value="${commercialSeal.OPERATOR_PHOTO}">
						</td>
					</tr>
					<tr>
						<th>授权书（确认书）：</th>
						<td colspan="3"><input  name="SQS_FILEID"  type="hidden"  value="${commercialSeal.SQS_FILEID }">
							<div style="width:100%;display: none;" id="UPDATE_FILE_DIV"></div>
								
								<c:if test="${isEdit == 1}">
								<a href="javascript:void(0);" onclick="openResultFileUploadDialog('SQS_FILEID')">
									<img id="UPDATE_FILE_BTN" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
								</c:if>
							<div style="width:100%;" id="fileListDiv1"></div>
						</td>
					</tr>
					<c:if test="${commercialSeal.STATUS >=2}">
					<tr>
						<th>备案凭证：</th>
						<td colspan="3"><input  name="BAPZ"  type="hidden"  value="${commercialSeal.BAPZ }">
							<div style="width:100%;" id="fileListDiv2"></div>
						</td>
					</tr>
					<tr>
						<th>印章刻制登记表：</th>
						<td colspan="3"><input  name="YZKZDJB"  type="hidden"  value="${commercialSeal.YZKZDJB }">
							<div style="width:100%;" id="fileListDiv3"></div>
						</td>
					</tr>
					</c:if>
				</table>
			</div>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<c:if test="${isEdit == 1}">
					<input value="保存" type="submit"
							class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
					<c:if test="${commercialSeal.STATUS == 0||empty commercialSeal}">
					<input value="提交" type="button" onclick="pushSealInfo();"
							class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
					</c:if>
				</c:if>
				<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

