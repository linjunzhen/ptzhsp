<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<style>
    .btn-x {
        margin-bottom: 0px;
        padding: 5px 0px;
    }
    .flexBox-center{
        display: flex;
        align-items: center;
    }
    .margin-box{
        margin : 10px 10px 10px 10px;
    }
    .btn-y{
        padding: 5px 5px;
        border-radius: 5px;
    }
    .btn-light{
        background-color: #f78107;color: #fff;
    }
    .btn-dark{
        background-color: #ccc;color: #fff;
    }
</style>
<script>

    $(function () {

        $("#doAppointContent").show();
        $("#myAppointContent").hide();

        $("#doAppoint").click(function () {
            $("#doAppointContent").show();
            $("#myAppointContent").hide();
        });

        $("#myAppoint").click(function () {
            $("#doAppointContent").hide();
            $("#myAppointContent").show();
        })
    })

    function cancelAppoint(val,recordId,thiz) {
        if (val == 1) {
            $.post("<%=path%>/busInteractController/wxBookCancelAppoint.do",{recordId:recordId},function (data) {
                if (data) {
                    var json = JSON.parse(data);
                    if (json.success) {
                        mui.alert(json.msg);
                        $(thiz).removeAttr("onclick").text("预约").removeClass("btn-light").addClass("btn-dark");
                    }
                }
            })
        }
    }


    function toBusinessChoose(departId) {
        location.href = "<%=path%>/busInteractController.do?wxBookBusinessChooseView&departId=" + departId;
    }
</script>
<body class="eui-bodyBg">
    <div style="display: flex;justify-content: center;align-items: center;">
        <button type="button" class="mui-btn mui-btn-outlined mui-btn-block btn-x" id="doAppoint">办事预约</button>
        <button type="button" class="mui-btn mui-btn-outlined mui-btn-block btn-x" id="myAppoint">我的预约</button>
    </div>
    <!--页面主体-->
    <div class="mui-content" style="margin-top: -15px;display: none;" id="doAppointContent">
        <ul class="mui-table-view">
            <c:forEach items="${list}" var="dataList">
                <li class="mui-table-view-cell" onclick="toBusinessChoose('${dataList.DEPART_ID}');">${dataList.DEPART_NAME}</li>
            </c:forEach>
        </ul>
    </div>
    <!--页面主体-->
    <div class="mui-content" style="margin-top: -15px;display: none;" id="myAppointContent">
        <div style="margin-top: 30px;"></div>
        <c:if test="${appointList == null}">
            <h3>暂无预约记录</h3>
        </c:if>
        <c:if test="${appointList != null}">
            <c:forEach items="${appointList}" var="list">
                <div class="mui-card" style="height: 120px;">
                    <div class="flexBox-center" style="justify-content: space-between;">
                        <span class="margin-box">${list.BUSINESS_NAME}</span>
                        <span class="margin-box">
                            <c:if test="${list.IS_OVERTIME == 1}"><span class="btn-y btn-dark">已过期</span></c:if>
                            <c:if test="${list.IS_OVERTIME == 0}">
                                <c:if test="${list.STATUS == 1}">
                                    <c:if test="${list.IS_TAKE == 1}">
                                        <span class="btn-y btn-dark">已取票</span>
                                    </c:if>
                                    <c:if test="${list.IS_TAKE == 0}">
                                        <span class="btn-y btn-light" onclick="cancelAppoint('${list.STATUS}','${list.RECORD_ID}',this)">取消</span>
                                    </c:if>
                                </c:if>
                                <c:if test="${list.STATUS == 0}"><span class="btn-y btn-dark">预约</span></c:if>
                                <c:if test="${list.STATUS == 2}"><span class="btn-y btn-dark">作废</span></c:if>
                            </c:if>
                        </span>
                    </div>
                    <div class="flexBox-center" style="margin-left: 10px;">
                        <span>预约时间：</span><span>${list.DATE_TIME} ${list.BEGIN_TIME}-${list.END_TIME}</span>
                    </div>
                    <div class="flexBox-center" style="margin-left: 10px;">
                        <span>预约状态：</span>
                        <span>
                            <c:if test="${list.IS_OVERTIME == 1}">已过期</c:if>
                            <c:if test="${list.IS_OVERTIME == 0}">
                                <c:if test="${list.STATUS == 1}">预约</c:if>
                                <c:if test="${list.STATUS == 0}">取消预约</c:if>
                                <c:if test="${list.STATUS == 2}">作废</c:if>
                            </c:if>
                        </span>
                    </div>
                    <div class="flexBox-center" style="margin-left: 10px;">
                        <span>取票状态：</span>
                        <span>
                            <c:if test="${list.IS_TAKE == 1}">已取票</c:if>
                            <c:if test="${list.IS_TAKE == 0}">未取票</c:if>
                        </span>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>

</body>
</html>