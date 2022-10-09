 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.AppUtil" %>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> loginMember = AppUtil.getLoginMember();
    String isLogin = "0";
    if (loginMember != null) {
        isLogin = "1";
        request.setAttribute("loginMember", loginMember);
    }
    request.setAttribute("isLogin",isLogin);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <title>平潭综合实验区不动产登记与交易</title>
    <!-- CSS -->
    <base href="<%=basePath%>">
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/aos.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/style.css">
    <script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/base64-1.0/jquery.base64.js"></script>
    <!-- JS -->
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.min.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.SuperSlide.2.1.2.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/aos.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.selectlist.js"></script>
    <eve:resources loadres="apputil,validationegine,layer,artdialog"></eve:resources>
    <style>
        .valiButton{
            padding: 5px 12px;
            background-color: #62a1cf;
            border-radius: 3px;
            color: #fff;
            cursor: pointer;
        }
        .lookBdcView{
            padding:6px;
            background-color: #62a1cf;
            border-radius: 5px;
            color: #fff;
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">

        $(function () {
            $("#cxyt").attr("style","width: 150px;height: 25px;font-size:13px;");
        })

        $(function () {
            var isLogin = '${isLogin}';
            if (isLogin) {
                if (isLogin == 0) {
                    window.top.location.href="<%=path%>/userInfoController/mztLogin.do?returnUrl=webSiteController/view.do?navTarget=site/bdc/info/bdcdjzlcx_lhr";
                } else {
                    var zjlx = '${loginMember.ZJLX}';
                    var sjhm = '${loginMember.SJHM}';
                    var zjhm = '${loginMember.ZJHM}';
                    var yhmc = '${loginMember.YHMC}';
                    if (zjhm) {
                        if (zjlx) {
                            var layload = layer.load('正在查询中......');
                            $.post("bdcApplyController/bdcdjzlcxlhrNew.do", {zjhm: zjhm, name:yhmc , mobile:sjhm }, function (data) {
                                layer.close(layload);
                                if (data) {
                                    var obj = JSON.parse(data)
                                    if (obj.success) {
                                        $("#verificationInput").show();
                                    } else {
                                        art.dialog({
                                            content : "查询出错，请联系管理员",
                                            icon : "succeed",
                                            ok : true
                                        });
                                    }
                                } else {
                                    art.dialog({
                                        content : "查询出错，请联系管理员",
                                        icon : "succeed",
                                        ok : true
                                    });
                                }
                            });
                        } else {
                            art.dialog({
                                content : "您的账号无证件类型数据",
                                icon : "warning",
                                ok : true
                            });
                        }
                    } else {
                        art.dialog({
                            content : "您的账号无身份证号码数据",
                            icon : "warning",
                            ok : true
                        });
                    }
                }
            } else {
                window.top.location.href="<%=path%>/userInfoController/mztLogin.do?returnUrl=webSiteController/view.do?navTarget=site/bdc/info/bdcdjzlcx_lhr";
            }

        });

        function identification() {
            var ptiiId = $("input[name='PTII']").val();
            $.post("bdcApplyController/identificationForData.do", {ptiiId: ptiiId}, function (data) {
                if (data) {
                    var json = JSON.parse(data);
                    var str = "";
                    for (let i = 0; i < json.length; i++) {
                        var REG_TYPE = json[i].REG_TYPE;
                        var PROPERTY_STATUS = json[i].PROPERTY_STATUS;
                        var OWNER_NAME = json[i].OWNER_NAME;
                        var ADDRESS = json[i].ADDRESS;
                        var ESTATE_PROPERTY_WORD_ID = json[i].ESTATE_PROPERTY_WORD_ID;
                        var REG_DATE = json[i].REG_DATE;
                        var BUILDING_AREA = json[i].BUILDING_AREA;
                        var PROJECT_USAGE = json[i].PROJECT_USAGE;
                        var RIGHT_PROPERTY = json[i].RIGHT_PROPERTY;
                        str += "权证类型： " + REG_TYPE + "<br>";
                        str += "产权状态： " + PROPERTY_STATUS + "<br>";
                        str += "权利人： " + OWNER_NAME + "<br>";
                        str += "不动产坐落： " + ADDRESS + "<br>";
                        str += "证号： " + ESTATE_PROPERTY_WORD_ID + "<br>";
                        str += "登记时间： " + REG_DATE + "<br>";
                        str += "面积： " + BUILDING_AREA + "<br>";
                        str += "用途： " + PROJECT_USAGE + "<br>";
                        str += "权利性质： " + RIGHT_PROPERTY + "<br><br>";
                    }
                    $("#qsrsbhjcxmm").html(str);
                }
            });
        }


        function testingVerificationCode() {
            var verificationCode = $("input[name='verificationCode']").val();
            if (verificationCode) {
                $.post("bdcApplyController/testingVerificationCode.do", {verificationCode: verificationCode}, function (data) {
                    if (data) {
                        var json = JSON.parse(data);
                        if (json.success) {
                            var obj = json.data;
                            if (obj) {
                                var str = "";
                                for (let i = 0; i < obj.length; i++) {
                                    var ID = obj[i].ID;
                                    var ZSLX = obj[i].ZSLX;
                                    var ZT = obj[i].ZT;
                                    var QLR = obj[i].QLR;
                                    var ADDRESS = obj[i].ADDRESS;
                                    var QZH = obj[i].QZH;
                                    var DJSJ = obj[i].DJSJ;
                                    var BUILDAREA = obj[i].BUILDAREA;
                                    var USEFACT = obj[i].USEFACT;
                                    var DYH = obj[i].DYH;
                                    var ZJH = obj[i].ZJH;
                                    var HOUSETYPE = obj[i].HOUSETYPE;
                                    var ROOMNUMBER = obj[i].ROOMNUMBER;
                                    var FLOOR_START = obj[i].FLOOR_START;
                                    var BUILDNUMBER = obj[i].BUILDNUMBER;
                                    var NOTE = obj[i].NOTE;
                                    var YTQX = obj[i].YTQX;
                                    str += "<div style='display: flex;justify-content: space-between;align-items: center'><div>";
                                    str += "权证类型： " + formateDate(ZSLX) + "<br>";
                                    str += "状态： " + formateDate(ZT) + "<br>";
                                    str += "权利人： " + formateDate(QLR) + "<br>";
                                    str += "权利人证件号码： " + formateDate(ZJH) + "<br>";
                                    str += "不动产坐落： " + formateDate(ADDRESS) + "<br>";
                                    str += "证号： " + formateDate(QZH) + "<br>";
                                    str += "登记时间： " + formateDate(DJSJ) + "<br>";
                                    str += "面积： " + formateDate(BUILDAREA) + "<br>";
                                    str += "用途： " + formateDate(USEFACT) + "<br>";
                                    str += "不动产单元号： " + formateDate(DYH) + "<br>";
                                    str += "户号： " + formateDate(ROOMNUMBER) + "<br>";
                                    str += "层号： " + formateDate(FLOOR_START) + "<br>";
                                    str += "幢号： " + formateDate(BUILDNUMBER) + "<br>";
                                    str += "附记： " + formateDate(NOTE) + "<br>";
                                    str += "土地权利起止时间： " + formateDate(YTQX) + "<br>";
                                    str += "房屋性质： " + formateDate(HOUSETYPE) + "<br><br>";
                                    str += "</div><div><span class='lookBdcView' onclick='openLookBdcImgView(\"" + ID + "\")'>查看附图</span></div></div>";
                                }
                                $("#htmlBox1").html(str);
                            } else {
                                var iden = json.idenMap;
                                var str = "兹有"+iden.NAME+"（居民身份证："+iden.ZJHM+"），经我中心不动产登记信息系统核查，"+iden.TODAY+"，在本中心无不动产登记信息记录<br><br>"
                                $("#htmlBox1").html(str);
                            }
                            $("#qsrsbhjcxmm").hide();
                            $("#verificationInput").hide();
                            $("#downLoadBox").show();
                        } else {
                            art.dialog({
                                content: json.msg,
                                icon: "warning",
                                ok: true
                            });
                        }
                    } else {
                        art.dialog({
                            content : "暂无不动产资料信息",
                            icon : "warning",
                            ok : true
                        });
                    }
                });
            } else {
                art.dialog({
                    content : "请输入验证码",
                    icon : "warning",
                    ok : true
                });
            }
        }

        function formateDate(data) {
            if (data) {
                return data;
            } else {
                return "";
            }
        }

        /*
        * 查看附图页面
        * */
        function openLookBdcImgView(id) {
            if (id) {
                $.dialog.open("bdcApplyController.do?lookBdcImgView&id=" + id, {
                    title: "查看附图",
                    width: "1100px",
                    lock: true,
                    resize: false,
                    height: "550px",
                    close: function () {

                    }
                });
            } else {
                art.dialog({
                    content : "系统错误，请联系管理员",
                    icon : "error",
                    ok : true
                })
            }
        }

        function downLoadBdcQueryMater() {
            var cxyt = $("input[name='cxyt']").val();
            if (cxyt && cxyt.length > 0) {
                window.location.href = encodeURI("<%=path%>/bdcApplyController/downLoadBdcQueryFile.do?cxyt=" + cxyt);
            } else {
                art.dialog({
                    content : "请选择查询用途",
                    icon : "warning",
                    ok : true
                })
            }
        }

    </script>
</head>

<body style="background: #f0f0f0;">

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>
<div class="eui-main">
    <div class="current"><span>您现在所在的位置：</span><a href="<%=path%>/webpage/site/bdc/info/index.jsp"><i>不动产</i></a> > 不动产登记资料查询 > 按本人身份查询</div>

    <div class="bsbox clearfix">
        <div class="bsboxT">
            <ul>
                <li class="on" style="background:none"><span>查询结果</span></li>
                <input type="hidden" name="PTII">
            </ul>
        </div>
        <div style="display: flex;justify-content: center;align-items: center;margin: 20px 0px 20px 0px;">
            <div style="color: #3188d2;display: none;" id="verificationInput">
                请输入验证码：<input type="text" class="textinput" name="verificationCode"> <span class="valiButton" style="margin-left: 40px;" onclick="testingVerificationCode();">确 定</span>
            </div>
        </div>
<%--        <div id="msgBox">--%>
<%--            <div style="display: flex;justify-content: center;align-content: center;color: red;margin-top: 10px;"><span>请按如下步骤前往闽政通app进行身份认证</span></div>--%>
<%--        </div>--%>
        <div class="bsboxC">
            <div id="qsrsbhjcxmm" style="text-align: left;font-size: 18px;line-height: 40px;margin-left: 50px;"></div>
            <div id="htmlBox1" style="text-align: left;font-size: 14px;line-height: 40px; margin-left: 50px;">
                <div>
					<div><span>1. 打开闽政通app,并点击打开【平潭通】</span></div>
					<div><span>2. 找到 【不动产查询】专区，选择“按本人查询”</span></div>
					<div><span>3. 点击【我已阅读】，进行不动产资料查询</span></div>
                </div>
                <div style="display: flex;justify-content: space-around;align-items:center;margin-top: 30px;">
                    <img src="<%=path%>/webpage/site/bdc/info/images/2020031201.jpg" style="width: 200px;height: auto;">
                    <img src="<%=path%>/webpage/website/common/images/app/mhjt.png" style="height: 40px;">
                    <img src="<%=path%>/webpage/site/bdc/info/images/2020031202.jpg" style="width: 200px;height: auto;">
                    <img src="<%=path%>/webpage/website/common/images/app/mhjt.png" style="height: 40px;">
                    <img src="<%=path%>/webpage/site/bdc/info/images/2020031203.jpg" style="width: 200px;height: auto;">
                </div>
            </div>
            <div style="text-align: left;font-size: 14px;line-height: 40px;margin-left: 50px;">
                备注：不排除查档申请人其它房产未登记情况。<br/>
                申明：查询人对查询中的涉及的国家秘密、个人隐私和商业秘密负有保密义务，不得泄露给他人，也不得不正当使用
            </div>
            <div id="downLoadBox" style="display: none;">
                <div style="display: flex;justify-content: center;align-items: center">
                    <span>查询用途： </span>
                    <eve:eveselect clazz="tab_text validate[required]" dataParams="bdcQueryYt"
                                   dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择查询用途"
                                   name="cxyt" id="cxyt">
                    </eve:eveselect>
                </div>
                <div style="display: flex;justify-content: center;align-items: center">
                    <span class="valiButton" id="downLoadBdcQuery" style="margin-top: 10px;" onclick="downLoadBdcQueryMater();">下 载</span>
                </div>
            </div>
        </div>
    </div>
</div>


	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>

</body>
</html>