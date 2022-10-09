<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta name="renderer" content="webkit"/> 
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>音频播放</title>
    <script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwplayer.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwpsrv.js"></script>
</head>
<body>
<div class="img" id="player1">loading...</div>
<script type="text/javascript">
    jwplayer("player1").setup({
        primary: "flash",
        skin: "<%=path%>/plug-in/jwplayer/skins/white.xml",
        file: "${requestScope.audioPath}",
        width: 400,
        height: 120,
        stretching:"fill"
    });
</script>
</body>
</html>