<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/eui.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/jquery.min.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/perfect-scrollbar.jquery.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/echarts.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/jquery.SuperSlide.2.1.3.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/layer-1.8.5/layer.min.js" ></script>
</head>
<script>

    $(function () {
        var itemCode = '${itemCode}';
        if (itemCode) {
            $("[name='ITEM_CODE']").val(itemCode);
        } else {
            $("[name='ITEM_CODE']").val('569262478QR00310');
        }
        queryData();


        window.setInterval(function (){
            queryData();
        },3600000);

    })

    function queryData() {
        var layload = layer.load('正在查询中…');
        $.ajax({
            url: "bdcApplyController.do?bdcsxtjbData",
            type: "post",
            async: false,
            data:{itemCode:$("[name='ITEM_CODE']").val()},
            success:function (data) {
                if (data) {
                    var json = JSON.parse(data);
                    initNdshjfajqs(json);
                    initFwpjStat(json);
                    initSllyStat(json);
                    initSqlyStat(json);
                    initCkbjtjStat(json);
                    initSllyStat1(json);
                    initItemName(json);
                }
                layer.close(layload);
            }
        })
    }

    function initItemName(json) {
        $("#itemNameText").text(json.itemName + "近7日受理量");
    }

    function initNdshjfajqs(json){
        var pastWeekDate = json.pastWeekDate;
        var jqtCountList = json.jqtCountList;
        var pastWeekDate = pastWeekDate.map(function (item) {
            return item.substring(5,10);
        });

        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById('ndshjfajqs'));
        var option = {
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#0980B4'
                    }
                }
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : pastWeekDate,
                    axisLine:{
                        lineStyle:{
                            fontSize: 16,
                            color:'#0ac3f8'
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine:{
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick:{       //y轴刻度线
                        show:false
                    },
                    splitLine: {     //网格线
                        show: false
                    },
                    show: false
                }
            ],
            series : [
                {
                    name:'办件数量',
                    type:'line',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    },
                    areaStyle: {color: ['#0980B4']},
                    lineStyle: {color: ['#0980B4']},
                    data: jqtCountList
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);

    }

    function initFwpjStat(json) {
        $("#fcmy").text(json.pjCountMap.fcmy);
        $("#my").text(json.pjCountMap.my);
        $("#yb").text(json.pjCountMap.yb);
        $("#bmy").text(json.pjCountMap.bmy);
        $("#wpj").text(json.pjCountMap.wpj);
    }

    function initSllyStat(json) {
        var cksq = json.cksqPersent;
        var xssq = json.wssqPersent;
        var data = [];
        var cksqObj = {};
        var xssqObj = {};
        cksqObj.name = '窗口申请';
        cksqObj.value = cksq;
        xssqObj.name = '线上申请';
        xssqObj.value = xssq;
        data.push(cksqObj);
        data.push(xssqObj);
        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById('sllyStat'));
        var option = {
            tooltip : {
                trigger : 'item',
                formatter : "{a} <br/>{b}: {d}%"
            },
            legend : {
                show : false,
                data : ['窗口申请','线上申请']
            },
            color : [ '#2a8bd0', '#16aac6'],
            series : [
                {
                    name : '受理来源',
                    type : 'pie',
                    radius : [ '55%', '65%' ],
                    avoidLabelOverlap : false,
                    label : {
                        normal : {
                            show : false,
                            position : 'center'
                        },
                        emphasis : {
                            show : true,
                            textStyle : {
                                fontSize : '14',
                                fontWeight : 'bold'
                            }
                        }
                    },
                    labelLine : {
                        normal : {
                            show : false
                        }
                    },
                    data : data
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);

    }

    function initSqlyStat(json) {
        $("#cksq").text(json.cksqPersent)
        $("#xssq").text(json.wssqPersent)
    }

    function initCkbjtjStat(json) {
        var sjzs = json.sjlCount;
        var sls = json.sjlCount;
        var wcls = 0;
        var ghs = 0;
        var zxs = 0;
        if (json.callMap) {
            wcls = json.callMap.wcls;
            ghs = json.callMap.ghs;
            zxs = json.callMap.zxs;
        }
        $("#sjzs").text(sjzs);
        $("#sls").text(sls);
        $("#wcls").text(wcls);
        $("#ghs").text(ghs);
        $("#zxs").text(zxs);
        $("#ghs").prev().attr("style", "width:" + toPercent(ghs / sls * 0.8));
        $("#zxs").prev().attr("style", "width:" + toPercent(zxs / sls * 0.8));

    }

    function initSllyStat1(json) {
        var grSllyCountList = json.grSllyCountList;
        if (grSllyCountList && grSllyCountList.length > 0) {
            var len = grSllyCountList[0].length;
            if (len >= 15) {
                len = 15;
            }
            if (grSllyCountList && grSllyCountList.length > 0) {
                var title = "<tr>";
                for (let i = 0; i < len; i++) {
                    title += "<th>" + grSllyCountList[0][i] + "</th>"
                }
                title += "</tr>";
                $("#sllyTitle").html(title);

                var body = "";
                for (let i = 1; i < grSllyCountList.length; i++) {
                    body += "<tr>";
                    for (let j = 0; j < len; j++) {
                        body += "<td>" + grSllyCountList[i][j] + "</td>";
                    }
                    body += "</tr>";
                }
                $("#sllyBody").html(body);
            }
        } else {
            $("#sllyTitle").html("");
            $("#sllyBody").html("");
        }
    }

    function toPercent(point) {
        var str = Number(point * 100).toFixed(2);
        str += "%";
        return str;
    }

    function backToParentUrl() {
        window.location.href="javascript:history.go(-1)";
    }

    function toBdcjbrytjb() {
        window.location.href = encodeURI("<%=path%>/bdcApplyController.do?bdcjbrytjbView");
    }

</script>
<body>
<div class="eui-bodyBox">
    <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/btn_back.png" style="position: absolute;cursor:pointer;left: 0px;top: 25px;z-index: 9999;" onclick="backToParentUrl()">
    <div class="eui-head"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/toptitle.png" /></div>
    <!--查询-->
    <div class="eui-sxtjSearch">
        <ul>
            <li style="width: 410px;">
                <span>查询部门</span>
                <div class="info"><input type="" name="" id="" value="行政服务中心（不动产登记交易科）" /></div>
            </li>
            <li style="width: 410px;">
                <span>事项名称</span>
                <div class="info">
                    <select name="ITEM_CODE">
                        <c:forEach items="${itemList}" var="list">
                            <option value="${list.ITEM_CODE}">${list.ITEM_NAME}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li style="width: 410px;">
                <span>时间选择</span>
                <div class="info">
                    <div class="eui-data">${date}</div>
                </div>
            </li>
            <li class="btn" style="width: 130px;" onclick="queryData();"><span>查询</span></li>
        </ul>
    </div>
    <!--查询-->

    <!--左侧-->
    <div class="eui-left eui-sxtjLeft">
        <!--不动产登记查询近7日受理量-->
        <div class="eui-bdcdjcxBox">
            <p class="eui-title" id="itemNameText"></p>
            <div class="eui-bdcdjcx eui-MainBox">
                <div id="ndshjfajqs" class="eui-k-decision-list5-main"></div>
            </div>
        </div>
        <!--不动产登记查询近7日受理量-->
        <!--服务评价-->
        <div class="eui-fwpjBox">
            <p class="eui-title">服务评价</p>
            <div class="eui-fwpj eui-MainBox sc">
                <ul>
                    <li>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon2.png"/>
                        <span class="name">非常满意</span>
                        <div class="star"><i style="width: 100%;"></i></div>
                        <span class="num" id="fcmy"></span>
                    </li>
                    <li>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon3.png"/>
                        <span class="name">满意</span>
                        <div class="star"><i style="width: 90%;"></i></div>
                        <span class="num" id="my"></span>
                    </li>
                    <li>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon4.png"/>
                        <span class="name">一般</span>
                        <div class="star"><i style="width: 60%;"></i></div>
                        <span class="num" id="yb"></span>
                    </li>
                    <li>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon3.png"/>
                        <span class="name">不满意</span>
                        <div class="star"><i style="width: 40%;"></i></div>
                        <span class="num" id="bmy">}</span>
                    </li>
                    <li>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon4.png"/>
                        <span class="name">未评价</span>
                        <div class="star"><i style="width: 0%;"></i></div>
                        <span class="num" id="wpj"></span>
                    </li>
                </ul>
            </div>
        </div>
        <!--服务评价-->
    </div>
    <!--左侧-->
    <!--中间-->
    <div class="eui-center eui-sxtjCenter">
        <!--地图-->
        <div class="eui-map">
            <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/map_pt.png" onclick="toBdcjbrytjb()" style="cursor: pointer;"/>
            <p class="name">各中心受理占比</p>
        </div>
        <!--地图-->
    </div>
    <!--中间-->

    <!--右侧-->
    <div class="eui-right eui-sxtjRight">
        <!--受理来源-->
        <div class="eui-sllyBox">
            <p class="eui-title">受理来源</p>
            <div class="eui-slly">
                <div class="eui_fdcjy_l">
                    <div class="eui_fdcjy_l_anim">
                        <i></i>
                        <i></i>
                        <i></i>
                    </div>
                    <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/title2.png" class="title"/>
                    <!--echarts控件-->
                    <div class="eui_echarts_fdcjy">
                        <div id="sllyStat" style="width:310px; height:310px;z-index: 999"></div>
                    </div>
                </div>
                <div class="eui_fdcjy_r">
                    <div class="eui_fdcjy_con">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-cksq.png"/>
                        <p>窗口申请</p>
                        <span class="eui_fdcjy_num"><b id="cksq"></b>%</span>
                    </div>
                    <div class="eui_fdcjy_con">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-xssq.png"/>
                        <p>线上申请</p>
                        <span class="eui_fdcjy_num"><b id="xssq"></b>%</span>
                    </div>
                </div>
            </div>
        </div>
        <!--受理来源-->
        <!--窗口办件统计-->
        <div class="eui-ckbjtjBox">
            <p class="eui-title">窗口办件统计</p>
            <div class="eui-ckbjtj eui-MainBox">
                <ul>
                    <li>
                        <p>收件总数</p>
                        <div class="jdt"><i style="width: 80%;"></i><span id="sjzs"></span></div>
                    </li>
                    <li>
                        <p>受理数</p>
                        <div class="jdt"><i style="width: 80%;"></i><span id="sls"></span></div>
                    </li>
                    <li>
                        <p>未处理数</p>
                        <div class="jdt"><i style="width: 0%;"></i><span id="wcls"></span></div>
                    </li>
                    <li>
                        <p>过号数</p>
                        <div class="jdt"><i style="width: 10%;"></i><span id="ghs"></span></div>
                    </li>
                    <li>
                        <p>咨询数</p>
                        <div class="jdt"><i style="width: 30%;"></i><span id="zxs"></span></div>
                    </li>
                </ul>
            </div>
        </div>
        <!--窗口办件统计-->
    </div>
    <!--右侧-->
    <!--各经办人员受理统计服务评价-->
    <div class="eui-gjbryBox">
        <p class="eui-title">受理来源</p>
        <div class="eui-gjbry eui-MainBox">
            <table border="0" cellspacing="0" cellpadding="0">
                <thead id="sllyTitle">

                </thead>
                <tbody id="sllyBody">

                </tbody>
            </table>
        </div>
    </div>
    <!--各经办人员受理统计服务评价-->
</div>
</body>

<script>
    $(".sc").perfectScrollbar();
</script>
</html>