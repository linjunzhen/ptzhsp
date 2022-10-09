<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.AppUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>${param7}</title>
    <link rel="stylesheet" href="<%=basePath%>/webpage/declare/css/mui.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/webpage/declare/css/eui.css"/>
    <script type="text/javascript" src="<%=basePath%>/webpage/declare/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/declare/js/mui.min.js"></script>
    <script src="js/mobileUtil.js"></script>
</head>
<script>
    function wxBookDo(dateTime,beginTime,endTime,allowBespeakNumber,businessCode,departId,isOverTime,bespeakNumber) {
        if (isOverTime == 0) {
            mui.alert("不可预约该时段");
            return;
        }

        if(parseInt(bespeakNumber)>=parseInt(allowBespeakNumber)){
            mui.alert("该时段已预满");
            return;
        }
        var message = "请确定预约时间：<br/>" + dateTime + " " + beginTime + "-" + endTime
        +"<br/><a>温馨提示：预约取号需实名，同一个人在一窗口一天只能预约一个号，预约成功后请提前十分钟至行政服务中心一楼咨询台出示身份证进行现场号取号  。</a>";
        var btnArray = ['否', '是'];
        mui.confirm(message,"",btnArray,function (e) {
            if (e.index == 1) {
                $.post("<%=path%>/itemBespeakController/doPortalBespeakApply.do",
                    {
                        "userName":'${loginMember.YHMC}',
                        "mobile":'${loginMember.SJHM}',
                        "cardNo":'${loginMember.ZJHM}',
                        "STATUS":"1",
                        "DATE_TIME":dateTime,
                        "BEGIN_TIME":beginTime,
                        "END_TIME":endTime,
                        "allowBespeakNumber":allowBespeakNumber,
                        "BUSINESS_CODE":businessCode,
                        "DEPART_ID":departId
                    },
                    function (data) {
                        if (data){
                            var json = JSON.parse(data);
                            mui.alert(json.msg,function () {
                                location.href = "<%=path%>/busInteractController.do?wxBookDepartListView&openid=<%=request.getSession().getAttribute("openid")%>&trustticket=<%=request.getSession().getAttribute("trustticket")%>";
                            });
                        }
                    }
                )
            }
        })
    }
</script>
<body class="eui-bodyBg">
<!--页面主体-->
<div class="mui-content">
    <ul class="mui-table-view">
        <c:forEach items="${wxBookTimeList}" var="dateList" varStatus="i">
            <li class="mui-table-view-cell mui-collapse">
                <a class="mui-navigate-right" href="#">${dateList.W_DATE}</a>
                <c:forEach items="${dateList.bespeaklist}" var="bespeaklist">
                    <div class="mui-collapse-content">
                        <p>${bespeaklist.BEGIN_TIME}-${bespeaklist.END_TIME}
                            <span onclick="wxBookDo('${dateList.W_DATE}','${bespeaklist.BEGIN_TIME}',
                                    '${bespeaklist.END_TIME}','${bespeaklist.ALLOW_BESPEAK_NUMBER}',
                                    '${businessCode}','${departId}','${bespeaklist.isOverTime}','${bespeaklist.bespeakNumber}')" style="color:cornflowerblue;margin-left: 20px;">预约</span></p>
                    </div>
                </c:forEach>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>