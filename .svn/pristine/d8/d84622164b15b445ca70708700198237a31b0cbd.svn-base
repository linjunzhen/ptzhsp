<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>对台职业资格证书采信证明</title>
    <link href="webpage/wsbs/twqualification/css/weui.css" rel="stylesheet">
    <link href="webpage/wsbs/twqualification/css/public_hb.css" rel="stylesheet" type="text/css">
    <link href="webpage/wsbs/twqualification/css//jquery-weui.css" rel="stylesheet">
    <link href="webpage/wsbs/twqualification/css/style.css" rel="stylesheet">
    <link href="webpage/wsbs/twqualification/css/zui.css" rel="stylesheet">
	
    <script src="webpage/wsbs/twqualification/js/jquery.js"></script>
    <script src="webpage/wsbs/twqualification/js/jquery-weui.js"></script>
    <script src="webpage/wsbs/twqualification/js/zui.js"></script>

    <style>
      .text{
          text-align: center;
      }
      
      .style1{
      	color: #0068b7; 
      	background: #e3f3ff;
      }
    </style>
</head>

<body>
<div class="text">
<table class="table table-striped" id="table">
    <tbody><tr>
        <td>名称</td>
        <td>信息</td>
    </tr>
<tr class="trs"><td>持证人</td><td>梅圣亚</td></tr><tr class="trs"><td>性别</td><td>女</td></tr><tr class="trs"><td>出生年月</td><td>1971.09.18</td></tr><tr class="trs"><td>台胞证号</td><td>02916195</td></tr><tr class="trs"><td>台湾职业资格名称及等级</td><td>美容技术士（乙级）</td></tr><tr class="trs"><td>证书号码</td><td>100-029169</td></tr><tr class="trs"><td colspan="2">经证书比对、采信，可证明梅圣亚具备大陆以下相当水平</td></tr><tr class="trs"><td>采信职业（工种）及等级</td><td>美容师（二级）</td></tr><tr class="trs"><td>有限范围</td><td></td></tr><tr class="trs"><td>证书编号</td><td>20180010002</td></tr><tr class="trs"><td>证书有限期</td><td>2018.9.26-2019.9.27</td></tr></tbody></table>
</div>
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">温馨提示</h4>
            </div>
            <div class="modal-body" id="xiaoxi">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
            var arr = $(".trs");
            var arr2 = $(".span");
            for (var i = arr.length-1;i>=0;i--){
                $(arr[i]).remove();
            }
            for (var i = arr2.length - 1; i >= 0; i--) {
                $(arr2[i]).remove();
            }
            if ('${twQualification.QUALIFICATION_ID}' == "") {
                var span = $("<p class='span'>" + "没有相关数据" + "</p>");
                $("#xiaoxi").append(span);
                $('#myModal').modal('toggle', 'center')
            }else{           
                    var tr1 = $("<tr class='trs'></tr>");
                    var td1 = $("<td>持证人</td>");
                    var td2 = "<td>${twQualification.USERNAME}</td>";
					
					var sex='${twQualification.SEX}';
					if(sex == '1'){
						sex = "男";
					}else if(sex == '2'){
						sex = "女";
					}
                    var tr2 = $("<tr class='trs'></tr>");
                    var td3 = $("<td>性别</td>");
                    var td4 = $("<td>"+sex+"</td>");

                    var tr3 = $("<tr class='trs'></tr>");
                    var td5 = $("<td>出生年月</td>");
                    var td6 = "<td>${twQualification.BIRTHDAY}</td>";

                    var tr4 = $("<tr class='trs'></tr>");
                    var td7 = $("<td>台胞证号</td>");
                    var td8 = "<td>${twQualification.TB_ZJHM}</td>";

                    var tr5 = $("<tr class='trs'></tr>");
                    var td9 = $("<td>台湾职业资格名称及等级</td>");
                    var td10 = "<td>${twQualification.ZYZG_LEVEL}</td>";

                    var tr6 = $("<tr class='trs'></tr>");
                    var td11 = $("<td>证书号码</td>");
                    var td12 = "<td>${twQualification.ZSHM}</td>";

                    var tr7 = $("<tr class='trs'></tr>");
                    var td13 = "<td colspan='2' class='style1'>经证书比对、采信，可证明${twQualification.USERNAME}具备大陆以下相当水平</td>";

                    var tr8 = $("<tr class='trs'></tr>");
                    var td14 = $("<td>采信职业（工种）及等级</td>");
                    var td15 = "<td>${twQualification.ZY_LEVEL}</td>";

                    var tr9 = $("<tr class='trs'></tr>");
                    var td16 = $("<td>有限范围</td>");
                    var td17 = "<td>${twQualification.YXFW}</td>";

                    var tr10 = $("<tr class='trs'></tr>");
                    var td19 = $("<td>证书编号</td>");
                    var td20 = "<td>${twQualification.ZS_NUMBER}</td>";

                    var tr11 = $("<tr class='trs'></tr>");
                    var td21 = $("<td>证书有限期</td>");
                    var td22 = "<td>${twQualification.ZS_VALIDITY}</td>";


                    tr1.append(td1).append(td2);
                    tr2.append(td3).append(td4);
                    tr3.append(td5).append(td6);
                    tr4.append(td7).append(td8);
                    tr5.append(td9).append(td10);
                    tr6.append(td11).append(td12);
                    tr8.append(td14).append(td15);
                    tr9.append(td16).append(td17);
                    tr10.append(td19).append(td20);
                    tr11.append(td21).append(td22);
                    tr7.append(td13);
                    $("#table").append(tr1).append(tr2).append(tr3).append(tr4).append(tr5).append(tr6).append(tr7).append(tr8).append(tr9).append(tr10).append(tr11);
                }
            });


</script>


</body></html>