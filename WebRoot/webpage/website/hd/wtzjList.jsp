<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String type = request.getParameter("type");
    request.setAttribute("type", type);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>堵点难点问题征集列表_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
    <!-- my97 begin -->
    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <!-- my97 end -->
    <link type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet" id="easyuiTheme">
    <link type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
    <script src="<%=path%>/plug-in/easyui-1.4/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
    <link type="text/css" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" rel="stylesheet">
    <link type="text/css" href="<%=path%>/plug-in/validationegine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet">
    <script src="<%=path%>/plug-in/validationegine-2.6.2/jquery.validationEngine.js" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/validationegine-2.6.2/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/json-2.0/json2.js" type="text/javascript"></script>
    <script src="<%=path%>/plug-in/layer-1.8.5/layer.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/swfupload-2.2/swfupload.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/upload/jquery.picupload.js"></script>
    <link type="text/css" href="<%=path%>/plug-in/jqueryautocomplete-1.2.3/jquery.autocomplete.css" rel="stylesheet">
    <script src="<%=path%>/plug-in/jqueryautocomplete-1.2.3/jquery.autocomplete.js" type="text/javascript"></script>
    <!--[if lte IE 6]>
    <script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
    </script>
    <![endif]-->

</head>

<body>
<%--开始编写头部文件 --%>
<jsp:include page="../index/head.jsp?currentNav=zxhd" />
<%--结束编写头部文件 --%>
<div class="container lbpadding">
    <%--    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>堵点难点问题征集</i></div>--%>
    <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>咨询互动</i> > <i>堵点难点问题征集</i></div>
    <div class="listMain clearfix">
        <div class="listL">
            <div class="listTitle">咨询互动</div>
            <div class="sublist">
                <ul>
                    <%--                    	<li><a href="webSiteController/view.do?navTarget=website/hd/xsqx">写诉求信</a></li>--%>
                    <li><a href="webSiteController/view.do?navTarget=website/hd/wtzj">堵点难点问题征集</a></li>
                        <li class="subOn"><a href="webSiteController/view.do?navTarget=website/hd/wtzjList">堵点难点问题列表</a></li>
                </ul>
            </div>
        </div>
        <div class="listR">
            <div>
                <div class="title3" style="margin-bottom:0px;">问题查询</div>
                    <div style="margin-top: 20px;margin-left: 36px;">
                        <input type="text" maxlength="30" class="textinput" id="questionNum" placeholder="请输入问题编号"/>
                        <a href="javascript:void(0);" onclick="findQuestionData()" style="padding: 6px 10px;background-color: #0075c0;color: #ffffff;">查询</a>
                    </div>
            </div>

            <div class="title3" style="margin-bottom:0px;">问题列表</div>
            <div class="eui-bgl" style="margin-top: 20px;">
                <table cellpadding="0" cellspacing="0" class="eui-table1">
                    <tr>
                        <th width="100px">编号</th>
                        <th width="200px">标题</th>
                        <th width="200px">单位</th>
                        <th width="50px">发件人</th>
                        <th width="50px">状态</th>

                    </tr>
                </table>
                <div class="bd">
                    <ul>
                        <li>
                            <table cellpadding="0" cellspacing="0" class="eui-table1" id="questionList">
                            </table>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%--开始编写尾部文件 --%>
<jsp:include page="../index/foot.jsp" />
<%--结束编写尾部文件 --%>
<script type="text/javascript">

    window.onload = function () {
        findQuestionData();
    }

    function findQuestionData() {
        var questionNum = $("#questionNum").val();
        $.ajax({
            url:"wyjcController/findQuestionList.do",
            type:"post",
            data:{
                questionNum:questionNum
            },
            success:function (data) {
                var questionList = data.rows;
                var str = "";
                if (questionList != null && questionList.length > 0) {
                    for (var i = 0; i < questionList.length; i++) {
                        str += "<tr><td width='100px;'>" + questionList[i].QUESTION_CODE + "</td>" +
                            "<td width='200px' style='cursor:pointer;text-decoration:underline;' id='"+questionList[i].QUESTION_ID+"' onclick='questionDetail(this)'>"+questionList[i].QUESTION_TITLE+"</td>" +
                            "<td width='200px'>"+questionList[i].QUESTION_ADDRESS+"</td>" +
                            "<td width='50px'>"+questionList[i].QUESTION_NAME+"</td>";
                        if (questionList[i].REPLY_FLAG == 0) {
                            str += "<td width='50px'>未回复</td></tr>"
                        } else {
                            str += "<td width='50px'>已回复</td></tr>"
                        }
                    }
                }
                $("#questionList").html(str)
            }
        })
    }

    function questionDetail(e){
        var questionId = e.id;
        $.dialog.open("wyjcController/findQuestionDetail.do?questionId=" + questionId, {
            title : "疑难问题详情",
            width : "600px",
            height : "400px",
            lock : true,
            resize : false
        }, false);
    }

</script>
</body>
</html>