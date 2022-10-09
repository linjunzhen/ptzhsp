<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
    String itemName = request.getParameter("ITEM_NAME");
    request.setAttribute("itemName", itemName);
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
    <title>不动产权证预约</title>
    <link rel="stylesheet" href="<%=path%>/webpage/declare/css/mui.min.css"/>
    <link rel="stylesheet" href="<%=path%>/webpage/declare/css/eui.css"/>
    <script type="text/javascript" src="<%=path%>/webpage/declare/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/layer-1.8.5/layer.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/declare/js/mui.min.js"></script>
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/zepto.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <!-- 必引cordova.js，实现与原生的交互 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/cordova.js"></script>
    <!-- 含闽政通获取地市信息，网络状态等方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/bingotouch.js"></script>
    <!-- 含闽政通获取用户信息，分享，获取版本号，人脸识别方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jmtplugins.js"></script>
    <!-- js加解密 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jsencrypt.js"></script>
</head>

<style>
    .title{
        display: flex;
        justify-content: center;
        height: 40px;
        align-items: center;
        font-weight: 700;
        font-size: 20px;
        padding-top: 10px;
        border-bottom: 1px solid #ccc;
        padding-bottom: 10px;
    }
    .mark{
        border-top: 1px solid #ccc;
        padding-bottom: 5px;
    }
    .mark>div{
        margin: 8px 10px 0px 10px;
        font-size: 15px;
    }
    label{
        font-size: 15px;
    }
    input{
        font-size: 15px;
    }
    .radio{
        display: flex;
        align-items: center;
        font-size: 15px;
    }
</style>

<script>
    function toBack() {
        window.location.href = "<%=path%>/bdcAppointController/bdclzTab.do";
    }
</script>

<body class="eui-bodyBg" style="background: #fff;margin:0px;padding: 0px;">
<!--页面主体-->
<div class="mui-content">
    <div class="eui-wrap">
        <form class="mui-input-group" id="form">
            <div class="title"><span style="position: absolute;left: 3px;padding: 5px 10px 5px 5px;" onclick="toBack()">《</span><span>预约领取不动产权证</span></div>
            <div class="mui-input-row">
                <label><span style="color: red;">*</span>产权人姓名</label>
                <input type="text" class="mui-input-clear home" name="CQR_NAME" placeholder="">
            </div>
            <div class="mui-input-row win">
                <label><span style="color: red;">*</span>产权人身份证</label>
                <input type="text" class="mui-input-clear home" name="CQR_CARDNO" placeholder="">
            </div>
            <div class="mui-input-row">
                <label><span style="color: red;">*</span>联系号码</label>
                <input type="text" class="mui-input-clear home" name="CQR_PHONE" placeholder="">
            </div>
            <div class="mui-input-row">
                <label><span style="padding: 3px;"></span>业务申报号</label>
                <input type="text" class="mui-input-clear" name="EXE_ID" placeholder="申报号在受理单上">
            </div>
            <div class="mui-input-row radio">
                <span style="margin-right: 40px;margin-right: 50px;margin-left: 16px;"><span style="color: red;">*</span>领证方式</span>
                <input type="radio" name="LZ_TYPE" value="1" onclick="showHomeView()">送证上门
                <div style="margin-left: 20px;"></div>
                <input type="radio" name="LZ_TYPE" value="0" onclick="showWinView();">窗口领证
            </div>
            <div id="showHomeInfo" style="display: none;">
                <div class="mui-input-row">
                    <label><span style="color: red;">*</span>收件人姓名</label>
                    <input type="text" class="mui-input-clear home" name="SJR_NAME" placeholder="">
                </div>
                <div class="mui-input-row">
                    <label><span style="color: red;">*</span>收件人身份证</label>
                    <input type="text" class="mui-input-clear home" name="SJR_CARDNO" placeholder="">
                </div>
                <div class="mui-input-row">
                    <label><span style="color: red;">*</span>收件地址</label>
                    <input type="text" class="mui-input-clear home" name="SJR_ADDRESS" placeholder="精确到门牌号">
                </div>
                <div class="mui-input-row">
                    <label>备注</label>
                    <input type="text" class="mui-input-clear home" name="REMARK" placeholder="">
                </div>
            </div>
            <div class="mui-button-row">
                <button type="button" style="padding: 6px 22px;border: 1px solid #35A8DB;background-color: #35A8DB;" class="mui-btn mui-btn-primary" onclick="appoint()">预约</button>
            </div>
            <div id="showWinMark" style="display: none;" class="mark">
                <div>说明</div>
                <div>1.不动产登记费（住宅80元，非住宅550元）</div>
                <div>2.不动产工本费（10元）</div>
            </div>
            <div id="showHomeMark" style="display: none;" class="mark">
                <div>说明</div>
                <div>1.不动产登记费（住宅80元，非住宅550元）</div>
                <div>2.不动产工本费（10元）</div>
                <div>3.快递费</div>
                <div>4.相关费用（快递费+登记费+工本费）将由快递人员送证上门时收取</div>
            </div>
        </form>
    </div>
</div>
<script>
    function showHomeView() {
        $("#showHomeMark").show();
        $("#showWinMark").hide();
        $("#showHomeInfo").show();
    }

    function showWinView() {
        $("#showWinMark").show();
        $("#showHomeMark").hide();
        $("#showHomeInfo").hide();
    }
    
    function appoint() {
        var LZ_TYPE = $("input[name='LZ_TYPE']:checked").val();
        var CQR_NAME = $("input[name='CQR_NAME']").val();
        var CQR_CARDNO = $("input[name='CQR_CARDNO']").val();
        var CQR_PHONE = $("input[name='CQR_PHONE']").val();
        if (LZ_TYPE) {
            var check = true;
            if (LZ_TYPE == '1') {
                mui(".home").each(function () {
                    if (!this.value || this.value.trim() == "") {
                        if (this.name != 'REMARK') {
                            var label = this.previousElementSibling;
                            mui.alert(label.innerText + "不允许为空");
                            check = false;
                            return false;
                        }
                    }
                });
            } else {
                if (!CQR_NAME) {
                    mui.alert("*产权人姓名" + "不允许为空");
                    check = false;
                    return false;
                }
                if (!CQR_CARDNO) {
                    mui.alert("*产权人身份证" + "不允许为空");
                    check = false;
                    return false;
                }
                if (!CQR_PHONE) {
                    mui.alert("*联系电话" + "不允许为空");
                    check = false;
                    return false;
                }
            }

            if (check) {
                $.post("<%=basePath%>identifyMsgByDsjController/personalIdentifyQueryBdc.do", {lineCardNo: CQR_CARDNO}, function (data) {
                    if (data) {
                        var json = JSON.parse(data);
                        if (json.xm != CQR_NAME) {
                            mui.alert("身份证号码与姓名匹配不上");
                        } else {
                            var formData = FlowUtil.getFormEleData("form");
                            var params = $.param(formData);
                            AppUtil.ajaxNoProgress({
                                url: "appointBdcqz.do",
                                params: params,
                                callback: function (data) {
                                    if (data) {
                                        if (data.success) {
                                            mui.alert("预约成功");
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        mui.alert("身份证号码有误");
                    }
                });
            }
        } else {
            mui.alert("请选择领证方式");
        }
    }
</script>
</body>
</html>