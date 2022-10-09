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
	<title>请选择分类，排队取号！</title>
	<link rel="stylesheet" type="text/css" href="webpage/call/takeNo/css/style.css"/>
	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!-- <script type="text/javascript" src="webpage/call/takeNo/js/jquery1.42.min.js"></script> -->
	<script type="text/javascript" src="webpage/call/takeNo/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
	<script type="text/javascript" src="plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
    <link rel="stylesheet" href="plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>

	<script type="text/javascript">
	function realSysTime(clock){
        var now=new Date();            //创建Date对象
        var year=now.getFullYear();    //获取年份
        var month=now.getMonth();    //获取月份
        var date=now.getDate();        //获取日期
        var day=now.getDay();        //获取星期
        var hour=now.getHours();    //获取小时
        var minu=now.getMinutes();    //获取分钟
        var sec=now.getSeconds();    //获取秒钟
        hour=checkTime(hour);
        minu=checkTime(minu);
        sec=checkTime(sec);
        month=month+1;
        month=checkTime(month);
        date=checkTime(date);
        var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var week=arr_week[day];        //获取中文的星期
        var day=year+"年"+month+"月"+date+"日 "+week;
        document.getElementById("day").innerHTML=day;
        var time=hour+" : "+minu+" : "+sec;    //组合系统时间
        clock.innerHTML=time;    //显示系统时间
    }
	function checkTime(i){
	    if (i<10){
	    	i="0" + i;
	    }
	    return i;
    }
    window.onload=function(){
        window.setInterval("realSysTime(clock)",1000);    //实时获取并显示系统时间
    }

    var departIds = "";
    function toChildDepart(departId){
    	$.post("callController.do?toChildDepart",{
    		departId:departId
		}, function(responseText, status, xhr) {
			if(responseText&&responseText!="websessiontimeout"){
				var obj = eval("("+responseText+")");
				var child = eval("("+obj.jsonString+")");
				var str = "<ul>";
				if(child.length<1){
			    	departIds = departId;
					$("#cardInfo").attr("style","");
					$("#parentDepart").attr("style","display: none;");
				}else{
					for(var i=0;i<child.length;i++){
						str+="<li><a href=\"javascript:void(0);\" onclick=\"readCard('"+child[i].DEPART_ID+"');\">"+child[i].DEPART_NAME+"</a></li>"
					}
					str+="</ul>";
					document.getElementById("childDepart").innerHTML=str;
					$("#childDepart").attr("style","");
					$("#parentDepart").attr("style","display: none;");
				}
				$("#chooseTitle").html("<a href=\"javascript:void(0);\" onclick=\"back();\"> <img src=\"webpage/call/takeNo/images/01.png\" /></a>");
			}
		});
    }
    function readCard(departId){
    	departIds = departId;
    	$("#cardInfo").attr("style","");
		$("#childDepart").attr("style","display: none;");
		$("#parentDepart").attr("style","display: none;");
		window.setTimeout("back()", 20 * 1000);
    }
    
    function back(){
    	departIds = "";
		$("#parentDepart").attr("style","");
		$("#childDepart").attr("style","display: none;");
		$("#cardInfo").attr("style","display: none;");
		$("#chooseTitle").html("目前仅支持试点业务！");
    } 
    function takeNo(){
    	$.post("callController.do?takeNo",{
    		departId:departIds,
    		lineName:"李夏怡",
    		lineCardNo:"352201198812131234",
	    	LINE_ZJLX:'SF'
		}, function(responseText, status, xhr) {
			if(responseText&&responseText!="websessiontimeout"){
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success){
					var child = eval("("+resultJson.jsonString+")");
					var lineNo = child.lineNo;
					var waitNum = child.waitNum;
					var winNo = child.winNo;
					$("#img").attr("src","webpage/call/takeNo/images/printing.png");
					//设置打印
					var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
					LODOP.PRINT_INIT("打印取号单");
					LODOP.SET_PRINT_PAGESIZE(1,800,830,''); 
					//LODOP.ADD_PRINT_RECT(0,0,320,500,0,1);//大小
					LODOP.SET_PRINT_STYLE("FontSize",11);//基本打印风格
					LODOP.ADD_PRINT_TEXT(20,25,240,25,"平潭综合试验区");
					LODOP.SET_PRINT_STYLEA(1,"FontName","隶书");
					LODOP.SET_PRINT_STYLEA(1,"FontSize",24);
					LODOP.ADD_PRINT_TEXT(50,35,240,25,"行政服务中心");
					LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
					LODOP.SET_PRINT_STYLEA(2,"FontSize",24);
					LODOP.ADD_PRINT_TEXT(120,35,240,25,"排队号："+lineNo);
					LODOP.SET_PRINT_STYLEA(3,"FontName","宋体");
					LODOP.SET_PRINT_STYLEA(3,"FontSize",20);
					LODOP.ADD_PRINT_TEXT(180,48,240,25,"当前等候人数："+waitNum+"人");
					LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
					LODOP.SET_PRINT_STYLEA(4,"FontSize",14);
					LODOP.ADD_PRINT_TEXT(210,60,240,25,"（请留意"+winNo+"号窗口）");
					/* LODOP.ADD_PRINT_TEXT(300,5,240,25,"温馨提示：");
					LODOP.ADD_PRINT_TEXT(330,5,285,25,"1、本单须在当天交办理窗口收执，超过时间请重新取号；");
					LODOP.ADD_PRINT_TEXT(375,5,285,25,"2、叫号三次未到窗口办理，本单作废；");
					LODOP.ADD_PRINT_TEXT(405,5,285,25,"3、请您留意窗口叫号，以免影响您的办事。"); */
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
			        var time = year+month+date+" "+hour+":"+minu+":"+sec;
					LODOP.ADD_PRINT_TEXT(280,7,280,25,"--------"+time+"--------");
					//LODOP.PREVIEW();
					//LODOP.PRINT();
					//打印结束					
					$("#img").attr("src","webpage/call/takeNo/images/printover.png");
					departIds = "";
					setTimeout("$(\"#cardInfo\").attr(\"style\",\"display: none;\");$(\"#parentDepart\").attr(\"style\",\"\");$(\"#childDepart\").attr(\"style\",\"display: none;\");$(\"#chooseTitle\").html(\"目前仅支持试点业务！\");$(\"#img\").attr(\"src\",\"webpage/call/takeNo/images/readcard1.png\");",3000);
					/* $("#cardInfo").attr("style","display: none;");
					$("#parentDepart").attr("style","");
					$("#childDepart").attr("style","display: none;");
					$("#chooseTitle").html("请选择分类，排队取号！"); */
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"warning",
						time:3,
						ok: false,
						top: "70%"
					});
					setTimeout("$(\"#cardInfo\").attr(\"style\",\"display: none;\");$(\"#parentDepart\").attr(\"style\",\"\");$(\"#childDepart\").attr(\"style\",\"display: none;\");$(\"#chooseTitle\").html(\"目前仅支持试点业务！\");$(\"#img\").attr(\"src\",\"webpage/call/takeNo/images/readcard1.png\");",3000);
				}
			}
		});
    }
	</script>
	<SCRIPT   LANGUAGE="javascript"   FOR="PrintIDCard1"   EVENT="OnIdCard(BeginTime,  EndTime,  Dep,  IDNum,  Addr,  Name,  Sex,  Folk, PicPath)">
		//alert(Name);
		if(departIds=="")return ;
		if(IDNum==""||IDNum==null){
			parent.art.dialog({
				content: "未能有效识别您的身份证，请重试！",
				icon:"warning",
				time:3,
				ok: false,
				top: "70%"
			});
			setTimeout("$(\"#cardInfo\").attr(\"style\",\"display: none;\");$(\"#parentDepart\").attr(\"style\",\"\");$(\"#childDepart\").attr(\"style\",\"display: none;\");$(\"#chooseTitle\").html(\"目前仅支持试点业务！\");$(\"#img\").attr(\"src\",\"webpage/call/takeNo/images/readcard1.png\");",3000);
		}
		else{
			$.post("callController.do?takeNo",{
	    		departId:departIds,
	    		lineName:Name,
	    		lineCardNo:IDNum,
	    		LINE_ZJLX:'SF'
			}, function(responseText, status, xhr) {
				if(responseText&&responseText!="websessiontimeout"){
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
						var child = eval("("+resultJson.jsonString+")");
						var lineNo = child.lineNo;
						var waitNum = child.waitNum;
						var winNo = child.winNo;
						$("#img").attr("src","webpage/call/takeNo/images/printing.png");
						//设置打印
						var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
						LODOP.PRINT_INIT("打印取号单");
						LODOP.SET_PRINT_PAGESIZE(1,800,830,''); 
						//LODOP.ADD_PRINT_RECT(0,0,320,500,0,1);//大小
						LODOP.SET_PRINT_STYLE("FontSize",11);//基本打印风格
						LODOP.ADD_PRINT_TEXT(20,25,240,25,"平潭综合实验区");
						LODOP.SET_PRINT_STYLEA(1,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(1,"FontSize",24);
						LODOP.ADD_PRINT_TEXT(50,35,240,25,"行政服务中心");
						LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
						LODOP.SET_PRINT_STYLEA(2,"FontSize",24);
						LODOP.ADD_PRINT_TEXT(120,35,240,25,"排队号："+lineNo);
						LODOP.SET_PRINT_STYLEA(3,"FontName","宋体");
						LODOP.SET_PRINT_STYLEA(3,"FontSize",20);
						LODOP.ADD_PRINT_TEXT(180,48,240,25,"当前等候人数："+waitNum+"人");
						LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
						LODOP.SET_PRINT_STYLEA(4,"FontSize",14);
						LODOP.ADD_PRINT_TEXT(210,60,240,25,"（请留意"+winNo+"号窗口）");
						/* LODOP.ADD_PRINT_TEXT(300,5,240,25,"温馨提示：");
						LODOP.ADD_PRINT_TEXT(330,5,285,25,"1、本单须在当天交办理窗口收执，超过时间请重新取号；");
						LODOP.ADD_PRINT_TEXT(375,5,285,25,"2、叫号三次未到窗口办理，本单作废；");
						LODOP.ADD_PRINT_TEXT(405,5,285,25,"3、请您留意窗口叫号，以免影响您的办事。"); */
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
				        var time = year+month+date+" "+hour+":"+minu+":"+sec;
						LODOP.ADD_PRINT_TEXT(280,7,280,25,"--------"+time+"--------");
						//LODOP.PREVIEW();
						LODOP.PRINT();
						$("#img").attr("src","webpage/call/takeNo/images/printover.png");
						//打印结束
						departIds = "";
	
						setTimeout("$(\"#cardInfo\").attr(\"style\",\"display: none;\");$(\"#parentDepart\").attr(\"style\",\"\");$(\"#childDepart\").attr(\"style\",\"display: none;\");$(\"#chooseTitle\").html(\"目前仅支持试点业务！\");$(\"#img\").attr(\"src\",\"webpage/call/takeNo/images/readcard1.png\");",3000);
						/* $("#cardInfo").attr("style","display: none;");
						$("#parentDepart").attr("style","");
						$("#childDepart").attr("style","display: none;");
						$("#chooseTitle").html("请选择分类，排队取号！"); */
					}else{
						departIds = "";
						parent.art.dialog({
							content: resultJson.msg,
							icon:"warning",
							time:3,
							ok: false,
							top: "70%"
						});
						setTimeout("$(\"#cardInfo\").attr(\"style\",\"display: none;\");$(\"#parentDepart\").attr(\"style\",\"\");$(\"#childDepart\").attr(\"style\",\"display: none;\");$(\"#chooseTitle\").html(\"目前仅支持试点业务！\");$(\"#img\").attr(\"src\",\"webpage/call/takeNo/images/readcard1.png\");",3000);
					}
				}
			});
		}
	</SCRIPT>	
  </head>
  
  <body ondragstart="return false" oncontextmenu="return false" onselectstart="return false">
	<div class="container">
    	<div class="ind_one">
        	<div class="indTime">
            	<p class="fsize24" id="day"></p>
                <p class="fsize100" id="clock"></p>
            </div>
        </div>
        <div class="ind_two"><img src="webpage/call/takeNo/images/ind_Img1.png"/></div>
        <div class="ind_three">
        	<div id="slideBox" class="slideBox">
                <div class="bd">
                    <ul>
                        <li><a href="javascript:void(0);"><img src="webpage/call/takeNo/images/img.png" /></a></li>
                        <li><a href="javascript:void(0);"><img src="webpage/call/takeNo/images/img1.png" /></a></li>
                    </ul>
                </div>
            </div>
            <script type="text/javascript">
			jQuery(".slideBox").slide({mainCell:".bd ul",effect:"leftLoop",autoPlay:true,delayTime:500});
			</script>
        </div>
        <div class="ind_four">
        	<div class="Title" id="chooseTitle">目前仅支持试点业务！</div>
        	<div class="typeLi" id="parentDepart">
            	<ul>
	            	<c:forEach items="${departList}" var="departInfo">
						<li ><a href="javascript:void(0);" <c:if test="${departInfo.IS_TAKE=='1'}">onclick="toChildDepart('${departInfo.DEPART_ID}')"</c:if> ><p>${departInfo.DEPART_NAME}</p></a></li>
						<%-- <li><a href="#"><p>${departInfo.DEPART_NAME}</p><span>（15位等候）</span></a></li> --%>
					</c:forEach>
                </ul>
            </div>
            <div class="typeLi1" id="childDepart" style="display: none;">
            </div>
            <div class="typeLi2" id="cardInfo" style="display: none;">
            	<ul>
            		<!-- <li><a href="javascript:void(0);" onclick="takeNo();">请将身份证放置在读卡区</a></li> -->
            		<li><img id="img" width="100%" height="100%" src="webpage/call/takeNo/images/readcard1.png" ></li>
            	</ul>
            </div>
        </div>
        <div class="ind_fix"><img src="webpage/call/takeNo/images/ind_Img4.png" /></div>
        <div class="ind_six">
        	<p>平潭综合实验区行政服务中心</p>
            <p>地址：平潭综合实验区金井湾大道 邮编：350400 电话：0591-23120222</p>
            <p>网站：xzfwzx.pingtan.gov.cn:8888</p>
        </div>
    </div>	
</body>
<OBJECT ID="PrintIDCard1"  WIDTH="0"  HEIGHT="0"  CLASSID="CLSID:A7DE699C-E219-4804-971C-309AB90F06CC">
</OBJECT>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>
</html>
