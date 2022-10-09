<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <base href="<%=basePath%>">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <title>平潭综合实验区不动产登记与交易</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <!-- CSS -->
    <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/aos.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/style.css">
    <!-- JS -->
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.SuperSlide.2.1.2.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/aos.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.selectlist.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script>

        $(function () {
            $("[name='BLLX'][value='1']").attr("checked", true);
            toUrl(1);
            toBllx(1);
        })

        function toUrl(val) {
            if (val == 1) {
                clearForm();
                $("#djjsq").show();
                $("#ftck").show();
                $("#djfjgbf").hide();
                $("#bllx").show();
                $("[name='TDYT']").val('').attr("disabled", false);
                $("[name='TYPE']").val('1');
                $("[name='BLLX']").removeAttr("checked");
                $("[name='BLLX'][value='1']").prop("checked", true);
            } else if (val == 2) {
                clearForm();
                $("#djjsq").show();
                $("#ftck").show();
                $("#djfjgbf").hide();
                $("#bllx").hide();
                $("#dwjzf").show();
                $("#zjfwfgxs").hide();
                $("#jcgzxm").hide();
                $("[name='TDYT']").val(1).attr("disabled", true);
                $("[name='TYPE']").val('2');
            } else if (val == 3) {
                clearForm();
                $("#djfjgbf").show();
                $("#djjsq").hide();
                $("#ftck").hide();
            }
        }

        function clearForm() {
            $("#dwjzf input , #dwjzf select").each(function () {
                $(this).val("");
            });
            $("#zjfwfgxs input , #zjfwfgxs select").each(function () {
                $(this).val("");
            });
            $("#jcgzxm input , #jcgzxm select").each(function () {
                $(this).val("");
            });
            $("[name='RJL1']").val('2.5');
            $("[name='RJL2']").val('4.0');
        }

        function toBllx(val) {
            if (val == 1) {
                clearForm();
                $("#dwjzf").show();
                $("#zjfwfgxs").hide();
                $("#jcgzxm").hide();
                $("#jsqTitle").text('用地出让金（元）：');
            } else if (val == 2) {
                clearForm();
                $("#dwjzf").hide();
                $("#zjfwfgxs").show();
                $("#jcgzxm").hide();
                $("#jsqTitle").text('补缴标准（元）：');
            } else if (val == 3) {
                clearForm();
                $("#dwjzf").hide();
                $("#zjfwfgxs").hide();
                $("#jcgzxm").show();
                $("#jsqTitle").text('补缴标准（元）：');
            }
        }

        function calculation() {
            var bllx = $("[name='BLLX']:checked").val();
            var type = $("[name='TYPE']").val();
            var formId;
            if (type == 1) {
                if (bllx == 1) {
                    formId = 'dwjzf';
                } else if (bllx == 2) {
                    formId = 'zjfwfgxs';
                } else if (bllx == 3) {
                    formId = 'jcgzxm';
                }
            } else if (type == 2) {
                formId = 'dwjzf';
            }
            var formData = FlowUtil.getFormEleData(formId);
            formData['BLLX'] = bllx;
            formData['TYPE'] = type;
            var textArr = [];
            var numberArr = [];
            $.each(formData, function (name, value) {
                var text = $("[name='" + name + "']").parent().prev().text().replace('*', '').replace('：','');
                var classValue = $("[name='"+name+"']").attr("class");
                if (!(value && value != '')) {
                   if (classValue && classValue.indexOf("required") != -1) {
                       textArr.push(text);
                   }
                }
                if (value && value != '') {
                    if (classValue && classValue.indexOf("number") != -1) {
                        if (!isNumber(value)) {
                            numberArr.push(text);
                        }
                    }
                }

            });
            if (textArr.length > 0) {
                art.dialog({
                    content:"请输入" + textArr[0],
                    icon:"warning",
                    ok:true
                });
                return;
            }
            if (numberArr.length > 0) {
                art.dialog({
                    content:numberArr[0]+"必须为数字",
                    icon:"warning",
                    ok:true
                });
                return;
            }

            $.ajax({
                url:"bdcApplyController.do?bdcLandPrcCalculator",
                method:"post",
                async:false,
                data:formData,
                success:function (data) {
                    if (data){
                        var json = JSON.parse(data);
                        if (json.success){
                            var value = json.value;
                            $("[name='YDCRJ']").val(json.value)
                            if (value.indexOf("-") != -1) {
                                art.dialog({
                                    content:"请检查输入值是否正确",
                                    icon:"warning",
                                    ok:true
                                })
                            }
                        } else {
                            art.dialog({
                                content:"系统错误，请联系管理员",
                                icon:"error",
                                ok:true
                            })
                        }
                    }
                }
            });
        }

        function changeTdzrqx1(val) {
            if (val == 1) {
                $("[name='YDMJ']").removeClass("required");
                $("#ydmjFont").hide();
            } else {
                $("[name='YDMJ']").addClass("required");
                $("#ydmjFont").show();
            }
        }

        function isNumber(val) {
            if(val === "" || val ==null){
                return false;
            }
            var reg = new RegExp('^[0-9]+([.]{1}[0-9]+){0,1}$');
            var flag = reg.test(val);
            return flag;
        }

        function changeRjl() {
            art.dialog({
                content:"有清晰明确且合法有效的批准文件，能够确定容积率的，按照批准文件执行",
                icon:"warning",
                ok:true
            })
        }

    </script>
    <style>
        .flexBox{
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .usualMargin {
            margin-top: 5px;
            margin-bottom: 5px;
        }
        .usualTitle{
            font-size: 23px;
            font-weight: 700;
        }
        .usualContent{
            display: flex;
            justify-content: left;
            margin-left: 30px;
            margin-top: 15px;
            align-items: center;
        }
        .usualSelect{
            width: 300px;
            height: 30px;
            font-size: 16px;
        }
        .usualInput{
            height: 30px;
            font-size: 16px;
            width: 300px;
        }
        .usualDiv{
            background-color: #ffffff;
            font-size: 16px;
            height: 600px;
        }
        .usualLabel{
            width: 220px;
        }
    </style>
</head>
<body style="background: #f0f0f0;">

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>
<div id="allForm"  class="eui-main" >
    <div style="display: flex;justify-content: center;">
        <div style="width: 250px;background-color: #ffffff;font-size: 16px;border-right: 1px solid #cccccc">
            <div class="flexBox usualMargin usualTitle">不动产费用查询</div>
            <div class="flexBox usualMargin" style="cursor: pointer;text-decoration:underline;margin-top: 15px;"><a href="javascript:void (0)" onclick="toUrl(1);">历史遗留不动产</a></div>
            <div class="flexBox usualMargin" style="cursor: pointer;text-decoration:underline"><a href="javascript:void (0)" onclick="toUrl(2);">国有划拨建设土地使用权转让</a></div>
            <div class="flexBox usualMargin" style="cursor: pointer;text-decoration:underline"><a href="javascript:void (0)" onclick="toUrl(3);">登记费及工本费</a></div>
            <div class="flexBox usualMargin" style="cursor: pointer;text-decoration:underline"><a href="javascript:void (0)" onclick="toUrl(4);">税费查询</a></div>
        </div>
        <div id="djjsq" class="usualDiv" style="width: 650px;border-right: 1px solid #cccccc;">
            <div class="flexBox usualMargin usualTitle">地价计算器</div>

            <input type="hidden" name="TYPE" class="required">

            <div class="usualContent" id="bllx">
                <div><font color="red">*</font>办理类型：</div>
                <div>
                    <c:forEach items="${jsqbllx}" var="list">
                        <input type="radio" name="BLLX" onclick="toBllx(this.value);" value="${list.DIC_CODE}"> ${list.DIC_NAME}
                    </c:forEach>
                </div>
            </div>
            <div id="dwjzf">
                <div class="usualContent" id="tdxz">
                    <div class="usualLabel"><font color="red">*</font>土地性质：</div>
                    <div>
                        <select name="TDXZ" class="usualSelect required">
                            <option value="">请选择</option>
                            <c:forEach items="${jsqtdxz}" var="list">
                                <option value="${list.DIC_CODE}">${list.DIC_NAME}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="usualContent" id="tdsyqmj">
                    <div class="usualLabel"><font color="red">*</font>土地使用权面积（平方米）：</div>
                    <div>
                        <input type="text" name="TDSYQMJ" class="usualInput required number">
                    </div>
                </div>
                <div class="usualContent" id="jzmj">
                    <div class="usualLabel"><font color="red">*</font>建筑面积（平方米）：</div>
                    <div>
                        <input type="text" name="JZMJ" class="usualInput required number">
                    </div>
                </div>
                <div class="usualContent" id="tdyt">
                    <div class="usualLabel"><font color="red">*</font>土地用途：</div>
                    <div>
                        <select name="TDYT" class="usualSelect required">
                            <option value="">请选择</option>
                            <c:forEach items="${jsqtdyt}" var="list">
                                <option value="${list.DIC_CODE}">${list.DIC_NAME}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div id="zjfwfgxs">
                <div class="usualContent" id="tdzrqx1">
                    <div class="usualLabel"><font color="red">*</font>土地转让情形：</div>
                    <div>
                        <select name="TDZRQX1" class="usualSelect required" onchange="changeTdzrqx1(this.value)">
                            <option value="">请选择</option>
                            <c:forEach items="${jsqtdzrlx}" var="list">
                                <option value="${list.DIC_CODE}">${list.DIC_NAME}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="usualContent" id="zjzmj1">
                    <div class="usualLabel"><font color="red">*</font>总建筑面积（平方米）：</div>
                    <div>
                        <input type="text" name="JJZMJ1" class="usualInput required number" >
                    </div>
                </div>
                <div class="usualContent" id="zdmj1">
                    <div class="usualLabel"><font color="red">*</font>占地面积（平方米）：</div>
                    <div>
                        <input type="text" name="ZDMJ1" class="usualInput required number" >
                    </div>
                </div>
                <div class="usualContent" id="ydmj">
                    <div class="usualLabel"><font color="red" id="ydmjFont" style="display: none;">*</font>用地面积（平方米）：</div>
                    <div>
                        <input type="text" name="YDMJ" class="usualInput number" >
                    </div>
                </div>
                <div class="usualContent" id="rjl1">
                    <div class="usualLabel"><font color="red">*</font>容积率：</div>
                    <div>
                        <input type="text" name="RJL1" class="usualInput required number" value="2.5" onchange="changeRjl();">
                    </div>
                </div>
            </div>
            <div id="jcgzxm">
                <div class="usualContent" id="tdzrqx2">
                    <div class="usualLabel"><font color="red">*</font>土地转让情形：</div>
                    <div>
                        <select name="TDZRQX2" class="usualSelect required">
                            <option value="">请选择</option>
                            <c:forEach items="${jsqtdzrlx}" var="list">
                                <option value="${list.DIC_CODE}">${list.DIC_NAME}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="usualContent" id="zjzmj2">
                    <div class="usualLabel"><font color="red">*</font>总建筑面积（平方米）：</div>
                    <div>
                        <input type="text" name="JJZMJ2" class="usualInput required number" >
                    </div>
                </div>
                <div class="usualContent" id="dyjzmj">
                    <div class="usualLabel"><font color="red">*</font>单元建筑面积（平方米）：</div>
                    <div>
                        <input type="text" name="DYJZMJ" class="usualInput required number" >
                    </div>
                </div>
                <div class="usualContent" id="zdmj2">
                    <div class="usualLabel"><font color="red">*</font>占地面积（平方米）：</div>
                    <div>
                        <input type="text" name="ZDMJ2" class="usualInput required number" >
                    </div>
                </div>
                <div class="usualContent" id="rjl2">
                    <div class="usualLabel"><font color="red">*</font>容积率：</div>
                    <div>
                        <input type="text" name="RJL2" class="usualInput required number" value="4.0" onchange="changeRjl();">
                    </div>
                </div>
            </div>
            <div class="flexBox" style="margin-top: 20px;margin-bottom: 20px;">
                <div><img src="<%=path%>/webpage/site/bdc/info/images/2020122501.png" style="width: 39px;height: 32px;" alt=""></div>
                <div><button style="height: 30px;width: 60px;" onclick="calculation();">计算</button></div>
            </div>
            <div style="border-bottom: 1px solid #cccccc;"></div>
            <div class="flexBox usualMargin usualTitle">地价计算器结果</div>
            <div class="usualContent" style="display: flex;justify-content: center;margin-top: 50px;">
                <div><span id="jsqTitle">用地出让金：</span></div>
                <div>
                    <input type="text" name="YDCRJ" class="usualInput" style="width: 284px;">
                </div>
            </div>
            <div class="usualContent" style="display: flex;justify-content: center;margin-top: 50px;">
                备注： 以上结算结果，仅供参考。具体收费以现场办理核算为准
            </div>
            <div style="margin-bottom: 50px;"></div>
        </div>
        <div id="ftck" style="width: 300px;" class="usualDiv">
            <div class="flexBox usualMargin usualTitle">附图查看</div>
            <div>
                <div class="flexBox" style="margin-top: 50px;">
                    <a href="<%=path%>/DownLoadServlet?fileId=wu_1eqm7t9051hbf1pkr1eqrrb0gj65" target="_blank">
                        <img src="<%=path%>/webpage/site/bdc/info/images/2020122901.jpg" style="width: 150px;height: auto;cursor: pointer;">
                    </a>
                </div>
                <div class="flexBox" style="margin-top: 50px;">
                    <a href="<%=path%>/DownLoadServlet?fileId=wu_1eqm7tssl186i1et21f4c1rtn1vhu5" target="_blank">
                        <img src="<%=path%>/webpage/site/bdc/info/images/2020122902.jpg" style="width: 150px;height: auto;cursor: pointer;">
                    </a>
                </div>
            </div>
        </div>

        <div id="djfjgbf" style="width: 950px;display: none;" class="usualDiv">
            <div class="flexBox">
                <img src="<%=path%>/webpage/site/bdc/info/images/2020122801.png">
            </div>
        </div>
    </div>
</div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>