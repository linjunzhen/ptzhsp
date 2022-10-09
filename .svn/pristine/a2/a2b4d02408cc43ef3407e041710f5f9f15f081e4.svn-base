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
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <title>VideoInputCtl</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/ptwgform/scan/css/style.css">
    <script type="text/javascript">
        var previewPhotoStr = "";
        
		function thumbnailPath()
		{
			var gfp=new Array();
            $("input[name='selPhoto']:checked").each(function(){ 
            	gfp.push($(this).val()); 
			}); 
            return gfp;
		}
		function UploadFileToHttp(){
			var imgs = document.getElementById("previewPhoto").getElementsByTagName("img");
			//var num=EThumbnails.GetDisplayCount();
			var num=imgs.length;
			if(num>0){
				//alert("确认是否拍摄取号人照片及身份证人像面照片！");
				UploadFile();
			}else{
				alert("请选择要上传的图片！");
				return;
			}
		}
		function UploadFile(){
			var matreClsm = '${matreClsm}';
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
			var tp = thumbnailPath();
			if(tp.length<=1){
				alert("请选择要上传的图片,图片至少包括取号人照片及身份证人像面照片！");
				return;
			}
			var imgStr;
			var imgStrAll = "";
			for(var i=0;i<tp.length;i++){
				imgStr=tp[i];
				if(imgStr=='' || imgStr==null || imgStr==undefined){
					continue;
				}
				imgStrAll = imgStrAll+";"+imgStr;
			}
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
		function readCard(){
			 var CVR_IDCard = document.getElementById("CVR_IDCard");					
				var strReadResult = CVR_IDCard.ReadCard();
				if(strReadResult == "0"){
					$("input[name='lineName']").val(CVR_IDCard.Name);
					$("input[name='lineCardNo']").val(CVR_IDCard.CardNo); 
	           	}else{
	           		alert("未能有效识别身份证，请重新读卡！");
	           		return false;
	           	}
		}
//------------------------------------身份身份证读取开始---------------------------------------------------
		function MyGetData()//OCX读卡成功后的回调函数
		{	
			lineName.value = GT2ICROCX.Name;//<-- 姓名--!>		
			lineCardNo.value = GT2ICROCX.CardNo;//<-- 卡号--!>      
			//namel.value = GT2ICROCX.NameL;//<-- 姓名--!>		      
			//sex.value = GT2ICROCX.Sex;//<-- 性别--!>		
			//sexl.value = GT2ICROCX.SexL;//<-- 性别--!>	
			//nation.value = GT2ICROCX.Nation;//<-- 民族--!>		
			//nationl.value = GT2ICROCX.NationL;//<-- 民族--!>	
			//born.value = GT2ICROCX.Born;//<-- 出生--!>	
			//bornl.value = GT2ICROCX.BornL;//<-- 出生--!>
			//address.value = GT2ICROCX.Address;//<-- 地址--!>
			//police.value = GT2ICROCX.Police;//<-- 发证机关--!>
			//activity.value = GT2ICROCX.Activity;//<-- 有效期--!>
			//activitylfrom.value = GT2ICROCX.ActivityLFrom;//<-- 有效期--!>
			//activitylto.value = GT2ICROCX.ActivityLTo;//<-- 有效期--!>
			//photo.value = GT2ICROCX.GetPhotoBuffer();
			
			//jpgPhotoFace1.value = GT2ICROCX.GetFaceJpgBase64(0);//双面
			//jpgPhotoFace1.value = GT2ICROCX.GetFaceJpgBase64(1);//正面
			//jpgPhotoFace2.value = GT2ICROCX.GetFaceJpgBase64(2);//反面	
		}

		function MyClearData()//OCX读卡失败后的回调函数
		{
			lineName.value = "";	 
			lineCardNo.value = "";
			//namel.value = ""     
			//sex.value = "";	
			//sexl.value = "";
			//nation.value = "";	
			//nationl.value = "";
			//born.value = "";	
			//bornl.value = "";
			//address.value = "";
			//police.value = "";
			//activity.value = "";
			//activitylfrom.value = "";
			//activitylto.value = "";
			//photo.value = "";
			//jpgPhotoFace1.value =  "";
			//jpgPhotoFace2.value =  "";	
		}

		function MyGetErrMsg()//OCX读卡消息回调函数
		{
			Status.value = GT2ICROCX.ErrMsg;	   
		}

		function StartRead()//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"

			//GT2ICROCX.Start() //循环读卡
			
			//单次读卡(点击一次读一次)
			
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard()	
			}
		} 

		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
//------------------------------------身份身份证读取结束---------------------------------------------------
//------------------------------------新高拍仪开始---------------------------------------------------

		function opendevice()
		{
			UDS_Video.Initial();
	        var nDeviceIndex = document.getElementById("sxtsx").value;
			UDS_Video.StartRun(nDeviceIndex);
		}
		function release()
		{
			UDS_Video.Uninitial();
		}
		function changedev1(){
			var iDeviceCount = UDS_Video.GetDeviceCount();
			var obj = document.getElementById("sxtsx").value;
			if(obj<iDeviceCount-1){
				document.getElementById("sxtsx").value = parseInt(obj)+1;
			}else {
				document.getElementById("sxtsx").value = 0;
			}
			opendevice();
       	}
        function grabimage() {
            var path="C:\\TEMP\\"+get_name()+".jpg";
            var szBase64 = UDS_Video.CaptureImageAndBase64(path);
            //预览
            previewPhotoStr = previewPhotoStr 
            	+ "<input type='checkbox' style='float:left;' pvalue='"+path+"' name='selPhoto' value='"+ szBase64 + "'/>"
                + "<img class='testImg' style='float:left;'  src='data:image/jpeg;base64," 
                + szBase64 + "' width='160' height='120'/>";
			$("#previewPhoto").html(previewPhotoStr);

        }

		function get_name(){
			var date=new Date();
			var yy=date.getFullYear().toString();
			var mm=(date.getMonth()+1).toString();
			var dd=date.getDate().toString();
			var hh=date.getHours().toString();
			var nn=date.getMinutes().toString();
			var ss=date.getSeconds().toString();
			var mi=date.getMilliseconds().toString();
			var picName=yy+mm+dd+hh+nn+ss+mi;
			return picName;
		}
//------------------------------------新高拍仪结束---------------------------------------------------

		function setValidate(zjlx){
			if(zjlx=="TWTX"||zjlx=="GATX"){
				$("#isVip").val(1);
				$("#zjhm").removeClass(",custom[vidcard]");
			}else if(zjlx=="SF"){
				$("#isVip").val(0);		
				$("#zjhm").addClass(",custom[vidcard]");
			}else{
				$("#isVip").val(0);
				$("#zjhm").removeClass(",custom[vidcard]");
			}
		}
		
		function changetoPad(){
			window.location.href="<%=basePath%>/webpage/callnew/queuing/PadInputTakeNoCtl.jsp?";
		}
    </script>
    <script type="text/javascript" for="VideoInputCtl" event="OnGrabTrigger(nDeviceIndex)">
		alert(nDeviceIndex);
	</script>
    <script type="text/javascript" for="VideoInputCtl" event="OnGrabNow(szFileName)">
		alert(szFileName);
	</script>
    <script type="text/javascript" for="VideoInputCtl" event="OnMaskChanged(nDeviceIndex,nStatus)">
		var text;
		
		if (nStatus == 0)
		{
			text = "New mask";
		}
		else if (nStatus == 1)
		{
			text = "Modify mask";
		}
		else if (nStatus == 2)
		{
			text = "Clear mask";
		}
		
		alert("Device:" + nDeviceIndex + ", " + text);
    </script> 
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
	MyClearData()
</SCRIPT>
    <style>
		.sel{
			border:solid 1px red;
		}
    </style>
</head>
<body onload="opendevice()" onload="release()" >
	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="gpy">
    	<div class="clearfix">
        	<div class="lfloat">
	            <div class="gpy-camera">
<!-- 	            <object id="VideoInputCtl" classid="CLSID:30516390-004F-40B9-9FC6-C9096B59262E" codebase="*.cab#version=1,0,0,1"  -->
<!-- 	            	width="560" height="300"></object> -->
	            <object id="UDS_Video" classid="clsid:CA859805-A3CE-4594-B019-BCBBE7F61D01" codebase="*.cab#version=1,0,0,1" 
	            	width="560" height="300"></object>
	    		</div>
	        </div>
	        <div class="rfloat">
	        	<div class="gpy-btn"><a href="javascript:void(0);" onclick="changedev1()">切换摄像头</a></div>
	        </div>
    	</div>
        <span style="color: red;">友情提示：请在仪器正常对焦后进行拍摄。（对焦时间约为10秒）<strong>请拍取取号人现场照片及身份证人像面照片以便核查。</strong></span>
<%--        <div id="previewPhoto" class="gpy-camera-info" >--%>
        <div id="previewPhoto" class="gpy-camera-info" 
            style="overflow-y:auto; width:710px; height:120px;">
        </div>
	    <div  id="AssistFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">证件类型：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" value="SF" name="LINE_ZJLX" id="LINE_ZJLX">
						</eve:eveselect> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
					</td>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">姓名：</span> <input
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
								data-options="url:'callSetController.do?loadBusiness&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'BUSINESS_CODE',textField:'BUSINESS_NAME',panelWidth: 235,panelHeight: '180' " />
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
        	<a class="camera" onclick="grabimage()"><span><i></i>拍 照</span></a>
        	<a class="submit" onclick="StartRead()""><span><i></i>读 卡</span></a>
        	<a class="submit" onclick="if(confirm( '请确认是否已拍取取号人照片及身份证人像面照片!')==false)return   false; UploadFileToHttp()"><span><i></i>确 认</span></a>
        	<a class="cancel" onclick="AppUtil.closeLayer();"><span><i></i>取 消</span></a>
        </div>
	</div>
	
    
	<!-- JS -->
    <script type="text/javascript">
		$(document).ready(function(){
			
			$(".gjgn").click(function(){
			  	$(".gpy-af").toggle();
			});
		
			$(".testImg").click(function(){
				alert(111);
			    $(this).addClass("sel").siblings("testImg").removeClass("sel");
			}); 
		});
		
		//$(".select1").select();
	</script>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
