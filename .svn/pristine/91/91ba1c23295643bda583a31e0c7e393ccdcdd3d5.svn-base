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
//------------------------------------新高拍仪开始---------------------------------------------------
		window.onbeforeunload = onbeforeunload_handler;         
		 function onbeforeunload_handler(){ 
// 			 console.log(11111111111111111111111111111);
			 release();         
		}
		function opendevice()
		{
	        var nDeviceIndex = document.getElementById("sxtsx").value;
			
			var ret = XWJImgOCX.Init(nDeviceIndex);
			if(ret <0)
			{
			    alert("初始化摄像头失败!");
	  	                    return;			
			}
			var width = GetResolutionWidth();
			var height =GetResolutionHeight(); 
			XWJImgOCX.SetResolution( nDeviceIndex,width,height,50);
			ret = XWJImgOCX.SetPrvWnd(nDeviceIndex);	
			if(ret <0)
			{
			         alert("设置摄像头预览窗口失败!");
		  	         return;			
			}
			ret = XWJImgOCX.Start(nDeviceIndex,0);	
			if(ret <0)
			{
			         alert("打开摄像头预览窗口失败!");
		  	         return;			
			}	         
			
		}
		<!--默认分辨率宽值-->
		function GetResolutionWidth()
		{
		    var width ;	
			width = 640;
			return width;	
		}
		<!--默认分辨率高值-->
		function GetResolutionHeight()
		{
		    var Height ;	
			Height = 480;
			return Height;
		}

		function release()
		{
	        var nDeviceIndex = document.getElementById("sxtsx").value;
			if(nDeviceIndex >= 0)
			{
				var ret = XWJImgOCX.Stop(nDeviceIndex,0);
				if(ret <0)
				{
				    alert("关闭摄像头失败!");
		  	                    return;			
				}
				ret = XWJImgOCX.Release(nDeviceIndex);
				if(ret <0)
				{
				    alert("释放摄像头失败!");
		  	                    return;			
				}
			}	
		}
		function changedev1(){
			release();
			var obj = document.getElementById("sxtsx").value;
			if(obj==0){
				document.getElementById("sxtsx").value = 1;
			}else {
				document.getElementById("sxtsx").value = 0;
			}
			opendevice();
       	}
        function grabimage() {
            var path="C:\\TEMP\\"+get_name()+".jpg";
            var nDeviceIndex = document.getElementById("sxtsx").value;
            var colortype = 0;//0-彩色24位;1-黑白(阈值);2-黑白(自动)
            var isCut = 0;//0-不去边;1-去边
            var isCorrect = 0;//0-不纠偏;1-纠偏
            var Compress = 50;//压缩比
            var RotateType = 0;//0-不旋转;1-右旋90度;2-旋转180度;3-左旋90度
            var szBase64 = XWJImgOCX.GetImage(nDeviceIndex,path,colortype,isCut,isCorrect,Compress,RotateType);
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
			      <OBJECT id="XWJImgOCX" width="560" height="300" classid="clsid:92D8093F-1E95-4C7E-B128-679C0EB77666" >
			      </OBJECT>
	    		</div>
	        </div>
	        <div class="rfloat">
	        	<div class="gpy-btn"><a href="javascript:void(0);" onclick="changedev1()">切换摄像头</a></div>
	        </div>
    	</div>
        <span style="color: red;">友情提示：请在仪器正常对焦后进行拍摄。（对焦时间约为10秒）!!点击确认后图片上传需要一定时间，请耐心等待。!!</span>
        <div id="previewPhoto" class="gpy-camera-info" 
            style="overflow-y:auto; width:710px; height:120px;">
        </div>
        <div class="gpy-sub">
        	<a class="camera" onclick="grabimage()"><span><i></i>拍 照</span></a>
        	<a class="submit" onclick="UploadFileToHttp()"><span><i></i>确 认</span></a>
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
