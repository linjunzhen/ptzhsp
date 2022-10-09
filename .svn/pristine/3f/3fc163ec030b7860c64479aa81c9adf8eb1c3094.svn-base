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
    <title>视频播放</title>
    <script type="text/javascript" src="<%=path %>/plug-in/ckplayer6.8/ckplayer/ckplayer.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=path %>/plug-in/ckplayer6.8/js/offlights.js" charset="utf-8"></script>
</head>
<body>
    <div id="ckplayerArea"></div>
    <script type="text/javascript">
    	var flashvars = {
            //f: 'http://127.0.0.1:8080/fzqsngFile/attachFiles/video/20161128/wu_1b2k8a88ne8d1doh7ar1kd9dal4.mp4',
            f: '${requestScope.videoPath}',
            c: 0
        };
        var params = {
            bgcolor: '#FFF',
            allowFullScreen: true,
            allowScriptAccess: 'always',
            wmode: 'transparent'
        };
        CKobject.embedSWF('<%=path%>/plug-in/ckplayer6.8/ckplayer/ckplayer.swf', 
        		          'ckplayerArea', 
        		          'ckplayer_ckplayerArea', 
        		          '640', 
        		          '480', 
        		           flashvars, 
        		           params);
    </script>
</body>
</html>