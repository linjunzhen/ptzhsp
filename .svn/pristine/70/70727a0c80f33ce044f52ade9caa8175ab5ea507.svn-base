<%@page import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
// 获取取号事项列表
    Map<String, Object> itemCart = AppUtil.getItemCart();
    request.setAttribute("itemCart", itemCart);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>自助取号系统</title>
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/style.css">
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/idangerous.swiper.css">
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.SuperSlide.2.1.2.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/idangerous.swiper.min.js"></script>
</head>

<body class="bg-none" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

<div class="eui-title">
    <a class="back" href="javascript:void(0);" onclick="backToParent();"><img src="webpage/callYctb/takeNo/images/back.png"></a>
    目前仅支持试点业务！
    <a href="javascript:void(0)" onclick="toItemCart();" class="qhlb">已选列表<i>&gt;</i><em id="qhlbNum">${itemNum}</em></a>
</div>
<!-- 	<div class="eui-title"><a href="javascript:void(0);" >目前仅支持试点业务！</a></div> -->
<div class="slideTxtBox eui-tab">
    <div class="hd">
        <ul>
            <e:for filterid="1" end="100" var="efor" dsid="177">
                <li><i></i>${efor.TYPE_NAME}</li>
            </e:for>
        </ul>
    </div>
    <div class="bd">

        <e:for filterid="1" end="100" var="efor" dsid="177">
            <div>
                <div class="eui-tit">${efor.TYPE_ALIAS}</div>
                <div class="multipleColumn mul${efor.ROWNUM_}">
                    <div class="eui-sx">

                        <e:for filterid="${efor.YQYZQH_ID}" end="1000" var="efor1" dsid="178">
                            <li>
                                <a href="javascript:void(0);" onclick="chooseMaterType('${efor1.ITEM_CODE}'
                                        ,'${efor1.ITEM_NAME}','${efor1.DEF_KEY}',
                                        '${efor1.BUSINESS_CODE}','${efor1.BUSINESS_NAME}')"

                                        <c:forEach items="${itemCart}" var="item" varStatus="s">
                                            <c:if test="${item.key==efor1.ITEM_CODE}">
                                                style="color:red"
                                            </c:if>
                                        </c:forEach>
                                >${efor1.ITEM_NAME}
                                </a>
                            </li>
                        </e:for>
                    </div>
                    <div class="ft">
                        <a class="prev1">上一页</a>
                        <ul></ul>
                        <a class="next1">下一页</a>
                    </div>
                </div>
                <script type="text/javascript">
                    jQuery(".mul${efor.ROWNUM_} .eui-sx li").each(function(i){ jQuery(".mul${efor.ROWNUM_} .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
                </script>
            </div>
        </e:for>
    </div>
</div>

<script type="text/javascript">
    function readCard(businessCode,businessName,status,departId,belongRoom,lc){
        if(belongRoom!='${roomNo}'){//不动产登记中心
            parent.art.dialog({
                content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往"+belongRoom+"厅取号！</font>",
                icon:"warning",
                time:3,
                width:"400px",
                height:"150px",
                ok: false
            });
        }else{
            if(status=='0'){
                parent.art.dialog({
                    content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往人工导引台咨询取号！</font>",
                    icon:"warning",
                    time:3,
                    width:"400px",
                    height:"150px",
                    ok: false
                });
            }else{
                window.parent.document.getElementById('takeFrame').src="webpage/callYctb/takeNo/readCard.jsp?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo=${roomNo}";
            }
        }
    }

    /* 使用js分组，每6个li放到一个ul里面
    jQuery(".mul1 .eui-sx li").each(function(i){ jQuery(".mul1 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
    jQuery(".mul2 .eui-sx li").each(function(i){ jQuery(".mul2 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
    jQuery(".mul3 .eui-sx li").each(function(i){ jQuery(".mul3 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
    jQuery(".mul4 .eui-sx li").each(function(i){ jQuery(".mul4 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});*/
    jQuery(".mul_5 .eui-sx li").each(function(i){ jQuery(".mul_5 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
    /* 调用SuperSlide，每次滚动一个ul，相当于每次滚动6个li */
    jQuery(".multipleColumn").slide({titCell:".ft ul",mainCell:".eui-sx",prevCell:".prev1",nextCell:".next1",autoPage:true,effect:"leftLoop"});

    jQuery(".slideTxtBox").slide({effect:"fade"});

    function backToParent(){
        window.parent.document.getElementById('takeFrame').src="callController.do?toYqyzTypeChoose&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
    }
    function chooseMaterType(itemCode,itemName,defKey,businessCode,businessName){
        location.href="<%=path%>/callYqyzController/toMaterChoose.do?itemCode="+itemCode+"&itemName="
            +encodeURIComponent(itemName)+"&defKey="+defKey+"&businessName="+encodeURIComponent(businessName)+"&businessCode="+businessCode+
            "&roomNo=${roomNo}&departId=${departId}&ifMaterPrint=${ifMaterPrint}&macType=${macType}";
    }
    function toItemCart(){
        var num = $("#qhlbNum").html();
        if(num>0){
            window.parent.document.getElementById('takeFrame').src="<%=path%>/callYqyzController/toItemCartMacW.do";
        } else{
            parent.art.dialog({
                content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">请先选择需要取号的事项！</font>",
                icon:"warning",
                time:3,
                width:"400px",
                height:"150px",
                ok: true
            });
        }
    }
</script>
</body>
</html>
