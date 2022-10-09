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
	String busTableName = request.getParameter("busTableName");
	
	request.setAttribute("matreClsm", matreClsm);
	request.setAttribute("uploadPath", uploadPath);
	request.setAttribute("attachKey", attachKey);
	request.setAttribute("uploadUserId", uploadUserId);
	request.setAttribute("uploadUserName", uploadUserName);
	request.setAttribute("busTableName", busTableName);
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
        //var szDefaultDevice = "NewImage DocCam"; // 540
        var szDefaultDevice = "[1a3c:010d]"; // 525
        var previewPhotoStr = "";
           
		function release() {
			var nDeviceIndex = VideoInputCtl.GetDeviceIndex();

			if (VideoInputCtl.IsDeviceOpened(nDeviceIndex))
			{
				VideoInputCtl.CloseDevice(nDeviceIndex);
			}
		}

		function changedev1(){
			var obj = document.getElementById("sxtsx").value;
			if(obj==0){
				document.getElementById("sxtsx").value = 1;
			}else{
				document.getElementById("sxtsx").value = 0
			}
			opendevice();
       	}
		
        function opendevice() {
            var nDeviceIndex = document.getElementById("sxtsx").value;

            if (!VideoInputCtl.IsDeviceOpened(nDeviceIndex))
                VideoInputCtl.OpenDevice(nDeviceIndex);

            var nFormatCount = VideoInputCtl.GetDeviceFormatCount(nDeviceIndex);
            //分辨率
            ResolutionList.options.length = 0;
            for (var i = 0; i < nFormatCount; i++) {
                var szFormatName = VideoInputCtl.GetDeviceFormatName(nDeviceIndex, i);
                ResolutionList.options.add(new Option(szFormatName, i));
            }
			//旋转角度
			selRotateList.options.length = 0;
			for (var i = 0; i < 4; i++) {
                var selRotate = 0 + 90*i;
                selRotateList.options.add(new Option(selRotate, selRotate));
            }
			
            VideoInputCtl.StartPlayDevice(nDeviceIndex);

            ExposureList.options.length = 0;
            for (var i = -5; i <= -1; i++) {
                ExposureList.options.add(new Option(i, i));
            }
        }

        function changeresolution() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            var nFormatIndex = ResolutionList.selectedIndex;
            VideoInputCtl.SetDeviceFormatIndex(nDeviceIndex, nFormatIndex);
        }

        function grabimage() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            var path="C:\\TEMP\\"+get_name()+".jpg";
            VideoInputCtl.GrabToFile(path);
            var szBase64 = VideoInputCtl.GrabToBase64(".jpg");
            //预览
            previewPhotoStr = previewPhotoStr 
            	+ "<input type='checkbox' style='float:left;' pvalue='"+path+"' name='selPhoto' value='"+ szBase64 + "'/>"
                + "<img class='testImg' style='float:left;'  src='data:image/jpeg;base64," 
                + szBase64 + "' width='160' height='120'/>";
			$("#previewPhoto").html(previewPhotoStr);

            if (OCREnabled.checked) {
                var nCount = VideoInputCtl.GetQRcodeCount();
                var szType, szText;
                for (var i = 0; i < nCount; i++) {
                    szType = VideoInputCtl.GetQRcodeTypeName(i);
                    szText = VideoInputCtl.GetQRcodeContent(i);
                    QRcodeText.value += szType + ": " + szText + "\r\n";
                }
            }
            OCRText.value = "";
            if (VideoInputCtl.GetDeviceOCR(nDeviceIndex)) {
                OCRText.value = VideoInputCtl.GetOCRResult();
            }
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
		function UploadFileToHttp(){
			var imgs = document.getElementById("previewPhoto").getElementsByTagName("img");
			//var num=EThumbnails.GetDisplayCount();
			var num=imgs.length;
			if(num>0){
				UploadFile();
			}else{
				alert("请选择要上传的图片！");
				return;
			}
		}
		function UploadFile(){
			var matreClsm = '${matreClsm}';
			var tp = thumbnailPath();
			if(tp.length<=0){
				alert("请选择要上传的图片！");
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
			$.post( "executionController.do?uploadBase64",{
				base64Code:imgStrAll, 
				attachKey:"${attachKey}",
				uploadUserId:"${uploadUserId}",
				uploadUserName:"${uploadUserName}",
				busTableName:"${busTableName}"},
				function(responseText1, status, xhr) {
					//alert(responseText1);
					var resultJson1 = $.parseJSON(responseText1);
					art.dialog.data("resultJsonInfo", resultJson1);
					AppUtil.closeLayer();
			});
		}
		function getTiff(){
			var imgs = document.getElementById("previewPhoto").getElementsByTagName("img");
			//var num=EThumbnails.GetDisplayCount();
			var num=imgs.length;
			if(num>0){
				var paths = getCheckedPath();
				tiff(paths);
			}else{
				alert("请选择要上传的图片！");
				return;
			}
		}
		
		function thumbnailPath()
		{
			var gfp=new Array();
            $("input[name='selPhoto']:checked").each(function(){ 
            	gfp.push($(this).val()); 
			}); 
            return gfp;
		}
		function getCheckedPath()
		{
			var gfp=new Array();
            $("input[name='selPhoto']:checked").each(function(){ 
            	gfp.push($(this).prop("pvalue")); 
			}); 
            return gfp;
		}

        function changebarcode() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceBarcode(nDeviceIndex, BarcodeEnabled.checked);
        }

        function changeqrcode() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceQRcode(nDeviceIndex, QRcodeEnabled.checked);
        }

        function changeocr() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceOCR(nDeviceIndex, OCREnabled.checked);
            VideoInputCtl.SetOCRLanguage(OCRLanguage.selectedIndex);
        }

        function rotateangle() {
			var angle = document.getElementById("selRotateList").value;
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceRotate(nDeviceIndex, angle);
        }

        function flipxy() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceFlipX(nDeviceIndex, FlipX.checked);
            VideoInputCtl.SetDeviceFlipY(nDeviceIndex, FlipY.checked);
        }

        function changecolormode() {
            VideoInputCtl.SetColorPreview(true);
            VideoInputCtl.SetColorMode(ColorMode.selectedIndex);
        }

        function changebinarizemethod() {
            VideoInputCtl.SetBinarizeMode(BinarizeMethod.selectedIndex);
        }

        function changedeskew() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceDeskew(nDeviceIndex, DeskewEnabled.checked);
            VideoInputCtl.SetDeskewPreview(true);
        }

        function showproperty() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.ShowDevicePages(nDeviceIndex);
        }

        function changeexposure() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceExposure(nDeviceIndex, ExposureList.value);
        }

        function autoexposure() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceAutoExposure(nDeviceIndex, AutoExposure.checked);
        }

        function startrecord() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();

            VideoInputCtl.SetDeviceRecordCodec(nDeviceIndex, RecordCodec.value);
            VideoInputCtl.SetDeviceRecordFileName(nDeviceIndex, RecordFile.value);
            VideoInputCtl.SetDeviceRecord(nDeviceIndex, true);

            changeresolution();
        }

        function stoprecord() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();

            VideoInputCtl.SetDeviceRecord(nDeviceIndex, false);

            changeresolution();
        }

        function grabbase64() {
            var szBase64 = VideoInputCtl.GrabToBase64(".jpg");
            Base64Text.value = szBase64;
            Base64Image.src = "data:image/tiff;base64," + szBase64;
        }

        function grabtopdf() {
            var path="C:\\TEMP\\"+get_name()+".pdf";
            VideoInputCtl.GrabToPdf(path, "", "", "", "", "");
        }

        function jpegtopdf() {
            VideoInputCtl.JpegToPdf(JpegFiles.value, PdfFile.value, "", "", "", "", "");
        }

        function autograb() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();

            if (!AutoGrab.checked) {
            	VideoInputCtl.SetDeviceAutoGrab(nDeviceIndex, false);
            }

            VideoInputCtl.SetDeviceAutoGrabSetting(nDeviceIndex,
                StableLow.value, StableHigh.value, StableTime.value,
                GrabPrefix.value, GrabExt.value);

            if (AutoGrab.checked) {
            	VideoInputCtl.SetDeviceAutoGrab(nDeviceIndex, true);
            }
        }

        function autograbreset() {
        	var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
        	VideoInputCtl.SetDeviceAutoGrabSn(nDeviceIndex, 0);
        }

        function facehighlight() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceFaceHighlight(nDeviceIndex,
                FaceHighlight.checked,
                FaceHiKernel.selectedIndex * 2 + 1,
                (FaceHiStep.selectedIndex + 1) * 2,
                FaceHiXml.value);
        }

        function undistort() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceUndistortCoeffs(nDeviceIndex, UCoeffs.value);
            VideoInputCtl.SetDeviceUndistort(nDeviceIndex, Undistort.checked);
        }

        function mouseenable() {
            VideoInputCtl.SetMouseEnabled(MouseLeft.checked, MouseRight.checked, MouseWheel.checked);
        }
		
		function combine() {
			var nCols = CombineCols.selectedIndex + 1;
			var files = CombineFiles.value;
			files = files.replace(/\r\n/g, '\n');
			alert(files);
			VideoInputCtl.CombineImages(files, CombineOut.value, nCols, 0xffffff);
		}
		
		function deleteaftercombine() {
			VideoInputCtl.SetDeleteAfterCombine(DeleteAfterCombine.checked);
		}
		
		function tiff(paths) {
			var files = paths;
			if(files.length<=0){
				alert("请选择要上传的图片！");
				return;
			}
			var imgStr;
			var imgStrAll = "";
			for(var i=0;i<files.length;i++){
				imgStr=files[i];
				if(imgStr=='' || imgStr==null || imgStr==undefined){
					continue;
				}
				imgStrAll = imgStrAll+imgStr+"|";
			}
            var TiffOut="C:\\TEMP\\"+get_name()+".tif";
			VideoInputCtl.JpegToTiff(imgStrAll, TiffOut);
            var tifBase64 = VideoInputCtl.Base64Image(TiffOut);
            //预览
            previewPhotoStr = previewPhotoStr 
            	+ "<input type='checkbox' style='float:left;' pvalue='"+TiffOut+"' name='selPhoto' value='"+ tifBase64 + "'/>"
                + "<img class='testImg' style='float:left;'  src='data:image/tiff;base64," 
                + tifBase64 + "' width='160' height='120'/>";
			$("#previewPhoto").html(previewPhotoStr);
		}
		
		function mugshot() {
			var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
			VideoInputCtl.SetDeviceMugshot(nDeviceIndex, Mugshot.checked);
			VideoInputCtl.SetDeviceMugshotThreshold(nDeviceIndex, MugshotThreshold.value);
		}
		function copyUrl2(){
			var Url2=document.getElementById("OCRText");
			Url2.select(); // 选择对象
			document.execCommand("Copy"); // 执行浏览器复制命令
			alert("已复制好，可贴粘。");
		}
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
	            <object id="VideoInputCtl" classid="CLSID:30516390-004F-40B9-9FC6-C9096B59262E" codebase="*.cab#version=1,0,0,1" 
	            	width="560" height="300"></object>
	    		</div>
	        </div>
	        <div class="rfloat">
	        	<div class="gpy-btn"><a href="javascript:void(0);" onclick="changedev1()">切换摄像头</a></div>
	        	<div class="gpy-btn"><a href="javascript:void(0);" id="ShowProperty" value="ShowProperty" 
	        	onclick="showproperty()">设备属性</a></div>
            	<div class="gpy-btn gjgn">高级功能<i class="af"></i></div>
                <div class="gpy-af">
                	<div class="gpy-af-con">
                    	分辨率
                        <select class="select1" id="ResolutionList" name="ResolutionList" onchange="changeresolution()">
                        </select>
                    </div>
                	<div class="gpy-af-con">
                    	旋转角度
                        <select class="select1" id="selRotateList"  name="selRotateList" onchange="rotateangle()">
                        </select>
                    </div>
<%--                	<div class="gpy-af-con">--%>
<%--                    	颜色设置--%>
<%--                        <select class="select1" id="ColorMode"  name="ColorMode" onchange="changecolormode()">--%>
<%--					        <option selected="Color">彩色</option>--%>
<%--					        <option selected="Grayscale">灰色</option>--%>
<%--					        <option selected="Blackwhite">黑白</option>--%>
<%--                        </select>--%>
<%--                    </div>--%>
                	<div class="gpy-af-con">
                    	曝光
                        <select class="select1" id="ExposureList"  name="ExposureList" onchange="changeexposure()">
                        </select>
                    </div>
                </div>
	        </div>
    	</div>
        <span style="color: red;">友情提示：请在仪器正常对焦后进行拍摄。（对焦时间约为10秒）</span>
<%--        <div id="previewPhoto" class="gpy-camera-info" >--%>
        <div id="previewPhoto" class="gpy-camera-info" 
            style="overflow-y:auto; width:710px; height:120px;">
        </div>
        <div class="gpy-sub">
        	<a class="camera" onclick="grabimage()"><span><i></i>拍 照</span></a>
<%--        	<a class="camera" onclick="grabtopdf()"><span><i></i>生成PDF</span></a>--%>
<%--        	<a class="submit" onclick="getTiff()"><span><i></i>生成TIFF文件</span></a>--%>
        	<a class="submit" onclick="UploadFileToHttp()"><span><i></i>确 认</span></a>
        	<a class="cancel" onclick="AppUtil.closeLayer();"><span><i></i>取 消</span></a>
        </div>
        <div id="OCRdiv" >
		    <label><input id="OCREnabled" type="checkbox" onclick="changeocr()" />使用OCR文字识别</label>
		    <label><input id="DeskewEnabled" type="checkbox" value="" onclick="changedeskew()">纠偏</label>
		    <select  class="select1" id="OCRLanguage" onchange="changeocr()">
		        <option>English</option>
		        <option>繁体中文</option>
		        <option>简体中文</option>
		    </select>
		    <input type="button" onClick="copyUrl2()" value="点击复制下列文本内容" />
		    <br/>
		    <textarea id="OCRText" readonly="readonly" style="height: 80px; width: 730px;"></textarea>
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
