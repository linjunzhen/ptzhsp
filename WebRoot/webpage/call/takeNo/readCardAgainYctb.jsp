<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String departId = request.getParameter("departId");
request.setAttribute("departId", departId);
String roomNo = request.getParameter("roomNo");
request.setAttribute("roomNo", roomNo);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="expires" content="0" />
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>自助取号系统</title>
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/cssnew/style.css">
</head>
<body class="bg-none" onbeforeunload="window_onUnload()" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请将身份证放入识别区！</a></div>
    
<!-- 	<div class="eui-title"><a href="javascript:void(0);" onclick="toChildDepart();">刷</a></div> -->
	
    <div class="readcard"><img id="img" src="webpage/call/takeNo/imagesnew/readcard.png" ></div>


	<script type="text/javascript">
		var LODOP;
		function window_onUnload(){
		    rdcard.DeleteOutputFile();
		    rdcard.DeleteAllPicture();
		    rdcard.EndRead();
			OnCloseScan();
		}
		function backToParent(){
			history.go(-1);
		}
		function backToTop(){
// 			window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
			window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChoose&roomNo=${roomNo}";
		}  
		function checkTime(i){
		    if (i<10){
		    	i="0" + i;
		    }
		    return i;
	    }
		window.onload=function(){
			rdcard.ReadCard2();
			/*====================二维码部分=================*/
            PrintIDCard1.OnStartScan();
			/*====================二维码部分=================*/
	        window.setTimeout("backToTop()", 30 * 1000);
	    }
	    
	    function isOver(printid){
	    	if((!LODOP.GET_VALUE('PRINT_STATUS_OK',printid))&&LODOP.GET_VALUE('PRINT_STATUS_EXIST',printid)){
				departId = "";						
				parent.art.dialog({
					id:"actionInfo",
					content: "<font style=\"font-size:24px;color:red;font-family:\"汉仪综艺体简\";\">打印机缺纸或卡纸，请联系工作人员！</font>",
					icon:"warning",
					time:0,
					width:"400px",
					height:"150px",
					ok: false
				});
			}else{
				$("#img").attr("src","webpage/call/takeNo/images/printover.png");
				//打印结束
				departId = "";

				window.setTimeout("backToTop()", 3 * 1000);
			}
	    }

		function toChildDepart(){
			var departId = '${departId}';
			var roomNo = '${roomNo}';
			//if(departId=="")return ;
		    var name = "陈成";
		    var cardNo = "350123199312183514";
		    if(cardNo==""||cardNo==null){
				parent.art.dialog({
					content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">未能有效识别您的身份证，请重试！</font>",
					icon:"warning",
					time:3,
					width:"300px",
					height:"150px",
					ok: false
				});
			}else{
// 		    	location.href="callController.do?toAgainChoose&roomNo="+roomNo+"&cardNo="+cardNo;
// 		    	location.href="newCallController/toAgainChoose.do?roomNo="+roomNo+"&cardNo="+cardNo;
		    	location.href="newCallController/toYctbAgainChoose.do?roomNo="+roomNo+"&cardNo="+cardNo;
			}
		}

		/*====================二维码部分=================*/
		function OnStartScan()
		{
			PrintIDCard1.OnStartScan();
		}
		function OnCloseScan()
		{
			PrintIDCard1.OnCloseScan();
		}
		/*====================二维码部分=================*/
	</script>
</body>

<OBJECT
	  classid="clsid:F1317711-6BDE-4658-ABAA-39E31D3704D3"                  
	  codebase="SDRdCard.cab#version=1,3,6,4"
	  width="0"
	  height="0"
	  align="center"
	  hspace="0"
	  vspace="0"
	  id="idcard"
	  name="rdcard"	
>
</OBJECT>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>

  <%--------------------------二维码部分-----------------------%>
  <OBJECT ID="PrintIDCard1" WIDTH="0" HEIGHT="0"
		  CLASSID="CLSID:A7DE699C-E219-4804-971C-309AB90F06CC"></OBJECT>
  <%--------------------------二维码部分-----------------------%>

  <script LANGUAGE="javascript" for="idcard" event="Readed()">
	var roomNo = '${roomNo}';
    var name = rdcard.NameS;
    var cardNo = rdcard.CardNo;
    if(cardNo==""||cardNo==null){
		parent.art.dialog({
			content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">未能有效识别您的身份证，请重试！</font>",
			icon:"warning",
			time:3,
			width:"300px",
			height:"150px",
			ok: false
		});
	}else{
    	//location.href="callController.do?toAgainChoose&roomNo="+roomNo+"&cardNo="+cardNo;
    	location.href="newCallController/toAgainChoose.do?roomNo="+roomNo+"&cardNo="+cardNo;
	}
</script>

  <script language="JavaScript" for="PrintIDCard1" event="OnCardID(CardID)">
	  if(CardID.substring(0,1) == "]") {
		  CardID = CardID.substring(3);
	  }
	  if (CardID != null&&CardID.length > 0) {
	  	$.ajax({
			url:"callYctbController/yctbGetIdentityByQrcodeforHlw.do",
			type:"post",
			data:{qrcode:CardID},
			success:function (data) {
				alert(data + "1111")
				if (data != null) {
					var identity = JSON.parse(JSON.parse(data));
					if(identity.msg == "success") {
						var cardNo = identity.number
						var name = identity.name
						var roomNo = '${roomNo}';
						if (cardNo==""||cardNo==null) {
							parent.art.dialog({
								content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">未能有效识别您的身份，请重试！</font>",
								icon:"warning",
								time:3,
								width:"300px",
								height:"150px",
								ok: false
							});
						} else {
							location.href="newCallController/toAgainChoose.do?roomNo="+roomNo+"&cardNo="+cardNo;
						}

					} else {
						parent.art.dialog({
							content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">"+identity.msg+"</font>",
							icon:"warning",
							time:3,
							width:"300px",
							height:"150px",
							ok: false
						});
					}
				}
			}
		})
	  }
  </script>

</html>
