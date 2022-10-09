<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" src="<%=basePath%>/plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript">
    $(function(){
        var hash={};
        //防止重复推送
        $.post("<%=path%>/callController.do?notifyWxHasOpenId", {
                recordId :'2c90b38a698f2c750169901b072d5584'},
            function(responseText, status, xhr) {
                hash[row.RECORD_ID]=true;
                var resultJson = $.parseJSON(responseText);
            }
        );
        hash[row.RECORD_ID]=true;
    });

</script>