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
	<meta http-equiv="X-UA-Compatible" content="IE=10" /> 
    <meta name="description" content="">  
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>自助取号系统</title>
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/cssnew/style.css">
</head>
<body class="bg-none" onbeforeunload="window_onUnload()" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">
	<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请将身份证放入识别区。</a></div>
    <div class="readcard"><img id="img" src="webpage/call/takeNo/imagesnew/readcard.png" ></div>


	<script type="text/javascript">
		function backToParent(){
			history.go(-1);
		}
		function backToTop(){
			var roomNo = '${roomNo}';
			if (roomNo == 'D') {
				window.parent.document.getElementById('takeFrame').src="callController.do?toYqyzTypeChooseMacW&roomNo=${roomNo}";
			} else if(roomNo == 'H'||roomNo == 'I'){
				window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
			} else{
				window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChooseMacW&roomNo=${roomNo}";
			}

		}  
		function checkTime(i){
		    if (i<10){
		    	i="0" + i;
		    }
		    return i;
	    }
		window.onload=function(){
			ReaddataCard();
			// /*====================二维码部分=================*/
			OnStartScan();
			/*====================二维码部分=================*/
	        window.setTimeout("backToTop()", 30 * 1000);
	    }
		function window_onUnload(){
			PrintIDCard1.EndReadIdCard();
			PrintIDCard1.StartReadIdCard();
			OnCloseScan();
		}
		function ReaddataCard()
		{
			var BeginTime=PrintIDCard1.GetBeginTime();
			var  EndTime=PrintIDCard1.GetEndTime();
			var  Dep=PrintIDCard1.GetDep();
			var  IDNum=PrintIDCard1.GetIDNum();
			var  Addr=PrintIDCard1.GetAddr();
			var  Name=PrintIDCard1.GetName();
			var  Sex=PrintIDCard1.GetSex();
			var  Folk=PrintIDCard1.GetFolk();
			var  PicPath=PrintIDCard1.GetPicPath();
			var  Birth=PrintIDCard1.GetBirth();
			if(Dep!="")
			{
				document.getElementById("idcard").value=IDNum;
				document.getElementById("idcardname").value=Name;
				PrintIDCard1.EndReadIdCard();
			}
		}
		var LODOP;
	    function isOver(printid){
	    	if((!LODOP.GET_VALUE('PRINT_STATUS_OK',printid))&&LODOP.GET_VALUE('PRINT_STATUS_EXIST',printid)){
				departId = "";			
				parent.art.dialog({
					id:"actionInfo",
					content: "<font style=\"font-size:24px;color:red;font-family:\"汉仪综艺体简\";\">取号机缺纸或卡纸，请到本层其他取号机取号或联系工作人员！</font>",
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

<OBJECT ID="PrintIDCard1" WIDTH="0" HEIGHT="0"
		CLASSID="CLSID:A7DE699C-E219-4804-971C-309AB90F06CC"></OBJECT> 
<OBJECT ID="Print1" WIDTH="0" HEIGHT="0"
		CLASSID="CLSID:0B8B9747-7B3A-42CB-ABC3-9A204A5A6EFD"></OBJECT>   <!-- 注册刷卡事件   -->


<script LANGUAGE="javascript" for="PrintIDCard1" event="OnIdCard()">

	var BeginTime=PrintIDCard1.GetBeginTime();
	var  EndTime=PrintIDCard1.GetEndTime();
	var  Dep=PrintIDCard1.GetDep();
	var  IDNum=PrintIDCard1.GetIDNum();
	var  Addr=PrintIDCard1.GetAddr();
	var  Name=PrintIDCard1.GetName();
	var  Sex=PrintIDCard1.GetSex();
	var  Folk=PrintIDCard1.GetFolk();
	var  PicPath=PrintIDCard1.GetPicPath();
	var  Birth=PrintIDCard1.GetBirth();
	
	var now=new Date();
	var year=now.getFullYear();
	var month=now.getMonth();
	var date=now.getDate();
	month=month+1;
	month=checkTime(month);
	date=checkTime(date);
	var today = year+month+date;
	var roomNo = '${roomNo}';
	//if(departId=="")return ;
    var name = PrintIDCard1.GetName();
    var cardNo = PrintIDCard1.GetIDNum();
    if(cardNo==""||cardNo==null){
		parent.art.dialog({
			content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">未能有效识别您的身份证，请重试！</font>",
			icon:"warning",
			time:3,
			width:"300px",
			height:"150px",
			ok: false
		});
	}else if(false){		
		parent.art.dialog({
			content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">您的身份证已过期，无法取号！</font>",
			icon:"warning",
			time:3,
			width:"300px",
			height:"150px",
			ok: false
		});
	}else{
    	location.href="newCallController/toAppointChooseMacW.do?roomNo="+roomNo+"&cardNo="+cardNo;
	}
</script>

  <script language="JavaScript" for="PrintIDCard1" event="OnCardID(CardID)">
	  var now=new Date();
	  var year=now.getFullYear();
	  var month=now.getMonth();
	  var date=now.getDate();
	  month=month+1;
	  month=checkTime(month);
	  date=checkTime(date);
	  var today = year+month+date;
	  var roomNo = '${roomNo}';
	  if(CardID.substring(0,1) == "]") {
		  CardID = CardID.substring(3);
	  }
	  $.ajax({
		  url:"callYctbController/yctbGetIdentityByQrcodeforHlw.do",
		  type:"post",
		  data:{qrcode:CardID},
		  success:function (data) {
			  if (data != null){
				  var identity = JSON.parse(JSON.parse(JSON.parse(data)));
				  if(identity.msg == "success"){
					  var cardNo = identity.number
					  var name = identity.name
					  var roomNo = '${roomNo}';
					  if (cardNo==""||cardNo==null){
						  parent.art.dialog({
							  content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">未能有效识别您的身份，请重试！</font>",
							  icon:"warning",
							  time:3,
							  width:"300px",
							  height:"150px",
							  ok: false
						  });
					  } else {
						  location.href="newCallController/toAppointChooseMacW.do?roomNo="+roomNo+"&cardNo="+cardNo;
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
  </script>

</html>
