<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>用户中心--平潭综合实验区行政服务中心</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<script type="text/javascript"
	src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>

<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>  
<!-- 引入验证库 -->
<eve:resources
	loadres="apputil,easyui,artdialog,json2,layer,validationegine,swfupload"></eve:resources>
<script type="text/javascript"> 

$(function(){
	var username = '${sessionScope.curLoginMember.YHMC}';
	if(null==username||""==username){
		window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login";
	}
        AppUtil.initWindowForm("WDCL", function(form, valid) {
            if (valid) {
                var formData = $("#WDCL").serialize();
                var url = $("#WDCL").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                              alert( resultJson.msg);
                              window.location.reload()
                        } else {
                            alert( resultJson.msg);
                        }
                    }
                });
            }
        }, "T_BSFW_USERRECADD");
// });
// $(function() {
	AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "JBPM6_FLOW_RESULT",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "RESULT_FILE_BTN",
		singleFileDivId : "RESULT_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='RESULT_FILE_URL']").val();
				var fileid=$("input[name='RESULT_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='RESULT_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='RESULT_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='RESULT_FILE_URL']").val(resultJson.filePath);
					$("input[name='RESULT_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#RESULT_FILE_DIV").children("a").eq(1).html());
				//$("#RESULT_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
				//获取文件ID
		        var fileId = resultJson.fileId;
	            //获取文件名称
	            var fileName = resultJson.fileName;
	            //获取材料KEY
	            var attachKey =resultJson.attachKey;
	            //获取文件路径
	            var filePath = resultJson.filePath;
	            //获取文件的类型
	            var fileType = resultJson.fileType;
	            //获取是否是图片类型
	            var isImg = resultJson.isImg;
				var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
	            spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
	            spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#fileListDiv").append(spanHtml); 
		}
	}); 
});
function bindScanCtrl(){
	var onlineBusTableName = "JBPM6_FLOW_RESULT";
	
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				initScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function initScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='RESULT_FILE_URL']").val();
		var fileid=$("input[name='RESULT_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='RESULT_FILE_URL']").val(fileurl+";"+resultJson[i].filePath);
			$("input[name='RESULT_FILE_ID']").val(fileid+";"+resultJson[i].fileId);
		}else{
			$("input[name='RESULT_FILE_URL']").val(resultJson[i].filePath);
			$("input[name='RESULT_FILE_ID']").val(resultJson[i].fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].fileId;
        //获取文件名称
        var fileName = resultJson[i].fileName;
        //获取材料KEY
        var attachKey =resultJson[i].attachKey;
        //获取文件路径
        var filePath = resultJson[i].filePath;
        //获取文件的类型
        var fileType = resultJson[i].fileType;
        //获取是否是图片类型
        var isImg = resultJson[i].isImg;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
        $("#fileListDiv").append(spanHtml); 
	}
}
function delUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='RESULT_FILE_URL']").val();
	var fileid=$("input[name='RESULT_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='RESULT_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='RESULT_FILE_ID']").val(arrayIds.join(";"));
}
function wdcllist(wdcllist){
	$("#wdcllist").html("");
    var newhtml = "";
    for(var i=0; i<wdcllist.length; i++){
	    newhtml +="<tr>"; 
	    newhtml += "<td width=\"40px\" valign=\"top\">"+(i+1)+"</td>";
	    newhtml += "<td width=\"200px\" valign=\"top\">"+wdcllist[i].FILE_NAME+"</td>";
	    newhtml += "<td width=\"100px\" valign=\"top\">"+wdcllist[i].CREATE_TIME+"</td>";
	    newhtml += "<td width=\"50px\" valign=\"top\">"+wdcllist[i].UPLOADER_NAME+"</td>";
	    newhtml += "<td width=\"100px\" valign=\"top\">";
    	newhtml += "<a href=\"javascript:void(0);\" onclick=\"deleteFlow('"
                +wdcllist[i].FILE_ID+"');\" class=\"userbtn3\">删除</a>";
		newhtml += "</td>";
	    newhtml +="</tr>"; 
    } 
    $("#wdcllist").html(newhtml);
}


function deleteFlow(fileId){
	art.dialog.confirm("您确定要删除该记录吗?", function() {
			var layload = layer.load('正在提交请求中…');  
			var selectColNames =fileId;
			$.post("userInfoController.do?multiDelMyFile",{
				   selectColNames:selectColNames
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
						reload();
					}else{
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

</head>
<body class="userbody">
	<%--开始编写头部文件 --%>
	<jsp:include page="../yhzx/head.jsp?currentNav=wdcl" />
	<%--结束编写头部文件 --%>
	<div class="container lbpadding20">
		<div class="main_t"></div>
		<div class="nmain clearfix">
			<%--开始引入用户中心的左侧菜单 --%>
			<jsp:include page="./yhmenu.jsp">
				<jsp:param value="wdcl" name="menuKey" />
			</jsp:include>
			<%--结束引入用户中心的左侧菜单 --%>
			<div class="nmainR">
				<div class="nmainRtitle" style="margin-top:0px;">
					<h3>我的材料</h3>
				</div>
				<div class="nuserC">
					<form id="WDCL" action="userInfoController.do?saveUserFile">
						<input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" /> 
						<input type="hidden" name="FILE_ID" id="FILE_ID" value="" />
						<input type="hidden" name="RESULT_FILE_URL" >
						<input type="hidden" name="RESULT_FILE_ID">
						<div>
							<table cellpadding="0" cellspacing="0" class="bstable3 tmargin12">
								<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
									<tr>
										<th>上传人姓名 <font class="dddl_platform_html_requiredFlag">*</font>
										</th>
										<td><input type="text"
											class="eve-input validate[required,maxSize[30]]"
											name="UPLOADER_NAME" value="${sessionScope.curLoginMember.YHMC}" />
										</td>
										<th>证件号码</th>
										<td><input type="text"
											class="eve-input validate[required,maxSize[18]],custom[vidcard]"
											name="SQRSFZ" value="${sessionScope.curLoginMember.ZJHM}" />
										</td>
									</tr>
									<tr>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
									</tr>
								</c:if>
								<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
									<tr>
										<th>上传人姓名 <font class="dddl_platform_html_requiredFlag">*</font>
										</th>
										<td><input type="text"
											class="eve-input validate[required,maxSize[30]]"
											name="UPLOADER_NAME" value="${sessionScope.curLoginMember.YHMC}" />
										</td>
										<th>证件号码</th>
										<td><input type="text"
											class="eve-input validate[required,maxSize[18]]"
											name="SQJG_CODE" value="${sessionScope.curLoginMember.ZZJGDM}" />
										</td>
									</tr>
									<tr>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
									</tr>
								</c:if>
		<tr>
			<th>附件</th>
			<td colspan="3">
				<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div><a id="RESULT_FILE_BTN"></a>
				<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
					<a href="javascript:bindScanCtrl()"><img id="${applyMater.MATER_CODE}"
					src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
				</div>
			</td>
		</tr>
		<tr>
			<th></th>
			<td colspan="3">
				<div style="width:100%;" id=fileListDiv></div>
			</td>
		</tr>
							</table>
								<div class="Ctext clearfix lbpadding32">
									<a href="javascript:void(0);" onclick="$('#WDCL').submit();"
										class="btn2">保 存</a>
								</div>
						</div>
					</form>
				</div>
				<div class="nmainRtitle" style="margin-top:0px;">
					<h3>材料信息</h3>
				</div>
				<table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
					<tr>
						<th width="40px">序号</th>
						<th width="200px">附件名称</th>
						<th width="100px">上传时间</th>
						<th width="50px">上传人</th>
						<th width="100px" style="background-image:none;">操作</th>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10"
					id="wdcllist">
				</table>
				<%--开始引入分页JSP --%>
				<jsp:include page="../common/page.jsp">
					<jsp:param value="userInfoController.do?wdcllist" name="pageURL" />
					<jsp:param value="wdcllist" name="callpage" />
					<jsp:param value="10" name="limitNum" />
				</jsp:include>
				<%--结束引入分页JSP--%>
			</div>
		</div>
	</div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<script type="text/javascript">jQuery(".nmainR").slide({titCell:".nmainRtitle1 li",mainCell:".nuserC"})</script>
	<%--结束编写尾部文件 --%>
</body>
</html>
