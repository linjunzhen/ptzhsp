<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style>
#container li {
    display: inline;
    word-break: break-all;
    word-wrap: break-word ;
    margin: 1px 2px 16px 1px;
    float:left;
    text-align:center; 
}

#container li div{
    width: 160px;
    height: 160px;
}

#container li img{
    max-width: 160px;
    max-height: 160px;
}

#container label {
    float: left;
    position: relative;
    overflow: hidden;
}

#container span {
    position: absolute;
    left: 0;
    top: 0;
    width: 500px;
    height: 500px;
}

</style>
<script type="text/javascript">
    $(function() {
    	$.post(rootPath + "/article/imgresourcesContent.do", {
    		"contentText": document.getElementById("eWebEditor1").contentWindow.getHTML()
    	}, function(r) {
    		if (r.success) {
    			var str = "";
    			$.each(r.result, function(idx, imgurl) {
    				str += "<li><label><span style='background:url("+imgurl+") no-repeat -5000px'></span><div><img src=\""+imgurl+"\"/></div><input type=\"radio\" name=\"selImg\" value=\"" + imgurl + "\"></label></li>"; 
    			});
        		$("#container").html(str);
    		} else {
    			$.messager.alert("提示", r.msg);    			
    		}
    	}, "json");
    });
</script>

<div title="文章图片" style="padding:10px">
    <ul id="container"></ul>
</div>


