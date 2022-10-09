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
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <meta name="description" content="">
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
<c:if test="${itemMap.roomNo!='A'&&itemMap.roomNo!='B'}">
    <div align="center"><span style="color:#F00;font-size:15px;font-weight:bold;">${itemMap.businessName} 当前排队人数：</span><span id="demo" style="color:#F00;font-size:15px;font-weight:bold;"></span></div>
</c:if>
<c:if test="${itemMap.roomNo=='A'||itemMap.roomNo=='B'}">
    <div align="center"><span style="color:#F00;font-size:15px;font-weight:bold;">${itemMap.roomNo}厅排队人数：</span><span id="demo" style="color:#F00;font-size:15px;font-weight:bold;"></span></div>
</c:if>
<script type="text/javascript">
    /*************************************************/
    /*一企一证取号机现场取号 */
    /*************************************************/
    var waiting = 0;
    $(function(){
        writeWaitNum();
    })
    function writeWaitNum(){
        var roomNo="${itemMap.roomNo}";
        if(roomNo=='A'||roomNo=='B'){
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
        window.parent.document.getElementById('takeFrame').src="callYqyzController/toYqyzZzTypeMacW.do?roomNo=${itemMap.roomNo}&ifMaterPrint=${itemMap.ifMaterPrint}&departId=${itemMap.departId}&cleanItem=1";
    }
    function checkTime(i){
        if (i<10){
            i="0" + i;
        }
        return i;
    }
    window.onload=function(){
        ReaddataCard();
        /*====================二维码部分=================*/
        OnStartScan();
        /*====================二维码部分=================*/
        window.setTimeout("backToTop()", 30 * 1000);
    }
    function window_onUnload(){
        PrintIDCard1.EndReadIdCard();
        PrintIDCard1.StartReadIdCard();
        PrintIDCard1.OnCloseScan();
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
            $("#img").attr("src","webpage/callYctb/takeNo/images/printover.png");
            //打印结束
            departId = "";

            window.setTimeout("backToTop()", 3 * 1000);
        }
    }
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
    var zzType = '${zzType}';
    var name = PrintIDCard1.GetName();
    var myname=name+"";
    var strname=myname.substr(0,1)+"*"+myname.substr(2,1);
    var cardNo = PrintIDCard1.GetIDNum();
    //有效期限截止日期
    var ActivityTo = PrintIDCard1.GetEndTime();
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
            LINE_ZJLX:'SF',
            zzType:zzType,
            address:Addr
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
                    $("#img").attr("src","webpage/callYctb/takeNo/images/printing.png");
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
                    if(departId=='2c90b38a571eb7e40157265c46261c7a'){
                        Print1.SetFontName("宋体");
                        Print1.SetFontSize(14);
                        Print1.PrintText(strname);
                        Print1.SetFontSize(8);
                        Print1.PrintText("   温馨提示：排队请遵守预约优先原则；");
                        Print1.PrintText("不动产登记业务取号必须用权利人身份证，");
                        Print1.PrintText("已办理委托的，须提供受托人身份证，");
                        Print1.PrintText("取号单名字须与权利人或受托人一致，");
                        Print1.PrintText("否则取号无效");
                    }
                    if(time>limiTtime){
                        Print1.SetFontSize(14);
                        Print1.PrintText("  （对外办公时间即将结束，");
                        Print1.PrintText("你所取的号可能无法及时办理。");
                        Print1.PrintText("感谢你对中心工作的支持。）");
                    }
                    Print1.SetFontSize(14);
                    if(roomNo=='A'||roomNo=='B'){
                        Print1.PrintText("     "+roomNo+"厅等候人数："+waitNum+"人");
                        Print1.PrintText("     "+businessName+"等待人数"+businessWaitNum+"人");
                    }else{
                        Print1.PrintText("     当前等候人数："+waitNum+"人");
                    }
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

<%--二维码--%>
<script LANGUAGE="JavaScript" for="PrintIDCard1" event="OnCardID(CardID)">
    if(CardID.substring(0,1) == "]") {
        CardID = CardID.substring(3);
    }
    /*====================二维码部分=================*/
    if (CardID != null&&CardID.length > 0) {
        $.ajax({
            url:"callYctbController/yctbGetIdentityByQrcodeforHlw.do",
            type:"post",
            data:{qrcode:CardID},
            success:function (data) {
                if (data != null) {
                    var identity = JSON.parse(JSON.parse(JSON.parse(data)))
                    if(identity.msg == "success") {
                        var cardNo = identity.number
                        var name = identity.name
                        var myname=name+"";
                        var strname=myname.substr(0,1)+"*"+myname.substr(2,1);
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
                        var zzType = '${zzType}';
                        if(cardNo==""||cardNo==null){
                            parent.art.dialog({
                                content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">未能有效识别您的身份，请重试！</font>",
                                icon:"warning",
                                time:3,
                                width:"300px",
                                height:"150px",
                                ok: false
                            });
                        }else{
                            $.post("callYqyzController/yqyzbGenerateNo.do",{
                                lineName:name,
                                lineCardNo:cardNo,
                                LINE_ZJLX:'SF',
                                zzType:zzType
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
                                        Print1.PrintText(" ");
                                        Print1.PrintText(" ");
                                        if(departId=='2c90b38a571eb7e40157265c46261c7a'){
                                            Print1.SetFontName("宋体");
                                            Print1.SetFontSize(14);
                                            Print1.PrintText(strname);
                                            Print1.SetFontSize(8);
                                            Print1.PrintText("   温馨提示：排队请遵守预约优先原则；");
                                            Print1.PrintText("不动产登记业务取号必须用权利人身份证，");
                                            Print1.PrintText("已办理委托的，须提供受托人身份证，");
                                            Print1.PrintText("取号单名字须与权利人或受托人一致，");
                                            Print1.PrintText("否则取号无效");
                                        }
                                        if(time>limiTtime){
                                            Print1.SetFontSize(14);
                                            Print1.PrintText("  （对外办公时间即将结束，");
                                            Print1.PrintText("你所取的号可能无法及时办理。");
                                            Print1.PrintText("感谢你对中心工作的支持。）");
                                        }
                                        Print1.SetFontSize(14);
                                        if(roomNo=='A'||roomNo=='B'){
                                            Print1.PrintText("     "+roomNo+"厅等候人数："+waitNum+"人");
                                            Print1.PrintText("     "+businessName+"等待人数"+businessWaitNum+"人");
                                        }else{
                                            Print1.PrintText("     当前等候人数："+waitNum+"人");
                                        }
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
                                        PrintIDCard1.OnCloseScan();
                                        setTimeout("backToTop()", 3 * 1000);
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


    /*====================二维码部分=================*/
</script>

</html>
