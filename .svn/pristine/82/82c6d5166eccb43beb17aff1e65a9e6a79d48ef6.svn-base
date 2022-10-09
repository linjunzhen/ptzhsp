<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">

	
    <script type="text/javascript" language="javascript">
    function loadfn(){
    	if(!window.ActiveXObject){
    		alert("对不起，请使用IE浏览器！");
    		return false;
		}
   	    if (ScoreOcx1.object==null) {
   	        alert("ScoreOcx插件未安装！");
   	    }
   	    else{
   	        alert("已检测到ScoreOcx插件！");
   	    }
    }
    
    </script>
  </head>

  <body>
	<br/>	
    <input type="button" onclick="loadfn();" value="test"/>
  </body>
  <OBJECT ID="ScoreOcx1"  WIDTH=""  HEIGHT=""  CLASSID="CLSID:A8C33162-4D7F-45BB-AD6C-5BFA88289320"></OBJECT>
</html>
