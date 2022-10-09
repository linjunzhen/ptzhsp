<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/callnew/takeNo/css/index.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/callnew/takeNo/css/style2.css">
<link rel="stylesheet" href="<%=path%>/webpage/callnew/takeNo/css/default.css" type="text/css">
<script>
	$(function(){
        setInterval('reloadView()',1000);
	});
	function reloadView(){
        $.post("callController.do?lineInfoJson",{},
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
            if(i<15){
            var callTime=callingLine[i].CALLING_TIME;
            if(callTime==''||typeof (callTime)=='undefined'){
                callTime="等候中";
            }else{
                callTime=callTime.substring(11,16);
            }
            var curWin=callingLine[i].CUR_WIN;
            if(curWin==''||typeof (curWin)=='undefined'){
                curWin=""
            }
            var name=formmatName(callingLine[i].LINE_NAME);
           newhtml+="<tr style=\"\">";
           newhtml+="<td style=\"text-align: center\">"+callingLine[i].LINE_NO+"</td>";
           newhtml+="<td>"+name+"</td>";
            newhtml+="<td style=\"text-align: center\">"+curWin+"</td >";
            if("等候中"==callTime){
                newhtml+="<td style=\"text-align: center;color:#ffb72b\">"+callTime+"</td>";
			}else{
                newhtml+="<td style=\"text-align: center\">"+callTime+"</td>";
			}
           newhtml+="</tr>";
        }
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <base href="<%=basePath%>">
		<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>平潭落地屏</title>

	</head>
	<body>   
	<%--<%  // 设置刷新页面的时间--%>
      <%--response.setHeader("refresh", "60");--%>
   <%--%>--%>
		<div class="LandingScreen">
			<div class="LandingScreen_title">
				<img src="webpage/callnew/takeNo/images/title1.png">
			</div>
			<div class="LandingScreen_body tablebox" style="padding:0;">
				<table id="tableId" class="" border="0" cellpadding="0" cellspacing="0" style="margin: auto">
					<thead  border="0" cellpadding="0" cellspacing="0">
			            <tr class="LandingScreen_body_tr1">
							<th class="ToDeclare">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;排队号 </th>
							<th class="declarant">申请人 </th>
							<th class="OutState">窗口号 </th>
							<th class="processingTime">呼叫时间 </th>
						</tr>
			       </thead>
					<tbody id ="mainContent">

					</tbody>
				</table>
			</div>
		<div style="text-align: center; color: #fff; width: 960px; padding-bottom: 20px;margin: auto;font-size:30px;font-weight: bold;">对窗口工作人员拒收材料或不岀具一次性告知函等服务不到位的行为，</div>
		<div style="text-align: center; color: #fff; width: 960px; margin: auto;font-size:30px;font-weight: bold;">请拨打12345热线投诉，我们将给您满意的答复。</div>
		</div>
	</body>
</html>
<script type="text/javascript">
    $(function(){
        $('tbody tr:nth-child(2n)').css('background','#20324a');
    })
</script>