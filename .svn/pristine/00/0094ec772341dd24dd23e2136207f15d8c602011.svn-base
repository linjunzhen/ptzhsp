<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <base href="<%=basePath%>">
	    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/css/style2.css">
		<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>平潭落地屏</title>
		<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
    	<link rel="stylesheet" href="plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
    	<link rel="stylesheet" type="text/css" href="webpage/callnew/takeNo/css/index.css">
	</head>
	<body>   
	<%  // 设置刷新页面的时间
      response.setHeader("refresh", "60");
   %>
		<div class="LandingScreen">
			<div class="LandingScreen_title">
				<img src="webpage/callnew/takeNo/images/title.png">
			</div>
			<div class="LandingScreen_body tablebox">
				<table id="tableId" class="" border="0" cellpadding="0" cellspacing="0">
					<thead  border="0" cellpadding="0" cellspacing="0">
			            <tr class="LandingScreen_body_tr1">
							<th class="ToDeclare">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申报号 </th>
							<th class="declarant">申请人 </th>
							<th class="OutState">出件状态 </th>
							<th class="processingTime">受理时间 </th>
						</tr>
			       </thead>
					<tbody>
           					<c:forEach items="${appointList}" var="appointInfo" varStatus="infoStatus">
								<tr style="height:110px!important">
									<td>${appointInfo.EXE_ID}</td>
									<td>${appointInfo.SQRMC}</td>
									<td>
	                   					<c:if test="${appointInfo.RUN_STATUS=='0'}"><font color='red'><b>草稿</b></font></c:if>
	                   					<c:if test="${appointInfo.RUN_STATUS=='1'}"><font color='green'><b>正在办理</b></font></c:if>
	                   					<c:if test="${appointInfo.RUN_STATUS=='2'||appointInfo.RUN_STATUS=='3'}">
	                   						<c:if test="${appointInfo.CJZT=='0'}">
	                   							<font color='red'><b>待取件</b></font>
	                   						</c:if>
	                   						<c:if test="${appointInfo.CJZT=='1'}">
	                   							<font color='white'><b>已取件</b></font>
	                   						</c:if>
	                   					</c:if>
<!-- 	                   					${appointInfo.RUN_STATUS} -->
                   					</td>
<!-- 									<td>${appointInfo.RUN_STATUS}</td> -->
									<td>${appointInfo.CREATE_TIME}</td>
								</tr>
           					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
	<script>
// 参数1 tableID,参数2 div高度，参数3 速度，参数4 tbody中tr几条以上滚动
tableScroll('tableId', 1535, 30, 1)
var MyMarhq;

function tableScroll(tableid, hei, speed, len) {
    clearTimeout(MyMarhq);
    $('#' + tableid).parent().find('.tableid_').remove()
    $('#' + tableid).parent().prepend(
        '<table class="tableid_"><thead>' + $('#' + tableid + ' thead').html() + '</thead></table>'
    ).css({
        'position': 'relative',
        'overflow': 'hidden',
        'height': hei + 'px'
    })
    $(".tableid_").find('th').each(function(i) {
        $(this).css('width', $('#' + tableid).find('th:eq(' + i + ')').width());
    });
    $(".tableid_").css({
        'position': 'absolute',
        'top': 0,
        'left': 0,
        'z-index': 9
    })
    $('#' + tableid).css({
        'position': 'absolute',
        'top': 0,
        'left': 0,
        'z-index': 1
    })

    if ($('#' + tableid).find('tbody tr').length > len) {
        $('#' + tableid).find('tbody').html($('#' + tableid).find('tbody').html() + $('#' + tableid).find('tbody').html());
        $(".tableid_").css('top', 0);
        $('#' + tableid).css('top', 0);
        var tblTop = 0;
        var outerHeight = $('#' + tableid).find('tbody').find("tr").outerHeight();

        function Marqueehq() {
            if (tblTop <= -outerHeight * $('#' + tableid).find('tbody').find("tr").length) {
                tblTop = 0;
            } else {
                tblTop -= 1;
            }
            $('#' + tableid).css('margin-top', tblTop + 'px');
            clearTimeout(MyMarhq);
            MyMarhq = setTimeout(function() {
                Marqueehq()
            }, speed);
        }

        MyMarhq = setTimeout(Marqueehq, speed);
        $('#' + tableid).find('tbody').hover(function() {
            clearTimeout(MyMarhq);
        }, function() {
            clearTimeout(MyMarhq);	
            if ($('#' + tableid).find('tbody tr').length > len) {
                MyMarhq = setTimeout(Marqueehq, speed);
            }
        })
    }

}
</script>
</html>
