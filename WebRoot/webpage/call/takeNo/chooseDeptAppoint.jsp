<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/cssnew/style.css">
</head>

<body class="bg-none" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title">请选择办事部门！</div>
    	
    <div class="scroll-left">
        <div class="bd">           
        
           	<c:forEach items="${departList}" var="departInfo" varStatus="infoStatus">
	           	<c:choose>
	           		<c:when test="${infoStatus.index==0}">
	           			<ul class="scroll-list">
	           				<li class="color${infoStatus.index+1}"><a href="javascript:void(0);" 
	           				onclick="showAppointGridRecord('${departInfo.NAME}')" >
	           				<img src="webpage/call/takeNo/imagesnew/icon${departInfo.ICON_NO}.png">
	           				${departInfo.DEPART_NAME}</a></li>
	           		</c:when>
	           		<c:when test="${infoStatus.index%12==0}">
	           			</ul>
	           			<ul class="scroll-list">
	           				<li class="color${infoStatus.index+1}"><a href="javascript:void(0);" 
	           				onclick="showAppointGridRecord()" >
	           				${departInfo.DEPART_NAME}</a></li>
	           		</c:when>
	           		<c:otherwise>
							<li class="color${infoStatus.index+1}"><a href="javascript:void(0);" 
	           				onclick="showAppointGridRecord()" >
							${departInfo.DEPART_NAME}</a></li>
					</c:otherwise>
	           	</c:choose>
			</c:forEach>
            </ul>

        </div>
        <div class="hd">
            <a class="next"></a>
            <ul></ul>
            <a class="prev"></a>
        </div>        
    </div>
    
    <script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.SuperSlide.2.1.2.js"></script>
    <script type="text/javascript">
		jQuery(".scroll-left").slide({titCell:".hd ul",mainCell:".bd",autoPage:true,effect:"left",pnLoop:false,scroll:1,vis:1,delayTime:200,trigger:"click"});
		
		function showAppointGridRecord(data){
			alert(data);
			var resultJson = $.parseJSON(data)
	        alert(resultJson.NAME);
			var now=new Date();
			now.setMinutes (now.getMinutes () + 10);
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
	        var rowsData = $("#AppointGrid").datagrid("getChecked");
	        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
				art.dialog({
					content: "请选择需要被操作的记录!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(rowsData.length>1){
				art.dialog({
					content: "只能选择一条记录进行操作!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				if(rowsData[0].DATE_TIME>day){
					art.dialog({
						content: "非今天预约号，不能取号!",
						icon:"warning",
					    ok: true
					});
					return null;
				}
				if(rowsData[0].BEGIN_TIME<time){
					art.dialog({
						content: "预约号已过期!",
						icon:"warning",
					    ok: true
					});
					return null;
				}
				if(rowsData[0].IS_TAKE==1){
					art.dialog({
						content: "该预约已取号，不能重复取号!",
						icon:"warning",
					    ok: true
					});
					return null;
				}
				var entityId = rowsData[0].ID;
				$.dialog.open("appointmentController.do?appointmentTake&entityId="+entityId, {
					title : "预约取号",
					width : "450px",
					height : "250px",
					lock : true,
					resize : false
				}, false);
			}
		}
		function checkTime(i){
		    if (i<10){
		    	i="0" + i;
		    }
		    return i;
	    }
		
		function rowformater(value,row,index){
			if(value=='1'){
				return '是';
			}else if(value=='0'){
				return '否';
			}
		}
		
		function rowformaterstatus(value,row,index){
			if(value=='1'){
				return "<font color='blue'>正常</font>";
			}else if(value=='2'){
				return "<font color='red'>作废</font>";
			}
		}
		
		$(document).ready(function() {
			
			var appointTime = {
		    	elem: "#APPOINT.APPOINT_TIME",
			    format: "YYYY-MM-DD",
			    istime: false,
			    min:laydate.now()
			};
			laydate(appointTime);
			
			//AppUtil.initAuthorityRes("AppointToolbar");
		});
    </script>
</body>
</html>
