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
    
    <object classid="clsid:262F963D-F810-43E6-BBD4-F21A2DA09DAC" id="DriverManager"  width="1" height="1" VIEWASTEXT>
  <param name="_Version" value="65536">
  <param name="_ExtentX" value="11774">
  <param name="_ExtentY" value="1323">
  <param name="_StockProps" value="0">
</object>
<OBJECT ID="EloamGlobal_ID" CLASSID="CLSID:52D1E686-D8D7-4DF2-9A74-8B8F4650BF73"></OBJECT>

    <script type="text/javascript">
		var EloamGlobal;
		var DeviceMain;
		var VideoMain;	
		//var videoCapMain;	
		var PicPath;
		var hasLoadSuccess = 0;
	    //初始化
		function Load()
		{
			EloamGlobal = document.getElementById("EloamGlobal_ID");
			EloamGlobal.GetKeyFromSoftDog(20);
			var ret;
			ret = EloamGlobal.InitDevs();
			if(ret)
			{
				if(!EloamGlobal.VideoCapInit())
				{
					alert("初始化录像失败");
				}
				OpenVideoMain();
			}
			hasLoadSuccess = 1;
		}
		function Unload()
		{
			if (VideoMain)
			{
				ViewMain.SetText("", 0);
				VideoMain.Destroy();
				VideoMain = null;
			}
			if(DeviceMain)
			{
				DeviceMain.Destroy();
				DeviceMain = null;
			}
			EloamGlobal.DeinitDevs();
			EloamGlobal = null;
		}
		function EnableDate(obj)
		{
			if (obj.checked)
			{
				var offsetx = 1000;
				var offsety = 60;
				var font;
				font = EloamGlobal.CreateTypeface(500, 500, 0, 0, 2, 0, 0, 0, "宋体");
				if (VideoMain)
				{
					var width = VideoMain.GetWidth();
					var heigth = VideoMain.GetHeight();
					VideoMain.EnableDate( font, 0, 0, 0xffffff, 0);
				}
				if (VideoAssist)
				{
					var width = VideoAssist.GetWidth();
					var heigth = VideoAssist.GetHeight();	
					VideoAssist.EnableDate( font, width - offsetx, heigth - offsety, 0xffffff, 0);
				}
				font.Destroy();
			}
			else
			{
				if(VideoMain)
				{
					VideoMain.DisableDate();
				}
			}
		}
		//左转
		function RotateLeft()
		{
			if (VideoMain)
				VideoMain.RotateLeft();
		}
		//右转
		function RotateRight()
		{
			if (VideoMain)
				VideoMain.RotateRight();
		}
		//左右
		function Mirror()
		{
			if (VideoMain)
				VideoMain.Mirror();
		}
		//上下
		function Flip()
		{
			if (VideoMain)
				VideoMain.Flip();
		}
		//纠偏裁边
		function SetDeskew(obj)
		{
			if (VideoMain)
			{
				if (obj.checked)
					VideoMain.EnableDeskewEx(1);
				else
					VideoMain.DisableDeskew();
			}
		}
		//去底色
		function DelBkColor(obj)
		{
			if (VideoMain)
			{
				if (obj.checked)
					VideoMain.EnableDelBkColor(0);
				else
					VideoMain.DisableDelBkColor();
			}
		}
		//设置反色
		function SetReverse(obj)
		{
			if (VideoMain)
			{
				if (obj.checked)
					VideoMain.EnableReverse();
				else
					VideoMain.DisableReverse();
			}
		}
		function GetTimeString()
		{
			var date = new Date();
			var yy = date.getFullYear().toString();
			var mm = (date.getMonth() + 1).toString();
			var dd = date.getDate().toString();
			var hh = date.getHours().toString();
			var nn = date.getMinutes().toString();
			var ss = date.getSeconds().toString();
			var mi = date.getMilliseconds().toString();
			var ret = yy + mm + dd + hh + nn + ss + mi;
			return ret;
		}
		
		function Upload()
		{
			//var http = EloamGlobal.CreateHttp("http://192.168.1.205:8086/upload.asp");//asp服务器demo地址
			var http = EloamGlobal.CreateHttp("http://192.168.1.205:8080/FileStreamDemo/servlet/FileSteamUpload?");//java服务器demo地址
			var ftp = EloamGlobal.CreateFtp("ftp://test:123@192.168.1.205:8090");
			if(ftp)
				ftp.CreateDir("/123456/");
		
			if (DeviceMain)
			{
				if (VideoMain)
				{
					var image = VideoMain.CreateImage(0, ViewMain.GetView());
					if (image)
					{
						if(http)
							http.UploadImage(image, 2, 0, "http.jpg");
						var path = EloamGlobal.GetTempName("jpg");
						image.Save(path, 0);					
						if(ftp)
							ftp.Upload(0, path, "123456/ftp.jpg");
						EloamGlobal.DelFile(path);
						
						image.Destroy();
						image = null;
					}	
				}		
			}
			
			if(http)
				http.Destroy();
			http = null;
			
			if(ftp)
				ftp.Destroy();
			ftp = null;
		}
		function UploadFile()
		{
			 //var http = plugin().Global_CreateHttp("http://192.168.1.56:80/upload.asp");//asp服务器demo地址
			var http = EloamGlobal.CreateHttp("http://192.168.1.56:8080/FileStreamDemo/servlet/FileSteamUpload?");//java服务器demo地址
			if (http)
			{
				var b = http.UploadImageFile("C:\\1.jpg", "2.jpg");
				if (b)
				{
					alert("上传成功");
				}
				else
				{
					alert("上传失败");
				}
		
				if(http)
					http.Destroy();
				http = null;
			}
			else
			{
				alert("url 错误");
			}
		}
		//获取安装路径
		function getAnPath(){
			var anpath=location.href;
			anpath=anpath.substring(anpath.lastIndexOf(':/')-1,anpath.lastIndexOf('/')+1);
			//alert(anpath);
			//替换所有斜杠	正则表达式 /\//g
			//anpath=anpath.replace(/\//g,'\\\\');
			return anpath;
		}
		function StopScanner()
		{
			EloamGlobal.ScannerStop();
			EloamGlobal.DeinitScanner();	
		}	
		function OpenVideoMain()
		{
			CloseVideoMain();
		
			if(DeviceMain)
			{
				var mode = document.getElementById('selMode1');
				var modeText = mode.options[mode.options.selectedIndex].text;
				var subtype = (modeText == "YUY2"? 1:(modeText == "MJPG"? 2:(modeText == "UYVY"? 4:-1)));
			
				var select1 = document.getElementById('selRes1'); 
				var nResolution1 = select1.selectedIndex;
				
				VideoMain = DeviceMain.CreateVideo(nResolution1, subtype);
				if (VideoMain)
				{
					ViewMain.SelectVideo(VideoMain);
					ViewMain.SetText("打开视频中，请等待...", 0);
				}
			}
		}
		function CloseVideoMain()
		{
			if (VideoMain)
			{
				VideoMain.Destroy();
				VideoMain = null;	
				ViewMain.SetText("", 0);	
			}
		}
		function changesubType1()
		{
			document.getElementById('selRes1').options.length = 0; 
			var mode = document.getElementById('selMode1');
			var modeText = mode.options[mode.options.selectedIndex].text;
			var subtype = (modeText == "YUY2"? 1:(modeText == "MJPG"? 2:(modeText == "UYVY"? 4:-1)));
			if((-1 != subtype) && (null != DeviceMain))
			{
				var select = document.getElementById('selRes1');
				var nResolution = DeviceMain.GetResolutionCountEx(subtype);
				for(var i = 0; i < nResolution; i++)
				{
					var width = DeviceMain.GetResolutionWidthEx(subtype, i);
					var heigth = DeviceMain.GetResolutionHeightEx(subtype, i);
					select.add(new Option(width.toString() + "*" + heigth.toString())); 
				}
				select.selectedIndex = 0;
				if(0 != hasLoadSuccess)
				{
					OpenVideoMain();
				}
			}
		}
		
		function changeScanSize()
		{
			if(VideoMain)
			{
				var scanSize = document.getElementById('selScanSize').options.selectedIndex;
				if(0 == scanSize)//原始尺寸
				{
					ViewMain.SetState(1);
				}
				else if(1 == scanSize || 2 == scanSize)
				{
					var rect;
					var width = VideoMain.GetWidth();
					var heigth = VideoMain.GetHeight();	
						
					if(1 == scanSize)//中等尺寸
					{
						rect = EloamGlobal.CreateRect(width/6, heigth/6, width*2/3, heigth*2/3);
					}
					if(2 == scanSize)//较小尺寸
					{
						rect = EloamGlobal.CreateRect(width/3, heigth/3, width/3, heigth/3);
					}
					
					ViewMain.SetState(2);
					ViewMain.SetSelectRect(rect);
				}
				else if(3 == scanSize)//自定义尺寸
				{
					//切换状态，清空框选区域
					ViewMain.SetState(1);
					ViewMain.SetState(2);
					alert("在主摄像头界面中，按住鼠标拖动即可框选尺寸!");
				}
			}
			else
			{
				alert("主摄像头视频未打开！");
			}
		}
	</script>
	<script language="Javascript" event="DevChange(type, idx, dbt)" for="EloamGlobal_ID" type="text/JavaScript">
	//设备接入和丢失
	//type设备类型， 1 表示视频设备， 2 表示音频设备
	//idx设备索引
	//dbt设备动作类型
		if (1 == type)
		{
			if (1 == dbt)//dbt 1 表示设备到达
			{
				var deviceType = EloamGlobal.GetEloamType(1, idx);
				
				if(1 == deviceType)//主摄像头
				{
					if(null == DeviceMain)
					{
						DeviceMain = EloamGlobal.CreateDevice(1, idx);
						if (DeviceMain)
						{
							var label =  document.getElementById('lab1');
							label.innerHTML = DeviceMain.GetFriendlyName();	
							
							var mode = document.getElementById('selMode1');
							var subType = DeviceMain.GetSubtype();
							if(0 != (subType & 1))
							{
								mode.add(new Option("YUY2"));
							}
							if(0 != (subType & 2))
							{
								mode.add(new Option("MJPG"));
							}
							if(0 != (subType & 4))
							{
								mode.add(new Option("UYVY"));
							}
							
							mode.selectedIndex = 0;
							
							var scanSize = document.getElementById('selScanSize');
							scanSize.add(new Option("原始尺寸"));
							scanSize.add(new Option("中等尺寸"));
							scanSize.add(new Option("较小尺寸"));
							scanSize.add(new Option("自定义尺寸"));
							scanSize.selectIndex = 0;
							
							changesubType1();
					
							if(0 != hasLoadSuccess)
							{
								OpenVideoMain();
							}
						}
					}
				}
					
			}
			
			if (2 == dbt)//dbt 2 表示设备丢失
			{
				if (DeviceMain)
				{
					if(idx == DeviceMain.GetIndex())
					{
						if(VideoMain)
						{
							VideoMain.Destroy();
							VideoMain = null;
							
							ViewMain.SetText("", 0);
						}
						DeviceMain.Destroy();
						DeviceMain = null;
						
						document.getElementById('selMode1').options.length = 0; 
						document.getElementById('selScanSize').options.length = 0; 
						document.getElementById('selRes1').options.length = 0; 
					}
				}			
					
			}
			
		}
		
	</script>
	
	<script language="Javascript" event="Arrival(video, id)" for="EloamGlobal_ID" type="text/JavaScript">
			
		if (VideoMain == video && 1 == id)//视频到达
		{
			changeScanSize();
		}
		
	</script>
	<script language="Javascript" event="MoveDetec(video, id)" for="EloamGlobal_ID" type="text/JavaScript">
			
		if (VideoMain == video)
		{
			Scan();
		}
	</script>
	
	
	<script type="text/javascript">
        var szDefaultDevice = "[1a3c:010d]"; // 525
        var previewPhotoStr = "";

		function grabimageLT()
		{
			if (VideoMain)
			{
				var imageList = VideoMain.CreateImageList(0, ViewMain.GetView());
				if (imageList)
				{
					ViewMain.PlayCaptureEffect();
					var count = imageList.GetCount();
					for (var i = 0; i < count; i++)
					{	
						var image = imageList.GetImage(i);
						var Name = "D:\\" + GetTimeString() + ".jpg";
						image.Save(Name, 0);
						EloamThumbnail.Add(Name);
						image.Destroy();
						image = null;
						alert("拍照成功照片路径："+Name);
						var szBase64 = DriverManager.GetB64String(Name);
						szBase64 = szBase64;
						
			            //预览
			            previewPhotoStr = previewPhotoStr 
			            	+ "<input type='checkbox' style='float:left;' pvalue='"+Name+"' name='selPhoto' value='"+ szBase64 + "'/>"
			                + "<img class='testImg' style='float:left;'  src='data:image/jpeg;base64," 
			                + szBase64 + "' width='160' height='120'/>";
						$("#previewPhoto").html(previewPhotoStr);
					}
				}
			}
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


    </script>
    
    <style>
		.sel{
			border:solid 1px red;
		}
    </style>
</head>
<body onLoad="Load()" onUnload="Unload()">
	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
	<div class="gpy">
    	<div class="clearfix">
        	<div class="lfloat">
	            <div class="gpy-camera">
	            <OBJECT ID="ViewMain" CLASSID="CLSID:26BA9E7F-78E9-4FB8-A05C-A4185D80D759" width="560" height="300"></OBJECT>
	    		</div>
	        </div>
	        <div class="rfloat">
	        	<div class="gpy-btn"><a href="javascript:void(0);" id="ShowProperty" value="ShowProperty" 
	        	onclick="showproperty()">设备属性</a></div>
            	<div class="gpy-btn gjgn">高级功能<i class="af"></i></div>
                <div class="gpy-af">
						<label id="lab1">设备1</label>
                	<div class="gpy-af-con">
                    	格式
						<select class="select1" style="width: 100px !important;" id="selMode1" name="selMode" onChange="changesubType1()"></select> 
                    </div>
                	<div class="gpy-af-con">
                    	分辨率
						<select class="select1" style="width: 100px !important;" id="selRes1" name="selRes" onChange="OpenVideoMain()"></select> 
                    </div>
                	<div class="gpy-af-con">
                    	尺寸
						<select class="select1" style="width: 100px !important;" id="selScanSize" name="selScanSize" onChange="changeScanSize()"></select>
                    </div>
                	<div class="gpy-af-con">
						<input id="setdeskew" type="checkbox" value="" onClick="SetDeskew(this)" />纠偏裁边
                    </div>
                </div>
	        </div>
    	</div>
        <span style="color: red;">友情提示：请在仪器正常对焦后进行拍摄。（对焦时间约为10秒）</span>
        <div id="previewPhoto" class="gpy-camera-info" 
            style="overflow-y:auto; width:710px; height:120px;">
        </div>
         <OBJECT ID="EloamThumbnail" CLASSID="CLSID:B5535A1B-D25B-4B3C-854F-94B12E284A4E" WIDTH="1" HEIGHT="1"></OBJECT>  
        <div class="gpy-sub">
        	<a class="camera" onclick="grabimageLT()"><span><i></i>拍 照</span></a>
        	<a class="submit" onclick="UploadFileToHttp()"><span><i></i>确 认</span></a>
        	<a class="cancel" onclick="AppUtil.closeLayer();"><span><i></i>取 消</span></a>
        </div>
	</div>
	
	<!-- 高级功能点击事件JS -->
    <script type="text/javascript">
		$(document).ready(function(){
			
			$(".gjgn").click(function(){
			  	$(".gpy-af").toggle();
			});
		
			$(".testImg").click(function(){
			    $(this).addClass("sel").siblings("testImg").removeClass("sel");
			}); 
		});
		
		//$(".select1").select();
	</script>
    
</body>
</html>
