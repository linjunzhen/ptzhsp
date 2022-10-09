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
    <script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
    <script language="javascript" src="webpage/callYctb/takeNo/js/LodopFuncs.js"></script>
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/style.css">
</head>
<body class="bg-none" onbeforeunload="window_onUnload()" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/callYctb/takeNo/images/back.png">请将身份证放入识别区！</a></div>

<div class="readcard"><img id="img" src="webpage/callYctb/takeNo/images/readcard.png" ></div>
<c:if test="${itemMap.roomNo!='D'}">
    <div align="center"><span style="color:#F00;font-size:15px;font-weight:bold;">${itemMap.businessName} 当前排队人数：</span><span id="demo" style="color:#F00;font-size:15px;font-weight:bold;"></span></div>
</c:if>
<c:if test="${itemMap.roomNo=='D'}">
    <div align="center"><span style="color:#F00;font-size:15px;font-weight:bold;">${itemMap.roomNo}厅排队人数：</span><span id="demo" style="color:#F00;font-size:15px;font-weight:bold;"></span></div>
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
        var roomNo="${itemMap.roomNo}";
        if(roomNo=='D'){
            $.ajax({
                url:"newCallController/waitingNumOfRoomNum.do",
                data:{roomNo:"${itemMap.roomNo}"},
                dataType:"json",
                type:"post",
                success:function(data){
                    waiting = data.total;
                    document.getElementById("demo").innerHTML=waiting;
                },
                error:function(data){}
            })
        }else{
            $.ajax({
                url:"newCallController/waitingGrid.do",
                data:{businessCode:"${itemMap.businessCode}"},
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
        window.parent.document.getElementById('takeFrame').src="callController.do?toYqyzTypeChoose&roomNo=${itemMap.roomNo}&ifMaterPrint=${itemMap.ifMaterPrint}&departId=${itemMap.departId}";
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
            $("#img").attr("src","webpage/callYctb/takeNo/images/printover.png");
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
    var departId = '${itemMap.departId}';
    var roomNo = '${itemMap.roomNo}';
    var businessName = '${itemMap.businessName}';
    var materTypeId = '${itemMap.materTypeId}';
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
        $.post("callYqyzController/yqyzGenerateNo.do",{
            lineName:name,
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
                    $("#img").attr("src","webpage/callYctb/takeNo/images/printing.png");
                    //设置打印
                    LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
                    LODOP.PRINT_INIT("打印取号单");
                    LODOP.SET_PRINT_PAGESIZE(1,800,840,'');
                    LODOP.SET_PRINT_STYLE("FontSize",10);//基本打印风格

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
                    if(time>limiTtime){
                        LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
                        LODOP.SET_PRINT_STYLEA(5,"FontSize",14);
                        LODOP.ADD_PRINT_TEXT(140,15,240,25,"（对外办公时间即将结束，");
                        LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
                        LODOP.SET_PRINT_STYLEA(5,"FontSize",14);
                        LODOP.ADD_PRINT_TEXT(160,15,240,25,"你所取的号可能无法及时办理。");
                        LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
                        LODOP.SET_PRINT_STYLEA(5,"FontSize",14);
                        LODOP.ADD_PRINT_TEXT(180,15,240,25,"感谢你对中心工作的支持。）");
                    }
                    LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
                    LODOP.SET_PRINT_STYLEA(4,"FontSize",20);
                    if(roomNo=='A'||roomNo=='B'){
                        LODOP.ADD_PRINT_TEXT(220,48,240,25,roomNo+"厅等候人数："+waitNum+"人");
                    }else{
                        LODOP.ADD_PRINT_TEXT(220,48,240,25,"当前等候人数："+waitNum+"人");
                    }
                    LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
                    LODOP.SET_PRINT_STYLEA(5,"FontSize",14);
                    if(winNo=="115"){
                        LODOP.ADD_PRINT_TEXT(250,48,240,25,"（请留意"+roomNo+"区台胞接待室）");
                    }else{
                        LODOP.ADD_PRINT_TEXT(250,48,240,25,"（请留意"+roomNo+"区"+winNo+"号窗口）");
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