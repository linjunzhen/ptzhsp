<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="eve" uri="/evetag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-cn">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit">
<title>身份证打印</title>
<link rel="stylesheet" type="text/css" href="css/eui.css">

<!-- 将不需要打印的部分，标记为class="noprint" -->
<style type="text/css" media=print>
.noprint {
    display: none
}
</style>

<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery3.min.js"></script>
<script type="text/javascript">
    /**
    *读卡
    */
    function StartRead() {
        var port = TrCtrl.TR_SdtGetSAMStatus_ocx();

        if (port == -1) {
            alert('设备连接失败');
            return;
        }

        var cardInfoStr = TrCtrl.TR_GetIdInfoExport_ocx();
        var cardInfos = cardInfoStr.split('||');
        if (cardInfos == '') {
            alert('未能有效识别身份证，请重新读卡！');
            return;
        }

        var born = new Date(cardInfos[4].replace('.', '/').replace('.', '/').replace('.', ''));
        var bornYear = born.getFullYear();
        var bornMonth = born.getMonth() + 1;
        var bornDay = born.getDate();

        $('#idcard_name').html(cardInfos[1]);
        $('#idcard_sex').html(cardInfos[2]);
        $('#idcard_nation').html(cardInfos[3]);
        $('#idcard_year').html(bornYear);
        $('#idcard_month').html(bornMonth);
        $('#idcard_day').html(bornDay);
        $('#idcard_address').html(cardInfos[6]);
        $('#idcard_cardno').html(cardInfos[5]);
        $('#idcard_police').html(cardInfos[7]);
        $('#idcard_activity').html(cardInfos[8] + '-' + cardInfos[9]);
        $('#idcard_photo').attr('src', cardInfos[11]);
    //$('#idcard_photo').attr('src', 'C:\\Users\\ASUS\\Desktop\\test_1.png');
    //$('#idcard_photo').attr('src', cardInfos[11].replace('.bmp', '.png'));
    //$('#idcard_photo').attr('src', 'data:image/png;base64,' + cardInfos[12]);
    }

    /**
        *身份证_打印
        */
    function IdCardPrint() {
        window.print();
    }

    /**
    *身份证信息保存
    */
    function IdCardSave() {
        var cardInfoStr = TrCtrl.TR_GetIdInfoExport_ocx();
        var cardInfos = cardInfoStr.split('||');
        if (cardInfos == '') {
            alert('请先读卡');
            return;
        }

        var activity = cardInfos[8] + '-' + cardInfos[9];
        var imgStr = cardInfos[12].replace('data:image/png;base64,', '');

        $.ajax({
            type : 'post',
            url : '<%=basePath%>idCardController.do?saveOrUpdate',
            dataType : 'json',
            data : {
                CARDNO : cardInfos[5],
                NAME : cardInfos[1],
                SEX : cardInfos[2],
                NATION : cardInfos[3],
                BORN : cardInfos[4],
                ADDRESS : cardInfos[6],
                POLICE : cardInfos[7],
                ACTIVITY : activity,
                PHOTO : imgStr
            },
            success : function(responseText) {
                if (responseText.success) {
                    var imgPath = '<%=path%>/attachFiles/IdCard/' + cardInfos[5] + '.png';
                    $('#idcard_photo').attr('src', imgPath);
                    alert(responseText.msg);
                }
            }
        });
    }
</script>

<body>
    <object classid="clsid:6EDE7AEC-5C00-4D60-B4FC-774F57A2A480" id=TrCtrl style="display:none"> </object>

    <div id="print_toolbar" class="noprint" style="z-index:999;text-align:center;margin:30px;position:absolute;top:0;left:0;right:0;bottom:0;">
        <a href="javascript:StartRead();" style="background:#3a81d0;border:none;padding:10px 20px;height:30px;cursor:pointer;margin:10px;color:#fff;border-radius:3px;">读 卡</a> <a
            href="javascript:IdCardSave();" style="background:#3a81d0;border:none;padding:10px 20px;height:30px;cursor:pointer;margin:10px;color:#fff;border-radius:3px;">保 存</a> <a
            href="javascript:IdCardPrint();" style="background:#3a81d0;border:none;padding:10px 20px;height:30px;cursor:pointer;margin:10px;color:#fff;border-radius:3px;">打 印</a> <a
            href="javascript:window.opener=null;window.open('','_self');window.close();"
            style="background:#3a81d0;border:none;padding:10px 20px;height:30px;cursor:pointer;margin:10px;color:#fff;border-radius:3px;">关 闭</a>
    </div>

    <div id="idcard_print" class="eui-print">
        <center style="font-size:35px;font-weight:bold;">平潭综合实验区行政服务中心</center>
        <div class="eui-idcard">
            <div class="bg">
                <img src="images/idcard1.png" />
            </div>
            <div class="photo">
                <img id='idcard_photo' src="images/head.png" />
            </div>
            <div id="idcard_name" class="name"></div>
            <div id="idcard_sex" class="sex"></div>
            <div id="idcard_nation" class="nation"></div>
            <div id="idcard_year" class="year"></div>
            <div id="idcard_month" class="month"></div>
            <div id="idcard_day" class="day"></div>
            <div id="idcard_address" class="address"></div>
            <div id="idcard_cardno" class="number"></div>
        </div>

        <div class="eui-idcard">
            <div class="bg">
                <img src="images/idcard2.png" />
            </div>
            <div id="idcard_police" class="office"></div>
            <div id="idcard_activity" class="indate"></div>
        </div>

        <div class="eui-footer">
            <div class="eui-sign">签名：</div>
            <div class="eui-date">
                <span>时间：</span> <span>年</span> <span>月</span> <span>日</span>
            </div>
        </div>
    </div>
</body>
</html>
