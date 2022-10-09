<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% long hours = new Date().getHours(); request.setAttribute("hours", hours); %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
<!-- 	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script> -->
<OBJECT ID="Print1" WIDTH="0" HEIGHT="0"
		CLASSID="CLSID:0B8B9747-7B3A-42CB-ABC3-9A204A5A6EFD"></OBJECT>   <!-- 注册刷卡事件   -->
<script type="text/javascript">
	function checkTime(i){
	    if (i<10){
	    	i="0" + i;
	    }
	    return i;
    }

	function backToParent(){
		var roomNo = '${roomNo}';
		if (roomNo == 'D') {
			window.parent.document.getElementById('takeFrame').src="callController.do?toYqyzTypeChooseMacW&roomNo=${roomNo}";
		} else if(roomNo == 'H'||roomNo == 'I'){
			window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
		}else {
			window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChooseMacW&roomNo=${roomNo}";
		}
	}  
	function backToTop(){
		var roomNo = '${roomNo}';
		if (roomNo == 'D') {
			window.parent.document.getElementById('takeFrame').src="callController.do?toYqyzTypeChooseMacW&roomNo=${roomNo}";
		} else if(roomNo == 'H'){
			window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
		} else {
			window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChooseMacW&roomNo=${roomNo}";
		}

	}  
	window.onload=function(){
        window.setTimeout("backToTop()", 30 * 1000);
    }
	function showAppointGridRecord(entityId,dateTime,beginTime,parentId,name){
		var id = entityId;
		var myname=name+"";
	    var strname=myname.substr(0,1)+"*"+myname.substr(2,1);
		var now=new Date();
		now.setMinutes (now.getMinutes () - 30);
        var year=now.getFullYear();
        var month=now.getMonth();
        var date=now.getDate();
        month=month+1;
        month=checkTime(month);
        date=checkTime(date);
        var day = year+"-"+month+"-"+date;
        var hour=now.getHours();
        var minu=now.getMinutes();
        hour=checkTime(hour);
        minu=checkTime(minu);
        var time = hour+":"+minu;
		var limiTtime = "17:00";
$.ajax({
	url : "newCallController/appointmentTakeNo.do",
	data:{entityId:id},
	success:function(resultJson) {
		var resultJsonObj = JSON.parse(resultJson); 
		if(resultJsonObj.success){
			var child = eval("("+resultJsonObj.jsonString+")");
			var lineNo = child.lineNo;
			var waitNum = child.waitNum;
			var winNo = child.winNo;
			var roomNo = child.roomNo;	
			var mobile = child.userMobile;
			
					//设置打印
					Print1.OnStart();
					Print1.SetFontName("隶书");
					Print1.SetFontSize(22);
					Print1.PrintText("  平潭综合实验区");
					Print1.PrintText("   行政服务中心");
					Print1.SetFontSize(8);
					Print1.PrintText(" ");
					Print1.PrintText("     （平潭综合实验区微信公众号预约取号）");
					Print1.PrintText(" ");
					Print1.SetFontName("宋体");
					Print1.SetFontSize(20);
					Print1.PrintText("    排队号："+lineNo);
						
					//添加取号手机号码打印输出
					Print1.SetFontSize(8);
					if(mobile!=null && mobile !=undefined && mobile!=""){
						Print1.PrintText("    手机号码："+mobile);
					}else{
						Print1.PrintText("    未录入手机号码,暂无法接收排队短信提醒!");
					}
					
					Print1.PrintText(" ");
					Print1.PrintText(" ");	
					if(time>limiTtime){
						Print1.SetFontSize(14);
						Print1.PrintText("  （对外办公时间即将结束，");
						Print1.PrintText("你所取的号可能无法及时办理。");
						Print1.PrintText("感谢你对中心工作的支持。）");
					}
					Print1.SetFontSize(14);
					Print1.PrintText("     当前等候人数："+waitNum+"人");
					Print1.PrintText(" ");
					Print1.SetFontSize(8);
					if(winNo=="115"){
						Print1.PrintText("  （请留意"+roomNo+"区台胞接待室）");
					}else{
						Print1.PrintText("  （请留意"+roomNo+"区"+winNo+"号窗口）");
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
					Print1.PrintText("-----"+time+"-----");
					Print1.PrintText(" ");
					Print1.PrintText(".");
					Print1.OnEnd();
					setTimeout("backToTop()", 3 * 1000);					
		}else{
			art.dialog({
				content: resultJsonObj.msg,
				icon:"error",
				ok: true
			});
    		window.setTimeout("backToTop()", 3 * 1000);
		}
	}
});
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
    
</script>
</head>
<body>
<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png"></a></div>
    
	<div class="index-main">
		<!--下-->
		<div class="index-top">
			
			<!--下中-->
			<div class="index-center">
				<div class="index-tyjksj">
					<div class="index-list">
						<div class="list-hd">
							<ul>
								<li>
									<span class="td1">姓名</span>
									<span class="td2">身份证号</span>
									<span class="td3">预约日期</span>
									<span class="td4">预约时段</span>
									<span class="td5">预约业务</span>
									<span class="td6">操作</span>
								</li>
							</ul>
						</div>
						<div class="list-bd">
							<ul>
           					<c:forEach items="${appointList}" var="appointInfo" varStatus="infoStatus">
								<li>
									<span class="td1">${appointInfo.NAME}</span>
									<span class="td2">${appointInfo.CARD}</span>
									<span class="td3">${appointInfo.DATE_TIME}</span>
									<span class="td4">${appointInfo.BEGIN_TIME}-${appointInfo.END_TIME}</span>
									<span class="td5">${appointInfo.BUSINESS_NAME}</span>
									<span class="td6">
										<c:if test="${fn:substring(appointInfo.END_TIME,0,2)>hours }">
									<a
									onclick="showAppointGridRecord('${appointInfo.RECORD_ID}',
									'${appointInfo.DATE_TIME}','${appointInfo.BEGIN_TIME}','${appointInfo.DEPART_ID}','${appointInfo.NAME}');">
										取号</a>
									</span>
									</c:if>
									<c:if test="${fn:substring(appointInfo.END_TIME,0,2)<= hours }">
										<a href="javascript:void(0);">
											过期</a>
										</span>
									</c:if>
								</li>
           					</c:forEach>
           					</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

