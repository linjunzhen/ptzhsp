<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String businessCode = request.getParameter("businessCode");
request.setAttribute("businessCode", businessCode);
String departId=request.getParameter("departId");
request.setAttribute("departId",departId);
String roomNo = request.getParameter("roomNo");
request.setAttribute("roomNo", roomNo);
String businessName = request.getParameter("businessName");
request.setAttribute("businessName", businessName);
String itemCode = request.getParameter("itemCode");
request.setAttribute("itemCode", itemCode);
String itemName = request.getParameter("itemName");
request.setAttribute("itemName", itemName);
String defKey = request.getParameter("defKey");
request.setAttribute("defKey", defKey);
String materTypeId = request.getParameter("materTypeId");
request.setAttribute("materTypeId", materTypeId);
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
<body class="bg-none" onbeforeunload="Unload()" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请将身份证放入识别区！</a></div>
    	
    <div class="readcard"><img id="img" src="webpage/call/takeNo/imagesnew/readcard.png" ></div>
<c:if test="${roomNo!='A'&&roomNo!='B'}">
	<div align="center"><span style="color:#F00;font-size:15px;font-weight:bold;">${businessName} 当前排队人数：</span><span id="demo" style="color:#F00;font-size:15px;font-weight:bold;"></span></div>
</c:if>
	<c:if test="${roomNo=='A'||roomNo=='B'}">
		<div align="center"><span style="color:#F00;font-size:15px;font-weight:bold;">${roomNo}厅排队人数：</span>
                 <span id="demo" style="color:#F00;font-size:15px;font-weight:bold;"></span>
				<span style="color:#F00;margin-left:10px;font-size:15px;font-weight:bold;">${businessName} 当前排队人数：</span>
                      <span id="demo1" style="color:#F00;font-size:15px;font-weight:bold;"></span>
		</div>
	</c:if>
	<script type="text/javascript">
		var waiting = 0;
		$(function(){
            writeWaitNum();
		})
		var LODOP;
		function window_onUnload(){
		    rdcard.DeleteOutputFile();
		    rdcard.DeleteAllPicture();
		    rdcard.EndRead();
		}
		function writeWaitNum(){
		    var roomNo="${roomNo}";
		    if(roomNo=='A'||roomNo=='B'){
                $.ajax({
                    url:"newCallController/waitingNumOfRoomNum.do",
                    data:{roomNo:"${roomNo}"},
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        waiting = data.total;
                        document.getElementById("demo").innerHTML=waiting;
                    },
                    error:function(data){}
                })
                $.ajax({
                    url:"newCallController/waitingGrid.do",
                    data:{businessCode:"${businessCode}"},
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        waiting = data.total;
                        document.getElementById("demo1").innerHTML=waiting;
                    },
                    error:function(data){}
                })
            }else{
                $.ajax({
                    url:"newCallController/waitingGrid.do",
                    data:{businessCode:"${businessCode}"},
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        waiting = data.total;
                        document.getElementById("demo").innerHTML=waiting;
                    },
                    error:function(data){}
                })
            }
		}
		function backToParent(){
			history.go(-1);
		}
		function backToTop(){
			window.parent.document.getElementById('takeFrame').src="newCallController/toYctbBusinessItemChoose.do?roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}";
		}
		function checkTime(i){
		    if (i<10){
		    	i="0" + i;
		    }
		    return i;
	    }
		window.onload=function(){
			rdcard.ReadCard2();
	        window.setTimeout("backToTop()", 30 * 1000);
	    }
	    
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

<script LANGUAGE="javascript" for="idcard" event="Readed()">
	var now=new Date();
	now.setMinutes (now.getMinutes () + 10);
	var year=now.getFullYear();
	var month=now.getMonth();
	var date=now.getDate();
	month=month+1;
	month=checkTime(month);
	date=checkTime(date);
	var day = year+"-"+month+"-"+date;
	var today = year+month+date;
	
	var hour=now.getHours();
	var minu=now.getMinutes();
	hour=checkTime(hour);
	minu=checkTime(minu);
	var time = hour+":"+minu;
	var limiTtime = "17:00";
	if (day>(year+"-06-01")&&day<(year+"-10-01")){
		limiTtime = "17:30";
	}
	var departId = '${departId}';
	var businessCode = '${businessCode}';
	var roomNo = '${roomNo}';
	var businessName = '${businessName}';
	var itemCode = '${itemCode}';
	var itemName = '${itemName}';
	var defKey = '${defKey}';
	var materTypeId = '${materTypeId}';
	if(businessCode=="")return ;
    var name = rdcard.NameS;
    var myname=name+"";
    var strname=myname.substr(0,1)+"*"+myname.substr(2,1);
    var cardNo = rdcard.CardNo;
	//有效期限截止日期
    var ActivityTo = rdcard.ActivityTo;
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
		$.post("newCallController/generateNo.do",{
	   		businessCode:businessCode,
	   		lineName:name,
	   		roomNo:roomNo,
	   		itemCode:itemCode,
	   		itemName:itemName,
	   		defKey:defKey,
	   		materTypeId:materTypeId,
	   		lineCardNo:cardNo,
	   		LINE_ZJLX:'SF'
		}, function(responseText, status, xhr) {
			if(responseText&&responseText!="websessiontimeout"){
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success){
					var child = eval("("+resultJson.jsonString+")");
					var lineNo = child.lineNo;
					var waitNum = child.waitNum;
					var winNo = child.winNo;
					var businessWaitNum=child.businessWaitNum;
					var mobile = child.userMobile;
					$("#img").attr("src","webpage/call/takeNo/images/printing.png");
					//设置打印
					LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
					LODOP.PRINT_INIT("打印取号单");
					LODOP.SET_PRINT_PAGESIZE(1,800,840,''); 
					LODOP.SET_PRINT_STYLE("FontSize",10);//基本打印风格					
					if(departId=='2c90b38a571eb7e40157265c46261c7a'){
						LODOP.ADD_PRINT_TEXT(0,25,240,25,"平潭综合实验区");
						LODOP.SET_PRINT_STYLEA(1,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(1,"FontSize",22);
						LODOP.ADD_PRINT_TEXT(30,35,240,25,"行政服务中心");
						LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(2,"FontSize",18);
						LODOP.ADD_PRINT_TEXT(65,15,240,25,"（平潭综合实验区微信公众号预约取号）");
						LODOP.SET_PRINT_STYLEA(3,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(3,"FontSize",13);
						
						LODOP.ADD_PRINT_TEXT(85,105,240,25,strname);
						LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
						LODOP.SET_PRINT_STYLEA(4,"FontSize",13);
						LODOP.ADD_PRINT_TEXT(110,35,240,20,"排队号："+lineNo);
						LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
						LODOP.SET_PRINT_STYLEA(5,"FontSize",16);
						
						//添加取号手机号码打印输出
						if(mobile!=null && mobile !=undefined && mobile!=""){
							LODOP.ADD_PRINT_TEXT(130,20,400,20,"手机号码："+mobile);
						}else{
							LODOP.ADD_PRINT_TEXT(130,20,400,20,"未录入手机号码,暂无法接收排队短信提醒!");
						}
							
						LODOP.ADD_PRINT_TEXT(145,0,300,15,"温馨提示：排队请遵守预约优先原则；");
						LODOP.ADD_PRINT_TEXT(160,0,300,15,"不动产登记业务取号必须用权利人身份证，");
						LODOP.ADD_PRINT_TEXT(175,0,320,15,"已办理委托的，须提供受托人身份证，");
						LODOP.ADD_PRINT_TEXT(190,0,320,15,"取号单名字须与权利人或受托人一致，");
						LODOP.ADD_PRINT_TEXT(205,0,320,15,"否则取号无效");

                        if(roomNo=='A'||roomNo=='B'){
                            LODOP.ADD_PRINT_TEXT(200,48,240,25,roomNo+"厅等候人数："+waitNum+"人");
                            LODOP.ADD_PRINT_TEXT(220,48,240,25,businessName+"  等待人数"+businessWaitNum+"人");
                        }else{
                            LODOP.ADD_PRINT_TEXT(220,48,240,25,"当前等候人数："+waitNum+"人");
                        }
						LODOP.SET_PRINT_STYLEA(12,"FontName","宋体");
						LODOP.SET_PRINT_STYLEA(12,"FontSize",12);
					}else{
						LODOP.ADD_PRINT_TEXT(20,25,240,25,"平潭综合实验区");
						LODOP.SET_PRINT_STYLEA(1,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(1,"FontSize",24);
						LODOP.ADD_PRINT_TEXT(50,35,240,25,"行政服务中心");
						LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(2,"FontSize",18);
						LODOP.ADD_PRINT_TEXT(90,15,240,25,"（平潭综合实验区微信公众号预约取号）");
						LODOP.SET_PRINT_STYLEA(3,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(3,"FontSize",14);
						LODOP.ADD_PRINT_TEXT(120,35,240,25,"排队号："+lineNo);
						
						//添加取号手机号码打印输出
						if(mobile!=null && mobile !=undefined && mobile!=""){
							LODOP.ADD_PRINT_TEXT(150,20,400,20,"手机号码："+mobile);
						}else{
							LODOP.ADD_PRINT_TEXT(150,20,400,20,"未录入手机号码,暂无法接收排队短信提醒!");
						}
							
						if(time>limiTtime){
							LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(6,"FontSize",14);
							LODOP.ADD_PRINT_TEXT(160,15,240,25,"（对外办公时间即将结束，");
							LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(6,"FontSize",14);
							LODOP.ADD_PRINT_TEXT(180,15,240,25,"你所取的号可能无法及时办理。");
							LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(6,"FontSize",14);
							LODOP.ADD_PRINT_TEXT(200,15,240,25,"感谢你对中心工作的支持。）");
						}
						LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
						LODOP.SET_PRINT_STYLEA(4,"FontSize",20);
						if(roomNo=='A'||roomNo=='B'){
                            LODOP.ADD_PRINT_TEXT(220,48,240,25,roomNo+"厅等候人数："+waitNum+"人");
                            LODOP.ADD_PRINT_TEXT(240,48,240,25,businessName+"  等待人数"+businessWaitNum+"人");
						}else{
                            LODOP.ADD_PRINT_TEXT(240,48,240,25,"当前等候人数："+waitNum+"人");
						}
					    LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
					    LODOP.SET_PRINT_STYLEA(6,"FontSize",14);
					}
					if(winNo=="115"){
						LODOP.ADD_PRINT_TEXT(270,48,240,25,"（请留意"+roomNo+"区台胞接待室）");
					}else{
						LODOP.ADD_PRINT_TEXT(270,48,240,25,"（请留意"+roomNo+"区"+winNo+"号窗口）");
					}
			        var now=new Date();            //创建Date对象
			        var year=now.getFullYear();    //获取年份
			        var month=now.getMonth();    //获取月份
			        var date=now.getDate();        //获取日期
			        var hour=now.getHours();    //获取小时
			        var minu=now.getMinutes();    //获取分钟
			        var sec=now.getSeconds();    //获取秒钟
			        hour=checkTime(hour);
			        minu=checkTime(minu);
			        sec=checkTime(sec);
			        month=month+1;
			        month=checkTime(month);
			        date=checkTime(date);
					var time = year+"-"+month+"-"+date+" "+hour+":"+minu+":"+sec;
					LODOP.ADD_PRINT_TEXT(280,7,280,25,"-----"+time+"-----");
					LODOP.SET_PRINT_MODE("CATCH_PRINT_STATUS",true);
					var printid = LODOP.PRINT();
					//LODOP.PREVIEW();
					setTimeout("isOver('"+printid+"')", 6 * 1000);
				}else{
					departIds = "";
					parent.art.dialog({
						content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">"+resultJson.msg+"</font>",
						icon:"warning",
						time:3,
						width:"400px",
						height:"150px",
						ok: false
					});
	        		window.setTimeout("backToTop()", 3 * 1000);
				}
			}
		});
	}
</script>

</html>
