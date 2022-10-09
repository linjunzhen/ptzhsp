<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/e-tags.tld"%> --%>
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
    <title>行政审批承诺书</title>
    <link rel="stylesheet" href="css/mui.min.css"/>
    <link rel="stylesheet" href="css/eui.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/zepto.js"></script>
    <!-- 必引cordova.js，实现与原生的交互 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/cordova.js"></script>
    <!-- 含闽政通获取地市信息，网络状态等方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/bingotouch.js"></script>
    <!-- 含闽政通获取用户信息，分享，获取版本号，人脸识别方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jmtplugins.js"></script>
    <!-- js加解密 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jsencrypt.js"></script>
</head>
<body class="eui-bodyBg">
    <!--页面主体-->
  <div class="mui-content">
    <p>平潭综合实验区行政审批局：</p>
    
    <p>本单位（人）于<span id="nowDate1"></span>向贵局申请办理
      <font style="font-weight: 600;font-style: oblique" color="black">${itemName}</font>
      
      事项审批，并按照规定要求递交了相关申请材料。现就有关事宜承诺如下：</p>
    <p>1、本单位（人）已认真学习了相关法律法规规章和规范性文件，已了解该项审批的有关要求，对有关规定的内容已经知晓和全面理解，承诺自身能够满足办理该事项的条件、标准和技术要求。
    </p>
    <p>
      2、本单位（人）承诺完全按照贵局公布的申请材料要求和标准，提交了全部申请材料。
    </p>
    <p>
      3、本单位（人）承诺所提供的申请材料实质内容均真实、合法、有效。
    </p>
    <p>
      4、本单位（人）承诺所提供的申请材料内容完全一致。
    </p>
    <p>
      5、本单位（人）承诺主动接受有关监管部门的监督和管理。
    </p>
    <p>
      6、本单位（人）承诺对违反上述承诺的行为，愿意承担相应的法律责任。若因违反有关法律法规及承诺，被撤销行政审批决定，愿意进入信用信息公示平台并自行承担因此所造成的经济和法律后果。
    </p>
    <p>
      7、本单位（人）承诺以上陈述真实、有效，是本单位（人）真实意思的表示。

    </p>
    <p style="margin-right: 20px;float: right">
      承诺时间：<span id="nowDate2"></span>
    </p>
    <br/>
    <br/>
    <br/>
    <div>
        <a class="eui-btn-blue" id="onBackBtn">返回</a>
    </div>
  </div>
  <script>
    var nowDate = getNowFormatDate();
    $('#nowDate1').html(nowDate);
    $('#nowDate2').html(nowDate);
    
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
    
    function onBack(){
        history.go(-1);
    }

    app.page.onLoad = function() {
        document.getElementById("onBackBtn").addEventListener('click', function() {
            app.link.getVersion(function(result){
                var isNativeCloseWin = 1;
                if(result['type']==1){//android
                    if(result['version']<=139){
                        isNativeCloseWin = 0;
                    }
                }else if(result['type']==2){//ios
                    if(result['version']<=41){
                        isNativeCloseWin = 0;
                    }
                }

                if(isNativeCloseWin==1){
                    app.back();
                }else{
                    history.go(-1);
                }
            });
        });
    }
  </script>  
</body>
</html>