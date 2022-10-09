<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
	String uploadPath = request.getParameter("uploadPath");
	String attachKey = request.getParameter("attachKey");
	String uploadUserId = request.getParameter("uploadUserId");
	String uploadUserName = request.getParameter("uploadUserName");
	String matreClsm = request.getParameter("matreClsm");
	
	request.setAttribute("matreClsm", matreClsm);
	request.setAttribute("uploadPath", uploadPath);
	request.setAttribute("attachKey", attachKey);
	request.setAttribute("uploadUserId", uploadUserId);
	request.setAttribute("uploadUserName", uploadUserName);	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head><base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
	<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>

	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <title>PadInputCtl</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/ptwgform/scan/css/style.css">
    <script type="text/javascript">
    	function DoGWQ_StartFace(){
			$("#lineName").val("");
			$("#lineCardNo").val("");
			$("#facePic").attr("src","");
    		GWQ.GWQ_CancelOperate();
			var ret=GWQ.DoGWQ_StartFace();
			if(ret==0){
				
			}else{
				parent.art.dialog({
					content : "启动失败，返回错误码为"+ret,
					icon : "error",
					time : 3,
					ok : true
				});
			}
		}
		
    	function DoGWQ_GetFrameWithIDInfo(){
			$("#lineName").val("");
			$("#lineCardNo").val("");
			$("#facePic").attr("src","");			
    		GWQ.GWQ_CancelOperate();
			var ret=GWQ.DoGWQ_GetFrameWithIDInfo();
			if(ret==0){
				
			}else{
				parent.art.dialog({
					content : "启动失败，返回错误码为"+ret,
					icon : "error",
					time : 3,
					ok : true
				});
			}
		}
		
		function UploadFileToHttp(){
			var businessCode = $('#businessCode').combobox('getValue');
			var lineName = document.getElementById("lineName").value;
			var lineCardNo = document.getElementById("lineCardNo").value;
			var LINE_ZJLX = document.getElementById("LINE_ZJLX").value;
			var isVip = 0;
			if(businessCode == "" || businessCode == null || businessCode == undefined
					||lineName == "" || lineName == null || lineName == undefined
					||lineCardNo == "" || lineCardNo == null || lineCardNo == undefined
					||LINE_ZJLX == "" || LINE_ZJLX == null || LINE_ZJLX == undefined){
				alert("请补充取号信息！");
				return;
			}
			var imgStrAll = $('#facePicInfo').val();
			$.post( "newCallController.do?videoInputTakeNo",{
				base64Code:imgStrAll, 
				attachKey:"${attachKey}",
				uploadUserId:"${uploadUserId}",
				uploadUserName:"${uploadUserName}",
				busTableName:"T_CKBS_QUEUERECORD",
				businessCode:businessCode,
				lineName:lineName,
				lineCardNo:lineCardNo,
				LINE_ZJLX:LINE_ZJLX,
				isVip:isVip},
				function(responseText1, status, xhr) {
					parent.$("#callingNewGrid").datagrid("reload");
					AppUtil.closeLayer();
			});
		}
		
		function changetoVideo(){
			window.location.href="<%=basePath%>/webpage/callnew/queuing/VideoInputTakeNoCtl.jsp?";
		}
    </script>
        
	<script language="javascript" event="OnAfterGWQ_StartFace(ErrorCode,JsonData)" for="GWQ">
		var errorCode = ErrorCode;
		var JsonData = JsonData;
		if(errorCode==0){
			var obj = JSON.parse(JsonData);
			$("#lineName").val(obj.name);
			$("#lineCardNo").val(obj.id_num);
			$("#facePicInfo").val(obj.base64_Face);
			$("#facePic").attr("src","data:image/jpeg;base64,"+obj.base64_Face);
		}else{
			alert("人脸比对不通过");
		}
	</script>
	<script language="javascript" event="OnAfterGWQ_GetFrameWithIDInfo(ErrorCode,JsonData)" for="GWQ">
		var errorCode = ErrorCode;
		var JsonData = JsonData;		
		if(errorCode==0){
			var obj = JSON.parse(JsonData);			
			AppUtil.ajaxProgress({
				url : "newCallController.do?mztIdentify",
				params : {
					idCard : obj.id_num,
					name : obj.name,
					faceImg : obj.base64_ID
				},
				callback : function(resultJson) {
					if(resultJson.success){			
						$("#lineName").val(obj.name);
						$("#lineCardNo").val(obj.id_num);
						$("#facePicInfo").val(obj.base64_ID);
					}else{
						alert(resultJson.msg);
					}
				}
			});
			$("#facePic").attr("src","data:image/jpeg;base64,"+obj.base64_ID);
		}else{
			alert("未能成功获取办理人员填写的个人信息和照片");
		}
	</script>
</head>
<body>
	<div class="gpy">
    	<div class="clearfix">
        	<div class="lfloat">
	            <div class="gpy-camera">
	            	<img src="" id="facePic" width="100%" height="100%"/>
	    		</div>
	        </div>
	        <div class="rfloat">
	        	<div class="gpy-btn"><a href="javascript:void(0);" onclick="changetoVideo()">切换高拍仪取号</a></div>
	        	<div class="gpy-btn"><a href="javascript:void(0);" onclick="DoGWQ_StartFace()">有身份证取号</a></div>
	        	<div class="gpy-btn"><a href="javascript:void(0);" onclick="DoGWQ_GetFrameWithIDInfo()">无身份证取号</a></div>
	        </div>
    	</div>
	    <div  id="AssistFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" id="facePicInfo" />
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">证件类型：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" value="SF" name="LINE_ZJLX" id="LINE_ZJLX">
						</eve:eveselect>
					</td>
					</td>
					<td colspan="2"><span style="width: 100px;float:left;text-align:right;">姓名：</span>
						<input
						type="text" style="width:150px;float:left;"
						maxlength="8"
						class="eve-input validate[required]" name="lineName" id="lineName" readonly="readonly" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">证件号码：</span>
						<input type="text" style="width:150px;float:left;"
						maxlength="18" id="lineCardNo" readonly="readonly"
						class="eve-input validate[required]"
						name="lineCardNo" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td><span
						style="width: 100px;float:left;text-align:right;">办理业务：</span> <input class="easyui-combobox"
								style="width:235px;" name="businessCode" id="businessCode"
								data-options="url:'callSetController.do?loadBusiness&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'BUSINESS_CODE',textField:'BUSINESS_NAME',panelWidth: 235,panelHeight: '300' " />
								 <font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
						<input type="text" style="width:150px;float:left;"
						maxlength="18" id="phoneNo"
						class="eve-input validate[required]"
						name="phoneNo" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td></td>
				</tr>
			</table>
		</div>
		<br/>
        <div class="gpy-sub">
        	<!-- <a class="camera" onclick="grabimage()"><span><i></i>拍 照</span></a>
        	<a class="submit" onclick="StartRead()""><span><i></i>读 卡</span></a> -->
        	<a class="submit" onclick="UploadFileToHttp();"><span><i></i>确 认</span></a>
        	<a class="cancel" onclick="AppUtil.closeLayer();"><span><i></i>取 消</span></a>
        </div>
	</div>
	
</body>
</html>
