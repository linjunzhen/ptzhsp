<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>液晶屏</title>
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="webpage/callnew/takeNo/css/smalleui.css">
</head>
<script>
	$(function(){
        reloadView();
        setInterval('reloadView()',5000);
	});
	function reloadView(){
        $.post("callController.do?yqyzlineInfoJson",{},
           function(responseText, status, xhr) {
               var resultJson = $.parseJSON(responseText);
               if (resultJson != null && resultJson.length > 0) {
                   listInfo(resultJson);
               } else {
                   $("#mainContent").html("");
               }
           }
        );
	}
    function listInfo(callingLine){
        $("#mainContent").html("");
        var newhtml = "";
        for(var i=0; i<callingLine.length; i++){
            var callTime=callingLine[i].CALLING_TIME;
            if(callTime==''||typeof (callTime)=='undefined'){
                callTime="等候中";
			}else{
                callTime=callTime.substring(11,16);
			}
			var curWin=callingLine[i].CUR_WIN;
            if(curWin==''||typeof (curWin)=='undefined'){
                curWin="";
            }
           newhtml+="<tr>";
           newhtml+="<td>"+callingLine[i].LINE_NO+"</td>";
           newhtml+="<td>"+curWin+"</td >";
           if("等候中"==callTime){
               newhtml+="<td class=\"eui-yellow\">"+callTime+"</td>";
		   }else{
               newhtml+="<td>"+callTime+"</td>";
		   }
           newhtml+="</tr>";
        }
        $("#mainContent").html(newhtml);
	}
	function formmatName(name){
	    var newName="";
	    var charName="";
	    for(var i=0;i<name.length;i++){
	        if(i==1){
	            charName="*";
			}else{
	            charName=name.substr(i,i+1);
			}
	        newName+=charName;
		}
		return newName;
	}
</script>
	<body>
	<div class="eui-smallscreenBox">
		<div class="eui-logo">
			<img src="webpage/callnew/takeNo/images/logoYqyz.png">
		</div>
		<div class="eui-table">
			<table border="0" cellpadding="0" cellspacing="0">
				<thead border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th>排队号 </th>
					<th>窗口号 </th>
					<th>呼叫时间 </th>
				</tr>
				</thead>
				<tbody id="mainContent">

				</tbody>
			</table>
		</div>
		<div style="text-align: center; color: #fff; width: 960px; padding-bottom: 20px;margin: auto;font-size:30px;font-weight: bold;">对窗口工作人员拒收材料或不岀具一次性告知函等服务不到位的行为，</div>
		<div style="text-align: center; color: #fff; width: 960px; margin: auto;font-size:30px;font-weight: bold;">请拨打12345热线投诉，我们将给您满意的答复。</div>
	</div>
	</body>

</html>
