<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
<head>
    <title>用户信息错误</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">

    </script>
</head>

<body>
<div id="section_container">
    <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
            <div class="scroller">
                <div class="margin10">
                    <form class="form-common" method="post" id="bindForm" action="userBindController.do?toBind">
                        <h4 class="eveTitle1" style="height: 100px;">用户信息错误，请联系管理员!</h4>
                    </form>
                    <div>
                    </div>
        </article>
    </section>
</div>
<!--- third -->
<script src="<%=path%>/webpage/weixin/agile-lite/third/zepto/zepto.min.js"></script>
<script src="<%=path%>/webpage/weixin/agile-lite/third/iscroll/iscroll-probe.js"></script>
<script src="<%=path%>/webpage/weixin/agile-lite/third/arttemplate/template-native.js"></script>
<!-- agile -->
<script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/agile/js/agile.js"></script>
<!--- bridge -->
<script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/exmobi.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/agile.exmobi.js"></script>
<!-- app -->
<script src="<%=path%>/webpage/weixin/agile-lite/component/timepicker/agile.timepicker.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/component/extend.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/app/js/app.js"></script>

</body>
</html>