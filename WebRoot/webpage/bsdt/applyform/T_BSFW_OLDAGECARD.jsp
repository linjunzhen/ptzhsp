<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String currentTime = sdf.format(new Date());
%>
<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">
        <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
        <script type="text/javascript" src="<%=basePath%>webpage/common/dynamic.jsp"></script>
        <script type="text/javascript" src="<%=basePath%>plug-in/json-2.0/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>webpage/common/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>webpage/bsdt/applyform/css/applyform.css" />
		<!-- lodop begin -->
	    <script type="text/javascript" src="<%=basePath %>plug-in/lodop-6.198/jquery.cookie.js"></script>
	    <script type="text/javascript" src="<%=basePath %>plug-in/lodop-6.198/licPrint.js"></script>
	    <script type="text/javascript" src="<%=basePath %>plug-in/lodop-6.198/LodopFuncs.js"></script>
	    <!-- lodop end -->
	    <!-- lightbox2 begin -->
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>plug-in/lightbox2/css/lightbox.min.css"/>
	    <script type="text/javascript" src="<%=basePath %>plug-in/lightbox2/js/lightbox.min.js"></script>
	    <!-- lightbox2 end -->
		<style type="text/css">
		    .tab_tk_pro1 tr td{color: #000;}
		    .eflowbutton{background: #3a81d0;border: none;padding: 0 10px;height: 28px;cursor: pointer;color: #fff;border-radius: 3px;}
		</style>
		<script type="text/javascript">
		
			function openApplicantAvatarImageUploadDialog(){
				var name = $('#OldAgeCardNewForm').find("input[name='SQRMC']").val();
				var idNum = $('#OldAgeCardNewForm').find("input[name='SQRSFZ']").val();
				if(name == '' || idNum == ''){
					alert("?????????????????????????????????????????????????????????");
					return;
				}
				/**
				 * ???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
				 */
				art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image&attachKey=000000000GF141701_02&busTableName=T_BSFW_OLDAGECARD', {
					title: "??????(??????)",
					width: "480px",
					height: "240px",
					lock: true,
					resize: false,
					close: function(){
						var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
						if(uploadedFileInfo){
							if(uploadedFileInfo.fileId){
								$('#000000000GF141701_02_pic').attr('src', "${_file_Server }"+uploadedFileInfo.filePath);
								$('#OldAgeCardNewForm').find("input[name='APPLICANT_AVATAR']").val(uploadedFileInfo.filePath);
								doFaceComparePost(name, idNum, uploadedFileInfo.fileId);
							}else{
								$("#000000000GF141701_02_pic").attr("src",'<%=path%>/webpage/bsdt/applyform/images/tab_btn_sc.png');
								$('#OldAgeCardNewForm').find("input[name='APPLICANT_AVATAR']").val('');
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
			
			
			//????????????????????????????????????
			function openJbrfrontcardImageUploadDialog(){
				/**
				 * ???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
				 */
				art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image&attachKey=000000000GF141701_13&busTableName=T_BSFW_OLDAGECARD', {
					title: "??????(??????)",
					width: "480px",
					height: "240px",
					lock: true,
					resize: false,
					close: function(){
						var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
						if(uploadedFileInfo){
							if(uploadedFileInfo.fileId){								
								var fileId = uploadedFileInfo.fileId;
								var fileName = uploadedFileInfo.fileName;
								var filePath = uploadedFileInfo.filePath;
								$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_ID']").val(fileId);
								$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_PATH']").val(filePath);
								$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_NAME']").val(fileName);
								var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
								spanHtml += (" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
								spanHtml += "<font color=\"blue\">"+fileName+"</font></a>";
								spanHtml += "<a href=\"${_file_Server }"+filePath+"\" data-lightbox=\""+fileId+"\" data-title=\""+fileName+"\"><font color=\"blue\">??????</font></a>";
								
								spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"doDelLQUploadMater('"+fileId+"','000000000GF141701_13');\" ><font color=\"red\">??????</font></a></p>";
								$("#UploadedFiles_000000000GF141701_13").html(spanHtml);
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
					  
			//?????????????????????????????????????????????
			function openJbrbackcardImageUploadDialog(){
				/**
				 * ???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
				 */
				art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image&attachKey=000000000GF141701_14&busTableName=T_BSFW_OLDAGECARD', {
					title: "??????(??????)",
					width: "480px",
					height: "240px",
					lock: true,
					resize: false,
					close: function(){
						var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
						if(uploadedFileInfo){
							if(uploadedFileInfo.fileId){								
								var fileId = uploadedFileInfo.fileId;
								var fileName = uploadedFileInfo.fileName;
								var filePath = uploadedFileInfo.filePath;
								$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_ID']").val(fileId);
								$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_PATH']").val(filePath);
								$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_NAME']").val(fileName);
								var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
								spanHtml += (" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
								spanHtml += "<font color=\"blue\">"+fileName+"</font></a>";
								spanHtml += "<a href=\"${_file_Server }"+filePath+"\" data-lightbox=\""+fileId+"\" data-title=\""+fileName+"\"><font color=\"blue\">??????</font></a>";
								
								spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"doDelLQUploadMater('"+fileId+"','000000000GF141701_14');\" ><font color=\"red\">??????</font></a></p>";
								$("#UploadedFiles_000000000GF141701_14").html(spanHtml);
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
			//???????????????????????????????????????	
			function openCertphotoImageUploadDialog(){
				/**
				 * ???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
				 */
				art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image&attachKey=000000000GF141701_11&busTableName=T_BSFW_OLDAGECARD', {
					title: "??????(??????)",
					width: "480px",
					height: "240px",
					lock: true,
					resize: false,
					close: function(){
						var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
						if(uploadedFileInfo){
							if(uploadedFileInfo.fileId){								
								var fileId = uploadedFileInfo.fileId;
								var fileName = uploadedFileInfo.fileName;
								var filePath = uploadedFileInfo.filePath;
								$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_ID']").val(fileId);
								$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_PATH']").val(filePath);
								$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_NAME']").val(fileName);
								var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
								spanHtml += (" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
								spanHtml += "<font color=\"blue\">"+fileName+"</font></a>";
								spanHtml += "<a href=\"${_file_Server }"+filePath+"\" data-lightbox=\""+fileId+"\" data-title=\""+fileName+"\"><font color=\"blue\">??????</font></a>";
								
								spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"doDelLQUploadMater('"+fileId+"','000000000GF141701_11');\" ><font color=\"red\">??????</font></a></p>";
								$("#UploadedFiles_000000000GF141701_11").html(spanHtml);
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
			 //??????????????????????????????????????????
			function openLqrphotoImageUploadDialog(){
				/**
				 * ???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
				 */
				art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image&attachKey=000000000GF141701_12&busTableName=T_BSFW_OLDAGECARD', {
					title: "??????(??????)",
					width: "480px",
					height: "240px",
					lock: true,
					resize: false,
					close: function(){
						var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
						if(uploadedFileInfo){
							if(uploadedFileInfo.fileId){								
								var fileId = uploadedFileInfo.fileId;
								var fileName = uploadedFileInfo.fileName;
								var filePath = uploadedFileInfo.filePath;
								$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_ID']").val(fileId);
								$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_PATH']").val(filePath);
								$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_NAME']").val(fileName);
								var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
								spanHtml += (" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
								spanHtml += "<font color=\"blue\">"+fileName+"</font></a>";
								spanHtml += "<a href=\"${_file_Server }"+filePath+"\" data-lightbox=\""+fileId+"\" data-title=\""+fileName+"\"><font color=\"blue\">??????</font></a>";
								
								spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"doDelLQUploadMater('"+fileId+"','000000000GF141701_12');\" ><font color=\"red\">??????</font></a></p>";
								$("#UploadedFiles_000000000GF141701_12").html(spanHtml);
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
		
			/**
			 * ?????????????????????
			 */
			function openTitleFileUploadDialog(materType,attachKey){
				//????????????????????????ID
				var uploadUserId = $("input[name='uploadUserId']").val();
				//????????????????????????NAME
				var uploadUserName = $("input[name='uploadUserName']").val();
				var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
				//???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
				art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType='
				+materType+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName)+'&busTableName='+EFLOW_BUSTABLENAME, {
					title: "??????(??????)",
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
								
								
								var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a style=\"cursor: pointer;color: blue;margin-right: 5px;\"";
								spanHtml+=(" href=\"" + __file_server 
								+ "download/fileDownload?attachId=" +uploadedFileInfo.fileId
								+ "&attachType="+attachType+"\" target=\"_blank\" ");
								spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>";
								if(attachType=="image"){
									spanHtml += "<a href=\"${_file_Server }"+uploadedFileInfo.filePath+"\" data-lightbox=\""+uploadedFileInfo.fileId+"\" data-title=\""+uploadedFileInfo.fileName+"\"><font color=\"blue\">??????</font></a>";
								}								
								spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"doDelLQUploadMater('"+uploadedFileInfo.fileId+"','"+attachKey+"');\" ><font color=\"red\">??????</font></a></p>";
								$("#UploadedFiles_"+attachKey).append(spanHtml);
								setWordHidden(attachKey);//????????????????????????
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
			$(function(){
				//??????????????????????????????
				$.validationEngine.defaults.autoHidePrompt = true;
				$.validationEngine.defaults.promptPosition = "topRight";
				$.validationEngine.defaults.autoHideDelay = "2000";
				$.validationEngine.defaults.fadeDuration = "0.5";
				$.validationEngine.defaults.autoPositionUpdate = true;
				$('#OldAgeCardNewForm').validationEngine('attach');
		    	var flowSubmitObj = FlowUtil.getFlowObj();
		    	if(flowSubmitObj){
		    		//????????????????????????????????????
		    		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		    		$("#EXEID_TD").append(flowSubmitObj.EFLOW_EXEID);
		    		 //?????????????????????????????????
		    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"OldAgeCardNewForm");
		    		//????????????????????????
		    		if(flowSubmitObj.busRecord){
		    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"OldAgeCardNewForm");
		    			if(flowSubmitObj.busRecord.APPLICANT_AVATAR){
		    				$('#000000000GF141701_02_pic').attr('src', '${_file_Server }'+flowSubmitObj.busRecord.APPLICANT_AVATAR);
		    			}
		    			if(flowSubmitObj.busRecord.BUSINESS_SOURCE == '2'){
		    				$("#APPLY_SOURCE_TH").append("?????????????????????");
		    				$("#APPLY_SOURCE_TD").append(flowSubmitObj.busRecord.GRIDMAN_NAME);
		    			}
		    			if(flowSubmitObj.busRecord.FACE_CHECKRESULT == 1){
		    				var content = "????????????????????????????????????????????????0???";
		    				$("#faceVerifyTip").html(content);
		    			}else if(flowSubmitObj.busRecord.FACE_CHECKRESULT == 0){
		    				var content = "????????????????????????????????????????????????"+flowSubmitObj.busRecord.FACE_VERIFY+"???";
		    				$("#faceVerifyTip").html(content);
		    			}
		    			if(flowSubmitObj.busRecord.CARD_TYPE == '1'){
		    				$("#OldAgeCardTypeTd").css('background-color', 'green');
		    			}else if(flowSubmitObj.busRecord.CARD_TYPE == '2'){
		    				$("#OldAgeCardTypeTd").css('background-color', 'red');
		    			}
		    			if(flowSubmitObj.busRecord.LQR_TYPE == '2'){
		    				$('#LQRID_LQRNAME_TR').show();
		    				$('#LQRMOBILE_LQRADDR_TR').show();
		    			}
		    			if(flowSubmitObj.busRecord.CERTPHOTO_ID && flowSubmitObj.busRecord.CERTPHOTO_NAME){
		    				var spanHtml = "<p id=\""+flowSubmitObj.busRecord.CERTPHOTO_ID+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
		            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+flowSubmitObj.busRecord.CERTPHOTO_ID+"');\">");
		            		spanHtml += "<font color=\"blue\">"+flowSubmitObj.busRecord.CERTPHOTO_NAME+"</font></a>";
		            		spanHtml += "<a href=\"${_file_Server }"+flowSubmitObj.busRecord.CERTPHOTO_PATH+"\" data-lightbox=\""+flowSubmitObj.busRecord.CERTPHOTO_ID+"\" data-title=\""+flowSubmitObj.busRecord.CERTPHOTO_NAME+"\"><font color=\"blue\">??????</font></a>";
		            		spanHtml += "</p>";
		            		$("#UploadedFiles_000000000GF141701_11").html(spanHtml);
		    			}
						if(flowSubmitObj.busRecord.LQRPHOTO_ID && flowSubmitObj.busRecord.LQRPHOTO_NAME){
							var spanHtml = "<p id=\""+flowSubmitObj.busRecord.LQRPHOTO_ID+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
		            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+flowSubmitObj.busRecord.LQRPHOTO_ID+"');\">");
		            		spanHtml += "<font color=\"blue\">"+flowSubmitObj.busRecord.LQRPHOTO_NAME+"</font></a>";
		            		spanHtml += "<a href=\"${_file_Server }"+flowSubmitObj.busRecord.LQRPHOTO_PATH+"\" data-lightbox=\""+flowSubmitObj.busRecord.LQRPHOTO_ID+"\" data-title=\""+flowSubmitObj.busRecord.LQRPHOTO_NAME+"\"><font color=\"blue\">??????</font></a>";
		            		spanHtml += "</p>";
		            		$("#UploadedFiles_000000000GF141701_12").html(spanHtml);
		    			}
						if(flowSubmitObj.busRecord.JBRFRONTCARD_ID && flowSubmitObj.busRecord.JBRFRONTCARD_NAME){
							var spanHtml = "<p id=\""+flowSubmitObj.busRecord.JBRFRONTCARD_ID+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
		            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+flowSubmitObj.busRecord.JBRFRONTCARD_ID+"');\">");
		            		spanHtml += "<font color=\"blue\">"+flowSubmitObj.busRecord.JBRFRONTCARD_NAME+"</font></a>";
		            		spanHtml += "<a href=\"${_file_Server }"+flowSubmitObj.busRecord.JBRFRONTCARD_PATH+"\" data-lightbox=\""+flowSubmitObj.busRecord.JBRFRONTCARD_ID+"\" data-title=\""+flowSubmitObj.busRecord.JBRFRONTCARD_NAME+"\"><font color=\"blue\">??????</font></a>";
		            		spanHtml += "</p>";
		            		$("#UploadedFiles_000000000GF141701_13").html(spanHtml);
		    			}
						if(flowSubmitObj.busRecord.JBRBACKCARD_ID && flowSubmitObj.busRecord.JBRBACKCARD_NAME){
							var spanHtml = "<p id=\""+flowSubmitObj.busRecord.JBRBACKCARD_ID+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
		            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+flowSubmitObj.busRecord.JBRBACKCARD_ID+"');\">");
		            		spanHtml += "<font color=\"blue\">"+flowSubmitObj.busRecord.JBRBACKCARD_NAME+"</font></a>";
		            		spanHtml += "<a href=\"${_file_Server }"+flowSubmitObj.busRecord.JBRBACKCARD_PATH+"\" data-lightbox=\""+flowSubmitObj.busRecord.JBRBACKCARD_ID+"\" data-title=\""+flowSubmitObj.busRecord.JBRBACKCARD_NAME+"\"><font color=\"blue\">??????</font></a>";
		            		spanHtml += "</p>";
		            		$("#UploadedFiles_000000000GF141701_14").html(spanHtml);
		    			}
		    		}else{
		    			$('#OldAgeCardNewForm').find("select[name='BUSINESS_SOURCE']").val(0);
		    		}
		    		//??????????????????
		    		if(flowSubmitObj.EFLOW_ISQUERYDETAIL == 'false'){
		    			if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '??????' || flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '????????????'){
			    			//???????????????????????????????????????
			    			$("#CARDNUM_CERTDATE_TR").hide();
			    			//????????????????????????
			    			$('#OldAgeCardNewForm').find("input[name='OPERATOR_NAME']").val('${sessionScope.curLoginUser.fullname}');
			    			$('#OldAgeCardNewForm').find("input[name='ACCEPTDEPT_NAME']").val('${sessionScope.curLoginUser.depName}');
			    			$('#OldAgeCardNewForm').find("input[name='ACCEPTDEPT_CODE']").val('${sessionScope.curLoginUser.depCode}');
			    			//???????????????????????????
			    			var uploadBtn = "<a href=\"javascript:void(0);\" onclick=\"openApplicantAvatarImageUploadDialog()\">"
							+"<img id=\"000000000GF141701_02\" src=\"webpage/bsdt/applyform/images/tab_btn_sc.png\"  style=\"width:60px;height:22px;\"/></a>";
			    			var scanBtn = "<a onclick=\"onSQRPhotoScanBtnClick();\" href=\"javascript:void(0);\">";
			    			scanBtn += "<img src=\"<%=path %>/webpage/bsdt/ptwgform/images/scan.png\" style=\"width:60px;height:22px;\"/></a>";
			    			$('#000000000GF141701_02_div').append(uploadBtn).append(scanBtn);
							
						
							
			    			if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '??????'){
			    				//?????????????????????????????????????????????
				    			$('#OldAgeCardNewForm').find("input[name='SQRSFZ']").focusout(function(){
						    		var validateResult = $(this).validationEngine("validate");
						    		if(!validateResult){
						    			var idCard = $(this).val();
						    			AppUtil.ajaxProgress({
											url: 'oldAgeCardController.do?applyCheck',
											params: {idNum:idCard},
											callback: function(resultJson){
												if(resultJson.success){
													var applyInfo = $.parseJSON(resultJson.jsonString);
													$('#OldAgeCardNewForm').find("select[name='SQRXB']").val(applyInfo.sex);
													$('#OldAgeCardNewForm').find("input[name='APPLICANT_BIRTHDAY']").val(applyInfo.birthday);
													$('#OldAgeCardNewForm').find("input[name='APPLICANT_AGE']").val(applyInfo.age);
													$('#OldAgeCardNewForm').find("select[name='CARD_TYPE']").val(applyInfo.cardType);
							    					$('#OldAgeCardNewForm').find("select[name='CARD_TYPE']").trigger('change');
												}else{
													alert(resultJson.msg);
							    					if(window.top){
							    						window.top.location.reload(true);
							    					}
												}
											}
						    			});
						    		}
						    	});
			    			}
			    			//?????????????????????????????????????????????
							$('#OldAgeCardNewForm').find("input[name='JBR_ZJHM']").focusout(function(){
								var validateResult = $(this).validationEngine("validate");
					    		if(!validateResult){
					    			var idCard = $(this).val();
					    			AppUtil.ajaxProgress({
										url: 'oldAgeCardController.do?validIdNum',
										params: {idNum:idCard},
										callback: function(resultJson){
											if(resultJson.success){
												var info = $.parseJSON(resultJson.jsonString);
												$('#OldAgeCardNewForm').find("select[name='JBR_SEX']").val(info.sex);
												$('#OldAgeCardNewForm').find("input[name='JBR_BIRTHDAY']").val(info.birthday);
											}else{
												alert(resultJson.msg);
												$(this).val('');
											}
										}
					    			});
					    		}
							});
			    		}else if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '????????????'){
			    			//??????????????????
			    			$("#SQRID_READBTN").remove();
			    			$("#JBRID_READBTN").remove();
			    			//????????????????????????
			    			$("#LQMARER_TD").show();
			    			$("#LNRYDZOPR_TD").show();
			    			$("#LQRXCZPOPR_TD").show();
			    			
			    			//????????????????????????
			    			$('#LQR_PANEL').show();
			    			$('#LQR_MATER_PANEL').show();
							
			    			//??????????????????
			    			$('#OldAgeCardNewForm').find("input[name='CERTIFICATE_DATE']").val("<%=currentTime%>");
			    		}
		    			
				    	
				    	$('#OldAgeCardNewForm').find("select[name='CARD_TYPE']").change(function(){
				    		var cartTypeVal = $(this).children('option:selected').val();
				    		if(cartTypeVal == ''){
				    			$("#OldAgeCardTypeTd").css('background-color', '#fff');
				    		}else if(cartTypeVal == '1'){
				    			$("#OldAgeCardTypeTd").css('background-color', 'green');
				    		}else if(cartTypeVal == '2'){
				    			$("#OldAgeCardTypeTd").css('background-color', 'red');
				    		}
				    	});
				    	
				    	$('#OldAgeCardNewForm').find("input[name='LQR_TYPE']").change(function(){
				    		var lqrTypeVal = $(this).val();
				    		if(lqrTypeVal == '2'){
				    			$('#LQRID_LQRNAME_TR').show();
				    			$('#LQRMOBILE_LQRADDR_TR').show();
				    		}else{
				    			$('#LQRID_LQRNAME_TR').hide();
				    			$('#LQRMOBILE_LQRADDR_TR').hide();
				    		}
				    	});
		    		}else{
		    			//????????????
		    			$("#SQRID_READBTN").remove();
		    			$("#JBRID_READBTN").remove();
		    			$("#LQRID_READBTN").remove();
		    			$('#OldAgeCardNewForm').find("input[name='SFXSJBRXX']").attr('disabled', 'disabled');
		    			$('#OldAgeCardNewForm').find("input[name='LQR_TYPE']").attr('disabled', 'disabled');
		    		}
		    	}
			});
			
			function FLOW_SubmitFun(flowSubmitObj){
				//?????????????????????????????????
				var validateResult = $("#OldAgeCardNewForm").validationEngine("validate");
				if(validateResult){
					var avatarPath = $('#OldAgeCardNewForm').find("input[name='APPLICANT_AVATAR']").val();
					if(avatarPath == ''){
						alert('?????????????????????????????????????????????????????????');
						return null;
					}
			        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("OldAgeCardNewForm", 0);	
					if(submitMaterFileJson || submitMaterFileJson == ""){
						$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
						//??????????????????????????????
						var formData = FlowUtil.getFormEleData("OldAgeCardNewForm");
						for(var index in formData){
							flowSubmitObj[eval("index")] = formData[index];
						}
						//??????????????????????????????????????????
						if(flowSubmitObj['SFXSJBRXX'] == '1'){
							if(flowSubmitObj['JBRFRONTCARD_ID'] == ''||
							   flowSubmitObj['JBRFRONTCARD_NAME'] == ''){
								alert('?????????????????????????????????????????????????????????????????????');
								return;
							}
							if(flowSubmitObj['JBRBACKCARD_ID'] == ''||
							   flowSubmitObj['JBRBACKCARD_NAME'] == ''){
								alert('?????????????????????????????????????????????????????????????????????');
								return;
							}
						}
						//????????????????????????????????????????????????
						if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '????????????'){
							if(flowSubmitObj['CERTPHOTO_ID'] == ''){
								alert('?????????????????????????????????????????????????????????????????????');
								return;
							}
							if(flowSubmitObj['LQRPHOTO_ID'] == ''){
								alert('??????????????????????????????????????????????????????');
								return;
							}
						}
						if(flowSubmitObj['PROJECT_NAME'] == ''){
							flowSubmitObj['PROJECT_NAME'] = "????????????"+flowSubmitObj['SQRMC'];
						}
						//??????????????????????????????
						flowSubmitObj.EFLOW_CALLBACKFN = "onOldAgeCardNewApplyCallback";
						return flowSubmitObj;
					 }else{
						 return null;
					 }
				 }else{
					 return null;
				 }
			}
			
			function FLOW_TempSaveFun(flowSubmitObj){
				//?????????????????????????????????
				var validateResult =$("#OldAgeCardNewForm").validationEngine("validate");
				if(validateResult){
					var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("OldAgeCardNewForm", 0);	
					if(submitMaterFileJson || submitMaterFileJson == ""){
						$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
						//??????????????????????????????
						var formData = FlowUtil.getFormEleData("OldAgeCardNewForm");
						for(var index in formData){
							flowSubmitObj[eval("index")] = formData[index];
						}
						if(flowSubmitObj['PROJECT_NAME'] == ''){
							flowSubmitObj['PROJECT_NAME'] = "????????????"+flowSubmitObj['SQRMC'];
						}
						//??????????????????????????????
						flowSubmitObj.EFLOW_CALLBACKFN = "onOldAgeCardNewApplyCallback";
						return flowSubmitObj;
					}else{
						return null;
					}
				 }else{
					 return null;
				 }
			}
			
			function FLOW_BackFun(flowSubmitObj){
				return flowSubmitObj;
			}
			
			function FLOW_PrintOldAgeCard(){
				//??????????????????????????????
				var formData = FlowUtil.getFormEleData("OldAgeCardNewForm");
				var flowSubmitObj = new Array();
				for(var index in formData){
					flowSubmitObj[eval("index")] = formData[index];
				}
				var LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
				LODOP.PRINT_INITA(-24, -7, 343, 234, "?????????????????????????????????");
				LODOP.SET_PRINT_PAGESIZE(1, 1000, 700, "CreateCustomPage");
				if(flowSubmitObj.CARD_TYPE == '1'){
					LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath%>webpage/business/oldAgeCard/images/GreenOldAgeCard.jpg'/>");
					LODOP.SET_PRINT_STYLE("FontSize", 11);
					LODOP.ADD_PRINT_TEXT("1.728cm", "1.979cm", "1.664cm", "0.664cm", flowSubmitObj.SQRMC);
					if(flowSubmitObj.SQRXB == '1'){
						LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "???");
					}else if(flowSubmitObj.SQRXB == '2'){
						LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "???");
					}
					LODOP.ADD_PRINT_TEXT("1.728cm", "5.628cm", "0.799cm", "0.667cm", flowSubmitObj.APPLICANT_NATION);
					LODOP.ADD_PRINT_TEXT("2.347cm", "2.029cm", "4.27cm", "0.664cm", flowSubmitObj.SQRSFZ);
					LODOP.ADD_PRINT_TEXT("2.979cm", "1.976cm", "4.217cm", "1.064cm", flowSubmitObj.APPLICANT_ADDR);
					LODOP.ADD_PRINT_TEXT("4.006cm", "2.056cm", "3.874cm", "0.664cm", flowSubmitObj.CARD_NUM);
					LODOP.ADD_PRINT_TEXT("4.628cm", "2.056cm", "3.953cm", "0.664cm", flowSubmitObj.CERTIFICATE_DATE);
					LODOP.ADD_PRINT_TEXT("5.247cm", "2.056cm", "3.979cm", "0.717cm", flowSubmitObj.CERTIFICATE_DEPT);
				}else if(flowSubmitObj.CARD_TYPE == '2'){
					LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath%>webpage/business/oldAgeCard/images/RedOldAgeCard.jpg'/>");
					LODOP.SET_PRINT_STYLE("FontSize", 11);
					LODOP.ADD_PRINT_TEXT("1.728cm", "1.979cm", "1.664cm", "0.664cm", flowSubmitObj.SQRMC);
					if(flowSubmitObj.SQRXB == '1'){
						LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "???");
					}else if(flowSubmitObj.SQRXB == '2'){
						LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "???");
					}
					LODOP.ADD_PRINT_TEXT("1.728cm", "5.628cm", "0.799cm", "0.667cm", flowSubmitObj.APPLICANT_NATION);
					LODOP.ADD_PRINT_TEXT("2.347cm", "2.029cm", "4.27cm", "0.664cm", flowSubmitObj.SQRSFZ);
					LODOP.ADD_PRINT_TEXT("2.979cm", "1.976cm", "4.217cm", "1.064cm", flowSubmitObj.APPLICANT_ADDR);
					LODOP.ADD_PRINT_TEXT("4.006cm", "2.056cm", "3.874cm", "0.664cm", flowSubmitObj.CARD_NUM);
					LODOP.ADD_PRINT_TEXT("4.628cm", "2.056cm", "3.953cm", "0.664cm", flowSubmitObj.CERTIFICATE_DATE);
					LODOP.ADD_PRINT_TEXT("5.247cm", "2.056cm", "3.979cm", "0.717cm", flowSubmitObj.CERTIFICATE_DEPT);
				}
				LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 340);
				LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 227);
				LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", 1);
				LODOP.SET_SHOW_MODE("BKIMG_PRINT", 0);
				LODOP.PREVIEW();
			}
			
			function onOldAgeCardNewApplyCallback(flowVarsJson){
				if(window.top){
					if(top.opener){
						if(top.opener.$("#OldAgeCardMyApplyGrid")){
							top.opener.$("#OldAgeCardMyApplyGrid").datagrid('reload');
						}
						if(top.opener.$("#OldAgeCardNeedMeHandleGrid")){
							top.opener.$("#OldAgeCardNeedMeHandleGrid").datagrid('reload');
						}
						if(top.opener.$("#OldAgeCardHandledByMeGrid")){
							top.opener.$("#OldAgeCardHandledByMeGrid").datagrid('reload');
						}
						if(top.opener.$("#OldAgeCardListByAuthGrid")){
							top.opener.$("#OldAgeCardListByAuthGrid").datagrid('reload');
						}
						//??????????????????portal
						top.opener.loadByMeHandledTaskDatas();
					}
					window.top.close();
				}
			}
			
			function doDelLQUploadMater(fileId, attachKey){
				art.dialog.confirm("???????????????????????????????", function(){
		    		var layload = layer.load("????????????????????????");
					$.post("fileAttachController.do?multiDel",{
							selectColNames: fileId
					    }, function(responseText, status, xhr){
					    	layer.close(layload);
					    	var resultJson = $.parseJSON(responseText);
							if(resultJson.success == true){
								//????????????span
					    		$("#"+fileId).remove();
								if(attachKey == '000000000GF141701_11'){
									$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_ID']").val('');
									$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_PATH']").val('');
									$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_NAME']").val('');
								}else if(attachKey == '000000000GF141701_12'){
									$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_ID']").val('');
									$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_PATH']").val('');
									$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_NAME']").val('');
								}else if(attachKey == '000000000GF141701_13'){
									$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_ID']").val('');
									$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_PATH']").val('');
									$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_NAME']").val('');
								}else if(attachKey == '000000000GF141701_14'){
									$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_ID']").val('');
									$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_PATH']").val('');
									$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_NAME']").val('');
								}
					    		$.each(SWFUpload.instances,function(n,swfObject) {   
						   		 	if(swfObject.settings.button_placeholder_id == attachKey){
						   		 		swfObject.setStats({successful_uploads: 0});
						   			}
					            }); 
							}
					    });
				});
			}
			
			function doReadCard(){
				$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/readIdCard.jsp", {
				    title: "???????????????XF660R-??????????????????",
					width: "750px",
					lock: true,
					resize: true,
					height: "400px",
					close: function(){
						var idCardData = art.dialog.data("idCardReadInfo");
						if(idCardData){
							$('#OldAgeCardNewForm').find("input[name='SQRSFZ']").val(idCardData.idNum);
							$('#OldAgeCardNewForm').find("input[name='SQRMC']").val(idCardData.name);
							$('#OldAgeCardNewForm').find("input[name='APPLICANT_NATION']").val(idCardData.nation);
							$('#OldAgeCardNewForm').find("input[name='APPLICANT_ADDR']").val(idCardData.address);
							if(idCardData.sex == '???'){
			    				$('#OldAgeCardNewForm').find("select[name='SQRXB']").val(1);
			    			}else if(idCardData.sex == '???'){
			    				$('#OldAgeCardNewForm').find("select[name='SQRXB']").val(2);
			    			}
							$('#OldAgeCardNewForm').find("input[name='APPLICANT_BIRTHDAY']").val(idCardData.birthYear+'-'+idCardData.birthMonth+'-'+idCardData.birthDay);
							$('#OldAgeCardNewForm').find("input[name='APPLICANT_CERTDEPT']").val(idCardData.signDept);
							$('#OldAgeCardNewForm').find("input[name='APPLICANT_CERTPERIOD']").val(idCardData.validDateBY+'.'+idCardData.validDateBM+'.'+idCardData.validDateBD+'-'+idCardData.validDateEY+'.'+idCardData.validDateEM+'.'+idCardData.validDateED);
							AppUtil.ajaxProgress({
								url: 'oldAgeCardController.do?applyCheck',
								params: {idNum:idCardData.idNum},
								callback: function(resultJson){
									if(resultJson.success){
										var applyInfo = $.parseJSON(resultJson.jsonString);
										$('#OldAgeCardNewForm').find("input[name='APPLICANT_AGE']").val(applyInfo.age);
										$('#OldAgeCardNewForm').find("select[name='CARD_TYPE']").val(applyInfo.cardType);
				    					$('#OldAgeCardNewForm').find("select[name='CARD_TYPE']").trigger('change');
									}else{
										alert(resultJson.msg);
				    					if(window.top){
				    						window.top.location.reload(true);
				    					}
									}
								}
			    			});
		    				art.dialog.removeData("idCardReadInfo");
						}
					}
				});
			}
			
			function doReadCard4Agent(){
				$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/readIdCard.jsp", {
				    title: "???????????????XF660R-??????????????????",
					width: "750px",
					lock: true,
					resize: true,
					height: "400px",
					close: function(){
						var idCardData = art.dialog.data("idCardReadInfo");
						if(idCardData){
							$('#OldAgeCardNewForm').find("input[name='JBR_ZJHM']").val(idCardData.idNum);
							$('#OldAgeCardNewForm').find("input[name='JBR_NAME']").val(idCardData.name);
							$('#OldAgeCardNewForm').find("input[name='JBR_ADDRESS']").val(idCardData.address);
							if(idCardData.sex == '???'){
			    				$('#OldAgeCardNewForm').find("select[name='JBR_SEX']").val(1);
			    			}else if(idCardData.sex == '???'){
			    				$('#OldAgeCardNewForm').find("select[name='JBR_SEX']").val(2);
			    			}
							$('#OldAgeCardNewForm').find("input[name='JBR_NATION']").val(idCardData.nation);
							$('#OldAgeCardNewForm').find("input[name='JBR_BIRTHDAY']").val(idCardData.birthYear+'-'+idCardData.birthMonth+'-'+idCardData.birthDay);
							$('#OldAgeCardNewForm').find("input[name='JBR_CERTDEPT']").val(idCardData.signDept);
							$('#OldAgeCardNewForm').find("input[name='JBR_CERTPERIOD']").val(idCardData.validDateBY+'.'+idCardData.validDateBM+'.'+idCardData.validDateBD+'-'+idCardData.validDateEY+'.'+idCardData.validDateEM+'.'+idCardData.validDateED);
		    				art.dialog.removeData("idCardReadInfo");
						}
					}
				});
			}
			
			function doReadCard4Lqr(){
				$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/readIdCard.jsp", {
				    title: "???????????????XF660R-??????????????????",
					width: "750px",
					lock: true,
					resize: true,
					height: "400px",
					close: function(){
						var idCardData = art.dialog.data("idCardReadInfo");
						if(idCardData){
							$('#OldAgeCardNewForm').find("input[name='LQR_IDNUM']").val(idCardData.idNum);
							$('#OldAgeCardNewForm').find("input[name='LQR_NAME']").val(idCardData.name);
							$('#OldAgeCardNewForm').find("input[name='LQR_ADDR']").val(idCardData.address);
		    				art.dialog.removeData("idCardReadInfo");
						}
					}
				});
			}
			
			function doFaceComparePost(name, idNum, fileId){
				AppUtil.ajaxProgress({
					url: 'oldAgeCardController.do?faceCompare',
					params: {name:name, idNum:idNum, fileId:fileId},
					callback: function(resultJson){
						if(resultJson.success){
							var faceResultJson = $.parseJSON(resultJson.jsonString);
							if(faceResultJson.result == 1){
								$('#OldAgeCardNewForm').find("input[name='FACE_CHECKRESULT']").val(faceResultJson.checkresult);
								$('#OldAgeCardNewForm').find("input[name='FACE_VERIFY']").val(faceResultJson.verify);
								var content;
								//?????????1(???????????????)0(???????????????)-1(??????????????????)-2(??????????????????)
								if(faceResultJson.checkresult == -2){
									content = "??????????????????????????????&nbsp;<a href=\"javascript:void(0);\" onclick=\"doFaceComparePost('"+name+"','"+idNum+"','"+fileId+"');\">[????????????]</a>";
								}else if(faceResultJson.checkresult == -1){
									content = "???????????????????????????????????????????????????";
								}else if(faceResultJson.checkresult == 1){
									content = "????????????????????????????????????????????????0???";
								}else if(faceResultJson.checkresult == 0){
									content = "????????????????????????????????????????????????"+faceResultJson.verify+"???";
								}
								$("#faceVerifyTip").html(content);
							}else{
								var content = "??????????????????????????????&nbsp;<a href=\"javascript:void(0);\" onclick=\"doFaceComparePost('"+name+"','"+idNum+"','"+fileId+"');\">[????????????]</a>";
								$("#faceVerifyTip").html(content);
							}
						}else{
							$("#faceVerifyTip").html("");
							alert(resultJson.msg);
						}
					}
				});
			}
			
			function doShowAgentPanel(){
				var val = $('input:checkbox[name="SFXSJBRXX"]:checked').val();
				if(val == null){
					$("#AGENT_PANEL").attr("style", "display:none;");
					$("#AGENT_MATER_PANEL").attr("style", "display:none;");
				}else{
					$("#AGENT_PANEL").attr("style", "");
					$("#AGENT_MATER_PANEL").attr("style", "");
				}
			}
			
			function onSQRPhotoScanBtnClick(){
				var name = $('#OldAgeCardNewForm').find("input[name='SQRSFZ']").val();
				var idNum = $('#OldAgeCardNewForm').find("input[name='SQRMC']").val();
				if(name == '' || idNum == ''){
					alert("?????????????????????????????????????????????????????????");
	           		return;
				}
				var uploadUserId = $("input[name='uploadUserId']").val();
				var uploadUserName = $("input[name='uploadUserName']").val();
				$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/scan.jsp?uploadPath=oldAgeCard&attachKey=000000000GF141701_02"+
				              "&uploadUserId="+uploadUserId+"&uploadUserName="+uploadUserName+"&busTableName=T_BSFW_OLDAGECARD&scanLimit=1", {
				    title: "???????????????XF660R-???????????????",
					width: "810px",
					lock: true,
					resize: true,
					height: "97%",
					close: function(){
					    var resultJsonInfo = art.dialog.data("docScannerResult");
						if(resultJsonInfo && resultJsonInfo.length > 0){
							var mater = resultJsonInfo[0];
							$('#000000000GF141701_02_pic').attr('src', "<%=basePath%>/"+mater.filePath);
		            		$('#OldAgeCardNewForm').find("input[name='APPLICANT_AVATAR']").val(mater.filePath);
		            		var name = $('#OldAgeCardNewForm').find("input[name='SQRMC']").val();
		            		var idNum = $('#OldAgeCardNewForm').find("input[name='SQRSFZ']").val();
		            		doFaceComparePost(name, idNum, mater.fileId);
							art.dialog.removeData("docScannerResult");
						}
					}
				});
			}
			
			function onPhotoScanBtnClick(attachKey){
				var uploadUserId = $("input[name='uploadUserId']").val();
				var uploadUserName = $("input[name='uploadUserName']").val();
				$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/scan.jsp?uploadPath=oldAgeCard&attachKey="+attachKey+
			              "&uploadUserId="+uploadUserId+"&uploadUserName="+uploadUserName+"&busTableName=T_BSFW_OLDAGECARD&scanLimit=1", {
				    title: "???????????????XF660R-???????????????",
					width: "810px",
					lock: true,
					resize: true,
					height: "97%",
					close: function(){
					    var resultJsonInfo = art.dialog.data("docScannerResult");
						if(resultJsonInfo && resultJsonInfo.length > 0){
							var mater = resultJsonInfo[0];
							if(attachKey == '000000000GF141701_11'){
								$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_ID']").val(mater.fileId);
								$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_PATH']").val(mater.filePath);
			            		$('#OldAgeCardNewForm').find("input[name='CERTPHOTO_NAME']").val(mater.fileName);
							}else if(attachKey == '000000000GF141701_12'){
								$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_ID']").val(mater.fileId);
								$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_PATH']").val(mater.filePath);
			            		$('#OldAgeCardNewForm').find("input[name='LQRPHOTO_NAME']").val(mater.fileName);
							}else if(attachKey == '000000000GF141701_13'){
								$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_ID']").val(mater.fileId);
								$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_PATH']").val(mater.filePath);
			            		$('#OldAgeCardNewForm').find("input[name='JBRFRONTCARD_NAME']").val(mater.fileName);
							}else if(attachKey == '000000000GF141701_14'){
								$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_ID']").val(mater.fileId);
								$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_PATH']").val(mater.filePath);
			            		$('#OldAgeCardNewForm').find("input[name='JBRBACKCARD_NAME']").val(mater.fileName);
							}
		            		var spanHtml = "<p id=\""+mater.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
		            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+mater.fileId+"');\">");
		            		spanHtml += "<font color=\"blue\">"+mater.fileName+"</font></a>";
		            		spanHtml += "<a href=\""+__ctxPath+"/"+mater.filePath+"\" data-lightbox=\""+mater.fileId+"\" data-title=\""+mater.fileName+"\"><font color=\"blue\">??????</font></a>";
		            		spanHtml += "<a href=\"javascript:void(0);\" onclick=\"doDelLQUploadMater('"+mater.fileId+"','"+attachKey+"');\" ><font color=\"red\">??????</font></a></p>";
		            		$("#UploadedFiles_"+attachKey).html(spanHtml);
							art.dialog.removeData("docScannerResult");
						}
					}
				});
			}
		</script>
    </head>
    <body style="margin:0px;background-color: #f7f7f7;">
        <form id="OldAgeCardNewForm" method="post">
            <div id="OldAgeCardNewFormDiv">
                <!--==========????????????????????? ===========-->
                <%-- ??????????????????(1:???????????? 2:????????????) --%>
                <input type="hidden" name="BSYHLX" value="1"/>
                <input type="hidden" name="PROJECT_NAME"/>
                <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
			    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
			    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
			    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
			    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
			    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
			    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
			    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
			    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
			    <!-- ????????????????????????????????? -->
			    <input type="hidden" name="APPLICANT_AVATAR"/>
			    <!-- ?????????????????? -->
			    <input type="hidden" name="ACCEPTDEPT_CODE"/>
			    <!-- ?????????????????? -->
			    <input type="hidden" name="FACE_CHECKRESULT"/>
			    <input type="hidden" name="FACE_VERIFY"/>
			    <!-- ????????????????????? -->
			    <input type="hidden" name="CERTPHOTO_ID"/>
			    <input type="hidden" name="CERTPHOTO_PATH"/>
			    <input type="hidden" name="CERTPHOTO_NAME"/>
			    <!-- ????????????????????? -->
			    <input type="hidden" name="LQRPHOTO_ID"/>
			    <input type="hidden" name="LQRPHOTO_PATH"/>
			    <input type="hidden" name="LQRPHOTO_NAME"/>
			    <!-- ???????????????????????????????????? -->
			    <input type="hidden" name="JBRFRONTCARD_ID"/>
			    <input type="hidden" name="JBRFRONTCARD_PATH"/>
			    <input type="hidden" name="JBRFRONTCARD_NAME"/>
			    <!-- ???????????????????????????????????? -->
			    <input type="hidden" name="JBRBACKCARD_ID"/>
			    <input type="hidden" name="JBRBACKCARD_PATH"/>
			    <input type="hidden" name="JBRBACKCARD_NAME"/>
                <!--==========????????????????????? ===========-->
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
                    <tr>
                        <th colspan="4">????????????</th>
                    </tr>
                    <tr>
                        <td class="tab_width" style="width:15%;">???????????????</td>
                        <td style="width:35%;">${serviceItem.ITEM_NAME}</td>
                        <td class="tab_width" style="width:15%;">???????????????</td>
                        <td style="width:35%;">????????????</td>
                    </tr>
                    <tr>
                        <td class="tab_width">???????????????</td>
                        <td>??????????????????/?????????</td>
                        <td class="tab_width">???????????????</td>
                        <td>?????????</td>
                    </tr>
                    <tr>
                        <td class="tab_width">????????????</td>
                        <td id="EXEID_TD"></td>
                        <td class="tab_width">?????????</td>
                        <td>????????????????????????????????????</td>
                    </tr>
                    <tr>
                        <td class="tab_width">???????????????</td>
                        <td>
                            <eve:eveselect clazz="tab_text3" dataParams="OldAgeCardApplySource" dataInterface="dictionaryService.findDatasForSelect"
							               defaultEmptyText="?????????????????????" name="BUSINESS_SOURCE"></eve:eveselect>
                        </td>
                        <td id="APPLY_SOURCE_TH" class="tab_width"></td>
                        <td id="APPLY_SOURCE_TD"></td>
                    </tr>
                </table>
                
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
                    <tr>
						<th colspan="4">????????????????????????</th>
					</tr>
					<tr>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text2 validate[required,custom[vidcard]]" maxlength="18" name="SQRSFZ"/>
					        <input id="SQRID_READBTN" type="button" onclick="doReadCard();" class="eflowbutton" value="??????"/>
					    </td>
					    <td class="tab_width" style="width:15%;" rowspan="6"><font class="tab_color">*</font>?????????????????????</td>
					    <td rowspan="6" style="width:35%;">
					        <div style="width:180px;text-align:center;">
					            <img id="000000000GF141701_02_pic" style="width:105px;height:140px;" src="<%=path%>/webpage/common/images/nopic.jpg"/>
					            <div id="000000000GF141701_02_div"></div>
					        </div>
					        <span id="faceVerifyTip" style="color:red;"></span>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="SQRMC"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <eve:eveselect clazz="tab_text3 validate[required]" dataParams="sex" dataInterface="dictionaryService.findDatasForSelect"
							               defaultEmptyText="???????????????" name="SQRXB"></eve:eveselect>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="APPLICANT_NATION"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="16" name="APPLICANT_BIRTHDAY"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="62" name="APPLICANT_ADDR"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="APPLICANT_CERTDEPT"/>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="APPLICANT_CERTPERIOD"/>
					    </td>
					</tr>
                </table>
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
                    <tr>
						<th colspan="4">?????????????????????</th>
					</tr>
					<tr>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>?????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text2 validate[required]" maxlength="3" name="APPLICANT_AGE"/>
					    </td>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text2 validate[required]" maxlength="62" name="SQRLXDZ"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width">???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[custom[mobilePhoneLoose]]" maxlength="11" name="SQRSJH"/>
					    </td>
					    <td class="tab_width">???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[custom[fixPhoneWithAreaCode]]" maxlength="16" name="SQRDHH"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????????????????</td>
					    <td id="OldAgeCardTypeTd">
					        <eve:eveselect clazz="tab_text3 validate[required]" dataParams="OldAgeCardType" dataInterface="dictionaryService.findDatasForSelect"
							               defaultEmptyText="?????????????????????????????????" name="CARD_TYPE"></eve:eveselect>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="CERTIFICATE_DEPT" value="???????????????????????????"/>
					    </td>
					</tr>
					<tr id="CARDNUM_CERTDATE_TR">
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="9" name="CARD_NUM"/>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="16" name="CERTIFICATE_DATE"/>
					    </td>
					</tr>
                </table>
				<%--???????????????????????????????????? --%>
				<jsp:include page="../../business/oldAgeCard/applyMaterList.jsp">
				    <jsp:param name="isWebsite" value="2"/>
				</jsp:include>
				<%--???????????????????????????????????? --%>
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
				    <tr>
						<th colspan="4">???????????????</th>
					</tr>
					<tr>
					    <td class="tab_width" style="width:15%;">????????????????????????</td>
					    <td colspan="3">
					        <input type="checkbox" name="SFXSJBRXX" value="1" onclick="doShowAgentPanel();">??????????????????????????????????????????
					    </td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="AGENT_PANEL" <c:if test="${execution.SFXSJBRXX!='1'}">style="display: none;"</c:if>>
					<tr>
						<th colspan="4">????????????????????????</th>
					</tr>
					<tr>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text2 validate[required,custom[vidcard]]" maxlength="18" name="JBR_ZJHM"/>
					        <input id="JBRID_READBTN" type="button" onclick="doReadCard4Agent();" class="eflowbutton" value="??????"/>
					    </td>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>?????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="JBR_NAME"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <eve:eveselect clazz="tab_text3 validate[required]" dataParams="sex" dataInterface="dictionaryService.findDatasForSelect"
							               defaultEmptyText="???????????????" name="JBR_SEX"></eve:eveselect>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="JBR_NATION"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="16" name="JBR_BIRTHDAY"/>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>?????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="62" name="JBR_ADDRESS"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="JBR_CERTDEPT"/>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="30" name="JBR_CERTPERIOD"/>
					    </td>
					</tr>
					<tr>
						<th colspan="4">?????????????????????</th>
					</tr>
					<tr>
					    <td class="tab_width"><font class="tab_color">*</font>?????????????????????</td>
					    <td>
					        <eve:eveselect clazz="tab_text3 validate[required]" dataParams="relation" dataInterface="dictionaryService.findDatasForSelect"
							               defaultEmptyText="???????????????????????????" name="JBR_RELATION"></eve:eveselect>
					    </td>
					    <td class="tab_width">???????????????</td>
					    <td>
					        <input type="text" class="tab_text2" maxlength="11" name="JBR_MOBILE"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width">???????????????</td>
					    <td>
					        <input type="text" class="tab_text2" maxlength="16" name="JBR_LXDH"/>
					    </td>
					    <td class="tab_width"><font class="tab_color">*</font>???????????????</td>
					    <td>
					        <input type="text" class="tab_text2 validate[required]" maxlength="62" name="JBR_LXDZ"/>
					    </td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro1" id="AGENT_MATER_PANEL" <c:if test="${execution.SFXSJBRXX!='1'}">style="display: none;"</c:if>>
				    <tr>
				        <c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
				            <th colspan="5">?????????????????????</th>
				        </c:if>
				        <c:if test="${empty nodeConfig || nodeConfig.UPLOAD_FILES!='1'}">
				            <th colspan="4">?????????????????????</th>
				        </c:if>
				    </tr>
				    <tr>
				        <td class="tab_width1" width="50px">??????</td>
				        <td class="tab_width1" width="400px">????????????</td>
				        <td class="tab_width1" width="80px">????????????</td>
				        <td class="tab_width1">??????</td>
				        <c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
						<td class="tab_width1" width="200px">????????????</td>
						</c:if>
				    </tr>
				    <tr>
				        <td>1</td>
				        <td><font class="tab_color">*</font>??????????????????????????????</td>
				        <td>??????1???</td>
				        <td>
				            <div id="UploadedFiles_000000000GF141701_13"></div>
				        </td>
				        <c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
				        <td>
				            <div id="div1_000000000GF141701_13">
								 <a href="javascript:void(0);" onclick="openJbrfrontcardImageUploadDialog()" style="float:left">
									<img id="000000000GF141701_13" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
				                <div id="000000000GF141701_13__SCAN" style="float:left">
				                    <a onclick="onPhotoScanBtnClick('000000000GF141701_13');" href="javascript:void(0);">
				                        <img src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>
				                    </a>
				                </div>
				            </div>
				        </td>
				        </c:if>
				    </tr>
				    <tr>
				        <td>1</td>
				        <td><font class="tab_color">*</font>??????????????????????????????</td>
				        <td>??????1???</td>
				        <td>
				            <div id="UploadedFiles_000000000GF141701_14"></div>
				        </td>
				        <c:if test="${not empty nodeConfig && nodeConfig.UPLOAD_FILES=='1'}">
				        <td>
				            <div id="div1_000000000GF141701_14">
								 <a href="javascript:void(0);" onclick="openJbrbackcardImageUploadDialog()" style="float:left">
									<img id="000000000GF141701_14" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
				                <div id="000000000GF141701_14__SCAN" style="float:left">
				                    <a onclick="onPhotoScanBtnClick('000000000GF141701_14');" href="javascript:void(0);">
				                        <img src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>
				                    </a>
				                </div>
				            </div>
				        </td>
				        </c:if>
				    </tr>
				</table>
				<!-- <c:if test="${!EFLOW_IS_START_NODE }">  -->
				<table id="LQR_PANEL" cellpadding="0" cellspacing="1" class="tab_tk_pro" style="display:none;">
				    <tr>
				        <th colspan="4">???????????????</th>
				    </tr>
				    <tr>
				        <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
				        <td style="width:85%;" colspan="3">
				            <ul>
				                <li style="display:inline;padding:0 20px;">
				                    <input type="radio" name="LQR_TYPE" value="0" class="validate[required]"/>???????????????
				                </li>
				                <li style="display:inline;padding:0 20px;">
				                    <input type="radio" name="LQR_TYPE" value="1" class="validate[required]"/>???????????????
				                </li>
				                <li style="display:inline;padding:0 20px;">
				                    <input type="radio" name="LQR_TYPE" value="2" class="validate[required]"/>???????????????
				                </li>
				            </ul>
				        </td>
				    </tr>
				    <tr id="LQRID_LQRNAME_TR" style="display:none;">
				        <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
				        <td style="width:35%;">
				            <input type="text" class="tab_text2 validate[required,custom[vidcard]]" maxlength="18" name="LQR_IDNUM"/>
					        <input id="LQRID_READBTN" type="button" onclick="doReadCard4Lqr();" class="eflowbutton" value="??????"/>
				        </td>
				        <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>?????????</td>
				        <td style="width:35%;">
				            <input type="text" class="tab_text2 validate[required]" maxlength="30" name="LQR_NAME"/>
				        </td>
				    </tr>
				    <tr id="LQRMOBILE_LQRADDR_TR" style="display:none;">
				        <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
				        <td style="width:35%;">
				            <input type="text" class="tab_text2 validate[required]" maxlength="16" name="LQR_MOBILE"/>
				        </td>
				        <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
				        <td style="width:35%;">
				            <input type="text" class="tab_text2 validate[required]" maxlength="62" name="LQR_ADDR"/>
				        </td>
				    </tr>
				</table>
				<table id="LQR_MATER_PANEL" cellpadding="0" cellspacing="1" class="tab_tk_pro1" style="display:none;">
				    <tr>
				        <td class="tab_width1" width="50px">??????</td>
				        <td class="tab_width1" width="400px">????????????</td>
				        <td class="tab_width1" width="80px">????????????</td>
				        <td class="tab_width1">??????</td>
						<td class="tab_width1" width="200px" style="display:none;" id="LQMARER_TD">????????????</td>
				    </tr>
				    <tr>
				        <td>1</td>
				        <td><font class="tab_color">*</font>??????????????????????????????</td>
				        <td>?????????1???</td>
				        <td>
				            <div id="UploadedFiles_000000000GF141701_11"></div>
				        </td>
				        <td style="display:none;" id="LNRYDZOPR_TD">
				            <div id="div1_000000000GF141701_11">
								 <a href="javascript:void(0);" onclick="openCertphotoImageUploadDialog()" style="float:left">
									<img id="000000000GF141701_11" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
				                <div id="000000000GF141701_11__SCAN" style="float:left">
				                    <a onclick="onPhotoScanBtnClick('000000000GF141701_11');" href="javascript:void(0);">
				                        <img src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>
				                    </a>
				                </div>
				            </div>
				        </td>
				    </tr>
				    <tr>
				        <td>2</td>
				        <td><font class="tab_color">*</font>?????????????????????</td>
				        <td>?????????1???</td>
				        <td>
				            <div id="UploadedFiles_000000000GF141701_12"></div>
				        </td>
				        <td style="display:none;" id="LQRXCZPOPR_TD">
				            <div id="div1_000000000GF141701_12">
								 <a href="javascript:void(0);" onclick="openLqrphotoImageUploadDialog()" style="float:left">
									<img id="000000000GF141701_12" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
				                <div id="000000000GF141701_12__SCAN" style="float:left">
				                    <a onclick="onPhotoScanBtnClick('000000000GF141701_12');" href="javascript:void(0);">
				                        <img src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>
				                    </a>
				                </div>
				            </div>
				        </td>
				    </tr>
				</table>
				<!-- </c:if> -->
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
				    <tr>
						<th colspan="4">????????????</th>
					</tr>
					<tr>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>?????????????????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text validate[required]" name="OPERATOR_NAME" maxlength="30"/>
					    </td>
					    <td class="tab_width" style="width:15%;"><font class="tab_color">*</font>???????????????</td>
					    <td style="width:35%;">
					        <input type="text" class="tab_text2 validate[required]" name="ACCEPTDEPT_NAME" maxlength="126"/>
					    </td>
					</tr>
					<tr>
					    <td class="tab_width" style="width:15%;">?????????</td>
					    <td colspan="3">
					        <textarea name="BUSINESS_REMARK" style="width:90%;height:100px;" class="validate[maxSize[510]]"></textarea>
					    </td>
					</tr>
			    </table>
            </div>
        </form>
        <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
	        <embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0"></embed>
	    </object>
    </body>
</html>