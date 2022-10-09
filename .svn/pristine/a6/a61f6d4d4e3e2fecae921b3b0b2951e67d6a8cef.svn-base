<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="<%=basePath%>webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>webpage/common/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>webpage/bsdt/applyform/css/applyform.css" />
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>
    <!-- lodop -->
    <script type="text/javascript" src="<%=basePath %>plug-in/lodop-6.198/jquery.cookie.js"></script>
    <script type="text/javascript" src="<%=basePath %>plug-in/lodop-6.198/licPrint.js"></script>
    <script type="text/javascript" src="<%=basePath %>plug-in/lodop-6.198/LodopFuncs.js"></script>
    <!-- lightbox2 -->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>plug-in/lightbox2/css/lightbox.min.css"/>
    <script type="text/javascript" src="<%=basePath %>plug-in/lightbox2/js/lightbox.min.js"></script>
    <style type="text/css">
	    .tab_tk_pro1 tr td{color: #000;}
	</style>
    <script type="text/javascript">
        $(document).ready(function(){
        	//初始化遗失声明上传按钮
        	AppUtil.bindSwfUpload({
				file_types: '*.jpg;*.jpeg;*.png;',
				file_size_limit: '20 MB',
				post_params: {
			        'uploadPath': 'applyform',
			        'uploadUserId': '${sessionScope.curLoginUser.userId}',
			        'uploadUserName': '${sessionScope.curLoginUser.fullname}',
			        'attachKey': '000000000GF141701_13',
			        'busTableName': 'T_BSFW_OLDAGECARD'
		        },
	            file_upload_limit: 30,
				file_queue_limit: 30,
	            button_placeholder_id: '000000000GF141701_13',
	            successful_uploads: null,
	            upload_success_handler: function(resultJson){
	            	if(resultJson.success){
		            	var fileId = resultJson.fileId;
	            		var fileName = resultJson.fileName;
	            		var attachKey = resultJson.attachKey;
	            		var filePath = resultJson.filePath;
	            		var isImg = resultJson.isImg;
	            		$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILEID']").val(fileId);
	            		$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_PATH']").val(filePath);
	            		$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILENAME']").val(fileName);
	            		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
	            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
	            		spanHtml += "<font color=\"blue\">"+fileName+"</font></a>";
	            		if(isImg == '1'){
	            			spanHtml += "<a href=\""+__ctxPath+"/"+filePath+"\" data-lightbox=\""+fileId+"\" data-title=\""+fileName+"\"><font color=\"blue\">预览</font></a>";
	            		}
	            		spanHtml += "<a href=\"javascript:void(0);\" onclick=\"doDelUploadMater('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>";
	            		$("#UploadedFiles_000000000GF141701_13").html(spanHtml);
	            	}
	            }
	    	});
        	
        	AppUtil.initWindowForm("OldAgeCardChangeForm", function(form, valid){
    			if(valid){
    				var stateFileId = $('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILEID']").val();
    				var stateFilePath = $('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_PATH']").val();
    				var stateFileName = $('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILENAME']").val();
    				if(stateFileId == '' || stateFileName == '' || stateFilePath == ''){
    					parent.art.dialog({
							content: '请打印并上传《福建省老年人优待证》遗失申明!',
							icon: "warning",
						    ok: true
						});
    					return;
    				}
    				var formData = $("#OldAgeCardChangeForm").serialize();
    				var url = $("#OldAgeCardChangeForm").attr("action");
    				AppUtil.ajaxProgress({
    					url: url,
    					params: formData,
    					callback: function(resultJson){
    						if(resultJson.success){
    							parent.art.dialog({
    								content: resultJson.msg,
    								icon: "succeed",
    								time: 3,
    							    ok: true
    							});
    							parent.$("#OldAgeCardListByAuthGrid").datagrid('reload');
    							AppUtil.closeLayer();
    						}else{
    							parent.art.dialog({
    								content: resultJson.msg,
    								icon: "error",
    							    ok: true
    							});
    						}
    					}
    				});
    			}
        	});
        });
        
        function doDelUploadMater(fileId, attachKey){
        	art.dialog.confirm("您确定要删除该文件吗?", function(){
	    		var layload = layer.load("正在提交请求中…");
				$.post("fileAttachController.do?multiDel",{
						selectColNames: fileId
				    }, function(responseText, status, xhr){
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							//移除目标span
				    		$("#"+fileId).remove();
				    		$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILEID']").val('');
				    		$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_PATH']").val('');
							$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILENAME']").val('');
				    		$.each(SWFUpload.instances,function(n,swfObject) {   
					   		 	if(swfObject.settings.button_placeholder_id == attachKey){
					   		 		swfObject.setStats({successful_uploads: 0});
					   			}
				            }); 
						}
				    });
			});
        }
        
        function onScanBtnClick(attachKey){
        	$.dialog.open("<%=path%>/webpage/business/DocScanner/XF660R/scan.jsp?uploadPath=oldAgeCard&attachKey="+attachKey+
		              "&uploadUserId=${sessionScope.curLoginUser.userId}&uploadUserName=${sessionScope.curLoginUser.fullname}&busTableName=T_BSFW_OLDAGECARD&scanLimit=1", {
			    title: "良田高拍仪XF660R-文档拍摄页",
				width: "810px",
				lock: true,
				resize: true,
				height: "97%",
				close: function(){
				    var resultJsonInfo = art.dialog.data("docScannerResult");
					if(resultJsonInfo && resultJsonInfo.length > 0){
						var mater = resultJsonInfo[0];
						$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILEID']").val(mater.fileId);
						$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_PATH']").val(mater.filePath);
	            		$('#OldAgeCardChangeForm').find("input[name='LOSTSTATE_FILENAME']").val(mater.fileName);
	            		var spanHtml = "<p id=\""+mater.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
	            		spanHtml += (" onclick=\"AppUtil.downLoadFile('"+mater.fileId+"');\">");
	            		spanHtml += "<font color=\"blue\">"+mater.fileName+"</font></a>";
	            		spanHtml += "<a href=\""+__ctxPath+"/"+mater.filePath+"\" data-lightbox=\""+mater.fileId+"\" data-title=\""+mater.fileName+"\"><font color=\"blue\">预览</font></a>";
	            		spanHtml += "<a href=\"javascript:void(0);\" onclick=\"doDelUploadMater('"+mater.fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>";
	            		$("#UploadedFiles_"+attachKey).html(spanHtml);
						art.dialog.removeData("docScannerResult");
					}
				}
			});
        }
        
        function doPrintLostState(){
        	var LODOP = getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
        	LODOP.PRINT_INITA(0, 0, 700, 906, "《福建省老年人优待证》遗失申明");
        	LODOP.SET_PRINT_PAGESIZE (1, 0, 0, "A4");
        	LODOP.SET_PRINT_MODE("NOCLEAR_AFTER_PRINT", true);
        	LODOP.SET_PRINT_STYLEA(0, "Stretch", 1);
        	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
        	LODOP.ADD_PRINT_URL(20, 13, "100%", "100%", '<%=basePath%>oldAgeCardController/printLostState.do?busId=${oldAgeCard.BUSINESS_ID }');
        	LODOP.PREVIEW();
        }
    </script>
</head>
<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
    <form id="OldAgeCardChangeForm" method="post" action="oldAgeCardController.do?updateUnregister">
        <div id="OldAgeCardChangeFormDiv" data-options="region:'center',split:true,border:false">
            <!--==========隐藏域部分开始 ===========-->
            <input type="hidden" name="BUSINESS_ID" value="${oldAgeCard.BUSINESS_ID }"/>
            <input type="hidden" name="LOSTSTATE_FILEID"/>
            <input type="hidden" name="LOSTSTATE_PATH"/>
			<input type="hidden" name="LOSTSTATE_FILENAME"/>
            <!--==========隐藏域部分结束 ===========-->
            <table class="tab_tk_pro" cellpadding="0" cellspacing="1">
                <tr>
					<th colspan="4">申请人信息</th>
				</tr>
				<tr>
				    <td class="tab_width" style="width:15%;">姓名</td>
				    <td style="width:35%;">${oldAgeCard.SQRMC }</td>
				    <td class="tab_width" style="width:15%;" rowspan="4">一寸红底彩照</td>
				    <td style="width:35%;" rowspan="4">
				        <img style="width:105px;height:140px;" src="<%=path%>/${oldAgeCard.APPLICANT_AVATAR}"/>
				    </td>
				</tr>
				<tr>
				    <td class="tab_width">身份证号</td>
				    <td>${oldAgeCard.SQRSFZ }</td>
				</tr>
				<tr>
				    <td class="tab_width">性别</td>
				    <td>${oldAgeCard.SQRXB == '1'?'男':'女' }</td>
				</tr>
				<tr>
				    <td class="tab_width">民族</td>
				    <td>${oldAgeCard.APPLICANT_NATION }</td>
				</tr>
				<tr>
				    <td class="tab_width">优待证类型</td>
				    <td>${oldAgeCard.CARD_TYPE == '1'?'绿证':'红证' }</td>
				    <td class="tab_width">优待证号</td>
				    <td>${oldAgeCard.CARD_NUM }</td>
				</tr>
				<tr>
				    <td class="tab_width">联系地址</td>
				    <td colspan="3">${oldAgeCard.SQRLXDZ }</td>
				</tr>
            </table>
            <table class="tab_tk_pro1" cellpadding="0" cellspacing="1">
                <tr>
                    <c:if test="${oldAgeCard.CARD_STATUS == '0' }">
                        <th colspan="4">材料信息</th>
                    </c:if>
                    <c:if test="${oldAgeCard.CARD_STATUS == '1' }">
                        <th colspan="3">材料信息</th>
                    </c:if>
				</tr>
				<tr>
					<td class="tab_width1" width="27%">材料名称</td>
					<td class="tab_width1" width="10%">材料打印</td>
					<td class="tab_width1">附件</td>
					<c:if test="${oldAgeCard.CARD_STATUS == '0' }">
					    <td class="tab_width1" width="25%">文件操作</td>
					</c:if>
				</tr>
				<tr>
				    <td><font class="tab_color">*</font>《福建省老年人优待证》遗失申明</td>
				    <td>
				        <a href="javascript:void(0);" onclick="doPrintLostState();"><font color="blue"><b>打印</b></font></a>
                    </td>
				    <td>
				        <c:if test="${oldAgeCard.CARD_STATUS == '0' }">
				            <div id="UploadedFiles_000000000GF141701_13"></div>
				        </c:if>
				        <c:if test="${oldAgeCard.CARD_STATUS == '1' }">
				            <div id="UploadedFiles_000000000GF141701_13">
				                <p>
				                    <a href="javascript:void(0);" style="cursor:pointer;" onclick="AppUtil.downLoadFile('${oldAgeCard.LOSTSTATE_FILEID }');">
				            		    <font color="blue">${oldAgeCard.LOSTSTATE_FILENAME }</font>
				            		</a>
				            		<a href="<%=basePath %>${oldAgeCard.LOSTSTATE_PATH }" data-lightbox="${oldAgeCard.LOSTSTATE_FILEID }" data-title="${oldAgeCard.LOSTSTATE_FILENAME }">
				                    	<font color="blue">预览</font>
				                    </a>
				            	</p>
				            </div>
				        </c:if>
				    </td>
				    <c:if test="${oldAgeCard.CARD_STATUS == '0' }">
				    <td>
				        <div id="div1_000000000GF141701_13">
		                	<div id="000000000GF141701_13__SC" style="width:80px;float:left;" >
			                    <img id="000000000GF141701_13" src="<%=path %>/webpage/bsdt/applyform/images/tab_btn_sc.png"/>
			                </div>
			                <div id="000000000GF141701_13__SCAN" style="float:left">
			                    <a onclick="onScanBtnClick('000000000GF141701_13');" href="javascript:void(0);">
			                        <img src="<%=path %>/webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>
			                    </a>
			                </div>
			            </div>
				    </td>
				    </c:if>
				</tr>
			</table>
        </div>
        <c:if test="${oldAgeCard.CARD_STATUS == '0' }">
        <div data-options="region:'south'" style="height:50px;">
            <div class="eve_buttons">
                <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight"/>
                <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();" />
            </div>
        </div>
        </c:if>
    </form>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0"> 
        <embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0"></embed>
    </object> 
</body>
</html>