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

<title>My JSP 'MyJsp.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="plug-in/laydate-1.1/laydatedemo.css" type="text/css" rel="stylesheet" />
<script src="plug-in/laydate-1.1/laydate.js"></script>
</head>

<body>


	<div class="laydate_main">
		<div class="box">

			<h2 style="margin-top:0;">简要介绍</h2>
			
			<div>
			<%--演示基本调用功能 --%>
			<input onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',min: laydate.now()})" class="laydate-icon" value="2013-09-08 23:23:30" />
			</div>


			<div class="demolist" style="padding-bottom: 10px;">
				日期范围限制：
				<ul class="inline">
					开始日：
					<input id="start" 
					class="laydate-icon"  />
					结束日:
					<input id="end"
					class="laydate-icon"  />
				</ul>
			</div>

		</div>
	</div>
</body>
<script type="text/javascript">
    var start = {
	    elem: '#start',
	    format: 'YYYY/MM/DD hh:mm:ss',
	    min: laydate.now(), //设定最小日期为当前日期
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
    var end = {
		    elem: '#end',
		    format: 'YYYY/MM/DD hh:mm:ss',
		    min: laydate.now(),
		    max: '2099-06-16 23:59:59',
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		        start.max = datas; //结束日选好后，重置开始日的最大日期
		    }
	};
	laydate(start);
	laydate(end);
</script>

</html>
