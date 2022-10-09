<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2"></eve:resources>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/css/style.css">
	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
<script type="text/javascript">
	function checkTime(i){
	    if (i<10){
	    	i="0" + i;
	    }
	    return i;
    }

	function backToParent(){
		window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
	}  
	function backToTop(){
		window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
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
<%--		$.dialog.open("callController.do?appointmentTake&entityId="+entityId, {--%>
<%--			title : "预约取号",--%>
<%--			width : "450px",--%>
<%--			height : "250px",--%>
<%--			lock : true,--%>
<%--			resize : false--%>
<%--		}, false);--%>
//AppUtil.ajaxProgress({
$.ajax({
	url : "callController.do?appointmentTakeNo",
	data:{entityId:id},
	success:function(resultJson) {
	//callback : function(resultJson) {
		//alert(resultJson);
		var resultJsonObj = JSON.parse(resultJson); 
		if(resultJsonObj.success){
			var child = eval("("+resultJsonObj.jsonString+")");
			var lineNo = child.lineNo;
			var waitNum = child.waitNum;
			var winNo = child.winNo;
			var roomNo = child.roomNo;	
			var createTime = child.createTime;
			var mobile = child.userMobile;	
			
			LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
			LODOP.PRINT_INIT("打印取号单");
			LODOP.SET_PRINT_PAGESIZE(1,800,840,''); 
			LODOP.SET_PRINT_STYLE("FontSize",10);//基本打印风格
			if(parentId=='2c90b38a5779662501579f6a0b820e0d'){
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
				LODOP.ADD_PRINT_TEXT(115,35,240,20,"排队号："+lineNo);
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
				
				LODOP.ADD_PRINT_TEXT(225,48,240,25,"当前等候人数："+waitNum+"人");
				LODOP.SET_PRINT_STYLEA(11,"FontName","宋体");
				LODOP.SET_PRINT_STYLEA(11,"FontSize",12);
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
			LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
			LODOP.SET_PRINT_STYLEA(4,"FontSize",20);
			
			//添加取号手机号码打印输出
			if(mobile!=null && mobile !=undefined && mobile!=""){
				LODOP.ADD_PRINT_TEXT(150,20,400,20,"手机号码："+mobile);
			}else{
				LODOP.ADD_PRINT_TEXT(150,20,400,20,"未录入手机号码,暂无法接收排队短信提醒!");
			}
						
			LODOP.ADD_PRINT_TEXT(180,48,240,25,"当前等候人数："+waitNum+"人");
			LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
			LODOP.SET_PRINT_STYLEA(6,"FontSize",14);
			}
			LODOP.ADD_PRINT_TEXT(250,48,240,25,"（请留意"+roomNo+"区"+winNo+"号窗口）");
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
// 	        var time = year+"-"+month+"-"+date+" "+hour+":"+minu+":"+sec;
	        var time = createTime;
			LODOP.ADD_PRINT_TEXT(280,7,280,25,"-----"+time+"-----");
			//LODOP.PREVIEW();
			LODOP.SET_PRINT_MODE("CATCH_PRINT_STATUS",true);
			var printid = LODOP.PRINT();
			//var pstatus=LODOP.GET_VALUE('PRINT_STATUS_ID',printid);
			setTimeout("isOver('"+printid+"')", 6 * 1000);					
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
									<span class="td5">预约部门</span>
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
									<span class="td5">${appointInfo.DEPART_NAME}</span>
									<span class="td6">
									<a href="javascript:void(0);" 
									onclick="showAppointGridRecord('${appointInfo.ID}',
									'${appointInfo.DATE_TIME}','${appointInfo.BEGIN_TIME}','${appointInfo.PARENT_ID}','${appointInfo.NAME}');">
									取号</span>
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

