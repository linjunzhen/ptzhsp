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
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/ptwgform/scan/css/style.css">
	<script language=JavaScript>

	        function deloptionRes()
	        {   
		        var obj=document.getElementById("selRes").options; 
		        while (obj.length > 0)
		        {
			        obj.options.remove(obj.length - 1);   
		        }   
	        }
	        function addoptionRes(s)   
	        {
		        var obj=document.getElementById("selRes").options; 
		        var opt = new Option(s, obj.length ); 
		        obj.options.add(opt);   
	        }
	        function deloptionScanSize()
	        {   
		        var obj=document.getElementById("selScanSize").options; 
		        while (obj.length > 0)
		        {
			        obj.options.remove(obj.length - 1);
		        }   
	        }
	        function addoptionScanSize(s)   
	        {
		        var obj=document.getElementById("selScanSize").options; 
		        var opt = new Option(s, obj.length ); 
		        obj.options.add(opt);   
	        }
	        function deloptionColor()
	        {   
		        var obj=document.getElementById("selColor").options; 
		        while (obj.length > 0)
		        {
			        obj.options.remove(obj.length - 1);   
		        }   
	        }
	        function addoptionColor(s)   
	        {
		        var obj=document.getElementById("selColor").options; 
		        var opt = new Option(s, obj.length ); 
		        obj.options.add(opt);   
	        }
	        function deloptionDev()
	        {   
		        var obj=document.getElementById("selDev").options; 
		        while (obj.length > 0)
		        {
			        obj.options.remove(obj.length - 1);   
		        }   
	        }
	        function addoptionDev(s)   
	        {
		        var obj=document.getElementById("selDev").options; 
		        var opt = new Option(s, obj.length ); 
		        obj.options.add(opt);   
	        }
	        function deloptionRotate()
	        {   
		        var obj=document.getElementById("selRotate").options; 
		        while (obj.length > 0)
		        {
			        obj.options.remove(obj.length - 1);   
		        }   
	        }
	        function addoptionRotate(s)   
	        {
		        var obj=document.getElementById("selRotate").options; 
		        var opt = new Option(s, obj.length ); 
		        obj.options.add(opt);   
	        }
	        function contentLoad()
	        {
			//ScanCtrl.EnableDateRecord(true);
				ScanCtrl.StartPreviewEx();
		      	fun();  
	        }
	        function fun()
	        {
				//deloptionDev();
				// var iDevIndex = ScanCtrl.GetCurDevIndex();
				// if(iDevIndex != -1)
				// {
						// var count = ScanCtrl.GetDeviceCount();
						// for(i = 0; i < count; i++)
						// {
							// var s = ScanCtrl.GetDevName(i);
						// addoptionDev(s);
						// }
					// document.getElementById("selDev").value=iDevIndex;
				// }

				deloptionRes();
				var iResIndex = ScanCtrl.GetCurResolutionIndex();
				if(iResIndex != -1)
				{
						var count = ScanCtrl.GetResolutionCount(); 
						for(i = 0;i < count; i++)
						{
							var w=ScanCtrl.GetResolutionWidth(i);
							var h=ScanCtrl.GetResolutionHeight(i);
							var str=w.toString()+"x"+h.toString();
							addoptionRes(str);
						} 
					document.getElementById("selRes").value=iResIndex;
				}   

				deloptionScanSize();
				var iScanSizeIndex = ScanCtrl.GetCurScanSizeIndex();
				if(iScanSizeIndex != -1)
				{
						var count = ScanCtrl.GetScanSizeCount();
						for(i = 0; i < count; i++)
						{
							var str = ScanCtrl.GetScanSizeName(i);
						addoptionScanSize(str);
						} 
					addoptionScanSize("自定义");
					
					var bCustom = ScanCtrl.IsCustom();
					if(bCustom)
					{
						document.getElementById("selScanSize").value=count;
					}
					else
					{
						document.getElementById("selScanSize").value=iScanSizeIndex;
					}
					
				}

				deloptionRotate();
				var iRotateIndex = ScanCtrl.GetCurRotateAngle();
				if(iRotateIndex != -1)
				{
					addoptionRotate("0");
					addoptionRotate("90");
					addoptionRotate("180");
					addoptionRotate("270");
					document.getElementById("selRotate").value=iRotateIndex;
				}

				deloptionColor();
				var iColorIndex = ScanCtrl.GetCurColor();
				if(iColorIndex != -1)
				{
					addoptionColor("彩色");
					addoptionColor("灰度");
					addoptionColor("黑白");
					document.getElementById("selColor").value=iColorIndex;
				}
				
				var bRotateCrop = ScanCtrl.IsRotateCrop();
				document.getElementById("rotatecrop").checked=bRotateCrop;
				var bDelBkColor = ScanCtrl.IsDelBackColor();
				document.getElementById("delbkcolor").checked=bDelBkColor;
	        }
			function start_preview(url) 
			{
				// ScanCtrl.SetCurDev(1);
				ScanCtrl.StartPreviewEx();
				//ScanCtrl.StartPreview();
				fun();
			}
			function loadstart() 
			{
				ScanCtrl.StartPreview();
				fun();
			}
			function stop_preview(url) 
			{   
				ScanCtrl.StopPreviewEx();
				fun();
			}
			function get_name()
			{
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
	        function TakePic(url)    
	        {
				var path="C:\\TEMP\\"+get_name()+".jpg";
				//ScanCtrl.EnableDateRecord(1);						   
				var flag=ScanCtrl.Scan(path);		
				if(flag)
				{
					EThumbnails.AddToDisplay(path);
				}
	        }
			function TakePicBase64(url)    
	        {
				var path="D:\\"+get_name()+".jpg";
				//ScanCtrl.EnableDateRecord(1);						   
				alert(ScanCtrl.ScanBase64(path));		
				ScanCtrl.DeleteMyFile(path);
	        }
	        function Property(url) 
            {
	            ScanCtrl.Property();
	        }
			function ZoomIn()
			{
				ScanCtrl.SetZoomIn();
			}
			function ZoomOut()
			{
				ScanCtrl.SetZoomOut();

			}
			function deletefolder()
			{
				ScanCtrl.DeleteFolder("D:\\imageqq");    
			}
	        function changeresolution()
	        {
		        var num= ScanCtrl.GetResolutionCount();
		        var obj=document.getElementById("selRes").options; 
		        var x = obj.selectedIndex;    

				ScanCtrl.SetResolution(x);  
				fun();   
	        }
	        function changedev()
	        {
		        var num= ScanCtrl.GetDeviceCount();
		        var obj=document.getElementById("selDev").options; 
		        var x = obj.selectedIndex;    

				ScanCtrl.SetCurDev(x); 
				fun();
	        }
			 function changedev1()
	        {
				var obj = document.getElementById("sxtsx").value;
				if(obj==0){
					document.getElementById("sxtsx").value = 1;
				}else{
					document.getElementById("sxtsx").value = 0
				}
				ScanCtrl.SetCurDev(obj); 
				fun();
	        }
	        function changescansize()
	        {
		        var   num=ScanCtrl.GetScanSizeCount();
		        var   obj=document.getElementById("selScanSize").options; 
		        var   x = obj.selectedIndex;    

				ScanCtrl.SetScanSize(x);
				fun();
	        }     
	        function changerotate()
	        {
		        var   obj=document.getElementById("selRotate").options; 
		        var   x = obj.selectedIndex;    

				ScanCtrl.SetVideoRotate(x);
				fun();
	        }	  
	        function changecolor()
	        {
		        var obj = document.getElementById("selColor").options; 
		        var x = obj.selectedIndex;    

				ScanCtrl.SetVideoColor(x);
				fun();
	        }
	        function RotateCrop(obj)
	        {
		        ScanCtrl.SetRotateCrop(obj.checked);
				fun();
	        }
	        function RemoveBackColor(obj)
	        {
		        ScanCtrl.DelBackColor(obj.checked);
				fun();
	        }
			function barcode() {

				alert(ScanCtrl.ScanBarcode("D:\\barcode.jpg"));
				alert(ScanCtrl.Barcode("D:\\barcode.jpg"));
			}
			function UploadFileToHttp(){
				var num=EThumbnails.GetDisplayCount();
				if(num>0){
					UploadFile();
				}else{
					ScanToHTTPServer();
				}
			}
			function UploadFile()
			{
				var matreClsm = '${matreClsm}';
				var tp=new Array();
				tp=thumbnailPath();
				if(tp.length<=0){
					alert("请选择要上传的图片！");
					return;
				}
				var imgStr;
				var results = new Array();
				for(var i=0;i<tp.length;i++){
					imgStr=tp[i];
					if(imgStr=='' || imgStr==null || imgStr==undefined){
						continue;
					}
					var result = ScanCtrl.UploadFileOfHTTP2('<%=basePath%>/UploadServlet?uploadPath=${uploadPath}&attachKey=${attachKey}&uploadUserId=${uploadUserId}&uploadUserName=${uploadUserName}',imgStr,'');	
					var resultJson = JSON2.parse(result);
					results.push(resultJson);					
				}
				art.dialog.data("resultJsonInfo", {
					resultJson:results,
				});
				AppUtil.closeLayer();
			}
			function ScanToHTTPServer(){
				var results = new Array();
				var result = ScanCtrl.ScanToHTTPServer2('<%=basePath%>/UploadServlet?uploadPath=${uploadPath}&attachKey=${attachKey}&uploadUserId=${uploadUserId}&uploadUserName=${uploadUserName}','','');
				var resultJson = JSON2.parse(result);				
				results.push(resultJson);
				art.dialog.data("resultJsonInfo", {
					resultJson:results,
				});
				AppUtil.closeLayer();
			}
			function thumbnailPath()
			{
				var isOk,num,gfp=new Array();
				num=EThumbnails.GetDisplayCount();
				for(var i=0;i<num;i++){
					isOk=EThumbnails.IsChecked(i);
					if(isOk==1){
					gfp[i]=EThumbnails.GetFilePath(i);
					}	
				}
				return gfp;
			}
			function thumbnailAllPath() 
			{
				var isOk, num, gfp = new Array();
				num = EThumbnails.GetDisplayCount();
				for (var i = 0; i < num; i++) {
				   
						gfp[i] = EThumbnails.GetFilePath(i);
				 
				}
				return gfp;
			}
			function SetJpgQuality()
			{
				ScanCtrl.SetJpegQuality(20);
			}
			function slectdev()
			{
			  //  ScanCtrl.SetCurDev(1);
			}
			function getName() {
				var date = new Date();
				var yy = date.getFullYear().toString();
				var mm = (date.getMonth() + 1).toString();
				var dd = date.getDate().toString();
				var hh = date.getHours().toString();
				var nn = date.getMinutes().toString();
				var ss = date.getSeconds().toString();
				var mi = date.getMilliseconds().toString();
				var getName = yy + mm + dd + hh + nn + ss + mi;
				return getName;
			}
			function getAnPath() 
			{
				var anpath = location.href;
				anpath = anpath.substring(anpath.lastIndexOf(':/') - 1, anpath.lastIndexOf('/') + 1);
				//alert(anpath);
				//replace /\//g
				//anpath=anpath.replace(/\//g,'\\\\');
				return anpath;
			}
			function ocr_by_mode() 
			{
				var anpath = getAnPath();

				var resName = anpath + getName() + ".xml";

				var model = anpath + "template/" + "imageMode.xml";

				//var isOk = ScanCtrl.ScanDiscern(model, resName);
				// var isOk = ScanCtrl.ScanRecogResults(model);
				alert("kaishi");
				var ok = ScanCtrl.InitOcr(model); 
				if (ok) {
					alert(ScanCtrl.GetResultByMode("name", model));
					alert(ScanCtrl.GetResultByMode("company", model));
					alert(ScanCtrl.GetResultByMode("telephone", model));
					alert(ScanCtrl.GetResultByMode("adreass", model));
				}
			}
			function IsColect() 
			{
				alert(ScanCtrl.IsConnect());
				//alert(ScanCtrl.GetFileBase64("D://1.jpg"));
			}
			function GettmpDir()
			{
				alert(ScanCtrl.GetTmpPath());
			}
			function DiscernIdcard()
			{
				var temp = ScanCtrl.IDCardRecognize();
				if(temp)
				{
					var msg = "姓名：" + ScanCtrl.GetIDCardName() + "\n"
						+ "民族：" + ScanCtrl.GetIDCardNation() + "\n"
						+ "性别：" + ScanCtrl.GetIDCardSex() + "\n"
						+ "出生日期：" + ScanCtrl.GetIDCardBorn() + "\n"
						+ "住址：" + ScanCtrl.GetIDCardAddr() + "\n"
						+ "号码：" + ScanCtrl.GetIDCardNo() + "\n"
						+ "发证机关：" + ScanCtrl.GetIDCardPolice() + "\n"
						+ "有效期：" + ScanCtrl.GetIDCardActive() + "\n"
						
					alert(msg);
				}
				else
				{
					alert("识别失败！");
				}
			}
			
		
        </script>

		<script language="Javascript" event="DeviceChange(code)" for="ScanCtrl">      
			fun();
		</script>
</head>

<body onload="contentLoad();">
	
	<input type="hidden" id="sxtsx" name="sxtsx" value="1"/>
	<div class="gpy">
    	<div class="clearfix">
        	<div class="lfloat">
            	<div class="gpy-camera">
					<object classid="clsid:090457CB-DF21-41EB-84BB-39AAFC9E271A"
						id="ScanCtrl" codebase="*.cab#version=1,0,0,1" width="560"
						height="320"></object>
				</div>
                <div class="gpy-camera-zoom">
                	<a href="javascript:void(0);"  onclick="changedev1()"><span class="qh">切换摄像头</span></a>
                	<a href="javascript:void(0);"  onclick="ZoomIn()"><span class="fd">放 大</span></a>
                	<a href="javascript:void(0);"  onclick="ZoomOut()"><span class="sx">缩 小</span></a>
                </div>
            </div>
        	<div class="rfloat">
            	<div class="gpy-btn"><a href="javascript:void(0);" onclick="start_preview()"><i class="begin"></i>开始预览</a></div>
            	<div class="gpy-btn"><a href="javascript:void(0);"  onclick="stop_preview()"><i class="stop"></i>停止预览</a></div>
            	<div class="gpy-btn"><a href="javascript:void(0);" onclick="Property()">设备属性</a></div>
            	<div class="gpy-btn gjgn">高级功能<i class="af"></i></div>
                <div class="gpy-af">
                	<div class="gpy-af-con">
                    	扫描尺寸
                        <select class="select1" id="selScanSize"  name="selScanSize" onchange="changescansize()">
                        </select>
                    </div>
                	<div class="gpy-af-con">
                    	旋转角度
                        <select class="select1" id="selRotate"  name="selRotate" onchange="changerotate()">
                        </select>
                    </div>
                	<div class="gpy-af-con">
                    	分辨率
                        <select class="select1" id="selRes" name="selRes"onchange="changeresolution()">
                        </select>
                    </div>
                	<div class="gpy-af-con">
                    	颜色设置
                        <select class="select1" id="selColor"  name="selColor" onchange="changecolor()">
                        </select>
                    </div>
                	<div class="gpy-af-con">
                    	<label><input id="rotatecrop" type="checkbox" value="" onclick="RotateCrop(this)">纠偏裁边</label>
                    	<label><input id="delbkcolor" type="checkbox" value="" onclick="RemoveBackColor(this)" >去底色</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="gpy-camera-info">
        	<OBJECT ID="EThumbnails"  CLASSID="CLSID:E8B3DD46-A440-4C3C-AB0A-DC689EEBDA84" width="718" height="130"></OBJECT>
        </div>
        <div class="gpy-sub">
        	<a class="camera" onclick="TakePic()"><span><i></i>拍 照</span></a>
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
		});
		
		//$(".select1").select();
	</script>
</body>
</html>