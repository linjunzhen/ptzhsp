<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预览代码</title>
    
	<link rel="stylesheet" href="<%=basePath%>/plug-in/codemirror-3.11/lib/codemirror.css">
    <script src="<%=basePath%>/plug-in/codemirror-3.11/lib/codemirror.js"></script>
    <script src="<%=basePath%>/plug-in/codemirror-3.11/mode/javascript/javascript.js"></script>
    <script src="<%=basePath%>/plug-in/codemirror-3.11/addon/hint/show-hint.js"></script>
    <script src="<%=basePath%>/plug-in/codemirror-3.11/addon/dialog/dialog.js"></script>
    <script src="<%=basePath%>/plug-in/codemirror-3.11/addon/edit/closetag.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/plug-in/codemirror-3.11/addon/dialog/dialog.css">
    <script src="<%=basePath%>/plug-in/codemirror-3.11/addon/search/searchcursor.js"></script>
    <script src="<%=basePath%>/plug-in/codemirror-3.11/addon/search/search.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/plug-in/codemirror-3.11/addon/hint/show-hint.css">
    <script src="<%=basePath%>/plug-in/codemirror-3.11/addon/hint/javascript-hint.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/plug-in/codemirror-3.11/doc/docs.css">
    <link rel="stylesheet" href="<%=basePath%>/plug-in/codemirror-3.11/theme/solarized.css">
    <link rel="stylesheet" href="<%=basePath%>/plug-in/codemirror-3.11/theme/eclipse.css">
  </head>
  
  <body>
     <form>
    <textarea  id="code" name="code">
        ${mycode}
    </textarea>
    
    </form>

    <script>
      CodeMirror.commands.autocomplete = function(cm) {
        CodeMirror.showHint(cm, CodeMirror.javascriptHint);
      }
      var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
    	autoCloseTags: true,
        tabMode: 'indent',
        theme:'solarized dark',
        lineNumbers: true,
        extraKeys: {"Ctrl-Space": "autocomplete"}
      });
     
     
    </script>
  </body>
</html>
