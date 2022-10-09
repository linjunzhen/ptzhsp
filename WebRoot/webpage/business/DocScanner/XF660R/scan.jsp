<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//业务表名称
	String busTableName = request.getParameter("busTableName");
	//业务表记录ID
	String busRecordId = request.getParameter("busRecordId");
	//上传用户ID
	String uploadUserId = request.getParameter("uploadUserId");
	//上传用户的名称
	String uploadUserName = request.getParameter("uploadUserName");
	//服务器端保存路径
	String uploadPath = request.getParameter("uploadPath");
	//拍摄文件个数限制
	String scanLimit = request.getParameter("scanLimit");
	
	//附件KEY
	String attachKey = request.getParameter("attachKey");
	if(StringUtils.isEmpty(uploadUserId) || StringUtils.isEmpty(uploadUserName) ||
	   StringUtils.isEmpty(uploadPath) || StringUtils.isEmpty(attachKey) ||
	   StringUtils.isEmpty(scanLimit)){
		out.println("参数缺失，高拍仪操作界面调用失败。");
		return;
	}
	request.setAttribute("uploadUserId", uploadUserId);
	request.setAttribute("uploadUserName", uploadUserName);
	request.setAttribute("uploadPath", uploadPath);
	request.setAttribute("attachKey", attachKey);
	request.setAttribute("scanLimit", scanLimit);
	if(StringUtils.isNotEmpty(busTableName)){
		request.setAttribute("busTableName", busTableName);
	}
	if(StringUtils.isNotEmpty(busRecordId)){
		request.setAttribute("busRecordId", busRecordId);
	}
%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>良田高拍仪-XF660R-文档拍摄页</title>
        <meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8" />
        <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/business/DocScanner/XF660R/css/style.css">
        <script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery2.js"></script>
        <link rel="stylesheet" href="<%=path %>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
        <script type="text/javascript" src="<%=path %>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
        <script type="text/javascript" src="<%=path %>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
        <script type="text/javascript" src="<%=path %>/plug-in/json-2.0/json2.js"></script>
        <object id="EloamGlobal_ID" classid="CLSID:52D1E686-D8D7-4DF2-9A74-8B8F4650BF73"></object>
        <script type="text/javascript">
        	var EloamGlobal;
    		var EloamDevice;
    		var EloamVideo;
    		
    		function Load(){
    			EloamGlobal = document.getElementById("EloamGlobal_ID");
    			EloamGlobal.InitDevs();
    		}
    		
    		function Unload(){
    			CloseVideo();
    			if(EloamDevice){
    				EloamDevice.Destroy();
    				EloamDevice = null;
    			}
    			
    			EloamGlobal.DeinitDevs();
    			EloamGlobal = null;
    		}
    		
    		function selectDevice(){		
    			CloseVideo();	
    			if(EloamDevice){
    				EloamDevice.Destroy();
    				EloamDevice = null;
    			}
    			
    			var select = document.getElementById('device'); 
    			var devIdx = select.selectedIndex;
    			
    			document.getElementById('subtype').options.length = 0; 
    			
    			if(-1 != devIdx){
    				EloamDevice = EloamGlobal.CreateDevice(1, devIdx);
    			
    				var mode = document.getElementById('subtype');
    				var subType = EloamDevice.GetSubtype();
    				if(0 != (subType & 1)){
    					mode.add(new Option("YUY2"));
    				}
    				if(0 != (subType & 2)){
    					mode.add(new Option("MJPG"));
    				}
    				if(0 != (subType & 4)){
    					mode.add(new Option("UYVY"));
    				}
    				
    				mode.selectedIndex = 0;
    			}
    			
    			selectSubtype();
    		}
    		
    		function selectSubtype(){
    			document.getElementById('resolution').options.length = 0; 
    			
    			var mode = document.getElementById('subtype');
    			var modeIdx = mode.options.selectedIndex;
    			if(-1 != modeIdx){
    				var modeText = mode.options[modeIdx].text;
    				var subtype = (modeText == "YUY2"? 1:(modeText == "MJPG"? 2:(modeText == "UYVY"? 4:-1)));
    				if((-1 != subtype) && (null != EloamDevice)){
    					var select = document.getElementById('resolution');
    					var nResolution = EloamDevice.GetResolutionCountEx(subtype);
    					for(var i = 0; i < nResolution; i++){
    						var width = EloamDevice.GetResolutionWidthEx(subtype, i);
    						var heigth = EloamDevice.GetResolutionHeightEx(subtype, i);
    						select.add(new Option(width.toString() + "*" + heigth.toString())); 
    					}
    					select.selectedIndex = 0;
    				}
    			}
    			
    			selectResolution();
    		}
    		
    		function selectResolution(){
    			ShowVideo();
    		}
    		
    		function changeScanSize(){
    			if(EloamVideo){
    				var scanSize = document.getElementById('selScanSize').options.selectedIndex;
    				//原始尺寸
    				if(0 == scanSize){
    					EloamView.SetState(1);
    				}else if(1 == scanSize || 2 == scanSize){
    					var rect;
    					var width = EloamVideo.GetWidth();
    					var heigth = EloamVideo.GetHeight();	
    					
    					//中等尺寸
    					if(1 == scanSize){
    						rect = EloamGlobal.CreateRect(width/6, heigth/6, width*2/3, heigth*2/3);
    					}
    					//较小尺寸
    					if(2 == scanSize){
    						rect = EloamGlobal.CreateRect(width/3, heigth/3, width/3, heigth/3);
    					}
    					
    					EloamView.SetState(2);
    					EloamView.SetSelectRect(rect);
    				}else if(3 == scanSize){ //自定义尺寸
    					//切换状态，清空框选区域
    					EloamView.SetState(1);
    					EloamView.SetState(2);
    					alert("在摄像头界面中，按住鼠标拖动即可框选尺寸!");
    				}
    			}else{
    				alert("摄像头视频未打开!");
    			}
    		}
    		
    		function ShowVideo(){
    			CloseVideo();
    			if(EloamDevice){
    				var mode = document.getElementById('subtype');
    				var modeIdx = mode.options.selectedIndex;
    				if(-1 != modeIdx){
    					var modeText = mode.options[modeIdx].text;
    					var subtype = (modeText == "YUY2"? 1:(modeText == "MJPG"? 2:(modeText == "UYVY"? 4:-1)));
    				
    					var select = document.getElementById('resolution'); 
    					var nResolution = select.selectedIndex;
    					
    					EloamVideo = EloamDevice.CreateVideo(nResolution, subtype);
    					if(EloamVideo){
    						EloamView.SelectVideo(EloamVideo);
    						EloamView.SetText("打开视频中，请等待...", 0);
    					}
    				}	
    			}	
    		}
    		
    		function CloseVideo(){
    			if(EloamVideo){
    				EloamView.SetText("", 0);
    				EloamVideo.Destroy();
    				EloamVideo = null;
    			}	
    		}
    		
    		function Property(){
    			if(EloamDevice){
    				EloamDevice.ShowProperty(EloamView.GetView());
    			}
    		}
    		
    		function SetDeskew(obj){
    			if(EloamVideo){
    				if(obj.checked){
    					EloamVideo.EnableDeskewEx(1);
    				}else{
    					EloamVideo.DisableDeskew();
    				}
    			}
    		}
    		
    		function DelBkColor(obj){
    			if(EloamVideo){
    				if(obj.checked){
    					EloamVideo.EnableDelBkColor(0);
    				}else{
    					EloamVideo.DisableDelBkColor();
    				}
    			}
    		}
    		
    		function RotateLeft(){
    			if(EloamVideo){
    				EloamVideo.RotateLeft();
    			}
    		}
    		
    		function RotateRight(){
    			if(EloamVideo){
    				EloamVideo.RotateRight();
    			}
    		}

    		function Mirror(){
    			if(EloamVideo){
    				EloamVideo.Mirror();
    			}
    		}
    		
    		function Flip(){
    			if(EloamVideo){
    				EloamVideo.Flip();
    			}
    		}
    		
    		function SwitchCamera(){
    			var select = document.getElementById('device'); 
    			var devIdx = select.selectedIndex;
    			var devCount = select.options.length;
    			
    			var nextDevIdx = devIdx + 1;
    			if(nextDevIdx > (devCount - 1)){
    				nextDevIdx = 0;
    			}
    			select.options[nextDevIdx].selected = true;
    			
    			$('#device').trigger('change');
    		}
    		
    		function Scan(){
    			if(EloamVideo){
    				var date = new Date();
    				var yy = date.getFullYear().toString();
    				var mm = (date.getMonth() + 1).toString();
    				var dd = date.getDate().toString();
    				var hh = date.getHours().toString();
    				var nn = date.getMinutes().toString();
    				var ss = date.getSeconds().toString();
    				var mi = date.getMilliseconds().toString();
    				var baseDir = "D:\\DocScanner\\" + yy + mm + dd + "\\";
    				if(EloamGlobal.CreateDir(baseDir)){
    					var Name = baseDir + yy + mm + dd + hh + nn + ss + mi + ".jpg";
        				var image = EloamVideo.CreateImage(0, EloamView.GetView());
        				if(image){
        					EloamView.PlayCaptureEffect();
        					image.Save(Name, 0);
        					EloamThumbnail.Add(Name);
        					image.Destroy();
        					image = null;
        				}
    				}else{
    					alert('图像文件夹创建失败,请保证系统D盘有足够空间可用!');
    				}
    			}else{
    				alert('操作失败,未检测到设备摄像信息!');
    			}
    		}
    		
    		function Upload(){
    			if(EloamThumbnail){
    				var serverUrl = '<%=basePath%>UploadServlet?';
    				serverUrl += 'uploadUserId=${uploadUserId}&uploadUserName=${uploadUserName}';
    				serverUrl += '&uploadPath=${uploadPath}&attachKey=${attachKey}';
    				var busTableName = '${busTableName}';
    				if(busTableName != ''){
    					serverUrl += '&busTableName='+busTableName;
    				}
    				var busRecordId = '${busRecordId}';
    				if(busRecordId != ''){
    					serverUrl += '&busRecordId='+busRecordId;
    				}
    				
    				var scanLimit = '${scanLimit}';
    				var thumbImgCount = EloamThumbnail.GetCount();
    				if(thumbImgCount == 0){
    					alert('操作失败,请拍摄图像!');
    				}else if(thumbImgCount != scanLimit){
    					alert('操作失败,仅限拍摄'+scanLimit+'张图像!');
    				}else{
    					//设置缩略图勾选
    					for(var curIdx = 0; curIdx < scanLimit; curIdx++){
    						EloamThumbnail.SetCheck(curIdx, true);
    					}
    					var selImgIdx = EloamThumbnail.GetSelected();
        				if(selImgIdx != -1){
        					var uploadSuc = EloamThumbnail.HttpUploadCheckImage(serverUrl, 1);
            				if(uploadSuc){
            					var serverInfoStr = EloamThumbnail.GetHttpServerInfo();
            					if(serverInfoStr){
            						var docScannerResult = new Array();
            						//上传后调用，返回HTTP服务器返回消息，消息以##分隔 
            						$.each(serverInfoStr.split('##'), function(index, node){
            							if(node != ''){
            								var resultJson = JSON2.parse(node);
                							docScannerResult.push(resultJson);
            							}
            						});
            						art.dialog.data("docScannerResult", docScannerResult);
            						CancelScan();
            					}else{
            						alert('图像上传失败,服务器无响应!');
            					}
            				}else{
            					alert('图像上传失败,服务器出错!');
            				}
        				}else{
        					alert('操作失败,请点击并勾选待上传的图像文件!');
        				}
    				}
    			}
    		}
    		
    		function CancelScan(){
    			Unload();
    			art.dialog.close();
    		}
    		
    		$(document).ready(function(){
    			$(".gjgn").click(function(){
    				$(this).children('i').toggleClass('af');
	    			$(".gpy-af").toggle();
	    		});
    		});
        </script>
        <script language="Javascript" event="DevChange(type, idx, dbt)" for="EloamGlobal_ID" type="text/JavaScript">
      	//设备接入和丢失
      	//type设备类型， 1 表示视频设备， 2 表示音频设备
      	//idx设备索引
      	//dbt设备动作类型
        	if(1 == type){
        		//dbt 1 表示设备到达
    			if(1 == dbt){
	    			var select = document.getElementById('device'); 
	    			var name = EloamGlobal.GetFriendlyName(1, idx);
	    			select.add(new Option(name));
    			
    				var type = EloamGlobal.GetEloamType(1, idx);
    				//若为主摄像头，打开视频
    				if(1 == type){
    					select.selectedIndex = idx;
    				}
    				
    				selectDevice();
    			}
    			//dbt 2 表示设备丢失
    			if(2 == dbt){
    				if(EloamDevice){
    					if(idx == EloamDevice.GetIndex()){
    						EloamView.SetText("", 0);
    						if(EloamVideo){
	    						EloamVideo.Destroy();
	    						EloamVideo = null;
    						}
    				
	    					EloamDevice.Destroy();
	    					EloamDevice = null;
    					}
    				}
    			
	    			document.getElementById('device').options.remove(idx);
	    			selectDevice();
    			}
    		}
        </script>
    </head>
    <body onload="Load()" onunload="Unload()">
        <div class="gpy">
            <!-- 图像区域及主操作区域 -->
            <div class="clearfix">
                <div class="lfloat">
                    <div class="gpy-camera">
                        <object id="EloamView" classid="CLSID:26BA9E7F-78E9-4FB8-A05C-A4185D80D759" width="560" height="320"></object>
                    </div>
                    <div class="gpy-camera-zoom">
	                	<a href="javascript:void(0);" onclick="SwitchCamera();">
	                	    <span class="qh">切换摄像头</span>
	                	</a>
	                	<a href="javascript:void(0);" onclick="RotateLeft();">
	                	    <span class="jz">左 转</span>
	                	</a>
	                	<a href="javascript:void(0);" onclick="RotateRight();">
	                	    <span class="jz">右 转</span>
	                	</a>
	                	<a href="javascript:void(0);" onclick="Mirror();">
	                	    <span class="jz">左 右</span>
	                	</a>
	                	<a href="javascript:void(0);" onclick="Flip();">
	                	    <span class="jz">上 下</span>
	                	</a>
	                </div>
                </div>
                <div class="rfloat">
                    <div class="gpy-btn">
                    	<a href="javascript:void(0);" onclick="ShowVideo();">
                    		<i class="begin"></i>打开视频
                    	</a>
                    </div>
	            	<div class="gpy-btn">
	            		<a href="javascript:void(0);" onclick="CloseVideo();">
	            			<i class="stop"></i>关闭视频
	            		</a>
	            	</div>
	            	<div class="gpy-btn">
	            	    <a href="javascript:void(0);" onclick="Property();">设备属性</a>
	            	</div>
	            	<div class="gpy-btn gjgn">高级功能<i class="af"></i></div>
	                <div class="gpy-af">
	                	<div class="gpy-af-con">
	                	       设备列表<select id="device" class="select1" onchange="selectDevice();"></select>
	                    </div>
	                	<div class="gpy-af-con">
	                    	视频模式<select id="subtype" class="select1" onchange="selectSubtype();"></select>
	                    </div>
	                	<div class="gpy-af-con">
	                    	分辨率<select id="resolution" class="select1" onchange="selectResolution();"></select>
	                    </div>
	                	<div class="gpy-af-con">
	                    	尺寸<select id="selScanSize" class="select1" onchange="changeScanSize();">
	                    	    	<option selected>原始尺寸</option>
	                    	    	<option>中等尺寸</option>
	                    	    	<option>较小尺寸</option>
	                    	    	<option>自定义尺寸</option>
	                    	    </select>
	                    </div>
	                	<div class="gpy-af-con">
	                    	<label>
	                    	    <input id="setdeskew" type="checkbox" value="" onclick="SetDeskew(this);"/>纠偏裁边
	                    	</label>
	                    	<label>
	                    	    <input id="delbkcolor" type="checkbox" value="" onclick="DelBkColor(this);"/>去底色
	                    	</label>
	                    </div>
	                </div>
                </div>
            </div>
            <!-- 采集结果显示区域 -->
            <div class="gpy-camera-info">
                <object id="EloamThumbnail" classid="CLSID:B5535A1B-D25B-4B3C-854F-94B12E284A4E" width="717" height="130"></object>
            </div>
            <!-- 按钮区域 -->
            <div class="gpy-sub">
	        	<a class="camera" href="javascript:void(0);" onclick="Scan();">
	        	    <span><i></i>拍 照</span>
	        	</a>
	        	<a class="submit" href="javascript:void(0);" onclick="Upload();">
	        	    <span><i></i>确 认</span>
	        	</a>
	        	<a class="cancel" href="javascript:void(0);" onclick="CancelScan();">
	        	    <span><i></i>取 消</span>
	        	</a>
	        </div>
        </div>
    </body>
</html>