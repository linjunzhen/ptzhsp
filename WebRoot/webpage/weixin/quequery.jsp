 <!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<%
    List<Map<String,Object>>  listA=(List<Map<String,Object>>)request.getAttribute("itemA");
    int sumA=0;
    for(Map<String,Object> map:listA){
        int num=Integer.parseInt(String.valueOf(map.get("num")));
        sumA+=num;
    }
    request.setAttribute("sumA",sumA);


    List<Map<String,Object>>  listC=(List<Map<String,Object>>)request.getAttribute("itemC");
    int sumC=0;
    for(Map<String,Object> map:listC){
        int num=Integer.parseInt(String.valueOf(map.get("num")));
        sumC+=num;
    }
    request.setAttribute("sumC",sumC);



    List<Map<String,Object>>  listD=(List<Map<String,Object>>)request.getAttribute("itemD");
    int sumD=0;
    for(Map<String,Object> map:listD){
        int num=Integer.parseInt(String.valueOf(map.get("num")));
        sumD+=num;
    }
    request.setAttribute("sumD",sumD);


    List<Map<String,Object>>  listE=(List<Map<String,Object>>)request.getAttribute("itemE");
    int sumE=0;
    for(Map<String,Object> map:listE){
        int num=Integer.parseInt(String.valueOf(map.get("num")));
        sumE+=num;
    }
    request.setAttribute("sumE",sumE);



    List<Map<String,Object>>  listF=(List<Map<String,Object>>)request.getAttribute("itemF");
    int sumF=0;
    for(Map<String,Object> map:listF){
        int num=Integer.parseInt(String.valueOf(map.get("num")));
        sumF+=num;
    }
    request.setAttribute("sumF",sumF);
    

    List<Map<String,Object>>  listY=(List<Map<String,Object>>)request.getAttribute("itemY");
    int sumY=0;
    for(Map<String,Object> map:listY){
        int num=Integer.parseInt(String.valueOf(map.get("num")));
        sumY+=num;
    }
    request.setAttribute("sumY",sumY);
%>
<head>
    <meta charset="utf-8">
    <title>业务查询</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="<%=path%>/webpage/weixin/css/eui.css">
    <style>

    </style>
</head>

<body style="background: #f2f2f2;">
<div class="eui-ywcxCon">
    <div class="eui-ywcxheader clearfix"  style="position: fixed;">
        <ul>
            <li class="menuItem">
                <a href="#item1" class="on">
                   	 一窗通办
                </a>
            </li>
            <li class="menuItem">
                <a href="#item2">
                    C厅
                </a>
            </li>
            <li class="menuItem">
                <a href="#item3">
                    D厅
                </a>
            </li>
            <li class="menuItem">
                <a href="#item4">
                    E厅
                </a>
            </li>
            <li class="menuItem">
                <a href="#F">
                    F厅
                </a>
            </li>
            <li class="menuItem">
                <a href="#item6">
                                                           婚姻登记
                </a>
            </li>
        </ul>
    </div>
    <div class="eui-ywcxlistbox">

        <div class="eui-ywcxtotal" id="item1">
            <div class="eui-totalL">
                <span>一窗通办</span>
            </div>
            <div class="eui-totalR">
                排队总数：<span>${sumA}</span>人
            </div>
        </div>
        <div class="eui-ywcxlist">
            <ul>
                <c:forEach items="${itemA}" var="itemA">
                    <li>
                        <div class="eui-ywcxliBox">
                            <i>${itemA.room_no}</i>
                            <div class="eui-ywcxinfo">
                                <div class="eui-ywcxL">
                                    <p class="eui-ywcxlTop">${itemA.business_name}</p>
                                    <p class="eui-ywcxlBtm">部门：<span>${itemA.depart_name}</span></p>
                                </div>
                                <div class="eui-ywcxR">
                                    <p class="eui-dqwz">
                                        <span class="eui-dqwztt">当前排队</span>
                                        <span><b>${itemA.num}</b>人</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>


        <div class="eui-ywcxtotal" id="item2">
            <div class="eui-totalL">
                <span>大厅C</span>
            </div>
            <div class="eui-totalR">
                排队总数：<span>${sumC}</span>人
            </div>
        </div>
        <div class="eui-ywcxlist">
            <ul>
                <c:forEach items="${itemC}" var="itemC">
                    <li>
                        <div class="eui-ywcxliBox">
                            <i>${itemC.room_no}</i>
                            <div class="eui-ywcxinfo">
                                <div class="eui-ywcxL">
                                    <p class="eui-ywcxlTop">${itemC.business_name}</p>
                                    <p class="eui-ywcxlBtm">部门：<span>${itemC.depart_name}</span></p>
                                </div>
                                <div class="eui-ywcxR">
                                    <p class="eui-dqwz">
                                        <span class="eui-dqwztt">当前排队</span>
                                        <span><b>${itemC.num}</b>人</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="eui-ywcxtotal" id="item3">
            <div class="eui-totalL">
                <span>大厅D</span>
            </div>
            <div class="eui-totalR">
                排队总数：<span>${sumD}</span>人
            </div>
        </div>
        <div class="eui-ywcxlist">
            <ul>
                <c:forEach items="${itemD}" var="itemD">
                    <li>
                        <div class="eui-ywcxliBox">
                            <i>${itemD.room_no}</i>
                            <div class="eui-ywcxinfo">
                                <div class="eui-ywcxL">
                                    <p class="eui-ywcxlTop">${itemD.business_name}</p>
                                    <p class="eui-ywcxlBtm">部门：<span>${itemD.depart_name}</span></p>
                                </div>
                                <div class="eui-ywcxR">
                                    <p class="eui-dqwz">
                                        <span class="eui-dqwztt">当前排队</span>
                                        <span><b>${itemD.num}</b>人</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="eui-ywcxtotal" id="item4">
            <div class="eui-totalL">
                <span>大厅E</span>
            </div>
            <div class="eui-totalR">
                排队总数：<span>${sumE}</span>人
            </div>
        </div>
        <div class="eui-ywcxlist">
            <ul>
                <c:forEach items="${itemE}" var="itemE">
                    <li>
                        <div class="eui-ywcxliBox">
                            <i>${itemE.room_no}</i>
                            <div class="eui-ywcxinfo">
                                <div class="eui-ywcxL">
                                    <p class="eui-ywcxlTop">${itemE.business_name}</p>
                                    <p class="eui-ywcxlBtm">部门：<span>${itemE.depart_name}</span></p>
                                </div>
                                <div class="eui-ywcxR">
                                    <p class="eui-dqwz">
                                        <span class="eui-dqwztt">当前排队</span>
                                        <span><b>${itemE.num}</b>人</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="eui-ywcxtotal" id="item5">
            <div class="eui-totalL">
                <span>大厅F</span>
            </div>
            <div class="eui-totalR">
                排队总数：<span>${sumF}</span>人
            </div>
        </div>
        <div class="eui-ywcxlist">
            <ul>
                <c:forEach items="${itemF}" var="itemF">
                <li>
                <div class="eui-ywcxliBox">
                    <i>${itemF.room_no}</i>
                    <div class="eui-ywcxinfo">
                        <div class="eui-ywcxL">
                            <p class="eui-ywcxlTop">${itemF.business_name}</p>
                            <p class="eui-ywcxlBtm">部门：<span>${itemF.depart_name}</span></p>
                        </div>
                        <div class="eui-ywcxR">
                            <p class="eui-dqwz">
                                <span class="eui-dqwztt">当前排队</span>
                                <span><b>${itemF.num}</b>人</span>
                            </p>
                        </div>
                    </div>
                </div>
            </li>
                </c:forEach>
            </ul>
        </div>
        
        
        <div class="eui-ywcxtotal" id="item6">
            <div class="eui-totalL">
                <span>婚姻登记</span>
            </div>
            <div class="eui-totalR">
                排队总数：<span>${sumY}</span>人
            </div>
        </div>
        <div class="eui-ywcxlist">
            <ul>
                <c:forEach items="${itemY}" var="itemY">
                    <li>
                        <div class="eui-ywcxliBox">
                            <i>${itemY.room_no}</i>
                            <div class="eui-ywcxinfo">
                                <div class="eui-ywcxL">
                                    <p class="eui-ywcxlTop">${itemY.business_name}</p>
                                    <p class="eui-ywcxlBtm">部门：<span>${itemY.depart_name}</span></p>
                                </div>
                                <div class="eui-ywcxR">
                                    <p class="eui-dqwz">
                                        <span class="eui-dqwztt">当前排队</span>
                                        <span><b>${itemY.num}</b>人</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        
    </div>
</div>
</body>
<script type="text/javascript"
        src="<%=path%>/webpage/weixin/js/jquery.min.js"></script>

<script>
    $(document).ready(function () {
        $(window).scroll(function () {
            var top = $(document).scrollTop(); //定义变量，获取滚动条的高度
            var menu = $(".eui-ywcxheader"); //定义变量，抓取#menu
            var items = $(".eui-ywcxlistbox").find(".eui-ywcxtotal"); //定义变量，查找.item

            var curId = ""; //定义变量，当前所在的楼层item #id

            items.each(function () {
                var m = $(this); //定义变量，获取当前类
                var itemsTop = m.offset().top; //定义变量，获取当前类的top偏移量
                if (top > itemsTop - 100) {
                    curId = "#" + m.attr("id");
                } else {
                    return false;
                }
            });

            //给相应的楼层设置cur,取消其他楼层的cur
            var curLink = menu.find(".on");
            if (curId && curLink.attr("href") != curId) {
                curLink.removeClass("on");
                menu.find("[href=" + curId + "]").addClass("on");
            }
            // console.log(top);
        });
        $(".eui-ywcxheader a").click(function () {
            $(this).removeClass("on");
            var href = $(this).attr("href");
            var pos = $(href).offset().top;
            $("html,body").animate({scrollTop: pos}, 500);
            return false;
        });
    });

</script>
</html>