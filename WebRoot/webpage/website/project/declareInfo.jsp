<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head></head>

<body>

<div class="eui-main">
    <jsp:include page="head.jsp"></jsp:include>
    <jsp:include page="banner.jsp"></jsp:include>
    <!-- 主体 -->
    <div class="eui-con">

        <div class="eui-tit round line">
            <b>网上申报指引<small>GUIDELINES FOR ONLINE DECLARATION</small></b>
        </div>
        <div class="eui-sbzy-pic eui-flex wrap">
            <!--
            <a href="#">1</a>
            <a href="#">3</a>
            <a href="#">5</a>
            <a href="#">7</a>
            <a href="#">2</a>
            <a href="#">4</a>
            <a href="#">6</a>
            <a href="#">8</a>
             -->
        </div>

        <div class="eui-tit round line">
            <b>常见问题<small>COMMON PROBLEM</small></b>
        </div>
        <div class="eui-flex tc eui-search">
            <input class="ipt" type="text" name="keyWord" placeholder="请输入问题名称" />
            <a class="eui-btn" href="javascript:void(0)" onclick="currentPage(1);resetTotal()">我要搜索</a>
        </div>
        <div class="eui-cjwt">
            <div class=" con"  id="wraper">

            </div>
            <div class="eui-page pager clearfix " id="pager" style="margin-left: 100px;">

            </div>
        </div>

    </div>
    <!-- 主体 end -->

    <!-- 底部 -->
    <div class="eui-footer">
        <iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no"
                allowtransparency="true"  src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
    </div>
    <!-- 底部 end -->
</div>


<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
    $(function() {
        // banner切换
        jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
        $(".eui-cjwt .arrow").click(function(){
            $(this).toggleClass("on")
        })
    });
</script>


<input name="totalData"  hidden="hidden" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/page.css">
<script src="<%=basePath%>/webpage/website/project/js/jquery.z-pager.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $("#pager").zPager({
        pageData:5,
        htmlBox: $('#wraper'),
        btnShow: true,
        ajaxSetData: false
    });

    function currentPage(currentPage){
        var start=(currentPage-1)*5;
        var keyWord=$("input[name='keyWord']").val();
        var url= '${webRoot}/projectWebsiteController.do?findContentForPage&start='+start+
        '&paras='+keyWord+':'+__commonProbleamModuleId+'&limit=5&dsid=235';
        url=encodeURI(url);

        $.ajax({
            url: url,
            type: 'post',
            async: false,//此处必须是同步
            dataType: 'json',
            success: function (data) {
                var result=JSON.parse(data.jsonString);
                var total=result.total;
                $("input[name='totalData']").val(total)
                var rows=result.data;
                var html="";
                for(var i=0; i<rows.length; i++){
                    var itemtitle=rows[i].ITEMTITLE;
                    var tid=rows[i].ITEMID;
                    var contentText=rows[i].CONTENTTEXT;
                    html=html+"   <div class=\"q\">"+itemtitle+"</div>\n" +
                        " <div class=\"a\">"+contentText+"</div>";
                }
                $("#wraper").html(html);
            }
        });
        /*
            触发页码数位置： Page/js/jquery.z-pager.js 64行
        */
        console.log("当前页码数：" + currentPage);
    }
    function settotal(){
        var keyWord=$("input[name='keyWord']").val();
        var url= '${webRoot}/projectWebsiteController.do?findContentForPage&start=0'+
            '&paras='+keyWord+':'+__commonProbleamModuleId+'&limit=5&dsid=235';
        url=encodeURI(url);
        $.ajax({
            url: url,
            type: 'post',
            async: false,//此处必须是同步
            dataType: 'json',
            success: function (data) {
                var result=JSON.parse(data.jsonString);
                var total=result.total;
                $("input[name='totalData']").val(total)
            }
        });
    }
    function resetTotal() {
        $("#pager").zPager({

        });
    }
</script>

</body>
</html>