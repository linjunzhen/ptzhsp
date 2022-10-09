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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title></title>
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/eui.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/jquery.min.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/perfect-scrollbar.jquery.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/echarts.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/jquery.SuperSlide.2.1.3.js" ></script>
</head>
<script>
    $(function () {

        window.setInterval(function (){location.reload();},3600000);

        initNdshjfajqs();
        initlawTypeStat();
        initlawTypeStat1();
        initCaseDistribute();
        initSllyStat();
    })

    function initNdshjfajqs(){

        var pastWeekDate = '${pastWeekDate}';
        var jqtCountList = '${jqtCountList}';
        var pastWeekDate = JSON.parse(pastWeekDate).map(function (item) {
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
                    data: JSON.parse(jqtCountList)
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);

    }

    function initlawTypeStat(){

        var pastWeekDate = '${pastWeekDate}';
        var pastWeekDate = JSON.parse(pastWeekDate).map(function (item) {
            return item.substring(5,10);
        });
        var jqtSlscCountList = '${jqtSlscCountList}';

        var myChart = echarts.init(document.getElementById('lawConsultType'));
        var option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : pastWeekDate,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine:{
                        lineStyle:{
                            fontSize: 16,
                            color:'#0ac3f8'
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisLabel:{
                        show:true,
                        interval:0,
                        rotate:45
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name:'单位（种）',
                    axisLine:{
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    show: false
                }
            ],
            series : [
                {
                    name:'平均受理时长',
                    type:'bar',
                    barWidth: '40%',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data:JSON.parse(jqtSlscCountList)
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);
    }

    function initlawTypeStat1(){

        var pastWeekDate = '${pastWeekDate}';
        var pastWeekDate = JSON.parse(pastWeekDate).map(function (item) {
            return item.substring(5,10);
        });
        var jqtBjscCountList = '${jqtBjscCountList}';

        var myChart = echarts.init(document.getElementById('lawConsultType1'));
        var option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : pastWeekDate,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine:{
                        lineStyle:{
                            fontSize: 16,
                            color:'#0ac3f8'
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisLabel:{
                        show:true,
                        interval:0,
                        rotate:45
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name:'单位（种）',
                    axisLine:{
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    show: false
                }
            ],
            series : [
                {
                    name:'平均受理时长',
                    type:'bar',
                    barWidth: '40%',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data:JSON.parse(jqtBjscCountList)
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);
    }

    function initCaseDistribute() {
        var ztqkJson = '${ztqkJson}';
        var itemNameArr = [];
        var sumArr = [];
        var ztqkArr = [];
        if (ztqkJson) {
            var ztqkObj = JSON.parse(ztqkJson);
            if (ztqkObj.length > 0) {
                for (let i = 0; i < ztqkObj.length; i++) {
                    var obj = {};
                    itemNameArr.push(ztqkObj[i].itemName);
                    sumArr.push(ztqkObj[i].count);
                    obj.name = ztqkObj[i].itemName;
                    obj.value = ztqkObj[i].count;
                    ztqkArr.push(obj);
                }
            }
        }

        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById('caseDistribute'));
        var option = {
            tooltip : {
                trigger : 'item',
                // formatter : "{a} <br/>{b}: {c} ({d}%)"
                formatter(params){
                    var str = "办件总数<br>";
                    if (params.data.name && params.data.name.length > 15) {
                        str += params.data.name.substr(0, 14) + "..." + "：";
                    } else {
                        str += params.data.name + "：";
                    }
                    str += params.data.value;
                    return str;
                }
            },
            legend : {
                show : false,
                data : itemNameArr
            },
            color : [ '#71a7ff', '#33cf78', '#00c0ff', '#835adf', '#7ca8a7', '#31fff8', '#6bf535', '#3577fa'],
            series : [
                {
                    name : '办件总数',
                    type : 'pie',
                    radius : [ '70%', '93%' ],
                    avoidLabelOverlap : false,
                    label : {
                        normal : {
                            show : false,
                            position : 'center'
                        },
                        emphasis : {
                            show : false,
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
                    data : ztqkArr
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.setOption(option);

    }

    function initSllyStat() {

        var cksq = '${cksqPersent}';
        var xssq = '${wssqPersent}';
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

    function toSxtjbStat(thiz) {
        var itemCode = $(thiz).attr("id");
        if (itemCode) {
            window.location.href = encodeURI("<%=path%>/bdcApplyController.do?bdcsxtjbView&itemCode=" + itemCode);
        }
    }

    function toBdcjbrytjb() {
        window.location.href = encodeURI("<%=path%>/bdcApplyController.do?bdcjbrytjbView");
    }

    function toYqbjmxbView(queryStr) {
        window.location.href = encodeURI("<%=path%>/bdcApplyController.do?bdcbjmxbView&"+queryStr);
    }

</script>
<body>
<div class="eui-bodyBox">
    <div class="eui-head"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/toptitle.png" /></div>

    <!--左侧-->
    <div class="eui-left">
        <!--总体情况-->
        <div class="eui-ztqkBox">
            <p class="eui-title">总体情况</p>
            <div class="eui-ztqk eui-MainBox">
                <div class="photo">
                    <div class="eui-ztqkEchart">
                        <div id="caseDistribute" style="width: 258px;height: 258px;z-index: 999"></div>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/bdcdj-icon3.png" class="echartImg"/>
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/title1.png" class="title"/>
                        <i class="i-1"></i>
                        <i class="i-2"></i>
                    </div>
                </div>
                <div class="info">
                    <ul>
                        <c:forEach var="list" items="${ztqkList}" varStatus="status">
                            <li><i class="i-${status.count}"></i><p id="${list.itemCode}" <c:if test="${list.itemName!= '其他'}">style="cursor: pointer;"</c:if> onclick="toSxtjbStat(this);">${list.itemName}</p><span>${list.sum}</span></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <!--总体情况-->
        <!--服务评价-->
        <div class="eui-fwpjBox">
            <p class="eui-title">服务评价</p>
            <div class="eui-fwpj eui-MainBox sc">
                <ul>
                    <li onclick="toYqbjmxbView('PJXX=3');" style="cursor: pointer;z-index: 9999;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon2.png"/>
                        <span class="name">非常满意</span>
                        <div class="star"><i style="width: 100%;"></i></div>
                        <span class="num">${pjCountMap.fcmy}</span>
                    </li>
                    <li onclick="toYqbjmxbView('PJXX=2');" style="cursor: pointer;z-index: 9999;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon3.png"/>
                        <span class="name">满意</span>
                        <div class="star"><i style="width: 90%;"></i></div>
                        <span class="num">${pjCountMap.my}</span>
                    </li>
                    <li onclick="toYqbjmxbView('PJXX=1');" style="cursor: pointer;z-index: 9999;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon4.png"/>
                        <span class="name">一般</span>
                        <div class="star"><i style="width: 60%;"></i></div>
                        <span class="num">${pjCountMap.yb}</span>
                    </li>
                    <li onclick="toYqbjmxbView('PJXX=0');" style="cursor: pointer;z-index: 9999;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon3.png"/>
                        <span class="name">不满意</span>
                        <div class="star"><i style="width: 40%;"></i></div>
                        <span class="num">${pjCountMap.bmy}</span>
                    </li>
                    <li onclick="toYqbjmxbView('PJXX=4');" style="cursor: pointer;z-index: 9999;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon4.png"/>
                        <span class="name">未评价</span>
                        <div class="star"><i style="width: 0%;"></i></div>
                        <span class="num">${pjCountMap.wpj}</span>
                    </li>
                </ul>
            </div>
        </div>
        <!--服务评价-->
    </div>
    <!--左侧-->
    <!--中间-->
    <div class="eui-center">
        <!--地图-->
        <div class="eui-map">
            <div class="eui-mapTime"><b>${date}</b></div>
            <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/map_pt.png" onclick="toBdcjbrytjb()" style="cursor: pointer;"/>
            <p class="name">各中心受理占比</p>
        </div>
        <!--地图-->
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
                    <div class="eui_fdcjy_con" onclick="toYqbjmxbView('SQQD=2');" style="cursor: pointer;z-index: 9999;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-cksq.png"/>
                        <p>窗口申请</p>
                        <span class="eui_fdcjy_num"><b>${cksqPersent}</b>%</span>
                    </div>
                    <div class="eui_fdcjy_con" onclick="toYqbjmxbView('SQQD=1');" style="cursor: pointer;">
                        <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-xssq.png"/>
                        <p>线上申请</p>
                        <span class="eui_fdcjy_num"><b>${wssqPersent}</b>%</span>
                    </div>
                </div>
            </div>
        </div>
        <!--受理来源-->
    </div>
    <!--中间-->

    <!--右侧-->
    <div class="eui-right">
        <!--每日受理量趋势-->
        <div class="eui-mrsllqsBox">
            <p class="eui-title">每日受理量趋势</p>
            <div class="eui-mrsllqs eui-MainBox" >
                <div class="eui-k-decision-list5-main" id="ndshjfajqs"></div>
            </div>
        </div>
        <!--每日受理量趋势-->
        <!--平均受理时长（分）-->
        <div class="eui-pjslBox">
            <p class="eui-title">平均受理时长（分）</p>
            <div class="eui-pjsl eui-MainBox">
                <div class="eui-k-left-list1-content" id="lawConsultType" style="width: 490px;height: 170px;"></div>
            </div>
        </div>
        <div class="eui-pjslBox">
            <p class="eui-title">平均办结时长（天）</p>
            <div class="eui-pjsl eui-MainBox">
                <div class="eui-k-left-list1-content" id="lawConsultType1" style="width: 490px;height: 170px;"></div>
            </div>
        </div>

        <!--平均受理时长（分）-->
        <!--办事效能-->
        <div class="eui-bsxnBox">
            <p class="eui-title">办事效能</p>
            <div class="eui-bsxn eui-MainBox">
                <ul>
                    <li onclick="toYqbjmxbView('1=1');" style="cursor: pointer;">
                        <div class="photo"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-sjl.png"/></div>
                        <div class="info">
                            <p>收件量</p>
                            <b>${sjlCount}</b>
                        </div>
                    </li>
                    <li onclick="toYqbjmxbView('1=1');" style="cursor: pointer;">
                        <div class="photo"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-sll.png"/></div>
                        <div class="info">
                            <p>受理量</p>
                            <b>${sjlCount}</b>
                        </div>
                    </li>
                    <li onclick="toYqbjmxbView('IS_YQ=0');" style="cursor: pointer;">
                        <div class="photo"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-jbj.png"/></div>
                        <div class="info">
                            <p>即办件（按时）</p>
                            <b>${jbjasCount}</b>
                        </div>
                    </li>
                    <li onclick="toYqbjmxbView('IS_YQ=1');" style="cursor: pointer;">
                        <div class="photo"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-jbjRed.png"/></div>
                        <div class="info">
                            <p>即办件（逾期）</p>
                            <b>${jbjyqCount}</b>
                        </div>
                    </li>
                    <li onclick="toYqbjmxbView('IS_YQ=0');" style="cursor: pointer;">
                        <div class="photo"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-cnj.png"/></div>
                        <div class="info">
                            <p>承诺件（按时）</p>
                            <b>${cnjasCount}</b>
                        </div>
                    </li>
                    <li onclick="toYqbjmxbView('IS_YQ=1');" style="cursor: pointer;">
                        <div class="photo"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/icon-cnjRed.png"/></div>
                        <div class="info">
                            <p>承诺件（逾期）</p>
                            <b>${cnjyqCount}</b>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!--每日受理量趋势-->
    </div>
    <!--右侧-->
</div>
</body>

<script>
    $(".sc").perfectScrollbar();
</script>
</html>
